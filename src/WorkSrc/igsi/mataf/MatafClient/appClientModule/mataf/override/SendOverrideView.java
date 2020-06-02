package mataf.override;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JScrollPane;

import mataf.desktop.views.MatafClientView;
import mataf.desktop.views.MatafTransactionView;
import mataf.logger.GLogger;
import mataf.types.MatafEmbeddedPanel;
import mataf.types.MatafErrorLabel;
import mataf.types.MatafScrollPane;
import mataf.utils.MatafGuiUtilities;

import com.ibm.dse.base.Context;
import com.ibm.dse.base.DSEObjectNotFoundException;
import com.ibm.dse.base.Hashtable;
import com.ibm.dse.base.IndexedCollection;
import com.ibm.dse.base.OperationRepliedEvent;
import com.ibm.dse.desktop.Desktop;
import com.ibm.dse.gui.DSECoordinationEvent;
import com.ibm.dse.gui.NavigationParameters;

import mataf.dse.appl.OpenDesktop;

/**
 * @author Oded Nissan
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class SendOverrideView extends MatafTransactionView {
	MatafEmbeddedPanel m_ePanel = null;
	Integer m_taskCode = null;
	OverrideWaitThread m_th = null;
	SendOverridePanel m_sendOr = null;
	MatafTransactionView m_trxView = null;
	boolean m_err = false;

	public SendOverrideView() {
		this.setViewName("SendOverrideView");
		setContextName("overrideCtx");
		setDisableWhileOperationRunning(true);
		executeOper("managersListOp", DSECoordinationEvent.CHAIN_ACTIVE_CONTEXT, null, null);
		GLogger.debug("SendORView: after execute managersListOp");
		initialize();
	}

	/**
	 * initialize the override pane.
	 * If there are no active managers display error
	 * dialog.
	 */
	private void initialize() {
		GLogger.debug("in SendOverridView Initialize");
		Context ctx = getContext();
		IndexedCollection ic = null;

		try {
			ic = (IndexedCollection) ctx.getElementAt("managersComboList");
		} catch (DSEObjectNotFoundException dee) {
			ic = null;
			dee.printStackTrace();
		}
		if (ic.size() < 1) {
			String msg = "אין מנהלים פעילים בסניף";
			if (!m_err) {
				MatafErrorLabel melErrors = OpenDesktop.getActiveTransactionView().getTheErrorLabel();
				melErrors.queueErrorMessage(msg);
			}
			this.close(true);
			String v[] = new String[1];
			v[0] = getViewName();
			MatafGuiUtilities.closeViews(v);
		} else {

			MatafEmbeddedPanel p = new MatafEmbeddedPanel();
			p.setBorder(null);
			BorderLayout layBorderLayout = new BorderLayout();
			p.setLayout(layBorderLayout);
			m_trxView = OpenDesktop.getActiveTransactionView();
			MatafEmbeddedPanel ePanel1 = m_trxView.getTransactionPanel();
			m_taskCode = new Integer(Desktop.getDesktop().getTaskArea().getCurrentTask().hashCode());
			m_ePanel = (MatafEmbeddedPanel) ePanel1.clone();

			m_sendOr = new SendOverridePanel();

			m_sendOr.setPreferredSize(new Dimension(500, 150));

			m_ePanel.setPreferredSize(new Dimension(MatafEmbeddedPanel.DEFAULT_TRANSACTION_SCREEN_SIZE.width - 20, MatafEmbeddedPanel.DEFAULT_TRANSACTION_SCREEN_SIZE.height));
			MatafScrollPane scroll = new MatafScrollPane(m_ePanel);
			scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			p.add(scroll, BorderLayout.CENTER);
			p.add(m_sendOr, BorderLayout.SOUTH);
			setTransactionPanel(p);

		} // if
	}

	/**
	 * @see com.ibm.dse.base.OperationRepliedListener#handleOperationRepliedEvent(OperationRepliedEvent)
	 */
	public void handleOperationRepliedEvent(OperationRepliedEvent arg0) {
		NavigationParameters params = new NavigationParameters();
		DSECoordinationEvent event = params.generateCoordinationEvent(DSECoordinationEvent.EVENT_SOURCETYPE_VIEW_CLOSED, this);
		event.setEventType(DSECoordinationEvent.EVENT_EVENTYPE_NAVIGATION);
		event.setViewName(getViewName());
		event.setName(this.getClass().getName() + ".actionPerformed");
		if (arg0.getSource() instanceof OverrideCancelClientOp) {
			// close the current view
			fireCoordinationEvent(event);
		} else if (arg0.getSource() instanceof OverrideReplyClientOp) {
			fireCoordinationEvent();
		}
		// check if this is an error.
		if (!"error".equalsIgnoreCase(arg0.getName())) {
			return;
		}
		m_err = true;
		Hashtable ht = (Hashtable) arg0.getParameters();
		if (null != ht) {
			String msg = (String) ht.get("errMessage");
			MatafErrorLabel melErrors = OpenDesktop.getActiveTransactionView().getTheErrorLabel();
			melErrors.queueErrorMessage(msg, Color.RED);
		}

	}

}
