package fwpilot.general.operations;
import com.ness.fw.flower.core.*;
import com.ness.fw.flower.util.ApplicationUtil;
public class SetDirtyFlagOp extends Operation
{
	
	protected void execute(Context ctx, ParameterList parameterList)	throws OperationExecutionException
	{
		try
		{
			ApplicationUtil.setDirtyFlag(ctx);
		}
		catch (ContextException e)
		{
			throw new OperationExecutionException("Could not set dirty Flag", e);
		}
	}
}
