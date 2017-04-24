package UserInterface;

import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;

@SuppressWarnings("serial")
public class FullDatabase extends JFrame implements Runnable{

	/**
	 * Variables
	 */
	private Font font = new Font("Arial", Font.PLAIN, 20);
	
	
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
		setSize((int)GUI.screenWidth/3, (int)GUI.screenHeight/2);
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
	}

}
