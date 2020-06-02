

package onjlib.ejb.mytest;

import javax.ejb.*;
import java.util.*;
import java.rmi.*;

public interface  MytestLocal extends EJBLocalObject {
	void setData(String data);
	String getData();
}
