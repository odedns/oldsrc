set soapclient = CreateObject("MSSOAP.SoapClient30") 
On Error Resume Next
Call soapclient.mssoapinit("http://localhost:8080/axis/services/Version?wsdl")
if err <> 0 then
  Wscript.echo "initialization failed" + err.description
end if

result = soapclient.getVersion
wscript.echo result

if err <> 0 then
  wscript.echo err.description
end if
