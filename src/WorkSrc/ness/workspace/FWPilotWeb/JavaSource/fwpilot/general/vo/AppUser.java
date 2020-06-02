package fwpilot.general.vo;

import java.util.Date;
import com.ness.fw.bl.ValueObject;

public interface AppUser extends ValueObject
{
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
	public Integer getSex();
	public void setSex(Integer i);
	public Integer getIdentification();
	public void setIdentification(Integer i);
}
