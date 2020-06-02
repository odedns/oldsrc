package mataf.eventmanager;

import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.awt.event.ItemListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeListener;

import mataf.desktop.views.MatafClientView;
import mataf.types.MatafEmbeddedPanel;

/**
 * @author Eyal Ben Ze'ev
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public abstract class AbstractEventManager
	implements ItemListener, ActionListener, FocusListener, KeyListener, MouseListener, PropertyChangeListener{
		
		public AbstractEventManager() {
		}

		public AbstractEventManager(MatafClientView aView) {
		}
		
		public AbstractEventManager(MatafEmbeddedPanel aPanel) {
		}
}
