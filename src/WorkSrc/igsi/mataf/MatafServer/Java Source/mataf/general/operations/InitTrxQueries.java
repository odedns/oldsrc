/*
 * Created on 17/05/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package mataf.general.operations;

import com.ibm.dse.base.DataElement;
import com.ibm.dse.base.IndexedCollection;
import com.ibm.dse.base.KeyedCollection;

/**
 * @author ronenk
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class InitTrxQueries extends MatafOperationStep
{

	
	/* (non-Javadoc)
	 * @see mataf.general.operations.MatafOperationStep#executeOp()
	 */
	public int executeOp() throws Exception
	{
		//TODO - read from XML the model of the transaction
		//TODO - read from XML the queries appended to the model & add it to the queriesList
		//TODO - read from database the queries that the clerk added to the queriesList per model 
		
		IndexedCollection queriesList = (IndexedCollection) getElementAt("queriesList");
		
		KeyedCollection kcoll1 = (KeyedCollection) DataElement.readObject("queryData");
		kcoll1.setValueAt("queryName", "bla bla");
		kcoll1.setValueAt("queryId", "1101");
		queriesList.addElement(kcoll1);

		KeyedCollection kcoll2 = (KeyedCollection) DataElement.readObject("queryData");
		kcoll2.setValueAt("queryName", "be be");
		kcoll2.setValueAt("queryId", "1102");
		queriesList.addElement(kcoll2);

		KeyedCollection kcoll3 = (KeyedCollection) DataElement.readObject("queryData");
		kcoll3.setValueAt("queryName", "si si");
		kcoll3.setValueAt("queryId", "1103");
		queriesList.addElement(kcoll3);
		 
		return RC_OK;
	}

}
