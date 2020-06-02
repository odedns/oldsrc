/**
 * LocalTimeSoap.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package com.ripedev.xsd.ZipCodeResults_xsd;

public interface LocalTimeSoap extends java.rmi.Remote {

    /**
     * Returns the local time for a given zip code.
     */
    public java.lang.String localTimeByZipCode(java.lang.String zipCode) throws java.rmi.RemoteException;
}
