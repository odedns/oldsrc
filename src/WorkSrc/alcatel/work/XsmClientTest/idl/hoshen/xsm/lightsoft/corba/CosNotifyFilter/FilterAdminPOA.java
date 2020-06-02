package hoshen.xsm.lightsoft.corba.CosNotifyFilter;


/**
* hoshen/xsm/lightsoft/corba/CosNotifyFilter/FilterAdminPOA.java .
* Generated by the IDL-to-Java compiler (portable), version "3.0"
* from lightsoft/CosNotifyFilter.idl
* 13:11:16 GMT+02:00 ��� ����� 28 ���� 2007
*/


/**
    * Interface for filter administrators.
    */
public abstract class FilterAdminPOA extends org.omg.PortableServer.Servant
                implements hoshen.xsm.lightsoft.corba.CosNotifyFilter.FilterAdminOperations, org.omg.CORBA.portable.InvokeHandler
{

  public hoshen.xsm.lightsoft.corba.CosNotifyFilter.FilterAdmin _this() {
     return hoshen.xsm.lightsoft.corba.CosNotifyFilter.FilterAdminHelper.narrow(
        super._this_object());
  }

  public hoshen.xsm.lightsoft.corba.CosNotifyFilter.FilterAdmin _this(org.omg.CORBA.ORB orb) {
     return hoshen.xsm.lightsoft.corba.CosNotifyFilter.FilterAdminHelper.narrow(
        super._this_object(orb));
  }

  public String[] _all_interfaces(
     org.omg.PortableServer.POA poa,
     byte[] objectId) {
         return (String[])__ids.clone();
  }

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:omg.org/CosNotifyFilter/FilterAdmin:1.0"};

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("add_filter", new java.lang.Integer (0));
    _methods.put ("remove_filter", new java.lang.Integer (1));
    _methods.put ("get_filter", new java.lang.Integer (2));
    _methods.put ("get_all_filters", new java.lang.Integer (3));
    _methods.put ("remove_all_filters", new java.lang.Integer (4));
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
       * Add a filter to this object's list of filters. All these filters
       * are tried upon reception of an event.
       * @parm <code>new_filter</code> - The filter to add.
       * @returns An unique ID that identifies the added filter.
       */
       case 0:  // CosNotifyFilter/FilterAdmin/add_filter
       {
         hoshen.xsm.lightsoft.corba.CosNotifyFilter.Filter new_filter = hoshen.xsm.lightsoft.corba.CosNotifyFilter.FilterHelper.read (in);
         int __result = (int)0;
         __result = this.add_filter (new_filter);
         out = $rh.createReply();
         out.write_long (__result);
         break;
       }


  /**
       * Remove a filter from this object's list of filters.
       * @parm <code>filter</code> - The filter ID.
       * @raises FilterNotFound If the ID does not correspond to any
       * filter that has been added to this object.
       */
       case 1:  // CosNotifyFilter/FilterAdmin/remove_filter
       {
         try {
           int filter = in.read_long ();
           this.remove_filter (filter);
           out = $rh.createReply();
         } catch (hoshen.xsm.lightsoft.corba.CosNotifyFilter.FilterNotFound __ex) {
           out = $rh.createExceptionReply ();
           hoshen.xsm.lightsoft.corba.CosNotifyFilter.FilterNotFoundHelper.write (out, __ex);
         }
         break;
       }


  /**
       * Get a filter from the filter ID.
       * @parm <code>filter</code> - The filter ID.
       * @raises FilterNotFound If the ID does not correspond to any
       * filter that has been added to this object.
       */
       case 2:  // CosNotifyFilter/FilterAdmin/get_filter
       {
         try {
           int filter = in.read_long ();
           hoshen.xsm.lightsoft.corba.CosNotifyFilter.Filter __result = null;
           __result = this.get_filter (filter);
           out = $rh.createReply();
           hoshen.xsm.lightsoft.corba.CosNotifyFilter.FilterHelper.write (out, __result);
         } catch (hoshen.xsm.lightsoft.corba.CosNotifyFilter.FilterNotFound __ex) {
           out = $rh.createExceptionReply ();
           hoshen.xsm.lightsoft.corba.CosNotifyFilter.FilterNotFoundHelper.write (out, __ex);
         }
         break;
       }


  /**
       * Get all filters added to this administration object.
       * @returns A sequence of filter IDs.
       */
       case 3:  // CosNotifyFilter/FilterAdmin/get_all_filters
       {
         int __result[] = null;
         __result = this.get_all_filters ();
         out = $rh.createReply();
         hoshen.xsm.lightsoft.corba.CosNotifyFilter.FilterIDSeqHelper.write (out, __result);
         break;
       }


  /**
       * Remove all filters added to this administration object.
       */
       case 4:  // CosNotifyFilter/FilterAdmin/remove_all_filters
       {
         this.remove_all_filters ();
         out = $rh.createReply();
         break;
       }

       default:
         throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
    }

    return out;
  } // _invoke


} // class _FilterAdminPOA