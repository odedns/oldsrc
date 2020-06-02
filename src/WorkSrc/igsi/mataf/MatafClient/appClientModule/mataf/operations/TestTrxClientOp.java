package mataf.operations;

import java.util.HashMap;

import mataf.services.proxy.ProxyService;
import mataf.services.proxy.RequestException;

import com.ibm.dse.base.DSEClientOperation;
import com.ibm.dse.base.DSEObjectNotFoundException;
import com.ibm.dse.base.IndexedCollection;
import com.ibm.dse.base.KeyedCollection;
import com.ibm.dse.clientserver.CSClientService;
import mataf.dse.appl.OpenDesktop;
import mataf.general.operations.MatafClientOp;

/**
 * @author Eyal Ben Ze'ev
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class TestTrxClientOp extends MatafClientOp {
	
	/**
	 * @see com.ibm.dse.base.DSEClientOperation#execute()
	 */
	public void execute() throws Exception {
//		setValueAt("*.GL_TRANS_ID","O101");
//		setValueAt("*.GL_PEULA","5101");		
//		setValueAt("*.GL_BANK","31");
//		setValueAt("*.GL_SNIF","284");
//		setValueAt("*.GL_SCH","105");
//		setValueAt("*.GL_CH","100471");
//		setValueAt("*.GL_SHAA","550");
//		setValueAt("*.GL_SIDURI_ID","00");
//		setValueAt("*.GL_BANK_MARECHET","31");
//		setValueAt("*.GL_SNIF_MARECHET","284");
//		setValueAt("*.GL_BANK_PAKID","31");
//		setValueAt("*.GL_SNIF_PAKID","284");
//		setValueAt("*.GL_ZIHUI_PAKID","111571");
//		setValueAt("*.GL_SAMCHUT_PAKID","01");
//		setValueAt("*.GL_TR_ASAKIM","27102003");
//		setValueAt("*.GL_MISHMERET","0");

//		//int iCode=SynchronicSend(RTCommands.CHECK_ACCESS_COMMAND,"trxid","T410");
//		ProxyService proxy =(ProxyService)OpenDesktop.getContext().getService("proxyService");
//		
//		boolean bRetCode=proxy.checkAccess("T410");
//		
//
//		IndexedCollection iColl = (IndexedCollection)getElementAt("myTableModel");
//		iColl.removeAll();
//		for (int i=0; i<7; i++) {
//			KeyedCollection kColl = (KeyedCollection)KeyedCollection.readObject("tableColumns");
//			kColl.setValueAt("data1",String.valueOf(i));
//			kColl.setValueAt("data2",String.valueOf(i));
//			kColl.setValueAt("data3",String.valueOf(i));
//			kColl.setValueAt("data4",String.valueOf(i));
//			kColl.setValueAt("data5",String.valueOf(i));
//			kColl.setValueAt("data6",String.valueOf(i));
//			kColl.setValueAt("data7",String.valueOf(i));
//			iColl.addElement(kColl);
//		}
//		System.out.println(getCSRequestFormat().format(getContext()));
//		CSClientService csClientService = (CSClientService) getService("CSClient");
//		csClientService.sendAndWait(this, 40000000);
//		System.out.println(getKeyedCollection());
////		fireHandleOperationRepliedEvent(new  OperationRepliedEvent(this));
//	}
//
//	static int SynchronicSend(String commandType,String key,String value) throws DSEObjectNotFoundException, NumberFormatException, RequestException
//	{
//		ProxyService proxy =(ProxyService)OpenDesktop.getContext().getService("proxyService");
//		HashMap hmTemp=proxy.sendRequest( Integer.parseInt(commandType),
//							key,
//							value);
//		String sRetCode=(String)hmTemp.get("retCode");
//		
//		
//		return Integer.parseInt(sRetCode);
	System.out.println("Hello Client Operation !!!");
	setValueAt("AccountNumber","Link is working");
	}
}
