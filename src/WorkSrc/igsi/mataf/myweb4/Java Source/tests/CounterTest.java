package tests;
import com.ibm.dse.application.*;
import com.ibm.dse.base.*;
import com.ibm.dse.applsrv.aa.*;

/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class CounterTest {

	static void init() throws Exception
	{
		Context.reset();
		Settings.reset("http://localhost/myweb4/dse/server/dse.ini");
		Settings.initializeExternalizers(com.ibm.dse.base.Settings.MEMORY);			
	}
	
	public static void main(String[] args) {
		try {
			init();
			
			Tracer trc = new Tracer();
			trc.warning("this is a warning");
			trc.debug("this is debug");
			trc.error("this is error");
			Counter cnt = (Counter) DataElement.readObject("counterA");
			Float total = cnt.getTotal();
			Float incr = cnt.getIncrement();
			Float totIncr = cnt.getTotIncrs();
			System.out.println("incr = " + incr + "\ntotal =" + total +
				"\ntotIncr = " + totIncr);
			cnt.setIncrement(new Float(6000));
			total = cnt.getTotal();
			incr = cnt.getIncrement();
			totIncr = cnt.getTotIncrs();
			System.out.println("incr = " + incr + "\ntotal =" + total +
				"\ntotIncr = " + totIncr);
			
		} catch(Exception e) {
			e.printStackTrace();	
		}
	}
}
