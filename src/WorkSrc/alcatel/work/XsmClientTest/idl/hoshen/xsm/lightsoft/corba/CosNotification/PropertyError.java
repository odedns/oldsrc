package hoshen.xsm.lightsoft.corba.CosNotification;


/**
* hoshen/xsm/lightsoft/corba/CosNotification/PropertyError.java .
* Generated by the IDL-to-Java compiler (portable), version "3.0"
* from lightsoft/CosNotification.idl
* 13:10:57 GMT+02:00 ��� ����� 28 ���� 2007
*/


/**
    * A structure used for <code>UnsupportedQoS</code> and
    * <code>UnsupportedAdmin</code> exceptions.
    */
public final class PropertyError implements org.omg.CORBA.portable.IDLEntity
{
  public hoshen.xsm.lightsoft.corba.CosNotification.QoSError_code code = null;
  public String name = "";
  public hoshen.xsm.lightsoft.corba.CosNotification.PropertyRange available_range = null;

  public PropertyError ()
  {
  } // ctor

  public PropertyError (hoshen.xsm.lightsoft.corba.CosNotification.QoSError_code _code, String _name, hoshen.xsm.lightsoft.corba.CosNotification.PropertyRange _available_range)
  {
    code = _code;
    name = _name;
    available_range = _available_range;
  } // Init ctor

} // class PropertyError
