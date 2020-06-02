package mataf.exchangers;

import java.awt.Color;

import mataf.data.VisualProperties;
import mataf.logger.GLogger;
import mataf.utils.MatafGuiUtilities;

import com.ibm.dse.base.Context;
import com.ibm.dse.base.DSEObjectNotFoundException;

/**
 * An adapter class for this interface.
 * The adapter gives default implementation of the 
 * VisualPropertiesAdapter methods.
 * 
 * @see mataf.exchangers.VisualPropertiesExchanger
 * 
 * @author Nati Dykstein. Creation Date : (20/10/2003 13:23:17).  
 */
	
public class VisualPropertiesAdapter implements VisualPropertiesExchanger
{
	public void setVisible(boolean visible){}

	public void setEnabled(boolean enabled){}

	public void setForeground(Color foreground){}

	public void setInErrorFromServer(boolean errorFromServer){}
	
	public void setRequired(boolean mandatory) {}

	public void setErrorMessage(String message){}

	public void setDataName(String dataName){}

	public String getDataName(){return "EmptyDataName";}

	public void requestFocus(){}
		
	/**
	 * Default Implementaion of this method.
	 */
	public void exchangeVisualProperties(Context ctx)
	{
		throw new IllegalArgumentException("Do not invoke exchangeVisualProperties(context) from ADAPTER !\n" +
											"Invoke exchangeVisualProperties(context, this) instead.");
	}
	
	/**
	 * The Implementation for the ADAPTER reference.
	 */
	public void exchangeVisualProperties(Context ctx, VisualPropertiesExchanger vpe)
	{
		// Get the VisualDataField object.
		Object propertiesExchanger=null;
		String dataName = vpe.getDataName();
		try 
		{
			// Mimic refreshDataExchanger() null/empty check. 
			// (The dataName will be empty when trying to activate
			// a view while in the process of closing it.)
			
			if((dataName==null) || (dataName.equals("")))
				return;
				
			propertiesExchanger = ctx.getElementAt(vpe.getDataName());
		
			// Update component with visual properties from the context.
			MatafGuiUtilities.exchangeVisualPropertiesWith(
						vpe,
						(VisualProperties)propertiesExchanger);
		}			 
		catch (DSEObjectNotFoundException e) 
		{
			GLogger.warn("Element "+dataName+" not found in context "+ctx.getName());
			return;
		}
		catch(ClassCastException e)
		{
			//Exception e2 = new IllegalArgumentException("Failed to exchange Visual Properties , Not a VisualDataField Or Unexpected Class Type at DataName : "+vpe.getDataName());
			//e2.printStackTrace();
			GLogger.warn("Failed to exchange Visual Properties , Not a VisualDataField Or Unexpected Class Type at DataName : "+vpe.getDataName());
		}
	}
}
