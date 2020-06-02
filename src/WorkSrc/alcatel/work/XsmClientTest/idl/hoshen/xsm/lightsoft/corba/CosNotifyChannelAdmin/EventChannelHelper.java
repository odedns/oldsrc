package hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin;


/**
* hoshen/xsm/lightsoft/corba/CosNotifyChannelAdmin/EventChannelHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.0"
* from lightsoft/CosNotifyChannelAdmin.idl
* 13:11:07 GMT+02:00 ��� ����� 28 ���� 2007
*/


/**
    * An interface for the event channel.
    */
abstract public class EventChannelHelper
{
  private static String  _id = "IDL:omg.org/CosNotifyChannelAdmin/EventChannel:1.0";

  public static void insert (org.omg.CORBA.Any a, hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.EventChannel that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.EventChannel extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_interface_tc (hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.EventChannelHelper.id (), "EventChannel");
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.EventChannel read (org.omg.CORBA.portable.InputStream istream)
  {
    return narrow (istream.read_Object (_EventChannelStub.class));
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.EventChannel value)
  {
    ostream.write_Object ((org.omg.CORBA.Object) value);
  }

  public static hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.EventChannel narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.EventChannel)
      return (hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.EventChannel)obj;
    else if (!obj._is_a (id ()))
      throw new org.omg.CORBA.BAD_PARAM ();
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      return new hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin._EventChannelStub (delegate);
    }
  }

  public static hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.EventChannel unchecked_narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.EventChannel)
      return (hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.EventChannel)obj;
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      return new hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin._EventChannelStub (delegate);
    }
  }

}
