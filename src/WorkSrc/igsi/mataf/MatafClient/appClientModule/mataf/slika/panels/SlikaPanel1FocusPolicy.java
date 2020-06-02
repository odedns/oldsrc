package mataf.slika.panels;

import java.util.Vector;

import mataf.focusmanager.MatafFocusTraversalPolicy;

/**
 * This class defines the focus policy for the SlikaPanel1.
 * 
 * @author Nati Dykstein. Creation Date : (14/10/2003 17:50:19).
 */
public class SlikaPanel1FocusPolicy extends MatafFocusTraversalPolicy
{
	private SlikaPanel1 panel;
	
	/**
	 * Constructor for SlikaPanel1FocusPolicy.
	 */
	public SlikaPanel1FocusPolicy(SlikaPanel1 panel) 
	{
		this.panel = panel;
		
		Vector v = new Vector();
		v.add(panel.getMatafTextField6());
		v.add(panel.getMatafTextField7());
		v.add(panel.getMatafTextField3());
		v.add(panel.getMatafTextField5());
		v.add(panel.getMatafTextField4());
		v.add(panel.getJTextField());
		v.add(panel.getJTextField1());
		
		setFocusOrderList(v);
	}	
}
