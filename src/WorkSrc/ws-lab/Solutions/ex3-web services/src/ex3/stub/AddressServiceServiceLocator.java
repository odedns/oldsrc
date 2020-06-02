/**
 * AddressServiceServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package ex3.stub;

public class AddressServiceServiceLocator extends org.apache.axis.client.Service implements ex3.stub.AddressServiceService {

    // Use to get a proxy class for AddressService
    private final java.lang.String AddressService_address = "http://localhost:8080/axis/services/AddressService";

    public java.lang.String getAddressServiceAddress() {
        return AddressService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String AddressServiceWSDDServiceName = "AddressService";

    public java.lang.String getAddressServiceWSDDServiceName() {
        return AddressServiceWSDDServiceName;
    }

    public void setAddressServiceWSDDServiceName(java.lang.String name) {
        AddressServiceWSDDServiceName = name;
    }

    public ex3.stub.AddressService getAddressService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(AddressService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getAddressService(endpoint);
    }

    public ex3.stub.AddressService getAddressService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            ex3.stub.AddressServiceSoapBindingStub _stub = new ex3.stub.AddressServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getAddressServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (ex3.stub.AddressService.class.isAssignableFrom(serviceEndpointInterface)) {
                ex3.stub.AddressServiceSoapBindingStub _stub = new ex3.stub.AddressServiceSoapBindingStub(new java.net.URL(AddressService_address), this);
                _stub.setPortName(getAddressServiceWSDDServiceName());
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
        String inputPortName = portName.getLocalPart();
        if ("AddressService".equals(inputPortName)) {
            return getAddressService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://localhost:8080/axis/services/AddressService", "AddressServiceService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("AddressService"));
        }
        return ports.iterator();
    }

}
