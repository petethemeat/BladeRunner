package UserInterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import Controllers.QRGeneratorController;
import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class QRGeneration extends JFrame implements Runnable{
	JComboBox<String> comboSize;
	JComboBox<String> comboError;
	JComboBox<String> comboEncoding;
	private JTextField id;
	private InputStream qrpath;
	private String qrname;
	private byte[] buffer;

	/**
	 * Create the application.
	 */
	public QRGeneration(double width, double height) {
		this.setTitle("QR Code Generation");
		this.setSize((int)width/3, (int)height/2);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JLabel lblPreview = new JLabel("PREVIEW");
		lblPreview.setHorizontalAlignment(SwingConstants.CENTER);
		lblPreview.setFont(new Font("Arial", Font.PLAIN, 24));
		getContentPane().add(lblPreview, BorderLayout.NORTH);
		
		JPanel infopanel = new JPanel();
		infopanel.setPreferredSize(new Dimension(10, 200));
		getContentPane().add(infopanel, BorderLayout.SOUTH);
		infopanel.setLayout(new BorderLayout(0, 0));
		
		// Setup panel for placing buttons
		JPanel buttonpanel = new JPanel();
		buttonpanel.setSize(new Dimension(100, 100));
		buttonpanel.setMinimumSize(new Dimension(10, 200));
		infopanel.add(buttonpanel, BorderLayout.SOUTH);
		GridBagLayout gbl_buttonpanel = new GridBagLayout();
		gbl_buttonpanel.columnWidths = new int[]{0};
		gbl_buttonpanel.rowHeights = new int[]{10, 10, 10};
		gbl_buttonpanel.columnWeights = new double[]{Double.MIN_VALUE};
		gbl_buttonpanel.rowWeights = new double[]{0.0, 0.0, 0.0};
		buttonpanel.setLayout(gbl_buttonpanel);
		
		// Add buttons and events for all buttons
		JButton generate = new JButton("GENERATE");
		generate.addActionListener(new ActionListener() {
			// Pull info from menus and run generation code to populate image panel with QR
			@SuppressWarnings("unused")
			@Override
			public void actionPerformed(ActionEvent e) {
				String size = (String) comboSize.getSelectedItem();
				size = size.replace("\"", "");
				Integer qrsize = Integer.parseInt(size);
				String error = (String) comboError.getSelectedItem();
				error = error.replace("%", "");
				Integer qrerror = Integer.parseInt(error);
				String encoding = id.getText();
				try {
					qrpath = QRGeneratorController.run(qrsize, encoding);
				} catch (IllegalArgumentException e2) {
					e2.printStackTrace();
				} catch (IOException e2) {
					e2.printStackTrace();
				}
				try {
					buffer = new byte[qrpath.available()];
					qrpath.read(buffer);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			   
			}
			
		});
		generate.setFont(new Font("Arial", Font.PLAIN, 20));
		generate.setFocusPainted(false);
		generate.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		GridBagConstraints gbc_generate = new GridBagConstraints();
		gbc_generate.insets = new Insets(0, 0, 10, 0);
		gbc_generate.gridx = 0;
		gbc_generate.gridy = 0;
		buttonpanel.add(generate, gbc_generate);
		
		
		JButton cancel = new JButton("CANCEL");
		cancel.addActionListener(new ActionListener() {
			// Close window upon selection of close button
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
			
		});
		
		JButton save = new JButton("SAVE");
		save.addActionListener(new ActionListener() {
			// Close window upon selection of close button
			@Override
			public void actionPerformed(ActionEvent arg0) {
				 JFileChooser c = new JFileChooser();
			      // Demonstrate "Save" dialog:
			      int rVal = c.showSaveDialog(QRGeneration.this);
			      if (rVal == JFileChooser.APPROVE_OPTION) {
			    	  qrname = c.getSelectedFile().getName();
			    	  
					  File targetFile = new File(c.getCurrentDirectory().toString() + "\\" + qrname + ".png");
				      OutputStream outStream;
					try {
						outStream = new FileOutputStream(targetFile);
						outStream.write(buffer);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				      
			      }
			      if (rVal == JFileChooser.CANCEL_OPTION) {
			    	c.setVisible(false);
			      }
			}
			
		});
		save.setFocusPainted(false);
		save.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		save.setFont(new Font("Arial", Font.PLAIN, 20));
		GridBagConstraints gbc_save = new GridBagConstraints();
		gbc_save.insets = new Insets(0, 0, 10, 0);
		gbc_save.gridx = 0;
		gbc_save.gridy = 1;
		buttonpanel.add(save, gbc_save);
		cancel.setFont(new Font("Arial", Font.PLAIN, 20));
		cancel.setFocusPainted(false);
		cancel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		GridBagConstraints gbc_cancel = new GridBagConstraints();
		gbc_cancel.insets = new Insets(0, 0, 10, 0);
		gbc_cancel.gridx = 0;
		gbc_cancel.gridy = 2;
		buttonpanel.add(cancel, gbc_cancel);
		
		
		// Setup panel for all dropdown selections
		JPanel dropdownpanel = new JPanel();
		infopanel.add(dropdownpanel, BorderLayout.CENTER);
		dropdownpanel.setLayout(new MigLayout("align 50% 50%", "[][][][][]", "[]"));
		
		JLabel lblSize = new JLabel("Size :");
		lblSize.setHorizontalTextPosition(SwingConstants.CENTER);
		lblSize.setHorizontalAlignment(SwingConstants.CENTER);
		lblSize.setFont(new Font("Arial", Font.PLAIN, 20));
		dropdownpanel.add(lblSize, "cell 4 0");
		
		
		// Add items to combobox
//		String[] sizes = {"1\"", "2\"", "3\"", "4\""};
//		comboSize = new JComboBox<String>(sizes);
		comboSize = new JComboBox<String>();
		comboSize.addItem("1\"");
		comboSize.addItem("2\"");
		comboSize.addItem("3\"");
		comboSize.addItem("4\"");
		comboSize.setPreferredSize(new Dimension(100, 29));
		comboSize.setFont(new Font("Arial", Font.PLAIN, 20));
		dropdownpanel.add(comboSize, "cell 5 0");
		
		
		// Add user area for text to generate as a qr code
		JLabel lblCode = new JLabel("Text :");
		lblCode.setHorizontalTextPosition(SwingConstants.LEADING);
		lblCode.setFont(new Font("Arial", Font.PLAIN, 20));
		dropdownpanel.add(lblCode, "cell 4 2");
		
		id = new JTextField(null);
		id.setFont(new Font("Arial", Font.PLAIN, 20));
		dropdownpanel.add(id, "cell 5 2");
		id.setColumns(10);
		id.setEditable(true);
		
		
		JPanel imagepanel = new JPanel();
		imagepanel.setBackground(Color.WHITE);
		getContentPane().add(imagepanel, BorderLayout.CENTER);
		
	}


	@Override
	public void run() {
		try {
			this.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	
}
