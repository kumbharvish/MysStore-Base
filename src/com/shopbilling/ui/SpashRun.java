package com.shopbilling.ui;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import com.shopbilling.constants.AppConstants;
import com.shopbilling.properties.AppProperties;
import com.shopbilling.services.AppLicenseServices;

public class SpashRun {
	
	private final static Logger logger = Logger.getLogger(SpashRun.class);

	public static void main(String[] args) throws Exception {
		new SplashUI();
		try {
			if(!AppProperties.check()){
				JOptionPane.showMessageDialog(null, AppConstants.LICENSE_ERROR_1, AppConstants.LICENSE_ERROR, JOptionPane.WARNING_MESSAGE);
			}else{
				if(AppLicenseServices.change()){
					JOptionPane.showMessageDialog(null, AppConstants.COMP_DATE_ERROR, AppConstants.COMP_DATE, JOptionPane.WARNING_MESSAGE);
				}else{
					if(!AppProperties.doCheck()){
						JOptionPane.showMessageDialog(null, AppConstants.LICENSE_ERROR_2, AppConstants.LICENSE_EXPIRED, JOptionPane.WARNING_MESSAGE);
						System.exit(0);
					}else{
						ManageStoreUI frame = new ManageStoreUI();
						frame.setVisible(true);
					}
				}
			}
			
		} catch (Exception e) {
			logger.info("$$ Exception $$ :" ,e);
			e.printStackTrace();
		}
	}
}
