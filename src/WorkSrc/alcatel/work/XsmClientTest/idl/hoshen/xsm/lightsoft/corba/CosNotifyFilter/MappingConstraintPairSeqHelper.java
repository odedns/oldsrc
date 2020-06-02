package hoshen.xsm.lightsoft.corba.CosNotifyFilter;


/**
* hoshen/xsm/lightsoft/corba/CosNotifyFilter/MappingConstraintPairSeqHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.0"
* from lightsoft/CosNotifyFilter.idl
* 13:11:15 GMT+02:00 ��� ����� 28 ���� 2007
*/


/**
    * A sequence of <code>MappingConstraintPair</code> structures.
    */
abstract public class MappingConstraintPairSeqHelper
{
  private static String  _id = "IDL:omg.org/CosNotifyFilter/MappingConstraintPairSeq:1.0";

  public static void insert (org.omg.CORBA.Any a, hoshen.xsm.lightsoft.corba.CosNotifyFilter.MappingConstraintPair[] that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static hoshen.xsm.lightsoft.corba.CosNotifyFilter.MappingConstraintPair[] extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = hoshen.xsm.lightsoft.corba.CosNotifyFilter.MappingConstraintPairHelper.type ();
      __typeCode = org.omg.CORBA.ORB.init ().create_sequence_tc (0, __typeCode);
      __typeCode = org.omg.CORBA.ORB.init ().create_alias_tc (hoshen.xsm.lightsoft.corba.CosNotifyFilter.MappingConstraintPairSeqHelper.id (), "MappingConstraintPairSeq", __typeCode);
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static hoshen.xsm.lightsoft.corba.CosNotifyFilter.MappingConstraintPair[] read (org.omg.CORBA.portable.InputStream istream)
  {
    hoshen.xsm.lightsoft.corba.CosNotifyFilter.MappingConstraintPair value[] = null;
    int _len0 = istream.read_long ();
    value = new hoshen.xsm.lightsoft.corba.CosNotifyFilter.MappingConstraintPair[_len0];
    for (int _o1 = 0;_o1 < value.length; ++_o1)
      value[_o1] = hoshen.xsm.lightsoft.corba.CosNotifyFilter.MappingConstraintPairHelper.read (istream);
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, hoshen.xsm.lightsoft.corba.CosNotifyFilter.MappingConstraintPair[] value)
  {
    ostream.write_long (value.length);
    for (int _i0 = 0;_i0 < value.length; ++_i0)
      hoshen.xsm.lightsoft.corba.CosNotifyFilter.MappingConstraintPairHelper.write (ostream, value[_i0]);
  }

}
