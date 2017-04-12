package UserInterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

import Controllers.AddController;
import net.miginfocom.swing.MigLayout;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class NewBlade extends JFrame implements Runnable{
	
	/*
	 * Variables
	 */
	private Font font = new Font("Arial", Font.PLAIN, 20);
	private JTextField bladeID;
	private JTextField bladeSize;
	private JTextField startDate;
	private JTextField hoursUsed;
	private String[] fields = {"id", "blade_size", "start_date", "hours_used"}; 
	private String[] values = new String[4];
	private JLabel lblNewBladeAdded;
	
	
	@Override
	public void run() {
		try {
			this.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Create the frame.
	 */
	public NewBlade(double width, double height) {
		this.setTitle("New Blade");
		this.setSize((int)width/3, (int)height/4);
		this.setResizable(false);
		this.setTitle("New Blade");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		// Setup of panel to accept and layout buttons in bottom of new window
		JPanel buttonpanel = new JPanel();
		buttonpanel.setPreferredSize(new Dimension(10, 50));
		getContentPane().add(buttonpanel, BorderLayout.SOUTH);
		buttonpanel.setLayout(new GridLayout(1, 0, 0, 0));
		
		JButton btnAdd = new JButton("ADD");
		btnAdd.addActionListener(new ActionListener() {
			// Get values from fields and call add controller code
			@Override
			public void actionPerformed(ActionEvent arg0) {
				values[0] = bladeID.getText();
				values[1] = bladeSize.getText();
				values[2] = startDate.getText();
				values[3] = hoursUsed.getText();
				try {
					AddController.run(fields, values);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				// Show a message to relay to customer that new blade was added to database
				lblNewBladeAdded.setVisible(true);
			}
			
		});
		btnAdd.setFocusPainted(false);
		btnAdd.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnAdd.setFont(font);
		buttonpanel.add(btnAdd);
		
		JButton btnDate = new JButton("GET DATE");
		btnDate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String date = CurrentDate.getCurrentDate();
				// Append zero to end of seconds to fit database format
				String appendeddate = date.concat(".0");
				startDate.setText(appendeddate);
			}
			
		});
		btnDate.setFocusPainted(false);
		btnDate.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnDate.setFont(font);
		buttonpanel.add(btnDate);
		
		JButton btnClose = new JButton("CLOSE");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnClose.setFocusPainted(false);
		btnClose.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnClose.setFont(font);
		buttonpanel.add(btnClose);
		
		// Setup of panel to parse and populate window with blade information
		// Need to setup a loop to take care of this in case they add to config file
		JPanel infopanel = new JPanel();
		getContentPane().add(infopanel, BorderLayout.CENTER);
		infopanel.setLayout(new MigLayout("", "[][][][grow][]", "[][grow][][grow][][grow][][grow][grow]"));
		
		JLabel lblBladeId = new JLabel("Blade ID :");
		lblBladeId.setFont(new Font("Arial", Font.PLAIN, 22));
		infopanel.add(lblBladeId, "cell 1 1");
		
		bladeID = new JTextField();
		bladeID.setEditable(true);
		bladeID.setFont(new Font("Arial", Font.PLAIN, 22));
		infopanel.add(bladeID, "cell 3 1,growx");
		bladeID.setColumns(10);
		
		
		JLabel lblIDFormat = new JLabel("Integer Value");
		lblIDFormat.setForeground(Color.RED);
		lblIDFormat.setFont(new Font("Arial", Font.PLAIN, 16));
		infopanel.add(lblIDFormat, "cell 4 1");
		
		JLabel lblBladeSize = new JLabel("Blade Size :");
		lblBladeSize.setFont(new Font("Arial", Font.PLAIN, 22));
		infopanel.add(lblBladeSize, "cell 1 3");
		
		bladeSize = new JTextField();
		bladeSize.setEditable(true);
		bladeSize.setFont(new Font("Arial", Font.PLAIN, 22));
		infopanel.add(bladeSize, "cell 3 3,growx");
		bladeSize.setColumns(10);
		
		
		JLabel lbSizeFormat = new JLabel("Integer Value");
		lbSizeFormat.setForeground(Color.RED);
		lbSizeFormat.setFont(new Font("Arial", Font.PLAIN, 16));
		infopanel.add(lbSizeFormat, "cell 4 3");
		
		JLabel lblStartDate = new JLabel("Start Date :");
		lblStartDate.setFont(new Font("Arial", Font.PLAIN, 22));
		infopanel.add(lblStartDate, "cell 1 5");
		
		startDate = new JTextField();
		startDate.setEditable(true);
		startDate.setFont(new Font("Arial", Font.PLAIN, 22));
		infopanel.add(startDate, "cell 3 5,growx");
		startDate.setColumns(10);
		
		
		JLabel lbDateFormat = new JLabel("YYYY-MM-DD HH:MM:SS.S");
		lbDateFormat.setForeground(Color.RED);
		lbDateFormat.setFont(new Font("Arial", Font.PLAIN, 16));
		infopanel.add(lbDateFormat, "cell 4 5");
		
		JLabel lblHoursUsed = new JLabel("Hours Used :");
		lblHoursUsed.setFont(new Font("Arial", Font.PLAIN, 22));
		infopanel.add(lblHoursUsed, "cell 1 7");
		
		hoursUsed = new JTextField();
		hoursUsed.setEditable(true);
		hoursUsed.setFont(new Font("Arial", Font.PLAIN, 22));
		infopanel.add(hoursUsed, "cell 3 7,growx");
		hoursUsed.setColumns(10);
		
		
		JLabel lbHoursFormat = new JLabel("Integer Value");
		lbHoursFormat.setForeground(Color.RED);
		lbHoursFormat.setFont(new Font("Arial", Font.PLAIN, 16));
		infopanel.add(lbHoursFormat, "cell 4 7");
		
		lblNewBladeAdded = new JLabel("New Blade Added to Database");
		lblNewBladeAdded.setForeground(Color.RED);
		lblNewBladeAdded.setVisible(false);
		lblNewBladeAdded.setFont(font);
		lblNewBladeAdded.setHorizontalAlignment(SwingConstants.CENTER);
		infopanel.add(lblNewBladeAdded, "cell 3 8, growx");
		
		
	}
	
}
