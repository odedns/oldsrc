package fwpilot.customer.dao;

import java.util.List;
import com.ness.fw.bl.DAOList;
import com.ness.fw.bl.DAOListManager;
import com.ness.fw.bl.LockableDAO;
import com.ness.fw.bl.ValueObjectList;

import fwpilot.customer.vo.Customer;
import fwpilot.customer.vo.CustomerManager;


public class CustomerDAOManager extends DAOListManager implements CustomerManager
{
	public CustomerDAOManager(DAOList list)
	{
		super(list);
	}
	
	public Customer getCustomerById(Integer id)
	{
		return (Customer)super.getById(id);
	}
	
	public Customer getCustomerByUID(Integer id)
	{
		return (Customer)super.getByUID(id);
	}

	public ValueObjectList getCustomerList()
	{
		return super.getValueObjectList();
	}
	
	public void add (Customer customer)
	{
		super.add((CustomerDAO)customer);
	}

	public boolean remove(Customer customer)
	{
		return super.remove((CustomerDAO)customer);
	}

	public void addCustomerForLocking(Customer customer)
	{
		super.addLockable((LockableDAO)customer);
	}
	
	public List getLockableList()
	{
		return super.getLockablesList();
	}

}
