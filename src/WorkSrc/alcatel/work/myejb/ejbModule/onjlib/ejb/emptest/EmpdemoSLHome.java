
package onjlib.ejb.emptest;

import javax.ejb.*;
import java.rmi.RemoteException;
import java.util.*;

public interface EmpdemoSLHome extends EJBHome {
  public EmpdemoSL create()
	throws CreateException, RemoteException;

}
