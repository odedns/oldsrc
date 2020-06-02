package hoshen.xsm.lightsoft.corba.emsMgr;


/**
* hoshen/xsm/lightsoft/corba/emsMgr/EMS_THelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.0"
* from lightsoft/emsMgr.idl
* 13:11:26 GMT+02:00 ��� ����� 28 ���� 2007
*/


/**
   * <p>Holds EMS identification information.</p>
   *
   * globaldefs::NamingAttributes_T <b>name</b>:
   * <dir>Represents the friendly name of the EMS and is constructed
   * according to the following pattern:<dir>
   * <code> "<i>CompanyName</i>/<i>EMSName</i>" </code>
   * The EMSName must be unique relative to the CompanyName. It is up to each
   * company to maintain this.</dir>
   * It is a readonly attribute.</dir>
   *
   * string <b>userLabel</b>:
   * <dir>The userLabel is a friendly name that the operator wants to give
   * to the EMS. Typical expectations of the operator is that the same
   * name is seen on all operation systems. This is set by the NMS and could be
   * displayed on the EMS based on each systems'
   * capabilities. THIS IS NOT A MANDATORY EXPECTATION, but is left to the
   * implementation of the EMS. This attribute can be set
   * by NMS through the Common_I interface service
   * <a href=_common.Common_I.html#common::Common_I::setUserLabel>setUserLabel</a>. 
   * It is a read/write attribute.</dir>
   *
   * string <b>nativeEMSName</b>:
   * <dir>Represents how the EMS refers to itself on EMS displays. Its
   * aim is to provide a "nomenclature bridge" to aid relating information
   * presented on NMS displays to EMS displays (via GUI cut through).
   * May be a null string.</dir>
   *
   * string <b>owner</b>:
   * <dir>The owner is provisionable by the NMS. This attribute can be set
   * by NMS through the Common_I interface service
   * <a href=_common.Common_I.html#common::Common_I::setOwner>setOwner</a>. 
   * It is a read/write attribute.</dir>
   *
   * string <b>emsVersion</b>:
   * <dir>Software version of the EMS. This is a free format string 
   * with no semantics attached to it for the NMS. Each EMS system 
   * models its software version independently. There is no
   * standard way to represent the software version.
   * Decision about support of a particular version by the NMS system is
   * up to the NMS system. emsVersion may be an empty string.
   * It is a readonly attribute.</dir>
   *
   * string <b>type</b>:
   * <dir>Free format string indicating the type of EMS.  The EMS type may be empty string.
   * It is a readonly attribute.</dir>
   *
   * globaldefs::NVSList_T <b>additionalInfo</b>:
   * <dir>This attribute allows the communication from the EMS to the NMS of additional 
   * information which is not explicitly modelled.
   * It is a readonly attribute.</dir>
   **/
abstract public class EMS_THelper
{
  private static String  _id = "IDL:mtnm.tmforum.org/emsMgr/EMS_T:1.0";

  public static void insert (org.omg.CORBA.Any a, hoshen.xsm.lightsoft.corba.emsMgr.EMS_T that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static hoshen.xsm.lightsoft.corba.emsMgr.EMS_T extract (org.omg.CORBA.Any a)
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
          org.omg.CORBA.StructMember[] _members0 = new org.omg.CORBA.StructMember [7];
          org.omg.CORBA.TypeCode _tcOf_members0 = null;
          _tcOf_members0 = hoshen.xsm.lightsoft.corba.globaldefs.NameAndStringValue_THelper.type ();
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_sequence_tc (0, _tcOf_members0);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_alias_tc (hoshen.xsm.lightsoft.corba.globaldefs.NVSList_THelper.id (), "NVSList_T", _tcOf_members0);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_alias_tc (hoshen.xsm.lightsoft.corba.globaldefs.NamingAttributes_THelper.id (), "NamingAttributes_T", _tcOf_members0);
          _members0[0] = new org.omg.CORBA.StructMember (
            "name",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_string_tc (0);
          _members0[1] = new org.omg.CORBA.StructMember (
            "userLabel",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_string_tc (0);
          _members0[2] = new org.omg.CORBA.StructMember (
            "nativeEMSName",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_string_tc (0);
          _members0[3] = new org.omg.CORBA.StructMember (
            "owner",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_string_tc (0);
          _members0[4] = new org.omg.CORBA.StructMember (
            "emsVersion",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_string_tc (0);
          _members0[5] = new org.omg.CORBA.StructMember (
            "type",
            _tcOf_members0,
            null);
          _tcOf_members0 = hoshen.xsm.lightsoft.corba.globaldefs.NameAndStringValue_THelper.type ();
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_sequence_tc (0, _tcOf_members0);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_alias_tc (hoshen.xsm.lightsoft.corba.globaldefs.NVSList_THelper.id (), "NVSList_T", _tcOf_members0);
          _members0[6] = new org.omg.CORBA.StructMember (
            "additionalInfo",
            _tcOf_members0,
            null);
          __typeCode = org.omg.CORBA.ORB.init ().create_struct_tc (hoshen.xsm.lightsoft.corba.emsMgr.EMS_THelper.id (), "EMS_T", _members0);
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

  public static hoshen.xsm.lightsoft.corba.emsMgr.EMS_T read (org.omg.CORBA.portable.InputStream istream)
  {
    hoshen.xsm.lightsoft.corba.emsMgr.EMS_T value = new hoshen.xsm.lightsoft.corba.emsMgr.EMS_T ();
    value.name = hoshen.xsm.lightsoft.corba.globaldefs.NVSList_THelper.read (istream);
    value.userLabel = istream.read_string ();
    value.nativeEMSName = istream.read_string ();
    value.owner = istream.read_string ();
    value.emsVersion = istream.read_string ();
    value.type = istream.read_string ();
    value.additionalInfo = hoshen.xsm.lightsoft.corba.globaldefs.NVSList_THelper.read (istream);
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, hoshen.xsm.lightsoft.corba.emsMgr.EMS_T value)
  {
    hoshen.xsm.lightsoft.corba.globaldefs.NVSList_THelper.write (ostream, value.name);
    ostream.write_string (value.userLabel);
    ostream.write_string (value.nativeEMSName);
    ostream.write_string (value.owner);
    ostream.write_string (value.emsVersion);
    ostream.write_string (value.type);
    hoshen.xsm.lightsoft.corba.globaldefs.NVSList_THelper.write (ostream, value.additionalInfo);
  }

}
