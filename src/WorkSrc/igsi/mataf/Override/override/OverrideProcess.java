package override;
import org.apache.wsif.util.*;
import org.xmlsoap.schemas.wsdl.wsadie.messages.BooleanMessage;
import org.xmlsoap.schemas.wsdl.wsadie.messages.FloatMessage;
import org.xmlsoap.schemas.wsdl.wsadie.messages.StringMessage;

import override_msg.OverrideInputMessage;
import override_msg.OverrideOutputMessage;

import org.apache.wsif.*;
import com.ibm.bpe.api.ProcessException;
import java.util.HashMap;
import java.util.Iterator;
/*
 * Builder generated code, only edit user code sections.
 * @generated
 */
public class OverrideProcess
	extends com.ibm.bpe.data.ProcessBackingBase
	implements com.ibm.bpe.data.WSDLVariableCallback {
	/*
	 * Global Data Context getter method
	 * NOTE: Do NOT edit, this method is automatically generated.
	 *       ANY CHANGES WILL NOT BE SAVED.
	 * @generated FlowContainer_1
	 */
	public org.xmlsoap.schemas.wsdl.wsadie.messages.IntMessage getInput()
		throws com.ibm.bpe.api.ProcessException {

		return new org.xmlsoap.schemas.wsdl.wsadie.messages.IntMessage(
			(WSIFMessage) readBPEVariable("input"));
	}
	/*
	 * Global Data Context setter method
	 * NOTE: Do NOT edit, this method is automatically generated.
	 *       ANY CHANGES WILL NOT BE SAVED.
	 * @generated FlowContainer_1
	 */
	public void setInput(
		org.xmlsoap.schemas.wsdl.wsadie.messages.IntMessage message)
		throws com.ibm.bpe.api.ProcessException {

		updateBPEVariable("input", message.message());
	}
	/*
	 * Global Data Context getter method
	 * NOTE: Do NOT edit, this method is automatically generated.
	 *       ANY CHANGES WILL NOT BE SAVED.
	 * @generated FlowContainer_2
	 */
	public org.xmlsoap.schemas.wsdl.wsadie.messages.IntMessage getOutput()
		throws com.ibm.bpe.api.ProcessException {

		return new org.xmlsoap.schemas.wsdl.wsadie.messages.IntMessage(
			(WSIFMessage) readBPEVariable("output"));
	}
	/*
	 * Global Data Context setter method
	 * NOTE: Do NOT edit, this method is automatically generated.
	 *       ANY CHANGES WILL NOT BE SAVED.
	 * @generated FlowContainer_2
	 */
	public void setOutput(
		org.xmlsoap.schemas.wsdl.wsadie.messages.IntMessage message)
		throws com.ibm.bpe.api.ProcessException {

		updateBPEVariable("output", message.message());
	}
	/*
	 * Global Data Context getter method
	 * NOTE: Do NOT edit, this method is automatically generated.
	 *       ANY CHANGES WILL NOT BE SAVED.
	 * @generated FlowContainer_3
	 */
	public org
		.xmlsoap
		.schemas
		.wsdl
		.wsadie
		.messages
		.StringMessage getChosenManager()
		throws com.ibm.bpe.api.ProcessException {

		return new org.xmlsoap.schemas.wsdl.wsadie.messages.StringMessage(
			(WSIFMessage) readBPEVariable("chosenManager"));
	}
	/*
	 * Global Data Context setter method
	 * NOTE: Do NOT edit, this method is automatically generated.
	 *       ANY CHANGES WILL NOT BE SAVED.
	 * @generated FlowContainer_3
	 */
	public void setChosenManager(
		org.xmlsoap.schemas.wsdl.wsadie.messages.StringMessage message)
		throws com.ibm.bpe.api.ProcessException {

		updateBPEVariable("chosenManager", message.message());
	}
	/*
	 * Global Data Context getter method
	 * NOTE: Do NOT edit, this method is automatically generated.
	 *       ANY CHANGES WILL NOT BE SAVED.
	 * @generated FlowContainer_4
	 */
	public org
		.xmlsoap
		.schemas
		.wsdl
		.wsadie
		.messages
		.BooleanMessage getOverrideResult()
		throws com.ibm.bpe.api.ProcessException {

		return new org.xmlsoap.schemas.wsdl.wsadie.messages.BooleanMessage(
			(WSIFMessage) readBPEVariable("overrideResult"));
	}
	/*
	 * Global Data Context setter method
	 * NOTE: Do NOT edit, this method is automatically generated.
	 *       ANY CHANGES WILL NOT BE SAVED.
	 * @generated FlowContainer_4
	 */
	public void setOverrideResult(
		org.xmlsoap.schemas.wsdl.wsadie.messages.BooleanMessage message)
		throws com.ibm.bpe.api.ProcessException {

		updateBPEVariable("overrideResult", message.message());
	}
	/*
	 * Global Data Context getter method
	 * NOTE: Do NOT edit, this method is automatically generated.
	 *       ANY CHANGES WILL NOT BE SAVED.
	 * @generated FlowContainer_5
	 */
	public org.xmlsoap.schemas.wsdl.wsadie.messages.FloatMessage getSum()
		throws com.ibm.bpe.api.ProcessException {

		return new org.xmlsoap.schemas.wsdl.wsadie.messages.FloatMessage(
			(WSIFMessage) readBPEVariable("sum"));
	}
	/*
	 * Global Data Context setter method
	 * NOTE: Do NOT edit, this method is automatically generated.
	 *       ANY CHANGES WILL NOT BE SAVED.
	 * @generated FlowContainer_5
	 */
	public void setSum(
		org.xmlsoap.schemas.wsdl.wsadie.messages.FloatMessage message)
		throws com.ibm.bpe.api.ProcessException {

		updateBPEVariable("sum", message.message());
	}
	/*
	 * Control condition method
	 * @generated FlowConditionalControlConnection_1
	 */
	public boolean controlCondition_FlowNode_6_out_FlowNode_5_in(WSIFMessage sourceMessage)
		throws com.ibm.bpe.api.ProcessException {
		boolean result = true;

		result = controlCondition_FlowNode_6_out_FlowNode_5_in();

		return result;
	}
	/*
	 * Control condition method
	 * NOTE: Only code contained in the USER CODE SECTION will be saved:
	 *		// user code begin {Condition Expression}
	 *		// user code end
	 * @generated FlowConditionalControlConnection_1
	 */
	public boolean controlCondition_FlowNode_6_out_FlowNode_5_in()
		throws com.ibm.bpe.api.ProcessException {
		boolean result = true;

		// user code begin {Condition Expression}
		OverrideInputMessage inMsg = getOverrideInput();
		float sum = inMsg.getSum();
		if (sum > 100) {
			result = false;
		}
		System.out.println("sum = " + sum);
		// user code end

		return result;
	}
	/*
	 * Correlation ID getter method
	 * NOTE: Only code contained in the USER CODE SECTION will be saved:
	 *		// user code begin {Correlation ID Expression}
	 *		// user code end
	 * @generated FlowNode_1
	 */
	public static String getCorrelationSetFromoverrideInput(
		override_msg.OverrideInputMessage message)
		throws com.ibm.bpe.api.ProcessException, org.apache.wsif.WSIFException {
		String correlationID = null;

		// user code begin {Correlation ID Expression}
		// user code end

		return correlationID;
	}
	/*
	 * Global Data Context getter method
	 * NOTE: Do NOT edit, this method is automatically generated.
	 *       ANY CHANGES WILL NOT BE SAVED.
	 * @generated FlowContainer_9
	 */
	public override_msg.OverrideInputMessage getOverrideInput()
		throws com.ibm.bpe.api.ProcessException {

		return new override_msg.OverrideInputMessage(
			(WSIFMessage) readBPEVariable("overrideInput"));
	}
	/*
	 * Global Data Context setter method
	 * NOTE: Do NOT edit, this method is automatically generated.
	 *       ANY CHANGES WILL NOT BE SAVED.
	 * @generated FlowContainer_9
	 */
	public void setOverrideInput(override_msg.OverrideInputMessage message)
		throws com.ibm.bpe.api.ProcessException {

		updateBPEVariable("overrideInput", message.message());
	}
	/*
	 * Global Data Context getter method
	 * NOTE: Do NOT edit, this method is automatically generated.
	 *       ANY CHANGES WILL NOT BE SAVED.
	 * @generated FlowContainer_10
	 */
	public override_msg.OverrideOutputMessage getOverrideOutput()
		throws com.ibm.bpe.api.ProcessException {

		return new override_msg.OverrideOutputMessage(
			(WSIFMessage) readBPEVariable("overrideOutput"));
	}
	/*
	 * Global Data Context setter method
	 * NOTE: Do NOT edit, this method is automatically generated.
	 *       ANY CHANGES WILL NOT BE SAVED.
	 * @generated FlowContainer_10
	 */
	public void setOverrideOutput(override_msg.OverrideOutputMessage message)
		throws com.ibm.bpe.api.ProcessException {

		updateBPEVariable("overrideOutput", message.message());
	}
	/*
	 * Java Snippet Activity Method
	 * NOTE: Only code contained in the USER CODE SECTION will be saved:
	 *		// user code begin {Java Snippet Activity}
	 *		// user code end
	 * @generated FlowNode_5
	 */
	public void javaSnippet_5() throws com.ibm.bpe.api.ProcessException {
		// user code begin {Java Snippet Activity}
		OverrideInputMessage inMsg = getOverrideInput();
		float sum = inMsg.getSum();
		StringMessage chosenManager = getChosenManager();
		BooleanMessage res =  getOverrideResult();
		OverrideOutputMessage outMsg = getOverrideOutput();
		outMsg.setSum(sum);
		outMsg.setChosenManager(chosenManager.getValue());
		outMsg.setOverrideResult(res.getValue());
	
		System.out.println(
			"sum = "
				+ sum
				+ "\tchosenManager = "
				+ outMsg.getChosenManager() 
				+ "\toverrideResult= " + outMsg.getOverrideResult());


		// user code end
	}
	/*
	 * Java Snippet Activity Method
	 * NOTE: Only code contained in the USER CODE SECTION will be saved:
	 *		// user code begin {Java Snippet Activity}
	 *		// user code end
	 * @generated FlowNode_6
	 */
	public void javaSnippet_6() throws com.ibm.bpe.api.ProcessException {
		// user code begin {Java Snippet Activity}
		StringMessage sm = new StringMessage();
		sm.setValue("");
		BooleanMessage bm = new BooleanMessage();
		bm.setValue(false);
		setChosenManager(sm);
		setOverrideResult(bm);
		// user code end
	}
}
