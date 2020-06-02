package mataf.services.proxy;

import java.util.HashMap;

import mataf.logger.GLogger;

/**
 * @author Oded Nissan 11/06/2003
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class SampleRequestHandler extends AbstractRequestHandler {

	/**
	 * @see composer.services.proxy.AbstractRequestHandler#execRequest(ProxyRequest)
	 */
	public HashMap execRequest(ProxyRequest req) 
		throws RequestException 
	{
		GLogger.debug("SampleRequestHandler : " + req.toString());
		if(req.getCommand() == -1) {
			throw new RequestException(220,"Sample Handler:Some fucking error");
		}
		if(req.getCommand() == Integer.parseInt(RTCommands.EVENT_LOGIN_COMMAND)) {
			GLogger.debug("got after login event");	
		}
		HashMap ht = new HashMap();
		ht.put("retCode","0");
		ht.put("reVal1","foo");
		ht.put("reVal2","bar");
		return(ht);
		
		
	}

}
