package hoshen.xsm.lightsoft.corba.CosEventChannelAdmin;


/**
* hoshen/xsm/lightsoft/corba/CosEventChannelAdmin/TypeError.java .
* Generated by the IDL-to-Java compiler (portable), version "3.0"
* from lightsoft/CosEventChannelAdmin.idl
* 11:11:10 IST ��� ����� 6 ������ 2005
*/

public final class TypeError extends org.omg.CORBA.UserException implements org.omg.CORBA.portable.IDLEntity
{

  public TypeError ()
  {
    super(TypeErrorHelper.id());
  } // ctor

  public TypeError (String $reason)
  {
    super(TypeErrorHelper.id() + "  " + $reason);
  } // ctor

} // class TypeError