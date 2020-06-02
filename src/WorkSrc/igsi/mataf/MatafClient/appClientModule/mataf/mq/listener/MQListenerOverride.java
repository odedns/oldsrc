package mataf.mq.listener;

import java.awt.Cursor;
import java.io.IOException;

import mataf.globalmessages.handler.IMessageClickHandler;
import mataf.globalmessages.handler.WindowMessagesHandler;
import mataf.logger.GLogger;
import mataf.override.OpenOverrideReqOp;
import mataf.override.OverrideConstants;
import mataf.utils.CursorFactory;
import mataf.utils.MQUtils;
import mataf.utils.NativeUtils;
import mataf.types.MatafOptionPane;
import com.ibm.dse.base.Context;
import com.ibm.dse.base.DSEClientOperation;
import com.ibm.dse.base.DSEEventObject;
import com.ibm.dse.base.DSEHandler;
import com.ibm.dse.base.DSEObjectNotFoundException;
import com.ibm.dse.base.Handler;
import com.ibm.dse.base.Hashtable;
import com.ibm.dse.base.Service;
import com.ibm.dse.desktop.*;
import com.ibm.dse.services.mq.MQConnection;
import com.ibm.mq.MQException;
import com.ibm.mq.MQMessage;
import mataf.dse.appl.OpenDesktop;
import javax.swing.JOptionPane;

/**
 * @author ronenk
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class MQListenerOverride extends Thread
{

	public boolean bTimeout=false;
	/**
	 * Constructor for MQListenerOverride.
	 */
	public MQListenerOverride()
	{
		super();
	}

		//Thread to wait for mq messages and transfer to messages window
	public void run()
	{
		MQConnection mqConnection;
		
		try
		{
			mqConnection = (MQConnection) Service.readObject("MQC");
		
			//Need this handler to capture timeout
			mqConnection.addHandler(new DSEHandler()
			{
				public Handler dispatchEvent(DSEEventObject event)
				{
					Hashtable ht = event.getParameters();
					MQException mqe = (MQException) ht.get("exception");
					if(mqe != null) 
					{
						bTimeout = true;
					}
					return(null);	
				}
			},"errorReceived");
			
		
			mqConnection.establishConnection();
			
			boolean bExit=false;
		
			MQMessage msg = null;
		
			String sUser="******";
			
			while(!bExit)
			{
			
				Context ctx = Context.getContextNamed("workstationCtx");
		
				try
				{
					if(ctx!=null)
					{
						sUser = (String)ctx.getValueAt("GLSE_GLBL.GKSE_KEY.GL_ZIHUI_PAKID");
						
						if(sUser==null || sUser.length()==0)
							sUser="******";
					}	
					else
						sUser="******";
				}
				catch (DSEObjectNotFoundException e)
				{
					GLogger.error(this.getClass(),null,null,e,false);
					sUser="******";
				}
				
				//sUser
				msg =(MQMessage)mqConnection.receive(sUser.getBytes(),4000);
				if(bTimeout)
				{
					bTimeout=false;
					continue;	
				}
			
				int iMsgType=msg.readInt();
				
				GLogger.info("MQListener Recieved MSG: "+iMsgType);
				try
				{
					switch(iMsgType)
					{
						case OverrideConstants.OVERRIDE_REQUEST:	
						{
							String sMessage="";
							final Context ctxOverride = (Context) MQUtils.unpackContext(msg);		
							
							sMessage =
									(String) ctxOverride
										.getValueAt(
									"tellerUserId")
									+ " אישור מנהל מ "
									+ (String) ctxOverride
										.getValueAt(
										"trxName");
							
							//String sMessage=(String)objCtx;
							try
							{
								NativeUtils.beep(500,700);
								Desktop.getDesktop().setCursor(CursorFactory.getCursor(CursorFactory.ICON_CLOCK_CURSOR));
								((MultipleStateIconLabel) ((Desktop) Desktop.getDesktop()).getComponentByName("OVERRIDE")).setState("ON");
							}
							catch(Exception e)
							{
								GLogger.error(MQListenerOverride.class,null,"Unable to load NativeUtils.dll",e,false);								
							}
							
							if(OpenDesktop.isBusinessScreenOpen()) {
								MatafOptionPane.showMessageDialog(Desktop.getDesktop(), " הגיעה בקשת אישור מנהל!", " אישור מנהל", JOptionPane.OK_OPTION);
							}
							
							WindowMessagesHandler.addMessage(sMessage,"ממתין",new IMessageClickHandler(){
								
								private Context ctx=ctxOverride;
								
								public Context getContext()
								{
									return ctx;
								}
								
								public int handleClick(int iRow)
								{
									MQMessage mqMessage=new MQMessage();
									/*
									 * if there are no more messages change the 
									 * cursor back to default.
									 */
									 if(WindowMessagesHandler.getNumMessages() <= 1) { 
										Desktop.getDesktop().setCursor(Cursor.getDefaultCursor());									
										((MultipleStateIconLabel) ((Desktop) Desktop.getDesktop()).getComponentByName("OVERRIDE")).setState("DEFAULT");

									 }
									
									
									try
									{
										MQConnection service = (MQConnection) Service.readObject("MQC");

										//service.establishConnection();										
										String sRequestingUser=(String)ctx.getValueAt("tellerUserId")+(String)ctx.getValueAt("trxUuid");
										mqMessage.correlationId=sRequestingUser.getBytes();
										mqMessage.writeInt(OverrideConstants.OVERRIDE_OPEN);
										mqMessage.writeUTF((String)ctx.getValueAt("trxUuid"));
										
										service.send(mqMessage);
										//service.closeConnection();
										
									}
									catch(Exception e)
									{
										GLogger.error(this.getClass(),null,null,e,false);
									}
									
									mqMessage=null;
									
									try
									{
										//Context cTrxCtx=ContextFormatter.unformatContext((String)ctx.getValueAt("ctxData"),(String)ctx.getValueAt("ctxName"));
									
										WindowMessagesHandler.changeStatus("בטיפול",(String)ctx.getValueAt("trxUuid"));		
									
										OpenOverrideReqOp clientOp = 
											(OpenOverrideReqOp) DSEClientOperation.readObject("openOverrideReqOp");
										TaskArea ar = com.ibm.dse.desktop.Desktop.getDesktop().getTaskArea();
					      				ar.openTask("openOverrideReqOp","mataf.override.OverrideView",new Boolean(false), "OPERATION","אישור מנהל","MA","overrideTask");
										clientOp.setContext(ctx);
										clientOp.execute();
										clientOp.close();
										
										
									}
									catch(Exception e)
									{
										GLogger.error(this.getClass(),null,null,e,false);
									}
									
									//WindowMessagesHandler.getTableModel().setValueAt("בטיפול",iRow,1);
									return 1;
								}
							},(String)ctxOverride.getValueAt("trxUuid"));
						 	break;
						}
						case OverrideConstants.OVERRIDE_OPEN:		break;	
						case OverrideConstants.OVERRIDE_REPLY:		break;
						case OverrideConstants.OVERRIDE_CANCEL:
						{
							String sUuidDelete=(String)msg.readUTF();
							/*int iRowIndex=WindowMessagesHandler.findMessage(sUuidDelete);
							if(iRowIndex!=-1)
								WindowMessagesHandler.delMessage(iRowIndex);
							*/
							WindowMessagesHandler.delMessage(sUuidDelete);
							break;
						}
						case OverrideConstants.OVERRIDE_USERCHANGE:{
																		bExit=true;
																		break;
																	}
							
					}
				}
				
				catch (Exception e)
				{
					GLogger.error(this.getClass(),null,null,e,false);
					e.printStackTrace();
				}
				
			}
			
			mqConnection.closeConnection();
		}
		catch (IOException e)
		{
			GLogger.error(this.getClass(),null,null,e,false);
			e.printStackTrace();
		}
		catch (MQException e)
		{
			GLogger.error(this.getClass(),null,null,e,false);
			e.printStackTrace();
		}
		catch (Exception e)
		{
			GLogger.error(this.getClass(),null,null,e,false);
		}
		
	}
}
