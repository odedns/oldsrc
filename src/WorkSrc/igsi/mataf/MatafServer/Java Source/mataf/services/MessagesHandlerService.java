package mataf.services;

import java.awt.Color;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import mataf.data.VisualDataField;
import mataf.format.VisualFieldFormat;
import mataf.services.reftables.RefTables;
import mataf.services.reftables.RefTablesService;

import com.ibm.dse.base.Context;
import com.ibm.dse.base.DSEException;
import com.ibm.dse.base.DSEInvalidArgumentException;
import com.ibm.dse.base.DSEObjectNotFoundException;
import com.ibm.dse.base.DataElement;
import com.ibm.dse.base.DataField;
import com.ibm.dse.base.IndexedCollection;
import com.ibm.dse.base.KeyedCollection;
import com.ibm.dse.base.Service;
import com.ibm.dse.base.Tag;
import com.ibm.dse.base.TagAttribute;
import com.ibm.dse.base.Vector;

/**
 * @author yossid
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class MessagesHandlerService extends Service {
	
	public static final String TABLE_NAME_ATT_NAME = "tableName";
	public static final String KEY_COLUMN_ATT_NAME = "keyColumnName";
	public static final String DESC_COLUMN_ATT_NAME = "msgDescColumnName";
	
	public static final String DEFUALT_MSG_SERVICE_NAME = "runtimeMsgs";
	
	Map mapedSrvData = null;
	
	/**
	 * Constructor for MessagesService.
	 */
	public MessagesHandlerService() {
		super();
	}

	/**
	 * Constructor for MessagesService.
	 * @param arg0
	 * @throws IOException
	 */
	public MessagesHandlerService(String arg0) throws IOException {
		super(arg0);
	}

	/**
	 * @see com.ibm.dse.base.Externalizable#initializeFrom(Tag)
	 */
	public Object initializeFrom(Tag aTag) throws IOException, DSEException {
		
		super.initializeFrom(aTag);
		
		mapedSrvData = new HashMap();
		
		Enumeration subTagsEnum = aTag.getSubTags().elements();
		Tag currentTag = null;
		
		while(subTagsEnum.hasMoreElements()) {
			currentTag = (Tag) subTagsEnum.nextElement();
			mapTag(currentTag);	
		}
		return(this);
	}
	
	private void mapTag(Tag aTag) {
		
		String name = null;
		String value = null;
		String mapKey = null;
		Map mapedTag = new HashMap();
		
		for (int i=0;i<aTag.getAttrList().size();i++) {
			TagAttribute attribute = (TagAttribute) aTag.getAttrList().elementAt(i);			
			name = attribute.getName();
			value = (String) attribute.getValue();
			if (name.equals("id")) {
				mapKey = value;
			} else {
				mapedTag.put(name, value);
			}
		}
		
		mapedSrvData.put(mapKey, mapedTag);
	}
	
	private Object getTableParam(String tableId, String paramName) {		
		return ((Map) mapedSrvData.get(tableId)).get(paramName);
	}
	
	public String getMsgFromTable(String msgNumber, RefTablesService refTables) throws Exception {
		return getMsgFromTable(DEFUALT_MSG_SERVICE_NAME, msgNumber, refTables, new ArrayList());
	}
		
	public String getMsgFromTable(String tableId, String msgNumber, RefTablesService refTables) throws Exception {
		return getMsgFromTable(tableId, msgNumber, refTables, new ArrayList());
	}
	
	public String getMsgFromTable(String tableId, String msgNumber, RefTablesService refTables, List msgParams) throws Exception {
		
		String msgTableName = (String) getTableParam(tableId, TABLE_NAME_ATT_NAME);
		String msgKeyColumnName = (String) getTableParam(tableId, KEY_COLUMN_ATT_NAME);
		String msgDescColumnName = (String) getTableParam(tableId, DESC_COLUMN_ATT_NAME);
		
		String message = (String) refTables.getByKey(msgTableName, msgKeyColumnName, msgNumber, msgDescColumnName);
		MessageFormat format = new MessageFormat(message);
		return format.format(msgParams.toArray());
	}
	
}
