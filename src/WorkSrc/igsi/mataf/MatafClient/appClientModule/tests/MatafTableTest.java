package tests;

import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import mataf.types.table.MatafTable;

/**
 * @author ronenk
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class MatafTableTest
{
	
	private JFrame frame;
	private JPanel mainPanel;

	protected MatafTable mtMessages=null;

	/**
	 * Constructor for MatafTableTest.
	 */
	public MatafTableTest()
	{
		super();
		frame = new JFrame("AutoCompletion Tester");
		
		mtMessages=new MatafTable();
		
		mtMessages.setColumnTypes(new Class[]{String.class,String.class});
		//mtMessages.setEditable(true);
		
		
		mtMessages.setDataNameAndColumns((((new com.ibm.dse.gui.VectorEditor(4)).setElemAt("globalMessagesWindow",0)
			).setElemAt(new com.ibm.dse.gui.ColumnFormatter("messageData.messageText","הודעה",null,false,false,false,200),1)
			).setElemAt(new com.ibm.dse.gui.ColumnFormatter("messageData.messageStatus","סטטוס",null,false,false,false,100),2)
		);

	
		mainPanel = new JPanel();
		mainPanel.add(mtMessages);
		
		frame.getContentPane().add(mainPanel);
		
	}

	public static void main(String[] args)
	{
		MatafTableTest tester = new MatafTableTest();
		tester.run();
	}
	
	public void run() 
	{
		frame.pack();
		frame.setVisible(true);
	}
}
