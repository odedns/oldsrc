/*
 * Created on: 24/06/2003
 * Author: yifat har-nof
 * @version $Id: ContextRepository.java,v 1.2 2005/05/08 12:49:24 yifat Exp $
 */
package com.ness.fw.flower.core;

/**
 * Used to keep all static context instances by context name.
 */
public interface ContextRepository
{
	/**
	 * Used to retrieve static context by its name. Used by the framework while 
	 * creating new dynamic context that parent context name is specified for it.
	 *
	 * @param contextName The static context name.
	 * @return Context The static context.
	 * @throws ContextRepositoryException
	 */
	public abstract Context getStaticContext(String contextName) throws ContextRepositoryException;
	
	/**
	 * Used to retrieve the first static context. 
	 * @return Context The static context.
	 * @throws ContextRepositoryException
	 */
	public abstract Context getFirstStaticContext() throws ContextRepositoryException;
	
}
