package com.shopbilling.services;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.shopbilling.dto.Product;
import com.shopbilling.dto.ProductAnalysis;
import com.shopbilling.utils.PDFUtils;

public class ProductSaleAnalysisServices {
	
	private static final String PRODUCT_WISE_PROFIT = "SELECT ITEM_NUMBER ,SUM(ITEM_QTY) AS TOTAL_QTY FROM BILL_ITEM_DETAILS WHERE BILL_NUMBER IN (SELECT BILL_NUMBER FROM CUSTOMER_BILL_DETAILS WHERE DATE(BILL_DATE_TIME) BETWEEN ? AND ?) GROUP BY ITEM_NUMBER ORDER BY SUM(ITEM_QTY) DESC";
	
	private static final String SUPPLIER_WISE_SALES = "SELECT SD.SUPPLIER_NAME,SUM(BID.ITEM_QTY) AS TOTAL_QTY,SUM(BID.ITEM_AMOUNT) AS TOTAL_SALES_AMT FROM BILL_ITEM_DETAILS BID,SUPPLIER_DETAILS SD WHERE BID.BILL_NUMBER IN (SELECT BILL_NUMBER FROM CUSTOMER_BILL_DETAILS WHERE DATE(BILL_DATE_TIME) BETWEEN ? AND ?) AND BID.SUPPLIER_ID = SD.SUPPLIER_ID GROUP BY BID.SUPPLIER_ID ORDER BY SUM(BID.ITEM_AMOUNT) DESC";
		//Get Product total Quantity between date
		public static List<ProductAnalysis> getProductTotalQuantity(Date fromDate,Date toDate) {
			Connection conn = null;
			PreparedStatement stmt = null;
			ProductAnalysis product = null;
			List<ProductAnalysis> productAnalysisList = new ArrayList<ProductAnalysis>();
			try {
				if(fromDate==null){
					fromDate = new Date(1947/01/01);
				}
				if(toDate==null){
					toDate = new Date(System.currentTimeMillis());
				}
				conn = PDFUtils.getConnection();
				stmt = conn.prepareStatement(PRODUCT_WISE_PROFIT);
				stmt.setDate(1, fromDate);
				stmt.setDate(2, toDate);
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					product = new ProductAnalysis();
					product.setProductCode(rs.getInt("ITEM_NUMBER"));
					product.setTotalQty(rs.getDouble("TOTAL_QTY"));
					
					productAnalysisList.add(product);
				}
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				PDFUtils.closeConnectionAndStatment(conn, stmt);
			}
			return productAnalysisList;
		}
		
		//Get Product Wise Profit for the given period
		public static List<ProductAnalysis> getProductWiseProfit(Date fromDate,Date toDate) {
			
			List<ProductAnalysis> productAnalysisList = getProductTotalQuantity(fromDate,toDate);
			HashMap<Integer,Product> productMap = ProductServices.getProductMap();
			for(ProductAnalysis p : productAnalysisList){
				if(productMap.containsKey(p.getProductCode())){
					p.setProductName(productMap.get(p.getProductCode()).getProductName());
					p.setProductMRP(productMap.get(p.getProductCode()).getProductMRP());
					p.setPurcasePrice(productMap.get(p.getProductCode()).getPurcasePrice());
				}
			}
			Collections.sort(productAnalysisList);
			return productAnalysisList;
			
		}
		
		//Product Wise Sales Analysis
		public static List<ProductAnalysis> getProductWiseSales(Date fromDate,Date toDate,Integer supplierId) {
			Connection conn = null;
			PreparedStatement stmt = null;
			ProductAnalysis product = null;
			List<ProductAnalysis> productAnalysisList = new ArrayList<ProductAnalysis>();
			try {
				StringBuffer PRODUCT_WISE_SALES = new StringBuffer("SELECT BID.ITEM_NUMBER , PD.PRODUCT_NAME,PD.QUANTITY,BID.ITEM_MRP,SUM(BID.ITEM_QTY) AS TOTAL_QTY FROM BILL_ITEM_DETAILS BID,PRODUCT_DETAILS PD WHERE BID.BILL_NUMBER IN (SELECT BILL_NUMBER FROM CUSTOMER_BILL_DETAILS WHERE DATE(BILL_DATE_TIME) BETWEEN ? AND ?) AND BID.ITEM_NUMBER = PD.PRODUCT_ID ");
				String supplierQuery = "AND PD.SUPPLIER_ID=? ";
				String groupByQuery= " GROUP BY BID.ITEM_NUMBER ORDER BY SUM(BID.ITEM_QTY) DESC";
				
				if(fromDate==null){
					fromDate = new Date(1947/01/01);
				}
				if(toDate==null){
					toDate = new Date(System.currentTimeMillis());
				}
				if(supplierId!=null) {
					PRODUCT_WISE_SALES.append(supplierQuery);
				}
				
				PRODUCT_WISE_SALES.append(groupByQuery);
				System.out.println("Product Wise Sales Query : "+PRODUCT_WISE_SALES.toString());
				conn = PDFUtils.getConnection();
				stmt = conn.prepareStatement(PRODUCT_WISE_SALES.toString());
				stmt.setDate(1, fromDate);
				stmt.setDate(2, toDate);
				if(supplierId!=null) {
					stmt.setInt(3, supplierId);
				}
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					product = new ProductAnalysis();
					product.setProductCode(rs.getInt("ITEM_NUMBER"));
					product.setProductName(rs.getString("PRODUCT_NAME"));
					product.setProductMRP(rs.getDouble("ITEM_MRP"));
					product.setTotalQty(rs.getDouble("TOTAL_QTY"));
					product.setCurrentQty(rs.getDouble("QUANTITY"));
					
					productAnalysisList.add(product);
				}
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				PDFUtils.closeConnectionAndStatment(conn, stmt);
			}
			return productAnalysisList;
		}
		
		//Supplier Wise Sales Analysis
				public static List<ProductAnalysis> getSupplierWiseSales(Date fromDate,Date toDate) {
					Connection conn = null;
					PreparedStatement stmt = null;
					ProductAnalysis product = null;
					List<ProductAnalysis> productAnalysisList = new ArrayList<ProductAnalysis>();
					try {
						if(fromDate==null){
							fromDate = new Date(1947/01/01);
						}
						if(toDate==null){
							toDate = new Date(System.currentTimeMillis());
						}
						conn = PDFUtils.getConnection();
						stmt = conn.prepareStatement(SUPPLIER_WISE_SALES);
						stmt.setDate(1, fromDate);
						stmt.setDate(2, toDate);
						ResultSet rs = stmt.executeQuery();
						while (rs.next()) {
							product = new ProductAnalysis();
							product.setTotalQty(rs.getInt("TOTAL_QTY"));
							product.setSupplierName(rs.getString("SUPPLIER_NAME"));
							product.setTotalSalesAmount(rs.getDouble("TOTAL_SALES_AMT"));

							productAnalysisList.add(product);
						}
						rs.close();
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						PDFUtils.closeConnectionAndStatment(conn, stmt);
					}
					return productAnalysisList;
				}
		
}
