package mataf.types.specific;

import mataf.types.MatafTableComboBoxButton;
import mataf.types.table.MatafTable;

/**
 * This class is a specific implementation of MatafTableComboBoxButton
 * that opens the banks list.
 * 
 * @author Nati Dykstein. Creation Date : (17/02/2004 11:20:41).  
 */
public class BanksTableComboBoxButton extends MatafTableComboBoxButton
{
	public BanksTableComboBoxButton()
	{
		MatafTable table = new MatafTable();
		table.setDataNameForList("banksList");
		table.getOurModel().addColumn(Integer.class,"מספר","bankData.GL_BANK", 50);
		table.getOurModel().addColumn(String.class,"שם בנק","bankData.GL_SHEM_BANK", 170);
	
		setTable(table);
	}
}
