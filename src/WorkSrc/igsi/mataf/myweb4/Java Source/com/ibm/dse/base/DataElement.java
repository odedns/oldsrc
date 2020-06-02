package com.ibm.dse.base;

/*_
 * Licensed Materials - Property of IBM
 * Restricted Materials of IBM
 * 5648-D89
 * (C) Copyright IBM Corp. 1998, 2003
 * US Government Users Restricted Rights - Use, duplication or disclosure
 * restricted by GSA ADP Schedule Contract with IBM Corp
 */

import com.ibm.dse.base.types.DSEType;
import com.ibm.dse.base.types.DSETypeException;
import com.ibm.dse.base.types.PropertyDescription;
import com.ibm.dse.base.types.Key;
import com.ibm.dse.base.types.ElementState;
import com.ibm.dse.base.types.Validation;
import com.ibm.rmi.io.OptionalDataException;

import java.io.IOException;
/**
 * The DataElement class is the abstract base class to represent data. 
 * @copyright(c) Copyright IBM Corporation 1998, 2003.
 */
public abstract class DataElement implements Externalizable, TypedData, DSECloneable {
	private static final java.lang.String COPYRIGHT =
		"Licensed Materials - Property of IBM "+//$NON-NLS-1$
		"Restricted Materials of IBM "+//$NON-NLS-1$
		"5648-D89 "+//$NON-NLS-1$
		"(C) Copyright IBM Corp. 1998, 2003 All Rights Reserved. "+//$NON-NLS-1$
		"US Government Users Restricted Rights - Use, duplication or disclosure "+//$NON-NLS-1$
		"restricted by GSA ADP Schedule Contract with IBM Corp ";//$NON-NLS-1$
	protected String name = Constants.A_void;
	protected String description=Constants.A_void;
	static Externalizer externalizer = null;
	// reference to the descriptor which provides business rules for this DataElement
	private PropertyDescription descriptor = null;
	// reference to the ErrorInfo which contains all errors detected when validating this data element
	private ErrorInfo errorInfo = null;
	// reference to the ElementState which contains a set of status flags(mandatory, hidden, etc).
	protected ElementState state = null;
	// reference to the Hashtable which has the new settings for the attributes of the default descriptor.
	protected com.ibm.dse.base.Hashtable parameters = null;
/**
 * This method must be implemented by the subclasses.
 * @return java.lang.Object
 */
public abstract Object clone() throws java.lang.CloneNotSupportedException;
/**
 * Returns the externalizer instance of this class.
 * @return com.ibm.dse.base.Externalizer
 */
public Externalizer externalizer() throws java.io.IOException {
	return getExternalizer();
}
/**
 * Returns the description of the data.
 * @return java.lang.String
 */
public String getDescription() {
	
	PropertyDescription descriptor=getDescriptor();
	
	if (descriptor != null)
		return descriptor.getDescription();
	else
	{	
		if (description==null) description=Constants.A_void;
		return description;
	}
}
/**
 * Returns the PropertyDescriptor for this business object.
 * @return PropertyDescription
 */
public PropertyDescription getDescriptor() {
	return descriptor;
}
/**
 * Throws an exception. Implemented in order to provide polymorphism.
 * @param aName java.lang.String
 */
public DataElement getElementAt(String aName) throws DSEObjectNotFoundException{
	throw new DSEObjectNotFoundException(DSEException.harmless,aName,"Invalid Argument: (getElementAt: " + aName + " )");
}
/**
 * Returns the errorInfo property.
 * @return com.ibm.dse.base.ErrorInfo
 */
public ErrorInfo getErrorInfo() {
	return errorInfo;
}
/**
 * Gets the externalizer.
 * @return com.ibm.dse.base.Externalizer
 */
public static Externalizer getExternalizer() throws IOException {
	if (externalizer==null) 
		throw new IOException("Data Elements externalizer not initialized");
	return externalizer;
}
/**
 * Returns a Key object which can be used to identify this business object.
 * For business objects which are not Typed, of simple Type, or simply do not support 
 * unique Keys, a Key with a value of zero is returned.
 * @return Key
 * @exception DSETypeExceotion - If key can not be build from this business object
 */
public Key getKey() throws DSETypeException {
	if (getDescriptor() != null) {
		getDescriptor().getType().getKey(this);
	}
	// not typed
	return null;
}
/**
 * Gets the name property (java.lang.String) value.
 * @return - The name property value
 * @see #setName
 */
public String getName() {
	return name;
}
/**
 * Returns the value of the element in the hashtable of parameters.
 * @param parameterName String
 * @return Object - The element value
 */
public Object getParameter(String parameterName) {
	if (getParameters() == null)
	{
		return null;
	}
	else
	{
		return getParameters().get(parameterName);
	}
}
/**
 * Returns the Hashtable of parameters for this business object.
 * @return com.ibm.dse.base.Hashtable
 */
public com.ibm.dse.base.Hashtable getParameters() {
	return parameters;
}
/**
 * Gets the state property.
 * @return com.ibm.dse.base.types.ElementState
 */
public ElementState getState() {
	return state;
}
/**
 * Gets a tag name.
 * @return java.lang.String
 */
public String getTagName() {
	try {
		return (String)getExternalizer().getTagClassTable().keyAtValue(getClass().getName());
	}
	catch (Exception e) {
		return getClass().getName();
	}		
}
/**
 * Returns the value of the data element. It is an abstract method. 
 * @return java.lang.Object
 */
public abstract Object getValue ( );
/**
 * Returns true if the amended flag is activated and false otherwise.
 * @return boolean
 */
public boolean isAmended() {
	return (getState().isAmended());
}
/**
 * Returns all the DataElements included in a collection at any level.
 * @return com.ibm.dse.base.Vector
 */
public abstract Vector nestedElements();
/**
 * Returns all the DataElements included in this collection at any level.
 * @return java.util.Hashtable
 */
public abstract void nestedElements(java.util.Hashtable theTable) ;
/**
 * Returns all the DataFields at any level included in a collection.
 * @return com.ibm.dse.base.Vector
 */
public abstract Vector nestedFields();
/**
 * Initializes a DataElement with its external value. This instance requires
 * an id (name). The externalizer and its tagInputStream are initialized. 
 */
public void readExternal() throws IOException {
	try {
		Tag aTag = externalizer.getTagInputStream().tagWithId(getName());
		initializeFrom(aTag);
	}	
	catch (Exception e)
	{
		throw new IOException(e.toString());
	}	
}
/**
 * Invokes the object creation from an ObjectInput.
 * @param s java.io.ObjectInput
 * @exception java.io.IOException 
 * @exception java.lang.ClassNotFoundException   
 */
public void readExternal(java.io.ObjectInput s) throws java.io.IOException, java.lang.ClassNotFoundException {
	//name
	name = s.readUTF();
	if((name.length()==1)&&(name.charAt(0)==0)) name=null;
	//description
	description = s.readUTF();
	if((description.length()==1)&&(description.charAt(0)==0)) description=null;
	//descriptor
	descriptor = (PropertyDescription)s.readObject();	
	
	
	//state
	state =(ElementState)s.readObject();	
	
	//parameters
	// Oded fixed Bug.
	// parameters =(com.ibm.dse.base.Hashtable) s.readObject();		
		
		
}
/**
 * Instantiates a DataElement named <I>name</I> from the definition file.
 * @return java.lang.Object
 */
public static Object readObject(String name) throws IOException {
	return getExternalizer().readObject(name);
}
/**
 * Throws an exception. Implemented to provide polymorphism.
 * @return com.ibm.dse.base.DataElement
 * @param aName java.lang.String
 */
public DataElement removeElementAt(String aName) throws DSEObjectNotFoundException{
	throw new DSEObjectNotFoundException(DSEException.harmless,aName,"Invalid Argument: (removeElementAt: " + aName + " )");
}
/**
 * Removes the tags for this object from the definition file
 * in which it is externalized. 
 */
public void removeExternal() throws java.io.IOException{
	getExternalizer().getTagOutputStream().removeTags(toTags());
}
/**
 * Sets the amended flag to <I>aValue</I>.
 * @param aValue boolean
 */
public void setAmended(boolean aValue) 
{
	getState().setAmended(aValue);
}
/**
 * Sets the value of description.
 * @param aDescription java.lang.String
 */
public void setDescription(String aDescription) {
	description = aDescription;
}
/**
 * Sets the PropertyDescriptor for this business object to that passed.
 * @param descriptor PropertyDescription
 */
public void setDescriptor(PropertyDescription descriptor) {
	this.descriptor = descriptor;
}
/**
 * Sets the errorInfo attribute.
 * @return com.ibm.dse.base.ErrorInfo
 */
public void setErrorInfo(ErrorInfo anErrorInfo) 
{
	errorInfo=anErrorInfo;
}
/**
 * Sets the externalizer.
 * @param anExternalizer com.ibm.dse.base.Externalizer
 */
public static void setExternalizer(Externalizer anExternalizer) {
	externalizer = anExternalizer;
}
/**
 * Sets the name property (java.lang.String) value.
 * @param name - The new value for the property
 * @see #getName
 */
public void setName(String aName) {
	name = aName;
}
/**
 * Sets the value of the element in the Hashtable of paramaters.
 * @param parameterName String
 * @param parameterValue Object
 */
public void setParameter(String parameterName, Object parameterValue) {
	if (getParameters() == null)
	{
		com.ibm.dse.base.Hashtable parameters = new com.ibm.dse.base.Hashtable();
		parameters.put(parameterName, parameterValue);
		setParameters(parameters);
	}
	else
	{
		getParameters().put(parameterName, parameterValue);
	}
}
/**
 * Sets the Hashtable of paramaters for this business object.
 * @param setparameters com.ibm.dse.base.Hashtable
 */
public void setParameters(com.ibm.dse.base.Hashtable theParameters) {
	this.parameters = theParameters;
}
/**
 * Sets the state property.
 * @param aState com.ibm.dse.base.types.ElementState
 */
public void setState(ElementState aState) 
{
	state=aState;
}
/**
 * Throws an exception. This method is implemented to maintain polymorphism.
 * @param aValue java.lang.Object
 */
public abstract void setValue(Object aValue) throws DSEInvalidArgumentException;
/**
 * Returns a visual representation of this data element.
 * @return Vector
 */
public Vector toStrings() {
	Vector v = new Vector();
	v.addElement(toString());
	return v;
}
/**
 * Returns a Vector with the DataElement represented as a Tag.
 * @return com.ibm.dse.base.Vector
 */
public Vector toTags() throws java.io.IOException {
	return toStrings().toTags();
}
/**
 * Returns null. Implemented in order to provide polymorphism.
 * @param aName java.lang.String
 */
public DataElement tryGetElementAt(String aName) {
	return null;
}
/**
 * Validates the object using its property descriptor.
 * @param java.lang.Object
 * @return java.lang.Object
 * @exception DSETypeException - If the object has not been successfully validated
 */
public Object validate(Object toValidate) throws com.ibm.dse.base.types.DSETypeException {
	return validate(toValidate,com.ibm.dse.base.types.TypeConstants.CONV_TYPE_DEFAULT);
}
/**
 * This method must be implemented by the subclasses.
 * @param toValidate java.lang.String
 * @param convType java.lang.String
 * @return java.lang.Object
 * @exception DSETypeException
 */
public abstract Object validate(Object toValidate, String convType) throws DSETypeException;
/**
 * Validates an object using its validator directly without conversion. If validator is not defined, the object is returned.
 * @return java.lang.Object - The validated object
 * @exception DSETypeException - If the object has not been successfully validated
 */
public Object validateObject(Object toValidate) throws com.ibm.dse.base.types.DSETypeException {
	PropertyDescription descriptor=getDescriptor();
	if (descriptor==null) return toValidate;
	else
	{
		Validation validator=descriptor.getValidator();
		if (validator==null) return toValidate;
		else if (DSEType.getShareDescriptors() == true) 
		{
			com.ibm.dse.base.Hashtable parameters = getParameters();
			return validator.validate(toValidate, descriptor, parameters);
		}
			else return validator.validate(toValidate, descriptor);
	}		
}
/**
 * Converts the instance into tags and writes the tags into a file. 
 */
public void writeExternal() throws IOException {
	getExternalizer().getTagOutputStream().writeTags(toTags());
}
/**
 * Provides concrete serialization handling for DataElement.
 * @param s java.io.ObjectOutput
 * @exception java.io.IOException 
 */
public void writeExternal(java.io.ObjectOutput s) throws java.io.IOException {
	//name
	if (name != null) s.writeUTF(name);
	else s.writeUTF(Constants.binaryZero);
	//description
	if (description != null) s.writeUTF(description);
	else s.writeUTF(Constants.binaryZero);
	//descriptor
	s.writeObject(descriptor);	
	
	
	//state
	s.writeObject(state);	
	
	//parameters
	if (parameters != null)  s.writeObject(parameters);	
}
}
