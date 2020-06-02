package mataf.halbanathon.operations;

/**
 *
 * 
 * @author Nati Dykstein. Creation Date : (03/11/2003 17:57:37).  
 */
public class HalbanatHonTnay3ServerOp extends HalbanatHonAbstractTnayServerOp
{

	/**
	 * @see com.ibm.dse.base.DSEServerOperation#execute()
	 */
	public void execute() throws Exception 
	{
		String accountNumber 	= (String)getValueAt("GLSX_K86P_PARAMS.HA_CH");
		String accountType 		= (String)getValueAt("GLSX_K86P_PARAMS.HA_SCH");
		
		if( (accountNumber.equals("431002")) && (accountType.equals("064")) )
			setConditionValid(true);
		else
			setConditionValid(false);
	}
}
