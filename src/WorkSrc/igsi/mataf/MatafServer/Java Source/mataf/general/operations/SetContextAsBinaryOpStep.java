package mataf.general.operations;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import mataf.utils.MatafUtilities;
import org.apache.axis.encoding.FieldTarget;

import com.ibm.dse.base.KeyedCollection;

/**
 * @author yossid
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class SetContextAsBinaryOpStep extends MatafOperationStep {
	
	public static final String CONTEXT_RECORD_TYPE = "C";
	
	/**
	 * @see mataf.general.operations.MatafOperationStep#executeOp()
	 */
	public int executeOp() throws Exception {
				
		try {
			KeyedCollection kcoll= getKeyedCollection();
			
			// Serialize to a byte array	
			ByteArrayOutputStream bos = new ByteArrayOutputStream() ;
        	ObjectOutput out = new ObjectOutputStream(bos) ;
			out.writeObject(kcoll);
			out.flush();
	        out.close();
	        	        
	        setValueAt("contextAsBinary", bos.toByteArray());
	        setValueAt("CZSJ_REC.GL_SUG_RESHUMA", CONTEXT_RECORD_TYPE);
		} catch (Exception e) {
			MatafUtilities.setErrorMessageInGLogger(this.getClass(), getContext(), e, null);
			addToErrorListFromXML();
			return RC_ERROR;
		}
		return RC_OK;
	}

}
