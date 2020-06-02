package hoshen.xsm.lightsoft.corba.emsMgr;


/**
* hoshen/xsm/lightsoft/corba/emsMgr/EMSMgr_IHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.0"
* from lightsoft/emsMgr.idl
* 13:11:27 GMT+02:00 ��� ����� 28 ���� 2007
*/


/**
   * <p>The EMSMgr_I is used to gain access to operations
   * which deal with the EMS itself.</p>
   *
   * <p>A handle to an instance of this interface is gained via the
   * <a href=_emsSession.EmsSession_I.html#emsSession::EmsSession_I::getManager>
   * getManager</a> operation in Manager.</p>
   **/
abstract public class EMSMgr_IHelper
{
  private static String  _id = "IDL:mtnm.tmforum.org/emsMgr/EMSMgr_I:1.0";

  public static void insert (org.omg.CORBA.Any a, hoshen.xsm.lightsoft.corba.emsMgr.EMSMgr_I that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static hoshen.xsm.lightsoft.corba.emsMgr.EMSMgr_I extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_interface_tc (hoshen.xsm.lightsoft.corba.emsMgr.EMSMgr_IHelper.id (), "EMSMgr_I");
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static hoshen.xsm.lightsoft.corba.emsMgr.EMSMgr_I read (org.omg.CORBA.portable.InputStream istream)
  {
    return narrow (istream.read_Object (_EMSMgr_IStub.class));
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, hoshen.xsm.lightsoft.corba.emsMgr.EMSMgr_I value)
  {
    ostream.write_Object ((org.omg.CORBA.Object) value);
  }

  public static hoshen.xsm.lightsoft.corba.emsMgr.EMSMgr_I narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof hoshen.xsm.lightsoft.corba.emsMgr.EMSMgr_I)
      return (hoshen.xsm.lightsoft.corba.emsMgr.EMSMgr_I)obj;
    else if (!obj._is_a (id ()))
      throw new org.omg.CORBA.BAD_PARAM ();
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      return new hoshen.xsm.lightsoft.corba.emsMgr._EMSMgr_IStub (delegate);
    }
  }

  public static hoshen.xsm.lightsoft.corba.emsMgr.EMSMgr_I unchecked_narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof hoshen.xsm.lightsoft.corba.emsMgr.EMSMgr_I)
      return (hoshen.xsm.lightsoft.corba.emsMgr.EMSMgr_I)obj;
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      return new hoshen.xsm.lightsoft.corba.emsMgr._EMSMgr_IStub (delegate);
    }
  }

}