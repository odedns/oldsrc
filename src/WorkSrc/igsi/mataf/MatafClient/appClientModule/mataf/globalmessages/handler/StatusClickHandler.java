package mataf.globalmessages.handler;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import mataf.types.table.MatafTable;

/**
 * @author ronenk
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class StatusClickHandler extends MouseAdapter
{

	/**
	 * Constructor for StatusClickHandler.
	 */
	public StatusClickHandler()
	{
		super();
	}



	/**
	 * @see java.awt.event.MouseListener#mouseClicked(MouseEvent)
	 */
	public void mouseClicked(MouseEvent e)
	{
		super.mouseClicked(e);
		
		if(e.getClickCount()==2)
		{
			MatafTable mtTemp=((MatafTable)e.getComponent());
		
			if(mtTemp.getSelectedColumn()==1)
			{
				int iSelectedRow=mtTemp.getSelectedRow();
				if(iSelectedRow!=-1)
				{
					IMessageClickHandler imchHandler=(IMessageClickHandler)mtTemp.getModel().getValueAt(iSelectedRow,2);
					if(imchHandler!=null)
						imchHandler.handleClick(iSelectedRow);
				}
			}
			
			
		}		
	}

}
