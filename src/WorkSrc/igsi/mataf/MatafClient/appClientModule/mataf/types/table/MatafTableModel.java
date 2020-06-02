package mataf.types.table;

import java.awt.Component;
import java.awt.Dimension;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import mataf.data.VisualDataField;
import mataf.logger.GLogger;
import mataf.types.MatafCheckBox;
import mataf.types.MatafComboTextField;
import mataf.types.MatafOptionPane;
import mataf.types.MatafTextField;
import mataf.types.textfields.MatafDecimalField;
import mataf.types.textfields.MatafNumericField;
import mataf.types.textfields.MatafStringField;

import com.ibm.dse.base.Context;
import com.ibm.dse.base.DSEException;
import com.ibm.dse.base.DataElement;
import com.ibm.dse.base.IndexedCollection;
import com.ibm.dse.base.KeyedCollection;
import com.ibm.dse.gui.DSECoordinatedPanel;

/**
 * This class is the model for the checks table.
 * Keys functionality is managed by <code>ChecksTableActionsBinder</code>.
 * 
 * Table uses MatafTableCellEditor as it's default editor and 
 * MatafTableCellRenderer as it's default renderer.
 * 
 * @see MatafTableActionsBinder.
 * 
 * @author Nati Dykshtein. Creation Date : (09/06/2003 16:23:25).  
 */
public class MatafTableModel extends AbstractTableModel
{
	/** 2-Dimensional Vector containing table's data.*/
	private Vector		data 				= new Vector();
	/** Contains column names.*/
	private Vector 	columnNames 		= new Vector();
	/** Contains dataNames.*/	
	private Vector		columnDataNames 	= new Vector();
	/** Contains the Class.type of each column.*/
	private Vector		columnClassTypes 	= new Vector();
	
	/** An empty KeyedCollection representing a row.*/
	private KeyedCollection emptyKColl;
	
	private Hashtable	errorsHashtable 	= new Hashtable();
	
	private String	tableNameInContext 		= "myTableModel";
	
	private String selectedRowDataName;
	
	private String keyedCollectionName;
	
	private int	numberOfHiddenColumns;
	
	private MatafTable			table;
	
	/** True if table is editable. */
	private boolean			editable;
	
	/** True if table is sortable. */
	private boolean			sortable = true;
	
//////////////////////////////////////////////////////////////////////////////
	
	public MatafTableModel(MatafTable table)
	{	
		super();	
		this.table = table;
		createModel();
	}
	
//////////////////////////////////////////////////////////////////////////////
	
	/** 
	 * Create the model from the context.
	 */
	private void createModel()
	{
		resetModel();				
		setKeyboardActions();
	}
	
//////////////////////////////////////////////////////////////////////////////


	
//////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Creates the table model from an IndexedCollection. (Context -> Table)
	 */
	void buildDataFromIndexedCollection(IndexedCollection iColl)
	{
		Vector iColElements = iColl.getElements();
		Vector rowKeys, rowValues;
		
		Vector newData = new Vector();
		
		// Iterate through the rows in the context.
		for(int row=0;row<iColElements.size();row++)
		{
			// Get the a row from the context.
			KeyedCollection kCol = 
				(KeyedCollection)iColElements.elementAt(row);
			
			// Get the keys of the row's KColl as a Vector.
			rowKeys = (Vector)kCol.getOrderVector();
			
			// Create an empty row to fill. 
			rowValues = new Vector(rowKeys.size());
			
			// Iterate through the columns in the row.
			for(int col=0;col<getColumnCount();col++)
			{
				// Get the column's dataname.
				String key = (String)columnDataNames.elementAt(col);
								
				// Get rid of the KeyedCollection prefix.
				int indexOfPoint = key.indexOf(".")+1;
				key = key.substring(indexOfPoint);
				
				Object value = kCol.tryGetValueAt(key);
				
				// Add value to newly created row.
				rowValues.addElement(value);
				
				// Process extra data from the KColl element.
				processAdditionalData(row, col, kCol, key);
			}
			
			// Add row to newly create table model.
			newData.addElement(rowValues);
		}
		
		// Replace old model with new model.
		data = newData;

		// Notify the table about the change in the model.
		if(data.size()!=0)
		{
			// Remember selected row prior to tableChanged() invokation.
			// (Since 1.3 it clears the selection.)
			int selectedRow = table.getSelectedRow();
			
			fireTableDataChanged();
			
			// Reselect the row (Only if there was a selection).
			if(selectedRow!=-1)
				table.setRowSelectionInterval(selectedRow, selectedRow);
		}
		else
			fireTableDataChanged();
	}
	
	/**
	 * Updates the context with changes made to the selected row.
	 * Activated upon focus lost of a cell editor. (Table Row -> Context)
	 */
	void updateContextWithRowInTableModel(int changedRowIndex)
	{
		// Get our context.
		Context ctx = null;
		
		try
		{
			ctx = table.getDSECoordinatedPanel().getContext();
		}
		catch(NullPointerException e)
		{
			// We're not residing in a DSECoordinated panel.
			return;
		}
		
		// Get the table's indexed collection from the context.
		IndexedCollection contextIC = 
				(IndexedCollection) ctx.tryGetElementAt(table.getDataNameForList());

		// Create destination vector from the context indexed collection.
		com.ibm.dse.base.Vector contextVec = 
								(com.ibm.dse.base.Vector)contextIC.getElements();

		// Get the changed row as a KeyedCollection.
		KeyedCollection changedRowKColl = 
				getRowFromDataAsKeyedCollection(changedRowIndex);
		
		// Copy model's row to context's row.
		// We're adding a new element to the context.
		if(changedRowIndex==contextVec.size())
			contextVec.add(changedRowKColl);
		else // We're changing an existing row in the context.
		{		
			if(changedRowIndex<contextVec.size())
				contextVec.setElementAt(changedRowKColl, changedRowIndex);
			else
			{ 
				// This case should not happend unless 2 
				// consecutive insertions were made.
			}
		}
	}
			
	/**
	 * Return a row from the table's model as a new KeyedCollection.(Table Row -> KeyedCollection)
	 */
	KeyedCollection getRowFromDataAsKeyedCollection(int row)
	{
		KeyedCollection kColl = null;
		try
		{
			kColl = (KeyedCollection)getEmptyKColl().clone();
		}
		catch (CloneNotSupportedException e1)
		{
			e1.printStackTrace();
		} 
		
		try
		{
			for(int column=0;column<getColumnCount();column++)
			{
				// Get the complete data name from the KeyedCollection.
				String key = (String)columnDataNames.elementAt(column);
				
				// Find the prefix.
				int indexOfPoint = key.indexOf(".")+1;
				
				// Get rid of the prefix.
				key = key.substring(indexOfPoint);
				
				// Get the current value from the table's model.
				Object value = getValueAt(row,column);
				
				// Set addition data related to the element.
				setAdditionalData(row, column , kColl, key);
								
				// Set the value into the right element at the KeyedCollection.
				kColl.setValueAt(key,value);
			}
		}		
		catch(DSEException e) 
		{
			e.printStackTrace();
		}
		
		return kColl;
	}
	
	
	/**
	 * Removes a row from the context's IndexedCollection.
	 * 
	 * @param rowToRemove
	 */
	void removeRowFromContext(int rowToRemove)
	{
		// Get our context.
		Context ctx = table.getDSECoordinatedPanel().getContext();
		
		// Get the table's indexed collection from the context.
		IndexedCollection contextIC = 
				(IndexedCollection) ctx.tryGetElementAt(table.getDataNameForList());

		// Create a vector from the context indexed collection.
		com.ibm.dse.base.Vector contextVec = 
								(com.ibm.dse.base.Vector)contextIC.getElements();
		
		// Create an empty KeyedCollection.
		KeyedCollection emptyKColl = getEmptyKColl();
		
		// Remove the row from the context.
		if(rowToRemove<contextVec.size())
			contextVec.remove(rowToRemove);
		
		// If we've deleted all the rows, add an empty row.
		if(contextVec.size()==0)		
			contextVec.add(emptyKColl);
	}

	
	/**
	 * Process additional data related to the value in the cell.
	 * This additional data is described in the properites of the
	 * VisualDataField.
	 * 
	 * @param row - The row index of the cell.
	 * @param col - The column index of the cell
	 * @param kColl - The KeyedCollection containing the elements.
	 * @param key - The key by which to retrieve the element.
	 */
	private void processAdditionalData(int row, int column, KeyedCollection kColl, String key)
	{
		DataElement element = kColl.tryGetElementAt(key);
		if(element instanceof VisualDataField)
		{
			VisualDataField visualField = (VisualDataField)element;
			updateErrorStateAt(row, column, visualField);
		}
	}
	
	/**
	 * Prepares additional data related to the value in the cell.
	 * This data is inserted to the element prior to sending it to the context.
	 * The additional data is described in the properites of the
	 * VisualDataField.
	 * 
	 * @param row - The row index of the cell.
	 * @param col - The column index of the cell
	 * @param kColl - The KeyedCollection containing the elements.
	 * @param key - The key by which to retrieve the element.
	 */
	private void setAdditionalData(int row, int column, KeyedCollection kColl, String key)
	{
		DataElement element = kColl.tryGetElementAt(key);
		if(element instanceof VisualDataField)
		{
			VisualDataField visualField = (VisualDataField)element;
			if(isCellInError(row, column))
				visualField.setErrorFromServer(getErrorMessageAt(row, column));
		}
	}


	/**
	 * Method uses an external class to bind keys to this component.
	 * 
	 * This method is also invoked when setting a table comboBox for this
	 * table in order to add some additional keys functionalities.
	 */
 	void setKeyboardActions()
	{
		new MatafTableActionsBinder(table, this).bindKeys();
	}			
//////////////////////////////////////////////////////////////////////////////

	boolean openDeletionConfirmationDialog()
	{
		int a = MatafOptionPane.showConfirmDialog(table,
											  "למחוק את השורה המסומנת ?",
											  "מחיקת שורה",
											  JOptionPane.YES_NO_OPTION,
											  JOptionPane.QUESTION_MESSAGE);

		return a==JOptionPane.YES_OPTION ? true : false;
	}
	
//////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns true is we are currently on the last VISIBLE cell in the last row.
	 * Note : The table may contain invisible columns so data.size() does not 
	 * 		  necessaraly represents the last visible row.
	 */
	boolean isOnLastCell()
	{
		int row = table.getSelectedRow();
		int col = table.getSelectedColumn();				

		return ((row==data.size()-1) && (col==getVisibleColumnCount()-1));
	}
	
//////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns true if selected row is empty.
	 */
	boolean selectedRowIsEmpty()
	{
		int row = table.getSelectedRow();
		
		for(int i=0;i<columnNames.size();i++)
		{
			Object val = getValueAt(row,i);
			
			// Booleans are ignored.
			if (val instanceof Boolean)
				continue;
			// 'null' value is considered empty.
			if(val==null)
				continue;
			if( !(val.equals("")))
				return false;
		}		
		return true;
	}
	
//////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Adds a new empty row to the table.
	 */
	public void appendNewRow()
	{
		if(data.size()==getMaxRows())
		{
			GLogger.warn("Cannot exceed maximum number of rows in table.");
			return;
		}
		
		// Create new empty row.
		Vector newRow = new Vector();
		for(int i=0;i<columnNames.size();i++)
		{
			if(getColumnClass(i).equals(Boolean.class))
			{
				newRow.addElement(new Boolean(false));			
			}			
			else
			{
				newRow.addElement(new String(""));
			}
		}
				
		// Add new row to the model.
		data.addElement(newRow);
		
		int indexOfAddedRow = getRowCount()-1;
		
		fireTableRowsInserted(indexOfAddedRow, indexOfAddedRow);

		// Set row selection to new row.
		table.setRowSelectionInterval(indexOfAddedRow, indexOfAddedRow);
		// Set column selection to the start of the table.
		table.setColumnSelectionInterval(0,0);
		
		// PENDING : Auto-Scroll down to make new line visible.
		
		// Create the new row in context as well.
		table.updateContextWithRowInTableModel(indexOfAddedRow);
		
		GLogger.debug("Row added.");
	}
	
//////////////////////////////////////////////////////////////////////////////

	/**
	 * Remove the currently selected row.
	 */
	public void removeSelectedRow()
	{
		removeRow(table.getSelectedRow());
	}
	
	/**
	 * Removes the specified row from the table and from the context.
	 *
	 * @param rowIndexToRemove The index of the row to remove.
	 */
	public void removeRow(int rowIndexToRemove)
	{
		// There is only one row in the table, just clear it.
		if(getRowCount()==1)
		{
			clearRow(0);
			table.repaint();		
		}
		else
		{	
			// Remove selected row.
			data.removeElementAt(rowIndexToRemove);

			// Notify the table about the deletion of the row.
			fireTableRowsDeleted(rowIndexToRemove, rowIndexToRemove);
		}
		
		// Clear errors from the cellInError hashtable.
		//clearErrorsForRow(rowIndexToRemove);

		// By default the selection will stay in place.
		int newSelectionIndex = rowIndexToRemove;
		
		// If last row was deleted, select the previous row.		
		if(rowIndexToRemove==getRowCount())
			newSelectionIndex--;			
		
		// Update selection.
		table.changeSelection(newSelectionIndex, 0, false, false);				
		
		// Remove row from the context also.
		removeRowFromContext(rowIndexToRemove);
	}

//////////////////////////////////////////////////////////////////////////////

	/**
	 * Clears the row contents.
	 */
	private void clearRow(int rowIndex)
	{
		Vector row = (Vector)(data.elementAt(rowIndex));
		
		for(int i=0;i<columnNames.size();i++)
		{
			Object val = row.elementAt(i);
			if(val instanceof String)
				row.setElementAt(new String(),i);
			if(val instanceof Boolean)
				row.setElementAt(new Boolean(false),i);
		}
	}
	
//////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Clears the table's model.
	 */
	private void resetModel()
	{
		columnDataNames.clear();
		columnNames.clear();
		data.clear();
	}
	
///////////////////////////////////////////////////////////////////////////////
			
	/**
	 * Creates a special header that enables sorting the columns by clicking.
	 * @deprecated replaced by getDefaultHeaderRenderer()
	 */
	private void createTableHeaders()
	{
		int noOfCols = getColumnCount();
		for(int i=0;i<noOfCols;i++)
			table.getColumnModel().getColumn(i).setHeaderRenderer(getDefaultHeaderRenderer());
	}
	
	/**
	 * Returns the default header renderer.
	 */
	private TableCellRenderer getDefaultHeaderRenderer()
	{
		return new MatafTableHeaderRenderer();
	}
	
	/**
	 * Returns the MatafTableCellRenderer as the default cell renderer
	 * for this table.
	 * (Presently we render all class types with the same renderer)
	 */
	private TableCellRenderer getDefaultRendererForClass(Class columnType)
	{
		return new MatafTableCellRenderer(table);
	}
	
	/**
	 * Creates the appropriate editor according to the column class type
	 * and its desired properties.
	 */
	private TableCellEditor getDefaultEditorForClass(Class columnType,
														String columnDataName,
														boolean isEditable,
														boolean isRequired,
														int maxChars,
														boolean selectFromTable)
	{
		int columnIndex = getColumnCount()-1;
		
		if(columnType.equals(Boolean.class))
		{
			// Create a check-box as the editor.
			MatafCheckBox editorComponent = new MatafCheckBox();
			editorComponent.setEditable(isEditable);
			editorComponent.setRequired(isRequired);
			
			return new MatafTableCellEditor(editorComponent, table);
		}
				
		MatafTextField editorComponent = null;
		
		// Type represents an integer number.
		if(isIntegerClass(columnType))
		{
			editorComponent = new MatafNumericField();
		}
		
		// Type represents a decimal number.
		if(isDecimalClass(columnType))
		{
			editorComponent = new MatafDecimalField();
		}
		
		// Type represents a value associated with a table.
		if(selectFromTable) 
		{
			// Create a ComboTextField as the editor.
			editorComponent = new MatafComboTextField();
		}
		
		// Type represents an alpha-numeric value.
		if(columnType.equals(String.class))
		{
			editorComponent = new MatafStringField();
		}
	
		// Check if an unknown class type was specified.
		if(editorComponent==null)
		{
			editorComponent = new MatafStringField();
			GLogger.warn("Unsupported column class type :" + columnType +
							" Using default editor.");
		}
		
		// Set common properties.
		editorComponent.setEditable(isEditable);
		editorComponent.setRequired(isRequired);
		editorComponent.setMaxChars(maxChars);
			
		return new MatafTableCellEditor(editorComponent, table, columnIndex);
	}
	
///////////////////////////////////////////////////////////////////////////////

	private boolean isIntegerClass(Class classType)
	{
		return classType.equals(Long.class) 		||
				classType.equals(Integer.class) 	||
				classType.equals(Short.class) 		||
				classType.equals(Byte.class);
	}
	
	private boolean isDecimalClass(Class classType)
	{
		return classType.equals(Float.class) ||
				classType.equals(Double.class);
	}


///////////////////////////////////////////////////////////////////////////////

	/**
	 * Adds a new column to the table model with the desired parameters. <p>
	 * The column will be editable, not required, no max char limit and
	 * with column autosize.
	 * 
	 * @param columnType The Class type of this column content.
	 * @param columnTitle The Title of this column.
	 * @param columnDataName The dataName associated with this column.
	 * 
	 */
	public void addColumn(Class columnType,
							 String	columnTitle,
							 String	columnDataName)
							 
	{
		addColumn(columnType, columnTitle, columnDataName, 
					true, false, -1, Integer.MAX_VALUE, false);
	}
	
	/**
	 * Adds a new column to the table model with the desired parameters.<p>
	 * The column will be editable, not required, and with column autosize.
	 * 
	 * @param columnType The Class type of this column content.
	 * @param columnTitle The Title of this column.
	 * @param columnDataName The dataName associated with this column.
	 * @param maxChars The number of chars allowed in this column.
	 * 
	 */
	public void addColumn(Class columnType,
							 String	columnTitle,
							 String	columnDataName,
							 int columnWidth)
							 
	{
		addColumn(columnType, columnTitle, columnDataName, 
					true, false, columnWidth, Integer.MAX_VALUE, false);
	}
	
	/**
	 * Adds a new column to the table model with the desired parameters.<p>
	 * The column will be editable and not required.
	 * 
	 * @param columnType The Class type of this column content.
	 * @param columnTitle The Title of this column.
	 * @param columnDataName The dataName associated with this column.
	 * @param maxChars The number of chars allowed in this column.
	 * @param columnWidth The size of this column in pixels.(-1 for autosize)
	 * 
	 */
	public void addColumn(Class columnType,
							 String	columnTitle,
							 String	columnDataName,
							 int columnWidth,
							 int maxChars)
	{
		addColumn(columnType, columnTitle, columnDataName, 
					true, false, columnWidth, maxChars, false);
	}
	
	/**
	 * Adds a new column to the table model with the desired parameters.<p>
	 * The column will editable with no max char limit and will be autosized.
	 * 
	 * @param columnType The Class type of this column content.
	 * @param columnTitle The Title of this column.
	 * @param columnDataName The dataName associated with this column.
	 * @param isRequired true if this column should not be allowed to be empty.	
	 * 
	 */
	public void addColumn(Class columnType,
							 String	columnTitle,
							 String	columnDataName,
							 boolean isRequired)
	{
		addColumn(columnType, columnTitle, columnDataName, 
					true, isRequired, -1, Integer.MAX_VALUE, false);
	}
	
	/**
	 * Adds a new column to the table model with the desired parameters.<p>
	 * The column will editable and autosized.
	 * 
	 * @param columnType The Class type of this column content.
	 * @param columnTitle The Title of this column.
	 * @param columnDataName The dataName associated with this column.
	 * @param isRequired true if this column should not be allowed to be empty.
 	 * @param maxChars The number of chars allowed in this column.
	 * 
	 */
	public void addColumn(Class columnType,
							 String	columnTitle,
							 String	columnDataName,
							 boolean isRequired,
							 int maxChars)
	{
		addColumn(columnType, columnTitle, columnDataName, 
					true, isRequired, -1, maxChars, false);
	}
	
	/**
	 * Adds a new column to the table model with the desired parameters.<p>
	 * The column will editable and autosized.
	 * 
	 * @param columnType The Class type of this column content.
	 * @param columnTitle The Title of this column.
	 * @param columnDataName The dataName associated with this column.
	 * @param isRequired true if this column should not be allowed to be empty.
	 * @param maxChars The number of chars allowed in this column.
	 * @param selectFromTable true if value can be selected from a table.
	 * 
	 */
	public void addColumn(Class columnType,
							 String	columnTitle,
							 String	columnDataName,
							 boolean isRequired,
							 int maxChars,
							 boolean selectFromTable)
	{
		addColumn(columnType, columnTitle, columnDataName, 
					true, isRequired, -1, maxChars, selectFromTable);
	}
	
	
	/**
	 * Adds a new column to the table model with the desired parameters.<p>
	 * The column will not have max char limit and will be autosized.
	 * 
	 * @param columnType The Class type of this column content.
	 * @param columnTitle The Title of this column.
	 * @param columnDataName The dataName associated with this column.
	 * @param isEditable true is this column should be editable.
	 * @param isRequired true if this column should not be allowed to be empty.
	 * 
	 */
	public void addColumn(Class columnType,
							 String	columnTitle,
							 String	columnDataName,
							 boolean isEditable,
							 boolean isRequired)
	{
		addColumn(columnType, columnTitle, columnDataName, 
					isEditable, isRequired, -1, Integer.MAX_VALUE, false);
	}
	
	/**
	 * Adds a new column to the table model with the desired parameters.<p>
	 * The column will be autosized.
	 * 
	 * @param columnType The Class type of this column content.
	 * @param columnTitle The Title of this column.
	 * @param columnDataName The dataName associated with this column.
	 * @param isEditable true is this column should be editable.
	 * @param isRequired true if this column should not be allowed to be empty.
	 * @param maxChars The number of chars allowed in this column.
	 * 
	 */
	public void addColumn(Class columnType,
							 String	columnTitle,
							 String	columnDataName,
							 boolean isEditable,
							 boolean isRequired,
							 int maxChars)
	{
		addColumn(columnType, columnTitle, columnDataName, 
					isEditable, isRequired, -1, maxChars, false);
	}
	
	
	/**
	 * Adds a new column to the table model with the desired parameters.<p>
	 * The column will be autosized.
	 * 
	 * @param columnType The Class type of this column content.
	 * @param columnTitle The Title of this column.
	 * @param columnDataName The dataName associated with this column.
	 * @param isEditable true is this column should be editable.
	 * @param isRequired true if this column should not be allowed to be empty.
	 * @param maxChars The number of chars allowed in this column.
	 * @param selectFromTable true if value can be selected from a table.
	 * 
	 */
	public void addColumn(Class columnType,
								 String	columnTitle,
								 String	columnDataName,
								 boolean isEditable,
								 boolean isRequired,
								 int maxChars,
								 boolean selectFromTable)
	{
		addColumn(columnType, columnTitle, columnDataName, 
					isEditable, isRequired, -1, maxChars, selectFromTable);
	}
	
	/**
	 * Adds a new column to the table model with the desired parameters.<p>
	 * 
	 * @param columnType The Class type of this column content.
	 * @param columnTitle The Title of this column.
	 * @param columnDataName The dataName associated with this column.
	 * @param isEditable true is this column should be editable.
	 * @param isRequired true if this column should not be allowed to be empty.
	 * @param columnWidth The size of this column in pixels.(-1 for autosize)
	 * @param maxChars The number of chars allowed in this column.
	 * @param selectFromTable true if value can be selected from a table.
	 * 
	 */
	public void addColumn(Class columnType,
							 String	columnTitle,
							 String	columnDataName,
							 boolean isEditable,
							 boolean isRequired,
							 int columnWidth,
							 int maxChars,
							 boolean selectFromTable)
	{
		// Update column names vector.
		columnNames.add(columnTitle);
		
		// Update column class type vector.
		columnClassTypes.add(columnType);
		
		// Update column dataNames vector
		columnDataNames.add(columnDataName);
		
		// Create an empty table column model.
		TableColumn tableColumn = new TableColumn(getColumnCount()-1);				
		
		// Set column header renderer.
		tableColumn.setHeaderRenderer(getDefaultHeaderRenderer());		
		
		// Set column renderer.
		tableColumn.setCellRenderer(getDefaultRendererForClass(columnType));
		
		// Set column editor.
		tableColumn.setCellEditor(getDefaultEditorForClass(columnType, 
															columnDataName,
															isEditable,
															isRequired,
															maxChars,
															selectFromTable));

		// Set column size.
		tableColumn.setMaxWidth(getAdjustedColumnSize(columnWidth, columnTitle));
		
		// Add the new column to the table.
		table.addColumn(tableColumn);
	}
	
	/**
	 * Adjust the column headers according to the size specified
	 * If size specified is negative - size is computed according to length
	 * of the column name.
	 * Table size is updated accordingly to accomedate the new column.
	 */
	private int getAdjustedColumnSize(int columnWidth, String columnName)
	{
		final int LETTER_WIDTH = 15;

		if(columnWidth<0)
			columnWidth=LETTER_WIDTH * columnName.length();
		
		int newTableWidth = table.getSize().width+=columnWidth;
		
		Dimension newTableDim = new Dimension(newTableWidth, 100);

		table.setSize(newTableDim);
		//table.setPreferredSize(newTableDim);
		
		return columnWidth;
	}
	
	String getSelectedRowDataName()
	{
		return getMetaDataDataName()+"."+"SelectedRow";
	}
	
	String getMetaDataDataName()
	{
		return table.getDataNameForList()+"_"+"MetaData";
	}
	
	
	/**
	 * Returns whether the table is empty.
	 */
	public boolean isEmpty()
	{
		return getRowCount()==0;
	}
	
	/**
	 * Returns whether the table is editable.
	 */
	public boolean isEditable()
	{
		return editable;
	}
	
	/**
	 * Sets the table edit state.
	 */
	public void setEditable(boolean editable)
	{
		this.editable = editable;
	}
	
	/**
	 * Returns whether this table is sortable.
	 */
	public boolean isSortable()
	{
		return sortable;
	}
	
	/**
	 * Sets the table sort state.
	 */
	public void setSortable(boolean sortable)
	{
		this.sortable = sortable;
	}
	
	/**
	 * Returns the value from the currently selected row and column.
	 */
	public Object getSelectedValue()
	{
		return getSelectedValue(table.getSelectedColumn());
	}
	
	/**
	 * Returns the value from the currently selected row and
	 * the specified column.
	 */
	public Object getSelectedValue(int columnIndex)
	{
		int selectedRow = table.getSelectedRow();
		if(selectedRow==-1)
			return null;
		return getValueAt(selectedRow, columnIndex);
	}
	
	/**
	 * Returns the row that it's column starts with the text specified.
	 */
	public int searchColumnFor(int column, String text)
	{
		// Igonre empty search strings.
		if(text.trim().equals(""))
			return -1;
			
		try
		{		
			for(int row=0;row<getRowCount();row++)
				if(getValueAt(row,column).toString().startsWith(text))
					return row;
		}
		catch(Exception e)
		{
			return -1;
		}
		
		return -1;
	}
	
//////////////////////////////////////////////////////////////////////////////
///////////////    Implementiation of TableModel Interface    ////////////////
//////////////////////////////////////////////////////////////////////////////
	
	public int getRowCount()
	{
		return data.size();
	}
		
  	public int getColumnCount()
 	{
  		return columnNames.size();
  	}
  	
  	public int getVisibleColumnCount()
 	{
  		return columnNames.size() - numberOfHiddenColumns;
  	}
  	
  	public String getColumnName(int column)
	{
		return (String)columnNames.elementAt(column);
	} 
	
	/**
	 * If table is not editable then non of its columns are editable.
	 * If table is editable then the editable state of each editor 
	 * component is checked.
	 */
	public boolean isCellEditable(int row, int column) 
	{
		if(!this.isEditable())
			return false;
		else
		{
			MatafTextField mtf = table.getTextEditorAtColumn(column);
			return mtf!=null ? table.getTextEditorAtColumn(column).isEditable() : false;
		}
	}	
	  	
  	/**
  	 * Returns the column class type.
  	 */
  	public Class getColumnClass(int columnIndex)
  	{
  		return (Class)columnClassTypes.elementAt(columnIndex);
  	}
  	
	/**
	 * Returns the value at a specific cell in the table's model.
	 * Method also transforms primitive values to thier corresponding 
	 * Wrapping class.
	 */
	public Object getValueAt(int row, int col) 
	{
		try
		{
			// Get the value as object.
			Object obj = ((Vector)(data.elementAt(row))).elementAt(col);
			if(obj==null)
				return null;
			try
			{
				// Turn it to String.
				String str = obj.toString();
				
				// Handle Boolean.
				if(getColumnClass(col).equals(Boolean.class))
				{
					if(str.equals(""))
						return Boolean.FALSE;
					else
						return Boolean.valueOf(str);
				}
				
				// Handle Double.
				if((getColumnClass(col).equals(Double.class)))
				{
					if(str.equals(""))
						return null;
					else
					{
						DecimalFormat decFormat = (DecimalFormat)DecimalFormat.getInstance();
						//System.out.println("Parsed : ="+decFormat.parse(str));
						return new Double(decFormat.parse(str).toString());
					}
				}
			}
			catch(Exception e)
			{
				System.out.println("Bad Input When getting value from table model : "+e);
				return null;
			}

			return obj;
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			GLogger.debug("getValueAt("+row+","+col+") out of bounds in table model - Returning Null");
			return null;
		}
	}
	
	/**
	 * Sets the value in the table model.
	 */
	public void setValueAt(Object val, int row, int col) 
	{
		try
		{
			((Vector)(data.elementAt(row))).setElementAt(val, col);
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			GLogger.debug("setValueAt("+row+","+col+") out of bounds in table model - Ignoring value : "+val);
		}
	}	
	
	
	public void addRow(Vector row)
	{
		// Add new row to the model.
		data.addElement(row);
		table.revalidate();
	}
	
	/** 
	 * Center key making to one place.
	 */
	public String generateKey(int row, int column)
	{
		return row + "," + column;
	}
	
	/**
	 * Update the hashtable of cells error state.
	 * The value in the hashtable is the error message.
	 */
	public void updateErrorStateAt(int row, int column, VisualDataField visualField)
	{
		boolean inError = visualField.isInErrorFromServer();
		String key = generateKey(row, column);
		String errorMessage = visualField.getErrorMessage();
		
		if(inError)
			errorsHashtable.put(key, errorMessage);
		else
			errorsHashtable.remove(key);
	}
	
	public String getErrorMessageAt(int row, int column)
	{
		return (String)errorsHashtable.get(generateKey(row, column));
	}
	
	/**
	 * Returns whether a cell is in error.
	 */
	public boolean isCellInError(int row, int column)
	{
		return errorsHashtable.containsKey(generateKey(row, column));
	}
	
	/**
	 * Method is invoked after the removal of row in the table
	 * to remove the error state of the cells in it.
	 */
	public void clearErrorsForRow(int row)
	{
		for(int col=0;col<getVisibleColumnCount();col++)
			errorsHashtable.remove(generateKey(row,col));
	}
	
	/**
	 * Returns true if cell is both required and empty.
	 */
	private boolean isRequiredCellEmpty(int row, int column)
	{
		Object value = getValueAt(row, column);		
		
		if(value==null)
			value="";
		else
			value = value.toString();
		
		MatafTableCellEditor mtce =
			(MatafTableCellEditor)table.getCellEditor(row, column); 
		
		Component c = mtce.getComponent();			
		
		boolean required = false;
		boolean isEmpty = false;
		
		if(c instanceof MatafTextField)
			required = ((MatafTextField)c).isRequired();
		
		isEmpty = ((String)value).trim().equals("");
		
		return required && isEmpty;
	}
	
	/**
	 * Checks if the selected row has a cell in error.
	 */
	public boolean isSelectedRowInError()	
	{
		return isRowInError(table.getSelectedRow());
	}
	
	/**
	 * Checks if the row has a cell in error.
	 */
	public boolean isRowInError(int row)
	{
		for(int col=0;col<getVisibleColumnCount();col++)
			if(isCellInError(row,col) || isRequiredCellEmpty(row,col))
				return true;
		return false;
	}
	
	/**
	 * Put the cell in error back in editing mode.
	 */
	public void editCellInError()
	{
		int row = table.getSelectedRow();		

		// Search for the first cell in error.
		for(int col=0;col<getVisibleColumnCount();col++)		
			if(isCellInError(row,col) || isRequiredCellEmpty(row,col))
			{
				table.changeSelection(row, col, false, false);
				table.setEditingRow(row);
				table.setEditingColumn(col);
				table.editCellAt(row,col);
				return;
			}
	}
	
	/**
	 * Gets the maximum number of rows allowed in the table as specified
	 * in the maxRows dataField in the MetaData of the table.
	 */
	private int getMaxRows()
	{
		DSECoordinatedPanel cPanel = table.getDSECoordinatedPanel();
		// If Coordinated panel is null, then the view is in construction.(No way we can exceed the maxRows)
		if(cPanel==null)
			return Integer.MAX_VALUE;
			
		Context context = table.getDSECoordinatedPanel().getContext();
		// If context is null, then the view is in construction.(No way we can exceed the maxRows)
		if(context==null)
			return Integer.MAX_VALUE;
			
		String s = (String)context.tryGetValueAt(getMetaDataDataName() + "."+ "maxRows");
		return Integer.parseInt(s);
	}
	
	/**
	 * Singelton method that returns an empty KeyedCollection representing
	 * a table's row.
	 * 
	 * @return Empty KeyedCollection
	 */
	KeyedCollection getEmptyKColl()
	{
		if(emptyKColl==null)
		{
			try
			{
				emptyKColl = (KeyedCollection) KeyedCollection.readObject(getKeyedCollectionName());
			}
			catch(IOException e)
			{
				e.printStackTrace();
				return null;
			}
		}
		
		return emptyKColl;
	}
	
	/** 
	 * Gets the name of the keyedCollection in the IndexedCollection.
	 */
	String getKeyedCollectionName()
	{
		if(keyedCollectionName==null)
		{
			IndexedCollection contextIC = 
				(IndexedCollection) table.getDSECoordinatedPanel().getContext().tryGetElementAt(table.getDataNameForList());
						
			keyedCollectionName = contextIC.tryGetElementAt(0).getName();
		}
		
		return keyedCollectionName;
	}

	
	
	/**
	 * Returns the columnDataNames.
	 * @return Vector
	 */
	public Vector getColumnDataNames() {
		return columnDataNames;
	}

	/**
	 * Returns the numberOfHiddenColumns.
	 * @return int
	 */
	public int getNumberOfHiddenColumns() {
		return numberOfHiddenColumns;
	}

	/**
	 * Sets the numberOfHiddenColumns.
	 * @param numberOfHiddenColumns The numberOfHiddenColumns to set
	 */
	public void setNumberOfHiddenColumns(int numberOfHiddenColumns) {
		this.numberOfHiddenColumns = numberOfHiddenColumns;
	}
	
	/**
	 * Used by the table model sorter.
	 * 
	 * @return Vector 2-Dim vector represnting the table's data.
	 */
	Vector getRawTableData()
	{
		return data;
	}
}
