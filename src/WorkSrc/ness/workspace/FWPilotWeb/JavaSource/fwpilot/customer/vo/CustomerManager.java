package fwpilot.customer.vo;

import com.ness.fw.bl.ValueObjectList;



public interface CustomerManager
{
	public Customer getCustomerById(Integer id);
	public Customer getCustomerByUID(Integer id);
	public ValueObjectList getCustomerList();
	public void add (Customer customer);
	public boolean remove(Customer customer);
	public void addCustomerForLocking(Customer customer);

}
