package hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin;


/**
* hoshen/xsm/lightsoft/corba/CosNotifyChannelAdmin/NotConnected.java .
* Generated by the IDL-to-Java compiler (portable), version "3.0"
* from lightsoft/CosNotifyChannelAdmin.idl
* 13:11:01 GMT+02:00 ��� ����� 28 ���� 2007
*/

public final class NotConnected extends org.omg.CORBA.UserException implements org.omg.CORBA.portable.IDLEntity
{

  public NotConnected ()
  {
    super(NotConnectedHelper.id());
  } // ctor

  public NotConnected (String $reason)
  {
    super(NotConnectedHelper.id() + "  " + $reason);
  } // ctor

} // class NotConnected
