/**
 * HelloWorldServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package ex1.stub;

public class HelloWorldServiceLocator extends org.apache.axis.client.Service implements ex1.stub.HelloWorldService {

    // Use to get a proxy class for HelloWorld
    private final java.lang.String HelloWorld_address = "http://localhost:8080/axis/services/HelloWorld";

    public java.lang.String getHelloWorldAddress() {
        return HelloWorld_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String HelloWorldWSDDServiceName = "HelloWorld";

    public java.lang.String getHelloWorldWSDDServiceName() {
        return HelloWorldWSDDServiceName;
    }

    public void setHelloWorldWSDDServiceName(java.lang.String name) {
        HelloWorldWSDDServiceName = name;
    }

    public ex1.stub.HelloWorld getHelloWorld() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(HelloWorld_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getHelloWorld(endpoint);
    }

    public ex1.stub.HelloWorld getHelloWorld(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            ex1.stub.HelloWorldSoapBindingStub _stub = new ex1.stub.HelloWorldSoapBindingStub(portAddress, this);
            _stub.setPortName(getHelloWorldWSDDServiceName());
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
            if (ex1.stub.HelloWorld.class.isAssignableFrom(serviceEndpointInterface)) {
                ex1.stub.HelloWorldSoapBindingStub _stub = new ex1.stub.HelloWorldSoapBindingStub(new java.net.URL(HelloWorld_address), this);
                _stub.setPortName(getHelloWorldWSDDServiceName());
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
        if ("HelloWorld".equals(inputPortName)) {
            return getHelloWorld();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://localhost:8080/axis/services/HelloWorld", "HelloWorldService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("HelloWorld"));
        }
        return ports.iterator();
    }

}
