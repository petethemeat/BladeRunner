package UserInterface;

import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.sql.SQLException;
import Controllers.QueryAllController;

import javax.swing.JTable;
import javax.swing.table.TableColumn;

@SuppressWarnings("serial")
public class FullDatabase extends JFrame implements Runnable{

	/**
	 * Variables
	 */
	private Font font = new Font("Arial", Font.PLAIN, 30);
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
		scrollPane.setFont(font);
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		setSize((int)GUI.screenWidth - ((int)GUI.screenWidth/6), (int)GUI.screenHeight/2);
		try {
			results = QueryAllController.run();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		table = new JTable(results, columnNames);
		table.setRowHeight(30);
		table.setFont(font);
		table.getTableHeader().setFont(font);
		TableColumn column = null;
		for (int i = 0; i <= 5; i++) {
		    column = table.getColumnModel().getColumn(i);
		    if (i == 3 || i == 5) {
		        column.setPreferredWidth(150); //4th and 6th column is bigger
		    } else {
		        column.setPreferredWidth(20);
		    }
		}
		scrollPane.setViewportView(table);
		
	}

}
