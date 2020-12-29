package framework;

import javax.swing.*;
import java.awt.*;

public class ImagePanel extends JPanel {
	
	// ����ͼ�����
	Image im;

	// ���캯��
	public ImagePanel(Image im) {
		this.im = im;// ��ʼ��ͼ�����
		// ��ȡ��ǰ��Ļ���
		int width = Toolkit.getDefaultToolkit().getScreenSize().width;
		int height = Toolkit.getDefaultToolkit().getScreenSize().height;
		this.setSize(width, height);
	}

	// �������
	public void paintComponent(Graphics g) {
		// �̳и��෽��
		super.paintComponent(g);
		g.drawImage(im, 0, 0, this.getWidth(), this.getHeight(), this);
	}
}
