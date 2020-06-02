package fwpilot.agreement.operations;

import com.ness.fw.bl.ValueObjectList;
import com.ness.fw.bl.proxy.BPOProxy;
import com.ness.fw.common.exceptions.GeneralException;
import com.ness.fw.common.logger.Logger;
import com.ness.fw.flower.core.Context;
import com.ness.fw.flower.core.ContextException;
import com.ness.fw.flower.core.Operation;
import com.ness.fw.flower.core.OperationExecutionException;
import com.ness.fw.flower.core.ParameterList;
import com.ness.fw.ui.TreeModel;
import com.ness.fw.ui.TreeNode;

import fwpilot.agreement.bpc.PLAgreementBPC;
import fwpilot.agreement.vo.Agreement;
import fwpilot.agreement.vo.AgreementPackage;
import fwpilot.agreement.vo.Package;
import fwpilot.agreement.vo.Program;

public class PLAddPackageOP extends Operation
{

	protected void execute(Context ctx, ParameterList parameterList) throws OperationExecutionException
	{

		Logger.debug("", "AddPackageOperation executed");

		try
		{
			addPackage(ctx);
		}

		catch (ContextException ce)
		{
			throw new OperationExecutionException("context->",ce);
		}

		catch (GeneralException ge)
		{
			throw new OperationExecutionException("general->",ge);
		}

	}

	private void addPackage(Context ctx) throws ContextException, GeneralException
	{

		// Get the agreementg
		Agreement agreement = (Agreement)ctx.getField("agreement");

		// get the tree node
		TreeModel model = (TreeModel)ctx.getField("agreementDetailsTreeModel");

		// check if there was an added node that not save. in this case we shoukd
		// remove it from the tree
		String lastPack = (String)ctx.getXIData("addedPackageNodeId");
		AgreementPackage ap = (AgreementPackage)ctx.getField("addedPackage");

		if (lastPack != null)
		{
			model.remove(lastPack);
			agreement.removePackage(ap);
		}

		// get the selected Package id
		int packId = ctx.getXIInteger("selectedPackage").intValue();

		Package pack;
		ValueObjectList programs;

		ap = agreement.createAgreementPackage();
		ap.setPackageID(new Integer(packId));		
//		agreement.addPackage(ap);
		
		PLAgreementBPC container = new PLAgreementBPC();
		container.setAgreementPackage(ap);
		BPOProxy.execute("loadPackageCmd",container);
		ap = container.getAgreementPackage();
		agreement.addPackage(ap);
		pack = ap.getPackage();
		programs = pack.getProgramList();

		// program node
		TreeNode node = new TreeNode(pack.getName());
		String nodeId;
		node.addExtraData("ID", pack.getId());
		node.addExtraData("type","package");

		nodeId = model.add(node);

		//programs nodes
		for (int i=0; i<programs.size(); i++)
		{
			Program program = (Program)programs.getValueObject(i);
			TreeNode progNode = new TreeNode(program.getName());
			progNode.addExtraData("ID", program.getId());
			progNode.addExtraData("type","program");
			model.add(progNode,nodeId);
		}

		model.setSelectedNode(nodeId);

		// saving the package. it will be used when saving the agreement
		ctx.setField("addedPackage",ap);
		ctx.setField("addedPackageNodeId",nodeId);
		}
}
