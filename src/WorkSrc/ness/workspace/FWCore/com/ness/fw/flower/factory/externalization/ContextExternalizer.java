/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: ContextExternalizer.java,v 1.1 2005/02/21 15:07:09 baruch Exp $
 */
package com.ness.fw.flower.factory.externalization;

import com.ness.fw.common.exceptions.AuthorizationException;
import com.ness.fw.common.exceptions.FatalException;
import com.ness.fw.common.externalization.*;
import com.ness.fw.flower.core.*;
import com.ness.fw.flower.factory.*;
import com.ness.fw.flower.util.DynamicGlobals;

/**
 * Factory used to parse XML and create <code>Context</code> instances
 */
public abstract class ContextExternalizer
{
	/**
	 * Instance of Externalizer
	 */
	private static ContextExternalizer instance;

	/**
	 * Should be called before first usage
	 * @param DOMRepository
	 */
	public static void initialize(DOMRepository domRepository) throws ExternalizerInitializationException
	{
		instance = new ContextExternalizerImpl(domRepository);
	}

	/**
	 * Used to retrieve instance of externalizer
	 */
	public static ContextExternalizer getInstance() throws ExternalizerNotInitializedException
	{
		if (instance == null)
		{
			throw new ExternalizerNotInitializedException("ContextExternalizer not initialized");
		}
		return instance;
	}

	/**
	 * Used to create instance of <code>Context</code>
	 *
	 * @param contextName name of context to create
	 * @param parent The parent context.
	 */
	public abstract Context createContext(String contextName, Context parent) throws ExternalizationException, FlowElementsFactoryException, AuthorizationException;

	/**
	 * Used to create instance of <code>Context</code>
	 *
	 * @param contextName name of context to create
	 * @param parent The parent context.
	 * @param dynamicGlobals The DynamicGlobals 
	 */
	public abstract Context createContext(String contextName, Context parent, DynamicGlobals dynamicGlobals) throws ExternalizationException, FlowElementsFactoryException, AuthorizationException;

	/**
	 * Used to retrieve list of static contexts. Usually called only once by <code>FlowElementsFactory</code>
	 */
	public abstract ContextList getStaticContextList() throws ContextException, FatalException, AuthorizationException;

	/**
	 * Returns the ContextFieldType according to given type.
	 * @param fieldName The field name.
	 * @param fieldType The field type. Could be one of the following:
	 * type definition name, Structure name, class name.
	 * @param contextName The {@link Context} name
	 * @return ContextFieldType
	 * @throws ExternalizerInitializationException
	 */
	public abstract ContextFieldType getContextFieldType(String fieldName, String fieldType, String ContextName) throws ExternalizerInitializationException;

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
	public abstract ContextField createField(String fieldName, ContextFieldType contextFieldType, int childAccess, String defaultValue, String caption) throws ExternalizerInitializationException;

	/**
	 * Used to add a new field dynamically to a structure definition.
	 * @param fieldName The field name
	 * @param fieldType fieldType The field type. Could be one of the following:
	 * type definition name or class name.
	 * @param defaultValue The default value for the field
	 * @param structureDefinition The {@link ContextStructureDefinition} to add the field.
	 * @throws ExternalizerInitializationException
	 */
	public abstract void addFieldToStructureDefinition(String fieldName, String fieldTypeStr, String defaultValue, ContextStructureDefinition structureDefinition) throws ExternalizerInitializationException;

	/**
	 * copy the elements (fields & servcices) from the contextElementSet into the given context peer.
	 * @param contextPeer The ContextPeer to add the elements.   
	 * @param contextElementSetDefName The contextElementSet definition name to copy the elements.
	 * @param dynamicMetaData The Dynamic context metaData.
	 * @return ContextMetaData The metadata of the ContextElementSet. 
	 */
	public abstract ContextMetaData copyContextElementSetDefinitions (ContextPeer contextPeer, String contextElementSetDefName, ContextMetaData dynamicMetaData) throws ExternalizerInitializationException, ContextException;

}
