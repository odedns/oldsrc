package hoshen.xsm.lightsoft.corba.CosTrading.RegisterPackage;


/**
* hoshen/xsm/lightsoft/corba/CosTrading/RegisterPackage/NoMatchingOffers.java .
* Generated by the IDL-to-Java compiler (portable), version "3.0"
* from lightsoft/CosTrading.idl
* 13:11:20 GMT+02:00 ��� ����� 28 ���� 2007
*/

public final class NoMatchingOffers extends org.omg.CORBA.UserException implements org.omg.CORBA.portable.IDLEntity
{
  public String constr = "";

  public NoMatchingOffers ()
  {
    super(NoMatchingOffersHelper.id());
  } // ctor

  public NoMatchingOffers (String _constr)
  {
    super(NoMatchingOffersHelper.id());
    constr = _constr;
  } // Init ctor

  public NoMatchingOffers (String $reason, String _constr)
  {
    super(NoMatchingOffersHelper.id() + "  " + $reason);
    constr = _constr;
  } // ctor

} // class NoMatchingOffers