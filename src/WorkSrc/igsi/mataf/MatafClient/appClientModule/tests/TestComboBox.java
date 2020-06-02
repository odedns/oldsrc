package tests;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import com.ibm.dse.gui.SpComboBox;
import com.ibm.dse.gui.SpPanel;

/**
 *
 * 
 * @author Nati Created : 02/06/2003  
 */
public class TestComboBox extends SpPanel
{

	private SpComboBox combo;
	
	public TestComboBox()
	{
		setLayout(new BorderLayout());
		combo = new SpComboBox();
		combo.setDataNameForList("comboList");
		combo.setUseKeyValues(true);
		combo.setDataName("selected");
		combo.setValueInContext("value");
	}

	public static void main(String[] args) 
	{
		JFrame f = new JFrame("Testing ComboBox");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.getContentPane().add(new TestComboBox());		
		f.setSize(640,480);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}
}
