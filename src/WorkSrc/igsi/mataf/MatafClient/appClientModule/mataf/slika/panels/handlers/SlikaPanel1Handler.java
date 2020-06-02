package mataf.slika.panels.handlers;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.util.HashMap;
import java.util.Map;

import mataf.data.VisualDataField;
import mataf.services.MessagesHandlerService;
import mataf.services.reftables.RefTables;
import mataf.slika.panels.SlikaPanel1;
import mataf.slika.panels.SlikaPanel1;
import mataf.types.MatafButton;
import mataf.types.MatafEmbeddedPanel;
import mataf.types.MatafRadioButton;
import mataf.types.MatafTextField;

import com.ibm.dse.base.Context;
import com.ibm.dse.base.DSEObjectNotFoundException;
import com.ibm.dse.base.IndexedCollection;
import com.ibm.dse.base.KeyedCollection;
import com.ibm.dse.base.OperationStep;
import com.mataf.dse.appl.OpenDesktop;
import com.mataf.eventmanager.AbstractEventManager;

/**
 * @author Eyal Ben Ze'ev
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class SlikaPanel1Handler extends AbstractEventManager implements FocusListener {
	
	private SlikaPanel1 observedPanel;
	
//	public SlikaPanel1Handler(SlikaPanel1 aPanel) {
//		this.observedPanel = aPanel;
//	}
	
	public SlikaPanel1Handler(SlikaPanel1 aPanel) {
		this.observedPanel = aPanel;
	}
	/**
	 * @see java.awt.event.FocusListener#focusGained(FocusEvent)
	 */
	public void focusGained(FocusEvent e) {
	}

	/**
	 * @see java.awt.event.FocusListener#focusLost(FocusEvent)
	 */
	public void focusLost(FocusEvent e) {
		
		if (e.getComponent().getName().equals("matafTextField3")) { // if field is Account number
//				observedPanel.getMatafTextField3().fireCoordinationEvent();
		}
	}

	/**
	 * @see java.awt.event.ItemListener#itemStateChanged(ItemEvent)
	 */
	public void itemStateChanged(ItemEvent e) {
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		try {
			if (e.getActionCommand().equals("loansDetailsBtn")) {
				Context ctx = OpenDesktop.getActiveContext();
			
				((VisualDataField) ctx.getElementAt("IdCardNumberTitle")).setIsVisible(Boolean.TRUE);
				((VisualDataField) ctx.getElementAt("MahutTashlumTitle")).setIsVisible(Boolean.TRUE);			
				((VisualDataField) ctx.getElementAt("IdCardNumber")).setIsVisible(Boolean.TRUE);
				((VisualDataField) ctx.getElementAt("HotsaotRishoniotLoanNumber")).setIsVisible(Boolean.TRUE);
				((VisualDataField) ctx.getElementAt("HotsaotRishoniotLoanAmmount")).setIsVisible(Boolean.TRUE);
				((VisualDataField) ctx.getElementAt("HotsaotRishoniotLoanNumberTitle")).setIsVisible(Boolean.TRUE);
				((VisualDataField) ctx.getElementAt("HotsaotRishoniotLoanAmmountTitle")).setIsVisible(Boolean.TRUE);
				((VisualDataField) ctx.getElementAt("MahutTashlum")).setIsVisible(Boolean.TRUE);
//				((VisualDataField) ctx.getElementAt("HalvaotTable")).setIsVisible(Boolean.TRUE);
				((VisualDataField) ctx.getElementAt("VadeHalvaotButton")).setIsEnabled(Boolean.TRUE);
				((VisualDataField) ctx.getElementAt("PerutHalvaotButton")).setIsEnabled(Boolean.FALSE);
			
			} else if (e.getActionCommand().equals("takbulimBtn")) {
				Context ctx = OpenDesktop.getActiveContext();
				IndexedCollection loansList = (IndexedCollection) ctx.getElementAt("LoansList");
				setEnabelment2fields(loansList, 0, loansList.size(), true);
			
			} else if (e.getActionCommand().equals("hotsaotRishoniotBtn")) {
				Context ctx = OpenDesktop.getActiveContext();
				IndexedCollection loansList = (IndexedCollection) ctx.getElementAt("LoansList");
				setEnabelment2fields(loansList,0, 1, true);
				setEnabelment2fields(loansList, 1, loansList.size(), false);
			
			}
			OpenDesktop.getActiveMatafPanel().refreshDataExchangers();
		} catch(DSEObjectNotFoundException ex) {
			ex.printStackTrace();
		}
	}
	
	private void setEnabelment2fields(IndexedCollection loansList, int startIndex, int endIndex, boolean value2set) 
										throws DSEObjectNotFoundException {
		KeyedCollection kcoll = null;
		for( int counter=startIndex ; counter<endIndex ; counter++) {
			kcoll = (KeyedCollection) loansList.getElementAt(counter);
			((VisualDataField) kcoll.getElementAt("loanNumber")).setIsEnabled(new Boolean(value2set));
			((VisualDataField) kcoll.getElementAt("loanAmount")).setIsEnabled(new Boolean(value2set));
		}
	}

	/**
	 * @see java.awt.event.KeyListener#keyPressed(KeyEvent)
	 */
	public void keyPressed(KeyEvent e) {
	}

	/**
	 * @see java.awt.event.KeyListener#keyReleased(KeyEvent)
	 */
	public void keyReleased(KeyEvent e) {
	}

	/**
	 * @see java.awt.event.KeyListener#keyTyped(KeyEvent)
	 */
	public void keyTyped(KeyEvent e) {
	}

	/**
	 * @see java.awt.event.MouseListener#mouseClicked(MouseEvent)
	 */
	public void mouseClicked(MouseEvent e) {
	}

	/**
	 * @see java.awt.event.MouseListener#mouseEntered(MouseEvent)
	 */
	public void mouseEntered(MouseEvent e) {
	}

	/**
	 * @see java.awt.event.MouseListener#mouseExited(MouseEvent)
	 */
	public void mouseExited(MouseEvent e) {
	}

	/**
	 * @see java.awt.event.MouseListener#mousePressed(MouseEvent)
	 */
	public void mousePressed(MouseEvent e) {
	}

	/**
	 * @see java.awt.event.MouseListener#mouseReleased(MouseEvent)
	 */
	public void mouseReleased(MouseEvent e) {
	}

	/**
	 * @see java.beans.PropertyChangeListener#propertyChange(PropertyChangeEvent)
	 */
	public void propertyChange(PropertyChangeEvent evt) {
	}
	
}
