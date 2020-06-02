package mataf.halbanathon.panels;

import java.awt.Component;
import java.awt.Container;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import javax.swing.LayoutFocusTraversalPolicy;

import mataf.focusmanager.MatafFocusTraversalPolicy;

/**
 * This class defines the focus policy for the SlikaPanel1.
 * 
 * @author Nati Dykstein. Creation Date : (14/10/2003 17:50:19).  
 */
public class MevatseaHapeulaFocusPolicy extends MatafFocusTraversalPolicy
{
	private MevatseaHapeulaComplete panel;
	
	/**
	 * Constructor for MevatseaHapeulaFocusPolicy.
	 */
	public MevatseaHapeulaFocusPolicy(MevatseaHapeulaComplete panel) 
	{
		this.panel = panel;
		
		List l = new Vector();
		
		l.add(panel.getMatafTextField());
		l.add(panel.getMatafTextField4());
		l.add(panel.getMatafTextField5());
		l.add(panel.getMatafTextField2());
		l.add(panel.getMatafTextField3());
		l.add(panel.getMatafTextField6());
		l.add(panel.getMatafTextField7());
		l.add(panel.getMatafTextField8());
		l.add(panel.getMatafTextField9());
		l.add(panel.getMatafTextField13());
		l.add(panel.getMatafTextField10());
		l.add(panel.getMatafTextField15());
		l.add(panel.getMatafTextField14());
		l.add(panel.getMatafTextField11());
		l.add(panel.getMatafTextField12());
		
		setFocusOrderList(l);
	}
}
