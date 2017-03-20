package UserInterface;

import java.awt.EventQueue;

import java.awt.Image;

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
import java.awt.Desktop;

import javax.swing.SwingConstants;
import java.io.File;
import java.io.IOException;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.border.BevelBorder;
import java.awt.Dimension;

public class GUI {

	private JFrame frmBit;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
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
		frmBit = new JFrame();
		frmBit.setTitle("BIT");
		frmBit.setBounds(100, 100, 480, 420);
		frmBit.setLocationRelativeTo(null);
		frmBit.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frmBit.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		mnFile.setFont(new Font("Segoe UI", Font.PLAIN, 22));
		menuBar.add(mnFile);
		
		JMenu mnNew = new JMenu("New");
		mnNew.setFont(new Font("Segoe UI", Font.PLAIN, 22));
		mnFile.add(mnNew);
		
		JMenuItem mntmBlade = new JMenuItem("Blade");
		mntmBlade.setFont(new Font("Segoe UI", Font.PLAIN, 22));
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
		mntmClose.setFont(new Font("Segoe UI", Font.PLAIN, 22));
		mntmClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		
		
		mnFile.add(mntmClose);
		
		
		
		JMenu mnScan = new JMenu("Scan");
		mnScan.setFont(new Font("Segoe UI", Font.PLAIN, 22));
		menuBar.add(mnScan);
		
		JRadioButtonMenuItem rdbtnmntmQr = new JRadioButtonMenuItem("QR");
		rdbtnmntmQr.setFont(new Font("Segoe UI", Font.PLAIN, 22));
		rdbtnmntmQr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textField.setEditable(true);
			}
		});
		mnScan.add(rdbtnmntmQr);
		
		JRadioButtonMenuItem rdbtnmntmRfid = new JRadioButtonMenuItem("RFID");
		rdbtnmntmRfid.setFont(new Font("Segoe UI", Font.PLAIN, 22));
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
		mnSetup.setFont(new Font("Segoe UI", Font.PLAIN, 22));
		menuBar.add(mnSetup);
		
		JMenuItem mntmQr = new JMenuItem("QR");
		mntmQr.setFont(new Font("Segoe UI", Font.PLAIN, 22));
		mntmQr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Open new window with qr setup information
				QRSetup setup = new QRSetup();
				setup.setLocationRelativeTo(frmBit);
				setup.setVisible(true);
			}
		});
		mnSetup.add(mntmQr);
		
		JMenu mnHelp = new JMenu("Help");
		mnHelp.setFont(new Font("Segoe UI", Font.PLAIN, 22));
		menuBar.add(mnHelp);
		
		JMenuItem mntmQr_1 = new JMenuItem("QR");
		mntmQr_1.setFont(new Font("Segoe UI", Font.PLAIN, 22));
		mntmQr_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Open file for QR instructions
				if (Desktop.isDesktopSupported()) {
				    try {
				        File myFile = new File("./docs/QRManual.pdf");
				        Desktop.getDesktop().open(myFile);
				    } catch (IOException ex) {
				        System.out.println("Could not locate file");
				    }
				}
			}
		});
		
		
		mnHelp.add(mntmQr_1);
		
		JMenuItem mntmRfid = new JMenuItem("RFID");
		mntmRfid.setFont(new Font("Segoe UI", Font.PLAIN, 22));
		mnHelp.add(mntmRfid);
		
		JLabel logo = new JLabel("");
		logo.setHorizontalAlignment(SwingConstants.CENTER);
		Image img = new ImageIcon(this.getClass().getResource("/logo.png")).getImage();
		logo.setIcon(new ImageIcon(img));
		frmBit.getContentPane().add(logo, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		frmBit.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1, BorderLayout.NORTH);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 50));
		
		JLabel lblBladeId = new JLabel("Blade ID :");
		lblBladeId.setFont(new Font("Tahoma", Font.PLAIN, 22));
		panel_1.add(lblBladeId);
		
		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 22));
		panel_1.add(textField);
		textField.setColumns(16);
		textField.setEditable(false);
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btnQuery = new JButton("QUERY");
		btnQuery.setPreferredSize(new Dimension(91, 40));
		btnQuery.setMaximumSize(new Dimension(91, 40));
		btnQuery.setFocusPainted(false);
		btnQuery.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnQuery.setFont(new Font("Tahoma", Font.PLAIN, 22));
		panel_2.add(btnQuery);
		
	}

}
