package hoshen.xsm.lightsoft.corba.CosEventChannelAdmin;

/**
* hoshen/xsm/lightsoft/corba/CosEventChannelAdmin/ProxyPushSupplierHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.0"
* from lightsoft/CosEventChannelAdmin.idl
* 11:11:10 IST ��� ����� 6 ������ 2005
*/


/**
    * Interface for a proxy push supplier.
    */
public final class ProxyPushSupplierHolder implements org.omg.CORBA.portable.Streamable
{
  public hoshen.xsm.lightsoft.corba.CosEventChannelAdmin.ProxyPushSupplier value = null;

  public ProxyPushSupplierHolder ()
  {
  }

  public ProxyPushSupplierHolder (hoshen.xsm.lightsoft.corba.CosEventChannelAdmin.ProxyPushSupplier initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = hoshen.xsm.lightsoft.corba.CosEventChannelAdmin.ProxyPushSupplierHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    hoshen.xsm.lightsoft.corba.CosEventChannelAdmin.ProxyPushSupplierHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return hoshen.xsm.lightsoft.corba.CosEventChannelAdmin.ProxyPushSupplierHelper.type ();
  }

}
