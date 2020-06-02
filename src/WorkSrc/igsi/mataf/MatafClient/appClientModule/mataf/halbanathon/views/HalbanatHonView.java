package mataf.halbanathon.views;

import java.util.Vector;

import mataf.data.VisualDataField;
import mataf.desktop.views.MatafDSEPanel;
import mataf.halbanathon.operations.HalbanatHonTransmitSugLakoachClientOp;
import mataf.halbanathon.panels.SugLakoachPanel;
import mataf.types.MatafButton;

import com.ibm.dse.base.Context;
import com.ibm.dse.base.DSEException;
import com.ibm.dse.base.DSEObjectNotFoundException;
import com.ibm.dse.base.DataMapperFormat;
import com.ibm.dse.base.FormatElement;
import com.ibm.dse.base.OperationRepliedEvent;
import com.ibm.dse.desktop.DSENavigationController;
import com.ibm.dse.desktop.DSETaskButton;
import com.ibm.dse.desktop.Desktop;
import com.ibm.dse.desktop.TaskButton;
import com.ibm.dse.gui.DSECoordinationEvent;

/**
 *
 * 
 * @author Nati Dykstein. Creation Date : (02/11/2003 13:55:53).  
 */
public class HalbanatHonView extends HalbanatHonAbstractView {
	public HalbanatHonView() {
		setViewName("halbanatHonView");
		setContextName("halbanathonCtx");
		setInstanceContext(true);
		setOperationName("initHalbanatHonClientOp");
		setExecuteWhenOpen(true);
		initialize();
	}

	private void initialize() {
		setActivePanel(new SugLakoachPanel());
	}

	/**
	 * @see mataf.desktop.views.MatafDSEPanel#handleOperationRepliedEvent(OperationRepliedEvent)
	 */
	public void handleOperationRepliedEvent(OperationRepliedEvent e) {
		super.handleOperationRepliedEvent(e);
		try {
			if (e.getSource()
				instanceof HalbanatHonTransmitSugLakoachClientOp) {
				String replyCode =
					(String) getValueAt("HASR_MIZDAMEN_PRATIM.HostHeaderReplyData.GKSR_HDR.GL_KOD_TIPUL");
				if (replyCode != null) {
					if ((replyCode.equalsIgnoreCase("k"))
						|| (replyCode.equalsIgnoreCase("h"))) {
						VisualDataField transmitButton =
							(VisualDataField) getContext().getElementAt(
								"HASS_LAK_PRATIM.TransmitButton");
						transmitButton.setIsEnabled(Boolean.FALSE);
					}
				}
			}

		} catch (DSEException ex) {
			ex.printStackTrace();
		}
	}
}
