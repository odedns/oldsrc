package tests.views;

import mataf.desktop.views.MatafClientView;
import mataf.desktop.views.MatafTransactionView;
import mataf.types.MatafButton;
import mataf.types.MatafEmbeddedPanel;

/**
 * @author Eyal Ben Ze'ev
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 *
 */
public class EmptyView extends MatafTransactionView {
	
	public EmptyView() {
//		setContextName("emptyViewCtx");
		initialize();
	}

	private void initialize() {
		MatafEmbeddedPanel p2 = new EmptyPanel();
		this.setSize(800, 600);
		setTransactionPanel(p2);
				
		//		p2.get
		//		this.add(p2,BorderLayout.EAST);
		//		this.add(getASpTextField(), null);
	}

} 