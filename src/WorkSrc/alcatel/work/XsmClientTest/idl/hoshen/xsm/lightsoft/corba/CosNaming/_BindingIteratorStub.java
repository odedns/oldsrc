package hoshen.xsm.lightsoft.corba.CosNaming;


/**
* hoshen/xsm/lightsoft/corba/CosNaming/_BindingIteratorStub.java .
* Generated by the IDL-to-Java compiler (portable), version "3.0"
* from lightsoft/CosNaming.idl
* 13:10:56 GMT+02:00 ��� ����� 28 ���� 2007
*/


/**
 * The BindingIterator interface allows a client to iterate through the
 * bindings using the next_one or next_n operations.
 */
public class _BindingIteratorStub extends org.omg.CORBA_2_3.portable.ObjectImpl implements hoshen.xsm.lightsoft.corba.CosNaming.BindingIterator
{
  // Constructors
  // NOTE:  If the default constructor is used, the
  //        object is useless until _set_delegate (...)
  //        is called.
  public _BindingIteratorStub ()
  {
    super ();
  }

  public _BindingIteratorStub (org.omg.CORBA.portable.Delegate delegate)
  {
    super ();
    _set_delegate (delegate);
  }


  /**
 * This operation returns the next binding. If there are no more
 * bindings, false is returned.
 *
 * @parm b - next binding.
 */
  public boolean next_one (hoshen.xsm.lightsoft.corba.CosNaming.BindingHolder b)
  {
    while(true) {
      if ( !this._is_local() ) {
        org.omg.CORBA.portable.InputStream _in = null;
        try {
          org.omg.CORBA.portable.OutputStream _out = _request ("next_one",true);
          _in = _invoke (_out);
          boolean __result = _in.read_boolean ();
          b.value = hoshen.xsm.lightsoft.corba.CosNaming.BindingHelper.read (_in);
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
          _servant_preinvoke( "next_one",_opsClass );
        if ( _so == null ) { 
          continue;
        }
        try {
         hoshen.xsm.lightsoft.corba.CosNaming.BindingHolder _b = new hoshen.xsm.lightsoft.corba.CosNaming.BindingHolder();
          boolean __result = ((hoshen.xsm.lightsoft.corba.CosNaming.BindingIteratorOperations)_so.servant).next_one( _b );
         b.value = _b.value;
          return __result;
        } finally { _servant_postinvoke( _so ); }
      }
    }
  } // next_one


  /** 
 * This operation returns at most the requested number of bindings.
 *
 * @parm how_many - maximum number of binding to return in bl.
 * @parm bl - list of bindings.
 */
  public boolean next_n (int how_many, hoshen.xsm.lightsoft.corba.CosNaming.BindingListHolder bl)
  {
    while(true) {
      if ( !this._is_local() ) {
        org.omg.CORBA.portable.InputStream _in = null;
        try {
          org.omg.CORBA.portable.OutputStream _out = _request ("next_n",true);
          _out.write_ulong (how_many);
          _in = _invoke (_out);
          boolean __result = _in.read_boolean ();
          bl.value = hoshen.xsm.lightsoft.corba.CosNaming.BindingListHelper.read (_in);
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
          _servant_preinvoke( "next_n",_opsClass );
        if ( _so == null ) { 
          continue;
        }
        try {
         hoshen.xsm.lightsoft.corba.CosNaming.BindingListHolder _bl = new hoshen.xsm.lightsoft.corba.CosNaming.BindingListHolder();
          boolean __result = ((hoshen.xsm.lightsoft.corba.CosNaming.BindingIteratorOperations)_so.servant).next_n( how_many,_bl );
         bl.value = _bl.value;
          return __result;
        } finally { _servant_postinvoke( _so ); }
      }
    }
  } // next_n


  /** 
 * This operation destroys the iterator.
 */
  public void destroy ()
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
          _servant_preinvoke( "destroy",_opsClass );
        if ( _so == null ) { 
          continue;
        }
        try {
          ((hoshen.xsm.lightsoft.corba.CosNaming.BindingIteratorOperations)_so.servant).destroy(  );
          return;
        } finally { _servant_postinvoke( _so ); }
      }
    }
  } // destroy

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:omg.org/CosNaming/BindingIterator:1.0"};

  public String[] _ids ()
  {
    return (String[])__ids.clone ();
  }

  final public static java.lang.Class _opsClass =
    hoshen.xsm.lightsoft.corba.CosNaming.BindingIteratorOperations.class;

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
} // class _BindingIteratorStub