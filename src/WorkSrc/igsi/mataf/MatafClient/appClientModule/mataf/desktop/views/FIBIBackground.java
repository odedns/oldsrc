package mataf.desktop.views;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

import mataf.types.MatafEmbeddedPanel;
import mataf.utils.FontFactory;

/**
 * A Custom component used to display the FIBI background image, and 
 * on the bottom-right corner display the current version of the application.
 * 
 * @author Nati Dykstein. Creation Date : (20/04/2004 17:31:45).  
 */
public class FIBIBackground extends JComponent
{
	private static final Image 	DESKTOP_BG_IMAGE;
	private static final String 	DESKTOP_VERSION;


	static 
	{
		URL iconURL = ClassLoader.getSystemResource("images/BGRFIBI2.gif");
		DESKTOP_BG_IMAGE = new ImageIcon(iconURL).getImage();
		DESKTOP_VERSION = java.util.ResourceBundle.getBundle("sampleappl").getString("desktop_version");
	}
	/**
	 * 
	 */
	public FIBIBackground()
	{
		super();
		//setPreferredSize(MatafEmbeddedPanel.DEFAULT_TRANSACTION_SCREEN_SIZE);
		//setSize(MatafEmbeddedPanel.DEFAULT_TRANSACTION_SCREEN_SIZE);
	}
	
	public void paint(Graphics g)
	{
		g.setFont(FontFactory.createFont("Monospaced",Font.PLAIN,10));
		FontMetrics fm = g.getFontMetrics(g.getFont());
		
		int stringWidth = fm.stringWidth(DESKTOP_VERSION);
		
		int textOffset = getWidth() - (stringWidth+2);
		
		int imageX = 0;
		int imageY = 9;
		int imageWidth = getWidth();
		int imageHeight = getHeight() - imageY;
		
		// Draw the background image.
		g.drawImage(DESKTOP_BG_IMAGE, imageX, imageY, imageWidth, imageHeight, this);
		
		// Display the version.
		g.drawString(DESKTOP_VERSION, textOffset, imageHeight + imageY - 2);
	}

}
