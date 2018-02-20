package com.shopbilling.dto;


public class ProductAnalysis implements Comparable<ProductAnalysis>{

	private int productCode;
	
	private String productName;
	
	private int totalQty;
	
	private double purcasePrice;
	
	private double profit;
	
	private double productMRP;
	
	private double totalMRPAmount;
	
	private String supplierName;
	
	private double totalSalesAmount;
	
	private int currentQty;
	
	public int getProductCode() {
		return productCode;
	}

	public void setProductCode(int productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getTotalQty() {
		return totalQty;
	}

	public void setTotalQty(int totalQty) {
		this.totalQty = totalQty;
	}

	public double getPurcasePrice() {
		return purcasePrice;
	}

	public void setPurcasePrice(double purcasePrice) {
		this.purcasePrice = purcasePrice;
	}

	public double getProfit() {
		return productMRP*totalQty - purcasePrice*totalQty;
	}

	public void setProfit(double profit) {
		this.profit = profit;
	}

	public double getProductMRP() {
		return productMRP;
	}

	public void setProductMRP(double productMRP) {
		this.productMRP = productMRP;
	}

	@Override
	public int compareTo(ProductAnalysis o) {

		if (this.getProfit() < o.getProfit()) {
	        return 1;
	    }
	    else if(this.getProfit() > o.getProfit()){
	        return -1;
	    }

		return 0;
	}

	public double getTotalMRPAmount() {
		return productMRP*totalQty;
	}

	public void setTotalMRPAmount(double totalMRPAmount) {
		this.totalMRPAmount = totalMRPAmount;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public double getTotalSalesAmount() {
		return totalSalesAmount;
	}

	public void setTotalSalesAmount(double totalSalesAmount) {
		this.totalSalesAmount = totalSalesAmount;
	}

	public int getCurrentQty() {
		return currentQty;
	}

	public void setCurrentQty(int currentQty) {
		this.currentQty = currentQty;
	}
	
}
