package com.shopbilling.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import org.fuin.utils4swing.layout.scalable.DefaultScalableLayoutRegistry;
import org.fuin.utils4swing.layout.scalable.ScalableLayoutUtils;

import com.shopbilling.dto.ProductAnalysis;
import com.shopbilling.services.ProductSaleAnalysisServices;
import com.shopbilling.utils.PDFUtils;
import com.toedter.calendar.JDateChooser;

public class SupplierWiseSalesAnalysis extends JInternalFrame {
	private JTable table;
	private DefaultTableModel reportModel;
	private JDateChooser toDateChooser;
	private JDateChooser fromDateChooser;
	private  JLabel lblTotalAmount;
	private JLabel lblSupplierName;
	/**
	 * Create the frame.
	 */
	public SupplierWiseSalesAnalysis() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(200, 85, 1107, 698);
		setTitle("Supplier Wise Sales Analysis");
		getContentPane().setLayout(null);
		Border border = new LineBorder(Color.black, 2);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Date Range", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 11, 1150, 60);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblFromDate = new JLabel("From Date :");
		lblFromDate.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblFromDate.setBounds(212, 16, 99, 28);
		panel.add(lblFromDate);
		
		JLabel lblToDate = new JLabel("To Date :");
		lblToDate.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblToDate.setBounds(545, 16, 79, 28);
		panel.add(lblToDate);
		
		JButton btnGetReport = new JButton("Get Report");
		btnGetReport.setIcon(new ImageIcon(SellReport.class.getResource("/images/clipboard_report_bar_24_ns.png")));
		btnGetReport.setBackground(UIManager.getColor("Button.background"));
		btnGetReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fillReportTable();
			}
		});
		btnGetReport.setBounds(852, 13, 127, 34);
		panel.add(btnGetReport);
		
		fromDateChooser = new JDateChooser();
		fromDateChooser.setBounds(321, 16, 133, 28);
		fromDateChooser.setDate(new Date());
		fromDateChooser.setFont(new Font("Tahoma", Font.BOLD, 12));
		panel.add(fromDateChooser);
		
		toDateChooser = new JDateChooser();
		toDateChooser.setBounds(634, 16, 133, 28);
		toDateChooser.setDate(new Date());
		toDateChooser.setFont(new Font("Tahoma", Font.BOLD, 12));
		panel.add(toDateChooser);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Supplier Details", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(10, 82, 1150, 409);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(15, 26, 1120, 372);
		panel_1.add(scrollPane);
		
		table = new JTable();
		reportModel = new DefaultTableModel(){
			 boolean[] columnEditables = new boolean[] {
					 false, false, false
					};
					public boolean isCellEditable(int row, int column) {
						return columnEditables[column];
					}
		 };
		 reportModel.setColumnIdentifiers(new String[] {
				 "Supplier Name","Total Quantity Sold","Total Sales Amount"}
	       );
		table.setModel(reportModel);
		table.getColumnModel().getColumn(1).setPreferredWidth(290);
		scrollPane.setViewportView(table);
		 PDFUtils.setTableRowHeight(table);
		 
		 JPanel panel_2 = new JPanel();
		 panel_2.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Top Supplier ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		 panel_2.setBounds(10, 502, 1150, 78);
		 getContentPane().add(panel_2);
		 panel_2.setLayout(null);
		 
		 lblTotalAmount = new JLabel("");
		 lblTotalAmount.setHorizontalAlignment(SwingConstants.CENTER);
		 lblTotalAmount.setFont(new Font("Tahoma", Font.BOLD, 16));
		 lblTotalAmount.setBounds(623, 40, 387, 31);
		 lblTotalAmount.setBorder(border);
		 panel_2.add(lblTotalAmount);
		 
		 JLabel lblLessSold = new JLabel("Total Sales Amount");
		 lblLessSold.setHorizontalAlignment(SwingConstants.CENTER);
		 lblLessSold.setBounds(623, 11, 387, 31);
		 lblLessSold.setBorder(border);
		 lblLessSold.setFont(new Font("Tahoma", Font.BOLD, 15));
		 panel_2.add(lblLessSold);
		 
		 lblSupplierName = new JLabel("");
		 lblSupplierName.setHorizontalAlignment(SwingConstants.CENTER);
		 lblSupplierName.setFont(new Font("Tahoma", Font.BOLD, 16));
		 lblSupplierName.setBounds(238, 40, 387, 31);
		 lblSupplierName.setBorder(border);
		 panel_2.add(lblSupplierName);
		 
		 JLabel lblMostSold = new JLabel("Supplier Name");
		 lblMostSold.setHorizontalAlignment(SwingConstants.CENTER);
		 lblMostSold.setFont(new Font("Tahoma", Font.BOLD, 15));
		 lblMostSold.setBounds(238, 11, 387, 31);
		 lblMostSold.setBorder(border);
		 panel_2.add(lblMostSold);
		 
		 ScalableLayoutUtils.installScalableLayoutAndKeys(new DefaultScalableLayoutRegistry(), this, 0.1);
	}
	
	//Fill Report Table
	private void fillReportTable(){
		List<ProductAnalysis> list= ProductSaleAnalysisServices.getSupplierWiseSales(fromDateChooser.getDate()==null?null:new java.sql.Date(fromDateChooser.getDate().getTime()),toDateChooser.getDate()==null?null:new java.sql.Date(toDateChooser.getDate().getTime()));
		reportModel.setRowCount(0);
		if(list.isEmpty()){
			JOptionPane.showMessageDialog(getContentPane(), "No Records found for the given period !");
			lblTotalAmount.setText("");
			lblSupplierName.setText("");
			
		}else{
			for(ProductAnalysis b : list){
				
				reportModel.addRow(new Object[]{b.getSupplierName(),b.getTotalQty(),PDFUtils.getDecimalFormat(b.getTotalSalesAmount())});
			}
			setReportValues();
		}
	}

	private void setReportValues() {
		String topSupplierName = (String)reportModel.getValueAt(0, 0);
		String totalSalesAmt = (String)reportModel.getValueAt(0, 2);
		lblTotalAmount.setText(" "+PDFUtils.getDecimalFormat(Double.valueOf(totalSalesAmt)));
		lblSupplierName.setText(" "+topSupplierName);
	}
}
