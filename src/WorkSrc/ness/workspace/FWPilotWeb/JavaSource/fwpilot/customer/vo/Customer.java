package fwpilot.customer.vo;

import java.util.*;
import com.ness.fw.bl.*;

public interface Customer extends ValueObject
{
	public static int TYPE_PRIVATE = 1;
	public static int TYPE_BUSINESS = 2;

	public static int STATUS_SINGLE = 1;
	public static int STATUS_MARRIED = 2;
	public static int STATUS_DIVORCED = 3;
	public static int STATUS_WIDOWER = 4;

	public static int SEX_MALE = 1;
	public static int SEX_FEMALE = 2;
	public static int SEX_UNKNOWN = 3;

	public Date getBirthDate();
	public Integer getFax();
	public String getName();
	public String getFirstName();
	public Integer getId();
	public String getLastName();
	public Integer getTelephone();
	public void setBirthDate(Date birthDate);
	public void setFax(Integer fax);
	public void setFirstName(String firstName);
	public void setLastName(String lastName);
	public void setTelephone(Integer telephone);
	public String getEnglishFirstName();
	public String getEnglishLastName();
	public FamilyMemberStatus getFamilyStatus();
	public Integer getMobilePhone();
	public Integer getNumberOfChilds();
	public SexStatus getSex();
	public boolean isSmoking();
	public Integer getType();
	public void setEnglishFirstName(String englishFirstName);
	public void setEnglishLastName(String englishLastName);
	public void setFamilyStatus(FamilyMemberStatus familyStatus);
	public void setMobilePhone(Integer mobilePhone);
	public void setNumberOfChilds(Integer numberOfChilds);
	public void setSex(SexStatus sex);
	public void setSmoking(boolean smoking);
	public void setType(Integer type);
	public Integer getIdentification();
	public void setIdentification(Integer identification);
	

	// Address
	public int getAddressCount();
	public Address getAddress(Integer addressId);
	public Address getAddressByUID(Integer uID);
	public void addAddress(Address familyMember);
	public boolean removeAddress(Address obj);
	public ValueObjectList getAddressList();
	public Address createAddress();

	// FamilyMember
	public int getFamilyMembersCount();
	public FamilyMember getFamilyMember(Integer familyMemeberId);
	public FamilyMember getFamilyMemberByUID(Integer uID);
	public void addFamilyMember(FamilyMember familyMember);
	public boolean removeFamilyMember(FamilyMember obj);
	public ValueObjectList getFamilyMemberList();

	// Profession
	public int getProfessionsCount();
	public void addProfession(Integer customerProfession);
	public boolean removeProfession(Integer customerProfession);
	public ValueObjectList getProfessionList();
	public FamilyMember createFamilyMemeber();
	public void setProfessions(List professions);
	public ArrayList getSelectedProfessions();
}
