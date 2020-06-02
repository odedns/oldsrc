package mataf.globalmessages.handler;

import javax.swing.SwingUtilities;

import mataf.logger.GLogger;

import com.ibm.dse.base.Context;
import com.ibm.dse.base.DSEObjectNotFoundException;
import com.ibm.dse.base.IndexedCollection;
import com.ibm.dse.base.KeyedCollection;

import mataf.desktop.beans.MatafWorkingArea;
import mataf.dse.appl.OpenDesktop;

/**
 * @author ronenk
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class WindowMessagesHandler
{

	//private static MatafTable mtMessages=null;
	//private static ArrayList mtMessages=new ArrayList();
	/**
	 * Constructor for WindowMessagesHandler.
	 */
	public WindowMessagesHandler()
	{
		super();
	}


	/*public static final MatafTable getMessagesTable()
	{
		return mtMessages;
	}*/
	
	
	/*public static Context getContextFromUuid(String sUuid)
	{
		if(mtMessages!=null)
		{
			int iRows=mtMessages.getModel().getRowCount();
			for(int i=0;i<iRows;i++)
			{
				String sTempUuid=(String)mtMessages.getModel().getValueAt(i,3);
				if(sUuid.equals(sTempUuid)==true)
					return (Context)mtMessages.getModel().getValueAt(i,2);
			}
		}
		
		return null;
	}*/
	

	/*public static int findMessage(String sUuid)	
	{
		if(mtMessages!=null)
		{
			int iRows=mtMessages.getModel().getRowCount();
			for(int i=0;i<iRows;i++)
			{
				IMessageClickHandler imchHandle=(IMessageClickHandler)mtMessages.getModel().getValueAt(i,2);
				if(imchHandle!=null)
				{
					Context ctxTemp=imchHandle.getContext();
					if(ctxTemp!=null)
					{
						String sTempUuid="";
						try
						{
							sTempUuid = (String) ctxTemp.getValueAt("trxUuid");
						}
						catch (DSEObjectNotFoundException e)
						{
							GLogger.error("DSEObjectNotFoundException: Error in WindowMessageHandler");
							GLogger.error(e);
						}
						if(sUuid.equals(sTempUuid)==true)
							return i;
					}
				}
			}
		}
		return -1;	
	}*/
		
	public static int addMessage(String sText,String sStatus)
	{
		
		//IndexedCollection ic = (IndexedCollection)IndexedCollection.readObject("globalMessagesWindow");
		IndexedCollection ic;
		try
		{
			ic =
				(IndexedCollection) Context.getContextNamed("pakidClientCtx")
					.getElementAt("globalMessagesWindow");
		
		//ic.removeAll();
	
		KeyedCollection kColl;
		kColl = (KeyedCollection) KeyedCollection.readObject("messageData");
		kColl.setValueAt("messageText",sText);
		kColl.setValueAt("messageStatus",sStatus);
		kColl.setValueAt("messageHandler","");
		//kColl.setValueAt("messageUUID","");
		ic.addElement(kColl);
		
		MatafWorkingArea.getActiveClientView().refreshDataExchangers();
		}
		catch (Exception e)
		{
			GLogger.error(WindowMessagesHandler.class, null,"Error in AddMessage",e,false);
		}
		
		/*if(mtMessages!=null)
		{
			Vector vMessage=new Vector();
			vMessage.add(sText);vMessage.add(sStatus);vMessage.add(null);//vMessage.add("");
			mtMessages.addRow(vMessage);
			mtMessages.updateUI();
		}
		else
			return 1;*/
		return 0;	
	}

	public static int addMessage(String sText,String sStatus,IMessageClickHandler imchHandler,String sUuid)
	{
		
		IndexedCollection ic;
		try
		{
			ic =
				(IndexedCollection) Context.getContextNamed("pakidClientCtx")
					.getElementAt("globalMessagesWindow");
		
		//ic.removeAll();
	
		KeyedCollection kColl;
		kColl = (KeyedCollection) KeyedCollection.readObject("messageData");
		kColl.setValueAt("messageText",sText);
		kColl.setValueAt("messageStatus",sStatus);
		kColl.setValueAt("messageHandler",imchHandler);
		//kColl.setValueAt("messageUUID",sUuid);

		ic.addElement(kColl);
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				MatafWorkingArea.getActiveClientView().refreshDataExchangers();
			}
		});
		
		}
		catch (Exception e)
		{
			GLogger.error(WindowMessagesHandler.class, null,"Error in AddMessage",e,false);
			e.printStackTrace();
		}
		
		return 0;	
	}

	
	
	private static int findMessage(String sUuid)
	{
		IndexedCollection ic;
		try
		{
			ic =
				(IndexedCollection) Context.getContextNamed("pakidClientCtx")
					.getElementAt("globalMessagesWindow");
					
			for(int i=0;i<ic.size();i++)
			{
				KeyedCollection kColl =(KeyedCollection) ic.getElementAt(i);
				//if((String)kColl.getValueAt("messageUUID")==sUuid)
				IMessageClickHandler imchHandle=(IMessageClickHandler)kColl.getValueAt("messageHandler");
				if(imchHandle!=null)
				{
					Context ctxTemp=imchHandle.getContext();
					if(ctxTemp!=null)
					{
						String sTempUuid="";
						try
						{
							sTempUuid = (String) ctxTemp.getValueAt("trxUuid");
							if(sUuid.equals(sTempUuid)==true)
								return i;
						}
						catch (DSEObjectNotFoundException e)
						{
							GLogger.error(WindowMessagesHandler.class, null,"Error in WindowMessageHandler",e,false);

						}
					}
				}
			}
		}
		catch(Exception e)
		{
			GLogger.error(WindowMessagesHandler.class, null,"Error in findMessage",e,false);
		}
		return -1;	
	}
	
	
	public static int changeStatus(String sStatus,String sUuid)
	{
		int iMessageIndex=findMessage(sUuid);
		GLogger.debug("changeStatus("+sUuid+") Index: "+iMessageIndex);
		if(iMessageIndex!=-1)	
		{
			IndexedCollection ic;
			try
			{
				ic =
				(IndexedCollection) Context.getContextNamed("pakidClientCtx")
					.getElementAt("globalMessagesWindow");
				
				 KeyedCollection kColl =(KeyedCollection) ic.getElementAt(iMessageIndex);
				 kColl.setValueAt("messageStatus",sStatus);
				 
				 MatafWorkingArea.getActiveClientView().refreshDataExchangers();
			}
			catch(Exception e)
			{
				GLogger.error(WindowMessagesHandler.class, null,"Error in changeStatus",e,false);
			}			
		}
		else
			return -1;
		return 0;	
	}


	/**
	 * get the number of messages in the window.
	 */
	public static int getNumMessages() 
	{
		int num = -1;
		try {
		IndexedCollection ic =
				(IndexedCollection) Context.getContextNamed("pakidClientCtx")
					.getElementAt("globalMessagesWindow");
			num = ic.size();
		} catch(Exception e) {
			GLogger.error(WindowMessagesHandler.class,null,"Error in getNumMessage",e,false);
			num = -1;	
		}
		return(num);
		
	}	
	
	public static int delMessage(String sUuid)
	{
		int iMessageIndex=findMessage(sUuid);
		if(iMessageIndex!=-1)
		{
			IndexedCollection ic;
			try
			{
				ic =
				(IndexedCollection) Context.getContextNamed("pakidClientCtx")
					.getElementAt("globalMessagesWindow");
				
				ic.removeElementAt(iMessageIndex);
				
				SwingUtilities.invokeLater(new Runnable()
				{
					public void run()
					{
						MatafWorkingArea.getActiveClientView().refreshDataExchangers();
					}
				});
			}
			catch(Exception e)
			{
				GLogger.error(WindowMessagesHandler.class, null,"Error in delMessage",e,false);

			}			
		}
		else
			return -1;
		return 0;	
	}
	
}
