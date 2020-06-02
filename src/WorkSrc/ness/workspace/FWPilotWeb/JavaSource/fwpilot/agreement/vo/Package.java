package fwpilot.agreement.vo;

import com.ness.fw.bl.*;

public interface Package extends ValueObject
{
	public Integer getId();
	public String getName();
	public Integer getLockId(); 
	public Integer getEndAge();
	public Integer getMaximalStartAge();
	public Integer getMinimalStartAge();
	public void setEndAge(Integer i);
	public void setMaximalStartAge(Integer maximalStartAge);
	public void setMinimalStartAge(Integer MinimalStartAge);
	public void setName(String string);
	public String getDescription();
	public void setDescription(String string);
	public ValueObjectList getProgramList();
	public Program getProgram (Integer programId);

}
