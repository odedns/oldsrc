package hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin;

/**
* hoshen/xsm/lightsoft/corba/CosNotifyChannelAdmin/ConnectionAlreadyInactiveHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.0"
* from lightsoft/CosNotifyChannelAdmin.idl
* 13:11:01 GMT+02:00 ��� ����� 28 ���� 2007
*/

public final class ConnectionAlreadyInactiveHolder implements org.omg.CORBA.portable.Streamable
{
  public hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.ConnectionAlreadyInactive value = null;

  public ConnectionAlreadyInactiveHolder ()
  {
  }

  public ConnectionAlreadyInactiveHolder (hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.ConnectionAlreadyInactive initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.ConnectionAlreadyInactiveHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.ConnectionAlreadyInactiveHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.ConnectionAlreadyInactiveHelper.type ();
  }

}
