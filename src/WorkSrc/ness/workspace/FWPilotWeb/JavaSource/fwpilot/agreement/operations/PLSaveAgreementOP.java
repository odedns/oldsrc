package fwpilot.agreement.operations;

import com.ness.fw.bl.proxy.BPOProxy;
import com.ness.fw.common.exceptions.BusinessLogicException;
import com.ness.fw.common.exceptions.GeneralException;
import com.ness.fw.common.logger.Logger;
import com.ness.fw.flower.core.Context;
import com.ness.fw.flower.core.ContextException;
import com.ness.fw.flower.core.Operation;
import com.ness.fw.flower.core.OperationExecutionException;
import com.ness.fw.flower.core.ParameterList;
import com.ness.fw.flower.core.ValidationException;
import com.ness.fw.flower.util.ApplicationUtil;
import com.ness.fw.shared.flower.bl.FlowerBusinessLogicUtil;
import com.ness.fw.ui.ListModel;
import com.ness.fw.ui.TableModel;
import com.ness.fw.ui.TreeModel;
import com.ness.fw.ui.TreeNode;
import com.ness.fw.util.StringFormatterUtil;

import fwpilot.agreement.bpc.PLAgreementBPC;
import fwpilot.agreement.vo.Agreement;
import fwpilot.agreement.vo.AgreementPackage;
import fwpilot.agreement.vo.AttachedDocument;
import fwpilot.agreement.vo.StatusVOFactory;
import fwpilot.agreement.vo.TypeVOFactory;

public class PLSaveAgreementOP extends Operation
{
	protected void execute(Context ctx, ParameterList parameterList) throws OperationExecutionException, ValidationException
	{
		Logger.debug("", "SaveAgreementOP executed");

		try
		{
			saveAgreement(ctx);
		}
		catch (ContextException ce)
		{
			throw new OperationExecutionException("save",ce);
		}

		catch (ValidationException ve)
		{
			throw ve;
		}

		catch (GeneralException ge)
		{
			throw new OperationExecutionException("general",ge);
		}

	}

	private void saveAgreement(Context ctx) throws ContextException, ValidationException ,GeneralException
	{
		// load the agreement
		Agreement agreement = (Agreement)ctx.getField("agreement");

		agreement.setName(ctx.getXIString("name"));
		agreement.setDescription(ctx.getXIString("description"));

		// updating customer type
		ListModel customerRadioModel = (ListModel) ctx.getField("customerTypeModel");
		agreement.setCustomerType(new Integer(customerRadioModel.getSelectedKey()));

		// updating agreement type
		ListModel sugRadioModel = (ListModel) ctx.getField("agreementTypeModel");
//		agreement.setType(new Integer((sugRadioModel.getSelectedKey())));
		agreement.setType(TypeVOFactory.getById(Integer.parseInt(sugRadioModel.getSelectedKey())));

		// updating automatic
		ListModel automaticCheckBoxModel = (ListModel) ctx.getField("automaticCheckBoxModel");
		agreement.setAutomaticStart(automaticCheckBoxModel.isValueSelected("0"));

		// updating can be cancel
		ListModel canBeCanceledCheckBoxModel = (ListModel) ctx.getField("canBeCanceledCheckBoxModel");
		agreement.setCanBeCanceled(canBeCanceledCheckBoxModel.isValueSelected("0"));

		// updating status
		ListModel statusModel = (ListModel) ctx.getField("statusModel");
//		agreement.setStatus(new Integer((statusModel.getSelectedKey())));
		agreement.setStatus(StatusVOFactory.getById(Integer.parseInt(statusModel.getSelectedKey())));

		agreement.setStartDate(ctx.getXIDate("startDate"));
		agreement.setEndDate(ctx.getXIDate("endDate"));

		agreement.setCustomerId(StringFormatterUtil.isEmpty(ctx.getXIString("customerId")) ? null : new Integer((ctx.getXIString("customerId"))));
		agreement.setMinimalPeriod(ctx.getXIInteger("minimalPeriod") == null ? null : ctx.getXIInteger("minimalPeriod"));

		// set the characters
		ListModel attributesModel = (ListModel) ctx.getField("attributesModel");

		agreement.setCharacteristics(attributesModel.getSelectedKeys());

		// check if package was added
		savePackage(ctx, agreement);

		try
		{
			PLAgreementBPC container = new PLAgreementBPC();
			container.setAgreement(agreement);
			FlowerBusinessLogicUtil.setBPOContainerUserData(ctx, container);
			BPOProxy.execute("saveAgreementCmd",container);
			ctx.setField("agreement",container.getAgreement());
		}

		catch (BusinessLogicException ble)
		{
			ApplicationUtil.mergeGlobalMessageContainer(ctx,ble.getMessagesContainer());
			if (ApplicationUtil.getGlobalMessageContainer(ctx).containsErrors())
			{
				throw new ValidationException();
			}
		}

		// load the agreement to the context
		
		// 19/01/04 - no need because ejb ???
		//ctx.setField("agreement", agreement);

		// TODO : ask yifat uf neccesary ?

		//before clearing the status wh should loog through all the rows in the
		// table and replace the UID with the new ID
		TableModel model = (TableModel)ctx.getField("documentsTableModel");
		if (model != null)
		{
			int size = model.getRowsCount();
			for (int i=0; i<size; i++)
			{
				com.ness.fw.ui.Row row = model.getRow(i);
				String uniqueId = (String)row.getExtraData("UID");
				if (uniqueId != null)
				{
					AttachedDocument document = agreement.getDocumentByUID(new Integer(uniqueId));
					row.addExtraData("UID",null);
					row.addExtraData("ID",String.valueOf((document.getId())));
				}
			}
		}

		// TODO : should be here ???
		// setting tne new id to the context
		ctx.setField("identification", agreement.getId());
	}


	private void savePackage(Context ctx, Agreement agreement) throws ContextException, GeneralException
	{

		// if package is selected we should load the package and update ut with
		// the values
	
		// Get the tree model
		TreeModel model = (TreeModel)ctx.getField("agreementDetailsTreeModel");

		// Get the selected node
		String selectedNodeID = model.getSelectedNodeId();
		TreeNode selectedNode = (TreeNode)model.getTreeNode(selectedNodeID);

		// get the context fields into the package
		AgreementPackage ap = (AgreementPackage)ctx.getField("addedPackage");
		if (ap != null)
		{
			setAgreementPackage(ap,ctx);
		}
		else
		{
			if (selectedNode.getExtraData("type").equals("package"))
			{
				ap = agreement.getPackage(((Integer)selectedNode.getExtraData("ID")));
				setAgreementPackage(ap,ctx);
			}

		}

		// setting to null - indicate that the package was saved
		// when clicking "new" the node will be added to the tree, but if
		// no "save" was operated the node should bre remove and new node
		// will add instead.
		// setting this field to null indicate that there is no need
		// to remove the node.
		ctx.setField("addedPackage",null);
		ctx.setField("addedPackageNodeId",null);
	}

	private void setAgreementPackage(AgreementPackage agreementPack, Context ctx) throws ContextException
	{
		Double discount = ctx.getXIDouble("packAfterDiscount");
		if (discount != null)
		{
			agreementPack.setAfterDiscount(ctx.getXIDouble("packAfterDiscount"));
		}
		else
		{
			agreementPack.setAfterDiscount(null);
		}

		agreementPack.setType(ctx.getXIInteger("packType"));
		agreementPack.setPremia((ctx.getXIDouble("packPremia")));
		agreementPack.setInsuranceSum((ctx.getXIDouble("packInsuranceSum")));

	}
}
