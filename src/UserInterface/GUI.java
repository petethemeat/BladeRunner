package UserInterface;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JRadioButtonMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;

import javax.swing.SwingConstants;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;

import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.border.BevelBorder;

import Controllers.Hub;
import Controllers.QueryController;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import java.awt.Component;

public class GUI {

	/*
	 * Variables
	 */
	private JFrame frmBit;
	private Font font = new Font("Arial", Font.PLAIN, 26);
	private JTextField textField;
	private JLabel errorMessage;
	public static double screenWidth;
	public static double screenHeight;
	private String[] results;
	private String id;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		Hub.start();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frmBit.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		// Get size of screen for adjusting of window
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		screenWidth = screenSize.getWidth();
		screenHeight = screenSize.getHeight();
		
		// Setup frame for laying panels and layouts for placement of components
		frmBit = new JFrame();
		frmBit.setResizable(false);
		frmBit.setTitle("BIT");
		frmBit.pack();
		frmBit.setSize((int)screenWidth/2,(int)screenHeight/2);
		frmBit.setLocationRelativeTo(null);
		frmBit.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frmBit.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		mnFile.setFont(font);
		menuBar.add(mnFile);
		
		JMenu mnNew = new JMenu("New");
		mnNew.setFont(font);
		mnFile.add(mnNew);
		
		JMenuItem mntmBlade = new JMenuItem("Blade");
		mntmBlade.setFont(font);
		mntmBlade.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Create new window to enter data for submission to database
				NewBlade newInventory = new NewBlade(screenWidth, screenHeight);
				newInventory.setVisible(true);
				newInventory.setLocationRelativeTo(frmBit);
			}
		});
		mnNew.add(mntmBlade);
		
		JMenuItem mntmClose = new JMenuItem("Close");
		mntmClose.setFont(font);
		mntmClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		
		
		mnFile.add(mntmClose);
		
		
		
		JMenu mnScan = new JMenu("Scan");
		mnScan.setFont(font);
		menuBar.add(mnScan);
		
		JRadioButtonMenuItem rdbtnmntmQr = new JRadioButtonMenuItem("QR");
		rdbtnmntmQr.setFont(font);
		rdbtnmntmQr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textField.setEditable(true);
			}
		});
		mnScan.add(rdbtnmntmQr);
		
		JRadioButtonMenuItem rdbtnmntmRfid = new JRadioButtonMenuItem("RFID");
		rdbtnmntmRfid.setFont(font);
		rdbtnmntmRfid.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.setEditable(true);
			}
		});
		mnScan.add(rdbtnmntmRfid);
		
		ButtonGroup btngroup = new ButtonGroup();
		btngroup.add(rdbtnmntmQr);
		btngroup.add(rdbtnmntmRfid);
		
		// Add menu for Setup of qr and qr code generation
		JMenu mnSetup = new JMenu("Setup");
		mnSetup.setFont(font);
		menuBar.add(mnSetup);
		
		// Add menu for qr power off 
		JMenu mnqrpower = new JMenu("QR Power Off");
		mnqrpower.setFont(font);
		mnSetup.add(mnqrpower);
		
		// Add barcode image to allow for user to power off QR
		JLabel qr_off = new JLabel("");
		mnqrpower.add(qr_off);
		Image off_img = new ImageIcon(this.getClass().getResource("/res/QRPowerOff.png")).getImage();
		qr_off.setIcon(new ImageIcon(off_img));
		
		// Add menu for pairing of qr with PC or MAC (MAC unused in this prototype)
		JMenu mnPairing = new JMenu("Pairing");
		mnPairing.setFont(font);
		mnSetup.add(mnPairing);
		
		// Menu item PC to pair QR with PC
		JMenu mnPc = new JMenu("PC");
		mnPc.setFont(font);
		mnPairing.add(mnPc);
		
		// Barcode image (PC) and instructions for pairing
		JLabel qr_pc = new JLabel("");
		mnPc.add(qr_pc);
		Image pc_img = new ImageIcon(this.getClass().getResource("/res/pcPairing.png")).getImage();
		qr_pc.setIcon(new ImageIcon(pc_img));

	
		// Menu item MAC to pair QR with MAC
		JMenu mnMac = new JMenu("MAC");
		mnMac.setFont(font);
		mnPairing.add(mnMac);
		
		// Barcode image (MAC) and instructions for pairing
		JLabel qr_mac = new JLabel("");
		mnMac.add(qr_mac);
		Image mac_img = new ImageIcon(this.getClass().getResource("/res/macPairing.PNG")).getImage();
		qr_mac.setIcon(new ImageIcon(mac_img));
		
		// Drop menu for selection of different QR scan modes
		JMenu mnScanMode = new JMenu("Scan Mode");
		mnScanMode.setFont(font);
		mnSetup.add(mnScanMode);
		
		// Selection for auto scan mode (scans and displays info)
		JMenu mnAutomatic = new JMenu("Automatic");
		mnAutomatic.setFont(font);
		mnScanMode.add(mnAutomatic);
		
		// Barcode image and instructions
		JLabel autoScan = new JLabel("");
		mnAutomatic.add(autoScan);
		Image autoscan_img = new ImageIcon(this.getClass().getResource("/res/autoScan.PNG")).getImage();
		autoScan.setIcon(new ImageIcon(autoscan_img));
		
		
		// Drop menu for inventory scan settings of QR scanner
		JMenu mnManual = new JMenu("Inventory");
		mnManual.setFont(font);
		mnScanMode.add(mnManual);
		
		// Instructions and barcode images for inventory mode of QR
		JLabel inventoryScan = new JLabel("");
		mnManual.add(inventoryScan);
		Image inventory_img = new ImageIcon(this.getClass().getResource("/res/inventoryScan.PNG")).getImage();
		inventoryScan.setIcon(new ImageIcon(inventory_img));
		
		JMenuItem mnQRCode = new JMenuItem("QR Code Generation");
		mnQRCode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				QRGeneration qrcode = new QRGeneration(screenWidth, screenHeight);
				qrcode.setVisible(true);
				qrcode.setLocationRelativeTo(frmBit);
			}
		});
		mnQRCode.setFont(font);
		mnSetup.add(mnQRCode);
		
		
		
		JMenu mnHelp = new JMenu("Help");
		mnHelp.setFont(font);
		menuBar.add(mnHelp);
		
		JMenuItem mntmQr_1 = new JMenuItem("QR");
		mntmQr_1.setFont(font);
		mntmQr_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Open file for QR instructions
				try {
				    File file = File.createTempFile("QRManual", ".pdf");
			        file.deleteOnExit();
			        InputStream resource = getClass().getResourceAsStream("/res/QRManual.pdf");
			        Path temp = file.toPath();
			        
			        Files.copy(resource, temp, StandardCopyOption.REPLACE_EXISTING);

				    if(Desktop.isDesktopSupported())
				    {
				        Desktop dTop = Desktop.getDesktop();
				        dTop.open(file);
				       
				    }
				} catch (IOException ex) {}
			}
		});
		
		
		mnHelp.add(mntmQr_1);
		
		JMenu mnRfid = new JMenu("RFID");
		mnRfid.setFont(font);
		mnHelp.add(mnRfid);
		
		JMenuItem mntmTiMicrocontroller = new JMenuItem("TI MicroController");
		mntmTiMicrocontroller.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Open file for TI datasheet
				try {
					// Create temp file for copying original to
					// Delete upon exiting
				    File file = File.createTempFile("TIDatasheet", ".pdf");
			        file.deleteOnExit();
			        // Extract file from its location 
			        // Get the path for copying to
			        InputStream resource = getClass().getResourceAsStream("/res/tm4c123.pdf");
			        Path temp = file.toPath();
			        // Copy original to temporary for display on desktop
			        Files.copy(resource, temp, StandardCopyOption.REPLACE_EXISTING);

			        // Show file on desktop
				    if(Desktop.isDesktopSupported()){
				        Desktop dTop = Desktop.getDesktop();
				        dTop.open(file);  
				    }
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		});
		mntmTiMicrocontroller.setFont(font);
		mnRfid.add(mntmTiMicrocontroller);
		
		JMenuItem mntmRfidM = new JMenuItem("RFID Module");
		mntmRfidM.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Open file for mfrc522 datasheet
				try {
					// Create temp file for copying original to
					// Delete upon exiting
				    File file = File.createTempFile("RFID", ".pdf");
			        file.deleteOnExit();
			        // Extract file from its location 
			        // Get the path for copying to
			        InputStream resource = getClass().getResourceAsStream("/res/MFRC522.pdf");
			        Path temp = file.toPath();
			        // Copy original to temporary for display on desktop
			        Files.copy(resource, temp, StandardCopyOption.REPLACE_EXISTING);

			        // Show file on desktop
				    if(Desktop.isDesktopSupported()){
				        Desktop dTop = Desktop.getDesktop();
				        dTop.open(file);  
				    }
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		});
		mntmRfidM.setFont(font);
		mnRfid.add(mntmRfidM);
		
		JMenuItem mntmWirelessModule = new JMenuItem("Wireless Module");
		mntmWirelessModule.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Open file for ESP8266 wireless datasheet
				try {
					// Create temp file for copying original to
					// Delete upon exiting
				    File file = File.createTempFile("WiFi", ".pdf");
			        file.deleteOnExit();
			        // Extract file from its location 
			        // Get the path for copying to
			        InputStream resource = getClass().getResourceAsStream("/res/ESP8266.pdf");
			        Path temp = file.toPath();
			        // Copy original to temporary for display on desktop
			        Files.copy(resource, temp, StandardCopyOption.REPLACE_EXISTING);

			        // Show file on desktop
				    if(Desktop.isDesktopSupported()){
				        Desktop dTop = Desktop.getDesktop();
				        dTop.open(file);  
				    }
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		});
		mntmWirelessModule.setFont(font);
		mnRfid.add(mntmWirelessModule);
		
		// Setup logo for top of screen and resize appropriately
		JLabel logo = new JLabel("");
		logo.setHorizontalAlignment(SwingConstants.CENTER);
		Image img = new ImageIcon(this.getClass().getResource("/res/TenarisLogo.png")).getImage();
		Image scaledimg = img.getScaledInstance((int)screenWidth/2, (int)screenHeight/5,  java.awt.Image.SCALE_SMOOTH);
		logo.setIcon(new ImageIcon(scaledimg));
		frmBit.getContentPane().add(logo, BorderLayout.NORTH);
		
		// Added panel for a border layout below the logo to place text and label at north and buttons/messages at center
		JPanel panel = new JPanel();
		frmBit.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		// Add a panel to border layout north with flow layout to place label and textfield for blade id
		JPanel pnlBladeID = new JPanel();
		panel.add(pnlBladeID, BorderLayout.NORTH);
		pnlBladeID.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 50));
		
		// Create and place blade id label and text field in newest panel
		JLabel lblBladeId = new JLabel("Blade ID :");
		lblBladeId.setFont(new Font("Arial", Font.PLAIN, 40));
		pnlBladeID.add(lblBladeId);
		
		textField = new JTextField(null);
		textField.setFont(new Font("Arial", Font.PLAIN, 40));
		pnlBladeID.add(textField);
		textField.setColumns(20);
		textField.setEditable(false);
		
		// Add panel to the center border layout for placement of query button and error message popups
		JPanel pnlButton = new JPanel();
		pnlButton.setFont(new Font("Arial", Font.PLAIN, 21));
		panel.add(pnlButton, BorderLayout.CENTER);
		
		JButton btnQuery = new JButton("QUERY DATABASE");
		btnQuery.setForeground(Color.BLACK);
		btnQuery.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Get blade id from textfield and query the database for blade info
				if(textField.isEditable()){
					id = textField.getText();
					if(!id.equals("")){
						// Hide error message if they put a value in the textfield
						errorMessage.setVisible(false);
						try {
							results = QueryController.run(id);
							// Create new window to enter data for submission to database
							ExistingBlades querydb = new ExistingBlades(screenWidth, screenHeight, results);
							querydb.setVisible(true);
							// Set location of window centered to frame
							querydb.setLocationRelativeTo(frmBit);	
						
						} catch (SQLException e) {
							// Pop-up window for error, no such blade exists
							
							e.printStackTrace();
						}
					}
					else{
						errorMessage.setVisible(true);
					}
				}
				else{
					errorMessage.setVisible(true);
				}
				
			}
		});
		pnlButton.setLayout(new BoxLayout(pnlButton, BoxLayout.Y_AXIS));
		btnQuery.setBackground(Color.LIGHT_GRAY);
		btnQuery.setIcon(null);
		btnQuery.setHorizontalTextPosition(SwingConstants.CENTER);
		btnQuery.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnQuery.setPreferredSize(new Dimension(300, 60));
		btnQuery.setMaximumSize(new Dimension(400, 80));
		btnQuery.setFocusPainted(false);
		btnQuery.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnQuery.setFont(new Font("Arial", Font.PLAIN, 36));
		pnlButton.add(btnQuery);
		pnlButton.add(Box.createVerticalStrut(10));
		
		// Throw a visual error to scan for an id
		errorMessage = new JLabel("Enter ID");
		errorMessage.setVisible(false);
		errorMessage.setForeground(Color.RED);
		errorMessage.setHorizontalAlignment(SwingConstants.CENTER);
		errorMessage.setAlignmentX(Component.CENTER_ALIGNMENT);
		errorMessage.setHorizontalTextPosition(SwingConstants.CENTER);
		errorMessage.setFont(new Font("Arial", Font.PLAIN, 30));
		pnlButton.add(errorMessage);
		
	}
}
