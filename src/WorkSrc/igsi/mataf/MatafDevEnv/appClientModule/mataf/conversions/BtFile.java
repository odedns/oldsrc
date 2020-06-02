package mataf.conversions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import com.ibm.dse.dw.model.ModelAccessException;

/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class BtFile {

	String m_dir;
	String m_fname;
	WBBasicHandler m_handler;
	String m_group = null;

	/**
	 * Constructor for BtFile.
	 */
	public BtFile() {
		super();
	}
	
	/**
	 * initialize a BtFile object.
	 * set the name of the file and directory to find the data and xml config
	 * file. 
	 * @param fname the name of the file.
	 * @param dir the directory to search for the data and config file.
	 * @param handler the WBBasicHandler class to use for handling
	 * this specific entity.
	 * @param group insert only instances belonging to this group.
	 * if null is specified, insert all instances.
	 */
	public BtFile(String fname, String dir, WBBasicHandler handler, String group)
	{
		m_dir = dir;
		m_fname = fname;	
		m_handler = handler;
		m_group = group;
	}

	/** 
	 * set the directory.
	 */
	public void setDir(String dir)
	{
		m_dir = dir;
	}
	
	/**
	 * set file name.
	 */
	public void setFname(String fname)
	{
		m_fname =fname;	
	}
		
	/**
	 * process the input BtFile. accroding to the 
	 * specific record format.
	 * call the handler for each record read.
	 */
	public void process() throws Exception
	{
		int lines = 0;
		String dataFile = m_dir + File.separatorChar + m_fname + ".txt";
		String cfgFile = "properties" + File.separatorChar + m_fname + ".xml";
		/**
		 * read configuration xml file.
		 * get the field array and entity info from
		 * the config file.
		 */
		BtConfigData data = BtConfigReader.readConfig(cfgFile, m_fname);
		BufferedReader br = new BufferedReader(new FileReader(dataFile));
		BtField fields[] = data.getBtFields();
		BtEntityInfo eInfo = data.getEntityInfo();


		/**
		 * process all lines in the input data file.
		 * for each line parse the record according 
		 * to the record format and update the 
		 * workspace with an Instance.
		 */		
		m_handler.setEntityInfo(eInfo);
		String line = null;
		while(null != (line = br.readLine())) {
			if(line.length() <=1) {
				break;
			}
			//System.out.println("line = " + line);
			++lines;
			fields = BtRecord.parseRecord(line,fields);
			// call handler pass fields array.	
			try {
				m_handler.addInstance(fields,m_group);
				System.out.println("added record: " + lines);
			} catch(ModelAccessException e) {
				System.out.println("Error adding record");
				e.printStackTrace();	
			}
			/*			
			if(lines > 2) 
				break;
			*/
			
		}
		br.close();
		System.out.println("processed lines : " + lines);
		
	}
	
	

}
