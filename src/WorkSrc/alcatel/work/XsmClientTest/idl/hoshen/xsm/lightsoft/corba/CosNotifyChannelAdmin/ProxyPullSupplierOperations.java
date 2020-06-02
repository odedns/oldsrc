package hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin;


/**
* hoshen/xsm/lightsoft/corba/CosNotifyChannelAdmin/ProxyPullSupplierOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.0"
* from lightsoft/CosNotifyChannelAdmin.idl
* 13:11:03 GMT+02:00 ��� ����� 28 ���� 2007
*/


/**
    * An interface for proxy pull suppliers.
    */
public interface ProxyPullSupplierOperations  extends hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.ProxySupplierOperations, hoshen.xsm.lightsoft.corba.CosNotifyComm.PullSupplierOperations
{

  /**
       * Connect an any type pull consumer to this proxy.
       * @parm <code>pull_consumer</code> - The <code>PullConsumer</code>
       * object reference.
       * @raises AlreadyConnected If this consumer is already connected.
       */
  void connect_any_pull_consumer (hoshen.xsm.lightsoft.corba.CosEventComm.PullConsumer pull_consumer) throws hoshen.xsm.lightsoft.corba.CosEventChannelAdmin.AlreadyConnected;
} // interface ProxyPullSupplierOperations
