package hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin;


/**
* hoshen/xsm/lightsoft/corba/CosNotifyChannelAdmin/ChannelNotFound.java .
* Generated by the IDL-to-Java compiler (portable), version "3.0"
* from lightsoft/CosNotifyChannelAdmin.idl
* 13:11:07 GMT+02:00 ��� ����� 28 ���� 2007
*/

public final class ChannelNotFound extends org.omg.CORBA.UserException implements org.omg.CORBA.portable.IDLEntity
{

  public ChannelNotFound ()
  {
    super(ChannelNotFoundHelper.id());
  } // ctor

  public ChannelNotFound (String $reason)
  {
    super(ChannelNotFoundHelper.id() + "  " + $reason);
  } // ctor

} // class ChannelNotFound