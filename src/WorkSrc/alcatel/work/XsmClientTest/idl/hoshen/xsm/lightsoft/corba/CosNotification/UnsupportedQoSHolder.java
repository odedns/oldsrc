package hoshen.xsm.lightsoft.corba.CosNotification;

/**
* hoshen/xsm/lightsoft/corba/CosNotification/UnsupportedQoSHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.0"
* from lightsoft/CosNotification.idl
* 13:10:58 GMT+02:00 ��� ����� 28 ���� 2007
*/

public final class UnsupportedQoSHolder implements org.omg.CORBA.portable.Streamable
{
  public hoshen.xsm.lightsoft.corba.CosNotification.UnsupportedQoS value = null;

  public UnsupportedQoSHolder ()
  {
  }

  public UnsupportedQoSHolder (hoshen.xsm.lightsoft.corba.CosNotification.UnsupportedQoS initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = hoshen.xsm.lightsoft.corba.CosNotification.UnsupportedQoSHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    hoshen.xsm.lightsoft.corba.CosNotification.UnsupportedQoSHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return hoshen.xsm.lightsoft.corba.CosNotification.UnsupportedQoSHelper.type ();
  }

}