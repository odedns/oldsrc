package hoshen.xsm.lightsoft.corba.CosEventChannelAdmin;


/**
* hoshen/xsm/lightsoft/corba/CosEventChannelAdmin/ProxyPushSupplierHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.0"
* from lightsoft/CosEventChannelAdmin.idl
* 11:11:10 IST ��� ����� 6 ������ 2005
*/


/**
    * Interface for a proxy push supplier.
    */
abstract public class ProxyPushSupplierHelper
{
  private static String  _id = "IDL:omg.org/CosEventChannelAdmin/ProxyPushSupplier:1.0";

  public static void insert (org.omg.CORBA.Any a, hoshen.xsm.lightsoft.corba.CosEventChannelAdmin.ProxyPushSupplier that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static hoshen.xsm.lightsoft.corba.CosEventChannelAdmin.ProxyPushSupplier extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_interface_tc (hoshen.xsm.lightsoft.corba.CosEventChannelAdmin.ProxyPushSupplierHelper.id (), "ProxyPushSupplier");
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static hoshen.xsm.lightsoft.corba.CosEventChannelAdmin.ProxyPushSupplier read (org.omg.CORBA.portable.InputStream istream)
  {
    return narrow (istream.read_Object (_ProxyPushSupplierStub.class));
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, hoshen.xsm.lightsoft.corba.CosEventChannelAdmin.ProxyPushSupplier value)
  {
    ostream.write_Object ((org.omg.CORBA.Object) value);
  }

  public static hoshen.xsm.lightsoft.corba.CosEventChannelAdmin.ProxyPushSupplier narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof hoshen.xsm.lightsoft.corba.CosEventChannelAdmin.ProxyPushSupplier)
      return (hoshen.xsm.lightsoft.corba.CosEventChannelAdmin.ProxyPushSupplier)obj;
    else if (!obj._is_a (id ()))
      throw new org.omg.CORBA.BAD_PARAM ();
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      return new hoshen.xsm.lightsoft.corba.CosEventChannelAdmin._ProxyPushSupplierStub (delegate);
    }
  }

}
