/**
 * HashCmdParams.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * jdk0450.04 v122904173847
 */

package onjlib.command;

public interface HashCmdParams extends java.rmi.Remote {
    public void setParam(java.lang.String name, java.lang.Object value) throws java.rmi.RemoteException;
    public java.lang.String toString() throws java.rmi.RemoteException;
    public void setCommandClassName(java.lang.String className) throws java.rmi.RemoteException;
    public java.lang.String getCommandClassName() throws java.rmi.RemoteException;
    public java.lang.Object getParam(java.lang.String name) throws java.rmi.RemoteException;
}
