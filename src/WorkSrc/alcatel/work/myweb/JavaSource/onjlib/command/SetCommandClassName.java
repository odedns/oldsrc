/**
 * SetCommandClassName.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * jdk0450.04 v122904173847
 */

package onjlib.command;

public class SetCommandClassName  implements java.io.Serializable {
    private java.lang.String className;

    public SetCommandClassName() {
    }

    public java.lang.String getClassName() {
        return className;
    }

    public void setClassName(java.lang.String className) {
        this.className = className;
    }

    private transient java.lang.ThreadLocal __history;
    public boolean equals(java.lang.Object obj) {
        if (obj == null) { return false; }
        if (obj.getClass() != this.getClass()) { return false;}
        SetCommandClassName other = (SetCommandClassName) obj;
        boolean _equals;
        _equals = true
            && ((this.className==null && other.getClassName()==null) || 
             (this.className!=null &&
              this.className.equals(other.getClassName())));
        if (!_equals) { return false; }
        if (__history == null) {
            synchronized (this) {
                if (__history == null) {
                    __history = new java.lang.ThreadLocal();
                }
            }
        }
        SetCommandClassName history = (SetCommandClassName) __history.get();
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
        SetCommandClassName history = (SetCommandClassName) __hashHistory.get();
        if (history != null) { return 0; }
        __hashHistory.set(this);
        int _hashCode = 1;
        if (getClassName() != null) {
            _hashCode += getClassName().hashCode();
        }
        __hashHistory.set(null);
        return _hashCode;
    }

}