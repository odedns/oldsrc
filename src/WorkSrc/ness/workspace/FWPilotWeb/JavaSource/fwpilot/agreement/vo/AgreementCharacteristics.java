package fwpilot.agreement.vo;

import com.ness.fw.bl.ValueObject;

public interface AgreementCharacteristics extends ValueObject
{
	public Integer getRelatedId();
	public Integer getAgreementId();
	public Integer getCharId();
	public String getName();
	public void setName(String name);
	public void setCharId(Integer charId);
}
