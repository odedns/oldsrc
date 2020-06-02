package fwpilot.customer.vo;

import fwpilot.customer.dao.CustomerDAO;


public class CustomerVOFactory
{
	public static Customer createCustomer()
	{
		return new CustomerDAO();
	}


}
