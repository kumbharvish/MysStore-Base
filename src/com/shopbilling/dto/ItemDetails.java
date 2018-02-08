package com.shopbilling.dto;

public class ItemDetails {

	private int itemNo;
	
	private int billNumber;
	
	private String itemName;
	
	private double MRP;
	
	private double rate;
	
	private double quantity;
	
	private double amount;
	
	private double purchasePrice;
	
	private int supplierId;

	public int getItemNo() {
		return itemNo;
	}

	public void setItemNo(int itemNo) {
		this.itemNo = itemNo;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public double getMRP() {
		return MRP;
	}

	public void setMRP(double mRP) {
		MRP = mRP;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public double getAmount() {
		return quantity*rate;
	}

	public double getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(double purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	@Override
	public String toString() {
		return "ItemDetails [itemNo=" + itemNo + ", itemName=" + itemName
				+ ", MRP=" + MRP + ", rate=" + rate + ", quantity=" + quantity
				+ ", amount=" + getAmount() + ", purchasePrice=" + purchasePrice
				+ "]";
	}

	public int getBillNumber() {
		return billNumber;
	}

	public void setBillNumber(int billNumber) {
		this.billNumber = billNumber;
	}

	public int getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(int supplierId) {
		this.supplierId = supplierId;
	}

	
}
