/*
 * Created on 26/11/2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package fwpilot.general.vo;

import java.util.ArrayList;
import java.util.List;

import com.ness.fw.common.exceptions.PersistenceException;
import com.ness.fw.persistence.*;


/**
 * @author yharnof
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CodeTablesUtil
{

	/**
	 * 
	 */
	public CodeTablesUtil()
	{
		super();
	}

	/**
	 * @return a list of {@link CodeTableValue} objects.
	 */
	private List getCodeTableValues (String tableName, String tableId) throws PersistenceException 
	{ 
		List values = new ArrayList();
		String sqlStatement = "select id, name from " + tableName + " order by name";
		SqlService ss = new SqlService(sqlStatement);
		
		ConnectionSequence seq = ConnectionSequence.beginSequence();
		
		Page p = Query.execute(ss, seq);
		seq.end();
		while (p.next())
		{
			values.add(new CodeTableValue(p.getInt("ID"), p.getString("NAME"), tableId));
		}
		return values;
	}


	/**
	 * @return a list of {@link CodeTableValue} objects.
	 */
	public List getProfessions ()throws PersistenceException
	{ 
		return getCodeTableValues("profession", "Profession");
	}

	/**
	 * @return a list of {@link CodeTableValue} objects.
	 */
	public List getAgents ()throws PersistenceException
	{ 
		return getCodeTableValues("T_AGENT", "Agent");
	}
	
	/**
	 * @return a list of {@link CodeTableValue} objects.
	 * @throws PersistenceException
	 */
	public List getCharacteristics ()throws PersistenceException
	{ 
		return getCodeTableValues("T_CHARACT", "Characteristics");
	}

	/**
	 * @return a list of {@link CodeTableValue} objects.
	 * @throws PersistenceException
	 */
	public List getStatuses ()throws PersistenceException
	{ 
		return getCodeTableValues("T_STATUS", "Status");
	}
	
	/**
	 * @return a list of {@link CodeTableValue} objects.
	 * @throws PersistenceException
	 */
	public List getAgreementTypes ()throws PersistenceException
	{ 
		return getCodeTableValues("T_AGR_TY", "AgreementType");
	}

	/**
	 * @return a list of {@link CodeTableValue} objects.
	 * @throws PersistenceException
	 */
	public List getProcesses ()throws PersistenceException
	{ 
		return getCodeTableValues("T_PROCESS", "Process");
	}

	/**
	 * @return a list of {@link CodeTableValue} objects.
	 * @throws PersistenceException
	 */
	public List getSteps ()throws PersistenceException
	{ 
		return getCodeTableValues("T_STEP", "Step");
	}

	/**
	 * @return a list of {@link CodeTableValue} objects.
	 * @throws PersistenceException
	 */
	public List getCities ()throws PersistenceException
	{ 
		return getCodeTableValues("T_CITY", "City");
	}


	
}
