package mataf.desktop.beans;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 *
 * 
 * @author Nati Dykstein. Creation Date : (17/05/2004 16:51:33).  
 */
public class PersonalMenuPopup extends JPopupMenu
{
	private JMenuItem item;
	/**
	 * 
	 */
	public PersonalMenuPopup()
	{
		super();
		item = new JMenuItem();
		item.setText("הוסף לתפריט אישי");
		add(item);
	}

	/**
	 * @param label
	 */
	public PersonalMenuPopup(String label)
	{
		super(label);
	}

}
