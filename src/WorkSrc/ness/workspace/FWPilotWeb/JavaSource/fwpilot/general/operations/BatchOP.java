package fwpilot.general.operations;

import com.ness.fw.flower.core.*;
import com.ness.fw.bl.proxy.BPOCommandException;
import com.ness.fw.bl.proxy.BPOCommandNotFoundException;
import com.ness.fw.bl.proxy.BPOProxy;
import com.ness.fw.common.exceptions.*;
import com.ness.fw.common.logger.*;

import fwpilot.general.bpc.PLBatchBPC;

/**
 * @author bhizkya
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class BatchOP extends Operation
{
	protected void execute(Context ctx, ParameterList parameterList) throws OperationExecutionException, FatalException, AuthorizationException
	{
		Logger.debug("", "BatchOP executed");
		try
		{
			BPOProxy.execute("batchCmd", new PLBatchBPC());
		}
		catch (BPOCommandNotFoundException e)
		{
			throw new FatalException("bpo", e);
		}
	
		catch (BPOCommandException e)
		{
			throw new FatalException("bpo", e);
		}

		catch (BusinessLogicException e)
		{
			throw new FatalException("bl", e);
		}
	}	
}
