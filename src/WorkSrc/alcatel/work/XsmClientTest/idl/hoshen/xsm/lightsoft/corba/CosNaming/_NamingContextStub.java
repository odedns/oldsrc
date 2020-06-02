package hoshen.xsm.lightsoft.corba.CosNaming;


/**
* hoshen/xsm/lightsoft/corba/CosNaming/_NamingContextStub.java .
* Generated by the IDL-to-Java compiler (portable), version "3.0"
* from lightsoft/CosNaming.idl
* 13:10:56 GMT+02:00 ��� ����� 28 ���� 2007
*/


/**
 * The NamingContext interface provides operations which support the following:
 * <ul>
 * <li> binding objects
 * <li> name resolution
 * <li> unbinding
 * <li> creating naming contexts
 * <li> deleting contexts
 * <li> listing a naming context
 * </ul>
 */
public class _NamingContextStub extends org.omg.CORBA_2_3.portable.ObjectImpl implements hoshen.xsm.lightsoft.corba.CosNaming.NamingContext
{
  // Constructors
  // NOTE:  If the default constructor is used, the
  //        object is useless until _set_delegate (...)
  //        is called.
  public _NamingContextStub ()
  {
    super ();
  }

  public _NamingContextStub (org.omg.CORBA.portable.Delegate delegate)
  {
    super ();
    _set_delegate (delegate);
  }


  /**
 * Creates a binding of a name and an object in the naming
 * context. Naming contexts that are bound using bind do not
 * participate in name resolution when compound names are passed to be
 * resolved. A bind operation that is passed a compound name is
 * defined as follows:
 *
 * <pre>
 * ctx->bind(< c1 ; c2 ; ... ; cn >, obj) :=
 * (ctx->resolve(< c1 ; c2 ; ... ; cn-1 >))->bind(< cn >, obj)
 * </pre>
 *
 * @parm n - binding name.
 * @parm obj - object to bind.
 * @raises AlreadyBound - if the name is bound in the context.
 */
  public void bind (hoshen.xsm.lightsoft.corba.CosNaming.NameComponent[] n, org.omg.CORBA.Object obj) throws hoshen.xsm.lightsoft.corba.CosNaming.NamingContextPackage.NotFound, hoshen.xsm.lightsoft.corba.CosNaming.NamingContextPackage.CannotProceed, hoshen.xsm.lightsoft.corba.CosNaming.NamingContextPackage.InvalidName, hoshen.xsm.lightsoft.corba.CosNaming.NamingContextPackage.AlreadyBound
  {
    while(true) {
      if ( !this._is_local() ) {
        org.omg.CORBA.portable.InputStream _in = null;
        try {
          org.omg.CORBA.portable.OutputStream _out = _request ("bind",true);
          hoshen.xsm.lightsoft.corba.CosNaming.NameHelper.write (_out, n);
          org.omg.CORBA.ObjectHelper.write (_out, obj);
          _in = _invoke (_out);
          return;
        } catch (org.omg.CORBA.portable.ApplicationException _ex) {
          _in = _ex.getInputStream ();
          String _id = _ex.getId ();
           if ( _id.equals ( hoshen.xsm.lightsoft.corba.CosNaming.NamingContextPackage.NotFoundHelper.id() ) )
            throw hoshen.xsm.lightsoft.corba.CosNaming.NamingContextPackage.NotFoundHelper.read( _in );
           else if ( _id.equals ( hoshen.xsm.lightsoft.corba.CosNaming.NamingContextPackage.CannotProceedHelper.id() ) )
            throw hoshen.xsm.lightsoft.corba.CosNaming.NamingContextPackage.CannotProceedHelper.read( _in );
           else if ( _id.equals ( hoshen.xsm.lightsoft.corba.CosNaming.NamingContextPackage.InvalidNameHelper.id() ) )
            throw hoshen.xsm.lightsoft.corba.CosNaming.NamingContextPackage.InvalidNameHelper.read( _in );
           else if ( _id.equals ( hoshen.xsm.lightsoft.corba.CosNaming.NamingContextPackage.AlreadyBoundHelper.id() ) )
            throw hoshen.xsm.lightsoft.corba.CosNaming.NamingContextPackage.AlreadyBoundHelper.read( _in );
           else throw new org.omg.CORBA.UNKNOWN( "Unexpected User Exception: " + _id );
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
          _servant_preinvoke( "bind",_opsClass );
        if ( _so == null ) { 
          continue;
        }
        try {
          ((hoshen.xsm.lightsoft.corba.CosNaming.NamingContextOperations)_so.servant).bind( n,obj );
          return;
        } finally { _servant_postinvoke( _so ); }
      }
    }
  } // bind


  /** 
 * Creates a binding of a name and an object in the naming context
 * even if the name is already bound in the context. Naming contexts
 * that are bound using rebind do not participate in name resolution
 * when compound names are passed to be resolved.
 *
 * @parm n - binding name.
 * @parm obj - object to bind.
 */
  public void rebind (hoshen.xsm.lightsoft.corba.CosNaming.NameComponent[] n, org.omg.CORBA.Object obj) throws hoshen.xsm.lightsoft.corba.CosNaming.NamingContextPackage.NotFound, hoshen.xsm.lightsoft.corba.CosNaming.NamingContextPackage.CannotProceed, hoshen.xsm.lightsoft.corba.CosNaming.NamingContextPackage.InvalidName
  {
    while(true) {
      if ( !this._is_local() ) {
        org.omg.CORBA.portable.InputStream _in = null;
        try {
          org.omg.CORBA.portable.OutputStream _out = _request ("rebind",true);
          hoshen.xsm.lightsoft.corba.CosNaming.NameHelper.write (_out, n);
          org.omg.CORBA.ObjectHelper.write (_out, obj);
          _in = _invoke (_out);
          return;
        } catch (org.omg.CORBA.portable.ApplicationException _ex) {
          _in = _ex.getInputStream ();
          String _id = _ex.getId ();
           if ( _id.equals ( hoshen.xsm.lightsoft.corba.CosNaming.NamingContextPackage.NotFoundHelper.id() ) )
            throw hoshen.xsm.lightsoft.corba.CosNaming.NamingContextPackage.NotFoundHelper.read( _in );
           else if ( _id.equals ( hoshen.xsm.lightsoft.corba.CosNaming.NamingContextPackage.CannotProceedHelper.id() ) )
            throw hoshen.xsm.lightsoft.corba.CosNaming.NamingContextPackage.CannotProceedHelper.read( _in );
           else if ( _id.equals ( hoshen.xsm.lightsoft.corba.CosNaming.NamingContextPackage.InvalidNameHelper.id() ) )
            throw hoshen.xsm.lightsoft.corba.CosNaming.NamingContextPackage.InvalidNameHelper.read( _in );
           else throw new org.omg.CORBA.UNKNOWN( "Unexpected User Exception: " + _id );
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
          _servant_preinvoke( "rebind",_opsClass );
        if ( _so == null ) { 
          continue;
        }
        try {
          ((hoshen.xsm.lightsoft.corba.CosNaming.NamingContextOperations)_so.servant).rebind( n,obj );
          return;
        } finally { _servant_postinvoke( _so ); }
      }
    }
  } // rebind


  /** 
 * Names an object that is a naming context. Naming contexts that are
 * bound using bind_context() participate in name resolution when
 * compound names are passed to be resolved. A bind_context operation
 * that is passed a compound name is defined as follows:
 * <pre>
 * ctx->bind_context(< c1 ; c2 ; ... ; cn >, nc) :=
 * (ctx->resolve(< c1 ; c2 ; ... ; cn-1 >))->bind_context(< cn >, nc)
 * </pre>
 *
 * @parm n - binding name.
 * @parm nc - naming context to bind.
 * @raises AlreadyBound - if the name is bound in the context.
 */
  public void bind_context (hoshen.xsm.lightsoft.corba.CosNaming.NameComponent[] n, hoshen.xsm.lightsoft.corba.CosNaming.NamingContext nc) throws hoshen.xsm.lightsoft.corba.CosNaming.NamingContextPackage.NotFound, hoshen.xsm.lightsoft.corba.CosNaming.NamingContextPackage.CannotProceed, hoshen.xsm.lightsoft.corba.CosNaming.NamingContextPackage.InvalidName, hoshen.xsm.lightsoft.corba.CosNaming.NamingContextPackage.AlreadyBound
  {
    while(true) {
      if ( !this._is_local() ) {
        org.omg.CORBA.portable.InputStream _in = null;
        try {
          org.omg.CORBA.portable.OutputStream _out = _request ("bind_context",true);
          hoshen.xsm.lightsoft.corba.CosNaming.NameHelper.write (_out, n);
          hoshen.xsm.lightsoft.corba.CosNaming.NamingContextHelper.write (_out, nc);
          _in = _invoke (_out);
          return;
        } catch (org.omg.CORBA.portable.ApplicationException _ex) {
          _in = _ex.getInputStream ();
          String _id = _ex.getId ();
           if ( _id.equals ( hoshen.xsm.lightsoft.corba.CosNaming.NamingContextPackage.NotFoundHelper.id() ) )
            throw hoshen.xsm.lightsoft.corba.CosNaming.NamingContextPackage.NotFoundHelper.read( _in );
           else if ( _id.equals ( hoshen.xsm.lightsoft.corba.CosNaming.NamingContextPackage.CannotProceedHelper.id() ) )
            throw hoshen.xsm.lightsoft.corba.CosNaming.NamingContextPackage.CannotProceedHelper.read( _in );
           else if ( _id.equals ( hoshen.xsm.lightsoft.corba.CosNaming.NamingContextPackage.InvalidNameHelper.id() ) )
            throw hoshen.xsm.lightsoft.corba.CosNaming.NamingContextPackage.InvalidNameHelper.read( _in );
           else if ( _id.equals ( hoshen.xsm.lightsoft.corba.CosNaming.NamingContextPackage.AlreadyBoundHelper.id() ) )
            throw hoshen.xsm.lightsoft.corba.CosNaming.NamingContextPackage.AlreadyBoundHelper.read( _in );
           else throw new org.omg.CORBA.UNKNOWN( "Unexpected User Exception: " + _id );
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
          _servant_preinvoke( "bind_context",_opsClass );
        if ( _so == null ) { 
          continue;
        }
        try {
          ((hoshen.xsm.lightsoft.corba.CosNaming.NamingContextOperations)_so.servant).bind_context( n,nc );
          return;
        } finally { _servant_postinvoke( _so ); }
      }
    }
  } // bind_context


  /** 
 * Creates a binding of a name and a naming context in the naming
 * context even if the name is already bound in the context. Naming
 * contexts that are bound using rebind_context() participate in name
 * resolution when compound names are passed to be resolved.
 *
 * @parm n - binding name.
 * @parm nc - naming context to bind.
 */
  public void rebind_context (hoshen.xsm.lightsoft.corba.CosNaming.NameComponent[] n, hoshen.xsm.lightsoft.corba.CosNaming.NamingContext nc) throws hoshen.xsm.lightsoft.corba.CosNaming.NamingContextPackage.NotFound, hoshen.xsm.lightsoft.corba.CosNaming.NamingContextPackage.CannotProceed, hoshen.xsm.lightsoft.corba.CosNaming.NamingContextPackage.InvalidName
  {
    while(true) {
      if ( !this._is_local() ) {
        org.omg.CORBA.portable.InputStream _in = null;
        try {
          org.omg.CORBA.portable.OutputStream _out = _request ("rebind_context",true);
          hoshen.xsm.lightsoft.corba.CosNaming.NameHelper.write (_out, n);
          hoshen.xsm.lightsoft.corba.CosNaming.NamingContextHelper.write (_out, nc);
          _in = _invoke (_out);
          return;
        } catch (org.omg.CORBA.portable.ApplicationException _ex) {
          _in = _ex.getInputStream ();
          String _id = _ex.getId ();
           if ( _id.equals ( hoshen.xsm.lightsoft.corba.CosNaming.NamingContextPackage.NotFoundHelper.id() ) )
            throw hoshen.xsm.lightsoft.corba.CosNaming.NamingContextPackage.NotFoundHelper.read( _in );
           else if ( _id.equals ( hoshen.xsm.lightsoft.corba.CosNaming.NamingContextPackage.CannotProceedHelper.id() ) )
            throw hoshen.xsm.lightsoft.corba.CosNaming.NamingContextPackage.CannotProceedHelper.read( _in );
           else if ( _id.equals ( hoshen.xsm.lightsoft.corba.CosNaming.NamingContextPackage.InvalidNameHelper.id() ) )
            throw hoshen.xsm.lightsoft.corba.CosNaming.NamingContextPackage.InvalidNameHelper.read( _in );
           else throw new org.omg.CORBA.UNKNOWN( "Unexpected User Exception: " + _id );
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
          _servant_preinvoke( "rebind_context",_opsClass );
        if ( _so == null ) { 
          continue;
        }
        try {
          ((hoshen.xsm.lightsoft.corba.CosNaming.NamingContextOperations)_so.servant).rebind_context( n,nc );
          return;
        } finally { _servant_postinvoke( _so ); }
      }
    }
  } // rebind_context


  /** 
 * The resolve operation is the process of retrieving an object bound
 * to a name in a given context. The given name must exactly match the
 * bound name. The naming service does not return the type of the
 * object. Clients are responsible for "narrowing" the object to the
 * appropriate type. That is, clients typically cast the returned
 * object from Object to a more specialized interface. Names can have
 * multiple components; therefore, name resolution can traverse
 * multiple contexts.
 * A compound resolve is defined as follows:
 * <pre>
 * ctx->resolve(< c1 ; c2 ; ... ; cn >) :=
 * ctx->resolve(< c1 ; c2 ; ... ; cn-1 >)->resolve(< cn >)
 * </pre>
 *
 * @parm n - binding name.
 * @returns bound object.
 */
  public org.omg.CORBA.Object resolve (hoshen.xsm.lightsoft.corba.CosNaming.NameComponent[] n) throws hoshen.xsm.lightsoft.corba.CosNaming.NamingContextPackage.NotFound, hoshen.xsm.lightsoft.corba.CosNaming.NamingContextPackage.CannotProceed, hoshen.xsm.lightsoft.corba.CosNaming.NamingContextPackage.InvalidName
  {
    while(true) {
      if ( !this._is_local() ) {
        org.omg.CORBA.portable.InputStream _in = null;
        try {
          org.omg.CORBA.portable.OutputStream _out = _request ("resolve",true);
          hoshen.xsm.lightsoft.corba.CosNaming.NameHelper.write (_out, n);
          _in = _invoke (_out);
          org.omg.CORBA.Object __result = org.omg.CORBA.ObjectHelper.read (_in);
          return __result;
        } catch (org.omg.CORBA.portable.ApplicationException _ex) {
          _in = _ex.getInputStream ();
          String _id = _ex.getId ();
           if ( _id.equals ( hoshen.xsm.lightsoft.corba.CosNaming.NamingContextPackage.NotFoundHelper.id() ) )
            throw hoshen.xsm.lightsoft.corba.CosNaming.NamingContextPackage.NotFoundHelper.read( _in );
           else if ( _id.equals ( hoshen.xsm.lightsoft.corba.CosNaming.NamingContextPackage.CannotProceedHelper.id() ) )
            throw hoshen.xsm.lightsoft.corba.CosNaming.NamingContextPackage.CannotProceedHelper.read( _in );
           else if ( _id.equals ( hoshen.xsm.lightsoft.corba.CosNaming.NamingContextPackage.InvalidNameHelper.id() ) )
            throw hoshen.xsm.lightsoft.corba.CosNaming.NamingContextPackage.InvalidNameHelper.read( _in );
           else throw new org.omg.CORBA.UNKNOWN( "Unexpected User Exception: " + _id );
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
          _servant_preinvoke( "resolve",_opsClass );
        if ( _so == null ) { 
          continue;
        }
        try {
          org.omg.CORBA.Object __result = ((hoshen.xsm.lightsoft.corba.CosNaming.NamingContextOperations)_so.servant).resolve( n );
          return __result;
        } finally { _servant_postinvoke( _so ); }
      }
    }
  } // resolve


  /** 
 * The unbind operation removes a name binding from a context.
 * A unbind operation that is passed a compound name is defined as follows:
 * <pre>
 * ctx->unbind(< c1 ; c2 ; ... ; cn >) :=
 * (ctx->resolve(< c1 ; c2 ; ... ; cn-1 >))->unbind(< cn >)
 * </pre>
 *
 * @parm n - binding name.
 */
  public void unbind (hoshen.xsm.lightsoft.corba.CosNaming.NameComponent[] n) throws hoshen.xsm.lightsoft.corba.CosNaming.NamingContextPackage.NotFound, hoshen.xsm.lightsoft.corba.CosNaming.NamingContextPackage.CannotProceed, hoshen.xsm.lightsoft.corba.CosNaming.NamingContextPackage.InvalidName
  {
    while(true) {
      if ( !this._is_local() ) {
        org.omg.CORBA.portable.InputStream _in = null;
        try {
          org.omg.CORBA.portable.OutputStream _out = _request ("unbind",true);
          hoshen.xsm.lightsoft.corba.CosNaming.NameHelper.write (_out, n);
          _in = _invoke (_out);
          return;
        } catch (org.omg.CORBA.portable.ApplicationException _ex) {
          _in = _ex.getInputStream ();
          String _id = _ex.getId ();
           if ( _id.equals ( hoshen.xsm.lightsoft.corba.CosNaming.NamingContextPackage.NotFoundHelper.id() ) )
            throw hoshen.xsm.lightsoft.corba.CosNaming.NamingContextPackage.NotFoundHelper.read( _in );
           else if ( _id.equals ( hoshen.xsm.lightsoft.corba.CosNaming.NamingContextPackage.CannotProceedHelper.id() ) )
            throw hoshen.xsm.lightsoft.corba.CosNaming.NamingContextPackage.CannotProceedHelper.read( _in );
           else if ( _id.equals ( hoshen.xsm.lightsoft.corba.CosNaming.NamingContextPackage.InvalidNameHelper.id() ) )
            throw hoshen.xsm.lightsoft.corba.CosNaming.NamingContextPackage.InvalidNameHelper.read( _in );
           else throw new org.omg.CORBA.UNKNOWN( "Unexpected User Exception: " + _id );
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
          _servant_preinvoke( "unbind",_opsClass );
        if ( _so == null ) { 
          continue;
        }
        try {
          ((hoshen.xsm.lightsoft.corba.CosNaming.NamingContextOperations)_so.servant).unbind( n );
          return;
        } finally { _servant_postinvoke( _so ); }
      }
    }
  } // unbind


  /** 
 * This operation returns a naming context implemented by the same
 * naming server as the context on which the operation was
 * invoked. The new context is not bound to any name.
 *
 * @returns new binding context.
 */
  public hoshen.xsm.lightsoft.corba.CosNaming.NamingContext new_context ()
  {
    while(true) {
      if ( !this._is_local() ) {
        org.omg.CORBA.portable.InputStream _in = null;
        try {
          org.omg.CORBA.portable.OutputStream _out = _request ("new_context",true);
          _in = _invoke (_out);
          hoshen.xsm.lightsoft.corba.CosNaming.NamingContext __result = hoshen.xsm.lightsoft.corba.CosNaming.NamingContextHelper.read (_in);
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
          _servant_preinvoke( "new_context",_opsClass );
        if ( _so == null ) { 
          continue;
        }
        try {
          hoshen.xsm.lightsoft.corba.CosNaming.NamingContext __result = ((hoshen.xsm.lightsoft.corba.CosNaming.NamingContextOperations)_so.servant).new_context(  );
          return __result;
        } finally { _servant_postinvoke( _so ); }
      }
    }
  } // new_context


  /** 
 * This operation creates a new context and binds it to the name
 * supplied as an argument. The newly-created context is implemented
 * by the same naming server as the context in which it was bound
 * (that is, the naming server that implements the context denoted by
 * the name argument excluding the last component). A bind_new_context
 * that is passed a compound name is defined as follows:
 * 
 * <pre>
 * ctx->bind_new_context(< c1 ; c2 ; ... ; cn >) :=
 * (ctx->resolve(< c1 ; c2 ; ... ; cn-1 >))->bind_new_context(< cn >)
 * </pre>
 *
 * @parm n - binding name.
 * @returns new binding context.
 * @raises AlreadyBound - if the name is bound in the context.
 */
  public hoshen.xsm.lightsoft.corba.CosNaming.NamingContext bind_new_context (hoshen.xsm.lightsoft.corba.CosNaming.NameComponent[] n) throws hoshen.xsm.lightsoft.corba.CosNaming.NamingContextPackage.NotFound, hoshen.xsm.lightsoft.corba.CosNaming.NamingContextPackage.CannotProceed, hoshen.xsm.lightsoft.corba.CosNaming.NamingContextPackage.InvalidName, hoshen.xsm.lightsoft.corba.CosNaming.NamingContextPackage.AlreadyBound
  {
    while(true) {
      if ( !this._is_local() ) {
        org.omg.CORBA.portable.InputStream _in = null;
        try {
          org.omg.CORBA.portable.OutputStream _out = _request ("bind_new_context",true);
          hoshen.xsm.lightsoft.corba.CosNaming.NameHelper.write (_out, n);
          _in = _invoke (_out);
          hoshen.xsm.lightsoft.corba.CosNaming.NamingContext __result = hoshen.xsm.lightsoft.corba.CosNaming.NamingContextHelper.read (_in);
          return __result;
        } catch (org.omg.CORBA.portable.ApplicationException _ex) {
          _in = _ex.getInputStream ();
          String _id = _ex.getId ();
           if ( _id.equals ( hoshen.xsm.lightsoft.corba.CosNaming.NamingContextPackage.NotFoundHelper.id() ) )
            throw hoshen.xsm.lightsoft.corba.CosNaming.NamingContextPackage.NotFoundHelper.read( _in );
           else if ( _id.equals ( hoshen.xsm.lightsoft.corba.CosNaming.NamingContextPackage.CannotProceedHelper.id() ) )
            throw hoshen.xsm.lightsoft.corba.CosNaming.NamingContextPackage.CannotProceedHelper.read( _in );
           else if ( _id.equals ( hoshen.xsm.lightsoft.corba.CosNaming.NamingContextPackage.InvalidNameHelper.id() ) )
            throw hoshen.xsm.lightsoft.corba.CosNaming.NamingContextPackage.InvalidNameHelper.read( _in );
           else if ( _id.equals ( hoshen.xsm.lightsoft.corba.CosNaming.NamingContextPackage.AlreadyBoundHelper.id() ) )
            throw hoshen.xsm.lightsoft.corba.CosNaming.NamingContextPackage.AlreadyBoundHelper.read( _in );
           else throw new org.omg.CORBA.UNKNOWN( "Unexpected User Exception: " + _id );
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
          _servant_preinvoke( "bind_new_context",_opsClass );
        if ( _so == null ) { 
          continue;
        }
        try {
          hoshen.xsm.lightsoft.corba.CosNaming.NamingContext __result = ((hoshen.xsm.lightsoft.corba.CosNaming.NamingContextOperations)_so.servant).bind_new_context( n );
          return __result;
        } finally { _servant_postinvoke( _so ); }
      }
    }
  } // bind_new_context


  /** 
 * The destroy operation deletes a naming context. The list operation
 * allows a client to iterate through a set of bindings in a naming
 * context.
 *
 * @raises NotEmpty - if the naming context contains bindings.
 */
  public void destroy () throws hoshen.xsm.lightsoft.corba.CosNaming.NamingContextPackage.NotEmpty
  {
    while(true) {
      if ( !this._is_local() ) {
        org.omg.CORBA.portable.InputStream _in = null;
        try {
          org.omg.CORBA.portable.OutputStream _out = _request ("destroy",true);
          _in = _invoke (_out);
          return;
        } catch (org.omg.CORBA.portable.ApplicationException _ex) {
          _in = _ex.getInputStream ();
          String _id = _ex.getId ();
           if ( _id.equals ( hoshen.xsm.lightsoft.corba.CosNaming.NamingContextPackage.NotEmptyHelper.id() ) )
            throw hoshen.xsm.lightsoft.corba.CosNaming.NamingContextPackage.NotEmptyHelper.read( _in );
           else throw new org.omg.CORBA.UNKNOWN( "Unexpected User Exception: " + _id );
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
          _servant_preinvoke( "destroy",_opsClass );
        if ( _so == null ) { 
          continue;
        }
        try {
          ((hoshen.xsm.lightsoft.corba.CosNaming.NamingContextOperations)_so.servant).destroy(  );
          return;
        } finally { _servant_postinvoke( _so ); }
      }
    }
  } // destroy


  /**
 * The list operation returns at most the requested number of bindings in
 * BindingList bl.
 * <ul>
 * <li> If the naming context contains additional bindings, the list
 * operation returns a BindingIterator with the additional bindings.
 * <li> If the naming context does not contain additional bindings, the
 * binding iterator is a nil object reference.
 * </ul>
 *
 * @parm how_many - maximum number of binding to return in bl.
 * @parm bl - list of bindings.
 * @parm bi - iterator over remaining bindings.
 */
  public void list (int how_many, hoshen.xsm.lightsoft.corba.CosNaming.BindingListHolder bl, hoshen.xsm.lightsoft.corba.CosNaming.BindingIteratorHolder bi)
  {
    while(true) {
      if ( !this._is_local() ) {
        org.omg.CORBA.portable.InputStream _in = null;
        try {
          org.omg.CORBA.portable.OutputStream _out = _request ("list",true);
          _out.write_ulong (how_many);
          _in = _invoke (_out);
          bl.value = hoshen.xsm.lightsoft.corba.CosNaming.BindingListHelper.read (_in);
          bi.value = hoshen.xsm.lightsoft.corba.CosNaming.BindingIteratorHelper.read (_in);
          return;
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
          _servant_preinvoke( "list",_opsClass );
        if ( _so == null ) { 
          continue;
        }
        try {
         hoshen.xsm.lightsoft.corba.CosNaming.BindingListHolder _bl = new hoshen.xsm.lightsoft.corba.CosNaming.BindingListHolder();
         hoshen.xsm.lightsoft.corba.CosNaming.BindingIteratorHolder _bi = new hoshen.xsm.lightsoft.corba.CosNaming.BindingIteratorHolder();
          ((hoshen.xsm.lightsoft.corba.CosNaming.NamingContextOperations)_so.servant).list( how_many,_bl,_bi );
         bl.value = _bl.value;
         bi.value = _bi.value;
          return;
        } finally { _servant_postinvoke( _so ); }
      }
    }
  } // list

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:omg.org/CosNaming/NamingContext:1.0"};

  public String[] _ids ()
  {
    return (String[])__ids.clone ();
  }

  final public static java.lang.Class _opsClass =
    hoshen.xsm.lightsoft.corba.CosNaming.NamingContextOperations.class;

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
} // class _NamingContextStub
