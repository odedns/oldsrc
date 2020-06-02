package mataf.srika.views;

import mataf.desktop.views.MatafDSEPanel;
import mataf.srika.panels.SrikaPanel1;

/**
 * The view for the srika.
 * 
 * @author Eyal Ben Ze'ev.
 * 
 */
public class SrikaView extends MatafDSEPanel {
	public SrikaView() {
		setViewName("srikaView");
		setContextName("srikaCtx");
		setInstanceContext(true);
		setOperationName("initSrikaClientOp");
		setExecuteWhenOpen(true);

		initialize();
	}

	private void initialize() {
		setActivePanel(new SrikaPanel1());
	}
} //  @jve:visual-info  decl-index=0 visual-constraint="0,0"