package mataf.srika.views;

import mataf.desktop.views.MatafDSEPanel;
import mataf.srika.panels.SrikaPanel2;

/**
 * The view for the srika.
 * 
 * @author Nati Dykstein.
 * 
 */
public class AdvancedSrikaView extends MatafDSEPanel {
	public AdvancedSrikaView() {
		setViewName("advancedSrikaView");
		setContextName("advancedSrikaCtx");
		setInstanceContext(true);
		//setOperationName("initAdvancedSrikaClientOp");
		setExecuteWhenOpen(true);

		initialize();
	}

	private void initialize() {
		setActivePanel(new SrikaPanel2());
	}
} //  @jve:visual-info  decl-index=0 visual-constraint="0,0"