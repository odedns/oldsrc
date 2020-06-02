/*
 *  Hello7Server.java
 *
 *  class Hello7Server implements the remote interface
 *  Hello7 to identify it as a remote object.
 *  Methods printHello() and getTime() are remote methods
 *  for clients to call.
 */


import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;

public class Hello7Server extends UnicastRemoteObject implements Hello7
{
    public Hello7Server() throws RemoteException
    {
        super();
    }

    public String printHello() throws RemoteException
    {
	System.out.println("server: in printHello");
        return( "Howdy!" );
    }

    public Date getTime() throws RemoteException
    {
	System.out.println("server: in getTime ");
        return( new Date( System.currentTimeMillis() ) );
    }

    public static void main( String args[] )
    {
        try
        {
            System.setSecurityManager( new RMISecurityManager() );

            Hello7Server server = new Hello7Server();
	    LocateRegistry.createRegistry(5001);
            Naming.rebind( "//:5001/Hello7Server", server );
            System.out.println( "Hello7Server :  bound in registry" );
        }
        catch( Exception e )
        {
            System.out.println( e.toString() );
	    e.printStackTrace();
        }
    }
}
