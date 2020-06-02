package mataf.general.operations;

/**
 * @author yossid
 *
 * This operation step simply add an error to be shown at the messages window.
 * Parameters:
 * 		msgTableId - messages table id to get the error message from
 * 		errorNo - error key in the messages table if there is an error
 * Returns:
 * 		- RC_OK 
 * 		- RC_ERROR 
 */
public class AddMessage2MsgListOpStep extends MatafOperationStep {

	/**
	 * Constructor for SetErrorMessageOpStep.
	 */
	public AddMessage2MsgListOpStep() {
		super();
	}

	/**
	 * @see mataf.general.operations.MatafOperationStep#executeOp()
	 */
	public int executeOp() throws Exception {
		addToErrorListFromXML();
		return RC_OK;
	}

}
