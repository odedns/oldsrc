package mataf.desktop.views;

import java.awt.BorderLayout;

import mataf.types.MatafEmbeddedPanel;
import mataf.types.MatafErrorLabel;

/**
 *
 * 
 * @author Nati Dykstein. Creation Date : (22/04/2004 12:35:04).  
 */
public class MatafTransactionView extends MatafDSEPanel
{
	/** The parent client view which holds this transaction view.*/
	MatafClientView parentClientView;

	private MatafEmbeddedPanel transactionPanel;
	
	/**
	 * Creates a new transaction view. 
	 */
	public MatafTransactionView()
	{
		super();
		initialize();
	}
	
	/**
	 * Initialize the transaction view.
	 */
	private void initialize()
	{
		setLayout(new BorderLayout());
		
		// Remove the view's border.
		setBorder(null);
		
		// Create and set the error label for this transaction view.
		setErrorBean(new MatafErrorLabel());
	}
	
	/**
	 * Creates a new transaction view with its transaction panel.
	 * @param transactionPanel
	 */
	public MatafTransactionView(MatafEmbeddedPanel transactionPanel)
	{
		super();
		initialize();
		prepareAndAddTransactionPanel(transactionPanel);
	}
	
	/**
	 * Removes previous transaction panel and adds the new one.
	 * @param transactionPanel
	 */
	public void setTransactionPanel(MatafEmbeddedPanel transactionPanel)
	{
		// Remove old panel (if exists).
		if(this.transactionPanel!=null)
			remove(transactionPanel);
		
		prepareAndAddTransactionPanel(transactionPanel);
	}
	
	
	/**
	 * Prepares the transaction panel, and add it to the transaction view.
	 * @param transactionPanel
	 */
	private void prepareAndAddTransactionPanel(MatafEmbeddedPanel transactionPanel)
	{
		this.transactionPanel = transactionPanel;
		
		// Remove the panel's border.
		transactionPanel.setBorder(null);
		
		// Set the panel as non-transparent.
		transactionPanel.setOpaque(true);
		
		// Add the panel to the center of the view.
		add(transactionPanel, BorderLayout.CENTER);
		
		// Add the button groups to the data exchangers.
		registerPanelButtonGroups(transactionPanel);
	}
	
	/**
	 * @return MatafEmbeddedPanel - The transaction panel.
	 */
	public MatafEmbeddedPanel getTransactionPanel()
	{
		return transactionPanel;
	}
	
	public MatafErrorLabel getTheErrorLabel()
	{
		return (MatafErrorLabel)getErrorBean();//(MatafErrorLabel)transactionPanel.getErrorBean();
	}

	/**
	 * @return MatafClientView - the client view in which this transaction view resides.
	 */
	public MatafClientView getParentClientView()
	{
		return parentClientView;
	}

	/**
	 * @param view - The client view in which the transaction view will reside.
	 */
	public void setParentClientView(MatafClientView view)
	{
		parentClientView = view;
	}
	
	/** 
	 * Make the transaction view also refresh its parent client view.
	 */
	public void refreshDataExchangers()
	{
		super.refreshDataExchangers();
		
		if(parentClientView!=null)
			parentClientView.refreshDataExchangers();
	}

	
//	public static void main(String[] args)
//	{
//		JFrame f = new JFrame("Testing Transaction View");
//		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		MatafTransactionView mtv = new MatafTransactionView();
//		SlikaPanel1 sp1 = new SlikaPanel1();
//		System.out.println("sp1 preferred size = "+sp1.getPreferredSize());		
//		mtv.add(sp1, BorderLayout.CENTER);
//		mtv.setPreferredSize(sp1.getPreferredSize());
//		f.getContentPane().add(mtv);
//		f.pack();
//		f.setVisible(true);
//	}


}
