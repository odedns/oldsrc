package hoshen.xsm.lightsoft.corba.CosTrading.LinkPackage;


/**
* hoshen/xsm/lightsoft/corba/CosTrading/LinkPackage/IllegalLinkName.java .
* Generated by the IDL-to-Java compiler (portable), version "3.0"
* from lightsoft/CosTrading.idl
* 13:11:20 GMT+02:00 ��� ����� 28 ���� 2007
*/

public final class IllegalLinkName extends org.omg.CORBA.UserException implements org.omg.CORBA.portable.IDLEntity
{
  public String name = "";

  public IllegalLinkName ()
  {
    super(IllegalLinkNameHelper.id());
  } // ctor

  public IllegalLinkName (String _name)
  {
    super(IllegalLinkNameHelper.id());
    name = _name;
  } // Init ctor

  public IllegalLinkName (String $reason, String _name)
  {
    super(IllegalLinkNameHelper.id() + "  " + $reason);
    name = _name;
  } // ctor

} // class IllegalLinkName