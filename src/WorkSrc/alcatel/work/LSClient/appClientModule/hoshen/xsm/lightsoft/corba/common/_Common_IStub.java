package hoshen.xsm.lightsoft.corba.common;


/**
* hoshen/xsm/lightsoft/corba/common/_Common_IStub.java .
* Generated by the IDL-to-Java compiler (portable), version "3.0"
* from lightsoft/common.idl
* 11:11:09 IST ��� ����� 6 ������ 2005
*/


/**
   * <p>The interface Common_I is a set of services and utilities 
   * that is inherited by every manager interface.<p>
   **/
public class _Common_IStub extends org.omg.CORBA_2_3.portable.ObjectImpl implements hoshen.xsm.lightsoft.corba.common.Common_I
{
  // Constructors
  // NOTE:  If the default constructor is used, the
  //        object is useless until _set_delegate (...)
  //        is called.
  public _Common_IStub ()
  {
    super ();
  }

  public _Common_IStub (org.omg.CORBA.portable.Delegate delegate)
  {
    super ();
    _set_delegate (delegate);
  }


  /**
   * <p>The nativeEMSName is owned by the EMS.  It represents how an EMS user addresses an object
   * on the EMS GUI.  The EMS may or may not support changing this value.</p>
   *
   * <p>When an object is created by the EMS, the EMS selects the nativeEMSName for the object.</p>
   *
   * <p>When an object is created by an NMS, the NMS specifies the userLabel for the object.  
   * If the EMS supports setting of nativeEMSNames, the nativeEMSName should be set to the same
   * value as the userLabel.  If the EMS does not support setting of nativeEMSNames, or if the
   * nativeEMSName has constraints that the
   * userLabel does not satisfy, the EMS selects the nativeEMSName for the object.</p>
   *
   * <p>After an object has been created, the nativeEMSName may be changed by the NMS, if the
   * EMS supports this functionality, using the setNativeEMSName operation.</p>
   *
   * @parm globaldefs::NamingAttributes_T objectName
   * @parm string nativeEMSName
   * @raises globaldefs::ProcessingFailureException<dir>
   * EXCPT_NOT_IMPLEMENTED - If EMS does not support this service<br>
   * EXCPT_INTERNAL_ERROR - Raised in case of non-specific EMS internal failure<br>
   * EXCPT_INVALID_INPUT - Raised when objectName is incorrectly formed<br>
   * EXCPT_ENTITY_NOT_FOUND - Raised when objectName references object which does not exist<br>
   * EXCPT_NE_COMM_LOSS - Raised when communications to managedElement is lost
   * </dir>
   **/
  public void setNativeEMSName (hoshen.xsm.lightsoft.corba.globaldefs.NameAndStringValue_T[] objectName, String nativeEMSName) throws hoshen.xsm.lightsoft.corba.globaldefs.ProcessingFailureException
  {
    if ( !this._is_local() ) {
      org.omg.CORBA.portable.InputStream _in = null;
      try {
         org.omg.CORBA.portable.OutputStream _out = _request ("setNativeEMSName",true);
         hoshen.xsm.lightsoft.corba.globaldefs.NVSList_THelper.write (_out, objectName);
         _out.write_string (nativeEMSName);
         _in = _invoke (_out);
         return;
      } catch (org.omg.CORBA.portable.ApplicationException _ex) {
         _in = _ex.getInputStream ();
         String _id = _ex.getId ();
         if ( _id.equals ( hoshen.xsm.lightsoft.corba.globaldefs.ProcessingFailureExceptionHelper.id() ) )
            throw hoshen.xsm.lightsoft.corba.globaldefs.ProcessingFailureExceptionHelper.read( _in );
         else throw new org.omg.CORBA.UNKNOWN( "Unexpected User Exception: " + _id );
      } catch (org.omg.CORBA.portable.RemarshalException _rm) {
        setNativeEMSName( objectName,nativeEMSName ); return;
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
        _servant_preinvoke( "setNativeEMSName",_opsClass );
      if ( _so == null ) { setNativeEMSName( objectName,nativeEMSName ); return; }
      try {
         ((hoshen.xsm.lightsoft.corba.common.Common_IOperations)_so.servant).setNativeEMSName( objectName,nativeEMSName );
         return;
      } finally { _servant_postinvoke( _so ); }
    }
  } // setNativeEMSName


  /**
   * <p>The userLabel is owned by the NMSes.  It is a string assigned by an NMS to an object.
   * The difference between the userLabel and the NamingAttributes name
   * is that the userLabel is an attribute of the objects that 
   * may be "set" by the NMS through well defined interfaces (setUserLabel).</p>
   *
   * <p>When an object is created by an NMS, the NMS specifies the userLabel for the object.</p>
   *
   * <p>When an object is created by the EMS, the EMS sets the userLabel to the nativeEMSName.</p>
   *
   * <p>Once an object is created, the userLabel may only be changed by an NMS through the 
   * setUserLabel operation.</p>
   *
   * @parm globaldefs::NamingAttributes_T objectName: Name of the object for which to change the
   *  userLabel.
   * @parm string userLabel: New user label to assign to the object
   * @parm boolean enforceUniqueness: Specifies whether or not userLabel should be checked for
   *  uniqueness amongst objects of the same class within the EMS.  If true, then the operation
   *  will fail if userLabel is already in use.
   * @raises globaldefs::ProcessingFailureException<dir>
   * EXCPT_NOT_IMPLEMENTED - If EMS does not support this service<br>
   * EXCPT_INTERNAL_ERROR - Raised in case of non-specific EMS internal failure<br>
   * EXCPT_INVALID_INPUT - Raised when objectName is incorrectly formed<br>
   * EXCPT_ENTITY_NOT_FOUND - Raised when objectName references object which does not exist<br>
   * EXCPT_NE_COMM_LOSS - Raised when communications to managedElement is lost<br>
   * EXCPT_USERLABEL_IN_USE - Raised when the userLabel uniqueness constraint is not met<br>
   * </dir>
   **/
  public void setUserLabel (hoshen.xsm.lightsoft.corba.globaldefs.NameAndStringValue_T[] objectName, String userLabel, boolean enforceUniqueness) throws hoshen.xsm.lightsoft.corba.globaldefs.ProcessingFailureException
  {
    if ( !this._is_local() ) {
      org.omg.CORBA.portable.InputStream _in = null;
      try {
         org.omg.CORBA.portable.OutputStream _out = _request ("setUserLabel",true);
         hoshen.xsm.lightsoft.corba.globaldefs.NVSList_THelper.write (_out, objectName);
         _out.write_string (userLabel);
         _out.write_boolean (enforceUniqueness);
         _in = _invoke (_out);
         return;
      } catch (org.omg.CORBA.portable.ApplicationException _ex) {
         _in = _ex.getInputStream ();
         String _id = _ex.getId ();
         if ( _id.equals ( hoshen.xsm.lightsoft.corba.globaldefs.ProcessingFailureExceptionHelper.id() ) )
            throw hoshen.xsm.lightsoft.corba.globaldefs.ProcessingFailureExceptionHelper.read( _in );
         else throw new org.omg.CORBA.UNKNOWN( "Unexpected User Exception: " + _id );
      } catch (org.omg.CORBA.portable.RemarshalException _rm) {
        setUserLabel( objectName,userLabel,enforceUniqueness ); return;
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
        _servant_preinvoke( "setUserLabel",_opsClass );
      if ( _so == null ) { setUserLabel( objectName,userLabel,enforceUniqueness ); return; }
      try {
         ((hoshen.xsm.lightsoft.corba.common.Common_IOperations)_so.servant).setUserLabel( objectName,userLabel,enforceUniqueness );
         return;
      } finally { _servant_postinvoke( _so ); }
    }
  } // setUserLabel


  /** 
   * <p>This service sets the owner attribute of the specified object.</p>
   *
   * @parm globaldefs::NamingAttributes_T objectName
   * @parm string owner
   * @raises globaldefs::ProcessingFailureException<dir>
   * EXCPT_NOT_IMPLEMENTED - If EMS does not support this service<br>
   * EXCPT_INTERNAL_ERROR - Raised in case of non-specific EMS internal failure<br>
   * EXCPT_INVALID_INPUT - Raised when objectName is incorrectly formed<br>
   * EXCPT_ENTITY_NOT_FOUND - Raised when objectName references an object
   *  that does not exist<br>
   * EXCPT_NE_COMM_LOSS - Raised when communications to managedElement is lost<br>
   * </dir>
   **/
  public void setOwner (hoshen.xsm.lightsoft.corba.globaldefs.NameAndStringValue_T[] objectName, String owner) throws hoshen.xsm.lightsoft.corba.globaldefs.ProcessingFailureException
  {
    if ( !this._is_local() ) {
      org.omg.CORBA.portable.InputStream _in = null;
      try {
         org.omg.CORBA.portable.OutputStream _out = _request ("setOwner",true);
         hoshen.xsm.lightsoft.corba.globaldefs.NVSList_THelper.write (_out, objectName);
         _out.write_string (owner);
         _in = _invoke (_out);
         return;
      } catch (org.omg.CORBA.portable.ApplicationException _ex) {
         _in = _ex.getInputStream ();
         String _id = _ex.getId ();
         if ( _id.equals ( hoshen.xsm.lightsoft.corba.globaldefs.ProcessingFailureExceptionHelper.id() ) )
            throw hoshen.xsm.lightsoft.corba.globaldefs.ProcessingFailureExceptionHelper.read( _in );
         else throw new org.omg.CORBA.UNKNOWN( "Unexpected User Exception: " + _id );
      } catch (org.omg.CORBA.portable.RemarshalException _rm) {
        setOwner( objectName,owner ); return;
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
        _servant_preinvoke( "setOwner",_opsClass );
      if ( _so == null ) { setOwner( objectName,owner ); return; }
      try {
         ((hoshen.xsm.lightsoft.corba.common.Common_IOperations)_so.servant).setOwner( objectName,owner );
         return;
      } finally { _servant_postinvoke( _so ); }
    }
  } // setOwner


  /** 
   * <p>This service retrieves the capabilities of the manager.
   * All non-specified capabilities are assumed to be unsupported.</p>
   *
   * @parm CapabilityList_T capabilities
   * @raises globaldefs::ProcessingFailureException:<dir>
   * EXCPT_INTERNAL_ERROR - Raised in case of non-specific EMS internal failure.
   * </dir>
   **/
  public void getCapabilities (hoshen.xsm.lightsoft.corba.common.CapabilityList_THolder capabilities) throws hoshen.xsm.lightsoft.corba.globaldefs.ProcessingFailureException
  {
    if ( !this._is_local() ) {
      org.omg.CORBA.portable.InputStream _in = null;
      try {
         org.omg.CORBA.portable.OutputStream _out = _request ("getCapabilities",true);
         _in = _invoke (_out);
         capabilities.value = hoshen.xsm.lightsoft.corba.common.CapabilityList_THelper.read (_in);
         return;
      } catch (org.omg.CORBA.portable.ApplicationException _ex) {
         _in = _ex.getInputStream ();
         String _id = _ex.getId ();
         if ( _id.equals ( hoshen.xsm.lightsoft.corba.globaldefs.ProcessingFailureExceptionHelper.id() ) )
            throw hoshen.xsm.lightsoft.corba.globaldefs.ProcessingFailureExceptionHelper.read( _in );
         else throw new org.omg.CORBA.UNKNOWN( "Unexpected User Exception: " + _id );
      } catch (org.omg.CORBA.portable.RemarshalException _rm) {
        getCapabilities( capabilities ); return;
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
        _servant_preinvoke( "getCapabilities",_opsClass );
      if ( _so == null ) { getCapabilities( capabilities ); return; }
      try {
         hoshen.xsm.lightsoft.corba.common.CapabilityList_THolder _capabilities = new hoshen.xsm.lightsoft.corba.common.CapabilityList_THolder();
         ((hoshen.xsm.lightsoft.corba.common.Common_IOperations)_so.servant).getCapabilities( _capabilities );
         capabilities.value = _capabilities.value;
         return;
      } finally { _servant_postinvoke( _so ); }
    }
  } // getCapabilities

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:mtnm.tmforum.org/common/Common_I:1.0"};

  public String[] _ids ()
  {
    return (String[])__ids.clone ();
  }

  final public static java.lang.Class _opsClass =
    hoshen.xsm.lightsoft.corba.common.Common_IOperations.class;

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
} // class _Common_IStub
