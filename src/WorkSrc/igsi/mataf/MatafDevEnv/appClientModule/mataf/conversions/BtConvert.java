package mataf.conversions;
import java.io.File;
import mataf.utils.WBConnectionManager;

/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class BtConvert {

	/**
	 * Constructor for BtConvert.
	 */
	public BtConvert() {
		super();
	}
	
	
	public static void main(String argv[])
	{
		System.out.println("in BtConvert ...");		
		BtFile btfile;
		String group = "Common";

		/**
		 * if group is null then process all groups.
		 */
		/*
		FldHandler handler = new FldHandler();
		btfile = new BtFile("fldlst","d:/work/dev_env/conversion",handler,group);
		
*/
			
		/*		
		FldscrHandler scrhandler = new FldscrHandler();
		btfile = new BtFile("fldscr","d:/work/dev_env/conversion",scrhandler,group);
		
		*/
		
		
/*
		StructHandler shandler = new StructHandler();
		btfile = new BtFile("strucl","d:/work/dev_env/conversion",shandler,group);
*/		
		
		StructDetailsHandler sdhandler = new StructDetailsHandler();
		btfile = new BtFile("strucd","d:/work/dev_env/conversion",sdhandler,group);				
					
		try {
			btfile.process();
			WBConnectionManager.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}
