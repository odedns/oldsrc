package rpc_management.rex;

/**
 * Insert the type's description here.
 * Creation date: (11/5/00 4:18:39 PM)
 * @author: Administrator
 */

import com.softwareag.entirex.aci.*; 
 



import java.io.IOException;
import java.util.Date;


import java.lang.Boolean;


import rpc_management.*;
import rpc_management.RequesttoEX;
import java.math.*;
import com.ibm.dse.base.*;
import rpc_management.services.*;

/**
 * <B>Example :</B> to write routine to mainframe.<BR><BR>
 * &nbsp&nbsp&nbsp&nbsp This example implements the RequesttoEX interface.<BR>
 * &nbsp&nbsp&nbsp&nbsp <B>You should:</B><BR>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
 * 1) copy the constructor as is.<BR>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
 * 2) implement two methods "getMessage","setMessage".<BR>
 *
 **/
 
public class REX_HAI045N0 implements RequesttoEX {
 
/**
 * The Constructor is full constant use.<BR>
 * &nbsp&nbsp Never change it !! every new routine like this one need to have<BR>
 * &nbsp&nbsp exactly the same constructor .<BR>
 */

public REX_HAI045N0()
{

	//**************** Broker init  ***********************
	String brokerID = Hai045n0.DEFAULT_BROKERID;
	String server = Hai045n0.DEFAULT_SERVER;
	try
	{
		migdal.services.GeneralParams params =
			(migdal.services.GeneralParams) (com.ibm.dse.base.Context.getRoot()).getService(
				"generalParamsService");
		String strBrokerId = params.getBrokerID();
		String strBrokerServer = params.getDefaultServer();

		//Broker broker = new Broker("ETB009"/*brokerID*/,server/*"RPC/SRV1/CALLNAT"*/);//2000 NT /7100 UNIX
		Broker broker = new Broker(strBrokerId /*brokerID*/, strBrokerServer /*"RPC/SRV1/CALLNAT"*/);
		//2000 NT /7100 UNIX
		this.hai045n0 = new Hai045n0(broker, server);
	} catch (DSEObjectNotFoundException ex)
	{
		ex.printStackTrace();
	}


}




/*
this method get values from tranContext, and set MF query to send.
*/
public void setMessage(Context context) throws REXException {

	System.out.println("in hai045n0 rex setMessage");
}

/*
this method put MF results in contexts
*/
public Context getMessage(Context context) throws rpc_management.REXException {

	System.out.println("int hai045n0 rex getMessage");

	return (null);
}

	public Hai045n0  hai045n0 = null;
}
