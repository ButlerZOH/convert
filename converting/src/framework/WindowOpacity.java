package framework;

import com.sun.awt.AWTUtilities;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 窗口淡入淡出
 */


public class WindowOpacity {
	public WindowOpacity(final JFrame jframe) {
		// 窗口设置淡入淡出代码段
		AWTUtilities.setWindowOpacity(jframe,1);
		/*ActionListener lisener = new ActionListener() {
			float alpha = 0;
			@Override
			public void actionPerformed(ActionEvent e) {
				if (alpha < 0.9) {
					AWTUtilities.setWindowOpacity(jframe, alpha += 0.1);
				} else {
					AWTUtilities.setWindowOpacity(jframe, 1);
					Timer source = (Timer) e.getSource();
					source.stop();
				}
			}
		};
		// 设置线程控制
		new Timer(200, lisener).start();*/
	}
}
