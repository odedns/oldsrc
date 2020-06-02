package override;
import org.apache.wsif.*;
import com.ibm.bpe.api.*;
import com.ibm.wsif.flow.util.ReplyContextImpl;
import com.ibm.wsif.flow.util.JMSReplyContextImpl;
import javax.xml.namespace.QName;
/**
 * OverrideProcessPortTypeServiceMDB
 * Generated code. Only edit user code sections.
 * @generated
 */
public class OverrideProcessPortTypeServiceMDB
	implements javax.ejb.MessageDrivenBean, javax.jms.MessageListener {
	/**
	 * @generated
	 */
	private static final String fieldWSADIEVersion = "5.0";
	/**
	 * @generated
	 */
	private javax.ejb.MessageDrivenContext fieldMessageDrivenContext;
	/**
	 * @generated
	 */
	private WSIFPort fieldInboundPort = null;
	/**
	 * @generated
	 */
	private LocalBusinessProcess fieldBusinessProcessBean;
	/**
	 * setMessageDrivenContext
	 * @generated
	 */
	public void setMessageDrivenContext(
		javax.ejb.MessageDrivenContext newMessageDrivenContext) {
		fieldMessageDrivenContext = newMessageDrivenContext;
	}
	/**
	 * onMessage
	 * @generated
	 */
	public void onMessage(javax.jms.Message msg) {
		try {
			executeFlowOperation(msg);
		} catch (WSIFException e) {
			e.printStackTrace();
		} catch (javax.jms.JMSException e) {
			e.printStackTrace();
		} catch (ProcessException e) {
			e.printStackTrace();
		} catch (javax.naming.NamingException e) {
			e.printStackTrace();
		} catch (javax.ejb.CreateException e) {
			e.printStackTrace();
		}
	}
	/**
	 * ejbCreate
	 * @generated
	 */
	public void ejbCreate() {
	}
	/**
	 * ejbRemove
	 * @generated
	 */
	public void ejbRemove() {
		if (fieldInboundPort != null) {
			try {
				fieldInboundPort.close();
			} catch (WSIFException e) {
				throw new javax.ejb.EJBException(e);
			}
		}
	}
	/**
	 * getInboundPort
	 * @generated
	 */
	public WSIFPort getInboundPort() throws WSIFException {

		if (fieldInboundPort == null) {
			// user code begin {custom_initialization}
			// user code end

			WSIFService service =
				WSIFServiceFactory.newInstance().getService(
					"override/OverrideProcessPortTypeJMSService.wsdl",
					this.getClass().getClassLoader(),
					"http://override/OverrideProcessPortTypeJMSService/",
					"OverrideProcessPortTypeService",
					"http://override/",
					"OverrideProcessPortType");

			if (service == null)
				return null;

			// user code begin {service_setup}
			// user code end

			fieldInboundPort =
				service.getPort("OverrideProcessPortTypeJMSPort");
		}
		return fieldInboundPort;
	}
	/**
	 * executeFlowOperation
	 * @generated
	 */
	public void executeFlowOperation(javax.jms.Message msg)
		throws
			WSIFException,
			javax.jms.JMSException,
			ProcessException,
			javax.naming.NamingException,
			javax.ejb.CreateException {

		org.apache.wsif.providers.jms.WSIFPort_Jms port =
			(org.apache.wsif.providers.jms.WSIFPort_Jms) getInboundPort();
		org.apache.wsif.providers.jms.JMSFormatter formatter =
			(org.apache.wsif.providers.jms.JMSFormatter) port.getFormatter();
		WSIFRequest request = formatter.unformatRequest(msg);
		JMSReplyContextImpl replyContext = null;

		Object corrId_Object = null;
		String corrId = null;
		String template = null;
		String eventName = null;
		boolean executeUninterruptibleOp = false;
		boolean executeInterruptibleOp = false;
		boolean executeEventOperation = false;

		if (matchOperation(request,
			"InputOperation",
			"InputOperationRequest",
			"InputOperationResponse")) {
			executeInterruptibleOp = true;
			corrId_Object =
				override.OverrideProcess.getCorrelationSetFromoverrideInput(
					new override_msg.OverrideInputMessage(
						request.getIncomingMessage()));
			corrId =
				(corrId_Object != null
					? "OverrideProcess" + '.' + corrId_Object.toString()
					: null);
			template = "OverrideProcess";
			replyContext = new JMSReplyContextImpl();
			replyContext.setIsSynchronousFlow(true);
			populateReplyContextOverrideProcess(replyContext);
		} // end if

		if (executeInterruptibleOp) {
			replyContext.setJMSCorrelationID(msg.getJMSMessageID());
			replyContext.setJMSDestination(msg.getJMSReplyTo());

			initiate(
				corrId,
				request.getIncomingMessage(),
				replyContext,
				template);

		}

	}
	/**
	 * call
	 * @generated
	 */
	protected WSIFMessage call(WSIFMessage msg, String template)
		throws
			ProcessException,
			javax.naming.NamingException,
			javax.ejb.CreateException {
		ClientObjectWrapper result =
			getBusinessProcessBean().call(
				template,
				new ClientObjectWrapper(msg));
		if (result != null)
			return (WSIFMessage) result.getObject();
		else
			return null;
	}
	/**
	 * initiate
	 * @generated
	 */
	protected void initiate(
		String corrId,
		WSIFMessage msg,
		ReplyContext replyContext,
		String template)
		throws
			ProcessException,
			javax.naming.NamingException,
			javax.ejb.CreateException {
		if (corrId == null)
			getBusinessProcessBean().callWithReplyContext(
				template,
				new ClientObjectWrapper(msg),
				new ReplyContextWrapper(replyContext));
		else
			getBusinessProcessBean().callWithReplyContext(
				template,
				corrId,
				new ClientObjectWrapper(msg),
				new ReplyContextWrapper(replyContext));
	}
	/**
	 * sendEvent
	 * @generated
	 */
	protected void sendEvent(String eventName, String corrId, WSIFMessage msg)
		throws
			ProcessException,
			javax.naming.NamingException,
			javax.ejb.CreateException {
		getBusinessProcessBean().sendEvent(
			corrId,
			eventName,
			new ClientObjectWrapper(msg));
	}
	/**
	 * getBusinessProcessBean
	 * @generated
	 */
	protected LocalBusinessProcess getBusinessProcessBean()
		throws
			ProcessException,
			javax.naming.NamingException,
			javax.ejb.CreateException {
		if (fieldBusinessProcessBean == null) {
			javax.naming.InitialContext ctx = new javax.naming.InitialContext();
			LocalBusinessProcessHome localBusinessProcessHome =
				(LocalBusinessProcessHome) ctx.lookup(
					"java:comp/env/ejb/local/BusinessProcessHome");
			fieldBusinessProcessBean = localBusinessProcessHome.create();
		}
		return fieldBusinessProcessBean;
	}
	/**
	 * matchOperation
	 * @generated
	 */
	protected boolean matchOperation(
		WSIFRequest request,
		String matchOperationName,
		String matchInputName,
		String matchOutputName) {
		if (request == null)
			return false;
		String operationName;
		String inputName;
		String outputName;

		operationName = request.getOperationName();
		inputName = request.getInputName();
		outputName = request.getOutputName();

		if (operationName == null && matchOperationName != null)
			return false;
		if (operationName != null && !operationName.equals(matchOperationName))
			return false;
		if (inputName == null && matchInputName != null)
			return false;
		if (inputName != null && !inputName.equals(matchInputName))
			return false;
		if (outputName == null && matchOutputName != null)
			return false;
		if (outputName != null && !outputName.equals(matchOutputName))
			return false;
		return true;
	}
	/**
	 * populateReplyContextOverrideProcess
	 * @generated
	 */
	protected void populateReplyContextOverrideProcess(ReplyContextImpl replyContext)
		throws WSIFException {
		replyContext.addOperation(
			new QName("http://override/", "overrideOutput"),
			"file:override/OverrideProcessPortTypeJMSService.wsdl",
			new QName(
				"http://override/OverrideProcessPortTypeJMSService/",
				"OverrideProcessPortTypeService"),
			new QName("http://override/", "OverrideProcessPortType"),
			new QName(
				"http://override/OverrideProcessPortTypeJMSBinding/",
				"OverrideProcessPortTypeJMSBinding"),
			"InputOperation",
			"InputOperationRequest",
			"InputOperationResponse");
	}
}
