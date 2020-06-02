package proxy;

import java.applet.*;
import java.io.*;
import java.util.*;
import java.net.*;
import securelink.comm.*;
import securelink.protocol.*;
import stream.*;
import util.*;
import muffin.*;
import gui.*;

/**
 * @HttpToSecureLink - Handle all coupling of HTTP protocol to the securelink.
 **/
public class HttpToSecureLink
implements ClientCallbackInterface {


 public static final String   STR_HTTP      = "http";
// public static final String   STR_URL       = "URL";
 public static final String   STR_REQUEST   = "Request";
 public static final String   STR_SEQUENCE  = "Sequence";
 public static final String   STR_METHOD    = "Method";
 public static final String   STR_GET       = "GET";
 public static final String   STR_SERVICE   = "SERVICE";
 public static final String   STR_COMMAND   = "HttpMethod";
 public static final String   STR_HST       = "hts";
 public static final String   STR_SUCCESSFUL_LOGIN = "SUCCESS";
 public static final String   STR_FAILED_LOGIN = "FAIL";


 protected ProxyFrame.NotifySLState    slStateDest;

 ClientLink clientLink;
 boolean  loginDone   = false;
 boolean  closed    = false;

 String  clientID   = null;
 String  clientPassword  = null;
 String  host    = null;
 int     port    = 0;

 // key = Long; Value = Socket
 Hashtable pending_requests;
 // Initial request sequence number
 long seq = 1; 


 Hashtable  replies;

 // Login state.
 Object     isLoginDone;
 boolean    successfulLogin;


/**
 * @HttpToSecureLink - create a link to the WebPass server that runs on the 
 *    specified host.
 * @param host - name of host on which WebPass server runs.
 * @param port - TCP port of the host machine on which WebPass server runs.
 * @param slStateDest - interface through which the state of the SecureLink 
 *    is notified.
 **/
 public HttpToSecureLink (String                 host, 
                          int                    port ,
                          ProxyFrame.NotifySLState   slStateDest)
 {
  pending_requests = new Hashtable ();
  replies = new Hashtable ();
  this.host = host;
  this.port = port;
  this.slStateDest = slStateDest;
 } 


/**
 * @login - Sends an error with "Unauthorized" code. this makes the browser 
 *    pop a login window.
 * @param client - The client from which request was sent.
 * @return - true if login was successfully.
 *           false if failed to login.
 **/
private boolean login(String id, String pass) 
{
 this.clientID = id;
 this.clientPassword = pass;
 // Initiate login sequence.
 initConnection();

 // Wait for success/failure on login attempt.
 successfulLogin = false;
 isLoginDone = new Object ();
 synchronized (isLoginDone)
   {
    try 
      {
       Main.writeDebug ("Waiting for login completion");
       isLoginDone.wait ();
       Main.writeDebug ("login completed");
      }
    catch (Exception e)
      {
       Main.writeDebug ("Exception at login attemp");
      }
   }
return (successfulLogin);
}

/**
 * @logout - logs out from WebPass server.
 **/
public void logout()
{
 if (clientLink != null)
   {
    clientLink.closeSession();
    clientLink = null; // ???
    // get rid of all securelink relaetd classes (take up a lot of space).
    System.gc();
    System.runFinalization();
   }
}

 
/**
 * @getURL - fetch the specified URL. the returned HTTP response is retrned in 
 *    an InputStream object.
 *
 * @param from_socket - The socket from which the request was read (from the 
 *    browser).
 * @param send_hash - StreamHashtable that is partialy built with parameters 
 *    to the WebPass Server. further proccessing might add more parameters
 * @param url - the URL that should be fetched.
 * @param request_body - in a "POST" msg , this is the body of the HTTP request.
 *    in a any other type of HTTP msg , this is null.
 * @param request_str - 
 *
 * @return - true if error was sent successfully to client.
 *           false failed to sent the error to client.
 **/
public InputStream getURL(Socket          from_socket, 
                          StreamHashtable send_hash ,
                          String          command ,
                          String          url, 
                          String          request_body,
                          String          request_str) 
{
 send_hash.put(STR_SEQUENCE, String.valueOf (seq));
 send_hash.put(STR_COMMAND, command);
 
  // In a "POST" HTTP-method , the form parameters are encoded in the URL.
  if (request_body != null)
    {
     // This is a POST request.
     //////////////////////////

      // replace '+' char with ' ' char
      String    noplus_str;

      // create a new  copy of the original string.
      noplus_str = request_body.replace ('+' , ' ');
      
      // Parse the String to Keys and Values separated by '&' and '='
      Main.log ("no-plus-str is:"+noplus_str);
      HttpTextMapper.extractPairs (noplus_str , send_hash);

      // replace %xx string with the ASCII char that matches xx hex value
      // e.g.  %2F is replaced with '/'
//      noplus_str = decodeNumbers (noplus_str); // This should replace the followed code
/*
      if (noplus_str.indexOf ('%') != -1)
        {
         StringBuffer    sb = new StringBuffer (noplus_str.length ());
         int             index = 0;
         int             replacedIndex;
         int             copiedUntil = 0;
         char            newChar;

         replacedIndex = 0;
         while ((index < (noplus_str.length () - 1)) &&
                (replacedIndex != -1))
           {
            replacedIndex = noplus_str.indexOf ('%',index);
            if (replacedIndex != -1)
              {
               sb.append (noplus_str.substring (copiedUntil , replacedIndex));
               newChar = (char)Integer.parseInt (noplus_str.substring (replacedIndex+1 , replacedIndex+3) , 16);
               sb.append (newChar);
               copiedUntil = replacedIndex+3;
               index = replacedIndex + 3;
              }
           }
         noplus_str = sb.toString ();
        }
*/
      // Parse the String to Keys and Values separated by '&' and '='
//      extractPairs (noplus_str , send_hash);
    }
  else
    {
     // request_body == null ==>> it means that it is a GET request.
     // The url might have a "?...." part. in case the '?' apears , it means 
     //   that key,value pairs are following it. these will be parsed into 
     //   the hashtable & sent to the server.
     /////////////////////////////////////////////////////////////////
     System.out.println ("*********** url="+url);
     System.out.println ("*********** body="+request_body);
//     System.out.println ("*********** str="+request_str);
     
    // In a "GET" HTTP-method , the form parameters are encoded in the URL.
    if (url.indexOf ('?') != -1)
       {
        String    pairs = url.substring (1+url.indexOf ('?'));
        System.out.println ("pairs string ="+pairs);
        // replace '+' char with ' ' char
        String  noPlusStr = pairs.replace('+',' ');
        System.out.println ("no plus string ="+noPlusStr);
//        String  noCodedNumbers = decodeNumbers (noPlusStr);
//        System.out.println ("no coded numbers ="+noCodedNumbers);
        // fill hash with pairs.
        HttpTextMapper.extractPairs (noPlusStr/*noCodedNumbers*/ , send_hash);
        System.out.println ("final hash"+send_hash.toString());
       }
    }

  debug("Gonna send to server the hash:"+send_hash);
  Main.log ("**** sending to server:\n"+send_hash);
  clientLink.sendMessage (send_hash);
  debug("\"clientLink.sendMessage\" returned");

  Long  currSeq = new Long (seq);
  pending_requests.put(currSeq,from_socket);
  seq ++;

  debug("Msg sent to server:"+send_hash.toString());

  // Wait (lock) thread until msg arrives.
  synchronized (from_socket)
    {
     try 
       {
        System.out.println ("Waiting for reply : seq="+(seq-1));
        from_socket.wait ();
        System.out.println ("Waiting is done   : seq="+(seq-1));
        // fetch reply & return it to the browser.
        InputStream   in = (InputStream)replies.get (currSeq);
        System.out.println ("**Returning reply stream");

        return (in);
       }
     catch (Exception e) 
       {
        System.out.println ("exception in wait for reply from ISEC "+e.getMessage());;
       }
    }
  return null;
 }


/**
 *
 **/
 public void initConnection() 
 {
  debug("init connection");

  try {
   // Tell the user that we've connected
   clientLink = new ClientLink();
   debug("allocated ClientLink");
   clientLink.registerClient(this);
   debug("registered client");
  } 
  catch (Exception e) {
   System.err.println("JSL*"+e.getMessage());
 
  }
  //debug("Got ID: "+clientID+" and Passwd: "+clientPassword);
  try {
   debug ("openSession to : host="+host+" , port="+port);
   clientLink.openSession(host,port);
  } 
  catch (NullPointerException NPE) {
   NPE.printStackTrace();
   debug("error opening session NPE " + NPE.getMessage());
  }
  catch (Exception E) {
   debug("error opening session IOE " + E.getMessage());
  }
  loginDone = false;
  closed= false;


//  ## wait
//  ## return ok/error

  debug("done initConnection");
 }

 
 public void notifyEvent(securelink.comm.Event event) 
 {
    
  String reply_html;
  Long   reply_seq;
  Socket reply_socket;


try {
  if ( event instanceof MessageEvent ) 
    {
     Hashtable message = ((MessageEvent)event).message;
     if (message == null) 
       debug ("****** message:null pointer *****");
     try 
       {
         reply_html = (String)message.get("HtmlReply");
         //debug("HtmlReply is:"+reply_html);
         reply_seq = (Long)message.get("Sequence");
         debug("Got reply:"+reply_seq);

         reply_socket = (Socket)pending_requests.get(reply_seq);
   
         if ((reply_html   == null) ||
             (reply_seq    == null)||
             (reply_socket == null))
              System.out.println ("****** null pointer*****");
         // MUFFIN CALL TO REPLY reply_html
         int     htmlLength = reply_html.length ();
         byte    htmlBytes[] = new byte [htmlLength];

//         if (reply_html.length () > 100)
//           System.out.println ("first 100 char are:"+reply_html.substring (0,100));
//         else
           System.out.println ("data is:"+reply_html);

         reply_html.getBytes (0 , reply_html.length () , htmlBytes , 0);
         ByteArrayInputStream    reply_stream = new ByteArrayInputStream (htmlBytes);

         replies.put (reply_seq , reply_stream);
         synchronized (reply_socket)
           {
       	    reply_socket.notify();
           }
         
       } 
     catch (Exception e) 
       {
         debug(" caught exception msg:"+e.getMessage());
         e.printStackTrace();
       }
    }
  else 
    if ( event instanceof RejectEvent) 
      {
      try {
       debug("Client got a REJECTED message");
       Hashtable message = ((RejectEvent)event).message;
       reply_seq = (Long)message.get("Sequence");

       String rejectDetails = ( (RejectEvent) event).details.toString();
       debug(rejectDetails);
       String error_msg = "<HTML> I-SEC Message: REJECTED\n </HTML>"+rejectDetails;
       reply_socket = (Socket)pending_requests.get(reply_seq);
   
         if ((reply_seq    == null)||
             (reply_socket == null))
              System.out.println ("****** null pointer*****");

         byte    htmlBytes[];

         reply_html = new String ("<H1> Security Error - Access Denied </H1>");
         htmlBytes = new byte [reply_html.length()];

         reply_html.getBytes (0 , reply_html.length () , htmlBytes , 0);
         ByteArrayInputStream    reply_stream = new ByteArrayInputStream (htmlBytes);

         replies.put (reply_seq , reply_stream);
         synchronized (reply_socket)
           {
       	    reply_socket.notify();
           }
       } 
     catch (Exception e) 
       {
         debug(" caught exception msg:"+e.getMessage());
         e.printStackTrace();
       }
      }
    else 
      if ( event instanceof FailedLogin) 
        {
         String error_msg = "I-SEC Message: Login Failure";
         debug("Client got a FailedLogin message");

         //MUFFIN CALL TO REPLY error_msg

         // Indicate error at login
         // Notify the waiting thead.
         successfulLogin = false;
         debug ("Notifying failed login");
         synchronized (isLoginDone)
           {
            isLoginDone.notify ();
           }
        }
      else 
        if ( event instanceof RequestClientIDEvent ) 
          {
           byte[] clientID_array = new byte[clientID.length()];
           debug(" id is:" + clientID );
           clientID.getBytes(0,clientID.length(),clientID_array,0); 
           // convert the string to bytes
           clientLink.identify(clientID_array);
          } 
        else 
          if ( event instanceof RequestAuthenTokenEvent ) 
            {
             AuthenToken token= new AuthenStaticToken();
             ((AuthenStaticToken)token).key = new byte[clientPassword.length()];
             clientPassword.getBytes(0,clientPassword.length(),((AuthenStaticToken)token).key,0); 
             clientLink.authenticate(token);
            } 
          else 
            if ( event instanceof SuccessfullLogin ) 
              {
               loginDone = true;
   
               // Indicate successfull login
               // Notify the waiting thead.
               slStateDest.notify (ProxyFrame.NotifySLState.ISEC_STATUS_CONNECTED);
               successfulLogin = true;
               System.out.println ("Notifying successfull login");
               synchronized (isLoginDone)
                 {
                  isLoginDone.notify ();
                 }

               //MsgBoxText = new String[1];
               //MsgBoxText[0] = "<CENTER><H1> Successful Login </H1>"+
               debug("login done");
              }
            else 
              if ( event instanceof RequestRandomEvent ) 
                {
                 byte[] random_data = {0,1,0,1,1,2,1,2,1};
                 clientLink.putRandom(random_data);
                } 
              else 
                if ( event instanceof ExceptionEvent ) 
                  {
                   debug("Exception is: "+event.getClass().getName()+" --> "+((ExceptionEvent)event).e.getMessage());
                  }
                else if ( event instanceof CloseSessionEvent ) 
                  { 
                   successfulLogin = false;
                   slStateDest.notify (ProxyFrame.NotifySLState.ISEC_STATUS_DISCONNECTED);
                   CloseSessionEvent csEvent = (CloseSessionEvent) event; 
                   debug("Client closed because reason " + csEvent.reason);
                   if ( csEvent.reason == CloseSessionEvent.EXCEPTION ) 
                     {
                      debug(" exception reason is "+csEvent.e.getMessage());
                     }
                   authorizationRequestedSinceLogout = false;
                   closed = true;
                  }
   } catch (Exception e) {
      debug("notify event exception:"+e.getMessage());
      e.printStackTrace();
   }
 }
 
 public static void debug(String msg) 
 {
  Main.writeDebug ("HSL> " + Thread.currentThread().getName() + ":" + msg);
 }

/* public void init() {
  
  // Parse the host+port params
  try {
   port = Integer.parseInt(getParameter("PORT"));
   host = getParameter("HOST");
  }
  catch (NumberFormatException e) {
   e.printStackTrace();
  }
  
 }
 */

/*
 * Part handling simulated login, here temporarily, until order is put
 * into this file.
 */
/*
 * Constants - HTTP protocol parts of syntax.
 */
private static final String AUTHORIZATION_DATA    = "Authorization"   ;
private static final String CHALLENGE_DATA        = "WWW-Authenticate";
private static final String BASIC_AUTH_SCHEME     = "Basic"           ;
private static final String REALM                 = "realm"           ;

private static final char   SCHEME_CRED_SEPARATOR = ' '               ;
private static final char   ID_PW_SEPARATOR       = ':'               ;
private static final int    UNAUTHORIZED_ERR_CODE = 401               ;

private static boolean authorizationRequestedSinceLogout = false;

/**
 * @handlePreLoginRequest - Handles an HTTP request issued before client
 *                          logged in, causing a simulation of web-server
 *                          login. this method can be called for every 
 *                          connection since it returns immediately when the 
 *                          client is already logged in.
 * @param client - The client that issued the request.
 * @param request - The request issued.
 * @return - true the request should be processed
 *                                          (completed successfull login).
 *           false the request should be discarded (no login yet).
 **/
public boolean handlePreLoginRequest(Client client, Request request)
{
 // Check whether we are already logged in or not. if not , log in now.
 if (successfulLogin == false)
   {
    if ( (!authorizationRequestedSinceLogout)
      ||   (!request.containsHeaderField(AUTHORIZATION_DATA)) )
      {
       Main.log ("************************");
       if ( sendUnauthorizedReply(client) )
         authorizationRequestedSinceLogout = true;
       Main.log ("************************");
         return false;
      }
    else
      {
       String AuthorizationData = request.getHeaderField(AUTHORIZATION_DATA);
       int splitPoint = AuthorizationData.indexOf(SCHEME_CRED_SEPARATOR);
       if ( splitPoint == -1 )
           return false;

       String scheme = AuthorizationData.substring(0, splitPoint);
       if ( !(scheme.equals(BASIC_AUTH_SCHEME)) )
           return false;

       String encodedCredentials = AuthorizationData.
                                              substring(splitPoint + 1);
       String clearCredentials = Base64.decode(encodedCredentials);
       splitPoint = clearCredentials.indexOf(ID_PW_SEPARATOR);
       if ( splitPoint == -1 )
           return false;
       String id = clearCredentials.substring(0, splitPoint);
       String pw = clearCredentials.substring(splitPoint + 1);

       successfulLogin = login(id, pw);
       if ( successfulLogin == false )
           sendUnauthorizedReply(client);
       return successfulLogin;
      }
   }
 // were already logged in.  
 return true;
}


/**
 * @sendUnauthorizedReply - Sends an error with "Unautorized" code,
 *                          this makes the browser pop a login window.
 * @param client - The client from which request was sent.
 * @return - true if error was sent successfully to client.
 *           false failed to sent the error to client.
 **/

private boolean sendUnauthorizedReply(Client client)
{
    HttpError  err = new HttpError(UNAUTHORIZED_ERR_CODE, "Unauthorized");
    err.getReply().setHeaderField(CHALLENGE_DATA,
                        BASIC_AUTH_SCHEME + " " + REALM + "=" + "\"eitan\"");
    Main.log ("Get Authen relpy is:"+err.getReply());
    try
    {
        client.write(err.getReply());
    }
    catch ( Exception e )
    {
        e.printStackTrace();
        // EITAN - what should be done with the exception.
        return false;
    }
    return true;
}

/*
private boolean sendUnauthorizedReply(Client client)
{
 FileInputStream     fis = null;
 
 try
 {
 fis = new FileInputStream ("RequestAuthReply.txt");
 }
 catch (FileNotFoundException e)
  {
   return false;
  }
    try
    {
     int     avail = fis.available ();
     avail ++;
     byte[]  data = new byte [avail];
     int     read;
     String  s;
     
     read = fis.read (data , 0 , avail);
     s = new String (data);
     OutputStream os = client.getOutputStream ();
     os.write (data , 0 , avail);
//     os.flush ();
//     client.write(err.getReply());
    }
    catch ( IOException e )
    {
        e.printStackTrace();
        System.out.println (e.getMessage());
        // EITAN - what should be done with the exception.
        return false;
    }
    finally
      {
       try
       {
       fis.close ();
       }
       catch (IOException e)
       {
        int i = 0;
       }
      }
    return true;
}
*/
/*
void copy (InputStream in, OutputStream out, boolean monitored) 
  throws java.io.IOException
{
	int   n;
	int   copySize = 8192;
	byte  buffer[] = new byte[copySize];

	while ((n = in.read (buffer, 0, copySize)) > 0)
  	{
	   out.write (buffer, 0, n);
	  }
	out.flush ();
}
*/

}
