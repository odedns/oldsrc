/**
 * WeatherServiceService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package ex2.stub;

public interface WeatherServiceService extends javax.xml.rpc.Service {
    public java.lang.String getWeatherServiceAddress();

    public ex2.stub.WeatherService getWeatherService() throws javax.xml.rpc.ServiceException;

    public ex2.stub.WeatherService getWeatherService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
