package hoshen.xsm.lightsoft.corba.CosNotifyFilter;


/**
* hoshen/xsm/lightsoft/corba/CosNotifyFilter/FilterNotFoundHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.0"
* from lightsoft/CosNotifyFilter.idl
* 13:11:16 GMT+02:00 ��� ����� 28 ���� 2007
*/

abstract public class FilterNotFoundHelper
{
  private static String  _id = "IDL:omg.org/CosNotifyFilter/FilterNotFound:1.0";

  public static void insert (org.omg.CORBA.Any a, hoshen.xsm.lightsoft.corba.CosNotifyFilter.FilterNotFound that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static hoshen.xsm.lightsoft.corba.CosNotifyFilter.FilterNotFound extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  private static boolean __active = false;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      synchronized (org.omg.CORBA.TypeCode.class)
      {
        if (__typeCode == null)
        {
          if (__active)
          {
            return org.omg.CORBA.ORB.init().create_recursive_tc ( _id );
          }
          __active = true;
          org.omg.CORBA.StructMember[] _members0 = new org.omg.CORBA.StructMember [0];
          org.omg.CORBA.TypeCode _tcOf_members0 = null;
          __typeCode = org.omg.CORBA.ORB.init ().create_exception_tc (hoshen.xsm.lightsoft.corba.CosNotifyFilter.FilterNotFoundHelper.id (), "FilterNotFound", _members0);
          __active = false;
        }
      }
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static hoshen.xsm.lightsoft.corba.CosNotifyFilter.FilterNotFound read (org.omg.CORBA.portable.InputStream istream)
  {
    hoshen.xsm.lightsoft.corba.CosNotifyFilter.FilterNotFound value = new hoshen.xsm.lightsoft.corba.CosNotifyFilter.FilterNotFound ();
    // read and discard the repository ID
    istream.read_string ();
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, hoshen.xsm.lightsoft.corba.CosNotifyFilter.FilterNotFound value)
  {
    // write the repository ID
    ostream.write_string (id ());
  }

}