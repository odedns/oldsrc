package mataf.desktop.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;

import mataf.borders.CustomizableLineBorder;
import mataf.desktop.beans.ShortcutTextField;
import mataf.desktop.infopanels.HodaotPanel;
import mataf.desktop.infopanels.InfoPanelsConstants;
import mataf.desktop.infopanels.PirteyLakoachPanel;
import mataf.desktop.infopanels.SheiltotPanel;
import mataf.dse.appl.DesktopSettings;

import mataf.types.MatafEmbeddedPanel;
import mataf.types.MatafErrorLabel;
import mataf.types.MatafMessagesPane;
import mataf.types.MatafScrollPane;
import mataf.types.MatafTabbedPane;
import mataf.types.table.MatafTable;
import mataf.types.textfields.MatafStringField;
import mataf.utils.FontFactory;

import com.ibm.dse.base.Settings;
import com.ibm.dse.gui.CoordinatedPanel;


/**
 * 
 * This class displays the view of a single client on the desktop.
 * The view consists of 4 sections :
 * CENTER - The business panel that includes the general information panel
 * 		and tabbedpane of the transaction view/views.
 * LEFT - Information panels that include the client information, queries, 
 * 			messages and CRM window.
 * DOWN - The transaction and client messages pane.
 *
 * @author Nati Dykshtein. Created : 26/05/03
 */
public class MatafClientView extends MatafDSEPanel 
{
	private MatafEmbeddedPanel businessMessagesPanel;
	
	private MatafEmbeddedPanel mainPanel;
	private MatafBusinessPanel businessPanel;
	
	private MatafMessagesPane matafMessagesPane;
		
	//private MatafEmbeddedPanel northPanel;
		
	private MatafEmbeddedPanel westPanel;
	
	private MatafTabbedPane tabbedPane;

	// Upper panel components :
	//private MatafLabel peulaLabel;
	private MatafStringField shortcutContextField;
	private ShortcutTextField shortcutField;
	//private JComboBox sugCheshbonCmbx;

	private SheiltotPanel sheiltotPanel;

	public MatafClientView() 
	{
		super();
		
		setContextName("clientCtx");

		setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

		// We handle this ourselves.
		setDisableWhileOperationRunning(false);

		setLayout(new BorderLayout(1, 1));

		//if (DesktopSettings.RT_ONLY_COEXSISTANCE)
		//	createNorthPanelForRTOnlyCoExsistance();
		//else
		//createNorthPanel();
		//add(northPanel, BorderLayout.NORTH);

		if (!DesktopSettings.RT_ONLY_COEXSISTANCE) {
			createWestPanel();
			add(westPanel, BorderLayout.WEST);
		}

		createMainPanel();
		add(mainPanel, BorderLayout.CENTER);

		//setSize(1015, 635);
		setBackground(Color.white);
		setBorder(null);
	}

	/**
	 * The north panel of the co-existance version.
	 */
	/*	private void createNorthPanelForRTOnlyCoExsistance()
		{
			northPanel = new MatafEmbeddedPanel();
			northPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
			northPanel.setPreferredSize(new Dimension(1024, 30));
			northPanel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
	
			peulaLabel = new MatafLabel("פעולה :");
			peulaLabel.setFont(FontFactory.getDefaultFont());
			northPanel.add(peulaLabel);
	
			shortcutContextField = new MatafStringField();
			shortcutContextField.setFreeTextEnabled(true);
			shortcutContextField.setText("ש");
			shortcutContextField.setEditable(false);
			shortcutContextField.setFont(
				FontFactory.createFont("Tahoma", Font.BOLD, 14));
	
			northPanel.add(shortcutContextField);
	
			shortcutField = new ShortcutTextField("000");
			shortcutField.setColumns(4);
			shortcutField.setFocusable(false);
			shortcutField.setFont(FontFactory.createFont("Tahoma", Font.BOLD, 14));
			//shortcutField.setPreferredSize(new Dimension(36, 18));
			northPanel.add(shortcutField);
		}*/

	/**
	 * The north panel of the new desktop.
	 */
/*	private void createNorthPanel() 
	{
		Dimension pref = new Dimension(100, 5);
		northPanel = new MatafEmbeddedPanel();
		northPanel.setPreferredSize(new Dimension(1024, 20));
		northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.X_AXIS));
		northPanel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

		sugCheshbonCmbx = new JComboBox();
		sugCheshbonCmbx.setEnabled(false);
		sugCheshbonCmbx.addItem("105");

		northPanel.add(Box.createRigidArea(new Dimension(300, 5)));
		MatafComboBox mcb = new MatafComboBox();
		mcb.addItem("שקל");
		mcb.addItem("דולר");
		mcb.addItem("לירה סטרלינג");
		mcb.addItem("כתר דני");
		mcb.addItem("ין");
		mcb.setEnabled(false);

		northPanel.add(mcb);
		MatafLabel coinLabel = new MatafLabel("מטבע :");
		coinLabel.setEnabled(false);
		northPanel.add(coinLabel);

		northPanel.add(Box.createRigidArea(pref));
		MatafNumericField snifField = new MatafNumericField();
		snifField.setColumns(6);
		northPanel.add(snifField);
		snifField.setEnabled(false);
		MatafLabel snifLabel = new MatafLabel("סניף :");
		northPanel.add(snifLabel);

		northPanel.add(Box.createRigidArea(pref));
		northPanel.add(sugCheshbonCmbx);
		MatafNumericField accountField = new MatafNumericField("");
		accountField.setPreferredSize(new Dimension(60, 20));
		accountField.setEnabled(false);
		northPanel.add(accountField);
		northPanel.add(new MatafLabel("מספר חשבון :"));

		northPanel.add(Box.createRigidArea(pref));
		shortcutField = new ShortcutTextField();
		northPanel.add(shortcutField);

		shortcutContextField = new MatafStringField();
		shortcutContextField.setEditable(false);
		//shortcutContextField.setMaximumSize(new Dimension(20, 18));
		shortcutContextField.setText("ש");
		shortcutContextField.setColumns(4);
		northPanel.add(shortcutContextField);

		peulaLabel = new MatafLabel("פעולה :");
		peulaLabel.setFont(FontFactory.getDefaultFont());
		northPanel.add(peulaLabel);
	}
*/
	/**
	 * Creates the left panel of the client view.
	 */
	private void createWestPanel() 
	{
		westPanel = new MatafEmbeddedPanel(new FlowLayout(FlowLayout.CENTER,2,2));
		westPanel.setPreferredSize(new Dimension(233, 650));
		westPanel.setBackground(new Color(248,248,248));				
		
		// Create the pirtey lakoach panel.
		PirteyLakoachPanel pratimPanel = new PirteyLakoachPanel();
		westPanel.add(pratimPanel);

		// Create the sheiltot panel.
		sheiltotPanel = new SheiltotPanel(this);		
		westPanel.add(sheiltotPanel);
		
		// Create the hodaot panel.
		HodaotPanel hodaotPanel = new HodaotPanel();
		westPanel.add(hodaotPanel);
		
		// Create the CRM panel.
		MatafEmbeddedPanel theCRMPanel = new MatafEmbeddedPanel(new BorderLayout());
		theCRMPanel.setBorder(BorderFactory.createEmptyBorder(0,2,0,2));
		theCRMPanel.setPreferredSize(new Dimension(233,160));
		theCRMPanel.setBackground(InfoPanelsConstants.PANEL_BG_COLOR);
		createCRMTabbedPane();
		theCRMPanel.add(tabbedPane, BorderLayout.NORTH);
		westPanel.add(theCRMPanel);
	}
	
	public SheiltotPanel getSheiltotPanel()
	{
		return sheiltotPanel;
	}
	

/*	private void oldCreateWestPanel() 
	{
		westPanel = new MatafEmbeddedPanel(new BorderLayout(5, 20));
		westPanel.setBorder(null);
		westPanel.setPreferredSize(new Dimension(233, 650));//667

		// Create info panels :
		PirteyLakoachPanel pratimPanel = new PirteyLakoachPanel();
		SheiltotPanel sheiltotPanel = new SheiltotPanel();
		HodaotPanel hodaotPanel = new HodaotPanel();

		// CRM Panel.
		MatafEmbeddedPanel theCRMPanel = new MatafEmbeddedPanel(new FlowLayout());//BorderLayout());
		theCRMPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		createCRMTabbedPane();
		//MatafScrollPane crmScroller = new MatafScrollPane(tabbedPane);
		//crmScroller.getViewport().getView().setSize(new Dimension(233,160));
		theCRMPanel.add(tabbedPane, BorderLayout.NORTH);

		// Create split panes :
		MatafSplitPane sheiltotAndPratimSplit = 
						new MatafSplitPane(JSplitPane.VERTICAL_SPLIT, 
											pratimPanel, 
											sheiltotPanel);
		

		MatafSplitPane sheiltotPratimAndHodaotSplit = 
						new MatafSplitPane(JSplitPane.VERTICAL_SPLIT, 
											sheiltotAndPratimSplit, 
											hodaotPanel);

		MatafSplitPane sheiltotPratimHodaotAndPeuloutSplit =
						new MatafSplitPane(JSplitPane.VERTICAL_SPLIT, 
											sheiltotPratimAndHodaotSplit, 
											theCRMPanel);
		
		sheiltotPratimHodaotAndPeuloutSplit.resetToPreferredSizes();
		sheiltotPratimHodaotAndPeuloutSplit.setPreferredSize(westPanel.getPreferredSize());

		westPanel.add(sheiltotPratimHodaotAndPeuloutSplit, BorderLayout.NORTH);
	}*/

	private void createCRMTabbedPane() 
	{
		tabbedPane = new MatafTabbedPane();
		tabbedPane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);		
		tabbedPane.setPreferredSize(new Dimension(233,160));
		MatafScrollPane tab1 = new MatafScrollPane(getFirstTab());
		tab1.setEnabled(false);
		tab1.setBorder(null);
		tab1.setBackground(InfoPanelsConstants.PANEL_BG_COLOR);
		tabbedPane.addTab("מידע CRM", tab1);
		JPanel tab2 = new JPanel();
		tab2.setBackground(Color.white);
		tabbedPane.addTab("שיווק דינמי", tab2);

		//tabbedPane.blinkTab(2);
	}

	private JTable getFirstTab() 
	{
		MatafTable table = new MatafTable();
		table.setName("TestTable");
		table.setDataNameForList("test");
		table.getOurModel().addColumn(String.class, "מס' התקשרות", "shemSugKesherStr");
		table.getOurModel().addColumn(String.class, "תאריך", "misparTeudatZehutInt");
		table.getOurModel().addColumn(String.class, "סוג פעולה", "toarLakoachStr");

		// Create dummy data.
		Vector v = new Vector();
		v.add("101");
		v.add("05/03/2003");
		v.add("קניית ני\"ע");
		table.addRow(v);
		
		v = new Vector();
		v.add("102");
		v.add("06/04/2003");
		v.add("קניית ני\"ע");
		table.addRow(v);
		
		v = new Vector();
		v.add("103");
		v.add("07/05/2003");
		v.add("קניית ני\"ע");
		table.addRow(v);
		
		v = new Vector();
		v.add("104");
		v.add("08/06/2003");
		v.add("קניית ני\"ע");
		table.addRow(v);
		
		v = new Vector();
		v.add("105");
		v.add("09/06/2003");
		v.add("קניית ני\"ע");
		table.addRow(v);
		
		v = new Vector();
		v.add("106");
		v.add("12/06/2003");
		v.add("קניית ני\"ע");
		table.addRow(v);
		
		return table;
	}

	private void createBusinessMessagesPanel() 
	{
		createMatafMessagePane();
		createErrorLabel();
		businessMessagesPanel = new MatafEmbeddedPanel(new BorderLayout());
		businessMessagesPanel.setBackground(MatafMessagesPane.BG_COLOR);
		businessMessagesPanel.setPreferredSize(new Dimension(130, 95));
		businessMessagesPanel.add(matafMessagesPane.getLogScrollPane(), BorderLayout.CENTER);
		//businessMessagesPanel.add(errorLabel, BorderLayout.CENTER);
	}

	private void createMatafMessagePane() 
	{
		matafMessagesPane = new MatafMessagesPane();
		matafMessagesPane.setFont(FontFactory.createFont("Tahoma", Font.BOLD, 12));
		matafMessagesPane.setDataNameForList("BusinessMessagesList");
		//matafMessagesPane.getLogScrollPane().setPreferredSize(new Dimension(510,100));
		
		Border empty = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		Border line = new CustomizableLineBorder(MatafEmbeddedPanel.BORDER_COLOR, 0, 0, 0, 0);
		matafMessagesPane.getLogScrollPane().setBorder(BorderFactory.createCompoundBorder(line, empty));
		matafMessagesPane.getLogScrollPane().setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		matafMessagesPane.getLogScrollPane().setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		matafMessagesPane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		matafMessagesPane.setSpPanel(this);
		//matafMessagesPane.addMessage("בדיקת שורת הודעות שגיאה", Color.red);
		//matafMessagesPane.addMessage("בדיקת שורת הודעות שגיאה", Color.blue);

	}

	private void createErrorLabel()
	{
		errorLabel = new MatafErrorLabel();
		
		errorLabel.setPreferredSize(new Dimension(510, 20));

		errorLabel.queueErrorMessage("");
	}

	/**
	 * Creates the main panel.
	 */
	private void createMainPanel() 
	{
		mainPanel = new MatafEmbeddedPanel(new BorderLayout(0, 2));
		mainPanel.setPreferredSize(new Dimension(770,667));
		mainPanel.setBorder(null);
		
		createBusinessMessagesPanel();
		mainPanel.add(businessMessagesPanel, BorderLayout.SOUTH);
		
		createBusinessPanel();
		mainPanel.add(businessPanel, BorderLayout.CENTER);
	}
	
	private void createBusinessPanel()
	{
		businessPanel = new MatafBusinessPanel();
		businessPanel.add(getTheErrorLabel(), BorderLayout.SOUTH);
		businessPanel.setTheErrorLabel(getTheErrorLabel());
	}

	///////////////////////////////////////////////////////////////////////////////

	/**
	 * Sets the active transaction view for the client view.
	 */
	public void addTransactionView(CoordinatedPanel transactionView) 
	{
		// Take the reference as MatafTransactionView.
		MatafTransactionView matafTransactionView = (MatafTransactionView)transactionView;
		
		// Tell the transaction view about its parent client view.
		matafTransactionView.setParentClientView(this);
		
		// Register the messages pane with the transaction view as well.
		matafTransactionView.registerOutsider(matafMessagesPane);
		
		// Register the queries panel with the transaction view as well.
		matafTransactionView.registerOutsider(getSheiltotPanel().getMatafLinkList());
		
		// Add the new transaction panel.
		businessPanel.addTransactionView(matafTransactionView);	
	}
	
	/**
	 * Removes the transaction view from the client view.
	 * @param transactionView
	 */
	public void removeTransactionView(CoordinatedPanel transactionView)
	{
		businessPanel.removeTransactionView((MatafTransactionView)transactionView);
	}

	///////////////////////////////////////////////////////////////////////////////

	/**
	 * Returns the panel that contains the tabbed pane and the 
	 * general information panel.
	 * @return
	 */
	public MatafBusinessPanel getBusinessPanel()
	{
		return businessPanel;
	}
	
	public MatafErrorLabel getTheErrorLabel()
	{
		return errorLabel;
	}

	/**
	 * Returns the active transaction view.
	 */
	public MatafTransactionView getActiveTransactionView() 
	{
		return businessPanel.getActiveTransactionView();
	}
	
	/**
	 * Activates an already existing transaction view.
	 * This method sets the specified transaction view as selected.
	 * @param transactionView
	 */
	public void activateTransactionView(MatafTransactionView transactionView)
	{
		businessPanel.activateTransactionView(transactionView);
	}

	///////////////////////////////////////////////////////////////////////////////

	/**
	 * Returns the businessMessagesPanel.
	 * @return MatafEmbeddedPanel
	 */
	public MatafEmbeddedPanel getBusinessMessagesPanel() {
		return businessMessagesPanel;
	}

	///////////////////////////////////////////////////////////////////////////////

	/**
	 * Sets the businessMessagesPanel.
	 * @param businessMessagesPanel The businessMessagesPanel to set
	 */
	public void setBusinessMessagesPanel(MatafEmbeddedPanel businessMessagesPanel) {
		this.businessMessagesPanel = businessMessagesPanel;
	}

	///////////////////////////////////////////////////////////////////////////////		

	/**
	 * Adds a message to the message window.
	 */
	public void addMessage(String message) {
		matafMessagesPane.addMessage(message, Color.black);
	}

	/**
	 * Adds a message with the specified color to the pane.
	 */
	public void addMessage(String message, Color color) {
		matafMessagesPane.addMessage(message, color);
	}
	///////////////////////////////////////////////////////////////////////////////	

	/**
	 * @see javax.swing.JComponent#getInsets()
	 */
	public Insets getInsets() {
		return new Insets(2, 2, 2, 2);
	}

	public MatafTabbedPane getTabbedPane() {
		return tabbedPane;
	}

	/**
	 * Returns a reference to the shortcut field on this matafPanel.
	 */
	public JTextField getShortcutField() {
		return shortcutField;
	}

	/**
	 * Sets the shortcut-context char on this matafPanel.
	 */
	public void setShortcutContextTo(char context) 
	{
		if(shortcutContextField!=null)
			shortcutContextField.setText("" + context);
	}

	/**
	 * Returns a the shortcut-context char on this matafPanel.
	 */
	public char getShortcutContextChar() {
		return shortcutContextField.getText().toCharArray()[0];
	}
	
	/**
	 * @return
	 */
	public MatafMessagesPane getMatafMessagesPane()
	{
		return matafMessagesPane;
	}


	///////////////////////////////////////////////////////////////////////////////	

	public static void main(String[] args) {
		try {
			// Init.
			Settings.reset("http://127.0.0.1:9080/MatafServer/dse/dse.ini");
			Settings.initializeExternalizers(Settings.MEMORY);
		} catch (Exception e) {
			e.printStackTrace();
		}

		JFrame testFrame = new JFrame("Test Panel");
		testFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		MatafTransactionView mtv = new MatafTransactionView();
		//mtv.setTransactionPanel(new SlikaPanel1());
		
		MatafClientView mcv = new MatafClientView();
		mcv.addTransactionView(mtv);
		
		//testFrame.getContentPane().add();
		testFrame.pack();
		testFrame.setVisible(true);
		
	}
}