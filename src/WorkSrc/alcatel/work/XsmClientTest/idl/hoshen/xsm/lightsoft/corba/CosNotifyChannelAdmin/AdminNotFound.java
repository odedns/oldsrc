package hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin;


/**
* hoshen/xsm/lightsoft/corba/CosNotifyChannelAdmin/AdminNotFound.java .
* Generated by the IDL-to-Java compiler (portable), version "3.0"
* from lightsoft/CosNotifyChannelAdmin.idl
* 13:11:07 GMT+02:00 ��� ����� 28 ���� 2007
*/

public final class AdminNotFound extends org.omg.CORBA.UserException implements org.omg.CORBA.portable.IDLEntity
{

  public AdminNotFound ()
  {
    super(AdminNotFoundHelper.id());
  } // ctor

  public AdminNotFound (String $reason)
  {
    super(AdminNotFoundHelper.id() + "  " + $reason);
  } // ctor

} // class AdminNotFound
