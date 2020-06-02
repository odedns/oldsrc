package tests;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;

public class JComboBoxExt
	extends JComboBox
	implements JComboBox.KeySelectionManager {
		
	private String searchFor;
	private long lastKeyStroke;


	public JComboBoxExt(ComboBoxModel aModel) {
		super(aModel);
		lastKeyStroke = new java.util.Date().getTime();
		setKeySelectionManager(this);
		searchFor = "";
	}
	
	public int selectionForKey(char aKey, ComboBoxModel aModel) {
		long currentKeyStroke = new java.util.Date().getTime();
		if (lastKeyStroke + 500 < currentKeyStroke) {
			searchFor = "" + aKey;
		} else {
			searchFor += aKey;
		}
		lastKeyStroke = currentKeyStroke;
		String current;
		for (int i = 0; i < aModel.getSize(); i++) {
			current = aModel.getElementAt(i).toString().toLowerCase();
			if (current.startsWith(searchFor)) {
				return i;
			}
		}
		return -1;
	}
	
}

