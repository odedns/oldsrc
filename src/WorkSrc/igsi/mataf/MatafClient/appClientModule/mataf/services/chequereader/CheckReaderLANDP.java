package mataf.services.chequereader;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import mataf.logger.GLogger;

import com.ibm.dse.base.Context;
import com.ibm.dse.base.DSEInvalidArgumentException;
import com.ibm.dse.base.DSEInvalidRequestException;
import com.ibm.dse.base.FormatElement;
import com.ibm.dse.base.Service;
import com.ibm.dse.desktop.Desktop;
import com.ibm.landp.BadServerRCException;
import com.ibm.landp.Cprb;
import com.ibm.landp.RmtReq;

/**
 * @author ronenk
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class CheckReaderLANDP extends Service 
{
	private Cprb cprb;
	private RmtReq req;
	
	private Thread tReader=null;

	private Thread tMain=null;

	private long lReturnCode=0;

	private JDialog jfMain=null;

	private String strCheckData=new String();

	//Landp return codes
   private static final int P1 = 0x01005031;
   private static final int P2 = 0x01005032;
   private static final int P3 = 0x01005033;
   private static final int P4 = 0x01005034;
   private static final int P6 = 0x01005036;
   private static final int P7 = 0x01005037;
   private static final int P8 = 0x01005038;
   private static final int PR = 0x01005052;
   private static final int PS = 0x01005053;
   private static final int PZ = 0x0100505A;

   private static final int I1 = 0x01004931;
   private static final int I2 = 0x01004932;
   private static final int I3 = 0x01004933;
   private static final int I4 = 0x01004934;
   private static final int I5 = 0x01004935;
   private static final int I6 = 0x01004936;
   private static final int I7 = 0x01004937;
   private static final int I8 = 0x01004938;
   private static final int I9 = 0x01004939;
   private static final int IR = 0x01004952;

   private static final int U1 = 0x01005531;
   private static final int U2 = 0x01005532;
   private static final int U3 = 0x01005533;
   private static final int U4 = 0x01005534;
   private static final int U5 = 0x01005535;
   private static final int U6 = 0x01005536;
   private static final int U7 = 0x01005537;
   private static final int U8 = 0x01005538;
   private static final int U9 = 0x01005539;
	
   private static final int ERR_TIMEOUT = 0x53540001;
   private static final int ERR_DOC_WRONGLY_FED = 0x435100E1;
   private static final int ERR_DOC_STUCK_INSIDE = 0x435100E2;
   private static final int ERR_BAD_CHARACTER = 0x435100E6;
   private static final int ERR_FRAME_ERROR = 0x435100E4;
   private static final int ERR_LOADING_OPTIONS = 0x435100E3;
   
	private static synchronized void getClassLock()
	{
		/** Thread need the class's lock to run this method.*/
		//System.out.println("Class Lock Obtained by "+Thread.currentThread());
		GLogger.debug("Class Lock Obtained by "+Thread.currentThread());
	}
	
	private void initCprb()
	{
		byte byteParam[]=new byte[27];
		cprb.setServerName("CHQSRVRW");
		cprb.setQDataLength(0);
		cprb.setQParmLength(26);
		cprb.setRDataLength(0);
		cprb.setRParmLength(26);
		cprb.setRepliedDataLength(0);
		cprb.setRepliedParmLength(0);
		
		cprb.setQParm(byteParam);
        cprb.setQData(new byte[100]);
        cprb.setRParm(byteParam);
        cprb.setRData(new byte[100]);
        
        cprb.setTimeout(10);
	}

	private void connectSPV() throws CRException
	{
// 		Connecting to SPV
 		cprb = new Cprb("SPV", "IN");
 		cprb.setQParmLength(26);
        cprb.setQDataLength(0);
        cprb.setRParmLength(26);
        cprb.setRDataLength(0);
    
        cprb.setQParm(new byte[1024]);
        cprb.setQData(new byte[1024]);
        cprb.setRParm(new byte[1024]);
        cprb.setRData(new byte[1024]);
    	
    	cprb.setTimeout(10);
        
        req.setCprb(cprb);
		try 
		{
			cprb = req.send();		
		} catch (Exception e) 
		{
			//e.printStackTrace();
			throw new CRException(CRException.CRE_SPV_CONNECTION_FAILED,e.getMessage());
		}
 		
 	}

	private void openAndArmChqReader() throws CRException
	{
//		Opening Phase 
		initCprb();
		cprb.setFunctionID("OP");
		
		req.setCprb(cprb);
		try 
		{
			cprb = req.send();		
		} 
		catch (Exception e) 
		{
			throw new CRException(CRException.CRE_LANDP_OPEN_FAILED,e.getMessage());
		}
		
//		ARMing phase
		byte byteTemp[]=new byte[1];
		byteTemp[0]=1;
		initCprb();
		cprb.setFunctionID("AR");
		cprb.setQDataLength(1);
        cprb.setQData(byteTemp);
		
		req.setCprb(cprb);
		try 
		{
			cprb = req.send();		
			GLogger.debug("ARM Returned: RouterRc = "+cprb.getRouterRC()+ " SeverRc = "+cprb.getServerRC());
		} catch (Exception e) 
		{
			throw new CRException(CRException.CRE_LANDP_ARM_FAILED,e.getMessage());
		}
		GLogger.debug("Recieved after Arm :"+new String(byteTemp));
	}


	private void closeChqReader() throws CRException
	{
		GLogger.debug("Closing");
		initCprb();
//		Cancel the chq device
		cprb.setFunctionID("KL");
		
		req.setCprb(cprb);
		try 
		{
			cprb = req.send();		
		} catch (Exception e) 
		{
			throw new CRException(CRException.CRE_ERROR_KILLING_SRVR ,e.getMessage());
		}
		
		initCprb();

		cprb.setFunctionID("CL");

		req.setCprb(cprb);
		try 
		{
			cprb = req.send();		
		} catch (Exception e) 
		{
			throw new CRException(CRException.CRE_ERROR_CLOSING_SRVR ,e.getMessage());
		}		
		GLogger.debug("Closing cheque reader");
		
		initCprb();		
		cprb.setFunctionID("EJ");
		cprb.setServerName("SPV");
		req.setCprb(cprb);
		try 
		{
			cprb = req.send();		
		} catch (Exception e) 
		{
			throw new CRException(CRException.CRE_ERROR_EJECTING_SRVR ,e.getMessage());
		}		
		GLogger.debug("Ejecting SPV");
		cprb=null;
		req=null;
	}
	
	/**
	 * Constructor for CheckReaderLANDP.
	 */
	public CheckReaderLANDP() {
		super();
	}

	/**
	 * Constructor for CheckReaderLANDP.
	 * @param arg0
	 * @throws IOException
	 */

	public CheckReaderLANDP(String arg0) throws IOException {
		super(arg0);
	}

	public void init() throws CRException
	{
		GLogger.debug("Trying to obtain class lock by :"+Thread.currentThread());
		getClassLock();
		try
		{
			req=new RmtReq(RmtReq.MODE_JVM_PID);
		}
		catch(Exception e)
		{
			throw new CRException(CRException.CRE_RMTREQ_FAILED,e.getMessage());
		}
		connectSPV();
		openAndArmChqReader();
	}

	public void close() throws CRException
	{
		closeChqReader();
		jfMain.dispose();		
	}

	private boolean CheckString()
	{
		return true;
	}

	public void readAndUnformat(Context context, FormatElement fe)
	{
			//.String strCheckData=new String();
			int iRet=readCheck();
			if(iRet==0)
			{
					try {
						unformatMessageReceived(context,fe,strCheckData);
					} catch (DSEInvalidRequestException e) {
							GLogger.error(CheckReaderLANDP.class ,null,"Error in CheckReaderLANDP DSEInvalidRequest",e,false);
							//System.out.println("Error in CheckReaderLANDP :"+e.getMessage());
					} catch (DSEInvalidArgumentException e) {
							GLogger.error(CheckReaderLANDP.class ,null,"Error in CheckReaderLANDP DSEInvalidArgument",e,false);
							//System.out.println("Error in CheckReaderLANDP :"+e.getMessage());
					}
			}
	}

	public String read() throws CRException
	{
		int iRet=readCheck();
		
		//System.out.println("Return code from readCheck: "+iRet);
		GLogger.debug("Return code from readCheck: "+iRet);
		
		if(iRet!=0)
		{
			switch(iRet)
			{
				case 1: throw new CRException(CRException.CRE_READING_ABROTED);
				case 2: throw new CRException(CRException.CRE_ERROR_IN_READING);
				case P1:throw new CRException(CRException.CRE_CHQ_FUNC_NOT_SUPP);
				case P2:throw new CRException(CRException.CRE_CHQ_INCORRECT_DATA_LEN);
				case P3:throw new CRException(CRException.CRE_CHQ_PREV_OPEN_MISS);
				case P4:throw new CRException(CRException.CRE_UNKNOWN_ERROR);
				case P6:throw new CRException(CRException.CRE_UNKNOWN_ERROR);
				case P7:return strCheckData;
				case P8:throw new CRException(CRException.CRE_UNKNOWN_ERROR);
				case PR:throw new CRException(CRException.CRE_CHQ_SRVR_READ_LEN_ERR);
				case PS:throw new CRException(CRException.CRE_CHQ_SRVR_STATUS_NOTOK);
				case PZ:throw new CRException(CRException.CRE_CHQ_INVALID_PARM_LEN);
	
				case I1:throw new CRException(CRException.CRE_CHQ_DEV_NOT_ONLINE);
				case I2:throw new CRException(CRException.CRE_CHQ_DEV_CABLE_BROKEN);
				case I3:throw new CRException(CRException.CRE_CHQ_SYS_MODL_NOT_SUPP);
				case I4:throw new CRException(CRException.CRE_UNKNOWN_ERROR);
				case I5:throw new CRException(CRException.CRE_CHQ_PREV_FUNC_NOT_COMPLT); //Ignored in read
				case I6:throw new CRException(CRException.CRE_UNKNOWN_ERROR);
				case I7:throw new CRException(CRException.CRE_UNKNOWN_ERROR);
				case I8:throw new CRException(CRException.CRE_CHQ_DEV_NO_HANDLRS);
				case I9:throw new CRException(CRException.CRE_UNKNOWN_ERROR);
				case IR:throw new CRException(CRException.CRE_UNKNOWN_ERROR);
				
				case U1:throw new CRException(CRException.CRE_CHQ_GENERAL_FAIL);
				case U2:throw new CRException(CRException.CRE_CHQ_NO_RESPONSE);
				case U3:throw new CRException(CRException.CRE_CHQ_TRANS_ERR_ADPTR);
				case U4:throw new CRException(CRException.CRE_CHQ_DEV_SELF_TEST_ERR);
				case U5:throw new CRException(CRException.CRE_UNKNOWN_ERROR);
				case U6:throw new CRException(CRException.CRE_CHQ_DEV_DRVR_NAME_ERR);
				case U7:throw new CRException(CRException.CRE_UNKNOWN_ERROR);
				case U8:throw new CRException(CRException.CRE_UNKNOWN_ERROR);
				case U9:throw new CRException(CRException.CRE_UNKNOWN_ERROR);
		
				case ERR_TIMEOUT:throw new CRException(CRException.CRE_TIMEOUT);
				case ERR_DOC_WRONGLY_FED:throw new CRException(CRException.CRE_ERR_DOC_WRONGLY_FED);
				case ERR_DOC_STUCK_INSIDE:throw new CRException(CRException.CRE_ERR_DOC_STUCK_INSIDE);
				case ERR_BAD_CHARACTER:throw new CRException(CRException.CRE_ERR_BAD_CHARACTER);
				case ERR_FRAME_ERROR:throw new CRException(CRException.CRE_ERR_FRAME_ERROR);
				case ERR_LOADING_OPTIONS:throw new CRException(CRException.CRE_ERR_LOADING_OPTIONS);
				
				default:throw new CRException(CRException.CRE_UNKNOWN_ERROR);
			}
		}
		else
		{
			
			return strCheckData;
		}
		//return strCheckData;
	}

	private void blockingRead()
	{
	   	tReader=new Thread()
	   	{
	   		private CheckReaderLANDP chqMain=CheckReaderLANDP.this;
	   		
	   		public void run()
	   		{
		   		boolean bExit=false;
				try
				{
					while(!bExit)
					{
						try
						{
								cprb.setTimeout(1);
								cprb = req.send();
								GLogger.debug("Reading TID: "+Thread.currentThread().toString());
							
						} 
						catch (BadServerRCException e) 
						{
							if(e.getReturnCode()!=0x01004935) //I5 Handling 
							{
								if(e.getReturnCode()!=0x01005037) //P7 Handling 
									e.printStackTrace();
								bExit=true;
								lReturnCode=e.getReturnCode();
							}
						}
					}
				}
				catch (Exception e) 
				{
					GLogger.debug("blockingRead->ExceptionTID: "+Thread.currentThread().toString());
					//e.printStackTrace();
					lReturnCode=2;
				}
				finally
				{
					System.out.println("Finally : Stopping Window thread");
					synchronized(chqMain)
					{
						System.out.println("Finally : Synchronized Notify");
						chqMain.notify();
					}
				}
	   		}
	   	};
	   	
	   	tReader.start();
	}

	private void chequeProgress()
	{
		new Thread(){public void run(){
		jfMain=new JDialog(Desktop.getFrame(),true);
		jfMain.setUndecorated(true);
		jfMain.setBounds(362,334,200,100);	   			
		jfMain.setLocationRelativeTo(null);
//		jfMain.setResizable(false);
		
//		jfMain.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		JPanel cMain= (JPanel)jfMain.getContentPane();
		cMain.setBorder(BorderFactory.createLineBorder(Color.black,2));
		cMain.setLayout(new FlowLayout());
		JLabel label = new JLabel("Waiting for CheckReader...");
		JProgressBar jpbProgress=new JProgressBar();
		jpbProgress.setIndeterminate(true);
		jpbProgress.setBounds(10,10,250,20);
		cMain.add(label);
		cMain.add(jpbProgress);
		JButton jbCancel=new JButton("Cancel");
		jbCancel.setBounds(30,50,50,30);
						
		cMain.add(jbCancel);
		jfMain.getRootPane().setDefaultButton(jbCancel);
//		jpMain.show();
		jbCancel.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				//System.out.println(e);
				
				((JButton)(e.getSource())).setEnabled(false);
				
				lReturnCode=1;
				if((tReader!=null) && (tReader.isAlive()))
				{
					GLogger.debug("Stopping reading thread.");
					tReader.stop();
				}

				synchronized(CheckReaderLANDP.this)
				{
					GLogger.debug("Notifying main thread.");
					CheckReaderLANDP.this.notify();	
				}
			}
		});

		jfMain.show();	
		jfMain.requestFocus();
		jbCancel.requestFocus();}}.start();
	}

	private int readCheck()
	{
		byte byteChequeData[]=new byte[1024];
		initCprb();
		cprb.setFunctionID("RD");
		cprb.setRDataLength(1024);
        cprb.setRData(byteChequeData);
		
			
		req.setCprb(cprb);
		
		//tMain=Thread.currentThread();
		
		chequeProgress();
		
		blockingRead();

		//tMain.suspend();
		synchronized(this)
		{
			try
			{
				GLogger.debug("Waiting On : "+this+"...");
				wait();
			}
			catch (InterruptedException e)
			{
				GLogger.debug("Notified.");
			}	
		}
		
		
		GLogger.debug("Return Code: "+lReturnCode);
		GLogger.debug("Read Returned: RouterRc = "+cprb.getRouterRC()+ " SeverRc = "+cprb.getServerRC());		
	
		strCheckData=new String(byteChequeData);
		
		GLogger.debug("Read :"+strCheckData);
		
		
		
		return (int) lReturnCode;
	}

	private void unformatMessageReceived(Context context, FormatElement formatelement,String strCheckData)
					        throws DSEInvalidRequestException, DSEInvalidArgumentException {
    
//		messageRead = "112222222<44444444>666666<";
		GLogger.debug("unformatMessageReceived() strCheckData: " + strCheckData +" ("+strCheckData.length()+")");
        formatelement.unformat(strCheckData, context);
    }

}
