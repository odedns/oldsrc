package fwpilot.agreement.vo;

import com.ness.fw.bl.ValueObject;

public interface Program extends ValueObject
{
	public Integer getId();
	public Integer getPackageID();
	public void setPackageID(Integer packageID);
	public String getDescription();
	public Integer getEndAge();
	public Integer getMaximalStartAge();
	public Integer getMinimalStartAge();
	public String getName();
	public void setDescription(String description);
	public void setEndAge(Integer endAge);
	public void setMaximalStartAge(Integer maximalStartAge);
	public void setMinimalStartAge(Integer minimalStartAge);
	public void setName(String name);

}
