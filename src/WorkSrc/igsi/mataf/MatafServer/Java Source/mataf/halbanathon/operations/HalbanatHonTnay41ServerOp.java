package mataf.halbanathon.operations;

/**
 *
 * 
 * @author Nati Dykstein. Creation Date : (05/11/2003 18:55:41).  
 */
public class HalbanatHonTnay41ServerOp extends HalbanatHonAbstractTnayServerOp 
{
	public void execute() throws Exception 
	{		
		String bankSystem = (String)getValueAt("GLSG_GLBL.GL_BANK");
		String accountType = (String)getValueAt("GLSX_K86P_PARAMS.HA_SCH");
		String accountNumber = (String)getValueAt("GLSX_K86P_PARAMS.HA_CH");
		
		if((bankSystem.equals("52")) && 
			(accountType.equals("064"))	&& 
			(accountNumber.equals("001334")) )
		{
			setConditionValid(true);
		}
		else
		{
			setConditionValid(false);
		}
	}
}
