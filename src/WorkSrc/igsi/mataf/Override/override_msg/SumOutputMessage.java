package override_msg;
import org.apache.wsif.WSIFMessage;
import org.apache.wsif.WSIFException;
import javax.xml.namespace.QName;
import com.ibm.wsdl.MessageImpl;
/**
 * SumOutputMessage
 * Generated code. Only edit user code sections.
 * @generated
 */
public class SumOutputMessage extends com.ibm.wsif.jca.util.WSIFMessageImpl {
	/**
	 * SumOutputMessage
	 * @generated
	 */
	public SumOutputMessage(WSIFMessage message) {
		super(message);
	}
	/**
	 * SumOutputMessage
	 * @generated
	 */
	public SumOutputMessage() {
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
