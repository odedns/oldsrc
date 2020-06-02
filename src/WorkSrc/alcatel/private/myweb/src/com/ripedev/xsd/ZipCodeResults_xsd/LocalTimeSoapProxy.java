package com.ripedev.xsd.ZipCodeResults_xsd;

public class LocalTimeSoapProxy implements com.ripedev.xsd.ZipCodeResults_xsd.LocalTimeSoap {
  private String _endpoint = null;
  private com.ripedev.xsd.ZipCodeResults_xsd.LocalTimeSoap localTimeSoap = null;
  
  public LocalTimeSoapProxy() {
    _initLocalTimeSoapProxy();
  }
  
  private void _initLocalTimeSoapProxy() {
    try {
      localTimeSoap = (new com.ripedev.xsd.ZipCodeResults_xsd.LocalTimeLocator()).getLocalTimeSoap();
      if (localTimeSoap != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)localTimeSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)localTimeSoap)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (localTimeSoap != null)
      ((javax.xml.rpc.Stub)localTimeSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.ripedev.xsd.ZipCodeResults_xsd.LocalTimeSoap getLocalTimeSoap() {
    if (localTimeSoap == null)
      _initLocalTimeSoapProxy();
    return localTimeSoap;
  }
  
  public java.lang.String localTimeByZipCode(java.lang.String zipCode) throws java.rmi.RemoteException{
    if (localTimeSoap == null)
      _initLocalTimeSoapProxy();
    return localTimeSoap.localTimeByZipCode(zipCode);
  }
  
  
}