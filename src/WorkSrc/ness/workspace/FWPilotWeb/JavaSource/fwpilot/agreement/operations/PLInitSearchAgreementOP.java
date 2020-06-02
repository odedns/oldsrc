package fwpilot.agreement.operations;

import java.util.List;
import java.util.Map;
import com.ness.fw.common.exceptions.*;
import com.ness.fw.common.logger.Logger;
import com.ness.fw.common.resources.LocalizedResources;
import com.ness.fw.flower.core.*;
import com.ness.fw.flower.util.ApplicationUtil;
import com.ness.fw.persistence.SqlService;
import com.ness.fw.ui.*;
import com.ness.fw.ui.events.Event;
import fwpilot.utils.Utils;
import fwpilot.agreement.vo.Agreement;
import fwpilot.agreement.vo.Status;
import fwpilot.agreement.vo.StatusVOFactory;
import fwpilot.agreement.vo.Type;
import fwpilot.agreement.vo.TypeVOFactory;

public class PLInitSearchAgreementOP extends Operation
{
	protected void execute(Context ctx, ParameterList parameterList) throws OperationExecutionException, FatalException, AuthorizationException
	{
		Logger.debug("", "PLInitSearchAgreementOP executed");
		try
		{
			ctx.setField("searchResultTableModel", buildSearchResultTableModel(ctx));
			ctx.setField("statusModel",buildStatusModel());
			ctx.setField("sugModel",buildTypeModel());
			ctx.setField("likeModel",buildLikeModel(ctx));
		}
		catch (ContextException ce)
		{
			throw new OperationExecutionException("set field ->",ce);
		}

		catch (ResourceException re)
		{
			throw new OperationExecutionException("resources->",re);
		}
	}

	private TableModel buildSearchResultTableModel(Context ctx) throws ResourceException
	{
		LocalizedResources localizable = ApplicationUtil.getLocalizedResources(ctx);
		TableModel model = new TableModel();
		model.setSelectionType(AbstractTableModel.SELECTION_SINGLE);
		model.setAllowMenus(true);
		model.setHeader(localizable.getString("searchResults"));


		// columns
		Column c1 = new Column(localizable.getString("agreementTable_name"),true);	
		Event link = new Event();
		c1.setCellClickEvent(link);
		c1.setLinkable(true);
		c1.setWidth("20%");
		model.addColumn(c1);

		Column c2 = new Column(localizable.getString("agreementTable_id"),true);
		c2.setWidth("10%");
		model.addColumn(c2);

		Column c3 = new Column(localizable.getString("agreementTable_status"),true);
		c3.setWidth("20%");
		model.addColumn(c3);

		Column c4 = new Column(localizable.getString("agreementTable_type"),true);
		c4.setWidth("20%");
		c4.setRemovable(false);
		model.addColumn(c4);

		Column c5 = new Column(localizable.getString("agreementTable_description"),true);
		c5.setWidth("30%");
		c5.setRemovable(false);
		model.addColumn(c5);

		return model;
	}

	private ListModel buildStatusModel() throws ResourceException, FatalException
	{
		ListModel model = new ListModel();
		model.setSelectionType(UIConstants.LIST_TYPE_SINGLE);

		List list = StatusVOFactory.getAll();
		for (int i=0; i<list.size();i++)
		{
			Status status = (Status)list.get(i);
			model.addValue(String.valueOf(status.getId()),status.getName());
		}

		return model;
	}

	private ListModel buildTypeModel() throws ResourceException, FatalException
	{

		ListModel model = new ListModel();
		model.setSelectionType(UIConstants.LIST_TYPE_SINGLE);

		List list = TypeVOFactory.getAll();
		for (int i=0; i<list.size();i++)
		{
			Type type = (Type)list.get(i);
			model.addValue(String.valueOf(type.getId()),type.getName());
		}

		model.setSelectedKey(String.valueOf(Agreement.TYPE_AGREEMENT));

		return model;
	}

	public ListModel buildLikeModel(Context ctx) throws ResourceException, ContextException
	{
		Map like = Utils.getLikeOptions(ApplicationUtil.getLocalizedResources(ctx));
		ListModel list = new ListModel();
		list.setSelectionType(UIConstants.LIST_TYPE_SINGLE);

		list.addValue(String.valueOf(SqlService.LIKE_CONTAINS),(String)like.get(new Integer(SqlService.LIKE_CONTAINS)));
		list.addValue(String.valueOf(SqlService.LIKE_STARTS_WITH),(String)like.get(new Integer(SqlService.LIKE_STARTS_WITH)));
		list.addValue(String.valueOf(SqlService.LIKE_ENDS_WITH),(String)like.get(new Integer(SqlService.LIKE_ENDS_WITH)));

		list.setSelectedKey(String.valueOf(SqlService.LIKE_STARTS_WITH));

		return list;
	}
}
