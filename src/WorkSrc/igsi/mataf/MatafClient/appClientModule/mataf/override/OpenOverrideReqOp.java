package mataf.override;
import java.awt.BorderLayout;

import mataf.logger.GLogger;
import mataf.operations.general.BasicClientOp;
import mataf.services.reftables.RefTablesService;
import mataf.types.MatafEmbeddedPanel;
import mataf.utils.IndexedColUtils;
import mataf.utils.MQUtils;

import com.ibm.dse.base.Context;
import com.ibm.dse.base.DSEInvalidRequestException;
import com.ibm.dse.base.IndexedCollection;

import mataf.dse.appl.OpenDesktop;

/**
 * @author Oded Nissan
 * This operation opens an Override request from the MQ message.
 * It then "freezes" the current transaction executing on the manager's 
 * workstation and replaces the client context, message list and transaction
 * context with that of the override request. It opens the transaction's
 * custom override screen and displays it.
 *
 */
public class OpenOverrideReqOp extends BasicClientOp {
		
	/**
	 * execute the operation.
	 * @throws Exception in case of error.
	 */
	public void execute()
		throws Exception
		
	{
		try
		{
		GLogger.debug("OpenOverrideReqOp");
		Context ctxOper=getContext();
		String sView=(String)ctxOper.getValueAt("viewName");		
		Class c =Class.forName(sView);
		MatafEmbeddedPanel mdpView = (MatafEmbeddedPanel)c.newInstance();				
		OverrideView ovCurrent=(OverrideView)OpenDesktop.getActiveTransactionView();								
		String sCtxName=(String)ctxOper.getValueAt("ctxName");
		
		try
		{
			Context ctxWorksation=getContextNamed("workstationCtx");
			RefTablesService refTables = (RefTablesService)ctxWorksation.getService("refTablesService");
			IndexedCollection ansIc = refTables.getAll("GLST_TESHUVA");
			ansIc.setName("managerAnswerList"); 
			GLogger.debug("got answers: " + ansIc.toString());
			IndexedCollection aic = (IndexedCollection) getElementAt("managerAnswerList");
			aic.removeAll();
			IndexedColUtils.copy(ansIc,aic);
			//addElement(ansIc);
		}
		catch(Exception e)
		{
			GLogger.error(this.getClass(), null, null, e,false);
			e.printStackTrace();
		}
		
		Context cTrxCtx = MQUtils.unpackTrxContext(ctxOper);
		ctxOper.removeAt("ctxData");
		ctxOper.chainTo(cTrxCtx);
		

		//A try catch block for contexts that already chained to pakidClientCtx
		
		try
		{
			cTrxCtx.chainTo(getContextNamed("clientCtx"));
		}
		catch(DSEInvalidRequestException dseE)
		{
			//we Ignore if there is no context there
		}
		
		//ovCurrent.setContext(cTrxCtx);
		ovCurrent.setContext(ctxOper);					
		ovCurrent.getOverridePanel().add(mdpView,BorderLayout.CENTER);
		
		
		
		
		ovCurrent.refreshDataExchangers();
		
		//mdpView.chainTo(ctxOper);
		
		/*DSECoordinationEvent event = new DSECoordinationEvent(DSECoordinationEvent.EVENT_EVENTYPE_ACTION);
		event.setChainContext(DSECoordinationEvent.CHAIN_PARENT_CONTEXT);
		event.setEventType(DSECoordinationEvent.EVENT_EVENTYPE_NAVIGATION);
		event.setEventSourceType(DSECoordinationEvent.EVENT_SOURCETYPE_OPEN_VIEW);		
		//event.setViewName("SampleOverrideView");
		//event.setName("SampleOverrideView");
		//event.setViewSource("mataf.override.SampleOverrideView");
		
		event.setViewName(ovCurrent.getViewName());
		event.setName(ovCurrent.getViewName());
		event.setViewSource(ovCurrent);
		
		MatafDSEPanel panel = OpenDesktop.getActiveMatafPanel();
		try
		{
			panel.fireCoordinationEvent(event);
			((DSETaskButton)Desktop.getDesktop().getTaskArea().getCurrentTask()).getNavigationController().setActiveView("SampleOverrideView");
			
		}
		catch(Exception e)
		{
			e.printStackTrace();	
		}*/
		sendReceive(4000,ctxOper);
		}
		catch(Exception e)
		{
			GLogger.error(this.getClass(), null, null, e,false);
			e.printStackTrace();
		}
	}

}
