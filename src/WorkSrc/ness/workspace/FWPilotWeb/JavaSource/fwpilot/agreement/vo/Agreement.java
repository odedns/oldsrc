package fwpilot.agreement.vo;

import java.util.*;
import com.ness.fw.bl.ValueObject;
import com.ness.fw.bl.ValueObjectList;

import fwpilot.customer.vo.Customer;

public interface Agreement extends ValueObject
{
	public static int CUSTOMER_TYPE_GENERAL = 1;
	public static int CUSTOMER_TYPE_COMPANY = 2;
	public static int CUSTOMER_TYPE_AGENCY = 3;

	public static int STATUS_PRIMARY = 1;
	public static int STATUS_ACTIVE = 2;
	public static int STATUS_CANCELED = 3;

	public static int TYPE_AGREEMENT = 1;
	public static int TYPE_SALE = 2;

	public boolean isAutomaticStart();
	public boolean isCanBeCanceled();
	public Integer getCustomerId();
	public Integer getCustomerType();
	public String getDescription();
	public Date getEndDate();
	public Integer getId();
	public Integer getMinimalPeriod();
	public String getName();
	public Date getStartDate();
	public Status getStatus();
	public Type getType();
	public void setAutomaticStart(boolean b);
	public void setCanBeCanceled(boolean b);
	public void setCustomerId(Integer customerId);
	public void setCustomerType(Integer customerType);
	public void setDescription(String description);
	public void setEndDate(Date endDate);
	public void setMinimalPeriod(Integer minimalPeriod);
	public void setName(String name);
	public void setStartDate(Date startDate);
	public void setStatus(Status status);
	public void setType(Type type);
	public Customer getCustomer();
	
	// Docuemnt
	public AttachedDocument getDocument(Integer documentId);
	public AttachedDocument getDocumentByUID(Integer UId);
	public AttachedDocument getDocumentByIndex(int index);
	public void addDocument(AttachedDocument doc);
	public boolean removeDocument(AttachedDocument obj);
	public void removeAllDocuments();
	public int getDocumentsCount();
	public int getNonDeletedDocumentsCount();
	public AttachedDocument createDocument();
	public ValueObjectList getDocumentList();

	// Packages
	
	public int getPackagesCount();
	public AgreementPackage getPackage(Integer packageId);
	public void addPackage(AgreementPackage pack);
	public boolean removePackage(AgreementPackage obj);
	public int getNonDeletedPackagesCount();
	public ValueObjectList getPackagesList();
	public AgreementPackage createAgreementPackage();

	// Approval
	public int getApprovalsCount();
	public AgreementApproval getApproval(Integer approvalId);
	public void addApproval(AgreementApproval approval);
	public boolean removeApproval(AgreementApproval obj);
	public ValueObjectList getApprovalList();

	// Characteristic
	public int getCharacteristicsCount();
	public AgreementCharacteristics getCharacteristic(Integer charId);
	public void addCharacteristic(Integer charId);
	public boolean removeCharacteristic(Integer charId);
	public void setCharacteristics(List selectedIds);
	public ValueObjectList getCharacteristicList();

}
