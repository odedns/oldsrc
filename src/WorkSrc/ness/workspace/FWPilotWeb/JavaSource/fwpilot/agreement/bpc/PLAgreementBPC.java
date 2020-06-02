package fwpilot.agreement.bpc;

import java.util.List;

import com.ness.fw.bl.BusinessProcessContainer;
import com.ness.fw.persistence.Page;
import fwpilot.agreement.vo.*;
import fwpilot.agreement.vo.Package;
import fwpilot.general.vo.CodeTableVO;

public class PLAgreementBPC extends BusinessProcessContainer
{
	private Agreement agreement;
	private Agreement copyAgreement;
	private Integer id;
	private Page page;
	private Catalog catalog;
	private AgreementPackage ap;
	private Package pack;
	private String mode;
	private List values;
	CodeTableVO codeTable;

	public PLAgreementBPC()
	{
		super();
	}

	public Agreement getAgreement()
	{
		return agreement;
	}

	public void setAgreement(Agreement agreement)
	{
		this.agreement = agreement;
	}

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}
	
	public Page getPage()
	{
		return page;
	}

	public void setPage(Page page)
	{
		this.page = page;
	}


	public Catalog getCatalog()
	{
		return catalog;
	}

	public void setCatalog(Catalog catalog)
	{
		this.catalog = catalog;
	}

	public Package getPack()
	{
		return pack;
	}

	public void setPack(Package pack)
	{
		this.pack = pack;
	}

	public void setAgreementPackage(AgreementPackage ap)
	{
		this.ap = ap;
	}
	
	public AgreementPackage getAgreementPackage()
	{
		return ap;
	}
	/**
	 * @return
	 */
	public String getMode()
	{
		return mode;
	}

	/**
	 * @param string
	 */
	public void setMode(String string)
	{
		mode = string;
	}

	/**
	 * @return
	 */
	public Agreement getCopyAgreement()
	{
		return copyAgreement;
	}

	/**
	 * @param agreement
	 */
	public void setCopyAgreement(Agreement agreement)
	{
		copyAgreement = agreement;
	}

	/**
	 * @return values
	 */
	public List getValues()
	{
		return values;
	}

	/**
	 * @param list
	 */
	public void setValues(List list)
	{
		values = list;
	}

	/**
	 * @return
	 */
	public CodeTableVO getCodeTable()
	{
		return codeTable;
	}

	/**
	 * @param tableVO
	 */
	public void setCodeTable(CodeTableVO tableVO)
	{
		codeTable = tableVO;
	}

}
