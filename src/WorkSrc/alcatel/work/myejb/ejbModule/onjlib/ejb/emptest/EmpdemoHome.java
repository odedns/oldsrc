package onjlib.ejb.emptest;

import javax.ejb.*;
import java.rmi.RemoteException;
import java.util.*;

public interface EmpdemoHome extends EJBHome {

  public Empdemo create(int id, String name, int dept)
	throws CreateException, RemoteException;
  public Empdemo findByPrimaryKey(Integer id)
	throws FinderException, RemoteException;
  public Collection findAll()
	throws FinderException, RemoteException;

}
