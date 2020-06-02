/*
 * Created on 12/07/2004
 * Author: yifat har-nof
 * @version $Id: PersistenceConstants.java,v 1.2 2005/04/04 09:45:06 yifat Exp $
 */
package com.ness.fw.persistence;

/**
 * Persistence Layer constants
 */
public class PersistenceConstants
{
	public static final String LOGGER_CONTEXT = "PERSISTENT ";

	// ------ Connection Data ------	
	public static final String CONNECTION_DATA_SOURCE = "Connection.datasource";
	public static final String CONNECTION_STRING =
		"Connection.connectionString";
	public static final String CONNECTION_DRIVER = "Connection.driver";
	public static final String CONNECTION_DB_USER = "Connection.dbUser";
	public static final String CONNECTION_DB_PASSWORD = "Connection.dbPassword";

	// ------ SQL Codes ------	
	public static final String SQLCODE_LOCKED_RECORD = "SqlCode.lockedRecord";
	public static final String SQLCODE_DUPLICATE_KEY = "SqlCode.duplicateKey";
	public static final String SQLCODE_MISSING_SAVEPOINT =
		"SqlCode.missingSavePoint";

	// ------ General Data ------	
	public static final String GENERAL_MAX_ROWS_IN_STATEMENT =
		"General.maxRowsInStatement";
	public static final String GENERAL_QUERY_TIMEOUT = "General.queryTimeout";
	public static final String GENERAL_ALLOW_SCROLLBALE_PS =
		"General.allowScrollablePreparedStatement";
	public static final String GENERAL_LIKE_SIGN = "General.likeSign";

	// ------ Identity Keys ------	
	public static final String IDENTITY_KEY_GET_LAST_VALUE =
		"IdentityKey.getLastIdentityKey";

	// ------ Save Points ------	
	public static final String SAVEPOINT_SET = "SavePoint.setUniqueSavePoint";
	public static final String SAVEPOINT_ROLLBACK =
		"SavePoint.rollbackToSavePoint";
	public static final String SAVEPOINT_RELEASE = "SavePoint.releaseSavePoint";

	// ------ Numerator ------	
	public static final String NUMERATOR_GET_DETAILS =
		"Numerator.getNumeratorDetails";
	public static final String NUMERATOR_INSERT_DETAILS =
		"Numerator.insertNumeratorDetails";
	public static final String NUMERATOR_UPDATE_DETAILS =
		"Numerator.updateNumeratorDetails";
	public static final String NUMERATOR_FIELD_LAST_VALUE = "LAST_VALUE";
	public static final String NUMERATOR_FIELD_STEP = "STEP";

}
