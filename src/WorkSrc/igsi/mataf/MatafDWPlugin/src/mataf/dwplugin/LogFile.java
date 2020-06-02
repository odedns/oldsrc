package mataf.dwplugin;
import java.io.*;

import com.ibm.jvm.io.FileOutputStream;

/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class LogFile {
	
	static PrintWriter m_pw = null;
	static LogFile m_log = null;
	
	/**
	 * init the logger.
	 * Open the file.
	 */
	private static void init() throws IOException
	{
		if(m_pw == null) {
			m_pw = new PrintWriter(new java.io.FileOutputStream("d:/work/matafdw.log"));	
		}	
			
	}
	
	
	public static LogFile getInstance() 
	{
		if(null == m_log) {
			m_log = new LogFile();
			try {
				init();	
			} catch(IOException ie) {
				m_log = null;	
			}
		}
		return(m_log);
	}
	
	public static String getStackTrace(Exception e) 
	{

		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		String s = sw.getBuffer().toString();
		return(s);
		
		
	}
	/**
	 * log a message.
	 */
	public static void log(String msg)
	{
		m_pw.println(msg);
		m_pw.flush();
	}
	
		
	public static void main(String[] args) {
		
		LogFile log = LogFile.getInstance();
		log.log("fuck off");
	}
}
