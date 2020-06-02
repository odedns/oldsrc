/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: ContextExternalizerImpl.java,v 1.2 2005/05/08 12:11:46 yifat Exp $
 */
package com.ness.fw.flower.factory.externalization;

import com.ness.fw.flower.core.*;
import com.ness.fw.flower.factory.*;
import com.ness.fw.flower.util.*;
import com.ness.fw.shared.common.XIData;
import com.ness.fw.common.exceptions.AuthorizationException;
import com.ness.fw.common.exceptions.FatalException;
import com.ness.fw.common.externalization.*;
import com.ness.fw.common.logger.*;
import org.w3c.dom.*;

import java.util.*;
import java.lang.reflect.*;

public class ContextExternalizerImpl extends ContextExternalizer
{
	/**
	 * The logger context
	 */
	private static final String LOGGER_CONTEXT = FlowElementsFactoryImpl.LOGGER_CONTEXT + " CONTEXT EXT.";

	/**
	 * Map of context peers
	 */
	private HashMap peers;
	
	/**
	 * Map of structure definitions
	 */
	private HashMap structures;
	
	/**
	 * Map of contextElementSet definitions
	 */
	private HashMap contextElementSets;

	/**
	 * The system context structure for handling event data.
	 */
	private ContextStructureDefinition eventContextStructure;

	/**
	 * Creates new ContextExternalizerImpl object.
	 * @param domRepository
	 */
	public ContextExternalizerImpl(DOMRepository domRepository)
	{
		//initializing maps
		peers = new HashMap();
		structures = new HashMap();
		contextElementSets = new HashMap();

		//retrieve list of doms that contain structures
        DOMList structureDomList = domRepository.getDOMList(ExternalizerConstants.CONTEXT_STRUCTURE_TAG_NAME);
		//read structures
		if (structureDomList != null)
		{
			//run over list of DOM's
			for (int i = 0; i < structureDomList.getDocumentCount(); i++)
			{
				Document doc = structureDomList.getDocument(i);
				//reading all structures from the DOM
				readStructures(doc);
			}
		}

		//retrieve list of doms that contain context element sets
		DOMList contextElementSetDomList = domRepository.getDOMList(ExternalizerConstants.CONTEXT_ELEMENT_SET_DEFINITION_TAG_NAME);
		//read contextElementSets
		if (contextElementSetDomList != null)
		{
			//run over list of DOM's
			for (int i = 0; i < contextElementSetDomList.getDocumentCount(); i++)
			{
				Document doc = contextElementSetDomList.getDocument(i);
				//reading all contextElementSets from the DOM
				readContextElementSets(doc);
			}
		}

		//create system context structure for handling event data
		createEventContextStructure();

		//retrieve list of doms that contain contexts
		DOMList ContextDomList = domRepository.getDOMList(ExternalizerConstants.CONTEXT_TAG_NAME);

		//read contexts
		if (ContextDomList != null)
		{
			//run over list of DOM's
			for (int i = 0; i < ContextDomList.getDocumentCount(); i++)
			{
				Document doc = ContextDomList.getDocument(i);

				//reading all contexts from the DOM
				readContexts(doc);
			}
		}
	}

	/**
	 * create system context structure for handling event data 
	 */
	private void createEventContextStructure () 
	{
		try
		{
			eventContextStructure = new ContextStructureDefinition(Event.EVENT_DATA_STRUCTURE_FIELD);
			
			ContextFieldType contextFieldType = getContextFieldType("event data fields", XIData.XI_STRING_TYPE_DEF, "event data structure");
			
			eventContextStructure.addStructureField(new ContextStructureField(Event.EVENT_NAME_FIELD, contextFieldType, null));
			eventContextStructure.addStructureField(new ContextStructureField(Event.FLOW_ID_FIELD, contextFieldType, null));
			eventContextStructure.addStructureField(new ContextStructureField(Event.FLOW_STATE_FIELD, contextFieldType, null));
			eventContextStructure.addStructureField(new ContextStructureField(Event.EVENT_EXTRA_PARAMS_FIELD, contextFieldType, null));
		}
		catch (ExternalizerInitializationException e)
		{
			Logger.error(LOGGER_CONTEXT, "Unable to initialize event data structure. Continue with other. See Exception.");
			Logger.error(LOGGER_CONTEXT, e);
		}
	}

	/**
	 * Used to create instance of <code>Context</code>
	 *
	 * @param contextName name of context to create
	 * @param parent The parent context.
	 */
	public Context createContext(String contextName, Context parent) throws ExternalizationException, FlowElementsFactoryException, AuthorizationException
	{
		return createContext(contextName, parent, null);
	}
	
	/**
	 * Used to create instance of <code>Context</code>
	 *
	 * @param contextName name of context to create
	 * @param parent The parent context.
	 * @param dynamicGlobals The DynamicGlobals 
	 */
	public Context createContext(String contextName, Context parent, DynamicGlobals dynamicGlobals) throws ExternalizationException, FlowElementsFactoryException, AuthorizationException
	{
		//retrieve context peer for the name
		ContextPeer contextPeer = (ContextPeer) peers.get(contextName);

		if (contextPeer == null)
		{
            throw new ExternalizationException("No context is exists with name [" + contextName + "]");
		}

		try
		{			
			if (contextPeer.getParentContextName() != null)
			{
				parent = FlowElementsFactory.getInstance().getStaticContext(contextPeer.getParentContextName());
			}
			
			Context firstDynamicContext = parent != null ? parent.getFirstDynamicContext() : null; 

			//creating new context instance
			return new ContextImpl(contextPeer, parent, firstDynamicContext, dynamicGlobals);
		} 
		catch (ContextException ex)
		{
			throw new ExternalizationException("Unable to create context instance", ex);
		}
		catch (FatalException ex)
		{
			throw new ExternalizationException("Unable to create context instance", ex);
		}		
	}

	/**
	 * Used to retrieve list of static contexts. Usually called only once by <code>FlowElementsFactory</code>
	 */
	public ContextList getStaticContextList() throws ContextException, FatalException, AuthorizationException
	{
		ContextList contextList = new ContextList();
		Iterator contextIterator = peers.keySet().iterator();

		//run over context peers map
        while (contextIterator.hasNext())
        {
	        String contextName = (String) contextIterator.next();
	        ContextPeer contextPeer = (ContextPeer) peers.get(contextName);
            if (contextPeer.getContextType() == Context.CONTEXT_TYPE_STATIC)
            {
	            Context ctx = new ContextImpl(contextPeer);

	            contextList.addContext(ctx);
            }
        }

		return contextList;
	}

	/**
	 * copy the elements (fields & servcices) from the contextElementSet into the given context peer.
	 * @param contextPeer The ContextPeer to add the elements.   
	 * @param contextElementSetDefName The contextElementSet definition name to copy the elements.
	 * @param dynamicMetaData The Dynamic context metaData.
	 * @return ContextMetaData The metadata of the ContextElementSet. 
	 */
	public ContextMetaData copyContextElementSetDefinitions (ContextPeer contextPeer, String contextElementSetDefName, ContextMetaData dynamicMetaData) throws ExternalizerInitializationException, ContextException
	{
		ContextMetaData contextElementSetMetaData = (ContextMetaData)contextElementSets.get(contextElementSetDefName);
		if(contextElementSetMetaData == null)
		{
			throw new ExternalizerInitializationException("Unable to load " + ExternalizerConstants.CONTEXT_ELEMENT_SET_TAG_NAME + " [" + contextPeer.getContextName() + "]. The " + ExternalizerConstants.CONTEXT_ELEMENT_SET_TAG_NAME + " [" + contextElementSetDefName + "] does not declared.");
		}
				
		contextPeer.copyMetaDataDefinitions(contextElementSetMetaData, dynamicMetaData);
		return contextElementSetMetaData;
	}

	/**
	 * Returns the ContextFieldType according to given type.
	 * @param fieldName The field name.
	 * @param fieldType The field type. Could be one of the following:
	 * type definition name, Structure name, class name.
	 * @param contextPeer The {@link ContextPeer} to add the field
	 * @return ContextFieldType
	 * @throws ExternalizerInitializationException
	 */
	public ContextFieldType getContextFieldType(String fieldName, String fieldType, String ContextName) throws ExternalizerInitializationException
	{
		ContextFieldType contextFieldType;
		//try to retrieve from list of structures
		ContextStructureDefinition structureDefinition = (ContextStructureDefinition) structures.get(fieldType);
		if (structureDefinition != null)
		{
			contextFieldType = new StructureContextFieldType(structureDefinition, fieldType);
		}
		//try to retrieve field type from types definition
		else
		{								
			try
			{
				contextFieldType = TypeDefinitionExternalizer.getInstance().getType(fieldType);
			} catch (Throwable e)
			{
				try
				{
					// baruch 
					// basicXITtype is null because it is not an xiType
					contextFieldType = new ClassContextFieldType(Class.forName(fieldType), fieldType, false, null);
				} catch (ClassNotFoundException ex)
				{
					throw new ExternalizerInitializationException("Unable to load context with name [" + ContextName + "] problem with field [" + fieldName + "]", ex);
				}
			}
		}
		return contextFieldType;
	}	
	
	/**
	 * Used to create a new field to the context.
	 * @param fieldName The field name
	 * @param contextFieldType The ContextFieldType object. 
	 * @param childAccess The child access of the field. Could be one of the following: READ_ONLY, FULL 
	 * @param defaultValue The default value for the field.
	 * @param caption The caption of the field.
	 * @return ContextField The new ContextField.
	 * @throws ExternalizerInitializationException
	 */
	public ContextField createField(String fieldName, ContextFieldType contextFieldType, int childAccess, String defaultValue, String caption) throws ExternalizerInitializationException
	{
		ContextField contextField = null;
		
		contextField = new ContextField(fieldName, contextFieldType, childAccess, defaultValue, caption);
		
		return contextField;
	}
	
	/**
	 * Used to add a new field dynamically to a structure definition.
	 * @param fieldName The field name
	 * @param fieldType fieldType The field type. Could be one of the following:
	 * type definition name or class name.
	 * @param defaultValue The default value for the field
	 * @param structureDefinition The {@link ContextStructureDefinition} to add the field.
	 * @throws ExternalizerInitializationException
	 */
	public void addFieldToStructureDefinition(String fieldName, String fieldTypeStr, String defaultValue, ContextStructureDefinition structureDefinition) throws ExternalizerInitializationException
	{
		ClassContextFieldType fieldType = null;
		 
		//try to retrieve field type from types definition
		try
		{
			//if type definition is found for the type it will be used. if not assuming that the
			//field type is already a class name
			fieldType = TypeDefinitionExternalizer.getInstance().getType(fieldTypeStr);
		}
		catch (Throwable ex){}

		//if class was not found in the type definition try to create class from the string itself
		try
		{
			if (fieldType == null)
			{
				// baruch
				// basicXITtype is null because it is not an xiType
				fieldType = new ClassContextFieldType(Class.forName(fieldTypeStr), null, null, fieldTypeStr, false, null);
			}
		} catch (ClassNotFoundException ex)
		{
			throw new ExternalizerInitializationException("Unable to load structure with name [" + structureDefinition.getStructureName() + "] problem with field [" + fieldName + "] type", ex);
		}

//		if (defaultValue != null)
//		{
//			checkDefaultValue(fieldType, defaultValue);
//		}

		//add field to structure
		structureDefinition.addStructureField(new ContextStructureField(fieldName, fieldType, defaultValue));
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// Private methods
	//
	///////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Parsing all context element sets in the specific DOM
	 * @param doc
	 */
	private void readContextElementSets(Document doc)
	{
		//root element of document
		Element rootElement = doc.getDocumentElement();

		//list of context element set nodes
		NodeList nodeList = XMLUtil.getElementsByTagName(rootElement, ExternalizerConstants.CONTEXT_ELEMENT_SET_DEFINITION_TAG_NAME);

		//run over list of context nodes
		for (int i = 0; i < nodeList.getLength(); i++)
		{
			//field set element
			Element element = (Element) nodeList.item(i);

			try
			{
				//parsing contextElementSet element
				readContextElementSet(element);
			} catch (Throwable ex)
			{
				Logger.error(LOGGER_CONTEXT, "Unable to initialize context element set. Continue with other. See Exception.");
				Logger.error(LOGGER_CONTEXT, ex);
			}
		}
	}

	/**
	 * Parsing all structures in the specific DOM
	 * @param doc
	 */
	private void readStructures(Document doc)
	{
		//root element of document
		Element rootElement = doc.getDocumentElement();

		//list of context nodes
		NodeList nodeList = XMLUtil.getElementsByTagName(rootElement, ExternalizerConstants.CONTEXT_STRUCTURE_TAG_NAME);

		//run over list of context nodes
		for (int i = 0; i < nodeList.getLength(); i++)
		{
			//context element
			Element element = (Element) nodeList.item(i);

			try
			{
				//parsing structure element
				readStructure(element);
			} catch (Throwable ex)
			{
				Logger.error(LOGGER_CONTEXT, "Unable to initialize structure. Continue with other. See Exception.");
				Logger.error(LOGGER_CONTEXT, ex);
			}
		}
	}

	/**
	 * Parsing all contexts in the specific DOM
	 * @param doc
	 */
	private void readContexts(Document doc)
	{
		//root element of document
        Element rootElement = doc.getDocumentElement();

		//list of context nodes
		NodeList nodeList = XMLUtil.getElementsByTagName(rootElement, ExternalizerConstants.CONTEXT_TAG_NAME);

		//run over list of context nodes
		for (int i = 0; i < nodeList.getLength(); i++)
		{
			//context element
			Element element = (Element) nodeList.item(i);

			try
			{
				//parsing context element
				readContext(element);
			} catch (Throwable ex)
			{
                Logger.error(LOGGER_CONTEXT, "Unable to initialize context. Continue with other. See Exception.");
				Logger.error(LOGGER_CONTEXT, ex);
			}
		}
	}

	/**
	 * Used to parse specific structure definition document element
	 * @param element
	 * @throws ExternalizerInitializationException
	 */
	private void readStructure(Element element) throws ExternalizerInitializationException
	{
		//retrieve context name
		String structureName = ExternalizerUtil.getName(element);

		if(structures.containsKey(structureName))
		{
			Logger.warning(LOGGER_CONTEXT, "Unable to initialize structure [" + structureName + "]. A structure with that name is already defined.");
		}
		else
		{			
			Logger.debug(LOGGER_CONTEXT, "Loading structure with name [" + structureName + "]");
	
			//creating structure definition instance
			ContextStructureDefinition structureDefinition = new ContextStructureDefinition(structureName);
	
			//processing structure fields
			processStructureFields(structureDefinition, element);
	
			structures.put(structureName, structureDefinition);
		}
	}

	/**
	 * retrieve the fields of the structure definition.
	 * @param structureDefinition The ContextStructureDefinition to add the fields.
	 * @param element The structure element.
	 * @throws ExternalizerInitializationException
	 */
	private void processStructureFields(ContextStructureDefinition structureDefinition, Element element) throws ExternalizerInitializationException
	{
		//list of context fields nodes
		NodeList nodeList = XMLUtil.getElementsByTagName(element, ExternalizerConstants.CONTEXT_STRUCTURE_FIELD_TAG_NAME);

		//run over list of fields nodes
		for (int i = 0; i < nodeList.getLength(); i++)
		{
			//specific field element
			Element fieldElement = (Element) nodeList.item(i);

			//retrieve field name
			String fieldName = ExternalizerUtil.getName(fieldElement);

			if(structureDefinition.isContainsField(fieldName))
			{
//				Logger.warning(loggerContext, "Unable to initialize structure [" + structureDefinition.getStructureName() + "]. The field [" + fieldName + "] is already defined.");
				throw new ExternalizerInitializationException ("Unable to load structure [" + structureDefinition.getStructureName() + "]. The field [" + fieldName + "] is already defined.");
			}
			else
			{
				//retrieve field type
				String fieldTypeStr = ExternalizerUtil.getType(fieldElement);
	
				//retrieve default value
				String defaultValue = XMLUtil.getAttribute(fieldElement, ExternalizerConstants.ATTR_DEFAULT_VALUE);
				
				addFieldToStructureDefinition(fieldName, fieldTypeStr, defaultValue, structureDefinition);
			}
		}
	}

	/**
	 * Used to parse specific contextElementSet document element
	 * @param element
	 */
	private void readContextElementSet(Element element) throws ExternalizerInitializationException
	{
		//retrieve field set name
		String name = ExternalizerUtil.getName(element);

		if (name == null)
		{
			throw new ExternalizerInitializationException("Unable to load " + ExternalizerConstants.CONTEXT_ELEMENT_SET_TAG_NAME + " with no name");
		}

		if(contextElementSets.containsKey(name))
		{
			Logger.warning(LOGGER_CONTEXT, "Unable to initialize contextElementSet [" + name + "]. A contextElementSet with that name is already defined.");
		}
		else
		{			
			Logger.debug(LOGGER_CONTEXT, "Loading " + ExternalizerConstants.CONTEXT_ELEMENT_SET_DEFINITION_TAG_NAME + " with name [" + name + "]");
	
			//creating ContextMetaData instance  for the fields
			ContextMetaData contextElementSetMetaData = new ContextMetaData();
	
			try
			{
				//processing fields
				processFields(element, name, contextElementSetMetaData);
				
				//processing services
				processServices(element, name, contextElementSetMetaData);
			}
			catch (ExternalizerInitializationException e)
			{
				throw new ExternalizerInitializationException ("Unable to load " + ExternalizerConstants.CONTEXT_ELEMENT_SET_DEFINITION_TAG_NAME + " [" + name + "]", e);
			}
			
			//add contextElementSet metaData to map of contextElementSets
			contextElementSets.put(name, contextElementSetMetaData);
		}
	}


	/**
	 * Used to parse specific context document element
	 * @param element
	 */
	private void readContext(Element element) throws ExternalizerInitializationException
	{
		//retrieve context name
		String contextName = ExternalizerUtil.getName(element);

		if (contextName == null)
		{
			throw new ExternalizerInitializationException("Unable to load context with no name");
		}

		if(peers.containsKey(contextName))
		{
			Logger.warning(LOGGER_CONTEXT, "Unable to initialize context [" + contextName + "]. A context with that name is already defined.");
		}
		else
		{			
			Logger.debug(LOGGER_CONTEXT, "Loading context with name [" + contextName + "]");
	
			//retrieve parent context name
			String parentContextName = XMLUtil.getAttribute(element, ExternalizerConstants.CONTEXT_ATTR_PARENT);
	
			//retrieve conext type
			int contextType = Context.CONTEXT_TYPE_DYNAMIC;
			String contextTypeStr = ExternalizerUtil.getType(element);
			if(contextTypeStr != null)
			{
				if (contextTypeStr.equals(ExternalizerConstants.CONTEXT_TYPE_STATIC))
				{
					contextType = Context.CONTEXT_TYPE_STATIC;
				}
				else if (!contextTypeStr.equals(ExternalizerConstants.CONTEXT_TYPE_DYNAMIC))
				{
					throw new ExternalizerInitializationException("Illegal context type [" + ExternalizerUtil.getType(element) + "] for context [" + contextName + "]");
				}
			}
	
			//retrieve add on demand attribute
			boolean addOnDemand = false;
			String addOnDemandStr = XMLUtil.getAttribute(element, ExternalizerConstants.CONTEXT_ATTR_ADD_ON_DEMAND);
	        if (addOnDemandStr != null && addOnDemandStr.equals(ExternalizerConstants.TRUE))
	        {
		        addOnDemand = true;
	        }
	
			//creating context peer instance
	        ContextPeer contextPeer = new ContextPeer(contextName, parentContextName, contextType, addOnDemand);
	
			try
			{
				//processing context peer fields
				processFields(element, contextName, contextPeer.getMetaData());
				
				//processing context peer services
				processServices(element, contextName, contextPeer.getMetaData());
				
				//processing context peer field sets
				processContextElementSets(contextPeer, element);
			}
			catch (ExternalizerInitializationException e1)
			{
				throw new ExternalizerInitializationException ("Unable to load context [" + contextName + "].", e1);
			}
	
	
			//add event data structure
			try
			{
				contextPeer.addContextField(new ContextField(Event.EVENT_DATA_STRUCTURE_FIELD, new StructureContextFieldType(eventContextStructure, Event.EVENT_DATA_STRUCTURE_FIELD), ContextField.CHILD_ACCESS_FULL, null, null), null);
			}
			catch (ContextException e)
			{
				throw new ExternalizerInitializationException ("error initializing context", e);
			}
	
			//add peer to map of peers
			peers.put(contextName, contextPeer);
		}
	}

	/**
	 * used to parse ContextElementSets information for specific context
	 * @param contextPeer The specific context peer
	 * @param element The context element
	 * @throws ExternalizerInitializationException
	 */
	private void processContextElementSets(ContextPeer contextPeer, Element element) throws ExternalizerInitializationException
	{
		//list of contextElementSet nodes
		NodeList nodeList = XMLUtil.getElementsByTagName(element, ExternalizerConstants.CONTEXT_ELEMENT_SET_TAG_NAME);

		//run over list of contextElementSet nodes
		for (int i = 0; i < nodeList.getLength(); i++)
		{
			try
			{
				//specific contextElementSet element
				Element fieldElement = (Element) nodeList.item(i);
	
				//retrieve contextElementSet def name
				String defName = XMLUtil.getAttribute(fieldElement, ExternalizerConstants.CONTEXT_ELEMENT_SET_ATTR_DEF_NAME);
				
				copyContextElementSetDefinitions(contextPeer, defName, null);
			}
			catch (ContextException e)
			{
//				Logger.error(loggerContext, "error initializing " + ExternalizerConstants.CONTEXT_ELEMENT_SET_TAG_NAME + " [" + contextPeer.getContextName(), e);
				throw new ExternalizerInitializationException ("error initializing " + ExternalizerConstants.CONTEXT_ELEMENT_SET_TAG_NAME + " [" + contextPeer.getContextName()+ "]", e);
			}
		}
	}

	/**
	 * used to parse services information for specific context / contextElementSet
	 * @param element The context / contextElementSet element
	 * @param contextName The name of the context / contextElementSet
	 * @param contextMetaData The metaData to add the services 
	 */
	private void processServices(Element element, String contextName, ContextMetaData contextMetaData) throws ExternalizerInitializationException
	{
		//list od services nodes
		NodeList nodeList = XMLUtil.getElementsByTagName(element, ExternalizerConstants.CONTEXT_SERVICE_TAG_NAME);

		//run over list of services nodes
		for (int i = 0; i < nodeList.getLength(); i++)
		{
			//specific service element
            Element serviceElement = (Element) nodeList.item(i);

			//retrieve service name
            String serviceName = ExternalizerUtil.getName(serviceElement);

			//retrieve service definition name
			String refName = XMLUtil.getAttribute(serviceElement, ExternalizerConstants.CONTEXT_SERVICE_DEF_NAME);

			if (serviceName == null || refName == null)
			{
				throw new ExternalizerInitializationException("Unable to load service with no name or refName for context [" + contextName + "]");
			}


			//read service parameters list
			ParameterList parameterList = ExternalizerUtil.createParametersList(serviceElement);

			//add service data to context metadata
			contextMetaData.addServiceData(serviceName, refName, parameterList);
		}
	}

	/**
	 * Used to retrieve fields for specific context / contextElementSet
	 * @param element The context / contextElementSet element
	 * @param contextName The name of the context / contextElementSet
	 * @param contextMetaData The metaData to add the fields 
	 */
	private void processFields(Element element, String contextName, ContextMetaData contextMetaData) throws ExternalizerInitializationException
	{
		//list of context fields nodes
		NodeList nodeList = XMLUtil.getElementsByTagName(element, ExternalizerConstants.CONTEXT_FIELD_TAG_NAME);
		
		try
		{

			//run over list of fields nodes
			for (int i = 0; i < nodeList.getLength(); i++)
			{
				//specific field element
				Element fieldElement = (Element) nodeList.item(i);
	
				//retrieve field name
	            String fieldName = ExternalizerUtil.getName(fieldElement);
	
				if(contextMetaData.isContainsField(fieldName))
				{
//					Logger.warning(loggerContext, "Unable to add the field [" + fieldName + "] to the context [" + contextName + "].  A field with that name is already defined.");
					throw new ExternalizerInitializationException ("Unable to load context [" + contextName + "]. The field [" + fieldName + "] is already defined.");
				}
				else
				{			
					//retrieve field type
					String fieldTypeStr = ExternalizerUtil.getType(fieldElement);
		
					//retrieve field's access modifyer
		            String access = XMLUtil.getAttribute(fieldElement, ExternalizerConstants.CONTEXT_ATTR_CHILD_ACCESS);
					int childAccess = ContextField.CHILD_ACCESS_READ_ONLY;
					if (access != null)
					{
						if (access.equals(ExternalizerConstants.CONTEXT_FIELD_ACCESS_READ_ONLY))
						{
							childAccess = ContextField.CHILD_ACCESS_READ_ONLY;
						}
						else if (access.equals(ExternalizerConstants.CONTEXT_FIELD_ACCESS_NONE))
						{
							childAccess = ContextField.CHILD_ACCESS_NONE;
						}
						else if (access.equals(ExternalizerConstants.CONTEXT_FIELD_ACCESS_FULL))
						{
							childAccess = ContextField.CHILD_ACCESS_FULL;
						}
						else
						{
							throw new ExternalizerInitializationException("Invalid field child access [" + access + "] for field [" + fieldName + "]. Unable to read context [" + contextName + "]");
						}
					}
		
					//retrieve default value
					String defaultValue = XMLUtil.getAttribute(fieldElement, ExternalizerConstants.ATTR_DEFAULT_VALUE);
						
					ContextFieldType contextFieldType = getContextFieldType(fieldName, fieldTypeStr, contextName);
					
					contextMetaData.addField(createField(fieldName, contextFieldType, childAccess, defaultValue, null));
				}
			}
		}
		catch (ContextException e)
		{
			throw new ExternalizerInitializationException("error initializing context fields", e);
		}
	}

	/**
	 * Used to check default value - type capability. If object of specified type can not be
	 * constructed using string with default value as defined in XML - exception will be thrown
	 */
	private void checkDefaultValue(ClassContextFieldType fieldType, String defaultValue) throws ExternalizerInitializationException
	{
		try
		{
			fieldType.getTypeClass().getConstructor(new Class[]{String.class}).newInstance(new Object[]{defaultValue});
		}catch (InvocationTargetException ex)
		{
			throw new ExternalizerInitializationException("Default value checking failed for type [" + fieldType + "] value [" + defaultValue +"]", ex.getTargetException());
		} catch (Throwable ex)
		{
			throw new ExternalizerInitializationException("Default value checking failed for type [" + fieldType + "] value [" + defaultValue +"]", ex);
		}
	}
	
}
