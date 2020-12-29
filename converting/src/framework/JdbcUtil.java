package framework;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class JdbcUtil {

	private static Logger logger = Logger.getLogger(JdbcUtil.class);
	private static JdbcUtil jdbcUtil;
	private static String url = null;
	private static String username = null;
	private static String password = null;
	private static String driver = null;
	private static Properties props = new Properties();

	static {

		try {
			// ��ȡ���ݿ������ļ�
			props.load(JdbcUtil.class.getResourceAsStream("/jdbc.properties"));
		} catch (IOException e) {
			logger.error("����jdbc.properties�����ļ��쳣", e);
		}
		
		driver = (props.getProperty("jdbc.driver"));
		url = (props.getProperty("jdbc.url"));
		username = (props.getProperty("jdbc.username"));
		password = (props.getProperty("jdbc.password"));
		
		// �������ݿ�����
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			logger.error("�������ݿ������쳣", e);
		}

	}

	// ����ģʽ
	public JdbcUtil getJdbcUtil() {
		if (jdbcUtil == null) {
			synchronized (JdbcUtil.class) {
				if (jdbcUtil == null) {
					jdbcUtil = new JdbcUtil();
				}
			}
		}
		return jdbcUtil;
	}

	/**
	 * ����һ�����ݿ�����
	 * 
	 * @return һ�����ݿ�����
	 * 
	 */
	public Connection getConnection() {
		Connection conn = null;
		// �������ݿ�����
		try {
			conn = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			logger.error("�������ݿ����ӷ����쳣", e);
		}
		return conn;
	}

	/**
	 * �ͷ����ݿ���Դ
	 */
	public void release(Object o) {
		if (o == null) {
			return;
		}
		if (o instanceof ResultSet) {
			try {
				((ResultSet) o).close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if (o instanceof PreparedStatement) {
			try {
				((PreparedStatement) o).close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if (o instanceof Connection) {
			Connection c = (Connection) o;
			try {
				if (!c.isClosed()) {
					c.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	// �ͷ����ݿ���Դ��������
	public void release(ResultSet rs, PreparedStatement pst, Connection conn) {
		release(rs);
		release(pst);
		release(conn);
	}

}