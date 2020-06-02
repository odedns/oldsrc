package mataf.types.table;

import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.text.DecimalFormat;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Vector;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import mataf.exchangers.VisualPropertiesExchanger;
import mataf.logger.GLogger;
import mataf.types.MatafEmbeddedPanel;
import mataf.types.MatafTableComboBoxButton;
import mataf.types.MatafTextField;
import mataf.utils.FontFactory;
import mataf.utils.HebrewUtilities;

import com.ibm.dse.base.Context;
import com.ibm.dse.base.DSEException;
import com.ibm.dse.base.IndexedCollection;
import com.ibm.dse.base.KeyedCollection;
import com.ibm.dse.desktop.Desktop;
import com.ibm.dse.gui.Converter;
import com.ibm.dse.gui.CoordinatedEventListener;
import com.ibm.dse.gui.CoordinatedEventMulticaster;
import com.ibm.dse.gui.DSECoordinatedPanel;
import com.ibm.dse.gui.DSECoordinationEvent;
import com.ibm.dse.gui.DataChangedListener;
import com.ibm.dse.gui.DataExchangerWithList;
import com.ibm.dse.gui.ErrorMessageEvent;
import com.ibm.dse.gui.ErrorMessageEventMulticaster;
import com.ibm.dse.gui.ErrorMessageGenerator;
import com.ibm.dse.gui.ErrorMessageListener;
import com.ibm.dse.gui.Formatter;
import com.ibm.dse.gui.NavigationParameters;
import com.ibm.dse.gui.Settings;
import com.ibm.dse.gui.SpPanel;

/**
 * 
 * A swing table that implements the DataExchangerWithList interface.
 * 
 * This table has special functionality when opened as a pop-up table
 * from the MatafTableComboBoxButton class.<p>
 *
 * 
 * @author Nati Dykstein. Created : 09/06/2003
 * 
 */


public class MatafTable extends JTable 
							implements DataExchangerWithList,
										VisualPropertiesExchanger,
										ErrorMessageGenerator
{
	private static final Font TABLE_FONT = FontFactory.createFont("Arial",Font.PLAIN,12);
	
	/** The combo box that opens this table.*/
	private MatafTableComboBoxButton	combo;
	
	/** Mouse listener that sorts the table when clicking on it's header.*/
	private MatafTableModelSorter checksTableModelSorter;
	
	/** Keeps track of typed keys for the auto-search engine.*/
	private String			autoSearchString="";
	
	/** The column by which the model is currently sorted.*/
	private int			sortingColumn;
	
	/** States if the table has been initially sorted. */
	private boolean 		alreadySorted;
	
	/** 
	 * States that the table contents are dynamic, and would
	 * build from scratch on every invokation of refreshDataExchangers().
	 * An editable table is necessaraly dynamic but not the otherway around.
	 */
	private boolean		dynamic;
	
	/** DataExchangerWithList vars.(Boring)*/
	private boolean 					fieldConvertible;
	private String 					fieldDataName;
	private char 						defaultDecimalSeparator;
	private int 						defaultNbOfDecimals;
	private Formatter 					fieldFormatter;
	private String	 					fieldDataDirection;
	private boolean 					fieldActivatedOkKey;
	private String 					fieldAlternativeDataName;
	private String 					fieldDataToClear;
	private transient CoordinatedEventListener 	aCoordinatedEventListener;
	private transient ErrorMessageListener 		aErrorMessageListener;
	private String						dataNameForList;
	private String						fieldDescriptionName;
	
	static
	{
		UIManager.put("TableHeader.background", new Color(159,183,207));
		UIManager.put("TableHeader.foreground", Color.white);
		UIManager.put("TableHeader.font", 
										FontFactory.createFont("Arial", Font.PLAIN, 14));
	}
	
	public MatafTable()
	{
		setModel(new MatafTableModel(this));
		checksTableModelSorter = new MatafTableModelSorter(this);
		
		// Allow row sorting.
		getTableHeader().addMouseListener(checksTableModelSorter);
		
		//setAutoCreateColumnsFromModel(false);
		setUI();
		initComposerVars();
	}
	
	private void initComposerVars()
	{
		fieldConvertible 			= false;
		fieldDataName 				= new String();
		defaultDecimalSeparator		= ((DecimalFormat)(DecimalFormat.getInstance(Locale.getDefault()))).getDecimalFormatSymbols().getDecimalSeparator();
		defaultNbOfDecimals			= ((DecimalFormat)(DecimalFormat.getInstance(Locale.getDefault()))).getMaximumFractionDigits();
		/** By default the table only updates the context and not vice versa.*/
		fieldDataDirection 			= new String(Settings.BOTH_DIRECTION);
		fieldActivatedOkKey 		= true;
		fieldAlternativeDataName 	= new String();
		//fieldDataToClear			= new String(Settings.BOTH_DIRECTION +"  "+Settings.INPUT_DIRECTION +"  " + Settings.OUTPUT_DIRECTION);
	}
	
	/**
	 * Method assigns this table to a pop-up.
	 * It attaches additional listeners, updates the keyboard actions and
	 * prevents the table from getting the focus.
	 */
	public void setTableComboBox(MatafTableComboBoxButton combo)
	{
		this.combo = combo;
		attachListenersForComboMode();
		getOurModel().setKeyboardActions();
		setFocusable(false);
	}

	/**
	 * Adds additional listeners for wider functionality when
	 * displaying the table in a pop-up :
	 * 1. Moving the mouse over a row makes it selected.
	 * 2. Typing a word will auto-search it in the sorted column.
	 * 3. Losing focus will close the pop-up.
	 */
	private void attachListenersForComboMode()
	{
		addMouseListener(new MouseAdapter()
		{
			/**
			 * Update selection and close pop-up.
			 * (Active only when used in a pop-up)
			 */
			public void mouseClicked(MouseEvent e)
			{
				handleSelectionFromCombo();
			}
		});
		
		/**
		 * Selects row on mouse over.
		 * (Active only when used in a pop-up)
		 */
		addMouseMotionListener(new MouseMotionAdapter() 
		{
			public void mouseMoved(MouseEvent e) 
			{
				Point p = e.getPoint();
            	int row = rowAtPoint(p);
            	int column = columnAtPoint(p);
	    		// The autoscroller can generate drag events outside the Table's range.
            	if ((row == -1) || (column == -1)) 
                	return;
            	changeSelection(row, column, e.isControlDown(), e.isShiftDown());
			}
		});
		
		/**
		 * Enables auto-search by typing.
		 * (Active only when used in a pop-up)
		 */
		addKeyListener(new KeyAdapter() 
		{
			public void keyPressed(KeyEvent e) 
			{
				activateTypeInAutoSearch(e);
			}
		});
	}
	
	/**
	 * This method builds a search string according to the user 
	 * typed-in keys, locates and selects the matching row.<p>
	 * 
	 * The search is conducted against the current sorting column ,
	 * and the search is said to be successfull if there is a row that
	 * it's sorting column starts with the typed-in search string.<p>
	 * 
	 * If search fails then another attempt is automatically made ,
	 * this time, without the previously typed letters.<p>
	 * 
	 * Example : If after typing '123' we type '4' and
	 *           no row that has it's sorting column start with 
	 * 			 the string '1234' is found , 
	 * 			 then another attempt is automatically made to find 
	 * 			 a row that it's sorting column starts with '4'.
	 */
	private void activateTypeInAutoSearch(KeyEvent e)
	{
		int keyPressed 		= e.getKeyChar();
		MatafTableModel model 	= (MatafTableModel)getModel();
		int column 			= getSortingColumn();
		
		// No column is currently selected.
		if (column == -1)
    		return;
		
		// If key pressed is not a digit convert it to hebrew;
		if(!Character.isDigit((char)keyPressed))
			keyPressed =
				HebrewUtilities.mapUSKeysToHebrewKeys(e.getKeyCode());

		// Concatenate to our search string.
		autoSearchString += (char)keyPressed;
		
		// Make first search attempt.
		int row = model.searchColumnFor(column, autoSearchString);
    	
    	// If first search attempt failed, auto-retry without appending.
    	if(row == -1)
    	{
			// If key pressed is not a digit convert it to hebrew.
			if(!Character.isDigit((char)keyPressed))
				keyPressed = 
					HebrewUtilities.mapUSKeysToHebrewKeys(e.getKeyCode());
			
			// Reset our search string with 'keyPressed'.
			autoSearchString = "" + (char)keyPressed;

			// Re-search (Second search attempt).
			row = model.searchColumnFor(column, autoSearchString);
        }
 
       	// Check that the second attempt did not fail.
    	if(row!=-1)
			changeSelection(row, column, e.isControlDown(), e.isShiftDown());
	}
	
	/**
	 * Closes the pop-up in which the table is displayed.
	 */
	private void closePopup()
	{	
		if(combo.isPopupOpen())
			combo.togglePopup();
	}
	
	/**
	 * Cancels the pop-up in which the table is displayed.
	 */
	void cancelPopup()
	{
		combo.cancelPopup();
	}
	
	/**
	 * Clears the keys that were typed so far in the auto-search string.
	 * Method is to be used by other classes.
	 */
	public void resetSearchString()
	{
		autoSearchString="";
	}
	
	/**
	 * This method closes the table-popup upon selection of a row.
	 * This method is used internally by MatafTable and it's sub-componenets.
	 */
	void handleSelectionFromCombo()
	{
		// Reset search string.
		autoSearchString="";
		closePopup();
	}		
	
	/**
	 * Returns whether this table is opened from a combo-box.
	 */
	boolean isOpenedFromCombo()
	{
		return combo != null;
	}		
	
	/**
	 * Returns a specific cell in the table as string.
	 */
	public String getCellAsString(int row, int column)
	{
		MatafTableModel model = (MatafTableModel)getModel();
		return	""+model.getValueAt(row, column);
	}
	
	/**
	 * Sets the specific UI of MatafTable.
	 * This includes the renderer for the table headers, and attaching listeners.
	 */
	private void setUI()
	{
		setSurrendersFocusOnKeystroke(true);
		setFont(TABLE_FONT);
		setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		getTableHeader().setReorderingAllowed(false);
		setGridColor(Color.white);
		
		getTableHeader().setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		
		//setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		// Set selection mode.
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		//setRowSelectionAllowed(false);
		
		// Allow ESCAPE key close the transaction.
		addKeyListener(new KeyAdapter()
		{
			public void keyPressed(KeyEvent e)
			{
				if(KeyEvent.getKeyText(e.getKeyCode()).equals("Escape"))
				{
					Component c = MatafTable.this.getParent();
					while(!(c instanceof MatafEmbeddedPanel))
						c = c.getParent();
					if(c==null)
						GLogger.debug("Table is not contained in a MatafEmbeddedPanel!, Could not close panel.");
					else
						((MatafEmbeddedPanel)c).activateCloseButton();
				}
			}
		});
		
		/**
		 * Makes the selectedRowIndex in the context always contain the 
		 * currently selected row in the table.
		 */
		getSelectionModel().addListSelectionListener(new ListSelectionListener() 
		{
			public void valueChanged(ListSelectionEvent e) 
			{
				if(e.getValueIsAdjusting())
					return;
				
				int selectedRow = ((DefaultListSelectionModel)e.getSource()).getAnchorSelectionIndex();
				
				updateContextWithSelectedRowIndex(selectedRow);
			}
		});
	}
	
	/**
	 * Update the context with the selected row index.
	 * 
	 * @param selectedRow - The currently selected row.
	 */
	private void updateContextWithSelectedRowIndex(int selectedRow)
	{			
		DSECoordinatedPanel cPanel = getDSECoordinatedPanel();
		
		// If Coordinated panel is null, then the view is in construction.
		if(cPanel==null)
			return;
			
		Context ctx = cPanel.getContext();
		
		// Construct the meta data name with postfixing to the 
		// table's dataNameForList.
		String metaDataName = getDataNameForList()+"_MetaData";
		
		try
		{
			ctx.setValueAt(metaDataName + "." + "selectedRowIndex", String.valueOf(selectedRow));
		}
		catch(DSEException e)
		{
			GLogger.warn("selectedRowIndex field could not be found in context : " +
						 ctx.getName() + " for table "+getDataNameForList());
		}
	}
	
	private void setSelectedRowIndexFromContext() {
		DSECoordinatedPanel cPanel = getDSECoordinatedPanel();
		
		// If Coordinated panel is null, then the view is in construction.
		if(cPanel==null)
			return;
		Context ctx = cPanel.getContext();

		// Construct the meta data name with postfixing to the 
		// table's dataNameForList.
		String metaDataName = getDataNameForList()+"_MetaData";
		int index = -1;
		try {
				String str = (String) ctx.getValueAt(metaDataName + "." + "selectedRowIndex");
			 index = Integer.parseInt((String)ctx.getValueAt(metaDataName + "." + "selectedRowIndex"));
		} catch (Exception e) {
			GLogger.warn("selectedRowIndex field could not be found in context : " +
									 ctx.getName() + " for table "+getDataNameForList());
			return;
		}
		if (index>=0)	
			setRowSelectionInterval(index, index);
	}
	
	
	public void updateContextWithRowInTableModel(int changedRowIndex)
	{
		getOurModel().updateContextWithRowInTableModel(changedRowIndex);
	}
	
	
	/**
	 * Convenient method for saving extra casting when referencing the model.
	 */
	public MatafTableModel getOurModel()
	{
		return (MatafTableModel)getModel();
	}
	
//	/**
//	 * Mimic the method from SpTable.
//	 * This method is deprecated - Use AddColumn() instead.
//	 * @deprecated 
//	 */
//	public void setDataNameAndColumns_TO_BE_REMOVED(VectorEditor vec)
//	{
//		//getOurModel().setDataNameAndColumns(vec);
//	}
	
	/**
	 * Adds a new empty row to the table.
	 */
	public void addRow(Vector row)
	{
		getOurModel().addRow(row);
	}
	
	/**
	 * If Table is editable make updates occur from the table
	 * to the context and vice versa.
	 * An editable table is considered dynamic.
	 */
	public void setEditable(boolean editable)
	{
		if(editable)
		{
			setDynamic(true);
			fieldDataDirection = Settings.BOTH_DIRECTION;
		}
		else
			fieldDataDirection = Settings.OUTPUT_DIRECTION;
			
		getOurModel().setEditable(editable);
	}
	
	/**
	 * Returns wether the table is editable.
	 */
	public boolean isEditable()
	{
		return getOurModel().isEditable();
	}
	
	public void setSortable(boolean sortable)
	{
		((MatafTableModel)getModel()).setSortable(sortable);
	}
	
	/**
	 * Hide columns that are not ment for displaying.
	 * These columns are created for their data in the model
	 * to be used.
	 * The order of indices affected is descending.
	 */
	public void setNumberOfHiddenColumns(int numberOfHiddenColumns)
	{
		for(int i=0;i<numberOfHiddenColumns;i++)
		{
			removeColumn(getColumnModel().getColumn(getColumnCount()-1));
		}
		
		getOurModel().setNumberOfHiddenColumns(numberOfHiddenColumns);
	}
	
	public Hashtable getRenderersHashtable()
	{
		return defaultRenderersByColumnClass;
	}
	
	/**
	 * Sets the maxChars property for the specified column.
	 * This method is will effect only editors which are instance of MatafTextField.
	 */
	public void setMaxCharsForColumn(int columnIndex, int maxChars)
	{
		Component c = getColumnModel().getColumn(columnIndex).getCellEditor().getTableCellEditorComponent(this,"",false,0,columnIndex);
		if(c instanceof MatafTextField)
		{
			((MatafTextField)c).setMaxChars(maxChars);
		}
	}
	
	/**
	 * Attaches NavigationParameters to a specific column.
	 */
	public void setNavigationParametersForColumn(int columnIndex, NavigationParameters param)
	{
		MatafTextField f = getTextEditorAtColumn(columnIndex);
		f.setType("Execute_Operation");
		f.setNavigationParameters(param);
	}
	
	/**
	 * Convenient method for referencing the editor component of 
	 * a specified column.
	 */
	public Component getEditorComponentAtColumn(int columnIndex)
	{
		MatafTableCellEditor cellEditor = 
			(MatafTableCellEditor)getColumnModel().getColumn(columnIndex).getCellEditor();
		return cellEditor.getComponent();
	}
	
	/**
	 * Convenient method for referencing the text editor of a specified column.
	 * If the editor component found in the specified column is not an
	 * instance of the default text editor (MatafTextField), the method will
	 * return null.
	 */
	public MatafTextField getTextEditorAtColumn(int columnIndex)
	{
		Component comp = getEditorComponentAtColumn(columnIndex);
		if(comp instanceof MatafTextField)
			return (MatafTextField)comp;
		else
			return null;
	}
	
	/**
	 * Update whether a specific cell is in error.
	 */
/*	public void setCellInError(int row, int column, boolean inError)
	{
		getOurModel().setCellInError(row, column, inError);
	}*/
	
	/**
	 * Returns whether a cell is in error.
	 * A Cell is in error if (inError==true || inErrorFromServer==true).
	 */
	public boolean isCellInError(int row, int column)
	{
		return getOurModel().isCellInError(row, column);
	}
	
	/**
	 * Returns the sortingColumn.
	 * @return int
	 */
	public int getSortingColumn() {
		return sortingColumn;
	}

	/**
	 * Sets the sortingColumn.
	 * @param sortingColumn The sortingColumn to set
	 */
	public void setSortingColumn(int sortingColumn) {
		this.sortingColumn = sortingColumn;
	}
	
	/**
	 * @see javax.swing.JTable#prepareRenderer(TableCellRenderer, int, int)
	 */
	public Component prepareRenderer(TableCellRenderer renderer, int row, int column) 
	{
		return super.prepareRenderer(renderer, row, column);
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.JTable#prepareEditor(javax.swing.table.TableCellEditor, int, int)
	 */
	public Component prepareEditor(TableCellEditor editor, int row, int column)
	{
		return super.prepareEditor(editor, row, column);
	}


	
	
	/////////////////// DataExchangerWithList Implementation ////////////////////////////////////////////////////////
	
	/**
	 * @see com.ibm.dse.gui.DataExchanger#addDataChangedListener(DataChangedListener)
	 */
	public void addDataChangedListener(DataChangedListener adl) {
	}

	/**
	 * @see com.ibm.dse.gui.DataExchanger#getAlternativeDataName()
	 */
	public String getAlternativeDataName() {
		return fieldAlternativeDataName;
	}

	/**
	 * Returns the converter associated to the label.
	 * @return com.ibm.dse.gui.Converter - the converter associated to the label 
	 */
	public Converter getConverter() 
	{
		return null;
	}

	/**
	 * @see com.ibm.dse.gui.DataExchanger#getDataDirection()
	 */
	public String getDataDirection() {
	return fieldDataDirection;
	}

	/**
	 * @see com.ibm.dse.gui.DataExchanger#getDataName()
	 */
	public String getDataName() {
		return fieldDataName;
	}
	
	public String getDescriptionDataName()
	{
		return fieldDescriptionName;
	}

	/**
	 * Return the value from the first column.
	 */
	public Object getDataValue() 
	{
		return ((MatafTableModel)getModel()).getSelectedValue(0);
	}
	
	/**
	 * Returns the description value from the second column.
	 */
	public Object getDataDescriptionValue()
	{
		return ((MatafTableModel)getModel()).getSelectedValue(1);
	}
	
	/**
	 * Gets the DSECoordinatedPanel where the component is placed
	 * @return com.ibm.dse.gui.DSECoordinatedPanel
	 */
	public DSECoordinatedPanel getDSECoordinatedPanel() {
		if (isOpenedFromCombo())
			return DSECoordinatedPanel.getDSECoordinatedPanel(combo);
		return DSECoordinatedPanel.getDSECoordinatedPanel(this);
	}

	/**
	 * @see com.ibm.dse.gui.DataExchanger#getErrorMessage()
	 */
	public String getErrorMessage() 
	{
		return "Empty Error Message";
	}
	
	public void setErrorMessage(String errorMessage)
	{
		if(getConverter()==null)
			return;
		getConverter().setErrorMessage(errorMessage);
	}
	
	/**
	 * Default implementation for tables.
	 */
	public void setInErrorFromServer(boolean errorFromServer)
	{}

	/**
	 * Gets the formatter property (com.ibm.dse.gui.Formatter) value.
	 * @return Formatter - the formatter property value
	 * @see #setFormatter
	 */
	public Formatter getFormatter() {
		return fieldFormatter;
	}
	
	/**
	* Formats the label value using the label formatter.
	*/
	public String getSourceValueForConversion() 
	{
		return null;
	}

	/**
	 * Receives a String formatted with the the float formatter corresponding to the default Locale.
	 * Returns the same value formatted as standard float
	 */
	public String getSourceValueForConversion2(String text) 
	{
		return null;	
	}

	/**
	 * Gets the SpPanel where the component is placed
	 * @return com.ibm.dse.gui.SpPanel
	 */
	public SpPanel getSpPanel() {
		return SpPanel.getSpPanel(this);
	}

	/**
	 * @see com.ibm.dse.gui.DataExchanger#hasAlternativeDataName()
	 */
	public boolean hasAlternativeDataName() {
		return false;
	}

	/**
	 * @see com.ibm.dse.gui.DataExchanger#isInError()
	 */
	public boolean isInError() {
		return false;
	}

	/**
	 * @see com.ibm.dse.gui.DataExchanger#isKeyedValue()
	 */
	public boolean isKeyedValue() {
		return false;
	}

	/**
	 * @see com.ibm.dse.gui.DataExchanger#isRequired()
	 */
	public boolean isRequired() {
		return false;
	}

	/**
	 * @see com.ibm.dse.gui.DataExchanger#removeDataChangedListener(DataChangedListener)
	 */
	public void removeDataChangedListener(DataChangedListener dcl) {
	}

	/**
	 * @see com.ibm.dse.gui.DataExchanger#setAlternativeDataName(String)
	 */
	public void setAlternativeDataName(String alternativeDataName) {
	fieldAlternativeDataName = alternativeDataName;
	}

	/**
	 * Sets the convertible property (boolean) value.
	 * @param convertible boolean, the new value for the property
	 * @see #getConvertible
	 */
	public void setConvertible(boolean convertible) {
		fieldConvertible = convertible;
	}

	/**
	 * Sets the dataDirection property (java.lang.String) value.
	 * @param dataDirection String, the new value for the property
	 * @see #getDataDirection
	 */
	public void setDataDirection(String dataDirection) {
		fieldDataDirection = dataDirection;
	}

	/**
	 * Sets the dataName property (java.lang.String) value.
	 * @param dataName String, the new value for the property
	 * @see #getDataName
	 */
	public void setDataName(String dataName) {
		fieldDataName = dataName;
	}
	
	public void setDescriptionDataName(String descriptionDataName) {
		fieldDescriptionName = descriptionDataName;
	}

	/**
	 * Sets the dataToClear property (java.lang.String) value.
	 * @param dataToClear The new value for the property.
	 * @see #getDataToClear
	 */
	public void setDataToClear(java.lang.String dataToClear) {
		fieldDataToClear = dataToClear;
	}
	
	/**
	 * Sets the dataValue property (Object) value.
	 * @param dataValue Object, the new value for the property
	 * @see #getDataValue
	 */
	public void setDataValue(Object dataValue) 
	{	
		
	}

	/**
	 * Sets the dataValue property (java.lang.String) value.
	 * @param value String, the new value String for the dataValue property 
	 */
	public void setDataValueAsObject(String value) 
	{

	}

	/**
	 * Sets the formatter property (com.ibm.dse.gui.Formatter) value.
	 * @param formatter Formatter, the new value for the property
	 * @see #getFormatter
	 */
	public void setFormatter(Formatter formatter) {
		fieldFormatter = formatter;
		if (formatter instanceof Converter)
			setConvertible(((Converter)formatter).getIsConvertible());
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
		return dataNameForList;
	}

	/**
	 * Sets out dataNameForList.
	 */
	public void setDataNameForList(String indexedCollectionName) 
	{
		dataNameForList = indexedCollectionName;
	}

	/**
	 * Update the table's model with the context's IndexedCollection.
	 * This method has been optimized to operate only on dynamic tables.
	 * Non-dynamic tables will only be built once.
	 */
	public void setDataValueForList(Object dataValueForList)
	{
		// If table has already been build and is not dynamic - return.
		if(getOurModel().getRowCount()!=0 && !isDynamic())
			return;
		
		//GLogger.debug("Building Table "+getDataNameForList()+" From Context!.");
		
		//IndexedCollection ic = new IndexedCollection();
		//ic.setElements((com.ibm.dse.base.Vector)((IndexedCollection)dataValueForList).getValue());
		
		// Build the table's model from the context's indexed collection.
		getOurModel().buildDataFromIndexedCollection((IndexedCollection)dataValueForList);
	
		setDataValue(getDataValue());

		// Sort first column by default.
		if(getOurModel().isSortable() && !alreadySorted)
		{
			alreadySorted = true;			
			checksTableModelSorter.sort(0);
			MatafTableModel model = getOurModel();
			for (int i=0; i<getColumnCount(); i++) {
				model.updateContextWithRowInTableModel(i);
			}			
		}
		setSelectedRowIndexFromContext();
		
		DSECoordinationEvent newEvent = new DSECoordinationEvent(this);
		newEvent.setRefresh(true);
		newEvent.setEventType(DSECoordinationEvent.EVENT_EVENTYPE_COORDINATION);
		newEvent.setEventSourceType(DSECoordinationEvent.EVENT_SOURCETYPE_DEFAULT);
		newEvent.setEventName(getName() + ".dataChanged");
		fireCoordinationEvent(newEvent);
	}
	
	public void fireCoordinationEvent(DSECoordinationEvent event)
	{
		if (aCoordinatedEventListener == null)
			return;
		
		aCoordinatedEventListener.handleDSECoordinationEvent(event);
	}
	
	/**
	 * Informs that and error in the text field has happened.
	 * @param event ErrorMessageEvent
	 */
	public void fireHandleErrorMessage(ErrorMessageEvent event) {
		if (aErrorMessageListener == null) {
			return;
		};
		aErrorMessageListener.handleErrorMessage(event);
	}
	
	/**
	 * @see com.ibm.dse.gui.DataExchanger#addCoordinatedEventListener(CoordinatedEventListener)
	 */
	public void addCoordinatedEventListener(CoordinatedEventListener newListener)	
	{
		aCoordinatedEventListener = CoordinatedEventMulticaster.add(aCoordinatedEventListener, newListener);
	}
	
	/**
	 * @see com.ibm.dse.gui.DataExchanger#removeCoordinatedEventListener(CoordinatedEventListener)
	 */
	public void removeCoordinatedEventListener(CoordinatedEventListener arg0) {
	}
	
	/**
	 * Adds an ErrorMessageListener.
	 * @param newListener ErrorMessageListener
	 */
	public void addErrorMessageListener(ErrorMessageListener newListener) {
		aErrorMessageListener = ErrorMessageEventMulticaster.add(aErrorMessageListener, newListener);
		return;
	}
	
	/**
	 * Removes an ErrorMessageListener.
	 * @param newListener ErrorMessageListener
	 */
	public void removeErrorMessageListener(ErrorMessageListener newListener) {
		aErrorMessageListener = ErrorMessageEventMulticaster.remove(aErrorMessageListener, newListener);
		return;
	}
	

	/**
	 * @see com.ibm.dse.gui.DataExchanger#addActionListener(ActionListener)
	 */
	public void addActionListener(ActionListener arg0) {
	}

	

	/**
	 * @see com.ibm.dse.gui.DataExchanger#getNavigationParameters()
	 */
	public NavigationParameters getNavigationParameters() {
		return null;
	}

	/**
	 * @see com.ibm.dse.gui.PanelActions#getType()
	 */
	public String getType() {
		return null;
	}

	/**
	 * @see com.ibm.dse.gui.DataExchanger#removeActionListener(ActionListener)
	 */
	public void removeActionListener(ActionListener arg0) {
	}

	
	/**
	 * @see com.ibm.dse.gui.DataExchanger#setHelpID(String)
	 */
	public void setHelpID(String arg0) {
	}

	/**
	 * @see com.ibm.dse.gui.DataExchanger#setNavigationParameters(NavigationParameters)
	 */
	public void setNavigationParameters(NavigationParameters arg0) {
	}

	/**
	 * @see com.ibm.dse.gui.DataExchanger#setType(String)
	 */
	public void setType(String arg0) {
	}

	/**
	 * @see com.ibm.dse.gui.PanelActions#getDataToClear()
	 */
	public String getDataToClear() {
		return null;
	}

	/**
	 * @see mataf.types.VisualPropertiesExchanger#exchangeVisualProperties()
	 */
	public void exchangeVisualProperties(Context ctx) 
	{
		ADAPTER.exchangeVisualProperties(ctx, this);
	}
	
	
	/**
	 * @return true - if the table model is subjected to changes after construction.
	 */
	public boolean isDynamic()
	{
		return dynamic;
	}

	/**
	 * Indicates that this table should rebuild itself on each call
	 * to refreshDataExchangers().
	 * This is false by default.
	 * @param dynamic - true if the table model is subjected to changes after construction.
	 */
	public void setDynamic(boolean dynamic)
	{
		this.dynamic = dynamic;
	}
	
	
	
	public static void main(String[] args)
	{		
		JFrame f = new JFrame("Testing Table");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
		MatafTable mt = new MatafTable();
		f.getContentPane().add(new JScrollPane(mt));
	
		f.setSize(640,480);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

}
