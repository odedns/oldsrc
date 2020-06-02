package usermanager;

import javax.naming.directory.*;
import javax.naming.*;

import java.util.*;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2000
 * Company:
 * @author
 * @version 1.0
 */

public class UserManager {

  private static String INITCTX= "com.sun.jndi.ldap.LdapCtxFactory";
  private static String SERVER_URL="ldap://host3:389";
  private static String ADMIN_DN="cn=Directory Manager";
  private static String ADMIN_PW="notadmin";
  private static String SEARCH_BASE="o=ginshoo.com";

  DirContext m_ctx;
  public UserManager() throws NamingException
  {
    init();
  }

  private void init() throws NamingException
  {
    Hashtable env = new Hashtable(5, 0.75f);
    env.put(Context.INITIAL_CONTEXT_FACTORY,INITCTX );
    env.put(Context.PROVIDER_URL, SERVER_URL);
    env.put(Context.SECURITY_AUTHENTICATION, "simple");
    env.put(Context.SECURITY_PRINCIPAL, ADMIN_DN);
    env.put(Context.SECURITY_CREDENTIALS, ADMIN_PW);
    /* get a handle to an Initial DirContext */
    m_ctx = new InitialDirContext(env);
  }


  public UserDetails findUser(int uid) throws NamingException
  {
      return(findUser("uid=" + uid));
  }

  public UserDetails findUser(String filter) throws NamingException
  {
    SearchControls constraints = new SearchControls();
    constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
    NamingEnumeration nm = m_ctx.search(SEARCH_BASE,filter,constraints);
    if(null != nm && nm.hasMore()) {
      SearchResult sr = (SearchResult)nm.next();
      Attributes attrs = sr.getAttributes();
      return new UserDetails(attrs);
    } else {
      return(null);
    }
  }


  public void addUser(UserDetails ud) throws NamingException
  {
	Attributes attrs = ud.getAttributes();
	m_ctx.createSubcontext("uid=" + ud.m_uid + ",ou=People," + SEARCH_BASE,attrs);

  }


  public void updateUser(UserDetails ud) throws NamingException
  {
	String dn = "uid=" + ud.m_uid + ",ou=People," + SEARCH_BASE;
	m_ctx.modifyAttributes(dn,DirContext.REPLACE_ATTRIBUTE,ud.getAttributes());
  }

  public void deleteUser(int uid) throws NamingException
  {
	String dn = "uid=" + uid + ",ou=People," + SEARCH_BASE;
	m_ctx.destroySubcontext(dn);
  }
   public void close()
   {
      try {
         m_ctx.close();
      } catch(NamingException e) {
        e.printStackTrace();
      }
   }

  public static void main(String argv[])
  {
    System.out.println("in main ..");
    try {
      UserManager um = new UserManager();
      UserDetails ud = um.findUser(-1);
      System.out.println(ud.toString());


      UserDetails ud1 = new UserDetails(1331,"oded","Tulip","tulip","tulip@ginshoo.com","tulip","9999","1",
                33333333,"kdfkfk",1,1,"Tel-Aviv","tulip","kuku",959595,1);
      um.addUser(ud1);
      System.out.println("adding user : " + ud1.toString());
      System.out.println("deleting user : " + ud1.toString());
      um.deleteUser(ud1.m_uid);

      um.close();
    } catch(Exception e) {
       e.printStackTrace();
    }

  } // main
}

