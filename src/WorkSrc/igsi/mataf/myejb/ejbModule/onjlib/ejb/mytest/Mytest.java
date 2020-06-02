
package onjlib.ejb.mytest;

import javax.ejb.*;
import java.util.*;
import java.rmi.*;

public interface  Mytest extends EJBObject {
	void setData(String data) throws RemoteException;
	String getData() throws RemoteException;
}
