package mataf.types.table;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;


/**
 * This class creates all the InputMap and ActionMap instances
 * and binds them to the table.
 * 
 * @author Nati Dykstein. Creation Date : (12/06/2003 15:33:24).  
 */
public class MatafTableActionsBinder
{
	private static final int NUMPAD_PLUS_KEY_CODE  = 107;
	private static final int NUMPAD_MINUS_KEY_CODE = 109;
	
	private static final int ADD_ROW_KEY 	= KeyEvent.VK_INSERT;
	private static final int REMOVE_ROW_KEY 	= KeyEvent.VK_DELETE;
	
	private MatafTable			table;
	private MatafTableModel 	tableModel;
	
	
	public MatafTableActionsBinder(MatafTable table, MatafTableModel tableModel)
	{	
		this.table = table;
		this.tableModel = tableModel;
		
	}
	
	/**
	 * Here all the key-bindings to the table take place.
	 */
	public void bindKeys()
	{
		/**
		 * If table is displayed in a popup, make the ENTER key close it.
		 */
		if(table.isOpenedFromCombo())
		{
			table.getInputMap().put(KeyStroke.getKeyStroke("ENTER"),"closepopup");
			
			table.getActionMap().put("closepopup",new AbstractAction() 
			{
				public void actionPerformed(ActionEvent e)
				{					
					table.handleSelectionFromCombo();
				}
			});
			
		/*	table.getInputMap().put(KeyStroke.getKeyStroke("ESCAPE"),"cancelpopup");
			
			table.getActionMap().put("cancelpopup",new AbstractAction() 
			{
				public void actionPerformed(ActionEvent e)
				{					
					table.cancelPopup();
				}
			});*/
		}
	
	
	///////////////////// Adding 'newline' functionality /////////////////////
	
		table.getInputMap().put(KeyStroke.getKeyStroke(ADD_ROW_KEY,0),"addline");
		
		/**
		 * Make the INSERT key add a row to the table.
		 */
		table.getActionMap().put("addline",new AbstractAction() 
		{
			public void actionPerformed(ActionEvent e)
			{				
				// Prevent adding rows to a non-editable table.
				if(!table.isEditable())
					return;
				
				// Adding allowed only when standing on the last row.
				if(table.getSelectedRow()!=table.getRowCount()-1)
					return;
				
				// If row has errors in it, auto-edit the erronous cell.
				if(tableModel.isSelectedRowInError())
					tableModel.editCellInError();
				else // No errors in row.
					tableModel.appendNewRow();
			}
		});
		
		
	//////////////////// Adding 'removeline' functionality ///////////////////
		table.getInputMap().put(KeyStroke.getKeyStroke(REMOVE_ROW_KEY,0),"removeline");
		
		/**
		 * Method adds the ability to remove a row from the table by pressing
		 * the DELETE key.
		 * If the row is not empty - a user confirmation dialog will pop.
		 * 
		 * PENDING : 1.Delete also the corresponding line in the context's IC.
		 */
		table.getActionMap().put("removeline",new AbstractAction()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(!table.isEditable())
					return;
				
				// Model is empty.
				if(tableModel.isEmpty())
					return;
			
				// Check if no selection was made.
				int selectedRow = table.getSelectedRow();
				if(selectedRow==-1)
					return;
			
				if(!tableModel.selectedRowIsEmpty())
				{
					if(tableModel.openDeletionConfirmationDialog())
						tableModel.removeRow(selectedRow);
				}
				else
					tableModel.removeRow(selectedRow);
			}
		});
	}
}
