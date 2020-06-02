package com.ibm.utils;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.rmi.UnexpectedException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.*;

import javax.wsdl.Message;
import javax.wsdl.Part;
import javax.xml.namespace.QName;

import org.apache.wsif.WSIFException;
import org.apache.wsif.WSIFMessage;
import org.apache.wsif.util.WSIFUtils;

import com.ibm.APIExerciser;
import com.ibm.bpe.api.*;
import com.ibm.bpe.database.ActivityMessageInstance;

/**
 * @author Søren Kristiansen, IBM Denmark A/S
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class WSDLHelper {
	private static final QName TYPE_XSD_LONG = new QName("http://www.w3.org/2001/XMLSchema", "long");
	private static final QName TYPE_XSD_DECIMAL = new QName("http://www.w3.org/2001/XMLSchema", "decimal");
	private static final QName TYPE_XSD_FLOAT = new QName("http://www.w3.org/2001/XMLSchema", "float");
	private static final QName TYPE_XSD_SHORT = new QName("http://www.w3.org/2001/XMLSchema", "short");
	private static final QName TYPE_XSD_INTEGER = new QName("http://www.w3.org/2001/XMLSchema", "integer");
	private static final QName TYPE_XSD_STRING = new QName("http://www.w3.org/2001/XMLSchema", "string");
	private static final QName TYPE_XSD_INT = new QName("http://www.w3.org/2001/XMLSchema", "int");
	private static final QName TYPE_XSD_DOUBLE = new QName("http://www.w3.org/2001/XMLSchema", "double");
	private static final QName TYPE_XSD_BOOLEAN = new QName("http://www.w3.org/2001/XMLSchema", "boolean");
	private static final QName TYPE_XSD_BYTE = new QName("http://www.w3.org/2001/XMLSchema", "byte");
	private static final QName TYPE_XSD_DATETIME = new QName("http://www.w3.org/2001/XMLSchema", "dateTime");
	private static final QName TYPE_XSD_TIMESTAMP = new QName("http://www.w3.org/2001/XMLSchema", "timestamp (yyyy-mm-dd hh:mm:ss.fffffffff)");	

	public static void printMessage(WSIFMessage theMessage) throws Exception {
		APIExerciser.println_s("======= Message name: " + theMessage.getName());
		Iterator iter = theMessage.getPartNames();
		while (iter.hasNext()) {
			String partName = (String) iter.next();
			Object theData = theMessage.getObjectPart(partName);
			if (theData != null)
				WSDLHelper.printPart(theData, partName);
		}
		APIExerciser.println_s("======= Message complete");
	}
	public static void printMessage(ActivityMessageInstance theMessage) throws Exception {
		APIExerciser.println_s("======= Printing Activity message instance");
		Object theObject = theMessage.getData();
		if (theObject == null)
			APIExerciser.println_s("No input found");					
		else
			APIExerciser.println_s(theObject.getClass().toString());		

		APIExerciser.println_s("======= Activity message complete");
	}
	
	

	public static String[] convertMessage(WSIFMessage theMessage) throws WSIFException {
		Iterator iter = theMessage.getPartNames();
		// iterate to get number of item (clumsy!!!)
		int i = 0;
		while (iter.hasNext())
		{
			iter.next();
			i++;
		}			
		String[] theMessages = null;
		if (i > 0)
			theMessages = new String[i];
		iter = theMessage.getPartNames();
		int j = 0;
		while (iter.hasNext()) {
			String partName = (String) iter.next();
			Object theData = theMessage.getObjectPart(partName);
			theMessages[j] = partName + ": " + theData.toString();
			j++;
		}
		return theMessages;
	}

	public static void promptForParts(WSIFMessage theMessage) throws Exception {
		Message wsdlDesc = theMessage.getMessageDefinition();
		Map theParts = wsdlDesc.getParts();
		Set partSet = theParts.entrySet();

		Iterator iter = partSet.iterator();
		while (iter.hasNext()) // Get input for all parts
			{
			java.util.Map.Entry entry = (java.util.Map.Entry) iter.next();
			Part aPart = (Part) entry.getValue();
			if (hasSimpleType(aPart))
			{			
				String partName = aPart.getName();
				String partValue = APIExerciser.prompt_s("   Enter input for part '" + partName + "' (" + aPart.getTypeName().getLocalPart() + "): ");
				WSDLHelper.setMessagePart(theMessage, partName, partValue);
			}
			else
				WSDLHelper.setComplexMessagePart(theMessage, aPart);
		}
	}

	
	public static ClientObjectWrapper createMessage(BusinessProcess processEJB, PIID thePIID, String theMessageType) throws Exception{
		ProcessInstanceData theProcessInstance = processEJB.getProcessInstance(thePIID);
		theProcessInstance = processEJB.getProcessInstance(theProcessInstance.getTopLevelProcessInstanceID());
		return processEJB.createMessage(theProcessInstance.getProcessTemplateID(), theMessageType)	;
	}
	

	public static ClientObjectWrapper createInputMessage(BusinessProcess theProcessContainer, String theTemplate) throws Exception {
		ProcessTemplateData templateData = theProcessContainer.getProcessTemplate(theTemplate);

		String inputTypeName = templateData.getInputMessageTypeName();
		return theProcessContainer.createMessage(templateData.getID(), inputTypeName);

	}
	
	public static ClientObjectWrapper createOutputMessage(BusinessProcess theProcessContainer, ActivityInstanceData activityInstance) throws Exception {

		String inputTypeName = activityInstance.getOutputMessageTypeName();
		return theProcessContainer.createMessage(activityInstance.getProcessTemplateID(), inputTypeName);

	}
	
	public static boolean hasSimpleType(Part aPart)
	{
		QName qName = aPart.getTypeName();
		return(	qName.equals(TYPE_XSD_INT) 			|| qName.equals(TYPE_XSD_SHORT) 	||
				qName.equals(TYPE_XSD_INTEGER) 		|| qName.equals(TYPE_XSD_STRING) 	||
				qName.equals(TYPE_XSD_LONG) 		|| qName.equals(TYPE_XSD_FLOAT)		||
				qName.equals(TYPE_XSD_DOUBLE) 		|| qName.equals(TYPE_XSD_BOOLEAN) 	||
				qName.equals(TYPE_XSD_BYTE) 		|| qName.equals(TYPE_XSD_DATETIME)	||
				qName.equals(TYPE_XSD_TIMESTAMP)    || qName.equals(TYPE_XSD_DECIMAL) );
		
	}
	public static boolean hasSimpleType(Object aObject)
	{
		
		return(	(aObject instanceof Integer )   		|| (aObject instanceof Short ) 		||
				(aObject instanceof BigInteger )  		|| (aObject instanceof String )  	||
				(aObject instanceof Long ) 				|| (aObject instanceof Float )		||
				(aObject instanceof Double ) 			|| (aObject instanceof Boolean ) 	||
				(aObject instanceof Byte ) 				|| (aObject instanceof Date )		||
				(aObject instanceof Timestamp ) 		|| (aObject instanceof BigDecimal ) );
		
	}


	public static void setComplexMessagePart(WSIFMessage theMessage, Part aPart) throws Exception {
		Message wsdlDesc = theMessage.getMessageDefinition();
		QName qName = aPart.getTypeName();
		if (qName == null) {
			APIExerciser.println_s("Unable to get typename for the part: " + aPart.getName());
			return;
		}
		String theLocalPart = aPart.getTypeName().getLocalPart();
		if (theLocalPart != null) {
			Class class1 = null;
			String theNamespaceURI = aPart.getTypeName().getNamespaceURI();
			String thePackageName = WSIFUtils.getPackageNameFromNamespaceURI(theNamespaceURI);
			String theClassName = WSIFUtils.getJavaClassNameFromXMLName(theLocalPart);
			String theFullClassName = thePackageName + "." + theClassName;
			Class thePartClass = Class.forName(theFullClassName);
			Object thePartObject = thePartClass.newInstance();
			getPartValue(thePartObject, aPart.getName());
			theMessage.setObjectPart(aPart.getName(), thePartObject);
		}
	}
	
private static void getPartValue(Object thePartObject, String thePartName) throws Exception {
	// Call all 'set' methods on thePartObject
	Method[] theMethods = thePartObject.getClass().getMethods();
	for (int i = 0; i < theMethods.length; i++) {
		Method aMethod = theMethods[i];
		String theMethodName = aMethod.getName();
		if (theMethodName.startsWith("set")) {
			//			thePartName = thePartName + "." + theMethodName.substring(3);
			String completePartName = thePartName+"."+theMethodName.substring(3);
			Object[] theParm = new Object[1];
			// get the input parameter for the 'set' method
			Class aClass[] = aMethod.getParameterTypes();
			QName qname = null;			
			if (aClass[0] != null) {
				String aClassName = aClass[0].getName();
				if (aClassName.equals("java.lang.String"))
					qname = TYPE_XSD_STRING;
				else if (aClassName.equals("int"))
					qname = TYPE_XSD_INT;
				else if (aClassName.equals("long"))
					qname = TYPE_XSD_LONG;
				else if (aClassName.equals("float"))
					qname = TYPE_XSD_FLOAT;
				else if (aClassName.equals("double"))
					qname = TYPE_XSD_DOUBLE;
				else if (aClassName.equals("boolean"))
					qname = TYPE_XSD_BOOLEAN;
				else if (aClassName.equals("short"))
					qname = TYPE_XSD_SHORT;
				else if (aClassName.equals("decimal"))
					qname = TYPE_XSD_DECIMAL;
				else if (aClassName.equals("byte"))
					qname = TYPE_XSD_BYTE;
				else if (aClassName.equals("java.util.Date")) 
					qname = TYPE_XSD_DATETIME;
				else if (aClassName.equals("java.sql.Timestamp")) 
					qname = TYPE_XSD_TIMESTAMP;
				else if (aClassName.equals("java.lang.Integer"))
					qname = TYPE_XSD_INTEGER;
				else if (aClassName.equals("java.math.BigDecimal"))
					qname = TYPE_XSD_DECIMAL;
				if (qname != null) {
					// The 'set' method takes a simple type
					String value = APIExerciser.prompt_s("   Enter input for part '" + completePartName + "' (" + qname.getLocalPart() + "): ");
					if (qname.equals(TYPE_XSD_INT)) {
						theParm[0] = new Integer(value.equals("") ? "0" : value);
					} else if (qname.equals(TYPE_XSD_SHORT)) {
						theParm[0] = new Short(value);
					} else if (qname.equals(TYPE_XSD_INTEGER)) {
						theParm[0] = new BigInteger(value);
					} else if (qname.equals(TYPE_XSD_STRING)) {
						theParm[0] = value;
					} else if (qname.equals(TYPE_XSD_LONG)) {
						theParm[0] = new Long(value);
					} else if (qname.equals(TYPE_XSD_FLOAT)) {
						theParm[0] = new Float(value);
					} else if (qname.equals(TYPE_XSD_DOUBLE)) {
						theParm[0] = new Double(value);
					} else if (qname.equals(TYPE_XSD_BOOLEAN)) {
						boolean flag1 = Boolean.valueOf(value).booleanValue();
						theParm[0] = new Boolean(value);
					} else if (qname.equals(TYPE_XSD_BYTE)) {
						theParm[0] = new Byte(value);
					} else if (qname.equals(TYPE_XSD_DATETIME)) {
						theParm[0] = DateFormat.getDateTimeInstance(3, 3, Locale.getDefault()).parse(value);
					} else if (qname.equals(TYPE_XSD_TIMESTAMP)) {
						if (value.equals(""))
							theParm[0] = new Timestamp(System.currentTimeMillis());
						else
							theParm[0] = Timestamp.valueOf(value);
					} else if (qname.equals(TYPE_XSD_DECIMAL)) {
						theParm[0] = new BigDecimal(value);
					}
				} else {
					Object complexObject = aClass[0].newInstance();
					getPartValue(complexObject, completePartName);
					theParm[0] = complexObject;
				}
				aMethod.invoke(thePartObject, theParm);
			}
		}
	}
}

private static void printPart(Object thePartObject, String thePartName) throws Exception {
	if (thePartObject==null)
	{
		APIExerciser.println_s("   " + thePartName + ": null");
		return;
	}
	if (hasSimpleType(thePartObject)) {
		APIExerciser.println_s("   " + thePartName + ": " + thePartObject.toString());
		return;
	}
	if (thePartObject instanceof Exception){
		APIExerciser.println_s("   " + thePartName + ": ");
		((Exception)thePartObject).printStackTrace();	
		return;
	}
	// Call all 'get' methods on thePartObject
	Method[] theMethods = thePartObject.getClass().getMethods();
	for (int i = 0; i < theMethods.length; i++) {
		Method aMethod = theMethods[i];
		String theMethodName = aMethod.getName();
		if (theMethodName.startsWith("get")&& !theMethodName.equals("getClass")) {
			String completePartName = thePartName + "." + theMethodName.substring(3);
			// invoke the get method
			Object theNextObject = aMethod.invoke(thePartObject, null);
			printPart(theNextObject, completePartName);
		}
	}
}


	public static void setMessagePart(WSIFMessage theMessage, String partName, String value) throws Exception {
		Message wsdlDesc = theMessage.getMessageDefinition();
		Map theParts = wsdlDesc.getParts();
		Part aPart = (Part) theParts.get(partName);
		if (aPart == null) {
			APIExerciser.println_s("Unknown part name: " + partName);
		}
		QName qName = aPart.getTypeName();
		if (qName == null) {
			APIExerciser.println_s("Unable to get typename for the part: " + partName);
			return;
		}
		if (qName.equals(TYPE_XSD_INT)) {
			int i = Integer.parseInt(value);
			theMessage.setIntPart(partName, i);
		} else if (qName.equals(TYPE_XSD_SHORT)) {
			short word0 = Short.parseShort(value);
			theMessage.setShortPart(partName, word0);
		} else if (qName.equals(TYPE_XSD_INTEGER)) {
			BigInteger biginteger = new BigInteger(value);
			theMessage.setObjectPart(partName, biginteger);
		} else if (qName.equals(TYPE_XSD_STRING)) {
			theMessage.setObjectPart(partName, value);
		} else if (qName.equals(TYPE_XSD_LONG)) {
			long l = Long.parseLong(value);
			theMessage.setLongPart(partName, l);
		} else if (qName.equals(TYPE_XSD_FLOAT)) {
			float f = Float.parseFloat(value);
			theMessage.setFloatPart(partName, f);
		} else if (qName.equals(TYPE_XSD_DOUBLE)) {
			double d = Double.parseDouble(value);
			theMessage.setDoublePart(partName, d);
		} else if (qName.equals(TYPE_XSD_BOOLEAN)) {
			boolean flag1 = Boolean.valueOf(value).booleanValue();
			theMessage.setBooleanPart(partName, flag1);
		} else if (qName.equals(TYPE_XSD_BYTE)) {
			byte byte0 = Byte.parseByte(value);
			theMessage.setBytePart(partName, byte0);
		} else if (qName.equals(TYPE_XSD_DATETIME)) {
			Date date = DateFormat.getDateTimeInstance(3, 3, Locale.getDefault()).parse(value);
			theMessage.setObjectPart(partName, date);
		} else if (qName.equals(TYPE_XSD_TIMESTAMP)) {
			Timestamp theTS = Timestamp.valueOf(value);
			theMessage.setObjectPart(partName, theTS);
		} else if (qName.equals(TYPE_XSD_DECIMAL)) {
			BigDecimal bigdecimal = new BigDecimal(value);
			theMessage.setObjectPart(partName, bigdecimal);
		} else {
			APIExerciser.println_s("Unknown typename: " + qName);
			return;
		}
	}
}
