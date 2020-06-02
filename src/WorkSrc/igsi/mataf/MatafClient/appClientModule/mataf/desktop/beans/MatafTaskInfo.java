/*
 * Created on 16/03/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package mataf.desktop.beans;

import mataf.common.transactions.checks.TransactionValidator;

import com.ibm.dse.desktop.TaskInfo;
import com.ibm.dse.desktop.TaskLauncher;

/**
 * @author eyalbz
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class MatafTaskInfo extends TaskInfo {
	private String connectionToHost = null;
	private String branchStatus = null;
	private String workstationStatus = null;
	private String cashRegisterStatus = null;
	private String cashRegisterType = null;
	private String connectionToPrinter = null;
	private String transactionType = null;
	private String operationType = null;
	private String trxId = null;
	
	public MatafTaskInfo() {
		super();
	}

	/**
	 * @param arg0
	 */
	public MatafTaskInfo(TaskLauncher taskLauncher) {
		super(taskLauncher);
	}

	/**
	 * @return
	 */
	public String getConnectionToHost() {
		return connectionToHost;
	}

	/**
	 * @param boolean1
	 */
	public void setConnectionToHost(Object connection) {
		connectionToHost = (String)connection;
	}

	/**
	 * @return
	 */
	public String getBranchStatus() {
		return branchStatus;
	}

	/**
	 * @param string
	 */
	public void setBranchStatus(Object string) {
		branchStatus = (String)string;
	}
	
	/* (non-Javadoc)
	 * @see com.ibm.dse.desktop.TaskInfo#toString()
	 */
	public String toString() {
		StringBuffer oldDescriptor = new StringBuffer(super.toString());
		StringBuffer newDescriptor = new StringBuffer();
		newDescriptor.append("\n connectionToHost=\"").append(getConnectionToHost()).append("\"\n");
		newDescriptor.append(" getConnectionToPrinter=\"").append(getConnectionToPrinter()).append("\"");
		newDescriptor.append(" getBranchStatus=\"").append(getBranchStatus()).append("\"");
		newDescriptor.append(" getWorkstationStatus=\"").append(getWorkstationStatus()).append("\"");
		newDescriptor.append(" getCashRegisterStatus=\"").append(getCashRegisterStatus()).append("\"");
		newDescriptor.append(" getCashRegisterType=\"").append(getCashRegisterType()).append("\"");
		newDescriptor.append(" getOperationType=\"").append(getOperationType()).append("\"");
		newDescriptor.append(" getTransactionType=\"").append(getTransactionType()).append("\"");
		newDescriptor.append(" getTrxId=\"").append(getCode()).append("\"");
		newDescriptor.append(">\n");
		int indexOfBigger = oldDescriptor.lastIndexOf(">");
		int lengthOfNewDescriptor = newDescriptor.length();
		oldDescriptor.replace(indexOfBigger, indexOfBigger+lengthOfNewDescriptor, newDescriptor.toString());
		return oldDescriptor.toString();
	}


	/**
	 * @return
	 */
	public String getCashRegisterStatus() {
		return cashRegisterStatus;
	}

	/**
	 * @return
	 */
	public String getConnectionToPrinter() {
		return connectionToPrinter;
	}

	/**
	 * @return
	 */
	public String getTransactionType() {
		return transactionType;
	}

	/**
	 * @return
	 */
	public String getWorkstationStatus() {
		return workstationStatus;
	}

	/**
	 * @param string
	 */
	public void setCashRegisterStatus(Object string) {
		cashRegisterStatus = (String)string;
	}

	/**
	 * @param string
	 */
	public void setConnectionToPrinter(Object string) {
		connectionToPrinter = (String)string;
	}

	/**
	 * @param string
	 */
	public void setTransactionType(Object string) {
		transactionType = (String)string;
	}

	/**
	 * @param string
	 */
	public void setWorkstationStatus(Object string) {
		workstationStatus = (String)string;
	}

	/**
	 * @return
	 */
	public String getOperationType() {
		return operationType;
	}

	/**
	 * @param string
	 */
	public void setOperationType(Object string) {
		operationType = (String)string;
	}
	
	public boolean accept(TransactionValidator validator) throws Exception{
		return validator.validate(this);
	}
	/**
	 * @return
	 */
	public String getCashRegisterType() {
		return cashRegisterType;
	}

	/**
	 * @param string
	 */
	public void setCashRegisterType(Object string) {
		cashRegisterType = (String)string;
	}

	/**
	 * Returns the trxId.
	 * @return String
	 */
	public String getTrxId() {
		return trxId;
	}

	/**
	 * Sets the trxId.
	 * @param trxId The trxId to set
	 */
	public void setTrxId(String trxId) {
		this.trxId = trxId;
	}

}
