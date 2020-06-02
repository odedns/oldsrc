package mataf.desktop.views;

import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.JRootPane;
import javax.swing.JSplitPane;
import javax.swing.event.AncestorEvent;

import sun.security.util.DerEncoder;

import mataf.data.VisualDataField;
import mataf.desktop.MatafTaskButton;
import mataf.desktop.beans.MatafFocusTracker;
import mataf.events.MatafMessageEvent;
import mataf.exchangers.VisualPropertiesExchanger;
import mataf.logger.GLogger;
import mataf.types.MatafButton;
import mataf.types.MatafEmbeddedPanel;
import mataf.types.MatafErrorLabel;

import com.ibm.dse.base.DSEException;
import com.ibm.dse.base.DSEObjectNotFoundException;
import com.ibm.dse.base.OperationRepliedEvent;
import com.ibm.dse.desktop.Desktop;
import com.ibm.dse.desktop.NavigationController;
import com.ibm.dse.desktop.TaskButton;
import com.ibm.dse.gui.DSECoordinatedPanel;
import com.ibm.dse.gui.DSEPanel;
import com.ibm.dse.gui.ErrorMessageEvent;
import com.ibm.dse.gui.Outsider;
import com.ibm.dse.gui.SpButton;
import com.ibm.dse.gui.SpFocusTracker;
import com.ibm.dse.gui.SpRadioButton;

/**
 * This class provides the basic functionality that every view
 * in the application will need.<p>
 * 
 * It modifies and improves the performance of the DSEPanel.
 * 
 * There are two important classes that inherits this class : <p>
 * <code> MatafClientView </code> and <code> MatafTransactionView. </code>
 * 
 * @author Nati Dykshtein. Created : 26/05/03
 */
public class MatafDSEPanel extends DSEPanel
{
	/** Needed to synthesize the java 1.4 FocusPolicy with the Composer's.*/
	private MatafFocusTracker focusTracker;

	protected MatafErrorLabel errorLabel;

	/** Contains all the VisualPropertiesExchangers.*/
	private Vector visualPropertiesExchangers = new Vector();

	public MatafDSEPanel()
	{
		super();

		// Set the default component orientation.
		setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

		// We handle this ourselves.
		setDisableWhileOperationRunning(false);

		// Remove the border.
		setBorder(null);

		addDebugListeners();
	}

	private void addDebugListeners()
	{
		addMouseListener(new MouseAdapter()
		{
			public void mousePressed(MouseEvent e)
			{
				System.out.println("Context is : " + getContextName());
				Component c =
					KeyboardFocusManager
						.getCurrentKeyboardFocusManager()
						.getFocusOwner();
				System.out.println("Current Focus Owner : " + c);
			}
		});
	}
	
	/**
	 * Prevents adding component directly to the panel.
	 */
	public Component add(Component c) {
		throw new RuntimeException("Do not use add() on an instance of " + "MatafDSEPanel, use addTransactionView" + "(CoordinatedPanel cp) instead.");
	}

	/**
	 * Adds the following functionality : 
	 * 1.Registers the SplitPane components on the panel.
	 * 2.Update the vector of the VisualPropertiesExchangers.
	 */
	protected void register(Component c)
	{
		super.register(c);
		if (c instanceof JSplitPane)
		{
			JSplitPane split = (JSplitPane)c;
			register(split.getRightComponent());
			register(split.getLeftComponent());
		}

		if (c instanceof VisualPropertiesExchanger)
			visualPropertiesExchangers.add(c);
	}

	/**
	 * Keeps a vector of the VisualPropertiesExchangers.
	 */
	public void registerOutsider(Outsider outsider)
	{
		super.registerOutsider(outsider);

		if (outsider instanceof VisualPropertiesExchanger)
			visualPropertiesExchangers.add(outsider);
	}

	/**
	 *  Refresh the VisualPropertiesExchangers.
	 */
	public void refreshDataExchangers()
	{
		super.refreshDataExchangers();

		for (Enumeration e = visualPropertiesExchangers.elements();
			e.hasMoreElements();
			)
		{
			VisualPropertiesExchanger ve =
				(VisualPropertiesExchanger)e.nextElement();
			try
			{
				ve.exchangeVisualProperties(getContext());
			}
			catch (NullPointerException ex)
			{
				//GLogger.warn("No DataName Assigned for object : "+ve.getClass().getName());
			}
			catch (StringIndexOutOfBoundsException ex)
			{
				//GLogger.warn("No DataName Assigned for object : "+ve.getClass().getName());
			}
		}
	}

	/**
	 * Uses our FocusTracker to involve the FocusTraversalPolicy.
	 */
	public SpFocusTracker getFocusManager()
	{
		if (focusTracker == null)
		{
			focusTracker = new MatafFocusTracker(this);
		}

		return focusTracker;
	}

	/**
	 * Remove sp's 'special' beahvior for keys.
	 */
	public void key(KeyEvent e)
	{
		// DO NOTHING !.
	}

	/**
	 * Not needed - removed for performace considerations.
	 */
	public void commit()
	{
		// DO NOTHING !.
	}

	/**
	 * Not needed - removed due to a bug.
	 */
	public void saveData()
	{
		// DO NOTHING !.
	}

	/**
	 * Remove sp's special beahvior for handling the state of the
	 * execute buttons.
	 */
	public void enableExecuteButton(boolean enable)
	{
		// DO NOTHING !
	}

	/**
	 * 
	 * Override's sp's setEnabled() by invoking a deprecated method.
	 */
	public void setEnabled(boolean isEnabled)
	{
		enable(isEnabled);
	}

	/**
	 * The last point to refresh the data exchangers before displaying
	 * the view.(Must be a better way ?)
	 */
/*	public void setVisible(boolean isVisible)
	{
		super.setVisible(isVisible);
		if (isVisible)
		{
			try
			{
				refreshDataExchangers();	
			}
			catch (Exception e)
			{
				// Catch it for the Visual Editor.
			}
			
			if(isFirstVisible())
			{
				setFirstVisible(false);
				try
				{
					if(getExecuteWhenOpen() && getOperation()!=null) 
						getOperation().execute();
				}
				catch (Exception e1)
				{
					e1.printStackTrace();
				}
			}
		}
	}*/

	/**
	 * Convenient method for referencing a VisualDataField from 
	 * this view's context.
	 */
	public VisualDataField getVisualDataField(String elementName)
	{
		try
		{
			return (VisualDataField)getContext().getElementAt(elementName);
		}
		catch (ClassCastException cce)
		{
			cce.printStackTrace();
			return null;
		}
		catch (DSEObjectNotFoundException donfe)
		{
			donfe.printStackTrace();
			return null;
		}
	}

	/**
	 * Methods adds the ability to close the view from the context by
	 * setting the "trxEnded" element in the context to true.
	 * 
	 * This is used to enable the server to close a view by setting this
	 * parameter through the context.
	 * 
	 * @see com.ibm.dse.gui.DSECoordinatedPanel#handleOperationRepliedEvent(OperationRepliedEvent)
	 */
	public void handleOperationRepliedEvent(OperationRepliedEvent e)
	{
		super.handleOperationRepliedEvent(e);
		try
		{
			String endTransaction = (String)getValueAt("trxEnded");
			if ("true".equalsIgnoreCase(endTransaction))
			{
				//closeCurrentView();
				// Reset the flag.
				setValueAt("trxEnded", "false");
				System.out.println("View closed from server.");
			}
		}
		catch (Exception ex)
		{
			GLogger.warn(
				"The mechanism for closing the view from the server "
					+ "COULD NOT be implemented becuase the field 'trxEnded' "
					+ "could not be found in context : "
					+ getContext().getName());
		}
	}

	/**
	 * Special handling for close-view events.
	 */
	//	public void handleDSECoordinationEvent(DSECoordinationEvent event) {
	//		if (event.getEventSourceType().equals(DSECoordinationEvent.EVENT_SOURCETYPE_CLOSE)) {
	//			try {
	//				// Close the view.
	//				Desktop.getDesktop().getTaskArea().getCurrentTask().closeTask();
	//			} catch (Exception e) {
	//				e.printStackTrace();
	//			}
	//		}
	//
	//		super.handleDSECoordinationEvent(event);
	//	}

	/**
	 * Adding the buttongroups as Outsiders to the DSEPanel.
	 */
	public void registerPanelButtonGroups(MatafEmbeddedPanel matafEmbeddedPanel)
	{
		Component[] comps = matafEmbeddedPanel.getComponents();
		for (int i = 0; i < comps.length; i++)
		{
			// Register the group of this RadioButton.
			if (comps[i] instanceof SpRadioButton)
			{
				Outsider outsider =
					(Outsider) ((SpRadioButton)comps[i]).getSpButtonGroup();
				registerOutsider(outsider);
			}
		}
	}

	/**
	 * Invoked when the current view is closed.
	 * Overrides DSEPanel's close() to notify parent view that it's child is closing
	 * prior to closing itself.
	 */
	public void close(boolean unchainContext)
	{
		TaskButton taskButton =
			Desktop.getDesktop().getTaskArea().getCurrentTask();

		if (taskButton instanceof MatafTaskButton)
		{
			MatafTaskButton matafTaskButton = (MatafTaskButton)taskButton;

			NavigationController navigator =
				matafTaskButton.getNavigationController();

			// Get the view id of the parent view.
			String parentViewID = navigator.getPreviousView(this.getViewName());

			// View has a parent.
			if ((parentViewID != null) && !parentViewID.equals(""))
			{
				// Get the parent view.										
				DSECoordinatedPanel view =
					(DSECoordinatedPanel)navigator.getViewInstance(
						parentViewID);

				// Notify parent that child view is closing.
				if (view instanceof MatafDSEPanel)
					 ((MatafDSEPanel)view).childViewClosed(this.getViewName());
			}
		}

		// Deregister this view as DSECoordinatedListener.
		// This is done for components that resides in more than one view.
		deRegisterOurselves();

		// 'Clean' the view leftovers.
		try
		{
			// Close the operation if any.
			if (getOperation() != null)
				getOperation().close();
			// Remove context.
			if (unchainContext && getContext() != null)
				getContext().unchain();
		}
		catch (DSEException e)
		{
			e.printStackTrace();
		}
	}
	
	
	
	private void deRegisterOurselves()
	{
		deRegisterOurselvesFromVector(getNextButtons());
		deRegisterOurselvesFromVector(getPrevButtons());
		deRegisterOurselvesFromVector(getExecuteButtons());
	}
	
	private void deRegisterOurselvesFromVector(Vector v)
	{
		for(int i=0;i<v.size();i++)
			((SpButton)v.elementAt(i)).removeCoordinatedEventListener(this);
	}

	/**
	 * Test close view.
	 *
	 */
	/*	public void closeView() 
		{
			DSENavigationController navigationController = (DSENavigationController) 
					((DSETaskButton) Desktop.getDesktop().getTaskArea().getCurrentTask()).getNavigationController();
			navigationController.closeView(getViewName());
			try {
				getContext().unchain();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}*/

	/**
	 * This method is invoked when the child view of this view is being closed.
	 * 
	 * @param event - The DSECoordinationEvent that caused the child view to close.
	 */
	public void childViewClosed(String childViewName)
	{
		// Empty Implementation.
	}

	/**
	 * Overide to return the reference to the error label.
	 */
	public MatafErrorLabel getTheErrorLabel()
	{
		// Empty Implementation.
		return null;
	}

	/**
	 * Handles the messages according to its type :
	 * ERROR_MESSAGE, INFORMATION_MESSAGE, LOADING_MESSAGE.
	 * Note : The message must be of type MatafMessageEvent to be
	 * 		  handled in a type-sensitive way. 
	 */
	public void handleErrorMessage(ErrorMessageEvent eme)
	{
		// No error label was found in this view.
		if (getTheErrorLabel() == null)
			return;

		// Get the messages.
		String[] messages = eme.getErrorMessageList();

		// No messages to handle.
		if (messages == null || messages.length == 0)
			return;

		// Remove previous icon.
		getTheErrorLabel().hideErrorIcon();

		// We want to clear the error message.
		if ("".equals(messages[0]))
		{
			getTheErrorLabel().queueErrorMessage("");
			return;
		}

		if (eme instanceof MatafMessageEvent)
		{
			MatafMessageEvent messageEvent = (MatafMessageEvent)eme;

			// Configure messages according to type :

			if (messageEvent
				.getMessageType()
				.equals(MatafMessageEvent.ERROR_MESSAGE))
			{
				getTheErrorLabel().showErrorIcon();
				getTheErrorLabel().queueErrorMessage(messages[0], Color.red);
			}

			if (messageEvent
				.getMessageType()
				.equals(MatafMessageEvent.INFORMATION_MESSAGE))
			{
				getTheErrorLabel().queueErrorMessage(messages[0], Color.blue);
			}

			if (messageEvent
				.getMessageType()
				.equals(MatafMessageEvent.LOADING_MESSAGE))
			{
				getTheErrorLabel().setForeground(Color.blue);
				getTheErrorLabel().setText(messages[0]);
			}
		}
		else
			getTheErrorLabel().queueErrorMessage(messages[0]);
	}

	/**
	 * Overrides DSEPanel's to do nothing.
	 */
	public void ancestorAdded(AncestorEvent event)
	{
	}

	/**
	 * Overrides DSEPanel's to do nothing.
	 */
	public void ancestorMoved(AncestorEvent event)
	{
	}

	/**
	 * Overrides DSEPanel's to do nothing.
	 */
	public void ancestorRemoved(AncestorEvent event)
	{
	}

	/**
	 * Not needed - removed for performace considerations.
	 */
	public SpButton get_RepeatButton()
	{
		return null;
	}

	public boolean isRefreshingExchangers()
	{
		return refreshing;
	}
}