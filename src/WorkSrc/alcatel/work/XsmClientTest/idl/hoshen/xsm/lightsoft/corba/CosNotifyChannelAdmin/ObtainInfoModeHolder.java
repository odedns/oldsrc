package hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin;

/**
* hoshen/xsm/lightsoft/corba/CosNotifyChannelAdmin/ObtainInfoModeHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.0"
* from lightsoft/CosNotifyChannelAdmin.idl
* 13:11:01 GMT+02:00 ��� ����� 28 ���� 2007
*/


/**
    * Enumeration of modes for obtaining subscription or offered types.
    */
public final class ObtainInfoModeHolder implements org.omg.CORBA.portable.Streamable
{
  public hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.ObtainInfoMode value = null;

  public ObtainInfoModeHolder ()
  {
  }

  public ObtainInfoModeHolder (hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.ObtainInfoMode initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.ObtainInfoModeHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.ObtainInfoModeHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.ObtainInfoModeHelper.type ();
  }

}