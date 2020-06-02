/*
 * Created on 02/05/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package mataf.desktop.beans;

import com.ibm.dse.desktop.IconState;

/**
 * @author Tibi Glazer
 *
 * Icon state class for Mataf project - adds tooltip text
 */
public class MatafIconState extends IconState {
	private String text;

	public MatafIconState()
	{
		super();
		text = null;
	}

	public String getToolTipText()
	{
		return text;
	}

	public void setToolTipText(Object o)
	{
		text = (String)o;
	}
}
