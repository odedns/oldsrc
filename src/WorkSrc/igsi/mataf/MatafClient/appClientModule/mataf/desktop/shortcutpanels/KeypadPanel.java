/*
 * Created on 23/05/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package mataf.desktop.shortcutpanels;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * @author eyalbz
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class KeypadPanel extends JPanel {

	/**
	 * 
	 */
	public KeypadPanel() {
		super();
		initUI();
	}

	/**
	 * @param isDoubleBuffered
	 */
	public KeypadPanel(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initUI();

	}

	/**
	 * @param layout
	 */
	public KeypadPanel(LayoutManager layout) {
		super(layout);
		initUI();

	}

	/**
	 * @param layout
	 * @param isDoubleBuffered
	 */
	public KeypadPanel(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		initUI();

	}

	/**
	* 
	*/
	private void initUI() {
		setSize(53, 70);
		setMinimumSize(new Dimension(53,70));
		GridBagLayout gridBagLayout = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		Insets insets = new Insets(2,2,2,2);
		constraints.insets = insets;
		
		setLayout(gridBagLayout);

		KeypadButton button = new KeypadButton("7");
		constraints.gridx = 0;
		constraints.gridy = 0;		
		gridBagLayout.setConstraints(button, constraints);
		this.add(button);

		button = new KeypadButton("8");
		constraints.gridx = 1;
		constraints.gridy = 0;
		gridBagLayout.setConstraints(button, constraints);
		this.add(button);

		button = new KeypadButton("9");
		constraints.gridx = 2;
		constraints.gridy = 0;
		gridBagLayout.setConstraints(button, constraints);
		this.add(button);

		button = new KeypadButton("4");
		constraints.gridx = 0;
		constraints.gridy = 1;
		gridBagLayout.setConstraints(button, constraints);
		this.add(button);

		button = new KeypadButton("5");
		constraints.gridx = 1;
		constraints.gridy = 1;
		gridBagLayout.setConstraints(button, constraints);
		this.add(button);

		button = new KeypadButton("6");
		constraints.gridx = 2;
		constraints.gridy = 1;
		gridBagLayout.setConstraints(button, constraints);
		this.add(button);

		button = new KeypadButton("1");
		constraints.gridx = 0;
		constraints.gridy = 2;
		gridBagLayout.setConstraints(button, constraints);
		this.add(button);

		button = new KeypadButton("2");
		constraints.gridx = 1;
		constraints.gridy = 2;
		gridBagLayout.setConstraints(button, constraints);
		this.add(button);

		button = new KeypadButton("3");
		constraints.gridx = 2;
		constraints.gridy = 2;
		gridBagLayout.setConstraints(button, constraints);
		this.add(button);

		button = new KeypadButton("0");
		constraints.gridx = 0;
		constraints.gridy = 3;
		constraints.gridwidth = 2;
		constraints.anchor = GridBagConstraints.WEST;
		gridBagLayout.setConstraints(button, constraints);
		this.add(button);
		
		updateUI();
		validate();
	}

}
