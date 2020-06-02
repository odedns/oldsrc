package mataf.conversions;
import com.ibm.dse.base.*;
import com.ibm.dse.dw.cm.*;
import com.ibm.dse.dw.model.*;
import java.io.*;

import mataf.utils.WBConnectionManager;

/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public abstract class WBBasicHandler {

	BtEntityInfo m_eInfo;
	String m_id;
	int m_counter = 0;
	/**
	 * Constructor for WBBasicHandler.
	 * @param entity the entity instances will be
	 * added to.
	 */
	public WBBasicHandler() 
	{
		super();		
	}

	public void setEntityInfo(BtEntityInfo eInfo) 
	{
		m_eInfo = eInfo;
	}

	/**
	 * get the group name from the record.
	 * Translate the code into a group name.
	 */
	private String getSubgroup(BtField fields[])
	{
		String name=null;
		String value=null;
		for(int i=0; i < fields.length; ++i) {
			name = fields[i].getName();
			if(name.equals("dictCode")) {
				value = (String) fields[i].getValue();
				fields[i].setIgnore(true);
				value = ValueConversions.cvtGroup(value);
				break;
			}		
		}
		return(value);	
	}
	/**
	 * return the Id for the new instance
	 * by searching the field array for a 
	 * specific field.
	 * In most cases: engName.
	 */
	protected String getId(BtField fields[])
	{
		String name=null;
		String value=null;
		for(int i=0; i < fields.length; ++i) {
			name = fields[i].getName();
			if(name.equals("id")) {
				value = (String) fields[i].getValue();		
				break;
			}		
		}
		return(value);
	}	
	
	/**
	 * get the related instance name.
	 */
	protected String getRelatedInstance(String pkName, BtField fields[])
	{
		String name = null;
		String value=null;
		for(int i=0; i < fields.length; ++i) {
			name = fields[i].getName();
			if(name.equals(pkName)) {
				value = (String) fields[i].getValue();		
				break;
			}		
		}		
		return(value);
	}
	
	private Group getFullGroup(String baseGroup,String group, String name)
		throws Exception
	{
		int len = name.length();
		int lastIndex = ( 4 < len ? 4 : len);
		String prefix = name.substring(0,lastIndex);
		String grpName = baseGroup + '.' + group;
		DefaultGroupPath grpPath = new DefaultGroupPath(grpName + '.' + prefix);
//		System.out.println("group = " + grpPath.toString());
//		System.out.println("parent = " + grpName);
		Workspace wks = WBConnectionManager.getWorkspace();											
		Group grp = wks.getGroup(grpPath);
		if(null == grp) {
			grp = wks.getModelFactoryImpl().createGroup();
			grp.setName(prefix);
			grp.setParent(wks.getGroup(new DefaultGroupPath(grpName)));
			grp.setDescription("description for " + prefix);
			grp.setOwner(wks.getUser("odedn"));
			grp.setUsersGroup(wks.getUsersGroup("default"));					
			grp.setStereoType("Server");
			grp.save();
		}
		return(grp);	
	}
	/**
	 * loop over all BtFields and update the workbench entity 
	 * with their values.
	 * @param BtField the BtField array to process.
	 * @throws Exception in case of error.
	 * @return Instance the added instance.
	 */
	public void addInstance(BtField fields[], String dataGroup) throws Exception
	{
//		System.out.println("WBBasicHandler.addInstance()");
		System.out.println("add to Entity : " + m_eInfo.getEntityName());
		
		/**
		 * read all fields if field name is engName then 
		 * we call setName.
		 * otherwise call setValue.
		 * Get the group name and put the Instance in the correct group.
		 */
		Group grp = null;
		Workspace wks = WBConnectionManager.getWorkspace();
		Entity ent = wks.getEntity(m_eInfo.getEntityName());		
		Instance myIns = wks.getModelFactoryImpl().createInstance();
		String relatedEntity = m_eInfo.getRelatedEntity();
		String group = getSubgroup(fields);
		if(dataGroup != null) {
			if(!group.equals(dataGroup)) {
				System.out.println("ignored: " + group);	
				return;
			}
		}		
		m_id = getId(fields);
		if(m_id == null) {
			//throw new Exception("Error id is null");	
			m_id = "unknown";
			System.out.println("adding unknown");
		}
		String baseGroup = m_eInfo.getGroup() + ".";
		myIns.setEntity(ent);
		myIns.setName(m_id);
		myIns.setOwner(wks.getUser("odedn"));
		myIns.setUsersGroup(wks.getUsersGroup("default"));		
		if(null == relatedEntity) {
			grp = getFullGroup(baseGroup,group, m_id);
			myIns.setParent(grp);
		}
		for(int i=0; i < fields.length; ++i) {
			String name = fields[i].getName();
			String value = (String) fields[i].getValue();
			/* if field should be ignored. */
			if(fields[i].getIgnore()) {
				continue;
			}
			/**
			 * convert yn fields to 
			 * a boolean value.
			 */
			if(fields[i].getYn()) {
				String val = ValueConversions.cvtYn((String)fields[i].getValue());
				fields[i].setValue(val);
				value = (String) fields[i].getValue();
				
			}
			if(name.equals("description")) {
					System.out.println("setting desc = " +value);					
					myIns.setDescription(value);

			} else {
					handleAttributes(ent, myIns, name, value);		
			}
		} // for


		System.out.println("adding : " + m_id);
		/**
		 * if there is a related entity we
		 * need to look up the related entity
		 * with the specific pk.
		 */
		if(null != relatedEntity) {
			String pk = m_eInfo.getRelatedEntityPK();
			String relatedInstance = getRelatedInstance(pk, fields);			
			DefaultInstanceRelativePath irPath = new DefaultInstanceRelativePath(relatedInstance);			
			grp = getFullGroup(baseGroup,group, relatedInstance);
			DefaultInstancePath iPath = new DefaultInstancePath(grp.getPath(),irPath);
			System.out.println("iPath = " + iPath);
			Instance ins = wks.getInstance(iPath);			
			if(null == ins) {
				throw new ModelAccessException("instance does not exist: " + iPath);	
			}
			/**
			 * We may have to fields with the same name in 
			 * the same records. This is not possible in composer
			 * so we create a unique name by appending a counter
			 * to the name.
			 * The refId stays the same.
			 */
			try {
				myIns.setParent(ins);
			} catch(ModelAccessException me) {
				++m_counter;
				m_id = m_id + "_" + m_counter;
				System.out.println("adding with counter : " + m_id);
				myIns.setName(m_id);
				myIns.setParent(ins);
			}
				
		}
		myIns.save();		
		/**
		 * call afterAddInstance callback.
		 */
		afterAddInstance(myIns,fields);
	}
	
	
	
	/**
	 * handle specific attribute conversions
	 * for the current file.
	 */
	public abstract void handleAttributes(Entity ent, Instance ins, String name, String value);

	/**
	 * after addInstance.
	 * This call back is called after addInstance.
	 * If we want to do something after the instance has been
	 * added - this is the place.
	 */
	protected void afterAddInstance(Instance ins, BtField fields[])
	{
	}
}
