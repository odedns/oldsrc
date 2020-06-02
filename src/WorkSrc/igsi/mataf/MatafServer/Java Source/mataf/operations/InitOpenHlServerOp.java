package mataf.operations;

import java.util.Enumeration;
import java.util.ResourceBundle;

import com.ibm.dse.base.Context;
import com.ibm.dse.base.DSEInvalidRequestException;
import com.ibm.dse.base.DSEObjectNotFoundException;
import com.ibm.dse.base.DSEServerOperation;
import com.ibm.dse.base.DataElement;
import com.ibm.dse.base.FormatElement;
import com.ibm.dse.base.IndexedCollection;
import com.ibm.dse.base.KeyedCollection;

public class InitOpenHlServerOp extends DSEServerOperation {

	public InitOpenHlServerOp() {
		super();
	}

	public InitOpenHlServerOp(String anOperationName)
		throws java.io.IOException {
		super(anOperationName);
	}

	public InitOpenHlServerOp(String anOperationName, String aParentContext)
		throws
			java.io.IOException,
			DSEInvalidRequestException,
			DSEObjectNotFoundException {
		super(anOperationName, aParentContext);
	}

	public InitOpenHlServerOp(String anOperationName, Context aParentContext)
		throws java.io.IOException, DSEInvalidRequestException {
		super(anOperationName, aParentContext);
	}

	public void execute() throws Exception {

		// Build "Maarechet" table
		IndexedCollection maarachotIcoll = (IndexedCollection) getElementAt("marechetList");
		maarachotIcoll.removeAll();
		
		ResourceBundle resource = ResourceBundle.getBundle("Maarachot");
		String maarechetCode = null;
		String maarechetText = null;
		Enumeration enum = resource.getKeys();
		while (enum.hasMoreElements()) {
			maarechetCode = (String)enum.nextElement();
			maarechetText = resource.getString(maarechetCode);
			KeyedCollection kc =
				(KeyedCollection) DataElement.readObject("marechetData");
			kc.setValueAt("marechetCode", maarechetCode);
			kc.setValueAt("marechetText", maarechetText);
			maarachotIcoll.addElement(kc);
		}
		
		// Build "Sug Cheshbon" table
		IndexedCollection sugCheshbonIcoll = (IndexedCollection) getElementAt("sugCheshbonHlList");
		sugCheshbonIcoll.removeAll();
		
		ResourceBundle sugChesbonres = ResourceBundle.getBundle("sugCheshbonHL");
		String sugCheshbonHlCode = null;
		String sugCheshbonHlText = null;
		Enumeration enume = sugChesbonres.getKeys();
		while (enume.hasMoreElements()) {
			sugCheshbonHlCode = (String)enume.nextElement();
			sugCheshbonHlText = sugChesbonres.getString(sugCheshbonHlCode);
			KeyedCollection kc =
				(KeyedCollection) DataElement.readObject("sugCheshbonHlData");
			kc.setValueAt("sugCheshbonHlCode", sugCheshbonHlCode);
			kc.setValueAt("sugCheshbonHlText", sugCheshbonHlText);
			sugCheshbonIcoll.addElement(kc);
		}		
		
		setValueAt("misparHl","42");
		
//		To debug data sent to the client uncomment following lines:
//		String str = ((FormatElement) getCSReplyFormat()).format(this);
//		System.out.println("data to client:");
//		System.out.println(str);

	}

}
