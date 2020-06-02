package fwpilot.agreement.operations;

import java.util.ArrayList;
import java.util.List;

import com.ness.fw.bl.ValueObjectList;
import com.ness.fw.bl.proxy.BPOProxy;
import com.ness.fw.common.exceptions.FatalException;
import com.ness.fw.common.exceptions.GeneralException;
import com.ness.fw.common.exceptions.ResourceException;
import com.ness.fw.common.logger.Logger;
import com.ness.fw.common.resources.LocalizedResources;
import com.ness.fw.flower.core.*;
import com.ness.fw.flower.util.ApplicationUtil;
import com.ness.fw.persistence.Page;
import com.ness.fw.ui.*;
import com.ness.fw.ui.events.CustomEvent;
import com.ness.fw.ui.text.IntegerTextModel;
import fwpilot.agreement.bpc.PLAgreementBPC;
import fwpilot.agreement.vo.*;

public class PLInitAgreement extends Operation
{
	protected void execute(Context ctx, ParameterList parameterList) throws OperationExecutionException
	{
		Logger.debug("", "PLInitAgreement executed");
		TreeModel treeModel;
		Agreement agreement = null;
		LocalizedResources localizable = ApplicationUtil.getLocalizedResources(ctx);
		PLAgreementBPC container;

		try
		{
			initScreen(ctx);			
			treeModel =  createEmptyModel(ctx);
			Integer agreementId = ctx.getXIInteger("agreementId");
			String mode = ctx.getXIString("mode");
			container = loadAgreement(agreementId,mode);
			agreement = container.getAgreement();

			ctx.setField("title",localizable.getString("buildAgreement"));
			ctx.setField("identification", agreement.getId());
			ctx.setField("name", agreement.getName());
			ctx.setField("description", agreement.getDescription());
			ctx.setField("startDate", agreement.getStartDate());
			ctx.setField("endDate", agreement.getEndDate());
			ctx.setField("minimalPeriod", agreement.getMinimalPeriod());

			ctx.setField("agreementDisplayStateModel", buildDisplayStateModel(agreement));

			
//			ctx.setField("customerId", agreement.getCustomerId() == null ? null : agreement.getCustomerId().toString());
			if(agreement.getCustomerId() != null)
			{
				ctx.setField("customerId", agreement.getCustomerId().toString());
				ctx.setField("customerName", agreement.getCustomer().getName());
			}

			ctx.setField("title",localizable.getString("agreementDetails"));
			fillTree(treeModel,container.getPage());

			ctx.setField("title",localizable.getString("buildAgreement"));

			// setting the agreement
			ctx.setField("agreement",agreement);
			ctx.setField("agreementDetailsTreeModel", treeModel);

			if (ctx.getField("attributesModel") == null)
			{
				ctx.setField("attributesModel",buildAttributesModel(agreement));
			}

			updateFields(ctx);
		}

		catch(ContextException ce)
		{
			throw new OperationExecutionException("InitBuildHeskem->setField->", ce);
		}

		catch(GeneralException ge)
		{
			throw new OperationExecutionException("InitBuildHeskem->fillTree->", ge);
		}
	}

	private PLAgreementBPC loadAgreement(Integer agreementId, String mode) throws GeneralException
	{
		PLAgreementBPC container = new PLAgreementBPC();
		container.setId(agreementId);
		container.setMode(mode);
		BPOProxy.execute("loadAgreementCmd", container);
		return container;
	}


	private TreeModel createEmptyModel(Context ctx) throws ResourceException
	{
		TreeNode root = new TreeNode();

		LocalizedResources localizable = ApplicationUtil.getLocalizedResources(ctx);
		root.setData(localizable.getString("generalAgreement"));
		root.addExtraData("type", "root");
		TreeModel treeModel = new TreeModel(root);
		treeModel.setId("agreementDetailsTreeModel");
		treeModel.setOpenNode(root.getId());

		return treeModel;
	}

	private void fillTree(TreeModel pModel, Page page) throws GeneralException
	{
		int packageID = -1;
		int prevPackageID = -1;
		String packageRowID = null;

//		PLAgreementBPC container = new PLAgreementBPC();
//		container.setAgreement(agreement);
//		PLAgreementBPO.getAgreementPackagesTree(container);
//		Page page = container.getPage();

		DefaultTreeNode packageNode = null;

		while (page.next())
		{
			packageID = page.getInt("PACKAGE_ID");

			// inserting package
			if (packageID != prevPackageID)
			{
				packageNode = buildPackageNode(page);
				packageRowID = pModel.add(packageNode);
				pModel.setOpenNode(packageNode.getId());
			}

			// insert the relative program

			// Check the there was a program and not a null of the left outer join
			if (page.getString("PROGRAM_NAME") != null)
			{
				DefaultTreeNode programNode = buildProgramNode(page);
				if (packageNode != null)
				{
					pModel.add(programNode,packageNode);
				}
				else
				{
					pModel.add(programNode,packageRowID);
				}

				pModel.setOpenNode(programNode.getId());

			}
			prevPackageID = packageID;
		}
	}

	/**
	 * @return
	 */
	private Object buildDisplayStateModel(Agreement agreement)
	{
		DisplayStateModel model = new DisplayStateModel();
		
		int status = Agreement.STATUS_PRIMARY;
		if(agreement.getStatus() != null)
			status = agreement.getStatus().getId().intValue();
			
		if(status == Agreement.STATUS_PRIMARY)
			model.setState(DisplayStateModel.DISPLAY_STATE_ENABLED);
		else if(status == Agreement.STATUS_ACTIVE)
			model.setState(DisplayStateModel.DISPLAY_STATE_DISABLED);
		else
			model.setState(DisplayStateModel.DISPLAY_STATE_HIDDEN);
		return model;
	}

	private TreeNode buildPackageNode(Page page)
	{
		TreeNode node = new TreeNode();
		node.setData(page.getString("PACKAGE_NAME"));
		node.addExtraData("ID", new Integer(page.getInt("PACKAGE_ID")));
		node.addExtraData("type","package");

		return node;
	}


	private TreeNode buildProgramNode(Page page)
	{
		TreeNode node = new TreeNode();
		node.setData(page.getString("PROGRAM_NAME"));
		node.addExtraData("ID", new Integer(page.getInt("PROGRAM_ID")));
		node.addExtraData("type","program");

		return node;
	}

	private void initScreen(Context ctx) throws ContextException, GeneralException
	{			
		ctx.setField("customerTypeModel", buildFirstRadioModel(ctx));
		ctx.setField("agreementTypeModel", buildSugRadioModel(ctx));
		ctx.setField("automaticCheckBoxModel", buildaAutomaticCheckBoxModel(ctx));
		ctx.setField("canBeCanceledCheckBoxModel", buildCanBeCanceledCheckBoxModelModel(ctx));
		ctx.setField("statusModel", buildStatusModel(ctx));
		ctx.setField("minimalPeriodTextModel", buildMinimalPeriodModel());
		ctx.setField("customerLinkModel", buildCustomerLinkModel());
		ctx.setField("deleteModel",buildDeleteModel());
	}


	private Object buildCustomerLinkModel()
	{
		ButtonModel buttonModel = new ButtonModel();
		
		CustomEvent event = new CustomEvent("customerDetails");
		event.setEventType(CustomEvent.EVENT_TYPE_READONLY);
		buttonModel.setButtonClickEvent(event);
		
		return buttonModel;
	}

	private ListModel buildAttributesModel(Agreement agreement) throws GeneralException
	{
		ListModel model = new ListModel();
		model.setSelectionType(UIConstants.LIST_TYPE_MULTIPLE);

//		PLAgreementBPC agreementBPC = new PLAgreementBPC();
//		BPOProxy.execute("loadCharacteristicsCmd", agreementBPC);
//		Object[] characteristics = agreementBPC.getValues().toArray();

		
		Object[] characteristics = CharacteristicVOFactory.getAll().toArray();
		for (int i=0; i<characteristics.length; i++)
		{
			//CodeTableValue pair = (CodeTableValue)characteristics[i];
			Characteristic characteristic = (Characteristic)characteristics[i];
			model.addValue(String.valueOf(characteristic.getId()), characteristic.getName());
//			model.addValue(String.valueOf(pair.getCode()), pair.getName());
		}

		// load the agreement characteristic
		if (agreement != null)
		{

//			PLAgreementBPC container = new PLAgreementBPC();
//			container.setAgreement(agreement);
//			PLAgreementBPO.loadCharacteristics(container);

			ValueObjectList selected = agreement.getCharacteristicList();
			for (int i=0; i<selected.size(); i++)
			{
				AgreementCharacteristics  pair = (AgreementCharacteristics)selected.getValueObject(i);
				model.addValue(String.valueOf(pair.getCharId()), pair.getName(), true);
			}
		}
		return model;
	}	
	
	private TextModel buildMinimalPeriodModel()
	{
		TextModel model = new IntegerTextModel();
		return model;
	}

	private ListModel buildStatusModel(Context ctx) throws ResourceException, FatalException
	{
		ListModel model = new ListModel();
		model.setSelectionType(UIConstants.LIST_TYPE_SINGLE);
//		LocalizedResources localizable = ApplicationUtil.getLocalizedResources(ctx);

		List list = StatusVOFactory.getAll();
		for (int i=0; i<list.size();i++)
		{
			Status status = (Status)list.get(i);
			model.addValue(String.valueOf(status.getId()),status.getName());
		}

//		model.addValue(String.valueOf(Agreement.STATUS_ACTIVE), localizable.getString("active"));
//		model.addValue(String.valueOf(Agreement.STATUS_CANCELED), localizable.getString("canceled"));
//		model.addValue(String.valueOf(Agreement.STATUS_PRIMARY), localizable.getString("primary"));



		model.setSelectedKey(String.valueOf(Agreement.STATUS_ACTIVE));

		return model;
	}

	private ListModel buildFirstRadioModel(Context ctx) throws ResourceException
	{
		ListModel model = new ListModel();
		model.setSelectionType(UIConstants.LIST_TYPE_SINGLE);
		LocalizedResources localizable = ApplicationUtil.getLocalizedResources(ctx);

		model.addValue(String.valueOf(Agreement.CUSTOMER_TYPE_GENERAL), localizable.getString("claleyHeskemDetails_general"));
		model.addValue(String.valueOf(Agreement.CUSTOMER_TYPE_COMPANY), localizable.getString("claleyHeskemDetails_body"));

		return model;
	}

	private ListModel buildCanBeCanceledCheckBoxModelModel(Context ctx) throws ResourceException
	{
		ListModel model = new ListModel();
		model.setSelectionType(UIConstants.LIST_TYPE_MULTIPLE);

		LocalizedResources localizable = ApplicationUtil.getLocalizedResources(ctx);

		model.addValue(String.valueOf(0), localizable.getString("claleyHeskemDetails_canCanceled"));
		return model;
	}

	private ListModel buildSugRadioModel(Context ctx) throws ResourceException, FatalException
	{
		ListModel model = new ListModel();
//		LocalizedResources localizable = ApplicationUtil.getLocalizedResources(ctx);

		model.setSelectionType(UIConstants.LIST_TYPE_SINGLE);

		List list = TypeVOFactory.getAll();
		for (int i=0; i<list.size();i++)
		{
			Type type = (Type)list.get(i);
			model.addValue(String.valueOf(type.getId()),type.getName());
		}


//		model.addValue(String.valueOf(Agreement.TYPE_AGREEMENT), localizable.getString("agreement"));
//		model.addValue(String.valueOf(Agreement.TYPE_SALE), localizable.getString("sale"));

		return model;
	}


	private ListModel buildaAutomaticCheckBoxModel(Context ctx) throws ResourceException
	{
		ListModel model = new ListModel();
		model.setSelectionType(UIConstants.LIST_TYPE_MULTIPLE);
		LocalizedResources localizable = ApplicationUtil.getLocalizedResources(ctx);
		model.addValue("0", localizable.getString("claleyHeskemDetails_automatic"));
		return model;
	}

	
	private void updateFields(Context ctx) throws ContextException, GeneralException
	{
		setNumDocs(ctx);
		// updating the model fields
		Agreement agreement = (Agreement)ctx.getField("agreement");

		ListModel automaticCheckBoxModel = (ListModel) ctx.getField("automaticCheckBoxModel");
		ListModel canBeCanceledCheckBoxModel = (ListModel) ctx.getField("canBeCanceledCheckBoxModel");
		ListModel customerRadioModel = (ListModel) ctx.getField("customerTypeModel");
		ListModel sugRadioModel = (ListModel) ctx.getField("agreementTypeModel");
		ListModel statusModel = (ListModel) ctx.getField("statusModel");

		ArrayList automaticKeys = new ArrayList();
		automaticKeys.add("0");

		ArrayList canceledKeys = new ArrayList();
		canceledKeys.add("0");

		if (agreement.isAutomaticStart())
		{
			automaticCheckBoxModel.setSelectedKeys(automaticKeys);
		}

		if (agreement.isCanBeCanceled())
		{
			canBeCanceledCheckBoxModel.setSelectedKeys(canceledKeys);
		}
				
		customerRadioModel.setSelectedKey(String.valueOf(agreement.getCustomerType()));
		sugRadioModel.setSelectedKey(String.valueOf(agreement.getType().getId()));

		statusModel.setSelectedKey(String.valueOf(agreement.getStatus().getId()));

		String customerName = null;
		
		agreement = (Agreement)ctx.getField("agreement");
		
		if (ctx.getXIString("customerId") != null)
		{
			if (ctx.getXIString("customerName") != null)
			{
				customerName = ctx.getXIString("customerName");
			}
			else
			if (agreement.getCustomer() != null)
			{
				customerName = agreement.getCustomer().getName();
			}	
			
			ctx.setField("customerName",customerName);		
		}
	}

	private void setNumDocs(Context ctx) throws ContextException, GeneralException
	{
		Agreement agreement = (Agreement)ctx.getField("agreement");
		PLAgreementBPC container = new PLAgreementBPC();
		container.setAgreement(agreement);
		BPOProxy.execute("loadDocumentCmd", container);
		agreement = container.getAgreement();
		ctx.setField("agreement",agreement);
		ctx.setField("numDocs",new Integer(agreement.getDocumentsCount()));
	}
	
	private ButtonModel buildDeleteModel()
	{
		ButtonModel buttonModel = new ButtonModel();
		CustomEvent customEvent = new CustomEvent("delete");
		customEvent.setConfirmMessageId("PL0001");
		buttonModel.setButtonClickEvent(customEvent);
		return buttonModel;
	}

}
