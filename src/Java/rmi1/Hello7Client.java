/*
 *  Hello7Client.java
 *
 *  Hello7Client uses RMI for methods in Hello7 class.
 */


import java.rmi.*;

public class Hello7Client
{
    public static void main( String args[] )
    {
        String hostName = "sne451";

        if( args.length > 0 )
            hostName = args[0];

        System.setSecurityManager( new RMISecurityManager() );

        try
        {
            Hello7 remote = (Hello7) Naming.lookup( "rmi://" + hostName + 
			    ":5001/Hello7Server" );
            System.out.println( remote.printHello() + "    It's now " + (remote.getTime()).toString() + " at the remote object." );
        }
        catch( Exception e )
        {
            System.out.println( e.toString() );
        }
    }
}
