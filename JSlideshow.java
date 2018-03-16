/*
 * Name: 		Schenck, Eric
 * Project: 	# Extra 1
 * Due:		 	March 10, 2018
 * Course:		cs24501-w18
 * 
 * Description:	
 * 			implement a class JSlideshow that allows user to play and pause an image slideshow.
 * 	The program will have a file menu to load the image file list 
 */




import java.awt.event.*;
import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.*;
import java.util.Scanner;
import java.net.URL;
import java.awt.image.BufferedImage;

@SuppressWarnings("serial")
public class JSlideshow extends JFrame implements ActionListener{

	
	Timer slideShowTmr;
	Timer tmr;
	
	ImageIcon[] image;				// using to store images as they are loading into program
	String[] imageNames;			// using to store image names
	
	String[] splitString;
	String inputString;
	
	int fileCounter = 0;
	int value;
	int imageIterator;
	
	JDialog aboutDialog;
	JDialog progressDialog;
	JProgressBar loading;
	JLabel imgLabel;
	JMenuBar menuBar;
	JMenu file;
	JMenuItem open;
	JMenuItem exit;
	JMenu help;
	JMenuItem about;
	JButton pause;
	JButton play;
	JButton ok;
	JPanel buttnPanel;
	
	JFileChooser fileChooser;
	
	JSlideshow(){
		setSize(800,600);					// setting intitial frame size
		setLayout(new BorderLayout());
		setTitle("JSlideshow");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		buildJMenu();
		
		//imgLabel = new JLabel();
		
		
		
		pause = new JButton("Pause");
		play = new JButton("Play");
		
		buttnPanel = new JPanel(new GridLayout(1,2));
		JPanel pausePanel = new JPanel(new BorderLayout());
		JPanel playPanel = new JPanel(new BorderLayout());
		
		pausePanel.add(pause, BorderLayout.CENTER);
		pausePanel.add(new JLabel("  ") , BorderLayout.PAGE_START);
		pausePanel.add(new JLabel("  ") , BorderLayout.LINE_START);
		pausePanel.add(new JLabel("  ") , BorderLayout.PAGE_END);
		pausePanel.add(new JLabel("  ") , BorderLayout.LINE_END);
		
		playPanel.add(play, BorderLayout.CENTER);
		playPanel.add(new JLabel("  ") , BorderLayout.PAGE_START);
		playPanel.add(new JLabel("  ") , BorderLayout.LINE_START);
		playPanel.add(new JLabel("  ") , BorderLayout.PAGE_END);
		playPanel.add(new JLabel("  ") , BorderLayout.LINE_END);
		
		buttnPanel.add(playPanel);
		buttnPanel.add(pausePanel);
		
		imgLabel = new JLabel();
		imgLabel.setBounds(40,30,800, 600);
		
		add(imgLabel, BorderLayout.CENTER);
		add(buttnPanel, BorderLayout.PAGE_END);
		
		fileChooser = new JFileChooser();
				
		image = new ImageIcon[20];							// picking a random size to set array too
		imageNames = new String[20];
		
		loading = new JProgressBar(0, 100);					// to be used for loading first image
		loading.setValue(0);
		loading.setStringPainted(true);
		
		progressDialog = new JDialog(this, "Loading...", false);
		
		play.addActionListener(this);
		pause.addActionListener(this);
		
		play.setEnabled(false);
		pause.setEnabled(false); 							// disabled until user loads a file into program
		
		buildAboutDialog();
			
		imageIterator = 1;											// image 1 will already be loading into frame
		slideShowTmr = new Timer(3000, ae -> {
			
			imgLabel.setIcon(image[imageIterator]);
			setTitle(imageNames[imageIterator]);
			
			++imageIterator;
			
			if(imageIterator == fileCounter){
				imageIterator = 0;
			}
			
			
		});
		
		
		
		
		
		setVisible(true);
	}
	
	public void buildJMenu(){
		file = new JMenu("File");
		open = new JMenuItem("Open");
		exit = new JMenuItem("Exit");
		file.add(open);
		file.addSeparator();
		file.add(exit);
		help = new JMenu("Help");
		about = new JMenuItem("About JSlideshow");
		help.add(about);
		menuBar = new JMenuBar();
		menuBar.add(file);
		menuBar.add(help);
		
		open.addActionListener(this);
		exit.addActionListener(this);
		about.addActionListener(this);
				
		open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C , ActionEvent.CTRL_MASK));
		exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X , ActionEvent.CTRL_MASK));
		file.setMnemonic(KeyEvent.VK_F);
		open.setMnemonic(KeyEvent.VK_O);
		exit.setMnemonic(KeyEvent.VK_X);
		
		
		setJMenuBar(menuBar);											// setting menubar to frame
	}
	
	public void loadFile(String filePath) throws IOException{
		
		
		progressBar();
		
		File file = new File(filePath);
		Scanner fileScan = new Scanner(file);
		
		int i = 0;
		
		while(fileScan.hasNextLine()){
			
			inputString = fileScan.nextLine();
			splitString = inputString.split("\\s+");
			
			imageNames[i] = splitString[0];
			
			//System.out.println(splitString[0]);
			//System.out.println(splitString[1]);
			//System.out.println(splitString[2]);
			
			
			
			
			
			switch(splitString[2]){									// last item in image file line is spec which
																	// i guess means specification like is it a URL or .PNG
			case "URL":
				URL url = new URL(splitString[1]);
				BufferedImage buffer = ImageIO.read(url);			
				image[i] = new ImageIcon(buffer);
				
				break;
			case "file://local":
				image[i] = new ImageIcon(splitString[1]);
				break;
			case "http://net":
				break;
				
			}
			++i;
			++fileCounter;
		}
		
		
		
		
		fileScan.close();
	}
	
	
	public void progressBar(){
		
		progressDialog.setSize(200, 100);
		progressDialog.setLocationRelativeTo(null);
		
		progressDialog.add(loading);
		
		value = 0;
		
		
		tmr = new Timer(1000, ae -> {
			
			value += 50;
			loading.setValue(value);
			
			if(value == 100 ){
				tmr.stop();
				progressDialog.setVisible(false);
			}
			
			
		});
		tmr.start();
		progressDialog.setVisible(true);
		
		
		
		
		
		
	}
	
	private void buildAboutDialog(){
		aboutDialog = new JDialog(this, "About JSlideshow", true);					// making aboutDialog 
		aboutDialog.setSize(200,100);									// setting size of dialog window
		aboutDialog.setLayout(new BorderLayout()); 						// set layout 
		aboutDialog.setLocationRelativeTo(this);
		
		
		ok = new JButton("Ok");										// ok button on dialog
		JLabel aboutLabel = new JLabel("(c) Eric Schenck");					
		
		aboutLabel.setHorizontalAlignment(SwingConstants.CENTER);

		JPanel topPan = new JPanel();									// default is FlowLayout()
		JPanel lowPan = new JPanel(new GridLayout(1,3));
		JPanel buttonPan = new JPanel(new BorderLayout());
		
		topPan.setLayout(new FlowLayout());
		topPan.add(aboutLabel);											// adding label 
		aboutDialog.add(topPan, BorderLayout.CENTER);					// addin pane to JDialog
		
		lowPan.add(new JLabel(""));
		lowPan.add(new JLabel(""));										// used to fill up blank spots to place button better
		
		buttonPan.add(ok, BorderLayout.CENTER);
		buttonPan.add(new JLabel(" "), BorderLayout.PAGE_END);
		buttonPan.add(new JLabel("   "), BorderLayout.LINE_END);			// used for formatting of the button
		
		lowPan.add(buttonPan);												// adding okay method
		
		aboutDialog.add(topPan, BorderLayout.CENTER);
		aboutDialog.add(lowPan, BorderLayout.PAGE_END);					// adding panels to dialog window
		
		ok.addActionListener(this); 									// adding listener to button
		aboutDialog.getRootPane().setDefaultButton(ok); 				// okay pressed upon enter key
		
		
		aboutDialog.setResizable(false); 								// not allowing user to resize window
		
		
	}
	
	
	
	
	
	
	public void actionPerformed(ActionEvent ae){
		
		switch(ae.getActionCommand()){
		case "Open":
			
			int result = fileChooser.showOpenDialog(null);	
			
			if(result == JFileChooser.APPROVE_OPTION){
				
				try {
					loadFile(fileChooser.getSelectedFile().getAbsolutePath());
				} catch (IOException e) { }
				
				for(int i = 0 ; i < fileCounter ; ++i){
					Image img = image[i].getImage().getScaledInstance(imgLabel.getWidth(), imgLabel.getHeight() , Image.SCALE_SMOOTH);
					image[i] = new ImageIcon(img);
				}
			
				imgLabel.setIcon(image[0]);								// loading initial image into window
				setTitle(imageNames[0]);
				
				play.setEnabled(true);
				pause.setEnabled(true);
			}
			
			
		
			break;
			
		case "Exit":
			System.exit(0);
			break;
			
		case "About JSlideshow":
			aboutDialog.setVisible(true);
			break;
			
		case "Play":
			slideShowTmr.start();
			break;
			
		case "Pause":
			slideShowTmr.stop();
			break;
		case "Ok":
			aboutDialog.setVisible(false);
			break;
		
		
		}
		
		
	}
	
	
	

	
	public static void main(String[] args){
		SwingUtilities.invokeLater(() ->
		new JSlideshow()
	);}
	
}
