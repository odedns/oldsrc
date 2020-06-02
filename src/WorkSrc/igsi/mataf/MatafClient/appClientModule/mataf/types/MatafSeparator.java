package mataf.types;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Font;

import javax.swing.JSeparator;

import mataf.utils.FontFactory;

/**
 * @author yossid
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class MatafSeparator extends JSeparator {
	
	private static final Color BG_COLOR = new Color(115, 154, 192);
//	private static final Color FG_COLOR = Color.white;
	private static final Color FG_COLOR = Color.red; // 4test
	private static final Font FONT = FontFactory.createFont("Arial", Font.PLAIN, 12);

	/**
	 * Constructor for MatafSeparator.
	 */
	public MatafSeparator() {
		super();
		initUI();
	}

	/**
	 * Init the component UI.
	 * Good mainly when creating an object not through the externalizer.
	 */
	private void initUI() {
		setForeground(FG_COLOR);
		setBackground(BG_COLOR);
		setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		super.setFont(FONT);
	}

}
