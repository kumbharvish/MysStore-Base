package com.shopbilling.ui;

import java.awt.Color;
import java.awt.Toolkit;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import com.shopbilling.dto.BillDetails;
import com.shopbilling.dto.Product;
import com.shopbilling.utils.PDFUtils;

public class ShowStockNotification extends JDialog {

	private JPanel contentPane;
	private DefaultTableModel productModel;
	private JTable productTable;
	/**
	 * Create the frame.
	 */
	public ShowStockNotification(JFrame frame,List<Product> productList) {
		super(frame,"View Bill",true);
		setTitle("Stock Limit Notification");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setIconImage(Toolkit.getDefaultToolkit().getImage("shop32X32.png"));
		setBounds(350, 100, 662, 407);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Product Details", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(10, 18, 636, 342);
		contentPane.add(panel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 17, 616, 314);
		panel.add(scrollPane);
		productTable = new JTable();
		productModel = new DefaultTableModel(){
			 boolean[] columnEditables = new boolean[] {
					 false, false, false
					};
					public boolean isCellEditable(int row, int column) {
						return columnEditables[column];
					}
		 };
		 productModel.setColumnIdentifiers(new String[] {
    		    "Product Name", "Company", "Quantity"}
       );
		 
		 //Table Row Height 
		 PDFUtils.setTableRowHeight(productTable);
		 
		productTable.setModel(productModel);
		productTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(productTable);
		
		 for(Product product : productList){
			 productModel.addRow(new Object[]{product.getProductName(),product.getProductCategory(),product.getQuanity()});
		 }
	}
	
}
