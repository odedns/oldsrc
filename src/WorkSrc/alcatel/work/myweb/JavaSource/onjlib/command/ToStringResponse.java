/**
 * ToStringResponse.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * jdk0450.04 v122904173847
 */

package onjlib.command;

public class ToStringResponse  implements java.io.Serializable {
    private java.lang.String toStringReturn;

    public ToStringResponse() {
    }

    public java.lang.String getToStringReturn() {
        return toStringReturn;
    }

    public void setToStringReturn(java.lang.String toStringReturn) {
        this.toStringReturn = toStringReturn;
    }

    private transient java.lang.ThreadLocal __history;
    public boolean equals(java.lang.Object obj) {
        if (obj == null) { return false; }
        if (obj.getClass() != this.getClass()) { return false;}
        ToStringResponse other = (ToStringResponse) obj;
        boolean _equals;
        _equals = true
            && ((this.toStringReturn==null && other.getToStringReturn()==null) || 
             (this.toStringReturn!=null &&
              this.toStringReturn.equals(other.getToStringReturn())));
        if (!_equals) { return false; }
        if (__history == null) {
            synchronized (this) {
                if (__history == null) {
                    __history = new java.lang.ThreadLocal();
                }
            }
        }
        ToStringResponse history = (ToStringResponse) __history.get();
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
        ToStringResponse history = (ToStringResponse) __hashHistory.get();
        if (history != null) { return 0; }
        __hashHistory.set(this);
        int _hashCode = 1;
        if (getToStringReturn() != null) {
            _hashCode += getToStringReturn().hashCode();
        }
        __hashHistory.set(null);
        return _hashCode;
    }

}
