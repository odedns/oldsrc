package hoshen.xsm.lightsoft.corba.CosTrading.LookupPackage;


/**
* hoshen/xsm/lightsoft/corba/CosTrading/LookupPackage/IllegalPreferenceHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.0"
* from lightsoft/CosTrading.idl
* 13:11:19 GMT+02:00 ��� ����� 28 ���� 2007
*/

abstract public class IllegalPreferenceHelper
{
  private static String  _id = "IDL:omg.org/CosTrading/Lookup/IllegalPreference:1.0";

  public static void insert (org.omg.CORBA.Any a, hoshen.xsm.lightsoft.corba.CosTrading.LookupPackage.IllegalPreference that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static hoshen.xsm.lightsoft.corba.CosTrading.LookupPackage.IllegalPreference extract (org.omg.CORBA.Any a)
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
          org.omg.CORBA.StructMember[] _members0 = new org.omg.CORBA.StructMember [1];
          org.omg.CORBA.TypeCode _tcOf_members0 = null;
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_string_tc (0);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_alias_tc (hoshen.xsm.lightsoft.corba.CosTrading.IstringHelper.id (), "Istring", _tcOf_members0);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_alias_tc (hoshen.xsm.lightsoft.corba.CosTrading.LookupPackage.PreferenceHelper.id (), "Preference", _tcOf_members0);
          _members0[0] = new org.omg.CORBA.StructMember (
            "pref",
            _tcOf_members0,
            null);
          __typeCode = org.omg.CORBA.ORB.init ().create_exception_tc (hoshen.xsm.lightsoft.corba.CosTrading.LookupPackage.IllegalPreferenceHelper.id (), "IllegalPreference", _members0);
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

  public static hoshen.xsm.lightsoft.corba.CosTrading.LookupPackage.IllegalPreference read (org.omg.CORBA.portable.InputStream istream)
  {
    hoshen.xsm.lightsoft.corba.CosTrading.LookupPackage.IllegalPreference value = new hoshen.xsm.lightsoft.corba.CosTrading.LookupPackage.IllegalPreference ();
    // read and discard the repository ID
    istream.read_string ();
    value.pref = istream.read_string ();
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, hoshen.xsm.lightsoft.corba.CosTrading.LookupPackage.IllegalPreference value)
  {
    // write the repository ID
    ostream.write_string (id ());
    ostream.write_string (value.pref);
  }

}
