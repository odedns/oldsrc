package hoshen.xsm.lightsoft.corba.CosNotification;


/**
* hoshen/xsm/lightsoft/corba/CosNotification/OptionalHeaderFieldsHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.0"
* from lightsoft/CosNotification.idl
* 13:10:57 GMT+02:00 ��� ����� 28 ���� 2007
*/


/**
    * A sequence of name-value pairs used for optional event header fields.
    */
abstract public class OptionalHeaderFieldsHelper
{
  private static String  _id = "IDL:omg.org/CosNotification/OptionalHeaderFields:1.0";

  public static void insert (org.omg.CORBA.Any a, hoshen.xsm.lightsoft.corba.CosNotification.Property[] that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static hoshen.xsm.lightsoft.corba.CosNotification.Property[] extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = hoshen.xsm.lightsoft.corba.CosNotification.PropertyHelper.type ();
      __typeCode = org.omg.CORBA.ORB.init ().create_sequence_tc (0, __typeCode);
      __typeCode = org.omg.CORBA.ORB.init ().create_alias_tc (hoshen.xsm.lightsoft.corba.CosNotification.PropertySeqHelper.id (), "PropertySeq", __typeCode);
      __typeCode = org.omg.CORBA.ORB.init ().create_alias_tc (hoshen.xsm.lightsoft.corba.CosNotification.OptionalHeaderFieldsHelper.id (), "OptionalHeaderFields", __typeCode);
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static hoshen.xsm.lightsoft.corba.CosNotification.Property[] read (org.omg.CORBA.portable.InputStream istream)
  {
    hoshen.xsm.lightsoft.corba.CosNotification.Property value[] = null;
    value = hoshen.xsm.lightsoft.corba.CosNotification.PropertySeqHelper.read (istream);
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, hoshen.xsm.lightsoft.corba.CosNotification.Property[] value)
  {
    hoshen.xsm.lightsoft.corba.CosNotification.PropertySeqHelper.write (ostream, value);
  }

}
