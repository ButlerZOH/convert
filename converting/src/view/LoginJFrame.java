package view;

import entity.User;
import framework.ImagePanel;
import framework.MyFont;
import framework.WindowOpacity;
import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;
import services.UserServiceImpl;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

//��¼����
public class LoginJFrame extends JFrame implements MouseListener, FocusListener {


	// ȫ�ֵ�λ�ñ��������ڱ�ʾ����ڴ����ϵ�λ��
	static Point origin = new Point();

	// ����ȫ�����
	JTextField username = new JTextField(20);
	JPasswordField password = new JPasswordField(20);
	JLabel zhanghao = new JLabel("�û���");
	JLabel mima = new JLabel("����");
	ImagePanel backgroundPanel = null;
	JButton button_minimize, button_close, button_login, button_register;

	public LoginJFrame() {

		// ���ڵ��뵭����̬Ч��
		new WindowOpacity(this);

		Image backgrounImage = null;
		try {
			backgrounImage = ImageIO.read(new File("image/timg.jpg"));
			Image imgae = ImageIO.read(new File("image/logo.png"));
			this.setIconImage(imgae);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// ���ڱ������
		backgroundPanel = new ImagePanel(backgrounImage);
		backgroundPanel.setLayout(null);

		zhanghao.setBounds(325, 202, 173, 30);
		zhanghao.setFont(MyFont.Static1);

		mima.setBounds(325, 240, 173, 30);
		mima.setFont(MyFont.Static1);

		username.setBounds(378, 202, 173, 30);
		username.setFont(MyFont.Static);
		username.addFocusListener(this);
		username.setText("�������û���");

		password.setBounds(378, 240, 173, 30);
		password.setFont(MyFont.Static);
		password.addFocusListener(this);
		password.setText("����������");
		password.setEchoChar('\0');

		button_login = new JButton("��¼");
		button_login.setBounds(380, 280, 70, 27);
		button_login.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.lightBlue));
		button_login.setForeground(Color.white);
		button_login.setFont(MyFont.Static);
		button_login.addMouseListener(this);

		button_register = new JButton("ע��");
		button_register.setBounds(480, 280, 70, 27);
		button_register.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.red));
		button_register.setForeground(Color.white);
		button_register.setFont(MyFont.Static);
		button_register.addMouseListener(this);

		backgroundPanel.add(zhanghao);
		backgroundPanel.add(mima);
		backgroundPanel.add(password);
		backgroundPanel.add(button_login);
		backgroundPanel.add(button_register);
		backgroundPanel.add(username);

		username.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyChar() == KeyEvent.VK_ENTER) {
					password.requestFocus(); // ��ȡ����
				}
			}
		});

		password.addKeyListener(new KeyAdapter() {
			/**
			 * ��ťEnter�����ͷ��¼�����
			 */
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyChar() == KeyEvent.VK_ENTER) {
					if ("�������û���".equals(username.getText())) {
						JOptionPane.showMessageDialog(null, "�û�������Ϊ��");
					} else if ("����������".equals(password.getText())) {
						JOptionPane.showMessageDialog(null, "�û����벻��Ϊ��");
					} else {
						// ��[�û���,����]��ϳ�һ���ַ����������
						String params[] = { username.getText(), password.getText() };
						UserServiceImpl userService = new UserServiceImpl();
						try {
							// ͨ��UserServiceImpl�������ͼ�����ݿ��в���һ����¼
							User user = userService.selectOne(params);
							if (user == null) {
								JOptionPane.showMessageDialog(null, "�û���������������");
							} else {
								LoginJFrame.this.setVisible(false); // ���ص�ǰҳ��
								new IndexJFrame(user); // ����ϵͳ������,������user����

							}
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					} // user-password
				} // KeyEvent.VK_ENTER

			} // keyReleased
		});

		this.add(backgroundPanel);
		this.setTitle("  ϵͳ�û���¼");
		this.setSize(830, 530);
		this.setVisible(true);
		this.requestFocus();
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
	}

	// ������¼�
	//@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == button_login) {
			if ("�������û���".equals(username.getText())) {
				JOptionPane.showMessageDialog(null, "�û������벻��Ϊ��");
			} else if ("����������".equals(password.getText())) {
				JOptionPane.showMessageDialog(null, "�û����벻��Ϊ��");
			} else {
				// ��[�û���,����]��ϳ�һ���ַ����������
				String params[] = { username.getText(), password.getText() };
				UserServiceImpl userService = new UserServiceImpl();
				try {
					// ͨ��UserServiceImpl�������ͼ�����ݿ��в���һ����¼
					User user = userService.selectOne(params);
					if (user == null) {
						JOptionPane.showMessageDialog(null, "�û���������������");
					} else {
						this.setVisible(false); // ���ص�ǰҳ��
						new IndexJFrame(user); // ����ϵͳ������,������user����
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		else if (e.getSource() == button_register) {
			new UserRegisterJFrame();
		}

	}

	//@Override
	public void mouseEntered(MouseEvent arg0) {

	}

	//@Override
	public void mouseExited(MouseEvent arg0) {

	}

	//@Override
	public void mousePressed(MouseEvent arg0) {

	}

	//@Override
	public void mouseReleased(MouseEvent arg0) {

	}

	// �۽��¼�
	//@Override
	public void focusGained(FocusEvent e) {
		if (e.getSource() == username) {
			if (username.getText().equals("�������û���")) {
				username.setText("");
			}
		} else if (e.getSource() == password) {
			if (password.getText().equals("����������")) {
				password.setText("");
				password.setEchoChar('*');
			}
		}

	}

	// ʧ���¼�
	//@Override
	public void focusLost(FocusEvent e) {
		if (e.getSource() == username) {
			if (username.getText().equals("")) {
				username.setText("�������û���");
			}
		} else if (e.getSource() == password) {
			if (password.getText().equals("")) {
				password.setText("����������");
				password.setEchoChar('\0');
			}
		}
	}

}