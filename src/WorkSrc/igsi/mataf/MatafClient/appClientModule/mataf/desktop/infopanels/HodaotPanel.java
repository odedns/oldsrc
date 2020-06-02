package mataf.desktop.infopanels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import mataf.desktop.views.MatafDSEPanel;
import mataf.globalmessages.handler.StatusClickHandler;
import mataf.types.MatafEmbeddedPanel;
import mataf.types.MatafLabel;
import mataf.types.MatafScrollPane;
import mataf.types.table.MatafTable;
import mataf.utils.FontFactory;

import com.ibm.dse.base.IndexedCollection;
import com.ibm.dse.base.KeyedCollection;
import com.ibm.dse.gui.DSEPanel;

/**
 *
 * 
 * @author Nati Dykstein. Creation Date : (16/09/2003 17:01:07).  
 */
public class HodaotPanel extends MatafEmbeddedPanel 
								implements InfoPanelsConstants
{
	protected MatafTable mtMessages=null;
	
	public HodaotPanel()
	{
		super(new BorderLayout());
		setPreferredSize(new Dimension(233,90));
		setBorder(BorderFactory.createEmptyBorder(0,2,0,2));
		
		JPanel hodaotNorthPanel = new JPanel(new BorderLayout());
		
		hodaotNorthPanel.setPreferredSize(PANEL_HEADLINE_DIMENSION);
		
		hodaotNorthPanel.setBorder(null);
		hodaotNorthPanel.setBackground(PANEL_HEADLINE_BG_COLOR);
		
		MatafLabel hodaotHeadlineLabel = new MatafLabel("מערכת הודעות");
		hodaotHeadlineLabel.setHorizontalAlignment(JLabel.CENTER);
		hodaotHeadlineLabel.setVerticalAlignment(JLabel.TOP);
		hodaotNorthPanel.add(hodaotHeadlineLabel);
		add(hodaotNorthPanel, BorderLayout.NORTH);
		
		JPanel hodaotSouthPanel= new JPanel(new BorderLayout());
	
		hodaotSouthPanel.setBorder(null);
		
		mtMessages=new MatafTable();
		mtMessages.setEditable(false);
		mtMessages.setDynamic(true);
		mtMessages.setFont(FontFactory.createFont("Tahoma",Font.PLAIN,10));
		mtMessages.setDataNameForList("globalMessagesWindow");
		mtMessages.getOurModel().addColumn(String.class, "הודעה", "messageData.messageText",200);
		mtMessages.getOurModel().addColumn(String.class, "סטטוס", "messageData.messageStatus",40);
		mtMessages.getOurModel().addColumn(String.class/*IMessageClickHandler.class*/, "CLASSREF", "messageData.messageHandler");
		
		
		mtMessages.setRowHeight(11);
	
		
		// Visualy removing the two columns.
		mtMessages.setNumberOfHiddenColumns(1);
		
		//tcTemp=mtMessages.getColumnModel().getColumn(2);
		//mtMessages.removeColumn(tcTemp);
		mtMessages.addMouseListener(new StatusClickHandler());
		//WindowMessagesHandler.setMessagesTable(mtMessages);
		
		//buildDemoData();

		InfoPanelsScrollPane jspScroll=new InfoPanelsScrollPane(mtMessages);
		jspScroll.setPreferredSize(new Dimension(230,100));
		hodaotSouthPanel.add(jspScroll,BorderLayout.WEST);
		add(hodaotSouthPanel,BorderLayout.CENTER);
	}
}
