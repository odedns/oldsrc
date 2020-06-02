package mataf.desktop.infopanels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import mataf.desktop.views.MatafClientView;
import mataf.types.MatafButton;
import mataf.types.MatafEmbeddedPanel;
import mataf.types.MatafLabel;
import mataf.types.MatafLinkList;
import mataf.utils.FontFactory;

/**
 * This info panel layouts the customizable queries area.
 * 
 * @author Nati Dykstein. Creation Date : (16/09/2003 16:46:44).  
 */
public class SheiltotPanel extends MatafEmbeddedPanel implements InfoPanelsConstants 
{
	
	private MatafLinkList mllQueries; 
	
	public SheiltotPanel(MatafClientView mcvParent)
	{
		super(new BorderLayout());
		
		setPreferredSize(new Dimension(233,80));
		setBorder(BorderFactory.createEmptyBorder(0,2,0,2));
		setBackground(PANEL_BG_COLOR);
		
		JPanel sheiltotNorthPanel = new JPanel(new BorderLayout());
		
		sheiltotNorthPanel.setPreferredSize(PANEL_HEADLINE_DIMENSION);
		
		sheiltotNorthPanel.setBorder(null);
		sheiltotNorthPanel.setBackground(PANEL_HEADLINE_BG_COLOR);
		
		MatafLabel sheiltotHeadlineLabel = new MatafLabel("שאילתות");
		sheiltotHeadlineLabel.setHorizontalAlignment(JLabel.CENTER);
		sheiltotHeadlineLabel.setVerticalAlignment(JLabel.TOP);
		sheiltotNorthPanel.add(sheiltotHeadlineLabel);
		
		//MatafButton addSheilta = new MatafButton("הוסף שאילתא");
		/*addSheilta.setFont(FontFactory.createFont("Tahoma", Font.PLAIN, 10));
		addSheilta.setMaximumSize(new Dimension(40,14));
		addSheilta.setEnabled(true);
		sheiltotNorthPanel.add(addSheilta,BorderLayout.WEST);*/
		
		MatafEmbeddedPanel sheiltotCenterPanel = new MatafEmbeddedPanel(null); //new FlowLayout(FlowLayout.RIGHT)
		sheiltotCenterPanel.setPreferredSize(new Dimension(220,60));
		sheiltotCenterPanel.setBorder(null);
		sheiltotCenterPanel.setBackground(PANEL_BG_COLOR);
		
		//JLabel testText = new JLabel("שערי ניירות ערך");
		//testText.setFont(FontFactory.createFont("Tahoma", Font.PLAIN, 11));
		//sheiltotCenterPanel.add(testText);
		InfoPanelsScrollPane sheiltotScroller = new InfoPanelsScrollPane(sheiltotCenterPanel);		
		add(sheiltotScroller, BorderLayout.CENTER);
		add(sheiltotNorthPanel, BorderLayout.NORTH);
		
		sheiltotCenterPanel.setSize(sheiltotCenterPanel.getWidth()-sheiltotScroller.getWidth(),sheiltotCenterPanel.getHeight());
		
		mllQueries=new MatafLinkList();
		mllQueries.setDataNameForList("queriesList");
		mllQueries.setQueryPanel(sheiltotCenterPanel);
		mllQueries.setSpPanel(mcvParent);
	
	}
	
	
	public MatafLinkList getMatafLinkList()
	{
		return  mllQueries;
	}
}
