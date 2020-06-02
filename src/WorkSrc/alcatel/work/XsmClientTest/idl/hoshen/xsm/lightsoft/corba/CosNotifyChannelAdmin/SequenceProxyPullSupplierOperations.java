package hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin;


/**
* hoshen/xsm/lightsoft/corba/CosNotifyChannelAdmin/SequenceProxyPullSupplierOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.0"
* from lightsoft/CosNotifyChannelAdmin.idl
* 13:11:05 GMT+02:00 ��� ����� 28 ���� 2007
*/


/**
    * An interface for sequence proxy pull suppliers.
    */
public interface SequenceProxyPullSupplierOperations  extends hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.ProxySupplierOperations, hoshen.xsm.lightsoft.corba.CosNotifyComm.SequencePullSupplierOperations
{

  /**
       * Connect a sequence type pull consumer to this proxy.
       * @parm <code>pull_consumer</code> - The <code>PullConsumer</code>
       * object reference.
       * @raises AlreadyConnected If this consumer is already connected.
       */
  void connect_sequence_pull_consumer (hoshen.xsm.lightsoft.corba.CosNotifyComm.SequencePullConsumer pull_consumer) throws hoshen.xsm.lightsoft.corba.CosEventChannelAdmin.AlreadyConnected;
} // interface SequenceProxyPullSupplierOperations
