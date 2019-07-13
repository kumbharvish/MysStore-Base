package com.shopbilling.services;

import java.util.TimerTask;

/*
 * Task to Periodically take DB backup based on configured time period
 */
public class DBScheduledDumpTask extends TimerTask {
	
	public void run() {
		System.out.println("-- Running scheduled DB Dump Task --");
		DBBackupService.createDBDump();
	}
}