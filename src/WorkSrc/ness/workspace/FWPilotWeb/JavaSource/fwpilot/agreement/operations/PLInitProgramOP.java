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
import fwpilot.agreement.vo.Program;

public class PLInitProgramOP extends Operation
{
	protected void execute(Context ctx, ParameterList parameterList) throws OperationExecutionException
	{
		Logger.debug("", "PLInitProgramOP executed");

		try
		{

			Program program = null;

			// Get the tree model
			TreeModel model = (TreeModel)ctx.getField("agreementDetailsTreeModel");
			TreeNode selectedNode = model.getSelectedNode();

			// Get the agreement
			Agreement agreement = (Agreement)ctx.getField("agreement");

			Integer programID  = (Integer)selectedNode.getExtraData("ID");
			Integer packageID = (Integer)((TreeNode)selectedNode.getParent()).getExtraData("ID");

		
			AgreementPackage ap  = agreement.getPackage(packageID);			

			// load packages & programs
			PLAgreementBPC container = new PLAgreementBPC();
			container.setAgreementPackage(ap);
			BPOProxy.execute("loadPackageCmd", container);
			ap = container.getAgreementPackage();
			Package pack = ap.getPackage();
			program = pack.getProgram(programID);

			//updating the program fields in the context
			ctx.setField("progId", program.getId());
			ctx.setField("progName", program.getName());
			ctx.setField("progMinimalStartAge",program.getMinimalStartAge());
			ctx.setField("progMaximalStartAge", program.getMaximalStartAge());
			ctx.setField("progEndAge",program.getEndAge());
			ctx.setField("progDescription", program.getDescription());
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
