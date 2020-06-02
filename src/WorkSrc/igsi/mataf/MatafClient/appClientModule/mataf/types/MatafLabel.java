package mataf.types;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.UIManager;

import mataf.exchangers.VisualPropertiesExchanger;
import mataf.utils.FontFactory;

import com.ibm.dse.base.Context;
import com.ibm.dse.gui.CoordinatedEventListener;
import com.ibm.dse.gui.CoordinatedEventMulticaster;
import com.ibm.dse.gui.DSECoordinationEvent;
import com.ibm.dse.gui.Settings;
import com.ibm.dse.gui.SpLabel;


/**
 * This Label has a support for the VisualProperties mechanism, and it also
 * allows the label to update the context upon changing.
 * (Standard SpLabel could be updated from the context but NOT vice versa)
 * 
 * @author Nati Dykstein
 */
public class MatafLabel extends SpLabel
							implements VisualPropertiesExchanger
{
	private static final Font FONT = FontFactory.getDefaultFont();
	
	private transient CoordinatedEventListener aCoordinatedEventListener=null;
	
	static
	{
		// Don't allow label to change its color when disabled.
		UIManager.put("Label.disabledForeground",Color.black);
	}
	
	
	public MatafLabel()
	{
		this("");
	}
	
	public MatafLabel(String text)
	{
		this(text, JLabel.RIGHT);
	}
	
	public MatafLabel(String text, int alignment)
	{
		super(text, alignment);
		setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		setDataDirection(Settings.OUTPUT_DIRECTION);
		setFont(FONT);
		setFocusable(false);
	}
	
	public void addCoordinatedEventListener(com.ibm.dse.gui.CoordinatedEventListener newListener) 
	{
		aCoordinatedEventListener = CoordinatedEventMulticaster.add(aCoordinatedEventListener, newListener);
	}
	
	public void removeCoordinatedEventListener(CoordinatedEventListener newListener) 
	{
		aCoordinatedEventListener = CoordinatedEventMulticaster.remove(aCoordinatedEventListener, newListener);
	}
	
	public void fireCoordinationEvent(DSECoordinationEvent event) 
	{	
		if (aCoordinatedEventListener == null) return;
		aCoordinatedEventListener.handleDSECoordinationEvent(event);
	}
	
	/**
	 * Notify the context about a change in the label.
	 */
	public void fireCoordinationEvent() 
	{
		DSECoordinationEvent newEvent = getNavigationParameters().generateCoordinationEvent(getType(),this);
		newEvent.setRefresh(true);
		newEvent.setEventName(getName()+".dataChanged");		
		fireCoordinationEvent(newEvent);
	}
	
	/**
	 * @see com.ibm.dse.gui.SpLabel#getDataValue()
	 */
	public Object getDataValue() 
	{
		return getText();
	}
	
	/** 
	 * Overriden to do nothing.
	 */
	/*public void setEnabled(boolean enabled)
	{
		// Do nothing to avoid change of the UI.
	}*/
	
	/**
	 * @see mataf.types.VisualPropertiesExchanger#exchangeVisualProperties()
	 */
	public void exchangeVisualProperties(Context ctx) 
	{		
		ADAPTER.exchangeVisualProperties(ctx, this);
	}

	/**
	 * @see mataf.types.VisualPropertiesExchanger#setErrorMessage(String)
	 */
	public void setErrorMessage(String message) 
	{/* Empty Implementation */ }

	/**
	 * @see mataf.types.VisualPropertiesExchanger#setInErrorFromServer(boolean)
	 */
	public void setInErrorFromServer(boolean errorFromServer) 
	{ /* Empty Implementation */ }

}
