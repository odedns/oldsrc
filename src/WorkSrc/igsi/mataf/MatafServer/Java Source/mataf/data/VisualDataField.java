package mataf.data;

import java.awt.Color;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.lang.reflect.Proxy;

import javax.swing.JPanel;

import mataf.logger.GLogger;

import com.ibm.dse.base.Constants;
import com.ibm.dse.base.DataField;
import com.ibm.dse.base.ErrorInfo;
import com.ibm.dse.base.JavaExtensions;
import com.ibm.dse.base.Tag;
import com.ibm.dse.base.TagAttribute;
import com.ibm.dse.base.types.DSEType;
import com.ibm.dse.base.types.DSETypeException;
import com.ibm.dse.base.types.ElementState;
import com.ibm.dse.base.types.PropertyDescription;
import com.ibm.dse.gui.Settings;
import com.ibm.jvm.io.FileOutputStream;

/**
 * @author yossid
 * 
 *
 */
public class VisualDataField extends DataField 
								implements Serializable, VisualProperties {

	public static final String RGB_COLOR_DELIM = ",";		

	protected Boolean isVisible 			= Boolean.TRUE;
	protected Boolean isEnabled 			= Boolean.TRUE;
	protected Boolean shouldRequestFocus 	= Boolean.FALSE;
	protected Boolean isRequired 			= Boolean.FALSE;
	protected Boolean errorFromServer 	= Boolean.FALSE;
	protected String errorMessage 		= " ";
	protected Color foreground			= (Color) Settings.getValueAt("mandatoryForegroundColor");

	/**
	 * Constructor for VisualDataField.
	 */
	public VisualDataField() {
		super();
	}

	/**
	 * Constructor for VisualDataField.
	 * @param arg0
	 * @throws IOException
	 */
	public VisualDataField(String arg0) throws IOException {
		super(arg0);
	}

	/**
	 * Returns the hasFocus.
	 * @return boolean
	 */

	/**
	 * @see com.ibm.dse.base.Externalizable#initializeFrom(Tag)
	 */
	public Object initializeFrom(Tag aTag) throws IOException {

		value = null;
		TagAttribute attribute;
		String attName = null;
		Object attValue = null;
		com.ibm.dse.base.Hashtable parameters = new com.ibm.dse.base.Hashtable();
		for (int i = aTag.getAttrList().size(); --i >= 0;) {
			attribute = (TagAttribute) aTag.getAttrList().elementAt(i);
			attName = attribute.getName();
			attValue = attribute.getValue();
			if (attName.equals(Constants.Id))
				setName((String) attValue);
			else if (attName.equals(Constants.Value))
				setValue(attValue);
			else if (attName.equals(Constants.Description))
				setDescription((String) (attValue));
			else if (attName.equals("shouldRequestFocus"))
				setShouldRequestFocus(Boolean.valueOf(attValue.toString()));
			else if (attName.equals("isEnabled"))
				setIsEnabled(Boolean.valueOf(attValue.toString()));
			else if (attName.equals("isVisible"))
				setIsVisible(Boolean.valueOf(attValue.toString()));
			else if (attName.equals("isRequired"))
				setIsRequired(Boolean.valueOf(attValue.toString()));
			else if (attName.equals("foregroundColor"))
				;//setForeground(Color.decode(attValue.toString()));
			else if (attName.equals("inErrorFromServer"))
				setInErrorFromServer(Boolean.valueOf(attValue.toString()));
			else if (attName.equals("errorMessage"))
				setErrorMessage(attValue.toString());
			else {
				parameters.put(attName, attValue);
			}
		}
		
		if(getValue()==null) {
			setValue(new String());
		}
		
		return this;

	}

	/**
	 * Returns the shouldRequestFocus.
	 * @return Boolean
	 */
	public Boolean getShouldRequestFocus() {
		return shouldRequestFocus;
	}
	
	public boolean shouldRequestFocus() {
		return shouldRequestFocus.booleanValue();
	}

	/**
	 * Returns the isEnable.
	 * @return Boolean
	 */
	public Boolean getIsEnabled() {
		return isEnabled;
	}
	
	public boolean isEnabled() {
		return isEnabled.booleanValue();
	}

	/**
	 * Returns the isVisible.
	 * @return Boolean
	 */
	public Boolean getIsVisible() {
		return isVisible;
	}
	
	public boolean isVisible() {
		return isVisible.booleanValue();
	}
	
	public Boolean getIsRequired() {
		return isRequired;
	}
	
	public boolean isRequired() {
		return isRequired.booleanValue();
	}

	/**
	 * Sets the hasFocus.
	 * @param hasFocus The hasFocus to set
	 */
	public void setShouldRequestFocus(Boolean shouldRequestFocus) {
		this.shouldRequestFocus = shouldRequestFocus;
	}

	/**
	 * Sets the isEnabled.
	 * @param isEnabled The isEnabled to set
	 */
	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	/**
	 * Sets the isVisible.
	 * @param isVisible The isVisible to set
	 */
	public void setIsVisible(Boolean isVisible) {
		this.isVisible = new Boolean(isVisible.booleanValue());
	}
	
	public void setIsRequired(Boolean isRequired) {
		this.isRequired = new Boolean(isRequired.booleanValue());
	}

	/**
	 * Returns the foreground.
	 * @return Color
	 */
	public Color getForeground() {
		return foreground;
	}

	/**
	 * Sets the foreground.
	 * @param foreground The foreground to set
	 */
	public void setForeground(Color foreground) {
		this.foreground = foreground;
	}

	/**
	 * Returns the errorMessage.
	 * @return String
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * Returns the inError.
	 * @return Boolean
	 */
	public Boolean getInErrorFromServer() {
		return errorFromServer;
	}
	
	public boolean isInErrorFromServer() {
		return errorFromServer.booleanValue();
	}

	/**
	 * Sets the errorMessage.
	 * @param errorMessage The errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/**
	 * NOTE : IT IS NOT ADVISED TO INVOKE THIS METHOD DIRECTLY.
	 * 			USE setErrorFromServer(String errorMessage) INSTEAD !.
	 * @param inError The inError to set
	 * @deprecated
	 */
	public void setInErrorFromServer(Boolean errorFromServer){
		this.errorFromServer = errorFromServer;
	}
	
	public void removeError() {
		setInErrorFromServer(Boolean.FALSE);
		setShouldRequestFocus(Boolean.FALSE);
		setErrorMessage(" ");
		setForeground(Color.black);
	}
	
	/**
	 * Puts the error message the server is sending to the client,
	 * and update all the relevant properties of the visual field.
	 */
	public void setErrorFromServer(String errorMessage) {
		setInErrorFromServer(Boolean.TRUE);
		//setShouldRequestFocus(Boolean.TRUE); // PENDING : Do we still need it ?
		setErrorMessage(errorMessage);
		setForeground((Color) Settings.getValueAt("errorForegroundColor"));
	}

	/**
	 * @see java.lang.Object#clone()
	 */
	public Object clone() throws CloneNotSupportedException 
	{

		VisualDataField myCloned=new VisualDataField();

		// Can't call for super because it will create instance of DataField
		myCloned=new VisualDataField();
		PropertyDescription descriptor=getDescriptor();
		Object value=getValue();
	 	myCloned.setName(getName());
		myCloned.setDescription(this.description);
		myCloned.setDescriptor(descriptor);
		
		// the next properties are from VisualDataField
		myCloned.setErrorMessage(errorMessage);
		myCloned.setInErrorFromServer(errorFromServer);
		myCloned.setIsEnabled(isEnabled);
		myCloned.setIsRequired(isEnabled);
		myCloned.setIsVisible(isVisible);
		myCloned.setShouldRequestFocus(shouldRequestFocus);
		myCloned.setForeground(foreground);
		
		// clone the State
		if (getState()!=null) myCloned.setState((ElementState)getState().clone());
		// clone the ErrorInfo
		if (getErrorInfo()!=null) myCloned.setErrorInfo((ErrorInfo)getErrorInfo().clone());
		// clone the Hashtable of parameters
		if (parameters != null) myCloned.setParameters((com.ibm.dse.base.Hashtable)((com.ibm.dse.base.Hashtable)this.getParameters().clone()));
		if (value==null) 
		{	myCloned.setValue(null);
			return myCloned;
		}
		else
		{
			// If the descriptor is defined use the descriptor to clone the value
			if (descriptor!=null)
			{
				try
				{
					myCloned.setValue(descriptor.cloneDescriptee(value));
					return myCloned;
				}
				catch (DSETypeException e) 
				{
					GLogger.error(this.getClass(), null, null, e, false);
					throw new CloneNotSupportedException ("DSETypeException trying to clone DataField.");//$NON-NLS-1$
				}
			}
			else // descriptor is not defined
			{
				if ((value instanceof String) | (value instanceof Number))
				{
					myCloned.setValue(value);
					return myCloned;
				}
				else if (value instanceof DataField)
				{
					myCloned.setValue(((DataField)value).clone());
					return myCloned;
				}
				try 
				{
					Class emptyTypes[] = {};
					Object[] emptyArgs={};
					java.lang.reflect.Method cloneMethod =value.getClass().getMethod("clone", emptyTypes);//$NON-NLS-1$
					myCloned.setValue(cloneMethod.invoke(value, emptyArgs));
					return myCloned;			
				}
				catch (Exception e)
				{
					 throw new CloneNotSupportedException("DataField value could not be cloned: "+value);//$NON-NLS-1$
				}
			}
		}
	}

			/**
	 * Validates an object using its property descriptor and returns the validated object.
	 * @param toValidate java.lang.Object
	 * @param convType String
	 * @return java.lang.Object
	 * @exception DSETypeException - If the object has not been successfully validated
	 */
	public Object validate(Object toValidate, String convType) throws com.ibm.dse.base.types.DSETypeException {
		PropertyDescription descriptor=getDescriptor();
		if (descriptor==null) return toValidate;
		else {
			try {
				this.setErrorInfo(null);
				validateState(toValidate);
				if ((getState().isMandatory()==false) && (toValidate==null))
					return toValidate;
				else
					if (DSEType.getShareDescriptors() == true)
					{
						com.ibm.dse.base.Hashtable parameters = getParameters();
						return descriptor.validate(toValidate, convType, false, parameters);
					}
					else  return descriptor.validate(toValidate, convType, false);
			}
			catch (DSETypeException e) {
				ErrorInfo errorInfo=new ErrorInfo(toValidate);
				errorInfo.put(e.getCode(), e.getMessage());
				// iterate throgh nested exceptions
				DSETypeException ne=e;
				boolean moreErrors=true;
				while (moreErrors) {
					try {
						ne=(DSETypeException)ne.getNestedException();
						errorInfo.put(ne.getCode(), ne.getMessage());
					}
					catch (Exception exc) {
						moreErrors=false;
					}
				}
				setErrorInfo(errorInfo);
				throw e;
			} // End catch
		} // End else			
	}
	
	/**
	 * Overrides DataField's writeExternal.
	 */
	public void writeExternal(ObjectOutput s) throws IOException 
	{
		// Parent fields.
		//s.writeObject((value==null) ? value : new String());
		//s.writeObject(getDescription());
		super.writeExternal(s);
		
		s.writeObject(getIsVisible());
		s.writeObject(getIsEnabled());		
		s.writeObject(getShouldRequestFocus());		
		s.writeObject(getIsRequired());		
		s.writeObject(getInErrorFromServer());
		
		s.writeObject(getErrorMessage());
		s.writeObject(getForeground());
	}
	
	/**
	 * Overrides DataField's readExternal.
	 */
	public void readExternal(ObjectInput s) throws IOException, ClassNotFoundException 
	{
		// Parent fields.
		//value = (String)s.readObject();
		//description = (String)s.readObject();
		super.readExternal(s);
		
		setIsVisible((Boolean)s.readObject());
		setIsEnabled((Boolean)s.readObject());
		setShouldRequestFocus((Boolean)s.readObject());
		setIsRequired((Boolean)s.readObject());
		setInErrorFromServer((Boolean)s.readObject());
		
		setErrorMessage((String)s.readObject());
		setForeground((Color)s.readObject());
	}
	
	/**
	 * Returns a visual representation of the DataField.
	 * @return java.lang.String
	 */
	public String toString() {

		String s = com.ibm.dse.base.Constants.Smaller + getTagName() + com.ibm.dse.base.Constants.Blanc;
		
		s = JavaExtensions.addAttribute(s, com.ibm.dse.base.Constants.Id, getName());
		if (getValue() != null)
			s = JavaExtensions.addAttribute(s, com.ibm.dse.base.Constants.Value, com.ibm.dse.base.Constants.A_void + getValue());
		s = JavaExtensions.addAttribute(s, com.ibm.dse.base.Constants.Description, getDescription());		
		s = JavaExtensions.addAttribute(s, "shouldRequestFocusFocus", getShouldRequestFocus().toString());		
		s = JavaExtensions.addAttribute(s, "isEnabled", getIsEnabled().toString());
		s = JavaExtensions.addAttribute(s, "isVisible", getIsVisible().toString());		
		s = JavaExtensions.addAttribute(s, "isRequired", getIsRequired().toString());		
		s = JavaExtensions.addAttribute(s, "foregroundColor", getForeground().toString());		
		s = JavaExtensions.addAttribute(s, "inErrorFromServer", getInErrorFromServer().toString());		
		s = JavaExtensions.addAttribute(s, "errorMessage", getErrorMessage());
		
		s = s + com.ibm.dse.base.Constants.A47 + com.ibm.dse.base.Constants.Bigger;
			
		return s;
	}
	
	public static void main(String[] args) throws Exception
	{
		final String FILENAME = "C:\\vdf.test";
		VisualDataField vdf = new VisualDataField();
		vdf.setErrorFromServer("This is a test error message");
		System.out.println("Going to write : "+vdf.getClass());
		System.out.println("toString() : "+vdf.toString());
		
		java.io.FileOutputStream fos = new java.io.FileOutputStream(FILENAME);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(vdf);
		oos.flush();
		oos.close();
		System.out.println("--- Finished writing object ---");
		
		System.out.println("Attempting to read object");
		VisualDataField newvdf;
		FileInputStream fis = new FileInputStream(FILENAME);
		ObjectInputStream ois = new ObjectInputStream(fis);
		newvdf = (VisualDataField)ois.readObject();
	
		System.out.println("--- Finished reading object ---");
		System.out.println("Read : "+newvdf.getClass());
		System.out.println("toString() : "+newvdf.toString());
	
	}
}
