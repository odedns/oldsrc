package tests;
import mataf.utils.MQUtils;

import com.ibm.dse.base.Context;
import com.ibm.dse.base.Service;
import com.ibm.dse.base.Settings;
import com.ibm.dse.services.mq.MQConnection;
import com.ibm.mq.MQMessage;


/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class BinFmtMq {

static void init() throws Exception
	{		
		Context.reset();
		Settings.reset("http://localhost/MatafServer/dse/dse.ini");
		Settings.initializeExternalizers(com.ibm.dse.base.Settings.MEMORY);			
	}
	
	
	
	public static void main(String[] args) {
		try {
			
			init();
			Context ctx = (Context) Context.readObject("halbanathonCtx");
			ctx.setValueAt("trxORData.trxUuid", new String(new Long(System.currentTimeMillis()).toString()));
			ctx.setValueAt("trxORData.trxId","106");
			ctx.setValueAt("trxORData.trxName","סריקת צקים");		
			ctx.setValueAt("trxORData.viewName","mataf.slika.panels.SlikaConclusionPanel");
			ctx.setValueAt("trxORData.ctxName","slikaCtx");
			ctx.setValueAt("trxORData.mgrUserId","111115");		
			ctx.setValueAt("trxORData.samchutMeasheret","");
			ctx.setValueAt("trxORData.answerCode","answerCode");				
			ctx.setValueAt("trxORData.answerTxt","answerTxt");				
			
			
			Context orCtx = (Context) Context.readObject("overrideCtx");
			orCtx.setValueAt("trxId","106");
			orCtx.setValueAt("trxUuid","11111");
			orCtx.setValueAt("trxName","סריקת צקים");		
			orCtx.setValueAt("ctxName","halbanathonCtx");
			orCtx.setValueAt("ctxData",ctx.getKeyedCollection());
			orCtx.removeAt("answerList");
			// write to MQ 
				// Instantiate an MQMessage for the received message:
			MQMessage msg = new MQMessage();
			byte corrId[] = "foo".getBytes();
			msg.correlationId = corrId;
			// Get the service from the context:
			MQConnection service = (MQConnection) Service.readObject("MQC");
			
			// Establish the connection with MQManager. Open the queues:
			service.establishConnection();
			/*
			msg.writeObject(orCtx.getKeyedCollection());
			*/
			MQUtils.packContext(msg,orCtx);
			service.send(msg);
						
			System.out.println("sent message ...");
			System.out.println("encoding: "  + msg.characterSet);
			// now read from mq
			msg =(MQMessage)service.receive(corrId,4000);				
			
			System.out.println("got message ..");
			int len = msg.getDataLength();			
			System.out.println("length = " + len);
					
		
			Context ctx2 = MQUtils.unpackContext(msg);
			System.out.println("ctx2 = " + ctx2.getKeyedCollection().toString());
			
			Context trxCtx = MQUtils.unpackTrxContext(ctx2);
			System.out.println("kc = " + trxCtx.getKeyedCollection().toString());						
			service.closeConnection();
					
		} catch(Exception e) {
			e.printStackTrace();	
		}
		
		System.out.println("java.version : " + System.getProperty("java.version"));
		System.out.println("encoding : " + System.getProperty("file.encoding"));

	}

}
