package mataf.exchangers;

import java.awt.Color;

import com.ibm.dse.base.Context;

/**
 *
 * This interface defines the methods that are invoked when exchanging visual
 * properties.
 * This mechanism is used to process and implement the visual hints sent from the 
 * server to the client.
 * 
 * ALWAYS KEEP THIS METHOD UP-TO-DATE WITH THE GETTER METHODS IN 
 * THE INTERFACE VisualProperties !.
 * 
 * @see mataf.data.VisualProperties
 * @see mataf.utils.MatafUtilities.exchangeVisualPropertiesWith(VisualPropertiesExchanger , VisualProperties)
 * 
 * @author Nati Dykstein. Creation Date : (18/09/2003 16:37:08).  
 */
public interface VisualPropertiesExchanger 
{
	/**
	 * An adapter class for this interface.
	 * It's main purpose is to give access to the default implementation of
	 * the exchangeVisualProperties() method.
	 */
	public final VisualPropertiesAdapter ADAPTER = new VisualPropertiesAdapter();
	
	public void setVisible(boolean visible);
	
	public void setEnabled(boolean enabled);
	
	public void setForeground(Color foreground);
	
	public void setInErrorFromServer(boolean errorFromServer);
	
	public void setRequired(boolean mandatory);
	
	public void setErrorMessage(String message);
	
	
	/**
	 * Visual Properties Exchangers rely on a dataName in the context.
	 */
	public void setDataName(String dataName);
	
	public String getDataName();
	
	
	
	/** 
	 * The ONLY method that does NOT correspond to 
	 * the getter/setter convention.
	 */
	public void requestFocus();
	
	/**
	 * The main method that should be implemented for exchanging
	 * visual properties.
	 * This method should always invoke the MatafUtils.exchangeVisualPropertiesWith()
	 * or it can use the default implmentaion in the Adapter class.(@see VisualPropertiesAdapter)
	 */
	public void exchangeVisualProperties(Context ctx);
}	
