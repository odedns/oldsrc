package mataf.utils;
import com.ibm.dse.services.comms.DSECCException;
import com.ibm.dse.services.ejb.DSEEJBConnector;
import com.ibm.dse.services.mq.*;
import com.ibm.mq.*;
import com.ibm.dse.base.*;
import java.io.IOException;

import mataf.logger.*;

/**
 * @author Oded Nissan 14/01/2004
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class MQUtils {

	/**
	 * cannot instantiate class.
	 */
	private MQUtils()
	{		
	}
	
	/**
	 * pack a context into an MQ message.
	 * @param msg the MQ message object.
	 * @param ctx the context to pack.
	 */	
	public static void packContext(MQMessage msg, Context ctx)
		throws IOException
	{
		msg.writeUTF(ctx.getName());
		msg.writeObject(ctx.getKeyedCollection());		
	}
	
	
	/**
	 * unpack a context from an Mq message object.
	 * @param msg the MQMessage object.
	 * @return Context the unpacked context.
	 */
	public static Context unpackContext(MQMessage msg)
		throws DSEException
	{
		Context ctx = null;
		try {
			String ctxName = msg.readUTF();
			ctx = (Context) Context.readObject(ctxName);
			KeyedCollection kc = (KeyedCollection) msg.readObject();
			ctx.setKeyedCollection(kc);
		} catch(Exception e) {
			e.printStackTrace();
			throw new DSEException(DSEException.critical,"MQUtils",e.getMessage());				
		}
		return(ctx);		
	}

	/**
	 * pack a transaction context and override context 
	 * into an MQ message.
	 * @param msg the MQ message object.
	 * @param ctx the context to pack.
	 * @param trxCtx the transaction context.
	 */	
	public static void packTrxContext(MQMessage msg, Context ctx, Context trxCtx)
		throws DSEException 
	{		
		try {
			ctx.setValueAt("ctxName", trxCtx.getName());
			ctx.setValueAt("ctxData",trxCtx.getKeyedCollection());
			packContext(msg,ctx);
		} catch(Exception e) {
			throw new DSEException(DSEException.critical,"MQUtils",e.getMessage());	
		}
				
	}
	
	/**
	 * unpack a context from an Mq message object.
	 * @param msg the MQMessage object.
	 * @return Context the unpacked context.
	 */
	public static Context unpackTrxContext(Context ctx)
		throws DSEException 
	{
		Context trxCtx = null;
		try {
			trxCtx = (Context) Context.readObject((String) ctx.getValueAt("ctxName"));
			KeyedCollection kc = (KeyedCollection) ctx.getValueAt("ctxData");
			trxCtx.setKeyedCollection(kc);
			
		} catch(Exception e) {
			throw new DSEException(DSEException.critical,"MQUtils",e.getMessage());	
		}
		return(trxCtx);		
	}

	/**
	 * send an Mq message to the MF in case of
	 * ishur menahel.
	 * @param ctx - the context to use.
	 * @throws DSEException in case of error.
	 */ 	
	public static void sendMqMsg(Context ctx) throws DSEException
	{
		GLogger.debug("in sendMqMsg");	
		try {
			
			/*
			 * send GKSI_HDR 
			 */
			String trxHdr = (String) ctx.getValueAt("trxMqHeader");
			StringBuffer sb = new StringBuffer();
			if(null != trxHdr) {
				sb.append(trxHdr);	
			}
			/*
			 * send GKSI_HDR_CONT 
			 */
			FormatElement fmt = (FormatElement) FormatElement.readObject("GKSI_HDR_CONT_fmt");			
			String s = fmt.format(ctx);			
			GLogger.debug("formatted message = " + s);						
			sb.append(s);
			/*
			 * now send GLSI_ISHUR_MENAEL_LOG
			 */									
			Integer status = (Integer) ctx.getValueAt("status");
			ctx.setValueAt("GLSI_ISHR_MENAEL_LOG.GL_SW_ISHUR_DCHIYA",status.toString()); 
			fmt = (FormatElement) FormatElement.readObject("GLSI_ISHR_MENAEL_LOG_Fmt");
			s = fmt.format(ctx);
			GLogger.debug("formatted message = " + s);
			sb.append(s);
			
			MQConnection mqconn = (MQConnection) Service.readObject("MQ_OR");
			mqconn.establishConnection();
			MQMessage msg = new MQMessage();
			msg.writeString(sb.toString());
			mqconn.send(msg);
			mqconn.closeConnection();
			
		} catch(Exception e) {
			e.printStackTrace();
			throw new DSEException(DSEException.critical,"MQUtils",e.getMessage());	
		}
	
	}
	

	/**
	 * send a message to mq service.
	 * @param mqSrvName the name of the MqService.
	 * @param s the string to send.
	 * @throws DSEException in case of error.
	 */
	public static void sendMsg(String mqSrvName, String s) throws DSEException
	{
		try {
			MQConnection mqconn = (MQConnection) Service.readObject(mqSrvName);
			mqconn.establishConnection();
			MQMessage msg = new MQMessage();
			msg.writeString(s);
			mqconn.send(msg);
			mqconn.closeConnection();
		} catch(Exception e) {
			e.printStackTrace();
			throw new DSEException(DSEException.critical,"MQUtils",e.getMessage());	
		}
		

	}

}
