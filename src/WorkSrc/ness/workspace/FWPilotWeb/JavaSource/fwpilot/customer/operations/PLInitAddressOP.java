package fwpilot.customer.operations;

import com.ness.fw.flower.core.*;
import com.ness.fw.flower.util.ApplicationUtil;
import com.ness.fw.ui.*;
import com.ness.fw.bl.ValueObjectList;
import com.ness.fw.bl.proxy.BPOProxy;
import com.ness.fw.common.exceptions.*;
import com.ness.fw.common.logger.*;
import com.ness.fw.common.resources.LocalizedResources;
import fwpilot.customer.bpc.PLCustomerBPC;
import fwpilot.customer.vo.*;
import fwpilot.customers.utils.AdressTools;

public class PLInitAddressOP extends Operation
{

	protected void execute(Context ctx, ParameterList parameterList) throws OperationExecutionException
	{
		Logger.debug("", "PLInitAddressOP executed");
		try
		{
			TableModel model = null;

			if (ctx.getField("addressMainModel") == null)
			{
				ctx.setField("addressMainModel",buildaMainCheckBoxModel(ctx));
			}

			if (ctx.getField("addressTableModel") == null)
			{
				model = buildDocumentTableModel(ctx);
				fillTable(model,ctx);
				ctx.setField("addressTableModel", model);
			}

		}

		catch (ContextException ce)
		{
			throw new OperationExecutionException("InitDocuments->set field ->",ce);
		}

		catch (GeneralException ge)
		{
			throw new OperationExecutionException("InitDocuments->",ge);
		}
	}


	private TableModel buildDocumentTableModel(Context ctx) throws ResourceException
	{
		TableModel model = new TableModel();
		model.setSelectionType(1);
		model.setAllowMenus(false);

		// columns
		LocalizedResources localizable = ApplicationUtil.getLocalizedResources(ctx);

		Column c1 = new Column(localizable.getString("cutomerAddressTable_city"),true);
		model.addColumn(c1);

		Column c2 = new Column(localizable.getString("cutomerAddressTable_street"),false);
		model.addColumn(c2);

		Column c3 = new Column(localizable.getString("cutomerAddressTable_streetNumber"),false);
		model.addColumn(c3);

		Column c4 = new Column(localizable.getString("cutomerAddressTable_telephone"),false);
		model.addColumn(c4);

		Column c5 = new Column(localizable.getString("cutomerAddressTable_mainAddress"),false);
		model.addColumn(c5);

		return model;

	}

	private void fillTable(TableModel model, Context ctx) throws PersistenceException, GeneralException,  ContextException
	{
		com.ness.fw.ui.Row row;
		
		Customer customer = (Customer)ctx.getField("customer");

		PLCustomerBPC customerBPC = new PLCustomerBPC();
		customerBPC.setCustomer(customer);
		BPOProxy.execute("loadAddressCmd", customerBPC);
		customer = customerBPC.getCustomer();
		ctx.setField("customer",customer);

		ValueObjectList addresses;
		addresses = customer.getAddressList();

		model.removeAllRows();

		for (int i=0; i < addresses.size(); i++)
		{
			Address address = (Address)addresses.getValueObject(i);
			row = model.createRow();

			if(address.getId()!= null) 
			{
				row.addExtraData("ID", String.valueOf(address.getId()));
			} 
			else 
			{
				row.addExtraData("UID", String.valueOf(address.getUID()));
			}

			// add the row to the table Model
			AdressTools.setRowWithAddress(model, row, address, ctx);
		}

		if (addresses.size() > 0)
		{
			// selecting the row
			model.setSelectedRow(0);
		}
	}

	private ListModel buildaMainCheckBoxModel(Context ctx) throws ResourceException
	{
		LocalizedResources localizable = ApplicationUtil.getLocalizedResources(ctx);
		ListModel model = new ListModel();
		model.setSelectionType(UIConstants.LIST_TYPE_MULTIPLE);
		model.addValue("0", localizable.getString("cutomerAddressTable_mainAddress"));
		return model;
	}
}
