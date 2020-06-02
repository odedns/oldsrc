package mataf.desktop.beans;

import mataf.logger.GLogger;

import com.ibm.dse.desktop.Desktop;

/**
 * @author Eyal Ben Ze'ev
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class MatafDesktop extends Desktop 								
{
	/**
	 * Constructor for MatafDesktop.
	 */
	public MatafDesktop() 
	{
		super();		
	}
	
	public void showMessage(String s,int i) 
	{
		GLogger.info(s);
	}
	
	protected static Desktop getNewDesktopInstance() 
	{
		return new MatafDesktop();
	}
	
	/**
	 * @see com.ibm.dse.desktop.Desktop#init()
	 */
	public void init() 
	{
		System.out.println("Trying to undecorate");
		Desktop.getFrame().setUndecorated(true);
		super.init();
	}
	
	public static void main(java.lang.String[] args) 
	{
		
		if ((args!=null)&& (args.length>0))
			setFileName(args[0]);		
		
		frame = new javax.swing.JFrame();
		
		if (Desktop.getDesktop()==null)
		
		theDesktop = getNewDesktopInstance();
		
		theDesktop.init();
		
		if (iniDesktopOK)
		{
			frame.getContentPane().add("Center", theDesktop);//$NON-NLS-1$
			frame.setSize(theDesktop.getSize());
			frame.setTitle(theDesktop.getTitle());
			theDesktop.centerView();
			frame.show();
			frame.addWindowListener(theDesktop);				
			theDesktop.setSize(frame.getSize());
		}
		else
			frame.dispose();
	}
}
