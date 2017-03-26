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
		setBounds(100, 100, 750, 600);
	}

	@Override
	public void run() {
		try {
			NewBlade frame = new NewBlade();
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
