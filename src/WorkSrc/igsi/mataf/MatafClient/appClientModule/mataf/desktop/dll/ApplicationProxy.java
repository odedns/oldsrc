package mataf.desktop.dll;

/**
 * @author Eyal Ben Ze'ev
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class ApplicationProxy {
	private static ApplicationProxy instance;
	static {
		System.loadLibrary("link");
	}
	public ApplicationProxy() {
		
	}
	
	public static synchronized ApplicationProxy getProxy() {
		if (instance==null)
			instance = new ApplicationProxy();
		return instance;
	}
	public native void printHello();
	public native boolean canRunTrx(String trxName);
	
	public static void main(String[] args){
		ApplicationProxy app = new ApplicationProxy();
		app.printHello();
		
		System.out.println(app.canRunTrx("001"));
	}

}
