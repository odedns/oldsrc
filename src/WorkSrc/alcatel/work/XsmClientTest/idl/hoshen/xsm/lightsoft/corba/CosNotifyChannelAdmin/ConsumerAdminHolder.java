package hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin;

/**
* hoshen/xsm/lightsoft/corba/CosNotifyChannelAdmin/ConsumerAdminHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.0"
* from lightsoft/CosNotifyChannelAdmin.idl
* 13:11:07 GMT+02:00 ��� ����� 28 ���� 2007
*/


/**
    * An interface for consumer administration objects.
    */
public final class ConsumerAdminHolder implements org.omg.CORBA.portable.Streamable
{
  public hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.ConsumerAdmin value = null;

  public ConsumerAdminHolder ()
  {
  }

  public ConsumerAdminHolder (hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.ConsumerAdmin initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.ConsumerAdminHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.ConsumerAdminHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.ConsumerAdminHelper.type ();
  }

}
