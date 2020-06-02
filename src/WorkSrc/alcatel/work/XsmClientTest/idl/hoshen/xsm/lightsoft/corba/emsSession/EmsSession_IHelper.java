package hoshen.xsm.lightsoft.corba.emsSession;


/**
* hoshen/xsm/lightsoft/corba/emsSession/EmsSession_IHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.0"
* from lightsoft/emsSession.idl
* 13:11:31 GMT+02:00 ��� ����� 28 ���� 2007
*/


/** 
     * <p>A handle to an instance of this interface is gained via the
     * <a href=_emsSessionFactory.EmsSessionFactory_I.html#emsSessionFactory::EmsSessionFactory_I::getEmsSession>
     * getemsSession</a> operation in EmsSessionFactory_I.</p>
     */
abstract public class EmsSession_IHelper
{
  private static String  _id = "IDL:mtnm.tmforum.org/emsSession/EmsSession_I:1.0";

  public static void insert (org.omg.CORBA.Any a, hoshen.xsm.lightsoft.corba.emsSession.EmsSession_I that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static hoshen.xsm.lightsoft.corba.emsSession.EmsSession_I extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_interface_tc (hoshen.xsm.lightsoft.corba.emsSession.EmsSession_IHelper.id (), "EmsSession_I");
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static hoshen.xsm.lightsoft.corba.emsSession.EmsSession_I read (org.omg.CORBA.portable.InputStream istream)
  {
    return narrow (istream.read_Object (_EmsSession_IStub.class));
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, hoshen.xsm.lightsoft.corba.emsSession.EmsSession_I value)
  {
    ostream.write_Object ((org.omg.CORBA.Object) value);
  }

  public static hoshen.xsm.lightsoft.corba.emsSession.EmsSession_I narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof hoshen.xsm.lightsoft.corba.emsSession.EmsSession_I)
      return (hoshen.xsm.lightsoft.corba.emsSession.EmsSession_I)obj;
    else if (!obj._is_a (id ()))
      throw new org.omg.CORBA.BAD_PARAM ();
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      return new hoshen.xsm.lightsoft.corba.emsSession._EmsSession_IStub (delegate);
    }
  }

  public static hoshen.xsm.lightsoft.corba.emsSession.EmsSession_I unchecked_narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof hoshen.xsm.lightsoft.corba.emsSession.EmsSession_I)
      return (hoshen.xsm.lightsoft.corba.emsSession.EmsSession_I)obj;
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      return new hoshen.xsm.lightsoft.corba.emsSession._EmsSession_IStub (delegate);
    }
  }

}
