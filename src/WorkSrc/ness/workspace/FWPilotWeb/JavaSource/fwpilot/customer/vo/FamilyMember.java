package fwpilot.customer.vo;

import java.util.Date;
import com.ness.fw.bl.ValueObject;

public interface FamilyMember extends ValueObject
{
	public static int TYPE_CHILD = 1;
	public static int TYPE_SPOUSE = 2;
	public static int TYPE_PARENT = 3;
	public static int TYPE_BROTHER = 4;
	public static int TYPE_SISTER = 5;

	public static int SEX_MALE = 1;
	public static int SEX_FEMALE = 2;
	public static int SEX_UNKNOWN = 3;

	public Date getBirthDate();
	public String getFirstName();
	public Integer getId();
	public String getLastName();
	public void setBirthDate(Date date);
	public void setFirstName(String string);
	public void setLastName(String string);
	public Integer getCustomerId();
	public RelatedTypeStatus getType();
	public void setCustomerId(Integer i);
	public void setType(RelatedTypeStatus i);
	public SexStatus getSex();
	public void setSex(SexStatus i);
	public Integer getIdentification();
	public void setIdentification(Integer i);

}