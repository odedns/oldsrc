package mataf.override;

import mataf.desktop.views.MatafClientView;
import mataf.desktop.views.MatafTransactionView;
import mataf.logger.GLogger;
import mataf.utils.MatafCoordinationEvent;

import com.ibm.dse.gui.DSECoordinationEvent;

/**
 * @author o000131
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class TestView extends MatafTransactionView {
	
	
	public TestView()
	{
		setViewName("TestView");
		setContextName("testPanelCtx");
//		setInstanceContext(true);		
		initialize();
	}
	
	private void initialize()
	{				
		setTransactionPanel(new TestPanel());	
	}	
	public static void main(String[] args) {
	}
	
	public void handleDSECoordinationEvent(DSECoordinationEvent event) {
		super.handleDSECoordinationEvent(event);
		if(event instanceof MatafCoordinationEvent) {
			MatafCoordinationEvent mevent = (MatafCoordinationEvent) event;
			if(mevent.getEventSourceType().equals(mevent.EVENT_SOURCETYPE_CLOSE_CHILD_VIEW)) {
				GLogger.debug("TestView : finished LocalOverride..");						
			
			}
		}
		
	}

}
