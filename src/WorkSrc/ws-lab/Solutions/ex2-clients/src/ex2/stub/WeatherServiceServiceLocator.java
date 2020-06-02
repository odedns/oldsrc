/**
 * WeatherServiceServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package ex2.stub;

public class WeatherServiceServiceLocator extends org.apache.axis.client.Service implements ex2.stub.WeatherServiceService {

    // Use to get a proxy class for WeatherService
    private final java.lang.String WeatherService_address = "http://localhost:8080/axis/services/WeatherService";

    public java.lang.String getWeatherServiceAddress() {
        return WeatherService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String WeatherServiceWSDDServiceName = "WeatherService";

    public java.lang.String getWeatherServiceWSDDServiceName() {
        return WeatherServiceWSDDServiceName;
    }

    public void setWeatherServiceWSDDServiceName(java.lang.String name) {
        WeatherServiceWSDDServiceName = name;
    }

    public ex2.stub.WeatherService getWeatherService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(WeatherService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getWeatherService(endpoint);
    }

    public ex2.stub.WeatherService getWeatherService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            ex2.stub.WeatherServiceSoapBindingStub _stub = new ex2.stub.WeatherServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getWeatherServiceWSDDServiceName());
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
            if (ex2.stub.WeatherService.class.isAssignableFrom(serviceEndpointInterface)) {
                ex2.stub.WeatherServiceSoapBindingStub _stub = new ex2.stub.WeatherServiceSoapBindingStub(new java.net.URL(WeatherService_address), this);
                _stub.setPortName(getWeatherServiceWSDDServiceName());
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
        if ("WeatherService".equals(inputPortName)) {
            return getWeatherService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://localhost:8080/axis/services/WeatherService", "WeatherServiceService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("WeatherService"));
        }
        return ports.iterator();
    }

}
