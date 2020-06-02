package com.ness.fw.workflow.mdb;

import java.util.Hashtable;

import javax.jms.*;
import javax.naming.*;
import javax.naming.directory.InitialDirContext;

import java.util.*;

import com.ness.fw.util.tree.Node;
//import com.ness.fw.common.exceptions.ResourceException;
import com.ness.fw.common.logger.Logger;
import com.ness.fw.common.resources.SystemResources;

/*
* Created on 0/11/2004
* Author Amit Mendelson
* @version $Id: P2PReplyUtil.java,v 1.8 2005/04/27 11:34:25 amit Exp $
*/

/**
 * This class handles reply generation from an UPES-activated program
 * to the workflow.
 * This class assumes the actImplCorrelID is already known.
 */
public class P2PReplyUtil
{

	public static final String LOGGER_CONTEXT = "WF MDB";

	/**
	 * Handle all aspects of the reply message.
	 * @param programOutputDataNode
	 * @param ActImplCorrelID
	 * @param responseRequired
	 * @param waitForDocuments
	 * @throws UpesException
	 */
	public static void reply(
		String actImplCorrelId,
		Node programTreeResult,
		boolean responseRequired,
		boolean waitForDocuments)
		throws UpesException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"reply started, received actImplCorrelId: " + actImplCorrelId);
		Logger.debug(
			LOGGER_CONTEXT,
			"reply, received responseRequired: " + responseRequired);
		Logger.debug(
			LOGGER_CONTEXT,
			"reply, received waitForDocuments: " + waitForDocuments);

		if (actImplCorrelId == null)
		{
			throw new UpesException(UPESConstants.CORREL_ID_IS_NULL);
		}

		if (waitForDocuments)
		{
			Logger.debug(LOGGER_CONTEXT, "reply, calling removeCorrelIdFromDB");
			WFDBUtil.removeCorrelIdFromDB(actImplCorrelId);
			Logger.debug(
				LOGGER_CONTEXT,
				"reply, completed removeCorrelIdFromDB");
		}

		//Check for bad format
		if ((programTreeResult == null)
			|| (programTreeResult.getDescription() == null)
			|| (!(programTreeResult
				.getDescription()
				.equals(UPESConstants.PROGRAM_OUTPUT_DATA))))
		{
			Logger.warning(LOGGER_CONTEXT, UPESConstants.BAD_TREE_FORMAT);
			return;
		}
		String nodeDescription = programTreeResult.getDescription();
		//Check for bad format
		if ((nodeDescription == null)
			|| (!(nodeDescription.equals(UPESConstants.PROGRAM_OUTPUT_DATA))))
		{
			Logger.warning(LOGGER_CONTEXT, UPESConstants.BAD_TREE_FORMAT);
			return;
		}

		String XMLReply =
			buildXMLReply(actImplCorrelId, programTreeResult, responseRequired);
		Logger.debug(LOGGER_CONTEXT, "reply, XML reply: " + XMLReply);
		try
		{

			handleResponseMessage(XMLReply);

		}
		catch (NamingException nex)
		{
			throw new UpesException(nex);
		}
		catch (JMSException jmse)
		{
			throw new UpesException(jmse);
		}
	}

	/**
	 * Convert the tree information to XML-format String.
	 * @param node
	 * @return StringBuffer
	 */
	private static StringBuffer toXMLString(Node node)
	{
		StringBuffer xmlBuffer = new StringBuffer(1024);
		String description = node.getDescription();
		xmlBuffer.append("<");
		xmlBuffer.append(description);
		xmlBuffer.append(">");

		if (!node.hasChildren())
		{
			HashMap extraData = node.getExtraData();
			if (extraData != null)
			{
				xmlBuffer.append(extraData.get(description));
				Set set = extraData.entrySet();
				Iterator iter = set.iterator();
				while (iter.hasNext())
				{
					//Pass on all the information to be kept in the container fields.
					Map.Entry nextObject = (Map.Entry) iter.next();
					xmlBuffer.append("<" + nextObject.getKey() + ">");
					xmlBuffer.append(nextObject.getValue());
					xmlBuffer.append("</" + nextObject.getKey() + ">");
				}
			}
		}
		else
		{

			TreeMap map = node.getChildren();
			Collection mapl = map.entrySet();
			Iterator it = mapl.iterator();
			while (it.hasNext())
			{
				xmlBuffer.append(
					toXMLString((Node) ((Map.Entry) (it.next())).getValue()));
			}
		}
		xmlBuffer.append("</" + description + ">");
		return xmlBuffer;
	}

	/**
	 * Generate the reply message from a predefeined XML
	 * @param actImplCorrelID
	 * @param programOutputData
	 * @return String The generated XML reply.
	 */
	private static String buildXMLReply(
		String actImplCorrelID,
		Node outputDataNode,
		boolean responseRequired)
	{

		String programOutputData = toXMLString(outputDataNode).toString();
		//TODO: check if the actImplCorrelID/ProgramOutputData
		//is not null.

		//TODO: make sure that the property is retrieved correctly!
		SystemResources resources = SystemResources.getInstance();

		//TODO: check if the resources are null			
		StringBuffer xmlMessage =
			new StringBuffer(
				resources.getProperty(UPESConstants.RESPONSE_MESSAGE));
		int indexOfCorrelID =
			xmlMessage.indexOf(UPESConstants.CORREL_ID_STRING);
		int correlIDLength = UPESConstants.CORREL_ID_STRING.length();

		xmlMessage.replace(
			indexOfCorrelID,
			indexOfCorrelID + correlIDLength,
			actImplCorrelID);
		int indexOfProgramOutputData =
			xmlMessage.indexOf(UPESConstants.PROGRAM_OUTPUT_DATA_STRING);
		int programOutputDataLength =
			UPESConstants.PROGRAM_OUTPUT_DATA_STRING.length();

		xmlMessage.replace(
			indexOfProgramOutputData,
			(indexOfProgramOutputData + programOutputDataLength),
			programOutputData);

		return xmlMessage.toString();
	}

	/**
	 * Handle Response Message
	 * @param responseMessage
	 * @return String the response message.
	 * @throws JMSException
	 * @throws NamingException
	 */
	public static void handleResponseMessage(String responseMessage)
		throws JMSException, NamingException
	{

		QueueSession queueSession = null;
		QueueConnection connection = null;
		QueueSender sender = null;
		Context initialContext = null;

		Hashtable properties = new Hashtable();
		properties.put(
			Context.INITIAL_CONTEXT_FACTORY,
			SystemResources.getInstance().getProperty(
				UPESConstants.INITIAL_CONTEXT_FACTORY_MAPPING));
		String iiopProviderUrl =
			SystemResources.getInstance().getProperty(
				UPESConstants.IIOP_PROVIDER_URL);
		Logger.debug(
			LOGGER_CONTEXT,
			"reply, iiopProviderUrl: " + iiopProviderUrl);
		properties.put(Context.PROVIDER_URL, iiopProviderUrl);
		properties.put(Context.REFERRAL, UPESConstants.THROW);

		initialContext = new InitialDirContext(properties);
		Logger.debug(LOGGER_CONTEXT, "reply, defined the initial context");
		try
		{

			QueueConnectionFactory factory =
				(QueueConnectionFactory) initialContext.lookup(
					SystemResources.getInstance().getProperty(
						UPESConstants.QUEUE_CONNECTION_FACTORY_LOOKUP));
			Logger.debug(
				LOGGER_CONTEXT,
				"reply, defined the queue connection factory");
			Queue queue =
				(Queue) initialContext.lookup(
					SystemResources.getInstance().getProperty(
						UPESConstants.QUEUE_LOOKUP));
			Logger.debug(LOGGER_CONTEXT, "reply, defined the queue");

			connection = factory.createQueueConnection();
			Logger.debug(LOGGER_CONTEXT, "reply, defined the queue connection");
			queueSession =
				connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
			Logger.debug(LOGGER_CONTEXT, "reply, defined the queue session");
			sender = queueSession.createSender(queue);
			Logger.debug(LOGGER_CONTEXT, "reply, defined the queue sender");
			sender.setDeliveryMode(DeliveryMode.PERSISTENT);
			Logger.debug(
				LOGGER_CONTEXT,
				"reply, set delivery mode in the queue sender");
			//TODO: Commented lines are optional.
			/*
			sender.setPriority(4);
			sender.setTimeToLive(0); //When we allow message to expire.
			*/
			TextMessage message =
				queueSession.createTextMessage(responseMessage);
			Logger.debug(
				LOGGER_CONTEXT,
				"reply, created the text message for sending");
			Logger.debug(
				LOGGER_CONTEXT,
				"The text message for sending: " + message);
			sender.send(message);
			Logger.debug(LOGGER_CONTEXT, "reply, sent the reply message");
		}
		finally
		{
			if (sender != null)
			{
				sender.close();
			}
			if (queueSession != null)
			{
				queueSession.close();
			}
			if (connection != null)
			{
				connection.close();
			}
			Logger.debug(
				LOGGER_CONTEXT,
				"reply, closed sender, queueSession and connection");
			if (initialContext != null)
			{
				initialContext.close();
			}
			Logger.debug(LOGGER_CONTEXT, "reply, closed the initial context");

		}
	}

}
