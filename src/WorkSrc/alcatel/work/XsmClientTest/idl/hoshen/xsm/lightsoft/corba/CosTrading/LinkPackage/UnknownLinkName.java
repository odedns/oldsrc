package hoshen.xsm.lightsoft.corba.CosTrading.LinkPackage;


/**
* hoshen/xsm/lightsoft/corba/CosTrading/LinkPackage/UnknownLinkName.java .
* Generated by the IDL-to-Java compiler (portable), version "3.0"
* from lightsoft/CosTrading.idl
* 13:11:20 GMT+02:00 ��� ����� 28 ���� 2007
*/

public final class UnknownLinkName extends org.omg.CORBA.UserException implements org.omg.CORBA.portable.IDLEntity
{
  public String name = "";

  public UnknownLinkName ()
  {
    super(UnknownLinkNameHelper.id());
  } // ctor

  public UnknownLinkName (String _name)
  {
    super(UnknownLinkNameHelper.id());
    name = _name;
  } // Init ctor

  public UnknownLinkName (String $reason, String _name)
  {
    super(UnknownLinkNameHelper.id() + "  " + $reason);
    name = _name;
  } // ctor

} // class UnknownLinkName