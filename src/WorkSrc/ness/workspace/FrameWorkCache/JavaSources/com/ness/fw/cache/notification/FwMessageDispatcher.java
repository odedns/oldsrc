/*
 * Created on: 13/02/2005
 * Author:  Alexey Levin
 * @version $Id: FwMessageDispatcher.java,v 1.1 2005/02/24 08:42:01 alexey Exp $
 */
package com.ness.fw.cache.notification;

import java.io.Serializable;

import org.jgroups.Channel;
import org.jgroups.MembershipListener;
import org.jgroups.MessageListener;
import org.jgroups.blocks.MessageDispatcher;
import org.jgroups.blocks.PullPushAdapter;
import org.jgroups.blocks.RequestHandler;

/**
 * 
 */
public class FwMessageDispatcher extends MessageDispatcher
{
	/**
	 * @param channel
	 * @param l
	 * @param l2
	 */
	public FwMessageDispatcher(
		Channel channel,
		MessageListener l,
		MembershipListener l2)
	{
		super(channel, l, l2);
	}

	/**
	 * @param channel
	 * @param l
	 * @param l2
	 * @param deadlock_detection
	 */
	public FwMessageDispatcher(
		Channel channel,
		MessageListener l,
		MembershipListener l2,
		boolean deadlock_detection)
	{
		super(channel, l, l2, deadlock_detection);
	}

	/**
	 * @param channel
	 * @param l
	 * @param l2
	 * @param deadlock_detection
	 * @param concurrent_processing
	 */
	public FwMessageDispatcher(
		Channel channel,
		MessageListener l,
		MembershipListener l2,
		boolean deadlock_detection,
		boolean concurrent_processing)
	{
		super(channel, l, l2, deadlock_detection, concurrent_processing);
	}

	/**
	 * @param channel
	 * @param l
	 * @param l2
	 * @param req_handler
	 */
	public FwMessageDispatcher(
		Channel channel,
		MessageListener l,
		MembershipListener l2,
		RequestHandler req_handler)
	{
		super(channel, l, l2, req_handler);
	}

	/**
	 * @param channel
	 * @param l
	 * @param l2
	 * @param req_handler
	 * @param deadlock_detection
	 */
	public FwMessageDispatcher(
		Channel channel,
		MessageListener l,
		MembershipListener l2,
		RequestHandler req_handler,
		boolean deadlock_detection)
	{
		super(channel, l, l2, req_handler, deadlock_detection);
	}

	/**
	 * @param channel
	 * @param l
	 * @param l2
	 * @param req_handler
	 * @param deadlock_detection
	 * @param concurrent_processing
	 */
	public FwMessageDispatcher(
		Channel channel,
		MessageListener l,
		MembershipListener l2,
		RequestHandler req_handler,
		boolean deadlock_detection,
		boolean concurrent_processing)
	{
		super(
			channel,
			l,
			l2,
			req_handler,
			deadlock_detection,
			concurrent_processing);
	}

	/**
	 * @param adapter
	 * @param id
	 * @param l
	 * @param l2
	 */
	public FwMessageDispatcher(
		PullPushAdapter adapter,
		Serializable id,
		MessageListener l,
		MembershipListener l2)
	{
		super(adapter, id, l, l2);
	}

	/**
	 * @param adapter
	 * @param id
	 * @param l
	 * @param l2
	 * @param req_handler
	 */
	public FwMessageDispatcher(
		PullPushAdapter adapter,
		Serializable id,
		MessageListener l,
		MembershipListener l2,
		RequestHandler req_handler)
	{
		super(adapter, id, l, l2, req_handler);
	}

	/**
	 * @param adapter
	 * @param id
	 * @param l
	 * @param l2
	 * @param req_handler
	 * @param concurrent_processing
	 */
	public FwMessageDispatcher(
		PullPushAdapter adapter,
		Serializable id,
		MessageListener l,
		MembershipListener l2,
		RequestHandler req_handler,
		boolean concurrent_processing)
	{
		super(adapter, id, l, l2, req_handler, concurrent_processing);
	}

	public Channel getChannel()
	{
		return channel; 
	}
}
