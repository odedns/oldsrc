package mataf.base;

import mataf.data.VisualDataField;

import com.ibm.dse.base.DSEException;
import com.ibm.dse.base.DSEInvalidRequestException;
import com.ibm.dse.base.DataElement;
import com.ibm.dse.base.DataField;
import com.ibm.dse.base.IndexedCollection;
import com.ibm.dse.base.KeyedCollection;
import com.ibm.dse.base.types.ElementState;

/**
 * @author Eyal Ben Ze'ev
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class MatafIndexedCollection extends IndexedCollection {

	/**
	* Creates a new DataElement that shares the same descriptor than the DataElement passed.
	* @param sourceDataElement 
	* @return com.ibm.dse.base.DataElement - The new DataElement
	* @exception DSEInvalidRequestException 
	*/
	public DataElement createElementSharingDescriptors(DataElement sourceDataElement) throws DSEInvalidRequestException {

		DataElement newDataElement = null;
		try {
			if (sourceDataElement instanceof VisualDataField) {
				DataField newDataField = new VisualDataField();
				newDataField.setValue(null);
				newDataElement = newDataField;
			} else if (sourceDataElement instanceof DataField) {
				DataField newDataField = new DataField();
				newDataField.setValue(null);
				newDataElement = newDataField;
			} else {
				if (sourceDataElement instanceof MatafIndexedCollection) {
					MatafIndexedCollection iColl = (MatafIndexedCollection) sourceDataElement;
					MatafIndexedCollection newIcoll = new MatafIndexedCollection();
					newIcoll.removeAll();
					newIcoll.setDataElement(iColl.getDataElement());
					newIcoll.setElementSubTag(iColl.getElementSubTag());
					newDataElement = newIcoll;
				} else if (sourceDataElement instanceof IndexedCollection) {
					IndexedCollection iColl = (IndexedCollection) sourceDataElement;
					IndexedCollection newIcoll = new IndexedCollection();
					newIcoll.removeAll();
					newIcoll.setDataElement(iColl.getDataElement());
					newIcoll.setElementSubTag(iColl.getElementSubTag());
					newDataElement = newIcoll;
				} else {
					if (sourceDataElement instanceof KeyedCollection) {
						KeyedCollection newKColl = new KeyedCollection();
						KeyedCollection sourceKColl = (KeyedCollection) sourceDataElement;
						int size = sourceKColl.size();
						for (int i = 0; i < size; i++) {
							newKColl.addElement(createElementSharingDescriptors(sourceKColl.getElementAt(i)));
						}
						newDataElement = newKColl;
					} else {
						throw new Exception("Unexpected data element type");
					}
				}
			}
		} catch (Throwable t) {
			throw new DSEInvalidRequestException(
				DSEException.harmless,
				this.getName(),
				"Exception instantiating indexedCollection elements. IColl name : " + getName() + ". Exception catched : " + t.getMessage());
		}

		newDataElement.setDescription(sourceDataElement.getDescription());
		newDataElement.setDescriptor(sourceDataElement.getDescriptor());
		newDataElement.setName(sourceDataElement.getName());
		if (sourceDataElement.getState() != null)
			newDataElement.setState((ElementState) sourceDataElement.getState().clone());
		return newDataElement;

	}
}
