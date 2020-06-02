package mataf.types;

import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JSplitPane;
import javax.swing.KeyStroke;

import org.apache.xerces.validators.common.DFAContentModel;

import mataf.borders.RoundBorder;
import mataf.logger.GLogger;

import com.ibm.dse.gui.EmbeddedPanel;

/**
 * This class is the base class for all panels in the application.
 * It encapsulates parts of the framework logic.
 * 
 * @author Nati Dikshtein & Eyal Halperin.
 * Created : 25/02/2003 11:49:29y67
 */

public class MatafEmbeddedPanel extends EmbeddedPanel
	implements Cloneable
									
{
	private static int BORDER_ARC_DIAMETER = 20;
	public static Color BORDER_COLOR = new Color(94, 127, 172);
	public static Color BG_COLOR = new Color(255,255,255);

	/** Border type.*/
	private static final RoundBorder EMBEDDED_PANEL_BORDER = new RoundBorder(BORDER_COLOR, 1, BORDER_ARC_DIAMETER);

	/** Default size of business screens.*/
	public static final Dimension DEFAULT_TRANSACTION_SCREEN_SIZE = new Dimension(782, 450);
	
	/** Default bounds of business screens.*/
	public static final Rectangle DEFAULT_TRANSACTION_SCREEN_BOUNDS = new Rectangle(DEFAULT_TRANSACTION_SCREEN_SIZE);

	/** Holds whether this panel draws a background color.*/
	protected boolean backgroundPainted = true;

	/** Holds whether this panel draws a border.*/
	protected boolean borderPainted = true;

	/**
	 * Constructor for MatafEmbeddedPanel.
	 * @param arg0
	 */
	public MatafEmbeddedPanel(LayoutManager layout) {
		super(layout);
		initialize();
	}
	
	/**
	 * Constructor for MatafPanel.
	 */
	public MatafEmbeddedPanel() {
		super();
		initialize();
	}
	
	
	/**
	 * This method initializes this
	 */
	private void initialize() {
		setOpaque(false);
		setBorder(EMBEDDED_PANEL_BORDER);
		setBackground(BG_COLOR);
		setFocusCycleRoot(true);
		//setFocusable(false);
		setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		setPreferredSize(DEFAULT_TRANSACTION_SCREEN_SIZE);
		
		configureKeyBindings();
	}
	
	private void configureKeyBindings()
	{
		InputMap im = getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		im.put(KeyStroke.getKeyStroke("ESCAPE"), "dispatchToExitbutton");
		
		ActionMap am = getActionMap();
		
		am.put("dispatchToExitbutton", new AbstractAction()
		{
			public void actionPerformed(ActionEvent e)
			{
				activateCloseButton();
			}
		});
	}
	
	/**
	 * Searches all the component in the panel for the button
	 * that closes the transaction and activate it.
	 */
	public void activateCloseButton()
	{
		Component[] comps = getComponents();
		for(int i=0;i<comps.length;i++)
		{
			if(comps[i] instanceof MatafButton)
			{
				MatafButton button = (MatafButton)comps[i]; 
				if(button.isCloseButton())
				{
					button.fireCoordinationEvent();
					break;
				}
			}
		}
		GLogger.warn("Could not find a close button.Can not close panel.");
	}

	/**
	 * Paints the panel's border.
	 */
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;
		Dimension size = getSize();
		int borderWidth = EMBEDDED_PANEL_BORDER.getBorderWidth();

		if (backgroundPainted) {
			g2.setColor(getBackground());
			g2.fillRoundRect(
				borderWidth,
				borderWidth,
				(int) (size.getWidth()) - (2 * borderWidth),
				(int) (size.getHeight()) - (2 * borderWidth),
				BORDER_ARC_DIAMETER,
				BORDER_ARC_DIAMETER);
		}
	}

/*	private void createBindingForDefaultKeys() {
		getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "approve");

		getActionMap().put("approve", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("approve");
				approveAction();
			}
		});
	}*/

	/**
	 * Activates the logic of pressing the approve button.
	 * The logic will be determined in derived classes.
	 */
	protected void approveAction() {
		System.out.println("Approving the Action for Panel : " + this);
		/** To be overriden by child classes.*/
	}

	/**
	 * Returns the borderWidth.
	 * @return int
	 */
	public int getBorderWidth() {
		return EMBEDDED_PANEL_BORDER.getBorderWidth();
	}

	/**
	 * Sets the borderWidth.
	 * @param borderWidth The borderWidth to set
	 */
	public void setBorderWidth(int borderWidth) {
		EMBEDDED_PANEL_BORDER.setBorderWidth(borderWidth);
	}

	/**
	 * Returns the backgroundPainted.
	 * @return boolean
	 */
	public boolean isBackgroundPainted() {
		return backgroundPainted;
	}

	/**
	 * Sets the backgroundPainted.
	 * @param backgroundPainted The backgroundPainted to set
	 */
	public void setBackgroundPainted(boolean backgroundPainted) {
		this.backgroundPainted = backgroundPainted;
	}

	/**
	 * Returns the borderPainted.
	 * @return boolean
	 */
	public boolean isBorderPainted() {
		return borderPainted;
	}

	/**
	 * Sets the borderPainted.
	 * @param borderPainted The borderPainted to set
	 */
	public void setBorderPainted(boolean borderPainted) {
		this.borderPainted = borderPainted;
	}
	
	/**
	 * Adds the ability to register a SplitPane on the panel.
	 */
	protected void register(Component c) 
	{
		super.register(c);
		if(c instanceof JSplitPane)
		{
			JSplitPane split = (JSplitPane)c;
			register(split.getRightComponent());
			register(split.getLeftComponent());
		}
	}
	
	/**
	 * @see com.ibm.dse.gui.SpPanel#key(KeyEvent)
	 */
	public void key(KeyEvent e) 
	{
		// DO NOTHING ! (overrides sp behavior)
	}
	
	/**
	 * @see java.lang.Object#clone()
	 */
	public Object clone() {
		Object o = null;
		try {
			o= super.clone();
		} catch(CloneNotSupportedException ce) {
			o = null;	
		}
		return(o);
	}	

} //  @jve:visual-info  decl-index=0 visual-constraint="0,0"