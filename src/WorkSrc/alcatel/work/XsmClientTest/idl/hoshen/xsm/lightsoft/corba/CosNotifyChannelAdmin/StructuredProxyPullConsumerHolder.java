package hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin;

/**
* hoshen/xsm/lightsoft/corba/CosNotifyChannelAdmin/StructuredProxyPullConsumerHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.0"
* from lightsoft/CosNotifyChannelAdmin.idl
* 13:11:05 GMT+02:00 ��� ����� 28 ���� 2007
*/


/**
    * An interface for structured proxy pull consumers.
    */
public final class StructuredProxyPullConsumerHolder implements org.omg.CORBA.portable.Streamable
{
  public hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.StructuredProxyPullConsumer value = null;

  public StructuredProxyPullConsumerHolder ()
  {
  }

  public StructuredProxyPullConsumerHolder (hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.StructuredProxyPullConsumer initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.StructuredProxyPullConsumerHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.StructuredProxyPullConsumerHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.StructuredProxyPullConsumerHelper.type ();
  }

}