package hoshen.ejb.dynproxy;

import java.util.ArrayList;

/**
 * Remote interface for Enterprise Bean: Invoker
 */
public interface Invoker extends javax.ejb.EJBObject
{
	public Object invoke(Class c, String methodName, ArrayList args) throws Exception;
}
