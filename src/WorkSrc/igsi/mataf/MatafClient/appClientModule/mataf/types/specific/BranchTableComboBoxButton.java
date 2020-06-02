package mataf.types.specific;

import mataf.types.MatafTableComboBoxButton;
import mataf.types.table.MatafTable;

/**
 * This class is a specific implementation of MatafTableComboBoxButton
 * that opens the Snif list.
 * This button can display 4 types of tables :
 * - An Empty Table.<p>
 * - List of branches of the Benleumi bank.<p>
 * - List of branches of the PAGI bank.<p>
 * - Complete list of all branches.
 * 
 * 
 * @author Nati Dykstein. Creation Date : (05/02/2004 15:27:01).  
 */
public class BranchTableComboBoxButton extends MatafTableComboBoxButton
{
	public static final int NONE		 		= 0;
	public static final int BENLEUMI 		= 1;
	public static final int PAGI 			= 2;
	public static final int ALL_BRANCHES 	= 3;
	
	private static final MatafTable EMPTY_TABLE;
	private static final MatafTable BENLEUMI_TABLE;
	private static final MatafTable PAGI_TABLE;
	private static final MatafTable ALL_BRANCHES_TABLE;
	
	private static final String	EMPTY_TABLE_DATANAME 	= "emptyBranchTable";
	private static final String	BENLEUMI_TABLE_DATANAME 	= "snifListBenleumi";
	private static final String	PAGI_TABLE_DATANAME 		= "snifListPagi";
	private static final String	ALL_BRANCHES_TABLE_DATANAME = "snifList";
	
	private int branchesType = BENLEUMI;
	
	private MatafTable currentTable;
	
	static
	{
		EMPTY_TABLE = new MatafTable();
		EMPTY_TABLE.setDataNameForList(EMPTY_TABLE_DATANAME);
		EMPTY_TABLE.getOurModel().addColumn(Integer.class,"מספר","snifData.GL_SNIF", 50);
		EMPTY_TABLE.getOurModel().addColumn(String.class,"שם סניף","snifData.GL_SHEM_SNIF", 150);		
		
		BENLEUMI_TABLE = new MatafTable();
		BENLEUMI_TABLE.setDataNameForList(BENLEUMI_TABLE_DATANAME);
		BENLEUMI_TABLE.getOurModel().addColumn(Integer.class,"מספר","snifData.GL_SNIF", 50);
		BENLEUMI_TABLE.getOurModel().addColumn(String.class,"שם סניף","snifData.GL_SHEM_SNIF", 150);		
		
		PAGI_TABLE = new MatafTable();
		PAGI_TABLE.setDataNameForList(PAGI_TABLE_DATANAME);
		PAGI_TABLE.getOurModel().addColumn(Integer.class,"מספר","snifData.GL_SNIF", 50);
		PAGI_TABLE.getOurModel().addColumn(String.class,"שם סניף","snifData.GL_SHEM_SNIF", 150);
		
		ALL_BRANCHES_TABLE = new MatafTable();
		ALL_BRANCHES_TABLE.setDataNameForList(ALL_BRANCHES_TABLE_DATANAME);
		ALL_BRANCHES_TABLE.getOurModel().addColumn(Integer.class,"מספר","snifData.GL_SNIF", 50);
		ALL_BRANCHES_TABLE.getOurModel().addColumn(String.class,"שם סניף","snifData.GL_SHEM_SNIF", 150);
	}
	
	public BranchTableComboBoxButton()
	{
		this(BENLEUMI);
	}
	
	public BranchTableComboBoxButton(int branchesType)
	{
		this.branchesType = branchesType;
		
		updateTable();
	}
	
	/**
	 * Update the current table to reference the right table.
	 */
	private void updateTable()
	{
		switch(branchesType)
		{
			case NONE		: currentTable = EMPTY_TABLE;break;
			case BENLEUMI 	: currentTable = BENLEUMI_TABLE; break;
			case PAGI		: currentTable = PAGI_TABLE;break;
			default		: currentTable = ALL_BRANCHES_TABLE;
		}
		
		setTable(currentTable);
	}
	
	/**
	 * @return branchType the type of branches currently displayed
	 * 			by this button.
	 */
	public int getBranchesType()
	{
		return branchesType;
	}

	/**
	 * @param branchesType
	 */
	public void setBranchesType(int branchesType)
	{
		if(this.branchesType!=branchesType)
		{
			this.branchesType = branchesType;
			updateTable();
		}
		
	}

}
