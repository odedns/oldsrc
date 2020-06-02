package mataf.types;

import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

import mataf.types.table.MatafTable;
import mataf.types.table.MatafTableModel;
import mataf.types.textfields.MatafNumericField;

import com.ibm.dse.base.DSEException;
import com.ibm.dse.base.DSEObjectNotFoundException;
import com.ibm.dse.gui.Settings;

/**
 *
 * This TextField is a specialized version of MatafNumericField that
 * is used in combination with the MatafTableComboBox and a description Label.
 * It validates the input againsts the model of the assigned TableComboBox.
 * 
 * @author Nati Dykstein. Creation Date : (22/12/2003 10:54:09).  
 */
public class MatafComboTextField extends MatafNumericField 
{
	/** The key used to open the pop-up table. */
	private static final String OPEN_POPUP_KEY = "F4";

	/** The referenced tableComboBoxButton.*/
	private MatafTableComboBoxButton 	matafTableComboBoxButton;

	/** The component to show the description in. */
	private MatafLabel 				descriptionLabel;
	
	/** Set to false to allow values that do not exist in the table. */
	private boolean					valueMustExistInTable = true;

	/**
	 * Constructor attaches the listeners the prevents the user from
	 * exiting the field if the value is not valid.
	 */
	public MatafComboTextField()
	{
		this("");
	}
	
	public MatafComboTextField(String text)
	{
		super(text);
		setDataDirection("Both");
		
		bindActionKeys();
		attachKeyListeners();
		attachFocusListeners();
	}
	
	/**
	 * Allow a special key to open the pop-up table.
	 */
	private void bindActionKeys()
	{
		getInputMap().put(KeyStroke.getKeyStroke(OPEN_POPUP_KEY),"openPopup");		
		
		getActionMap().put("openPopup",new AbstractAction() 
		{
			public void actionPerformed(ActionEvent e)
			{
				validateTableComboBox();
				if(isInCellEditor())
				{
					// Fill table from context prior to opening.
					fillTableFromContext();
				}
				if(!matafTableComboBoxButton.isPopupOpen())
					matafTableComboBoxButton.togglePopup();
			}
		});
	}	
	
	/**
	 * Make ESCAPE close the pop-up if open.
	 * If pop-up is closed allow parent to handle it.
	 * 
	 * @see mataf.types.MatafTextField#handleEscapeKey(java.awt.event.KeyEvent)
	 */
	public void handleEscapeKey(KeyEvent e)
	{
		if(matafTableComboBoxButton.isPopupOpen())
			matafTableComboBoxButton.cancelPopup();
		else
			super.handleEscapeKey(e);
	}

	
	/**
	 * Attach the key listeners.
	 */
	private void attachKeyListeners()
	{
		addKeyListener(new KeyAdapter() 
		{
			/**
			 * If the pop-up is open - dispatch key pressed events to its table.
			 */
			public void keyPressed(KeyEvent e) 
			{
				validateTableComboBox();
				
				if(matafTableComboBoxButton.isPopupOpen())
					matafTableComboBoxButton.getTable().dispatchEvent(e);
			}
		});
	}
	
	/**
	 * Attach the focus listeners.
	 */
	private void attachFocusListeners()
	{
		addFocusListener(new FocusAdapter() {
			/**
			 * Close the pop-up upon leaving the textfield.
			 */
			public void focusLost(FocusEvent e) 
			{
				validateTableComboBox();
				if(matafTableComboBoxButton.isPopupOpen())
					matafTableComboBoxButton.togglePopup();
			}
		});
	}
	
	/**
	 * Throws an exception if our tableComboBox is null.
	 */
	private void validateTableComboBox()
	{
		if(matafTableComboBoxButton==null)
			throw new IllegalStateException(
			"No Table or TableComboBox were assigned" +
			" to this ComboTextField !");
	}
	
	/**
	 * Disables also the associated TableComboBox.
	 */
	public void setEnabled(boolean b) 
	{
		super.setEnabled(b);
		matafTableComboBoxButton.setEnabled(b);
	}
	
	/**
	 * Returns the row that matches the text currently
	 * in the textfield.
	 */
	public int getRowByCurrentText()
	{
		// No Table is attached.
		if(getTable()==null)
			return -1;
			
		MatafTableModel tableModel = getTable().getOurModel();
		
		// Table was not build yet.
		if(tableModel.getRowCount()==0 && isInCellEditor())
			fillTableFromContext();
			
		String text = isInCellEditor() ? getTextFromTableModel() : getText();
		
		return tableModel.searchColumnFor(0,text);
	}
	
	/**
	 * Get the description value in the table according to the
	 * specified row.
	 * Note : We are assuming that the second column contains the description.
	 */
	public String getDescriptionInRow(int rowNumber)
	{
		MatafTableModel tableModel = getTable().getOurModel();
		int descriptionColumnNumber = 1;
		return tableModel.getValueAt(rowNumber, descriptionColumnNumber).toString();
	}
	
	/**
	 * Assigns the MatafTableComboBox to this MatafComboTextField.
	 */
	public void setTableComboBox(MatafTableComboBoxButton matafTableComboBoxButton)
	{
		this.matafTableComboBoxButton = matafTableComboBoxButton;
		
		if(matafTableComboBoxButton.getTable()==null)
			throw new IllegalArgumentException("Cannot set a tableComboBox with a null table!");
		
		matafTableComboBoxButton.setAttachedTextField(this);
	}
	
	/**
	 * Assigns the description label to the MatafComboTextField.
	 */
	public void setDescriptionLabel(MatafLabel descriptionLabel)
	{
		this.descriptionLabel = descriptionLabel;
		descriptionLabel.setDataDirection(Settings.BOTH_DIRECTION);
	}
	
	/**
	 * Returns the descriptionLabel.
	 * @return MatafLabel
	 */
	public MatafLabel getDescriptionLabel() 
	{
		return descriptionLabel;
	}
	
	/**
	 * Updates the context with the label's new description.
	 */
	public void setDataValue(Object value)
	{
		super.setDataValue(value);
		
		int row = getRowByCurrentText();
		if(row==-1)
			return;
		
		// Update the label value in the context.
		if(descriptionLabel!=null)
		{
			try
			{
				getDSECoordinatedPanel().getContext().setValueAt(descriptionLabel.getDataName(),getDescriptionInRow(row));
			}
			catch (DSEException e)
			{			
				e.printStackTrace();
			}
		}
	}

	
	/**
	 * Also make the description label notify the context about 
	 * the change in it's value.
	 */
	public void fireCoordinationEvent() 
	{
		super.fireCoordinationEvent();
		if(getDescriptionLabel()!=null)
			getDescriptionLabel().fireCoordinationEvent();
	}
	
	/**
	 * Make sure that we close the popup before we surrender the focus
	 * to another component.
	 * If we're inside a celle editor, transfer the focus accordingly.
	 * 
	 * @see java.awt.Component#transferFocus()
	 */
	public void transferFocus() 
	{
		if(matafTableComboBoxButton.isPopupOpen())
			matafTableComboBoxButton.cancelPopup();
			
		super.transferFocus();
	}
	
	/**
	 * Returns the table assigned to the comboTextField.
	 * @return MatafTable
	 */
	public MatafTable getTable() 
	{
		return matafTableComboBoxButton != null ? matafTableComboBoxButton.getTable() : null;
	}

	/**
	 * Method is invoked when we're using the MatafComboTextField 
	 * without a MatafTableComboBoxButton.(For example - in a table)
	 * In this case we create it ourselves and assign the
	 * textfield to it.
	 * 
	 * NOTE : When created this way, the MatafTableComboBox button
	 * 			does not exist on the panel (so surely it's
	 * 			not visible).
	 */
	public void setTable(MatafTable matafTable) 
	{
		if(matafTableComboBoxButton==null)
		{
			matafTableComboBoxButton = new MatafTableComboBoxButton();
			matafTableComboBoxButton.setAttachedTextField(this);
			matafTableComboBoxButton.setVisible(false);
		}
		matafTableComboBoxButton.setTable(matafTable);
	}
	
	/**
	 * Method makes sure that the table is updated from the context
	 * although it's not a child of the view and hence is not
	 * affected by invokations of refreshDataExchangers().
	 * 
	 * This is the case when the MatafComboTextField is used as a cell
	 * editor.
	 */
	public void fillTableFromContext()
	{
		try 
		{
			Object listValue =
				getCellEditor().getTable().getDSECoordinatedPanel().getContext().getElementAt(
					getTable().getDataNameForList());
		   	getTable().setDataValueForList(listValue);
		} 
		catch (DSEObjectNotFoundException e) 
		{/*Due to invokations of refreshDataExchangers on non-valid views. */}
	}
	
	/**
	 * Sets the content of the description label associated with the
	 * combo textfield.
	 * @param description
	 */
	public void setDescription(String description)
	{
		if(descriptionLabel!=null)
			descriptionLabel.setText(description);
	}
	
	
	/**
	 * Adds check of the value against the table.
	 * 
	 * @see mataf.types.MatafTextField#isFocusAllowedToLeave()
	 */
	public boolean isFocusAllowedToLeave()
	{
		// Get matching row in table according to current text.
		int row = getRowByCurrentText();
		
		// Check if textfield is required and empty.
		if(isRequired() && isEmpty())
		{
			displayErrorMessage("ערך חובה להקשה");
			setDescription("");
			return false;
		}
		
		// No matching row was found in the table.
		if(row==-1 && valueMustExistInTable)
		{
			displayErrorMessage("ערך לא קיים בטבלה");
			setDescription("");
			selectAll();
			return false;
		}
		
		// All validations passed.
		clearErrorMessage();
		
		// Update description only if a matching row was found.
		if(row!=-1)
			setDescription(getDescriptionInRow(row));
		
		return true;
	}

	/**
	 * @return
	 */
	public boolean isValueMustExistInTable()
	{
		return valueMustExistInTable;
	}

	/**
	 * @param b
	 */
	public void setValueMustExistInTable(boolean b)
	{
		valueMustExistInTable = b;
	}

}