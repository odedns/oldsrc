package mataf.override;

import mataf.desktop.beans.MatafWorkingArea;
import mataf.logger.GLogger;
import mataf.operations.general.BasicClientOp;
import mataf.services.proxy.ProxyService;
import mataf.services.reftables.RefTablesService;
import mataf.utils.ContextUtils;
import mataf.utils.IndexedColUtils;

import com.ibm.dse.base.Context;
import com.ibm.dse.base.DataElement;
import com.ibm.dse.base.IndexedCollection;

/**
 * @author Oded Nissan
 *
 * This operation retrieves the list of available managers from the RT
 * using the proxy. It then updated the context with the data so that
 * the override screen can display the combo of available managers.
 */
public class ManagersListOp extends BasicClientOp {
	
	
	/**
	 * execute the operation.
	 * Get the list of available managers from the proxy.
	 * Put them in the context for the screen to display.
	 * @throws Exception in case of error.
	 */
	public void execute()
		throws Exception
		
	{
		try {
			GLogger.debug("ManagersListOp.execute()");		
			//GLogger.debug("parent = " + getParent().getName());
			Context ctx2 = getContext();
			Context ctx = getContextNamed("workstationCtx");
			ProxyService proxy = (ProxyService) ctx.getService("proxyService");
//			String samchut = (String) ctx.getValueAt("GLSE_GLBL.GL_SAMCHUT");
			Context trxCtx = MatafWorkingArea.getActiveTransactionView().getContext();
			String samchut = (String) trxCtx.getValueAt("trxORData.samchutMeasheret");
			String snif60 = (String) ctx.getValueAt("GLSE_GLBL.GL_SAMCHUT_SNIF_60");
			IndexedCollection ic = proxy.getManagersList(snif60,samchut);
			GLogger.debug("managerList.size = " + ic.size());
			if(ic.size() > 0) {
				/*
				 * put the user's manager first
				 * in the list of managers.
				 */
				String managerId = (String) ctx.getValueAt("GLSE_GLBL.GL_ZIHUI_MENAHEL");
				IndexedColUtils.putFirst(ic,"managerId",managerId);
				ic.setName("managersList");
				IndexedCollection mic = (IndexedCollection) getElementAt("managersList");
				mic.removeAll();
				IndexedColUtils.copy(ic,mic);
				addElement(mic);
			}
		
			/*
			 * now copy managerName into the combo box
			 * indexedCollection.
			 */
			String v[] = IndexedColUtils.toStringArray(ic,"managerName");
			IndexedCollection ic2 = (IndexedCollection) getElementAt("managersComboList");
			for(int i=0; i < v.length; ++i) {
				DataElement fld = (DataElement) DataElement.readObject("managerName");
				fld.setValue(v[i]);
				ic2.addElement(fld);
			}
			addElement(ic2);
		
			/*
			 * get the answer code from the table.
			 */
			 RefTablesService refTables = (RefTablesService) ctx.getService("refTablesService");
			 IndexedCollection ansIc = refTables.getAll("GLST_BAKASHA");
			 ansIc.setName("answerList"); 
			 GLogger.debug("got answers: " + ansIc.toString());
			 IndexedCollection aic = (IndexedCollection) getElementAt("answerList");
			 aic.removeAll();
			 IndexedColUtils.copy(ansIc,aic);
	 		 addElement(ansIc);		
 			 setValueAt("status", new Integer(0));		 
 			 GLogger.debug("managersListOp done ...ic2 = " + ic2.toString());		
		} catch(Exception e) {
			// e.printStackTrace();
			GLogger.error(this.getClass(), null ,"Exception in ManagersListOp " , e,false);								
			setError( " שגיאה בשליפת רשימת מנהלים: " + e.getMessage());			
			
		}
	}
	
	
	

}
