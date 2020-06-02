package fwpilot.customer.vo;

import com.ness.fw.bl.ValueObject;

public interface CustomerProfession extends ValueObject
{
	public Integer getRelatedId();
	public Integer getCustomerId();
	public String getName();
	public Integer getProfessionId();
	public void setName(String name);
	public void setProfessionId(Integer professionId);

}
