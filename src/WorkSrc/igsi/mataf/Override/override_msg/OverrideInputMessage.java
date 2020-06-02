package override_msg;
import org.apache.wsif.WSIFMessage;
import org.apache.wsif.WSIFException;
import javax.xml.namespace.QName;
import com.ibm.wsdl.MessageImpl;
/**
 * OverrideInputMessage
 * Generated code. Only edit user code sections.
 * @generated
 */
public class OverrideInputMessage
	extends com.ibm.wsif.jca.util.WSIFMessageImpl {
	/**
	 * OverrideInputMessage
	 * @generated
	 */
	public OverrideInputMessage(WSIFMessage message) {
		super(message);
	}
	/**
	 * OverrideInputMessage
	 * @generated
	 */
	public OverrideInputMessage() {
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
}
