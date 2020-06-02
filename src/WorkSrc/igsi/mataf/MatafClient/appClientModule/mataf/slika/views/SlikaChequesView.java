package mataf.slika.views;

import mataf.data.VisualDataField;
import mataf.desktop.views.MatafDSEPanel;
import mataf.slika.operations.AcceptSlClientOp;
import mataf.slika.operations.ReadCheckDetailsClientOp;
import mataf.slika.operations.SendCheckDetailsClientOp;
import mataf.slika.panels.SlikaPanel2;
import mataf.types.table.MatafTable;
import mataf.utils.MatafCoordinationEvent;

import com.ibm.dse.base.DSEObjectNotFoundException;
import com.ibm.dse.base.IndexedCollection;
import com.ibm.dse.base.OperationRepliedEvent;
import com.ibm.dse.gui.DSECoordinationEvent;
import com.ibm.dse.gui.SpButton;

/**
 * The view for the slika second screen.
 * 
 * This class also handles the operation replyed events when
 * special actions are needed on the return of some operations.
 * 
 * @author Nati Dykstein.
 * 
 */
public class SlikaChequesView extends MatafDSEPanel
{
	private SlikaPanel2 slikaPanel2;
	
	public SlikaChequesView()
	{
		slikaPanel2 = new SlikaPanel2();
		setViewName("slikaChequesView");
		setContextName("slikaChequesCtx");
		initialize();
	}

	private void initialize()
	{
		setActivePanel(slikaPanel2);
	}

	/**
	 * If checks table is valid - opens HalbanatHon by programatically 
	 * clicking a zero sized operation button.
	 */
	public void handleOperationRepliedEvent(OperationRepliedEvent e)
	{
		if (e.getSource() instanceof SendCheckDetailsClientOp)
		{
			SendCheckDetailsClientOp clientOp =
				(SendCheckDetailsClientOp)e.getSource();
			IndexedCollection messages = null;
			try
			{
				messages = 
					(IndexedCollection)getContext().getElementAt("BusinessMessagesList");
		
			// Verify that no error we're reported on the checks table
			// before opening HalbanatHon.
			if (clientOp.isChecksTableValid() && messages.size() == 0)
				slikaPanel2.getMatafButton10().doClick();
			}
			catch (DSEObjectNotFoundException e1)
			{
				e1.printStackTrace();
			}
		}
		else
			if (e.getSource() instanceof AcceptSlClientOp)
			{
				DSECoordinationEvent coordinationEvent =
					new DSECoordinationEvent(new SpButton());
				coordinationEvent.setLinkContextTo(DSECoordinationEvent.CTXUSED_ACTIVE);
				coordinationEvent.setOperationName("");
				// Not relevant for open view but just in case...
				coordinationEvent.setViewName("slikaSummaryView");
				coordinationEvent.setViewSource("mataf.slika.views.SlikaSummaryView");
				coordinationEvent.setNavigation(DSECoordinationEvent.NAVIGATION_CHILDREN);
				coordinationEvent.setEventSourceType(DSECoordinationEvent.EVENT_SOURCETYPE_OPEN_VIEW);
				coordinationEvent.setChainContext(DSECoordinationEvent.CHAIN_ACTIVE_CONTEXT);
				// Not relevant for open view but just in case...
				coordinationEvent.setOutputMapFormat("");
				coordinationEvent.setInputMapFormat("");
				coordinationEvent.setCloseMapFormat("");
				coordinationEvent.setOpenMapFormat("");
				coordinationEvent.setEventType(
					DSECoordinationEvent.EVENT_EVENTYPE_NAVIGATION);
				fireCoordinationEvent(coordinationEvent);
			}
			else // Place the focus on the amount column.
				if (e.getSource() instanceof ReadCheckDetailsClientOp)
				{	
					// We're assuming the check reader always 
					// adds a check to the last row.
					MatafTable table = slikaPanel2.getMatafTable();
					int row = table.getOurModel().getRowCount()-1;
					final int AMOUNT_COLUMN_NUMBER = 5;
					table.requestFocus();
					table.setEditingRow(row);
					table.setEditingColumn(AMOUNT_COLUMN_NUMBER);
					table.changeSelection(row, AMOUNT_COLUMN_NUMBER, false, false);
				}
		
		super.handleOperationRepliedEvent(e);
	}
	/**
	 * @see mataf.desktop.views.MatafDSEPanel#afterChildClose(Object)
	 */
	public void afterChildClose(Object param) {
		try {
			boolean overrideResult = Boolean.valueOf((String) getContext().getValueAt("trxORData.overrideResult")).booleanValue();
			if(overrideResult) {
				((VisualDataField) getContext().getElementAt("KlotHamchaotButton")).setIsEnabled(Boolean.TRUE);
			}
		} catch (DSEObjectNotFoundException e) {
			e.printStackTrace();
		}
	}


	
	/**
	 * @see com.ibm.dse.gui.SpPanel#handleDSECoordinationEvent(DSECoordinationEvent)
	 */
	public void handleDSECoordinationEvent(DSECoordinationEvent event) {
		super.handleDSECoordinationEvent(event);
		try {
			if(event.getEventSourceType() == MatafCoordinationEvent.EVENT_SOURCETYPE_CLOSE_CHILD_VIEW) {
				boolean overrideResult;
				overrideResult = Boolean.valueOf((String) getContext().getValueAt("trxORData.overrideResult")).booleanValue();
				if(overrideResult) {
					((VisualDataField) getContext().getElementAt("KlotHamchaotButton")).setIsEnabled(Boolean.TRUE);
					((VisualDataField) getContext().getElementAt("IshurMenahelButton")).setIsEnabled(Boolean.FALSE);
					((VisualDataField) getContext().getElementAt("IshurMenahelMeruchakButton")).setIsEnabled(Boolean.FALSE);
				}
			}
		} catch (DSEObjectNotFoundException e) {
			e.printStackTrace();
		}
	}

}