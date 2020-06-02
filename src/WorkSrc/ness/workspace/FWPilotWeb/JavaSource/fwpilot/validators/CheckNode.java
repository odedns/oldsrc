package fwpilot.validators;

import com.ness.fw.flower.core.Context;
import com.ness.fw.flower.core.ContextException;
import com.ness.fw.flower.core.ParameterList;
import com.ness.fw.flower.core.ValidationException;
import com.ness.fw.flower.core.ValidationProcessException;
import com.ness.fw.flower.core.Validator;
import com.ness.fw.ui.TreeModel;
import com.ness.fw.ui.TreeNode;

public class CheckNode extends Validator
{

	String nodeType;
	public void initialize(ParameterList parameterList) throws ValidationProcessException
	{
		nodeType = (String)parameterList.getParameter(0).getValue();
	}

	public void validate(Context ctx) throws ValidationException
	{
		boolean errors = false;

		try
		{
			// Get the tree model
			TreeModel model = (TreeModel)ctx.getField("agreementDetailsTreeModel");

			// Get the selected node
			String selectedNodeID;
			selectedNodeID = model.getSelectedNodeId();
			TreeNode selectedNode = (TreeNode)model.getTreeNode(selectedNodeID);
			if (!selectedNode.getExtraData("type").equals(nodeType))
			{
				errors = true;
			}

			if (errors)
			{
				throw new ValidationException();
			}
		}

		catch (ContextException ce)
		{
			throw new ValidationException();
		}
	}
}
