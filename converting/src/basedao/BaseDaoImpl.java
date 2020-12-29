package basedao;

import framework.JdbcUtil;
import org.apache.log4j.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 说明：基础Dao数据库操作实现：增、删、改、查4个基本操作
 * 
 * @author LS
 * 
 * 
 */
public class BaseDaoImpl {

	private static Logger logger = Logger.getLogger(BaseDaoImpl.class);
	private Connection conn;
	private PreparedStatement pst;
	private ResultSet rs;

	// 构造方法获取数据库连接
	public BaseDaoImpl() {
		conn = new JdbcUtil().getJdbcUtil().getConnection();
	}

	// 重写查询数据方法
	public List select(String sql, int columnNum, Object[] paraArray) throws SQLException {
		List list = new ArrayList();
		pst = conn.prepareStatement(sql);
		if (paraArray != null) {
			for (int i = 0, length = paraArray.length; i < length; i++) {
				pst.setObject(i + 1, paraArray[i]);
			}
		}
		ResultSet rs = pst.executeQuery();
		while (rs.next()) {
			Object[] array = new Object[columnNum];
			for (int i = 0; i < columnNum; i++) {
				array[i] = rs.getObject(i + 1);
			}
			list.add(array);
		}
		new JdbcUtil().getJdbcUtil().release(rs, pst, conn);
		return list;
	}

	// 重写插入数据方法

	public int insert(String sql, Object[] paraArray) throws SQLException {
		pst = conn.prepareStatement(sql);
		for (int i = 0, length = paraArray.length; i < length; i++) {
			pst.setObject(i + 1, paraArray[i]);
		}
		int i = pst.executeUpdate();
		new JdbcUtil().getJdbcUtil().release(rs, pst, conn);
		return i;
	}

	// 重写更新数据方法

	public int update(String sql, Object[] paraArray) throws SQLException {
		pst = conn.prepareStatement(sql);
		for (int i = 0, length = paraArray.length; i < length; i++) {
			pst.setObject(i + 1, paraArray[i]);
		}
		int i = pst.executeUpdate();
		new JdbcUtil().getJdbcUtil().release(rs, pst, conn);
		return i;
	}

	// 重写删除数据方法
	public int delete(String sql, Object[] paraArray) throws SQLException {
		pst = conn.prepareStatement(sql);
		for (int i = 0, length = paraArray.length; i < length; i++) {
			pst.setObject(i + 1, paraArray[i]);
		}
		int i = pst.executeUpdate();
		new JdbcUtil().getJdbcUtil().release(rs, pst, conn);
		return i;
	}

}