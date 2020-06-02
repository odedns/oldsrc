package onjlib.ejb.invoker;

import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * Remote interface for Enterprise Bean: Invoker
 */
public interface Invoker 
{
	public Object invoke(String className, String methodName, ArrayList args, String argsHandler) throws Exception, RemoteException;
}
