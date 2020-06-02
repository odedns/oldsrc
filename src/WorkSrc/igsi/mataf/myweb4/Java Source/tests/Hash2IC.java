package tests;

import com.ibm.dse.base.*;


import java.util.HashMap;
import java.util.Set;
import java.util.Map;
import java.util.Iterator;
/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class Hash2IC {

	static void init() throws Exception
	{
		Context.reset();
		Settings.reset("http://localhost/myweb4/dse/server/dse.ini");
		Settings.initializeExternalizers(com.ibm.dse.base.Settings.MEMORY);	
		
	}

	static void addElementAt(IndexedCollection ic, DataElement data, int index)
		throws Exception
	{
		Vector v = (Vector) ic.getElements();
		DataElement de = (DataElement )v.remove(0);
		v.add(0,data);
		v.add(de);	
		ic.setElements(v);				
	}

	static IndexedCollection getManagersList(HashMap hp)	
		throws Exception
	{
		IndexedCollection ic = new IndexedCollection();
		Set entries = hp.entrySet();		
		Iterator iter = entries.iterator();
		Map.Entry entry = null;
		String key = null;		
		while(iter.hasNext()) {
			entry = (Map.Entry) iter.next();
		
			KeyedCollection kc = new KeyedCollection();
			kc.setDynamic(true);
			key = (String) entry.getKey();
			kc.trySetValueAt("userid",key);
			kc.trySetValueAt("name",(String) entry.getValue());			
			ic.addElement(kc);
		}
		return(ic);
	}
	
	
	/* 
	 * main test
	 */
	public static void main(String[] args) {
		try {
			init();
			HashMap hp = new HashMap();
			hp.put("moshes","Moshe Spigler");
			hp.put("avid", "Avi Danon");
			hp.put("tibig", "Tibi Gelzer");
			hp.put("ronenk", "Ronen Krein");
			IndexedCollection ic = getManagersList(hp);
			KeyedCollection kc = new KeyedCollection();
			kc.setDynamic(true);
			kc.trySetValueAt("userid","defuser");
			kc.trySetValueAt("name","defname");					
			addElementAt(ic,kc,0);
			System.out.println("ic = " + ic.toString());
		} catch(Exception e) {
			e.printStackTrace();	
		}
	}
}
