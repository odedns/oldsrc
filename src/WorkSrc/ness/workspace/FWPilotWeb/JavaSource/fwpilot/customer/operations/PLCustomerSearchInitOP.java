package fwpilot.customer.operations;

import com.ness.fw.flower.core.*;
import com.ness.fw.flower.util.ApplicationUtil;
import com.ness.fw.ui.*;
import com.ness.fw.ui.events.Event;
import com.ness.fw.common.exceptions.*;
import com.ness.fw.common.logger.*;
import com.ness.fw.common.resources.LocalizedResources;
import fwpilot.customer.vo.Customer;

public class PLCustomerSearchInitOP extends Operation
{
	protected void execute(Context ctx, ParameterList parameterList) throws OperationExecutionException, FatalException, AuthorizationException
	{
		Logger.debug("", "CustomerInitOperation executed");

		try
		{
			if (ctx.getField("searchResultTableModel") == null)
			{
				ctx.setField("searchResultTableModel", buildSearchResultTableModel(ctx));
			}
			
			if (ctx.getField("customerTypeModel") == null)
			{
				ctx.setField("customerTypeModel", buildCustomerTypeModel(ctx));
			}
			ctx.setField("custID", null);
		}
		catch (ContextException ce)
		{
			throw new OperationExecutionException("set field ->",ce);
		} 
		catch (ResourceException re)
		{
			throw new OperationExecutionException("resource ->",re);
		}
	}

	private TableModel buildSearchResultTableModel(Context ctx) throws ResourceException
	{
		TableModel model = new TableModel();
		model.setSelectionType(1);
		
		model.setAllowMenus(false);
		LocalizedResources localizable = ApplicationUtil.getLocalizedResources(ctx);

		model.setHeader(localizable.getString("searchResults"));
		model.setSummary("&nbsp;");
		
		model.setMaxPagesToDisplay(10);

		// columns
		Column c = new Column(localizable.getString("customerTable_type"),false);
		c.setWidth("20%");
		model.addColumn(c);

		Column c1 = new Column(localizable.getString("customerTable_id"),false);
		c1.setWidth("20%");
		model.addColumn(c1);

		Column c2 = new Column(localizable.getString("customerTable_name"),true);
		c2.setWidth("30%");
		Event link = new Event();
		link.setEventTargetType(Event.EVENT_TARGET_TYPE_DEFAULT);
		c2.setLinkable(true);
		c2.setCellClickEvent(link);
		model.addColumn(c2);

		Column c3 = new Column(localizable.getString("customerTable_address"),true);
		c3.setWidth("30%");
		model.addColumn(c3);

		Column c4 = new Column(localizable.getString("customerTable_telephone"),false);
		c4.setWidth("30%");
		model.addColumn(c4);
		
		return model;

	}

	private ListModel buildCustomerTypeModel(Context ctx) throws ResourceException
	{
		ListModel model = new ListModel();
		model.setSelectionType(UIConstants.LIST_TYPE_SINGLE);
		LocalizedResources localizable = ApplicationUtil.getLocalizedResources(ctx);

		model.addValue(String.valueOf(Customer.TYPE_PRIVATE), localizable.getString("customerTable_private"));
		model.addValue(String.valueOf(Customer.TYPE_BUSINESS), localizable.getString("customerTable_business"));
		model.setSelectedKey(String.valueOf(Customer.TYPE_PRIVATE));

		return model;

	}
}
