package hoshen.xsm.lightsoft.corba.CosTrading;


/**
* hoshen/xsm/lightsoft/corba/CosTrading/ProxyPOA.java .
* Generated by the IDL-to-Java compiler (portable), version "3.0"
* from lightsoft/CosTrading.idl
* 13:11:20 GMT+02:00 ��� ����� 28 ���� 2007
*/


/** This interface supports proxy offers.
 * It is not yet supported.
 */
public abstract class ProxyPOA extends org.omg.PortableServer.Servant
                implements hoshen.xsm.lightsoft.corba.CosTrading.ProxyOperations, org.omg.CORBA.portable.InvokeHandler
{

  public hoshen.xsm.lightsoft.corba.CosTrading.Proxy _this() {
     return hoshen.xsm.lightsoft.corba.CosTrading.ProxyHelper.narrow(
        super._this_object());
  }

  public hoshen.xsm.lightsoft.corba.CosTrading.Proxy _this(org.omg.CORBA.ORB orb) {
     return hoshen.xsm.lightsoft.corba.CosTrading.ProxyHelper.narrow(
        super._this_object(orb));
  }

  public String[] _all_interfaces(
     org.omg.PortableServer.POA poa,
     byte[] objectId) {
         return (String[])__ids.clone();
  }

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:omg.org/CosTrading/Proxy:1.0", 
    "IDL:omg.org/CosTrading/TraderComponents:1.0", 
    "IDL:omg.org/CosTrading/SupportAttributes:1.0"};

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("export_proxy", new java.lang.Integer (0));
    _methods.put ("withdraw_proxy", new java.lang.Integer (1));
    _methods.put ("describe_proxy", new java.lang.Integer (2));
    _methods.put ("_get_lookup_if", new java.lang.Integer (3));
    _methods.put ("_get_register_if", new java.lang.Integer (4));
    _methods.put ("_get_link_if", new java.lang.Integer (5));
    _methods.put ("_get_proxy_if", new java.lang.Integer (6));
    _methods.put ("_get_admin_if", new java.lang.Integer (7));
    _methods.put ("_get_supports_modifiable_properties", new java.lang.Integer (8));
    _methods.put ("_get_supports_dynamic_properties", new java.lang.Integer (9));
    _methods.put ("_get_supports_proxy_offers", new java.lang.Integer (10));
    _methods.put ("_get_type_repos", new java.lang.Integer (11));
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

  /* doc: 51 */
       case 0:  // CosTrading/Proxy/export_proxy
       {
         try {
           hoshen.xsm.lightsoft.corba.CosTrading.Lookup target = hoshen.xsm.lightsoft.corba.CosTrading.LookupHelper.read (in);
           String type = in.read_string ();
           hoshen.xsm.lightsoft.corba.CosTrading.Property properties[] = hoshen.xsm.lightsoft.corba.CosTrading.PropertySeqHelper.read (in);
           boolean if_match_all = in.read_boolean ();
           String recipe = in.read_string ();
           hoshen.xsm.lightsoft.corba.CosTrading.Policy policies_to_pass_on[] = hoshen.xsm.lightsoft.corba.CosTrading.PolicySeqHelper.read (in);
           String __result = null;
           __result = this.export_proxy (target, type, properties, if_match_all, recipe, policies_to_pass_on);
           out = $rh.createReply();
           out.write_string (__result);
         } catch (hoshen.xsm.lightsoft.corba.CosTrading.IllegalServiceType __ex) {
           out = $rh.createExceptionReply ();
           hoshen.xsm.lightsoft.corba.CosTrading.IllegalServiceTypeHelper.write (out, __ex);
         } catch (hoshen.xsm.lightsoft.corba.CosTrading.UnknownServiceType __ex) {
           out = $rh.createExceptionReply ();
           hoshen.xsm.lightsoft.corba.CosTrading.UnknownServiceTypeHelper.write (out, __ex);
         } catch (hoshen.xsm.lightsoft.corba.CosTrading.InvalidLookupRef __ex) {
           out = $rh.createExceptionReply ();
           hoshen.xsm.lightsoft.corba.CosTrading.InvalidLookupRefHelper.write (out, __ex);
         } catch (hoshen.xsm.lightsoft.corba.CosTrading.IllegalPropertyName __ex) {
           out = $rh.createExceptionReply ();
           hoshen.xsm.lightsoft.corba.CosTrading.IllegalPropertyNameHelper.write (out, __ex);
         } catch (hoshen.xsm.lightsoft.corba.CosTrading.PropertyTypeMismatch __ex) {
           out = $rh.createExceptionReply ();
           hoshen.xsm.lightsoft.corba.CosTrading.PropertyTypeMismatchHelper.write (out, __ex);
         } catch (hoshen.xsm.lightsoft.corba.CosTrading.ReadonlyDynamicProperty __ex) {
           out = $rh.createExceptionReply ();
           hoshen.xsm.lightsoft.corba.CosTrading.ReadonlyDynamicPropertyHelper.write (out, __ex);
         } catch (hoshen.xsm.lightsoft.corba.CosTrading.MissingMandatoryProperty __ex) {
           out = $rh.createExceptionReply ();
           hoshen.xsm.lightsoft.corba.CosTrading.MissingMandatoryPropertyHelper.write (out, __ex);
         } catch (hoshen.xsm.lightsoft.corba.CosTrading.ProxyPackage.IllegalRecipe __ex) {
           out = $rh.createExceptionReply ();
           hoshen.xsm.lightsoft.corba.CosTrading.ProxyPackage.IllegalRecipeHelper.write (out, __ex);
         } catch (hoshen.xsm.lightsoft.corba.CosTrading.DuplicatePropertyName __ex) {
           out = $rh.createExceptionReply ();
           hoshen.xsm.lightsoft.corba.CosTrading.DuplicatePropertyNameHelper.write (out, __ex);
         } catch (hoshen.xsm.lightsoft.corba.CosTrading.DuplicatePolicyName __ex) {
           out = $rh.createExceptionReply ();
           hoshen.xsm.lightsoft.corba.CosTrading.DuplicatePolicyNameHelper.write (out, __ex);
         }
         break;
       }


  /* enddoc */
       case 1:  // CosTrading/Proxy/withdraw_proxy
       {
         try {
           String id = in.read_string ();
           this.withdraw_proxy (id);
           out = $rh.createReply();
         } catch (hoshen.xsm.lightsoft.corba.CosTrading.IllegalOfferId __ex) {
           out = $rh.createExceptionReply ();
           hoshen.xsm.lightsoft.corba.CosTrading.IllegalOfferIdHelper.write (out, __ex);
         } catch (hoshen.xsm.lightsoft.corba.CosTrading.UnknownOfferId __ex) {
           out = $rh.createExceptionReply ();
           hoshen.xsm.lightsoft.corba.CosTrading.UnknownOfferIdHelper.write (out, __ex);
         } catch (hoshen.xsm.lightsoft.corba.CosTrading.ProxyPackage.NotProxyOfferId __ex) {
           out = $rh.createExceptionReply ();
           hoshen.xsm.lightsoft.corba.CosTrading.ProxyPackage.NotProxyOfferIdHelper.write (out, __ex);
         }
         break;
       }

       case 2:  // CosTrading/Proxy/describe_proxy
       {
         try {
           String id = in.read_string ();
           hoshen.xsm.lightsoft.corba.CosTrading.ProxyPackage.ProxyInfo __result = null;
           __result = this.describe_proxy (id);
           out = $rh.createReply();
           hoshen.xsm.lightsoft.corba.CosTrading.ProxyPackage.ProxyInfoHelper.write (out, __result);
         } catch (hoshen.xsm.lightsoft.corba.CosTrading.IllegalOfferId __ex) {
           out = $rh.createExceptionReply ();
           hoshen.xsm.lightsoft.corba.CosTrading.IllegalOfferIdHelper.write (out, __ex);
         } catch (hoshen.xsm.lightsoft.corba.CosTrading.UnknownOfferId __ex) {
           out = $rh.createExceptionReply ();
           hoshen.xsm.lightsoft.corba.CosTrading.UnknownOfferIdHelper.write (out, __ex);
         } catch (hoshen.xsm.lightsoft.corba.CosTrading.ProxyPackage.NotProxyOfferId __ex) {
           out = $rh.createExceptionReply ();
           hoshen.xsm.lightsoft.corba.CosTrading.ProxyPackage.NotProxyOfferIdHelper.write (out, __ex);
         }
         break;
       }

       case 3:  // CosTrading/TraderComponents/_get_lookup_if
       {
         hoshen.xsm.lightsoft.corba.CosTrading.Lookup __result = null;
         __result = this.lookup_if ();
         out = $rh.createReply();
         hoshen.xsm.lightsoft.corba.CosTrading.LookupHelper.write (out, __result);
         break;
       }

       case 4:  // CosTrading/TraderComponents/_get_register_if
       {
         hoshen.xsm.lightsoft.corba.CosTrading.Register __result = null;
         __result = this.register_if ();
         out = $rh.createReply();
         hoshen.xsm.lightsoft.corba.CosTrading.RegisterHelper.write (out, __result);
         break;
       }

       case 5:  // CosTrading/TraderComponents/_get_link_if
       {
         hoshen.xsm.lightsoft.corba.CosTrading.Link __result = null;
         __result = this.link_if ();
         out = $rh.createReply();
         hoshen.xsm.lightsoft.corba.CosTrading.LinkHelper.write (out, __result);
         break;
       }

       case 6:  // CosTrading/TraderComponents/_get_proxy_if
       {
         hoshen.xsm.lightsoft.corba.CosTrading.Proxy __result = null;
         __result = this.proxy_if ();
         out = $rh.createReply();
         hoshen.xsm.lightsoft.corba.CosTrading.ProxyHelper.write (out, __result);
         break;
       }

       case 7:  // CosTrading/TraderComponents/_get_admin_if
       {
         hoshen.xsm.lightsoft.corba.CosTrading.Admin __result = null;
         __result = this.admin_if ();
         out = $rh.createReply();
         hoshen.xsm.lightsoft.corba.CosTrading.AdminHelper.write (out, __result);
         break;
       }

       case 8:  // CosTrading/SupportAttributes/_get_supports_modifiable_properties
       {
         boolean __result = false;
         __result = this.supports_modifiable_properties ();
         out = $rh.createReply();
         out.write_boolean (__result);
         break;
       }

       case 9:  // CosTrading/SupportAttributes/_get_supports_dynamic_properties
       {
         boolean __result = false;
         __result = this.supports_dynamic_properties ();
         out = $rh.createReply();
         out.write_boolean (__result);
         break;
       }

       case 10:  // CosTrading/SupportAttributes/_get_supports_proxy_offers
       {
         boolean __result = false;
         __result = this.supports_proxy_offers ();
         out = $rh.createReply();
         out.write_boolean (__result);
         break;
       }

       case 11:  // CosTrading/SupportAttributes/_get_type_repos
       {
         org.omg.CORBA.Object __result = null;
         __result = this.type_repos ();
         out = $rh.createReply();
         org.omg.CORBA.ObjectHelper.write (out, __result);
         break;
       }

       default:
         throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
    }

    return out;
  } // _invoke


} // class _ProxyPOA
