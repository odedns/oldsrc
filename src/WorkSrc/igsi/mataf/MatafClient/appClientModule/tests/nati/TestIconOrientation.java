package tests.nati;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import mataf.borders.CustomizableLineBorder;

/**
 *
 * 
 * @author Nati Dykstein. Creation Date : (13/04/2004 16:19:15).  
 */
public class TestIconOrientation extends JPanel
{

	/**
	 * 
	 */
	public TestIconOrientation()
	{
		super(new FlowLayout());
		setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		JLabel testLabel = new JLabel("בדיקת אייקון");
		testLabel.setBorder(new CustomizableLineBorder(Color.red,1,0,0,0));
		testLabel.setIcon(new ImageIcon("appClientModule/images/Desktop/ikon_error.gif"));
		testLabel.setHorizontalTextPosition(SwingConstants.LEFT);
		setSize(400,300);
		add(testLabel, BorderLayout.CENTER);
	}

}
