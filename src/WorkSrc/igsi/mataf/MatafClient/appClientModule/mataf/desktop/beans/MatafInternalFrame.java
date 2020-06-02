package mataf.desktop.beans;

import mataf.dse.gui.*;
import mataf.ui.*;

import com.ibm.dse.desktop.SpInternalFrame;

public class MatafInternalFrame extends SpInternalFrame {

	/**
	 * Constructor for PoalimInternalFrame
	 */
	public MatafInternalFrame() {
		super();
	}

	/**
	 * Constructor for PoalimInternalFrame
	 */
	public MatafInternalFrame(String arg0) {
		super(arg0);
	}

	/**
	 * Constructor for PoalimInternalFrame
	 */
	public MatafInternalFrame(String arg0, boolean arg1) {
		super(arg0, arg1);
	}

	/**
	 * Constructor for PoalimInternalFrame
	 */
	public MatafInternalFrame(String arg0, boolean arg1, boolean arg2) {
		super(arg0, arg1, arg2);
	}

	/**
	 * Constructor for PoalimInternalFrame
	 */
	public MatafInternalFrame(
		String arg0,
		boolean arg1,
		boolean arg2,
		boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	/**
	 * Constructor for PoalimInternalFrame
	 */
	public MatafInternalFrame(
		String arg0,
		boolean arg1,
		boolean arg2,
		boolean arg3,
		boolean arg4) {
		super(arg0, arg1, arg2, arg3, arg4);
	}
	public void updateUI(){
		setUI(new MatafInternalFrameUI(this));
	}

}

