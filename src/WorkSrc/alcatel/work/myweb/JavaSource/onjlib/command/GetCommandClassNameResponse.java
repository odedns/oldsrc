/**
 * GetCommandClassNameResponse.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * jdk0450.04 v122904173847
 */

package onjlib.command;

public class GetCommandClassNameResponse  implements java.io.Serializable {
    private java.lang.String getCommandClassNameReturn;

    public GetCommandClassNameResponse() {
    }

    public java.lang.String getGetCommandClassNameReturn() {
        return getCommandClassNameReturn;
    }

    public void setGetCommandClassNameReturn(java.lang.String getCommandClassNameReturn) {
        this.getCommandClassNameReturn = getCommandClassNameReturn;
    }

    private transient java.lang.ThreadLocal __history;
    public boolean equals(java.lang.Object obj) {
        if (obj == null) { return false; }
        if (obj.getClass() != this.getClass()) { return false;}
        GetCommandClassNameResponse other = (GetCommandClassNameResponse) obj;
        boolean _equals;
        _equals = true
            && ((this.getCommandClassNameReturn==null && other.getGetCommandClassNameReturn()==null) || 
             (this.getCommandClassNameReturn!=null &&
              this.getCommandClassNameReturn.equals(other.getGetCommandClassNameReturn())));
        if (!_equals) { return false; }
        if (__history == null) {
            synchronized (this) {
                if (__history == null) {
                    __history = new java.lang.ThreadLocal();
                }
            }
        }
        GetCommandClassNameResponse history = (GetCommandClassNameResponse) __history.get();
        if (history != null) { return (history == obj); }
        if (this == obj) return true;
        __history.set(obj);
        __history.set(null);
        return true;
    }

    private transient java.lang.ThreadLocal __hashHistory;
    public int hashCode() {
        if (__hashHistory == null) {
            synchronized (this) {
                if (__hashHistory == null) {
                    __hashHistory = new java.lang.ThreadLocal();
                }
            }
        }
        GetCommandClassNameResponse history = (GetCommandClassNameResponse) __hashHistory.get();
        if (history != null) { return 0; }
        __hashHistory.set(this);
        int _hashCode = 1;
        if (getGetCommandClassNameReturn() != null) {
            _hashCode += getGetCommandClassNameReturn().hashCode();
        }
        __hashHistory.set(null);
        return _hashCode;
    }

}
