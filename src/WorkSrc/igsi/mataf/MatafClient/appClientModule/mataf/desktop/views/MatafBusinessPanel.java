package mataf.desktop.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.NoSuchElementException;
import java.util.Vector;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import mataf.desktop.MatafNavigationController;
import mataf.desktop.MatafTaskButton;
import mataf.desktop.beans.MatafWorkingArea;
import mataf.types.MatafEmbeddedPanel;
import mataf.types.MatafErrorLabel;
import mataf.types.MatafLinkList;
import mataf.types.MatafMessagesPane;
import mataf.types.MatafTabbedPane;

import com.ibm.dse.desktop.Desktop;

/**
 * This panel contains the general information panel and a tabbed pane
 * the hold all the open transaction views.
 * 
 * @author Nati Dykstein. Creation Date : (13/04/2004 17:24:42).  
 */
public class MatafBusinessPanel extends MatafEmbeddedPanel
									implements ChangeListener
{
	private static final Color 	BG_COLOR = new Color(203,223,250);
	
	/** Default size of business panel.*/
	public static final Dimension DEFAULT_BUSINESS_SIZE = new Dimension(782, 520);
	
	/** Used to fill the background when no transaction is open.*/
	private final FIBIBackground 	BG_COMPONENT = new FIBIBackground(); 	
	
	private MatafGeneralInformationPanel generalInfoPanel;
	
	private MatafTabbedPane tabbedPane = new MatafTabbedPane();
	
	/** Contains all the open transactions. */
	private Vector	businessPanes = new Vector();
	
	/** Reference to the error label of the client view.*/
	private MatafErrorLabel errorLabel;
	
	/** Holds the currently selected businessPane.*/
	private MatafBusinessPane selectedPane;

	/**
	 * 
	 */
	public MatafBusinessPanel()
	{
		super(new BorderLayout());
		
		//setBackgroundPainted(true);
		setBackground(BG_COLOR);
		
		// Create the header panel.
		generalInfoPanel = new MatafGeneralInformationPanel();

		// Create and Initialize the tabbed pane.
		initTabbedPane();
				
		// Set its font.
		//tabbedPane.setFont(FontFactory.getDefaultFont());
		
		// Initially display a nice image as background.		
		add(BG_COMPONENT, BorderLayout.CENTER);
		
		setPreferredSize(DEFAULT_BUSINESS_SIZE);
		setSize(DEFAULT_BUSINESS_SIZE);
	}
	
	private void initTabbedPane()
	{
		// Create the business tabbed pane.
		tabbedPane.setTabPlacement(JTabbedPane.BOTTOM);
		
		// Add listener to the tabbed pane.
		tabbedPane.addChangeListener(this);
		
		// Removed the tab's border.
		tabbedPane.setBorder(null);
	}
	
	/**
	 * Actions performed when putting the first transaction panel.
	 */
	private void firstPaneWasAdded()
	{
		remove(BG_COMPONENT);
		remove(errorLabel);
		add(generalInfoPanel, BorderLayout.NORTH);		
		add(tabbedPane, BorderLayout.CENTER);
		validate();
		repaint();
	}
	
	/**
	 * Actions performed when removing the last transaction panel.
	 * PENDING : The client context is begin cleared after closing a task.
	 * 			  This should be handled differently when dealing with
	 * 				multiple clients.
	 */
	private void lastPaneWasRemoved()
	{
		remove(generalInfoPanel);
		remove(tabbedPane);
		add(BG_COMPONENT, BorderLayout.CENTER);
		add(errorLabel, BorderLayout.SOUTH);
		selectedPane=null;
		
		// Clear the client context.
		MatafWorkingArea.getActiveClientView().getContext().clearKeyedCollection();
		
		// Update screen.
		MatafWorkingArea.getActiveClientView().refreshDataExchangers();
		
		validate();
		repaint();
	}
	
	/**
	 * Creates a new pane with the new transaction view.
	 * @param transactionView
	 */
	public void addTransactionView(MatafTransactionView transactionView)
	{
		// When putting the first transactionPanel, remove the background
		// and add the tabbed pane.
		if(businessPanes.size()==0)
			firstPaneWasAdded();
		
		// Wrap the transaction panel inside the BusinessPane.
		MatafBusinessPane bPane = new MatafBusinessPane(transactionView);
		
		// Add the business pane to the vector.
		businessPanes.add(bPane);
		
		// Add it to the tabbedpane.
		tabbedPane.addTab(getTitleOf(bPane), bPane);
		
		// Set the new transaction view as active.
		tabbedPane.setSelectedComponent(bPane);
		
		// Update our selected pane.
		selectedPane = bPane;
	}
	
	/**
	 * Removes a transaction view from the tabbed pane.
	 * 
	 * @param transactionView
	 */
	public void removeTransactionView(MatafTransactionView transactionView)
	{
		// Get the business pane of the transaction view.
		MatafBusinessPane bPane = getBusinessPaneOfTransactionView(transactionView);
		
		// Remove the business pane from the vector.
		businessPanes.remove(bPane);
		
		// Remove the business pane from the tabbed pane.
		int tabIndex = getTabIndexOf(bPane);
		if(tabIndex!=-1)
			tabbedPane.removeTabAt(tabIndex);
		else
			throw new NoSuchElementException("Failed to remove transaction view " +				"\"" + transactionView.getClass().getName() + "\"" +				" from the client view. View does not exist.");		
		// Check if we removed the last pane.
		if(businessPanes.size()==0)
			lastPaneWasRemoved();
	}
	
	/**
	 * Returns the tab index of the business pane.
	 * 
	 * @param businessPane 
	 * @return -1 if the panel was not part of the tabbedpane.
	 */
	private int getTabIndexOf(MatafBusinessPane businessPane)
	{
		String title = getTitleOf(businessPane);
		for(int i=0;i<tabbedPane.getTabCount();i++)
		{
			if(title.equals(tabbedPane.getTitleAt(i)))
				return i;
		}
		
		return -1;
	}
	
	/**
	 * Returns the task description as the tab's title.
	 * 
	 * @param businessPane
	 * @return
	 */
	private String getTitleOf(MatafBusinessPane businessPane)
	{
		try
		{
			return businessPane.getTaskButton().getText();
		}
		catch(NullPointerException e)
		{
			System.err.println("Failed to retrieve task name.");
			return "Task "+tabbedPane.getTabCount();
		}
	}
	
	/**
	 * Returns the active business pane.
	 * A business pane contains a transaction view and an error label.
	 * @return
	 */
	public MatafBusinessPane getActiveBusinessPane()
	{
		return (MatafBusinessPane)tabbedPane.getSelectedComponent();
	}
	
	/**
	 * Returns the active transaction view.
	 * @return
	 */
	public MatafTransactionView getActiveTransactionView()
	{
		return getActiveBusinessPane().getTransactionView();
	}
	
	/**
	 * Returns the business pane of the specified transaction view.
	 * @param transactionView
	 * @return null if none of the business panes contains the specified view.
	 */
	public MatafBusinessPane getBusinessPaneOfTransactionView(MatafTransactionView transactionView)
	{
		for(int i=0;i<tabbedPane.getTabCount();i++)
		{
			MatafBusinessPane mbp = (MatafBusinessPane)tabbedPane.getComponentAt(i);
			
			if(transactionView.equals(mbp.getTransactionView()))
				return mbp;
		}
		
		return null;
	}
	
	/**
	 * Make transactionView become the active transactionView in the tabbedPane. 
	 * @param transactionView
	 */
	public void activateTransactionView(MatafTransactionView transactionView)
	{
		tabbedPane.setSelectedComponent(getBusinessPaneOfTransactionView(transactionView));
	}
	
	public void setTheErrorLabel(MatafErrorLabel errorLabel)
	{
		this.errorLabel = errorLabel;
	}
	
	/**
	 * Invoked when changing tabs :	
	 * 1.Deregister the business messages pane from the previous view
	 * 		and register it to the active view.
	 * 2.Deliver the focus to the active view.
	 * 3.Refresh the view.
	 * 
	 */
	public void stateChanged(ChangeEvent e)
	{
		System.out.println("TAB CHANGED ");
		
		// Tab is just being created.
		if(selectedPane==null)
			return;
			
		// Last tab was removed.
		if(tabbedPane.getSelectedComponent()==null)
			return;
		
		MatafBusinessPane mbp = getActiveBusinessPane();
		MatafTaskButton currentTask = (MatafTaskButton) Desktop.getDesktop().getTaskArea().getCurrentTask();
		MatafNavigationController currentTaskNC = (MatafNavigationController) currentTask.getNavigationController(); 
		// Update the activated task button.(Temporary)
		MatafTaskButton task = mbp.getTaskButton();
		if (currentTask != task & (currentTaskNC.isTaskAccessible()))
			task.activate();
		
		// Get the previous view.
		MatafTransactionView previousView = selectedPane.getTransactionView();
		
		// Deregister it from the previous transaction view.
		deregisterComponentsFromPreviousView(previousView);
		
		// Update selectedPane active business panel.
		selectedPane = getActiveBusinessPane();
		
		// Update active view to the selected pane.
		MatafTransactionView activeView = selectedPane.getTransactionView();
		
		// Register the messages pane to the active transaction view.
		registerComponentsInActiveView(activeView);
			
		// Refresh the active view.
		activeView.refreshDataExchangers();
	}
	
	/**
	 * Deregister components from the previuos view.
	 * 
	 * @param view
	 */
	private void deregisterComponentsFromPreviousView(MatafTransactionView view)
	{
		
		MatafClientView mcvCurrent=MatafWorkingArea.getActiveClientView();
		
		// Get the client's message pane to remove.
		 MatafMessagesPane mmp = mcvCurrent.getMatafMessagesPane();
			 
		view.removeDataExchanger(mmp);
		
		MatafLinkList mllQueries=mcvCurrent.getSheiltotPanel().getMatafLinkList();
		view.removeDataExchanger(mllQueries);
		
	}

	/**
	 * Register components from in the active view.
	 * 
	 * @param view
	 */
	private void registerComponentsInActiveView(MatafTransactionView view)
	{
		MatafClientView mcvCurrent=MatafWorkingArea.getActiveClientView();
		// Get the client's message pane to register.
		MatafMessagesPane mmp = mcvCurrent.getMatafMessagesPane();
		view.registerOutsider(mmp);
		
		MatafLinkList mllQueries=mcvCurrent.getSheiltotPanel().getMatafLinkList();
		view.registerOutsider(mllQueries);
		
		
	}


//	public static void main(String[] args)
//	{
//		JFrame f = new JFrame("Testing Business Panel");
//		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		MatafBusinessPanel bPanel = new MatafBusinessPanel();
//		f.getContentPane().add(bPanel, BorderLayout.CENTER);
//		f.pack();
//		f.setVisible(true);
//		f.getAccessibleContext();
//		bPanel.addTransactionView(new MatafTransactionView(new SlikaPanel1()));
//		bPanel.addTransactionView(new MatafTransactionView(new SlikaPanel2()));
//		bPanel.addTransactionView(new MatafTransactionView(new SrikaPanel1()));
//	}
}

