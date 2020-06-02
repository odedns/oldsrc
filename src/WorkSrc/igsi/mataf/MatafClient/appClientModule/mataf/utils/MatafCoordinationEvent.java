package mataf.utils;

import com.ibm.dse.gui.DSECoordinationEvent;

/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class MatafCoordinationEvent extends DSECoordinationEvent {

	public static final String EVENT_SOURCETYPE_CLOSE_CHILD_VIEW="Close_Child_View";
	/**
	 * Constructor for MatafCoordinationEvent.
	 * @param arg0
	 */
	public MatafCoordinationEvent(Object arg0) {
		super(arg0);
	}

}
