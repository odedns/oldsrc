package mataf.operations;

import com.ibm.dse.base.DSEServerOperation;
import com.ibm.dse.base.FormatElement;
import com.ibm.dse.base.KeyedCollection;

/**
 * @author Eyal Ben Ze'ev
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class TestIcollServerOp extends DSEServerOperation {
	/**
	 * @see com.ibm.dse.base.DSEServerOperation#execute()
	 */
	public void execute() throws Exception {
		System.out.println("In server Op");
		for (int i = 0; i < 7; i++) {
			KeyedCollection kColl = (KeyedCollection) getElementAt("myTableModel." + i);
			kColl.setValueAt("data1", "S " + String.valueOf(i));
			kColl.setValueAt("data2", "S " + String.valueOf(i));
			kColl.setValueAt("data3", "S " + String.valueOf(i));
			kColl.setValueAt("data4", "S " + String.valueOf(i));
			kColl.setValueAt("data5", "S " + String.valueOf(i));
			kColl.setValueAt("data6", "S " + String.valueOf(i));
			kColl.setValueAt("data7", "S " + String.valueOf(i));
		}
		FormatElement fe = getCSReplyFormat();
		String str = fe.format(getContext());
	}

}
