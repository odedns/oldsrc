package com.ibm.dse.appl.ej.client;

/*_
 * Licensed Materials - Property of IBM
 * Restricted Materials of IBM
 * 5648-D89
 * (C) Copyright IBM Corp. 2000, 2001
 * US Government Users Restricted Rights - Use, duplication or disclosure
 * restricted by GSA ADP Schedule Contract with IBM Corp
 */
import com.ibm.dse.gui.*;
import com.ibm.dse.base.ClientOperation;
import com.ibm.dse.base.Context;
import com.ibm.dse.base.DSEClientOperation;
import com.ibm.dse.base.Trace;
import com.ibm.dse.base.DataElement;
import com.ibm.dse.base.DataField;
import com.ibm.dse.base.Settings;
import com.ibm.dse.base.DSEObjectNotFoundException;
import com.ibm.dse.base.DSEInvalidArgumentException;
import com.ibm.dse.base.DSEInvalidRequestException;
import com.ibm.dse.base.DSEException;
import com.ibm.dse.base.DSEOperation;
import com.ibm.dse.base.KeyedCollection;
import com.ibm.dse.base.IndexedCollection;
import com.ibm.dse.base.Operation;
import com.ibm.dse.clientserver.CSClientService;
import com.ibm.dse.desktop.Desktop;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Enumeration;
import java.util.Vector;

/**
 * This view allows the user to query the electronic journal tables.
 * The queries are predefined by the system.
 * When the user selects a query and updates the view,
 * the view dynamically changes to reflect the widgets needed for entries to create the query.
 * It also updates the summary table columns to reflect the number and names of the columns
 * expected in the result of that query.
 * @copyright (c) Copyright  IBM Corporation 2000, 2001
 */

public class EJViewer extends OperationPanel implements javax.swing.event.ListSelectionListener {
	private static final java.lang.String COPYRIGHT = "Licensed Materials - Property of IBM " +
		//$NON-NLS-1$
		"Restricted Materials of IBM " + //$NON-NLS-1$
		"5648-D89 " + //$NON-NLS-1$
		"(C) Copyright IBM Corp. 2000, 2001 All Rights Reserved. " + //$NON-NLS-1$
		"US Government Users Restricted Rights - Use, duplication or disclosure " + //$NON-NLS-1$
	"restricted by GSA ADP Schedule Contract with IBM Corp "; //$NON-NLS-1$

	class IvjEventHandler
		implements com.ibm.dse.appl.ej.client.EJDetailPanelListener, com.ibm.dse.appl.ej.client.EJSummaryPanelListener, java.awt.event.ActionListener {
		public void actionPerformed(java.awt.event.ActionEvent e) {
			if (e.getSource() == EJViewer.this.getQueryComboBox())
				connEtoC1(e);
			if (e.getSource() == EJViewer.this.getUpdateViewButton())
				connEtoC2(e);
			if (e.getSource() == EJViewer.this.getQueryComboBox())
				connEtoC6(e);
		};
		public void detailsButtonAction_actionPerformed(java.util.EventObject newEvent) {
			if (newEvent.getSource() == EJViewer.this.getSummaryPanel())
				connEtoC4(newEvent);
		};
		public void detailsCloseButtonAction_actionPerformed(java.util.EventObject newEvent) {
		};
		public void detailsHelpButtonAction_actionPerformed(java.util.EventObject newEvent) {
		};
		public void scrollPaneTableAction_actionPerformed(java.util.EventObject newEvent) {
			if (newEvent.getSource() == EJViewer.this.getSummaryPanel())
				connEtoC3(newEvent);
		};
		public void summaryButtonAction_actionPerformed(java.util.EventObject newEvent) {
			if (newEvent.getSource() == EJViewer.this.getDetailPanel())
				connEtoC5(newEvent);
		};
		public void summaryCloseButtonAction_actionPerformed(java.util.EventObject newEvent) {
		};
		public void summaryHelpButtonAction_actionPerformed(java.util.EventObject newEvent) {
		};
	}
	private EJDetailPanel ivjDetailPanel = null;
	private javax.swing.JPanel ivjInputPanel = null;
	private javax.swing.JLabel ivjJLabel1 = null;
	private SpButton ivjQueryButton = null;
	private javax.swing.JPanel ivjQueryChoicePanel = null;
	private SpComboBox ivjQueryComboBox = null;
	private javax.swing.JPanel ivjQueryDummyPanel = null;
	private javax.swing.JPanel ivjQueryPanel = null;
	private javax.swing.JPanel ivjResultPanel = null;
	private EJSummaryPanel ivjSummaryPanel = null;
	private SpButton ivjUpdateViewButton = null;
	private javax.swing.JPanel ivjWidgetPanel = null;
	private javax.swing.JScrollPane ivjWidgetScrollPane = null;
	private javax.swing.JPanel ivjScrollPanePanel = null;
	private Hashtable queries;
	private Hashtable convertersTable = null;
	private String viewOperationName = "ejClientOp";
	private String viewerName = "Viewer";
	private String journalAlias = "Journal";
	private String viewerType = "log";
	private String viewerMode = "admin";
	private String schemaInfoOperationName = "retrieveSchemaInfoClientOp";
	private String xmlParseImplementer = "com.ibm.dse.appl.ej.client.EJQueryParserImpl";
//	private String xmlFile = "c:\\dse\\xml\\ejview.xml"; // includes full path
	private String xmlFile = "http://127.0.0.1:80/MatafServer/dse/ej/ejview"; // includes full path
	IvjEventHandler ivjEventHandler = new IvjEventHandler();
	private javax.swing.JLabel ivjDescriptionLabel = null;
	private javax.swing.JLabel ivjDescriptionTitleLabel = null;
	private SpErrorLabel ivjFormatErrorLabel = null;
	private javax.swing.JPanel ivjQueryButtonPanel = null;
	private javax.swing.JPanel ivjUpdateButtonPanel = null;
	private javax.swing.JPanel ivjJPanel1 = null;
	private java.awt.FlowLayout ivjJPanel1FlowLayout = null;
	private javax.swing.JPanel ivjJPanel2 = null;
	private java.awt.FlowLayout ivjUpdateButtonPanelFlowLayout = null;
	private SpComboBox ivjEntitiesComboBox = null;
	private javax.swing.JLabel ivjEntitiesLabel = null;
	private SpComboBox ivjGenNumberComboBox = null;
	private javax.swing.JLabel ivjGenNumberLabel = null;

	/* Trace component identification */
	public java.lang.String COMPID = "#EJ";
	/**
	 * EJViewer constructor.
	 */
	public EJViewer() {
		super();
		initialize();
	}
	/**
	 * EJViewer constructor.
	 * @param layout java.awt.LayoutManager
	 */
	public EJViewer(java.awt.LayoutManager layout) {
		super(layout);
	}
	/**
	 * EJViewer constructor.
	 * @param layout java.awt.LayoutManager
	 * @param isDoubleBuffered boolean
	 */
	public EJViewer(java.awt.LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
	}
	/**
	 * EJViewer constructor.
	 * 
	 * @param aViewerName java.lang.String
	 */
	public EJViewer(String aViewerName) {
		this();
		setViewerName(aViewerName);
		initialize();
	}
	/**
	 * EJViewer constructor.
	 * @param o com.ibm.dse.base.Operation
	 */
	public EJViewer(com.ibm.dse.base.Operation o) {
		super(o);
	}
	/**
	 * EJViewer constructor.
	 * @param isDoubleBuffered boolean
	 */
	public EJViewer(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
	}
	/**
	 * connEtoC1:  (QueryComboBox.action.actionPerformed(java.awt.event.ActionEvent) --> EJViewerOP.reInitializeView()V)
	 * @param arg1 java.awt.event.ActionEvent
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private void connEtoC1(java.awt.event.ActionEvent arg1) {
		try {
			// user code begin {1}
			// user code end
			this.reInitializeView();
			// user code begin {2}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {3}
			// user code end
			handleException(ivjExc);
		}
	}
	/**
	 * connEtoC2:  (UpdateViewButton.action.actionPerformed(java.awt.event.ActionEvent) --> EJViewerOP.updateView()V)
	 * @param arg1 java.awt.event.ActionEvent
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private void connEtoC2(java.awt.event.ActionEvent arg1) {
		try {
			// user code begin {1}
			// user code end
			this.updateView();
			// user code begin {2}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {3}
			// user code end
			handleException(ivjExc);
		}
	}
	/**
	 * connEtoC3:  (SummaryPanel.EJSummaryPanel.scrollPaneTableAction_actionPerformed(java.util.EventObject) --> EJViewerOP.enableDetailsButton()V)
	 * @param arg1 java.util.EventObject
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private void connEtoC3(java.util.EventObject arg1) {
		try {
			// user code begin {1}
			// user code end
			this.enableDetailsButton();
			// user code begin {2}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {3}
			// user code end
			handleException(ivjExc);
		}
	}
	/**
	 * connEtoC4:  (SummaryPanel.EJSummaryPanel.detailsButtonAction_actionPerformed(java.util.EventObject) --> EJViewerOP.handleDetailsButton()V)
	 * @param arg1 java.util.EventObject
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private void connEtoC4(java.util.EventObject arg1) {
		try {
			// user code begin {1}
			// user code end
			this.handleDetailsButton();
			// user code begin {2}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {3}
			// user code end
			handleException(ivjExc);
		}
	}
	/**
	 * connEtoC5:  (DetailPanel.EJDetailPanel.summaryButtonAction_actionPerformed(java.util.EventObject) --> EJViewerOP.handleSummaryButton()V)
	 * @param arg1 java.util.EventObject
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private void connEtoC5(java.util.EventObject arg1) {
		try {
			// user code begin {1}
			// user code end
			this.handleSummaryButton();
			// user code begin {2}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {3}
			// user code end
			handleException(ivjExc);
		}
	}
	/**
	 * connEtoC6:  (QueryComboBox.action.actionPerformed(java.awt.event.ActionEvent) --> EJViewerOP.updateQueryDescription()V)
	 * @param arg1 java.awt.event.ActionEvent
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private void connEtoC6(java.awt.event.ActionEvent arg1) {
		try {
			// user code begin {1}
			// user code end
			this.updateQueryDescription();
			// user code begin {2}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {3}
			// user code end
			handleException(ivjExc);
		}
	}
	/**
	 * Makes sure that users cannot click the details button 
	 * until they make a selection in the table.
	 * 
	 */
	public void enableDetailsButton() {
		getSummaryPanel().getPublicDetailsButton().setEnabled(true);
	}
	/**
	 * Return a converter object
	 * 
	 * @return com.ibm.dse.gui.Converter
	 * @param converterName java.lang.String
	 */
	private Converter getConverter(String converterName) {
		Converter aConverter = null;
		aConverter = (Converter) getConvertersTable().get(converterName);
		return aConverter;
	}
	/**
	 * Returns the convertersTable attribute.
	 * 
	 * @return java.util.Hashtable
	 */
	public Hashtable getConvertersTable() {
		return convertersTable;
	}
	/**
	 * Return the DescriptionLabel property value.
	 * @return javax.swing.JLabel
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private javax.swing.JLabel getDescriptionLabel() {
		if (ivjDescriptionLabel == null) {
			try {
				ivjDescriptionLabel = new javax.swing.JLabel();
				ivjDescriptionLabel.setName("DescriptionLabel");
				ivjDescriptionLabel.setFont(new java.awt.Font("dialog", 0, 12));
				ivjDescriptionLabel.setText("");
				ivjDescriptionLabel.setMaximumSize(new java.awt.Dimension(300, 15));
				ivjDescriptionLabel.setForeground(java.awt.Color.black);
				// user code begin {1}
				// user code end
			} catch (java.lang.Throwable ivjExc) {
				// user code begin {2}
				// user code end
				handleException(ivjExc);
			}
		}
		return ivjDescriptionLabel;
	}
	/**
	 * Return the DescriptionTitleLabel property value.
	 * @return javax.swing.JLabel
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private javax.swing.JLabel getDescriptionTitleLabel() {
		if (ivjDescriptionTitleLabel == null) {
			try {
				ivjDescriptionTitleLabel = new javax.swing.JLabel();
				ivjDescriptionTitleLabel.setName("DescriptionTitleLabel");
				ivjDescriptionTitleLabel.setText("Query description :");
				// user code begin {1}
				// user code end
			} catch (java.lang.Throwable ivjExc) {
				// user code begin {2}
				// user code end
				handleException(ivjExc);
			}
		}
		return ivjDescriptionTitleLabel;
	}
	/**
	 * Return the DetailPanel property value.
	 * @return com.ibm.dse.appl.ej.client.EJDetailPanel
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private EJDetailPanel getDetailPanel() {
		if (ivjDetailPanel == null) {
			try {
				ivjDetailPanel = new com.ibm.dse.appl.ej.client.EJDetailPanel();
				ivjDetailPanel.setName("DetailPanel");
				// user code begin {1}
				// user code end
			} catch (java.lang.Throwable ivjExc) {
				// user code begin {2}
				// user code end
				handleException(ivjExc);
			}
		}
		return ivjDetailPanel;
	}
	/**
	 * Return the EntitiesComboBox property value.
	 * @return com.ibm.dse.gui.SpComboBox
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private com.ibm.dse.gui.SpComboBox getEntitiesComboBox() {
		if (ivjEntitiesComboBox == null) {
			try {
				ivjEntitiesComboBox = new com.ibm.dse.gui.SpComboBox();
				ivjEntitiesComboBox.setName("EntitiesComboBox");
				ivjEntitiesComboBox.setDataName("entityName");
				ivjEntitiesComboBox.setPreferredSize(new java.awt.Dimension(110, 25));
				ivjEntitiesComboBox.setMinimumSize(new java.awt.Dimension(50, 25));
				ivjEntitiesComboBox.setMaximumSize(new java.awt.Dimension(32767, 25));
				// user code begin {1}
				ivjEntitiesComboBox.addCoordinatedEventListener(this);
				ivjEntitiesComboBox.addActionListener(ivjEventHandler);
				// user code end
			} catch (java.lang.Throwable ivjExc) {
				// user code begin {2}
				// user code end
				handleException(ivjExc);
			}
		}
		return ivjEntitiesComboBox;
	}
	/**
	 * Return the EntitiesLabel property value.
	 * @return javax.swing.JLabel
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private javax.swing.JLabel getEntitiesLabel() {
		if (ivjEntitiesLabel == null) {
			try {
				ivjEntitiesLabel = new javax.swing.JLabel();
				ivjEntitiesLabel.setName("EntitiesLabel");
				ivjEntitiesLabel.setText("Entities");
				// user code begin {1}
				// user code end
			} catch (java.lang.Throwable ivjExc) {
				// user code begin {2}
				// user code end
				handleException(ivjExc);
			}
		}
		return ivjEntitiesLabel;
	}
	/**
	 * Return the FormatErrorLabel property value.
	 * @return com.ibm.dse.gui.SpErrorLabel
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private com.ibm.dse.gui.SpErrorLabel getFormatErrorLabel() {
		if (ivjFormatErrorLabel == null) {
			try {
				ivjFormatErrorLabel = new com.ibm.dse.gui.SpErrorLabel();
				ivjFormatErrorLabel.setName("FormatErrorLabel");
				ivjFormatErrorLabel.setPreferredSize(new java.awt.Dimension(300, 25));
				ivjFormatErrorLabel.setText(" ");
				ivjFormatErrorLabel.setMaximumSize(new java.awt.Dimension(400, 25));
				ivjFormatErrorLabel.setMinimumSize(new java.awt.Dimension(200, 25));
				// user code begin {1}
				// user code end
			} catch (java.lang.Throwable ivjExc) {
				// user code begin {2}
				// user code end
				handleException(ivjExc);
			}
		}
		return ivjFormatErrorLabel;
	}
	/**
	 * Return the GenNumberComboBox property value.
	 * @return com.ibm.dse.gui.SpComboBox
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private com.ibm.dse.gui.SpComboBox getGenNumberComboBox() {
		if (ivjGenNumberComboBox == null) {
			try {
				ivjGenNumberComboBox = new com.ibm.dse.gui.SpComboBox();
				ivjGenNumberComboBox.setName("GenNumberComboBox");
				ivjGenNumberComboBox.setDataName("generationNum");
				ivjGenNumberComboBox.setPreferredSize(new java.awt.Dimension(35, 25));
				ivjGenNumberComboBox.setMinimumSize(new java.awt.Dimension(25, 25));
				ivjGenNumberComboBox.setMaximumSize(new java.awt.Dimension(32767, 25));
				// user code begin {1}
				ivjGenNumberComboBox.addCoordinatedEventListener(this);
				ivjGenNumberComboBox.addActionListener(ivjEventHandler);
				// user code end
			} catch (java.lang.Throwable ivjExc) {
				// user code begin {2}
				// user code end
				handleException(ivjExc);
			}
		}
		return ivjGenNumberComboBox;
	}
	/**
	 * Return the GenNumberLabel property value.
	 * @return javax.swing.JLabel
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private javax.swing.JLabel getGenNumberLabel() {
		if (ivjGenNumberLabel == null) {
			try {
				ivjGenNumberLabel = new javax.swing.JLabel();
				ivjGenNumberLabel.setName("GenNumberLabel");
				ivjGenNumberLabel.setText("Gen #");
				// user code begin {1}
				// user code end
			} catch (java.lang.Throwable ivjExc) {
				// user code begin {2}
				// user code end
				handleException(ivjExc);
			}
		}
		return ivjGenNumberLabel;
	}
	/**
	 * Return the InputPanel property value.
	 * @return javax.swing.JPanel
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private javax.swing.JPanel getInputPanel() {
		if (ivjInputPanel == null) {
			try {
				ivjInputPanel = new javax.swing.JPanel();
				ivjInputPanel.setName("InputPanel");
				ivjInputPanel.setPreferredSize(new java.awt.Dimension(680, 200));
				ivjInputPanel.setBorder(new javax.swing.border.EtchedBorder());
				ivjInputPanel.setLayout(new java.awt.GridBagLayout());
				ivjInputPanel.setMinimumSize(new java.awt.Dimension(100, 100));

				java.awt.GridBagConstraints constraintsWidgetPanel = new java.awt.GridBagConstraints();
				constraintsWidgetPanel.gridx = 0;
				constraintsWidgetPanel.gridy = 0;
				constraintsWidgetPanel.fill = java.awt.GridBagConstraints.BOTH;
				constraintsWidgetPanel.weightx = 1.0;
				constraintsWidgetPanel.weighty = 12.0;
				getInputPanel().add(getWidgetPanel(), constraintsWidgetPanel);
				// user code begin {1}
				// user code end
			} catch (java.lang.Throwable ivjExc) {
				// user code begin {2}
				// user code end
				handleException(ivjExc);
			}
		}
		return ivjInputPanel;
	}
	/**
	 * This method takes in a vector of EJParameters 
	 * and returns a vector of the Items' names for the combo box drop down list.
	 * 
	 * @return java.util.Vector
	 * @param aList java.util.Vector
	 */
	private Vector getItemList(Vector aList) {
		int itemCount = aList.size();
		Vector nameList = new Vector(itemCount);
		for (int j = 0; j < itemCount; j++) {
			EJParameter aParam = (EJParameter) aList.elementAt(j);
			if (aParam.getName().equals("Param"))
				nameList.addElement(aParam.getLabel());
		}
		return nameList;
	}
	/**
	 * Return the JLabel1 property value.
	 * @return javax.swing.JLabel
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private javax.swing.JLabel getJLabel1() {
		if (ivjJLabel1 == null) {
			try {
				ivjJLabel1 = new javax.swing.JLabel();
				ivjJLabel1.setName("JLabel1");
				ivjJLabel1.setText("Query name");
				// user code begin {1}
				// user code end
			} catch (java.lang.Throwable ivjExc) {
				// user code begin {2}
				// user code end
				handleException(ivjExc);
			}
		}
		return ivjJLabel1;
	}
	/**
	 * Returns the journalAlias attribute.
	 * 
	 * @return java.lang.String
	 */
	public String getJournalAlias() {
		return journalAlias;
	}
	/**
	 * Return the JPanel1 property value.
	 * @return javax.swing.JPanel
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private javax.swing.JPanel getJPanel1() {
		if (ivjJPanel1 == null) {
			try {
				ivjJPanel1 = new javax.swing.JPanel();
				ivjJPanel1.setName("JPanel1");
				ivjJPanel1.setPreferredSize(new java.awt.Dimension(115, 35));
				ivjJPanel1.setLayout(getJPanel1FlowLayout());
				ivjJPanel1.setMinimumSize(new java.awt.Dimension(115, 35));
				ivjJPanel1.setMaximumSize(new java.awt.Dimension(32767, 35));
				getJPanel1().add(getQueryButton(), getQueryButton().getName());
				// user code begin {1}
				// user code end
			} catch (java.lang.Throwable ivjExc) {
				// user code begin {2}
				// user code end
				handleException(ivjExc);
			}
		}
		return ivjJPanel1;
	}
	/**
	 * Return the JPanel1FlowLayout property value.
	 * @return java.awt.FlowLayout
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private java.awt.FlowLayout getJPanel1FlowLayout() {
		java.awt.FlowLayout ivjJPanel1FlowLayout = null;
		try {
			/* Create part */
			ivjJPanel1FlowLayout = new java.awt.FlowLayout();
			ivjJPanel1FlowLayout.setAlignment(java.awt.FlowLayout.RIGHT);
			ivjJPanel1FlowLayout.setVgap(10);
			ivjJPanel1FlowLayout.setHgap(10);
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		};
		return ivjJPanel1FlowLayout;
	}
	/**
	 * Return the JPanel2 property value.
	 * @return javax.swing.JPanel
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private javax.swing.JPanel getJPanel2() {
		if (ivjJPanel2 == null) {
			try {
				ivjJPanel2 = new javax.swing.JPanel();
				ivjJPanel2.setName("JPanel2");
				ivjJPanel2.setPreferredSize(new java.awt.Dimension(320, 35));
				ivjJPanel2.setLayout(new java.awt.GridBagLayout());
				ivjJPanel2.setMinimumSize(new java.awt.Dimension(220, 35));
				ivjJPanel2.setMaximumSize(new java.awt.Dimension(2147483647, 35));

				java.awt.GridBagConstraints constraintsFormatErrorLabel = new java.awt.GridBagConstraints();
				constraintsFormatErrorLabel.gridx = 0;
				constraintsFormatErrorLabel.gridy = 0;
				constraintsFormatErrorLabel.gridwidth = 2;
				constraintsFormatErrorLabel.fill = java.awt.GridBagConstraints.BOTH;
				constraintsFormatErrorLabel.insets = new java.awt.Insets(0, 10, 0, 10);
				getJPanel2().add(getFormatErrorLabel(), constraintsFormatErrorLabel);
				// user code begin {1}
				// user code end
			} catch (java.lang.Throwable ivjExc) {
				// user code begin {2}
				// user code end
				handleException(ivjExc);
			}
		}
		return ivjJPanel2;
	}
	/**
	 * Return the QueryButton property value.
	 * @return com.ibm.dse.gui.SpButton
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private com.ibm.dse.gui.SpButton getQueryButton() {
		if (ivjQueryButton == null) {
			try {
				ivjQueryButton = new com.ibm.dse.gui.SpButton();
				ivjQueryButton.setName("QueryButton");
				ivjQueryButton.setText("Query");
				ivjQueryButton.setMaximumSize(new java.awt.Dimension(95, 25));
				ivjQueryButton.setPreferredSize(new java.awt.Dimension(95, 25));
				ivjQueryButton.setType("Ok");
				ivjQueryButton.setMinimumSize(new java.awt.Dimension(95, 25));
				// user code begin {1}
				// user code end
			} catch (java.lang.Throwable ivjExc) {
				// user code begin {2}
				// user code end
				handleException(ivjExc);
			}
		}
		return ivjQueryButton;
	}
	/**
	 * Return the ButtonPanel property value.
	 * @return javax.swing.JPanel
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private javax.swing.JPanel getQueryButtonPanel() {
		if (ivjQueryButtonPanel == null) {
			try {
				ivjQueryButtonPanel = new javax.swing.JPanel();
				ivjQueryButtonPanel.setName("QueryButtonPanel");
				ivjQueryButtonPanel.setBorder(new javax.swing.border.EtchedBorder());
				ivjQueryButtonPanel.setLayout(new java.awt.GridBagLayout());
				ivjQueryButtonPanel.setMaximumSize(new java.awt.Dimension(32767, 25));
				ivjQueryButtonPanel.setPreferredSize(new java.awt.Dimension(680, 25));
				ivjQueryButtonPanel.setMinimumSize(new java.awt.Dimension(0, 25));

				java.awt.GridBagConstraints constraintsJPanel1 = new java.awt.GridBagConstraints();
				constraintsJPanel1.gridx = 1;
				constraintsJPanel1.gridy = 0;
				constraintsJPanel1.fill = java.awt.GridBagConstraints.BOTH;
				constraintsJPanel1.weightx = 1.0;
				constraintsJPanel1.weighty = 1.0;
				getQueryButtonPanel().add(getJPanel1(), constraintsJPanel1);

				java.awt.GridBagConstraints constraintsJPanel2 = new java.awt.GridBagConstraints();
				constraintsJPanel2.gridx = 0;
				constraintsJPanel2.gridy = 0;
				constraintsJPanel2.fill = java.awt.GridBagConstraints.BOTH;
				constraintsJPanel2.weightx = 1.0;
				constraintsJPanel2.weighty = 1.0;
				getQueryButtonPanel().add(getJPanel2(), constraintsJPanel2);
				// user code begin {1}
				// user code end
			} catch (java.lang.Throwable ivjExc) {
				// user code begin {2}
				// user code end
				handleException(ivjExc);
			}
		}
		return ivjQueryButtonPanel;
	}
	/**
	 * Return the QueryChoicePanel property value.
	 * @return javax.swing.JPanel
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private javax.swing.JPanel getQueryChoicePanel() {
		if (ivjQueryChoicePanel == null) {
			try {
				ivjQueryChoicePanel = new javax.swing.JPanel();
				ivjQueryChoicePanel.setName("QueryChoicePanel");
				ivjQueryChoicePanel.setBorder(new javax.swing.border.EtchedBorder());
				ivjQueryChoicePanel.setLayout(new java.awt.GridBagLayout());

				java.awt.GridBagConstraints constraintsJLabel1 = new java.awt.GridBagConstraints();
				constraintsJLabel1.gridx = 0;
				constraintsJLabel1.gridy = 0;
				constraintsJLabel1.anchor = java.awt.GridBagConstraints.NORTHWEST;
				constraintsJLabel1.insets = new java.awt.Insets(10, 10, 10, 0);
				getQueryChoicePanel().add(getJLabel1(), constraintsJLabel1);

				java.awt.GridBagConstraints constraintsQueryComboBox = new java.awt.GridBagConstraints();
				constraintsQueryComboBox.gridx = 0;
				constraintsQueryComboBox.gridy = 1;
				constraintsQueryComboBox.fill = java.awt.GridBagConstraints.HORIZONTAL;
				constraintsQueryComboBox.anchor = java.awt.GridBagConstraints.NORTHEAST;
				constraintsQueryComboBox.weightx = 1.0;
				constraintsQueryComboBox.insets = new java.awt.Insets(0, 10, 10, 100);
				getQueryChoicePanel().add(getQueryComboBox(), constraintsQueryComboBox);

				java.awt.GridBagConstraints constraintsQueryDummyPanel = new java.awt.GridBagConstraints();
				constraintsQueryDummyPanel.gridx = 0;
				constraintsQueryDummyPanel.gridy = 4;
				constraintsQueryDummyPanel.fill = java.awt.GridBagConstraints.BOTH;
				constraintsQueryDummyPanel.weightx = 1.0;
				constraintsQueryDummyPanel.weighty = 1.0;
				getQueryChoicePanel().add(getQueryDummyPanel(), constraintsQueryDummyPanel);

				java.awt.GridBagConstraints constraintsDescriptionTitleLabel = new java.awt.GridBagConstraints();
				constraintsDescriptionTitleLabel.gridx = 0;
				constraintsDescriptionTitleLabel.gridy = 2;
				constraintsDescriptionTitleLabel.anchor = java.awt.GridBagConstraints.WEST;
				constraintsDescriptionTitleLabel.insets = new java.awt.Insets(25, 10, 5, 0);
				getQueryChoicePanel().add(getDescriptionTitleLabel(), constraintsDescriptionTitleLabel);

				java.awt.GridBagConstraints constraintsDescriptionLabel = new java.awt.GridBagConstraints();
				constraintsDescriptionLabel.gridx = 0;
				constraintsDescriptionLabel.gridy = 3;
				constraintsDescriptionLabel.fill = java.awt.GridBagConstraints.HORIZONTAL;
				constraintsDescriptionLabel.insets = new java.awt.Insets(5, 10, 0, 10);
				getQueryChoicePanel().add(getDescriptionLabel(), constraintsDescriptionLabel);
				// user code begin {1}
				// user code end
			} catch (java.lang.Throwable ivjExc) {
				// user code begin {2}
				// user code end
				handleException(ivjExc);
			}
		}
		return ivjQueryChoicePanel;
	}
	/**
	 * Return the QueryComboBox property value.
	 * @return com.ibm.dse.gui.SpComboBox
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private com.ibm.dse.gui.SpComboBox getQueryComboBox() {
		if (ivjQueryComboBox == null) {
			try {
				ivjQueryComboBox = new com.ibm.dse.gui.SpComboBox();
				ivjQueryComboBox.setName("QueryComboBox");
				ivjQueryComboBox.setPreferredSize(new java.awt.Dimension(130, 25));
				ivjQueryComboBox.setMaximumSize(new java.awt.Dimension(32767, 25));
				ivjQueryComboBox.setMinimumSize(new java.awt.Dimension(50, 25));
				// user code begin {1}
				// user code end
			} catch (java.lang.Throwable ivjExc) {
				// user code begin {2}
				// user code end
				handleException(ivjExc);
			}
		}
		return ivjQueryComboBox;
	}
	/**
	 * Return the QueryDummyPanel property value.
	 * @return javax.swing.JPanel
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private javax.swing.JPanel getQueryDummyPanel() {
		if (ivjQueryDummyPanel == null) {
			try {
				ivjQueryDummyPanel = new javax.swing.JPanel();
				ivjQueryDummyPanel.setName("QueryDummyPanel");
				ivjQueryDummyPanel.setLayout(new java.awt.GridBagLayout());

				java.awt.GridBagConstraints constraintsEntitiesLabel = new java.awt.GridBagConstraints();
				constraintsEntitiesLabel.gridx = 0;
				constraintsEntitiesLabel.gridy = 0;
				constraintsEntitiesLabel.anchor = java.awt.GridBagConstraints.WEST;
				constraintsEntitiesLabel.weightx = 10.0;
				constraintsEntitiesLabel.insets = new java.awt.Insets(0, 10, 5, 0);
				getQueryDummyPanel().add(getEntitiesLabel(), constraintsEntitiesLabel);

				java.awt.GridBagConstraints constraintsGenNumberLabel = new java.awt.GridBagConstraints();
				constraintsGenNumberLabel.gridx = 1;
				constraintsGenNumberLabel.gridy = 0;
				constraintsGenNumberLabel.anchor = java.awt.GridBagConstraints.WEST;
				constraintsGenNumberLabel.insets = new java.awt.Insets(0, 5, 5, 0);
				getQueryDummyPanel().add(getGenNumberLabel(), constraintsGenNumberLabel);

				java.awt.GridBagConstraints constraintsEntitiesComboBox = new java.awt.GridBagConstraints();
				constraintsEntitiesComboBox.gridx = 0;
				constraintsEntitiesComboBox.gridy = 1;
				constraintsEntitiesComboBox.fill = java.awt.GridBagConstraints.HORIZONTAL;
				constraintsEntitiesComboBox.weightx = 10.0;
				constraintsEntitiesComboBox.insets = new java.awt.Insets(5, 10, 0, 30);
				getQueryDummyPanel().add(getEntitiesComboBox(), constraintsEntitiesComboBox);

				java.awt.GridBagConstraints constraintsGenNumberComboBox = new java.awt.GridBagConstraints();
				constraintsGenNumberComboBox.gridx = 1;
				constraintsGenNumberComboBox.gridy = 1;
				constraintsGenNumberComboBox.fill = java.awt.GridBagConstraints.HORIZONTAL;
				constraintsGenNumberComboBox.weightx = 1.0;
				constraintsGenNumberComboBox.insets = new java.awt.Insets(5, 5, 0, 10);
				getQueryDummyPanel().add(getGenNumberComboBox(), constraintsGenNumberComboBox);
				// user code begin {1}
				// user code end
			} catch (java.lang.Throwable ivjExc) {
				// user code begin {2}
				// user code end
				handleException(ivjExc);
			}
		}
		return ivjQueryDummyPanel;
	}
	/**
	 * Return the QueryPanel property value.
	 * @return javax.swing.JPanel
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private javax.swing.JPanel getQueryPanel() {
		if (ivjQueryPanel == null) {
			try {
				ivjQueryPanel = new javax.swing.JPanel();
				ivjQueryPanel.setName("QueryPanel");
				ivjQueryPanel.setBorder(new javax.swing.border.EtchedBorder());
				ivjQueryPanel.setLayout(new java.awt.GridBagLayout());
				ivjQueryPanel.setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));
				ivjQueryPanel.setPreferredSize(new java.awt.Dimension(680, 200));
				ivjQueryPanel.setMinimumSize(new java.awt.Dimension(100, 100));

				java.awt.GridBagConstraints constraintsQueryChoicePanel = new java.awt.GridBagConstraints();
				constraintsQueryChoicePanel.gridx = 0;
				constraintsQueryChoicePanel.gridy = 0;
				constraintsQueryChoicePanel.fill = java.awt.GridBagConstraints.BOTH;
				constraintsQueryChoicePanel.weightx = 1.0;
				constraintsQueryChoicePanel.weighty = 12.0;
				getQueryPanel().add(getQueryChoicePanel(), constraintsQueryChoicePanel);
				// user code begin {1}
				// user code end
			} catch (java.lang.Throwable ivjExc) {
				// user code begin {2}
				// user code end
				handleException(ivjExc);
			}
		}
		return ivjQueryPanel;
	}
	/**
	 * Return the ResultPanel property value.
	 * @return javax.swing.JPanel
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private javax.swing.JPanel getResultPanel() {
		if (ivjResultPanel == null) {
			try {
				ivjResultPanel = new javax.swing.JPanel();
				ivjResultPanel.setName("ResultPanel");
				ivjResultPanel.setBorder(new javax.swing.border.EtchedBorder());
				ivjResultPanel.setLayout(new java.awt.CardLayout());
				ivjResultPanel.setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));
				ivjResultPanel.setPreferredSize(new java.awt.Dimension(680, 300));
				ivjResultPanel.setMinimumSize(new java.awt.Dimension(350, 100));
				getResultPanel().add(getDetailPanel(), getDetailPanel().getName());
				getResultPanel().add(getSummaryPanel(), getSummaryPanel().getName());
				// user code begin {1}
				// user code end
			} catch (java.lang.Throwable ivjExc) {
				// user code begin {2}
				// user code end
				handleException(ivjExc);
			}
		}
		return ivjResultPanel;
	}
	/**
	 * Returns the schemaInfoOperationName attribute value.
	 * 
	 * @return java.lang.String
	 */
	public String getSchemaInfoOperationName() {
		return schemaInfoOperationName;
	}
	/**
	 * Return the ScrollPanePanel property value.
	 * @return javax.swing.JPanel
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private javax.swing.JPanel getScrollPanePanel() {
		if (ivjScrollPanePanel == null) {
			try {
				ivjScrollPanePanel = new javax.swing.JPanel();
				ivjScrollPanePanel.setName("ScrollPanePanel");
				ivjScrollPanePanel.setLayout(null);
				ivjScrollPanePanel.setBounds(0, 0, 160, 120);
				ivjScrollPanePanel.setMaximumSize(new java.awt.Dimension(32767, 32767));
				ivjScrollPanePanel.setMinimumSize(new java.awt.Dimension(120, 160));
				// user code begin {1}
				// user code end
			} catch (java.lang.Throwable ivjExc) {
				// user code begin {2}
				// user code end
				handleException(ivjExc);
			}
		}
		return ivjScrollPanePanel;
	}
	/**
	 * Return the SummaryPanel property value.
	 * @return com.ibm.dse.appl.ej.client.EJSummaryPanel
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private EJSummaryPanel getSummaryPanel() {
		if (ivjSummaryPanel == null) {
			try {
				ivjSummaryPanel = new com.ibm.dse.appl.ej.client.EJSummaryPanel();
				ivjSummaryPanel.setName("SummaryPanel");
				// user code begin {1}
				// user code end
			} catch (java.lang.Throwable ivjExc) {
				// user code begin {2}
				// user code end
				handleException(ivjExc);
			}
		}
		return ivjSummaryPanel;
	}
	/**
	 * Return the ErrorLabelPanel property value.
	 * @return javax.swing.JPanel
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private javax.swing.JPanel getUpdateButtonPanel() {
		if (ivjUpdateButtonPanel == null) {
			try {
				ivjUpdateButtonPanel = new javax.swing.JPanel();
				ivjUpdateButtonPanel.setName("UpdateButtonPanel");
				ivjUpdateButtonPanel.setBorder(new javax.swing.border.EtchedBorder());
				ivjUpdateButtonPanel.setLayout(getUpdateButtonPanelFlowLayout());
				ivjUpdateButtonPanel.setMaximumSize(new java.awt.Dimension(32767, 35));
				ivjUpdateButtonPanel.setPreferredSize(new java.awt.Dimension(680, 35));
				ivjUpdateButtonPanel.setMinimumSize(new java.awt.Dimension(0, 35));
				getUpdateButtonPanel().add(getUpdateViewButton(), getUpdateViewButton().getName());
				// user code begin {1}
				// user code end
			} catch (java.lang.Throwable ivjExc) {
				// user code begin {2}
				// user code end
				handleException(ivjExc);
			}
		}
		return ivjUpdateButtonPanel;
	}
	/**
	 * Return the UpdateButtonPanelFlowLayout property value.
	 * @return java.awt.FlowLayout
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private java.awt.FlowLayout getUpdateButtonPanelFlowLayout() {
		java.awt.FlowLayout ivjUpdateButtonPanelFlowLayout = null;
		try {
			/* Create part */
			ivjUpdateButtonPanelFlowLayout = new java.awt.FlowLayout();
			ivjUpdateButtonPanelFlowLayout.setAlignment(java.awt.FlowLayout.RIGHT);
			ivjUpdateButtonPanelFlowLayout.setVgap(10);
			ivjUpdateButtonPanelFlowLayout.setHgap(10);
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		};
		return ivjUpdateButtonPanelFlowLayout;
	}
	/**
	 * Return the UpdateViewButton property value.
	 * @return com.ibm.dse.gui.SpButton
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private com.ibm.dse.gui.SpButton getUpdateViewButton() {
		if (ivjUpdateViewButton == null) {
			try {
				ivjUpdateViewButton = new com.ibm.dse.gui.SpButton();
				ivjUpdateViewButton.setName("UpdateViewButton");
				ivjUpdateViewButton.setText("Update view");
				// user code begin {1}
				// user code end
			} catch (java.lang.Throwable ivjExc) {
				// user code begin {2}
				// user code end
				handleException(ivjExc);
			}
		}
		return ivjUpdateViewButton;
	}
	/**
	 * Returns the viewerMode attribute.
	 * 
	 * @return java.lang.String
	 */
	public String getViewerMode() {
		return viewerMode;
	}
	/**
	 * Returns the viewerName attribute.
	 * 
	 * @return java.lang.String
	 */
	public String getViewerName() {
		return viewerName;
	}
	/**
	 * Returns the viewerType attribute.
	 * 
	 * @return java.lang.String
	 */
	public String getViewerType() {
		return viewerType;
	}
	/**
	 * Returns the viewOperationName attribute.
	 * 
	 * @return java.lang.String
	 */
	public String getViewOperationName() {
		return viewOperationName;
	}
	/**
	 * Return the WidgetPanel property value.
	 * @return javax.swing.JPanel
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private javax.swing.JPanel getWidgetPanel() {
		if (ivjWidgetPanel == null) {
			try {
				ivjWidgetPanel = new javax.swing.JPanel();
				ivjWidgetPanel.setName("WidgetPanel");
				ivjWidgetPanel.setLayout(new javax.swing.BoxLayout(getWidgetPanel(), javax.swing.BoxLayout.X_AXIS));
				getWidgetPanel().add(getWidgetScrollPane(), getWidgetScrollPane().getName());
				// user code begin {1}
				// user code end
			} catch (java.lang.Throwable ivjExc) {
				// user code begin {2}
				// user code end
				handleException(ivjExc);
			}
		}
		return ivjWidgetPanel;
	}
	/**
	 * Return the JScrollPane1 property value.
	 * @return javax.swing.JScrollPane
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private javax.swing.JScrollPane getWidgetScrollPane() {
		if (ivjWidgetScrollPane == null) {
			try {
				ivjWidgetScrollPane = new javax.swing.JScrollPane();
				ivjWidgetScrollPane.setName("WidgetScrollPane");
				ivjWidgetScrollPane.setVerticalScrollBarPolicy(javax.swing.JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
				ivjWidgetScrollPane.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				ivjWidgetScrollPane.setPreferredSize(new java.awt.Dimension(19, 19));
				getWidgetScrollPane().setViewportView(getScrollPanePanel());
				// user code begin {1}
				// user code end
			} catch (java.lang.Throwable ivjExc) {
				// user code begin {2}
				// user code end
				handleException(ivjExc);
			}
		}
		return ivjWidgetScrollPane;
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
	 * Takes the selected row in the summary table
	 * and returns its full details from the Electronic journal table.
	 */

	public void handleDetailsButton() {
		try {
			int anInt = getSummaryPanel().getScrollTable().getSelectedRow();
			KeyedCollection aKColl = (KeyedCollection) getOperation().getElementAt("ejItems." + anInt);
			IndexedCollection iColl = (IndexedCollection) getOperation().getElementAt("detailsList");
			iColl.removeAll();
			Hashtable kCollHashtable = aKColl.getElements();
			Enumeration anEnum = kCollHashtable.keys();
			while (anEnum.hasMoreElements()) {
				String keyString = (String) anEnum.nextElement();
				Object valueString = ((DataField) kCollHashtable.get(keyString)).getValue();
				KeyedCollection aDataField = (KeyedCollection) DataElement.readObject("detailsData");
				aDataField.setValueAt("key", keyString);
				aDataField.setValueAt("value", valueString);
				iColl.addElement(aDataField);
			}
			getSummaryPanel().setVisible(false);
			getDetailPanel().setVisible(true);
			validate();
		} catch (DSEObjectNotFoundException e) {
			Trace.trace(COMPID, Trace.Severe, Trace.Error, null, e.toString() + ". Could not find ejItems.");
		} catch (DSEInvalidArgumentException e) {
			Trace.trace(COMPID, Trace.Severe, Trace.Error, null, e.toString() + ". Wrong details list key.");
		} catch (IOException e) {
			Trace.trace(COMPID, Trace.Severe, Trace.Error, null, e.toString() + ". I/O error.");
		}
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
	 * Returns from the details table back to the summary table.
	 */

	public void handleSummaryButton() {
		getSummaryPanel().getPublicDetailsButton().setEnabled(false);
		getSummaryPanel().setVisible(true);
		getDetailPanel().setVisible(false);
		validate();

	}
	/**
	 * Initializes connections
	 * @exception java.lang.Exception The exception description.
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private void initConnections() throws java.lang.Exception {
		// user code begin {1}
		// user code end
		getQueryComboBox().addActionListener(ivjEventHandler);
		getUpdateViewButton().addActionListener(ivjEventHandler);
		getSummaryPanel().addEJSummaryPanelListener(ivjEventHandler);
		getDetailPanel().addEJDetailPanelListener(ivjEventHandler);
	}
	/**
	 * Initialize the class.
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private void initialize() {
		try {
			// user code begin {1}
			// user code end
			setName("EJViewer");
			setOperationName("");
			setLayout(new java.awt.GridBagLayout());
			setBorder(new javax.swing.border.EtchedBorder());
			setSize(750, 500);

			java.awt.GridBagConstraints constraintsInputPanel = new java.awt.GridBagConstraints();
			constraintsInputPanel.gridx = 1;
			constraintsInputPanel.gridy = 0;
			constraintsInputPanel.fill = java.awt.GridBagConstraints.BOTH;
			constraintsInputPanel.weightx = 1.0;
			constraintsInputPanel.weighty = 5.0;
			add(getInputPanel(), constraintsInputPanel);

			java.awt.GridBagConstraints constraintsResultPanel = new java.awt.GridBagConstraints();
			constraintsResultPanel.gridx = 0;
			constraintsResultPanel.gridy = 2;
			constraintsResultPanel.gridwidth = 2;
			constraintsResultPanel.fill = java.awt.GridBagConstraints.BOTH;
			constraintsResultPanel.weightx = 1.0;
			constraintsResultPanel.weighty = 10.0;
			add(getResultPanel(), constraintsResultPanel);

			java.awt.GridBagConstraints constraintsQueryPanel = new java.awt.GridBagConstraints();
			constraintsQueryPanel.gridx = 0;
			constraintsQueryPanel.gridy = 0;
			constraintsQueryPanel.fill = java.awt.GridBagConstraints.BOTH;
			constraintsQueryPanel.weightx = 1.0;
			constraintsQueryPanel.weighty = 2.0;
			add(getQueryPanel(), constraintsQueryPanel);

			java.awt.GridBagConstraints constraintsQueryButtonPanel = new java.awt.GridBagConstraints();
			constraintsQueryButtonPanel.gridx = 1;
			constraintsQueryButtonPanel.gridy = 1;
			constraintsQueryButtonPanel.fill = java.awt.GridBagConstraints.BOTH;
			constraintsQueryButtonPanel.weightx = 1.0;
			constraintsQueryButtonPanel.weighty = 1.0;
			add(getQueryButtonPanel(), constraintsQueryButtonPanel);

			java.awt.GridBagConstraints constraintsUpdateButtonPanel = new java.awt.GridBagConstraints();
			constraintsUpdateButtonPanel.gridx = 0;
			constraintsUpdateButtonPanel.gridy = 1;
			constraintsUpdateButtonPanel.fill = java.awt.GridBagConstraints.BOTH;
			constraintsUpdateButtonPanel.weightx = 1.0;
			constraintsUpdateButtonPanel.weighty = 1.0;
			add(getUpdateButtonPanel(), constraintsUpdateButtonPanel);
			initConnections();
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
		// user code begin {2}

		// The operation name is set here for testing purposes.
		// The desktop should take care of that.
		// This shoud be removed when integrated with the desktop.
		if (getOperation() == null) {
			setOperationName("ejClientOp");
		}
//		try {
//			initBTTClientEnv();
//		} catch (Exception e) {
//			e.printStackTrace();
//			return;
//		}
		initializeProperties(getViewerName());
		String schemaInfoOpName = getSchemaInfoOperationName();
		RetrieveSchemaInfoClientOperation initOperation = null;
		try {
			initOperation = (RetrieveSchemaInfoClientOperation) DSEOperation.readObject(schemaInfoOpName);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		try {
			initOperation.execute();
		} catch (Exception e) {
		}
		String fileName = null;
		EJQueryParser parser = null;
		getSummaryPanel().getScrollTable().getSelectionModel().addListSelectionListener(this);
		try {
			// Instantiate the EJWidgetBuilder implementor
			fileName = getXMLParseImplementer();
			parser = (EJQueryParser) Class.forName(fileName).newInstance();
		} catch (ClassNotFoundException e) {
			Trace.trace(COMPID, Trace.Severe, Trace.Error, null, e.toString() + ". Problem with EJQueryParser.");
		} catch (InstantiationException e) {
			Trace.trace(COMPID, Trace.Severe, Trace.Error, null, e.toString() + ". Problem with EJQueryParser.");
		} catch (IllegalAccessException e) {
			Trace.trace(COMPID, Trace.Severe, Trace.Error, null, e.toString() + ". Problem with EJQueryParser.");
		}
		// Parse the XML file
		parser.initXML(getXMLFile());
		queries = parser.getQuerySet().getQueries();
		populateComboBox();
		updateQueryDummyPanel();
		getSummaryPanel().setVisible(true);
		getDetailPanel().setVisible(false);
		getQueryButton().setVisible(false);
		// user code end
	}
	/**
	 * Reads the properties from the XML file and initializes the attributes.
	 * If an attribute is not defined in the XML file, the default value is used.
	 * 
	 * @param aString java.lang.String
	 */
	public void initializeProperties(String aViewerName) {
		try {
			KeyedCollection aKColl = (KeyedCollection) DataElement.readObject(aViewerName);

			// set journalAlias attribute
			String aJAlias = null;
			try {
				aJAlias = (String) aKColl.getValueAt("journalAlias");
			} catch (DSEObjectNotFoundException ne) {
			}
			if (aJAlias != null)
				setJournalAlias(aJAlias);

			// set xmlFile attribute
			String anXMLFile = null;
			try {
				anXMLFile = (String) aKColl.getValueAt("files.xmlFile");
			} catch (DSEObjectNotFoundException ne) {
				Trace.trace(COMPID, Trace.Severe, Trace.Error, null, ne.toString() + ". Could not find xmlFile path.");
			}
			if (anXMLFile != null)
				setXMLFile(anXMLFile);

			// set viewerType attribute
			String aViewerType = null;
			try {
				aViewerType = (String) aKColl.getValueAt("viewerType");
			} catch (DSEObjectNotFoundException ne) {
				Trace.trace(COMPID, Trace.Severe, Trace.Error, null, ne.toString() + ". Could not find viewerType.");
			}
			if (aViewerType != null)
				setViewerType(aViewerType);

			// set viewerMode attribute
			String aViewerMode = null;
			try {
				aViewerMode = (String) aKColl.getValueAt("viewerMode");
			} catch (DSEObjectNotFoundException ne) {
				Trace.trace(COMPID, Trace.Severe, Trace.Error, null, ne.toString() + ". Could not find viewerMode.");
			}
			if (aViewerMode != null)
				setViewerMode(aViewerMode);

			// set schemaInfoOperationName attribute
			String aSchemaInfoOperationName = null;
			try {
				aSchemaInfoOperationName = (String) aKColl.getValueAt("schemaInfoOperationName");
			} catch (DSEObjectNotFoundException ne) {
				Trace.trace(COMPID, Trace.Severe, Trace.Error, null, ne.toString() + ". Could not find schemaInfoOperationName.");
			}
			if (aSchemaInfoOperationName != null)
				setSchemaInfoOperationName(aSchemaInfoOperationName);

			// set xmlParseImplementer attribute
			String anXMLParseImplementer = null;
			try {
				anXMLParseImplementer = (String) aKColl.getValueAt("parsers.xmlFileParseImplementer");
			} catch (DSEObjectNotFoundException ne) {
				Trace.trace(COMPID, Trace.Severe, Trace.Error, null, ne.toString() + ". Could not find xmlFileParseImplementer.");
			}
			if (anXMLParseImplementer != null)
				setXMLParseImplementer(anXMLParseImplementer);

			// set viewOperationName attribute
			String aViewOperationName = null;
			try {
				aViewOperationName = (String) aKColl.getValueAt("viewOperationName");
			} catch (DSEObjectNotFoundException ne) {
				Trace.trace(COMPID, Trace.Severe, Trace.Error, null, ne.toString() + ". Could not find viewOperationName.");
			}
			if (aViewOperationName != null)
				setViewOperationName(aViewOperationName);

			// set convertersTable attribute
			Hashtable aConvertersTable = new Hashtable(5);
			try {
				Converter aDateConverter = (Converter) aKColl.getValueAt("converters.date");
				aConvertersTable.put("date", aDateConverter);
			} catch (DSEObjectNotFoundException ne) {
				Trace.trace(COMPID, Trace.Severe, Trace.Error, null, ne.toString() + ". Could not find date converter.");
			}
			try {
				Converter aPDateConverter = (Converter) aKColl.getValueAt("converters.pdate");
				aConvertersTable.put("pdate", aPDateConverter);
			} catch (DSEObjectNotFoundException ne) {
				Trace.trace(COMPID, Trace.Severe, Trace.Error, null, ne.toString() + ". Could not find pdate converter.");
			}
			try {
				Converter anIntegerConverter = (Converter) aKColl.getValueAt("converters.integer");
				aConvertersTable.put("integer", anIntegerConverter);
			} catch (DSEObjectNotFoundException ne) {
				Trace.trace(COMPID, Trace.Severe, Trace.Error, null, ne.toString() + ". Could not find integer converter.");
			}
			try {
				Converter aFloat2Converter = (Converter) aKColl.getValueAt("converters.float2");
				aConvertersTable.put("float2", aFloat2Converter);
			} catch (DSEObjectNotFoundException ne) {
				Trace.trace(COMPID, Trace.Severe, Trace.Error, null, ne.toString() + ". Could not find float2 converter.");
			}
			try {
				Converter aFloat4Converter = (Converter) aKColl.getValueAt("converters.float4");
				aConvertersTable.put("float4", aFloat4Converter);
			} catch (DSEObjectNotFoundException ne) {
				Trace.trace(COMPID, Trace.Severe, Trace.Error, null, ne.toString() + ". Could not find float4 converter.");
			}
			setConvertersTable(aConvertersTable);
		} catch (IOException e) {
			Trace.trace(COMPID, Trace.Severe, Trace.Error, null, e.toString());
			e.printStackTrace();
		}
	}
	/**
	 * This method is to clear up the view from the previously displayed widgets
	 * 
	 */
	private void initializeView() {

		//getScrollPanePanel().removeAll();
		int compCount = getScrollPanePanel().getComponentCount();
		java.awt.Component[] compArray = getScrollPanePanel().getComponents();
		for (int i = 0; i < compCount; i++) {
			java.awt.Component comp = compArray[i];
			if (comp instanceof DataExchanger) {
				getDataExchangers().removeElement(comp);
				((DataExchanger) comp).addCoordinatedEventListener(this);
				((DataExchanger) comp).addActionListener(ivjEventHandler);
				if (((DataExchanger) comp).isRequired() && (((DataExchanger) comp).getDataValue() == null))
					this.getChildrenNotFilled().removeElement((DataExchanger) comp);
				if (((DataExchanger) comp).isInError())
					this.getChildrenInError().removeElement((DataExchanger) comp);
				if (comp instanceof ErrorMessageGenerator) {
					((ErrorMessageGenerator) comp).removeErrorMessageListener(this);
				}
			}
			getScrollPanePanel().remove(comp);
		}
		int anInt = getSummaryPanel().getSummaryScrollPaneTable().getColumnCount();
		for (int i = 0; i < anInt; i++) {
			TableColumnModel aTableModel = getSummaryPanel().getSummaryScrollPaneTable().getColumnModel();
			TableColumn aColumn = aTableModel.getColumn(0);
			getSummaryPanel().getSummaryScrollPaneTable().removeColumn(aColumn);
		}
		refreshDataExchangers();
	}
	/**
	 * This is the main entrypoint and starts the part when it is run as an application.
	 * @param args java.lang.String[]
	 */
	public static void main(java.lang.String[] args) {
		try {
			javax.swing.JFrame frame = new javax.swing.JFrame();
			EJViewer aEJViewer;
			aEJViewer = new EJViewer();
			frame.setContentPane(aEJViewer);
			frame.setSize(aEJViewer.getSize());
			frame.addWindowListener(new java.awt.event.WindowAdapter() {
				public void windowClosing(java.awt.event.WindowEvent e) {
					System.exit(0);
				};
			});
			frame.setVisible(true);
		} catch (Throwable exception) {
			System.err.println("Exception occurred in main() of com.ibm.dse.gui.OperationPanel");
			exception.printStackTrace(System.out);
		}
	}
	/**
	 * This method is to populate the combo box list with the proper values retrieved from the XML file
	 * 
	 */
	private void populateComboBox() {

		Enumeration enum = queries.keys();
		Vector aVector = new Vector();
		while (enum.hasMoreElements()) {
			aVector.addElement((String) enum.nextElement());
		}
		getQueryComboBox().setListElements(aVector);

	}
	/**
	 * This method is to populate the table with the proper columns as retrieved from the XML file
	 * 
	 * @param aVector java.util.Vector
	 */
	private void processColumns(Vector aVector) {
		int anInt = aVector.size();
		int sizeInt = 0;
		if (100 % anInt > 0) {
			sizeInt = (100 / anInt) + 1;
		} else {
			sizeInt = 100 / anInt;
		}
		VectorEditor aVectorEditor = (new VectorEditor(anInt + 1)).setElemAt("ejItems", 0);
		for (int i = 0; i < anInt; i++) {
			EJColumn aColumn = (EJColumn) aVector.elementAt(i);
			Converter aConverter = getConverter(aColumn.getType());
			aVectorEditor =
				aVectorEditor.setElemAt(
					new com.ibm.dse.gui.ColumnFormatter(aColumn.getName(), aColumn.getLabel(), aConverter, false, false, true, sizeInt),
					i + 1);
		}
		getSummaryPanel().getSummaryScrollPaneTable().setDataNameAndColumns(aVectorEditor);
	}
	/**
	 * This method is to add a combo box to the view based on information retrieved from the XML file
	 * It assumes that the list of entries for the drop down list could be defined in the XML file 
	 * as well as in the context.
	 * It checks for the item name "DataNameForList". If it exists, then it uses the context.
	 * Otherwise it uses the list of items defined in the XML file.
	 * 
	 * @param rowIndex int
	 * @param columnIndex int
	 * @param name String
	 * @param items java.util.Vector
	 */
	private void processComboBox(int rowIndex, int columnIndex, String aName, Vector items) {
		com.ibm.dse.gui.SpComboBox aComboBox = new com.ibm.dse.gui.SpComboBox();
		aComboBox.setName(aName);
		aComboBox.setDataName(aName);
		aComboBox.setDataDirection(com.ibm.dse.gui.Settings.INPUT_DIRECTION);
		aComboBox.setBounds((columnIndex * 180) + 10, (rowIndex * 25) + 40, 150, 25);
		int anInt = items.size();
		String aList = null;
		for (int i = 0; i < anInt; i++) {
			if (((EJParameter) items.elementAt(i)).getName().equals("DataNameForList")) {
				aList = ((EJParameter) items.elementAt(i)).getLabel();
				break;
			}
		}
		if (aList == null) {
			aComboBox.setListElements(getItemList(items));
		} else {
			aComboBox.setDataNameForList(aList);
		}
		aComboBox.setPreferredSize(new java.awt.Dimension(65, 25));
		aComboBox.setMinimumSize(new java.awt.Dimension(65, 25));
		aComboBox.setMaximumSize(new java.awt.Dimension(32767, 25));
		getDataExchangers().addElement(aComboBox);
		aComboBox.addCoordinatedEventListener(this);
		//	aComboBox.addActionListener(ivjEventHandler);
		if (aComboBox.isRequired() && (aComboBox.getDataValue() == null)) {
			if (getChildrenNotFilled().indexOf(aComboBox) == -1)
				getChildrenNotFilled().addElement(aComboBox);
		}
		if (aComboBox.isInError()) {
			if (getChildrenInError().indexOf(aComboBox) == -1)
				getChildrenInError().addElement(aComboBox);
		}
		if (aComboBox instanceof ErrorMessageGenerator) {
			((ErrorMessageGenerator) aComboBox).addErrorMessageListener(this);
		}
		getScrollPanePanel().add(aComboBox);
		try {
			getOperation().setValueAt(aName, aComboBox.getItemAt(0));
		} catch (DSEInvalidArgumentException e) {
			Trace.trace(COMPID, Trace.Severe, Trace.Error, null, e.toString());
		} catch (DSEObjectNotFoundException e) {
			Trace.trace(COMPID, Trace.Severe, Trace.Error, null, e.toString());
		}
	}
	/**
	 * This method is to process which widgets go where in the view 
	 * based on information retrieved from the XML file
	 * 
	 * @param aVector java.util.Vector
	 */
	private void processInputs(Vector aVector) {
		int anInt = aVector.size();
		getScrollPanePanel().repaint();
		getScrollPanePanel().setPreferredSize(new java.awt.Dimension(120, anInt * 30 + 100));
		EJInput anInput = null;
		try {
			for (int i = 0; i < anInt; i++) {
				anInput = (EJInput) aVector.elementAt(i);
				String inputWidgetType = anInput.getWidget().trim();
				String inputLabel = anInput.getLabel().trim();
				String inputName = anInput.getName().trim();
				String widgetFormatType = anInput.getType().trim();
				DataElement aDataElement = (DataElement) (new DataField());
				aDataElement.setName(inputName);
				int columnIndex = i % 2;
				int rowIndex = i / 2;
				try {
					getOperation().getElementAt(inputName).setValue("");
				} catch (DSEObjectNotFoundException e) {
					getOperation().addElement(aDataElement);
				} catch (DSEInvalidArgumentException e) {
					Trace.trace(COMPID, Trace.Severe, Trace.Error, null, e.toString());
				}
				processWidgetLabel(i, 0, inputLabel);
				if (inputWidgetType.equals("textField")) {
					processTextField(i, 1, inputName, widgetFormatType);
					refreshDataExchangers();
				} else if (inputWidgetType.equals("comboBox")) {
					Vector itemVector = anInput.getParameters();
					processComboBox(i, 1, inputName, itemVector);
					refreshDataExchangers();
				}
			}
		} catch (DSEInvalidRequestException ie) {
			Trace.trace(COMPID, Trace.Severe, Trace.Error, null, ie.toString() + ". Problem with adding element.");
		}
	}
	/**
	 * This method is to add a text field to the view based on information retrieved from the XML file
	 * 
	 * @param rowIndex int
	 * @param columnIndex int
	 * @param name String
	 */
	private void processTextField(int rowIndex, int columnIndex, String aName, String aConverter) {
		com.ibm.dse.gui.SpTextField aTextField = new com.ibm.dse.gui.SpTextField();
		aTextField.setName(aName);
		aTextField.setBounds((columnIndex * 180) + 10, (rowIndex * 25) + 40, 150, 25);
		aTextField.setName(aName);
		aTextField.setDataName(aName);
		aTextField.setDataDirection(com.ibm.dse.gui.Settings.INPUT_DIRECTION);
		aTextField.setRequired(true);
		aTextField.setBorder(new javax.swing.plaf.basic.BasicBorders.MarginBorder());
		aTextField.setPreferredSize(new java.awt.Dimension(65, 25));
		aTextField.setMinimumSize(new java.awt.Dimension(65, 25));
		aTextField.setMaximumSize(new java.awt.Dimension(32767, 25));
		Converter fieldConverter = getConverter(aConverter);
		if (fieldConverter != null) {
			aTextField.setFormatter(fieldConverter);
		}
		getDataExchangers().addElement(aTextField);
		aTextField.addCoordinatedEventListener(this);
		//	aTextField.addActionListener((java.awt.event.ActionListener) this);
		if (aTextField.isRequired() && (aTextField.getDataValue() == null)) {
			if (getChildrenNotFilled().indexOf(aTextField) == -1)
				getChildrenNotFilled().addElement(aTextField);
		}
		if (aTextField.isInError()) {
			if (getChildrenInError().indexOf(aTextField) == -1)
				getChildrenInError().addElement(aTextField);
		}
		if (aTextField instanceof ErrorMessageGenerator) {
			((ErrorMessageGenerator) aTextField).addErrorMessageListener(this);
		}
		getScrollPanePanel().add(aTextField);
	}
	/**
	 * This method is to add a label to the view associated with each widget 
	 * based on information retrieved from the XML file
	 * 
	 * @param rowIndex int
	 * @param columnIndex int
	 * @param aName String
	 */
	private void processWidgetLabel(int rowIndex, int columnIndex, String aName) {
		javax.swing.JLabel aLabel = new javax.swing.JLabel();
		aLabel.setText(aName);
		aLabel.setBounds((columnIndex * 180) + 10, (rowIndex * 25) + 40, 150, 23);
		getScrollPanePanel().add(aLabel);
	}
	/**
	 * Cleans up the context and resets the view to the original status before the update button was pressed.
	 * This method is called every time the user changes the query selection.
	 * 
	 */
	public void reInitializeView() {
		initializeView();
		try {
			IndexedCollection iColl1 = (IndexedCollection) getOperation().getElementAt("ejItems");
			IndexedCollection iColl2 = (IndexedCollection) getOperation().getElementAt("detailsList");
			iColl1.removeAll();
			iColl2.removeAll();
		} catch (DSEObjectNotFoundException e) {
			Trace.trace(COMPID, Trace.Severe, Trace.Error, null, e.toString() + ". Could not find ejItems or detailsList.");
		}
		getSummaryPanel().getPublicDetailsButton().setEnabled(false);
		getQueryButton().setVisible(false);
	}
	/**
	 * Sets the convertersTable attribute.
	 * 
	 * @param aTable java.util.Hashtable
	 */
	public void setConvertersTable(Hashtable aTable) {
		convertersTable = aTable;
	}
	/**
	 * Sets the journalAlias attribute.
	 * 
	 * @param aString java.lang.String
	 */
	public void setJournalAlias(String aString) {
		journalAlias = aString;
	}
	/**
	 * Sets the schemaInfoOperationName attribute value.
	 * 
	 * @param aString java.lang.String
	 */
	public void setSchemaInfoOperationName(String aString) {
		schemaInfoOperationName = aString;
	}
	/**
	 * Sets the viewerMode attribute.
	 * 
	 * @param aString java.lang.String
	 */
	public void setViewerMode(String aString) {
		viewerMode = aString;
	}
	/**
	 * Sets the viewerName attribute.
	 * 
	 * @param aString java.lang.String
	 */
	public void setViewerName(String aString) {
		viewerName = aString;
	}
	/**
	 * Sets the viewerType attribute.
	 * 
	 * @param aString java.lang.String
	 */
	public void setViewerType(String aString) {
		viewerType = aString;
	}
	/**
	 * Sets the viewOperationName attribute.
	 * 
	 * @param aString java.lang.String
	 */
	public void setViewOperationName(String aString) {
		viewOperationName = aString;
	}
	/**
	 * This method is to set the string value of the where clause in to the context
	 * 
	 */
	private void setWhereClause() {
		try {
			com.ibm.dse.base.DSEOperation anOperation = (com.ibm.dse.base.DSEOperation) getOperation();
			String aString = ((EJQuery) queries.get(getQueryComboBox().getSelectedItem())).getStatement();
			int anInt = aString.indexOf("where");
			String newString = null;
			if (anInt > 0) {
				newString = aString.substring(anInt + 5).trim();
			} else {
				newString = aString.trim();
			}
			anOperation.setValueAt(EJQueryClientOperation.PROCESS_SQL_STATEMENT, newString);
		} catch (DSEObjectNotFoundException onfe) {
			Trace.trace(COMPID, Trace.Severe, Trace.Error, null, onfe.toString() + ". Problem with operation.");
		} catch (DSEInvalidArgumentException iae) {
			Trace.trace(COMPID, Trace.Severe, Trace.Error, null, iae.toString() + ". Problem with SQL statement.");
		}
	}
	/**
	 * Sets the xmlFile attribute (file name, including the full path).
	 * 
	 * @param aFile java.lang.String
	 */
	public void setXMLFile(String aFile) {
		xmlFile = aFile;
	}
	/**
	 * Sets the xmlParseImplementer attribute.
	 * 
	 * @param aString java.lang.String
	 */
	public void setXMLParseImplementer(String aString) {
		xmlParseImplementer = aString;
	}
	/**
	 * Updates the Query description with the selected query description.
	 * 
	 */
	public void updateQueryDescription() {
		String keyString = (String) getQueryComboBox().getSelectedItem();
		String aString = ((EJQuery) queries.get(keyString)).getDescription();
		if (aString == null || aString.trim().equals("")) {
			getDescriptionTitleLabel().setText(" ");
			getDescriptionLabel().setText(" ");
		} else {
			getDescriptionTitleLabel().setText("Query description :");
			getDescriptionLabel().setText(aString);
		}
	}
	/**
	 * The entities and generation number combo boxes are populated with the proper values if the viewer type is "journal"
	 * otherwise the combo boxes and their labels are set invisible
	 * 
	 */
	private void updateQueryDummyPanel() {
		if (getViewerType().equals("journal")) {
			String schemaInfoOpName = getSchemaInfoOperationName();
			RetrieveSchemaInfoClientOperation initOperation = null;
			try {
				initOperation = (RetrieveSchemaInfoClientOperation) DSEOperation.readObject(schemaInfoOpName);
			} catch (IOException ioe) {
				Trace.trace(COMPID, Trace.Severe, Trace.Error, null, ioe.toString() + ". Could not find schemaInfoOpName.");
			}
			try {
				initOperation.execute();
			} catch (Exception e) {
				Trace.trace(COMPID, Trace.Severe, Trace.Error, null, e.toString());
			}
			Vector entitiesVector = new Vector();
			String entityName = null;
			int genNumber = 0;
			if (getViewerMode().equals("admin")) {
				try {
					Enumeration enum = ((IndexedCollection) initOperation.getElementAt("entities")).getEnumeration();
					while (enum.hasMoreElements()) {
						entitiesVector.addElement(((DataElement) enum.nextElement()).getValue());
					}
					genNumber = ((Long) initOperation.getValueAt("numOfGeneration")).intValue();
				} catch (DSEObjectNotFoundException nfe) {
					Trace.trace(COMPID, Trace.Severe, Trace.Error, null, nfe.toString() + ". Could not find numOfGeneration or entities.");
				}
			} else {
				try {
					entityName = (String) initOperation.getValueAt("entitiyName");
					entitiesVector.addElement(entityName);
					genNumber = ((Long) initOperation.getValueAt("numOfGeneration")).intValue();
				} catch (DSEObjectNotFoundException nfe) {
					Trace.trace(COMPID, Trace.Severe, Trace.Error, null, nfe.toString() + ". Could not find numOfGeneration or entityName.");
				}
			}
			getEntitiesComboBox().setListElements(entitiesVector);
			Vector numVector = new Vector(genNumber);
			for (int i = 0; i < genNumber; i++) {
				numVector.addElement("" + (i + 1));
			}
			getGenNumberComboBox().setListElements(numVector);
			try {
				getOperation().setValueAt("generationNum", getGenNumberComboBox().getItemAt(0));
				getOperation().setValueAt("entityName", getEntitiesComboBox().getItemAt(0));
			} catch (DSEInvalidArgumentException e) {
				Trace.trace(COMPID, Trace.Severe, Trace.Error, null, e.toString());
			} catch (DSEObjectNotFoundException e) {
				Trace.trace(COMPID, Trace.Severe, Trace.Error, null, e.toString() + ". Could not find numOfGeneration or entityName.");
			}
		} else {
			getEntitiesLabel().setVisible(false);
			getGenNumberLabel().setVisible(false);
			getEntitiesComboBox().setVisible(false);
			getGenNumberComboBox().setVisible(false);
		}
	}
	/**
	 * Updates the view based on the query selected
	 * by adding the proper widgets needed to satisfy the query
	 * and adding the right columns in the summary table as expected by the result of the query.
	 */

	public void updateView() {

		reInitializeView();
		String queryString = ((String) getQueryComboBox().getSelectedItem()).trim();
		EJQuery aQuery = (EJQuery) queries.get(queryString);
		Vector columnsVector = aQuery.getResult().getColumns();
		processColumns(columnsVector);
		Vector inputsVector = aQuery.getInputs();
		processInputs(inputsVector);
		setWhereClause();
		getSummaryPanel().setVisible(true);
		getDetailPanel().setVisible(false);
		getQueryButton().setVisible(true);
		validate();

	}
	/**
	 * Detects a selection change and enables the Details button.
	 * 
	 * @param e javax.swing.event.ListSelectionEvent
	 */
	public void valueChanged(javax.swing.event.ListSelectionEvent e) {
		getSummaryPanel().getPublicDetailsButton().setEnabled(true);
	}
	
	public void initBTTClientEnv() throws Exception{
		Context.reset();
		Settings.reset("http://127.0.0.1:80/MatafServer/dse/dse.ini");
		Settings.initializeExternalizers(Settings.MEMORY);
		
		Context ctx = (Context) Context.readObject("workstation");
		((CSClientService) ctx.getService("CSClient")).establishSession();
		
		ClientOperation startup = (ClientOperation) DSEClientOperation.readObject("startupClientOp");
		startup.execute();
	}
}
