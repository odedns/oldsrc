package mataf.slika.panels.handlers;

import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;

import mataf.data.VisualDataField;
import mataf.desktop.views.MatafDSEPanel;
import mataf.types.MatafButton;
import mataf.types.MatafEmbeddedPanel;

import com.ibm.dse.base.Context;
import com.ibm.dse.base.DSEInvalidArgumentException;
import com.ibm.dse.base.DSEObjectNotFoundException;
import com.ibm.dse.base.IndexedCollection;
import com.mataf.dse.appl.OpenDesktop;
import com.mataf.eventmanager.AbstractEventManager;

/**
 * @author yossid
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class SlikaPanel2Handler extends AbstractEventManager implements FocusListener {

	/**
	 * Constructor for SlikaPanel2Handler.
	 */
	public SlikaPanel2Handler() {
		super();
	}

	/**
	 * Constructor for SlikaPanel2Handler.
	 * @param aView
	 */
	public SlikaPanel2Handler(MatafDSEPanel aView) {
		super(aView);
	}

	/**
	 * Constructor for SlikaPanel2Handler.
	 * @param aPanel
	 */
	public SlikaPanel2Handler(MatafEmbeddedPanel aPanel) {
		super(aPanel);
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
			if (e.getActionCommand().equals("TakenHamchaotBtn")) {
				Context ctx = OpenDesktop.getActiveContext();
				MatafButton matafBtn = (MatafButton) e.getSource();
				((VisualDataField) ctx.getElementAt("VadeNetunimButton")).setIsEnabled(Boolean.TRUE);
				((VisualDataField) ctx.getElementAt("CheckReaderButton")).setIsEnabled(Boolean.TRUE);
				((VisualDataField) ctx.getElementAt("KlotHamchaotButton")).setIsEnabled(Boolean.FALSE);
				((VisualDataField) ctx.getElementAt("ChecksTable")).setIsEnabled(Boolean.TRUE);
				matafBtn.getDSECoordinatedPanel().refreshDataExchangers();
			} 
		} catch(DSEObjectNotFoundException ex) {
			ex.printStackTrace();
		} 
	}

	/**
	 * @see java.awt.event.KeyListener#keyTyped(KeyEvent)
	 */
	public void keyTyped(KeyEvent e) {
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
	 * @see java.awt.event.MouseListener#mouseClicked(MouseEvent)
	 */
	public void mouseClicked(MouseEvent e) {
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
	 * @see java.beans.PropertyChangeListener#propertyChange(PropertyChangeEvent)
	 */
	public void propertyChange(PropertyChangeEvent evt) {
	}

}
