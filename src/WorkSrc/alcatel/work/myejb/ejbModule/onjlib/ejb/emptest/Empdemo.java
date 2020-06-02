package onjlib.ejb.emptest;

import java.rmi.RemoteException;
import javax.ejb.*;

public interface Empdemo extends EJBObject {
	public String getName() throws RemoteException;
	public void setName(String data) throws RemoteException;
	public int getDept() throws RemoteException;
	public void setDept(int dept) throws RemoteException;
	public int getId() throws RemoteException;
}
