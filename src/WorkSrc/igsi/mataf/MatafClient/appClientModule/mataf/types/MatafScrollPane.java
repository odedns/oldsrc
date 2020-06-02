package mataf.types;

import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;

import javax.swing.JScrollPane;
import javax.swing.JViewport;

import mataf.exchangers.VisualPropertiesExchanger;

import com.ibm.dse.base.Context;



/**
 * A Scrollpane customized for the application.
 * 
 * @author Nati Dykshtein.
 *
 */
public class MatafScrollPane extends JScrollPane 
								implements VisualPropertiesExchanger
{
	private static final Color scrollBarBackground = new Color(203,223,250) ;
	//private static final Color BG_COLOR = new Color(203,223,250);
	private static final Color headerBackground = new Color(204,224,242) ;
	
	/** MatafScrollPane Border - Must be Like the table grid Color  */
	//private static final Color borderColor = new Color(0x53,0x7F,0xB1) ;
	
	int scrollPaneHeight ;
	
	private String dataName;

 	/**
	 * Constructor for MatafScrollPane.
	 * @param view
	 * @param vsbPolicy
	 * @param hsbPolicy
	 */
	public MatafScrollPane(Component view, int vsbPolicy, int hsbPolicy) {
		super(view, vsbPolicy, hsbPolicy);
		initialize() ;
	}

	/**
	 * Constructor for PoalimScrollPane.
	 * @param view
	 */
	public MatafScrollPane(Component view) {
		super(view);
		initialize() ;

	}

	/**
	 * Constructor for PoalimScrollPane.
	 * @param vsbPolicy
	 * @param hsbPolicy
	 */
	public MatafScrollPane(int vsbPolicy, int hsbPolicy) {
		super(vsbPolicy, hsbPolicy);
		initialize() ;

	}

	/**
	 * Constructor for PoalimScrollPane.
	 */
	public MatafScrollPane() {
		super();
		initialize() ;

	}

	public void initialize()
	{
		//getViewport().setBackground(Color.white);
		//setBorder(BorderFactory.createLineBorder(borderColor,1));
		//setViewportBorder(null);
		setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

		JViewport columnHeader = getColumnHeader();
		if(columnHeader!=null)
			columnHeader.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		this.getVerticalScrollBar().setBackground(scrollBarBackground) ;
		this.getVerticalScrollBar().setForeground(scrollBarBackground) ;
		this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED) ;
		this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED) ;
		setBackground(headerBackground);
	}




/*	private int  getPoalimScrollPaneHeight(int height) {
		
		 int checkHeight = height / PoalimTable.getTableRowHeight() ;
		return checkHeight*PoalimTable.getTableRowHeight() ;		
	}*/

	/**
	 * @see java.awt.Component#setBounds(int, int, int, int)
	 */
/*	public void setBounds(int x, int y, int width, int height) {
		
		super.setBounds(x, y, width, getPoalimScrollPaneHeight(height)+2);
	}*/
	
	/**
	 * @see mataf.types.VisualPropertiesExchanger#exchangeVisualProperties()
	 */
	public void exchangeVisualProperties(Context ctx) 
	{
		ADAPTER.exchangeVisualProperties(ctx, this);
	}

	/**
	 * @see mataf.types.VisualPropertiesExchanger#getDataName()
	 */
	public String getDataName() 
	{
		return dataName;
	}

	/**
	 * @see mataf.types.VisualPropertiesExchanger#setDataName(String)
	 */
	public void setDataName(String dataName) 
	{
		this.dataName = dataName;
	}
	
	/**
	 * Delegates the functionality to the view component.
	 */
	public void setEnabled(boolean enabled)
	{		
		super.setEnabled(enabled);
		getViewport().getView().setEnabled(enabled);
	}


	/**
	 * @see mataf.types.VisualPropertiesExchanger#setErrorMessage(String)
	 */
	public void setErrorMessage(String message) 
	{ /* Empty Implementation */ }

	/**
	 * @see mataf.types.VisualPropertiesExchanger#setInErrorFromServer(boolean)
	 */
	public void setInErrorFromServer(boolean errorFromServer) 
	{ /* Empty Implementation */ }
	
	/**
	 * @see mataf.exchangers.VisualPropertiesExchanger#setRequired(boolean)
	 */
	public void setRequired(boolean mandatory)
	{ /* Empty Implementation */ }

}
