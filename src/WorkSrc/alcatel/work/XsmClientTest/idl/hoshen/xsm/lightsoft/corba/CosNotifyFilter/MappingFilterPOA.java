package hoshen.xsm.lightsoft.corba.CosNotifyFilter;


/**
* hoshen/xsm/lightsoft/corba/CosNotifyFilter/MappingFilterPOA.java .
* Generated by the IDL-to-Java compiler (portable), version "3.0"
* from lightsoft/CosNotifyFilter.idl
* 13:11:16 GMT+02:00 ��� ����� 28 ���� 2007
*/


/**
    * Interface for a mapping filter.
    */
public abstract class MappingFilterPOA extends org.omg.PortableServer.Servant
                implements hoshen.xsm.lightsoft.corba.CosNotifyFilter.MappingFilterOperations, org.omg.CORBA.portable.InvokeHandler
{

  public hoshen.xsm.lightsoft.corba.CosNotifyFilter.MappingFilter _this() {
     return hoshen.xsm.lightsoft.corba.CosNotifyFilter.MappingFilterHelper.narrow(
        super._this_object());
  }

  public hoshen.xsm.lightsoft.corba.CosNotifyFilter.MappingFilter _this(org.omg.CORBA.ORB orb) {
     return hoshen.xsm.lightsoft.corba.CosNotifyFilter.MappingFilterHelper.narrow(
        super._this_object(orb));
  }

  public String[] _all_interfaces(
     org.omg.PortableServer.POA poa,
     byte[] objectId) {
         return (String[])__ids.clone();
  }

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:omg.org/CosNotifyFilter/MappingFilter:1.0"};

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("_get_constraint_grammar", new java.lang.Integer (0));
    _methods.put ("_get_default_value", new java.lang.Integer (1));
    _methods.put ("add_mapping_constraints", new java.lang.Integer (2));
    _methods.put ("modify_mapping_constraints", new java.lang.Integer (3));
    _methods.put ("get_mapping_constraints", new java.lang.Integer (4));
    _methods.put ("get_all_mapping_constraints", new java.lang.Integer (5));
    _methods.put ("remove_all_mapping_constraints", new java.lang.Integer (6));
    _methods.put ("destroy", new java.lang.Integer (7));
    _methods.put ("match", new java.lang.Integer (8));
    _methods.put ("match_structured", new java.lang.Integer (9));
    _methods.put ("match_typed", new java.lang.Integer (10));
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
       * The constraint grammer used by this filter.
       */
       case 0:  // CosNotifyFilter/MappingFilter/_get_constraint_grammar
       {
         String __result = null;
         __result = this.constraint_grammar ();
         out = $rh.createReply();
         out.write_string (__result);
         break;
       }


  /**
       * The output value for any match operation that returns true.
       */
       case 1:  // CosNotifyFilter/MappingFilter/_get_default_value
       {
         org.omg.CORBA.Any __result = null;
         __result = this.default_value ();
         out = $rh.createReply();
         out.write_any (__result);
         break;
       }


  /**
       * Associates one or more mapping constraints with this filter object.
       * @parm <code>constraint_list</code> - List of mapping constraints to
       * be associated with filter.
       * @returns A list with ID for each of the added mapping constraints.
       * @raises InvalidConstraint If any of the mapping constraints
       * are invalid.
       * @raises InvalidValue If any of the constraints have a typecode
       * that is different from the <code>value_type</code> attribute.
       */
       case 2:  // CosNotifyFilter/MappingFilter/add_mapping_constraints
       {
         try {
           hoshen.xsm.lightsoft.corba.CosNotifyFilter.MappingConstraintPair pair_list[] = hoshen.xsm.lightsoft.corba.CosNotifyFilter.MappingConstraintPairSeqHelper.read (in);
           hoshen.xsm.lightsoft.corba.CosNotifyFilter.MappingConstraintInfo __result[] = null;
           __result = this.add_mapping_constraints (pair_list);
           out = $rh.createReply();
           hoshen.xsm.lightsoft.corba.CosNotifyFilter.MappingConstraintInfoSeqHelper.write (out, __result);
         } catch (hoshen.xsm.lightsoft.corba.CosNotifyFilter.InvalidConstraint __ex) {
           out = $rh.createExceptionReply ();
           hoshen.xsm.lightsoft.corba.CosNotifyFilter.InvalidConstraintHelper.write (out, __ex);
         } catch (hoshen.xsm.lightsoft.corba.CosNotifyFilter.InvalidValue __ex) {
           out = $rh.createExceptionReply ();
           hoshen.xsm.lightsoft.corba.CosNotifyFilter.InvalidValueHelper.write (out, __ex);
         }
         break;
       }


  /**
       * Delete or modify mapping constraints on this filter object.
       * @parm <code>del_list</code> - List of mapping constraints to delete.
       * @parm <code>modify_list</code> - List of constraints to modify.
       * @raises InvalidConstraint If any of the constraints in the
       * modify_list input sequences are invalid.
       * @raises InvalidValue If any of the constraints have a typecode
       * that is different from the <code>value_type</code> attribute.
       * @raises ConstraintNotFound If a constraint in either
       * of the two input sequences was not found.
       */
       case 3:  // CosNotifyFilter/MappingFilter/modify_mapping_constraints
       {
         try {
           int del_list[] = hoshen.xsm.lightsoft.corba.CosNotifyFilter.ConstraintIDSeqHelper.read (in);
           hoshen.xsm.lightsoft.corba.CosNotifyFilter.MappingConstraintInfo modify_list[] = hoshen.xsm.lightsoft.corba.CosNotifyFilter.MappingConstraintInfoSeqHelper.read (in);
           this.modify_mapping_constraints (del_list, modify_list);
           out = $rh.createReply();
         } catch (hoshen.xsm.lightsoft.corba.CosNotifyFilter.InvalidConstraint __ex) {
           out = $rh.createExceptionReply ();
           hoshen.xsm.lightsoft.corba.CosNotifyFilter.InvalidConstraintHelper.write (out, __ex);
         } catch (hoshen.xsm.lightsoft.corba.CosNotifyFilter.InvalidValue __ex) {
           out = $rh.createExceptionReply ();
           hoshen.xsm.lightsoft.corba.CosNotifyFilter.InvalidValueHelper.write (out, __ex);
         } catch (hoshen.xsm.lightsoft.corba.CosNotifyFilter.ConstraintNotFound __ex) {
           out = $rh.createExceptionReply ();
           hoshen.xsm.lightsoft.corba.CosNotifyFilter.ConstraintNotFoundHelper.write (out, __ex);
         }
         break;
       }


  /**
       * Return a list of mapping constraints for this filter object
       * based on IDs.
       * @parm <code>id_list</code> - List of mapping constraint IDs.
       * @returns A list of mapping constraints for the IDs in the
       * input sequence.
       * @raises ConstraintNotFound If a mapping constraint was not found.
       */
       case 4:  // CosNotifyFilter/MappingFilter/get_mapping_constraints
       {
         try {
           int id_list[] = hoshen.xsm.lightsoft.corba.CosNotifyFilter.ConstraintIDSeqHelper.read (in);
           hoshen.xsm.lightsoft.corba.CosNotifyFilter.MappingConstraintInfo __result[] = null;
           __result = this.get_mapping_constraints (id_list);
           out = $rh.createReply();
           hoshen.xsm.lightsoft.corba.CosNotifyFilter.MappingConstraintInfoSeqHelper.write (out, __result);
         } catch (hoshen.xsm.lightsoft.corba.CosNotifyFilter.ConstraintNotFound __ex) {
           out = $rh.createExceptionReply ();
           hoshen.xsm.lightsoft.corba.CosNotifyFilter.ConstraintNotFoundHelper.write (out, __ex);
         }
         break;
       }


  /**
       * Return a list of all mapping constraints for this filter object.
       * @returns A list of all mapping constraints.
       */
       case 5:  // CosNotifyFilter/MappingFilter/get_all_mapping_constraints
       {
         hoshen.xsm.lightsoft.corba.CosNotifyFilter.MappingConstraintInfo __result[] = null;
         __result = this.get_all_mapping_constraints ();
         out = $rh.createReply();
         hoshen.xsm.lightsoft.corba.CosNotifyFilter.MappingConstraintInfoSeqHelper.write (out, __result);
         break;
       }


  /**
       * Removes all mapping constraints defined on this filter object.
       */
       case 6:  // CosNotifyFilter/MappingFilter/remove_all_mapping_constraints
       {
         this.remove_all_mapping_constraints ();
         out = $rh.createReply();
         break;
       }


  /**
       * Destroys this mapping filter object.
       */
       case 7:  // CosNotifyFilter/MappingFilter/destroy
       {
         this.destroy ();
         out = $rh.createReply();
         break;
       }


  /**
       * Evaluates the input event against the filter constraints defined
       * on this filter object. If the event satisfies one of the filter
       * constraints TRUE is returned, otherwise FALSE is returned.
       * @parm <code>filterable_data</code> - The event to evaluate.
       * @parm <code>result_to_set</code> - Value of a constraint that
       * matches the event or <code>default_value</code> if the event
       * does not match any constraints.
       * @returns TRUE if event matches any constraint, FALSE otherwise.
       * raises UnsupportedFilterableData If the event specified by
       * <code>filterable_data</code> contains data that this filter is
       * not designed to handle.
       */
       case 8:  // CosNotifyFilter/MappingFilter/match
       {
         try {
           org.omg.CORBA.Any filterable_data = in.read_any ();
           org.omg.CORBA.AnyHolder result_to_set = new org.omg.CORBA.AnyHolder ();
           boolean __result = false;
           __result = this.match (filterable_data, result_to_set);
           out = $rh.createReply();
           out.write_boolean (__result);
           out.write_any (result_to_set.value);
         } catch (hoshen.xsm.lightsoft.corba.CosNotifyFilter.UnsupportedFilterableData __ex) {
           out = $rh.createExceptionReply ();
           hoshen.xsm.lightsoft.corba.CosNotifyFilter.UnsupportedFilterableDataHelper.write (out, __ex);
         }
         break;
       }


  /**
       * Evaluates the input event against the filter constraints defined
       * on this filter object. If the event satisfies one of the filter
       * constraints TRUE is returned, otherwise FALSE is returned.
       * @parm <code>filterable_data</code> - The structured event to evaluate.
       * @parm <code>result_to_set</code> - Value of a constraint that
       * matches the event or <code>default_value</code> if the event
       * does not match any constraints.
       * @returns TRUE if event matches any constraint, FALSE otherwise.
       * raises UnsupportedFilterableData If the event specified by
       * <code>filterable_data</code> contains data that this filter is
       * not designed to handle.
       */
       case 9:  // CosNotifyFilter/MappingFilter/match_structured
       {
         try {
           hoshen.xsm.lightsoft.corba.CosNotification.StructuredEvent filterable_data = hoshen.xsm.lightsoft.corba.CosNotification.StructuredEventHelper.read (in);
           org.omg.CORBA.AnyHolder result_to_set = new org.omg.CORBA.AnyHolder ();
           boolean __result = false;
           __result = this.match_structured (filterable_data, result_to_set);
           out = $rh.createReply();
           out.write_boolean (__result);
           out.write_any (result_to_set.value);
         } catch (hoshen.xsm.lightsoft.corba.CosNotifyFilter.UnsupportedFilterableData __ex) {
           out = $rh.createExceptionReply ();
           hoshen.xsm.lightsoft.corba.CosNotifyFilter.UnsupportedFilterableDataHelper.write (out, __ex);
         }
         break;
       }


  /**
       * Evaluates the input event against the filter constraints defined
       * on this filter object. If the event satisfies one of the filter
       * constraints TRUE is returned, otherwise FALSE is returned.
       * @parm <code>filterable_data</code> - The typed event to evaluate.
       * @parm <code>result_to_set</code> - Value of a constraint that
       * matches the event or default_value if the event does not match
       * any constraints.
       * @returns TRUE if event matches any constraint, FALSE otherwise.
       * raises UnsupportedFilterableData If the event specified by
       * <code>filterable_data</code> contains data that this filter is
       * not designed to handle.
       */
       case 10:  // CosNotifyFilter/MappingFilter/match_typed
       {
         try {
           hoshen.xsm.lightsoft.corba.CosNotification.Property filterable_data[] = hoshen.xsm.lightsoft.corba.CosNotification.PropertySeqHelper.read (in);
           org.omg.CORBA.AnyHolder result_to_set = new org.omg.CORBA.AnyHolder ();
           boolean __result = false;
           __result = this.match_typed (filterable_data, result_to_set);
           out = $rh.createReply();
           out.write_boolean (__result);
           out.write_any (result_to_set.value);
         } catch (hoshen.xsm.lightsoft.corba.CosNotifyFilter.UnsupportedFilterableData __ex) {
           out = $rh.createExceptionReply ();
           hoshen.xsm.lightsoft.corba.CosNotifyFilter.UnsupportedFilterableDataHelper.write (out, __ex);
         }
         break;
       }

       default:
         throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
    }

    return out;
  } // _invoke


} // class _MappingFilterPOA