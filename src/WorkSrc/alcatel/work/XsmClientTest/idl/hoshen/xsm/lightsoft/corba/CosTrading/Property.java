package hoshen.xsm.lightsoft.corba.CosTrading;


/**
* hoshen/xsm/lightsoft/corba/CosTrading/Property.java .
* Generated by the IDL-to-Java compiler (portable), version "3.0"
* from lightsoft/CosTrading.idl
* 13:11:18 GMT+02:00 ��� ����� 28 ���� 2007
*/


/** A service types describes a service supporting a
 * number of properties. Properties are a name/value tuples.
 * The value field holds both the type and value of a
 * property. Property types must correspond to the type defined
 * for the property by the corresponding service type.
 */
public final class Property implements org.omg.CORBA.portable.IDLEntity
{
  public String name = "";
  public org.omg.CORBA.Any value = null;

  public Property ()
  {
  } // ctor

  public Property (String _name, org.omg.CORBA.Any _value)
  {
    name = _name;
    value = _value;
  } // Init ctor

} // class Property