package tests;
import com.ibm.mq.*;

/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class MQTst {

	static final String HOST = "10.11.103.216"; // 10.11.103.1
	static final String QUEUE = "QS1";
	static final String QUEUE_MANAGER = "Q283US00"; // Q284UE00

	public static void main(String[] args) {
		try {

			MQEnvironment.hostname = HOST;
			MQEnvironment.port = 1414;
			MQEnvironment.channel = "SERVER.CHANNEL";
			MQEnvironment.CCSID = 862;

			MQQueueManager qmng = new MQQueueManager(QUEUE_MANAGER);
			int openOptions = MQC.MQOO_OUTPUT | 
							  MQC.MQOO_INPUT_AS_Q_DEF |
							  MQC.MQOO_SET |
							  MQC.MQOO_INQUIRE; 
							  
		
			// Instantiate an MQMessage for the received message:
			MQMessage msg = new MQMessage();
//			msg.characterSet = 1255;
			MQQueue queue1 = qmng.accessQueue(QUEUE,openOptions,
						null,null,null);	
			
			msg.writeString("some message");
			MQPutMessageOptions opts = new MQPutMessageOptions();
			
			
			queue1.put(msg,opts);
			System.out.println("sent message..");
			
			queue1.get(msg);
			System.out.println("got message: " + msg.readLine());
			msg.clearMessage();
			
			String corr1 = "client1";
			String corr2 = "client2";
			String s1 = "message to client 1";
			String s2 = "message to client 2";
			msg.correlationId = corr1.getBytes();
			msg.writeString(s1);
			queue1.put(msg,opts);
			System.out.println("sent message to client 1..");
			msg.clearMessage();
			
			msg.correlationId = corr2.getBytes();
			msg.writeString(s2);
			queue1.put(msg,opts);
			System.out.println("sent message to client 2..");
			msg.clearMessage();
			
			msg.correlationId = corr2.getBytes();
			queue1.get(msg);		
			System.out.println("got message: " + msg.readLine());
			msg.clearMessage();
			msg.correlationId = corr1.getBytes();
			queue1.get(msg);		
			System.out.println("got message: " + msg.readLine());

			
			
			queue1.close();
			qmng.disconnect();
		
		}catch(Exception e) {
			e.printStackTrace();	
		}
		System.exit(1);
	}


}
