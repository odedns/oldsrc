package fwpilot.general.dao;

import java.util.ArrayList;
import java.util.List;

import com.ness.fw.common.exceptions.PersistenceException;
import com.ness.fw.persistence.*;

import fwpilot.general.vo.CodeTableValue;

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
	private List getCodeTableValues (ConnectionSequence seq, String tableName, String tableId) throws PersistenceException 
	{ 
		List values = new ArrayList();
		String sqlStatement = "select id, name from " + tableName + " order by name";
		SqlService ss = new SqlService(sqlStatement);		
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
	public List getProfessions (ConnectionSequence seq) throws PersistenceException
	{ 
		return getCodeTableValues(seq, "profession", "Profession");
	}

	/**
	 * @return a list of {@link CodeTableValue} objects.
	 */
	public List getAgents (ConnectionSequence seq) throws PersistenceException
	{ 
		return getCodeTableValues(seq, "T_AGENT", "Agent");
	}
	
	/**
	 * @return a list of {@link CodeTableValue} objects.
	 * @throws PersistenceException
	 */
	public List getCharacteristics (ConnectionSequence seq) throws PersistenceException
	{ 
		return getCodeTableValues(seq, "T_CHARACT", "Characteristics");
	}

	/**
	 * @return a list of {@link CodeTableValue} objects.
	 * @throws PersistenceException
	 */
	public List getStatuses (ConnectionSequence seq) throws PersistenceException
	{ 
		return getCodeTableValues(seq, "T_STATUS", "Status");
	}
	
	/**
	 * @return a list of {@link CodeTableValue} objects.
	 * @throws PersistenceException
	 */
	public List getAgreementTypes (ConnectionSequence seq) throws PersistenceException
	{ 
		return getCodeTableValues(seq, "T_AGR_TY", "AgreementType");
	}

	/**
	 * @return a list of {@link CodeTableValue} objects.
	 * @throws PersistenceException
	 */
	public List getProcesses (ConnectionSequence seq) throws PersistenceException
	{ 
		return getCodeTableValues(seq, "T_PROCESS", "Process");
	}

	/**
	 * @return a list of {@link CodeTableValue} objects.
	 * @throws PersistenceException
	 */
	public List getSteps (ConnectionSequence seq) throws PersistenceException
	{ 
		return getCodeTableValues(seq, "T_STEP", "Step");
	}

	/**
	 * @return a list of {@link CodeTableValue} objects.
	 * @throws PersistenceException
	 */
	public List getCities (ConnectionSequence seq) throws PersistenceException
	{ 
		return getCodeTableValues(seq, "T_CITY", "City");
	}


	
}
