package mataf.srika.views;

import mataf.desktop.views.MatafDSEPanel;
import mataf.srika.panels.SrikaNavigationPanel;

/**
 *
 * 
 * @author Nati Dykstein. Creation Date : (15/02/2004 14:47:54).  
 */
public class SrikaNavigationView extends MatafDSEPanel
{

	public SrikaNavigationView() {
		setViewName("srikaNavigationView");
		setContextName("srikaNavigationCtx");
		setInstanceContext(true);
		//setOperationName("srikaNavigationClientOp");
		setExecuteWhenOpen(true);

		initialize();
	}

	private void initialize() {
		setActivePanel(new SrikaNavigationPanel());
	}
}
