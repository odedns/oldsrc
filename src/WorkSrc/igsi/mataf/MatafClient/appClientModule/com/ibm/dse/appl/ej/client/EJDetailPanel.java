package com.ibm.dse.appl.ej.client;

/*_
 * Licensed Materials - Property of IBM
 * Restricted Materials of IBM
 * 5648-D89
 * (C) Copyright IBM Corp. 2000, 2002
 * US Government Users Restricted Rights - Use, duplication or disclosure
 * restricted by GSA ADP Schedule Contract with IBM Corp
 */
import com.ibm.dse.gui.*;

/**
 * This panel is responsible for displaying all the columns for a specific entry
 * in the Electronic Journal table.
 * The display takes the form of two columns: labeled key and value.
 * The key is the label of the column
 * and the value is the value of the selected row in the table.
 * @copyright (c) Copyright  IBM Corporation 2000, 2002
 */
 
public class EJDetailPanel extends javax.swing.JPanel {
	private static final java.lang.String COPYRIGHT =
		"Licensed Materials - Property of IBM "+//$NON-NLS-1$
		"Restricted Materials of IBM "+//$NON-NLS-1$
		"5648-D89 "+//$NON-NLS-1$
		"(C) Copyright IBM Corp. 2000, 2002 All Rights Reserved. "+//$NON-NLS-1$
		"US Government Users Restricted Rights - Use, duplication or disclosure "+//$NON-NLS-1$
		"restricted by GSA ADP Schedule Contract with IBM Corp ";//$NON-NLS-1$

class IvjEventHandler implements java.awt.event.ActionListener {
		public void actionPerformed(java.awt.event.ActionEvent e) {
			if (e.getSource() == EJDetailPanel.this.getDetailsHelpButton()) 
				connEtoC1(e);
			if (e.getSource() == EJDetailPanel.this.getDetailsCloseButton()) 
				connEtoC2(e);
			if (e.getSource() == EJDetailPanel.this.getSummaryButton()) 
				connEtoC3(e);
		};
	}
	private javax.swing.JPanel ivjButtonJPanel = null;
	private java.awt.FlowLayout ivjButtonJPanelFlowLayout = null;
	private javax.swing.JScrollPane ivjDetailsTable = null;
	private javax.swing.JPanel ivjDummyPanel = null;
	private SpTable ivjScrollPaneTable = null;
	private SpButton ivjDetailsCloseButton = null;
	private SpButton ivjDetailsHelpButton = null;
	private SpButton ivjSummaryButton = null;
	protected transient com.ibm.dse.appl.ej.client.EJDetailPanelListener fieldEJDetailPanelListenerEventMulticaster = null;
	IvjEventHandler ivjEventHandler = new IvjEventHandler();
	private javax.swing.JLabel ivjDetailsTableLabel = null;
/**
 * EJDetailPanel default constructor.
 */
 
public EJDetailPanel() {
	super();
	initialize();
}
/**
 * EJDetailPanel constructor.
 * @param layout java.awt.LayoutManager
 */
 
public EJDetailPanel(java.awt.LayoutManager layout) {
	super(layout);
}
/**
 * EJDetailPanel constructor.
 * @param layout java.awt.LayoutManager
 * @param isDoubleBuffered boolean
 */
 
public EJDetailPanel(java.awt.LayoutManager layout, boolean isDoubleBuffered) {
	super(layout, isDoubleBuffered);
}
/**
 * EJDetailPanel constructor.
 * @param isDoubleBuffered boolean
 */
 
public EJDetailPanel(boolean isDoubleBuffered) {
	super(isDoubleBuffered);
}
/**
 * 
 * @param newListener com.ibm.dse.appl.ej.client.EJDetailPanelListener
 */
public void addEJDetailPanelListener(com.ibm.dse.appl.ej.client.EJDetailPanelListener newListener) {
	fieldEJDetailPanelListenerEventMulticaster = com.ibm.dse.appl.ej.client.EJDetailPanelListenerEventMulticaster.add(fieldEJDetailPanelListenerEventMulticaster, newListener);
	return;
}
/**
 * connEtoC1:  (DetailsHelpButton.action.actionPerformed(java.awt.event.ActionEvent) --> EJDetailPanel.fireDetailsHelpButtonAction_actionPerformed(Ljava.util.EventObject;)V)
 * @param arg1 java.awt.event.ActionEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void connEtoC1(java.awt.event.ActionEvent arg1) {
	try {
		// user code begin {1}
		// user code end
		this.fireDetailsHelpButtonAction_actionPerformed(new java.util.EventObject(this));
		// user code begin {2}
		// user code end
	} catch (java.lang.Throwable ivjExc) {
		// user code begin {3}
		// user code end
		handleException(ivjExc);
	}
}
/**
 * connEtoC2:  (DetailsCloseButton.action.actionPerformed(java.awt.event.ActionEvent) --> EJDetailPanel.fireDetailsCloseButtonAction_actionPerformed(Ljava.util.EventObject;)V)
 * @param arg1 java.awt.event.ActionEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void connEtoC2(java.awt.event.ActionEvent arg1) {
	try {
		// user code begin {1}
		// user code end
		this.fireDetailsCloseButtonAction_actionPerformed(new java.util.EventObject(this));
		// user code begin {2}
		// user code end
	} catch (java.lang.Throwable ivjExc) {
		// user code begin {3}
		// user code end
		handleException(ivjExc);
	}
}
/**
 * connEtoC3:  (SummaryButton.action.actionPerformed(java.awt.event.ActionEvent) --> EJDetailPanel.fireSummaryButtonAction_actionPerformed(Ljava.util.EventObject;)V)
 * @param arg1 java.awt.event.ActionEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void connEtoC3(java.awt.event.ActionEvent arg1) {
	try {
		// user code begin {1}
		// user code end
		this.fireSummaryButtonAction_actionPerformed(new java.util.EventObject(this));
		// user code begin {2}
		// user code end
	} catch (java.lang.Throwable ivjExc) {
		// user code begin {3}
		// user code end
		handleException(ivjExc);
	}
}
/**
 * Method to support listener events.
 * @param newEvent java.util.EventObject
 */
protected void fireDetailsCloseButtonAction_actionPerformed(java.util.EventObject newEvent) {
	if (fieldEJDetailPanelListenerEventMulticaster == null) {
		return;
	};
	fieldEJDetailPanelListenerEventMulticaster.detailsCloseButtonAction_actionPerformed(newEvent);
}
/**
 * Method to support listener events.
 * @param newEvent java.util.EventObject
 */
protected void fireDetailsHelpButtonAction_actionPerformed(java.util.EventObject newEvent) {
	if (fieldEJDetailPanelListenerEventMulticaster == null) {
		return;
	};
	fieldEJDetailPanelListenerEventMulticaster.detailsHelpButtonAction_actionPerformed(newEvent);
}
/**
 * Method to support listener events.
 * @param newEvent java.util.EventObject
 */
protected void fireSummaryButtonAction_actionPerformed(java.util.EventObject newEvent) {
	if (fieldEJDetailPanelListenerEventMulticaster == null) {
		return;
	};
	fieldEJDetailPanelListenerEventMulticaster.summaryButtonAction_actionPerformed(newEvent);
}
/**
 * Return the ButtonJPanel property value.
 * @return javax.swing.JPanel
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JPanel getButtonJPanel() {
	if (ivjButtonJPanel == null) {
		try {
			ivjButtonJPanel = new javax.swing.JPanel();
			ivjButtonJPanel.setName("ButtonJPanel");
			ivjButtonJPanel.setBorder(new javax.swing.border.EtchedBorder());
			ivjButtonJPanel.setLayout(getButtonJPanelFlowLayout());
			ivjButtonJPanel.setMaximumSize(new java.awt.Dimension(32767, 45));
			ivjButtonJPanel.setPreferredSize(new java.awt.Dimension(280, 45));
			ivjButtonJPanel.setMinimumSize(new java.awt.Dimension(280, 45));
			getButtonJPanel().add(getSummaryButton(), getSummaryButton().getName());
			getButtonJPanel().add(getDetailsCloseButton(), getDetailsCloseButton().getName());
			getButtonJPanel().add(getDetailsHelpButton(), getDetailsHelpButton().getName());
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjButtonJPanel;
}
/**
 * Return the ButtonJPanelFlowLayout property value.
 * @return java.awt.FlowLayout
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.FlowLayout getButtonJPanelFlowLayout() {
	java.awt.FlowLayout ivjButtonJPanelFlowLayout = null;
	try {
		/* Create part */
		ivjButtonJPanelFlowLayout = new java.awt.FlowLayout();
		ivjButtonJPanelFlowLayout.setAlignment(java.awt.FlowLayout.RIGHT);
		ivjButtonJPanelFlowLayout.setVgap(10);
		ivjButtonJPanelFlowLayout.setHgap(10);
	} catch (java.lang.Throwable ivjExc) {
		handleException(ivjExc);
	};
	return ivjButtonJPanelFlowLayout;
}
/**
 * Return the DetailsCloseButton property value.
 * @return com.ibm.dse.gui.SpButton
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private com.ibm.dse.gui.SpButton getDetailsCloseButton() {
	if (ivjDetailsCloseButton == null) {
		try {
			ivjDetailsCloseButton = new com.ibm.dse.gui.SpButton();
			ivjDetailsCloseButton.setName("DetailsCloseButton");
			ivjDetailsCloseButton.setText("Close");
			ivjDetailsCloseButton.setMaximumSize(new java.awt.Dimension(80, 25));
			ivjDetailsCloseButton.setPreferredSize(new java.awt.Dimension(80, 25));
			ivjDetailsCloseButton.setType("Close");
			ivjDetailsCloseButton.setMinimumSize(new java.awt.Dimension(80, 25));
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjDetailsCloseButton;
}
/**
 * Return the DetailsHelpButton property value.
 * @return com.ibm.dse.gui.SpButton
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private com.ibm.dse.gui.SpButton getDetailsHelpButton() {
	if (ivjDetailsHelpButton == null) {
		try {
			ivjDetailsHelpButton = new com.ibm.dse.gui.SpButton();
			ivjDetailsHelpButton.setName("DetailsHelpButton");
			ivjDetailsHelpButton.setText("Help");
			ivjDetailsHelpButton.setMaximumSize(new java.awt.Dimension(80, 25));
			ivjDetailsHelpButton.setPreferredSize(new java.awt.Dimension(80, 25));
			ivjDetailsHelpButton.setType("Help");
			ivjDetailsHelpButton.setMinimumSize(new java.awt.Dimension(80, 25));
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjDetailsHelpButton;
}
/**
 * Return the DetailsTable property value.
 * @return javax.swing.JScrollPane
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JScrollPane getDetailsTable() {
	if (ivjDetailsTable == null) {
		try {
			ivjDetailsTable = new javax.swing.JScrollPane();
			ivjDetailsTable.setName("DetailsTable");
			ivjDetailsTable.setVerticalScrollBarPolicy(javax.swing.JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			ivjDetailsTable.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			ivjDetailsTable.setPreferredSize(new java.awt.Dimension(450, 300));
			ivjDetailsTable.setMinimumSize(new java.awt.Dimension(300, 100));
			getDetailsTable().setViewportView(getScrollPaneTable());
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjDetailsTable;
}
/**
 * Return the DetailsTableLabel property value.
 * @return javax.swing.JLabel
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JLabel getDetailsTableLabel() {
	if (ivjDetailsTableLabel == null) {
		try {
			ivjDetailsTableLabel = new javax.swing.JLabel();
			ivjDetailsTableLabel.setName("DetailsTableLabel");
			ivjDetailsTableLabel.setText("EJ Details Table");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjDetailsTableLabel;
}
/**
 * Return the DummyPanel property value.
 * @return javax.swing.JPanel
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JPanel getDummyPanel() {
	if (ivjDummyPanel == null) {
		try {
			ivjDummyPanel = new javax.swing.JPanel();
			ivjDummyPanel.setName("DummyPanel");
			ivjDummyPanel.setPreferredSize(new java.awt.Dimension(450, 300));
			ivjDummyPanel.setLayout(null);
			ivjDummyPanel.setMaximumSize(new java.awt.Dimension(32767, 32767));
			ivjDummyPanel.setMinimumSize(new java.awt.Dimension(300, 100));
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjDummyPanel;
}
/**
 * Return the ScrollPaneTable property value.
 * @return com.ibm.dse.gui.SpTable
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private com.ibm.dse.gui.SpTable getScrollPaneTable() {
	if (ivjScrollPaneTable == null) {
		try {
			ivjScrollPaneTable = new com.ibm.dse.gui.SpTable();
			ivjScrollPaneTable.setName("ScrollPaneTable");
			getDetailsTable().setColumnHeaderView(ivjScrollPaneTable.getTableHeader());
			getDetailsTable().getViewport().setScrollMode(getDetailsTable().getViewport().BACKINGSTORE_SCROLL_MODE );
			ivjScrollPaneTable.setDataNameAndColumns((((new com.ibm.dse.gui.VectorEditor(3)).setElemAt("detailsList",0)
).setElemAt(new com.ibm.dse.gui.ColumnFormatter("key","Key",null,false,false,false,75),1)
).setElemAt(new com.ibm.dse.gui.ColumnFormatter("value","Value",null,false,false,false,75),2));
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
 * Return the SummaryButton property value.
 * @return com.ibm.dse.gui.SpButton
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private com.ibm.dse.gui.SpButton getSummaryButton() {
	if (ivjSummaryButton == null) {
		try {
			ivjSummaryButton = new com.ibm.dse.gui.SpButton();
			ivjSummaryButton.setName("SummaryButton");
			ivjSummaryButton.setPreferredSize(new java.awt.Dimension(80, 25));
			ivjSummaryButton.setText("Summary");
			ivjSummaryButton.setMaximumSize(new java.awt.Dimension(80, 25));
			ivjSummaryButton.setMinimumSize(new java.awt.Dimension(80, 25));
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjSummaryButton;
}
/**
 * Called whenever the part throws an exception.
 * @param exception java.lang.Throwable
 */
private void handleException(java.lang.Throwable exception) {

	/* Uncomment the following lines to print uncaught exceptions to stdout */
	// System.out.println("--------- UNCAUGHT EXCEPTION ---------");
	// exception.printStackTrace(System.out);
}
/**
 * Initializes connections
 * @exception java.lang.Exception - The exception description
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void initConnections() throws java.lang.Exception {
	// user code begin {1}
	// user code end
	getDetailsHelpButton().addActionListener(ivjEventHandler);
	getDetailsCloseButton().addActionListener(ivjEventHandler);
	getSummaryButton().addActionListener(ivjEventHandler);
}
/**
 * Initializes the class.
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void initialize() {
	try {
		// user code begin {1}
		// user code end
		setName("EJDetailPanel");
		setPreferredSize(new java.awt.Dimension(450, 350));
		setLayout(new java.awt.GridBagLayout());
		setSize(600, 600);
		setMinimumSize(new java.awt.Dimension(450, 200));

		java.awt.GridBagConstraints constraintsDetailsTable = new java.awt.GridBagConstraints();
		constraintsDetailsTable.gridx = 0; constraintsDetailsTable.gridy = 1;
		constraintsDetailsTable.fill = java.awt.GridBagConstraints.BOTH;
		constraintsDetailsTable.weightx = 1.0;
		constraintsDetailsTable.weighty = 8.0;
		constraintsDetailsTable.insets = new java.awt.Insets(5, 10, 30, 0);
		add(getDetailsTable(), constraintsDetailsTable);

		java.awt.GridBagConstraints constraintsButtonJPanel = new java.awt.GridBagConstraints();
		constraintsButtonJPanel.gridx = 0; constraintsButtonJPanel.gridy = 2;
		constraintsButtonJPanel.gridwidth = 2;
		constraintsButtonJPanel.fill = java.awt.GridBagConstraints.BOTH;
		constraintsButtonJPanel.weightx = 1.0;
		add(getButtonJPanel(), constraintsButtonJPanel);

		java.awt.GridBagConstraints constraintsDummyPanel = new java.awt.GridBagConstraints();
		constraintsDummyPanel.gridx = 1; constraintsDummyPanel.gridy = 1;
		constraintsDummyPanel.fill = java.awt.GridBagConstraints.BOTH;
		constraintsDummyPanel.weightx = 1.0;
		constraintsDummyPanel.weighty = 8.0;
		constraintsDummyPanel.insets = new java.awt.Insets(0, 0, 30, 0);
		add(getDummyPanel(), constraintsDummyPanel);

		java.awt.GridBagConstraints constraintsDetailsTableLabel = new java.awt.GridBagConstraints();
		constraintsDetailsTableLabel.gridx = 0; constraintsDetailsTableLabel.gridy = 0;
		constraintsDetailsTableLabel.anchor = java.awt.GridBagConstraints.WEST;
		constraintsDetailsTableLabel.insets = new java.awt.Insets(10, 10, 5, 0);
		add(getDetailsTableLabel(), constraintsDetailsTableLabel);
		initConnections();
	} catch (java.lang.Throwable ivjExc) {
		handleException(ivjExc);
	}
	// user code begin {2}
	// user code end
}
/**
 * main entrypoint - starts the part when it is run as an application
 * @param args java.lang.String[]
 */
public static void main(java.lang.String[] args) {
	try {
		javax.swing.JFrame frame = new javax.swing.JFrame();
		EJDetailPanel aEJDetailPanel;
		aEJDetailPanel = new EJDetailPanel();
		frame.setContentPane(aEJDetailPanel);
		frame.setSize(aEJDetailPanel.getSize());
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
 * 
 * @param newListener com.ibm.dse.appl.ej.client.EJDetailPanelListener
 */
public void removeEJDetailPanelListener(com.ibm.dse.appl.ej.client.EJDetailPanelListener newListener) {
	fieldEJDetailPanelListenerEventMulticaster = com.ibm.dse.appl.ej.client.EJDetailPanelListenerEventMulticaster.remove(fieldEJDetailPanelListenerEventMulticaster, newListener);
	return;
}
}
