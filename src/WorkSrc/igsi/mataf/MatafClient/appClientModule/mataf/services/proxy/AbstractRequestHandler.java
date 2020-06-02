package mataf.services.proxy;

import java.util.HashMap;
/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public abstract class AbstractRequestHandler implements RequestHandlerIF {

	/**
	 * Constructor for AbstractRequestHandler.
	 */
	public AbstractRequestHandler() {
		super();
	}
	
	
	/**
	 * execute the request.
	 * In case of success return the return params
	 * in the HashMap to be sent back to the caller.
	 * in case of error throw an exception with 
	 * the appropriate error code and error message.
	 */
	public abstract HashMap execRequest(ProxyRequest req) 
		throws RequestException;

}
