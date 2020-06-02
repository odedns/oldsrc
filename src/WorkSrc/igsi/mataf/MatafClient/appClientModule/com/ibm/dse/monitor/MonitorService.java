package com.ibm.dse.monitor;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JSplitPane;

import com.ibm.dse.base.DSEException;
import com.ibm.dse.base.Service;
import com.ibm.dse.base.Tag;
import com.ibm.dse.base.TagAttribute;

/*
	What's next:
		- display context services
		- state debugger
*/

public class MonitorService extends Service implements WindowListener {

	static boolean panelIsOpen=false;
	static JFrame jf;	

	protected void checkFrame(int layout) {
		if (!panelIsOpen) {
			panelIsOpen=true;
			jf=MonitorPanel.showMonitorFrame(layout);
			jf.addWindowListener(this);
		}
	}
	
	public Object initializeFrom(Tag aTag) throws DSEException, java.io.IOException {
		TagAttribute attr;
		String attrValue;
		int layout=JSplitPane.HORIZONTAL_SPLIT;

		for (int i=aTag.getAttrList().size();--i >= 0;) {
			attr=(TagAttribute)aTag.getAttrList().elementAt(i);
			if (attr.getName().equals("layout")) {
				attrValue=((String)attr.getValue()).toUpperCase();
				if (attrValue.startsWith("H")) layout=JSplitPane.HORIZONTAL_SPLIT;
				else if (attrValue.startsWith("V")) layout=JSplitPane.VERTICAL_SPLIT;
			}
		}
		try {
			checkFrame(layout);
		} catch (Exception e) {
			System.err.println("Error: could not open monitor panel (see exception below)");
			e.printStackTrace(System.err);
		}
		return this;
	}

	public void windowClosing(WindowEvent e) {
		panelIsOpen=false;
	}

	public void windowActivated(WindowEvent e) {}
	public void windowClosed(WindowEvent e) {}
	public void windowDeactivated(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowOpened(WindowEvent e)  {}

}
