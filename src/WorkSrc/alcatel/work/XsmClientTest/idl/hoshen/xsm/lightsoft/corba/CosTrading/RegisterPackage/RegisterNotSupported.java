package hoshen.xsm.lightsoft.corba.CosTrading.RegisterPackage;


/**
* hoshen/xsm/lightsoft/corba/CosTrading/RegisterPackage/RegisterNotSupported.java .
* Generated by the IDL-to-Java compiler (portable), version "3.0"
* from lightsoft/CosTrading.idl
* 13:11:20 GMT+02:00 ��� ����� 28 ���� 2007
*/

public final class RegisterNotSupported extends org.omg.CORBA.UserException implements org.omg.CORBA.portable.IDLEntity
{
  public String name[] = null;

  public RegisterNotSupported ()
  {
    super(RegisterNotSupportedHelper.id());
  } // ctor

  public RegisterNotSupported (String[] _name)
  {
    super(RegisterNotSupportedHelper.id());
    name = _name;
  } // Init ctor

  public RegisterNotSupported (String $reason, String[] _name)
  {
    super(RegisterNotSupportedHelper.id() + "  " + $reason);
    name = _name;
  } // ctor

} // class RegisterNotSupported