// Stub class generated by rmic, do not edit.
// Contents subject to change without notice.

public final class Hello7Server_Stub
    extends java.rmi.server.RemoteStub
    implements Hello7, java.rmi.Remote
{
    private static java.rmi.server.Operation[] operations = {
        new java.rmi.server.Operation("java.util.Date getTime()"), 
        new java.rmi.server.Operation("java.lang.String printHello()")
    };
    
    private static final long interfaceHash = 4600289229433332260L;
    
    // Constructors
    public Hello7Server_Stub() {
        super();
	 System.out.println("in constructor");
    }
    public Hello7Server_Stub(java.rmi.server.RemoteRef rep) {
        super(rep);
	 System.out.println("in constructor");
    }
    // Methods from remote interfaces
    
    // Implementation of getTime
    public java.util.Date getTime() throws java.rmi.RemoteException {
        int opnum = 0;
        java.rmi.server.RemoteRef sub = ref;
	 System.out.println("in getTime");
        java.rmi.server.RemoteCall call = sub.newCall((java.rmi.server.RemoteObject)this, operations, opnum, interfaceHash);
        try {
            sub.invoke(call);
        } catch (java.rmi.RemoteException ex) {
            throw ex;
        } catch (java.lang.Exception ex) {
            throw new java.rmi.UnexpectedException("Unexpected exception", ex);
        };
        java.util.Date $result;
        try {
            java.io.ObjectInput in = call.getInputStream();
            $result = (java.util.Date)in.readObject();
        } catch (java.io.IOException ex) {
            throw new java.rmi.UnmarshalException("Error unmarshaling return", ex);
        } catch (java.lang.ClassNotFoundException ex) {
		ex.printStackTrace();
            throw new java.rmi.UnmarshalException("Return value class not found", ex);
        } catch (Exception ex) {
            throw new java.rmi.UnexpectedException("Unexpected exception", ex);
        } finally {
            sub.done(call);
        }
        return $result;
    }
    
    // Implementation of printHello
    public java.lang.String printHello() throws java.rmi.RemoteException {
        int opnum = 1;
        java.rmi.server.RemoteRef sub = ref;
	 System.out.println("in printHello");
        java.rmi.server.RemoteCall call = sub.newCall((java.rmi.server.RemoteObject)this, operations, opnum, interfaceHash);
        try {
            sub.invoke(call);
        } catch (java.rmi.RemoteException ex) {
            throw ex;
        } catch (java.lang.Exception ex) {
            throw new java.rmi.UnexpectedException("Unexpected exception", ex);
        };
        java.lang.String $result;
        try {
            java.io.ObjectInput in = call.getInputStream();
            $result = (java.lang.String)in.readObject();
        } catch (java.io.IOException ex) {
            throw new java.rmi.UnmarshalException("Error unmarshaling return", ex);
        } catch (java.lang.ClassNotFoundException ex) {
            throw new java.rmi.UnmarshalException("Return value class not found", ex);
        } catch (Exception ex) {
            throw new java.rmi.UnexpectedException("Unexpected exception", ex);
        } finally {
            sub.done(call);
        }
        return $result;
    }
}
