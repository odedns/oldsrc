package hoshen.xsm.lightsoft.corba.CosNotification;


/**
* hoshen/xsm/lightsoft/corba/CosNotification/UnsupportedAdmin.java .
* Generated by the IDL-to-Java compiler (portable), version "3.0"
* from lightsoft/CosNotification.idl
* 13:10:58 GMT+02:00 ��� ����� 28 ���� 2007
*/

public final class UnsupportedAdmin extends org.omg.CORBA.UserException implements org.omg.CORBA.portable.IDLEntity
{
  public hoshen.xsm.lightsoft.corba.CosNotification.PropertyError admin_err[] = null;

  public UnsupportedAdmin ()
  {
    super(UnsupportedAdminHelper.id());
  } // ctor

  public UnsupportedAdmin (hoshen.xsm.lightsoft.corba.CosNotification.PropertyError[] _admin_err)
  {
    super(UnsupportedAdminHelper.id());
    admin_err = _admin_err;
  } // Init ctor

  public UnsupportedAdmin (String $reason, hoshen.xsm.lightsoft.corba.CosNotification.PropertyError[] _admin_err)
  {
    super(UnsupportedAdminHelper.id() + "  " + $reason);
    admin_err = _admin_err;
  } // ctor

} // class UnsupportedAdmin
