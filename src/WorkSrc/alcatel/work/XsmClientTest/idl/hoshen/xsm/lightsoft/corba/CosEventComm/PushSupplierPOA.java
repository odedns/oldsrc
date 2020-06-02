package hoshen.xsm.lightsoft.corba.CosEventComm;


/**
* hoshen/xsm/lightsoft/corba/CosEventComm/PushSupplierPOA.java .
* Generated by the IDL-to-Java compiler (portable), version "3.0"
* from lightsoft/CosEventComm.idl
* 13:10:54 GMT+02:00 ��� ����� 28 ���� 2007
*/


/**
    * Interface for a push supplier.
    */
public abstract class PushSupplierPOA extends org.omg.PortableServer.Servant
                implements hoshen.xsm.lightsoft.corba.CosEventComm.PushSupplierOperations, org.omg.CORBA.portable.InvokeHandler
{

  public hoshen.xsm.lightsoft.corba.CosEventComm.PushSupplier _this() {
     return hoshen.xsm.lightsoft.corba.CosEventComm.PushSupplierHelper.narrow(
        super._this_object());
  }

  public hoshen.xsm.lightsoft.corba.CosEventComm.PushSupplier _this(org.omg.CORBA.ORB orb) {
     return hoshen.xsm.lightsoft.corba.CosEventComm.PushSupplierHelper.narrow(
        super._this_object(orb));
  }

  public String[] _all_interfaces(
     org.omg.PortableServer.POA poa,
     byte[] objectId) {
         return (String[])__ids.clone();
  }

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:omg.org/CosEventComm/PushSupplier:1.0"};

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("disconnect_push_supplier", new java.lang.Integer (0));
  }

  public org.omg.CORBA.portable.OutputStream _invoke (String $method,
                                org.omg.CORBA.portable.InputStream in,
                                org.omg.CORBA.portable.ResponseHandler $rh)
  {
    org.omg.CORBA.portable.OutputStream out = null;
    java.lang.Integer __method = (java.lang.Integer)_methods.get ($method);
    if (__method == null)
      throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);

    switch (__method.intValue ())
    {

  /**
       * Disconnect a push supplier.
       */
       case 0:  // CosEventComm/PushSupplier/disconnect_push_supplier
       {
         this.disconnect_push_supplier ();
         out = $rh.createReply();
         break;
       }

       default:
         throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
    }

    return out;
  } // _invoke


} // class _PushSupplierPOA