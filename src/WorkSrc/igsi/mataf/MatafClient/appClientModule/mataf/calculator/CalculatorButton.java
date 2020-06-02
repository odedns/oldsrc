package mataf.calculator;

import java.awt.Font;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.border.BevelBorder;



/**
 * The button used within the calculator.
 * 
 * @author Nati Dykstein. Creation Date : (03/03/2004 15:16:39).  
 */
public class CalculatorButton extends JButton
{
	private static final Font FONT = new Font("Tahoma",Font.BOLD, 14);

	/**
	 * 
	 */
	public CalculatorButton()
	{
		super();
		init();	
	}

	/**
	 * @param text
	 */
	public CalculatorButton(String text)
	{
		super(text);
		init();		
	}

	/**
	 * @param a
	 */
	public CalculatorButton(Action a)
	{
		super(a);		
	}

	/**
	 * @param icon
	 */
	public CalculatorButton(Icon icon)
	{
		super(icon);		
	}

	/**
	 * @param text
	 * @param icon
	 */
	public CalculatorButton(String text, Icon icon)
	{
		super(text, icon);
	}
	
	private void init()
	{
		setFocusable(false);
		setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		setFont(FONT);
	}
}
