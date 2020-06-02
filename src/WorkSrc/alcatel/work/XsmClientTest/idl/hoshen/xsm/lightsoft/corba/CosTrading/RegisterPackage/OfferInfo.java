package hoshen.xsm.lightsoft.corba.CosTrading.RegisterPackage;


/**
* hoshen/xsm/lightsoft/corba/CosTrading/RegisterPackage/OfferInfo.java .
* Generated by the IDL-to-Java compiler (portable), version "3.0"
* from lightsoft/CosTrading.idl
* 13:11:20 GMT+02:00 ��� ����� 28 ���� 2007
*/


/** This structure holds information about an offer.
 * It contains a eference to the object making the
 * offer, the name of the service type it provides
 * and a set of properties it supports.
 */
public final class OfferInfo implements org.omg.CORBA.portable.IDLEntity
{
  public org.omg.CORBA.Object reference = null;
  public String type = "";
  public hoshen.xsm.lightsoft.corba.CosTrading.Property properties[] = null;

  public OfferInfo ()
  {
  } // ctor

  public OfferInfo (org.omg.CORBA.Object _reference, String _type, hoshen.xsm.lightsoft.corba.CosTrading.Property[] _properties)
  {
    reference = _reference;
    type = _type;
    properties = _properties;
  } // Init ctor

} // class OfferInfo
