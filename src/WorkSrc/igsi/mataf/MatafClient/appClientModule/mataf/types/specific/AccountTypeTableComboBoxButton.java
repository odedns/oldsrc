package mataf.types.specific;

import mataf.types.MatafTableComboBoxButton;
import mataf.types.table.MatafTable;

/**
 * This class is a specific implementation of MatafTableComboBoxButton
 * that opens the Sug Chesbon list.
 * 
 * @author Nati Dykstein. Creation Date : (05/02/2004 15:37:41).  
 */
public class AccountTypeTableComboBoxButton extends MatafTableComboBoxButton
{
	public AccountTypeTableComboBoxButton()
	{
		MatafTable table = new MatafTable();
		table.setDataNameForList("accountTypesList");
		table.getOurModel().addColumn(Integer.class,"מספר","accountTypeData.GL_SCH", 50);
		table.getOurModel().addColumn(String.class,"סוג חשבון","accountTypeData.GL_SHEM_SCH", 170);
	
		setTable(table);
	}
}
