package com.shopbilling.ui;

import java.awt.Color;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.shopbilling.constants.AppConstants;
import com.shopbilling.properties.AppProperties;
import com.shopbilling.services.AppLicenseServices;
import com.thehowtotutorial.splashscreen.JSplash;

public class SplashRun {
	
	private final static Logger logger = Logger.getLogger(SplashRun.class);

	public static void main(String[] args) throws Exception {
		JSplash jsplash  = null;
		try {
			PropertyConfigurator.configure(SplashRun.class.getClassLoader().getResourceAsStream("resources/log4j.properties"));
			jsplash = new JSplash(SplashRun.class.getResource("/images/MyStoreSplash.png"), true, true, false, null, null, Color.BLACK, Color.GRAY);
			jsplash.splashOn();
			jsplash.setProgress(20, "Loading Properties");
			Thread.sleep(1000);
			jsplash.setProgress(50, "Applying Properties");
			Thread.sleep(1000);
			if(!AppProperties.check()){
				jsplash.splashOff();
				JOptionPane.showMessageDialog(null, AppConstants.LICENSE_ERROR_1, AppConstants.LICENSE_ERROR, JOptionPane.WARNING_MESSAGE);
				System.exit(0);
			}else{
				if(AppLicenseServices.change()){
					jsplash.splashOff();
					JOptionPane.showMessageDialog(null, AppConstants.COMP_DATE_ERROR, AppConstants.COMP_DATE, JOptionPane.WARNING_MESSAGE);
					System.exit(0);
				}else{
					if(!AppProperties.doCheck()){
						jsplash.splashOff();
						JOptionPane.showMessageDialog(null, AppConstants.LICENSE_ERROR_2, AppConstants.LICENSE_EXPIRED, JOptionPane.WARNING_MESSAGE);
						System.exit(0);
					}else{
						jsplash.setProgress(80, "Starting Application");
						Thread.sleep(1000);
						jsplash.splashOff();
						ManageStoreUI frame = new ManageStoreUI();
						frame.setVisible(true);
					}
				}
			}
			
		} catch (Exception e) {
			logger.info("$$ Exception $$ :" ,e);
			jsplash.splashOff();
			e.printStackTrace();
		}
	}
}
