/**
 * LocalTime.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package com.ripedev.xsd.ZipCodeResults_xsd;

public interface LocalTime extends javax.xml.rpc.Service {

/**
 * Provides local time for supplied zip code.
 */
    public java.lang.String getLocalTimeSoap12Address();

    public com.ripedev.xsd.ZipCodeResults_xsd.LocalTimeSoap getLocalTimeSoap12() throws javax.xml.rpc.ServiceException;

    public com.ripedev.xsd.ZipCodeResults_xsd.LocalTimeSoap getLocalTimeSoap12(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
    public java.lang.String getLocalTimeSoapAddress();

    public com.ripedev.xsd.ZipCodeResults_xsd.LocalTimeSoap getLocalTimeSoap() throws javax.xml.rpc.ServiceException;

    public com.ripedev.xsd.ZipCodeResults_xsd.LocalTimeSoap getLocalTimeSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
