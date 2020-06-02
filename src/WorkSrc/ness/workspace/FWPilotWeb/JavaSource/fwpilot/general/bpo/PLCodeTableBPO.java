package fwpilot.general.bpo;

import java.util.List;

import com.ness.fw.bl.*;
import com.ness.fw.common.exceptions.*;
import com.ness.fw.persistence.*;

import fwpilot.agreement.dao.CharacteristicDAO;
import fwpilot.agreement.dao.StatusDAO;
import fwpilot.agreement.dao.TypeDAO;
import fwpilot.customer.dao.FamilyMemberStatusDAO;
import fwpilot.customer.dao.ProfessionDAO;
import fwpilot.customer.dao.RelatedTypeStatusDAO;
import fwpilot.customer.dao.SexDAO;
import fwpilot.general.bpc.PLCodeTableBPC;
import fwpilot.general.vo.CodeTable;

public class PLCodeTableBPO extends BusinessProcess
{

			
	public static void getAll(PLCodeTableBPC container) throws FatalException  
	{
		ConnectionSequence seq = null;
		try
		{
			seq = ConnectionSequence.beginSequence();		
			List values = null;
		
			if (container.getEntity().equals("status"))
			{
				values = StatusDAO.findAll(seq);
			}
			else if (container.getEntity().equals("characteristic"))
			{
				values = CharacteristicDAO.findAll(seq);
			}
			else if (container.getEntity().equals("type"))
			{
				values = TypeDAO.findAll(seq);
			}
			else if (container.getEntity().equals("profession"))
			{
				values = ProfessionDAO.findAll(seq);
			}
			else if (container.getEntity().equals("familyMemberStatus"))
			{
				values = FamilyMemberStatusDAO.findAll(seq);
			}
			else if (container.getEntity().equals("sex"))
			{
				values = SexDAO.findAll(seq);
			}
			else if (container.getEntity().equals("relatedType"))
			{
				values = RelatedTypeStatusDAO.findAll(seq);
			}
	
			container.setValues(values);
			seq.end();
		}
		catch (PersistenceException e)
		{
			throw new FatalException("persitance",e);
		}
		finally
		{
			if (seq != null)
			{
				try 
				{
					seq.end();
				} 
				catch (PersistenceException e) 
				{
					throw new FatalException("persistance",e);		
				}
			}
		}			
	}
	
	public static void getById(PLCodeTableBPC container) throws PersistenceException, FatalException 
	{
		ConnectionSequence seq = null;
		try
		{
			seq = ConnectionSequence.beginSequence();		
			CodeTable codeTable = null;
			Integer id = container.getId();
		
			if (container.getEntity().equals("status"))
			{
				codeTable = StatusDAO.findById(id,seq);
			}
			else if (container.getEntity().equals("characteristic"))
			{
				codeTable = CharacteristicDAO.findById(id,seq);
			}
			else if (container.getEntity().equals("type"))
			{
				codeTable = TypeDAO.findById(id,seq);
			}
			else if (container.getEntity().equals("profession"))
			{
				codeTable = ProfessionDAO.findById(id,seq);
			}
			else if (container.getEntity().equals("familyMemberStatus"))
			{
				codeTable = FamilyMemberStatusDAO.findById(id,seq);
			}
			else if (container.getEntity().equals("sex"))
			{
				codeTable = SexDAO.findById(id,seq);
			}
			else if (container.getEntity().equals("relatedType"))
			{
				codeTable = RelatedTypeStatusDAO.findById(id,seq);
			}
	
			container.setCodeTable(codeTable);
			seq.end();
		}
		catch (PersistenceException e)
		{
			throw new FatalException("persitance",e);
		}
		finally
		{
			if (seq != null)
			{
				try 
				{
					seq.end();
				} 
				catch (PersistenceException e) 
				{
					throw new FatalException("persistance",e);		
				}
			}
		}

	}
		
}
