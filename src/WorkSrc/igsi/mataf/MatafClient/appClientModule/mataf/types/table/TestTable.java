package mataf.types.table;

import java.awt.FlowLayout;

import javax.swing.InputMap;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;

import mataf.types.MatafEmbeddedPanel;
import mataf.types.MatafTextField;
import mataf.types.textfields.MatafNumericField;

import com.ibm.dse.base.Context;
import com.ibm.dse.base.Settings;

/**
 *
 * 
 * @author Nati Dykstein. Creation Date : (21/10/2003 12:41:14).  
 */
public class TestTable extends MatafEmbeddedPanel {

	/**
	 * Constructor for TestTable.
	 */
	
	JTable t1;
	MatafTable t2;
	
	public TestTable() throws Exception
	{
		super(new FlowLayout());
		
		Context.reset();
		Settings.reset("http://127.0.0.1:80/MatafServer/dse/dse.ini");
		Settings.initializeExternalizers(Settings.MEMORY);
		
		
		t1 = new JTable(new String[][]{{"Test1","Test2","Test3"},
										{"Test4","Test5","Test6"}},
							  new String[]{"Col1","Col2","Col3"});
		
		
		
		t1.setSurrendersFocusOnKeystroke(true);
		add(new JScrollPane(t1));
		attachEditors(t1);

//		debugKeyBindings(t1);
		
		t2 = new MatafTable();
		

		
		t2.setEditable(true);
		t2.setDataNameForList("ICTestTable");
		t2.getOurModel().addColumn(Long.class, "מספר הלוואה", "LoanRecord.loanNumber");
		t2.getOurModel().addColumn(Double.class, "סכום", "LoanRecord.loanAmount");
		add(new JScrollPane(t2));

		//debugKeyBindings(t2);
		
		setSize(640,480);
	}
	
	private void attachEditors(JTable table)
	{
		MatafNumericField tf = new MatafNumericField();
		table.getColumnModel().getColumn(0).setCellEditor(new MatafTableCellEditor(tf, t1));
	}
	
	private void debugKeyBindings(JTable table)
	{
		InputMap iMap = table.getInputMap();
		KeyStroke[] keyStrokes = iMap.allKeys();		
		System.out.println("Found "+keyStrokes.length+" key strokes :");
		for(int i=0;i<keyStrokes.length;i++)
		{
			System.out.println(i+"."+keyStrokes[i]);
		}
	}
}
