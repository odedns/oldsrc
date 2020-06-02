package fwpilot.customer.vo;

import com.ness.fw.bl.ValueObject;

public interface Address extends ValueObject
{
	public static int TYPE_CUSTOMER = 1;
	public static int TYPE_CHILD = 2;

	public String getCity();
	public Integer getId();
	public Integer getParentId();
	public String getStreet();
	public Integer getStreetNumber();
	public Integer getType();
	public void setCity(String city);
	public void setId(Integer id);
	public void setParentId(Integer parentId);
	public void setStreet(String street);
	public void setStreetNumber(Integer streetNumber);
	public void setType(Integer i);
	public boolean isMain();
	public void setMain(boolean b);
	public Integer getFax();
	public Integer getTelephone();
	public void setFax(Integer fax);
	public void setTelephone(Integer telephone);
}
