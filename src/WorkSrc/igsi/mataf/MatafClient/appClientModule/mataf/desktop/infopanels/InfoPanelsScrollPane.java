package mataf.desktop.infopanels;

import java.awt.Component;
import java.awt.ComponentOrientation;

import mataf.types.MatafScrollPane;

/**
 *
 * 
 * @author Nati Dykstein. Creation Date : (12/05/2004 16:07:46).  
 */
public class InfoPanelsScrollPane extends MatafScrollPane
										implements InfoPanelsConstants
{

	/**
	 * @param view
	 * @param vsbPolicy
	 * @param hsbPolicy
	 */
	public InfoPanelsScrollPane(Component view, int vsbPolicy, int hsbPolicy)
	{
		super(view, vsbPolicy, hsbPolicy);
		initComponent();
	}

	/**
	 * @param view
	 */
	public InfoPanelsScrollPane(Component view)
	{
		super(view);
		initComponent();
	}

	/**
	 * @param vsbPolicy
	 * @param hsbPolicy
	 */
	public InfoPanelsScrollPane(int vsbPolicy, int hsbPolicy)
	{
		super(vsbPolicy, hsbPolicy);
		initComponent();
	}

	/**
	 * 
	 */
	public InfoPanelsScrollPane()
	{
		super();
		initComponent();
	}
	
	private void initComponent()
	{
		setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
		setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS);
		setBackground(PANEL_BG_COLOR);
		getVerticalScrollBar().setBackground(PANEL_BG_COLOR);
	}

}
