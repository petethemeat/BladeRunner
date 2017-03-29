package UserInterface;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingConstants;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JComboBox;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.border.BevelBorder;
import java.awt.Color;

@SuppressWarnings("serial")
public class QRGeneration extends JFrame implements Runnable{


	/**
	 * Create the application.
	 */
	public QRGeneration(double width, double height) {
		this.setTitle("QR Code Generation");
		this.setSize((int)width/3, (int)height/2);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JLabel lblPreview = new JLabel("PREVIEW");
		lblPreview.setHorizontalAlignment(SwingConstants.CENTER);
		lblPreview.setFont(new Font("Arial", Font.PLAIN, 24));
		getContentPane().add(lblPreview, BorderLayout.NORTH);
		
		JPanel panelInfo = new JPanel();
		getContentPane().add(panelInfo, BorderLayout.SOUTH);
		panelInfo.setLayout(new MigLayout("align 50% 50%", "[][][][grow][][grow]", "[][][][]"));
		
		JLabel lblSize = new JLabel("Size :");
		lblSize.setHorizontalTextPosition(SwingConstants.CENTER);
		lblSize.setHorizontalAlignment(SwingConstants.CENTER);
		lblSize.setFont(new Font("Arial", Font.PLAIN, 20));
		panelInfo.add(lblSize, "cell 4 0,alignx left");
		
		JComboBox<String> comboSize = new JComboBox<String>();
		// Add items to combobox
		comboSize.addItem("1\"");
		comboSize.addItem("2\"");
		comboSize.addItem("3\"");
		comboSize.addItem("4\"");
		comboSize.setPreferredSize(new Dimension(100, 29));
		comboSize.setMaximumRowCount(15);
		comboSize.setFont(new Font("Arial", Font.PLAIN, 20));
		panelInfo.add(comboSize, "flowy,cell 5 0");
		
		JLabel lblError = new JLabel("Error :");
		lblError.setHorizontalAlignment(SwingConstants.CENTER);
		lblError.setFont(new Font("Arial", Font.PLAIN, 20));
		panelInfo.add(lblError, "flowy,cell 4 1");
		
		JComboBox<String> comboError = new JComboBox<String>();
		comboError.addItem("7%");
		comboError.addItem("15%");
		comboError.addItem("25%");
		comboError.addItem("30%");
		comboError.setFont(new Font("Arial", Font.PLAIN, 20));
		comboError.setPreferredSize(new Dimension(100, 29));
		panelInfo.add(comboError, "flowy,cell 5 1");
		
		JLabel lblEncoding = new JLabel("Encoding :");
		lblEncoding.setHorizontalTextPosition(SwingConstants.LEADING);
		lblEncoding.setFont(new Font("Arial", Font.PLAIN, 20));
		panelInfo.add(lblEncoding, "flowy,cell 4 2");
		
		JComboBox<String> comboEncoding = new JComboBox<String>();
		comboEncoding.addItem("Alphanumeric");
		comboEncoding.addItem("Manchester");
		comboEncoding.setFont(new Font("Arial", Font.PLAIN, 20));
		comboEncoding.setPreferredSize(new Dimension(100, 29));
		panelInfo.add(comboEncoding, "flowy,cell 5 2");
		
		JButton btnGenerate = new JButton("GENERATE");
		btnGenerate.setFocusPainted(false);
		btnGenerate.setFont(new Font("Arial", Font.PLAIN, 24));
		btnGenerate.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnGenerate.setHorizontalTextPosition(SwingConstants.CENTER);
		panelInfo.add(btnGenerate, "cell 4 3");
		
		JButton btnClose = new JButton("CLOSE");
		btnClose.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}	
		});
		btnClose.setHorizontalTextPosition(SwingConstants.CENTER);
		btnClose.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnClose.setFocusPainted(false);
		btnClose.setFont(new Font("Arial", Font.PLAIN, 24));
		panelInfo.add(btnClose, "cell 5 3");
		
		JPanel imagepanel = new JPanel();
		imagepanel.setBackground(Color.WHITE);
		getContentPane().add(imagepanel, BorderLayout.CENTER);
		
		
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
