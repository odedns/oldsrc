package mataf.desktop.infopanels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import mataf.types.MatafEmbeddedPanel;
import mataf.types.MatafLabel;

/**
 *
 * 
 * @author Nati Dykstein. Creation Date : (16/09/2003 17:14:36).  
 */
public class ShivukPanel extends MatafEmbeddedPanel 
							implements InfoPanelsConstants
{
	public ShivukPanel()
	{
		super(new BorderLayout());
		setMinimumSize(new Dimension(200,50));
		setPreferredSize(new Dimension(200,115));
		setBorder(BorderFactory.createLineBorder(Color.black));
		JPanel shivukNorthPanel = new JPanel(new BorderLayout());
		shivukNorthPanel.setMinimumSize(PANEL_HEADLINE_DIMENSION);
		shivukNorthPanel.setPreferredSize(PANEL_HEADLINE_DIMENSION);
		shivukNorthPanel.setMaximumSize(PANEL_HEADLINE_DIMENSION);
		shivukNorthPanel.setBorder(null);
		shivukNorthPanel.setBackground(PANEL_HEADLINE_BG_COLOR);
		MatafLabel shivukHeadlineLabel = new MatafLabel("מידע עזר");
		shivukHeadlineLabel.setHorizontalAlignment(JLabel.CENTER);
		shivukHeadlineLabel.setVerticalAlignment(JLabel.TOP);
		shivukNorthPanel.add(shivukHeadlineLabel);
		add(shivukNorthPanel, BorderLayout.NORTH);
	}

}
