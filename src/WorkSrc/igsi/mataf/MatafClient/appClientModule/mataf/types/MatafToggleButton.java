package mataf.types;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import mataf.exchangers.VisualPropertiesExchanger;
import mataf.utils.FontFactory;

import com.ibm.dse.base.Context;
import com.ibm.dse.gui.SpToggleButton;

/**
 *
 * 
 * @author Nati Dykstein. Creation Date : (19/01/2004 14:15:34).  
 */
public class MatafToggleButton extends SpToggleButton
									implements VisualPropertiesExchanger 
{
	private static final Color TEXT_COLOR;
	private static final Font FONT;
	public  static final Dimension DEFAULT_SIZE;
	
	private String dataName;
		
	static 
	{
		FONT = FontFactory.createFont("Arial", Font.BOLD, 14);
		TEXT_COLOR = Color.black; // Overrided by Visual Properties mechanism.
		DEFAULT_SIZE = new Dimension(100, 20);
	}
	
	/**
	 * Constructor for MatafToggleButton.
	 */
	public MatafToggleButton() 
	{
		super();
		setFont(FONT);
		setForeground(TEXT_COLOR);
		setRequestFocusEnabled(false);		
	}
	
	public void setDataName(String dataName)
	{
		this.dataName = dataName;
	}

	public String getDataName()
	{
		return dataName;
	}
	
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
	{ /* Empty Implementation */}

	/**
	 * @see mataf.types.VisualPropertiesExchanger#setInErrorFromServer(boolean)
	 */
	public void setInErrorFromServer(boolean errorFromServer)
	{ /* Empty Implementation */}

	/**
	 * @see mataf.exchangers.VisualPropertiesExchanger#setRequired(boolean)
	 */
	public void setRequired(boolean mandatory)
	{ /* Empty Implementation */}

}
