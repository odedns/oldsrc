package mataf.types;

import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.FocusTraversalPolicy;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JFormattedTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.text.DefaultFormatter;
import javax.swing.text.DefaultFormatterFactory;

import mataf.events.MatafMessageEvent;
import mataf.exchangers.VisualPropertiesExchanger;
import mataf.focusmanager.MatafFocusTraversalPolicy;
import mataf.logger.GLogger;
import mataf.types.table.MatafTable;
import mataf.types.table.MatafTableCellEditor;
import mataf.utils.FontFactory;

import com.ibm.dse.base.Context;
import com.ibm.dse.gui.CoordinatedEventListener;
import com.ibm.dse.gui.CoordinatedEventMulticaster;
import com.ibm.dse.gui.DSECoordinatedPanel;
import com.ibm.dse.gui.DSECoordinationEvent;
import com.ibm.dse.gui.DataChangedListener;
import com.ibm.dse.gui.DataExchanger;
import com.ibm.dse.gui.ErrorMessageEvent;
import com.ibm.dse.gui.ErrorMessageEventMulticaster;
import com.ibm.dse.gui.ErrorMessageGenerator;
import com.ibm.dse.gui.ErrorMessageListener;
import com.ibm.dse.gui.Formatter;
import com.ibm.dse.gui.NavigationParameters;
import com.ibm.dse.gui.Settings;
import com.ibm.dse.gui.SpPanel;

/**
 * This TextField is used an an abstract class for type-specific
 * textfields.<p>
 * 
 * It functions both as an FormattedTextField and as a text cell editor 
 * in MatafTable class.
 * 
 * This class manages the focus traversal.
 *
 * @author Nati Dykstein.
 */
public abstract class MatafTextField extends JFormattedTextField
								implements VisualPropertiesExchanger,
											DataExchanger,
											ErrorMessageGenerator
												
{
	private static final Color BACKGROUND_COLOR_IN_FOCUS = new Color(255,238,221);
	private static final Font  FONT = FontFactory.getDefaultFont();
	
	private static String DEFAULT_TYPE = new String(DSECoordinationEvent.EVENT_SOURCETYPE_DEFAULT);

	/** Composer Vars. */
	private transient ErrorMessageListener aErrorMessageListener;
	private transient CoordinatedEventListener aCoordinatedEventListener;
	private String[] 	errorMessageFocusGained;
	//private Formatter 	fieldFormatter;
	private NavigationParameters fieldNavigationParameters = new NavigationParameters();
	private String		dataDirection;
	private String 	dataName;
	private String 	type = DEFAULT_TYPE;

	/** Indicates the server has detected an error in this bean. */
	private boolean				errorFromServer;

	/** Indicates the TextField is used as a cell editor. */
	private boolean				inCellEditor;
	
	/** The Cell Editor that this TextField is used by.*/
	private MatafTableCellEditor 	cellEditor;
   
    /** Max chars allowed in this textfield.*/
    private int					maxChars = Integer.MAX_VALUE;
    
	private boolean required;
	
	/** True if we are in the middle of mergeing the verifiers.*/
	//private boolean mergingVerifiers;
	
	/** Indicates that the focus traversal was triggered by the keybaord.*/
	private boolean keyboardTriggeredFocusTraversal;
	
	/** Last inserted text.*/
	private String		previousText = "";
	
	/** Used to minimize the error messages event we fire.*/
	private boolean lastWasError;
		
/*	static
	{
		// Makes the textfield more readable when disabled.
		UIManager.put("TextField.inactiveBackground", new Color(238,238,238));//UIManager.getColor("TextField.background"));
	}*/
	
	/**
	 * Constructor for MatafTextField.
	 */
	public MatafTextField() 
	{		
		this("");
	}
	
	public MatafTextField(String text)
	{
		super();
		initComponent();		
		setText(text);
		registerFocusListeners();
	//	addPropertyChangeListener(this);
	}
	
	
//	/**
//	 * Listening for a change in the inputVerifier property.
//	 * Any invokation to setInputVerifier() will merge the old inputVerifier with
//	 * the new one.
//	 * (Default java implementtaion overrides the old inputverifier with the new one)
//	 */
//	public void propertyChange(PropertyChangeEvent evt)
//	{
//		if(evt.getPropertyName().equals("inputVerifier"))
//		{
//			if(!mergingVerifiers)
//			{
//				if(evt.getOldValue()!=null)
//				{
//					final InputVerifier oldVerifier = (InputVerifier)evt.getOldValue();
//					final InputVerifier newVerifier = (InputVerifier)evt.getNewValue();
//					InputVerifier mergedVerifier = new InputVerifier()
//					{
//						/**
//						 * Merge the validations of the two verifiers.
//						 */
//						public boolean verify(JComponent inputComp)
//						{
//							return oldVerifier.verify(inputComp) && newVerifier.verify(inputComp);							
//						}
//						
//						/**
//						 * Execute the operation and invoke the verification method.
//						 */
//						public boolean shouldYieldFocus(JComponent inputComp)
//						{
//							MatafTextField tf = (MatafTextField)inputComp;
//							if(!tf.isInCellEditor())
//								  tf.fireCoordinationEvent();
//							return verify(inputComp);
//						}
//					};
//					
//					// Prevents an infinite loop.
//					mergingVerifiers = true;
//					
//					// Set the merged input verifier.
//					setInputVerifier(mergedVerifier);
//				}
//			}
//			else // Reset the flag.
//				mergingVerifiers = false;
//		}
//	}

	
	/**
	 * Configure the L&F and install listeners.
	 */
	private void initComponent()
	{
		setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		setFont(FontFactory.getDefaultFont());
		
		// Let us control the focus traversal.
		setFocusTraversalKeysEnabled(false);

		addKeyListener(new KeyAdapter()
		{
			public void keyPressed(KeyEvent e)
			{
				// Trigger the focus traversal mechanism.
				if(KeyEvent.getKeyText(e.getKeyCode()).equals("Tab") ||
						KeyEvent.getKeyText(e.getKeyCode()).equals("Enter"))
				{
					activateFocusTraversal(e.isShiftDown(), true);
				}
				
				// Notify that the ESCAPE key was pressed.
				if(KeyEvent.getKeyText(e.getKeyCode()).equals("Escape"))
				{
					handleEscapeKey(e);
				}
				
				// Clear the text when pressing the keypad '+'.								
				if(e.getKeyCode()==KeyEvent.VK_ADD)
				{
					setText("");
				}
			}
		});
		
		//addInsertFunctionality();
		
		setFont(FONT);
        setDataDirection(Settings.INPUT_DIRECTION);
		setFocusLostBehavior(COMMIT);
	}
	
	/**
	 * Handle ESCAPE key by notifying the panel to close itself.
	 * (Only if the textfield is not used as a cell editor)
	 * 
	 * Decendants can add different funtionality.
	 *
	 */
	public void handleEscapeKey(KeyEvent e)
	{
		if(!inCellEditor)
			getParent().dispatchEvent(e);
	}

	/**
	 * Displays a waiting message while activating an operation.
	 */
	private void displayWaitingForOperationMessage()
	{
		String pleaseWaitMessage = "אנא המתן...";
		String messageType = "Loading";
		setErrorMessage(pleaseWaitMessage);
		if(inCellEditor)
			cellEditor.getTable().fireHandleErrorMessage(new MatafMessageEvent(cellEditor.getTable(), new String[]{pleaseWaitMessage}, messageType));
		else
			fireHandleErrorMessage(new MatafMessageEvent(this, new String[]{pleaseWaitMessage}, messageType));
	}
	
	/**
	 * Activate focus traversal mechanism.
	 * @param backwards - true if we want the focus to traverse backwards.
	 */
	public void activateFocusTraversal(final boolean backwards,final boolean keyboardTrigger)
	{
		keyboardTriggeredFocusTraversal = keyboardTrigger;
		
		// If we're activating an operation, display a waiting message.
		if(getNavigationParameters()!=null && !backwards)
			displayWaitingForOperationMessage();
		
		// Prevent starvation of the AWT-EventQueue thread.
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				// Always allow focus to move backwards.
				if(backwards)
					transferFocusBackward();
				else
				{
					// Additional actions when used in a cell editor.
					if(inCellEditor)
					{
						//System.out.println("Traversing focus for cell editor");
						cellEditor.stopCellEditing();
		
						// Update the context about the change in the row's data.
						int editingRow = cellEditor.getEditingRow();		
						if(editingRow!=-1)
							cellEditor.getTable().updateContextWithRowInTableModel(editingRow);
					}
					
					// Activate the operation.					
					fireCoordinationEvent();
					
					 // Check if focus is allowed to leave.					
					 if(isFocusAllowedToLeave())
					 {
					 	//System.out.println("Focus is allowed to leave.");
					 	// Transfer focus only if keyboard was the trigger.
					 	// (flag is reseted at focusLost())
					 	if(keyboardTriggeredFocusTraversal)
					 	{
					 	 	transferFocus();
					 	}
					 }
					 else
					 {
						// Retake the focus after the mouse has traversed it.
						if(!keyboardTriggeredFocusTraversal)
							requestFocus();
							
						if(inCellEditor)
						{
							// PENDING : Checking...					
							//cellEditor.getTable().getOurModel().editCellInError();						
						}
							
						// Reset the flag.
						keyboardTriggeredFocusTraversal = false;
					 }
					// System.out.println("Finished traversing.");
				}
			}
		});
	}
	
	/**
	 * Fixes a bug in which the display formatter is being used instead of the
	 * edit formatter.
	 * The workaround re-installs the edit formatter each time the cell
	 * recieves focus.
	 */
	private void reInstallFormatter()
	{
		// Remember current text.
		String pText = getText();
	
		// Get the formatter factory of the textfield.
		DefaultFormatterFactory dff = 
							(DefaultFormatterFactory)getFormatterFactory();				
	
		// Re-install the default formatter.
		setFormatter(dff.getDefaultFormatter());
		//dff.getDefaultFormatter().install(this);
	
		// Set back the text.
		setText(pText);
	}
	
	/**
	 * Always clear the text prior to invoking setText().
	 * This is made to reduce the complexity of the formatters code. 
	 * 
	 * @see javax.swing.text.JTextComponent#setText(java.lang.String)
	 */
	public void setText(String t)
	{
		super.setText("");
		super.setText(t);
	}

	
	/**
	 * @return
	 */
	public int getMaxChars()
	{
		return maxChars;
	}
	
	/**
	 * Exchange visual properties.
	 */
	public void exchangeVisualProperties(Context ctx) 
	{
		ADAPTER.exchangeVisualProperties(ctx, this);
	}
	
	/**
	 * Extends the functionality of the DataExchanger.
	 */
	public void setDataValue(Object dataValue) 
	{
		String dataString = (String)dataValue;
		
		if((getDataName()==null) || getDataName().equals(""))
			return;

		// Make the component appear in error when errorFromServer is true.
		if(errorFromServer)
		{
			String[] message = new String[1];
			message = getErrorMessageFocusGained();
			fireHandleErrorMessage(new MatafMessageEvent(this, message));
			setToolTipText(message[0]);
			// Indicates that last message was an error.
			lastWasError = true;			
		}
		else
			if(lastWasError)
			{
				fireHandleErrorMessage(new MatafMessageEvent(this, ""));
				lastWasError = false;
			}
		
		
		// Execute operation on a new and non-empty value.
		if ((dataString!=null) && !dataString.equals(getText()))
		{
			setText(dataString);
			DSECoordinationEvent newEvent = getNavigationParameters().generateCoordinationEvent(getType(),this);
			newEvent.setRefresh(true);
			newEvent.setEventName(getName()+".dataChanged");
			fireCoordinationEvent(newEvent);
		}
		else
			setText(dataString);
	}
	
	public void setErrorMessage(String errorMessage)
	{
		setErrorMessageFocusGained(errorMessage);
	}

	/**
	 * Apply special behavior on focus events.
	 */
	private void registerFocusListeners()
	{
		// UI.
		addFocusListener(new FocusListener() 
		{
			public void focusGained(FocusEvent e) 
			{
				setBackground(BACKGROUND_COLOR_IN_FOCUS);
			}
			
			public void focusLost(FocusEvent e) 
			{
				setBackground(isEditable() ? UIManager.getColor("TextField.background") 
												: UIManager.getColor("TextField.inactiveBackground"));
			}
		});
		
		// Model
		addFocusListener(new FocusListener()
		{
			public void focusGained(FocusEvent e)
			{
				focusGainedActions(e);
			}
			
			public void focusLost(FocusEvent e) 
			{
				 focusLostActions(e);
			}
		});
	}
	
	/**
	 * @see mataf.types.VisualPropertiesExchanger#setErrorFromServer(boolean)
	 */
	public void setInErrorFromServer(boolean errorFromServer) 
	{
		this.errorFromServer = errorFromServer;
	}
	
	/**
	 * If we're in table, consult the table for the error indication.
	 * @return
	 */
	public boolean isInErrorFromServer() 
	{
		if(inCellEditor)
		{
			int row = cellEditor.getEditingRow();
			int col = cellEditor.getEditingColumn();		
			return cellEditor.getTable().isCellInError(row, col);
		}
		else
			return errorFromServer;
	}
	
	/**
	 * Used when we're displaying an error message for a textfield that
	 * is used as a cell editor.
	 */
	private void displayErrorMessageByTable()
	{
		int row = cellEditor.getEditingRow();
		int col = cellEditor.getEditingColumn();
		MatafTable table = cellEditor.getTable();
		String errorMessage = 
				table.getOurModel().getErrorMessageAt(row, col);
		cellEditor.getTable().fireHandleErrorMessage(new MatafMessageEvent(table, new String[]{errorMessage}));
	}
	
	
	/**
	 * A convenient method for displaying an error message
	 * in the error message label bean.
	 * Sets the text color to red.
	 */
	public void displayErrorMessage(String errorMessage)
	{
		setForeground(Color.red);
		setErrorMessage(errorMessage);
		setToolTipText(errorMessage);
		if(inCellEditor)
			displayErrorMessageByTable();
		else
			fireHandleErrorMessage(new MatafMessageEvent(this, new String[]{errorMessage}));		
	}
	
	/**
	 * Clears the error message and restore the text color to black.
	 */
	public void clearErrorMessage()
	{
		setForeground(Color.black);
		setErrorMessage("");
		setToolTipText(null);
		
		if(inCellEditor)
		{
			MatafTable table = cellEditor.getTable();
			cellEditor.getTable().fireHandleErrorMessage(new MatafMessageEvent(table, new String[]{""}));
		}
		else
			fireHandleErrorMessage(new MatafMessageEvent(this, new String[]{""}));
	}
	
	/**
	 * Selectes all text upon entering and displays the error
	 * message recieved from the server.
	 */
	public void focusGainedActions(FocusEvent e)
	{
		// System.out.println("In MatafTextField(\""+getText()+"\") FocusGained !");
		
		reInstallFormatter();
		
		if(!isInCellEditor())
			selectAll();
	}
	
	
	/**
	 * Handles the case when the the focus traversal was triggered
	 * by the mouse.
	 */
	public void focusLostActions(FocusEvent e) 
	{
		//GLogger.debug("Performing focusLostActions of MatafTextField('"+getText()+"').");
		if(!keyboardTriggeredFocusTraversal)
		{
			if(inCellEditor)
			{
				// Commit the editing.
				cellEditor.stopCellEditing();
				
				// We clicked outside the table.
				if(e.getOppositeComponent()!=cellEditor.getTable())
				{
					System.out.println("Jumping outside the table.");
					activateFocusTraversal(false, false);
				}
				else // We clicked inside the table.
				{
					int currentColumn 		= cellEditor.getEditingColumn();
					int currentRow 		= cellEditor.getEditingRow();
					int jumpingToColumn 	= cellEditor.getTable().getSelectedColumn();
					int jumpingToRow 		= cellEditor.getTable().getSelectedRow();
					
					if(currentColumn<jumpingToColumn || jumpingToRow!=currentRow)
					{
						System.out.println("Jumping to a forward cell !");
						activateFocusTraversal(false, false);
					}
					else
						System.out.println("Jumping to a backward cell !");
				}
			}
			else
			{
				//System.out.println("Focus traversal was triggered by mouse.");
				Container focusCycleRoot = getFocusCycleRootAncestor();
				
				FocusTraversalPolicy focusPolicy = 
					focusCycleRoot.getFocusTraversalPolicy();
				
				if(focusPolicy!=null)
				{
					Component jumpFrom = this;
					Component jumpTo = e.getOppositeComponent();

					boolean directionForward =
						MatafFocusTraversalPolicy.isComponentAfter(focusPolicy, jumpTo, jumpFrom);
								
					if(directionForward)
					{
						//System.out.println("Traversing focus forward !, applying checks.");
						// Mouse(or non-keyboard trigger) activated the 
						// focus traversal forward.
						activateFocusTraversal(false, false);
					}
					//else
					//	System.out.println("Traversing focus backward !");
				}
				else
					GLogger.warn("No focus policy was found for component : "+this);
			}
		}
		
		keyboardTriggeredFocusTraversal = false;
	}
	
	/**
	 * Performs validations on the textfield before allowing
	 * the focus to leave the textfield.
	 * 	   
	 * @param oppositeComponent - The component to which the focus is about go.
	 * @return
	 */
	public boolean isFocusAllowedToLeave()
	{
		//System.out.println("Checking if focus is allowed to leave ?");
		// Check if textfield is required and empty.
		if(isRequired() && isEmpty())
		{
			//System.out.println("Required not passed.");
			displayErrorMessage("ערך חובה להקשה");
			return false;
		}

		// Check if we received an error from the server.
		if(isInErrorFromServer())
		{
			//System.out.println("Server check not passed.");
			displayErrorMessage(getErrorMessageFocusGained()[0]);
			selectAll();
			return false;
		}

		//System.out.println("passed.");
		// All validations passed.
		clearErrorMessage();
		return true;
	}

	
	/**
	 * Closely couple the enable property to the editable & focusable property.
	 */
	public void setEnabled(boolean enabled) 
	{
		//super.setEnabled(enabled);
		super.setEditable(enabled);
		super.setFocusable(enabled);
	}
	
	/**
	 * Returns true if the value is empty or consists only of
	 * white characters.
	 */
	public boolean isEmpty()
	{
		if(inCellEditor)
		{
			return "".equals(getTextFromTableModel());
		}
		else	
			return getText().equals("");
	}
	
	/**
	 * Method retrieves the data from the table model instead of from the
	 * model of the textfield.
	 * This method is used when the textfield is used in a cell editor and we
	 * want the real value from the table's model. 
	 * @return
	 */
	public String getTextFromTableModel()
	{
		if(inCellEditor)
		{
			int row = cellEditor.getEditingRow();
			int col = cellEditor.getEditingColumn();
			Object val = cellEditor.getTable().getValueAt(row, col);
			return String.valueOf(val);
		}
		else
			throw new IllegalStateException("Cannot invoke getTextFromTableModel() when textfield is not used as a CellEditor.");
	}
	
	/**
	 * Returns true if the textfields is assigned to a table as a 
	 * cell editor.
	 */
	public boolean isInCellEditor()
	{
		return inCellEditor;
	}
	
	public void setInCellEditor(boolean inCellEditor)
	{
		this.inCellEditor = inCellEditor;
	}
	
	/**
	 * Sets the cellEditor to which this textfield is assigned to.
	 */
	public void setCellEditor(MatafTableCellEditor cellEditor)
	{
		if(cellEditor!=null)
		{
			inCellEditor = true;
			this.cellEditor = cellEditor;
		}
		else
			inCellEditor = false;
	}
	
	public MatafTableCellEditor getCellEditor()
	{
		return cellEditor;
	}

	/**
	 * @see mataf.exchangers.VisualPropertiesExchanger#setForeground(Color)
	 */
	public void setForeground(Color foreground) 
	{
		super.setForeground(foreground);
	}
	
	/**
	 * If recieved error from server, display the text in red.
	 */
	public void setColors() 
	{
		if(errorFromServer)
			setForeground(Color.red);
		else
			setForeground(Color.black);
	}

	/**
	 * @see mataf.exchangers.VisualPropertiesExchanger#setRequired(boolean)
	 */
	public void setRequired(boolean required) 
	{
		this.required = required;
	}
	
	/**
	 * The required property.
	 */
	public boolean isRequired() 
	{
		return required;
	}
	
	
	
	/**
	 * @return
	 */
	public String getPreviousText()
	{
		return previousText;
	}

	/**
	 * @param string
	 */
	public void setPreviousText(String string)
	{
		previousText = string;
	}

	
	/**
	 * Handle special case where we need to transfer 
	 * the focus while we're used as a CellEditor.
	 * 
	 * @see java.awt.Component#transferFocus()
	 */
	public void transferFocus() 
	{
		if(isInCellEditor())
        {        				
			cellEditor.selectNextCell();
        }
        else 
        	super.transferFocus();
	}
	
	/**
	 * Handle special case where we need to transfer 
	 * the focus while we're used as a CellEditor.
	 * 
	 * @see java.awt.Component#transferFocusBackward()
	 */
	public void transferFocusBackward()
	{
		if(isInCellEditor())
		{        				
			cellEditor.selectPreviousCell();
		}
		else 
			super.transferFocusBackward();
	}

	
	/**
	 * Returns true if the textfield currently holds
	 * the maximal number of chars.
	 * Used by the formatters to decide wether to transfer focus or not.
	 * 
	 * @return true - if the textfield currently holds
	 * the maximal number of chars.
	 */
	public boolean isMaxCharsReached()
	{
		if(getMaxChars()==Integer.MAX_VALUE)
			return false;
		return getText().length()==getMaxChars();
	}
	
	/**
	 * Updates the display pattern according to the max chars property.
	 *
	 */
	public void setMaxChars(int maxChars)
	{
		this.maxChars = maxChars;
	}
	
	/**
	 * Allows to switch between overwrite state by pressing INSERT
	 * while editing the textfield.
	 */
/*	private void addInsertFunctionality()
	{
		getInputMap().put(KeyStroke.getKeyStroke("INSERT"),"toggleInsert");
		
		getActionMap().put("toggleInsert",new AbstractAction() 
		{
			public void actionPerformed(ActionEvent e)
			{					
				DefaultFormatter formatter = (DefaultFormatter)getFormatter();
				System.out.print("Toggeling insert state, ");
				
				// Toggle between overwrite states.				
				formatter.setOverwriteMode(!formatter.getOverwriteMode());
				System.out.println("overwrite=" + formatter.getOverwriteMode());
			}
		});
	}*/
	
	/**
	 * By default returns the text.
	 * Subclasses should override this method for special behavior.
	 * @return
	 */
	public String getUnFormattedText()
	{
		return getText();
	}
	
	
	
	
	/**
	 * Returns the text as displayed by the DisplayFormatter.
	 * If no DisplayFormatter is installed returns the text as
	 * displayed by the DefaultFormatter.
	 *  
	 * @return
	 */
	public String getDisplayText()
	{
		DefaultFormatterFactory dff = 
						(DefaultFormatterFactory)getFormatterFactory();
		try
		{
			return dff.getDisplayFormatter().valueToString(new Double(getUnFormattedText()));
		}
		catch (Exception e)
		{	
			// No display formatter is available. Just return the text.			
			//e.printStackTrace();
			return getText();
		}
	}
	
///////////////////////////////////////////////////////////////////////////////
////////////////////// C O M P O S E R   V A R I A B L E S ////////////////////
///////////////////////////////////////////////////////////////////////////////
	
	
	/**
	 * 
	 */
	public Object getDataValue() 
	{
		return getDisplayText();
	}
	
	/**
	 * Gets the DSECoordinatedPanel where the component is placed
	 * @return com.ibm.dse.gui.DSECoordinatedPanel
	 */
	public DSECoordinatedPanel getDSECoordinatedPanel() {
		return DSECoordinatedPanel.getDSECoordinatedPanel(this);
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
	 * Fires a DSECoordinationEvent when data has changed.
	 */
	public void fireCoordinationEvent() 
	{
		DSECoordinationEvent newEvent = getNavigationParameters().generateCoordinationEvent(getType(),this);
		newEvent.setRefresh(true);
		newEvent.setEventName(getName()+".dataChanged");
		fireCoordinationEvent(newEvent);
	}
	/**
	 * Fires a DSECoordinationEvent when data has changed.
	 * @param event DSECoordinationEvent
	 */
	public void fireCoordinationEvent(DSECoordinationEvent event) {
		setColors();
		if (aCoordinatedEventListener == null)
		{ 
			// If we're in a table, let the table fire the event.
			if(isInCellEditor())
			{
				getCellEditor().getTable().fireCoordinationEvent(event);
			}
			
			return;
		}
		aCoordinatedEventListener.handleDSECoordinationEvent(event);
	}
	
	/**
	 * Sets the errorMessageFocusGained property (java.lang.String) value.
	 * @param errorMessageFocusGained String, the new value for the property
	 */
	protected void setErrorMessageFocusGained(String newValue) {
		errorMessageFocusGained=new String[1];
		if (newValue==null)
			errorMessageFocusGained[0]="";
		else 
			errorMessageFocusGained[0] = newValue;
	}
	
	/**
	 * Gets the errorMessageFocusGained property (java.lang.String) value.
	 * @return String[] - the errorMessageFocusGained property value
	 */
	public String[] getErrorMessageFocusGained() 
	{
		if(inCellEditor)
		{
			int row = cellEditor.getEditingRow();
			int col = cellEditor.getEditingColumn();		
			return new String[]{cellEditor.getTable().getOurModel().getErrorMessageAt(row, col)};
		}
		else
			return errorMessageFocusGained;
	}
	
	/**
	 * Gets the navigationParameters property (com.ibm.dse.gui.NavigationParameters) value.
	 * @return The navigationParameters property value.
	 * @see #setNavigationParameters
	 */
	public NavigationParameters getNavigationParameters() {
		return fieldNavigationParameters;
	}
	
	/**
	 * Adds an CoordinatedEventListener.
	 * @param newListener CoordinatedEventListener
	 */
	public void addCoordinatedEventListener(com.ibm.dse.gui.CoordinatedEventListener newListener) 	{
		aCoordinatedEventListener = CoordinatedEventMulticaster.add(aCoordinatedEventListener, newListener);
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
	 * Gets the SpPanel where the component is placed
	 * @return com.ibm.dse.gui.SpPanel
	 */
	public SpPanel getSpPanel() {
		return SpPanel.getSpPanel(this);
	}
	
	/**
	 * @see com.ibm.dse.gui.DataExchanger#getDataName()
	 */
	public String getDataName() 
	{
		return dataName;
	}

	/**
	 * @see com.ibm.dse.gui.DataExchanger#setDataName(String)
	 */
	public void setDataName(String dataName)
	{
		this.dataName = dataName;
	}

	/**
	 * @see com.ibm.dse.gui.DataExchanger#addDataChangedListener(DataChangedListener)
	 * @deprecated
	 */
	public void addDataChangedListener(DataChangedListener adl) {
	}

	/**
	 * @see com.ibm.dse.gui.DataExchanger#getAlternativeDataName()
	 */
	public String getAlternativeDataName() {
		return "";
	}

	/**
	 * @see com.ibm.dse.gui.DataExchanger#getDataDirection()
	 */
	public String getDataDirection() {
		return dataDirection;
	}

	/**
	 * @see com.ibm.dse.gui.DataExchanger#getErrorMessage()
	 */
	public String getErrorMessage() {
		return "No Error";
	}

	/**
	 * Gets the type property (java.lang.String) value.
	 * @return The type property value.
	 * @see #setType
	 */
	public String getType() {
		return type;
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
	 * @see com.ibm.dse.gui.DataExchanger#removeCoordinatedEventListener(CoordinatedEventListener)
	 */
	public void removeCoordinatedEventListener(CoordinatedEventListener newListener) {
	}

	/**
	 * @see com.ibm.dse.gui.DataExchanger#removeDataChangedListener(DataChangedListener)
	 */
	public void removeDataChangedListener(DataChangedListener dcl) {
	}

	/**
	 * @see com.ibm.dse.gui.DataExchanger#setAlternativeDataName(String)
	 */
	public void setAlternativeDataName(String o) {
	}

	/**
	 * @see com.ibm.dse.gui.DataExchanger#setDataDirection(String)
	 */
	public void setDataDirection(String dataDirection) 
	{
		this.dataDirection = dataDirection;
	}

	/**
	 * @see com.ibm.dse.gui.DataExchanger#setHelpID(String)
	 */
	public void setHelpID(String helpID) {
	}

	/**
	 * Sets the navigationParameters property (com.ibm.dse.gui.NavigationParameters) value.
	 * @param navigationParameters The new value for the property.
	 * @see #getNavigationParameters
	 */
	public void setNavigationParameters(NavigationParameters navigationParameters) {
		fieldNavigationParameters = navigationParameters;
	}

	/**
	 * Sets the type property (java.lang.String) value.
	 * @param type The new value for the property.
	 * @see #getType
	 */
	public void setType(java.lang.String type) {
		this.type = type;
	}

	/**
	 * @see com.ibm.dse.gui.PanelActions#getDataToClear()
	 */
	public String getDataToClear() {
		return "";
	}	
	
	/**
	 * @see java.awt.Component#toString()
	 */
	public String toString() 
	{
		return "DataName : "+getDataName()+" Text : " + getText()+" "+super.toString();
	}
}

