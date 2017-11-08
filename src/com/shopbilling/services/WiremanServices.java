package com.shopbilling.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JComboBox;

import com.shopbilling.dto.StatusDTO;
import com.shopbilling.dto.WiremanDetails;
import com.shopbilling.utils.PDFUtils;

public class WiremanServices {

	private static final String GET_ALL_WIREMAN = "SELECT * FROM WIREMAN_DETAILS";
	
	private static final String INS_WIREMAN = "INSERT INTO WIREMAN_DETAILS " 
												+ "(MOBILE_NUMBER,NAME,ADDRESS)" 
												+ " VALUES(?,?,?)";
	
	private static final String DELETE_WIREMAN = "DELETE FROM WIREMAN_DETAILS WHERE ID=?";
	
	private static final String UPDATE_WIREMAN= "UPDATE WIREMAN_DETAILS SET MOBILE_NUMBER=?," 
												+"NAME=?, ADDRESS=?" 
												+" WHERE ID=?";

	public static List<WiremanDetails> getAllWiremans() {
		Connection conn = null;
		PreparedStatement stmt = null;
		WiremanDetails pc = null;
		List<WiremanDetails> WiremanDetailsList = new ArrayList<WiremanDetails>();
		try {
			conn = PDFUtils.getConnection();
			stmt = conn.prepareStatement(GET_ALL_WIREMAN);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				pc = new WiremanDetails();
				pc.setMobileNo(rs.getLong("MOBILE_NUMBER"));
				pc.setId(rs.getInt("ID"));
				pc.setName(rs.getString("NAME"));
				pc.setAddress(rs.getString("ADDRESS"));
				
				WiremanDetailsList.add(pc);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			PDFUtils.closeConnectionAndStatment(conn, stmt);
		}
		return WiremanDetailsList;
	}
	
	public static StatusDTO addWireman(WiremanDetails wiremanDetails) {
		Connection conn = null;
		PreparedStatement stmt = null;
		StatusDTO status = new StatusDTO();
		try {
			if(wiremanDetails!=null){
				conn = PDFUtils.getConnection();
				stmt = conn.prepareStatement(INS_WIREMAN);
				stmt.setLong(1,wiremanDetails.getMobileNo());
				stmt.setString(2,wiremanDetails.getName());
				stmt.setString(3,wiremanDetails.getAddress());
				
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
				stmt = conn.prepareStatement(DELETE_WIREMAN);
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
	
	public static StatusDTO updateWireman(WiremanDetails wiremanDetails) {
		Connection conn = null;
		PreparedStatement stmt = null;
		StatusDTO status = new StatusDTO();
		try {
			if(wiremanDetails!=null){
				conn = PDFUtils.getConnection();
				stmt = conn.prepareStatement(UPDATE_WIREMAN);
				stmt.setLong(1,wiremanDetails.getMobileNo());
				stmt.setString(2,wiremanDetails.getName());
				stmt.setString(3, wiremanDetails.getAddress());
				stmt.setInt(4, wiremanDetails.getId());
				
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
		combobox.addItem("-- Select --");
		for(WiremanDetails w :getAllWiremans()){
			combobox.addItem(w.getName());
			wiremanMap.put(w.getName(), w.getMobileNo());
		}
	}
	
}
