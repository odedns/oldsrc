package mataf.conversions;

/**
 * 
 * This class holds the configuration data for a Btrieve file
 * read from the XML file.
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class BtConfigData {

	BtField m_fld[];
	BtEntityInfo m_eInfo;
	/**
	 * Constructor for BtConfigData.
	 */
	public BtConfigData(BtField fields[], BtEntityInfo info) 
	{
		super();
		m_fld = fields;
		m_eInfo = info;
	}
	
	public BtField[]  getBtFields()
	{
		return(m_fld);
	}
	
	public BtEntityInfo getEntityInfo()
	{
		return(m_eInfo);
	}

}
