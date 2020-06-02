package hoshen.xsm.lightsoft.corba.CosNotification;


/**
* hoshen/xsm/lightsoft/corba/CosNotification/QoSError_codeHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.0"
* from lightsoft/CosNotification.idl
* 13:10:57 GMT+02:00 ��� ����� 28 ���� 2007
*/


/**
    * An enumeration of quality of service error codes.
    */
abstract public class QoSError_codeHelper
{
  private static String  _id = "IDL:omg.org/CosNotification/QoSError_code:1.0";

  public static void insert (org.omg.CORBA.Any a, hoshen.xsm.lightsoft.corba.CosNotification.QoSError_code that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static hoshen.xsm.lightsoft.corba.CosNotification.QoSError_code extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_enum_tc (hoshen.xsm.lightsoft.corba.CosNotification.QoSError_codeHelper.id (), "QoSError_code", new String[] { "UNSUPPORTED_PROPERTY", "UNAVAILABLE_PROPERTY", "UNSUPPORTED_VALUE", "UNAVAILABLE_VALUE", "BAD_PROPERTY", "BAD_TYPE", "BAD_VALUE"} );
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static hoshen.xsm.lightsoft.corba.CosNotification.QoSError_code read (org.omg.CORBA.portable.InputStream istream)
  {
    return hoshen.xsm.lightsoft.corba.CosNotification.QoSError_code.from_int (istream.read_long ());
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, hoshen.xsm.lightsoft.corba.CosNotification.QoSError_code value)
  {
    ostream.write_long (value.value ());
  }

}
