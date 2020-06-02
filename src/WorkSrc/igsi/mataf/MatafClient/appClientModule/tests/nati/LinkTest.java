package tests.nati;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import mataf.types.MatafButton;
import mataf.types.MatafLabel;
import mataf.types.MatafLink;

import com.ibm.dse.base.Context;
import com.ibm.dse.base.DSEClientOperation;
import com.ibm.dse.base.DSEInvalidArgumentException;
import com.ibm.dse.base.DSEObjectNotFoundException;
import com.ibm.dse.base.DSEOperation;
import com.ibm.dse.base.Settings;
import com.ibm.dse.clientserver.CSClientService;
import com.ibm.dse.gui.DSEPanel;

public class LinkTest extends DSEPanel
{
	MatafButton mButton;
	MatafButton mButton2;
	MatafButton mButton3;
	
	MatafLabel mLabel;
	MatafLink mLink;
	
	public LinkTest()
	{
		setContextName("slikaCtx");
		
		setLayout(new BorderLayout());
		/*mButton=new MatafButton("Activate Client Operation");
		mButton.setNavigationParameters(new com.ibm.dse.gui.NavigationParameters("activeView",1,"testTrxClientOp",0,"","","","","","",0,0,0,0,false,false));
		mButton.setType("Execute_Operation");
		
		add(mButton);*/
		
		/*mButton2 = new MatafButton("Update Context Directly");
		mButton2.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed(java.awt.event.ActionEvent e)
			{
				System.out.println("Putting Value directly");
				
				try
				{
					setValueAt("AccountNumber", "Value : "+Math.random());
					refreshDataExchangers();
				}
				catch (DSEInvalidArgumentException e1)
				{					
					e1.printStackTrace();
				}
				catch (DSEObjectNotFoundException e1)
				{
					e1.printStackTrace();
				}
			}
		});
		
		add(mButton2);*/
		
		mButton3 = new MatafButton("RefreshDataExchangers()");
		mButton3.addActionListener(new ActionListener()
		{
			private boolean bEnabled=false;
			public void actionPerformed(ActionEvent e)
			{
				//refreshDataExchangers();
				
				mLink.setEnabled(bEnabled);
				mLink.repaint();
				bEnabled=!bEnabled;
			}
		});
		
		add(mButton3);
		
		mLabel=new MatafLabel("Test");
		mLabel.setDataName("AccountNumber");
		mLabel.setSize(60,60);
		add(mLabel,BorderLayout.CENTER);
		
		mLink=new MatafLink("Test");
		mLink.setSize(80,50);
		mLink.setVerticalAlignment(SwingConstants.TOP);
		mLink.setHorizontalAlignment(SwingConstants.LEFT);
		mLink.setEnabled(false);
		/*mLink.setDataName("AccountNumber");
		mLink.setNavigationParameters(new com.ibm.dse.gui.NavigationParameters("activeView",1,"testTrxClientOp",0,"","","","","","",0,0,0,0,false,false));
		mLink.setType("Execute_Operation");*/
		mLink.setBorder(new LineBorder(Color.RED));
		add(mLink,BorderLayout.SOUTH);
		
	}
	
	private static void initEnvironment() throws Exception
	{
		Context.reset();
		Settings.reset("http://127.0.0.1:9080/MatafServer/dse/dse.ini");
		Settings.initializeExternalizers(Settings.MEMORY);
	
		Context ctx = (Context) Context.readObject("workstationCtx");			
		CSClientService csrv = (CSClientService) ctx.getService("CSClient");
		csrv.establishSession();	
		DSEClientOperation startOp =  (DSEClientOperation) DSEOperation.readObject("startupClientOp");
		startOp.execute();
	}
	
	
	public static void main(String[] args) throws Exception
	{
		initEnvironment();
		
		JFrame f = new JFrame("Test");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		LinkTest lt = new LinkTest();

		f.getContentPane().add(lt);
		f.setSize(640,480);
		f.setVisible(true);
	}
}