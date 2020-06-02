package com.ibm.dse.appl.ej.client;

/*_
 * Licensed Materials - Property of IBM
 * Restricted Materials of IBM
 * 5648-D89
 * (C) Copyright IBM Corp. 2000, 2003
 * US Government Users Restricted Rights - Use, duplication or disclosure
 * restricted by GSA ADP Schedule Contract with IBM Corp
 */
import com.ibm.dse.base.*;
import org.apache.xerces.parsers.DOMParser;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NamedNodeMap;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Vector;
import com.ibm.dse.appl.ej.client.EJButton;

/**
 * This class is responsible for parsing the XML file.
 * The nodes are mapped to their corresponding classes.
 * The queries are stored in the EJQuerySet in the form of a hash table with the query label as the key.
 * @copyright (c) Copyright  IBM Corporation 2000, 2003
 */
public class EJQueryParserImpl implements EJQueryParser {
	private static final java.lang.String COPYRIGHT =
		"Licensed Materials - Property of IBM "+//$NON-NLS-1$
		"Restricted Materials of IBM "+//$NON-NLS-1$
		"5648-D89 "+//$NON-NLS-1$
		"(C) Copyright IBM Corp. 2000, 2003 All Rights Reserved. "+//$NON-NLS-1$
		"US Government Users Restricted Rights - Use, duplication or disclosure "+//$NON-NLS-1$
		"restricted by GSA ADP Schedule Contract with IBM Corp ";//$NON-NLS-1$
	private EJQuerySet querySet = null;
	private EJButtonSet buttonSet = new EJButtonSet();
	private EJQuery query = null;
	private EJInput input = null;
	private EJResult result = null;
	private EJParameter param = null;
	private EJColumn column = null;
	private Vector inputs = new Vector();
	private String statement = null;
	//private EJButtonRow buttonRow = null;
	private EJButton button = null;
	private String lastNodeName = null;
	/* Trace component identification */
	public java.lang.String COMPID = "#EJ";
/**
 * EJQueryParserImpl default constructor.
 */
 
public EJQueryParserImpl() {
	super();
}
/**
 * Returns the querySet property value.
 * @return com.ibm.dse.appl.ej.client.EJQuerySet
 */
 
public EJQuerySet getQuerySet() {
	return querySet;
}

/**
 * Returns the buttonSet property value.
 * @return com.ibm.dse.appl.ej.client.EJButtonSet
 */
 
public EJButtonSet getButtonSet() {
	return buttonSet;
}
/**
 * Retrieves the column information to be added to the table.
 * 
 */
private void handleColumn(Node aNode) {
	
	String name = null;
	String label = null;
	String type = null;
	NamedNodeMap aMap = null;
	aMap = aNode.getAttributes();
	name = aMap.getNamedItem("name").getNodeValue();
	label = aMap.getNamedItem("label").getNodeValue();
	try {type = aMap.getNamedItem("type").getNodeValue();} catch (Exception e) {}
	column = new EJColumn();
	column.setName(name);
	column.setLabel(label);
	column.setType(type);
	result.addColumn(column);
	lastNodeName = "Column";
	
}
/**
 * Retrieves the widget information to be added to the table.
 * 
 */
private void handleInput(Node aNode) {
	
	String name = null;
	String label = null;
	String type = null;
	String widget = null;
	String defaultValue = null;
	NamedNodeMap aMap = null;
	if(input != null) {
		query.addInput(input);
	}
	aMap = aNode.getAttributes();
	name = aMap.getNamedItem("name").getNodeValue();
	label = aMap.getNamedItem("label").getNodeValue();
	type = aMap.getNamedItem("type").getNodeValue();
	widget = aMap.getNamedItem("widget").getNodeValue();
	try {defaultValue = aMap.getNamedItem("defaultValue").getNodeValue();} catch(Exception e) {}
	if(lastNodeName.equals("Input") && aNode.getNodeName().equals("Input") || lastNodeName.equals("Param") && aNode.getNodeName().equals("Input")) {
		inputs.addElement(input);
	}
	input = new EJInput();
	input.setName(name);
	input.setLabel(label);
	input.setType(type);
	input.setWidget(widget);
	input.setDefaultValue(defaultValue);
	lastNodeName = "Input";
	
}
/**
 * Handles the item node by adding a list of entries to be displayed in the combobox's drop down list.
 * 
 */
private void handleParam(Node aNode) {
	
	String name = null;
	String label = null;
	NamedNodeMap aMap = null;
	aMap = aNode.getAttributes();
	name = aMap.getNamedItem("name").getNodeValue();
	label = aMap.getNamedItem("label").getNodeValue();
	param = new EJParameter();
	param.setName(name);
	param.setLabel(label);
	input.addParameter(param);
	lastNodeName = "Param";
	
}
/**
 * Handles the query node by adding the vector of columns to be added to the table 
 * and widgets to be displatyed in the view.
 * @param aNode Node 
 */
private void handleQuery(Node aNode) {
	String name = null;
	String label = null;
	String description = null;
	NamedNodeMap aMap = null;
	aMap = aNode.getAttributes();
	name = aMap.getNamedItem("name").getNodeValue();
	label = aMap.getNamedItem("label").getNodeValue();
	Node nodeItem = aMap.getNamedItem("description");
	if (nodeItem != null) {
		description = nodeItem.getNodeValue();
	}
	query = new EJQuery();
	query.setName(name);
	query.setLabel(label);
	query.setDescription(description);
	lastNodeName = "Query";
}
/**
 * Handles the querySet node by setting up a Hashtable of queries.
 */
private void handleQuerySet(Node aNode) {
	
	String name = null;
	NamedNodeMap aMap = null;
	aMap = aNode.getAttributes();
	name = aMap.getNamedItem("name").getNodeValue();
	querySet = new EJQuerySet();
	querySet.setName(name);
	lastNodeName = null;	
	
}
/**
 * Handles the result node by adding a vector of columns to the query node.
 */
private void handleResult(Node aNode) {
	
	inputs.addElement(input);
	input = null;
	result = new EJResult();
	lastNodeName = "Result";
	
}
/**
 * This method retrieves the SQL statement information to be associated with each query selection.
 * 
 */
private void handleStatement(Node aNode) {
	
	NamedNodeMap aMap = null;
	if((aNode.getParentNode().getNodeName().trim().equals("Statement")) && (aNode.getNodeValue() != null) && !(aNode.getNodeValue().trim().equals(""))) {
		statement = aNode.getNodeValue();
		query.setInputs(inputs);
		query.setStatement(statement);
		query.setResult(result);
		querySet.addQuery(query);
		inputs = new Vector();
		lastNodeName = "Statement";
	}
	
}
private void handleButton(Node aNode) {
	String label=null;
	String opId=null;
	String selection=null;
	String kCollName=null;
	
	NamedNodeMap aMap = null;
	aMap = aNode.getAttributes();
	label = aMap.getNamedItem("label").getNodeValue();
	
	try {opId = aMap.getNamedItem("opId").getNodeValue();} catch (Exception e) {Trace.trace(COMPID,Trace.Severe,Trace.Error,null, e.toString());}
	try {selection = aMap.getNamedItem("selection").getNodeValue();} catch (Exception e) {Trace.trace(COMPID,Trace.Severe,Trace.Error,null, e.toString());}
	try {kCollName = aMap.getNamedItem("kCollName").getNodeValue();} catch (Exception e) {Trace.trace(COMPID,Trace.Severe,Trace.Error,null, e.toString());}
	
	button = new EJButton();
	button.setLabel(label);
	button.setType(opId);
	button.setOpId(opId);
	button.setSelection(selection);
	button.setKCollName(kCollName);
	buttonSet.addButton(button);
	lastNodeName = "Button";
}

/**
 * Parses the XML file and sets up the queries hash table.
 * @param aFile java.lang.String
 */
public void initXML(String aFileName) {
	DOMParser parser = new DOMParser();
	try {
		parser.parse(aFileName);
		Document doc = parser.getDocument();
		Element ele = doc.getDocumentElement();
		navigateTree(ele);
	} catch (FileNotFoundException fe) {
		Trace.trace(COMPID,Trace.Severe,Trace.Error,null, fe.toString() + ". Could not find the xml file.");
	} catch (SAXException se) {
		Trace.trace(COMPID,Trace.Severe,Trace.Error,null, se.toString());
	} catch (IOException ioe) {
		ioe.printStackTrace();
	}
}
/**
 * Iterates through the XML file processing each node
 * 
 * @aNode org.w3c.dom.Node
 */
private void navigateTree(org.w3c.dom.Node aNode) {
	
	if(aNode == null) return;
	processNode(aNode);
	navigateTree(aNode.getFirstChild());
	navigateTree(aNode.getNextSibling());
	
}
/**
 * Processes the nodes in the XML file
 * 
 * @aNode org.w3c.dom.Node
 */
private void processNode(org.w3c.dom.Node aNode) {
	
	
	
	String elementName = aNode.getNodeName().trim();
	
	if(elementName.equals("QuerySet")) {
		handleQuerySet(aNode);
	}
	else if(elementName.equals("Query")) {
		handleQuery(aNode);
	}
	else if(elementName.equals("Input")) {
		handleInput(aNode);
	}
	else if(elementName.equals("Param")) {
		handleParam(aNode);
	}
	else if(elementName.equals("Result")) {
		handleResult(aNode);
	}
	else if(elementName.equals("Column")) {
		handleColumn(aNode);
	}
	else if(elementName.equals("#text")) {
		handleStatement(aNode);
	}
	else if(elementName.equals("Button")) {
		handleButton(aNode);
	}	
}
/**
 * Sets the value of the querySet property.
 * @param value  com.ibm.dse.appl.ej.client.EJQuerySet
 */
 
public void setQuerySet(EJQuerySet value) {
	querySet = value;
}
/**
 * Sets the value of the buttonSet property.
 * @param value com.ibm.dse.appl.ej.client.EJQuerySet
 */
 
public void setButtonSet(EJButtonSet value) {
	buttonSet = value;
}

}
