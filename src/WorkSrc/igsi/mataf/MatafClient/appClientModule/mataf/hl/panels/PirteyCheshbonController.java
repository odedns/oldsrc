package mataf.hl.panels;

import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;

import mataf.types.MatafComboBox;

import com.ibm.dse.base.Context;
import mataf.eventmanager.AbstractEventManager;

/**
 * @author Eyal Ben Ze'ev
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class PirteyCheshbonController extends AbstractEventManager {
	
	private PirteyCheshbon controlledPanel;

	/**
	 * Constructor for OpenHlViewController.
	 * @param aView
	 */
	public PirteyCheshbonController(PirteyCheshbon aPanel) {
		super(aPanel);
		controlledPanel = aPanel;
	}

	/**
	 * @see java.awt.event.ItemListener#itemStateChanged(ItemEvent)
	 */
	public void itemStateChanged(ItemEvent e) {
//		System.out.println("itemStateChanged:");
//		System.out.println("Source="+e.getSource());
		if (e.getSource() instanceof MatafComboBox) {
			MatafComboBox comboBox = (MatafComboBox) e.getSource();
			int index = comboBox.getSelectedIndex();
			Context ctx = comboBox.getDSECoordinatedPanel().getContext();
			if (comboBox.getName().equals("MaarechectComboBox")) {								
				try {
					String maarechetText = (String)ctx.getValueAt("marechetList."+index+".marechetText");
					ctx.setValueAt("chosenMarechetText",maarechetText);
					controlledPanel.getLabel5().setDataValue(ctx.getValueAt("chosenMarechetText"));
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			} else if (comboBox.getName().equals("SnifComboBox")) {								
				try {
					String snifText = (String)ctx.getValueAt("snifList."+index+".snifText");
					ctx.setValueAt("chosenSnifText",snifText);
					controlledPanel.getLabel7().setDataValue(ctx.getValueAt("chosenSnifText"));
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			} else if (comboBox.getName().equals("SugCheshbonHlComboBox")) {								
				try {
					String sugCheshbonHlText = (String)ctx.getValueAt("sugCheshbonHlList."+index+".sugCheshbonHlText");
					ctx.setValueAt("chosenSugCheshbonHlText",sugCheshbonHlText);
					controlledPanel.getLabel8().setDataValue(ctx.getValueAt("chosenSugCheshbonHlText"));
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			
		}

	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		System.out.println("actionPerformed:");
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
		System.out.println("propertyChange:");
	}

}
