package webservices;

import java.net.*;
import java.util.*;
import org.w3c.dom.*;
import org.apache.soap.*;
import org.apache.soap.encoding.*;
import org.apache.soap.encoding.soapenc.*;
import org.apache.soap.rpc.*;
import org.apache.soap.util.xml.*;
import org.apache.soap.messaging.*;
import org.apache.soap.transport.http.*;

public class WSBeanProxy
{
  private Call call;
  private URL url = null;
  private String stringURL = "http://localhost:9080/myweb/servlet/rpcrouter";
  private java.lang.reflect.Method setTcpNoDelayMethod;

  public WSBeanProxy()
  {
    try
    {
      setTcpNoDelayMethod = SOAPHTTPConnection.class.getMethod("setTcpNoDelay", new Class[]{Boolean.class});
    }
    catch (Exception e)
    {
    }
    call = createCall();
  }

  public synchronized void setEndPoint(URL url)
  {
    this.url = url;
  }

  public synchronized URL getEndPoint() throws MalformedURLException
  {
    return getURL();
  }

  private URL getURL() throws MalformedURLException
  {
    if (url == null && stringURL != null && stringURL.length() > 0)
    {
      url = new URL(stringURL);
    }
    return url;
  }

  public synchronized java.lang.String getData() throws Exception
  {
    String targetObjectURI = "http://tempuri.org/webservices.WSBean";
    String SOAPActionURI = "";

    if(getURL() == null)
    {
      throw new SOAPException(Constants.FAULT_CODE_CLIENT,
      "A URL must be specified via WSBeanProxy.setEndPoint(URL).");
    }

    call.setMethodName("getData");
    call.setEncodingStyleURI(Constants.NS_URI_SOAP_ENC);
    call.setTargetObjectURI(targetObjectURI);
    Vector params = new Vector();
    call.setParams(params);
    Response resp = call.invoke(getURL(), SOAPActionURI);

    //Check the response.
    if (resp.generatedFault())
    {
      Fault fault = resp.getFault();
      call.setFullTargetObjectURI(targetObjectURI);
      throw new SOAPException(fault.getFaultCode(), fault.getFaultString());
    }
    else
    {
      Parameter refValue = resp.getReturnValue();
      return ((java.lang.String)refValue.getValue());
    }
  }

  public synchronized void setData(java.lang.String data) throws Exception
  {
    String targetObjectURI = "http://tempuri.org/webservices.WSBean";
    String SOAPActionURI = "";

    if(getURL() == null)
    {
      throw new SOAPException(Constants.FAULT_CODE_CLIENT,
      "A URL must be specified via WSBeanProxy.setEndPoint(URL).");
    }

    call.setMethodName("setData");
    call.setEncodingStyleURI(Constants.NS_URI_SOAP_ENC);
    call.setTargetObjectURI(targetObjectURI);
    Vector params = new Vector();
    Parameter dataParam = new Parameter("data", java.lang.String.class, data, Constants.NS_URI_SOAP_ENC);
    params.addElement(dataParam);
    call.setParams(params);
    Response resp = call.invoke(getURL(), SOAPActionURI);

    //Check the response.
    if (resp.generatedFault())
    {
      Fault fault = resp.getFault();
      call.setFullTargetObjectURI(targetObjectURI);
      throw new SOAPException(fault.getFaultCode(), fault.getFaultString());
    }

  }

  protected Call createCall()
  {
    SOAPHTTPConnection soapHTTPConnection = new SOAPHTTPConnection();
    if ( setTcpNoDelayMethod != null)
    {
      try
      {
        setTcpNoDelayMethod.invoke(soapHTTPConnection, new Object[]{Boolean.TRUE});
      }
      catch (Exception ex)
      {
      }
    }
    Call call = new Call();
    call.setSOAPTransport(soapHTTPConnection);
    SOAPMappingRegistry smr = call.getSOAPMappingRegistry();
    return call;
  }
}
