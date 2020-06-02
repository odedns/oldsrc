package hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin;


/**
* hoshen/xsm/lightsoft/corba/CosNotifyChannelAdmin/SupplierAdminOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.0"
* from lightsoft/CosNotifyChannelAdmin.idl
* 13:11:07 GMT+02:00 ��� ����� 28 ���� 2007
*/


/**
    * An interface for supplier administration objects.
    */
public interface SupplierAdminOperations  extends hoshen.xsm.lightsoft.corba.CosNotification.QoSAdminOperations, hoshen.xsm.lightsoft.corba.CosNotifyComm.NotifyPublishOperations, hoshen.xsm.lightsoft.corba.CosNotifyFilter.FilterAdminOperations, hoshen.xsm.lightsoft.corba.CosEventChannelAdmin.SupplierAdminOperations
{

  /**
       * A unique identified for this administration object.
       */
  int MyID ();

  /**
       * The event channel object that created this administration object.
       */
  hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.EventChannel MyChannel ();

  /**
       * Indicates whether AND or OR semantics is used when combining 
       * administration object filters and proxy filters.
       */
  hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.InterFilterGroupOperator MyOperator ();

  /**
       * A list of pull consumer proxies created by the administration object.
       */
  int[] pull_consumers ();

  /**
       * A list of pull consumer proxies created by the administration object.
       */
  int[] push_consumers ();

  /**
       * Get the proxy with the specified ID.
       * @parm <code>proxy_id</code> - The ID of the proxy to retrieve.
       * @raises ProxyNotFound If no proxy with the specified ID could 
       * be found.
       */
  hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.ProxyConsumer get_proxy_consumer (int proxy_id) throws hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.ProxyNotFound;

  /**
       * Obtain a pull consumer proxy for this administration object.
       * @parm <code>ctype</code> - The client type.
       * @parm <code>proxy_id</code> - The ID of the newly created proxy,
       * i.e. the return value.
       * @returns A ProxyConsumer object reference.
       * @raises AdminLimitExceeded If the number of proxies associated 
       * with this administration object exceeds the MaxConsumers property.
       */
  hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.ProxyConsumer obtain_notification_pull_consumer (hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.ClientType ctype, org.omg.CORBA.IntHolder proxy_id) throws hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.AdminLimitExceeded;

  /**
       * Obtain a push consumer proxy for this administration object.
       * @parm <code>ctype</code> - The client type.
       * @parm <code>proxy_id</code> - The ID of the newly created proxy,
       * i.e. the return value.
       * @returns A ProxyConsumer object reference.
       * @raises AdminLimitExceeded If the number of proxies associated 
       * with this administration object exceeds the MaxConsumers property.
       */
  hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.ProxyConsumer obtain_notification_push_consumer (hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.ClientType ctype, org.omg.CORBA.IntHolder proxy_id) throws hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.AdminLimitExceeded;

  /**
       * Destroy this administration object and all proxies created by it.
       */
  void destroy ();
} // interface SupplierAdminOperations