package mataf.srika.panels;

import java.awt.LayoutManager;

import mataf.types.MatafButton;
import mataf.types.MatafEmbeddedPanel;

/**
 * This panel displays the scanning results of T491 in a table.
 * 
 * @author Nati Dykstein. Creation Date : (12/02/2004 15:26:28).  
 */
public class SrikaResultsSummaryPanel extends MatafEmbeddedPanel
{
	private mataf.types.MatafTitle matafTitle = null;
	private mataf.types.MatafScrollPane matafScrollPane = null;
	private mataf.types.table.MatafTable matafTable = null;
	private mataf.types.MatafButton matafButton = null;
	private mataf.types.MatafButton matafButton1 = null;
	private mataf.types.MatafButton testButton = null;
	/**
	 * 
	 */
	public SrikaResultsSummaryPanel()
	{
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
        this.add(getMatafTitle(), null);
        this.add(getMatafScrollPane(), null);
        this.add(getMatafButton(), null);
        this.add(getMatafButton1(), null);
        this.add(getTestButton(), null);
        this.setBounds(0, 0, 780, 450);
			
	}
	/**
	 * This method initializes matafTitle
	 * 
	 * @return mataf.types.MatafTitle
	 */
	private mataf.types.MatafTitle getMatafTitle() {
		if(matafTitle == null) {
			matafTitle = new mataf.types.MatafTitle();
			matafTitle.setText("סריקת סליקה אלקטרונית - תוצאות הסריקה");
		}
		return matafTitle;
	}
	/**
	 * This method initializes matafScrollPane
	 * 
	 * @return mataf.types.MatafScrollPane
	 */
	private mataf.types.MatafScrollPane getMatafScrollPane() {
		if(matafScrollPane == null) {
			matafScrollPane = new mataf.types.MatafScrollPane();
			matafScrollPane.setViewportView(getMatafTable());
			matafScrollPane.setBounds(30, 55, 720, 356);
		}
		return matafScrollPane;
	}
	/**
	 * This method initializes matafTable
	 * 
	 * @return mataf.types.table.MatafTable
	 */
	private mataf.types.table.MatafTable getMatafTable() {
		if(matafTable == null) {
			matafTable = new mataf.types.table.MatafTable();
			matafTable.setDataNameForList("");
			matafTable.getOurModel().addColumn(String.class,"יום","");
			matafTable.getOurModel().addColumn(String.class,"שעה","");
			matafTable.getOurModel().addColumn(String.class,"מס זיהוי","");
			matafTable.getOurModel().addColumn(String.class,"מס תחנה","");
			matafTable.getOurModel().addColumn(String.class,"פעולה","");
			matafTable.getOurModel().addColumn(String.class,"חשבון","");
			matafTable.getOurModel().addColumn(String.class,"סוג חשבון","");
			matafTable.getOurModel().addColumn(String.class,"סניף","");
			matafTable.getOurModel().addColumn(String.class,"כמות המחאות","");
			matafTable.getOurModel().addColumn(String.class,"סה\"כ סכום","");
			
		}
		return matafTable;
	}
	/**
	 * This method initializes matafButton
	 * 
	 * @return mataf.types.MatafButton
	 */
	private mataf.types.MatafButton getMatafButton() {
		if(matafButton == null) {
			matafButton = new mataf.types.MatafButton();
			matafButton.setBounds(30, 425, 50, 20);
			matafButton.setType(MatafButton.CLOSE_VIEW);
			matafButton.setText("צא");
		}
		return matafButton;
	}
	/**
	 * This method initializes matafButton1
	 * 
	 * @return mataf.types.MatafButton
	 */
	private mataf.types.MatafButton getMatafButton1() {
		if(matafButton1 == null) {
			matafButton1 = new mataf.types.MatafButton();
			matafButton1.setBounds(90, 425, 130, 20);
			matafButton1.setText("המשך סריקה >>");
		}
		return matafButton1;
	}
	/**
	 * This method initializes testButton
	 * 
	 * @return mataf.types.MatafButton
	 */
	private mataf.types.MatafButton getTestButton() {
		if(testButton == null) {
			testButton = new mataf.types.MatafButton();
			testButton.setBounds(286, 425, 108, 20);
			testButton.setText("פתח סריקה");
			testButton.setType(MatafButton.NEXT_VIEW);
			testButton.setNavigationParameters(new com.ibm.dse.gui.NavigationParameters("activeView",2,"",0,"srikaNavigationView","mataf.srika.views.SrikaNavigationView","","","","",0,0,0,0,false,false));
		}
		return testButton;
	}
}
