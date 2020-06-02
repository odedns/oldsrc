/* Created on 08/01/2007 */ 
package test;

import tests.MyBpoIF;
import hoshen.configuration.entities.*;
import hoshen.configuration.entities.DeviceId;
import hoshen.configuration.entities.HsnConnection;
import hoshen.configuration.entities.HsnTP;
import hoshen.configuration.services.InventoryServices;
import hoshen.ejb.dynproxy.ServiceProxyFactory;

/**
 * 
 * @author odedn
 */
public class TranslationTest {
	private static InventoryServices inventoryServices;
	public static void testLoadTpByExtId() throws Exception
	{
		String extTpName = "LSN/EMS_XDM_182/1100;4:512;/sts3c_au4-j=16";
		String shelfId = "TS-XDM10-B01-01";
		String cardId = "I2";
		DeviceId device = new DeviceId();
		device.setSiteId("0190");
		device.setShelfId(shelfId);
		device.setCardId(cardId);
		
		HsnTP tp[] = inventoryServices.loadTPByExtrernalId(device,extTpName);
		System.out.println("got len = " + tp.length);
		 System.out.println("HsnTP = "  + tp[0].getTpId().toString());
	}
	
	public static void testGetSnC() throws Exception
	{
		System.out.println("translating connection");
		HsnConnection conn = inventoryServices.loadConnectionByExternalId("SNC1");
		System.out.println("got conn: " + conn.getNativeEmsName());
	}

	public static void testProxy() throws Exception
	{
		
		MyBpoIF mybpo = (MyBpoIF) ServiceProxyFactory.getService("mybpoif","iiop://localhost:2809");
		String s = mybpo.find(new Integer(100));
		System.out.println("mybpo.find= " + s);
      
	}
	
	
	public static void testLoadTpByTpName() throws Exception
	{
		  DeviceId deviceId = new DeviceId();

          deviceId.setSiteId("0190");
          deviceId.setShelfId("TS-XDM05-A01-01");
          deviceId.setCardId("IC1");
          TpName tpName = new TpName();
          tpName.setFullName("A01RX");
          TpId tpId = new TpId(deviceId, tpName);
          HsnTP tp = inventoryServices.loadTP(tpId);
         if(tp != null) {
         	System.out.println("HsnTP = "  + tp.getTpId().toString());
         } else {
         	System.out.println("tp is null");
         }
	
	}
	
	
	public static void main(String[] args)
	{
		System.out.println("Translationtest");
		try {
			inventoryServices = (InventoryServices)   ServiceProxyFactory.getService(InventoryServices.REMOTE_SERVICE_NAME,"iiop://localhost:2809");
			 //testLoadTpByExtId();
			testLoadTpByTpName();
			
		   
		} catch (Exception e) {
			e.printStackTrace();
	  	} 


	}
}
