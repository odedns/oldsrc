package mataf.desktop.beans;

import java.awt.Component;
import java.awt.FocusTraversalPolicy;

import mataf.desktop.views.MatafTransactionView;
import mataf.logger.GLogger;
import mataf.types.MatafEmbeddedPanel;

import com.ibm.dse.gui.SpFocusTracker;

/**
 * This class inherits from SpFocusTracker so it could
 * elegantly replace it as the Focus Tracker of the DSEPanel.
 * It uses the FocusTraversalPolicy of the business panel to
 * decide which component recives the focus upon opening.
 * 
 * @author Nati Dykstein. Creation Date : (22/10/2003 13:20:01).  
 */
public class MatafFocusTracker extends SpFocusTracker {

	/**
	 * Constructor for MatafFocusTracker.
	 * @param root
	 */	
	public MatafFocusTracker(Component root) 
	{
		super(root);
	}

	/**
	 * Uses the the panel's FocusTraversalPolicy in order put the focus
	 * on the first component.
	 */
	public Component getLastFocused() 
	{
		// Panel is shown for the first time.
		if(super.getLastFocused()==null)
		{
			MatafTransactionView matafTransactionView = 
										MatafWorkingArea.getActiveTransactionView();		
			MatafEmbeddedPanel embeddedPanel = 
										matafTransactionView.getTransactionPanel();			
			FocusTraversalPolicy ftp = embeddedPanel.getFocusTraversalPolicy();
			if(ftp==null)
			{
				GLogger.warn("Panel is not a focus cycle root ancestor : "+embeddedPanel);
				return super.getLastFocused();
		    }
			Component comp = ftp.getDefaultComponent(embeddedPanel);
			return comp;
		}
		return super.getLastFocused();
	}
}
