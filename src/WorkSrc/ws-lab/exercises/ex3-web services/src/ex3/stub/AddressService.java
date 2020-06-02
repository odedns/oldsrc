/**
 * AddressService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package ex3.stub;

public interface AddressService extends java.rmi.Remote {
    public ex3.stub.Address getAddress(java.lang.String userName) throws java.rmi.RemoteException;
    public void addAddress(java.lang.String userName, ex3.stub.Address address) throws java.rmi.RemoteException;
}
