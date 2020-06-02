import amdocs.tmwww.inlinesessionbean.*;

import javax.naming.*;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.util.*;
import amdocs.tmwww.entitydata.*;
import amdocs.tmwww.gn.*;

//import amdocs.uams.*;
//import amdocs.uams.auth.*;

//import amdocs.uams.ejb.admin.*;
//import amdocs.uams.ejb.wl.WLUamsUserInfo;


import amdocs.uams.*;
import amdocs.uams.auth.*;
import amdocs.uamsimpl.client.authentication.cred.*;
//import amdocs.uamsimpl.server.ejb.admin.*;


import amdocs.tmwww.security.Password;

public class Client {

	static String url       = "t3://localhost:8111";
//	static String user      = "user";
//  	static String password  = "user";
 	
	static InvoiceData invoice;

    static Context ctx;
    static InlineSessionHome home;
    static InlineSession inline;

  	public static void main(String[] args) {
    	System.out.println("\nBeginning inlineSessionBean.Client...\n");
    	
		String passwordEncrypted = null;
		
		String user = "lenacc"; //"ll_user";
		String pswd = "lenacc";
		
		
		try {
			passwordEncrypted = Password.getInstance().encrypt(pswd);
		}	catch (Exception ex) {return;}
		

		checkClient (user,passwordEncrypted);
		//checkClient (user,pswd);
		//checkClient ("user2","user2");
		//checkClient ("cc1","cc1");
	}
		
  	public static void checkClient (String user, String password) {
    	System.out.println("\nBeginning inlineSessionBean.Client...\n");
	    try {
			UamsAuthenticationService as = UamsAuthentication.getAuthenticationService(null);
			UamsAuthenticationContext ac = as.authenticate(
						null, user, new UamsPasswordCredential(password), null, 0L, 0L, null);
			
			int status = ac.getStatus();
			
			if ( status != UamsAuthenticationContext.COMPLETE) {
				System.out.println("Authentication is failed.\n Code: " + status + "\n");
				return;
			}
/*
		switch (status){
			case ( UamsAuthenticationContext.FAILURE ):
				System.out.println("Authentication failed.\n");
				break;
			case ( UamsAuthenticationContext.REJECTED):
				System.out.println("Authentication rejected.\n");
				break;
			case ( UamsAuthenticationContext.CONTINUE_NEEDED):
				System.out.println("Authentication continue needed.\n");
				break;
			default:
				// the user has already been redirected to another page - and must stop processing
                System.out.println("Authentication status is unknown.\n");
		}
*/
			
			String ticket = ac.getTicket();
			System.out.println("Authentication is OK!");//\n Ticket: <" + ticket + ">\n");

	/*		long userStatus = ac.getUserStatus();

			System.out.println ("UserStatus . Flags:\n" + 
			(getFlags(userStatus,UamsUserInfo.ACCOUNT_ACTIVE) ? " ACCOUNT_ACTIVE\n" : "") +
			(getFlags(userStatus,UamsUserInfo.ACCOUNT_DISABLED) ? " ACCOUNT_DISABLED\n" : "") +
			(getFlags(userStatus,UamsUserInfo.ACCOUNT_LOCKED) ? " ACCOUNT_LOCKED\n" : "") +
		    (getFlags(userStatus,UamsUserInfo.ACCOUNT_NEVER_EXPIRED) ? " ACCOUNT_NEVER_EXPIRED\n" : "") +
			(getFlags(userStatus,UamsUserInfo.CREDENTIAL_EXPIRED) ? " CREDENTIAL_EXPIRED\n" : "") +
			(getFlags(userStatus,UamsUserInfo.CREDENTIAL_EXPIRING) ? " CREDENTIAL_EXPIRING\n" : ""));
*/
	    	
    		ctx = getInitialContext(ticket, password);
      		home = (InlineSessionHome) ctx.lookup("inline.InlineSessionHome");
       		inline = home.create(); 

			inline.setCustomerId ("lena");
			Hashtable h = new Hashtable();
			h.put ("1","one");
			h.put ("2","two");
			Filter fltr = new Filter (h);
			inline.setBillDetailsFilter (fltr);
			fltr = inline.getBillDetailsFilter ();
			System.out.println ("Hashtable"+fltr.getHash());
			



			//testBean_simcard ("10006100000030");

       		//inline.setUserId(user);
			//testBean_sort_position ("10006100000030");
       		//System.out.println ("Role "+ inline.getUserRole());
       		
       		//inline.setCustomerId ("ll_user");
	  		//inline.setGeneratePassword(true);
			

	  		//testBean_without_setBillDefNumber (user);
	  		//String[] str = new String[2];
	  		//s/tr[0] = new String (password+"old");
	  		//str[1] = new String (password+"New");
	
			//inline.setUserId (user);
	  		//inline.setNewPassword (str);
			//testBean_sort_position ("111111");
			//inline.setCustomerId ("cc4");


			//testBean_sort("1234560");

//			testBean ("1234560");
/*			testBean ("188765");
			testBean_2 ("001230815471503");
			testBean_position ("1234560");
			testBean_position_2 ("1234560");
			testBean_position ("111111");
			//Thread.sleep (50000);
			testBean_position_2 ("111111");
			testBean_simcard ("1234560");
			testBean_call ("1234560");
*/
  		}  	catch (Exception e) {
      				System.out.println(":::::::::::::: Unexpected Error :::::::::::::::::");
      				e.printStackTrace();
    			}
    
	}

  /**
   * Gets an initial context for the current user, password and url.
   *
   * @return                  Context
   * @exception               java.lang.Exception if there is
   *                          an error in getting the Context
   */
  static public Context getInitialContext(String user, String password) throws Exception 
  {
    Hashtable h = new Hashtable();
    h.put(Context.INITIAL_CONTEXT_FACTORY,
        "weblogic.jndi.WLInitialContextFactory");
    h.put(Context.PROVIDER_URL, url);
    if (user != null) {
      //System.out.println ("user: " + user);
      h.put(Context.SECURITY_PRINCIPAL, user);
      if (password == null) 
      password = "";
      h.put(Context.SECURITY_CREDENTIALS, password);
      h.put("url",url);
    } 
    return new InitialContext(h);
  }
  

	static void testBean_sort_position (String str) throws Exception
	{ 
  		inline.setInvoiceNumber (str);
		System.out.println ("InvoiceNumber = "+str);
		PositionsView positions= inline.getPositionsView();
		if ( positions == null)
			System.out.println ("*****  entity not found");
		else {
			System.out.println ("Number of lines ="+positions.invoicePositionDataList.length);
			for (int i=0; i<positions.invoicePositionDataList.length; i++) 
				System.out.println (""+i+" line_number="+positions.invoicePositionDataList[i].m_lineNumber + " Date="+positions.invoicePositionDataList[i].m_effective_startDate);
		}

		System.out.println ("After sort");
				
		inline.setPositionSort(8);
		positions= inline.getPositionsView();
		if ( positions == null)
			System.out.println ("*****  entity not found");
		else {
			System.out.println ("Number of lines ="+positions.invoicePositionDataList.length);
			for (int i=0; i<positions.invoicePositionDataList.length; i++) 
				System.out.println (""+i+" line_number="+positions.invoicePositionDataList[i].m_lineNumber + " Date="+positions.invoicePositionDataList[i].m_effective_startDate);
		}
		
		System.out.println ("After next sort");
				
		inline.setPositionSort(8);
		positions= inline.getPositionsView();
		if ( positions == null)
			System.out.println ("*****  entity not found");
		else {
			System.out.println ("Number of lines ="+positions.invoicePositionDataList.length);
			for (int i=0; i<positions.invoicePositionDataList.length; i++) 
				System.out.println (""+i+" line_number="+positions.invoicePositionDataList[i].m_lineNumber + " Date="+positions.invoicePositionDataList[i].m_effective_startDate);
		}
	}



	static void testBean (String str) throws Exception
	{ 
  		inline.setBillDefNumber (str);
		System.out.println ("BillDefNumber = "+str);
		InvoicesView invoices = inline.getInvoicesView();
		if (invoices == null)
			System.out.println ("Invoice not found");
		else {
			System.out.println ("Number of invoices ="+invoices.invoiceDataList.length);
			for (int i=0; i<invoices.invoiceDataList.length; i++) 
				System.out.println ("Invoice "+i+" invoice_number="+invoices.invoiceDataList[i].m_invoiceNumber);
		}
	}

	static void testBean_sort (String str) throws Exception
	{ 
  		inline.setBillDefNumber (str);
		System.out.println ("BillDefNumber = "+str);
		InvoicesView invoices = inline.getInvoicesView();
		if (invoices == null)
			System.out.println ("Invoice not found");
		else {
			System.out.println ("Number of invoices ="+invoices.invoiceDataList.length);
			for (int i=0; i<invoices.invoiceDataList.length; i++) 
				System.out.println ("Invoice "+i+" invoice_number="+invoices.invoiceDataList[i].m_invoiceNumber);
		}		
		System.out.println ("After sort");
				
		inline.setInvoiceSort(3);
		invoices = inline.getInvoicesView();
		if (invoices == null)
			System.out.println ("Invoice not found");
		else {
			System.out.println ("Number of invoices ="+invoices.invoiceDataList.length);
			for (int i=0; i<invoices.invoiceDataList.length; i++) 
				System.out.println ("Invoice "+i+" invoice_number="+invoices.invoiceDataList[i].m_invoiceNumber);
			
		}

		System.out.println ("After next sort");
				
		inline.setInvoiceSort(3);
		invoices = inline.getInvoicesView();
		if (invoices == null)
			System.out.println ("Invoice not found");
		else {
			System.out.println ("Number of invoices ="+invoices.invoiceDataList.length);
			for (int i=0; i<invoices.invoiceDataList.length; i++) 
				System.out.println ("Invoice "+i+" invoice_number="+invoices.invoiceDataList[i].m_invoiceNumber);
			
		}
	}


	static void testBean_2 (String str) throws Exception
	{ 
  		inline.setInvoiceNumber (str);
		System.out.println ("InvoiceNumber = "+str);
		invoice = inline.getInvoiceData();
		if (invoice == null)
			System.out.println ("Invoice not found");
		else {
			System.out.println ("invoice_number="+invoice.m_invoiceNumber + "account_number="+invoice.m_billDefNumber);
		}
	}


	static void testBean_position (String str) throws Exception
	{ 
  		inline.setInvoiceNumber (str);
		System.out.println ("test getPositionsView for invoiceNumber = "+str);
		PositionsView positions= inline.getPositionsView();
		if ( positions == null)
			System.out.println ("*****  entity not found");
		else {
			System.out.println ("Number of lines ="+positions.invoicePositionDataList.length);
			for (int i=0; i<positions.invoicePositionDataList.length; i++) 
				System.out.println (""+i+" line_number="+positions.invoicePositionDataList[i].m_lineNumber);
		}
	}

	static void testBean_position_2 (String str) throws Exception
	{ 
  		inline.setInvoiceNumber (str);
		System.out.println ("test getPositionsView for invoiceNumber = "+str);
		PositionsView positions= inline.getPositionsView();
		if ( positions == null)
			System.out.println ("*****  entity not found");
		else {
			System.out.println ("Number of lines ="+positions.invoicePositionDataList.length);
			for (int i=0; i<positions.invoicePositionDataList.length; i++) 
				System.out.println (""+i+" line_number="+positions.invoicePositionDataList[i].m_lineNumber);
		}
	}


	static void testBean_simcard (String str) throws Exception
	{ 
  		inline.setInvoiceNumber (str);
		System.out.println ("test getSimCardsView for invoiceNumber = "+str);
		SimCardsView simcards= inline.getSimCardsView();
		if ( simcards == null)
			System.out.println ("*****  entity not found");
		else {
			System.out.println ("Number of lines ="+simcards.invoiceSimCardDataList.length);
			for (int i=0; i<simcards.invoiceSimCardDataList.length; i++) 
				System.out.println (""+i+" sim_card="+simcards.invoiceSimCardDataList[i].m_simCard + 
						"duration="+simcards.invoiceSimCardDataList[i].m_duration);
		}
	}


	static void testBean_call (String str) throws Exception
	{ 
  		inline.setInvoiceNumber (str);
		System.out.println ("test getCallsView for invoiceNumber = "+str);
		CallsView calls= inline.getCallsView();
		if ( calls == null)
			System.out.println ("*****  entity not found");
		else {
			System.out.println ("Number of lines ="+calls.invoiceCallDataList.length);
			for (int i=0; i<calls.invoiceCallDataList.length; i++) 
				System.out.println (""+i+" line_number="+calls.invoiceCallDataList[i].m_lineNumber);
		}
	}
	
	
	
	static void testBean_without_setBillDefNumber (String user) throws Exception
	{ 
		inline.setUserId (user);
		InvoicesView invoices = inline.getInvoicesView();
		if (invoices == null)
			System.out.println ("Invoice not found");
		else {
			System.out.println ("Number of invoices ="+invoices.invoiceDataList.length);
			for (int i=0; i<invoices.invoiceDataList.length; i++) 
				System.out.println ("Invoice "+i+" invoice_number="+invoices.invoiceDataList[i].m_invoiceNumber);
		}
	}
	
	
	
	public static boolean getFlags(long userStatus,long flags)
	{
		return ((flags & ~userStatus) == 0l);
	}



}
