package mataf.types;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.Popup;
import javax.swing.PopupFactory;

import mataf.exchangers.VisualPropertiesExchanger;
import mataf.types.table.MatafTable;
import mataf.utils.FontFactory;

import com.ibm.dse.base.Context;
import com.ibm.dse.base.DSEClientOperation;
import com.ibm.dse.base.Settings;
import com.ibm.dse.desktop.Desktop;
import com.ibm.dse.gui.Converter;
import com.ibm.dse.gui.CoordinatedEventListener;
import com.ibm.dse.gui.DSECoordinatedPanel;
import com.ibm.dse.gui.DSECoordinationEvent;
import com.ibm.dse.gui.DataChangedListener;
import com.ibm.dse.gui.DataExchangerWithList;
import com.ibm.dse.gui.Formatter;
import com.ibm.dse.gui.NavigationParameters;
import com.ibm.dse.gui.SpPanel;


/**
 * Class is a comboBox-like component that displays tables in a 
 * drop-down popup.
 * The class is DataExchangerForList that delegates its methods to its table.  
 * 
 * @author Nati Dykstein. Creation Date : (21/07/2003 15:01:46).  
 */
public class MatafTableComboBoxButton extends JButton
								implements DataExchangerWithList,VisualPropertiesExchanger
{
	private static final Color		BACKGROUND_COLOR;
	
	/** Combo-box's down arrow image.*/
	private static final ImageIcon 	ARROW_IMAGE;
	
	public static final Dimension 	COMBO_DEFAULT_SIZE;
	
	public static final int 			POPUP_DEFAULT_WIDTH;
	public static final int 			POPUP_DEFAULT_HEIGHT;

	private static PopupFactory popupFactory = PopupFactory.getSharedInstance();
	
	/** The table to pop-up.*/
	private MatafTable 			table;
	/** The scroller upon the table is set.*/
	private JScrollPane			scroller;
	/** The popup window.*/
	private Popup					popup;
	/** True if pop-up is showing.*/
	private boolean				popupOpen;
	/** The last value selected.*/
	private String					lastSelection;
	/** The operation to invoke upon selection. */
	private String					clientOperationName;
	/** The attached textfield. */
	private MatafComboTextField	attachedTextField;
	
	private Dimension 				computedScrollerSize;
	
	static
	{
		ARROW_IMAGE = new 
					ImageIcon(ClassLoader.getSystemResource("images/guibeans/arrowdown.gif"));
		COMBO_DEFAULT_SIZE  	= new Dimension(60,20);
		POPUP_DEFAULT_WIDTH  	= 300;
		POPUP_DEFAULT_HEIGHT	= 84;
		BACKGROUND_COLOR 		= new Color(255,255,204);
	}

	public MatafTableComboBoxButton()
	{
		this(null);
		setText("בחר");
		setFocusable(false);
		setRolloverEnabled(true);
		attachKeyListener();
	}
	
	public MatafTableComboBoxButton(MatafTable table)
	{
		// UI settings.
		setSize(COMBO_DEFAULT_SIZE);
		setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		//setBackground(BACKGROUND_COLOR);
		setFont(FontFactory.getDefaultFont());
		
		// Set us as the table's combo box popup opener.
		if(table!=null)
			assignToTable(table);
		
		// Remove button's default mouse behavior.
		removeMouseListener(getMouseListeners()[0]);
		
		// Add desired mouse behavior - Open popup on mousePressed.
		addMouseListener(new MouseAdapter() {
			
			public void mousePressed(MouseEvent e) 
			{				
				if(!isEnabled())
					return;
				requestFocus();
				togglePopup();
			}
		});
		
		// Add desired keyboard behavior - Open popup on keyPressed.
	/*	addKeyListener(new KeyAdapter() 
		{		
			public void keyPressed(KeyEvent e) 
			{
				if(!isEnabled())
					return;
				togglePopup();
			}
		});*/
	}
	
	/**
	 * Allow the escape key to canecl the pop-up window.
	 *
	 */
	private void attachKeyListener()
	{
		addKeyListener(new KeyAdapter() 
		{
			/**
			 * If the pop-up is open - dispatch key pressed events to its table.
			 */
			public void keyPressed(KeyEvent e) 
			{
				if(KeyEvent.getKeyText(e.getKeyCode()).equals("Escape"))
				{
					cancelPopup();
				}
			}
		});
	}
	
	/**
	 * Assign the table to be displayed in the combobox.
	 * This method also configures the scroller.
	 */
	public void assignToTable(MatafTable table)
	{
		// Re-configure scroller when changing tables.
		if(this.table!=table)
			configureScroller(table);
		
		this.table = table;
		
		// Send our reference to the table.
		table.setTableComboBox(this);
	}
	
	/**
	 * Configure the scroller viewport and size.
	 * 
	 * @param table - The table to display in the scroll pane.
	 */
	private void configureScroller(MatafTable table)
	{
		scroller = new MatafScrollPane(table);

		// Set the size of the scroller inside the popup.
		scroller.setPreferredSize(computeScrollerSize(table));
	}
	
	/**
	 * Toggles the combo-box popup table.
	 */
	public void togglePopup()
	{
		if(popupOpen)
			closePopup();
		else
			openPopup();
	}
	
	/**
	 * Opens the popup.Used internally by togglePopup().
	 *
	 */
	private void openPopup()
	{
		MatafLabel errorMessage = null;
		// No table was assigned.
		if(table==null)
		{
			errorMessage = new MatafLabel("לא מקושר לטבלה");
		}
		else	// Table is empty.
			if(table.getRowCount()==0)
			{			
				errorMessage = new MatafLabel("הטבלה ריקה");
			}
		
		// An error was detected.
		if(errorMessage!=null)
		{
			// Get the position of the popup-window.
			Point pos = computePopupPosition();
		
			// Get reference to the created popup-window.
			popup = popupFactory.getPopup(Desktop.getFrame(),
										errorMessage,
										pos.x,
										pos.y);
		
			popup.show();
			popupOpen = true;
		}
		else
		{
			// Get the position of the popup-window.
			Point pos = computePopupPosition();
			
			// Get Reference to the created popup-window.
			popup = popupFactory.getPopup(Desktop.getFrame(),
											scroller,
											pos.x,
											pos.y);
			
			popup.show();
			popupOpen = true;
	
			// If table is opened for the first time - selecte the first row.
			if(table.getSelectedColumn()==-1)
				table.changeSelection(0,0,false,false);
		}
	}
	
	
	/**
	 * Closes the popup.Used internally by togglePopup().
	 *
	 */
	private void closePopup()
	{
		// Hide popup.
		popup.hide();
	
		// Update the popupOpen state.
		popupOpen = false;
	
		// We're closing the error message, no need for further ops.
		if(table==null || (table.getRowCount()==0))
			return;
	
		// Reset the 'type into' feature in the table.
		table.resetSearchString();
	
		// Get selection from table.
		String selection = getRowAsString(table.getSelectedRow());
	
		// Do only if value was changed.
		if(selection!=lastSelection)
		{
			// Notify action listeners about the selection.
			fireActionPerformed(new ActionEvent(this,0,"New value in TableComboBox has been Selected."));
		
			// Update the value of the attached textfield.
			if(attachedTextField!=null)
			{
				// Check that table is not empty.
				if(getDataValue()!=null)
				{
					attachedTextField.setText(getDataValue().toString());
					// When attached TextField is part of a table
					// the TableCellEditor will fire the event.
					if(!attachedTextField.isInCellEditor())
						attachedTextField.fireCoordinationEvent();
				}
				else
					attachedTextField.setText("");
			}
	
			// Activate the associated client opertaion.(If any)
			activateClientOperation();
		
			// Update last selected value.
			lastSelection = selection;
		}
	
		// Get the focus back to the textfield.
		if(attachedTextField!=null)
			attachedTextField.activateFocusTraversal(false, true);
	}
	
	
	
	/**
	 * This method will cancel the popup.
	 * To close the popup normally  - use togglePopup().
	 */
	public void cancelPopup()
	{
		// Hide popup.
		popup.hide();
		
		// Update the popupOpen state.
		popupOpen = false;
		
		// Reset the 'type into' feature in the table.
		table.resetSearchString();
		
		// Get back the focus.
		//if(getAttachedTextField()!=null)
		//	getAttachedTextField().requestFocus();
	}
	
	
	/**
	 * Computes scroller size according to the table, and add
	 * the scroll bar size.
	 */
	private Dimension computeScrollerSize(MatafTable table)
	{		
		if(computedScrollerSize==null)
		{
			int tableWidth = table.getSize().width;
			computedScrollerSize = new Dimension(tableWidth, POPUP_DEFAULT_HEIGHT);
		}
		return computedScrollerSize;
	}	
	
	/**
	 * Computes popup-window position according to Component Orientation.
	 * If the component is not visible, the pop-up will open under the 
	 * textfield.
	 */
	private Point computePopupPosition()
	{
		MatafTextField mtf = getAttachedTextField();
		Point p = isVisible() ? getLocationOnScreen() : 
								 mtf.getLocationOnScreen();
								 
		p.y += isVisible() ? getHeight() : mtf.getHeight();
		
		int componentWidth = 
					isVisible() ? getSize().width : mtf.getSize().width;

		if(scroller!=null)
			if(!getComponentOrientation().isLeftToRight())
				p.x -= (scroller.getPreferredSize().width - componentWidth);

		return p;
	}
	
	/**
	 * Returns the columns in the specified row as space seperated string.
	 */
	public String getRowAsString(int rowNumber)
	{
		String s="";
		int columnCount = table.getColumnCount();

		for(int i=0;i<columnCount;i++)
			s += " " + table.getCellAsString(rowNumber,i);
		return s;
	}
	
	/**
	 * Returns the columns in the selected row as space seperated string.
	 */
	
	public String getSelectedRowAsString()
	{
		return getRowAsString(table.getSelectedRow());
	}
	

	/**
	 * Method draws the arrow icon on the combo-box.
	 */
	public void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		Point pos = computeArrowPosition();
		g.drawImage(ARROW_IMAGE.getImage(), pos.x, pos.y, this);
	}
	
	/**
	 * Computes the arrow icon position on the combo-box 
	 * according to the component's orientation.
	 */
	private Point computeArrowPosition()
	{
		Point p = new Point();
		int ICON_EDGE_GAP = 3;
		
		p.y = (getHeight() - ARROW_IMAGE.getIconHeight())/2;
		
		if(getComponentOrientation().isLeftToRight())
		{
			p.x = getWidth()-(ARROW_IMAGE.getIconWidth()+ICON_EDGE_GAP);
		}
		else
		{
			p.x = ICON_EDGE_GAP;
		}		
		
		return p;
	}
	
	
	/**
	 * Activate the associated client operation.
	 */
	private void activateClientOperation()
	{
		// No client operation was assigned for table combobox.
		if(clientOperationName==null)
			return;
		try
		{
			// Get the active context.
			Context ctx = getDSECoordinatedPanel().getContext();
			
			// Update the value in the context on-the-spot, prior to the operation.			
			ctx.setValueAt(getAttachedTextField().getDataName(), getDataValue());
			
			// Reflect the operation.
			DSEClientOperation clientOp = 
				(DSEClientOperation) DSEClientOperation.readObject(clientOperationName);

			// Activate the operation.
			clientOp.chainTo(ctx);
			clientOp.execute();
			clientOp.unchain();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Returns the table.
	 * @return MatafTable
	 */
	public MatafTable getTable() {
		return table;
	}

	/**
	 * Sets the table.
	 * @param table The table to set
	 */
	public void setTable(MatafTable table) {
		assignToTable(table);
	}


	
	private static MatafTable createExampleTable()
	{
		MatafTable table = new MatafTable();
		table.setFont(FontFactory.createFont("Tahoma", Font.PLAIN, 10));
		table.setName("TestTable");
		table.setDataNameForList("test");
		table.getOurModel().addColumn(String.class, "מס' התקשרות", "shemSugKesherStr");
		table.getOurModel().addColumn(String.class, "תאריך", "misparTeudatZehutInt");
		table.getOurModel().addColumn(String.class, "סוג פעולה", "toarLakoachStr");
		
		Vector v = new Vector();
		v.add("302");v.add("רוטשילד");
		table.addRow(v);
		v = new Vector();
		v.add("432");v.add("רמת-גן");
		table.addRow(v);
		v = new Vector();
		v.add("523");v.add("ירושלים");
		table.addRow(v);
		v = new Vector();
		v.add("527");v.add("שדרות חן");
		table.addRow(v);
		v = new Vector();
		v.add("227");v.add("דימונה");
		table.addRow(v);
		v = new Vector();
		v.add("745");v.add("חיפה");
		table.addRow(v);

		return table;		
	}
	
/////////////////////////////////////////////////////////////////////////////////
//////////////////////// DataExchangerWithList Implementation ///////////////////
/////////////////////////////////////////////////////////////////////////////////
	/**
	 * @see com.ibm.dse.gui.DataExchanger#addDataChangedListener(DataChangedListener)
	 */
	public void addDataChangedListener(DataChangedListener adl) 
	{
		if (table!=null)
			table.addDataChangedListener(adl);
	}

	/**
	 * @see com.ibm.dse.gui.DataExchanger#getAlternativeDataName()
	 */
	public String getAlternativeDataName() 
	{
		return (table!=null) ? table.getAlternativeDataName() : "EMPTY";
	}

	/**
	 * Returns the converter associated to the label.
	 * @return com.ibm.dse.gui.Converter - the converter associated to the label 
	 */
	public Converter getConverter() 
	{
		return (table!=null) ? table.getConverter() : null;
	}

	/**
	 * @see com.ibm.dse.gui.DataExchanger#getDataDirection()
	 */
	public String getDataDirection() 
	{
		return (table!=null) ? table.getDataDirection() : "EMPTY";
	}

	/**
	 * @see com.ibm.dse.gui.DataExchanger#getDataName()
	 */
	public String getDataName() 
	{
		return (table!=null) ? table.getDataName() : "EMPTY";
	}
	
	public String getDataDescriptionName()
	{
		return (table!=null) ? table.getDescriptionDataName() : "EMPTY";
	}

	/**
	 * Returns currently selected value.
	 */
	public Object getDataValue() 
	{
		return (table!=null) ? table.getDataValue() : null;
	}

	/**
	 * Gets the DSECoordinatedPanel where the component is placed
	 * @return com.ibm.dse.gui.DSECoordinatedPanel
	 */
	public DSECoordinatedPanel getDSECoordinatedPanel() 
	{
		return DSECoordinatedPanel.getDSECoordinatedPanel(this);
	}

	/**
	 * @see com.ibm.dse.gui.DataExchanger#getErrorMessage()
	 */
	public String getErrorMessage() 
	{
		return (table!=null) ? table.getErrorMessage() : "EMPTY";
	}
	
	public void setErrorMessage(String errorMessage)
	{
		if(table!=null)
			table.setErrorMessage(errorMessage);
	}
	
	public void setInErrorFromServer(boolean errorFromServer)
	{
		if(table!=null)
			table.setInErrorFromServer(errorFromServer);
	}

	/**
	 * Gets the formatter property (com.ibm.dse.gui.Formatter) value.
	 * @return Formatter - the formatter property value
	 * @see #setFormatter
	 */
	public Formatter getFormatter() 
	{
		return (table!=null) ? table.getFormatter() : null;
	}
	
	/**
	* Formats the label value using the label formatter.
	*/
	protected String getSourceValueForConversion() 
	{
		return (table!=null) ? table.getSourceValueForConversion() : "EMPTY";
	}

	/**
	 * Receives a String formatted with the the float formatter corresponding to the default Locale.
	 * Returns the same value formatted as standard float
	 */
	protected String getSourceValueForConversion2(String text) 
	{
		return (table!=null) ? table.getSourceValueForConversion2(text) : "EMPTY";
	}

	/**
	 * Gets the SpPanel where the component is placed
	 * @return com.ibm.dse.gui.SpPanel
	 */
	public SpPanel getSpPanel() 
	{
		return (table!=null) ? table.getSpPanel() : null;
	}

	/**
	 * @see com.ibm.dse.gui.DataExchanger#hasAlternativeDataName()
	 */
	public boolean hasAlternativeDataName() 
	{
		return (table!=null) ? table.hasAlternativeDataName() : false;
	}

	/**
	 * @see com.ibm.dse.gui.DataExchanger#isInError()
	 */
	public boolean isInError() 
	{
		return (table!=null) ? table.isInError() : false;
	}

	/**
	 * @see com.ibm.dse.gui.DataExchanger#isKeyedValue()
	 */
	public boolean isKeyedValue() 
	{
		return (table!=null) ? table.isKeyedValue() : false;
	}

	/**
	 * @see com.ibm.dse.gui.DataExchanger#isRequired()
	 */
	public boolean isRequired() 
	{
		return (table!=null) ? table.isRequired() : false;
	}

	/**
	 * @see com.ibm.dse.gui.DataExchanger#removeDataChangedListener(DataChangedListener)
	 */
	public void removeDataChangedListener(DataChangedListener dcl) 
	{
	}

	/**
	 * @see com.ibm.dse.gui.DataExchanger#setAlternativeDataName(String)
	 */
	public void setAlternativeDataName(String alternativeDataName) 
	{
		if(table!=null)
			table.setAlternativeDataName(alternativeDataName);
	}

	/**
	 * Sets the convertible property (boolean) value.
	 * @param convertible boolean, the new value for the property
	 * @see #getConvertible
	 */
	public void setConvertible(boolean convertible) 
	{
		if(table!=null)
			table.setConvertible(convertible);
	}

	/**
	 * Sets the dataDirection property (java.lang.String) value.
	 * @param dataDirection String, the new value for the property
	 * @see #getDataDirection
	 */
	public void setDataDirection(String dataDirection) 
	{
		if(table!=null)
			table.setDataDirection(dataDirection);
	}

	/**
	 * Sets the dataName property (java.lang.String) value.
	 * @param dataName String, the new value for the property
	 * @see #getDataName
	 */
	public void setDataName(String dataName) 
	{
		if(table!=null)
			table.setDataName(dataName);
	}
	
	public void setDescriptionDataName(String descriptionDataName) {
		if(table!=null)
			table.setDescriptionDataName(descriptionDataName);
	}

	/**
	 * Sets the dataToClear property (java.lang.String) value.
	 * @param dataToClear The new value for the property.
	 * @see #getDataToClear
	 */
	public void setDataToClear(java.lang.String dataToClear) 
	{
		if(table!=null)
			table.setDataToClear(dataToClear);
	}
	
	/**
	 * Exchange visual properties.
	 */
	public void exchangeVisualProperties(Context ctx) 
	{
		ADAPTER.exchangeVisualProperties(ctx, this);
	}
	
	/**
	 * Sets the dataValue property (Object) value.
	 * @param dataValue Object, the new value for the property
	 * @see #getDataValue
	 */
	public void setDataValue(Object dataValue) 
	{
		if(table!=null)
			table.setDataValue(dataValue);
	}

	/**
	 * Sets the dataValue property (java.lang.String) value.
	 * @param value String, the new value String for the dataValue property 
	 */
	protected void setDataValueAsObject(String value) 
	{
		if(table!=null)
			table.setDataValueAsObject(value);
	}

	/**
	 * Sets the formatter property (com.ibm.dse.gui.Formatter) value.
	 * @param formatter Formatter, the new value for the property
	 * @see #getFormatter
	 */
	public void setFormatter(Formatter formatter) 
	{
		if(table!=null)
			table.setFormatter(formatter);
	}

	/**
	 * @see com.ibm.dse.gui.DataExchanger#setRequired(boolean)
	 */
	public void setRequired(boolean req) {
	}

	/**
	 * Returns our dataNameForList.
	 */
	public String getDataNameForList() 
	{
		return (table!=null) ? table.getDataNameForList() : null;
	}

	/**
	 * Sets out dataNameForList.
	 */
	public void setDataNameForList(String indexedCollectionName) 
	{
		if(table!=null)
			table.setDataNameForList(indexedCollectionName);
	}

	/**
	 * Update the table's model from the context IndexedCollection.
	 */
	public void setDataValueForList(Object dataValue)
	{
		if(table!=null)
			table.setDataValueForList(dataValue);
	}
	
	public void fireCoordinationEvent(DSECoordinationEvent event) 
	{
		if(table!=null)
			table.fireCoordinationEvent(event);
	}

	/**
	 * @see com.ibm.dse.gui.DataExchanger#addActionListener(ActionListener)
	 */
	public void addActionListener(ActionListener l) 
	{
		super.addActionListener(l);
	}

	/**
	 * @see com.ibm.dse.gui.DataExchanger#addCoordinatedEventListener(CoordinatedEventListener)
	 */
	public void addCoordinatedEventListener(CoordinatedEventListener arg0) 
	{
		if(table!=null)
			table.addCoordinatedEventListener(arg0);
	}

	/**
	 * @see com.ibm.dse.gui.DataExchanger#getNavigationParameters()
	 */
	public NavigationParameters getNavigationParameters() 
	{
		return (table!=null) ? table.getNavigationParameters() : null;
	}

	/**
	 * @see com.ibm.dse.gui.PanelActions#getType()
	 */
	public String getType() 
	{		
		return (table!=null) ? table.getType() : "EMPTY";
	}

	/**
	 * @see com.ibm.dse.gui.DataExchanger#removeActionListener(ActionListener)
	 */
	public void removeActionListener(ActionListener arg0) 
	{
		if(table!=null)
			table.removeActionListener(arg0);
	}

	/**
	 * @see com.ibm.dse.gui.DataExchanger#removeCoordinatedEventListener(CoordinatedEventListener)
	 */
	public void removeCoordinatedEventListener(CoordinatedEventListener arg0) 
	{
		if(table!=null)
			table.removeCoordinatedEventListener(arg0);
	}

	/**
	 * @see com.ibm.dse.gui.DataExchanger#setHelpID(String)
	 */
	public void setHelpID(String arg0) 
	{
		if(table!=null)
			table.setHelpID(arg0);
	}

	/**
	 * @see com.ibm.dse.gui.DataExchanger#setNavigationParameters(NavigationParameters)
	 */
	public void setNavigationParameters(NavigationParameters arg0) 
	{
		if(table!=null)
			table.setNavigationParameters(arg0);
	}

	/**
	 * @see com.ibm.dse.gui.DataExchanger#setType(String)
	 */
	public void setType(String arg0) 
	{
		if(table!=null)
			table.setType(arg0);
	}

	/**
	 * @see com.ibm.dse.gui.PanelActions#getDataToClear()
	 */
	public String getDataToClear() 
	{
		return (table!=null) ? table.getDataToClear() : "EMPTY";
	}
	
	/**
	 * Returns the popupOpen.
	 * @return boolean
	 */
	public boolean isPopupOpen() {
		return popupOpen;
	}

	/**
	 * Sets the popupOpen.
	 * @param popupOpen The popupOpen to set
	 */
	public void setPopupOpen(boolean popupOpen) {
		this.popupOpen = popupOpen;
	}
	
	/**
	 * When disabling the field, close the pop-up.
	 * 
	 * @see mataf.types.VisualPropertiesExchanger#setEnabled(boolean)
	 */
	public void setEnabled(boolean enabled) 
	{
		super.setEnabled(enabled);
		if(!enabled && isPopupOpen())
			cancelPopup();
	}
	
	/**
	 * Returns the clientOperation.
	 * @return MatafClientOp
	 */
	public String getClientOperationName() {
		return clientOperationName;
	}

	/**
	 * Sets the clientOperation.
	 * @param clientOperation The clientOperation to set
	 */
	public void setClientOperation(String clientOperationName) {
		this.clientOperationName = clientOperationName;
	}
	
		/**
	 * Returns the attachedTextField.
	 * @return MatafComboTextField
	 */
	public MatafComboTextField getAttachedTextField() {
		return attachedTextField;
	}

	/**
	 * Sets the attachedTextField.
	 * @param attachedTextField The attachedTextField to set
	 */
	public void setAttachedTextField(MatafComboTextField attachedTextField) {
		this.attachedTextField = attachedTextField;
	}

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
		
		JFrame f = new JFrame("Testing Table");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		f.getContentPane().setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		f.getContentPane().setLayout(new FlowLayout(FlowLayout.RIGHT));
		f.getContentPane().add(new JLabel("בחר סניף :"));
		
		MatafTableComboBoxButton tableBox = new MatafTableComboBoxButton(createExampleTable());
		
		tableBox.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				System.out.println("In ActionPerformed1");
				System.out.println(e.getSource());
			}
		});
				
		tableBox.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		tableBox.setBackground(new Color(255,255,204));
		f.getContentPane().add(tableBox);
		
		JComboBox box = new JComboBox();
		box.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		box.addItem("TEST1hjvkvhkjvhkhjvkhvkjhvkhvkhvkhvh");
		box.addItem("T2");
		box.addItem("TESTTEST3");
		box.addItem("TEST4");
		for(int i=0;i<20;i++)
			box.addItem("Auto"+i);
		
		f.getContentPane().add(box);
		
		f.setSize(640,280);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}
}
