/**
 * GetParamResponse.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * jdk0450.04 v122904173847
 */

package onjlib.command;

public class GetParamResponse  implements java.io.Serializable {
    private java.lang.Object getParamReturn;

    public GetParamResponse() {
    }

    public java.lang.Object getGetParamReturn() {
        return getParamReturn;
    }

    public void setGetParamReturn(java.lang.Object getParamReturn) {
        this.getParamReturn = getParamReturn;
    }

    private transient java.lang.ThreadLocal __history;
    public boolean equals(java.lang.Object obj) {
        if (obj == null) { return false; }
        if (obj.getClass() != this.getClass()) { return false;}
        GetParamResponse other = (GetParamResponse) obj;
        boolean _equals;
        _equals = true
            && ((this.getParamReturn==null && other.getGetParamReturn()==null) || 
             (this.getParamReturn!=null &&
              this.getParamReturn.equals(other.getGetParamReturn())));
        if (!_equals) { return false; }
        if (__history == null) {
            synchronized (this) {
                if (__history == null) {
                    __history = new java.lang.ThreadLocal();
                }
            }
        }
        GetParamResponse history = (GetParamResponse) __history.get();
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
        GetParamResponse history = (GetParamResponse) __hashHistory.get();
        if (history != null) { return 0; }
        __hashHistory.set(this);
        int _hashCode = 1;
        if (getGetParamReturn() != null) {
            _hashCode += getGetParamReturn().hashCode();
        }
        __hashHistory.set(null);
        return _hashCode;
    }

}
