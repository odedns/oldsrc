package hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin;

/**
* hoshen/xsm/lightsoft/corba/CosNotifyChannelAdmin/StructuredProxyPushSupplierHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.0"
* from lightsoft/CosNotifyChannelAdmin.idl
* 13:11:05 GMT+02:00 ��� ����� 28 ���� 2007
*/


/**
    * An interface for structured proxy push suppliers.
    */
public final class StructuredProxyPushSupplierHolder implements org.omg.CORBA.portable.Streamable
{
  public hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.StructuredProxyPushSupplier value = null;

  public StructuredProxyPushSupplierHolder ()
  {
  }

  public StructuredProxyPushSupplierHolder (hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.StructuredProxyPushSupplier initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.StructuredProxyPushSupplierHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.StructuredProxyPushSupplierHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.StructuredProxyPushSupplierHelper.type ();
  }

}
