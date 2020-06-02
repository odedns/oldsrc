package mataf.conversions;

/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class BtEntityInfo {
	String m_group;
	String m_entity;
	String m_relation;
	String m_pk;
	/**
	 * Constructor for BtEntityInfo.
	 */
	public BtEntityInfo() {
		super();
		m_entity = m_relation =  m_pk = m_group = null;
	}
	
	/**
	 * constructor.
	 */
	public BtEntityInfo(String entity, String relation, String pk, String group) 
	{
		super();
		m_entity = entity;
		m_relation = relation;
		m_pk = pk;
		m_group = group;
	}
	


	public void setGroup(String group)
	{
		m_group = group;	
	}
	
	public String getGroup()
	{
		return(m_group);	
	}
	
	public void setEntityName(String entity)
	{
		m_entity = entity;
	}
	
	
	public String getEntityName()
	{
		return(m_entity);
	}


	public void setRelatedEntity(String relation)
	{
		m_relation = relation;	
	}
	/**
	 * get the name of the related entity.
	 * We will lookup this entity.
	 */	
	public String getRelatedEntity()
	{
		return(m_relation);
	}	


	public void setRelatedEntityPK(String pk)
	{
		m_pk = pk;	
	}
	/**
	 * get the attribute which is the pk
	 * of the related entity.
	 * We can lookup the related entity using
	 * this pk.
	 */	
	public String getRelatedEntityPK()
	{
		return(m_pk);
	}
	

}
