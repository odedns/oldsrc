package mataf.desktop.beans;

import java.awt.LayoutManager;

import com.ibm.dse.desktop.CustomizableArea;

/**
 * @author Eyal Ben Ze'ev
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class MatafCustomizableArea extends CustomizableArea {

	/**
	 * Constructor for MatafCustomizableArea.
	 */
	public MatafCustomizableArea() {
		super();
	}

	/**
	 * Constructor for MatafCustomizableArea.
	 * @param arg0
	 */
	public MatafCustomizableArea(LayoutManager arg0) {
		super(arg0);
	}

	/**
	 * Constructor for MatafCustomizableArea.
	 * @param arg0
	 * @param arg1
	 */
	public MatafCustomizableArea(LayoutManager arg0, boolean arg1) {
		super(arg0, arg1);
	}

	/**
	 * Constructor for MatafCustomizableArea.
	 * @param arg0
	 */
	public MatafCustomizableArea(boolean arg0) {
		super(arg0);
	}
	
	public void setBackgroundColor(Object obj)	{
		setBackground(DesktopUtils.colorTokenizer(obj)) ;
	}

}
