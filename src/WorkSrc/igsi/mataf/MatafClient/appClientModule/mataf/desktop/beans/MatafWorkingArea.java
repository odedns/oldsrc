package mataf.desktop.beans;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.MenuSelectionManager;

import mataf.desktop.views.MatafClientView;
import mataf.desktop.views.MatafTransactionView;
import mataf.dse.appl.OpenDesktop;
import mataf.types.MatafEmbeddedPanel;
import mataf.types.MatafTabbedPane;

import com.ibm.dse.desktop.Desktop;
import com.ibm.dse.desktop.SpInternalFrame;
import com.ibm.dse.desktop.WorkingArea;

/**
 * Handles the client views.
 *
 * 
 * @author Nati Dykstein. Creation Date : (02/05/2004 16:37:47).
 */
public class MatafWorkingArea extends WorkingArea 
{
	/** FUTURE : would be a vector of client views. */
	private MatafClientView matafClientView;
	
	private MatafTabbedPane clientsTabbedPane;
	
	private static MatafWorkingArea refForStatic;
	
	/**
	 * Constructor for MatafWorkingArea
	 */
	public MatafWorkingArea() 
	{
		super();
		
		refForStatic = this;
		
		clientsTabbedPane = new MatafTabbedPane();
		clientsTabbedPane.setSize(1015,665);
		
		matafClientView = new MatafClientView();
		
		clientsTabbedPane.addTab("לקוח א'",matafClientView);
		
		add(clientsTabbedPane);
		
		attachKeyListeners();
	}
	
	public static MatafWorkingArea getWorkingArea()
	{
		return refForStatic;
	}

	/**
	 * Returns the active client view.
	 * @return
	 */
	public static MatafClientView getActiveClientView()
	{
		return refForStatic.matafClientView;
	}
	
	/**
	 * Returns the active transaction view of the active client view.
	 * @return
	 */
	public static MatafTransactionView getActiveTransactionView()
	{
		return getActiveClientView().getActiveTransactionView();
	}
	
	
	private void attachKeyListeners()
	{
		addKeyListener(new KeyAdapter()
		{
			/**
			 * Used when closing all the transactions and the focus is on
			 * the working area, we need to handel the trigger that opens the
			 * menu here.(instead of letting the rootpane do it as usual)
			 */
			public void keyPressed(KeyEvent e)
			{
				// When the menu is closed, Enter opens the first menu.
				if(e.getKeyCode()==KeyEvent.VK_ENTER)
				{
					// Check that the menu is closed.
					if(MenuSelectionManager.defaultManager().getSelectedPath().length==0)
					{
						JMenuItem menuItem = MatafMenuBar.getMenuItemByTaskName("1000");
						menuItem.doClick();
					}
				}
			}
		});
	}
		
	
	/**
	 * Overided to always return the same internal frame.
	 */	
/*	protected SpInternalFrame getNewInternalFrame() 
	{
		if(mainInternalFrame==null)
			mainInternalFrame = new MatafInternalFrame();
		mainInternalFrame.setBorder(null);
		return mainInternalFrame;
		
		MatafInternalFrame mif = new MatafInternalFrame();
		return mif;
	}*/

	/**
	 * Overided to always return the same internal frame.
	 */
/*	public SpInternalFrame getFrameAtLevel(int i,int widthNA,int heightNA,int x,int y) 
	{
		if(mainInternalFrame==null)
		{
			mainInternalFrame = new MatafInternalFrame();
			mainInternalFrame.setBorder(null);
		}
		
		return mainInternalFrame;
	
		//MatafInternalFrame mif = (MatafInternalFrame)super.getFrameAtLevel(i,widthNA, heightNA, x, y) ;
		//mif.setBorder(null);
		//return mif ;
	}
*/
	/**
	 * Sets the error line message on MatafPanel.
	 */
	public void setErrorLine(String errorMsg)
	{
		matafClientView.getTheErrorLabel().setText(errorMsg);
	}
	
	/**
	 * Sets the error line message with the specified color
	 * on getMatafPanel().
	 */
	public void setErrorLine(String errorMsg,Color errorMsgColor)
	{
		matafClientView.getTheErrorLabel().queueErrorMessage(errorMsg, errorMsgColor);
	}
	
	/**
	 * Adds a message to the message area on MatafPanel.
	 */
	public void addMessage(String message)
	{
		getActiveClientView().addMessage(message);
	}
	
	/**
	 * Adds a message witht the specified color to the 
	 * message area on MatafPanel.
	 */
	public void addMessage(String message,Color messageColor)
	{
		getActiveClientView().addMessage(message, messageColor);
	}
	
	/* (non-Javadoc)
	 * @see com.ibm.dse.desktop.WorkingArea#toString()
	 */
	public String toString()
	{
		return getClass().getName();
	}

}