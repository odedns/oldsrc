package hoshen.xsm.lightsoft.corba.CosNotification;

/**
* hoshen/xsm/lightsoft/corba/CosNotification/EventHeaderHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.0"
* from lightsoft/CosNotification.idl
* 13:10:58 GMT+02:00 ��� ����� 28 ���� 2007
*/


/**
    * The event header. A two-tuple consisting of the fixed header
    * and an optional header.
    */
public final class EventHeaderHolder implements org.omg.CORBA.portable.Streamable
{
  public hoshen.xsm.lightsoft.corba.CosNotification.EventHeader value = null;

  public EventHeaderHolder ()
  {
  }

  public EventHeaderHolder (hoshen.xsm.lightsoft.corba.CosNotification.EventHeader initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = hoshen.xsm.lightsoft.corba.CosNotification.EventHeaderHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    hoshen.xsm.lightsoft.corba.CosNotification.EventHeaderHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return hoshen.xsm.lightsoft.corba.CosNotification.EventHeaderHelper.type ();
  }

}
