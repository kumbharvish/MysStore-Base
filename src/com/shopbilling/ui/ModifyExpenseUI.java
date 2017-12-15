package com.shopbilling.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Date;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.shopbilling.dto.Expense;
import com.shopbilling.dto.SalesmanDetails;
import com.shopbilling.dto.StatusDTO;
import com.shopbilling.services.ExpensesServices;
import com.shopbilling.services.SalesmanServices;
import com.shopbilling.ui.AddExpenseUI.ItemChangeListener;
import com.shopbilling.utils.PDFUtils;
import com.toedter.calendar.JDateChooser;

public class ModifyExpenseUI extends JDialog {

	private JPanel contentPane;
	private JTextField tf_amount;
	private JTextField tf_description;
	JComboBox cb_expenseCategory;
	JDateChooser dateChooser;
	Integer id;
	private JComboBox cb_salesman;
	private HashMap<String, Long> salesmanMap = new HashMap<>();
	private HashMap<Long,String> salesmanNameMap = new HashMap<>();
	/**
	 * Create the frame.
	 */
	public ModifyExpenseUI(Integer eid,JFrame frame) {
		super(frame,"Modify Expense",true);
		setTitle("Modify Expense");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setIconImage(Toolkit.getDefaultToolkit().getImage("shop32X32.png"));
		setBounds(350, 100, 521, 481);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		Expense exp = ExpensesServices.getExpenseDetails(eid);
		id = exp.getId();
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setBounds(54, 19, 406, 413);
		contentPane.add(panel);
		
		JLabel label = new JLabel("Expense Category *: ");
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setBounds(10, 32, 121, 25);
		panel.add(label);
		
		JLabel label_1 = new JLabel("Amount *: ");
		label_1.setHorizontalAlignment(SwingConstants.RIGHT);
		label_1.setBounds(10, 104, 121, 25);
		panel.add(label_1);
		
		tf_amount = new JTextField();
		tf_amount.setFont(new Font("Tahoma", Font.BOLD, 12));
		tf_amount.setDisabledTextColor(Color.BLACK);
		tf_amount.setColumns(10);
		tf_amount.setBounds(141, 104, 225, 25);
		tf_amount.setText(PDFUtils.getDecimalFormat(exp.getAmount()));
		panel.add(tf_amount);
		
		JLabel label_2 = new JLabel("Description : ");
		label_2.setHorizontalAlignment(SwingConstants.RIGHT);
		label_2.setBounds(10, 176, 121, 25);
		panel.add(label_2);
		
		tf_description = new JTextField();
		tf_description.setFont(new Font("Tahoma", Font.BOLD, 12));
		tf_description.setDisabledTextColor(Color.BLACK);
		tf_description.setColumns(10);
		tf_description.setBounds(141, 176, 225, 25);
		tf_description.setText(exp.getDescription());
		panel.add(tf_description);
		
		JLabel label_3 = new JLabel("Date *: ");
		label_3.setHorizontalAlignment(SwingConstants.RIGHT);
		label_3.setBounds(10, 248, 121, 25);
		panel.add(label_3);
		
		cb_expenseCategory = new JComboBox();
		cb_expenseCategory.setBounds(141, 32, 225, 25);
		cb_expenseCategory.addItem("-- Select Category --");
		ExpensesServices.populateDropdown(cb_expenseCategory);
		cb_expenseCategory.setSelectedItem(exp.getCategory());
		cb_expenseCategory.addItemListener(new ItemChangeListener());
		panel.add(cb_expenseCategory);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.setBounds(158, 339, 89, 23);
		panel.add(btnUpdate);
		
		dateChooser = new JDateChooser((Date) null);
		dateChooser.setFont(new Font("Tahoma", Font.BOLD, 12));
		dateChooser.setBounds(141, 248, 225, 25);
		dateChooser.setDate(exp.getDate());
		panel.add(dateChooser);
		
		JLabel label_4 = new JLabel("Salesman : ");
		label_4.setHorizontalAlignment(SwingConstants.RIGHT);
		label_4.setBounds(10, 301, 121, 25);
		panel.add(label_4);
		
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(PDFUtils.isMandatorySelected(cb_expenseCategory)&&
						PDFUtils.isMandatoryEntered(tf_amount) && dateChooser.getDate()!=null){
					Expense expense = new Expense();
					expense.setCategory((String)cb_expenseCategory.getSelectedItem());
					expense.setDate(new java.sql.Date(dateChooser.getDate().getTime()));
					expense.setAmount(Double.parseDouble(tf_amount.getText()));
					expense.setDescription(tf_description.getText());
					expense.setId(id);
					if("Salesman Salary".equals(cb_expenseCategory.getSelectedItem())) {
						expense.setSalesmanMobile(salesmanMap.get(cb_salesman.getSelectedItem()));
					}else {
						expense.setSalesmanMobile(0);
					}
					StatusDTO status = ExpensesServices.updateExpense(expense);
					if(status.getStatusCode()==0){
						JOptionPane.showMessageDialog(getContentPane(), "Expense Updated Successfully !");
						dispose();
					}else{
						JOptionPane.showMessageDialog(getContentPane(), "Error occured ", "Error", JOptionPane.WARNING_MESSAGE);
					}
					
				}else{
					JOptionPane.showMessageDialog(getContentPane(), "Please enter mandatory fields !");
				}
			}
		});
		
		populateDropdown();
		cb_salesman = new JComboBox();
		SalesmanServices.populateDropdown(cb_salesman, salesmanMap);
		if("Salesman Salary".equals(exp.getCategory())) {
			cb_salesman.setEnabled(true);
		}else {
			cb_salesman.setEnabled(false);
		}
		
		cb_salesman.setBounds(141, 303, 225, 25);
		cb_salesman.setSelectedItem(salesmanNameMap.get(exp.getSalesmanMobile()));
		panel.add(cb_salesman);
	}
	
	class ItemChangeListener implements ItemListener{
	    @Override
	    public void itemStateChanged(ItemEvent event) {
	       if (event.getStateChange() == ItemEvent.SELECTED) {
	          String category = (String)event.getItem();
	          if("Salesman Salary".equals(category)) {
	        	  cb_salesman.setEnabled(true);
	          }else {
	        	  cb_salesman.setEnabled(false);
	          }
	       }
	    }       
	}
	
	public void populateDropdown(){
		for(SalesmanDetails w :SalesmanServices.getAllWiremans()){
			salesmanNameMap.put(w.getMobileNo(),w.getName());
		}
	}
}
