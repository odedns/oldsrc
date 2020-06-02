package fwpilot.activities;

import com.ness.fw.common.exceptions.GeneralException;
import com.ness.fw.flower.core.Activity;
import com.ness.fw.flower.core.ActivityCompletionEvent;
import com.ness.fw.flower.core.ActivityException;
import com.ness.fw.flower.core.Context;
import com.ness.fw.flower.core.ParameterList;
import com.ness.fw.flower.core.ValidationException;
import com.ness.fw.ui.TreeModel;
import com.ness.fw.ui.TreeNode;

public class ChooseNodeActivity extends Activity
{

	private static String ROOT = "root";
	private static String PACKAGE = "package";
	private static String PROGRAM = "program";


	public void initialize(ParameterList parameterList) throws ActivityException
	{
	}

	public ActivityCompletionEvent execute(Context ctx) throws ActivityException, ValidationException
	{
		String selectedFlow = "";

		try
		{

			// Get the tree model
			TreeModel model = (TreeModel)ctx.getField("agreementDetailsTreeModel");

			// Get the selected node
			String selectedNodeID;
			if (model.getSelectedNode() == null)
			{
				selectedNodeID = model.getRoot().getId();
			}
			else
			{
				selectedNodeID = model.getSelectedNodeId();
			}

			TreeNode selectedNode = (TreeNode)model.getTreeNode(selectedNodeID);

			// According to the selected node, build the current detail
			if (selectedNode.getId().equals(model.getRoot().getId()))
			{
				selectedFlow = ROOT;
			}

			else if (selectedNode.getExtraData("type").equals("program"))
			{
				selectedFlow = PROGRAM;

			}
			else
			{
				selectedFlow = PACKAGE;

			}

			model.setSelectedNode(selectedNode.getId());

			// return the appropiate event
			return new ActivityCompletionEvent(selectedFlow);
	}

		catch (GeneralException e)
		{
			throw new ActivityException ("general", e);
		}
	}
}
