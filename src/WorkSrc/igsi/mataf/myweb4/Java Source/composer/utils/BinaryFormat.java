package composer.utils;

import java.io.IOException;
import com.ibm.dse.base.*;

/**
 * @author Oded Nissan
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class BinaryFormat extends KeyedCollectionFormat {

	/**
	 * Constructor for BinaryFormat.
	 */
	public BinaryFormat() 
	{
		super();
	}
	
	/**
	 * @see com.ibm.dse.base.KeyedCollectionFormat#formatContext(Context)
	 */
	public String formatContext(Context arg0)
		throws
			DSEInvalidClassException,
			DSEInvalidRequestException,
			DSEInvalidArgumentException 
	{
		if(null == arg0) {
			throw new DSEInvalidArgumentException("ERROR","100","BinaryFormat.format() : Context argument cannot be null");	
		}

		String kcName = getDataElementName();
		KeyedCollection kc = null;
		String s = null;
		try {
			if(null != kcName) {
				kc = (KeyedCollection) arg0.getElementAt(kcName);
			} else {
				kc = (KeyedCollection) arg0.getKeyedCollection();
			}
			s = formatKeyedCollection(kc);
		} catch(Exception e) {
			e.printStackTrace();
			s = null;
		}		
		return(s);
	}

	/**
	 * @see com.ibm.dse.base.KeyedCollectionFormat#formatKeyedCollection(KeyedCollection)
	 */
	public String formatKeyedCollection(KeyedCollection arg0)
		throws
			DSEInvalidClassException,
			DSEInvalidRequestException,
			DSEInvalidArgumentException 
	{
		String s = null;
		System.out.println("formating : " + arg0.toString());
		try {
			s = KeyedColSerializer.serialize((KeyedCollection) arg0);
		} catch(Exception e) {
			e.printStackTrace();
			return(null);	
		}
		return(s);				
		
	}

	/**
	 * @see com.ibm.dse.base.KeyedCollectionFormat#unformatContext(String, Context)
	 */
	public DataElement unformatContext(String arg0, Context arg1)
		throws DSEInvalidRequestException, DSEInvalidArgumentException 
	{
		String kcName = getDataElementName();
		KeyedCollection kc = null;		

		System.out.println("unformatting: " + kcName);
		if(null == arg1) {
			throw new DSEInvalidArgumentException("ERROR","100","BinaryFormat.format() : Context argument cannot be null");	
		}
		try {
			if(null != kcName) {
				kc = (KeyedCollection) arg1.getElementAt(kcName);
			} else {
				kc = (KeyedCollection) arg1.getKeyedCollection();	
			}
			kc = unformatKeyedCollection(arg0,kc);				
		} catch(Exception e) {
			e.printStackTrace();
			kc = null;			
		}
		arg1.setKeyedCollection(kc);
		return(kc);
	}

	/**
	 * @see com.ibm.dse.base.KeyedCollectionFormat#unformatKeyedCollection(String, KeyedCollection)
	 */
	public KeyedCollection unformatKeyedCollection(
		String arg0,
		KeyedCollection arg1)
		throws DSEInvalidRequestException, DSEInvalidArgumentException 
	{
		KeyedCollection kc = null;		
		try {
			kc = KeyedColSerializer.deserialize(arg0);
		} catch(Exception e) {
			e.printStackTrace();
			kc = null;			
		}
		return(kc);
	}

	/**
	 * @see com.ibm.dse.base.Externalizable#initializeFrom(Tag)
	 */
	public Object initializeFrom(Tag aTag) throws IOException, DSEException 
	{
		setDataElementName(com.ibm.dse.base.Constants.A_void);
		TagAttribute attribute;
		String name;
		String value;
		boolean flag = false;
		for (int i=aTag.getAttrList().size();--i >= 0;)
		{
			attribute = (TagAttribute)aTag.getAttrList().elementAt(i);
			name = attribute.getName();
			value = (String) (attribute.getValue());
			if (name.equals(com.ibm.dse.base.Constants.DataName)) {							
				setDataElementName(value);
				flag = true;
			} // if
		} // for	
		
		if(!flag) {
			setDataElementName(null);
		}
		return(this);
	}


}
