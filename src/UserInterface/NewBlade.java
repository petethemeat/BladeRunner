package UserInterface;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class NewBlade extends JFrame implements Runnable{

	
	
	
	/**
	 * Create the frame.
	 */
	public NewBlade(double width, double height) {
		this.setTitle("New Blade");
		this.setSize((int)width/4, (int)height/4);
		setResizable(false);
		setTitle("New Blade");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		
	}

	@Override
	public void run() {
		try {
			this.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
