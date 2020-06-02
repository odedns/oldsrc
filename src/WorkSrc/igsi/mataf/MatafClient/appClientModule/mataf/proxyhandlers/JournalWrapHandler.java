package mataf.proxyhandlers;

import java.util.HashMap;

import mataf.logger.GLogger;
import mataf.services.electronicjournal.JournalWrapClientOp;
import mataf.services.proxy.AbstractRequestHandler;
import mataf.services.proxy.ProxyRequest;
import mataf.services.proxy.RequestException;

import com.ibm.dse.base.DSEServerOperation;
import com.ibm.dse.base.DataElement;
import com.ibm.dse.base.IndexedCollection;
import com.ibm.dse.base.KeyedCollection;

/**
 * @author Oded Nissan 11/03/2004
 * This handler handles RT requests to wrap the
 * Composer journal entities.
 *  
 */
public class JournalWrapHandler extends AbstractRequestHandler {

	private static final String EJCODE_PARAM = "ejcode";
	/**
	 * @see mataf.services.proxy.RequestHandlerIF#execRequest(ProxyRequest)
	 */
	public HashMap execRequest(ProxyRequest req) throws RequestException {
		
		HashMap params = req.getParams();		
		GLogger.debug("Wrap handler params = " + params.toString());
		String entity = (String) params.get(EJCODE_PARAM);
		if("Z".equals(entity)) {
			entity = "SLIKA_JOURNAL";	
		}
		try {
			JournalWrapClientOp op = (JournalWrapClientOp) DSEServerOperation.readObject("journalWrapClientOp");		
			IndexedCollection ic = (IndexedCollection) op.getElementAt("journalEntityList");
			KeyedCollection kc = (KeyedCollection) DataElement.readObject("journalEntity");
			kc.setValueAt("name",entity);
			ic.addElement(kc);				
			op.addElement(ic);
			op.execute();	
		} catch(Exception e) {
			e.printStackTrace();
			throw new RequestException("Error in JournalWrapHandler: " + e);	
		}
		return null;
	}

}
