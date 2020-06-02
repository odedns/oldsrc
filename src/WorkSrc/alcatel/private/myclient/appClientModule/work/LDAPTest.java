package work;

 
import java.util.Hashtable;
import java.util.Enumeration;

import javax.naming.*;
import javax.naming.directory.*;

/*
/**
 * @author o000131
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class LDAPTest {
//ldap://10.18.188.135:389/CN=Users,DC=FIBI,DC=Corp??sub?objectclass=User

	public static void main(String[] args) {
		
	    Hashtable env = new Hashtable(5, 0.75f);    
    	env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");

	    /* Specify host and port to use for directory service */
    	env.put(Context.PROVIDER_URL, "ldap://10.18.188.135:389");
    	env.put(Context.SECURITY_PRINCIPAL,"D797MC01\\o000131");
    	env.put(Context.SECURITY_CREDENTIALS,"odedn03");
	    try {
    	    /* get a handle to an Initial DirContext */
        	DirContext ctx = new InitialDirContext(env);

	        /* specify search constraints to search subtree */
    	    SearchControls constraints = new SearchControls();
        	constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);

			//String filter = "(&(objectCategory=User) (name=*));";
			String filter = "objectClass=User";
			String dn = "CN=Users,DC=FIBI,DC=Corp";			
//			String dn = "DC=FIBI,DC=Corp";			
	        /* search for all entries with surname of Jensen */
    	    NamingEnumeration results
	            = ctx.search(dn, filter, constraints);
			
			if(null != results) {
				System.out.println("results =" + results.toString());	
			}
	        /* for each entry print out name + all attrs and values */
    	    while (results != null && results.hasMore()) {
	            SearchResult si = (SearchResult)results.next();

	            /* print its name */
    	        System.out.println("name: " + si.getName());

	            Attributes attrs = si.getAttributes();
    	        if (attrs == null) {
        	        System.out.println("No attributes");
            	} else {
                	/* print each attribute */
                	for (NamingEnumeration ae = attrs.getAll();
                    	 ae.hasMoreElements();) {
	                    Attribute attr = (Attribute)ae.next();
    	                String attrId = attr.getID();

	                    /* print each value */
    	                for (Enumeration vals = attr.getAll();
        	                 vals.hasMoreElements();
            	             System.out.println(attrId + ": " + vals.nextElement()))
                            ;
             	   	} // for
            	} // if
         	   System.out.println();
         	   ctx.close();
        	} //while
	    } catch (NamingException e) {
    	    System.err.println("Search example failed.");
        	e.printStackTrace();
    	}
	} // main

}  // class
