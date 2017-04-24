package UserInterface;

import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import javax.swing.border.BevelBorder;

import Controllers.QueryAllController;

import javax.swing.JTextArea;
import net.miginfocom.swing.MigLayout;
import javax.swing.JTable;

@SuppressWarnings("serial")
public class FullDatabase extends JFrame implements Runnable{

	/**
	 * Variables
	 */
	private Font font = new Font("Arial", Font.PLAIN, 20);
	private String[][] results;
	private JTable table;
	private String[] columnNames = {"Blade ID", "Machine ID", "Blade Size", "Start Date", "Hours Used", "End Date"};
	
	
	@Override
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
	public FullDatabase() {
		setTitle("Database");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setFont(new Font("Arial", Font.PLAIN, 20));
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		setSize((int)GUI.screenWidth/3, (int)GUI.screenHeight/2);
		try {
			results = QueryAllController.run();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		table = new JTable(results, columnNames);
		table.setFont(new Font("Arial", Font.PLAIN, 20));
		scrollPane.setViewportView(table);
		
	}

}
