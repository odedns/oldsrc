package hoshen.xsm.lightsoft.corba.CosTrading;


/**
* hoshen/xsm/lightsoft/corba/CosTrading/_SupportAttributesStub.java .
* Generated by the IDL-to-Java compiler (portable), version "3.0"
* from lightsoft/CosTrading.idl
* 13:11:18 GMT+02:00 ��� ����� 28 ���� 2007
*/


/** This interface contains attributes that describe
 * type of functionality supported by a trader service.
 */
public class _SupportAttributesStub extends org.omg.CORBA_2_3.portable.ObjectImpl implements hoshen.xsm.lightsoft.corba.CosTrading.SupportAttributes
{
  // Constructors
  // NOTE:  If the default constructor is used, the
  //        object is useless until _set_delegate (...)
  //        is called.
  public _SupportAttributesStub ()
  {
    super ();
  }

  public _SupportAttributesStub (org.omg.CORBA.portable.Delegate delegate)
  {
    super ();
    _set_delegate (delegate);
  }

  public boolean supports_modifiable_properties ()
  {
    while(true) {
      if ( !this._is_local() ) {
        org.omg.CORBA.portable.InputStream _in = null;
        try {
          org.omg.CORBA.portable.OutputStream _out = _request ("_get_supports_modifiable_properties",true);
          _in = _invoke (_out);
          boolean __result = _in.read_boolean ();
          return __result;
        } catch (org.omg.CORBA.portable.ApplicationException _ex) {
          _in = _ex.getInputStream ();
          String _id = _ex.getId ();
          throw new org.omg.CORBA.UNKNOWN( "Unexpected User Exception: " + _id );
        } catch (org.omg.CORBA.portable.RemarshalException _rm) {
          continue;
        } catch (org.omg.CORBA.portable.UnknownException _ue) {
          Throwable _oe = _ue.originalEx;
          if (_oe instanceof Error)
              throw (Error)_oe;
          else if (_oe instanceof RuntimeException)
              throw (RuntimeException)_oe;
          else
              throw _ue;
        } finally { _releaseReply (_in); }
      }
      else {
        org.omg.CORBA.portable.ServantObject _so =
          _servant_preinvoke( "_get_supports_modifiable_properties",_opsClass );
        if ( _so == null ) { 
          continue;
        }
        try {
          boolean __result = ((hoshen.xsm.lightsoft.corba.CosTrading.SupportAttributesOperations)_so.servant).supports_modifiable_properties(  );
          return __result;
        } finally { _servant_postinvoke( _so ); }
      }
    }
  } // supports_modifiable_properties

  public boolean supports_dynamic_properties ()
  {
    while(true) {
      if ( !this._is_local() ) {
        org.omg.CORBA.portable.InputStream _in = null;
        try {
          org.omg.CORBA.portable.OutputStream _out = _request ("_get_supports_dynamic_properties",true);
          _in = _invoke (_out);
          boolean __result = _in.read_boolean ();
          return __result;
        } catch (org.omg.CORBA.portable.ApplicationException _ex) {
          _in = _ex.getInputStream ();
          String _id = _ex.getId ();
          throw new org.omg.CORBA.UNKNOWN( "Unexpected User Exception: " + _id );
        } catch (org.omg.CORBA.portable.RemarshalException _rm) {
          continue;
        } catch (org.omg.CORBA.portable.UnknownException _ue) {
          Throwable _oe = _ue.originalEx;
          if (_oe instanceof Error)
              throw (Error)_oe;
          else if (_oe instanceof RuntimeException)
              throw (RuntimeException)_oe;
          else
              throw _ue;
        } finally { _releaseReply (_in); }
      }
      else {
        org.omg.CORBA.portable.ServantObject _so =
          _servant_preinvoke( "_get_supports_dynamic_properties",_opsClass );
        if ( _so == null ) { 
          continue;
        }
        try {
          boolean __result = ((hoshen.xsm.lightsoft.corba.CosTrading.SupportAttributesOperations)_so.servant).supports_dynamic_properties(  );
          return __result;
        } finally { _servant_postinvoke( _so ); }
      }
    }
  } // supports_dynamic_properties

  public boolean supports_proxy_offers ()
  {
    while(true) {
      if ( !this._is_local() ) {
        org.omg.CORBA.portable.InputStream _in = null;
        try {
          org.omg.CORBA.portable.OutputStream _out = _request ("_get_supports_proxy_offers",true);
          _in = _invoke (_out);
          boolean __result = _in.read_boolean ();
          return __result;
        } catch (org.omg.CORBA.portable.ApplicationException _ex) {
          _in = _ex.getInputStream ();
          String _id = _ex.getId ();
          throw new org.omg.CORBA.UNKNOWN( "Unexpected User Exception: " + _id );
        } catch (org.omg.CORBA.portable.RemarshalException _rm) {
          continue;
        } catch (org.omg.CORBA.portable.UnknownException _ue) {
          Throwable _oe = _ue.originalEx;
          if (_oe instanceof Error)
              throw (Error)_oe;
          else if (_oe instanceof RuntimeException)
              throw (RuntimeException)_oe;
          else
              throw _ue;
        } finally { _releaseReply (_in); }
      }
      else {
        org.omg.CORBA.portable.ServantObject _so =
          _servant_preinvoke( "_get_supports_proxy_offers",_opsClass );
        if ( _so == null ) { 
          continue;
        }
        try {
          boolean __result = ((hoshen.xsm.lightsoft.corba.CosTrading.SupportAttributesOperations)_so.servant).supports_proxy_offers(  );
          return __result;
        } finally { _servant_postinvoke( _so ); }
      }
    }
  } // supports_proxy_offers

  public org.omg.CORBA.Object type_repos ()
  {
    while(true) {
      if ( !this._is_local() ) {
        org.omg.CORBA.portable.InputStream _in = null;
        try {
          org.omg.CORBA.portable.OutputStream _out = _request ("_get_type_repos",true);
          _in = _invoke (_out);
          org.omg.CORBA.Object __result = org.omg.CORBA.ObjectHelper.read (_in);
          return __result;
        } catch (org.omg.CORBA.portable.ApplicationException _ex) {
          _in = _ex.getInputStream ();
          String _id = _ex.getId ();
          throw new org.omg.CORBA.UNKNOWN( "Unexpected User Exception: " + _id );
        } catch (org.omg.CORBA.portable.RemarshalException _rm) {
          continue;
        } catch (org.omg.CORBA.portable.UnknownException _ue) {
          Throwable _oe = _ue.originalEx;
          if (_oe instanceof Error)
              throw (Error)_oe;
          else if (_oe instanceof RuntimeException)
              throw (RuntimeException)_oe;
          else
              throw _ue;
        } finally { _releaseReply (_in); }
      }
      else {
        org.omg.CORBA.portable.ServantObject _so =
          _servant_preinvoke( "_get_type_repos",_opsClass );
        if ( _so == null ) { 
          continue;
        }
        try {
          org.omg.CORBA.Object __result = ((hoshen.xsm.lightsoft.corba.CosTrading.SupportAttributesOperations)_so.servant).type_repos(  );
          return __result;
        } finally { _servant_postinvoke( _so ); }
      }
    }
  } // type_repos

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:omg.org/CosTrading/SupportAttributes:1.0"};

  public String[] _ids ()
  {
    return (String[])__ids.clone ();
  }

  final public static java.lang.Class _opsClass =
    hoshen.xsm.lightsoft.corba.CosTrading.SupportAttributesOperations.class;

  private void readObject (java.io.ObjectInputStream s)
  {
     try 
     {
       String str = s.readUTF ();
       org.omg.CORBA.Object obj = org.omg.CORBA.ORB.init ((String[])null, null).string_to_object (str);
       org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl) obj)._get_delegate ();
       _set_delegate (delegate);
     } catch (java.io.IOException e) {}
  }

  private void writeObject (java.io.ObjectOutputStream s)
  {
     try 
     {
       String str = org.omg.CORBA.ORB.init ((String[])null, null).object_to_string (this);
       s.writeUTF (str);
     } catch (java.io.IOException e) {}
  }
} // class _SupportAttributesStub