package hoshen.xsm.lightsoft.corba.CosTrading;


/**
* hoshen/xsm/lightsoft/corba/CosTrading/Offer.java .
* Generated by the IDL-to-Java compiler (portable), version "3.0"
* from lightsoft/CosTrading.idl
* 13:11:18 GMT+02:00 ��� ����� 28 ���� 2007
*/


/** This structure contains information about an object exported
 * by the trader. It contains a reference to the object and
 * the properties that describe the object.
 */
public final class Offer implements org.omg.CORBA.portable.IDLEntity
{
  public org.omg.CORBA.Object reference = null;
  public hoshen.xsm.lightsoft.corba.CosTrading.Property properties[] = null;

  public Offer ()
  {
  } // ctor

  public Offer (org.omg.CORBA.Object _reference, hoshen.xsm.lightsoft.corba.CosTrading.Property[] _properties)
  {
    reference = _reference;
    properties = _properties;
  } // Init ctor

} // class Offer
