package com.shopbilling.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import org.fuin.utils4swing.layout.scalable.DefaultScalableLayoutRegistry;
import org.fuin.utils4swing.layout.scalable.ScalableLayoutUtils;

import com.shopbilling.dto.StatusDTO;
import com.shopbilling.dto.UserDetails;
import com.shopbilling.dto.SalesmanDetails;
import com.shopbilling.services.SalesmanServices;
import com.shopbilling.utils.PDFUtils;

import net.java.dev.designgridlayout.DesignGridLayout;
import net.java.dev.designgridlayout.LabelAlignment;

public class ManageSalesmanUI extends JInternalFrame {

	private JPanel contentPane;
	private UserDetails userDetails;
	private JTextField id ;
	private JTextField mobileNumber;
	private JTextField name; 
	private JTextField address ;
	private DefaultTableModel salesmanModel;
	private JTable salesmanTable;

	public ManageSalesmanUI() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(200, 85, 1107, 698);
		setTitle("Manage Salesman");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setResizable(false);
		
		JPanel panel = new JPanel();
		panel.setBounds(148, 28, 804, 548);
		panel.setLayout(null);
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.setBounds(34, 27, 739, 159);
		panel.add(panel_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(33, 210, 741, 299);
		panel.add(scrollPane);
		DesignGridLayout layout = new DesignGridLayout(panel_1);
		layout.labelAlignment(LabelAlignment.RIGHT);
		 id = new JTextField();
		 id.setEditable(false);
		 mobileNumber = new JTextField(20);
		 mobileNumber.setFont(new Font("Tahoma", Font.BOLD, 12));
		 name = new JTextField(20);
		 name.setFont(new Font("Tahoma", Font.BOLD, 12));
		 address = new JTextField(10);
		 address.setFont(new Font("Tahoma", Font.BOLD, 12));
 		JButton deleteButton = new JButton("Delete");
 		JButton updateButton = new JButton("Update");
 		JButton saveButton = new JButton("Add");
 		JButton resetButton = new JButton("Reset");
		layout.row().grid(new JLabel("Mobile Number *:"))	.add(mobileNumber).grid(new JLabel(""));
		layout.row().grid(new JLabel("Name *:"))	.add(name).grid(new JLabel(""));
		layout.row().grid(new JLabel("Address:"))	.add(address).grid(new JLabel(""));
		layout.row().grid(new JLabel(""));
		layout.emptyRow();
 		layout.row().right().add(saveButton).add(updateButton).add(deleteButton).add(resetButton);
 		salesmanModel = new DefaultTableModel(){
			 boolean[] columnEditables = new boolean[] {
						false, false, false, false
					};
					public boolean isCellEditable(int row, int column) {
						return columnEditables[column];
					}
		 };
		 salesmanModel.setColumnIdentifiers(new String[] {
				"id", "Mobile Number", "Name", "Address"
			}
        );
		 salesmanTable = new JTable();
		 salesmanTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = salesmanTable.getSelectedRow();
	        	id.setText(salesmanTable.getModel().getValueAt(row, 0).toString());
				mobileNumber.setText(salesmanTable.getModel().getValueAt(row, 1).toString());
				name.setText(salesmanTable.getModel().getValueAt(row, 2).toString());
				address.setText(salesmanTable.getModel().getValueAt(row, 3).toString());
			}
		});
		 //Table Row Height 
		 PDFUtils.setTableRowHeight(salesmanTable);
		 
		 salesmanTable.setModel(salesmanModel);
		scrollPane.setViewportView(salesmanTable);
		//Hide Id Column
		salesmanTable.getColumnModel().getColumn(0).setMinWidth(0);
		salesmanTable.getColumnModel().getColumn(0).setMaxWidth(0);
		salesmanTable.getColumnModel().getColumn(0).setWidth(0);
		salesmanTable.getColumnModel().getColumn(1).setMinWidth(150);
		salesmanTable.getColumnModel().getColumn(1).setMaxWidth(150);
		salesmanTable.getColumnModel().getColumn(1).setWidth(150);
		contentPane.setLayout(null);
		contentPane.add(panel);
		fillSalesmanTableData(salesmanModel);
		 
		//Reset Button Action
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetSalesmanTextFields();
			}
		});
		
		//Save Button Action
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveWiremanAction();
			}
		});
		//Update Button Action
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateWiremanAction();
			}
		});
		//Delete Button Action
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteSalesmanAction();
			}
		});
			
		mobileNumber.addKeyListener(new KeyAdapter() {
		   public void keyTyped(KeyEvent e) {
		      char c = e.getKeyChar();
		      if ( ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
		         e.consume();  // ignore event
		      }
		   }
		});
		
		ScalableLayoutUtils.installScalableLayoutAndKeys(new DefaultScalableLayoutRegistry(), this, 0.1);
	}
	
	private void deleteSalesmanAction() {
		if(id.getText().equals("")){
			JOptionPane.showMessageDialog(contentPane, "Please select Salesman!");
		}else{
			int dialogButton = JOptionPane.YES_NO_OPTION;
			int dialogResult = JOptionPane.showConfirmDialog (null, "Are you sure?","Warning",dialogButton);
			if(dialogResult == JOptionPane.YES_OPTION){
				if(SalesmanServices.allowDeleteWireman(Long.parseLong(mobileNumber.getText()))) {
					JOptionPane.showMessageDialog(contentPane,"The selected Salesman can not be deleted,since there is other data related to this salesman in the system ","Error",JOptionPane.WARNING_MESSAGE);
				}else {
					SalesmanServices.deleteWireman(Integer.parseInt(id.getText()));
					JOptionPane.showMessageDialog(contentPane, "Salesman deleted Sucessfully!");
					resetSalesmanTextFields();
					fillSalesmanTableData(salesmanModel);
				}
			}else{
				resetSalesmanTextFields();
			}
			
		}
	}
	public void fillSalesmanTableData(DefaultTableModel model){
		List<SalesmanDetails> wiremanList= SalesmanServices.getAllWiremans();
		model.setRowCount(0);
		if(wiremanList.isEmpty()){
			JOptionPane.showMessageDialog(contentPane, "No Salesman found!");
		}else{
			for(SalesmanDetails p : wiremanList){
				 model.addRow(new Object[]{p.getId(), p.getMobileNo(), p.getName(), p.getAddress()});
			}
		}
	}
	
	public void resetSalesmanTextFields(){
		id.setText("");
		mobileNumber.setText("");
		name.setText("");
		address.setText("");
	}
	
	public UserDetails getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}

	private void updateWiremanAction() {
		SalesmanDetails wireman = new SalesmanDetails();
		if(id.getText().equals("")){
			JOptionPane.showMessageDialog(contentPane, "Please select Salesman!");
		}else{
			if(PDFUtils.isMandatoryEntered(mobileNumber)
				&& PDFUtils.isMandatoryEntered(name))
			{
			wireman.setId(Integer.parseInt(id.getText()));
			wireman.setMobileNo(Long.parseLong(mobileNumber.getText()));
			wireman.setName(name.getText());
			wireman.setAddress(address.getText());
			
			StatusDTO status = SalesmanServices.updateWireman(wireman);
			if(status.getStatusCode()==0){
				resetSalesmanTextFields();
				fillSalesmanTableData(salesmanModel);
				JOptionPane.showMessageDialog(contentPane, "Salesman Details Updated!");
			}else{
				if(status.getException().contains("Duplicate entry")){
					JOptionPane.showMessageDialog(getContentPane(), "Entered Salesman Mobile Number already exists!", "Error", JOptionPane.WARNING_MESSAGE);
				}else{
					JOptionPane.showMessageDialog(getContentPane(), "Exception occured ", "Error", JOptionPane.WARNING_MESSAGE);
				}
			}
			}else{
				JOptionPane.showMessageDialog(contentPane, "Please Enter Mandatory fields!");
			}
		}
	}

	private void saveWiremanAction() {
		if(id.getText().equals("")){
			if(PDFUtils.isMandatoryEntered(mobileNumber)
					&& PDFUtils.isMandatoryEntered(name))
				{
				SalesmanDetails wireman = new SalesmanDetails();
				wireman.setMobileNo(Long.parseLong(mobileNumber.getText()));
				wireman.setName(name.getText());
				wireman.setAddress(address.getText());
				StatusDTO status = SalesmanServices.addWireman(wireman);
				if(status.getStatusCode()==0){
					resetSalesmanTextFields();
					fillSalesmanTableData(salesmanModel);
					JOptionPane.showMessageDialog(contentPane, "Salesman Details Added!");
				}else{
					if(status.getException().contains("Duplicate entry")){
						JOptionPane.showMessageDialog(null, "Entered Salesman Mobile Number already exists!", "Error", JOptionPane.WARNING_MESSAGE);
					}else{
						JOptionPane.showMessageDialog(null, "Error occured ", "Error", JOptionPane.WARNING_MESSAGE);
					}
				}
			}else{
				JOptionPane.showMessageDialog(contentPane, "Please Enter Mandatory fields!");
			}
		}else{
			JOptionPane.showMessageDialog(contentPane, "Please reset fields!");
		}
	}
}
