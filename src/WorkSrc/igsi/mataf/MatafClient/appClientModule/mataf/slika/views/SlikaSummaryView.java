/*
 * Created on 22/01/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package mataf.slika.views;

import mataf.desktop.views.MatafDSEPanel;
import mataf.slika.panels.SummaryPanel;

/**
 * @author ronenk
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class SlikaSummaryView extends MatafDSEPanel
{
	private SummaryPanel summaryPanel;
	
	public SlikaSummaryView() 
	{
		summaryPanel = new SummaryPanel();
		setViewName("slikaSummaryView");
		setContextName("slikaSummaryCtx");
		initialize();
	}
	
	private void initialize()
	{
		setActivePanel(summaryPanel);
	}
}
