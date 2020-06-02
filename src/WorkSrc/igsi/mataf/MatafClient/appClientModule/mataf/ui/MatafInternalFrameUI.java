package mataf.ui;

import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.LookAndFeel;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicInternalFrameUI;

public class MatafInternalFrameUI extends BasicInternalFrameUI {

	/**
	 * Constructor for MatafInternalFrameUI
	 */
	public MatafInternalFrameUI(JInternalFrame arg0) 
	{
		super(arg0);
	}
	

	public static ComponentUI createUI(JComponent jc){
		return new MatafInternalFrameUI((JInternalFrame)jc) ;
	}
	
	protected JComponent createNorthPane(JInternalFrame j) {
		return null ;
	}

	protected void installDefaults(){
		super.installDefaults();
		LookAndFeel.installBorder(frame , "EmptyBorder");
	}
}

