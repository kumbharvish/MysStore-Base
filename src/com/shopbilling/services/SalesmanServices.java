package com.shopbilling.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JComboBox;

import com.shopbilling.dto.StatusDTO;
import com.shopbilling.dto.SalesmanDetails;
import com.shopbilling.utils.PDFUtils;

public class SalesmanServices {

	private static final String GET_ALL_SALESMAN= "SELECT * FROM SALESMAN_DETAILS";
	
	private static final String IS_WIREMAN_DELETION_ALLOWED = "SELECT COUNT(*) AS COUNT FROM CUSTOMER_BILL_DETAILS WHERE SALESMAN_MOB_NO=?";
	
	private static final String INS_SALESMAN= "INSERT INTO SALESMAN_DETAILS " 
												+ "(MOBILE_NUMBER,NAME,ADDRESS)" 
												+ " VALUES(?,?,?)";
	
	private static final String DELETE_SALESMAN= "DELETE FROM SALESMAN_DETAILS WHERE ID=?";
	
	private static final String UPDATE_SALESMAN= "UPDATE SALESMAN_DETAILS SET MOBILE_NUMBER=?," 
												+"NAME=?, ADDRESS=?" 
												+" WHERE ID=?";

	public static List<SalesmanDetails> getAllWiremans() {
		Connection conn = null;
		PreparedStatement stmt = null;
		SalesmanDetails pc = null;
		List<SalesmanDetails> salesmanDetailsList = new ArrayList<SalesmanDetails>();
		try {
			conn = PDFUtils.getConnection();
			stmt = conn.prepareStatement(GET_ALL_SALESMAN);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				pc = new SalesmanDetails();
				pc.setMobileNo(rs.getLong("MOBILE_NUMBER"));
				pc.setId(rs.getInt("ID"));
				pc.setName(rs.getString("NAME"));
				pc.setAddress(rs.getString("ADDRESS"));
				
				salesmanDetailsList.add(pc);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			PDFUtils.closeConnectionAndStatment(conn, stmt);
		}
		return salesmanDetailsList;
	}
	
	public static StatusDTO addWireman(SalesmanDetails salesmanDetails) {
		Connection conn = null;
		PreparedStatement stmt = null;
		StatusDTO status = new StatusDTO();
		try {
			if(salesmanDetails!=null){
				conn = PDFUtils.getConnection();
				stmt = conn.prepareStatement(INS_SALESMAN);
				stmt.setLong(1,salesmanDetails.getMobileNo());
				stmt.setString(2,salesmanDetails.getName());
				stmt.setString(3,salesmanDetails.getAddress());
				
				int i = stmt.executeUpdate();
				if(i>0){
					status.setStatusCode(0);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			status.setException(e.getMessage());
			status.setStatusCode(-1);
		} finally {
			PDFUtils.closeConnectionAndStatment(conn, stmt);
		}
		return status;
	}
	
	public static boolean deleteWireman(int id) {
		Connection conn = null;
		PreparedStatement stmt = null;
		boolean flag=false;
		try {
				conn = PDFUtils.getConnection();
				stmt = conn.prepareStatement(DELETE_SALESMAN);
				stmt.setInt(1,id);
				
				int i = stmt.executeUpdate();
				if(i>0){
					flag=true;
				}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			PDFUtils.closeConnectionAndStatment(conn, stmt);
		}
		return flag;
	}
	
	public static StatusDTO updateWireman(SalesmanDetails salesmanDetails) {
		Connection conn = null;
		PreparedStatement stmt = null;
		StatusDTO status = new StatusDTO();
		try {
			if(salesmanDetails!=null){
				conn = PDFUtils.getConnection();
				stmt = conn.prepareStatement(UPDATE_SALESMAN);
				stmt.setLong(1,salesmanDetails.getMobileNo());
				stmt.setString(2,salesmanDetails.getName());
				stmt.setString(3, salesmanDetails.getAddress());
				stmt.setInt(4, salesmanDetails.getId());
				
				int i = stmt.executeUpdate();
				if(i>0){
					status.setStatusCode(0);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			status.setException(e.getMessage());
			status.setStatusCode(-1);
		} finally {
			PDFUtils.closeConnectionAndStatment(conn, stmt);
		}
		return status;
	}
	
	
	
public static void populateDropdown(JComboBox<String> combobox,HashMap<String,Long> wiremanMap){
		for(SalesmanDetails w :getAllWiremans()){
			combobox.addItem(w.getName());
			wiremanMap.put(w.getName(), w.getMobileNo());
		}
	}

public static boolean allowDeleteWireman(Long wiremanMobile) {
	Connection conn = null;
	PreparedStatement stmt = null;
	int count=0;
	try {
		conn = PDFUtils.getConnection();
		stmt = conn.prepareStatement(IS_WIREMAN_DELETION_ALLOWED);
		stmt.setLong(1, wiremanMobile);
		ResultSet rs = stmt.executeQuery();

		if (rs.next()) {
			count = rs.getInt("COUNT");
		}
		rs.close();
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		PDFUtils.closeConnectionAndStatment(conn, stmt);
	}
	if(count>0) {
		return true;
	}
	return false;
}
	
}
