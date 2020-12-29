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
	JFrame f = new JFrame("���û�ע��");
	static Point origin = new Point();

	// ����ȫ�����
	JTextField username = new JTextField(20);
	JPasswordField password = new JPasswordField(20);
	JLabel zhanghao = new JLabel("�û���");
	JLabel mima = new JLabel("����");
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
		username.setText("�������û���");

		password.setBounds(130, 100, 173, 30);
		password.setFont(MyFont.Static);
		password.addFocusListener(this);
		password.setText("����������");
		password.setEchoChar('\0');

		button_register = new JButton("ע��");
		button_register.setBounds(120, 150, 70, 27);
		button_register.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.lightBlue));
		button_register.setForeground(Color.white);
		button_register.setFont(MyFont.Static);
		button_register.addMouseListener(this);

		button_exit = new JButton("�˳�");
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
					}  else {
						// ��[�û���,����]��ϳ�һ���ַ����������
						String params[] = { username.getText() };
						UserServiceImpl userService = new UserServiceImpl();
						int result = 0;
						try {
							// ͨ��UserServiceImpl�������ͼ�����ݿ��в���һ����¼
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
									JOptionPane.showMessageDialog(null, "ע��ɹ�");
									f.setVisible(false); // ���ص�ǰҳ��

								}

							} else {
								JOptionPane.showMessageDialog(null, "�û����ظ�");

							}
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					} // user-password
				} // KeyEvent.VK_ENTER

			} // keyReleased
		});

	}

	// �۽��¼�

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

	// ������¼�

	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == button_register) {
			if ("�������û���".equals(username.getText())) {
				JOptionPane.showMessageDialog(null, "�û������벻��Ϊ��");
			} else if ("����������".equals(password.getText())) {
				JOptionPane.showMessageDialog(null, "�û����벻��Ϊ��");
			} else {
				// ��[�û���,����]��ϳ�һ���ַ����������
				String params[] = { username.getText() };
				UserServiceImpl userService = new UserServiceImpl();
				int result = 0;
				try {
					// ͨ��UserServiceImpl�������ͼ�����ݿ��в���һ����¼
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
							JOptionPane.showMessageDialog(null, "ע��ɹ�");
							f.setVisible(false); // ���ص�ǰҳ��

						}

					} else {
						JOptionPane.showMessageDialog(null, "�û����ظ�");

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