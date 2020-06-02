package hoshen.xsm.lightsoft.corba.CosTrading;


/**
* hoshen/xsm/lightsoft/corba/CosTrading/PropertyTypeMismatchHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.0"
* from lightsoft/CosTrading.idl
* 13:11:18 GMT+02:00 ��� ����� 28 ���� 2007
*/

abstract public class PropertyTypeMismatchHelper
{
  private static String  _id = "IDL:omg.org/CosTrading/PropertyTypeMismatch:1.0";

  public static void insert (org.omg.CORBA.Any a, hoshen.xsm.lightsoft.corba.CosTrading.PropertyTypeMismatch that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static hoshen.xsm.lightsoft.corba.CosTrading.PropertyTypeMismatch extract (org.omg.CORBA.Any a)
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
          org.omg.CORBA.StructMember[] _members0 = new org.omg.CORBA.StructMember [2];
          org.omg.CORBA.TypeCode _tcOf_members0 = null;
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_string_tc (0);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_alias_tc (hoshen.xsm.lightsoft.corba.CosTrading.IstringHelper.id (), "Istring", _tcOf_members0);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_alias_tc (hoshen.xsm.lightsoft.corba.CosTrading.ServiceTypeNameHelper.id (), "ServiceTypeName", _tcOf_members0);
          _members0[0] = new org.omg.CORBA.StructMember (
            "type",
            _tcOf_members0,
            null);
          _tcOf_members0 = hoshen.xsm.lightsoft.corba.CosTrading.PropertyHelper.type ();
          _members0[1] = new org.omg.CORBA.StructMember (
            "prop",
            _tcOf_members0,
            null);
          __typeCode = org.omg.CORBA.ORB.init ().create_exception_tc (hoshen.xsm.lightsoft.corba.CosTrading.PropertyTypeMismatchHelper.id (), "PropertyTypeMismatch", _members0);
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

  public static hoshen.xsm.lightsoft.corba.CosTrading.PropertyTypeMismatch read (org.omg.CORBA.portable.InputStream istream)
  {
    hoshen.xsm.lightsoft.corba.CosTrading.PropertyTypeMismatch value = new hoshen.xsm.lightsoft.corba.CosTrading.PropertyTypeMismatch ();
    // read and discard the repository ID
    istream.read_string ();
    value.type = istream.read_string ();
    value.prop = hoshen.xsm.lightsoft.corba.CosTrading.PropertyHelper.read (istream);
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, hoshen.xsm.lightsoft.corba.CosTrading.PropertyTypeMismatch value)
  {
    // write the repository ID
    ostream.write_string (id ());
    ostream.write_string (value.type);
    hoshen.xsm.lightsoft.corba.CosTrading.PropertyHelper.write (ostream, value.prop);
  }

}
