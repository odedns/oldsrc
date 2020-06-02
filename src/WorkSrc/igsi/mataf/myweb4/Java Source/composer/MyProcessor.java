package composer;

import java.io.IOException;

import com.ibm.dse.automaton.*;
import com.ibm.dse.base.*;


/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class MyProcessor extends DSEProcessor {

	String m_lastState;
	Context m_lastCtx = null;
	/**
	 * Constructor for MyProcessor.
	 */
	public MyProcessor() {
		super();
	}

	/**
	 * Constructor for MyProcessor.
	 * @param arg0
	 */
	public MyProcessor(String arg0) {
		super(arg0);
	}

	/**
	 * Constructor for MyProcessor.
	 * @param arg0
	 * @param arg1
	 */
	public MyProcessor(String arg0, String arg1) {
		super(arg0, arg1);
	}

	/**
	 * Constructor for MyProcessor.
	 * @param arg0
	 */
	public MyProcessor(boolean arg0) {
		super(arg0);
	}

	/**
	 * @see com.ibm.dse.automaton.Processor#initialize()
	 */
	public void initialize() throws IOException, DSEException {
		super.initialize();
	}

	/**
	 * @see com.ibm.dse.base.Notifier#terminate()
	 */
	public void terminate() throws DSEException {
		super.terminate();
	}

	/**
	 * @see com.ibm.dse.base.Externalizable#initializeFrom(Tag)
	 */
	public Object initializeFrom(Tag arg0) throws IOException, DSEException {
		return super.initializeFrom(arg0);
	}
	
	/**
	 *  executes the processor.
	 * call super.execute();
	 */
	public void execute() throws DSEInvalidArgumentException,
				                    DSEProcessorException
	{
		super.execute();
		m_lastCtx = getContext();
		System.out.println("MyProcessor.execute()");	
	}				                    


	
	public void setCurrentState(State st) 
	{
		super.setCurrentState(st);
		m_lastCtx = getContext();
		if(null != st) {
			m_lastState = st.getName();				
			System.out.println("lastState=" + m_lastState);
			printCtx();
		}
	}
		
	private void printCtx() 
	{
		try {
			System.out.println("mydata.name = " + (String) m_lastCtx.getValueAt("Name"));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getLastState()
	{
		return(m_lastState);
	}	

}
