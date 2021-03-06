package UserInterface;

import javax.swing.JFrame;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.JButton;
import java.awt.Dimension;
import javax.swing.border.BevelBorder;
import javax.swing.plaf.FontUIResource;

import Controllers.DeleteController;
import Controllers.UpdateController;

import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import net.miginfocom.swing.MigLayout;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.Color;


@SuppressWarnings("serial")
public class ExistingBlades extends JFrame implements Runnable{
	
	/*
	 * Variables
	 */
	private Font font = new Font("Arial", Font.PLAIN, 20);
	private JPanel infopanel;
	private JTextField bladeID;
	private JTextField bladeSize;
	private JTextField startDate;
	private JTextField hoursUsed;
	private JLabel lblEdited;
	private String id;
	private String[] fields = {"saw_id", "blade_size", "start_date", "hours_used", "end_of_use"}; 
	private String[] values = new String[5];
	private JTextField endofUse;
	private JTextField machineID;

		
	public void run() {
		try {
			this.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * Create the application.
	 */
	public ExistingBlades(double width, double height, String[] results) {
		this.setTitle("Blade Info");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		this.setSize((int)width/3, (int)height/2);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		// Initialize bladeID string to result[0]
		id = results[0];
		
		// Setup of panel to accept and layout buttons in bottom of new window
		JPanel buttonpanel = new JPanel();
		buttonpanel.setPreferredSize(new Dimension(10, 50));
		getContentPane().add(buttonpanel, BorderLayout.SOUTH);
		buttonpanel.setLayout(new GridLayout(1, 0, 0, 0));
		
		JButton btnEdit = new JButton("EDIT");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(machineID.isEditable() && bladeSize.isEditable() && startDate.isEditable() && hoursUsed.isEditable() 
					&& endofUse.isEditable()){
					machineID.setEditable(false);
					bladeSize.setEditable(false);
					startDate.setEditable(false);
					hoursUsed.setEditable(false);
					endofUse.setEditable(false);
				}
				else{
					// Make updated message dissapear since they are choosing to re-edit
					lblEdited.setVisible(false);
					// Set fields to editable for customer to change values
					machineID.setEditable(true);
					bladeSize.setEditable(true);
					startDate.setEditable(true);
					hoursUsed.setEditable(true);
					endofUse.setEditable(true);
				}
			}
		});
		btnEdit.setFocusPainted(false);
		btnEdit.setFont(font);
		btnEdit.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		buttonpanel.add(btnEdit);
		
		JButton btnUpdate = new JButton("UPDATE");
		btnUpdate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				values[0] = bladeSize.getText();
				values[1] = machineID.getText();
				values[2] = startDate.getText();
				values[3] = hoursUsed.getText();
				values[4] = endofUse.getText();
				try {
					UpdateController.run(id, values, fields);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				lblEdited.setVisible(true);
				// Set all fields to uneditable after updating blade
				bladeSize.setEditable(false);
				machineID.setEditable(false);
				startDate.setEditable(false);
				hoursUsed.setEditable(false);
				endofUse.setEditable(false);
			}
			
		});
		btnUpdate.setFocusPainted(false);
		btnUpdate.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnUpdate.setFont(font);
		buttonpanel.add(btnUpdate);
		
		JButton btnDelete = new JButton("DELETE");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Make a popup window to ask user for reassurance in deleting the blade
				// 0 = Yes, 1 = No
				String question = new String("<html><font size='5';font face='arial'>Are you sure?");
				UIManager.put("OptionPane.buttonFont", new FontUIResource(font));
				int answer = JOptionPane.showConfirmDialog(null, question, "Confirmation", JOptionPane.YES_NO_OPTION);
				
				
				if(answer == 0){
					// Call on delete controller to delete id from database
					try {
						DeleteController.run(bladeID.getText());
					} catch (SQLException e1) {
						
						lblEdited.setText("Blade ID #" + id + " has already been deleted");
						lblEdited.setVisible(true);
						return;
						
					}
					lblEdited.setText("Blade ID #" + id + " has been deleted");
					lblEdited.setVisible(true);
				}
				else{} // Do nothing		
			}
		});
		btnDelete.setFocusPainted(false);
		btnDelete.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnDelete.setFont(font);
		buttonpanel.add(btnDelete);
		
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
		infopanel = new JPanel();
		getContentPane().add(infopanel, BorderLayout.CENTER);
		infopanel.setLayout(new MigLayout("", "[][][][grow][]", "[][grow][][grow][][grow][][grow][][grow][][grow][grow]"));
		
		// Create label, textfield, and format message for blade id
		JLabel lblBladeId = new JLabel("Blade ID :");
		lblBladeId.setFont(new Font("Arial", Font.PLAIN, 22));
		infopanel.add(lblBladeId, "cell 1 1");
		
		bladeID = new JTextField();
		bladeID.setEditable(false);
		bladeID.setFont(new Font("Arial", Font.PLAIN, 22));
		infopanel.add(bladeID, "cell 3 1,growx");
		bladeID.setColumns(10);
		bladeID.setText(results[0]);
		
		JLabel lblIDFormat = new JLabel("Integer Value");
		lblIDFormat.setForeground(Color.RED);
		lblIDFormat.setFont(new Font("Arial", Font.PLAIN, 16));
		infopanel.add(lblIDFormat, "cell 4 1");
		
		// Add label, text, and format for machine ID as requested by Tenaris
		JLabel lblMachineId = new JLabel("Machine ID :");
		lblMachineId.setFont(new Font("Arial", Font.PLAIN, 22));
		infopanel.add(lblMachineId, "cell 1 3");
		
		machineID = new JTextField();
		machineID.setFont(new Font("Arial", Font.PLAIN, 22));
		machineID.setEditable(false);
		infopanel.add(machineID, "cell 3 3,growx");
		machineID.setColumns(10);
		machineID.setText(results[1]);
		
		JLabel lblSawIdFormat = new JLabel("Integer Value");
		lblSawIdFormat.setForeground(Color.RED);
		lblSawIdFormat.setFont(new Font("Arial", Font.PLAIN, 16));
		infopanel.add(lblSawIdFormat, "cell 4 3");
		
		// Create label, textfield, and format message for blade size
		JLabel lblBladeSize = new JLabel("Blade Size :");
		lblBladeSize.setFont(new Font("Arial", Font.PLAIN, 22));
		infopanel.add(lblBladeSize, "cell 1 5");
		
		bladeSize = new JTextField();
		bladeSize.setEditable(false);
		bladeSize.setFont(new Font("Arial", Font.PLAIN, 22));
		infopanel.add(bladeSize, "cell 3 5,growx");
		bladeSize.setColumns(10);
		bladeSize.setText(results[2]);
		
		JLabel lbSizeFormat = new JLabel("Integer Value");
		lbSizeFormat.setForeground(Color.RED);
		lbSizeFormat.setFont(new Font("Arial", Font.PLAIN, 16));
		infopanel.add(lbSizeFormat, "flowx,cell 4 5");
		
		// Create label, textfield, and format message for blade start date
		JLabel lblStartDate = new JLabel("Start Date :");
		lblStartDate.setFont(new Font("Arial", Font.PLAIN, 22));
		infopanel.add(lblStartDate, "cell 1 7");
		
		startDate = new JTextField();
		startDate.setEditable(false);
		startDate.setFont(new Font("Arial", Font.PLAIN, 22));
		infopanel.add(startDate, "cell 3 7,growx");
		startDate.setColumns(10);
		startDate.setText(results[3]);
		
		JLabel lbDateFormat = new JLabel("YYYY-MM-DD HH:MM:SS.S");
		lbDateFormat.setForeground(Color.RED);
		lbDateFormat.setFont(new Font("Arial", Font.PLAIN, 16));
		infopanel.add(lbDateFormat, "cell 4 7");
		
		// Create label, textfield, and format message for blade hours used
		JLabel lblHoursUsed = new JLabel("Hours Used :");
		lblHoursUsed.setFont(new Font("Arial", Font.PLAIN, 22));
		infopanel.add(lblHoursUsed, "cell 1 9");
		
		hoursUsed = new JTextField();
		hoursUsed.setEditable(false);
		hoursUsed.setFont(new Font("Arial", Font.PLAIN, 22));
		infopanel.add(hoursUsed, "cell 3 9,growx");
		hoursUsed.setColumns(10);
		hoursUsed.setText(results[4]);
		
		JLabel lbHoursFormat = new JLabel("Integer Value");
		lbHoursFormat.setForeground(Color.RED);
		lbHoursFormat.setFont(new Font("Arial", Font.PLAIN, 16));
		infopanel.add(lbHoursFormat, "cell 4 9");
		
		// Add label and text field for end of use date as requested by Tenaris
		JLabel lblEndOfUse = new JLabel("End of Use :");
		lblEndOfUse.setFont(new Font("Arial", Font.PLAIN, 22));
		infopanel.add(lblEndOfUse, "cell 1 11");
		
		endofUse = new JTextField();
		endofUse.setEditable(false);
		endofUse.setFont(new Font("Arial", Font.PLAIN, 22));
		infopanel.add(endofUse, "cell 3 11,growx");
		endofUse.setColumns(10);
		endofUse.setText(results[5]);
		
		JLabel lblEndUseFormat = new JLabel("YYYY-MM-DD HH:MM:SS.S");
		lblEndUseFormat.setForeground(Color.RED);
		lblEndUseFormat.setFont(new Font("Arial", Font.PLAIN, 16));
		infopanel.add(lblEndUseFormat, "cell 4 11");
		
		// Set a user message to acknowledge that blade has been updated
		// Create label for message relaying blade has been updated when button pressed
		lblEdited = new JLabel("Blade ID #" + id + " has been updated");
		lblEdited.setForeground(Color.RED);
		lblEdited.setVisible(false);
		lblEdited.setFont(font);
		lblEdited.setHorizontalAlignment(SwingConstants.CENTER);
		infopanel.add(lblEdited, "cell 3 12,growx");
	
	}
	
}
