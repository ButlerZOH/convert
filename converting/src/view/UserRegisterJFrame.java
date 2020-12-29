package view;

import entity.User;
import framework.MyFont;
import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;
import services.UserServiceImpl;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class UserRegisterJFrame implements MouseListener, FocusListener {
	JFrame f = new JFrame("新用户注册");
	static Point origin = new Point();

	// 定义全局组件
	JTextField username = new JTextField(20);
	JPasswordField password = new JPasswordField(20);
	JLabel zhanghao = new JLabel("用户名");
	JLabel mima = new JLabel("密码");
	JPanel backgroundPanel = new JPanel();
	JButton button_minimize, button_close, button_register, button_exit;

	public UserRegisterJFrame() {
		zhanghao.setBounds(70, 50, 173, 30);
		zhanghao.setFont(MyFont.Static1);

		mima.setBounds(70, 100, 173, 30);
		mima.setFont(MyFont.Static1);

		username.setBounds(130, 50, 173, 30);
		username.setFont(MyFont.Static);
		username.addFocusListener(this);
		username.setText("请输入用户名");

		password.setBounds(130, 100, 173, 30);
		password.setFont(MyFont.Static);
		password.addFocusListener(this);
		password.setText("请输入密码");
		password.setEchoChar('\0');

		button_register = new JButton("注册");
		button_register.setBounds(120, 150, 70, 27);
		button_register.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.lightBlue));
		button_register.setForeground(Color.white);
		button_register.setFont(MyFont.Static);
		button_register.addMouseListener(this);

		button_exit = new JButton("退出");
		button_exit.setBounds(200, 150, 70, 27);
		button_exit.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.red));
		button_exit.setForeground(Color.white);
		button_exit.setFont(MyFont.Static);
		button_exit.addMouseListener(this);

		backgroundPanel.setSize(430, 300);
		backgroundPanel.setLayout(null);
		backgroundPanel.add(zhanghao);
		backgroundPanel.add(mima);
		backgroundPanel.add(password);
		backgroundPanel.add(button_register);
		backgroundPanel.add(button_exit);
		backgroundPanel.add(username);
		try {
			Image imgae = ImageIO.read(new File("image/logo.png"));
			f.setIconImage(imgae);
		} catch (IOException e) {
			e.printStackTrace();
		}
		f.add(backgroundPanel);
		f.setVisible(true);
		f.setSize(430, 300);
		f.setLocationRelativeTo(null);
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
					}  else {
						// 将[用户名,密码]组合成一个字符串数组对象
						String params[] = { username.getText() };
						UserServiceImpl userService = new UserServiceImpl();
						int result = 0;
						try {
							// 通过UserServiceImpl类对象试图从数据库中查找一条记录
							User user = userService.selectOne2(params);
							if (user == null) {
								String id = UUID.randomUUID().toString().replaceAll("-", "");
								String params2[] = { id, username.getText(), password.getText() };
								UserServiceImpl userregister = new UserServiceImpl();
								try {
									result = userregister.registerUser(params2);
								} catch (Exception e1) {
									e1.printStackTrace();
								}
								if (result > 0) {
									JOptionPane.showMessageDialog(null, "注册成功");
									f.setVisible(false); // 隐藏当前页面

								}

							} else {
								JOptionPane.showMessageDialog(null, "用户名重复");

							}
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					} // user-password
				} // KeyEvent.VK_ENTER

			} // keyReleased
		});

	}

	// 聚焦事件

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

	// 鼠标点击事件

	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == button_register) {
			if ("请输入用户名".equals(username.getText())) {
				JOptionPane.showMessageDialog(null, "用户名密码不能为空");
			} else if ("请输入密码".equals(password.getText())) {
				JOptionPane.showMessageDialog(null, "用户密码不能为空");
			} else {
				// 将[用户名,密码]组合成一个字符串数组对象
				String params[] = { username.getText() };
				UserServiceImpl userService = new UserServiceImpl();
				int result = 0;
				try {
					// 通过UserServiceImpl类对象试图从数据库中查找一条记录
					User user = userService.selectOne2(params);
					if (user == null) {
						String id = UUID.randomUUID().toString().replaceAll("-", "");
						String params2[] = { id, username.getText(), password.getText() };
						UserServiceImpl userregister = new UserServiceImpl();
						try {
							result = userregister.registerUser(params2);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
						if (result > 0) {
							JOptionPane.showMessageDialog(null, "注册成功");
							f.setVisible(false); // 隐藏当前页面

						}

					} else {
						JOptionPane.showMessageDialog(null, "用户名重复");

					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		} else if (e.getSource() == button_exit) {
			f.setVisible(false);
		}
	}

	//@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	//@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	//@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	//@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}