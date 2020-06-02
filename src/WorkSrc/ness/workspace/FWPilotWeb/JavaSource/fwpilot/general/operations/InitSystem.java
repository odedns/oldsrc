
package fwpilot.general.operations;

import java.util.ArrayList;
import java.util.Date;

import com.ness.fw.cache.CacheFactory;
import com.ness.fw.cache.exceptions.CacheException;
import com.ness.fw.common.auth.UserAuthData;
import com.ness.fw.common.exceptions.AuthorizationException;
import com.ness.fw.common.exceptions.FatalException;
import com.ness.fw.common.logger.Logger;
import com.ness.fw.flower.core.Context;
import com.ness.fw.flower.core.ContextException;
import com.ness.fw.flower.core.Operation;
import com.ness.fw.flower.core.OperationExecutionException;
import com.ness.fw.flower.core.ParameterList;
import com.ness.fw.shared.common.SystemConstants;
import com.ness.fw.shared.flower.bl.FlowerBusinessLogicUtil;
import com.ness.fw.ui.DirtyModel;
import com.ness.fw.ui.MessagesModel;
import com.ness.fw.ui.ToolBarModel;

public class InitSystem extends Operation
{
	static boolean isInit = false;
	
	protected void execute(Context ctx, ParameterList parameterList) throws OperationExecutionException, FatalException, AuthorizationException
	{
		Logger.debug("", "InitSystem executed");		

		try 
		{

			System.err.println("jdk is..." + System.getProperty("java.version"));
			System.err.println("java.class.version" + System.getProperty("java.class.version"));
			System.err.println("java.class.path" + System.getProperty("java.class.path"));
			System.err.println("classLoader" + this.getClass().getClassLoader().getClass().getName());
		
			ctx.setField("currentDate",new Date(System.currentTimeMillis()));
			
			UserAuthData authData = FlowerBusinessLogicUtil.getUserAuthData(ctx);
			ctx.setField("userId", authData.getUserID());

			// Creating message model
			MessagesModel messagesModel = new MessagesModel();
			messagesModel.setNormalHeight(20);
			messagesModel.setExpandHeight(70);
			messagesModel.setMessagesState(MessagesModel.MESSAGES_OPEN);
			ctx.setField("messagesModel",messagesModel);
			
			// Creating toolbars model
			ToolBarModel leftModel = new ToolBarModel();
			ToolBarModel rightModel = new ToolBarModel();
			ctx.setField("leftToolBar", leftModel);
			ctx.setField("rightToolBar", rightModel);
			
			// Creating toolbars model
			ToolBarModel pagingToolBar = new ToolBarModel();
			ctx.setField("pagingToolBar", pagingToolBar);


			// Creating dirty model
			DirtyModel dirty = new DirtyModel();
			ctx.setField(SystemConstants.DIRTY_FLAG_FIELD_NAME,dirty);			

			//Init cache config
			if (!isInit)
			{
				isInit = true;
				ArrayList configRoots = new ArrayList(1);
				configRoots.add("config/cache");
				CacheFactory.getInstance().initialize(configRoots);
			}
	
		}
		catch(ContextException ce)
		{
			throw new OperationExecutionException("context", ce);
		} 
		catch (CacheException e)
		{
			throw new OperationExecutionException("cache", e);
		} 
	}
}
