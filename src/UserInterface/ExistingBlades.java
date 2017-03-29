package UserInterface;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.JButton;
import java.awt.Dimension;
import javax.swing.border.BevelBorder;

import Controllers.DeleteController;

import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JTextField;
import net.miginfocom.swing.MigLayout;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.Color;

@SuppressWarnings("serial")
public class ExistingBlades extends JFrame implements Runnable{
	private JTextField bladeID;
	private JTextField bladeSize;
	private JTextField startDate;
	private JTextField hoursUsed;

		
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
		
		this.setSize((int)width/3, (int)height/4);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		// Setup of panel to accept and layout buttons in bottom of new window
		JPanel buttonpanel = new JPanel();
		buttonpanel.setPreferredSize(new Dimension(10, 50));
		getContentPane().add(buttonpanel, BorderLayout.SOUTH);
		buttonpanel.setLayout(new GridLayout(1, 0, 0, 0));
		
		JButton btnEdit = new JButton("EDIT");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(bladeID.isEditable() && bladeSize.isEditable() && startDate.isEditable() && hoursUsed.isEditable()){
					bladeID.setEditable(false);
					bladeSize.setEditable(false);
					startDate.setEditable(false);
					hoursUsed.setEditable(false);
				}
				else{
					bladeID.setEditable(true);
					bladeSize.setEditable(true);
					startDate.setEditable(true);
					hoursUsed.setEditable(true);
				}
			}
		});
		btnEdit.setFocusPainted(false);
		btnEdit.setFont(new Font("Arial", Font.PLAIN, 19));
		btnEdit.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		buttonpanel.add(btnEdit);
		
		JButton btnUpdate = new JButton("UPDATE");
		btnUpdate.setFocusPainted(false);
		btnUpdate.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnUpdate.setFont(new Font("Arial", Font.PLAIN, 19));
		buttonpanel.add(btnUpdate);
		
		JButton btnDelete = new JButton("DELETE");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Call on delete controller to delete id from database
				try {
					DeleteController.run(bladeID.getText());
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnDelete.setFocusPainted(false);
		btnDelete.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnDelete.setFont(new Font("Arial", Font.PLAIN, 19));
		buttonpanel.add(btnDelete);
		
		JButton btnClose = new JButton("CLOSE");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnClose.setFocusPainted(false);
		btnClose.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnClose.setFont(new Font("Arial", Font.PLAIN, 19));
		buttonpanel.add(btnClose);
		
		// Setup of panel to parse and populate window with blade information
		// Need to setup a loop to take care of this in case they add to config file
		JPanel infopanel = new JPanel();
		getContentPane().add(infopanel, BorderLayout.CENTER);
		infopanel.setLayout(new MigLayout("", "[][][][grow][]", "[][][][][][][][]"));
		
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
		
		JLabel lblBladeSize = new JLabel("Blade Size :");
		lblBladeSize.setFont(new Font("Arial", Font.PLAIN, 22));
		infopanel.add(lblBladeSize, "cell 1 3");
		
		bladeSize = new JTextField();
		bladeSize.setEditable(false);
		bladeSize.setFont(new Font("Arial", Font.PLAIN, 22));
		infopanel.add(bladeSize, "cell 3 3,growx");
		bladeSize.setColumns(10);
		bladeSize.setText(results[1]);
		
		JLabel lbSizeFormat = new JLabel("Integer Value");
		lbSizeFormat.setForeground(Color.RED);
		lbSizeFormat.setFont(new Font("Arial", Font.PLAIN, 16));
		infopanel.add(lbSizeFormat, "cell 4 3");
		
		JLabel lblStartDate = new JLabel("Start Date :");
		lblStartDate.setFont(new Font("Arial", Font.PLAIN, 22));
		infopanel.add(lblStartDate, "cell 1 5");
		
		startDate = new JTextField();
		startDate.setEditable(false);
		startDate.setFont(new Font("Arial", Font.PLAIN, 22));
		infopanel.add(startDate, "cell 3 5,growx");
		startDate.setColumns(10);
		startDate.setText(results[2]);
		
		JLabel lbDateFormat = new JLabel("YYYY-MM-DD HH:MM:SS.S");
		lbDateFormat.setForeground(Color.RED);
		lbDateFormat.setFont(new Font("Arial", Font.PLAIN, 16));
		infopanel.add(lbDateFormat, "cell 4 5");
		
		JLabel lblHoursUsed = new JLabel("Hours Used :");
		lblHoursUsed.setFont(new Font("Arial", Font.PLAIN, 22));
		infopanel.add(lblHoursUsed, "cell 1 7");
		
		hoursUsed = new JTextField();
		hoursUsed.setEditable(false);
		hoursUsed.setFont(new Font("Arial", Font.PLAIN, 22));
		infopanel.add(hoursUsed, "cell 3 7,growx");
		hoursUsed.setColumns(10);
		hoursUsed.setText(results[3]);
		
		JLabel lbHoursFormat = new JLabel("Integer Value");
		lbHoursFormat.setForeground(Color.RED);
		lbHoursFormat.setFont(new Font("Arial", Font.PLAIN, 16));
		infopanel.add(lbHoursFormat, "cell 4 7");
		
		
	}


}
