package fwpilot.agreement.vo;

import com.ness.fw.bl.ValueObject;

public interface AgreementPackage extends ValueObject
{
	public static int TYPE_BASIC = 1;
	public static int TYPE_SELF = 2;
	public static int TYPE_EXPANDED = 3;
	public static int TYPE_APPENDIX = 4;

	public Integer getId();
	public Integer getAgreementId();
	public Integer getPackageID();
	public void setAgreementId(Integer AgreementId);
	public void setPackageID(Integer packageID);
	public Package getPackage();
	public Double getAfterDiscount();
	public Double getInsuranceSum();
	public Double getPremia();
	public Integer getType();
	public void setAfterDiscount(Double afterDiscount);
	public void setInsuranceSum(Double insuranceSum);
	public void setPremia(Double premia);
	public void setType(Integer type);
}
