package hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin;

/**
* hoshen/xsm/lightsoft/corba/CosNotifyChannelAdmin/NotConnectedHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.0"
* from lightsoft/CosNotifyChannelAdmin.idl
* 13:11:01 GMT+02:00 ��� ����� 28 ���� 2007
*/

public final class NotConnectedHolder implements org.omg.CORBA.portable.Streamable
{
  public hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.NotConnected value = null;

  public NotConnectedHolder ()
  {
  }

  public NotConnectedHolder (hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.NotConnected initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.NotConnectedHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.NotConnectedHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.NotConnectedHelper.type ();
  }

}
