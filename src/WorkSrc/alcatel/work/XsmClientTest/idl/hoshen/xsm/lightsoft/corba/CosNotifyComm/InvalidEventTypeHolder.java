package hoshen.xsm.lightsoft.corba.CosNotifyComm;

/**
* hoshen/xsm/lightsoft/corba/CosNotifyComm/InvalidEventTypeHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.0"
* from lightsoft/CosNotifyComm.idl
* 13:11:10 GMT+02:00 ��� ����� 28 ���� 2007
*/

public final class InvalidEventTypeHolder implements org.omg.CORBA.portable.Streamable
{
  public hoshen.xsm.lightsoft.corba.CosNotifyComm.InvalidEventType value = null;

  public InvalidEventTypeHolder ()
  {
  }

  public InvalidEventTypeHolder (hoshen.xsm.lightsoft.corba.CosNotifyComm.InvalidEventType initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = hoshen.xsm.lightsoft.corba.CosNotifyComm.InvalidEventTypeHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    hoshen.xsm.lightsoft.corba.CosNotifyComm.InvalidEventTypeHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return hoshen.xsm.lightsoft.corba.CosNotifyComm.InvalidEventTypeHelper.type ();
  }

}
