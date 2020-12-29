package view;

import entity.User;
import framework.ImagePanel;
import framework.MyFont;
import framework.WindowOpacity;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

//ϵͳ������
public class IndexJFrame extends JFrame implements MouseListener, ActionListener {

	// �����û�����
	private User user;

	// ���帨������
	int sign_home = 0;
	int sign_baseData = 0;
	int sign_purchase_sale_stock = 0;
	int sign_userManager = 0;

	// �����Ļ�Ĵ�С
	final static int width = Toolkit.getDefaultToolkit().getScreenSize().width;
	final static int height = Toolkit.getDefaultToolkit().getScreenSize().height;

	// ����ȫ�����
	JPanel backgroundPanel, topPanel, topMenu, topPrompt, centerPanel, subPanel, subMenu;
	JTabbedPane jTabbedPane;

	JLabel home, baseData, purchase_sale_stock, userManager;

	public IndexJFrame(User user) {

		this.user = user;
		//���ڵ��뵭��Ч��
		new WindowOpacity(this);
		// ����tab�������
		UIManager.put("TabbedPane.tabAreaInsets", new javax.swing.plaf.InsetsUIResource(0, 0, 0, 0));
		try {
			Image imgae = ImageIO.read(new File("image/logo.png"));
			this.setIconImage(imgae);
		} catch (IOException e) {
			e.printStackTrace();
		}

		initBackgroundPanel();
		this.setTitle("���ݿ����ϵͳ");
		this.setSize((int) (width * 0.8f), (int) (height * 0.8f));
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	// ��ʼ���������
	public void initBackgroundPanel() {
		try{
			File file = new File("D:\\convert");
			if (!file.exists()){
				file.mkdir();
				String f = file.getAbsolutePath()+"\\�ļ�Ŀ¼˵��.txt";
				FileWriter out = new FileWriter(f);
				out.write("���ļ���¼��¼��վ��Ϣ������ɾ��");
				out.close();
			}
		}catch (IOException e1){
			e1.printStackTrace();
		}

		backgroundPanel = new JPanel(new BorderLayout());
		initTop();
		initCenterPanel();

		backgroundPanel.add(topPanel, "North");
		backgroundPanel.add(centerPanel, "Center");
		this.add(backgroundPanel);
	}

	// ��ʼ�������������
	public void initTop() {

		initTopMenu();
		initTopPrompt();

		topPanel = new JPanel(new BorderLayout());
		topPanel.setPreferredSize(new Dimension(width, 40));

		topPanel.add(topMenu, "West");
		topPanel.add(topPrompt, "East");
	}

	// ��ʼ�������˵�
	public void initTopMenu() {

		topMenu = new JPanel();
		topMenu.setPreferredSize(new Dimension(500, 40));
		topMenu.setOpaque(false);

		String[] nameStrings = { "��ҳ", "��������"};

		home = CreateMenuLabel(home, nameStrings[0], "home", topMenu);
		home.setName("home");
		baseData = CreateMenuLabel(baseData, nameStrings[1], "baseData", topMenu);
		baseData.setName("baseData");


	}

	// ���������˵�Label
	public JLabel CreateMenuLabel(JLabel jlb, String text, String name, JPanel who) {
		JLabel line = new JLabel("<html>&nbsp;<font color='#D2D2D2'>|</font>&nbsp;</html>");
		Icon icon = new ImageIcon("image/" + name + ".png");
		jlb = new JLabel(icon);
		jlb.setText("<html><font color='black'>" + text + "</font>&nbsp;</html>");
		jlb.addMouseListener(this);
		jlb.setFont(MyFont.Static);
		who.add(jlb);

		return jlb;
	}

	// ��ʼ��������ӭ���
	public void initTopPrompt() {

		Icon icon = new ImageIcon("image/male.png");
		JLabel label = new JLabel(icon);
		if (user != null) {
			label.setText("<html><font color='black'>��ӭ����</font><font color='#336699'><b>" + this.user.getName()
					+ "</b></font></html>");
		} else {
			label.setText("<html><font color='black'>��ӭ����</font><font color='#336699'><b></b></font></html>");
		}
		label.setFont(MyFont.Static);
		topPrompt = new JPanel();
		topPrompt.setPreferredSize(new Dimension(180, 40));
		topPrompt.setOpaque(false);
		topPrompt.add(label);
	}

	// ��ʼ���������
	public void initCenterPanel() {
		centerPanel = new JPanel(new BorderLayout());
		home.setText("<html><font color='#336699' style='font-weight:bold'>" + "��ҳ" + "</font>&nbsp;</html>");
		creatHome();
		centerPanel.setOpaque(false);
	}

	// ��ʼ����������
	public void initSign() {
		sign_home = 0;
		sign_baseData = 0;
		sign_purchase_sale_stock = 0;
		sign_userManager = 0;
	}

	// ������ҳ���
	public void creatHome() {
		centerPanel.removeAll();
		try {
			Image bgimg = ImageIO.read(new File("image/indexbackground.png"));
			ImagePanel centerBackground = new ImagePanel(bgimg);
			centerPanel.add(centerBackground, "Center");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// ���������������
	public void creatBaseDataTab() {
		centerPanel.removeAll();
		// ����tab����λ��
		jTabbedPane = new JTabbedPane(JTabbedPane.TOP);
		// ����tab����
		jTabbedPane.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
		jTabbedPane.setFont(MyFont.Static);

		//ͨ����������һ������
		jTabbedPane.addTab("����վ����", new ConverterFrame().backgroundPanel);
		jTabbedPane.addTab("���վ����",new TransformerManage().backgroundPanel);
		jTabbedPane.addTab("���վ����",new MixManageFrame().backgroundPanel);
		centerPanel.add(jTabbedPane, "Center");
	}

	// ������¼�
	//@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == home) {
			initSign();
			sign_home = 1;
			creatHome();
			home.setText("<html><font color='#336699' style='font-weight:bold'>" + "��ҳ" + "</font>&nbsp;</html>");
			baseData.setText("<html><font color='black'>" + "��������" + "</font>&nbsp;</html>");
		} else if (e.getSource() == baseData) {
			initSign();
			sign_baseData = 1;
			creatBaseDataTab();
			baseData.setText("<html><font color='#336699' style='font-weight:bold'>" + "��������" + "</font>&nbsp;</html>");
			home.setText("<html><font color='black'>" + "��ҳ" + "</font>&nbsp;</html>");
		} else {
			System.out.println("ok");
		}

	}

	// ��껮���¼�
	//@Override
	public void mouseEntered(MouseEvent e) {
		if (e.getSource() == home) {
			home.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			home.setText("<html><font color='#336699' style='font-weight:bold'>" + "��ҳ" + "</font>&nbsp;</html>");
		} else if (e.getSource() == baseData) {
			baseData.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			baseData.setText("<html><font color='#336699' style='font-weight:bold'>" + "��������" + "</font>&nbsp;</html>");
		}

	}

	// ��껮���¼�
	//@Override
	public void mouseExited(MouseEvent e) {
		if (e.getSource() == home) {
			if (sign_home == 0) {
				home.setText("<html><font color='black'>" + "��ҳ" + "</font>&nbsp;</html>");
			}
		} else if (e.getSource() == baseData) {
			if (sign_baseData == 0) {
				baseData.setText("<html><font color='black'>" + "��������" + "</font>&nbsp;</html>");
			}
		}
	}

	//@Override
	public void mousePressed(MouseEvent e) {
		
	}

	//@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	//@Override
	public void actionPerformed(ActionEvent e) {

	}
	
}