/*
 * Created on: 11/01/2005
 * Author:  user name
 * @version $Id: JGroupsDistributionManager.java,v 1.2 2005/03/15 07:42:41 alexey Exp $
 */
package com.ness.fw.cache.notification;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.jgroups.Address;
import org.jgroups.Channel;
import org.jgroups.JChannel;
import org.jgroups.MembershipListener;
import org.jgroups.Message;
import org.jgroups.View;
import org.jgroups.blocks.GroupRequest;
import org.jgroups.blocks.MessageDispatcher;
import org.jgroups.blocks.RequestHandler;
import org.jgroups.util.Util;

import com.ness.fw.cache.CacheEventHandler;
import com.ness.fw.cache.CacheManager;
import com.ness.fw.cache.config.CacheConfigFactory;
import com.ness.fw.cache.config.CacheDefinition;
import com.ness.fw.cache.config.CacheEntityConfig;
import com.ness.fw.cache.exceptions.CacheException;
import com.ness.fw.cache.implementation.CacheEntityContainer;
import com.ness.fw.common.logger.Logger;

/**
 * JGroups implementation for notification mechanism.
 * JGroupsDistributionManager has two main tasks: notify other caches about cache activity
 * and handle events from other caches about cache activity.
 */
public class JGroupsDistributionManager implements DistributionManager, MembershipListener, RequestHandler
{
	//Operation difinition
	public final static String GET_OPERATION = "get";
	public final static String GET_ALL_OPERATION = "getAll";
	public final static String PUT_OPERATION = "put";
	public final static String PUT_ALL_OPERATION = "putAll";
	public final static String CLEAR_OPERATION = "clear";
	public final static String REMOVE_OPERATION = "remove";
	public final static String KEY_SET_OPERATION = "keySet";
	public final static String IS_CENTRAL_NODE_OPERATION = "isCentralNode";

	/**
	 * Cache event handler.
	 */
	private CacheEventHandler handler;
	
	/**
	 * Message dispatcher.
	 */
	private FwMessageDispatcher dispatcher;
	
	/**
	 * Tells whether need to notify.
	 * The flag is true when more than 1 member connected to the entity group.
	 */
	private boolean needNotify;
	
	/**
	 * Central cache node address. Used only if cache type is central, whith means
	 * all notification will send to the central cache only.
	 */
	private Address centralNodeAddress;
	
	/**
	 * Cache name.
	 */
	private String cacheName;
	
	/**
	 * Cache definition.
	 */
	private CacheDefinition cacheDefinition;

	private static ByteArrayOutputStream byteArrOutputStream;
	private static ObjectOutputStream objOutputStream;
	
	/**
	 * Creates a new instance.
	 * @param entity entity name.
	 */
	public JGroupsDistributionManager(String cacheName, CacheDefinition cacheDefinition) throws CacheException
	{
		init(cacheName, cacheDefinition);
	}
	
	/**
	 * Initializes a newly created notifier.
	 */
	private void init(String cacheName, CacheDefinition cacheDefinition) throws CacheException
	{
		//Set cache name
		this.cacheName = cacheName;
		
		//Set cache definition
		this.cacheDefinition = cacheDefinition;
		
		try
		{
			byteArrOutputStream = new ByteArrayOutputStream(65535);
			objOutputStream = new ObjectOutputStream(byteArrOutputStream);

			//Create a new channel
			JChannel channel = new JChannel();
			
			//This option means don't send to itself
			channel.setOpt(Channel.LOCAL, Boolean.FALSE);
			
			//Connect to cache chanel
			channel.connect(cacheName);

			//Create dispatcher
			dispatcher = new FwMessageDispatcher(channel, null, this, this);
			
			//If the cache is central and this instance is not a central node of cache
			if(cacheDefinition.isCentral() && !cacheDefinition.isCentralNode())
			{
				//Init central node address
				centralNodeAddress = findCentralNodeAddress(channel);
				
				if(centralNodeAddress == null)
				{
					throw new CacheException("central node not found");
				}
				
				//Always will notify to central node only
				needNotify = true;
			}
			else
			{
				//If more than 1 member is connected to the group then need to notify 
				if(channel.getView().getMembers().size() > 1)
				{
					needNotify = true;
				}
			}
		}
		catch (Throwable e)
		{
			Logger.error(CacheManager.LOGGER_CONTEXT, getClass().getName() + " failed to initialize distribution manager for cache '" + cacheName + "'", e);
			
			throw new CacheException("failed to initialize distribution manager for cache '" + cacheName + "'", e);
		} 
	}
	
	/**
	 * Finds a central node address.
	 * @return a central node address.
	 */
	private Address findCentralNodeAddress(JChannel channel)
	{
		Address address = null;
		Object response = null;
		
		//Get local address
		Address local = channel.getLocalAddress();

		try
		{
			//Get members list
			Vector allMembers = channel.getView().getMembers();
			
			//Create message body
			MessageBody msgBody = new MessageBody(null, IS_CENTRAL_NODE_OPERATION, null, null);
			
			if(allMembers != null)
			{
				//Create message
//				Message msg = new Message(null, null, objectToByteBuffer(msgBody));
				Message msg = new Message(null, null, Util.objectToByteBuffer(msgBody));
				
				//Sequential ask all members and break when receiving first not null response
				for(int i = 0; i < allMembers.size(); i++)
				{
					Address dest = (Address)allMembers.get(i);
					
					if(!dest.equals(channel.getLocalAddress()))
					{
						//Set message destination
						msg.setDest(dest);
				
						//Send the message
						response = dispatcher.sendMessage(msg, GroupRequest.GET_FIRST, 0);
					
						//If response is not null - break
						if(response != null)
						{
							address = dest;
							break;
						}
					}
				}
			}
		}
		catch (Exception e)
		{
			Logger.error(CacheManager.LOGGER_CONTEXT, getClass().getName() + ".findCentralNodeAddress() failed", e);
		}
		
		return address;
	}

	/**
	 * Sends notification to all connected members and doesn't wait for response.
	 * @param msgBody message body.
	 */
	private void post(MessageBody msgBody)
	{	
		try
		{
			//If the cache is central and this instance is not a central node of cache
			if(cacheDefinition.isCentral() && !cacheDefinition.isCentralNode())
			{
				postToCentral(msgBody);
			}
			else
			{
				postToAll(msgBody);
			}
		}
		catch (Throwable e)
		{
			Logger.error(CacheManager.LOGGER_CONTEXT, getClass().getName() + "post failed", e);
		}
	}
	
	/**
	 * Post a message to central node.
	 * @param msgBody
	 */
	private void postToCentral(MessageBody msgBody) throws Exception
	{
		if(centralNodeAddress != null)
		{
			if(dispatcher != null)
			{
				//Create message
				Message msg = new Message(centralNodeAddress, null, Util.objectToByteBuffer(msgBody));
					
				//Send the message
				dispatcher.sendMessage(msg, GroupRequest.GET_NONE, 0);
			}
			else
			{
				throw new CacheException("cannot post message to central node: dispatcher is null");
			}
		}
		else
		{
			throw new CacheException("cannot post message to central node: central node is null");
		}
	}

	/**
	 * Posts message to all members.
	 * @param msgBody
	 */
	private void postToAll(MessageBody msgBody) throws Exception
	{
		if(dispatcher != null)
		{
			//Create message
//			Message msg = new Message(null, null, objectToByteBuffer(msgBody));
			Message msg = new Message(null, null, Util.objectToByteBuffer(msgBody));
			
			//Send the message
			dispatcher.castMessage(null, msg, GroupRequest.GET_NONE, 0);
		}
		else
		{
			throw new CacheException("cannot post message: dispatcher is null");
		}
	}
	
	/**
	 * Sends notification to all connected members and waits for response.
	 * @param msgBody message body.
	 * @return response.
	 */
	private Object send(MessageBody msgBody)
	{
		Object response = null;
		
		try
		{
			//If the cache is central and this instance is not a central node of cache
			if(cacheDefinition.isCentral() && !cacheDefinition.isCentralNode())
			{
				response = sendToCentral(msgBody);
			}
			else
			{
				response = sendToAll(msgBody);
			}
		}
		catch (Throwable e)
		{
			Logger.error(CacheManager.LOGGER_CONTEXT, getClass().getName() + "send failed", e);
		}
		
		return response;
	}
	
	/**
	 * Sends a message to central node.
	 * @param msgBody
	 * @return
	 */
	private Object sendToCentral(MessageBody msgBody) throws Exception
	{
		Object response = null;
		
		if(centralNodeAddress != null)
		{
			if(dispatcher != null)
			{
				//Create message
//				Message msg = new Message(centralNodeAddress, null, objectToByteBuffer(msgBody));
				Message msg = new Message(centralNodeAddress, null, Util.objectToByteBuffer(msgBody));
				
				//Send the message
				response = dispatcher.sendMessage(msg, GroupRequest.GET_FIRST, 0);
			}
			else
			{
				throw new CacheException("cannot send message to central node: dispatcher is null");
			}
		}
		else
		{
			throw new CacheException("cannot send message to central node: central node is null");
		}
		
		return response;
	}

	/**
	 * Sends a message to all members.
	 * @param msgBody
	 * @return
	 */
	public Object sendToAll(MessageBody msgBody) throws Exception
	{
		Object response = null;
		
		Vector members = dispatcher.getChannel().getView().getMembers();
		if(members != null)
		{
			//Create message
//			Message msg = new Message(null, null, objectToByteBuffer(msgBody));
			Message msg = new Message(null, null, Util.objectToByteBuffer(msgBody));
			
			MessageDispatcher sender;
			if(cacheDefinition.isCentral() && cacheDefinition.isCentralNode())
			{
				//This prevents deadlock in central cache and this is a central node:
				//a system get stack while central node handling get or getAll, it needs to send
				//notification to other caches.
				sender = new FwMessageDispatcher(dispatcher.getChannel(), null, this, this);
			}
			else
			{
				sender = dispatcher;
			}
			
			// TODO check if should send the request to all caches together
			
			//Sequential ask all members and break when receiving first not null response
			for(int i = 0; i < members.size(); i++)
			{
				Address dest = (Address)members.get(i);
				
				if(!dest.equals(dispatcher.getChannel().getLocalAddress()))
				{
					//Set message destination
					msg.setDest(dest);
			
					//Send the message
					response = sender.sendMessage(msg, GroupRequest.GET_FIRST, 0);
				
					//If response is not null - break
					if(response != null)
					{
						break;
					}
				}
			}
		}
		else
		{
			throw new CacheException("cannot send message: no members found");
		}
		
		return response;
	}
	
	/**
	 * Passes message to all members except message sender.
	 * Relevant for cenral node only.
	 * @param msg
	 */
	private void passMessage(Message msg)
	{
		Vector members = dispatcher.getChannel().getView().getMembers();
		if((members != null) && (dispatcher != null))
		{
			//Create a new message
			Message msgToAll = new Message(null, null, msg.getBuffer());
			
			//Create a dest 
			Vector dest = (Vector)members.clone();
			
			//Remove a sender from dest list
			dest.remove(msg.getSrc());
			
			//Pass a message to all members
			dispatcher.castMessage(dest, msgToAll, GroupRequest.GET_NONE, 0);
		}
	}
	
	
	//------------------------ DisrtibutionManager implementation ---------------------------------
	/**
	 * Sends notification about put operation to all the instances of the same cache.
	 * @param entity entity name
	 * @param key
	 * @param value
	 */
	public void notifyPut(String entity, Object key, Object value)
	{
		if(needNotify)
		{
			//Create a message body
			MessageBody msgBody = new MessageBody(entity, PUT_OPERATION, key, value);
		
			//Notify all
			post(msgBody);
		}
	}

	/**
	 * @see com.ness.fw.cache.notification.DistributionManager#notifyPut(java.lang.String, java.util.Map)
	 */
	public void notifyPut(String entity, Map items)
	{
		if(needNotify)
		{
			//Create a message body
			MessageBody msgBody = new MessageBody(entity, PUT_OPERATION, items);
		
			//Notify all
			post(msgBody);
		}
	}

	/**
	 * @see com.ness.fw.cache.notification.DistributionManager#notifyPut(java.lang.String, java.util.Set)
	 */
	public void notifyPut(String entity, Set keySet)
	{
		if(needNotify)
		{
			//Create a message body
			MessageBody msgBody = new MessageBody(entity, PUT_OPERATION, new HashSet(keySet));
		
			//Notify all
			post(msgBody);
		}
	}

	/**
	 * Sends notification about putAll operation to all the instances of the same cache.
	 * @param entity entity name
	 * @param container
	 */
	public void notifyPutAll(String entity, CacheEntityContainer container)
	{
		if(needNotify)
		{
			//Create a message body
			MessageBody msgBody = new MessageBody(entity, PUT_ALL_OPERATION, container);
		
			//Notify all
			post(msgBody);
		}
	}

	/**
	 * @see com.ness.fw.cache.notification.DistributionManager#notifyPutAll(java.util.Set)
	 */
	public void notifyPutAll(String entity, Set keySet)
	{
		if(needNotify)
		{
			//Create a message body
			MessageBody msgBody = new MessageBody(entity, PUT_ALL_OPERATION, keySet);
		
			//Notify all
			post(msgBody);
		}
	}

	/**
	 * Sends notification about get operation to all the instances of the same cache.
	 * @param entity entity name
	 * @param key
	 * @return value associated with this key. 
	 */
	public Object notifyGet(String entity, Object key)
	{
		Object value = null;
		
		if(needNotify)
		{
			//Create a message body
			MessageBody msgBody = new MessageBody(entity, GET_OPERATION, key, null);
			
			//Send the message and receive response
			value = send(msgBody);
		}
		
		return value;
	}

	/**
	 * Sends notification about getAll operation to all the instances of the same cache.
	 * @param entity entity name
	 * @return
	 */
	public CacheEntityContainer notifyGetAll(String entity)
	{
		Object value = null;
		
		if(needNotify)
		{
			//Create a message body
			MessageBody msgBody = new MessageBody(entity, GET_ALL_OPERATION, null, null);
			
			//Send the message and receive response
			value = send(msgBody);
		}
		
		return (CacheEntityContainer)value;
	}

	/**
	 * Sends notification about remove operation to all the instances of the same cache.
	 * @param entity entity name
	 * @param key
	 */
	public void notifyRemove(String entity, Object key)
	{
		if(needNotify)
		{
			//Create a message body
			MessageBody msgBody = new MessageBody(entity, REMOVE_OPERATION, key, null);
			
			//Notify all
			post(msgBody);
		}
	}

	/**
	 * Sends notification about clear operation to all the instances of the same cache.
	 * @param entity entity name
	 */
	public void notifyClear(String entity)
	{
		if(needNotify)
		{
			//Create a message body
			MessageBody msgBody = new MessageBody(entity, CLEAR_OPERATION, null, null);
			
			//Notify all
			post(msgBody);
		}
	}

	/**
	 * @see com.ness.fw.cache.notification.DistributionManager#notifyKeySet()
	 */
	public Set notifyKeySet(String entity)
	{
		Set keySet = null;
		
		if(needNotify)
		{
			//Create a message body
			MessageBody msgBody = new MessageBody(entity, KEY_SET_OPERATION, null, null);
			
			//Send a message and receive the response
			keySet = (Set)send(msgBody);
		}
		
		return keySet;
	}

	/**
	 * Sets cache event handler.
	 * @param entity entity name
	 * @param handler
	 */
	public void setCacheEventHandler(CacheEventHandler handler)
	{
		this.handler = handler;
	}
	//---------------------- end of DistributedManager implementation --------------------------


	//---------------------- MembershipListener implementation ------------------------
	/**
	 * Called by JGroups to notify the target object of a change of membership.
	 * Updates the needNotify flag - true if more than 1 member connected to the cache group,
	 * false otherwise.
	 */
	public void viewAccepted(View newView)
	{
		//If the cache is central and this instance is not a central node of cache
		if(cacheDefinition.isCentral() && !cacheDefinition.isCentralNode())
		{
			if(!newView.getMembers().contains(centralNodeAddress))
			{
				centralNodeAddress = null;
				
				Logger.error(CacheManager.LOGGER_CONTEXT, "central node not exist");
			}
		}
		else
		{
			//Update the needNotify flag
			if(dispatcher.getChannel().getView().getMembers().size() > 1)
			{
				needNotify = true;
			}
			else
			{
				needNotify = false;
			}
		}
	}

	/**
	 * Called when a member is suspected.
	 */
	public void suspect(Address suspected_mbr)
	{
	}

	/**
	 * Block sending and receiving of messages until viewAccepted() is called.
	 */
	public void block()
	{
	}
	//---------------------- end of MembershipListener implementation ------------------------
	
	
	//------------------------ RequestHandler implementation -----------------------------
	/**
	 * Handles event.
	 */
	public Object handle(Message msg)
	{
		Object res = null;
		 
		try
		{
			//Get message body
			MessageBody msgBody = (MessageBody)Util.objectFromByteBuffer(msg.getBuffer());
	
			//Get entity name
			String entityName = msgBody.getEntity();
			
			//Get entity config
			CacheEntityConfig entityConfig = null;
			
			//entityName can be null only if operation is IS_CENTRAL_NODE
			if(entityName != null)
			{
				entityConfig = CacheConfigFactory.getCacheConfigManager().getCacheEntityConfig(entityName, true);
			}

			//Handle if entity size is not zero or this is central cache and central node
			if((handler != null) && ((entityConfig != null && !entityConfig.isSizeZero()) || (cacheDefinition.isCentral() && cacheDefinition.isCentralNode())))
			{
				//Get operation
				String operation = msgBody.getOperation();
	
				if(operation.equals(GET_OPERATION))
				{
					//Handle GET operation
					res = handler.handleGet(entityName, msgBody.getKey());
				}
				else if(operation.equals(GET_ALL_OPERATION))
				{
					//Handle GET_ALL operation
					res = handler.handleGetAll(entityName);
				}
				else if(operation.equals(PUT_OPERATION))
				{
					//Handle PUT operation
					handlePutEvent(msg, msgBody, entityName);
				}
				else if(operation.equals(PUT_ALL_OPERATION))
				{
					//Handle PUT_ALL operation
					handlePutAllEvent(msg, msgBody, entityName);
				}
				else if(operation.equals(REMOVE_OPERATION))
				{
					//Handle REMOVE operation
					handler.handleRemove(entityName, msgBody.getKey());
			
					//If this is a central node, pass a message to all members except message sender
					if(cacheDefinition.isCentral() && cacheDefinition.isCentralNode())
					{
						passMessage(msg);
					}
				}
				else if(operation.equals(CLEAR_OPERATION))
				{
					//Handle CLEAR operation
					handler.handleClear(entityName);
			
					//If this is a central node, pass a message to all members except message sender
					if(cacheDefinition.isCentral() && cacheDefinition.isCentralNode())
					{
						passMessage(msg);
					}
				}
				else if(operation.equals(KEY_SET_OPERATION))
				{
					//Handle KEY_SET operation
					res = handler.handleKeySet(entityName);
				}
				else if(operation.equals(IS_CENTRAL_NODE_OPERATION))
				{
					//Handle IS_CENTRAL_NODE operation
					if(cacheDefinition.isCentral() && cacheDefinition.isCentralNode())
					{
						res = Boolean.TRUE;
					}
				}
			}
		}
		catch (Exception e)
		{
			Logger.error(CacheManager.LOGGER_CONTEXT, getClass().getName() + ".handle() failed", e);
		}
	
		return res;
	}
	//------------------------ end of RequestHandler implementation -----------------------------

	/**
	 * Handles put event.
	 */
	private void handlePutEvent(Message msg, MessageBody msgBody, String entityName)
	{
		Map items = msgBody.getItems();
		Set keySet = msgBody.getKeySet();
		if(items != null)
		{
			handler.handlePut(entityName, items);
		}
		else if(keySet != null)
		{
			handler.handlePut(entityName, keySet);
		}
		else
		{
			handler.handlePut(entityName, msgBody.getKey(), msgBody.getValue());
		}
		
		//If this is a central node, pass a message to all members except message sender
		if(cacheDefinition.isCentral() && cacheDefinition.isCentralNode())
		{
			passMessage(msg);
		}
	}

	/**
	 * Handles PUT_ALL event.
	 * @param msg
	 * @param msgBody
	 * @param entityName
	 */
	private void handlePutAllEvent(Message msg, MessageBody msgBody, String entityName)
	{
		CacheEntityContainer container = msgBody.getContainer();
		Set keySet = msgBody.getKeySet();
		
		if(container != null)
		{
			//Handle PUT_ALL operation
			handler.handlePutAll(entityName, container);
		}
		else if(keySet != null)
		{
			//Handle PUT_ALL operation
			handler.handlePutAll(entityName, keySet);
		}
		else
		{
			Logger.error(CacheManager.LOGGER_CONTEXT, "missing data for handling " + PUT_ALL_OPERATION + " operation");
		}
		
		//If this is a central node, pass a message to all members except message sender
		if(cacheDefinition.isCentral() && cacheDefinition.isCentralNode())
		{
			passMessage(msg);
		}
	}

	/**
	 * Serializes an object into a byte buffer.
	 * The object has to implement interface Serializable or Externalizable
	 */
//	private static byte[] objectToByteBuffer(Object obj) throws Exception 
//	{
//		byte[] result=null;
//		
//		synchronized(byteArrOutputStream) 
//		{
//			byteArrOutputStream.reset();
//			objOutputStream.writeObject(obj);
//			result=byteArrOutputStream.toByteArray();
//			objOutputStream.reset();
//		}
//
//		return result;
//	}
}

