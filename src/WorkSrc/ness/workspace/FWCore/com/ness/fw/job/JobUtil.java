/*
 * Created on: 02/11/2004
 * Author: baruch hizkya
 */
package com.ness.fw.job;

import java.text.SimpleDateFormat;
import java.util.*;
import org.w3c.dom.*;

import com.ness.fw.common.externalization.DOMList;
import com.ness.fw.common.externalization.XMLUtil;
import com.ness.fw.common.externalization.XMLUtilException;
import com.ness.fw.common.logger.Logger;
import com.ness.fw.job.util.DOMRepository;

/**
 * A collection of utility methods for jobs.  
*/
public class JobUtil 
{

	/**
	 * The logger context
	 */
	private static final String LOGGER_CONTEXT = "BATCH";

	/**
	 * The records map
	 */
	private static HashMap params = new HashMap();
	
	private static StringBuffer xml = null;
	private static int spaces = 0;
	private static int tab = 0;


	/**
	 * convience method for translating a booleans days into
	 * string that represent the week days
	 * @param days
	 * @return
	 */
	public static String convertArr2String(boolean[] days)
	{
		String strDays = "";
		if (days != null)
		{
			// Looping over boolean array
			for (int i=0; i<7; i++)
			{
				if (days[i])
				{
					strDays = strDays + i + ",";
				}
			}
			
			strDays = strDays.substring(0,strDays.length() - 1);
		}
		return strDays;
	}

	/**
	 * convience method for translating a string that represent the week days
	 * into booleans array
	 * 
	 * @param days
	 * @return
	 */
	public static boolean[] convertString2Arr(String strDays)
	{
		boolean[] days = new boolean[7];
		for (int i=0;i<7; i++)
		{
			days[i] = false;
		}
		if (strDays != null)
		{
			StringTokenizer tokenizer = new StringTokenizer(strDays,",");
			// Looping over the seperated days
			while (tokenizer.hasMoreTokens())
			{
				String token = tokenizer.nextToken();
				days[Integer.parseInt(token)]= true;			
			}
		}
		return days;
	}

	/**
	 * a convience method for translating a xml string into hashmap
	 * @param xmlPparams
	 * @return
	 * @throws XMLUtilException
	 */
	public static HashMap convertXML2HashMap(String xmlPparams) throws XMLUtilException
	{
	
		// no parameters set
		if (xmlPparams == null)
		{
			params.clear();
			return params;
		}
	
		//creating DOM repository
		DOMRepository domRepository = new DOMRepository();
		
		try
		{
			domRepository.initialize(xmlPparams);
		} 
		catch (XMLUtilException e)
		{
			throw e;
		}

		synchronized (params)
		{
			params.clear();

			//read records
			DOMList recDomList = domRepository.getDOMList(XMLConstants.CONTAINER_TAG_NAME);
			if (recDomList != null)
			{
				for (int i = 0; i < recDomList.getDocumentCount(); i++)
				{
					Document doc = recDomList.getDocument(i);
	
					processParamsDOM(doc);
				}
			}			
		}

		return params;
	}

	/**
	 * a convience method for translating a hashmap into xml
	 * @param params
	 * @return
	 */
	public static String convertHashMap2XML(HashMap params)
	{
		xml = new StringBuffer(1024);
		if (params == null)
		{
			return null;
		}

		// creating xml header		
		xml.append(getStartTag(XMLConstants.XML_TAG_NAME) +
				   getAttribute(XMLConstants.XML_ATTR_VERSION,XMLConstants.XML_VERSION) + 
				   getAttribute(XMLConstants.XML_ATTR_ENCODING,XMLConstants.XML_ENCODING) + "?" + getEndTag(false));

		xml.append(getStartTag(XMLConstants.ROOT_TAG_NAME) + getEndTag(false));
		
		// creating header
		xml.append(getStartTag(XMLConstants.CONTAINER_TAG_NAME) + getEndTag(false));

		toXML(params);

		// closing header
		xml.append(getEndTag(XMLConstants.CONTAINER_TAG_NAME));
		xml.append(getEndTag(XMLConstants.ROOT_TAG_NAME));

		
		return xml.toString();
	}

	/**
	 * this method responsible for converting hashmap to xml string
	 * it's called when there is a hashmap entry (in array or hashmap)
	 * @param params
	 * @return
	 */
	private static String toXML(HashMap params)
	{			
		Iterator keys = params.keySet().iterator();
		while (keys.hasNext())
		{
			String key = (String)keys.next();
			Object value = params.get(key);
			// According to value instance call the relvantive translator
			if (value instanceof String)
			{
				xml.append(createParam(key,(String)value, XMLConstants.PARAM_ATTR_SINGLE_TYPE));
			}
			else if (value instanceof ArrayList)
			{
				// calling recursivley to translate array to xml
				toXML(key,(ArrayList)value);
			}
			else if (value instanceof HashMap)
			{
				// calling recursivley to translate hashMap to xml
				toXML(key,(HashMap)value);
			}
			
		}

		return xml.toString();	
	}

	/**
	 * this method responsible for converting hashmap to xml string
	 * with a key
	 * 
	 * @param key
	 * @param params
	 * @return
	 */
	private static String toXML(String key, HashMap params)
	{	
		// create map entry
		xml.append(createParam(key,XMLConstants.PARAM_ATTR_MAP_TYPE,false));
		toXML((params));
		xml.append(getEndTag(XMLConstants.PARAM_TAG_NAME));
		return xml.toString();
	}


	/**
	 * create param entry
	 * @param key
	 * @param type
	 * @param close
	 * @return
	 */
	private static StringBuffer createParam(String key, String type, boolean close)
	{
		StringBuffer paramEntry = new StringBuffer();
		paramEntry.append(getStartTag(XMLConstants.PARAM_TAG_NAME) +
						  getAttribute(XMLConstants.PARAM_ATTR_KEY,key) + 
						  getAttribute(XMLConstants.PARAM_ATTR_TYPE,type) + 						   
						  getEndTag(true,close));
		return paramEntry;
	}


	/**
	 * create param entry
	 * @param key
	 * @param value
	 * @param type
	 * @return
	 */
	private static StringBuffer createParam(String key, String value, String type)
	{
		StringBuffer paramEntry = new StringBuffer();
		paramEntry.append(getStartTag(XMLConstants.PARAM_TAG_NAME) +
						  getAttribute(XMLConstants.PARAM_ATTR_KEY,key) + 
						  getAttribute(XMLConstants.PARAM_ATTR_VALUE,value) + 						   
						  getAttribute(XMLConstants.PARAM_ATTR_TYPE,type) + 						   
						  getEndTag());
		return paramEntry;
	}


	/**
	 * this method responsible for converting array to xml string
	 * it's called when there is a array entry
	 * @param array
	 * @return
	 */
	private static void toXML(ArrayList array)
	{
		int size = array.size();
		for (int i=0; i<size; i++)
		{
			Object value = array.get(i);
			// According to value instance call the relvantive translator
			if (value instanceof String)
			{
				xml.append(getStartTag(XMLConstants.ENTRY_TAG_NAME) +
				getAttribute(XMLConstants.PARAM_ATTR_VALUE,(String)value) + 						   
				getEndTag());
				
			}
			else if (value instanceof ArrayList)
			{
				// create entry tag with array type
				xml.append(getStartTag(XMLConstants.ENTRY_TAG_NAME) +
						   getAttribute(XMLConstants.PARAM_ATTR_TYPE,XMLConstants.PARAM_ATTR_ARRAY_TYPE) + 
						   getEndTag(false));
				// calling recursivly
				toXML((ArrayList)value);
				// close entry tag
				xml.append(getEndTag(XMLConstants.ENTRY_TAG_NAME));
			}
			else if (value instanceof HashMap)
			{	
				// create entry tag with map type
				xml.append(getStartTag(XMLConstants.ENTRY_TAG_NAME) +
						   getAttribute(XMLConstants.PARAM_ATTR_TYPE,XMLConstants.PARAM_ATTR_MAP_TYPE) + 
						   getEndTag(false));
				// calling recursivly
				toXML((HashMap)value);
				// close entry tag
				xml.append(getEndTag(XMLConstants.ENTRY_TAG_NAME));
			}		
		}
	}



	/**
	 * this method responsible for converting array to xml string
	 * with a key
	 * @param key
	 * @param array
	 */
	private static void toXML(String key, ArrayList array)
	{
		// create array entry
		xml.append(createParam(key,XMLConstants.PARAM_ATTR_ARRAY_TYPE,false));
		toXML(array);		
		xml.append(getEndTag(XMLConstants.PARAM_TAG_NAME));		
	}




	/**
	 * Parsing all params in the specific DOM
	 * @param doc
	 */
	private static void processParamsDOM (Document doc)
	{
		Element rootElement = doc.getDocumentElement();

		NodeList nodes = XMLUtil.getElementsByTagName(rootElement, XMLConstants.CONTAINER_TAG_NAME);

		for (int i = 0; i < nodes.getLength(); i++)
		{
			Element paramElement = (Element) nodes.item(i);

			try
			{
				readParam(paramElement);
			}
			catch (Throwable ex)
			{
				Logger.error(LOGGER_CONTEXT, "Unable to initialize legacy Record. Continue with other.");
				Logger.error(LOGGER_CONTEXT, ex);
			}
		}
	}


	/**
	 * parse oaram element
	 * @param paramElement
	 * @throws LegacyCommandsExternalizationException
	 */
	private static void readParam(Element element)
	{
		NodeList paramNodesList = XMLUtil.getElementsByTagName(element, XMLConstants.PARAM_TAG_NAME);
		for (int i = 0; i < paramNodesList.getLength(); i++)
		{
			Element paramElement = (Element) paramNodesList.item(i);

			// getting param attributes
			String paramName = XMLUtil.getAttribute(paramElement, XMLConstants.PARAM_ATTR_KEY);
			String paramValue = XMLUtil.getAttribute(paramElement, XMLConstants.PARAM_ATTR_VALUE);
			String typeValue = XMLUtil.getAttribute(paramElement, XMLConstants.PARAM_ATTR_TYPE);
				
			// single param
			if (typeValue.equals(XMLConstants.PARAM_ATTR_SINGLE_TYPE))
			{
				params.put(paramName, paramValue);
			}
			// hashMap param
			else if (typeValue.equals(XMLConstants.PARAM_ATTR_MAP_TYPE))
			{
				// process map entries		
				HashMap map = processMap(paramElement);
				params.put(paramName,map);			
			}
			// array param
			else if (typeValue.equals(XMLConstants.PARAM_ATTR_ARRAY_TYPE))
			{
				// process array entries
				ArrayList arr = processArray(paramElement);
				params.put(paramName, arr);				
			}
				
			Logger.debug(LOGGER_CONTEXT, "loading batch param [" + paramName + "]");
		}
	}

	/**
	 * parsing array entry
	 * @param element
	 * @return
	 */
	private static ArrayList processArray(Element element)
	{
		ArrayList arr = new ArrayList();

		NodeList nodes = XMLUtil.getElementsByTagName(element, XMLConstants.ENTRY_TAG_NAME);
		
		for (int i = 0; i < nodes.getLength(); i++)
		{
			Element entryElement = (Element) nodes.item(i);

			try
			{
				String typeValue = XMLUtil.getAttribute(entryElement, XMLConstants.PARAM_ATTR_TYPE);
				// simple entry in the aray
				if (typeValue == null)
				{
					String paramValue = XMLUtil.getAttribute(entryElement, XMLConstants.PARAM_ATTR_VALUE);
					arr.add(paramValue);
				}
				// array entry
				else if (typeValue.equals(XMLConstants.PARAM_ATTR_ARRAY_TYPE))
				{
					ArrayList tmpArray = new ArrayList();
					tmpArray = processArray(entryElement);
					arr.add(tmpArray);					
				}
				// map entry
				else if (typeValue.equals(XMLConstants.PARAM_ATTR_MAP_TYPE))
				{
					HashMap tmpMap = new HashMap();
					tmpMap = processMap(entryElement);
					arr.add(tmpMap);
				}
			}
			catch (Throwable ex)
			{
				Logger.error(LOGGER_CONTEXT, "Unable to initialize array entry. Continue with other.");
				Logger.error(LOGGER_CONTEXT, ex);
			}
		}
		
		return arr;	
	}

	/**
	 * parsing hashmap entry
	 * @param element
	 * @return
	 */
	private static HashMap processMap(Element element)
	{
		HashMap map = new HashMap();

		NodeList nodes = XMLUtil.getElementsByTagName(element, XMLConstants.PARAM_TAG_NAME);
		
		for (int i = 0; i < nodes.getLength(); i++)
		{
			Element entryElement = (Element)nodes.item(i);

			try
			{
				String typeValue = XMLUtil.getAttribute(entryElement, XMLConstants.PARAM_ATTR_TYPE);
				String paramKey = XMLUtil.getAttribute(entryElement, XMLConstants.PARAM_ATTR_KEY);

				// simple entry in the aray
				if (typeValue.equals(XMLConstants.PARAM_ATTR_SINGLE_TYPE))
				{
					String paramValue = XMLUtil.getAttribute(entryElement, XMLConstants.PARAM_ATTR_VALUE);
					map.put(paramKey,paramValue);
				}
				// array entry
				else if (typeValue.equals(XMLConstants.PARAM_ATTR_ARRAY_TYPE))
				{
					ArrayList tmpArray = new ArrayList();
					tmpArray = processArray(entryElement);
					map.put(paramKey,tmpArray);					
				}
				// hashmap entry
				else if (typeValue.equals(XMLConstants.PARAM_ATTR_MAP_TYPE))
				{
					HashMap tmpMap = new HashMap();
					tmpMap = processMap(entryElement);
					map.put(paramKey,tmpMap);
				}
			}
			catch (Throwable ex)
			{
				Logger.error(LOGGER_CONTEXT, "Unable to initialize map entry. Continue with other.");
				Logger.error(LOGGER_CONTEXT, ex);
			}
		}
		
		return map;

	}

	/**
	 * get start tag with the specific name and spaces
	 * @param name
	 * @param spaces
	 * @return
	 */
	private static String getStartTag(String name, int spaces)
	{
		String stringSpaces = new String();
		for (int i=0; i<spaces; i++)
		{
			stringSpaces += " ";
		}
		return stringSpaces + "<" + name + " ";
	}

	/**
	 * get start tag with the specific name
	 * @param name
	 * @return
	 */
	private static String getStartTag(String name)
	{
		return getStartTag(name,0);
	}

	/**
	 * get end tag with specific name
	 * @param tagName
	 * @return
	 */
	private static String getEndTag(String tagName)
	{
		return getEndTag(tagName,true);
	}

	/**
	 * get end tag with specific name
	 * @param tagName
	 * @param withCR
	 * @return
	 */
	private static String getEndTag(String tagName, boolean withCR)
	{
		String str = "</" + tagName + ">";
		if (withCR)
		{
			str += "\n";
		}
		return str;
	}

	/**
	 * get end tag
	 * @return
	 */
	private static String getEndTag()
	{
		return getEndTag(true,true);
	}

	/**
	 * get end tag with close mark
	 * @param close
	 * @return
	 */
	private static String getEndTag(boolean close)
	{
		return getEndTag(true,close);
	}

	/**
	 * get end tag with close mark and cr
	 * @param withCR
	 * @param close
	 * @return
	 */
	private static String getEndTag(boolean withCR, boolean close)
	{
		String str = ">";
	
		if (close)
		{
			str = "/>";
		}
		if (withCR)
		{
			str += "\n";
		}
		return str;
	}


	/**
	 * get the str with quot
	 * @param str
	 * @return
	 */
	private static String getQuot(String str)
	{
		return "\"" + str + "\"";
	}

	/**
	 * get the str with single quot
	 * @param str
	 * @return
	 */
	private static String getSingleQuot(String str)
	{
		return "'" + str + "'";
	}

	/**
	 * get attribute with the specific name
	 * @param name
	 * @return
	 */
	private static String getAttribute(String name)
	{
		return (" " + name + "=");
	}

	/**
	 * get attribute with the specific name and value
	 * @param name
	 * @param value
	 * @return
	 */
	private static String getAttribute(String name, String value)
	{
		return " " + name + "=" + getQuot(value);
	}
	
	/**
	 * get the time represented by this date
	 * @param date
	 * @return
	 */
	public static String getTime(Date date)
	{
		String now;
		Calendar calender = Calendar.getInstance();
		calender.setTime(date);
		now = new SimpleDateFormat("HH:mm:ss").format(calender.getTime());
		return now;
	}

	/**
	 * et the time represented by the current time
	 * @return
	 */
	public static String getTime()
	{
		String now;
		Date date = new Date();
		Calendar calender = Calendar.getInstance();
		calender.setTime(date);
		calender.add(Calendar.MINUTE,1);
		now = new SimpleDateFormat("HH:mm:ss").format(calender.getTime());
		return now;
	}

	/**
	 * et the time represented by the current time
	 * @return
	 */
	public static Date getCurrentTime()
	{
		Date date = new Date();
		Calendar calender = Calendar.getInstance();
		calender.setTime(date);
		calender.add(Calendar.MINUTE,1);
		return calender.getTime();	
	}



	/**
	 * get the current time
	 * @return
	 */
	public static Date getCurrentDate()
	{
		return new Date();
	}
	
	/**
	 * return the next date, whice the job will run
	 * @param data
	 * @return
	 */
	public static Date computeNextDate(AbstractFrequencyJobSchduleData data)
	{
		Date nextDate = data.getLastRuntime();
//		if (data.getLastRuntime() == null)
//		{
//			nextDate = data.getFrequencyDate();
//		}
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(nextDate);
		int frequencyType = data.getType();

		switch (frequencyType)
		{
			case AbstractJobSchduleData.DAILY_FREQUENCY_TYPE :
			{
				calendar.add(Calendar.DAY_OF_YEAR,1);
				break;
			}
	
			case AbstractJobSchduleData.WEEKLY_FREQUENCY_TYPE :
			{
				// find the closet day to the last run day (of the days to run)
				int closetDay = findClosetDay((WeeklyJobSchduleData)data) + 1;	
				if (data.getNextDate() != null)
				{
					calendar.setTime(data.getNextDate());
				}			
				calendar.set(Calendar.DAY_OF_WEEK,closetDay);
				break;
			}

			case AbstractJobSchduleData.MONTHLY_FREQUENCY_TYPE :
			{
				calendar.add(Calendar.MONTH,1);
				break;
			}
		}
	
		return calendar.getTime();		
	} 
	
	/**
	 * finds the closet day to run a daily job according to the last day
	 *  that was run
	 * @param data
	 * @return
	 */
	private static int findClosetDay(WeeklyJobSchduleData data)
	{
		Date lastDate = data.getLastRuntime();
		boolean[] days = data.getFrequencyDays();
		int lastRunDay;
		int currentLastRunDay;
		// the job never runed
		if (lastDate == null)
		{
			lastRunDay = findDay(0,days.length,days);
		}
		else
		{
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(lastDate);
			currentLastRunDay = calendar.get(Calendar.DAY_OF_WEEK);

			// find the closet day from current..end
			lastRunDay = findDay(currentLastRunDay ,days.length,days);
			// if not found try to find it in 0..current-1	
			if (lastRunDay == -1)
			{		
				lastRunDay = findDay(0,currentLastRunDay,days);
				// a week has past, so add 7 days to the next week
				calendar.add(Calendar.DAY_OF_WEEK,7);
				data.setNextDate(calendar.getTime());
			}
		}
		
		return lastRunDay;		
	}
	
	/**
	 * finds a day
	 * @param lowerIndex
	 * @param upperIndex
	 * @param days
	 * @return
	 */
	private static int findDay(int lowerIndex, int upperIndex, boolean[] days)
	{
		int day = -1;
		for (int i=lowerIndex; i<upperIndex; i++)
		{
			if (days[i])
			{
				day = i;
				break;
			}
		}
		
		return day;	
	} 
	
	/**
	 * Setting the time's part of this date to 0
	 * @param date
	 * @return
	 */
	public static Date resetDate(Date date)
	{
		Date retDate = null;
		if (date != null)
		{
			Calendar calendar = Calendar.getInstance();	
			calendar.setTime(date);
			calendar.set(Calendar.HOUR_OF_DAY,0);
			calendar.set(Calendar.MINUTE,0);
			calendar.set(Calendar.SECOND,0);
			calendar.set(Calendar.MILLISECOND,0);
			retDate = calendar.getTime();
		}

		return retDate;
	}

	
}