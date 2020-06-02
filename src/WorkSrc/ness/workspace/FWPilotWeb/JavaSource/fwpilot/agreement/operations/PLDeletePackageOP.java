package fwpilot.agreement.operations;

import com.ness.fw.bl.proxy.BPOProxy;
import com.ness.fw.common.exceptions.GeneralException;
import com.ness.fw.common.logger.Logger;
import com.ness.fw.flower.core.*;
import com.ness.fw.shared.flower.bl.FlowerBusinessLogicUtil;
import com.ness.fw.ui.*;
import fwpilot.agreement.bpc.PLAgreementBPC;
import fwpilot.agreement.vo.Agreement;

public class PLDeletePackageOP extends Operation
{
	protected void execute(Context ctx, ParameterList parameterList) throws OperationExecutionException
	{
		Logger.debug("", "DeletePackageOP executed");

		try
		{
			// Get the agreement
			Agreement agreement = (Agreement)ctx.getField("agreement");

			// Get the tree model
			TreeModel model = (TreeModel) ctx.getField("agreementDetailsTreeModel");

			// Get the selected node
			String selectedNodeID;
			selectedNodeID = model.getSelectedNodeId();
			TreeNode selectedNode = (TreeNode) model.getTreeNode(selectedNodeID);

			// before removing save the parent node and select it (windows behviour)
			String parentNodeId = selectedNode.getParent().getId();
			model.remove(selectedNodeID);
			model.setSelectedNode(parentNodeId);

			// remove from the agreement
			agreement.removePackage(agreement.getPackage(((Integer)selectedNode.getExtraData("ID"))));

			PLAgreementBPC container = new PLAgreementBPC();
			container.setAgreement(agreement);
			FlowerBusinessLogicUtil.setBPOContainerUserData(ctx, container);
			BPOProxy.execute("saveAgreementCmd",container);
			ctx.setField("agreement",container.getAgreement());
		}
		catch (ContextException ce)
		{
			throw new OperationExecutionException("context", ce);
		}
	
		catch (GeneralException ge)
		{
			throw new OperationExecutionException("general", ge);
		}
	}
}
