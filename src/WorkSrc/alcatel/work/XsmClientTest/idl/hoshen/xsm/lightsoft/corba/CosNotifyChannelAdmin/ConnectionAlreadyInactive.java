package hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin;


/**
* hoshen/xsm/lightsoft/corba/CosNotifyChannelAdmin/ConnectionAlreadyInactive.java .
* Generated by the IDL-to-Java compiler (portable), version "3.0"
* from lightsoft/CosNotifyChannelAdmin.idl
* 13:11:01 GMT+02:00 ��� ����� 28 ���� 2007
*/

public final class ConnectionAlreadyInactive extends org.omg.CORBA.UserException implements org.omg.CORBA.portable.IDLEntity
{

  public ConnectionAlreadyInactive ()
  {
    super(ConnectionAlreadyInactiveHelper.id());
  } // ctor

  public ConnectionAlreadyInactive (String $reason)
  {
    super(ConnectionAlreadyInactiveHelper.id() + "  " + $reason);
  } // ctor

} // class ConnectionAlreadyInactive