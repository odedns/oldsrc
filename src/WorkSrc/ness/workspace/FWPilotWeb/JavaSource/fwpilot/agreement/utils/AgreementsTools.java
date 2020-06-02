package fwpilot.agreement.utils;

import com.ness.fw.common.exceptions.*;
import com.ness.fw.common.resources.LocalizedResources;
import com.ness.fw.persistence.Page;
import com.ness.fw.ui.*;
import com.ness.fw.ui.MenuItem;
import com.ness.fw.ui.events.CustomEvent;


public class AgreementsTools 
{	
	public static Row createRow(Page page, LocalizedResources localizable) throws UIException, ResourceException 
	{	
		int id;
		String name;
		String type;
		String description;
		String status;
		
		Menu menu = new Menu();
	
		CustomEvent detailsLink = new CustomEvent("link1");
		MenuItem menuItem1 = new MenuItem(localizable.getString("agreementDetails"),detailsLink);


		CustomEvent copyLink = new CustomEvent("link2");
		MenuItem menuItem2 = new MenuItem(localizable.getString("copy"),copyLink);
		menu.addMenuItem(menuItem1);
		menu.addMenuItem(menuItem2);

		id = page.getInt("ID");
		name  = page.getString("NAME");
		status  = page.getString("STATUS");
		description  = page.getString("DESCRIPTION");
		type  = page.getString("TYPE");
		
		com.ness.fw.ui.Row UIRow = new com.ness.fw.ui.Row();
		Cell cell = new Cell(name);
		UIRow.addCell(cell);
		cell = new Cell(String.valueOf(id));
		UIRow.addCell(cell);
		cell = new Cell(status);
		UIRow.addCell(cell);
		cell = new Cell(type);
		UIRow.addCell(cell);
		cell = new Cell(description);
		UIRow.addCell(cell);
		
		UIRow.setMenu(menu);
				
		return UIRow;		

	}
}
