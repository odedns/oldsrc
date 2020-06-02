package tests.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingConstants;

import mataf.services.proxy.ProxyService;
import mataf.services.proxy.RTCommands;
import mataf.types.MatafEmbeddedPanel;
import mataf.types.MatafLink;

import com.ibm.dse.base.Context;
import com.ibm.dse.gui.SpButtonGroup;

/**
 * @author Eyal Ben Ze'ev
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class EmptyPanel extends MatafEmbeddedPanel {

	
     private SpButtonGroup group = null;
     private mataf.types.MatafScrollPane matafScrollPane = null;
     private mataf.types.table.MatafTable matafTable = null;
     private mataf.types.MatafButton matafButton = null;
	private mataf.types.MatafLink matafLink = null;
	/**
	 * This method initializes 
	 * 
	 */
	public EmptyPanel() {
		super();
		initialize();
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
        this.add(getMatafScrollPane(), null);
        this.add(getMatafButton(), null);
        this.add(getMatafLink(), null);
        this.setBounds(0,0,780,450);
			
	}
	
	/**
	 * This method initializes matafScrollPane
	 * 
	 * @return mataf.types.MatafScrollPane
	 */
	private mataf.types.MatafScrollPane getMatafScrollPane() {
		if(matafScrollPane == null) {
			matafScrollPane = new mataf.types.MatafScrollPane();
			matafScrollPane.setViewportView(getMatafTable());
			matafScrollPane.setBounds(149, 79, 441, 185);
		}
		return matafScrollPane;
	}
	/**
	 * This method initializes matafTable
	 * 
	 * @return mataf.types.table.MatafTable
	 */
	private mataf.types.table.MatafTable getMatafTable() {
		if(matafTable == null) {
			matafTable = new mataf.types.table.MatafTable();
			matafTable.setDataNameForList("myTableModel");
			matafTable.getOurModel().addColumn(String.class, "Data 1", "tableColumns.data1");
			matafTable.getOurModel().addColumn(String.class, "Data 2", "tableColumns.data2");
			matafTable.getOurModel().addColumn(String.class, "Data 3", "tableColumns.data3");
			matafTable.getOurModel().addColumn(String.class, "Data 4", "tableColumns.data4");
			matafTable.getOurModel().addColumn(String.class, "Data 5", "tableColumns.data5");
			matafTable.getOurModel().addColumn(String.class, "Data 6", "tableColumns.data6");
			matafTable.getOurModel().addColumn(String.class, "Data 7", "tableColumns.data7");
		}
		return matafTable;
	}
	/**
	 * This method initializes matafButton
	 * 
	 * @return mataf.types.MatafButton
	 */
	private mataf.types.MatafButton getMatafButton() {
		if(matafButton == null) {
			matafButton = new mataf.types.MatafButton();
			matafButton.setBounds(154, 316, 92, 27);
			matafButton.setText("דו קליק");
			//matafButton.setType("Execute_Operation");
			//matafButton.setNavigationParameters(new com.ibm.dse.gui.NavigationParameters("processor",2,"testTrxClientOp",0,"","","","","","",0,0,0,0,false,false));
			matafButton.addActionListener(new ActionListener(){

				public void actionPerformed(ActionEvent e)
				{
					// TODO Auto-generated method stub
					try
					{
						Context wksCtx = (Context) Context.getContextNamed("workstationCtx");
						ProxyService m_proxy = (ProxyService) wksCtx.getService("proxyService");
						m_proxy.sendRequest(Integer.parseInt(RTCommands.MANAGERSLIST_COMMAND),"params","0");
					}
					catch(Exception e1)
					{
						
					}
				}
				
			});
		}
		return matafButton;
	}
	/**
	 * This method initializes matafLink
	 * 
	 * @return mataf.types.MatafLink
	 */
	private mataf.types.MatafLink getMatafLink() {
		if(matafLink == null) {
			matafLink = new MatafLink();
			matafLink.setBounds(39, 21, 112, 43);
			matafLink.setSize(99, 48);
			matafLink.setVerticalAlignment(SwingConstants.BOTTOM);
			matafLink.setType("Execute_Operation");
			matafLink.setText("הפעלת OP");
			matafLink.setNavigationParameters(new com.ibm.dse.gui.NavigationParameters("processor",2,"testTrxClientOp",0,"","","","","","",0,0,0,0,false,false));
			matafLink.setLocation(19, 20);
			matafLink.setEnabled(true);
			matafLink.setDataName("LINK1");
		}
		return matafLink;
	}
}  //  @jve:visual-info  decl-index=0 visual-constraint="29,-13"
