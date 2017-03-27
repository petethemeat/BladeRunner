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

public class GUI {

	private JFrame frmBit;
	private JTextField textField;
	private double screenWidth;
	private double screenHeight;
	private int screenX;
	private int screenY;
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
		mnFile.setFont(new Font("Segoe UI", Font.PLAIN, 26));
		menuBar.add(mnFile);
		
		JMenu mnNew = new JMenu("New");
		mnNew.setFont(new Font("Segoe UI", Font.PLAIN, 26));
		mnFile.add(mnNew);
		
		JMenuItem mntmBlade = new JMenuItem("Blade");
		mntmBlade.setFont(new Font("Segoe UI", Font.PLAIN, 26));
		mntmBlade.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Create new window to enter data for submission to database
				NewBlade newInventory = new NewBlade();
				newInventory.setVisible(true);
				newInventory.setLocationRelativeTo(frmBit);
			}
		});
		mnNew.add(mntmBlade);
		
		JMenuItem mntmClose = new JMenuItem("Close");
		mntmClose.setFont(new Font("Segoe UI", Font.PLAIN, 26));
		mntmClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		
		
		mnFile.add(mntmClose);
		
		
		
		JMenu mnScan = new JMenu("Scan");
		mnScan.setFont(new Font("Segoe UI", Font.PLAIN, 26));
		menuBar.add(mnScan);
		
		JRadioButtonMenuItem rdbtnmntmQr = new JRadioButtonMenuItem("QR");
		rdbtnmntmQr.setFont(new Font("Segoe UI", Font.PLAIN, 26));
		rdbtnmntmQr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textField.setEditable(true);
			}
		});
		mnScan.add(rdbtnmntmQr);
		
		JRadioButtonMenuItem rdbtnmntmRfid = new JRadioButtonMenuItem("RFID");
		rdbtnmntmRfid.setFont(new Font("Segoe UI", Font.PLAIN, 26));
		rdbtnmntmRfid.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.setEditable(true);
			}
		});
		mnScan.add(rdbtnmntmRfid);
		
		ButtonGroup btngroup = new ButtonGroup();
		btngroup.add(rdbtnmntmQr);
		btngroup.add(rdbtnmntmRfid);
		
		JMenu mnSetup = new JMenu("Setup");
		mnSetup.setFont(new Font("Segoe UI", Font.PLAIN, 26));
		menuBar.add(mnSetup);
		
		JMenu mnPairing = new JMenu("Pairing");
		mnPairing.setFont(new Font("Segoe UI", Font.PLAIN, 26));
		mnSetup.add(mnPairing);
		
		// Menu item PC to pair QR with PC
		JMenu mnPc = new JMenu("PC");
		mnPc.setFont(new Font("Segoe UI", Font.PLAIN, 26));
		mnPairing.add(mnPc);
		
		/*// Barcode image (PC) and instructions for pairing
		JLabel qr_pc = new JLabel("");
		mnPc.add(qr_pc);
		Image pc_img = new ImageIcon(this.getClass().getResource("/pcPairing.png")).getImage();
		qr_pc.setIcon(new ImageIcon(pc_img));*/
		
		// Menu item MAC to pair QR with MAC
		JMenu mnMac = new JMenu("MAC");
		mnMac.setFont(new Font("Segoe UI", Font.PLAIN, 26));
		mnPairing.add(mnMac);
		
		/*// Barcode image (MAC) and instructions for pairing
		JLabel qr_mac = new JLabel("");
		mnMac.add(qr_mac);
		Image mac_img = new ImageIcon(this.getClass().getResource("../img/macPairing.PNG")).getImage();
		qr_mac.setIcon(new ImageIcon(mac_img));
		*/
		
		// Drop menu for selection of different QR scan modes
		JMenu mnScanMode = new JMenu("Scan Mode");
		mnScanMode.setFont(new Font("Segoe UI", Font.PLAIN, 26));
		mnSetup.add(mnScanMode);
		
		// Selection for auto scan mode (scans and displays info)
		JMenu mnAutomatic = new JMenu("Automatic");
		mnAutomatic.setFont(new Font("Segoe UI", Font.PLAIN, 26));
		mnScanMode.add(mnAutomatic);
		
		/*// Barcode image and instructions
		JLabel autoScan = new JLabel("");
		mnAutomatic.add(autoScan);
		Image autoscan_img = new ImageIcon(this.getClass().getResource("/autoScan.PNG")).getImage();
		autoScan.setIcon(new ImageIcon(autoscan_img));
		*/
		
		// Drop menu for inventory scan settings of QR scanner
		JMenu mnManual = new JMenu("Inventory");
		mnManual.setFont(new Font("Segoe UI", Font.PLAIN, 26));
		mnScanMode.add(mnManual);
		
		/*// Instructions and barcode images for inventory mode of QR
		JLabel inventoryScan = new JLabel("");
		mnManual.add(inventoryScan);
		Image inventory_img = new ImageIcon(this.getClass().getResource("/inventoryScan.PNG")).getImage();
		inventoryScan.setIcon(new ImageIcon(inventory_img));
		*/
		
		
		JMenu mnHelp = new JMenu("Help");
		mnHelp.setFont(new Font("Segoe UI", Font.PLAIN, 26));
		menuBar.add(mnHelp);
		
		JMenuItem mntmQr_1 = new JMenuItem("QR");
		mntmQr_1.setFont(new Font("Segoe UI", Font.PLAIN, 26));
		mntmQr_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Open file for QR instructions
				try {
				    File file = File.createTempFile("QRManual", ".pdf");
			        file.deleteOnExit();
			        InputStream resource = getClass().getResourceAsStream("/QRManual.pdf");
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
		mnRfid.setFont(new Font("Segoe UI", Font.PLAIN, 26));
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
			        InputStream resource = getClass().getResourceAsStream("/tm4c123.pdf");
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
		mntmTiMicrocontroller.setFont(new Font("Segoe UI", Font.PLAIN, 26));
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
			        InputStream resource = getClass().getResourceAsStream("/MFRC522.pdf");
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
		mntmRfidM.setFont(new Font("Segoe UI", Font.PLAIN, 26));
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
			        InputStream resource = getClass().getResourceAsStream("/ESP8266.pdf");
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
		mntmWirelessModule.setFont(new Font("Segoe UI", Font.PLAIN, 26));
		mnRfid.add(mntmWirelessModule);
		
		/*// Setup logo for top of screen and resize appropriately
		JLabel logo = new JLabel("");
		logo.setHorizontalAlignment(SwingConstants.CENTER);
		Image img = new ImageIcon(this.getClass().getResource("/logo.png")).getImage();
		Image scaledimg = img.getScaledInstance((int)screenWidth/2, (int)screenHeight/5,  java.awt.Image.SCALE_SMOOTH);
		logo.setIcon(new ImageIcon(scaledimg));
		frmBit.getContentPane().add(logo, BorderLayout.NORTH);
		*/
		
		JPanel panel = new JPanel();
		frmBit.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1, BorderLayout.NORTH);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 50));
		
		JLabel lblBladeId = new JLabel("Blade ID :");
		lblBladeId.setFont(new Font("Arial", Font.PLAIN, 40));
		panel_1.add(lblBladeId);
		
		textField = new JTextField();
		textField.setFont(new Font("Arial", Font.PLAIN, 40));
		panel_1.add(textField);
		textField.setColumns(20);
		textField.setEditable(false);
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btnQuery = new JButton("QUERY DATABASE");
		btnQuery.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Get blade id from textfield and query the database for blade info
				try{
					id = textField.getText();
				}catch (NullPointerException e){
					// Throw a visual error to scan for an id
					e.printStackTrace();
				}
				try {
					results = QueryController.run(id);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				// Create new window to enter data for submission to database
				ExistingBlades querydb = new ExistingBlades(screenWidth, screenHeight, results);
				querydb.setVisible(true);
				// Set location offset from main frame
				screenX = frmBit.getX();
				screenY = frmBit.getY();
				querydb.setLocation(screenX + 100, screenY + 100);
			}
		});
		btnQuery.setBackground(Color.LIGHT_GRAY);
		btnQuery.setIcon(null);
		btnQuery.setHorizontalTextPosition(SwingConstants.RIGHT);
		btnQuery.setPreferredSize(new Dimension(300, 60));
		btnQuery.setMaximumSize(new Dimension(400, 80));
		btnQuery.setFocusPainted(false);
		btnQuery.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnQuery.setFont(new Font("Arial", Font.PLAIN, 30));
		panel_2.add(btnQuery);
		
	}

}
