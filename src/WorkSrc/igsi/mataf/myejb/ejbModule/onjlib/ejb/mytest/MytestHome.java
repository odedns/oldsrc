
package onjlib.ejb.mytest;

import javax.ejb.*;
import java.rmi.*;

public interface  MytestHome extends EJBHome {
	public Mytest create() throws RemoteException, CreateException;
}
