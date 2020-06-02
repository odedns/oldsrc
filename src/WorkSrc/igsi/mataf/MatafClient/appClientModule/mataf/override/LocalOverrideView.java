package mataf.override;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JOptionPane;

import mataf.desktop.beans.MatafWorkingArea;
import mataf.desktop.views.MatafClientView;
import mataf.desktop.views.MatafTransactionView;
import mataf.logger.GLogger;
import mataf.types.MatafEmbeddedPanel;
import mataf.types.MatafErrorLabel;
import mataf.types.MatafOptionPane;
import mataf.types.MatafScrollPane;
import mataf.utils.MatafCoordinationEvent;
import mataf.utils.MatafGuiUtilities;

import com.ibm.dse.base.Context;
import com.ibm.dse.base.DSEInvalidArgumentException;
import com.ibm.dse.base.DSEObjectNotFoundException;
import com.ibm.dse.base.Hashtable;
import com.ibm.dse.base.IndexedCollection;
import com.ibm.dse.base.OperationRepliedEvent;
import com.ibm.dse.desktop.Desktop;
import com.ibm.dse.gui.DSECoordinationEvent;
import com.ibm.dse.gui.NavigationParameters;

import mataf.dse.appl.OpenDesktop;

/**
 * @author ronenk
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class LocalOverrideView extends MatafTransactionView {
	MatafEmbeddedPanel m_ePanel = null;
	Integer m_taskCode = null;
	//SendOverridePanel m_sendOr = null;
	LocalOverridePanel m_localPanel = null;
	MatafTransactionView m_trxView = null;

	public LocalOverrideView() {
		this.setViewName("LocalOverrideView");
		setContextName("overrideCtx");
		setDisableWhileOperationRunning(true);
		executeOper("managersListOp", DSECoordinationEvent.CHAIN_ACTIVE_CONTEXT, null, null);
		GLogger.debug("LocalORView: after execute managersListOp");
		initialize();
	}

	private void initialize() {
		GLogger.debug("in LocalOverridView Initialize");
		Context ctx = getContext();
		IndexedCollection ic = null;
		try {
			ic = (IndexedCollection) ctx.getElementAt("managersComboList");
		} catch (DSEObjectNotFoundException dee) {
			ic = null;
			dee.printStackTrace();
		}
		if (ic.size() < 1) {
			MatafOptionPane.showMessageDialog(this, "אין מנהלים פעילים בסניף- נסה מאוחר יותר", "שגיאה", JOptionPane.CLOSED_OPTION);
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

			m_localPanel = new LocalOverridePanel();

			m_localPanel.setPreferredSize(new Dimension(500, 150));

			m_ePanel.setPreferredSize(new Dimension(MatafEmbeddedPanel.DEFAULT_TRANSACTION_SCREEN_SIZE.width - 20, MatafEmbeddedPanel.DEFAULT_TRANSACTION_SCREEN_SIZE.height));
			MatafScrollPane scroll = new MatafScrollPane(m_ePanel);
			p.add(scroll, BorderLayout.CENTER);
			p.add(m_localPanel, BorderLayout.SOUTH);
			setTransactionPanel(p);
			
			String strOverrideAhead="false";
			try
			{
				Context trxCtx   = MatafWorkingArea.getActiveTransactionView().getContext();
				strOverrideAhead = (String) trxCtx.getValueAt("trxORData.overrideInAdvance");
			}
			catch (DSEObjectNotFoundException e)
			{
				// TODO Auto-generated catch block
				//e.printStackTrace();
				GLogger.error(LocalOverrideView.class,null,"Unable to find trxORData.OverrideInAdvance",e,false);
			}
			if(Boolean.valueOf(strOverrideAhead).booleanValue())
				m_localPanel.getSpPasswordField().setEnabled(false);
				

		}
	}

	/**
	 * @see com.ibm.dse.gui.DSEPanel#handleDSECoordinationEvent(DSECoordinationEvent)
	 */

	public void handleDSECoordinationEvent(DSECoordinationEvent event) {
		super.handleDSECoordinationEvent(event);
		if (event.getEventSourceType().equals(event.EVENT_SOURCETYPE_CLOSE_NAVIGATION)) {
			GLogger.debug("LocalOverrideView - closed view");

			/*
			 * notify the calling view that the 
			 * override view has finished.
			 */
			LocalOverrideView sView = this;
			MatafCoordinationEvent mevent = new MatafCoordinationEvent(this);
			mevent.setEventSourceType(MatafCoordinationEvent.EVENT_SOURCETYPE_CLOSE_CHILD_VIEW);
			sView.m_trxView.handleDSECoordinationEvent(mevent);

		}

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
			fireCoordinationEvent(event);
		} else if (arg0.getSource() instanceof LocalOverrideOp) 
		{
			String sExit=(String)arg0.getParameters().get("exit");
			if(Boolean.valueOf(sExit).booleanValue())
				fireCoordinationEvent(event);
		}
		System.out.println("***** close the view ******");
		// check if this is an error.
		if ("error".equalsIgnoreCase(arg0.getName())) {
			Hashtable ht = (Hashtable) arg0.getParameters();
			if (null != ht) {
				String msg = (String) ht.get("errMessage");
				MatafErrorLabel melErrors = OpenDesktop.getActiveTransactionView().getTheErrorLabel();
				melErrors.queueErrorMessage(msg, java.awt.Color.RED);
			}
		}
		
		super.handleOperationRepliedEvent(arg0);
	}

}
