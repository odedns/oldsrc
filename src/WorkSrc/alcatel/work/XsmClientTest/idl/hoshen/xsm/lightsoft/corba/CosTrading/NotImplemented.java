package hoshen.xsm.lightsoft.corba.CosTrading;


/**
* hoshen/xsm/lightsoft/corba/CosTrading/NotImplemented.java .
* Generated by the IDL-to-Java compiler (portable), version "3.0"
* from lightsoft/CosTrading.idl
* 13:11:18 GMT+02:00 ��� ����� 28 ���� 2007
*/

public final class NotImplemented extends org.omg.CORBA.UserException implements org.omg.CORBA.portable.IDLEntity
{

  public NotImplemented ()
  {
    super(NotImplementedHelper.id());
  } // ctor

  public NotImplemented (String $reason)
  {
    super(NotImplementedHelper.id() + "  " + $reason);
  } // ctor

} // class NotImplemented