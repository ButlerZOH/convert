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

//系统主界面
public class IndexJFrame extends JFrame implements MouseListener, ActionListener {

	// 定义用户对象
	private User user;

	// 定义辅助变量
	int sign_home = 0;
	int sign_baseData = 0;
	int sign_purchase_sale_stock = 0;
	int sign_userManager = 0;

	// 获得屏幕的大小
	final static int width = Toolkit.getDefaultToolkit().getScreenSize().width;
	final static int height = Toolkit.getDefaultToolkit().getScreenSize().height;

	// 定义全局组件
	JPanel backgroundPanel, topPanel, topMenu, topPrompt, centerPanel, subPanel, subMenu;
	JTabbedPane jTabbedPane;

	JLabel home, baseData, purchase_sale_stock, userManager;

	public IndexJFrame(User user) {

		this.user = user;
		//窗口淡入淡出效果
		new WindowOpacity(this);
		// 设置tab面板缩进
		UIManager.put("TabbedPane.tabAreaInsets", new javax.swing.plaf.InsetsUIResource(0, 0, 0, 0));
		try {
			Image imgae = ImageIO.read(new File("image/logo.png"));
			this.setIconImage(imgae);
		} catch (IOException e) {
			e.printStackTrace();
		}

		initBackgroundPanel();
		this.setTitle("数据库管理系统");
		this.setSize((int) (width * 0.8f), (int) (height * 0.8f));
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	// 初始化背景面板
	public void initBackgroundPanel() {
		try{
			File file = new File("D:\\convert");
			if (!file.exists()){
				file.mkdir();
				String f = file.getAbsolutePath()+"\\文件目录说明.txt";
				FileWriter out = new FileWriter(f);
				out.write("该文件记录记录电站信息，切勿删除");
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

	// 初始化顶部顶部面板
	public void initTop() {

		initTopMenu();
		initTopPrompt();

		topPanel = new JPanel(new BorderLayout());
		topPanel.setPreferredSize(new Dimension(width, 40));

		topPanel.add(topMenu, "West");
		topPanel.add(topPrompt, "East");
	}

	// 初始化顶部菜单
	public void initTopMenu() {

		topMenu = new JPanel();
		topMenu.setPreferredSize(new Dimension(500, 40));
		topMenu.setOpaque(false);

		String[] nameStrings = { "首页", "基础数据"};

		home = CreateMenuLabel(home, nameStrings[0], "home", topMenu);
		home.setName("home");
		baseData = CreateMenuLabel(baseData, nameStrings[1], "baseData", topMenu);
		baseData.setName("baseData");


	}

	// 创建顶部菜单Label
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

	// 初始化顶部欢迎面板
	public void initTopPrompt() {

		Icon icon = new ImageIcon("image/male.png");
		JLabel label = new JLabel(icon);
		if (user != null) {
			label.setText("<html><font color='black'>欢迎您，</font><font color='#336699'><b>" + this.user.getName()
					+ "</b></font></html>");
		} else {
			label.setText("<html><font color='black'>欢迎您，</font><font color='#336699'><b></b></font></html>");
		}
		label.setFont(MyFont.Static);
		topPrompt = new JPanel();
		topPrompt.setPreferredSize(new Dimension(180, 40));
		topPrompt.setOpaque(false);
		topPrompt.add(label);
	}

	// 初始化中心面板
	public void initCenterPanel() {
		centerPanel = new JPanel(new BorderLayout());
		home.setText("<html><font color='#336699' style='font-weight:bold'>" + "首页" + "</font>&nbsp;</html>");
		creatHome();
		centerPanel.setOpaque(false);
	}

	// 初始化辅助变量
	public void initSign() {
		sign_home = 0;
		sign_baseData = 0;
		sign_purchase_sale_stock = 0;
		sign_userManager = 0;
	}

	// 创建首页面板
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

	// 创建基础数据面板
	public void creatBaseDataTab() {
		centerPanel.removeAll();
		// 设置tab标题位置
		jTabbedPane = new JTabbedPane(JTabbedPane.TOP);
		// 设置tab布局
		jTabbedPane.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
		jTabbedPane.setFont(MyFont.Static);

		//通过这里进入第一个界面
		jTabbedPane.addTab("换流站管理", new ConverterFrame().backgroundPanel);
		jTabbedPane.addTab("变电站管理",new TransformerManage().backgroundPanel);
		jTabbedPane.addTab("混合站管理",new MixManageFrame().backgroundPanel);
		centerPanel.add(jTabbedPane, "Center");
	}

	// 鼠标点击事件
	//@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == home) {
			initSign();
			sign_home = 1;
			creatHome();
			home.setText("<html><font color='#336699' style='font-weight:bold'>" + "首页" + "</font>&nbsp;</html>");
			baseData.setText("<html><font color='black'>" + "基础数据" + "</font>&nbsp;</html>");
		} else if (e.getSource() == baseData) {
			initSign();
			sign_baseData = 1;
			creatBaseDataTab();
			baseData.setText("<html><font color='#336699' style='font-weight:bold'>" + "基础数据" + "</font>&nbsp;</html>");
			home.setText("<html><font color='black'>" + "首页" + "</font>&nbsp;</html>");
		} else {
			System.out.println("ok");
		}

	}

	// 鼠标划入事件
	//@Override
	public void mouseEntered(MouseEvent e) {
		if (e.getSource() == home) {
			home.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			home.setText("<html><font color='#336699' style='font-weight:bold'>" + "首页" + "</font>&nbsp;</html>");
		} else if (e.getSource() == baseData) {
			baseData.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			baseData.setText("<html><font color='#336699' style='font-weight:bold'>" + "基础数据" + "</font>&nbsp;</html>");
		}

	}

	// 鼠标划出事件
	//@Override
	public void mouseExited(MouseEvent e) {
		if (e.getSource() == home) {
			if (sign_home == 0) {
				home.setText("<html><font color='black'>" + "首页" + "</font>&nbsp;</html>");
			}
		} else if (e.getSource() == baseData) {
			if (sign_baseData == 0) {
				baseData.setText("<html><font color='black'>" + "基础数据" + "</font>&nbsp;</html>");
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