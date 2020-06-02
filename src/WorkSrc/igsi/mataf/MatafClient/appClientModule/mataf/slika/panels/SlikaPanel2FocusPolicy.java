package mataf.slika.panels;

import java.util.Vector;

import mataf.focusmanager.MatafFocusTraversalPolicy;

/**
 * This class defines the focus policy for the SlikaPanel1.
 * 
 * @author Nati Dykstein. Creation Date : (14/10/2003 17:50:19).  
 */
public class SlikaPanel2FocusPolicy extends MatafFocusTraversalPolicy
{
	private SlikaPanel2 panel;
	
	/**
	 * Constructor for SlikaPanel1FocusPolicy.
	 */
	public SlikaPanel2FocusPolicy(SlikaPanel2 panel) 
	{
		this.panel = panel;		
		setDefaultComponent(panel.getMatafTable());
	}
}