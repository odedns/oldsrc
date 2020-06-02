package override_msg;
import org.apache.wsif.WSIFMessage;
import org.apache.wsif.WSIFException;
import javax.xml.namespace.QName;
import com.ibm.wsdl.MessageImpl;
/**
 * OverrideOutputMessage
 * Generated code. Only edit user code sections.
 * @generated
 */
public class OverrideOutputMessage
	extends com.ibm.wsif.jca.util.WSIFMessageImpl {
	/**
	 * OverrideOutputMessage
	 * @generated
	 */
	public OverrideOutputMessage(WSIFMessage message) {
		super(message);
	}
	/**
	 * OverrideOutputMessage
	 * @generated
	 */
	public OverrideOutputMessage() {
		super();
	}
	/**
	 * getSum
	 * @generated
	 */
	public float getSum() {
		try {
			return ((Float) super.getObjectPart("sum")).floatValue();
		} catch (WSIFException exc) {
			throw new java.lang.IllegalArgumentException(exc.toString());
		}
	}
	/**
	 * setSum
	 * @generated
	 */
	public void setSum(float newValue) {
		try {
			super.setObjectPart("sum", new Float(newValue));
		} catch (WSIFException exc) {
			throw new java.lang.IllegalArgumentException(exc.toString());
		}
	}
	/**
	 * getOverrideResult
	 * @generated
	 */
	public boolean getOverrideResult() {
		try {
			return ((Boolean) super.getObjectPart("overrideResult"))
				.booleanValue();
		} catch (WSIFException exc) {
			throw new java.lang.IllegalArgumentException(exc.toString());
		}
	}
	/**
	 * setOverrideResult
	 * @generated
	 */
	public void setOverrideResult(boolean newValue) {
		try {
			super.setObjectPart("overrideResult", new Boolean(newValue));
		} catch (WSIFException exc) {
			throw new java.lang.IllegalArgumentException(exc.toString());
		}
	}
	/**
	 * getChosenManager
	 * @generated
	 */
	public java.lang.String getChosenManager() {
		try {
			return (java.lang.String) super.getObjectPart("chosenManager");
		} catch (WSIFException exc) {
			throw new java.lang.IllegalArgumentException(exc.toString());
		}
	}
	/**
	 * setChosenManager
	 * @generated
	 */
	public void setChosenManager(java.lang.String newValue) {
		try {
			super.setObjectPart("chosenManager", newValue);
		} catch (WSIFException exc) {
			throw new java.lang.IllegalArgumentException(exc.toString());
		}
	}
}
