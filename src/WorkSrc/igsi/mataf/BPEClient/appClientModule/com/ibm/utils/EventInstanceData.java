package com.ibm.utils;

import com.ibm.bpe.api.*;

/**
 * @author dk92351
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class EventInstanceData {
	EIID theEIID;
	AIID theAIID;
	PIID thePIID;
	String theName;
	
	public EventInstanceData(EIID aEIID, AIID aAIID, PIID aPIID, String aName){
	 theEIID = aEIID;
	 theAIID = aAIID;
	 thePIID = aPIID;
	 theName = aName;
		
	}
	/**
	 * Returns the theAIID.
	 * @return AIID
	 */
	public AIID getTheAIID() {
		return theAIID;
	}

	/**
	 * Returns the theEIID.
	 * @return EIID
	 */
	public EIID getTheEIID() {
		return theEIID;
	}

	/**
	 * Returns the theName.
	 * @return String
	 */
	public String getTheName() {
		return theName;
	}

	/**
	 * Returns the thePIID.
	 * @return PIID
	 */
	public PIID getThePIID() {
		return thePIID;
	}

}
