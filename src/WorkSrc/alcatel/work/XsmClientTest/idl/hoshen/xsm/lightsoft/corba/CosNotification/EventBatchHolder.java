package hoshen.xsm.lightsoft.corba.CosNotification;


/**
* hoshen/xsm/lightsoft/corba/CosNotification/EventBatchHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.0"
* from lightsoft/CosNotification.idl
* 13:10:58 GMT+02:00 ��� ����� 28 ���� 2007
*/


/**
    * A sequence of events used by the sequence variants of the supplier
    * and consumer interfaces.
    */
public final class EventBatchHolder implements org.omg.CORBA.portable.Streamable
{
  public hoshen.xsm.lightsoft.corba.CosNotification.StructuredEvent value[] = null;

  public EventBatchHolder ()
  {
  }

  public EventBatchHolder (hoshen.xsm.lightsoft.corba.CosNotification.StructuredEvent[] initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = hoshen.xsm.lightsoft.corba.CosNotification.EventBatchHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    hoshen.xsm.lightsoft.corba.CosNotification.EventBatchHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return hoshen.xsm.lightsoft.corba.CosNotification.EventBatchHelper.type ();
  }

}
