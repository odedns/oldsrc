package com.ibm.dse.base;

/*_
 * Licensed Materials - Property of IBM
 * Restricted Materials of IBM
 * 5648-D89
 * (C) Copyright IBM Corp. 1998, 2003
 * US Government Users Restricted Rights - Use, duplication or disclosure
 * restricted by GSA ADP Schedule Contract with IBM Corp
 */
import com.ibm.dse.base.types.*;
import java.util.Enumeration;


/**
 * This class contains a collection of elements  
 * of the same class ordered by index.
 * @copyright(c) Copyright IBM Corporation 1998, 2003.
 */
public class IndexedCollection extends DataCollection {
	private static final java.lang.String COPYRIGHT =
		"Licensed Materials - Property of IBM "+//$NON-NLS-1$
		"Restricted Materials of IBM "+//$NON-NLS-1$
		"5648-D89 "+//$NON-NLS-1$
		"(C) Copyright IBM Corp. 1998, 2003 All Rights Reserved. "+//$NON-NLS-1$
		"US Government Users Restricted Rights - Use, duplication or disclosure "+//$NON-NLS-1$
		"restricted by GSA ADP Schedule Contract with IBM Corp ";//$NON-NLS-1$
	protected Vector elements ;
	private DataElement dataElement = null; //used if index size equals zero
	private Tag subTag = null;


public static final String	defaultName		=	"aName";
/**
 * This is the default constructor used to create an IndexedCollection object. 
 */
public IndexedCollection ( ) {
}
/**
 * This constructor creates an <I>IndexedCollection</I> object with a specified name. 
 * @param aString java.lang.String
 */
public IndexedCollection (String aName) throws java.io.IOException {
	setName(aName);
	System.out.println("IndexedCol name = " + aName);
	readExternal();
}
/**
 * Adds an element at the end of the collection.
 * @param aDataElement - The element to be added
 */
public void addElement(DataElement aDataElement) {
	getElements().addElement(aDataElement);
}
public void addElement(String aName, Object aValue, String aType, int aPosition) throws DSEInvalidArgumentException, ClassNotFoundException, InstantiationException, IllegalAccessException{
	if (aPosition>getElements().size())
		throw new DSEInvalidArgumentException(DSEException.harmless,"IColl","Cannot add an element at the position "+
					aPosition+" in the IndexedCollection");
	try{
		DataElement de=null;
		if (getDataElement()==null){
			DataElement de2=null;
			if (aType==null){
				if (aName==null || aName.equals(Constants.A_void)){
					de2=new DataField();
					if (aValue!=null) de2.setValue(aValue);
					de2.setName(IndexedCollection.defaultName);
				}else {
					de2=new KeyedCollection();
					de2.setName(aName);
					((KeyedCollection)de2).setDynamic(true);
				}
			}else {
				de2=(DataElement)Class.forName(aType).newInstance();
				if (aName!=null) de2.setName(aName);
				else de2.setName(Constants.A_void);
				if (aValue!=null) de2.setValue(aValue);
				if (de2 instanceof KeyedCollection) ((KeyedCollection)de2).setDynamic(true);
			}
			setDataElement(de2);
		}
		de=this.tryGetElementAt(aPosition);
		if (de == null){			
			de=(DataElement)getDataElement().clone();
			if (de instanceof KeyedCollection) ((KeyedCollection)de).setDynamic(true);
			if (de!=null) addElement(de);
		}
		if ((de instanceof KeyedCollection) && (aName!=null) && (aName.length()>0)){
			try{
				((KeyedCollection)de).getValueAt(aName);
				if (aValue!=null) {
					if (((KeyedCollection)de).trySetValueAt(aName,aValue) == -1){
						((KeyedCollection)getDataElement()).addElement(aName,aValue,Constants.A_void,aType);
						for (int h=0;h<getElements().size();h++)
							((KeyedCollection)getElementAt(h)).addElement(aName,aValue,Constants.A_void,aType);
					}
				}
			}catch (DSEObjectNotFoundException ex){
				((KeyedCollection)getDataElement()).addElement(aName,aValue,Constants.A_void,aType);
				for (int h=0;h<getElements().size();h++)
					((KeyedCollection)getElementAt(h)).addElement(aName,aValue,Constants.A_void,aType);
			}
		}else {
			if ((de!=null) && (aValue!=null)) de.setValue(aValue);
		}
	}catch (Exception e){
		throw new DSEInvalidArgumentException(DSEException.harmless,"IColl","An exception has ocurred while adding an element to an IndexedCollection "+e.toString());
	}
}
/**
 * Returns a new IndexedCollection cloned from the current one.
 * @return com.ibm.dse.base.DataElement - The new indexed collection
 */
public Object clone() throws java.lang.CloneNotSupportedException
{
	PropertyDescription descriptor=getDescriptor();
	if (descriptor!=null)
	{
		try
		{
			return ((IndexedCollection)descriptor.cloneDescriptee(this));
		}
		catch (DSETypeException e) 
		{
			e.printStackTrace();
			throw new CloneNotSupportedException ("DSETypeException trying to clone indexed collection");
		}
	}
	else
	{
		IndexedCollection cloned = new IndexedCollection();
		cloned.setName(getName());
		cloned.setDescription(this.description);
		cloned.setDescriptor(descriptor);
		cloned.setDataElement((DataElement)getDataElement().clone());
		cloned.setElementSubTag(subTag);
	    // clone the State
        if (getState()!=null) cloned.setState((ElementState)getState().clone());
		if (parameters != null) cloned.setParameters((com.ibm.dse.base.Hashtable)((com.ibm.dse.base.Hashtable)this.getParameters().clone()));
		java.util.Enumeration elements=getEnumeration();
		while (elements.hasMoreElements())
		{
			DataElement element=(DataElement)elements.nextElement();
			DataElement clonedElement=(DataElement)element.clone();
			cloned.addElement(clonedElement);
		}
		return cloned;
	}
}
/**
 * Returns a new DataElement to be inserted in the indexed collection that can share the same descriptors of the existing elements.
 * @param shareDescriptor - True if the descriptors can be shared
 * @return com.ibm.dse.base.DataElement - The new DataElement
 * @exception DSEInvalidRequestException
 */
public DataElement createElement(boolean shareDescriptor) throws DSEInvalidRequestException {


	DataElement aDataElement = null;
	try {
		if ((shareDescriptor)&&(size()>0))
		{
			return createElementSharingDescriptors(getElementAt(0));
		}
		else
		{
		if (getDescriptor() == null) {
			aDataElement = (DataElement) DataElement.getExternalizer().convertTagToObject(getElementSubTag());
		}
		else {
			PropertyDescription pd = getDescriptor().getType().getPropertyDescriptor(0);
			aDataElement = (DataElement) pd.getType().createInstance(pd);
			}
		}
	} catch (Throwable t) {
		throw new DSEInvalidRequestException(
			DSEException.harmless, 
			this.getName(), 
			"Exception instantiating indexedCollection elements. IColl name : " + getName() + ". Exception catched : " + t.getMessage()); 
	}


	return aDataElement;



}
/**
 * Creates a new DataElement that shares the same descriptor than the DataElement passed.
 * @param sourceDataElement 
 * @return com.ibm.dse.base.DataElement - The new DataElement
 * @exception DSEInvalidRequestException 
 */
public DataElement createElementSharingDescriptors(DataElement sourceDataElement) throws DSEInvalidRequestException {


	DataElement newDataElement = null;
	try
	{


		if (sourceDataElement instanceof DataField)
		{
			DataField newDataField = new DataField();
			newDataField.setValue(null);
			newDataElement = newDataField;
		}
		else
		{
			if(sourceDataElement instanceof IndexedCollection)
			{
			IndexedCollection iColl = (IndexedCollection)sourceDataElement;
			IndexedCollection newIcoll = new IndexedCollection();
			newIcoll.setDataElement(iColl.getDataElement());
			newIcoll.setElementSubTag(iColl.getElementSubTag());
			newDataElement = newIcoll;
			}
			else
			{
				if(sourceDataElement instanceof KeyedCollection)
				{
					KeyedCollection newKColl = new KeyedCollection();
					KeyedCollection sourceKColl = (KeyedCollection) sourceDataElement;
					int size = sourceKColl.size();
					for (int i = 0; i < size; i++){
						newKColl.addElement(createElementSharingDescriptors(sourceKColl.getElementAt(i)));
					}
					newDataElement = newKColl;
				}
				else {throw new Exception("Unexpected data element type");}
			}
		}
	}
	
	catch (Throwable t) {
		throw new DSEInvalidRequestException(
			DSEException.harmless, 
			this.getName(), 
			"Exception instantiating indexedCollection elements. IColl name : " + getName() + ". Exception catched : " + t.getMessage()); 
	}


	newDataElement.setDescription(sourceDataElement.getDescription());
	newDataElement.setDescriptor(sourceDataElement.getDescriptor());
	newDataElement.setName(sourceDataElement.getName());
	if (sourceDataElement.getState() != null)
	   	newDataElement.setState((ElementState)sourceDataElement.getState().clone());	
	return newDataElement;



}
/**
 * Gets the dataElement defined if the indexed collection size is zero.
 * @return com.ibm.dse.base.DataElement
 */
public DataElement getDataElement() {
	return dataElement;
}
/**
 * Returns the element located at the index <I>anIdentifier</I>.
 * @param anIndentifier int
 * @param anIndent int
 */
public DataElement getElementAt(int anIdentifier) throws DSEObjectNotFoundException{
	try {return (DataElement) getElements().elementAt(anIdentifier);}
	catch (Throwable e) 
		{ throw new DSEObjectNotFoundException(DSEException.
			harmless,
			(Integer.toString(anIdentifier)),
			"Indexed collection " +getName() + " does not have any element at index "+ anIdentifier);
		} 
}
/**
 * Performs a recursive search of the element. If it is the last element in the path, 
 * the method calls the getElementAt(int) method.  
 * @return DataElement
 * @param anIdentifier java.lang.String
 */
public DataElement getElementAt(String anIdentifier) throws DSEObjectNotFoundException{
	DataElement de = null;
	if ((de = tryGetElementAt(anIdentifier)) == null)
		throw new DSEObjectNotFoundException(DSEException.
			harmless,
			anIdentifier,
			(new StringBuffer(128)).append("Indexed collection ").append(getName()).append( " does not have any element at index ").append(anIdentifier).toString());
	else return de;
	
}
/**
 * Returns the value of elements.
 * @return java.util.Vector
 */
public java.util.Vector getElements() {
	if ( elements == null ) elements= new Vector();
	return elements;
}
/**
 * Sets the data element. 
 * @param aDataElement com.ibm.dse.base.DataElement
 */
public Tag getElementSubTag() 
{
	return subTag;
}
/**
 * Returns the IndexedCollection's elements as an Enumeration. 
 * @return java.util.Enumeration
 */
public java.util.Enumeration getEnumeration() {
	return getElements().elements();
}
/**
 * Returns the Indexed Collection's elements. This method is implemented to provide polymorphism. 
 * @return java.lang.Object
 */
public Object getValue() {
	return getElements();
}
/**
 * Initializes an IndexedCollection with the attributes in aTag.
 * @return Externalizable
 * @param aTag Externalization.Tag
 */
public Object initializeFrom(Tag aTag) throws java.io.IOException {
	description=com.ibm.dse.base.Constants.A_void;
	int size=1;
	TagAttribute attribute;
	String name ;
	String value ;
	boolean  createHashtable = false;
	com.ibm.dse.base.Hashtable parameters = new com.ibm.dse.base.Hashtable();
	for (int i=aTag.getAttrList().size();--i >= 0;){
		attribute = (TagAttribute)aTag.getAttrList().elementAt(i); 
		name = attribute.getName();
		value = (String)attribute.getValue();
		if (name.equals(com.ibm.dse.base.Constants.Id)) setName(value);
		else if (name.equals(com.ibm.dse.base.Constants.Description)) setDescription(value);
	  	else if (name.equals(com.ibm.dse.base.Constants.Size)) {
			size = Integer.parseInt(value);
			if (size<size()) getElements().setSize(size);
		}else
			{
			 createHashtable = true;
			 parameters.put(name, value);
			}	
	}


	Tag aSubTag = null;
	
	int nTags = aTag.getSubTags().size();
	
	for (int index=0 ; index < nTags ; index ++)
	{
		aSubTag = (Tag) aTag.getSubTags().elementAt(index);
		
		if (aSubTag!=null) 
		{
			if(aSubTag.getName().equals(com.ibm.dse.base.types.Constants.param)) 
				{
					String paramName = null;
					String paramValue = null;
					java.util.Enumeration subTagAttributes = aSubTag.getAttrList().elements();	
					while (subTagAttributes.hasMoreElements()) 
					{
						TagAttribute subTagAttribute = (TagAttribute) subTagAttributes.nextElement();
						if (subTagAttribute.getName().equals(com.ibm.dse.base.types.Constants.id)) 
							paramName = (String)subTagAttribute.getValue();
						else if (subTagAttribute.getName().equals(com.ibm.dse.base.types.Constants.value))
							paramValue = (String)subTagAttribute.getValue();
					}
					if ((paramName!=null) && (paramValue!=null))
					{
						createHashtable = true;
						parameters.put(paramName, paramValue);
					}
				}
			else
				{		
					setDataElement((DataElement)getExternalizer().convertTagToObject(aSubTag));
					setElementSubTag(aSubTag);
					if (size != 0)
					{
						// An indexed collection is made by a unique DataElement repeated several times.
						for (int i=size();i<size;i++) 
						{
							// The DataElement will be added as many times as the size attribute specifies it.
							addElement((DataElement)getExternalizer().convertTagToObject(aSubTag));
						}
					}
				}
		}
	}
	if (createHashtable == true) this.setParameters(parameters);
	return this;
}
/**
 * Adds as qualified name all elements of this IndexedCollection to the Hashtable passed by parameter.
 * Each entry has as a key the qualified name of the DataElement and as value the DataElement object.
 * @param theTable java.util.Hashtable
 * @param Path String - The path where the IndexedCollection is
 */
public void nestedQualifiedElements(java.util.Hashtable theTable, String Path) {
	DataElement anElement = null;
	int position = 0;	
	String qualifiedName = null;	


	if (Path==null || Path==Constants.A_void) Path = Constants.A_void;
	else Path = Path+Constants.dot;
		
	for (Enumeration e = getEnumeration(); e.hasMoreElements();)
	{ 
		anElement = (DataElement) e.nextElement();
		qualifiedName = Path+position;		
		theTable.put(qualifiedName,anElement);
		if (!(anElement instanceof DataField))
			((DataCollection)anElement).nestedQualifiedElements(theTable,qualifiedName);
			
		position++;				
	}			 		
}
/**
 * Invokes the object creation from an ObjectInput.
 * @param s java.io.ObjectInput
 * @exception java.io.IOException.
 * @exception java.lang.ClassNotFoundException.
 */
public void readExternal(java.io.ObjectInput s) throws java.io.IOException, java.lang.ClassNotFoundException {


	super.readExternal(s);


	// dataElement
	// dataElement = (DataElement)s.readObject();	
	Object o = s.readObject();
	System.out.println("o = " + o.getClass().getName());
	dataElement = (DataElement) o;
	
	//subTag
	if (s.readInt() == 1) {
		subTag = new Tag();
		subTag.readExternal(s);
	}
	
	
	// elements
	int items = s.readInt();
	if (items >0){
		elements = new Vector(items);
		DataElement aDataElement;
		try {
			for (int i = 0; i < items; i++){
				aDataElement= createElement(true);
				aDataElement.setValue(s.readObject());
				elements.addElement(aDataElement);
			}
		} catch (DSEException e){
			if( Trace.doTrace(Constants.COMPID,Trace.Medium,Trace.Error) ) {
				Trace.trace(Constants.COMPID,Trace.Medium,Trace.Error,Settings.getTID(),"Error creating indexedCollection element: "+e.getMessage());
			}
		
		}
	}	


}
/**
 * Removes all elements.
 */
public void removeAll() {
	getElements().removeAllElements();
}
/**
 * Remove the element at the index marked by anIndex.
 * @return DataElement
 * @param anIndex int
 */
public DataElement removeElementAt(int anIndex) throws DSEObjectNotFoundException{
	DataElement deletedElement = (DataElement) getElements().elementAt(anIndex);
	if (deletedElement == null)
		{ throw new DSEObjectNotFoundException(DSEException.harmless, 
			Integer.toString(anIndex),
			"Indexed collection " +getName() + " does not have any element at index "+ anIndex+
			" and therefore can not be removed.");
		} 	
	getElements().removeElementAt(anIndex);
	return deletedElement;
}
/**
 * Performs a recursive search for the element. If it is the last element in the path, 
 * this method calls the removeElementAt(int) method. 
 * @return DataElement
 * @param anIdentifier java.lang.String
 */
public DataElement removeElementAt(String anIdentifier) throws DSEObjectNotFoundException{
	try 
		{ int index = anIdentifier.indexOf('.');
			if (index == -1) { return removeElementAt(Integer.parseInt(anIdentifier,10));}
			DataElement anElement = getElementAt(Integer.parseInt(anIdentifier.substring(0,index),10));
			return anElement.removeElementAt(anIdentifier.substring(index + 1,anIdentifier.length())); 
		}
	catch (java.lang.NumberFormatException e)	
		{ throw new DSEObjectNotFoundException(DSEException.harmless,anIdentifier,
			"Indexed collection " +getName() + " does not have any element named "+ anIdentifier+
			" and therefore can not be removed.");
		}
}
/**
 * Sets the data element.
 * @param aDataElement com.ibm.dse.base.DataElement
 */
public void setDataElement(DataElement aDataElement) 
{
	dataElement=aDataElement;
}
/**
 * Sets the value of elements.
 * @param aVector java.util.Vector
 */
public void setElements(Vector aVector) {
	elements = aVector;
}
/**
 * Sets the data element. 
 * @param aDataElement com.ibm.dse.base.DataElement
 */
public void setElementSubTag(Tag aSubTag) 
{
	subTag=aSubTag;
}
/**
 * Validates the value to be set and if it succeeds then sets the value to the IndexedCollection from a Vector of 
 * DataElements.
 * The elements attribute will contain a cloned Vector from the Vector passed by parameter. 
 * The subTag attribute will contain a Tag Object constructed from "toString()" representation of first vector's element. 
 * The DataElement attribute will contain a new DataElement build from the subTag attribute. 
 * @param aValue java.lang.Object - Must be a Vector of DataElements
 * @exception com.ibm.dse.base.DSEInvalidArgumentException - The parameter is not a Vector
 */
public void setValue(Object aValue) throws DSEInvalidArgumentException {
	
	if (aValue instanceof Vector)
	{
	 try
	 {
		 validate(aValue);
	 }
	 catch(DSETypeException e)
	 {
		 throw new DSEInvalidArgumentException(DSEException.harmless,"Invalid value for indexed collection","DSETypeException trying to validate the value "+aValue+": "+e);
	 }
	 Vector aVector = (Vector)((Vector)aValue).clone();


	 if (aVector.size() == 0)
	 {
		 elements = null;
		 dataElement = null;
		 subTag = null;
		 return;
	 }
	 
	 DataElement aDataElement = (DataElement)aVector.elementAt(0);
	 
	 removeAll();
	 setElements(aVector);


	 if(getDescriptor() == null)
	 {
		 //We build the Tag Object from the toString representation of the first vector's DataElement.
		 String tagString = aDataElement.toString();


			 //We transform, to work properly with default externalizers, the toString representation from XML to SGML
		 int from=0;
		 while( (from = tagString.indexOf("/>",from)) > 0)
		 {
			 tagString=tagString.substring(0,from)+tagString.substring(from+1);
		 }
		 


		 java.io.BufferedInputStream streamTagString = new java.io.BufferedInputStream(new java.io.ByteArrayInputStream(tagString.getBytes()));
		 try 
		 {
		  boolean oldShowProgressBar = Settings.showProgressBar;
		  Settings.showProgressBar(false);
		  TagInputStream aTagInputStream = new TagInputStream(streamTagString,getExternalizer().TYPE_DATA);
		  Settings.showProgressBar(oldShowProgressBar);
		  setElementSubTag((Tag)aTagInputStream.getTags().elementAt(0));
		  
		  //We create a new DataElement from obtained Tag, and sets the value to DataElement attribute.
		  setDataElement((DataElement)getExternalizer().convertTagToObject(getElementSubTag()));
	  	 }
		 catch (java.io.IOException e) 
		 {
			if( Trace.doTrace(Constants.COMPID,Trace.High,Trace.Error) )
			{
				String aMessage =  "An IOException has occurred in setValue(Object)::IndexedCollection";
				String aTID = com.ibm.dse.base.Settings.getTID();
				Trace.trace(Constants.COMPID,Trace.High,Trace.Error,aTID,aMessage);
			}		 
		 }
	 }
	}
	else super.setValue(aValue);
}
/**
 * Returns the number of elements.
 * @return int
 */
public int size() {
	return getElements().size();
}
/**
 * Returns a visual representation of this data.
 * @return String
 */
public String toString() {
	return toStrings().toOneString();
}
/**
 * Returns a Vector containing a visual representation of this data. 
 * @return Vector
 */
public Vector toStrings() {
	String tagName = (String)getTagName(); 
	String s = null;
	String lcn = null;
	String shortClassName = null;
	if (tagName == null)
	{lcn = getClass().getName();
	shortClassName = lcn.substring(lcn.lastIndexOf((int)'.')+1);
	s = Constants.Smaller+shortClassName+Constants.Blanc;}
	else
	{s = Constants.Smaller+tagName+Constants.Blanc;}
	if (getName().length()>0) s=s+Constants.Id+Constants.equal+JavaExtensions.doubleQuote(getName())+Constants.Blanc;
	if (size()>1) s = s + Constants.Size+Constants.equal+JavaExtensions.doubleQuote(String.valueOf(size()))+Constants.Blanc;
	if (getDescription().length()>0) s=s+Constants.Description+Constants.equal+JavaExtensions.doubleQuote(getDescription())+Constants.Blanc;
	s = s+Constants.Bigger;
	Vector stringsVector = new Vector();
	stringsVector.addElement(s);
	Enumeration elements = getElements().elements();
	while (elements.hasMoreElements()) {
		stringsVector.add(((Externalizable)elements.nextElement()).toStrings());
	}
	if (tagName == null)
	{stringsVector.addElement(Constants.FinalTag+shortClassName+Constants.Blanc+Constants.Bigger);}
	else
	{stringsVector.addElement(Constants.FinalTag+tagName+Constants.Blanc+Constants.Bigger);}
	
	stringsVector.tabulate();
	return stringsVector;
}
/**
 * Returns a Vector with the indexed collection represented as a Tag.
 * @return Tag
 */
public Vector toTags() throws java.io.IOException {
	String startString = Constants.ICollId+getName();
	if (size()>0) 
		startString = startString + Constants.SizeE+JavaExtensions.doubleQuote(String.valueOf(size()));
	
	startString = startString+Constants.Bigger;
	Tag startTag = new Tag(startString);
	Vector tagsVector = new Vector();
	tagsVector.addElement(startTag);
	Tag endTag = new Tag(Constants.ICollEnd);
	Enumeration elements = getElements().elements();
	if (elements.hasMoreElements()) 
		tagsVector.add(((Externalizable)elements.nextElement()).toTags());
	tagsVector.addElement(endTag);
	return tagsVector;
}
/**
 * Returns the element located at the index anIdentifier.
 * @param anIndentifier int
 * @param anIndent int
 */
public DataElement tryGetElementAt(int anIdentifier) {
	try {return (DataElement) getElements().elementAt(anIdentifier);}
	catch (Throwable e) 
		{ 
			return null;
		} 
}
/**
 * Performs a recursive search of the element. If it is the last element in the path, 
 * the method calls the getElementAt(int) method.  
 * @return DataElement
 * @param anIdentifier java.lang.String
 */
public DataElement tryGetElementAt(String anIdentifier) {
	int index = anIdentifier.indexOf(Constants.dot);
	if (index == -1) { return tryGetElementAt(Integer.parseInt(anIdentifier,10));}
	DataElement anElement = tryGetElementAt(Integer.parseInt(anIdentifier.substring(0,index),10));
	if (anElement == null) return null;
	return anElement.tryGetElementAt(anIdentifier.substring(index + 1)); 
}
/**
 * Returns the value of an element located in the path aCompositeKey.
 * Returns null if the element is not found or if it is found but has a null value.
 * @return java.lang.Object
 * @param aCompositeKey java.lang.String
 */
public Object tryGetValueAt(String aCompositeKey){
	Object anElement = tryGetElementAt(aCompositeKey);
	if (anElement != null) return tryGetElementAt(aCompositeKey).getValue();
	else return null;
}
/**
 * Sets the value of an element located in the aCompositeKey path.
 * @return int, 0:success; -1:DSEObjectNotFoundException
 * @param aCompositeKey java.lang.String
 */
public int trySetValueAt(String aName,Object aValue) throws DSEInvalidArgumentException{
	
	try
	{
		DataElement anElement = tryGetElementAt(aName);
		if (anElement != null){
			Object validValue=anElement.validate(aValue);
			if (validValue != null) {
				anElement.setValue(validValue);
				return 0;
			}
		}
		
	}
	catch (DSETypeException e)
	{
		 throw new DSEInvalidArgumentException(DSEException.harmless,"Invalid value for data collection","DSETypeException trying to validate the value "+aValue+" for the data name "+aName+": "+e);		 
	}
	return -1;
	
}
/**
 * Validates the object passed (that must be an indexed collection or a vector) and if successful, 
 * the object is returned, otherwise an appropriate exception is thrown. 
 * @param toValidate Object
 * @param descriptor PropertyDescription
 * @exception DSETypeException - Thrown if the validation is unsuccessful
 */
public Object validate(Object toValidate) throws DSETypeException{
	PropertyDescription descriptor=getDescriptor();
		if (descriptor!=null) {
		if (DSEType.getShareDescriptors() == true) 
		{
			com.ibm.dse.base.Hashtable parameters = getParameters();
			return descriptor.validate(toValidate, parameters);
		}
		else return descriptor.validate(toValidate);
	}
	else return toValidate;
}
/**
 * Provides concrete serialization handling for IndexedCollection.
 * @param s java.io.ObjectOutput
 * @exception java.io.IOException
 */
public void writeExternal(java.io.ObjectOutput s) throws java.io.IOException {


	super.writeExternal(s);


	// dataElement			
	s.writeObject(dataElement);


	// subTag
	if (subTag == null)s.writeInt(0);
	else {
		s.writeInt(1);
		subTag.writeExternal(s);
	}


	// elements
	if (elements != null) {
		int items =elements.size();
		s.writeInt(items);
		for (int i = 0; i < items; i++){
			s.writeObject(((DataElement)elements.elementAt(i)).getValue());
		}
	}
	else s.writeInt(-1);
	
}
}
