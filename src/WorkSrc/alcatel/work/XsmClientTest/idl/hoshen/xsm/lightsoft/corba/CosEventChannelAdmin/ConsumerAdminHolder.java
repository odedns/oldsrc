package hoshen.xsm.lightsoft.corba.CosEventChannelAdmin;

/**
* hoshen/xsm/lightsoft/corba/CosEventChannelAdmin/ConsumerAdminHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.0"
* from lightsoft/CosEventChannelAdmin.idl
* 13:10:53 GMT+02:00 ��� ����� 28 ���� 2007
*/


/**
    * Interface for the consumer administration object.
    */
public final class ConsumerAdminHolder implements org.omg.CORBA.portable.Streamable
{
  public hoshen.xsm.lightsoft.corba.CosEventChannelAdmin.ConsumerAdmin value = null;

  public ConsumerAdminHolder ()
  {
  }

  public ConsumerAdminHolder (hoshen.xsm.lightsoft.corba.CosEventChannelAdmin.ConsumerAdmin initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = hoshen.xsm.lightsoft.corba.CosEventChannelAdmin.ConsumerAdminHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    hoshen.xsm.lightsoft.corba.CosEventChannelAdmin.ConsumerAdminHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return hoshen.xsm.lightsoft.corba.CosEventChannelAdmin.ConsumerAdminHelper.type ();
  }

}
