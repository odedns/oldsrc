/**
 * LocalTimeLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package com.ripedev.xsd.ZipCodeResults_xsd;

public class LocalTimeLocator extends org.apache.axis.client.Service implements com.ripedev.xsd.ZipCodeResults_xsd.LocalTime {

/**
 * Provides local time for supplied zip code.
 */

    public LocalTimeLocator() {
    }


    public LocalTimeLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public LocalTimeLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for LocalTimeSoap12
    private java.lang.String LocalTimeSoap12_address = "http://www.ripedev.com/webservices/LocalTime.asmx";

    public java.lang.String getLocalTimeSoap12Address() {
        return LocalTimeSoap12_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String LocalTimeSoap12WSDDServiceName = "LocalTimeSoap12";

    public java.lang.String getLocalTimeSoap12WSDDServiceName() {
        return LocalTimeSoap12WSDDServiceName;
    }

    public void setLocalTimeSoap12WSDDServiceName(java.lang.String name) {
        LocalTimeSoap12WSDDServiceName = name;
    }

    public com.ripedev.xsd.ZipCodeResults_xsd.LocalTimeSoap getLocalTimeSoap12() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(LocalTimeSoap12_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getLocalTimeSoap12(endpoint);
    }

    public com.ripedev.xsd.ZipCodeResults_xsd.LocalTimeSoap getLocalTimeSoap12(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.ripedev.xsd.ZipCodeResults_xsd.LocalTimeSoap12Stub _stub = new com.ripedev.xsd.ZipCodeResults_xsd.LocalTimeSoap12Stub(portAddress, this);
            _stub.setPortName(getLocalTimeSoap12WSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setLocalTimeSoap12EndpointAddress(java.lang.String address) {
        LocalTimeSoap12_address = address;
    }


    // Use to get a proxy class for LocalTimeSoap
    private java.lang.String LocalTimeSoap_address = "http://www.ripedev.com/webservices/LocalTime.asmx";

    public java.lang.String getLocalTimeSoapAddress() {
        return LocalTimeSoap_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String LocalTimeSoapWSDDServiceName = "LocalTimeSoap";

    public java.lang.String getLocalTimeSoapWSDDServiceName() {
        return LocalTimeSoapWSDDServiceName;
    }

    public void setLocalTimeSoapWSDDServiceName(java.lang.String name) {
        LocalTimeSoapWSDDServiceName = name;
    }

    public com.ripedev.xsd.ZipCodeResults_xsd.LocalTimeSoap getLocalTimeSoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(LocalTimeSoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getLocalTimeSoap(endpoint);
    }

    public com.ripedev.xsd.ZipCodeResults_xsd.LocalTimeSoap getLocalTimeSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.ripedev.xsd.ZipCodeResults_xsd.LocalTimeSoapStub _stub = new com.ripedev.xsd.ZipCodeResults_xsd.LocalTimeSoapStub(portAddress, this);
            _stub.setPortName(getLocalTimeSoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setLocalTimeSoapEndpointAddress(java.lang.String address) {
        LocalTimeSoap_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     * This service has multiple ports for a given interface;
     * the proxy implementation returned may be indeterminate.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.ripedev.xsd.ZipCodeResults_xsd.LocalTimeSoap.class.isAssignableFrom(serviceEndpointInterface)) {
                com.ripedev.xsd.ZipCodeResults_xsd.LocalTimeSoap12Stub _stub = new com.ripedev.xsd.ZipCodeResults_xsd.LocalTimeSoap12Stub(new java.net.URL(LocalTimeSoap12_address), this);
                _stub.setPortName(getLocalTimeSoap12WSDDServiceName());
                return _stub;
            }
            if (com.ripedev.xsd.ZipCodeResults_xsd.LocalTimeSoap.class.isAssignableFrom(serviceEndpointInterface)) {
                com.ripedev.xsd.ZipCodeResults_xsd.LocalTimeSoapStub _stub = new com.ripedev.xsd.ZipCodeResults_xsd.LocalTimeSoapStub(new java.net.URL(LocalTimeSoap_address), this);
                _stub.setPortName(getLocalTimeSoapWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("LocalTimeSoap12".equals(inputPortName)) {
            return getLocalTimeSoap12();
        }
        else if ("LocalTimeSoap".equals(inputPortName)) {
            return getLocalTimeSoap();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://ripedev.com/xsd/ZipCodeResults.xsd", "LocalTime");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://ripedev.com/xsd/ZipCodeResults.xsd", "LocalTimeSoap12"));
            ports.add(new javax.xml.namespace.QName("http://ripedev.com/xsd/ZipCodeResults.xsd", "LocalTimeSoap"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("LocalTimeSoap12".equals(portName)) {
            setLocalTimeSoap12EndpointAddress(address);
        }
        else 
if ("LocalTimeSoap".equals(portName)) {
            setLocalTimeSoapEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
