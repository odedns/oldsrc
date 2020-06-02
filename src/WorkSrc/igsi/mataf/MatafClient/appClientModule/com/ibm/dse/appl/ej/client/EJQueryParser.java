package com.ibm.dse.appl.ej.client;

/*_
 * Licensed Materials - Property of IBM
 * Restricted Materials of IBM
 * 5648-D89
 * (C) Copyright IBM Corp. 2000
 * US Government Users Restricted Rights - Use, duplication or disclosure
 * restricted by GSA ADP Schedule Contract with IBM Corp
 */
/**
 * The implementor of this interface is responsible for parsing the XML file.
 * @copyright (c) Copyright  IBM Corporation 2000
 */
 
public interface EJQueryParser {

/**
 * Returns the querySet property value.
 * @return com.ibm.dse.appl.ej.client.EJQuerySet
 */
 
EJQuerySet getQuerySet();
/**
 * This method is responsible for parsing the XML file.
 * @param aFile java.lang.String
 */
 
void initXML(String aFile);
}
