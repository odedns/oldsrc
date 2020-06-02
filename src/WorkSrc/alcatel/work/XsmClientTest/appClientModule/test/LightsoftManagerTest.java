/*
 * Created on 02/11/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package test;

import java.util.Iterator;
import java.util.List;

import hoshen.common.utils.ToStringBuilder;
import hoshen.xsm.lightsoft.LightsoftManager;
import hoshen.xsm.lightsoft.corba.equipment.EquipmentHolder_T;
import hoshen.xsm.lightsoft.corba.equipment.Equipment_T;
import hoshen.xsm.lightsoft.corba.globaldefs.NameAndStringValue_T;
import hoshen.xsm.lightsoft.corba.managedElement.ManagedElement_T;
import hoshen.xsm.lightsoft.corba.terminationPoint.TerminationPoint_T;
import junit.framework.TestCase;

/**
 * @author odedn
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class LightsoftManagerTest extends TestCase {

	LightsoftManager manager = null;
	String subnetName = "ECI/LightSoft_1-LSN:NMS/0001";
	
	public static void main(String[] args) {
		junit.textui.TestRunner.run(LightsoftManagerTest.class);
	}

	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		manager = LightsoftManager.getInstance();
	}

	/*
	 * @see TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
		manager.close();
	}

	
	public void testGetAllManagedElements() throws Exception {
		List l = manager.getAllManagedElements();
		assertTrue(l != null);
		//printList(l);
	}
	

	public void testPing() throws Exception
	{
		manager.ping();
	}
	
	
	public void testGetAlltopologicalLinks() throws Exception {
		List l = manager.getAllTopologicalLinks();
		assertTrue(l != null);
		//printList(l);
	}
	
	
	
	public void testGetAllSubnetworks() throws Exception {
		List l = manager.getAllSubnetworks();
		assertTrue(l != null);
		//printList(l);
	}
	
	
	public void testGetAllEquipment() throws Exception
	{
		
		String name = "LSN/EMS_XDM_182/1200";
		List l = manager.getAllEquipment(name);
		assertTrue(l != null);
	}
	
	

	public void testGetAllSNC() throws Exception {
		List l = manager.getAllSNC((short)15);
		assertTrue(l != null);
	}

	public void testGetAllPTPs() throws Exception
	{
		
		String name = "LSN/EMS_XDM_182/1200";
		List l = manager.getAllPTPs(name);
		assertTrue(l != null);
	}
	
	
	private static void printList(List l)
	{
		Iterator iter = l.iterator();
		while(iter.hasNext()) {
			Object o = iter.next();
			if(o instanceof EquipmentHolder_T) {
				//LSDebug.printEquipmentHolder((EquipmentHolder_T)o);
			} else {
				if(o instanceof Equipment_T) {
					LSDebug.printEquipment((Equipment_T) o);
				} else {
					if(o instanceof TerminationPoint_T) {
						LSDebug.printPTP((TerminationPoint_T)o);
					} else {
						if(o instanceof ManagedElement_T) {
							LSDebug.printManagedElement((ManagedElement_T) o);						
						} else {
						System.out.println(ToStringBuilder.toString(o));
						} // if
					} // if
				} // if
			} //if
		} // while
	}
	
		
	
}
