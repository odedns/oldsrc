package hoshen.xsm.lightsoft.corba.CosTrading.LookupPackage;


/**
* hoshen/xsm/lightsoft/corba/CosTrading/LookupPackage/HowManyPropsHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.0"
* from lightsoft/CosTrading.idl
* 13:11:19 GMT+02:00 ��� ����� 28 ���� 2007
*/


/** This enum is used to determine whether to return
 * property information with returned offers.
 * <pre>
 * none	- Do not return properties.
 * some	- Return specified properties.
 * all	- Return all properties.
 * </pre>
 */
abstract public class HowManyPropsHelper
{
  private static String  _id = "IDL:omg.org/CosTrading/Lookup/HowManyProps:1.0";

  public static void insert (org.omg.CORBA.Any a, hoshen.xsm.lightsoft.corba.CosTrading.LookupPackage.HowManyProps that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static hoshen.xsm.lightsoft.corba.CosTrading.LookupPackage.HowManyProps extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_enum_tc (hoshen.xsm.lightsoft.corba.CosTrading.LookupPackage.HowManyPropsHelper.id (), "HowManyProps", new String[] { "none", "some", "all"} );
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static hoshen.xsm.lightsoft.corba.CosTrading.LookupPackage.HowManyProps read (org.omg.CORBA.portable.InputStream istream)
  {
    return hoshen.xsm.lightsoft.corba.CosTrading.LookupPackage.HowManyProps.from_int (istream.read_long ());
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, hoshen.xsm.lightsoft.corba.CosTrading.LookupPackage.HowManyProps value)
  {
    ostream.write_long (value.value ());
  }

}
