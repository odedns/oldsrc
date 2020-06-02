package mataf.desktop.infopanels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.ibm.dse.base.Context;
import com.ibm.dse.desktop.Desktop;

import mataf.desktop.beans.MatafMenuBar;
import mataf.desktop.beans.MatafMenuItem;
import mataf.desktop.beans.MatafWorkingArea;
import mataf.dse.appl.OpenDesktop;
import mataf.services.proxy.ProxyService;
import mataf.types.MatafDecimalLabel;
import mataf.types.MatafEmbeddedPanel;
import mataf.types.MatafLabel;
import mataf.types.MatafLink;
import mataf.utils.FontFactory;

/**
 * This panel displays data on the current customer.
 * The panel is displayed on the north-west side of the main panel.
 * 
 * @author Nati Dykstein. Creation Date : (16/09/2003 16:42:48).  
 */
public class PirteyLakoachPanel extends MatafEmbeddedPanel
									implements InfoPanelsConstants
{
	private static final Dimension 	PANEL_HEADLINE_DIMENSION = new Dimension(200,16);
	private static final Color		PANEL_HEADLINE_BG_COLOR = new Color(200,220,255);
	
	
	private static final String[] dataNames = new String[]{
			"ItraMeshicha","ItraAdkanit","ItraKlalit",
			"Feedbacks.Feedback1","Feedbacks.Feedback2","Feedbacks.Feedback3","Feedbacks.Feedback4",
			"Feedbacks.Feedback5","Feedbacks.Feedback6","Feedbacks.Feedback7"};
	
	private JPanel pratimInfoPanel;
	//private JPanel pratimNegativeInfoPanel;
	private JPanel pratimPositiveInfoPanel;
	
	MatafLabel[] positiveLabels;
	MatafLabel[] negativeLabels;
	
	
	public PirteyLakoachPanel()
	{
		super(new BorderLayout());
		setBorder(BorderFactory.createEmptyBorder(0,2,0,2));
	
		setPreferredSize(new Dimension(233,290));
		
		JPanel pratimNorthPanel = new JPanel(new BorderLayout());
		
		pratimNorthPanel.setPreferredSize(PANEL_HEADLINE_DIMENSION);
		
		pratimNorthPanel.setBorder(null);
		pratimNorthPanel.setBackground(PANEL_HEADLINE_BG_COLOR);
		MatafLabel pratimHeadlineLabel = new MatafLabel("פרטי לקוח");
		
		pratimHeadlineLabel.setHorizontalAlignment(JLabel.CENTER);
		pratimHeadlineLabel.setVerticalAlignment(JLabel.TOP);
		pratimNorthPanel.add(pratimHeadlineLabel);
		
		// Holds the 3 information panels.
		JPanel pratimCenterPanel = new JPanel(new BorderLayout());

		// Regular info panel.
		pratimInfoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		pratimInfoPanel.setBackground(PANEL_BG_COLOR);
		pratimInfoPanel.setPreferredSize(new Dimension(200,20));
		
		// Positive info panel.
		pratimPositiveInfoPanel = new MatafEmbeddedPanel(new GridLayout(10,1));		setOpaque(false);
		pratimPositiveInfoPanel.setBorder(null);
		pratimPositiveInfoPanel.setOpaque(true);
		pratimPositiveInfoPanel.setBackground(PANEL_BG_COLOR);
		pratimPositiveInfoPanel.setPreferredSize(new Dimension(200,240));
		
	/*	// Negative info panel.
		pratimNegativeInfoPanel = new MatafEmbeddedPanel(new GridLayout(4,1))//new FlowLayout(FlowLayout.RIGHT))
		{
			GradientPaint gp = 
				new GradientPaint(0,0,new Color(255,240,240),5,5,Color.white,true);
			// Instance Initializer
			{
				setOpaque(false);
				setBorder(null);
				setBackgroundPainted(false);
			}
			// Special red background.
			public void paint(Graphics g) {				
				Graphics2D g2 = (Graphics2D)g;
				g2.setPaint(gp);
				g.fillRect(0,0,getSize().width,getSize().height);
				super.paint(g);
			}
		};
		pratimNegativeInfoPanel.setBackground(Color.white);
		pratimNegativeInfoPanel.setPreferredSize(new Dimension(200,70));*/

		addFieldsToInfoPanel();
		pratimCenterPanel.add(pratimInfoPanel, BorderLayout.NORTH);
		
		addFieldsToPositivePanel();
		InfoPanelsScrollPane pratimScroller = new InfoPanelsScrollPane(pratimPositiveInfoPanel);
		pratimCenterPanel.add(pratimScroller, BorderLayout.CENTER);
		
		//addFieldsToNegativePanel();		
		//pratimCenterPanel.add(pratimNegativeInfoPanel, BorderLayout.SOUTH);
		
		add(pratimNorthPanel, BorderLayout.NORTH);
		add(pratimCenterPanel, BorderLayout.CENTER);
	}
	
	private void addFieldsToInfoPanel()
	{
		MatafLink accountInfoLabel1 = new MatafLink(""); //,JLabel.CENTER
		accountInfoLabel1.setFont(FontFactory.createFont("Tahoma",Font.BOLD,12));
		//accountInfoLabel1.setVerticalAlignment(SwingConstants.TOP);
		accountInfoLabel1.setDataName("AccountBalance.OwnerName");
		
		// Activate Client-Information RT transaction.
		accountInfoLabel1.addMouseListener(new MouseAdapter()
		{
			public void mousePressed(MouseEvent e)
			{
				//setForeground(Color.blue);
				openRTClientInfoWindow();
			}

			public void mouseReleased(MouseEvent e)
			{
				//setForeground(Color.black);
			}
		});
		
		pratimInfoPanel.add(accountInfoLabel1);
	}
	
	private void openRTClientInfoWindow()
	{
		System.out.println("Sending RT request for client information.");
		try
		{
			ProxyService proxy = 
				(ProxyService)OpenDesktop.getContext().getService("proxyService");
			
			// Get transaction context.
			Context transactionCtx = 
					MatafWorkingArea.getActiveTransactionView().getContext();
			
			// Get current values from context.
			String branchNumber = (String)transactionCtx.getValueAt("BranchIdInput");
			String accountType = (String)transactionCtx.getValueAt("AccountType");
			String accountNumber = (String)transactionCtx.getValueAt("AccountNumber");
			
			// Update RT chaining structures.
			proxy.setGlobalValueAt("GLSG_SHIRSHUR_CH.GL_SNIF", branchNumber);
			proxy.setGlobalValueAt("GLSG_SHIRSHUR_CH.GL_SCH", accountType);
			proxy.setGlobalValueAt("GLSG_SHIRSHUR_CH.GL_CH", accountNumber);
				
			
			// Activate the RT transaction.
			String taskCode = 
				((MatafMenuItem)MatafMenuBar.getMenuItemByTaskName("1101")).getCode();
			
			// Make the RT know that we're activating nested transaction.
			String suffix = "*";
			
			proxy.activateTransaction(taskCode+suffix);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private void addFieldsToPositivePanel()
	{
		positiveLabels = new MatafLabel[dataNames.length];
		
		MatafEmbeddedPanel ePanel1 = new MatafEmbeddedPanel(new FlowLayout(FlowLayout.RIGHT));
		ePanel1.setBackgroundPainted(false);
		ePanel1.setBorder(null);
		MatafLabel l1 = new MatafLabel("יתרת משיכה  :");
		l1.setFont(FontFactory.getDefaultFont());
		l1.setVerticalAlignment(SwingConstants.TOP);
		positiveLabels[0] = new MatafDecimalLabel("");
		positiveLabels[0].setVerticalAlignment(SwingConstants.TOP);
		positiveLabels[0].setDataName("AccountBalance" + "." + dataNames[0]);
		positiveLabels[0].setFont(FontFactory.getDefaultFont());
		ePanel1.add(l1);
		ePanel1.add(positiveLabels[0]);
		pratimPositiveInfoPanel.add(ePanel1);
		
		
		MatafEmbeddedPanel ePanel2 = new MatafEmbeddedPanel(new FlowLayout(FlowLayout.RIGHT));
		ePanel2.setBackgroundPainted(false);
		ePanel2.setBorder(null);
		MatafLabel l2 = new MatafLabel("יתרת עדכנית :");
		l2.setVerticalAlignment(SwingConstants.TOP);
		positiveLabels[1] = new MatafDecimalLabel("");
		positiveLabels[1].setVerticalAlignment(SwingConstants.TOP);
		positiveLabels[1].setDataName("AccountBalance" + "." + dataNames[1]);
		ePanel2.add(l2);
		ePanel2.add(positiveLabels[1]);
		pratimPositiveInfoPanel.add(ePanel2);
		
		MatafEmbeddedPanel ePanel3 = new MatafEmbeddedPanel(new FlowLayout(FlowLayout.RIGHT));
		ePanel3.setBackgroundPainted(false);
		ePanel3.setBorder(null);
		MatafLabel l3 = new MatafLabel("יתרת כללית  :");
		l3.setVerticalAlignment(SwingConstants.TOP);
		positiveLabels[2] = new MatafDecimalLabel("");
		positiveLabels[2].setVerticalAlignment(SwingConstants.TOP);
		positiveLabels[2].setDataName("AccountBalance" + "." + dataNames[2]);
		ePanel3.add(l3);
		ePanel3.add(positiveLabels[2]);
		pratimPositiveInfoPanel.add(ePanel3);
		
		
		for(int i=3;i<dataNames.length;i++)
		{
			
			positiveLabels[i] = new MatafLabel("נתונים " + i);
			positiveLabels[i].setFont(FontFactory.getDefaultFont());
			positiveLabels[i].setVerticalAlignment(SwingConstants.TOP);
			positiveLabels[i].setDataName("AccountBalance" + "." + dataNames[i]);
			pratimPositiveInfoPanel.add(positiveLabels[i]);
		}
	}
	
/*	private void addFieldsToNegativePanel()
	{
		String dataNamePrefix = "AccountBalance.NegativeBalance.BalanceData";
		
		negativeLabels = new MatafLabel[dataNames.length];
		
		for(int i=0;i<dataNames.length;i++)
		{
			negativeLabels[i] = new MatafLabel("נתונים שליליים " + i);
			negativeLabels[i].setFont(FontFactory.getDefaultFont());
			negativeLabels[i].setVerticalAlignment(SwingConstants.TOP);
			negativeLabels[i].setDataName(dataNamePrefix + "." + dataNames[i]);
			pratimNegativeInfoPanel.add(negativeLabels[i]);
		}
	}*/
}
