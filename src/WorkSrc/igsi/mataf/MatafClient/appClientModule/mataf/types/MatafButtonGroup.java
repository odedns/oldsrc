package mataf.types;

import java.awt.Color;
import java.util.Enumeration;

import mataf.exchangers.VisualPropertiesExchanger;

import com.ibm.dse.base.Context;
import com.ibm.dse.gui.DSECoordinationEvent;
import com.ibm.dse.gui.SpButtonGroup;


/**
 * @author Nati Dykstein.
 *
 * Created : 18/09/2003. 15:45.
 */
public class MatafButtonGroup extends SpButtonGroup
									implements VisualPropertiesExchanger
{
	
	/**
	 * @see com.ibm.dse.gui.SpButtonGroup#fireCoordinationEvent(DSECoordinationEvent)
	 */
	public void fireCoordinationEvent(DSECoordinationEvent event) 
	{
		super.fireCoordinationEvent(event);
//		System.out.println("IN MatafButtonGroup "+getDataName()+" fireCoordinationEvent()");
	
		if((getDataName()==null) || getDataName().equals(""))
			return;
	}
	
	private MatafRadioButton getFirstButton()
	{
		return ((MatafRadioButton)(getElements().nextElement()));
	}
	
	/**
	 * Enables/Disables all the buttons in the group.
	 */
	public void setEnabled(boolean enabled)
	{
		for(Enumeration e = getElements();e.hasMoreElements();)
			((MatafRadioButton)e.nextElement()).setEnabled(enabled);
	}
	
	/**
	 * Empty implementaion to comply with the VisualPropertiesExchanger interface.
	 */
	public void setErrorMessage(String errorMessage)
	{}
	
	
	/**
	 * Sets the foreground of all the buttons in the group.
	 */
	public void setForeground(Color color)
	{
		for(Enumeration e = getElements();e.hasMoreElements();)
			((MatafRadioButton)e.nextElement()).setForeground(color);
	}
	
	/**
	 * @see mataf.data.VisualPropertiesExchanger#isVisible()
	 */
	public void setVisible(boolean visible) 
	{
		for(Enumeration e = getElements();e.hasMoreElements();)
			((MatafRadioButton)e.nextElement()).setVisible(visible);
	}
	
	/**
	 * Invokation of requestFocus() will result in requesting the focus
	 * on the first button in the buttongroup.
	 */
	public void requestFocus()
	{
		getFirstButton().requestFocus();
	}
	
	/**
	 * Empty implementation to comply with the VisualPropertiesExchanger interface.
	 */
	public void setInErrorFromServer(boolean inError) 
	{ /* Empty Implementation */ }	

	
	/**
	 * @see mataf.types.VisualPropertiesExchanger#exchangeVisualProperties()
	 */
	public void exchangeVisualProperties(Context ctx) 
	{
		ADAPTER.exchangeVisualProperties(ctx, this);
	}
	
	/**
	 * Prevent fireCoordinationEvent fired by SpButtonGroup.
	 */
	public void setRequired(boolean mandatory)
	{
		// TODO Auto-generated method stub
	}
}
