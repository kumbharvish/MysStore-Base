package com.shopbilling.dto;

import java.sql.Date;

public class Expense {
	
	private int id;
	
	private String category;
	
	private double amount;
	
	private String description;
	
	private Date date;
	
	private long salesmanMobile;
	
	private String salesmanName;

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Long getSalesmanMobile() {
		return salesmanMobile;
	}

	public void setSalesmanMobile(long salesmanMobile) {
		this.salesmanMobile = salesmanMobile;
	}

	public String getSalesmanName() {
		return salesmanName;
	}

	public void setSalesmanName(String salesmanName) {
		this.salesmanName = salesmanName;
	}
	
	

}
