package hoshen.ejb.dynproxy;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Invoker_SEI extends Remote
{
	String invoke(
			String className,
			String methodName,
			String params,
			String args,
			String identity)
	throws RemoteException;
}