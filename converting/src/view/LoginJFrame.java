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

//登录窗口
public class LoginJFrame extends JFrame implements MouseListener, FocusListener {


	// 全局的位置变量，用于表示鼠标在窗口上的位置
	static Point origin = new Point();

	// 定义全局组件
	JTextField username = new JTextField(20);
	JPasswordField password = new JPasswordField(20);
	JLabel zhanghao = new JLabel("用户名");
	JLabel mima = new JLabel("密码");
	ImagePanel backgroundPanel = null;
	JButton button_minimize, button_close, button_login, button_register;

	public LoginJFrame() {

		// 窗口淡入淡出动态效果
		new WindowOpacity(this);

		Image backgrounImage = null;
		try {
			backgrounImage = ImageIO.read(new File("image/timg.jpg"));
			Image imgae = ImageIO.read(new File("image/logo.png"));
			this.setIconImage(imgae);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 窗口背景面板
		backgroundPanel = new ImagePanel(backgrounImage);
		backgroundPanel.setLayout(null);

		zhanghao.setBounds(325, 202, 173, 30);
		zhanghao.setFont(MyFont.Static1);

		mima.setBounds(325, 240, 173, 30);
		mima.setFont(MyFont.Static1);

		username.setBounds(378, 202, 173, 30);
		username.setFont(MyFont.Static);
		username.addFocusListener(this);
		username.setText("请输入用户名");

		password.setBounds(378, 240, 173, 30);
		password.setFont(MyFont.Static);
		password.addFocusListener(this);
		password.setText("请输入密码");
		password.setEchoChar('\0');

		button_login = new JButton("登录");
		button_login.setBounds(380, 280, 70, 27);
		button_login.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.lightBlue));
		button_login.setForeground(Color.white);
		button_login.setFont(MyFont.Static);
		button_login.addMouseListener(this);

		button_register = new JButton("注册");
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
					password.requestFocus(); // 获取焦点
				}
			}
		});

		password.addKeyListener(new KeyAdapter() {
			/**
			 * 按钮Enter按键释放事件操作
			 */
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyChar() == KeyEvent.VK_ENTER) {
					if ("请输入用户名".equals(username.getText())) {
						JOptionPane.showMessageDialog(null, "用户名不能为空");
					} else if ("请输入密码".equals(password.getText())) {
						JOptionPane.showMessageDialog(null, "用户密码不能为空");
					} else {
						// 将[用户名,密码]组合成一个字符串数组对象
						String params[] = { username.getText(), password.getText() };
						UserServiceImpl userService = new UserServiceImpl();
						try {
							// 通过UserServiceImpl类对象试图从数据库中查找一条记录
							User user = userService.selectOne(params);
							if (user == null) {
								JOptionPane.showMessageDialog(null, "用户名或者密码有误");
							} else {
								LoginJFrame.this.setVisible(false); // 隐藏当前页面
								new IndexJFrame(user); // 进入系统主界面,并传递user参数

							}
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					} // user-password
				} // KeyEvent.VK_ENTER

			} // keyReleased
		});

		this.add(backgroundPanel);
		this.setTitle("  系统用户登录");
		this.setSize(830, 530);
		this.setVisible(true);
		this.requestFocus();
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
	}

	// 鼠标点击事件
	//@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == button_login) {
			if ("请输入用户名".equals(username.getText())) {
				JOptionPane.showMessageDialog(null, "用户名密码不能为空");
			} else if ("请输入密码".equals(password.getText())) {
				JOptionPane.showMessageDialog(null, "用户密码不能为空");
			} else {
				// 将[用户名,密码]组合成一个字符串数组对象
				String params[] = { username.getText(), password.getText() };
				UserServiceImpl userService = new UserServiceImpl();
				try {
					// 通过UserServiceImpl类对象试图从数据库中查找一条记录
					User user = userService.selectOne(params);
					if (user == null) {
						JOptionPane.showMessageDialog(null, "用户名或者密码有误");
					} else {
						this.setVisible(false); // 隐藏当前页面
						new IndexJFrame(user); // 进入系统主界面,并传递user参数
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

	// 聚焦事件
	//@Override
	public void focusGained(FocusEvent e) {
		if (e.getSource() == username) {
			if (username.getText().equals("请输入用户名")) {
				username.setText("");
			}
		} else if (e.getSource() == password) {
			if (password.getText().equals("请输入密码")) {
				password.setText("");
				password.setEchoChar('*');
			}
		}

	}

	// 失焦事件
	//@Override
	public void focusLost(FocusEvent e) {
		if (e.getSource() == username) {
			if (username.getText().equals("")) {
				username.setText("请输入用户名");
			}
		} else if (e.getSource() == password) {
			if (password.getText().equals("")) {
				password.setText("请输入密码");
				password.setEchoChar('\0');
			}
		}
	}

}