/*
 * Created on: 24/06/2003
 * Author: yifat har-nof
 * @version $Id: ContextRepositoryImpl.java,v 1.2 2005/05/08 12:49:24 yifat Exp $
 */
package com.ness.fw.flower.core;

import java.util.*;

/**
 * Used to keep all static context instances by context name.
 */
public class ContextRepositoryImpl implements ContextRepository
{
	/**
	 * A map contains the static contexts.
	 */
	private HashMap staticContexts;

	/**
	 * Constructs ContextRepositoryImpl with the list of static context's.
	 *
	 * @param contextList A {@link ContextList} contains the static contexts.
	 * @throws ContextException
	 */
	public ContextRepositoryImpl(ContextList contextList) throws ContextException
	{
		staticContexts = new HashMap();
        LinkedList contextLinkedList = new LinkedList();

		//copy the contexts into the LinkedList
		for (int i = 0; i < contextList.getContextCount(); i++)
		{
			Context ctx = contextList.getContext(i);
            contextLinkedList.add(ctx);
		}

		//run over linked list while it has elements
		while (contextLinkedList.size() > 0)
		{
			//create new iterator each time
			ListIterator listIterator = contextLinkedList.listIterator();

			//context with no parent
			boolean topContextFound = false;

			//run over the LinkedList while looking for the top context with no parent.
			while (listIterator.hasNext())
			{
				Context context = (Context) listIterator.next();
				if (context.getParentContextName() == null)
				{
					topContextFound = true;

					//removing context from linked list
					listIterator.remove();

					//lookup for all contexts children and chain them and remove them also from linked list
					chainChildren(context, contextLinkedList);
					break;
				}
			}

			if (!topContextFound)
			{
				//if no one of contexts in the list has no parent - illegal hierarchy (no root context)
				throw new ContextException("Unable to build static context hierarchy. No root context found.");
			}
		}
	}

	/**
	 * Used to retrieve static context by its name. Used by the framework while 
	 * creating new dynamic context that parent context name is specified for it.
	 *
	 * @param contextName The static context name.
	 * @return Context The static context.
	 * @throws ContextRepositoryException
	 */
	public Context getStaticContext(String contextName) throws ContextRepositoryException
	{
		Context ctx = (Context) staticContexts.get(contextName);
		if (ctx == null)
		{
			throw new ContextRepositoryException("Unable to get static context. No static context with name [" + contextName + "] is found in context repository");
		}

        return  ctx;
	}

	/**
	 * Used to retrieve the first static context. 
	 * @return Context The static context.
	 * @throws ContextRepositoryException
	 */
	public Context getFirstStaticContext() throws ContextRepositoryException
	{
		Context firstContext = null;

		if(staticContexts.size() > 0)
		{
			String contextName = (String)staticContexts.keySet().iterator().next();
			firstContext = getStaticContext(contextName);
		}
		
		return firstContext;
	}

	/**
	 * Prints the context hierarchy.
	 */
	public String toString()
	{
        LinkedList linkedList = new LinkedList();

        Iterator it = staticContexts.keySet().iterator();
		while (it.hasNext())
		{
			String contextName = (String) it.next();
            linkedList.add(staticContexts.get(contextName));
		}

		StringBuffer sb = new StringBuffer(5120);
		sb.append("Context repository:\n");

        while (linkedList.size() > 0)
		{
			ListIterator listIterator = linkedList.listIterator();
			while (listIterator.hasNext())
			{
				Context context = (Context) listIterator.next();
				if (context.getParentContextName() == null)
				{
					listIterator.remove();
					createPrintString(context, linkedList, sb, 0);
					break;
				}
			}
		}

		return sb.toString();
	}


	/**
	 * Used by <code>toString</code> method
	 * @param context {@link Context} to print.
	 * @param linkedList
	 * @param sb
	 * @param tabs
	 */	
	private void createPrintString(Context context, LinkedList linkedList, StringBuffer sb, int tabs)
	{
		for (int i = 0; i < tabs; i++)
		{
			sb.append("\t");
		}

        sb.append("[" + context.getName() + "]\n");
		ListIterator it = linkedList.listIterator();

		while (it.hasNext())
		{
			Context ctx = (Context) it.next();
			if (ctx.getParentContextName() != null && ctx.getParentContextName().equals(context.getName()))
			{
				it.remove();
				createPrintString(ctx, linkedList, sb, tabs + 1);
			}
		}
	}

	/**
	 * Used by constructor to chain the static contexts.
	 * @param rootContext The root of all the static {@link Context}s.  
	 * @param list A {@link LinkedList} contains all the static {@link Context}s.
	 */
	private void chainChildren(Context rootContext, LinkedList list)
	{
		staticContexts.put(rootContext.getName(), rootContext);

		ListIterator listIter = list.listIterator();
		//run over the list of static contexts
		while (listIter.hasNext())
		{
			Context context = (Context) listIter.next();
			String contextParentName = context.getParentContextName();

			//if context's parent context name equals to root context set root context as parent for the context
			//and remove it from the list
			if (contextParentName != null && contextParentName.equals(rootContext.getName()))
			{
				context.setParent(rootContext);

				listIter.remove();
				//recursive calling to itself with the context as argument
				chainChildren(context, list);
			}
		}
	}
}
