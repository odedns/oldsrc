/*
 * Created on 01/11/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package test;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import hoshen.common.utils.ToStringBuilder;
import hoshen.xsm.lightsoft.LSConverter;
import hoshen.xsm.lightsoft.LightsoftManager;
import hoshen.xsm.lightsoft.corba.CosNotification.StructuredEvent;
import hoshen.xsm.lightsoft.corba.equipment.EquipmentHolder_T;
import hoshen.xsm.lightsoft.corba.equipment.Equipment_T;
import hoshen.xsm.lightsoft.corba.globaldefs.NameAndStringValue_T;
import hoshen.xsm.lightsoft.corba.managedElement.ManagedElement_T;
import hoshen.xsm.lightsoft.corba.subnetworkConnection.CrossConnect_T;
import hoshen.xsm.lightsoft.corba.subnetworkConnection.SubnetworkConnection_T;
import hoshen.xsm.lightsoft.corba.terminationPoint.TerminationPoint_T;
import hoshen.xsm.lightsoft.corba.topologicalLink.TopologicalLink_T;

/**
 * @author odedn
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TestLS {

	public static void main(String[] args) {
		
		// String name = "LSN/EMS_XDM_90/1111";
		// String name = "LSN/EMS_XDM_182/1200";
		String name = "LSN/EMS_XDM_182/1100";
		String sncName = "1:1288";
//		name=EMS;value=ECI/LightSoft_1;name=ManagedElement;value=LSN/EMS_XDM_182/1200;name=PTP;value=34:10162

		//String ptpName = "34:10080";
		String ptpName = "34:10170";
		// LSN/EMS_XDM_182/1200;34:10080;/vt2_tu12=1
		// accessId = LSN/EMS_XDM_182/1100;4:512
		
		//String name = "LSN/EMS_SYNCOM_5/7";
		LightsoftManager manager = null;
		System.out.println("TestLS ..");
		try {
			manager = LightsoftManager.getInstance("hoshen3","hoshen3");
			//List l = manager.getAllManagedElements();
			manager.ping();
			NameAndStringValue_T subnet[] = manager.getSubnetName();
			System.out.println("subnet = " +LSUtils.nsvToString(subnet));
			//List l = manager.getAllSubnetworks();
			//List l = manager.getAllTopologicalLinks();
			//List l = manager.getAllSNC((short)15);
			//List l = manager.getAllEquipment(name);
			//List l = manager.getAllActiveMaintenanceOp("ManagedElement",name);
			//List l = manager.getAllPTPs(name);
			//List l = manager.getAllCTPs(name,"PTP", ptpName);
			//List l = manager.getAllTps(name);
			//List l = manager.getAllEMSandMEActiveAlarms();
			//List l = manager.getAllActiveAlarms(name);
			//manager.performMaintenanceOp("ManagedElement",nameR,"FACILITY_LOOPBACK",(short) 0,0);
			
			//manager.performMaintenanceOpTp(LightsoftManager.PTP,name,ptpName,"FACILITY_LOOPBACK",(short) 0,LightsoftManager.MOM_OPERATE);
			//manager.performMaintenanceOpTp(LightsoftManager.PTP,name,ptpName,"FACILITY_LOOPBACK",(short) 0,LightsoftManager.MOM_RELEASE);
			//List l = manager.getAllActiveMaintenanceOp("ManagedElement",name);
			/*
			ManagedElement_T me = manager.getManagedElement(name);
			List l = new LinkedList();
			l.add(me);
			*/
			//List l = manager.getRoute(sncName,true);
			/*
			manager.openPullConsumer();
			List l = manager.tryPullEvents();
			if(l != null) {
				LSDebug.printList(l);
			}
			*/
			//PrintWriter pw = new PrintWriter(new FileOutputStream("c:/tmp/tp.txt"));
			//LSDebug.printList(pw,l);
			//pw.close();
			
			
			//ManagedElement_T element = manager.getManagesdElement(name);
			// manager.getAllEquipment(name);
		//	manager.getAllEquipment(name);
		//	manager.getAllEMSandMEActiveAlarms();
		//	manager.getAllTopologicalLinks("3350");
		} catch(Exception e){
			
			e.printStackTrace();
		}
		manager.close();
		System.out.println("TestLS done ...");
		
	}
	

	
	
}
