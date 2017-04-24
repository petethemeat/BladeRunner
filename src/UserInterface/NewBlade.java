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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.plaf.FontUIResource;

import Controllers.AddController;
import net.miginfocom.swing.MigLayout;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

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
	private String[] fields = {"id", "saw_id", "blade_size", "start_date", "hours_used", "end_of_use"}; 
	private String[] values = new String[6];
	private JLabel lblNewBladeAdded;
	private JTextField endofUse;
	private JTextField machineID;
	
	
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
		this.setSize((int)width/3, (int)height/2);
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
				values[1] = machineID.getText();
				values[2] = bladeSize.getText();
				values[3] = startDate.getText();
				values[4] = hoursUsed.getText();
				values[5] = endofUse.getText();
				
				try {
					AddController.run(fields, values);
				} catch (SQLException e) {
					// Show a pop up message that relays to user that blade already exists
					lblNewBladeAdded.setVisible(false);
					String message = e.getMessage();
					String errormessage = new String("<html><font size='5';font face='arial'>" + message);
					UIManager.put("OptionPane.buttonFont", new FontUIResource(font));
					JOptionPane.showMessageDialog(null, errormessage, "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				// Show a message to relay to customer that new blade was added to database
				lblNewBladeAdded.setVisible(true);
			}
			
		});
		
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
		btnAdd.setFocusPainted(false);
		btnAdd.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnAdd.setFont(font);
		buttonpanel.add(btnAdd);
		
		JButton btnClose = new JButton("CLOSE");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		// Added button for clearing text fields
		JButton btnClear = new JButton("CLEAR");
		btnClear.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// Clear all fields for new info to be added
				bladeID.setText("");
				machineID.setText("");
				bladeSize.setText("");
				startDate.setText("");
				hoursUsed.setText("");
				endofUse.setText("");
				// Clear user message
				lblNewBladeAdded.setVisible(false);
			}
		});
		btnClear.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnClear.setFont(font);
		btnClear.setHorizontalTextPosition(SwingConstants.CENTER);
		btnClear.setFocusPainted(false);
		buttonpanel.add(btnClear);
		btnClose.setFocusPainted(false);
		btnClose.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnClose.setFont(font);
		buttonpanel.add(btnClose);
		
		// Setup of panel to parse and populate window with blade information
		// Need to setup a loop to take care of this in case they add to config file
		JPanel infopanel = new JPanel();
		getContentPane().add(infopanel, BorderLayout.CENTER);
		infopanel.setLayout(new MigLayout("", "[][][][grow][]", "[][grow][][grow][][grow][][grow][][grow][][grow][grow]"));
		
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
		
		// Add label, text, and format for machine ID as requested by Tenaris
		JLabel lblMachineId = new JLabel("Machine ID :");
		lblMachineId.setFont(new Font("Arial", Font.PLAIN, 22));
		infopanel.add(lblMachineId, "cell 1 3");
		
		machineID = new JTextField();
		machineID.setEditable(true);
		machineID.setFont(new Font("Arial", Font.PLAIN, 22));
		infopanel.add(machineID, "cell 3 3,growx");
		machineID.setColumns(10);
		
		JLabel lblSawIDFormat = new JLabel("Integer Value");
		lblSawIDFormat.setForeground(Color.RED);
		lblSawIDFormat.setFont(new Font("Arial", Font.PLAIN, 16));
		infopanel.add(lblSawIDFormat, "cell 4 3");
		
		// Create label, textfield, and format for blade size
		JLabel lblBladeSize = new JLabel("Blade Size :");
		lblBladeSize.setFont(new Font("Arial", Font.PLAIN, 22));
		infopanel.add(lblBladeSize, "cell 1 5");
		
		bladeSize = new JTextField();
		bladeSize.setEditable(true);
		bladeSize.setFont(new Font("Arial", Font.PLAIN, 22));
		infopanel.add(bladeSize, "cell 3 5,growx");
		bladeSize.setColumns(10);
		
		
		JLabel lbSizeFormat = new JLabel("Integer Value");
		lbSizeFormat.setForeground(Color.RED);
		lbSizeFormat.setFont(new Font("Arial", Font.PLAIN, 16));
		infopanel.add(lbSizeFormat, "cell 4 5");
		
		// Create label, textfield, and format message for blade start date
		JLabel lblStartDate = new JLabel("Start Date :");
		lblStartDate.setFont(new Font("Arial", Font.PLAIN, 22));
		infopanel.add(lblStartDate, "cell 1 7");
		
		startDate = new JTextField();
		startDate.setEditable(true);
		startDate.setFont(new Font("Arial", Font.PLAIN, 22));
		infopanel.add(startDate, "cell 3 7,growx");
		startDate.setColumns(10);
		
		
		JLabel lbDateFormat = new JLabel("YYYY-MM-DD HH:MM:SS.S");
		lbDateFormat.setForeground(Color.RED);
		lbDateFormat.setFont(new Font("Arial", Font.PLAIN, 16));
		infopanel.add(lbDateFormat, "cell 4 7");
		
		// Create label, text, and format label for hours used on blade
		JLabel lblHoursUsed = new JLabel("Hours Used :");
		lblHoursUsed.setFont(new Font("Arial", Font.PLAIN, 22));
		infopanel.add(lblHoursUsed, "cell 1 9");
		
		hoursUsed = new JTextField();
		hoursUsed.setEditable(true);
		hoursUsed.setFont(new Font("Arial", Font.PLAIN, 22));
		infopanel.add(hoursUsed, "cell 3 9,growx");
		hoursUsed.setColumns(10);
		
		
		JLabel lbHoursFormat = new JLabel("Integer Value");
		lbHoursFormat.setForeground(Color.RED);
		lbHoursFormat.setFont(new Font("Arial", Font.PLAIN, 16));
		infopanel.add(lbHoursFormat, "cell 4 9");
		
		// Add label and text field for end of use date as requested by Tenaris
		JLabel lblEndOfUse = new JLabel("End of Use :");
		lblEndOfUse.setFont(new Font("Arial", Font.PLAIN, 22));
		infopanel.add(lblEndOfUse, "cell 1 11");
		
		endofUse = new JTextField();
		endofUse.setEditable(true);
		endofUse.setFont(new Font("Arial", Font.PLAIN, 22));
		infopanel.add(endofUse, "cell 3 11,growx");
		endofUse.setColumns(10);
		
		JLabel lblEndUseFormat = new JLabel("YYYY-MM-DD HH:MM:SS.S");
		lblEndUseFormat.setForeground(Color.RED);
		lblEndUseFormat.setFont(new Font("Arial", Font.PLAIN, 16));
		infopanel.add(lblEndUseFormat, "cell 4 11");
		
		// Set a user message to acknowledge that blade has been added
		lblNewBladeAdded = new JLabel("New Blade Added to Database");
		lblNewBladeAdded.setForeground(Color.RED);
		lblNewBladeAdded.setVisible(false);
		lblNewBladeAdded.setFont(font);
		lblNewBladeAdded.setHorizontalAlignment(SwingConstants.CENTER);
		infopanel.add(lblNewBladeAdded, "cell 3 12, growx");
		
		
	}
	
}
