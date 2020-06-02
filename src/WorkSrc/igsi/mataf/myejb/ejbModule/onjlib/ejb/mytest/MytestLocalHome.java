

package onjlib.ejb.mytest;

import javax.ejb.*;
import java.rmi.*;

public interface  MytestLocalHome extends EJBLocalHome {
	public MytestLocal create() throws CreateException;
}
