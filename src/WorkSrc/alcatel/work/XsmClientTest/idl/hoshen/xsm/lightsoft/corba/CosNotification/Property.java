package hoshen.xsm.lightsoft.corba.CosNotification;


/**
* hoshen/xsm/lightsoft/corba/CosNotification/Property.java .
* Generated by the IDL-to-Java compiler (portable), version "3.0"
* from lightsoft/CosNotification.idl
* 13:10:57 GMT+02:00 ��� ����� 28 ���� 2007
*/


/**
    * A property is a name/value pair. The name is a unique identifier for
    * the property. The value contains both the type and the value itself.
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
