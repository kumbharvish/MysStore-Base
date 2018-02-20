package com.shopbilling.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import org.fuin.utils4swing.layout.scalable.DefaultScalableLayoutRegistry;
import org.fuin.utils4swing.layout.scalable.ScalableLayoutUtils;

import com.shopbilling.dto.MonthlyReport;
import com.shopbilling.dto.MyStoreDetails;
import com.shopbilling.services.MyStoreServices;
import com.shopbilling.services.ReportServices;
import com.shopbilling.utils.PDFUtils;
import com.toedter.calendar.JMonthChooser;
import com.toedter.calendar.JYearChooser;

public class MonthlyReportUI extends JInternalFrame {
	
	private JLabel lblTotalSalesPendingValue;
	private JLabel lblTotalSalesCashValue;
	private JLabel lblTotalSalesReturnAmtValue;
	private JLabel lblTotalExpensesAmtValue;
	private JLabel lblTotalCustomerSettleAmtValue;
	private JLabel lblTotalQuantitySoldValue;
	private JLabel lblTotalStockPurchaseValue;
	private JYearChooser yearChooser;
	private JMonthChooser monthChooser;
	private JLabel lblTotalSalesValue;
	private JLabel lblTotalProfitValue;

	public MonthlyReportUI() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(200, 85, 1107, 698);
		setTitle("Monthly Report");
		getContentPane().setLayout(null);
		MyStoreDetails myStore = MyStoreServices.getMyStoreDetails();
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setBounds(239, 22, 697, 568);
		getContentPane().add(panel);
		panel.setLayout(null);
		Border border = new LineBorder(Color.black, 1);
		
		JLabel lblStoreName = new JLabel(myStore.getStoreName());
		lblStoreName.setHorizontalAlignment(SwingConstants.CENTER);
		lblStoreName.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblStoreName.setBounds(126, 11, 444, 24);
		panel.add(lblStoreName);
		
		JLabel lblStoreAddress = new JLabel(myStore.getAddress()+", "+myStore.getAddress2()+", "+myStore.getCity());
		lblStoreAddress.setHorizontalAlignment(SwingConstants.CENTER);
		lblStoreAddress.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblStoreAddress.setBounds(126, 36, 444, 24);
		panel.add(lblStoreAddress);
		
		JLabel lblTotalSalesPending = new JLabel("Total Sales Pending Amount");
		lblTotalSalesPending.setHorizontalAlignment(SwingConstants.CENTER);
		lblTotalSalesPending.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblTotalSalesPending.setBounds(98, 134, 292, 35);
		lblTotalSalesPending.setBorder(border);
		panel.add(lblTotalSalesPending);
		
		lblTotalSalesPendingValue = new JLabel("0.00");
		lblTotalSalesPendingValue.setHorizontalAlignment(SwingConstants.CENTER);
		lblTotalSalesPendingValue.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTotalSalesPendingValue.setBounds(389, 134, 206, 35);
		lblTotalSalesPendingValue.setBorder(border);
		panel.add(lblTotalSalesPendingValue);
		
		JLabel lblTotalSalesCash = new JLabel("Total Sales Cash Amount");
		lblTotalSalesCash.setHorizontalAlignment(SwingConstants.CENTER);
		lblTotalSalesCash.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblTotalSalesCash.setBounds(98, 180, 292, 35);
		lblTotalSalesCash.setBorder(border);
		panel.add(lblTotalSalesCash);
		
		lblTotalSalesCashValue = new JLabel("0.00");
		lblTotalSalesCashValue.setHorizontalAlignment(SwingConstants.CENTER);
		lblTotalSalesCashValue.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTotalSalesCashValue.setBounds(389, 180, 206, 35);
		lblTotalSalesCashValue.setBorder(border);
		panel.add(lblTotalSalesCashValue);
		
		JLabel lblTotalSalesReturnAmt = new JLabel("Total Sales Return Amount");
		lblTotalSalesReturnAmt.setHorizontalAlignment(SwingConstants.CENTER);
		lblTotalSalesReturnAmt.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblTotalSalesReturnAmt.setBounds(98, 317, 292, 35);
		lblTotalSalesReturnAmt.setBorder(border);
		panel.add(lblTotalSalesReturnAmt);
		
		lblTotalSalesReturnAmtValue = new JLabel("0.00");
		lblTotalSalesReturnAmtValue.setHorizontalAlignment(SwingConstants.CENTER);
		lblTotalSalesReturnAmtValue.setBounds(389, 317, 206, 35);
		lblTotalSalesReturnAmtValue.setBorder(border);
		lblTotalSalesReturnAmtValue.setFont(new Font("Tahoma", Font.PLAIN, 15));
		panel.add(lblTotalSalesReturnAmtValue);
		
		JLabel lblTotalExpensesAmt = new JLabel("Total Expenses Amount");
		lblTotalExpensesAmt.setHorizontalAlignment(SwingConstants.CENTER);
		lblTotalExpensesAmt.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblTotalExpensesAmt.setBounds(98, 363, 292, 35);
		lblTotalExpensesAmt.setBorder(border);
		panel.add(lblTotalExpensesAmt);
		
		lblTotalExpensesAmtValue = new JLabel("0.00");
		lblTotalExpensesAmtValue.setHorizontalAlignment(SwingConstants.CENTER);
		lblTotalExpensesAmtValue.setBounds(389, 363, 206, 35);
		lblTotalExpensesAmtValue.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblTotalExpensesAmtValue.setBorder(border);
		panel.add(lblTotalExpensesAmtValue);
		
		JLabel lblTotalCustomerSettleAmt = new JLabel("Total Customer Settlement Amount");
		lblTotalCustomerSettleAmt.setHorizontalAlignment(SwingConstants.CENTER);
		lblTotalCustomerSettleAmt.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblTotalCustomerSettleAmt.setBounds(98, 409, 292, 35);
		lblTotalCustomerSettleAmt.setBorder(border);
		panel.add(lblTotalCustomerSettleAmt);
		
		lblTotalCustomerSettleAmtValue = new JLabel("0.00");
		lblTotalCustomerSettleAmtValue.setHorizontalAlignment(SwingConstants.CENTER);
		lblTotalCustomerSettleAmtValue.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblTotalCustomerSettleAmtValue.setBounds(389, 409, 206, 35);
		lblTotalCustomerSettleAmtValue.setBorder(border);
		panel.add(lblTotalCustomerSettleAmtValue);
		
		
		JLabel lblTotalStockPurchase = new JLabel("Total Stock Purchase Amount");
		lblTotalStockPurchase.setHorizontalAlignment(SwingConstants.CENTER);
		lblTotalStockPurchase.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblTotalStockPurchase.setBounds(98, 455, 292, 35);
		lblTotalStockPurchase.setBorder(border);
		panel.add(lblTotalStockPurchase);
		
		JLabel lblTotalQuantitySold = new JLabel("Total Quantity Sold");
		lblTotalQuantitySold.setHorizontalAlignment(SwingConstants.CENTER);
		lblTotalQuantitySold.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblTotalQuantitySold.setBounds(98, 501, 292, 35);
		lblTotalQuantitySold.setBorder(border);
		panel.add(lblTotalQuantitySold);
		
		lblTotalQuantitySoldValue = new JLabel("0");
		lblTotalQuantitySoldValue.setHorizontalAlignment(SwingConstants.CENTER);
		lblTotalQuantitySoldValue.setBounds(389, 501, 206, 35);
		lblTotalQuantitySoldValue.setBorder(border);
		lblTotalQuantitySoldValue.setFont(new Font("Tahoma", Font.PLAIN, 15));
		panel.add(lblTotalQuantitySoldValue);
		
		lblTotalStockPurchaseValue = new JLabel("0.00");
		lblTotalStockPurchaseValue.setHorizontalAlignment(SwingConstants.CENTER);
		lblTotalStockPurchaseValue.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblTotalStockPurchaseValue.setBounds(389, 455, 206, 35);
		lblTotalStockPurchaseValue.setBorder(border);
		panel.add(lblTotalStockPurchaseValue);
		
		JLabel lblMonth = new JLabel("Month :");
		lblMonth.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMonth.setBounds(98, 98, 58, 25);
		panel.add(lblMonth);
		
		monthChooser = new JMonthChooser();
		monthChooser.setBounds(170, 98, 91, 25);
		panel.add(monthChooser);
		
		JLabel lblYear = new JLabel("Year :");
		lblYear.setHorizontalAlignment(SwingConstants.RIGHT);
		lblYear.setBounds(271, 98, 58, 25);
		panel.add(lblYear);
		
		yearChooser = new JYearChooser();
		yearChooser.setBounds(340, 98, 91, 25);
		yearChooser.setStartYear(2016);
		yearChooser.setFont(new Font("Tahoma", Font.BOLD, 13));
		panel.add(yearChooser);
		
		JButton btnNewButton = new JButton("Get Report");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getReport();
			}
		});
		btnNewButton.setBounds(472, 98, 105, 25);
		panel.add(btnNewButton);
		
		JLabel lblTotalProfit = new JLabel("Total Profit Amount");
		lblTotalProfit.setHorizontalAlignment(SwingConstants.CENTER);
		lblTotalProfit.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblTotalProfit.setBounds(98, 270, 292, 36);
		lblTotalProfit.setBorder(border);
		panel.add(lblTotalProfit);
		
		lblTotalProfitValue = new JLabel();
		lblTotalProfitValue.setHorizontalAlignment(SwingConstants.CENTER);
		lblTotalProfitValue.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTotalProfitValue.setBounds(389, 270, 206, 36);
		lblTotalProfitValue.setBorder(border);
		panel.add(lblTotalProfitValue);
		
		JLabel lblTotalSales = new JLabel("Total Sales Amount");
		lblTotalSales.setHorizontalAlignment(SwingConstants.CENTER);
		lblTotalSales.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblTotalSales.setBounds(98, 226, 292, 35);
		lblTotalSales.setBorder(border);
		panel.add(lblTotalSales);
		
		lblTotalSalesValue = new JLabel();
		lblTotalSalesValue.setHorizontalAlignment(SwingConstants.CENTER);
		lblTotalSalesValue.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTotalSalesValue.setBounds(389, 226, 206, 35);
		lblTotalSalesValue.setBorder(border);
		panel.add(lblTotalSalesValue);
		getReport();
		
		ScalableLayoutUtils.installScalableLayoutAndKeys(new DefaultScalableLayoutRegistry(), this, 0.1);
	}

	protected void getReport() {
		int month = monthChooser.getMonth();
		int year = yearChooser.getYear();
		 Calendar gc = new GregorianCalendar();
	        gc.set(Calendar.MONTH, month);
	        gc.set(Calendar.DAY_OF_MONTH, 1);
	        gc.set(Calendar.YEAR, year);
	        
	        Date monthStart = gc.getTime();
	        gc.add(Calendar.MONTH, 1);
	        gc.add(Calendar.DAY_OF_MONTH, -1);
	        
	        Date monthEnd = gc.getTime();

	        System.out.println("Calculated month start date : " + PDFUtils.getFormattedDate(monthStart));
	        System.out.println("Calculated month end date : " + PDFUtils.getFormattedDate(monthEnd));
	        
	        MonthlyReport report = ReportServices.getMonthlyReport(new java.sql.Date(monthStart.getTime()), new java.sql.Date(monthEnd.getTime()));
	        
	        lblTotalSalesPendingValue.setText(PDFUtils.getAmountFormat(report.getTotalSalesPendingAmt()));
	        lblTotalSalesCashValue.setText(PDFUtils.getAmountFormat(report.getTotalSalesCashAmt()));
	        lblTotalSalesReturnAmtValue.setText(PDFUtils.getAmountFormat(report.getTotalSalesReturnAmt()));
	        lblTotalExpensesAmtValue.setText(PDFUtils.getAmountFormat(report.getTotalExpensesAmt()));
	        lblTotalCustomerSettleAmtValue.setText(PDFUtils.getAmountFormat(report.getTotalCustSettlementAmt()));
	        lblTotalStockPurchaseValue.setText(PDFUtils.getAmountFormat(report.getTotalPurchaseAmt()));
	        lblTotalQuantitySoldValue.setText(String.valueOf(report.getTotalQtySold()));
	        lblTotalProfitValue.setText(PDFUtils.getDecimalFormat(report.getTotalProfitAmount()));
	        lblTotalSalesValue.setText(PDFUtils.getDecimalFormat(report.getTotalSalesAmount()));
	}
}
