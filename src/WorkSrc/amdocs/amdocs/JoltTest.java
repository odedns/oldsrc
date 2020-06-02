
import java.sql.*;
import java.io.*;
import bea.jolt.*;



public class JoltTest {

    private static final String SERVER_ADDRESS = "//199.221.37.96:33030";
    private static final String USER_NAME = "sbcs32c1";
    private static final String PASSWORD = "unix11";
    private static final int OPER_ID = 11111;
    private static final String SRV_NAME = "arGtCstBan00";
    
    private static final int BAN_ID = 100002662;
    public static final String  TRANSACTION_MODE = "TRANSACTION_MODE";
    public static final String  OPERATOR_ID = "OPERATOR_ID";
    public static final String  RUN_DATE = "RUN_DATE";


    public static void main(String argv[]){
    System.out.println("Test ConnectJolt started ...");

        try {

           JoltSession joltSession;
           // try to establish a connection.
           JoltSessionAttributes sattr;
           sattr = new JoltSessionAttributes();
           sattr.setString(sattr.APPADDRESS,SERVER_ADDRESS);
           // keep the connection open.
           sattr.setInt(sattr.IDLETIMEOUT,0);
           joltSession = new JoltSession(sattr,USER_NAME,null,PASSWORD,null);
           System.out.println("ConnectJolt succedded.");

           JoltRemoteService service = new JoltRemoteService(SRV_NAME,joltSession);

           service.addByte(TRANSACTION_MODE,(byte)'O');
           service.addInt(OPERATOR_ID,OPER_ID);
           service.addInt("BAN",BAN_ID);
           service.addString(RUN_DATE,"20000105");
           service.addString("MARKET_CODE","DLS");           
           service.call(null);

           int rowcount = service.getIntDef("ROWCOUNT",0);
           System.out.println("rowcount :" + rowcount );

           if (rowcount != 0) {
               System.out.println("SUBSCRIBER_NO :" + service.getStringDef("SUBSCRIBER_NO",""));
               System.out.println("LAST_BUSINESS_NAME:" + 
			       service.getStringDef("LAST_BUSINESS_NAME",""));
               System.out.println("CUSTOMER_ID:" + 
			       service.getIntDef("CUSTOMER_ID",0));
               System.out.println("CONTACT_TELNO:" + 
			       service.getStringDef("CONTACT_TELNO",""));
               System.out.println("FIRST_NAME:" + 
			       service.getStringDef("FIRST_NAME",""));
               System.out.println("NAME_ID:" + 
			       service.getIntDef("NAME_ID",0));
           }

        } catch (Exception e){
            e.printStackTrace();
        }

        System.out.println("Test ConnectJolt ended.");
    }
}
