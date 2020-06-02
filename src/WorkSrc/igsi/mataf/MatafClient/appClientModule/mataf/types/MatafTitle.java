package mataf.types;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import mataf.utils.FontFactory;

/**
 * Label that use as a header in the business screens.
 * Creation date: (8/10/2003 15:43:41)
 * @author: Nati Dykstein.
 */
public class MatafTitle extends MatafLabel
{
	private static final Color 		TEXT_COLOR 	= new Color(0x0C,0x78,0xCA);
	private static final Font  		FONT	 	= FontFactory.createFont("Arial",1,18);
	private static final Color		LINE_COLOR	= new Color(0x5D,0x74,0xAA);
	private static final int	 		LINE_WIDTH	= 2;
	private static final BasicStroke	BOLD_STROKE	= new BasicStroke(LINE_WIDTH);
	private static final String 		ALIGNMENT	= "    ";
	private static final int			WIDTH		= MatafEmbeddedPanel.DEFAULT_TRANSACTION_SCREEN_SIZE.width;
	private static final int			HEIGHT		= 25;
	
	private boolean underlined = true;

	/**
	 * PoalimTitleLabel constructor.
	 */
	public MatafTitle() {
		this("");
	}
	
	public MatafTitle(String text) {
		super(text);		
		setText(text);
		initialize();
	}
	
	/**
	 * Initialize the class.
	 */
	private void initialize() 
	{
		setBounds(0, 0, WIDTH, HEIGHT);
		setFont(FONT);		
		setForeground(TEXT_COLOR);
		setHorizontalAlignment(JLabel.RIGHT);
		setHorizontalTextPosition(SwingConstants.LEADING);
		setOpaque(false);
	}
	
	/**
	 * Draw a bold line under the the text.
	 */
	protected void paintBorder(Graphics g)
	{
		if(!underlined)
			return;
		Graphics2D g2 = (Graphics2D)g;
		Dimension size = getSize();
		
		g2.setColor(LINE_COLOR);
		g2.setStroke(BOLD_STROKE);
		g2.drawLine(10, size.height-LINE_WIDTH,
					size.width-10, size.height-LINE_WIDTH);
	}
	
	/**
	 * Overrides parent's setText to add the space alignmnet before
	 * the labels text, ONLY if no icon is present.
	 */
	public void setText(String text)
	{
		super.setText((getIcon() == null ? ALIGNMENT : "") + text);
	}
	
	/**
	 * @see javax.swing.JLabel#setIcon(Icon)
	 */
	public void setIcon(Icon icon) 
	{		
		super.setIcon(icon);		
		if(icon==null)
			return;
		// Update the text after adding the icon.
		// (with icon no alignment is used - removing all spaces)
		String t = getText();
		super.setText(t.substring(t.lastIndexOf('p')+1));
	}
	
	/**
	 * Returns true if the label draws an underline.
	 * @return boolean
	 */
	public boolean isUnderlined()
	{
		return underlined;
	}

	/**
	 * Sets the underlined state of the label.
	 * @param underlined The underlined to set
	 */
	public void setUnderlined(boolean underlined)
	{
		this.underlined = underlined;
	}
		
	public static void main(String[] args)
	{
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.getContentPane().setLayout(null);
		f.getContentPane().setBackground(Color.white);
		MatafTitle pt = new MatafTitle("בדיקת כותרת");
		f.getContentPane().add(pt);
		f.setSize(320,200);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}
	

}
