package com.shopbilling.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import org.apache.log4j.Logger;
import org.fuin.utils4swing.layout.scalable.DefaultScalableLayoutRegistry;
import org.fuin.utils4swing.layout.scalable.ScalableLayoutUtils;

import com.shopbilling.constants.AppConstants;
import com.shopbilling.dto.Barcode;
import com.shopbilling.dto.Product;
import com.shopbilling.services.AutoSuggestTable;
import com.shopbilling.services.JasperServices;
import com.shopbilling.services.ProductServices;
import com.shopbilling.utils.JasperUtils;
import com.shopbilling.utils.PDFUtils;
import java.awt.FlowLayout;

public class PrintBarcodeSheetUI extends JInternalFrame {
	
	private HashMap<String,Product> productMap; 
	private JTextField tf_ProductName;
	private JTextField tf_StockQty;
	private JTextField tf_CategoryName;
	private JTextField tf_Barcode;
	private AutoSuggestTable<String> autoSuggestTable;
	private final static Logger logger = Logger.getLogger(PrintBarcodeSheetUI.class);
	private JTextField tf_Mrp;
	private JRadioButton rdbtnStickers_65;
	private JRadioButton rdbtnStickers_24;
	private JRadioButton rdbtnStickers_40;
	private JTextField tf_startPosition;
	private JTextField tf_noOfLabels;
	private JLabel lbl65Image;
	private JLabel lbl40Image;
	private JLabel lbl24Image;
	/**
	 * Create the frame.
	 */
	public PrintBarcodeSheetUI() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(200, 85, 1107, 698);
		setTitle("Print Barcode Label");
		getContentPane().setLayout(null);
		Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Products", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(150, 26, 519, 556);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		autoSuggestTable = new AutoSuggestTable<String>(getProductName());
		autoSuggestTable.setForeground(Color.BLUE);
		autoSuggestTable.setFont(new Font("Dialog", Font.BOLD, 13));
		autoSuggestTable.setColumns(10);
		autoSuggestTable.setBounds(175, 34, 288, 27);
		autoSuggestTable.setBorder(border);
		panel_1.add(autoSuggestTable);
		
		JLabel lblSelectProduct = new JLabel("Select Product :");
		lblSelectProduct.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSelectProduct.setBounds(45, 34, 120, 27);
		panel_1.add(lblSelectProduct);
		
		JLabel label = new JLabel("Barcode  ");
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setForeground(Color.GRAY);
		label.setBounds(77, 173, 120, 25);
		label.setBorder(border);
		panel_1.add(label);
		
		JLabel label_1 = new JLabel("Category Name  ");
		label_1.setHorizontalAlignment(SwingConstants.RIGHT);
		label_1.setForeground(Color.GRAY);
		label_1.setBounds(77, 149, 120, 25);
		label_1.setBorder(border);
		panel_1.add(label_1);
		
		JLabel label_2 = new JLabel("Stock Quantity  ");
		label_2.setHorizontalAlignment(SwingConstants.RIGHT);
		label_2.setForeground(Color.GRAY);
		label_2.setBounds(77, 125, 120, 25);
		label_2.setBorder(border);
		panel_1.add(label_2);
		
		JLabel label_3 = new JLabel("Product Name  ");
		label_3.setHorizontalAlignment(SwingConstants.RIGHT);
		label_3.setForeground(Color.GRAY);
		label_3.setBounds(77, 101, 120, 25);
		label_3.setBorder(border);
		panel_1.add(label_3);
		
		JLabel mrplabel = new JLabel("MRP  ");
		mrplabel.setHorizontalAlignment(SwingConstants.RIGHT);
		mrplabel.setForeground(Color.GRAY);
		mrplabel.setBounds(77, 197, 120, 25);
		mrplabel.setBorder(border);
		panel_1.add(mrplabel);
		
		tf_ProductName = new JTextField();
		tf_ProductName.setFont(new Font("Dialog", Font.BOLD, 13));
		tf_ProductName.setEnabled(false);
		tf_ProductName.setEditable(false);
		tf_ProductName.setDisabledTextColor(Color.DARK_GRAY);
		tf_ProductName.setColumns(10);
		tf_ProductName.setBounds(196, 101, 279, 25);
		tf_ProductName.setBorder(border);
		
		panel_1.add(tf_ProductName);
		
		tf_StockQty = new JTextField();
		tf_StockQty.setFont(new Font("Dialog", Font.BOLD, 13));
		tf_StockQty.setEnabled(false);
		tf_StockQty.setEditable(false);
		tf_StockQty.setDisabledTextColor(Color.DARK_GRAY);
		tf_StockQty.setColumns(10);
		tf_StockQty.setBounds(196, 125, 279, 25);
		tf_StockQty.setBorder(border);
		panel_1.add(tf_StockQty);
		
		tf_CategoryName = new JTextField();
		tf_CategoryName.setFont(new Font("Dialog", Font.BOLD, 13));
		tf_CategoryName.setEnabled(false);
		tf_CategoryName.setEditable(false);
		tf_CategoryName.setDisabledTextColor(Color.DARK_GRAY);
		tf_CategoryName.setColumns(10);
		tf_CategoryName.setBorder(border);
		tf_CategoryName.setBounds(196, 149, 279, 25);
		panel_1.add(tf_CategoryName);
		
		tf_Barcode = new JTextField();
		tf_Barcode.setFont(new Font("Dialog", Font.BOLD, 13));
		tf_Barcode.setEnabled(false);
		tf_Barcode.setEditable(false);
		tf_Barcode.setDisabledTextColor(Color.DARK_GRAY);
		tf_Barcode.setColumns(10);
		tf_Barcode.setBorder(border);
		tf_Barcode.setBounds(196, 173, 279, 25);
		panel_1.add(tf_Barcode);
		
		tf_Mrp = new JTextField();
		tf_Mrp.setFont(new Font("Dialog", Font.BOLD, 13));
		tf_Mrp.setEnabled(false);
		tf_Mrp.setEditable(false);
		tf_Mrp.setDisabledTextColor(Color.DARK_GRAY);
		tf_Mrp.setColumns(10);
		tf_Mrp.setBorder(border);
		tf_Mrp.setBounds(196, 197, 279, 25);
		panel_1.add(tf_Mrp);
		
		JButton btnCreateBarcodeSheet = new JButton("Create Barcode Sheet");
		btnCreateBarcodeSheet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createBarcodeSheet();
			}
		});
		btnCreateBarcodeSheet.setBounds(154, 417, 211, 23);
		panel_1.add(btnCreateBarcodeSheet);
		
		JLabel lblStickerPaperType = new JLabel("Sticker Paper Type :");
		lblStickerPaperType.setHorizontalAlignment(SwingConstants.RIGHT);
		lblStickerPaperType.setBounds(26, 279, 120, 27);
		panel_1.add(lblStickerPaperType);
		
		ButtonGroup bg = new ButtonGroup();
		
		rdbtnStickers_65 = new JRadioButton("65 Stickers");
		rdbtnStickers_65.setBounds(154, 281, 108, 23);
		rdbtnStickers_65.setSelected(true);
		rdbtnStickers_65.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setPaperLayoutImage();
			}
		});
		panel_1.add(rdbtnStickers_65);
		
		rdbtnStickers_24 = new JRadioButton("24 Stickers");
		rdbtnStickers_24.setBounds(374, 281, 101, 23);
		panel_1.add(rdbtnStickers_24);
		
		rdbtnStickers_40 = new JRadioButton("40 Stickers");
		rdbtnStickers_40.setBounds(264, 281, 108, 23);
		panel_1.add(rdbtnStickers_40);
		bg.add(rdbtnStickers_65);
		bg.add(rdbtnStickers_40);
		bg.add(rdbtnStickers_24);
		rdbtnStickers_40.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setPaperLayoutImage();	
			}
		});
		rdbtnStickers_24.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setPaperLayoutImage();
			}
		});
		
		JLabel lblStartPosition = new JLabel("Start Position :");
		lblStartPosition.setHorizontalAlignment(SwingConstants.RIGHT);
		lblStartPosition.setBounds(26, 358, 120, 27);
		panel_1.add(lblStartPosition);
		
		tf_startPosition = new JTextField();
		tf_startPosition.setBounds(154, 358, 86, 27);
		tf_startPosition.setText("1");
		tf_startPosition.setFont(new Font("Dialog", Font.BOLD, 13));
		panel_1.add(tf_startPosition);
		tf_startPosition.setColumns(10);
		
		JLabel lblNoOfLabels = new JLabel("No Of Labels :");
		lblNoOfLabels.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNoOfLabels.setBounds(249, 358, 120, 27);
		panel_1.add(lblNoOfLabels);
		
		tf_noOfLabels = new JTextField();
		tf_noOfLabels.setColumns(10);
		tf_noOfLabels.setBounds(377, 358, 86, 27);
		tf_noOfLabels.setFont(new Font("Dialog", Font.BOLD, 13));
		tf_noOfLabels.setText("65");
		panel_1.add(tf_noOfLabels);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Paper Layout", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(724, 91, 367, 471);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		lbl65Image = new JLabel("");
		lbl65Image.setBounds(10, 25, 347, 435);
		lbl65Image.setIcon(resizeImage("/images/65_Labels.png", lbl65Image));
		panel.add(lbl65Image);
		
		lbl40Image = new JLabel("");
		lbl40Image.setBounds(10, 25, 347, 435);
		lbl40Image.setIcon(resizeImage("/images/40_Labels.png", lbl40Image));
		lbl40Image.setVisible(false);
		panel.add(lbl40Image);
		
		lbl24Image = new JLabel("");
		lbl24Image.setBounds(10, 25, 347, 435);
		lbl24Image.setIcon(resizeImage("/images/24_Labels.png", lbl24Image));
		lbl24Image.setVisible(false);
		panel.add(lbl24Image);
		autoSuggestTable.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					if(!autoSuggestTable.getText().equals("")){
						resetFields();
						setProductDetails(autoSuggestTable.getText());
					}
					
				}
			}
		});
		ScalableLayoutUtils.installScalableLayoutAndKeys(new DefaultScalableLayoutRegistry(), this, 0.1);
	}
	
	protected void setPaperLayoutImage() {
		if(rdbtnStickers_65.isSelected()) {
			tf_noOfLabels.setText("65");
			tf_startPosition.setText("1");
			lbl65Image.setVisible(true);
			lbl40Image.setVisible(false);
			lbl24Image.setVisible(false);
		}else if (rdbtnStickers_40.isSelected()) {
			tf_startPosition.setText("1");
			tf_noOfLabels.setText("40");
			lbl65Image.setVisible(false);
			lbl40Image.setVisible(true);
			lbl24Image.setVisible(false);
		}else if(rdbtnStickers_24.isSelected()) {
			tf_startPosition.setText("1");
			tf_noOfLabels.setText("24");
			lbl65Image.setVisible(false);
			lbl40Image.setVisible(false);
			lbl24Image.setVisible(true);
		}
		
	}

	public ImageIcon resizeImage(String url,JLabel label){
        ImageIcon MyImage = new ImageIcon(PrintBarcodeSheetUI.class.getResource(url));
        Image img = MyImage.getImage();
        Image newImage = img.getScaledInstance(label.getWidth(), label.getHeight(),Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(newImage);
        return image;
    }
	
	protected void setProductDetails(String productName) {
		Product product = productMap.get(productName);
		tf_ProductName.setText(" "+product.getProductName());
		tf_CategoryName.setText(" "+product.getProductCategory());
		tf_Barcode.setText(" "+String.valueOf(product.getProductBarCode()));
		tf_StockQty.setText(" "+String.valueOf(product.getQuanity()));
		tf_Mrp.setText(" "+PDFUtils.getDecimalFormat(product.getProductMRP()) );
	}
	
	private void resetFields(){
		tf_ProductName.setText("");
		tf_CategoryName.setText("");
		tf_Barcode.setText("");
		tf_StockQty.setText("");
		tf_Mrp.setText("");
	}

	public List<String> getProductName(){
		
		List<String> productNameList = new ArrayList<String>();
		productMap = new HashMap<String, Product>();
		for(Product product :ProductServices.getAllProducts()){
			productNameList.add(product.getProductName()+" : "+PDFUtils.getDecimalFormat(product.getProductMRP()));
			productMap.put(product.getProductName()+" : "+PDFUtils.getDecimalFormat(product.getProductMRP()),product);
		}
		return productNameList;
	}

	private void createBarcodeSheet() {
		String fileName = tf_ProductName.getText();
		String JrxmlName = null;
		//List<Barcode> barcodeList=null;
		int startPosition = 1;
		int noOfLabels = 0;
		if(PDFUtils.isMandatoryEntered(tf_Barcode) 
				&& PDFUtils.isMandatoryEntered(tf_ProductName)
				&& PDFUtils.isMandatoryEntered(tf_Mrp)){
			try {
				Barcode barcode = new  Barcode();
				barcode.setBarcode(tf_Barcode.getText().trim());
				barcode.setPrice(Double.valueOf(tf_Mrp.getText()));
				barcode.setProductName(tf_ProductName.getText());
				
				if(rdbtnStickers_65.isSelected()) {
					JrxmlName = AppConstants.BARCODE_65_JASPER;
					//barcodeList = createDataSource(barcode,65);
					noOfLabels = 65;
				}else if (rdbtnStickers_24.isSelected()) {
					JrxmlName = AppConstants.BARCODE_24_JASPER;
					//barcodeList = createDataSource(barcode,24);
					noOfLabels = 24;
				}else if(rdbtnStickers_40.isSelected()) {
					JrxmlName = AppConstants.BARCODE_40_JASPER;
					//barcodeList = createDataSource(barcode,40);
					noOfLabels = 40;
				}
				if(!tf_startPosition.getText().equals("")) {
					startPosition = Integer.valueOf(tf_startPosition.getText());
				}
				if(!tf_noOfLabels.getText().equals("")) {
					noOfLabels = Integer.valueOf(tf_noOfLabels.getText());
				}
				
				boolean isSuccess = JasperUtils.createPDFForBarcode(JasperServices.createDataForBarcode(barcode,noOfLabels,startPosition), JrxmlName, fileName);
				if(!isSuccess) {
					JOptionPane.showMessageDialog(getContentPane(),"Barcode length should be 12 digits! Please correct barcode number!","Barcode Invalid",JOptionPane.WARNING_MESSAGE);
				}
			} catch (Exception e1) {
				e1.printStackTrace();
				logger.error("Print Barcode Sheet Exception : ",e1);
			} 
		}else{
			JOptionPane.showMessageDialog(getContentPane(), "Please Select the Product !");
		}
	}
}
