package hoshen.xsm.lightsoft.corba.CosTrading.RegisterPackage;


/**
* hoshen/xsm/lightsoft/corba/CosTrading/RegisterPackage/UnknownTraderName.java .
* Generated by the IDL-to-Java compiler (portable), version "3.0"
* from lightsoft/CosTrading.idl
* 13:11:20 GMT+02:00 ��� ����� 28 ���� 2007
*/

public final class UnknownTraderName extends org.omg.CORBA.UserException implements org.omg.CORBA.portable.IDLEntity
{
  public String name[] = null;

  public UnknownTraderName ()
  {
    super(UnknownTraderNameHelper.id());
  } // ctor

  public UnknownTraderName (String[] _name)
  {
    super(UnknownTraderNameHelper.id());
    name = _name;
  } // Init ctor

  public UnknownTraderName (String $reason, String[] _name)
  {
    super(UnknownTraderNameHelper.id() + "  " + $reason);
    name = _name;
  } // ctor

} // class UnknownTraderName