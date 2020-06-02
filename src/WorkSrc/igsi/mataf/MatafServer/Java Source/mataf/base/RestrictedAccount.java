package mataf.base;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import com.ibm.dse.base.DataElement;
import com.ibm.dse.base.KeyedCollection;
import com.ibm.dse.base.Tag;
import com.ibm.dse.base.TagAttribute;

/**
 * @author Eyal Ben Ze'ev
 *
 * This class represnts an account that some operations are not allowed when
 * working with it.
 */
public class RestrictedAccount extends KeyedCollection {

	String opStepClassName;
	ArrayList opStepParameter;

	/**
	 * Constructor for RestrictedAccount.
	 */
	public RestrictedAccount() {		
		super();
		opStepParameter = new ArrayList();
	}

	/**
	 * Constructor for RestrictedAccount.
	 * @param arg0
	 * @throws IOException
	 */
	public RestrictedAccount(String arg0) throws IOException {
		super(arg0);
		opStepParameter = new ArrayList();
	}

	/**
	 * initializeFrom() method as badly implemnted by BCN labs,
	 * with the added initializer for the opStepClassName property.
	 * 
	 * @param aTag
	 * @throws IOException
	 */
	public Object initializeFrom(Tag aTag) throws IOException {
		super.description = "";
		com.ibm.dse.base.Hashtable parameters = new com.ibm.dse.base.Hashtable();
		boolean createHashtable = false;
		for (int i = 0; i < aTag.getAttrList().size(); i++) {
			TagAttribute attribute = (TagAttribute) aTag.getAttrList().elementAt(i);
			String name = attribute.getName();
			String value = (String) attribute.getValue();
			if (name.equals("id"))
				setName(value);
			else if (name.equals("dynamic"))
				setDynamic(value);
			else if (name.equals("description"))
				setDescription(value);
			else if (name.equals("opStepClassName")) {
				setOpStepClassName(value);
			} else {
				createHashtable = true;
				parameters.put(name, value);
			}
		}

		Tag nextSubTag = null;
		int nTags = aTag.getSubTags().size();
		for (int i = 0; i < nTags; i++) {
			nextSubTag = (Tag) aTag.getSubTags().elementAt(i);
			if (nextSubTag != null)
				if (nextSubTag.getName().equals("param")) {
					String paramName = null;
					String paramValue = null;
					for (Enumeration subTagAttributes = nextSubTag.getAttrList().elements(); subTagAttributes.hasMoreElements();) {
						TagAttribute subTagAttribute = (TagAttribute) subTagAttributes.nextElement();
						if (subTagAttribute.getName().equals("id"))
							paramName = (String) subTagAttribute.getValue();
						else if (subTagAttribute.getName().equals("value"))
							paramValue = (String) subTagAttribute.getValue();
					}

					if (paramName != null && paramValue != null) {
						createHashtable = true;
						parameters.put(paramName, paramValue);
					}
				} else if (nextSubTag.getName().equals("opStepParameter")){
					String bank = null;
					String snif = null;
					for (Enumeration subTagAttributes = nextSubTag.getAttrList().elements(); subTagAttributes.hasMoreElements();) {
						TagAttribute subTagAttribute = (TagAttribute) subTagAttributes.nextElement();
						if (subTagAttribute.getName().equals("bank"))
							bank = (String) subTagAttribute.getValue();
						else if (subTagAttribute.getName().equals("snif"))
							snif = (String) subTagAttribute.getValue();
					}
					if (bank != null && snif != null) {
						Hashtable params = new Hashtable();
						params.put(bank, snif);
						opStepParameter.add(params);
					}
				} else {
					DataElement aDataElement = (DataElement) DataElement.getExternalizer().convertTagToObject(nextSubTag);
					addElement(aDataElement);
				}
		}

		if (createHashtable)
			setParameters(parameters);
		return this;
	}	

	/**
	 * Returns the opStepClassName.
	 * @return String
	 */
	public String getOpStepClassName() {
		return opStepClassName;
	}

	/**
	 * Sets the opStepClassName.
	 * @param opStepClassName The opStepClassName to set
	 */
	public void setOpStepClassName(String opStepClassName) {
		this.opStepClassName = opStepClassName;
	}

	/**
	 * Returns the opStepParameter.
	 * @return ArrayList
	 */
	public ArrayList getOpStepParameter() {
		return opStepParameter;
	}

	/**
	 * Sets the opStepParameter.
	 * @param opStepParameter The opStepParameter to set
	 */
	public void setOpStepParameter(ArrayList opStepParameter) {
		this.opStepParameter = opStepParameter;
	}

}
