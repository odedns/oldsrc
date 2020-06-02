package hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin;


/**
* hoshen/xsm/lightsoft/corba/CosNotifyChannelAdmin/ProxyPushConsumerOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.0"
* from lightsoft/CosNotifyChannelAdmin.idl
* 13:11:03 GMT+02:00 ��� ����� 28 ���� 2007
*/


/**
    * An interface for proxy push consumers.
    */
public interface ProxyPushConsumerOperations  extends hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.ProxyConsumerOperations, hoshen.xsm.lightsoft.corba.CosNotifyComm.PushConsumerOperations
{

  /**
       * Connect an any type push supplier to this proxy.
       * @parm <code>push_supplier</code> - The <code>PushSupplier</code>
       * object reference.
       * @raises AlreadyConnected If this supplier is already connected.
       */
  void connect_any_push_supplier (hoshen.xsm.lightsoft.corba.CosEventComm.PushSupplier push_supplier) throws hoshen.xsm.lightsoft.corba.CosEventChannelAdmin.AlreadyConnected;
} // interface ProxyPushConsumerOperations
