/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: TypeDefinitionExternalizerImpl.java,v 1.1 2005/02/21 15:07:10 baruch Exp $
 */
package com.ness.fw.flower.factory.externalization;

import org.w3c.dom.*;
import java.util.*;
import java.lang.reflect.*;

import com.ness.fw.flower.factory.*;
import com.ness.fw.flower.core.*;
import com.ness.fw.common.externalization.*;
import com.ness.fw.common.logger.*;
import com.ness.fw.common.resources.*;

public class TypeDefinitionExternalizerImpl extends TypeDefinitionExternalizer
{
	public static final String INITIALIZE_METHOD_NAME  =   "initialize";

	private static final String LOGGER_CONTEXT = FlowElementsFactoryImpl.LOGGER_CONTEXT + " TYPE DEF. EXT.";

    private HashMap typesMap;
	private HashMap builders;

	public TypeDefinitionExternalizerImpl(DOMRepository domRepository) throws ExternalizerInitializationException
	{
        DOMList domList = domRepository.getDOMList(ExternalizerConstants.TYPE_DEFINITION_TAG_NAME);
		typesMap = new HashMap();
		builders = new HashMap();

		if (domList != null)
		{
			for (int i = 0; i < domList.getDocumentCount(); i++)
            {
	            processDOMForBuilders(domList.getDocument(i));
            }

			for (int i = 0; i < domList.getDocumentCount(); i++)
			{
				processDOMForTypes(domList.getDocument(i));
			}
		}
	}

	public Class getTypeClass(String typeName) throws ExternalizationException
	{
		return getType(typeName).getTypeClass();
	}

	public ClassContextFieldType getType(String typeName) throws ExternalizationException
	{
		Object res = typesMap.get(typeName);
		if (res == null)
		{
			throw new ExternalizationException("No type definition is exists for type [" + typeName + "]");
		}

		return (ClassContextFieldType) res;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// Private methods
	//
	///////////////////////////////////////////////////////////////////////////////////////////////////
	private void processDOMForTypes(Document document)
	{
		Element rootElement = document.getDocumentElement();
		NodeList nodes = XMLUtil.getElementsByTagName(rootElement, ExternalizerConstants.TYPE_DEFINITION_TAG_NAME);

		for (int i = 0; i < nodes.getLength(); i++)
		{
			Element el = (Element)nodes.item(i);
            processElementForTypes(el);
		}
	}

	private void processDOMForBuilders(Document document)
	{
		Element rootElement = document.getDocumentElement();
		NodeList nodes = XMLUtil.getElementsByTagName(rootElement, ExternalizerConstants.TYPE_DEFINITION_TAG_NAME);

		for (int i = 0; i < nodes.getLength(); i++)
		{
			Element el = (Element)nodes.item(i);
			processElementForBuilders(el);
		}
	}


	private void processElementForBuilders(Element el)
	{
		NodeList buildersList = XMLUtil.getElementsByTagName(el, ExternalizerConstants.TYPE_DEFINITION_BUILDER_DEFINITION_TAG_NAME);

		for (int i = 0; i < buildersList.getLength(); i++)
		{
			String name = null;

			try{
				Element builderElement = (Element) buildersList.item(i);

				name = ExternalizerUtil.getName(builderElement);
				
				if(builders.containsKey(name))
				{
					Logger.warning(LOGGER_CONTEXT, "Unable to initialize builder [" + name + "]. A builder with that name is already defined.");
				}
				else
				{
					ContextFieldBuilder builder = createBuilder(builderElement);
					builders.put(name, builder);
				}
			}catch (Throwable ex)
			{
				Logger.error(LOGGER_CONTEXT, "Unable to initialize type definition builder for [" + name + "]. Continue with other builders. See Exception.");
				Logger.error(LOGGER_CONTEXT, ex);
			}
		}
	}

	private ContextFieldBuilder createBuilder(Element builderElement) throws Exception, IllegalAccessException, ClassNotFoundException, ExternalizerInitializationException
	{
		String className = ExternalizerUtil.getClassName(builderElement);

		ContextFieldBuilder builder = (ContextFieldBuilder) Class.forName(className).newInstance();

		ParameterList parameterList = ExternalizerUtil.createParametersList(builderElement);

		builder.initialize(parameterList);
		return builder;
	}

	private void processElementForTypes(Element el)
	{
		NodeList nodes = XMLUtil.getElementsByTagName(el, ExternalizerConstants.TYPE_DEFINITION_TYPE_TAG_NAME);

		for (int i = 0; i < nodes.getLength(); i++)
		{
			String typeName = null;
			try
			{
				Element typeEl = (Element)nodes.item(i);

				typeName = ExternalizerUtil.getName(typeEl);

				if(typesMap.containsKey(typeName))
				{
					Logger.warning(LOGGER_CONTEXT, "Unable to initialize type definition [" + typeName + "]. A  type definition with that name is already defined.");
				}
				else
				{

					String typeClassName = ExternalizerUtil.getClassName(typeEl);
					Class clazz;
	
					Logger.debug(LOGGER_CONTEXT, "Loading type: type [" + typeName + "] class [" + typeClassName + "]");
					clazz = Class.forName(typeClassName);
	
					ContextFieldBuilderBundle builderBundleList[] = createBuilderBundleList(typeEl, typeName, clazz);
					ContextFieldPrinterBundle printer = createPrinter(typeEl, typeName, clazz);

					boolean xiType = false;
					String xiTypeStr = XMLUtil.getAttribute(typeEl, ExternalizerConstants.TYPE_ATTR_IS_XI_TYPE);
					if (xiTypeStr != null)
					{
						if (xiTypeStr.equals(ExternalizerConstants.TRUE))
						{
							xiType = true;
						}
						else if (! xiTypeStr.equals(ExternalizerConstants.FALSE))
						{
							throw new ExternalizerInitializationException("Illegal " + ExternalizerConstants.TYPE_ATTR_IS_XI_TYPE + " value [" + xiTypeStr + "] is specified for type [" + typeName + "]");
						}
					}
	
					// start baruch 
					String basicXITypeName = null;
					if (xiType)
					{
						basicXITypeName = XMLUtil.getAttribute(typeEl,ExternalizerConstants.TYPE_ATTR_BASIC_XI_TYPE);
						// if basicXIType wasn't declared set it with the type
						if (basicXITypeName == null)
						{
							basicXITypeName = typeName;
						}
					}
						
					
					ClassContextFieldType fieldType = new ClassContextFieldType(clazz, builderBundleList, printer, typeName, xiType, basicXITypeName);
	
					// end baruch
	
					typesMap.put(typeName, fieldType);
				}
			} catch (Throwable ex)
			{
				Logger.error(LOGGER_CONTEXT, "Unable to initialize type definition for [" + typeName + "]. Continue with other type. See Exception.");
				Logger.error(LOGGER_CONTEXT, ex);
			}
		}
	}

	private ContextFieldBuilderBundle[] createBuilderBundleList(Element typeEl, String typeName, Class typeClass) throws ExternalizerInitializationException, ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException
	{
		ArrayList res = new ArrayList();

		NodeList list = XMLUtil.getElementsByTagName(typeEl, ExternalizerConstants.TYPE_DEFINITION_BUILDERS_TAG_NAME);
		if (list.getLength() > 0)
		{
			list = XMLUtil.getElementsByTagName((Element)list.item(0), ExternalizerConstants.TYPE_DEFINITION_BUILDER_TAG_NAME);

			for (int i = 0; i < list.getLength(); i++)
			{
				Element builderElement = (Element) list.item(i);

				String className = ExternalizerUtil.getClassName(builderElement);
				String refName = XMLUtil.getAttribute(builderElement, ExternalizerConstants.BUILDER_DEFINITION_ATTR_BUILDER_TYPE);
				String methodName = XMLUtil.getAttribute(builderElement, ExternalizerConstants.ATTR_METHOD_NAME);
				String typeFromStr = XMLUtil.getAttribute(builderElement, ExternalizerConstants.ATTR_FROM_TYPE);
				Class typeFrom = Class.forName(typeFromStr);

				if (className == null && refName == null)
				{
					throw new ExternalizerInitializationException("No className nor refName specified for builder at type [" + typeName + "]");
				}
				else if (className != null && refName != null)
				{
					throw new ExternalizerInitializationException("Both className and refName specified for builder at type [" + typeName + "]");
				}

				ContextFieldBuilder builder;
				if (className != null)
				{
					builder = (ContextFieldBuilder) Class.forName(className).newInstance();
				}
				else
				{
					builder = (ContextFieldBuilder) builders.get(refName);
					if (builder == null)
					{
						throw new ExternalizerInitializationException("No builder is defined for name [" + refName + "] at type [" + typeName + "]");
					}
				}

				Method builderMethod = builder.getClass().getMethod(methodName, new Class[]{ContextFieldBuilderParams.class, typeClass, typeFrom});

				res.add(new ContextFieldBuilderBundle(builder, builderMethod, typeFrom));
			}
		}

		if (res.size() == 0)
		{
			return null;
		}
		else
		{
			ContextFieldBuilderBundle arr[] = new ContextFieldBuilderBundle[res.size()];
			for (int i = 0; i < arr.length; i++)
			{
				arr [i] = (ContextFieldBuilderBundle) res.get(i);

			}

			return arr;
		}
	}

	private ContextFieldPrinterBundle createPrinter(Element typeEl, String typeName, Class typeClass) throws ExternalizerInitializationException, ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException
	{
		NodeList list = XMLUtil.getElementsByTagName(typeEl, ExternalizerConstants.TYPE_DEFINITION_PRINTER_TAG_NAME);

		if (list.getLength() > 0)
		{
			Element printerElement = (Element) list.item(0);

			String className = ExternalizerUtil.getClassName(printerElement);
			String refName = XMLUtil.getAttribute(printerElement, ExternalizerConstants.BUILDER_DEFINITION_ATTR_BUILDER_TYPE);
			String methodName = XMLUtil.getAttribute(printerElement, ExternalizerConstants.ATTR_METHOD_NAME);

			if (className == null && refName == null)
			{
				throw new ExternalizerInitializationException("No className nor refName specified for printer at type [" + typeName + "]");
			}
			else if (className != null && refName != null)
			{
				throw new ExternalizerInitializationException("Both className and refName specified for printer at type [" + typeName + "]");
			}

			ContextFieldPrinter printer;
			if (className != null)
			{
				printer = (ContextFieldPrinter) Class.forName(className).newInstance();
			}
			else
			{
				printer = (ContextFieldPrinter) builders.get(refName);
				if (printer == null)
				{
					throw new ExternalizerInitializationException("No builder is defined for name [" + refName + "] at type [" + typeName + "]");
				}
			}

			Method printerMethod = printer.getClass().getMethod(methodName, new Class[]{LocalizedResources.class, typeClass, String.class});

			return new ContextFieldPrinterBundle(printer, printerMethod);
		}

		return null;
	}

}
