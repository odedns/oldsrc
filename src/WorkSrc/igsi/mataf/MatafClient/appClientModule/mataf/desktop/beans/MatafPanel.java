package mataf.desktop.beans;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;

import mataf.borders.CustomizableLineBorder;
import mataf.dse.gui.MatafErrorLabel;
import mataf.types.MatafEmbeddedPanel;
import mataf.types.MatafMessagesPane;
import mataf.types.MatafOptionPane;
import mataf.types.MatafTabbedPane;
import mataf.types.MatafTable;

import com.ibm.dse.base.Settings;
import com.ibm.dse.gui.DSEPanel;

/**
 * 
 * This class displays the main view on the desktop.
 * The view consists of 4 sections :
 * 1 - The main operating panel
 * 2 - The tabbed pane.
 * 3 - The messages window.
 * 4 - The error line.
 * 
 * @author Nati Dykshtein. Created : 26/05/03
 */
public class MatafPanel extends DSEPanel 
{
     private static String CLEAR_MESSAGES 	= "-1";
     private static String END_OF_MESSAGES 	= "-2";
     
     private static int	MAXIMUM_VISIBLE_MESSAGES = 6;
     private static Image backgroundImg;
     
     
     private JPanel				mainPanel;
     private MatafEmbeddedPanel	southPanel;
     private MatafEmbeddedPanel	southRightPanel;
     private MatafEmbeddedPanel	southLeftPanel;
     private MatafEmbeddedPanel	westPanel;
     private MatafMessagesPane		matafMessagesPane;
     private int					messagesNumber;
     private MatafErrorLabel		errorLabel;
     private MatafTabbedPane		tabbedPane;
     
	static
	{
		backgroundImg = new ImageIcon("appClientModule/images/fibibackground.gif").getImage();
	}
	
	public MatafPanel() 
	{
		super();
		
		setLayout(new BorderLayout(1,1));	
		
		createWestPanel();
	    add(westPanel, BorderLayout.WEST);
		
		createMainPanel();
        add(mainPanel, BorderLayout.CENTER);
        
   		createSouthPanel();
        add(southPanel, BorderLayout.SOUTH);
        
        setSize(1015, 635);
        setBackground(Color.white);
        setBorder(null);
        
	}
	
	private void createWestPanel()
	{
		westPanel = new MatafEmbeddedPanel(new BorderLayout(5,20));
		
		// Info panel's headline
		JLabel headline = new JLabel("מאפייני חשבון",JLabel.CENTER);
		headline.setOpaque(false);
		headline.setFont(new Font("",Font.BOLD,12));
		headline.setForeground(Color.blue);
//		westPanel.add(headline, BorderLayout.NORTH);

		// Info panel's main displaying area.
		JPanel center = new JPanel();
		center.setOpaque(false);
		center.setPreferredSize(new Dimension(100,100));
/*		center.add(new JLabel("יתרה למשיכה :"));
		center.add(new JLabel("38,950.00"));
		center.add(new JLabel("יתרה עדכנית"));
		center.add(new JLabel("273,935.00"));
		center.add(new JLabel("יתרה כללית :"));
		center.add(new JLabel("0.00"));
		center.add(new JLabel("מאפייני החשבון :"));*/
		westPanel.add(center, BorderLayout.CENTER);
	}
	
	private void createSouthPanel()
	{
		createSouthLeftPanel();
		createSouthRightPanel();
		southPanel = new MatafEmbeddedPanel(new BorderLayout());
		southPanel.add(tabbedPane, BorderLayout.WEST);
		southPanel.add(southRightPanel, BorderLayout.EAST);
	}
	
	private void createSouthLeftPanel()
	{
		southLeftPanel = new MatafEmbeddedPanel(new BorderLayout());		
		createTabbedPane();
		southLeftPanel.add(tabbedPane, BorderLayout.CENTER);
	}
	
	private void createTabbedPane()
	{
		tabbedPane = new MatafTabbedPane();
		//tabbedPane.setOpaque(false);
		//tabbedPane.setBorder(BorderFactory.createLineBorder(Color.gray));
		tabbedPane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		tabbedPane.setPreferredSize(new Dimension(500,150));
		tabbedPane.setBorder(BorderFactory.createEmptyBorder(3,3,3,3));
		tabbedPane.setFont(new Font("Courier", Font.BOLD, 12));
		JScrollPane tab1 = new JScrollPane(getFirstTab());
		tab1.setBackground(Color.white);
		tabbedPane.addTab("נושא 1                    ",tab1);
		JPanel tab2 = new JPanel();
		tab2.setBackground(Color.white);
		tabbedPane.addTab("נושא 2                    ",tab2);
		JPanel tab3 = new JPanel();
		tab3.setBackground(Color.white);
		tabbedPane.addTab("נושא 3                    ",tab3);
		JPanel tab4 = new JPanel();
		tab4.setBackground(Color.white);
		tabbedPane.addTab("נושא 4                    ",tab4);
		
		tabbedPane.blinkTab(2);
	}
	
	private JTable getFirstTab()
	{
		MatafTable table = new MatafTable();
		table.setName("TestTable");
		table.setDataNameAndColumns(((((((new com.ibm.dse.gui.VectorEditor(6)).setElemAt("lmx7ReceiveLefiZehutImKtovetList",0)
			).setElemAt(new com.ibm.dse.gui.ColumnFormatter("shemSugKesherStr","סוג קשר",null,false,false,false,20),1)
			).setElemAt(new com.ibm.dse.gui.ColumnFormatter("misparTeudatZehutInt","מזהה",null,false,false,false,20),2)
			).setElemAt(new com.ibm.dse.gui.ColumnFormatter("toarLakoachStr","תואר",null,false,false,false,20),3)
			).setElemAt(new com.ibm.dse.gui.ColumnFormatter("shemLakoachStr","שם",null,false,false,false,20),4)
			).setElemAt(new com.ibm.dse.gui.ColumnFormatter("kodEretzMelelStr","ארץ",null,false,false,false,20),5));
			
		Vector v = new Vector();
		v.add("פרטי");v.add("0423483231");v.add("קשת");v.add("ישראל ישראלי");v.add("ישראל");
		table.addRow(v);
		v = new Vector();
		v.add("פרטי");v.add("0634523433");v.add("קשת");v.add("חיים");v.add("ישראל");
		table.addRow(v);
		v = new Vector();
		v.add("עיסקי");v.add("645345443");v.add("פלח");v.add("IBM");v.add("איטליה");
		table.addRow(v);
		v = new Vector();
		v.add("עיסקי");v.add("346345654");v.add("פלח");v.add("BMW");v.add("גרמניה");
		table.addRow(v);
		v = new Vector();
		v.add("אסטרטגי");v.add("325534353");v.add("עתיד");v.add("פלאפון");v.add("ישראל");
		table.addRow(v);
		v = new Vector();
		v.add("עיסקי");v.add("153235343");v.add("פלח");v.add("WEBSPHERE");v.add("איטליה");
		table.addRow(v);
		v = new Vector();
		v.add("עיסקי");v.add("752235434");v.add("פנימי");v.add("IGS");v.add("איטליה");
		table.addRow(v);
		return table;
	}
	
	private void createSouthRightPanel()
	{
		createMatafMessagePane();
		createErrorLabel();
		southRightPanel = new MatafEmbeddedPanel(new BorderLayout());
		southRightPanel.setBorder(null);
		southRightPanel.setPreferredSize(new Dimension(510,150));
		southRightPanel.add(matafMessagesPane.getLogScrollPane(), BorderLayout.NORTH);
		southRightPanel.add(errorLabel, BorderLayout.CENTER);
	}
	
	private void createMatafMessagePane()
	{
		matafMessagesPane = new MatafMessagesPane();
		matafMessagesPane.getLogScrollPane().setPreferredSize(new Dimension(510,130));
		Border empty = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		Border line	 = new CustomizableLineBorder(
										MatafEmbeddedPanel.BORDER_COLOR, 0, 6, 0, 0);
		matafMessagesPane.getLogScrollPane().setBorder(BorderFactory.createCompoundBorder(line, empty));
		matafMessagesPane.getLogScrollPane().setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		matafMessagesPane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		addMessage("ממתין להתחברות למערכת.",Color.CYAN);
	}
	
	private void createErrorLabel()
	{
		errorLabel = new MatafErrorLabel();
		errorLabel.setOpaque(false);
		Border empty = BorderFactory.createEmptyBorder(2,2,2,2);
		Border line	 = new CustomizableLineBorder(
										MatafEmbeddedPanel.BORDER_COLOR, 1, 6, 0, 0);
		
		errorLabel.setBorder(BorderFactory.createCompoundBorder(line,empty));
		
		errorLabel.setForeground(Color.red);
		errorLabel.setFont(new Font("Courier",Font.BOLD,12));
		errorLabel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		errorLabel.setPreferredSize(new Dimension(510,20));

		errorLabel.queueErrorMessage("");
	}	
	
	/**
	 * Creates the main panel with an image at the background.
	 */
	private void createMainPanel()
	{
		mainPanel = new JPanel()
		{
			public void paint(Graphics g)
			{
				super.paint(g);
				g.drawImage(backgroundImg,0,0,getWidth()-10,getHeight()-30,this);
			}
		};
		mainPanel.setLayout(null);
		mainPanel.setBackground(Color.white);
	}	
	
///////////////////////////////////////////////////////////////////////////////	
	
	/**
	 * Sets the active panel for the desktop.
	 */
	public void setActivePanel(MatafEmbeddedPanel panel)
	{
		remove(mainPanel);
		add(panel, BorderLayout.CENTER);
		validate();
		repaint();
	}
	
///////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Prevent adding component directly to the panel.
	 */
	public Component add(Component c)
	{
		throw new RuntimeException("Do not use add() on an instance of MatafPanel, use setActivePanel(MatafEmebeddedPanel p) instead");
	}
	
///////////////////////////////////////////////////////////////////////////////		
	
	/**
	 * Returns the errorLabel.
	 */
	public MatafErrorLabel getTheErrorLabel()
	{
		return errorLabel;
	}
	
///////////////////////////////////////////////////////////////////////////////		
	
	/**
	 * Adds a message to the message window.
	 */
	public void addMessage(String message)
	{
		addMessage(message, Color.black);
	}
	
///////////////////////////////////////////////////////////////////////////////	
	
	/**
	 * Adds a message with the specified color to the message area.
	 */
	public void addMessage(String message, Color messageColor)
	{
		// Solve almost non-visible white text coming from the RT.
		if(messageColor.equals(Color.white))
			messageColor = Color.black;
		// Clears the message area.
		if(message.equals(CLEAR_MESSAGES))
		{
			matafMessagesPane.getLogPane().setText("");
			messagesNumber = 0;
		}
		else
			// End of current group of messages.
			if(message.equals(END_OF_MESSAGES))
			{
				// Check if we have to show a popup.
				if(messagesNumber>MAXIMUM_VISIBLE_MESSAGES)
					showMessagesInPopup();
			}		
			else // Append a new message.
			{
				matafMessagesPane.log(message, messageColor);
				messagesNumber++;
			}
	}
	
	private void showMessagesInPopup()
	{
		String text = matafMessagesPane.getLogPane().getText();
		MatafOptionPane.showMessageDialog(	this,
										text,
										"חלון הודעות",
										JOptionPane.INFORMATION_MESSAGE);
	}
	
	/**
	 * @see javax.swing.JComponent#getInsets()
	 */
	public Insets getInsets() 
	{
		return new Insets(2,2,2,2);
	}
	
	public MatafTabbedPane getTabbedPane()
	{
		return tabbedPane;
	}

	
///////////////////////////////////////////////////////////////////////////////	
	
	public static void main(String[] args)
	{
		try
		{
			// Init.
			Settings.reset("http://127.0.0.1/MatafServer/dse/dse.ini");
			Settings.initializeExternalizers(Settings.MEMORY);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		JFrame testPanel = new JFrame("Test Panel");
		MatafPanel mp = new MatafPanel();
		testPanel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		testPanel.getContentPane().add(mp);
		testPanel.setVisible(true);
		testPanel.setExtendedState(JFrame.MAXIMIZED_BOTH);
	}
}