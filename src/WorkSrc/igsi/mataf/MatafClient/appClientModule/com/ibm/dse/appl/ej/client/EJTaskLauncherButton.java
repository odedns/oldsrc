package com.ibm.dse.appl.ej.client;


/*_
 * Licensed Materials - Property of IBM
 * Restricted Materials of IBM
 * 5648-D89
 * (C) Copyright IBM Corp. 2003
 * US Government Users Restricted Rights - Use, duplication or disclosure
 * restricted by GSA ADP Schedule Contract with IBM Corp
 */
import com.ibm.dse.desktop.TaskLauncherButton;
import com.ibm.dse.base.DSEOperation;
import com.ibm.dse.base.DataElement;
import com.ibm.dse.base.KeyedCollection;
import com.ibm.dse.base.Trace;
import com.ibm.dse.appl.ej.client.EJSummaryPanel;
import com.ibm.dse.gui.SpTable;
import com.ibm.dse.base.IndexedCollection;
import java.lang.String;
import java.util.Enumeration;


/**
 * This class represents the widget for the dynamic button added in the ejview.xml file.
 * @copyright (c) Copyright IBM Corporation 2003
 */
 
public class EJTaskLauncherButton extends TaskLauncherButton {
	
	
		private static final java.lang.String COPYRIGHT =
		"Licensed Materials - Property of IBM "+//$NON-NLS-1$
		"Restricted Materials of IBM "+//$NON-NLS-1$
		"5648-D89 "+//$NON-NLS-1$
		"(C) Copyright IBM Corp. 2003 All Rights Reserved. "+//$NON-NLS-1$
		"US Government Users Restricted Rights - Use, duplication or disclosure "+//$NON-NLS-1$
		"restricted by GSA ADP Schedule Contract with IBM Corp ";//$NON-NLS-1$

/**
 * The selection attribute has two values: SelectedRow and ResultSet/
 * The selection attribute has to be specified externally in the ejviewer.xml.
 */
	
		public String selection = null;
		
/**
 * The kCollName attribute represents the name of the keyed Collection specified in the data structure (dsedata).
 * The kCollName attribute has to be specified externally in the ejviewer.xml.
 */
		public String kCollName = null;
//		public String COMPID = "#EJ";
		
		
/**
 * This method gets the operation ID from the operation property of the EJTaskLauncherButton.
 * A certain data structure is assumed (please see documentation).
 * The operation context is populated with data from the scroll table.
 * All mandatory data for the operation execution has to be displayed on the table.
 * Context, formatters, operations, and data have to be defined as prescribed.
 * Client operation is executed.
 * @return void
 */
	
	 public void openTask()
    	{
    	IndexedCollection iColl = null;	
    	String op = this.getOperation();
       	try{
       	DSEOperation dseOp = (DSEOperation)DSEOperation.readObject(op);
       
       	EJSummaryPanel summaryPanel = (EJSummaryPanel)(this.getParent().getParent());
       
       	SpTable scrollTable = summaryPanel.getScrollTable();
       
       	if (this.getSelection().equals("SelectedRow"))
       	{
       		int selectedRow = scrollTable.getSelectedRow();
       		
       		KeyedCollection newKcoll = (KeyedCollection)DataElement.readObject(this.getKCollName());
       		
       		Enumeration names = newKcoll.getElements().keys();
       		
       		while (names.hasMoreElements())
       		{
       			String name = names.nextElement().toString();
       			for (int i=0; i < scrollTable.getColumnCount(); i++)
       			{
       				if ((scrollTable.getDataElement(selectedRow,i) != null)&&(scrollTable.getDataElement(selectedRow,i).getName().equals(name)))
       				newKcoll.setValueAt(name, scrollTable.getDataElement(selectedRow,i).getValue());
       			}
      		
       		}
       		
       		
       		Enumeration elementEnum = dseOp.getContext().getKeyedCollection().getElements().elements();
       		while (elementEnum.hasMoreElements())
       		{
       			
       			Object element = elementEnum.nextElement();
       		 	if ((element != null) && (element instanceof IndexedCollection))
       		 	{
       		 		((IndexedCollection)element).addElement(newKcoll);
       		 	}
       		 
       		}
 			
 		dseOp.execute();
       	dseOp.close();
       	}
       
       	if (this.getSelection().equals("ResultSet"))
       	{
       		
       		Enumeration elementEnum = dseOp.getContext().getKeyedCollection().getElements().elements();
       			while (elementEnum.hasMoreElements())
       			{
       				Object element = elementEnum.nextElement();
       		 		if (element instanceof IndexedCollection)
       		 		{
       		 			iColl = (IndexedCollection)element;
       		 		}
       		 	}
    		for (int i=0; i<scrollTable.getRowCount(); i++)
       		{
       		
       			KeyedCollection newKcoll = (KeyedCollection)DataElement.readObject(this.getKCollName());
       			Enumeration names = newKcoll.getElements().keys();
       		
       			while (names.hasMoreElements())
       			{
       				String name = names.nextElement().toString();
       				for (int j=0; j < scrollTable.getColumnCount(); j++)
       				{
       					if ((scrollTable.getDataElement(i,j) != null)&&(scrollTable.getDataElement(i,j).getName().equals(name))) {
       						newKcoll.setValueAt(name, scrollTable.getDataElement(i,j).getValue());
       					}
       				}
      			}
      			
     			if (iColl != null) iColl.addElement(newKcoll);			
       		}
       	dseOp.execute();
       	dseOp.close();
       	}
       }
       catch (Exception e)
       {
       	
       	//this.handleException(e);
		e.printStackTrace(System.out);
       }
    }
    
 /**
 * Returns the type selection property value.
 * @return java.util.String
 */
       
    public String getSelection() {
		return selection;
	}
/**
 * Sets the value of the selection property.
 * @param value java.lang.String
 */

	public void setSelection(String value) {
		selection = value;
	}
	
/**
 * Returns the kCollName property value.
 * @return java.util.String
 */
	  public String getKCollName() {
		return kCollName;
	}

/**
 * Sets the value of the kCollName property.
 * @param value java.lang.String
 */
	public void setKCollName(String value) {
		kCollName = value;
	}
}



