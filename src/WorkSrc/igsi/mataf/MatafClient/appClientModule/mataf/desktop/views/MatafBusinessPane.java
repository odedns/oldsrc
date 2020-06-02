package mataf.desktop.views;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JScrollPane;

import mataf.desktop.MatafTaskButton;
import mataf.types.MatafEmbeddedPanel;
import mataf.types.MatafErrorLabel;
import mataf.types.MatafScrollPane;

import com.ibm.dse.desktop.Desktop;

/**
 * This panel contains a transaction view.
 * Each tab in the tabbed pane contains one MatafBusinessPane.
 * 
 * @author Nati Dykstein. Creation Date : (13/04/2004 18:12:08).  
 */
public class MatafBusinessPane extends MatafEmbeddedPanel
{
	MatafTransactionView transactionView;
	MatafErrorLabel		errorLabel;
	
	/** We temporarly associate this pane with a task button.
	 * This will be removed when we the TaskButton functionality will
	 * be integerated into the MatafBusinessPanel.
	 */
	MatafTaskButton		taskButton;
	
	/**
	 * Init the panel.
	 */
	public MatafBusinessPane()
	{
		super(new BorderLayout());
		setBorder(null);
		setOpaque(true);
		setFocusable(false);
	}
	
	/**
	 * Needed to prevent the pane from getting the focus
	 * when switching between tabs.
	 */
	public void requestFocus()
	{
		// Do Nothing.
	}

	
	/**
	 * Init the panel with a new transaction panel.
	 */
	public MatafBusinessPane(MatafTransactionView transactionView)
	{
		super(new BorderLayout());
		
		this.transactionView = transactionView;
		
		// PENDING : This is only temporary.
		try
		{
			taskButton = 
				(MatafTaskButton)Desktop.getDesktop().getTaskArea().getCurrentTask();
		}
		catch(NullPointerException e)
		{
			System.err.println("Detected running without the desktop.");
		}
		
		// Remove our border.
		setBorder(null);
		
		// Draw the pane with background.
		setOpaque(true);
		
		//setBackground(Color.RED);
				
		transactionView.setPreferredSize(MatafEmbeddedPanel.DEFAULT_TRANSACTION_SCREEN_SIZE);
		
		MatafScrollPane matafScroller = new MatafScrollPane(transactionView);
		//matafScroller.getViewport().setBackground(Color.green);
		matafScroller.setBorder(null);
		matafScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
				
		add(matafScroller, BorderLayout.CENTER);		
		
		MatafErrorLabel errorLabel = (MatafErrorLabel)transactionView.getErrorBean();
				
		add(errorLabel, BorderLayout.SOUTH);
	}
	
	public MatafTransactionView getTransactionView()
	{
		return transactionView;
	}
	
	/**
	 * @return
	 */
	public MatafTaskButton getTaskButton()
	{
		return taskButton;
	}
	
	/**
	 * Used to deliver the focus to the last-focused component of
	 * the transaction view when switching tabs.
	 */
	public void setVisible(boolean isVisible)
	{
		super.setVisible(isVisible);
		
		if(isVisible)
		{
			Component c = transactionView.getFocusManager().getLastFocused();
			if(c!=null)
				c.requestFocusInWindow();
		}
	}
}
