package mataf.halbanathon.operations;

import com.ibm.dse.base.Hashtable;
import com.ibm.dse.base.IndexedCollection;
import com.ibm.dse.base.Vector;

import mataf.data.VisualDataField;
import mataf.format.VisualFieldFormat;
import mataf.general.operations.MatafOperationStep;
import mataf.general.operations.OperationsUtil;
import mataf.services.reftables.RefTables;

/**
 *
 * 
 * @author Nati Dykstein. Creation Date : (02/11/2003 11:01:15).  
 */
public class H13 extends MatafOperationStep 
{
	/**
	 * @see mataf.general.operations.MatafOperationStep#executeOp()
	 */
	public int executeOp() throws Exception 
	{
		Vector sugPeulaRows = HalbanatHonUtil.getSugPeulaRows(getContext());
		
		if(sugPeulaRows.size()==0)
			return RC_OK;
		
		Hashtable sugPeulaRow = (Hashtable) sugPeulaRows.elementAt(0);
		String peulaMemukenet = (String)sugPeulaRow.get("HA_PEULA_MEMUKENET");
		if(peulaMemukenet.equals("3"))
		{
			VisualDataField sugLakoachBean = 
				(VisualDataField)getElementAt("HASS_LAKOACH_SUG.HA_SUG_LAKOACH");
			sugLakoachBean.setValue("1");
			sugLakoachBean.setIsEnabled(Boolean.FALSE);
			
			VisualDataField transmitButton = 
				(VisualDataField)getElementAt("HASS_LAKOACH_SUG.transmitButton");
			transmitButton.setIsEnabled(Boolean.FALSE);
			
			VisualDataField nextButton = 
				(VisualDataField)getElementAt("HASS_LAKOACH_SUG.nextButton");
			nextButton.setShouldRequestFocus(Boolean.TRUE);
			
			return RC_OK;
		}
		
		String actionNotInTable = 
					(String)getValueAt("HelpData.actionNotInTable");
		if(actionNotInTable.equals("1"))
			return RC_OK;
		
		
		Hashtable record = HalbanatHonUtil.getDecisionTableRecord(getContext());
		if(record.get("HA_MIZDAMEN_BILVAD").equals("1"))
		{
			VisualDataField sugLakoachBean = 
				(VisualDataField)getElementAt("HASS_LAKOACH_SUG.HA_SUG_LAKOACH");
			sugLakoachBean.setValue("1");
			
			VisualDataField transmitButton = 
				(VisualDataField)getElementAt("HASS_LAKOACH_SUG.transmitButton");
			transmitButton.setIsEnabled(Boolean.FALSE);
			
			VisualDataField nextButton = 
				(VisualDataField)getElementAt("HASS_LAKOACH_SUG.nextButton");
			nextButton.setIsEnabled(Boolean.TRUE);
			nextButton.setShouldRequestFocus(Boolean.TRUE);
			
			return RC_OK;
		}
		
		if(record.get("HA_MIZDAMEN_BILVAD").equals("2"))
		{
			VisualDataField sugLakoachBean = 
				(VisualDataField)getElementAt("HASS_LAKOACH_SUG.HA_SUG_LAKOACH");
			sugLakoachBean.setValue("2");
			
			VisualDataField nextButton = 
				(VisualDataField)getElementAt("HASS_LAKOACH_SUG.nextButton");
			nextButton.setIsEnabled(Boolean.FALSE);
			
			VisualDataField transmitButton = 
				(VisualDataField)getElementAt("HASS_LAKOACH_SUG.transmitButton");
			transmitButton.setIsEnabled(Boolean.TRUE);
			transmitButton.setShouldRequestFocus(Boolean.TRUE);
			
			return RC_OK;
		}
		
		if(record.get("HA_MIZDAMEN_BILVAD").equals("0"))
		{
			VisualDataField sugLakoachBean = 
				(VisualDataField)getElementAt("HASS_LAKOACH_SUG.HA_SUG_LAKOACH");
			sugLakoachBean.setIsEnabled(Boolean.TRUE);

			return RC_OK;
		}
		
		return RC_OK;
		
	}

}
