package fwpilot.agreement.vo;

import java.util.Date;
import com.ness.fw.bl.ValueObject;

public interface AgreementApproval extends ValueObject
{
	public Integer getAgreementId();
	public Date getApprovalDate();
	public Integer getId();
	public Integer getStatus();
	public Integer getType();
	public void setApprovalDate(Date date);
	public void setStatus(Integer status);
	public void setType(Integer type);
}
