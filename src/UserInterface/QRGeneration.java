package UserInterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import Controllers.QRGeneratorController;
import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class QRGeneration extends JFrame implements Runnable{
	
	/*
	 * Variables
	 */
	private String home = System.getProperty("user.home");
	private Font font = new Font("Arial", Font.PLAIN, 20);
	private JComboBox<String> comboResolution;
	private JPanel imagepanel;
	private JTextField id;
	private InputStream qrpath;
	private String qrname;
	private byte[] buffer;
	private OutputStream outStreamTemp;
	private OutputStream outStreamTarget;
	private Image img;
	private JLabel qrimage;
	private File temp;
	private JLabel qrCreated;
	private File targetFile;

	/**
	 * Create the application.
	 */
	public QRGeneration(double width, double height) {
		// Setup of frame and main panel
		this.setTitle("QR Code Generation");
		this.setSize((int)width/3, (int)height/2);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		// Add title for preview pane where image will appear below
		JLabel lblPreview = new JLabel("PREVIEW");
		lblPreview.setHorizontalAlignment(SwingConstants.CENTER);
		lblPreview.setFont(new Font("Arial", Font.PLAIN, 24));
		getContentPane().add(lblPreview, BorderLayout.NORTH);
		
		// Setup a panel for size and id info text field and combobox
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
		gbl_buttonpanel.rowHeights = new int[]{10, 10, 10};
		gbl_buttonpanel.columnWeights = new double[]{Double.MIN_VALUE};
		gbl_buttonpanel.rowWeights = new double[]{0.0, 0.0, 0.0};
		buttonpanel.setLayout(gbl_buttonpanel);
		
		// Add buttons and events for all buttons
		JButton generate = new JButton("GENERATE");
		generate.addActionListener(new ActionListener() {
			// Pull info from menus and run generation code to populate image panel with QR
			@Override
			public void actionPerformed(ActionEvent e) {
				String size = (String) comboResolution.getSelectedItem();
				// Eliminate end of string to pass to controller
				Integer endindex = size.indexOf(" ");
				size = size.substring(0, endindex);
				System.out.println(size);
				Integer qrsize = Integer.parseInt(size);
				String encoding = id.getText();
				try {
					qrpath = QRGeneratorController.run(qrsize, encoding);
				} catch (IllegalArgumentException e2) {
					e2.printStackTrace();
				} catch (IOException e2) {
					e2.printStackTrace();
				}
				try {
					buffer = new byte[qrpath.available()];
					qrpath.read(buffer);
					// Write image buffer to temporary file to add to preview pane
					temp = File.createTempFile("temp", ".png");
					outStreamTemp = new FileOutputStream(temp);
					outStreamTemp.write(buffer);
		
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
				try {
					img = ImageIO.read(temp);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				// Add image to imagepanel preview label
				Image scaledimg = img.getScaledInstance((int)GUI.screenWidth/6, (int)GUI.screenHeight/6,  java.awt.Image.SCALE_SMOOTH);
				qrimage.setIcon(new ImageIcon(scaledimg));
				// Acknowledge file created for user
				qrCreated.setText("<html>QR Image Generated<br><&nbsp Please Save to File</html>");
				qrCreated.setVisible(true);
			
				// Delete temp file
				temp.delete();
				
				// Close streams to release resources 
				try {
					qrpath.close();
					outStreamTemp.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
			}
			
		});
		generate.setFont(font);
		generate.setFocusPainted(false);
		generate.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		GridBagConstraints gbc_generate = new GridBagConstraints();
		gbc_generate.insets = new Insets(0, 0, 10, 0);
		gbc_generate.gridx = 0;
		gbc_generate.gridy = 0;
		buttonpanel.add(generate, gbc_generate);
		
		
		JButton cancel = new JButton("EXIT");
		cancel.addActionListener(new ActionListener() {
			// Close window upon selection of close button
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
			
		});
		
		JButton save = new JButton("SAVE");
		save.addActionListener(new ActionListener() {
			// Close window upon selection of close button
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser saveBrowser = new JFileChooser(home + "/Desktop");
				// Set Chooser size in relation to computer screen
				saveBrowser.setPreferredSize(new Dimension((int)GUI.screenWidth/2, (int)GUI.screenHeight/2));
				// Set font for jfilechooser so user can see it
				setFileChooserFont(saveBrowser.getComponents());
				
			    // Demonstrate "Save" dialog:
			    int rVal = saveBrowser.showSaveDialog(QRGeneration.this);
			    if (rVal == JFileChooser.APPROVE_OPTION) {
			    	qrname = saveBrowser.getSelectedFile().getName();
			    	if(qrname.endsWith(".png")){
			    		targetFile = new File(saveBrowser.getCurrentDirectory().toString() + "\\" + qrname);
			    	}
			    	else { 
			    		targetFile = new File(saveBrowser.getCurrentDirectory().toString() + "\\" + qrname + ".png");
			    	}
					try {
						outStreamTarget = new FileOutputStream(targetFile);
						outStreamTarget.write(buffer);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					// Display message to user acknowledging the save
					qrCreated.setText("<html><br>File Saved</html>");
					
					// Close output stream after completion
				    try {
				    	outStreamTarget.close();
				    } catch (IOException e) {
						e.printStackTrace();
				    }
				      
			     }
			     // If cancel is chosen hide the filechooser window
			     if (rVal == JFileChooser.CANCEL_OPTION) {
			    	 saveBrowser.setVisible(false);
			     }
			}
			
		});
		save.setFocusPainted(false);
		save.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		save.setFont(font);
		GridBagConstraints gbc_save = new GridBagConstraints();
		gbc_save.insets = new Insets(0, 0, 10, 0);
		gbc_save.gridx = 0;
		gbc_save.gridy = 1;
		buttonpanel.add(save, gbc_save);
		cancel.setFont(font);
		cancel.setFocusPainted(false);
		cancel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		GridBagConstraints gbc_cancel = new GridBagConstraints();
		gbc_cancel.insets = new Insets(0, 0, 10, 0);
		gbc_cancel.gridx = 0;
		gbc_cancel.gridy = 2;
		buttonpanel.add(cancel, gbc_cancel);
		
		
		// Setup panel for all dropdown selections
		JPanel dropdownpanel = new JPanel();
		infopanel.add(dropdownpanel, BorderLayout.CENTER);
		dropdownpanel.setLayout(new MigLayout("align 50% 50%", "[][][][][]", "[]"));
		
		JLabel lblSize = new JLabel("Size :");
		lblSize.setHorizontalTextPosition(SwingConstants.CENTER);
		lblSize.setHorizontalAlignment(SwingConstants.CENTER);
		lblSize.setFont(font);
		dropdownpanel.add(lblSize, "cell 4 0");
		
		
		// Add items to combobox
		comboResolution = new JComboBox<String>();
		comboResolution.addItem("100 x 100");
		comboResolution.addItem("200 x 200");
		comboResolution.addItem("300 x 300");
		comboResolution.addItem("400 x 400");
		comboResolution.addItem("500 x 500");
		comboResolution.setPreferredSize(new Dimension(150, 30));
		comboResolution.setFont(font);
		dropdownpanel.add(comboResolution, "cell 5 0");
		
		
		// Add user area for text to generate as a qr code
		JLabel lblCode = new JLabel("Text :");
		lblCode.setHorizontalTextPosition(SwingConstants.LEADING);
		lblCode.setFont(font);
		dropdownpanel.add(lblCode, "cell 4 2");
		
		id = new JTextField(null);
		id.setFont(font);
		dropdownpanel.add(id, "cell 5 2");
		id.setPreferredSize(new Dimension(150,30));
		id.setEditable(true);
		
		
		imagepanel = new JPanel();
		//imagepanel.setBackground(Color.WHITE);
		getContentPane().add(imagepanel, BorderLayout.CENTER);
		imagepanel.setLayout(new BorderLayout(0, 0));
		
		// Create label for image display later
		qrimage = new JLabel("");
		qrimage.setHorizontalTextPosition(SwingConstants.CENTER);
		qrimage.setHorizontalAlignment(SwingConstants.CENTER);
		imagepanel.add(qrimage, BorderLayout.CENTER);
		
		// Create label for user messages later
		qrCreated = new JLabel();
		qrCreated.setHorizontalTextPosition(SwingConstants.CENTER);
		qrCreated.setHorizontalAlignment(SwingConstants.CENTER);
		qrCreated.setFont(new Font("Arial", Font.PLAIN, 30));
		qrCreated.setForeground(Color.RED);
		qrCreated.setVisible(false);
		imagepanel.add(qrCreated, BorderLayout.SOUTH);
		
	}
	
	/**
	 * Method will iterate through all components of jfilechooser to set proper font
	 * @param comp
	 */
	private void setFileChooserFont(Component[] comp)
	{  
	  for(int x = 0; x < comp.length; x++)  
	  {  
	    if(comp[x] instanceof Container) {
	    	setFileChooserFont(((Container)comp[x]).getComponents());  
	    }
	    try{
	    	comp[x].setFont(font);
	    }  
	    catch(Exception e){}//do nothing  
	  }  
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
