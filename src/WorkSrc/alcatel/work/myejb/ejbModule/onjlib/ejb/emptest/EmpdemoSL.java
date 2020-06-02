package onjlib.ejb.emptest;

import java.rmi.RemoteException;
import javax.ejb.*;

public interface EmpdemoSL extends EJBObject {
	public Empinfo [] getAll() throws RemoteException;
	public Empinfo  getEmp(int id) throws RemoteException;
}
