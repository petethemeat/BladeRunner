package UserInterface;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class QRGeneration extends JFrame implements Runnable{


	/**
	 * Create the application.
	 */
	public QRGeneration(double width, double height) {
		this.setTitle("QR Code Generation");
		this.setSize((int)width/3, (int)height/2);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		
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
