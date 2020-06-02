package fwpilot.agreement.vo;

import java.util.Date;
import com.ness.fw.bl.ValueObject;

public interface AttachedDocument extends ValueObject
{
	public static int SYSTEM_DIMUT = 1;
	public static int SYSTEM_ARCHIVE = 2;
	public static int SYSTEM_FILE = 3;
	
	public Integer getAgreementId();
	public Date getAttachDate();
	public String getDescription();
	public Integer getDocId();
	public Integer getId();
	public Integer getSystem();
	public void setAgreementId(Integer agreementId);
	public void setAttachDate(Date attachDate);
	public void setDescription(String description);
	public void setDocId(Integer docId);
	public void setSystem(Integer system);

}
