package mataf.ui;

import java.awt.Color;
import java.awt.FontMetrics;

import javax.swing.plaf.metal.MetalTabbedPaneUI;

	/**
	 * Class for the customized UI.<p>
	 * The tabs are rendered with a fixed width, and the selected
	 * tab's color can be set.
	 * 
	 */	
	public class CustomTabbedPaneUI extends MetalTabbedPaneUI 
	{
		private static final int FIXED_TAB_WIDTH = 120;
				
		private Color selectedBackgroundColor 	= new Color(20, 99, 164);
		private Color foregroundColor 			= Color.white;
		
		
		/** 
		 * Overrides BasicTabbedPaneUI's.<p>
		 * @return a fixed width in pixels.
		 * @see BasicTabbedPaneUI
		 * 
		 */
		protected int calculateTabWidth(int tabPlacement, int tabIndex, 
											FontMetrics metrics)
		{
			return FIXED_TAB_WIDTH;
		}
	}
