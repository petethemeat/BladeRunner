package UserInterface;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class NewBlade extends JFrame implements Runnable{

	
	
	
	/**
	 * Create the frame.
	 */
	public NewBlade() {
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
