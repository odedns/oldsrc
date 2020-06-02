package mataf.desktop.beans;

import java.util.Hashtable;

import javax.swing.Icon;

import com.ibm.dse.desktop.DesktopComponent;
import com.ibm.dse.desktop.IconState;
import com.ibm.dse.desktop.MultipleStateIconLabel;

/**
 * MatafMultipleStateIconLabel class
 *  	
 * @author 	Tibi Glazer
 * @version 	1.0
 * @since		2-5-2004
 */
public class MatafMultipleStateIconLabel extends MultipleStateIconLabel implements DesktopComponent {

	protected Hashtable texts;

	public MatafMultipleStateIconLabel()
	{
		texts = new Hashtable();
	}

	public MatafMultipleStateIconLabel(String text)
	{
		super(text);
		texts = new Hashtable();
	}

	public MatafMultipleStateIconLabel(Icon icon)
	{
		super(icon);
		texts = new Hashtable();
	}


	/* (non-Javadoc)
	 * @see com.ibm.dse.desktop.DesktopComponent#add(java.lang.Object)
	 */
	public void add(Object o) {
		if (o instanceof MatafIconState) {
			super.add(o);			
			MatafIconState mis = (MatafIconState)o;
			texts.put(mis.getName(), mis.getToolTipText());
		}
		else if (o instanceof IconState) {
			super.add(o);			
			IconState is = (IconState)o;
			texts.put(is.getName(), null);
		}
	}
	
	public void setState(String s)
	{
		super.setState(s);
		super.setToolTipText((String)texts.get(s));
	}
}
