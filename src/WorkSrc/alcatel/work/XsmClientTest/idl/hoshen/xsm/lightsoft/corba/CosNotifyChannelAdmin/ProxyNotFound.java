package hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin;


/**
* hoshen/xsm/lightsoft/corba/CosNotifyChannelAdmin/ProxyNotFound.java .
* Generated by the IDL-to-Java compiler (portable), version "3.0"
* from lightsoft/CosNotifyChannelAdmin.idl
* 13:11:07 GMT+02:00 ��� ����� 28 ���� 2007
*/

public final class ProxyNotFound extends org.omg.CORBA.UserException implements org.omg.CORBA.portable.IDLEntity
{

  public ProxyNotFound ()
  {
    super(ProxyNotFoundHelper.id());
  } // ctor

  public ProxyNotFound (String $reason)
  {
    super(ProxyNotFoundHelper.id() + "  " + $reason);
  } // ctor

} // class ProxyNotFound
