package UserInterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class QRGeneration extends JFrame implements Runnable{
	JComboBox<String> comboSize;
	JComboBox<String> comboError;
	JComboBox<String> comboEncoding;
	private JTextField id;

	/**
	 * Create the application.
	 */
	public QRGeneration(double width, double height) {
		this.setTitle("QR Code Generation");
		this.setSize((int)width/3, (int)height/2);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JLabel lblPreview = new JLabel("PREVIEW");
		lblPreview.setHorizontalAlignment(SwingConstants.CENTER);
		lblPreview.setFont(new Font("Arial", Font.PLAIN, 24));
		getContentPane().add(lblPreview, BorderLayout.NORTH);
		
		JPanel infopanel = new JPanel();
		infopanel.setPreferredSize(new Dimension(10, 200));
		getContentPane().add(infopanel, BorderLayout.SOUTH);
		infopanel.setLayout(new BorderLayout(0, 0));
		
		// Setup panel for placing buttons
		JPanel buttonpanel = new JPanel();
		buttonpanel.setSize(new Dimension(100, 100));
		buttonpanel.setMinimumSize(new Dimension(10, 200));
		infopanel.add(buttonpanel, BorderLayout.SOUTH);
		GridBagLayout gbl_buttonpanel = new GridBagLayout();
		gbl_buttonpanel.columnWidths = new int[]{0};
		gbl_buttonpanel.rowHeights = new int[]{0, 41};
		gbl_buttonpanel.columnWeights = new double[]{Double.MIN_VALUE};
		gbl_buttonpanel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		buttonpanel.setLayout(gbl_buttonpanel);
		
		// Add buttons and events for all buttons
		JButton generate = new JButton("GENERATE");
		generate.addActionListener(new ActionListener() {
			// Pull info from menus and run generation code to populate image panel with QR
			@Override
			public void actionPerformed(ActionEvent e) {
				String size = (String) comboSize.getSelectedItem();
				size = size.replace("\"", "");
				Integer qrsize = Integer.parseInt(size);
				String error = (String) comboError.getSelectedItem();
				error = error.replace("%", "");
				Integer qrerror = Integer.parseInt(error);
				String encoding = id.getText();
				
			}
			
		});
		generate.setFont(new Font("Arial", Font.PLAIN, 26));
		generate.setFocusPainted(false);
		generate.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		GridBagConstraints gbc_generate = new GridBagConstraints();
		gbc_generate.insets = new Insets(0, 0, 5, 5);
		gbc_generate.gridx = 0;
		gbc_generate.gridy = 0;
		buttonpanel.add(generate, gbc_generate);
		
		
		JButton close = new JButton("CLOSE");
		close.addActionListener(new ActionListener() {
			// Close window upon selection of close button
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
			
		});
		close.setFont(new Font("Arial", Font.PLAIN, 26));
		close.setFocusPainted(false);
		close.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		GridBagConstraints gbc_close = new GridBagConstraints();
		gbc_close.insets = new Insets(0, 0, 0, 5);
		gbc_close.gridx = 0;
		gbc_close.gridy = 1;
		buttonpanel.add(close, gbc_close);
		
		
		// Setup panel for all dropdown selections
		JPanel dropdownpanel = new JPanel();
		infopanel.add(dropdownpanel, BorderLayout.CENTER);
		dropdownpanel.setLayout(new MigLayout("align 50% 50%", "[][][][][]", "[]"));
		
		JLabel lblSize = new JLabel("Size :");
		lblSize.setHorizontalTextPosition(SwingConstants.CENTER);
		lblSize.setHorizontalAlignment(SwingConstants.CENTER);
		lblSize.setFont(new Font("Arial", Font.PLAIN, 20));
		dropdownpanel.add(lblSize, "cell 4 0");
		
		
		// Add items to combobox
//		String[] sizes = {"1\"", "2\"", "3\"", "4\""};
//		comboSize = new JComboBox<String>(sizes);
		comboSize = new JComboBox<String>();
		comboSize.addItem("1\"");
		comboSize.addItem("2\"");
		comboSize.addItem("3\"");
		comboSize.addItem("4\"");
		comboSize.setPreferredSize(new Dimension(100, 29));
		comboSize.setFont(new Font("Arial", Font.PLAIN, 20));
		dropdownpanel.add(comboSize, "cell 5 0");
		
		JLabel lblError = new JLabel("Error :");
		lblError.setHorizontalAlignment(SwingConstants.CENTER);
		lblError.setFont(new Font("Arial", Font.PLAIN, 20));
		dropdownpanel.add(lblError, "cell 4 1");
		
//		String[] errors = {"5%", "10%", "15%", "30%"};
//		comboError = new JComboBox<String>(errors);
		comboError = new JComboBox<String>();
		comboError.addItem("5%");
		comboError.addItem("10%");
		comboError.addItem("15%");
		comboError.addItem("30%");
		comboError.setFont(new Font("Arial", Font.PLAIN, 20));
		comboError.setPreferredSize(new Dimension(100, 29));
		dropdownpanel.add(comboError, "cell 5 1");
		
//		JLabel lblEncoding = new JLabel("Encoding :");
//		lblEncoding.setHorizontalTextPosition(SwingConstants.LEADING);
//		lblEncoding.setFont(new Font("Arial", Font.PLAIN, 20));
//		dropdownpanel.add(lblEncoding, "cell 4 2");
//		
//		String[] encodings = {"Alphanumeric", "Binary"};
//		comboEncoding = new JComboBox<String>(encodings);
//		comboEncoding.setFont(new Font("Arial", Font.PLAIN, 20));
//		comboEncoding.setPreferredSize(new Dimension(100, 29));
//		dropdownpanel.add(comboEncoding, "cell 5 2");
		
		// Add user area for text to generate as a qr code
		JLabel lblCode = new JLabel("Text :");
		lblCode.setHorizontalTextPosition(SwingConstants.LEADING);
		lblCode.setFont(new Font("Arial", Font.PLAIN, 20));
		dropdownpanel.add(lblCode, "cell 4 2");
		
		id = new JTextField(null);
		id.setFont(new Font("Arial", Font.PLAIN, 20));
		dropdownpanel.add(id, "cell 5 2");
		id.setColumns(10);
		id.setEditable(true);
		
		
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
