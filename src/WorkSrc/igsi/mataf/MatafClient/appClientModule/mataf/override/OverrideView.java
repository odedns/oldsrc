package mataf.override;

import mataf.desktop.views.MatafClientView;
import mataf.desktop.views.MatafTransactionView;

import com.ibm.dse.base.OperationRepliedEvent;
import com.ibm.dse.gui.DSECoordinationEvent;
import com.ibm.dse.gui.NavigationParameters;

/**
 * @author o000131
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class OverrideView extends MatafTransactionView {

	public OverrideView() {
		setViewName("OverrideView");
		//        setContextName("slikaCtx");
		initialize();
	}

	private OverridePanel opPanel = null;

	public OverridePanel getOverridePanel() {
		return opPanel;
	}

	private void initialize() {
		setTransactionPanel(opPanel = new OverridePanel());
	}

	/* (non-Javadoc)
	 * @see mataf.desktop.views.MatafDSEPanel#handleOperationRepliedEvent(com.ibm.dse.base.OperationRepliedEvent)
	 */
	public void handleOperationRepliedEvent(OperationRepliedEvent e) {
		NavigationParameters params = new NavigationParameters();
		DSECoordinationEvent event = params.generateCoordinationEvent(DSECoordinationEvent.EVENT_SOURCETYPE_VIEW_CLOSED, this);
		event.setEventType(DSECoordinationEvent.EVENT_EVENTYPE_NAVIGATION);
		event.setViewName(getViewName());
		event.setName(this.getClass().getName() + ".actionPerformed");
		if (e.getSource() instanceof OverrideResponceApproveOp) 
		{
			//Close the view
			fireCoordinationEvent(event);
		} else if (e.getSource() instanceof OverrideResponceRefuseOp) 
		{
			fireCoordinationEvent(event);
		} else if (e.getSource() instanceof OverrideResponceExitOp)
		{
			fireCoordinationEvent(event);
		}
		super.handleOperationRepliedEvent(e);
	}
	
	/* (non-Javadoc)
	 * @see mataf.desktop.views.MatafTransactionView#refreshDataExchangers()
	 */
	public void refreshDataExchangers()
	{
		// TODO Auto-generated method stub
		super.refreshDataExchangers();
	}


}
