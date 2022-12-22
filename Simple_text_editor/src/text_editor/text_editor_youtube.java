package text_editor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.MenuBar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.jar.JarFile;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.Document;
import javax.swing.text.StyledEditorKit.FontSizeAction;

//---
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.KeyStroke;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.JTextComponent;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;


public class text_editor_youtube extends JFrame implements ActionListener{
	
	// create text area
	JTextArea text_area;
	
	// scroll pane
	JScrollPane scrollPane;

	//FONT SIZE
	JSpinner fontsizespinner; 
	
	//labal
	JLabel fontlabel;
 
	//change color
	JButton fontcolorbuttom;
	
	//font type
	JComboBox fontBox;   
	
	//menuBar
	JMenuBar menuBar;
	JMenu fileJMenu;
	JMenuItem openItem;
	JMenuItem saveItem;
	JMenuItem exitItem;
	//-----------------
	JMenu editJMenu;
	JMenuItem undoItem;
	JMenuItem redoItem;
	
	//undo & redo
	UndoManager undoManager;
	
	
	public text_editor_youtube() {  // using constructor to launch the screen window frame at the beginning
		
		// TODO Auto-generated constructor stub
		// launch frame or layout for the text editor
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // add frame
		this.setTitle("Sinai university text editor");	// set title
		this.setSize(800,800);	// set width and length respectively
		this.setLayout(new FlowLayout());	// layout style
		this.setLocationRelativeTo(null);	// to appear in middle of the screen	
		
		text_area = new JTextArea(); // creating object from class TextArea
		//text_area.setPreferredSize(new Dimension(490, 450));  // text area Dimensions  (we will not use it because it will be defined in JScroll pane)
		// preferred to be little smaller than frame
		
		
		// wrap to next line if it finished in previous one
		text_area.setLineWrap(true);
		text_area.setWrapStyleWord(true);
		
		// default font
		text_area.setFont(new Font("Arial", Font.PLAIN, 20));
		
		// create scroll pane
		scrollPane =  new JScrollPane(text_area);  // add text area to scroll
		scrollPane.setPreferredSize(new Dimension(790, 720));  // set size
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);  // set vertical scroll pane
		
		// font size
		fontsizespinner =new JSpinner();  //create object
		fontsizespinner.setPreferredSize(new Dimension(50,25));  // size of icon
		fontsizespinner.setValue(16);   //set default value
		fontsizespinner.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				text_area.setFont(new Font(text_area.getFont().getFamily(), Font.PLAIN,(int)fontsizespinner.getValue()));  //action of change font
			}
		});
		
		// font label
		fontlabel =new JLabel("Font : ");
		
		// change color
		fontcolorbuttom =new JButton("Color");
		fontcolorbuttom.addActionListener(this);
		
		//change font type
		
		
		
		String [] fonts= GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		fontBox=new JComboBox(fonts);
		fontBox.addActionListener(this);
		fontBox.setSelectedItem("Arial");
		
		
		// menu bar 
		menuBar=new JMenuBar();
		fileJMenu=new JMenu("file");
		openItem=new JMenuItem("open");
		saveItem=new JMenuItem("save");
		exitItem=new JMenuItem("exit");
		
		openItem.addActionListener(this);
		saveItem.addActionListener(this);
		exitItem.addActionListener(this);
		
		fileJMenu.add(openItem);
		fileJMenu.add(saveItem);
		fileJMenu.add(exitItem);
		menuBar.add(fileJMenu);
		
		// undo & redo
		editJMenu =new JMenu("edit");
		undoItem = new JMenuItem("undo");
		redoItem = new JMenuItem("redo");
		
		undoItem.addActionListener(this);
		redoItem.addActionListener(this);
		
		
		editJMenu.add(undoItem);
		editJMenu.add(redoItem);
		menuBar.add(editJMenu);
		
		undoManager = new UndoManager(); 
		text_area.getDocument().addUndoableEditListener(undoManager);
		
		
				
		//this.add(text_area);	// adding text area to the frame
		this.setJMenuBar(menuBar);
		this.add(fontlabel);
		this.add(fontsizespinner);
		this.add(fontcolorbuttom);
		this.add(fontBox);
		this.add(scrollPane);
		this.setVisible(true);
		
		
	}
	
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if(e.getSource()==fontcolorbuttom) {
			
			JColorChooser colorChooser =new JColorChooser();
			Color color =colorChooser.showDialog(null, "choose acolor ", Color.black);
			text_area.setForeground(color);
		}
		if(e.getSource()==fontBox)
		{
			text_area.setFont(new Font((String)fontBox.getSelectedItem(),Font.PLAIN,text_area.getFont().getSize()));
		}
		if(e.getSource()==openItem)
		{
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(new File("."));
			FileNameExtensionFilter filter=new FileNameExtensionFilter("text files", "txt");
			fileChooser.setFileFilter(filter);
			int response=fileChooser.showOpenDialog(null);
			
			if(response==JFileChooser.APPROVE_OPTION) {
				File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
				Scanner filein =null;
				try {
					filein=new Scanner(file);
					if(file.isFile()) {
						while(filein.hasNextLine()) {
							String lineString=filein.nextLine()+"\n";
							text_area.append(lineString);
						}
						
					}
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				finally {
					filein.close();
				}
			}
		}
		if(e.getSource()==saveItem)
		{
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(new File("."));
			 int response = fileChooser.showSaveDialog(null);
			   
			   if(response == JFileChooser.APPROVE_OPTION) 
			   {
				   File file;
				  PrintWriter fileout=null;
				  file=new File(fileChooser.getSelectedFile().getAbsolutePath());
				  try {
					fileout=new PrintWriter(file);
					fileout.println(text_area.getText());
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				  finally {
					fileout.close();
				}
				  
			   }
			
		}
		if(e.getSource()==exitItem)
		{
			System.exit(0);
		}
		if(e.getSource() == undoItem) {
			if(undoManager.canUndo()) {
				undoManager.undo();
			}
		}
		if(e.getSource() == redoItem) {
			if(undoManager.canRedo()) {
				undoManager.redo();
			}
		}
	}
}
