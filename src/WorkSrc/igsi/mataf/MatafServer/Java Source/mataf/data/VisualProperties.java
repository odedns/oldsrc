package mataf.data;

import java.awt.Color;

/**
 *
 * This interface defines the methods used by the VisualPropertiesExchanger to 
 * access and modify the visual bean properties as defined in the context.
 * 
 * @author Nati Dykstein. Creation Date : (07/09/2003 13:04:47).  
 */
public interface VisualProperties {
	
	public boolean 	isVisible();
	
	public boolean 	isEnabled();
	
	public Color 		getForeground();
	
	public boolean 	isInErrorFromServer();
	
	public boolean 	isRequired();
	
	public String 		getErrorMessage();
	
	/** 
	 * The ONLY method that does NOT correspond to 
	 * the getter/setter convention.
	 */
	public boolean shouldRequestFocus();

}
