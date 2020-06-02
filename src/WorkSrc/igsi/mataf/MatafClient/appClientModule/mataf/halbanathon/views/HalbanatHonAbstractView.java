package mataf.halbanathon.views;

import mataf.data.VisualDataField;
import mataf.desktop.views.MatafDSEPanel;

import com.ibm.dse.base.DSEInvalidRequestException;
import com.ibm.dse.base.DSEObjectNotFoundException;
import com.ibm.dse.desktop.DSENavigationController;
import com.ibm.dse.desktop.DSETaskButton;
import com.ibm.dse.desktop.Desktop;
import com.ibm.dse.gui.DSECoordinationEvent;

/**
 * @author Eyal Ben Ze'ev
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public abstract class HalbanatHonAbstractView extends MatafDSEPanel {
	/**
	 * @see com.ibm.dse.gui.DSEPanel#handleDSECoordinationEvent(DSECoordinationEvent)
	 */
	public void handleDSECoordinationEvent(DSECoordinationEvent event) {
		DSENavigationController navigationController =(DSENavigationController)
				((DSETaskButton)Desktop.getDesktop().getTaskArea().getCurrentTask()).getNavigationController();
			
		if (event.getEventSourceType().equals(DSECoordinationEvent.EVENT_SOURCETYPE_CLOSE)) 
		{
			navigationController.closeView(getViewName());
			try {
				getContext().unchain();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return;
		} 
		else if (event.getEventSourceType().equals(DSECoordinationEvent.EVENT_SOURCETYPE_CLOSE_NAVIGATION)) {
			//String parentViewName = navigationController.getParent(getViewName());
			navigationController.closeView(getViewName());
			try {
				getContext().unchain();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		super.handleDSECoordinationEvent(event);
	}
	
	/**
	 * @see mataf.desktop.views.MatafDSEPanel#afterChildClose(Object)
	 */
	public void afterChildClose(Object param) {
		try {
			if (((String) getValueAt("trxOrData.overrideResult")).equals(Boolean.TRUE.toString())) {
				((VisualDataField) getContext().getElementAt("HASS_LAKOACH_SUG.TransmitButton")).setIsEnabled(Boolean.TRUE);
			} else {
			
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
