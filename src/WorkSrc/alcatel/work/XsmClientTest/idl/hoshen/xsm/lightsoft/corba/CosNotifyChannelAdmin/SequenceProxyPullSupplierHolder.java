package hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin;

/**
* hoshen/xsm/lightsoft/corba/CosNotifyChannelAdmin/SequenceProxyPullSupplierHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.0"
* from lightsoft/CosNotifyChannelAdmin.idl
* 13:11:04 GMT+02:00 ��� ����� 28 ���� 2007
*/


/**
    * An interface for sequence proxy pull suppliers.
    */
public final class SequenceProxyPullSupplierHolder implements org.omg.CORBA.portable.Streamable
{
  public hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.SequenceProxyPullSupplier value = null;

  public SequenceProxyPullSupplierHolder ()
  {
  }

  public SequenceProxyPullSupplierHolder (hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.SequenceProxyPullSupplier initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.SequenceProxyPullSupplierHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.SequenceProxyPullSupplierHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.SequenceProxyPullSupplierHelper.type ();
  }

}