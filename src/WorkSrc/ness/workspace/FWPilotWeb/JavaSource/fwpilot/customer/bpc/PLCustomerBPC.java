package fwpilot.customer.bpc;

import java.util.List;

import com.ness.fw.bl.BusinessProcessContainer;

import fwpilot.customer.vo.Customer;
import fwpilot.customer.vo.CustomerManager;

public class PLCustomerBPC extends BusinessProcessContainer
{
	private Customer customer;
	private Integer customerId;
	private Integer customerType;
	boolean customerExists = true;
	List values;
	CustomerManager customerManager;	

	public PLCustomerBPC()
	{
		super();
	}

	public Customer getCustomer()
	{
		return customer;
	}

	public void setCustomer(Customer customer)
	{
		this.customer = customer;
	}

	public Integer getCustomerId()
	{
		return customerId;
	}

	public void setCustomerId(Integer customerId)
	{
		this.customerId = customerId;
	}

	public Integer getCustomerType()
	{
		return customerType;
	}

	public void setCustomerType(Integer integer)
	{
		customerType = integer;
	}

	public boolean isCustomerExists()
	{
		return customerExists;
	}

	public void setCustomerExists(boolean customerExists)
	{
		this.customerExists = customerExists;
	}

	/**
	 * @return value
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
	public CustomerManager getCustomerManager()
	{
		return customerManager;
	}

	/**
	 * @param manager
	 */
	public void setCustomerManager(CustomerManager manager)
	{
		customerManager = manager;
	}

}
