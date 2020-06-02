/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: ValidatorExternalizerImpl.java,v 1.1 2005/02/21 15:07:09 baruch Exp $
 */
package com.ness.fw.flower.factory.externalization;

import com.ness.fw.flower.core.*;
import com.ness.fw.flower.factory.*;
import com.ness.fw.common.externalization.*;
import com.ness.fw.common.logger.*;
import org.w3c.dom.*;

import java.util.*;
import java.lang.reflect.*;

public class ValidatorExternalizerImpl extends ValidatorExternalizer
{
	private static final String LOGGER_CONTEXT = FlowElementsFactoryImpl.LOGGER_CONTEXT + " VALIDATOR EXT.";

	private HashMap validatorsMap;
	private HashMap validationCheckDeclarationsMap;
	private HashMap validationChecksMap;
	private HashMap validationCheckSetsMap;

	public ValidatorExternalizerImpl(DOMRepository domRepository)
	{
		//validationCheckDeclaration
		DOMList domList = domRepository.getDOMList(ExternalizerConstants.VALIDATION_CHECK_DEFINITION_TAG_NAME);
		validationCheckDeclarationsMap = new HashMap();
		if (domList != null)
		{
			for (int i = 0; i < domList.getDocumentCount(); i++)
			{
				processDOMForValidationCheckDeclarations(domList.getDocument(i));
			}
		}

		//validationCheck
		domList = domRepository.getDOMList(ExternalizerConstants.VALIDATION_CHECK_TAG_NAME);
		validationChecksMap = new HashMap();
		if (domList != null)
		{
			for (int i = 0; i < domList.getDocumentCount(); i++)
			{
				processDOMForValidationChecks(domList.getDocument(i));
			}
		}

		//validationCheck Sets
		domList = domRepository.getDOMList(ExternalizerConstants.VALIDATION_CHECK_SET_TAG_NAME);
		validationCheckSetsMap = new HashMap();
		if (domList != null)
		{
			for (int i = 0; i < domList.getDocumentCount(); i++)
			{
				processDOMForValidationCheckSets(domList.getDocument(i));
			}
		}


		//validators doms
		domList = domRepository.getDOMList(ExternalizerConstants.VALIDATOR_TAG_NAME);
		validatorsMap = new HashMap();

		if (domList != null)
		{
			for (int i = 0; i < domList.getDocumentCount(); i++)
			{
				processDOMForValidators(domList.getDocument(i));
			}
		}
	}

	public Validator createValidator(String validatorName) throws ExternalizationException
	{
		Validator validator = (Validator) validatorsMap.get(validatorName);
		if (validator == null)
		{
			throw new ExternalizationException("No validator is defined for name [" + validatorName + "]");
		}

		return validator;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// Private methods
	//
	///////////////////////////////////////////////////////////////////////////////////////////////////

	private void processDOMForValidationCheckSets(Document document)
	{
		Element rootElement = document.getDocumentElement();
		NodeList nodes = XMLUtil.getElementsByTagName(rootElement, ExternalizerConstants.VALIDATION_CHECK_SET_TAG_NAME);

		for (int i = 0; i < nodes.getLength(); i++)
		{
			Element el = (Element)nodes.item(i);
			try
			{
				processValidationCheckSetElement(el);
			} catch (Throwable ex)
			{				
				Logger.error(LOGGER_CONTEXT, "Unable to initialize validationCheckSet. Continue with other. See Exception.");
				Logger.error(LOGGER_CONTEXT, ex);
			}
		}
	}

	private void processValidationCheckSetElement(Element el) throws ExternalizerInitializationException
	{
		String name = ExternalizerUtil.getName(el);

		if(validationCheckSetsMap.containsKey(name))
		{
			Logger.warning(LOGGER_CONTEXT, "Unable to initialize validationCheckSet [" + name + "]. A validationCheckSet with that name is already defined.");
		}
		else
		{

			CheckSet checkSet = new CheckSet(name);
	
			NodeList checkNodeList = XMLUtil.getElementsByTagName(el, ExternalizerConstants.CHECK_TAG_NAME);
			for (int i = 0; i < checkNodeList.getLength(); i++)
			{
				Element checkElement = (Element) checkNodeList.item(i);
	
				String refName = XMLUtil.getAttribute(checkElement, ExternalizerConstants.CHECK_ATTR_VALIDATION_CHECK_NAME);
				String defName = XMLUtil.getAttribute(checkElement, ExternalizerConstants.CHECK_ATTR_VALIDATION_CHECK_DEF);
				
				if (refName == null && defName == null)
				{
					throw new ExternalizerInitializationException("Unable to initialize validation check set [" + name + "] one of checks does not declare " + ExternalizerConstants.CHECK_ATTR_VALIDATION_CHECK_NAME + " or " + ExternalizerConstants.CHECK_ATTR_VALIDATION_CHECK_DEF);
				}
	
				String stopOnErrorStr = XMLUtil.getAttribute(checkElement, ExternalizerConstants.ATTR_STOP_ON_ERROR);
				boolean stopOnError = stopOnErrorStr == null ? false : stopOnErrorStr.equals(ExternalizerConstants.TRUE) ? true : false;
	
				if (refName != null)
				{
					ValidationCheckWrapper checkWrapper = (ValidationCheckWrapper)validationChecksMap.get(refName);
					if (checkWrapper == null)
					{
						throw new ExternalizerInitializationException("Unable to initialize validation check set [" + name + "] the check [" + refName + "] does not declared");
					}
					checkSet.addCheck(checkWrapper, stopOnError);
				}
				else
				{
					checkSet.addCheck(createValidationCheckWrapper(checkElement), stopOnError);
				}
	
			}
			validationCheckSetsMap.put(checkSet.getName(), checkSet);
		}
	}

	private void processDOMForValidationChecks(Document document)
	{
		Element rootElement = document.getDocumentElement();
		NodeList nodes = XMLUtil.getElementsByTagName(rootElement, ExternalizerConstants.VALIDATION_CHECK_TAG_NAME);

		for (int i = 0; i < nodes.getLength(); i++)
		{
			Element el = (Element)nodes.item(i);
			try
			{
				processValidationCheckElement(el);
			} catch (Throwable ex)
			{
				Logger.error(LOGGER_CONTEXT, "Unable to initialize validationCheck. Continue with other. See Exception.");
				Logger.error(LOGGER_CONTEXT, ex);
			}
		}
	}

	private void processValidationCheckElement(Element el) throws ExternalizerInitializationException
	{
		if(validationChecksMap.containsKey(ExternalizerUtil.getName(el)))
		{
			Logger.warning(LOGGER_CONTEXT, "Unable to initialize validationCheck [" + ExternalizerUtil.getName(el) + "]. A validationCheck with that name is already defined.");
		}
		else
		{
			ValidationCheckWrapper checkWrapper = createValidationCheckWrapper(el);
			validationChecksMap.put(checkWrapper.getName(), checkWrapper);
		}
	}

	private ValidationCheckWrapper createValidationCheckWrapper(Element el) throws ExternalizerInitializationException
	{
		String name = ExternalizerUtil.getName(el);
		String validationCheckDef = XMLUtil.getAttribute(el, ExternalizerConstants.CHECK_ATTR_VALIDATION_CHECK_DEF);
		ValidationCheckDeclaration checkDeclaration = (ValidationCheckDeclaration) validationCheckDeclarationsMap.get(validationCheckDef);
		if (checkDeclaration == null)
		{
			throw new ExternalizerInitializationException("Unable to initialize validation check [" + name + "] - the validation check declaration for [" + validationCheckDef + "] does not exists");
		}

		ValidationCheckWrapper checkWrapper = new ValidationCheckWrapper(name, checkDeclaration.getValidationCheck(), checkDeclaration.getValidationCheckMethod());

		//field names
		ValidationCheckFieldData fieldDatas[] = new ValidationCheckFieldData[checkDeclaration.getValidationCheckParamDeclarationCount()];

		//read params
		NodeList paramNodeList = XMLUtil.getElementsByTagName(el, ExternalizerConstants.VALIDATION_CHECK_PARAM);
		int paramNodeListLength = paramNodeList.getLength();

	
		//baruch
		String valueType = null;
	
	
		//i for declaration
		//j for nodes
		int j = 0;
		for (int i = 0; i < checkDeclaration.getValidationCheckParamDeclarationCount(); i++, j++)
		{
			ValidationCheckParamDeclaration paramDeclaration = checkDeclaration.getValidationCheckParamDeclaration(i);
			if (j >= paramNodeListLength)
			{
				throw new ExternalizerInitializationException("Parameter [" + paramDeclaration.getName() + "] is not specified for validation check [" + name + "] as was declared at [" + checkDeclaration.getName() + "]");
			}

			if (paramDeclaration.getMultiplicity() != 1)
			{
				ArrayList paramsList = new ArrayList();
				for (int k = 0; k < paramDeclaration.getMultiplicity(); k++, j++)
				{
					if ( j >= paramNodeListLength)
					{
						if (paramDeclaration.getMultiplicity() == ValidationCheckParamDeclaration.MULTIPLICITY_MANY)
						{
							break;
						}
						else
						{
							throw new ExternalizerInitializationException("Parameter [" + paramDeclaration.getName() + "] is not specified as many times for validation check [" + name + "] as was declared by multiplicity [" + paramDeclaration.getMultiplicity() + "] at [" + checkDeclaration.getName() + "].");
						}
					}

					Element paramEl = (Element) paramNodeList.item(j);

					String paramName = ExternalizerUtil.getName(paramEl);
					if (!paramName.equals(paramDeclaration.getName()))
					{
						if (paramDeclaration.getMultiplicity() == ValidationCheckParamDeclaration.MULTIPLICITY_MANY)
						{
							break;
						}
						else
						{
							throw new ExternalizerInitializationException("Parameter [" + paramDeclaration.getName() + "] is not specified for validation check [" + name + "] as was declared at [" + checkDeclaration.getName() + "]. [" + paramName + "] is specified instead");
						}
					}

					String paramValue       = ExternalizerUtil.getValue(paramEl);
					String paramFieldName   = XMLUtil.getAttribute(paramEl, ExternalizerConstants.ATTR_FIELD_NAME);

					// baruch
					valueType = XMLUtil.getAttribute(paramEl, ExternalizerConstants.CHECK_PARAM_ATTR_VALUE_TYPE);
	
					if (paramValue == null && valueType !=null)
					{
						throw new ExternalizerInitializationException("Unable to initialize validation check [" + name + "] - parameter [" + paramName + "] declared value type without value parameter. value type should use only with value and not with field name");						
					}

					if (paramValue == null && paramFieldName == null)
					{
						throw new ExternalizerInitializationException("Unable to initialize validation check [" + name + "] - parameter [" + paramName == null ? "anonymous" : paramName + "] does not declare value nor field name");
					}
					else if (paramValue != null && paramFieldName != null)
					{
						throw new ExternalizerInitializationException("Unable to initialize validation check [" + name + "] - parameter [" + paramName == null ? "anonymous" : paramName + "] can not declare value and field name");
					}

					if (paramValue != null)
					{
						try
						{
														
							paramsList.add(new ValidationCheckWrapper.ValidationParam(paramName, getParamValueConstructor(valueType,paramDeclaration).newInstance(new Object[]{paramValue}), false, false));								

							// paramsList.add(new ValidationCheckWrapper.ValidationParam(paramName, paramDeclaration.getType().newInstance(new Object[]{paramValue}), false, false));

							//end barcuh
						} catch (Throwable ex)
						{
							// barcuh
							//baruch
							String classType = null;
							if (paramDeclaration.getType() != null)
							{
								classType = paramDeclaration.getType().getDeclaringClass().getName();
							}
							else
							{
								classType = valueType;
							}
						
							throw new ExternalizerInitializationException("Parameter [" + paramDeclaration.getName() + "] of type [" +  classType + "] failed to initialize with value [" + paramValue + "]", ex);
												

						//	throw new ExternalizerInitializationException("Parameter [" + paramDeclaration.getName() + "] of type [" +  paramDeclaration.getType().getDeclaringClass().getName() + "] failed to initialize with value [" + paramValue + "]");
						}
					}
					else
					{
						paramsList.add(new ValidationCheckWrapper.ValidationParam(paramName, paramFieldName, true, false));
					}
				}

				checkWrapper.addParam(paramDeclaration.getName(), paramsList, true);
				j--;
			}
			else
			{
				Element paramEl = (Element) paramNodeList.item(j);

				String paramCaption = XMLUtil.getAttribute(paramEl, ExternalizerConstants.ATTR_CAPTION);
				String paramName        = ExternalizerUtil.getName(paramEl);
				if (!paramName.equals(paramDeclaration.getName()))
				{
					throw new ExternalizerInitializationException("Parameter [" + paramDeclaration.getName() + "] is not specified for validation check [" + name + "] as was declared at [" + checkDeclaration.getName() + "]. [" + paramName + "] is specified instead");
				}

				String paramValue       = ExternalizerUtil.getValue(paramEl);
				String paramFieldName   = XMLUtil.getAttribute(paramEl, ExternalizerConstants.ATTR_FIELD_NAME);

				// baruch
				valueType = XMLUtil.getAttribute(paramEl, ExternalizerConstants.CHECK_PARAM_ATTR_VALUE_TYPE);

				if (paramValue == null && valueType !=null)
				{
					throw new ExternalizerInitializationException("Unable to initialize validation check [" + name + "] - parameter [" + paramName + "] declared value type without value parameter. value type should use only with value and not with field name");						
				}
	
				if (paramValue == null && paramFieldName == null)
				{
					throw new ExternalizerInitializationException("Unable to initialize validation check [" + name + "] - parameter [" + paramName == null ? "anonymous" : paramName + "] does not declare value nor field name");
				}

				if (paramValue != null)
				{
					try
					{							
						checkWrapper.addParam(paramName, getParamValueConstructor(valueType,paramDeclaration).newInstance(new Object[]{paramValue}), false);						
						
							// checkWrapper.addParam(paramName, paramDeclaration.getType().newInstance(new Object[]{paramValue}), false);
	
							// end baruch
		
							ValidationCheckFieldData fieldData = new ValidationCheckFieldData(paramName, paramCaption, false);
							fieldDatas[i] = fieldData;
					} 
					catch (Throwable ex)
					{
						//baruch
						String classType = null;
						if (paramDeclaration.getType() != null)
						{
							classType = paramDeclaration.getType().getDeclaringClass().getName();
						}
						else
						{
							classType = valueType;
						}
						
						throw new ExternalizerInitializationException("Parameter [" + paramDeclaration.getName() + "] of type [" +  classType + "] failed to initialize with value [" + paramValue + "]", ex);
//						throw new ExternalizerInitializationException("Parameter [" + paramDeclaration.getName() + "] of type [" +  paramDeclaration.getType().getDeclaringClass().getName() + "] failed to initialize with value [" + paramValue + "]");
					}
				}
				else
				{
					String setFieldErrorStateStr = XMLUtil.getAttribute(paramEl, ExternalizerConstants.ATTR_FIELD_SET_ERROR_STATE);
					boolean setFieldErrorState   = setFieldErrorStateStr != null && setFieldErrorStateStr.equals(ExternalizerConstants.TRUE);

					checkWrapper.addParam(paramName, paramFieldName, true);
					ValidationCheckFieldData fieldData = new ValidationCheckFieldData(paramFieldName, paramCaption, setFieldErrorState);
					fieldDatas[i] = fieldData;

				}
			}
		}

		checkWrapper.setFieldDatas(fieldDatas);

		if (j < paramNodeListLength)
		{
			throw new ExternalizerInitializationException("Unable to initialize validation check [" + name + "] - [" + (paramNodeListLength - j) + "] extra parameters specified");
		}

		return checkWrapper;
	}

	private void processDOMForValidationCheckDeclarations(Document document)
	{
		Element rootElement = document.getDocumentElement();
		NodeList nodes = XMLUtil.getElementsByTagName(rootElement, ExternalizerConstants.VALIDATION_CHECK_DEFINITION_TAG_NAME);

		for (int i = 0; i < nodes.getLength(); i++)
		{
			Element el = (Element)nodes.item(i);
			try
			{
				processValidationCheckDeclarationElement(el);
			} catch (Throwable ex)
			{
				Logger.error(LOGGER_CONTEXT, "Unable to initialize validation check declaration. Continue with other. See Exception.");
				Logger.error(LOGGER_CONTEXT, ex);
			}
		}
	}

	private void processValidationCheckDeclarationElement(Element el) throws Exception
	{
		String name = ExternalizerUtil.getName(el);

		if(validationCheckDeclarationsMap.containsKey(name))
		{
			Logger.warning(LOGGER_CONTEXT, "Unable to initialize validationCheckDeclaration [" + name + "]. A validationCheckDeclaration with that name is already defined.");
		}
		else
		{
			String className = ExternalizerUtil.getClassName(el);
			String methodName = ExternalizerUtil.getMethodName(el);
			ValidationCheck check = (ValidationCheck) Class.forName(className).newInstance();
			ArrayList paramClasses = new ArrayList();
			ValidationCheckDeclaration checkDeclaration = new ValidationCheckDeclaration(name, check);
	
			//process params
			NodeList paramsNodeList = XMLUtil.getElementsByTagName(el, ExternalizerConstants.VALIDATION_CHECK_PARAM_DEFINITION_TAG_NAME);
			for (int i = 0; i < paramsNodeList.getLength(); i++)
			{
				Element paramElement = (Element) paramsNodeList.item(i);
				String paramName = ExternalizerUtil.getName(paramElement);
				String paramType = ExternalizerUtil.getType(paramElement);
				
				// baruch
				String defaultType = XMLUtil.getAttribute(paramElement,ExternalizerConstants.VALIDATION_CHECK_PARAM_ATTR_DEF_TYPE);
				
				Class paramClass;
				try{
					paramClass = TypeDefinitionExternalizer.getInstance().getTypeClass(paramType);
				}catch (ExternalizationException ex)
				{
					paramClass = Class.forName(paramType);
				}
	
				paramClasses.add(paramClass);
				Constructor constructor;
	
				try{
					constructor = paramClass.getConstructor(new Class[]{String.class});
				}catch (Throwable ex)
				{
					constructor = null;
				}
	
				String multStr = XMLUtil.getAttribute(paramElement, ExternalizerConstants.VALIDATION_PARAM_MULTITPICITY);
				int mult = multStr.equals(ExternalizerConstants.VALIDATION_PARAM_MULTITPICITY_MANY) ? ValidationCheckParamDeclaration.MULTIPLICITY_MANY : Integer.parseInt(multStr);
	
				checkDeclaration.addValidationCheckParamDeclaration(new ValidationCheckParamDeclaration(paramName, constructor, mult,defaultType));
			}
	
			Class clazz[] = new Class[paramClasses.size() + 1];
			clazz[0] = ValidationCheckBundle.class;
			for (int i = 1; i < clazz.length; i++)
			{
				if (checkDeclaration.getValidationCheckParamDeclaration(i - 1).getMultiplicity() == 1)
				{
					clazz[i] = (Class) paramClasses.get(i - 1);
				}
				else
				{
					clazz[i] = Array.newInstance((Class) paramClasses.get(i - 1), 0).getClass();
				}
			}
	
			try{
				checkDeclaration.setValidationCheckMethod(check.getClass().getMethod(methodName, clazz));
			}catch (Throwable ex)
			{
				throw new ExternalizerInitializationException("Unable to initialize Validation Check Declaration [" + name + "]", ex);
			}
	
			validationCheckDeclarationsMap.put(name, checkDeclaration);
		}
	}

	private void processDOMForValidators(Document document)
	{
		Element rootElement = document.getDocumentElement();
		NodeList nodes = XMLUtil.getElementsByTagName(rootElement, ExternalizerConstants.VALIDATOR_TAG_NAME);

		for (int i = 0; i < nodes.getLength(); i++)
		{
			Element el = (Element)nodes.item(i);
			try
			{
				processValidatorElement(el);
			} catch (Throwable ex)
			{
				Logger.error(LOGGER_CONTEXT, "Unable to initialize validator. Continue with other. See Exception.");
				Logger.error(LOGGER_CONTEXT, ex);
			}
		}
	}

	private void processValidatorElement(Element el) throws ClassNotFoundException, InstantiationException, IllegalAccessException, ExternalizerInitializationException, ValidationProcessException
	{
		String name = ExternalizerUtil.getName(el);
		if(validatorsMap.containsKey(name))
		{
			Logger.warning(LOGGER_CONTEXT, "Unable to initialize validator [" + name + "]. A validator with that name is already defined.");
		}
		else
		{
			String typeStr = ExternalizerUtil.getType(el);

			Validator validator;
			if (typeStr.equals(ExternalizerConstants.VALIDATOR_TYPE_CUSTOM))
			{
				validator = createCustomValidator(el, name);
			}
			else
			{
				validator = createComplexValidator(el, name);
			}

			validatorsMap.put(name, validator);
		}
	}

	private Validator createComplexValidator(Element el, String name) throws ExternalizerInitializationException
	{
		ComplexValidator validator = new ComplexValidator(name);

		NodeList nodeList = el.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++)
		{
			Element element = (Element) nodeList.item(i);
			String elementName = element.getNodeName();

			if (elementName.equals(ExternalizerConstants.CHECK_TAG_NAME))
			{
				addCheckToValidator(element, name, validator);
			}
			else if (elementName.equals(ExternalizerConstants.CHECK_SET_TAG_NAME))
			{
				addCheckSetToValidator(element, name, validator);
			}
		}

		return validator;
	}

	private void addCheckSetToValidator(Element setElement, String name, ComplexValidator validator) throws ExternalizerInitializationException
	{
		String validationCheckSetName = XMLUtil.getAttribute(setElement, ExternalizerConstants.CHECK_SET_ATTR_VAL_CHECK_SET_NAME);
		CheckSet set = (CheckSet) validationCheckSetsMap.get(validationCheckSetName);

		if (set == null)
		{
			throw new ExternalizerInitializationException("Unable to initialize complex validator [" + name + "].The check set [" + validationCheckSetName + "] does not been declared");
		}

		for (int j = 0; j < set.count(); j++)
		{
			validator.addCheck(set.getValidationCheck(j), set.getFlag(j));
		}
	}

	private void addCheckToValidator(Element checkElement, String name, ComplexValidator validator) throws ExternalizerInitializationException
	{
		String refName = XMLUtil.getAttribute(checkElement, ExternalizerConstants.CHECK_ATTR_VALIDATION_CHECK_NAME);
		String defName = XMLUtil.getAttribute(checkElement, ExternalizerConstants.CHECK_ATTR_VALIDATION_CHECK_DEF);
		if (refName == null && defName == null)
		{
			throw new ExternalizerInitializationException("Unable to initialize complex validator [" + name + "] one of checks does not declare refName nor type");
		}

		String stopOnErrorStr = XMLUtil.getAttribute(checkElement, ExternalizerConstants.ATTR_STOP_ON_ERROR);
		boolean stopOnError = stopOnErrorStr == null ? false : stopOnErrorStr.equals(ExternalizerConstants.TRUE) ? true : false;

		if (refName != null)
		{
			ValidationCheckWrapper checkWrapper = (ValidationCheckWrapper)validationChecksMap.get(refName);
			if (checkWrapper == null)
			{
				throw new ExternalizerInitializationException("Unable to initialize validator [" + name + "] the check [" + refName + "] does not declared");
			}
			validator.addCheck(checkWrapper, stopOnError);
		}
		else
		{
			validator.addCheck(createValidationCheckWrapper(checkElement), stopOnError);
		}
	}

	private Validator createCustomValidator(Element el, String name) throws InstantiationException, IllegalAccessException, ClassNotFoundException, ExternalizerInitializationException, ValidationProcessException
	{
		Validator validator;
		String className = ExternalizerUtil.getClassName(el);

		validator = (Validator) Class.forName(className).newInstance();
		validator.setName(name);

		ParameterList parameterList = ExternalizerUtil.createParametersList(el);
		validator.initialize(parameterList);
		return validator;
	}
	
	// start barcuh
	
	private Constructor getParamValueConstructor(String valueType, ValidationCheckParamDeclaration paramDeclaration) throws ExternalizerInitializationException
	{
		Constructor constructor = null;	
		try
		{
			// if param declared a type, use it
			if (paramDeclaration.getType() != null)
			{
				constructor = paramDeclaration.getType();
			}
			// try to use the defaultType, if no override type was declared
			else if (paramDeclaration.getDefaultType() != null && valueType == null)
			{
				constructor = getClassByType(paramDeclaration.getDefaultType()).getConstructor(new Class[]{String.class});
			}
			// validationCheckDef override the defaultType
			else if (valueType != null)
			{		
				constructor = getClassByType(valueType).getConstructor(new Class[]{String.class});	
			}
			else
			{
				throw new ExternalizerInitializationException("no appropiate type was found for param " + paramDeclaration.getName());				
			}
		}
		catch (Throwable ex)
		{
			constructor = null;
			throw new ExternalizerInitializationException("error while creating type " + valueType, ex);
		}
		
		return constructor;

	}
		
	private Class getClassByType(String type) throws ExternalizerNotInitializedException, ClassNotFoundException
	{
		Class clazz = null;
		
		try
		{
			clazz = TypeDefinitionExternalizer.getInstance().getTypeClass(type);
		}
		catch (ExternalizationException ex)
		{
			clazz = Class.forName(type);
		}
		
		return clazz;
		
	}
	
	// end baruch


	private static class CheckSet
	{
		private String name;
		private ArrayList checks;
		private ArrayList flags;

		public CheckSet(String name)
		{
			this.name = name;

			checks = new ArrayList();
			flags = new ArrayList();
		}

		public void addCheck(ValidationCheckWrapper checkWrapper, boolean stopOnError)
		{
			checks.add(checkWrapper);
			flags.add(new Boolean(stopOnError));
		}

		public String getName()
		{
			return name;
		}

		public int count()
		{
			return checks.size();
		}

		public boolean getFlag(int index)
		{
			return ((Boolean) flags.get(index)).booleanValue();
		}

		public ValidationCheckWrapper getValidationCheck(int index)
		{
			return (ValidationCheckWrapper) checks.get(index);
		}
	}
}
