package com.shopbilling.ui;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JWindow;
import javax.swing.SwingConstants;

public class SplashUI {

	public SplashUI(){
		JWindow window = new JWindow();
		window.getContentPane().add(
		    new JLabel("", new ImageIcon(SplashUI.class.getResource("/images/splash.png")), SwingConstants.CENTER));
		window.setBounds(100, 250, 300, 250);
		 Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		    int x = (int) ((dimension.getWidth() - window.getWidth()) / 2);
		    int y = (int) ((dimension.getHeight() - window.getHeight()) / 2);
		   window.setLocation(x, y);
		window.setVisible(true);
		try {
		    Thread.sleep(2000);
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
		window.setVisible(false);
		window.dispose();
	}
}
