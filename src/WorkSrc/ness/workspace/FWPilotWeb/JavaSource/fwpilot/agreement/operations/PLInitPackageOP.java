package fwpilot.agreement.operations;

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

public class PLInitPackageOP extends Operation
{
	protected void execute(Context ctx, ParameterList parameterList) throws OperationExecutionException
	{
		Logger.debug("", "PLInitPackageOP executed");

		try
		{

			Package pack = null;

			// Get the selected node
			TreeModel model = (TreeModel)ctx.getField("agreementDetailsTreeModel");			
			TreeNode selectedNode = model.getSelectedNode();

			// Get the agreement
			Agreement agreement = (Agreement)ctx.getField("agreement");

			Integer packageID = (Integer)selectedNode.getExtraData("ID");
			AgreementPackage ap  = agreement.getPackage(packageID);

			// load packages & programs
			PLAgreementBPC container = new PLAgreementBPC();
			container.setAgreementPackage(ap);
			BPOProxy.execute("loadPackageCmd", container);
						
			ap = container.getAgreementPackage();
			
			pack = ap.getPackage();

			//updating the catalog program fields in the context
			ctx.setField("packId", pack.getId());
			ctx.setField("packName", pack.getName());
			ctx.setField("packMinimalStartAge", pack.getMinimalStartAge());
			ctx.setField("packMaximalStartAge", pack.getMaximalStartAge());
			ctx.setField("packEndAge", pack.getEndAge());
			ctx.setField("packDescription", pack.getDescription());

			//updating the agreement program fields in the context
			ctx.setField("packType", ap.getType());
			ctx.setField("packInsuranceSum", ap.getInsuranceSum());

			ctx.setField("packAfterDiscount",ap.getAfterDiscount() == null ? null :ap.getAfterDiscount());
			ctx.setField("packPremia", ap.getPremia());


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
}
