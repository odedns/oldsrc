package hoshen.xsm.lightsoft.corba.CosTrading.ProxyPackage;

/**
* hoshen/xsm/lightsoft/corba/CosTrading/ProxyPackage/ProxyInfoHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.0"
* from lightsoft/CosTrading.idl
* 13:11:21 GMT+02:00 ��� ����� 28 ���� 2007
*/

public final class ProxyInfoHolder implements org.omg.CORBA.portable.Streamable
{
  public hoshen.xsm.lightsoft.corba.CosTrading.ProxyPackage.ProxyInfo value = null;

  public ProxyInfoHolder ()
  {
  }

  public ProxyInfoHolder (hoshen.xsm.lightsoft.corba.CosTrading.ProxyPackage.ProxyInfo initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = hoshen.xsm.lightsoft.corba.CosTrading.ProxyPackage.ProxyInfoHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    hoshen.xsm.lightsoft.corba.CosTrading.ProxyPackage.ProxyInfoHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return hoshen.xsm.lightsoft.corba.CosTrading.ProxyPackage.ProxyInfoHelper.type ();
  }

}