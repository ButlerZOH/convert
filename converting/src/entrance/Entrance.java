package entrance;

import entity.User;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
import view.LoginJFrame;

import javax.swing.*;


public class Entrance {

	public static void main(String[] args) {
		try {

			BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.translucencyAppleLike;
			org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
			UIManager.put("RootPane.setupButtonVisible", false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//User user = new User();
		new LoginJFrame();

	}
}