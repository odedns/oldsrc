package hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin;

/**
* hoshen/xsm/lightsoft/corba/CosNotifyChannelAdmin/SequenceProxyPullConsumerHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.0"
* from lightsoft/CosNotifyChannelAdmin.idl
* 13:11:05 GMT+02:00 ��� ����� 28 ���� 2007
*/


/**
    * An interface for sequence proxy pull consumers.
    */
public final class SequenceProxyPullConsumerHolder implements org.omg.CORBA.portable.Streamable
{
  public hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.SequenceProxyPullConsumer value = null;

  public SequenceProxyPullConsumerHolder ()
  {
  }

  public SequenceProxyPullConsumerHolder (hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.SequenceProxyPullConsumer initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.SequenceProxyPullConsumerHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.SequenceProxyPullConsumerHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.SequenceProxyPullConsumerHelper.type ();
  }

}