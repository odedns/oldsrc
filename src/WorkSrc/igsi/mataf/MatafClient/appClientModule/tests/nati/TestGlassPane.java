/*
 * Created on 01/04/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package tests.nati;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * @author ronenk
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class TestGlassPane extends JFrame
{

	private javax.swing.JPanel jContentPane = null;

	/**
	 * This is the default constructor
	 */
	public TestGlassPane(String title)
	{
		super(title);
		initialize();
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize()
	{
		this.setSize(300, 200);
		this.setContentPane(getJContentPane());
		
		JComponent glassPane = (JComponent)getGlassPane();
		glassPane.setBackground(Color.pink);
		//glassPane.setOpaque(true);
		glassPane.setLayout(new BorderLayout());
		//JLabel testLabel = new JLabel("TESTING GLASS PANE");
		//testLabel.setBackground(Color.pink);
		//testLabel.setOpaque(true);
		//glassPane.add(testLabel, BorderLayout.CENTER);
		glassPane.setOpaque(false);
		glassPane.setVisible(true);
		glassPane.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				System.out.println("Clicked on Glass pane");
			}
		});
		getGlassPane().setVisible(true);
	}
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane()
	{
		if (jContentPane == null)
		{
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new java.awt.BorderLayout());
			jContentPane.add(new JButton("NORTH"), BorderLayout.NORTH);
			jContentPane.add(new JButton("CENTER"), BorderLayout.CENTER);
			jContentPane.add(new JButton("SOUTH"), BorderLayout.SOUTH);
		}
		return jContentPane;
	}
	
	public static void main(String[] args)
	{
		TestGlassPane tgp = new TestGlassPane("TestGlassPane");
		tgp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		tgp.pack();
		tgp.setVisible(true);
	}
}
