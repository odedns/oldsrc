/*
 *  Hello7.java
 *
 *  Hello7 interface is a Remote interface that
 *  gets implemented by the remote object, in
 *  this case the Hello7Server.
 */


import java.rmi.*;
import java.util.Date;

public interface Hello7 extends Remote
{
    String printHello() throws RemoteException;
    Date getTime() throws RemoteException;
}
