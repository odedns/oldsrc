package fwpilot.general.operations;
import com.ness.fw.flower.core.*;
import com.ness.fw.flower.util.ApplicationUtil;

public class ClearDirtyFlagOp extends Operation
{
	
	protected void execute(Context ctx, ParameterList parameterList)	throws OperationExecutionException
	{
		try
		{
			ApplicationUtil.clearDirtyFlag(ctx);
		}
		catch (ContextException e)
		{
			throw new OperationExecutionException("Could not clear dirty Flag", e);
		}
	}
}