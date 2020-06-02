package hoshen.xsm.lightsoft.corba.CosEventChannelAdmin;


/**
* hoshen/xsm/lightsoft/corba/CosEventChannelAdmin/ProxyPushSupplierOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.0"
* from lightsoft/CosEventChannelAdmin.idl
* 11:11:10 IST ��� ����� 6 ������ 2005
*/


/**
    * Interface for a proxy push supplier.
    */
public interface ProxyPushSupplierOperations  extends hoshen.xsm.lightsoft.corba.CosEventComm.PushSupplierOperations
{

  /**
       * Connect a push consumer to this proxy.
       * @parm <code>push_consumer</code> - The <code>PushConsumer</code>
       * object reference.
       * @raises AlreadyConnected If this consumer is already connected.
       * @raises TypeError Illegal consumer type.
       */
  void connect_push_consumer (hoshen.xsm.lightsoft.corba.CosEventComm.PushConsumer push_consumer) throws hoshen.xsm.lightsoft.corba.CosEventChannelAdmin.AlreadyConnected, hoshen.xsm.lightsoft.corba.CosEventChannelAdmin.TypeError;
} // interface ProxyPushSupplierOperations
