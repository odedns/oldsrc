package mataf.halbanathon.operations;

/**
 *
 * 
 * @author Nati Dykstein. Creation Date : (04/11/2003 11:03:28).  
 */
public class HalbanatHonTnay12ServerOp extends HalbanatHonAbstractTnayServerOp 
{
	/**
	 * @see com.ibm.dse.base.DSEServerOperation#execute()
	 */
	public void execute() throws Exception 
	{		
		String bankPeula = (String)getValueAt("GLSX_K86P_PARAMS.HA_BANK");
		String bankSystem = (String)getValueAt("GLSG_GLBL.GL_BANK");
		if(!bankPeula.equals(bankSystem))
			setConditionValid(false);
		else
		{
			String accountType = (String)getValueAt("GLSX_K86P_PARAMS.HA_SCH");
			if(accountType.equals("0"))
				setConditionValid(false);
			else
			{
				String coinType = (String)getValueAt("GLSX_K86P_PARAMS.HA_MATBEA");
				if(coinType.equals("0"))
					setConditionValid(HalbanatHonUtil.isExistsInMati(getContext()));
				else
					setConditionValid(HalbanatHonUtil.isExistsInMatach(getContext()));
			}
		}
	}
}
