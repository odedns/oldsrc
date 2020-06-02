package mataf.srika.views;

import mataf.desktop.views.MatafDSEPanel;
import mataf.srika.panels.SrikaResultsSummaryPanel;

/**
 *
 * 
 * @author Nati Dykstein. Creation Date : (15/02/2004 17:42:30).  
 */
public class SrikaResultsView extends MatafDSEPanel
{

	public SrikaResultsView() {
		setViewName("srikaResultsView");
		setContextName("srikaResultsCtx");
		setInstanceContext(true);
		//setOperationName("srikaResultsClientOp");
		setExecuteWhenOpen(true);

		initialize();
	}

	private void initialize() {
		setActivePanel(new SrikaResultsSummaryPanel());
	}
}
