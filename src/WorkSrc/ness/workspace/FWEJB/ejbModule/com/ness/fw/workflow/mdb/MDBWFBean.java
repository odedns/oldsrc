package com.ness.fw.workflow.mdb;

import org.w3c.dom.*;
import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ejb.CreateException;
import javax.ejb.MessageDrivenContext;
import javax.ejb.MessageDrivenBean;
import java.util.*;
import com.ness.fw.common.SystemInitializationException;
import com.ness.fw.common.SystemInitializationManager;
import com.ness.fw.common.externalization.XMLUtil;
import com.ness.fw.common.externalization.XMLUtilException;
import com.ness.fw.util.tree.Node;
import com.ness.fw.util.tree.KeyGenerator;
import com.ness.fw.workflow.mdb.ActivateUPESUtil;
import com.ness.fw.workflow.mdb.UPESConstants;
import com.ness.fw.common.logger.Logger;

/*
* Created on 0/11/2004
* Author Amit Mendelson
* @version $Id: MDBWFBean.java,v 1.10 2005/04/06 12:27:54 amit Exp $
*/

/**
 * Bean implementation class for Enterprise Bean: MDBWF
 * This class implements the core of UPES implementation.
 * It handles the event of message arrival and sending a reply to the workflow.
 */


public class MDBWFBean implements MessageDrivenBean, MessageListener
{

	/**
	 * Private instance of MessageDrivenContext
	 */
	private MessageDrivenContext fMessageDrivenCtx;
	
	private static final String SHOULD_INIT_VARIABLE_NAME = "shouldInitializedSystem";
	private static final String SERVER_CONF_LOCATION_VARIABLE_NAME = "serverConfLocation";
	private static final String INITIAL_CONTEXT_NAME = "java:comp/env";
	
	public static final String 	LOGGER_CONTEXT = "WF MDB";
	/**
	 * getMessageDrivenContext
	 */
	public MessageDrivenContext getMessageDrivenContext()
	{
		return fMessageDrivenCtx;
	}

	/**
	 * setMessageDrivenContext
	 * Required since the class implements MessageDrivenBean.
	 */
	public void setMessageDrivenContext(MessageDrivenContext ctx)
	{
		fMessageDrivenCtx = ctx;
	}

	/**
	 * ejbCreate
	 */
	public void ejbCreate() throws CreateException
	{
		Boolean shouldInitializedSystem;
		try
		{

			Logger.debug(LOGGER_CONTEXT,"started ejbCreate");

			InitialContext ctx = new InitialContext();
			Context myEnv = (Context)ctx.lookup(INITIAL_CONTEXT_NAME);
			shouldInitializedSystem = (Boolean)myEnv.lookup(SHOULD_INIT_VARIABLE_NAME);
			String location = (String)myEnv.lookup(SERVER_CONF_LOCATION_VARIABLE_NAME);
			
			if (shouldInitializedSystem.booleanValue())
			{
				if (!SystemInitializationManager.getInstance().isInitialized())
				{
					SystemInitializationManager.getInstance().initializeConfiguration(location);
				}
			}

			Logger.debug(LOGGER_CONTEXT,"finished ejbCreate");

		}
		catch (NamingException e)
		{
			e.printStackTrace();
			throw new CreateException(UPESConstants.MDB_INITIALIZATION_PROBLEM);
		} 
		catch (SystemInitializationException e)
		{
			e.printStackTrace();
			throw new CreateException(UPESConstants.MDB_INITIALIZATION_PROBLEM);
		}
	}
	
		/**
		 * onMessage
		 * Takes care of getting required parameters from the message,
		 * and passing them to the appropriate service.
		 * @param msg
		 */
		public void onMessage(Message msg)
		{

			Logger.debug(LOGGER_CONTEXT,"started onMessage");

			try
			{
				if((msg==null))
				{
					Logger.warning(LOGGER_CONTEXT, 
						UPESConstants.MDB_MESSAGE_NULL);
					return;
				}

				TextMessage textMessage = (TextMessage) msg;

				String rawMessage = textMessage.getText();
				Logger.debug(LOGGER_CONTEXT,"onMessage, Message: " + rawMessage);

				/*
				 * The MDB should handle the case of a request to terminate
				 * the program.
				 */
				 //Build the Node.
				 //Receive the Message type (2nd Node in the tree).
				if (rawMessage.indexOf(UPESConstants.TERMINATE_PROGRAM) != -1)
				{
					Logger.warning(LOGGER_CONTEXT, 
						UPESConstants.MDB_PROGRAM_TERMINATED);
					return;
				}

				if (rawMessage.indexOf(UPESConstants.ACTIVITY_EXPIRED) != -1)
				{
					Logger.warning(LOGGER_CONTEXT, 
						UPESConstants.MDB_ACTIVITY_EXPIRED);

					return;
				}

				/*
				 * From here, the message is assumed to be of type
				 * "ActivityImplInvoke".
				 */

				//  Wrap the ProgramInputData section in a Tree.
				Node programInputData = retrieveProgramInputData(rawMessage);
				Logger.debug(LOGGER_CONTEXT,"onMessage, generated Node from message");

				XMLUtil xu = new XMLUtil();
				Document doc = XMLUtil.readXMLFromString(rawMessage, false);
			
				String programLogicName = xu.getField(doc, UPESConstants.PROGRAM_NAME);
				Logger.debug(LOGGER_CONTEXT,"onMessage, program logic name: " + programLogicName);
				
				String actImplCorrelID = xu.getField(doc, UPESConstants.ACT_IMPL_CORREL_ID);
				Logger.debug(LOGGER_CONTEXT,"onMessage, actImplCorrelID: " + actImplCorrelID);

				String processName = xu.getField(doc, UPESConstants.PROCESS_NAME);
				Logger.debug(LOGGER_CONTEXT,"onMessage, processName: " + processName);

				String processTemplateName = xu.getField(doc, UPESConstants.PROCESS_TEMPLATE_NAME);
				Logger.debug(LOGGER_CONTEXT,"onMessage, processTemplateName: " + processTemplateName);
				
				String waitForDocuments = xu.getField(doc, UPESConstants.WAIT_FOR_DOCUMENTS);
				Logger.debug(LOGGER_CONTEXT,"onMessage, waitForDocuments: " + waitForDocuments);

				//ActivateUPES decides which program will be called.
				ActivateUPESUtil.callService(programInputData, programLogicName, actImplCorrelID,
					processName, processTemplateName, waitForDocuments);

				Logger.debug(LOGGER_CONTEXT,"onMessage, completed operation");

			}
			catch (XMLUtilException xue)
			{
				Logger.error(UPESConstants.UPES_CONTEXT, xue);
			}
			catch (JMSException jmse)
			{
				Logger.error(UPESConstants.UPES_CONTEXT, jmse);
			}
			catch (UpesException ue) 
			{
				Logger.error(UPESConstants.UPES_CONTEXT, ue);
			} 
		}
	
	   /**
		* ejbRemove
		* Required since the class implements MessageDrivenBean.
		*/
		public void ejbRemove()
		{
			Logger.debug(LOGGER_CONTEXT,"called ejbRemove");
		}

	/**
	 * Assistance method to allow retrieval of the ProgramInputData only.
	 * @param rawMessage
	 * @return Node A tree that contains only the ProgramInputData.
	 * @throws XMLUtilException
	 */
	private Node retrieveProgramInputData(String rawMessage) throws XMLUtilException
	{
		Node node = retrieveMessageTree(rawMessage);
		ArrayList nodesList = 
			node.findDescendantsByDescription(UPESConstants.PROGRAM_INPUT_DATA, 0, Node.MAX_RECURSION,false);
		if((nodesList==null)||(nodesList.size()==0))
		{
			return null;
		}
		// In a valid message there should be only one node named PROGRAM_INPUT_DATA.
		return (Node)nodesList.get(0);
	}

	/**
	 * Assistance method for quick retrieval of the ProgramOutputData defaults.
	 * @param rawMessage
	 * @return Node A tree that contains only the ProgramOutputData defaults.
	 * @throws XMLUtilException
	 */
	private Node retrieveProgramOutputDataDefaults(String rawMessage) 
		throws XMLUtilException
	{
		Node node = retrieveMessageTree(rawMessage);
		ArrayList nodesList = 
			node.findDescendantsByDescription(UPESConstants.PROGRAM_OUTPUT_DATA_DEFAULTS,
				0, Node.MAX_RECURSION,false);
		if((nodesList==null)||(nodesList.size()==0))
		{
			return null;
		}
		/*
		 * In a valid message there should be only one node named 
		 * PROGRAM_OUTPUT_DATA_DEFAULTS.
		 */
		return (Node)nodesList.get(0);
	}
	
	/**
	 * Utility method for keeping the Message information as a tree.
	 * @param rawMessage The text message
	 * @return Node A tree which contains the message information.
	 * @throws XMLUtilException
	 */
	private Node retrieveMessageTree(String rawMessage) throws XMLUtilException
	{

		Document document = XMLUtil.readXMLFromString(rawMessage, false);

		Element rootElement = document.getDocumentElement();
		Node node = createTree(rootElement);

		return node;
	}
	
	/**
	 * Assistance method for creation of the tree starting from a
	 * specified root.
	 * @param rootElement The requested base of the tree.
	 * @return Node The generated tree.
	 */
	private Node createTree(Element rootElement)
	{
		KeyGenerator keyGenerator = new KeyGenerator();
		//Generate a new node which description is the tagName of the root element.
		Node node = new Node(keyGenerator, rootElement.getTagName());

		NodeList list = rootElement.getChildNodes();
		
		//Create the childs of the node.
		for(int i=0;i<list.getLength();i++)
		{
			Node node1 = createTree(list.item(i), keyGenerator);
			if(node1!=null)
			{
				node.addChild(node1);
			}
		}
		return node;
	}

	/**
	 * Assistance method for creation of tree from a specified Node.
	 * @param rootNode org.w3c.dom.Node node Requested base of the tree
	 * @param keyGenerator Used to generate the Node
	 * @return Node The generated tree.
	 */
	private Node createTree(org.w3c.dom.Node rootNode, KeyGenerator keyGenerator)
	{

		//Deepest level - Don't create a text node.
		if(rootNode instanceof Text)
		{
			return null;
		}
		Node node = new Node(keyGenerator, ((Element)rootNode).getTagName());

		NodeList list = rootNode.getChildNodes();
		for(int i=0;i<list.getLength();i++)
		{
			//Don't add a text node - Instead put the value at the parent.
			//Each Node can't have more than one Text child
			//(By the expected XML format).
			if(list.item(i) instanceof Text)
			{
				node.setDescription(rootNode.getNodeName());
				HashMap newMap = new HashMap();
				newMap.put(rootNode.getNodeName(), list.item(i).getNodeValue());
				node.addExtraData(newMap);
				
			} else {
				Node node1 = createTree(list.item(i), keyGenerator);
				if(node1!=null)
				{
					node.addChild(node1);
				} else
				{
					node.setDescription(list.item(i).getNodeName());
					HashMap newMap = new HashMap();
					newMap.put(list.item(i).getNodeName(), list.item(i).getNodeValue());
					node.addExtraData(newMap);
				}
			}
		}
		return node;
	}
}
