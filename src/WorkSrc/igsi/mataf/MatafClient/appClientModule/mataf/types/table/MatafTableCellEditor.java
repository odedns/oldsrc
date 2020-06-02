package mataf.types.table;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.DefaultCellEditor;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.TableCellEditor;

import mataf.types.MatafCheckBox;
import mataf.types.MatafTextField;
import mataf.utils.FontFactory;

import com.ibm.dse.gui.ErrorMessageEvent;

/**
 * This class uses the editors from JTable class (code copied to here).
 * We have 3 type of editors : GenericEditor, NumberEditor and BooleanEditor.
 * 
 * The MatafTableCellEditor itself functions only as a delegator to one of the
 * three editors mentioned above.
 *
 *  
 * @author Nati Dykstein. Creation Date : (21/09/2003 13:47:17).  
 */
public class MatafTableCellEditor extends DefaultCellEditor
										implements FocusListener
{
	/** 
	 * Indicates the row we are editing.
	 * This is a class variable becuase we want all the CellEditors
	 * to always be on the same row.
	 */
	private static int	 editingRow;
	
	private MatafTable 	 table;

	/** Indicates the row we are editing.*/
	private int			 editingColumn;
	
	private MatafNumberEditor 	numberEditor;
	private MatafGenericEditor genericEditor;
	private MatafBooleanEditor	booleanEditor;
	
	// For debug purposes.
	private JTable t;
	
	/** This constructor is used for debug purposes.*/
	public MatafTableCellEditor(MatafTextField tf, JTable t)
	{super(tf);this.t = t;}
	
	/** 
	 * TextField CellEditor Constructor.
	 */
	public MatafTableCellEditor(final MatafTextField textField, MatafTable table, int editingColumn) 
	{
		super(textField);		
		this.table = table;
		this.editingColumn = editingColumn;
				
		textField.setCellEditor(this);
		textField.setFont(FontFactory.getDefaultFont());
		textField.addFocusListener(this);
		
		// PENDING : Solves unexplained copying from one row to another.
		textField.setFocusLostBehavior(MatafTextField.REVERT);
	}
			
	/** 
	 * CheckBox CellEditor Constructor.
	 */
	public MatafTableCellEditor(MatafCheckBox checkBox, MatafTable table) 
	{
		super(checkBox);
		this.table = table;
		
		checkBox.addFocusListener(new FocusAdapter() 
		{
			public void focusGained(FocusEvent e) 
			{
				editingRow 		= MatafTableCellEditor.this.table.getSelectedRow();
				editingColumn 	= MatafTableCellEditor.this.table.getSelectedColumn();
			}
			
			public void focusLost(FocusEvent e) 
			{
				boolean data = getOurBooleanEditor().isSelected();
				MatafTableCellEditor.this.table.setValueAt(Boolean.valueOf(data), editingRow, editingColumn);
			}
		});
	}
	
	/**
	 * Applies the display formatter editor prior to
	 * returning the textfield's value.
	 */
	public Object getCellEditorValue()
	{
		MatafTextField textField = getOurTextEditor();
		
		if(textField!=null && !"".equals(textField.getText()))
		{
			return textField.getDisplayText();
		}
		else
			return super.getCellEditorValue();
	}

	
	/**
	 * @see javax.swing.DefaultCellEditor#getTableCellEditorComponent(JTable, Object, boolean, int, int)
	 */
	public Component getTableCellEditorComponent(JTable table,Object value,
													boolean isSelected,
													int row,int column) 
	{
		// Get the column class.
		Class columnClass = table.getModel().getColumnClass(column);

		// Get the default editor for this class.
		TableCellEditor tEditor = getDefaultEditor(columnClass);

		JComponent c = (JComponent)tEditor.getTableCellEditorComponent(table, value, isSelected, row, column);
		
		// Add the listener only once.
	/*	if(c.getKeyListeners().length==0)
		{
			c.addKeyListener(new KeyAdapter() {
				public void keyPressed(KeyEvent e) 
				{
					// Enter finished the editing and select next cell.
					if(e.getKeyCode()==KeyEvent.VK_ENTER)
					{
						selectNextCell();
					}
					
					// Escape cancels the editing.
					if(e.getKeyCode()==KeyEvent.VK_ESCAPE)
					{
						fireEditingCanceled();
					}
				}
			});
		}*/
	
		return c;
	}
	
	/** 
	 * Finish editing and select the next cell.
	 */
	public void selectNextCell()
	{
		fireEditingStopped();
		int maxIndex = table.getOurModel().getVisibleColumnCount();
		int nextIndex = (editingColumn+1) > maxIndex ? 0 : editingColumn+1;
		getTable().changeSelection(editingRow, nextIndex, false, false);
	}
	
	/** 
	 * Finish editing and select the previous cell.
	 */
	public void selectPreviousCell()
	{
		fireEditingStopped();
		int nextIndex = (editingColumn - 1) < 0 ? 0 : editingColumn - 1;
		getTable().changeSelection(editingRow, nextIndex, false, false);
	}
	
	/**
	 * Reselect last cell and select the text inside.
	 */
/*	public void reselectCell()
	{
		getTable().editCellAt(editingRow, editingColumn);
		getTable().getTextEditorAtColumn(editingColumn).selectAll();
	}*/
	
	/**
	 * Update the editingRow and handle the error message.
	 */
	public void focusGained(java.awt.event.FocusEvent e)
	{
		//System.out.println("In MatafTableCellEditor FocusGained("+getOurTextEditor().getText()+"), Editing Row = "+table.getEditingRow());
		editingRow = table.getEditingRow();
		
		// If cell is in error - Fire the error message.
	/*	if (table.isCellInError(editingRow, editingColumn))
		{
			MatafTextField mtf = getOurTextEditor();
			String errorMessage = table.getOurModel().getErrorMessageAt(editingRow, editingColumn);
			
			table.fireHandleErrorMessage(new ErrorMessageEvent(mtf ,new String[]{errorMessage}));
			mtf.setToolTipText(errorMessage);
			mtf.setForeground(Color.red);
		}
		else
		{
			table.fireHandleErrorMessage(new ErrorMessageEvent(getOurTextEditor(),""));
			getOurTextEditor().setToolTipText("");
			getOurTextEditor().setForeground(Color.black);
		}*/
	}

	/**
	 * Updates the table model with the new data.
	 * 
	 * PENDING : When cancelling the editing, dont update the context!
	 */
	public void focusLost(java.awt.event.FocusEvent e) 
	{
		//System.out.println("In MatafTableCellEditor("+getOurTextEditor().getText()+") FocusLost !");
		
	/*	stopCellEditing();
		
		// Update the context about the change in the row's data.		
		if(editingRow!=-1)
			table.updateContextWithRowInTableModel(editingRow);
		
		// Fire the coordination event.
		if(getOurTextEditor()!=null)
			getOurTextEditor().fireCoordinationEvent();
			
		// PENDING : Checking...
		table.getOurModel().editCellInError();*/
	}

	public MatafTextField getOurTextEditor()
	{
		return (MatafTextField)editorComponent;
	}
	
	public MatafCheckBox getOurBooleanEditor()
	{
		return (MatafCheckBox)editorComponent;
	}
	
	/**
	 * Sets the required property (boolean) value.
	 * @param required boolean, the new value for the property
	 */
	public void setRequired(boolean required) 
	{}
	
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
		this.table = table;
	}
	
	private boolean isEditorEmpty()
	{
		if(getOurTextEditor()!=null)
			return getOurTextEditor().getText().trim().equals("");
		else
			return true;
	}
 
    /**
     * Returns the appropriate editor according to the column class type.
     * 
     * @param columnClass - The column class type.
     * @return TableCellEditor
     */
    public TableCellEditor getDefaultEditor(Class columnClass)
    {
    	if(columnClass.isAssignableFrom(Number.class))
    	{
    		if (numberEditor==null)
    			numberEditor = new MatafNumberEditor(getOurTextEditor());
    		return numberEditor;
    	}
    	
    	if(columnClass.equals(Boolean.class))
    	{
    		if (booleanEditor==null)
    			booleanEditor = new MatafBooleanEditor(getOurBooleanEditor());
    		return booleanEditor;
    	}
   
   		if (genericEditor==null)
   			genericEditor = new MatafGenericEditor(getOurTextEditor());
    		return genericEditor;
    }
    
    
    /**
	 * Returns the editingRow.
	 * @return int
	 */
	public int getEditingRow() {
		return editingRow;
	}
    /**
	 * Returns the editingColumn.
	 * @return int
	 */
	public int getEditingColumn() 
	{
		return editingColumn;
	}
    
/*******************************************************************************************************************/
    
    /**
     * Default Editors as copied from JTable class.
     */
    class MatafGenericEditor extends DefaultCellEditor 
    {

		Class[] argTypes = new Class[]{String.class};
		java.lang.reflect.Constructor constructor;
		Object value;
	
		public MatafGenericEditor(MatafTextField textField)
		{
			super(textField);
		}
	
		public boolean stopCellEditing() {
		    String s = (String)super.getCellEditorValue();
		    // Here we are dealing with the case where a user
		    // has deleted the string value in a cell, possibly
		    // after a failed validation. Return null, so that
		    // they have the option to replace the value with
		    // null or use escape to restore the original.
		    // For Strings, return "" for backward compatibility.
		    if ("".equals(s)) 
		    {
				if (constructor.getDeclaringClass() == String.class) 
				{
			    	value = s;
				}
				super.stopCellEditing();
		    }
	
		    try 
		    {
				value = constructor.newInstance(new Object[]{s});
		    }
		    catch (Exception e) 
		    {
				((JComponent)getComponent()).setBorder(new LineBorder(Color.red));
				return false;
		    }
		    return super.stopCellEditing();
		}
	
		public Component getTableCellEditorComponent(JTable table, Object value,
							 boolean isSelected,
							 int row, int column) 
		{
		    this.value = null;
		    ((JComponent)getComponent()).setBorder(new LineBorder(Color.black));
		    try 
		    {
				Class type = table.getColumnClass(column);
				// Since our obligation is to produce a value which is
				// assignable for the required type it is OK to use the
				// String constructor for columns which are declared
				// to contain Objects. A String is an Object.
				if (type == Object.class) 
				{
				    type = String.class;
				}
				constructor = type.getConstructor(argTypes);
		    }
		    catch (Exception e) 
		    {
				return null;
		    }
		    return super.getTableCellEditorComponent(table, value, isSelected, row, column);
		}
	
		public Object getCellEditorValue() 
		{
		    return value;
		}
    }

    class MatafNumberEditor extends MatafGenericEditor 
    {
		public MatafNumberEditor(MatafTextField textField) 
		{
			super(textField);
		    ((MatafTextField)getComponent()).setHorizontalAlignment(MatafTextField.RIGHT);
		}
    }

    static class MatafBooleanEditor extends DefaultCellEditor 
    {
		public MatafBooleanEditor(MatafCheckBox checkBox) 
		{
		    super(checkBox);
		    checkBox.setHorizontalAlignment(MatafCheckBox.CENTER);
		}
    }	
}
