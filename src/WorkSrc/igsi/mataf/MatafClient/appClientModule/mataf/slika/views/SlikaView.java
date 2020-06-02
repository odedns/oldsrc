package mataf.slika.views;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.Vector;

import mataf.desktop.views.MatafDSEPanel;
import mataf.slika.panels.SlikaPanel1;
import mataf.slika.panels.SlikaPanel1;
import mataf.types.MatafButton;

import com.ibm.dse.base.Context;
import com.ibm.dse.base.DataMapperFormat;
import com.ibm.dse.base.FormatElement;

import com.ibm.dse.gui.DSECoordinatedPanel;
import com.ibm.dse.gui.DSECoordinationEvent;

/**
 * The view for the slika.
 * 
 * @author Nati Dykstein.
 * 
 */
public class SlikaView extends MatafDSEPanel
{	
	public SlikaView() 
	{		
		setViewName("slikaView");
		setContextName("slikaCtx");
		setInstanceContext(true);		
		setOperationName("initSlikaClientOp");
		setExecuteWhenOpen(true);
		
		initialize();
	}
	
	private void initialize()
	{		
		setActivePanel(new SlikaPanel1());
	}
	
		/**
	 * @see com.ibm.dse.gui.DSEPanel#handleDSECoordinationEvent(DSECoordinationEvent)
	 */
	public void handleDSECoordinationEvent(DSECoordinationEvent event) {
		
		if (event.getEventSourceType().equals(DSECoordinationEvent.EVENT_SOURCETYPE_NEXT)) {
			if (event.getSource() instanceof MatafButton) {
				if (((MatafButton)event.getSource()).getActionCommand().equals("open_halbanat_hon")) {
					try {
						String map = event.getOpenMapFormat();
						DataMapperFormat mapper = (DataMapperFormat)FormatElement.readObject(map);
						Vector chidrenCtxVector = getContext().getChildren();
						Context halbantHonCtx = null;
						for (int i = 0; i < chidrenCtxVector.size(); i++) {
							halbantHonCtx = (Context)chidrenCtxVector.get(i);
							if (halbantHonCtx.getName().equals("halbanathonCtx")) {
								Context source = getContext();
								mapper.mapContents(getContext(), halbantHonCtx);
								System.out.println(halbantHonCtx.getKeyedCollection());
							}
						}
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			
		}
		
		
		super.handleDSECoordinationEvent(event);
	}	
}  //  @jve:visual-info  decl-index=0 visual-constraint="0,0"