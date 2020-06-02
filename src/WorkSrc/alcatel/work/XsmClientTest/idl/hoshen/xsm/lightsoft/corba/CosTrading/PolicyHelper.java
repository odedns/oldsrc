package hoshen.xsm.lightsoft.corba.CosTrading;


/**
* hoshen/xsm/lightsoft/corba/CosTrading/PolicyHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.0"
* from lightsoft/CosTrading.idl
* 13:11:18 GMT+02:00 ��� ����� 28 ���� 2007
*/


/** Policies determine trader behavior, for example, when
 * queries are evaluated. Policies exist to determine the
 * cardinality of offers and property matching criteria.
 */
abstract public class PolicyHelper
{
  private static String  _id = "IDL:omg.org/CosTrading/Policy:1.0";

  public static void insert (org.omg.CORBA.Any a, hoshen.xsm.lightsoft.corba.CosTrading.Policy that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static hoshen.xsm.lightsoft.corba.CosTrading.Policy extract (org.omg.CORBA.Any a)
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
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_alias_tc (hoshen.xsm.lightsoft.corba.CosTrading.PolicyNameHelper.id (), "PolicyName", _tcOf_members0);
          _members0[0] = new org.omg.CORBA.StructMember (
            "name",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_any);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_alias_tc (hoshen.xsm.lightsoft.corba.CosTrading.PolicyValueHelper.id (), "PolicyValue", _tcOf_members0);
          _members0[1] = new org.omg.CORBA.StructMember (
            "value",
            _tcOf_members0,
            null);
          __typeCode = org.omg.CORBA.ORB.init ().create_struct_tc (hoshen.xsm.lightsoft.corba.CosTrading.PolicyHelper.id (), "Policy", _members0);
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

  public static hoshen.xsm.lightsoft.corba.CosTrading.Policy read (org.omg.CORBA.portable.InputStream istream)
  {
    hoshen.xsm.lightsoft.corba.CosTrading.Policy value = new hoshen.xsm.lightsoft.corba.CosTrading.Policy ();
    value.name = istream.read_string ();
    value.value = istream.read_any ();
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, hoshen.xsm.lightsoft.corba.CosTrading.Policy value)
  {
    ostream.write_string (value.name);
    ostream.write_any (value.value);
  }

}
