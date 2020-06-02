/**
 * Copyright (c) 1994-2002 Oded Nissan.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * 3. The name of the author may not be used to endorse or promote
 * products derived from this software without specific prior written
 * permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS
 * OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED.  IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS
 * OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
 * OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 *
 */
package onjlib.ejb.utils;

import javax.ejb.*;
import javax.naming.*;
import java.util.Hashtable;

/**
 * EJB utility methods.
 * @author Oded Nissan
 * @version 1.0 15/1/2002
 */
public abstract class EJBUtils {

	   static final String WLS_CTX_FACTORY = "weblogic.jndi.WLInitialContextFactory";

	   public static InitialContext getInitialContext() throws NamingException
	   {
			if(ServerContext()) {
			   return(getLocalCtx());
		}

		return(getRemoteCtx());
	   }

	   private static InitialContext getLocalCtx() throws NamingException
	   {
			return(new InitialContext());
	   }

	   private static InitialContext getRemoteCtx() throws NamingException
	   {
			Hashtable h = new Hashtable();
			h.put(Context.INITIAL_CONTEXT_FACTORY,WLS_CTX_FACTORY);
			String url = System.getProperty("server.url","t3://localhost:7001");
			h.put(Context.PROVIDER_URL, url);
			InitialContext ctx = new InitialContext(h);
			return(ctx);
	   }
	   public static boolean ServerContext()
	   {
			return(isWLS());
	   }

	   /**
		* Checks to see if the current JVM is running WLS.
		* @return boolean true if Weblogic is running.
		*/
	   public static boolean isWLS()
	   {
			boolean res = false;
			String srvName = System.getProperty("weblogic.Name");
			if(null != srvName && srvName.length()> 0) {
				res = true;
			}
			return(res);
	   }



}
