package mataf.types;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Font;
import java.util.Collections;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import mataf.utils.FontFactory;

import com.ibm.dse.gui.SpTabbedPane;

/**
 * Adds blinking tabs functionality to the TabbedPane.
 * 
 * @author Nati Dykshtein. Created : 08/06/2003.
 *
 */
public class MatafTabbedPane extends SpTabbedPane 
								implements Runnable,ChangeListener
{
	private static final int		COLOR_OFFSET 	= 10;
	private static final int		BLINK_DELAY 	= 50;
	private static final int		FADE_STEPS		= 20;

	/** Used to blink the tabs. */
	private Thread 	blinker;
	/** The fading color.*/
	private Color		fadeColor = MatafEmbeddedPanel.BORDER_COLOR;
	/** Used to fade between the two colors. */
	private int 		steps;
	/** Keeps track of blinking tabs. */
	private SortedSet 	blinkingTabsIndices;
	
	//private CustomTabbedPaneUI tabbedPaneUI;

	static
	{
		//UIManager.put("TabbedPane.selected",Color.white);
		//UIManager.put("TabbedPane.background",new Color(230,240,255));
		//UIManager.put("TabbedPane.selectHighlight",new Color(200,220,255));
	}
	
	/**
	 * Sets up the default tabs color.
	 * Initializes the SortedSet.
	 * Mark the class as the ChangListener.
	 */
	public MatafTabbedPane()
	{
		setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		
		// Init our SortedSet as synchronized to enable the use of
		// iterator without dealing with ConcurrentModificationException.
		blinkingTabsIndices = Collections.synchronizedSortedSet(new TreeSet());
		
		// Needed for stopping the blinking upon selecting the tab.
		addChangeListener(this);
		
		setFocusable(false);
		
		setFont(FontFactory.createFont("Tahoma", Font.BOLD,10));
	}
	
	/**
	 * Make the tab with the specified index blink.
	 */
	public void blinkTab(int tabIndex)
	{
		if(tabIndex>=getTabCount())
			throw new IndexOutOfBoundsException("Cannot blink, tab number " +
												   tabIndex +
												   " does not exist!");

		// Add the newly blinking tab to the blinkingTabIndices trace.
		blinkingTabsIndices.add(new Integer(tabIndex));
		
		// Start thread if no tabs are blinking.
		if(blinker==null)
		{
			blinker = new Thread(this);
			blinker.start();
		}
	}
	
	/**
	 * Stop all tabs from blinking.
	 * This is achieved by stopping the thread and clearing the trace vector.
	 */
	public void stopAllBlinking()
	{
		blinker = null;
		blinkingTabsIndices.clear();
		setBackground(getBackground());
	}
	
	/**
	 * Stop a specific tag from blinking.
	 */
	public void stopBlinkingTab(int tabIndex)
	{
		setBackgroundAt(tabIndex, getBackground());
		blinkingTabsIndices.remove(new Integer(tabIndex));
		if(blinkingTabsIndices.size()==0)
			blinker = null;
	}
	
	/**
	 * Set all tabs in the vector to the specified color.
	 */
	private void blinkVectorWithColor(Color color)
	{
		for(Iterator i=blinkingTabsIndices.iterator();i.hasNext();)
			setBackgroundAt( ((Integer)i.next()).intValue() , color);
	}
	
	/**
	 * Returns a modified version of the color with the specified
	 * offset.
	 */
	private static Color getOffsetColor(Color c,int offset)
	{
		return	new Color(c.getRed(),
						  c.getGreen()+offset,
						  c.getBlue()-offset);
	}

	/**
	 * The Thread's run method which blinks all the tabs in the
	 * trace vector.
	 */
	public void run()
	{
		while(true)
		{
			if(blinker==null)
				break;
			
			// Fade the color in and out.			
			fadeColor = getOffsetColor(fadeColor, 
										(steps<FADE_STEPS/2) ? COLOR_OFFSET : 
																-COLOR_OFFSET);
			// Switch between colors.
			if(isShowing())
				blinkVectorWithColor(fadeColor);
			
			try
			{
				Thread.sleep(BLINK_DELAY);
			}
			catch(InterruptedException e) {}

			steps++;
			steps %= FADE_STEPS;
		}
	}
	
	
	/**
	 * Stop the blinking of the tab upon its selection.
	 */
	public void stateChanged(ChangeEvent e) 
	{
		int s = getSelectedIndex();
		if(blinkingTabsIndices.contains(new Integer(s)))
			stopBlinkingTab(getSelectedIndex());
	}

	
	public static void main(String[] args) throws Exception
	{
		UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
		JFrame f = new JFrame("Testing TabbedPane");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		
		MatafTabbedPane mtp = new MatafTabbedPane();
		mtp.setBackground(Color.white);
		mtp.setBorder(BorderFactory.createLineBorder(Color.gray));
		mtp.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		mtp.setSize(400,200);
		
		JPanel tab1 = new JPanel();
		tab1.setBackground(Color.white);
		mtp.add("מאפייני חשבון",tab1);
		JPanel tab2 = new JPanel();
		tab2.setBackground(Color.white);
		mtp.add("פרטי לקוח",tab2);
		JPanel tab3 = new JPanel();
		tab3.setBackground(Color.white);
		mtp.add("שווק דינמי",tab3);
		JPanel tab4 = new JPanel();
		tab4.setBackground(Color.white);
		mtp.add("פרטי מנהלי",tab4);
		
		f.getContentPane().add(mtp);
		
		f.setSize(640,480);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
		
		mtp.blinkTab(2);
	}
}
