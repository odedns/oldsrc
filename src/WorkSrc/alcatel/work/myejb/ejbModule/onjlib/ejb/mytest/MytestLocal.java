

package onjlib.ejb.mytest;

import javax.ejb.*;

public interface  MytestLocal extends EJBLocalObject {
	void setData(String data) ;
	String getData();
}
