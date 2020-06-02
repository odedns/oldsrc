package com.ibm.dse.appl.ej.client;
/*_
 * Licensed Materials - Property of IBM
 * Restricted Materials of IBM
 * 5648-D89
 * (C) Copyright IBM Corp. 2000, 2003
 * US Government Users Restricted Rights - Use, duplication or disclosure
 * restricted by GSA ADP Schedule Contract with IBM Corp
 */
import com.ibm.dse.gui.*;
import com.ibm.dse.appl.ej.client.EJButton;
//import com.ibm.dse.gui.EJTaskLauncherButton;
import com.ibm.dse.appl.ej.client.EJTaskLauncherButton;
import com.ibm.dse.base.Trace;
import java.util.Hashtable;
import java.util.Enumeration;
/**
 * This panel represents the result of a query.
 * It does not contain every column in the Electronic Journal table.
 * The number and names of the columns are specified in the XML file.
 * @copyright (c) Copyright  IBM Corporation 2000, 2003
 */
 
public class EJSummaryPanel extends javax.swing.JPanel {
	private static final java.lang.String COPYRIGHT =
		"Licensed Materials - Property of IBM "+//$NON-NLS-1$
		"Restricted Materials of IBM "+//$NON-NLS-1$
		"5648-D89 "+//$NON-NLS-1$
		"(C) Copyright IBM Corp. 2000, 2003 All Rights Reserved. "+//$NON-NLS-1$
		"US Government Users Restricted Rights - Use, duplication or disclosure "+//$NON-NLS-1$
		"restricted by GSA ADP Schedule Contract with IBM Corp ";//$NON-NLS-1$
class IvjEventHandler implements java.awt.event.ActionListener {
		public void actionPerformed(java.awt.event.ActionEvent e) {
			if (e.getSource() == EJSummaryPanel.this.getSummaryHelpButton()) 
				connEtoC1(e);
			if (e.getSource() == EJSummaryPanel.this.getSummaryCloseButton()) 
				connEtoC2(e);
			if (e.getSource() == EJSummaryPanel.this.getDetailsButton()) 
				connEtoC3(e);
			if (e.getSource() == EJSummaryPanel.this.getScrollPaneTable()) 
				connEtoC4(e);
		};
	}
	private javax.swing.JPanel ivjButtonPanel = null;
	private java.awt.FlowLayout ivjButtonPanelFlowLayout = null;
	private SpButton ivjDetailsButton = null;
	private SpTable ivjScrollPaneTable = null;
	private SpButton ivjSummaryCloseButton = null;
	private SpButton ivjSummaryHelpButton = null;
	private javax.swing.JScrollPane ivjSummaryTable = null;
	protected transient com.ibm.dse.appl.ej.client.EJSummaryPanelListener fieldEJSummaryPanelListenerEventMulticaster = null;
	IvjEventHandler ivjEventHandler = new IvjEventHandler();
	private javax.swing.JLabel ivjSummaryTableLabel = null;
	//ML
	private String xmlFile = "c:\\dse\\xml\\ejview.xml"; // includes full path
	private EJButtonSet dynamicButtons;
	public java.lang.String COMPID = "#EJ";
	private String xmlParseImplementer = "com.ibm.dse.appl.ej.client.EJQueryParserImpl";
	//ML\
/**
 * EJSummaryPanel default constructor.
 */
 
public EJSummaryPanel() {
	super();
	initialize();
}
/**
 * EJSummaryPanel constructor.
 * @param layout java.awt.LayoutManager
 */
 
public EJSummaryPanel(java.awt.LayoutManager layout) {
	super(layout);
}
/**
 * EJSummaryPanel constructor.
 * @param layout java.awt.LayoutManager
 * @param isDoubleBuffered boolean
 */
 
public EJSummaryPanel(java.awt.LayoutManager layout, boolean isDoubleBuffered) {
	super(layout, isDoubleBuffered);
}
/**
 * EJSummaryPanel constructor.
 * @param isDoubleBuffered boolean
 */
 
public EJSummaryPanel(boolean isDoubleBuffered) {
	super(isDoubleBuffered);
}
/**
 * Adds a listener for events coming from the Summary Panel of the Electronic Journal Viewer.
 * @param newListener com.ibm.dse.appl.ej.client.EJSummaryPanelListener
 */
public void addEJSummaryPanelListener(com.ibm.dse.appl.ej.client.EJSummaryPanelListener newListener) {
	fieldEJSummaryPanelListenerEventMulticaster = com.ibm.dse.appl.ej.client.EJSummaryPanelListenerEventMulticaster.add(fieldEJSummaryPanelListenerEventMulticaster, newListener);
	return;
}
/**
 * connEtoC1:  (SummaryHelpButton.action.actionPerformed(java.awt.event.ActionEvent) --> EJSummaryPanel.fireSummaryHelpButtonAction_actionPerformed(Ljava.util.EventObject;)V)
 * @param arg1 java.awt.event.ActionEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void connEtoC1(java.awt.event.ActionEvent arg1) {
	try {
		// user code begin {1}
		// user code end
		this.fireSummaryHelpButtonAction_actionPerformed(new java.util.EventObject(this));
		// user code begin {2}
		// user code end
	} catch (java.lang.Throwable ivjExc) {
		// user code begin {3}
		// user code end
		handleException(ivjExc);
	}
}
/**
 * connEtoC2:  (SummaryCloseButton.action.actionPerformed(java.awt.event.ActionEvent) --> EJSummaryPanel.fireSummaryCloseButtonAction_actionPerformed(Ljava.util.EventObject;)V)
 * @param arg1 java.awt.event.ActionEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void connEtoC2(java.awt.event.ActionEvent arg1) {
	try {
		// user code begin {1}
		// user code end
		this.fireSummaryCloseButtonAction_actionPerformed(new java.util.EventObject(this));
		// user code begin {2}
		// user code end
	} catch (java.lang.Throwable ivjExc) {
		// user code begin {3}
		// user code end
		handleException(ivjExc);
	}
}
/**
 * connEtoC3:  (DetailsButton.action.actionPerformed(java.awt.event.ActionEvent) --> EJSummaryPanel.fireDetailsButtonAction_actionPerformed(Ljava.util.EventObject;)V)
 * @param arg1 java.awt.event.ActionEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void connEtoC3(java.awt.event.ActionEvent arg1) {
	try {
		// user code begin {1}
		// user code end
		this.fireDetailsButtonAction_actionPerformed(new java.util.EventObject(this));
		// user code begin {2}
		// user code end
	} catch (java.lang.Throwable ivjExc) {
		// user code begin {3}
		// user code end
		handleException(ivjExc);
	}
}
/**
 * connEtoC4:  (ScrollPaneTable.action.actionPerformed(java.awt.event.ActionEvent) --> EJSummaryPanel.fireScrollPaneTableAction_actionPerformed(Ljava.util.EventObject;)V)
 * @param arg1 java.awt.event.ActionEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void connEtoC4(java.awt.event.ActionEvent arg1) {
	try {
		// user code begin {1}
		// user code end
		this.fireScrollPaneTableAction_actionPerformed(new java.util.EventObject(this));
		// user code begin {2}
		// user code end
	} catch (java.lang.Throwable ivjExc) {
		// user code begin {3}
		// user code end
		handleException(ivjExc);
	}
}
/**
 * Supports listener events.
 * @param newEvent java.util.EventObject
 */
protected void fireDetailsButtonAction_actionPerformed(java.util.EventObject newEvent) {
	if (fieldEJSummaryPanelListenerEventMulticaster == null) {
		return;
	};
	fieldEJSummaryPanelListenerEventMulticaster.detailsButtonAction_actionPerformed(newEvent);
}
/**
 * Supports listener events.
 * @param newEvent java.util.EventObject
 */
protected void fireScrollPaneTableAction_actionPerformed(java.util.EventObject newEvent) {
	if (fieldEJSummaryPanelListenerEventMulticaster == null) {
		return;
	};
	fieldEJSummaryPanelListenerEventMulticaster.scrollPaneTableAction_actionPerformed(newEvent);
}
/**
 * Supports listener events.
 * @param newEvent java.util.EventObject
 */
protected void fireSummaryCloseButtonAction_actionPerformed(java.util.EventObject newEvent) {
	if (fieldEJSummaryPanelListenerEventMulticaster == null) {
		return;
	};
	fieldEJSummaryPanelListenerEventMulticaster.summaryCloseButtonAction_actionPerformed(newEvent);
}
/**
 * Supports listener events.
 * @param newEvent java.util.EventObject
 */
protected void fireSummaryHelpButtonAction_actionPerformed(java.util.EventObject newEvent) {
	if (fieldEJSummaryPanelListenerEventMulticaster == null) {
		return;
	};
	fieldEJSummaryPanelListenerEventMulticaster.summaryHelpButtonAction_actionPerformed(newEvent);
}
/**
 * Return the ButtonPanel property value.
 * @return javax.swing.JPanel
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JPanel getButtonPanel() {
	EJTaskLauncherButton button = null;
	if (ivjButtonPanel == null) {
		try {
			ivjButtonPanel = new javax.swing.JPanel();
			ivjButtonPanel.setName("ButtonPanel");
			ivjButtonPanel.setBorder(new javax.swing.border.EtchedBorder());
			ivjButtonPanel.setLayout(getButtonPanelFlowLayout());
			ivjButtonPanel.setMaximumSize(new java.awt.Dimension(32767, 45));
			ivjButtonPanel.setPreferredSize(new java.awt.Dimension(450, 45));
			ivjButtonPanel.setMinimumSize(new java.awt.Dimension(450, 45));
			getButtonPanel().add(getDetailsButton(), getDetailsButton().getName());
			getButtonPanel().add(getSummaryCloseButton(), getSummaryCloseButton().getName());
			getButtonPanel().add(getSummaryHelpButton(), getSummaryHelpButton().getName());
			// user code begin {1}
			// user code end
			
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjButtonPanel;
}
/**
 * Return the ButtonPanelFlowLayout property value.
 * @return java.awt.FlowLayout
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.FlowLayout getButtonPanelFlowLayout() {
	java.awt.FlowLayout ivjButtonPanelFlowLayout = null;
	try {
		/* Create part */
		ivjButtonPanelFlowLayout = new java.awt.FlowLayout();
		ivjButtonPanelFlowLayout.setAlignment(java.awt.FlowLayout.RIGHT);
		ivjButtonPanelFlowLayout.setVgap(10);
		ivjButtonPanelFlowLayout.setHgap(10);
	} catch (java.lang.Throwable ivjExc) {
		handleException(ivjExc);
	};
	return ivjButtonPanelFlowLayout;
}
/**
 * Return the DetailsButton property value.
 * @return com.ibm.dse.gui.SpButton
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private com.ibm.dse.gui.SpButton getDetailsButton() {
	if (ivjDetailsButton == null) {
		try {
			ivjDetailsButton = new com.ibm.dse.gui.SpButton();
			ivjDetailsButton.setName("DetailsButton");
			ivjDetailsButton.setText("Details");
			ivjDetailsButton.setMaximumSize(new java.awt.Dimension(80, 25));
			ivjDetailsButton.setPreferredSize(new java.awt.Dimension(80, 25));
			ivjDetailsButton.setMinimumSize(new java.awt.Dimension(80, 25));
			ivjDetailsButton.setEnabled(false);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjDetailsButton;
}
/**
 * Provides access to the details button from the main view.
 * 
 * @return com.ibm.dse.gui.SpButton
 */
public SpButton getPublicDetailsButton() {
	return getDetailsButton();
}/**
 * Return the ScrollPaneTable property value.
 * @return com.ibm.dse.gui.SpTable
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private com.ibm.dse.gui.SpTable getScrollPaneTable() {
	if (ivjScrollPaneTable == null) {
		try {
			ivjScrollPaneTable = new com.ibm.dse.gui.SpTable();
			ivjScrollPaneTable.setName("ScrollPaneTable");
			getSummaryTable().setColumnHeaderView(ivjScrollPaneTable.getTableHeader());
			getSummaryTable().getViewport().setScrollMode(getSummaryTable().getViewport().BACKINGSTORE_SCROLL_MODE);
			ivjScrollPaneTable.setBounds(0, 0, 200, 200);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjScrollPaneTable;
}
/**
 * Provides access to the scroll pane table from the main view.
 * 
 * @return com.ibm.dse.gui.SpTable
 */
public SpTable getScrollTable() {
	return getScrollPaneTable();
}
/**
 * Return the SummaryCloseButton property value.
 * @return com.ibm.dse.gui.SpButton
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private com.ibm.dse.gui.SpButton getSummaryCloseButton() {
	if (ivjSummaryCloseButton == null) {
		try {
			ivjSummaryCloseButton = new com.ibm.dse.gui.SpButton();
			ivjSummaryCloseButton.setName("SummaryCloseButton");
			ivjSummaryCloseButton.setText("Close");
			ivjSummaryCloseButton.setMaximumSize(new java.awt.Dimension(80, 25));
			ivjSummaryCloseButton.setPreferredSize(new java.awt.Dimension(80, 25));
			ivjSummaryCloseButton.setType("Close");
			ivjSummaryCloseButton.setMinimumSize(new java.awt.Dimension(80, 25));
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjSummaryCloseButton;
}
/**
 * Return the SummaryHelpButton property value.
 * @return com.ibm.dse.gui.SpButton
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private com.ibm.dse.gui.SpButton getSummaryHelpButton() {
	if (ivjSummaryHelpButton == null) {
		try {
			ivjSummaryHelpButton = new com.ibm.dse.gui.SpButton();
			ivjSummaryHelpButton.setName("SummaryHelpButton");
			ivjSummaryHelpButton.setText("Help");
			ivjSummaryHelpButton.setMaximumSize(new java.awt.Dimension(80, 25));
			ivjSummaryHelpButton.setPreferredSize(new java.awt.Dimension(80, 25));
			ivjSummaryHelpButton.setType("Help");
			ivjSummaryHelpButton.setMinimumSize(new java.awt.Dimension(80, 25));
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjSummaryHelpButton;
}
/**
 * Returns the ScrollPaneTable property value.
 * @return com.ibm.dse.gui.SpTable
 */
 public com.ibm.dse.gui.SpTable getSummaryScrollPaneTable() {
	
	return ivjScrollPaneTable;
	
}
/**
 * Return the SummaryTable property value.
 * @return javax.swing.JScrollPane
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JScrollPane getSummaryTable() {
	if (ivjSummaryTable == null) {
		try {
			ivjSummaryTable = new javax.swing.JScrollPane();
			ivjSummaryTable.setName("SummaryTable");
			ivjSummaryTable.setVerticalScrollBarPolicy(javax.swing.JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			ivjSummaryTable.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			ivjSummaryTable.setPreferredSize(new java.awt.Dimension(450, 300));
			ivjSummaryTable.setMinimumSize(new java.awt.Dimension(450, 100));
			getSummaryTable().setViewportView(getScrollPaneTable());
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjSummaryTable;
}
/**
 * Return the SummaryTableLabel property value.
 * @return javax.swing.JLabel
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JLabel getSummaryTableLabel() {
	if (ivjSummaryTableLabel == null) {
		try {
			ivjSummaryTableLabel = new javax.swing.JLabel();
			ivjSummaryTableLabel.setName("SummaryTableLabel");
			ivjSummaryTableLabel.setText("EJ Summary Table");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjSummaryTableLabel;
}
/**
 * Returns the XML file name, including the full path.
 * 
 * @return java.lang.String
 */
public String getXMLFile() {
	return xmlFile;
}
/**
 * Returns the xmlParseImplementer attribute.
 * 
 * @return java.lang.String
 */
public String getXMLParseImplementer() {
	return xmlParseImplementer;
}
/**
 * Called whenever the part throws an exception.
 * @param exception java.lang.Throwable
 */
private void handleException(java.lang.Throwable exception) {
	/* Uncomment the following lines to print uncaught exceptions to stdout */
	 System.out.println("--------- UNCAUGHT EXCEPTION ---------");
	 exception.printStackTrace(System.out);
}
/**
 * Initializes connections
 * @exception java.lang.Exception The exception description.
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void initConnections() throws java.lang.Exception {
	// user code begin {1}
	// user code end
	getSummaryHelpButton().addActionListener(ivjEventHandler);
	getSummaryCloseButton().addActionListener(ivjEventHandler);
	getDetailsButton().addActionListener(ivjEventHandler);
	getScrollPaneTable().addActionListener(ivjEventHandler);
}
/**
 * Initialize the class.
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void initialize() {
	try {
		// user code begin {1}
		// user code end
		setName("EJSummaryPanel");
		setPreferredSize(new java.awt.Dimension(450, 350));
		setLayout(new java.awt.GridBagLayout());
		setSize(600, 600);
		setMinimumSize(new java.awt.Dimension(450, 350));
		
	//ML
		String fileName = null;
		EJQueryParserImpl parser = null;
		try {
		fileName = getXMLParseImplementer();
		parser = (EJQueryParserImpl) Class.forName(fileName).newInstance();
	} catch (ClassNotFoundException e) {
		Trace.trace(COMPID,Trace.Severe,Trace.Error,null, e.toString() + ". Problem with EJQueryParser.");
	} catch (InstantiationException e) {
		Trace.trace(COMPID,Trace.Severe,Trace.Error,null, e.toString() + ". Problem with EJQueryParser.");
	} catch (IllegalAccessException e) {
		Trace.trace(COMPID,Trace.Severe,Trace.Error,null, e.toString() + ". Problem with EJQueryParser.");
	}
	// Parse the XML file
	parser.initXML(getXMLFile());
	dynamicButtons = parser.getButtonSet();
	//loop
	
	EJTaskLauncherButton button = null;
	Enumeration buttons = dynamicButtons.getButtons().elements();	
			while(buttons.hasMoreElements())
			{
				EJButton dynamicButton = (EJButton)buttons.nextElement();
				button = new EJTaskLauncherButton();
				button.setType(dynamicButton.getType());
				button.setText(dynamicButton.getLabel());
				button.setOperation(dynamicButton.getOpId());
				button.setSelection(dynamicButton.getSelection());
				button.setKCollName(dynamicButton.getKCollName());
				button.setMaximumSize(new java.awt.Dimension(80, 25));
				button.setPreferredSize(new java.awt.Dimension(80, 25));
				button.setMinimumSize(new java.awt.Dimension(80, 25));
				button.setVisible(true);
				getButtonPanel().add(button,button.getName());
			}
	//ML\
		java.awt.GridBagConstraints constraintsSummaryTable = new java.awt.GridBagConstraints();
		constraintsSummaryTable.gridx = 0; constraintsSummaryTable.gridy = 1;
		constraintsSummaryTable.fill = java.awt.GridBagConstraints.BOTH;
		constraintsSummaryTable.weightx = 1.0;
		constraintsSummaryTable.weighty = 8.0;
		constraintsSummaryTable.insets = new java.awt.Insets(5, 10, 30, 10);
		add(getSummaryTable(), constraintsSummaryTable);
		java.awt.GridBagConstraints constraintsButtonPanel = new java.awt.GridBagConstraints();
		constraintsButtonPanel.gridx = 0; constraintsButtonPanel.gridy = 2;
		constraintsButtonPanel.fill = java.awt.GridBagConstraints.BOTH;
		constraintsButtonPanel.weightx = 1.0;
		add(getButtonPanel(), constraintsButtonPanel);
		java.awt.GridBagConstraints constraintsSummaryTableLabel = new java.awt.GridBagConstraints();
		constraintsSummaryTableLabel.gridx = 0; constraintsSummaryTableLabel.gridy = 0;
		constraintsSummaryTableLabel.anchor = java.awt.GridBagConstraints.WEST;
		constraintsSummaryTableLabel.insets = new java.awt.Insets(10, 10, 5, 0);
		add(getSummaryTableLabel(), constraintsSummaryTableLabel);
		initConnections();
	} catch (java.lang.Throwable ivjExc) {
		handleException(ivjExc);
	}
	// user code begin {2}
	// user code end
}
/**
 * This is the main entrypoint - starts the part when it is run as an application.
 * @param args java.lang.String[]
 */
public static void main(java.lang.String[] args) {
	try {
		javax.swing.JFrame frame = new javax.swing.JFrame();
		EJSummaryPanel aEJSummaryPanel;
		aEJSummaryPanel = new EJSummaryPanel();
		frame.setContentPane(aEJSummaryPanel);
		frame.setSize(aEJSummaryPanel.getSize());
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				System.exit(0);
			};
		});
		frame.setVisible(true);
	} catch (Throwable exception) {
		System.err.println("Exception occurred in main() of javax.swing.JPanel");
		exception.printStackTrace(System.out);
	}
}
/**
 * Removes the listener for Summary Panel events.
 * @param newListener com.ibm.dse.appl.ej.client.EJSummaryPanelListener
 */
public void removeEJSummaryPanelListener(com.ibm.dse.appl.ej.client.EJSummaryPanelListener newListener) {
	fieldEJSummaryPanelListenerEventMulticaster = com.ibm.dse.appl.ej.client.EJSummaryPanelListenerEventMulticaster.remove(fieldEJSummaryPanelListenerEventMulticaster, newListener);
	return;
}
}
