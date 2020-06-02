// FrontEnd Plus GUI for JAD
// DeCompiled : JDBCJournal.class
package com.ibm.dse.services.jdbc;

import com.ibm.dse.base.Context;
import com.ibm.dse.base.DSEException;
import com.ibm.dse.base.DSEInternalErrorException;
import com.ibm.dse.base.DSEInvalidArgumentException;
import com.ibm.dse.base.DSEInvalidRequestException;
import com.ibm.dse.base.DSENotifier;
import com.ibm.dse.base.DSEObjectNotFoundException;
import com.ibm.dse.base.FormatElement;
import com.ibm.dse.base.Hashtable;
import com.ibm.dse.base.HashtableFormat;
import com.ibm.dse.base.HashtableIndexedCollectionFormat;
import com.ibm.dse.base.JavaExtensions;
import com.ibm.dse.base.Service;
import com.ibm.dse.base.Tag;
import com.ibm.dse.base.TagAttribute;
import com.ibm.dse.base.Trace;
import com.ibm.dse.base.Vector;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Enumeration;

// Referenced classes of package com.ibm.dse.services.jdbc:
//            Journal, DSESQLException, JournalConstants, JDBCService, 
//            JDBCJournalSchemaGenerator, DatabaseResultSetMetaData, JDBCServicesAdministrator

public class JDBCJournal extends Journal
{

    private static final String COPYRIGHT = "Licensed Materials - Property of IBM Restricted Materials of IBM 5648-D89 (C) Copyright IBM Corp. 1998, 2003 All Rights Reserved. US Government Users Restricted Rights - Use, duplication or disclosure restricted by GSA ADP Schedule Contract with IBM Corp ";
    private Hashtable columnsTable;
    protected String JDBCDriver;
    protected boolean createSchema;
    protected String schemaName;
    private int wrapNumber;
    private String date;
    protected String tableName;
    private static final String var_insert = "INSERT INTO ";
    private static final String var_update = "UPDATE ";
    private static final String var_where = " WHERE ";
    private static int singleTableLastRecordNumber;
    private boolean singleTable;
    private static Object poolSem = new Object();

    public JDBCJournal()
    {
        columnsTable = new Hashtable();
        JDBCDriver = JournalConstants.EJ_DEFAULT_JDBCDRIVER;
        createSchema = true;
        schemaName = JournalConstants.DEFAULT_SCHEMA_NAME;
        singleTable = false;
    }

    public JDBCJournal(String s, String s1)
        throws DSEInvalidRequestException
    {
        columnsTable = new Hashtable();
        JDBCDriver = JournalConstants.EJ_DEFAULT_JDBCDRIVER;
        createSchema = true;
        schemaName = JournalConstants.DEFAULT_SCHEMA_NAME;
        singleTable = false;
        if(s == null || s.equals(""))
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s2 = "Invalid Entity name: " + s;
                Trace.trace("#EJ", 1024, 8, null, s2);
            }
            throw new DSEInvalidRequestException("DSEException.critical", "", "Invalid Entity name: " + s);
        }
        if(s1 == null || s1.equals(""))
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s3 = "Invalid Schema name: " + s1;
                Trace.trace("#EJ", 1024, 8, null, s3);
            }
            throw new DSEInvalidRequestException("DSEException.critical", "", "Invalid Schema name: " + s1);
        } else
        {
            super.entity = s;
            schemaName = s1;
            return;
        }
    }

    public int addRecord(Context context, HashtableFormat hashtableformat)
        throws DSEInvalidArgumentException, DSEInvalidRequestException, DSEInternalErrorException, DSESQLException
    {
        if(context == null)
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s = "aContext reference is null.";
                Trace.trace("#EJ", 1024, 8, null, s);
            }
            throw new DSEInvalidArgumentException("DSEException.critical", "", "aContext reference is null.");
        }
        if(hashtableformat == null)
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s1 = "aHashtableFormat reference is null.";
                Trace.trace("#EJ", 1024, 8, null, s1);
            }
            throw new DSEInvalidArgumentException("DSEException.critical", "", "aFormatName reference is null.");
        }
        try
        {
            Hashtable hashtable = hashtableformat.format(context);
            if(hashtable == null)
            {
                if(Trace.doTrace("#EJ", 1024, 8))
                {
                    String s2 = "The data hashtable reference is null.";
                    Trace.trace("#EJ", 1024, 8, null, s2);
                }
                throw new DSEInternalErrorException("DSEException.critical", "", "The data hashtable reference is null.");
            } else
            {
                return addRecord(hashtable);
            }
        }
        catch(Exception exception)
        {
            if(Trace.doTrace("#EJ", 256, 16))
            {
                String s3 = "Exception: " + exception.getMessage();
                Trace.trace("#EJ", 256, 16, null, s3);
            }
            throw new DSEInternalErrorException("DSEException.critical", "", exception.getMessage());
        }
    }

    public int addRecord(Context context, String s)
        throws DSEInvalidArgumentException, DSEInvalidRequestException, DSEInternalErrorException, DSESQLException
    {
        if(context == null)
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s1 = "aContext reference is null.";
                Trace.trace("#EJ", 1024, 8, null, s1);
            }
            throw new DSEInvalidArgumentException("DSEException.critical", "", "aContext reference is null.");
        }
        if(s == null || s.equals(""))
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s2 = "aFormatName reference is null or the argument size is 0.";
                Trace.trace("#EJ", 1024, 8, null, s2);
            }
            throw new DSEInvalidArgumentException("DSEException.critical", "", "aFormatName reference is null or the argument size is 0");
        }
        try
        {
            HashtableFormat hashtableformat = (HashtableFormat)FormatElement.readObject(s);
            if(hashtableformat == null)
            {
                if(Trace.doTrace("#EJ", 256, 16))
                {
                    String s3 = "The formatter reference is null.";
                    Trace.trace("#EJ", 256, 16, null, s3);
                }
                throw new DSEInternalErrorException("DSEException.critical", "", "The formatter reference is null.");
            }
            Hashtable hashtable = hashtableformat.format(context);
            if(hashtable == null)
            {
                if(Trace.doTrace("#EJ", 256, 16))
                {
                    String s5 = "The data hashtable reference is null.";
                    Trace.trace("#EJ", 256, 16, null, s5);
                }
                throw new DSEInternalErrorException("DSEException.critical", "", "The data hashtable reference is null.");
            } else
            {
                return addRecord(hashtable);
            }
        }
        catch(Exception exception)
        {
            if(Trace.doTrace("#EJ", 256, 16))
            {
                String s4 = "Exception: " + exception.getMessage();
                Trace.trace("#EJ", 256, 16, null, s4);
            }
            throw new DSEInternalErrorException("DSEException.critical", "", exception.getMessage());
        }
    }

    public int addRecord(Hashtable hashtable)
        throws DSEInvalidRequestException, DSEInvalidArgumentException, DSEInternalErrorException, DSESQLException
    {
        if(Trace.doTrace("#EJ", 1024, 2))
        {
            String s = "Adding a record....";
            Trace.trace("#EJ", 1024, 2, null, s);
        }
        if(hashtable == null)
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s1 = "The data hashtable reference is null.";
                Trace.trace("#EJ", 1024, 8, null, s1);
            }
            throw new DSEInvalidArgumentException("DSEException.critical", "", "The data hashtable reference is null.");
        }
        if(columnsTable == null)
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s2 = "The columns hashtable reference is null.";
                Trace.trace("#EJ", 1024, 8, null, s2);
            }
            throw new DSEInvalidArgumentException("DSEException.critical", "", "The columns hashtable reference is null.");
        }
        if(!isActive())
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s3 = "The Journal is not in ACTIVE state. Can not add the record to the Journal.";
                Trace.trace("#EJ", 1024, 8, null, s3);
            }
            throw new DSEInvalidRequestException("DSEException.critical", "", "The Journal is not in ACTIVE state. Can not add the record to the Journal.");
        }
        synchronized(poolSem)
        {
            Vector vector = recordToRow(hashtable);
            try
            {
                PreparedStatement preparedstatement = getAddPstmt();
                Enumeration enumeration = vector.elements();
                int i = 0;
                if(isSingleTable())
                    i = getSingleTableLastRecordNumber();
                else
                    i = getLastRecordNumber();
                i++;
                preparedstatement.setObject(1, new Integer(i));
                DatabaseResultSetMetaData databaseresultsetmetadata = JDBCJournalSchemaGenerator.getMetaData(schemaName, isCreateSchema());
                if(databaseresultsetmetadata == null)
                {
                    if(Trace.doTrace("#EJ", 256, 16))
                    {
                        String s5 = "getMetaData() returned null.";
                        Trace.trace("#EJ", 256, 16, null, s5);
                    }
                    throw new DSEInternalErrorException("DSEException.critical", "", "getMetaData() returned null.");
                }
                for(int j = 2; enumeration.hasMoreElements(); j++)
                {
                    Object obj1 = enumeration.nextElement();
                    if(obj1 == null)
                        preparedstatement.setNull(j, databaseresultsetmetadata.getColumnType(j));
                    else
                    if(obj1 instanceof BigDecimal)
                        preparedstatement.setBigDecimal(j, (BigDecimal)obj1);
                    else
                        preparedstatement.setObject(j, obj1);
                }

                preparedstatement.executeUpdate();
                if(isSingleTable())
                    setSingleTableLastRecordNumber(i);
                else
                    setLastRecordNumber(i);
                if(super.autoCommit && Trace.doTrace("#EJ", 1024, 2))
                {
                    String s6 = "Commiting all Database changes...";
                    Trace.trace("#EJ", 1024, 2, null, s6);
                }
                int k = i;
                return k;
            }
            catch(SQLException sqlexception)
            {
                if(Trace.doTrace("#EJ", 1024, 8))
                {
                    String s4 = "SQLException received. " + sqlexception.getMessage();
                    Trace.trace("#EJ", 1024, 8, null, s4);
                }
                throw new DSESQLException("DSEException.critical", "", sqlexception.getErrorCode(), sqlexception.getSQLState(), "SQLException while adding row in Journal Table for Schema" + schemaName + ": " + sqlexception.getMessage());
            }
        }
    }

    private Vector allRowsToRecords(ResultSet resultset)
        throws DSEInvalidArgumentException, DSEInternalErrorException, DSESQLException
    {
        Vector vector = new Vector();
        Object obj = null;
        try
        {
            if(JDBCServicesAdministrator.databaseConnection == null || JDBCServicesAdministrator.databaseConnection.isClosed())
                JDBCServicesAdministrator.databaseConnection = getDatabaseConnection();
            ResultSetMetaData resultsetmetadata = resultset.getMetaData();
            if(resultsetmetadata == null)
            {
                if(Trace.doTrace("#EJ", 256, 16))
                {
                    String s = "aResultSet.getMetaData() returned null.";
                    Trace.trace("#EJ", 256, 16, null, s);
                }
                throw new DSEInternalErrorException("DSEException.critical", "", "aResultSet.getMetaData() returned null.");
            }
            int i = resultsetmetadata.getColumnCount();
            String s2 = null;
            Object obj1 = null;
            Object obj2 = null;
            if(i < 1)
            {
                if(Trace.doTrace("#EJ", 256, 16))
                {
                    String s5 = "Invalid number of columns: " + i;
                    Trace.trace("#EJ", 256, 16, null, s5);
                }
                throw new DSEInternalErrorException("DSEException.critical", "", "Invalid number of columns: " + i);
            }
            s2 = resultsetmetadata.getColumnName(1);
            if(!s2.equals(JournalConstants.JT_RECNBR_COLUMN_NAME))
            {
                if(Trace.doTrace("#EJ", 256, 16))
                {
                    String s6 = "The first column name should be " + JournalConstants.JT_RECNBR_COLUMN_NAME + ". Actual name is " + s2;
                    Trace.trace("#EJ", 256, 16, null, s6);
                }
                throw new DSEInternalErrorException("DSEException.critical", "", "The first column name should be " + JournalConstants.JT_RECNBR_COLUMN_NAME + ". Actual name is " + s2);
            }
            while(resultset.next()) 
            {
                Hashtable hashtable = new Hashtable();
                for(int j = 2; j <= i; j++)
                {
                    String s3 = resultsetmetadata.getColumnName(j);
                    String s4 = (String)columnsTable.get(s3);
                    if(s4 == null)
                        s4 = s3;
                    Object obj3 = resultset.getObject(s3);
                    if(obj3 != null)
                        hashtable.put(s4, obj3);
                }

                if(hashtable != null)
                    vector.addElement(hashtable);
                else
                if(Trace.doTrace("#EJ", 1024, 4))
                {
                    String s7 = "One of the returned records is null.";
                    Trace.trace("#EJ", 1024, 4, null, s7);
                }
            }
        }
        catch(SQLException sqlexception)
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s1 = "SQLException while converting row to Record: " + sqlexception.getMessage();
                Trace.trace("#EJ", 1024, 8, null, s1);
            }
            throw new DSESQLException("DSEException.critical", "", sqlexception.getErrorCode(), sqlexception.getSQLState(), "SQLException while converting row to Record: " + sqlexception.getMessage());
        }
        return vector;
    }

    private Vector allRowsToRecordsWithSomeColumns(ResultSet resultset)
        throws DSEInvalidArgumentException, DSEInternalErrorException, DSESQLException
    {
        Vector vector = new Vector();
        Object obj = null;
        try
        {
            if(JDBCServicesAdministrator.databaseConnection == null || JDBCServicesAdministrator.databaseConnection.isClosed())
                JDBCServicesAdministrator.databaseConnection = getDatabaseConnection();
            ResultSetMetaData resultsetmetadata = resultset.getMetaData();
            if(resultsetmetadata == null)
            {
                if(Trace.doTrace("#EJ", 256, 16))
                {
                    String s = "aResultSet.getMetaData() returned null.";
                    Trace.trace("#EJ", 256, 16, null, s);
                }
                throw new DSEInternalErrorException("DSEException.critical", "", "aResultSet.getMetaData() returned null.");
            }
            int i = resultsetmetadata.getColumnCount();
            Object obj1 = null;
            Object obj2 = null;
            Object obj3 = null;
            if(i < 1)
            {
                if(Trace.doTrace("#EJ", 256, 16))
                {
                    String s4 = "Invalid number of columns: " + i;
                    Trace.trace("#EJ", 256, 16, null, s4);
                }
                throw new DSEInternalErrorException("DSEException.critical", "", "Invalid number of columns: " + i);
            }
            while(resultset.next()) 
            {
                Hashtable hashtable = new Hashtable();
                for(int j = 1; j <= i; j++)
                {
                    String s2 = resultsetmetadata.getColumnName(j);
                    String s3 = (String)columnsTable.get(s2);
                    if(s3 == null)
                        s3 = s2;
                    Object obj4 = resultset.getObject(s2);
                    if(obj4 != null)
                        hashtable.put(s3, obj4);
                }

                if(hashtable != null)
                    vector.addElement(hashtable);
                else
                if(Trace.doTrace("#EJ", 1024, 4))
                {
                    String s5 = "One of the returned records is null.";
                    Trace.trace("#EJ", 1024, 4, null, s5);
                }
            }
        }
        catch(SQLException sqlexception)
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s1 = "SQLException while converting row to Record: " + sqlexception.getMessage();
                Trace.trace("#EJ", 1024, 8, null, s1);
            }
            throw new DSESQLException("DSEException.critical", "", sqlexception.getErrorCode(), sqlexception.getSQLState(), "SQLException while converting row to Record: " + sqlexception.getMessage());
        }
        return vector;
    }

    protected void buildAddString()
        throws DSESQLException, DSEInternalErrorException, DSEInvalidRequestException, DSEInvalidArgumentException, DSEException
    {
        StringBuffer stringbuffer = new StringBuffer();
        stringbuffer.append("INSERT INTO ");
        stringbuffer.append(getTableName());
        stringbuffer.append(journalTableRowInsertDefinition());
        setAddString(stringbuffer);
    }

    protected void buildConnectionProperties()
    {
        super.buildConnectionProperties();
        if(JDBCDriver != null)
            super.connectionProperties.put("JDBCDriver", JDBCDriver);
    }

    protected void buildUpdateString()
        throws DSESQLException, DSEInternalErrorException, DSEInvalidRequestException, DSEInvalidArgumentException, DSEException
    {
        StringBuffer stringbuffer = new StringBuffer();
        stringbuffer.append("UPDATE ");
        stringbuffer.append(getTableName());
        stringbuffer.append(journalTableRowUpdateDefinition());
        stringbuffer.append(" WHERE ");
        setUpdateString(stringbuffer);
    }

    public void commit()
        throws DSESQLException
    {
        if(!super.autoCommit)
            try
            {
                super.databaseConnection.commit();
            }
            catch(SQLException sqlexception)
            {
                if(Trace.doTrace("#EJ", 1024, 8))
                {
                    String s1 = "SQLException while committing Database changes for Entity " + super.entity + ": " + sqlexception.getMessage();
                    Trace.trace("#EJ", 1024, 8, null, s1);
                }
                throw new DSESQLException("DSEException.critical", "", sqlexception.getErrorCode(), sqlexception.getSQLState(), "SQLException while committing Database changes for Entity " + super.entity + ": " + sqlexception.getMessage());
            }
        else
        if(Trace.doTrace("#EJ", 1024, 2))
        {
            String s = "Journal is set with AUTOCOMMIT=TRUE. Commit not executed.";
            Trace.trace("#EJ", 1024, 2, null, s);
        }
    }

    protected String controlTableName()
    {
        return schemaName + "." + JournalConstants.CONTROL_TABLE_NAME;
    }

    public String currentGenerationDate()
        throws DSEInternalErrorException, DSESQLException
    {
        Object obj = null;
        Object obj1 = null;
        String s = null;
        try
        {
            Statement statement = super.databaseConnection.createStatement();
            if(statement == null)
            {
                if(Trace.doTrace("#EJ", 256, 16))
                {
                    String s1 = "databaseConnection.createStatement() return null";
                    Trace.trace("#EJ", 256, 16, null, s1);
                }
                throw new DSEInternalErrorException("DSEException.critical", "", "databaseConnection.createStatement() return null");
            }
            String s2 = "SELECT " + JournalConstants.CT_LASTDATE_COLUMN_NAME + " FROM " + controlTableName() + " WHERE " + JournalConstants.CT_ENTITY_COLUMN_NAME + " = '" + super.entity + "'";
            ResultSet resultset = statement.executeQuery(s2);
            if(resultset == null)
            {
                if(Trace.doTrace("#EJ", 256, 16))
                {
                    String s3 = "stmt.executeQuery(preparedString) returned null.";
                    Trace.trace("#EJ", 256, 16, null, s3);
                }
                throw new DSEInternalErrorException("DSEException.critical", "", "stmt.executeQuery(preparedString) returned null.");
            }
            if(resultset.next())
                s = resultset.getString(1);
            resultset.close();
            statement.close();
            if(s == null)
                return null;
            else
                return s;
        }
        catch(SQLException sqlexception)
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s4 = "SQLException while retrieving currentGenerationDate for Entity " + super.entity + ": " + sqlexception.getMessage();
                Trace.trace("#EJ", 1024, 8, null, s4);
            }
            throw new DSESQLException("DSEException.critical", "", sqlexception.getErrorCode(), sqlexception.getSQLState(), "SQLException while retrieving currentGenerationDate for Entity " + super.entity + ": " + sqlexception.getMessage());
        }
    }

    protected int currentWrapNumber()
        throws DSEInternalErrorException, DSEInvalidRequestException, DSEInvalidArgumentException, DSESQLException
    {
        return currentWrapNumberForSchema(schemaName);
    }

    protected int currentWrapNumberForSchema(String s)
        throws DSEInternalErrorException, DSEInvalidRequestException, DSEInvalidArgumentException, DSESQLException
    {
        Object obj = null;
        Object obj1 = null;
        boolean flag = false;
        try
        {
            Statement statement = super.databaseConnection.createStatement();
            if(statement == null)
            {
                if(Trace.doTrace("#EJ", 256, 16))
                {
                    String s1 = "databaseConnection.createStatement() return null";
                    Trace.trace("#EJ", 256, 16, null, s1);
                }
                throw new DSEInternalErrorException("DSEException.critical", "", "databaseConnection.createStatement() return null");
            }
            String s2 = "SELECT " + JournalConstants.CT_WRAPNBR_COLUMN_NAME + " FROM " + controlTableName() + " WHERE " + JournalConstants.CT_ENTITY_COLUMN_NAME + " = '" + super.entity + "'";
            ResultSet resultset = statement.executeQuery(s2);
            if(resultset == null)
            {
                if(Trace.doTrace("#EJ", 256, 16))
                {
                    String s3 = "stmt.executeQuery(preparedString) returned null.";
                    Trace.trace("#EJ", 256, 16, null, s3);
                }
                throw new DSEInternalErrorException("DSEException.critical", "", "stmt.executeQuery(preparedString) returned null.");
            }
            int i;
            if(resultset.next())
            {
                i = resultset.getInt(1);
            } else
            {
                if(Trace.doTrace("#EJ", 256, 16))
                {
                    String s4 = "ResultSet contains no row.";
                    Trace.trace("#EJ", 256, 16, null, s4);
                }
                throw new DSEInternalErrorException("DSEException.critical", "", "ResultSet contains No row.");
            }
            resultset.close();
            statement.close();
            return i;
        }
        catch(SQLException sqlexception)
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s5 = "SQLException while retrieving current Wrap Number for Entity " + super.entity + ": " + sqlexception.getMessage();
                Trace.trace("#EJ", 1024, 8, null, s5);
            }
            throw new DSESQLException("DSEException.critical", "", sqlexception.getErrorCode(), sqlexception.getSQLState(), "SQLException while retrieving current Wrap Number for Entity " + super.entity + ": " + sqlexception.getMessage());
        }
    }

    protected void deleteJournalContent(int i)
        throws DSEInternalErrorException, DSESQLException
    {
        if(Trace.doTrace("#EJ", 1024, 2))
        {
            String s = "deleteJournalContent(" + i + ")";
            Trace.trace("#EJ", 1024, 2, null, s);
        }
        Object obj = null;
        try
        {
            Statement statement = getFromStmtPool();
            if(statement == null)
            {
                if(Trace.doTrace("#EJ", 256, 16))
                {
                    String s1 = "databaseConnection.createStatement() return null";
                    Trace.trace("#EJ", 256, 16, null, s1);
                }
                throw new DSEInternalErrorException("DSEException.critical", "", "databaseConnection.createStatement() return null");
            }
            String s2 = "DELETE FROM " + tableName(schemaName, super.entity, i);
            statement.executeUpdate(s2);
            returnToStmtPool(statement);
        }
        catch(SQLException sqlexception)
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s3 = "SQLException deleting content of Journal Table " + tableName(schemaName, super.entity, i) + ": " + sqlexception.getMessage();
                Trace.trace("#EJ", 1024, 8, null, s3);
            }
            throw new DSESQLException("DSEException.critical", "", sqlexception.getErrorCode(), sqlexception.getSQLState(), "SQLException deleting content of Journal Table " + tableName(schemaName, super.entity, i) + ": " + sqlexception.getMessage());
        }
    }

    protected void deleteRecord(int i)
        throws DSEInternalErrorException, DSESQLException
    {
        if(Trace.doTrace("#EJ", 1024, 2))
        {
            String s = "deleteRecord(" + i + ")";
            Trace.trace("#EJ", 1024, 2, null, s);
        }
        Object obj = null;
        try
        {
            Statement statement = getFromStmtPool();
            if(statement == null)
            {
                if(Trace.doTrace("#EJ", 256, 16))
                {
                    String s1 = "databaseConnection.createStatement() return null";
                    Trace.trace("#EJ", 256, 16, null, s1);
                }
                throw new DSEInternalErrorException("DSEException.critical", "", "databaseConnection.createStatement() return null");
            }
            String s2 = "DELETE FROM " + getTableName() + " WHERE " + JournalConstants.JT_RECNBR_COLUMN_NAME + " = " + i;
            int j = statement.executeUpdate(s2);
            returnToStmtPool(statement);
            if(super.autoCommit && Trace.doTrace("#EJ", 1024, 2))
            {
                String s4 = "Commiting all Database changes...";
                Trace.trace("#EJ", 1024, 2, null, s4);
            }
        }
        catch(SQLException sqlexception)
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s3 = "SQLException while deleting record number " + i + " from Table " + getTableName() + ": " + sqlexception.getMessage();
                Trace.trace("#EJ", 1024, 8, null, s3);
            }
            throw new DSESQLException("DSEException.critical", "", sqlexception.getErrorCode(), sqlexception.getSQLState(), "SQLException while deleting record number " + i + " from Table " + getTableName() + ": " + sqlexception.getMessage());
        }
    }

    public Hashtable getColumnsTable()
    {
        return columnsTable;
    }

    protected Connection getDatabaseConnection()
    {
        return super.databaseConnection;
    }

    public Vector getEntities()
        throws DSESQLException, DSEInternalErrorException
    {
        try
        {
            if(JDBCServicesAdministrator.databaseConnection == null || JDBCServicesAdministrator.databaseConnection.isClosed())
                JDBCServicesAdministrator.databaseConnection = getDatabaseConnection();
            return JDBCJournalSchemaGenerator.allEntities(schemaName, isCreateSchema());
        }
        catch(SQLException sqlexception)
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s = "SQLException while retrieving Entities from Database: " + sqlexception.getMessage();
                Trace.trace("#EJ", 1024, 8, null, s);
            }
            throw new DSESQLException("DSEException.critical", "", sqlexception.getErrorCode(), sqlexception.getSQLState(), "SQLException while retrieving Entities from Database: " + sqlexception.getMessage());
        }
        catch(DSEInvalidRequestException dseinvalidrequestexception)
        {
            if(Trace.doTrace("#EJ", 256, 16))
            {
                String s1 = "DSEInvalidRequestException while retrieving Entities from Database: " + dseinvalidrequestexception.getMessage();
                Trace.trace("#EJ", 256, 16, null, s1);
            }
            throw new DSEInternalErrorException("DSEException.critical", "", "DSEInvalidRequestException while retrieving Entities from Database: " + dseinvalidrequestexception.getMessage());
        }
    }

    private Statement getFromStmtPool()
        throws SQLException
    {
        synchronized(this)
        {
            if(super.stmtPool.size() > 0)
            {
                Statement statement = (Statement)super.stmtPool.remove(super.stmtPool.size() - 1);
                return statement;
            }
        }
        return super.databaseConnection.createStatement();
    }

    public String getJDBCDriver()
    {
        return JDBCDriver;
    }

    public int getNumberOfGenerations()
        throws DSESQLException, DSEInternalErrorException
    {
        try
        {
            if(JDBCServicesAdministrator.databaseConnection == null || JDBCServicesAdministrator.databaseConnection.isClosed())
                JDBCServicesAdministrator.databaseConnection = getDatabaseConnection();
            return JDBCJournalSchemaGenerator.numberOfGenerations(schemaName, isCreateSchema());
        }
        catch(SQLException sqlexception)
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s = "SQLException while retrieving number of generations: " + sqlexception.getMessage();
                Trace.trace("#EJ", 1024, 8, null, s);
            }
            throw new DSESQLException("DSEException.critical", "", sqlexception.getErrorCode(), sqlexception.getSQLState(), "SQLException while retrieving number of generations: " + sqlexception.getMessage());
        }
        catch(DSEInvalidRequestException dseinvalidrequestexception)
        {
            if(Trace.doTrace("#EJ", 256, 16))
            {
                String s1 = "DSEInvalidRequestException while retrieving number of generations: " + dseinvalidrequestexception.getMessage();
                Trace.trace("#EJ", 256, 16, null, s1);
            }
            throw new DSEInternalErrorException("DSEException.critical", "", "DSEInvalidRequestException while retrieving number of generations: " + dseinvalidrequestexception.getMessage());
        }
    }

    public String getSchemaName()
    {
        return schemaName;
    }

    public String getTableName()
    {
        return tableName;
    }

    protected int getWrapNumber()
    {
        return wrapNumber;
    }

    public Object initializeColumnFrom(Tag tag)
    {
        String s = null;
        String s1 = "";
        for(Enumeration enumeration = tag.getAttrList().elements(); enumeration.hasMoreElements();)
        {
            TagAttribute tagattribute = (TagAttribute)enumeration.nextElement();
            if(tagattribute.getName().equals("id"))
                s = (String)tagattribute.getValue();
            else
            if(tagattribute.getName().equals("dataName"))
                s1 = (String)tagattribute.getValue();
        }

        if(s1.length() > 0)
            getColumnsTable().put(s, s1);
        return this;
    }

    public Object initializeFrom(Tag tag)
        throws IOException
    {
        super.autoCommit = false;
        schemaName = "";
        columnsTable = new Hashtable();
        for(Enumeration enumeration = tag.getAttrList().elements(); enumeration.hasMoreElements();)
        {
            TagAttribute tagattribute = (TagAttribute)enumeration.nextElement();
            if(tagattribute.getName().equals("id"))
                setName((String)tagattribute.getValue());
            else
            if(tagattribute.getName().equals("autoCommit"))
                setAutoCommit(((String)tagattribute.getValue()).equals("true"));
            else
            if(tagattribute.getName().equals("schema"))
                setSchemaName((String)tagattribute.getValue());
            else
            if(tagattribute.getName().equals("createSchema"))
                setCreateSchema(tagattribute.getValue().equals("true") || tagattribute.getValue().equals("yes"));
            else
            if(tagattribute.getName().equals("poolName"))
                setPoolName((String)tagattribute.getValue());
            else
            if(tagattribute.getName().equals("JDBCDriver"))
                setJDBCDriver((String)tagattribute.getValue());
            else
            if(tagattribute.getName().equals("waitRetry"))
                setWaitRetry(tagattribute.getValue().equals("true") || tagattribute.getValue().equals("yes"));
            else
            if(tagattribute.getName().equals("dataSourceName"))
                setDataSourceName((String)tagattribute.getValue());
            else
            if(tagattribute.getName().equals("sharedConnection"))
                setSharedConnection((String)tagattribute.getValue());
            else
            if(tagattribute.getName().equals("singleTable"))
                setSingleTable(tagattribute.getValue().equals("true") || tagattribute.getValue().equals("yes"));
            else
            if(tagattribute.getName().equals("statementPoolSize"))
                try
                {
                    setStatementPoolSize((new Integer((String)tagattribute.getValue())).intValue());
                }
                catch(NumberFormatException numberformatexception)
                {
                    setStatementPoolSize(32);
                }
        }

        buildConnectionProperties();
        Enumeration enumeration1 = tag.getSubTags().elements();
        Object obj = null;
        while(enumeration1.hasMoreElements()) 
        {
            Tag tag1 = (Tag)enumeration1.nextElement();
            if(tag1.getName().equals("column"))
                initializeColumnFrom(tag1);
        }
        return this;
    }

    public boolean isConnected()
        throws DSEInvalidRequestException, DSEInternalErrorException, DSESQLException, DSEObjectNotFoundException, DSEException
    {
        try
        {
            return super.databaseConnection != null && !super.databaseConnection.isClosed() && verifyConnection();
        }
        catch(SQLException sqlexception)
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s = "SQLException when trying to request connection information." + sqlexception.getMessage();
                Trace.trace("#EJ", 1024, 8, null, s);
            }
            throw new DSESQLException("DSEException.critical", "", sqlexception.getErrorCode(), sqlexception.getSQLState(), sqlexception.getMessage());
        }
    }

    public boolean isCreateSchema()
    {
        return createSchema;
    }

    public boolean isValidEntity()
        throws DSEInternalErrorException, DSESQLException
    {
        Object obj = null;
        Object obj1 = null;
        boolean flag = false;
        try
        {
            Statement statement = super.databaseConnection.createStatement();
            if(statement == null)
            {
                if(Trace.doTrace("#EJ", 256, 16))
                {
                    String s = "databaseConnection.createStatement() return null";
                    Trace.trace("#EJ", 256, 16, null, s);
                }
                throw new DSEInternalErrorException("DSEException.critical", "", "databaseConnection.createStatement() return null");
            }
            String s1 = "SELECT " + JournalConstants.CT_ENTITY_COLUMN_NAME + " FROM " + controlTableName() + " WHERE " + JournalConstants.CT_ENTITY_COLUMN_NAME + " = '" + super.entity + "'";
            ResultSet resultset = statement.executeQuery(s1);
            if(resultset == null)
            {
                if(Trace.doTrace("#EJ", 256, 16))
                {
                    String s2 = "stmt.executeQuery(preparedString) returned null.";
                    Trace.trace("#EJ", 256, 16, null, s2);
                }
                throw new DSEInternalErrorException("DSEException.critical", "", "stmt.executeQuery(preparedString) returned null.");
            }
            if(resultset.next() && resultset.getString(1).equals(super.entity))
                flag = true;
            resultset.close();
            statement.close();
            return flag;
        }
        catch(SQLException sqlexception)
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s3 = "SQLException while retrieving Control Table row for Entity " + super.entity + ": " + sqlexception.getMessage();
                Trace.trace("#EJ", 1024, 8, null, s3);
            }
            throw new DSESQLException("DSEException.critical", "", sqlexception.getErrorCode(), sqlexception.getSQLState(), "SQLException while retrieving Control Table row for Entity " + super.entity + ": " + sqlexception.getMessage());
        }
    }

    private String journalTableRowInsertDefinition()
        throws DSEInvalidArgumentException, DSEInvalidRequestException, DSEInternalErrorException, DSESQLException
    {
        String s = " (";
        String s1 = ") VALUES ( ";
        try
        {
            if(JDBCServicesAdministrator.databaseConnection == null || JDBCServicesAdministrator.databaseConnection.isClosed())
                JDBCServicesAdministrator.databaseConnection = getDatabaseConnection();
            DatabaseResultSetMetaData databaseresultsetmetadata = JDBCJournalSchemaGenerator.getMetaData(schemaName, isCreateSchema());
            if(databaseresultsetmetadata == null)
            {
                if(Trace.doTrace("#EJ", 256, 16))
                {
                    String s2 = "getMetaData(schemaName) for Schema " + schemaName + " returned  null.";
                    Trace.trace("#EJ", 256, 16, null, s2);
                }
                throw new DSEInternalErrorException("DSEException.critical", "", "getMetaData(schemaName) for Schema " + schemaName + " returned null.");
            }
            int i = databaseresultsetmetadata.getColumnCount();
            if(i > 0)
            {
                for(int j = 1; j < i; j++)
                {
                    s = s + databaseresultsetmetadata.getColumnName(j) + ", ";
                    s1 = s1 + "?, ";
                }

                String s5 = s + databaseresultsetmetadata.getColumnName(i) + s1 + "? )";
                return s5;
            }
            if(Trace.doTrace("#EJ", 256, 16))
            {
                String s4 = "Journal Table columns count for Schema " + schemaName + " is 0.";
                Trace.trace("#EJ", 256, 16, null, s4);
            }
            throw new DSEInternalErrorException("DSEException.critical", "", "Journal Table columns count for Schema " + schemaName + " is 0.");
        }
        catch(SQLException sqlexception)
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s3 = "SQLException while converting record to row: " + sqlexception.getMessage();
                Trace.trace("#EJ", 1024, 8, null, s3);
            }
            throw new DSESQLException("DSEException.critical", "", sqlexception.getErrorCode(), sqlexception.getSQLState(), "SQLException while converting record to row: " + sqlexception.getMessage());
        }
    }

    private String journalTableRowUpdateDefinition()
        throws DSEInvalidArgumentException, DSEInvalidRequestException, DSEInternalErrorException, DSESQLException
    {
        String s = " SET ";
        try
        {
            if(JDBCServicesAdministrator.databaseConnection == null || JDBCServicesAdministrator.databaseConnection.isClosed())
                JDBCServicesAdministrator.databaseConnection = getDatabaseConnection();
            DatabaseResultSetMetaData databaseresultsetmetadata = JDBCJournalSchemaGenerator.getMetaData(schemaName, isCreateSchema());
            if(databaseresultsetmetadata == null)
            {
                if(Trace.doTrace("#EJ", 256, 16))
                {
                    String s1 = "getMetaData(schemaName) for Schema " + schemaName + " returned  null.";
                    Trace.trace("#EJ", 256, 16, null, s1);
                }
                throw new DSEInternalErrorException("DSEException.critical", "", "getMetaData(schemaName) for Schema " + schemaName + " returned null.");
            }
            int i = databaseresultsetmetadata.getColumnCount();
            if(i > 0)
            {
                String s3 = null;
                int j;
                for(j = 1; j < i; j++)
                {
                    s3 = databaseresultsetmetadata.getColumnName(j);
                    if(!s3.equals(JournalConstants.JT_RECNBR_COLUMN_NAME))
                        s = s + s3 + " = ?, ";
                }

                s3 = databaseresultsetmetadata.getColumnName(j);
                if(!s3.equals(JournalConstants.JT_RECNBR_COLUMN_NAME))
                    s = s + s3 + " = ?";
                return s;
            }
            if(Trace.doTrace("#EJ", 256, 16))
            {
                String s4 = "Journal Table columns count for Schema " + schemaName + " is 0.";
                Trace.trace("#EJ", 256, 16, null, s4);
            }
            throw new DSEInternalErrorException("DSEException.critical", "", "Journal Table columns count for Schema " + schemaName + " is 0.");
        }
        catch(SQLException sqlexception)
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s2 = "SQLException while converting record to row: " + sqlexception.getMessage();
                Trace.trace("#EJ", 1024, 8, null, s2);
            }
            throw new DSESQLException("DSEException.critical", "", sqlexception.getErrorCode(), sqlexception.getSQLState(), "SQLException while converting record to row: " + sqlexception.getMessage());
        }
    }

    public void loadDriver()
        throws DSEInternalErrorException
    {
        String s = getJDBCDriver();
        if(s == null)
            setJDBCDriver(JournalConstants.EJ_DEFAULT_JDBCDRIVER);
        loadDriver(s);
    }

    public void loadDriver(String s)
        throws DSEInternalErrorException
    {
        if(Trace.doTrace("#EJ", 1024, 2))
        {
            String s1 = "loadDriver (" + s + ")";
            Trace.trace("#EJ", 1024, 2, null, s1);
        }
        try
        {
            Class.forName(s).newInstance();
        }
        catch(Exception exception)
        {
            if(Trace.doTrace("#EJ", 256, 16))
            {
                String s2 = "Exception while loading JDBC driver: " + s + ". " + exception.getMessage();
                Trace.trace("#EJ", 256, 16, null, s2);
            }
            throw new DSEInternalErrorException("DSEException.critical", "", "Exception while loading JDBC driver: " + s + ". " + exception.getMessage());
        }
    }

    public static JDBCJournal newFor(String s, String s1)
        throws DSEInvalidRequestException
    {
        return new JDBCJournal(s, s1);
    }

    protected int nextWrapNumber()
        throws DSEInternalErrorException, DSEInvalidRequestException, DSEInvalidArgumentException, DSESQLException
    {
        return nextWrapNumberForSchema(schemaName);
    }

    protected int nextWrapNumberForSchema(String s)
        throws DSEInternalErrorException, DSEInvalidRequestException, DSEInvalidArgumentException, DSESQLException
    {
        try
        {
            int i = 0;
            int j = 0;
            j = currentWrapNumber();
            if(JDBCServicesAdministrator.databaseConnection == null || JDBCServicesAdministrator.databaseConnection.isClosed())
                JDBCServicesAdministrator.databaseConnection = getDatabaseConnection();
            int k = JDBCJournalSchemaGenerator.numberOfGenerations(schemaName, isCreateSchema());
            if(j == 0 || j < k)
                i = j + 1;
            else
                i = 1;
            return i;
        }
        catch(SQLException sqlexception)
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s1 = "SQLException while getting the current wrap number from Database:" + sqlexception.getMessage();
                Trace.trace("#EJ", 1024, 8, null, s1);
            }
            throw new DSESQLException("DSEException.critical", "", sqlexception.getErrorCode(), sqlexception.getSQLState(), "SQLException while getting the current wrap number from Database:" + sqlexception.getMessage());
        }
    }

    public void open(String s, int i)
        throws DSEInvalidRequestException, DSEInternalErrorException, DSESQLException
    {
        if(Trace.doTrace("#EJ", 1024, 2))
        {
            String s1 = "open(" + s + "," + i + ")";
            Trace.trace("#EJ", 1024, 2, null, s1);
        }
        if(getState().equals(JournalConstants.EJ_STATE_INACTIVE))
        {
            super.entity = s;
            wrapNumber = i;
            setTableName(tableName(schemaName, super.entity, wrapNumber));
            super.lastRecordNumber = queryLastRecordNumber();
            setState(JournalConstants.EJ_STATE_ACTIVE);
        } else
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s2 = "State must be inactive to open Journal service";
                Trace.trace("#EJ", 1024, 8, null, s2);
            }
            throw new DSEInvalidRequestException("DSEException.critical", "", "State must be inactive to open Journal service");
        }
    }

    public void open(String s, String s1, int i)
        throws DSEInvalidRequestException, DSEInternalErrorException, DSESQLException
    {
        if(Trace.doTrace("#EJ", 1024, 2))
        {
            String s2 = "open(" + s + "," + s1 + "," + i + ")";
            Trace.trace("#EJ", 1024, 2, null, s2);
        }
        if(getState().equals(JournalConstants.EJ_STATE_INACTIVE))
        {
            schemaName = s;
            super.entity = s1;
            wrapNumber = i;
            setTableName(tableName(schemaName, super.entity, wrapNumber));
            super.lastRecordNumber = queryLastRecordNumber();
            setState(JournalConstants.EJ_STATE_ACTIVE);
        } else
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s3 = "State must be inactive to open Journal service";
                Trace.trace("#EJ", 1024, 8, null, s3);
            }
            throw new DSEInvalidRequestException("DSEException.critical", "", "State must be inactive to open Journal service");
        }
    }

    public void openForEntity(String s)
        throws DSEInternalErrorException, DSEInvalidRequestException, DSEInvalidArgumentException, DSESQLException
    {
        if(Trace.doTrace("#EJ", 1024, 2))
        {
            String s1 = "openForEntity(" + s + ")";
            Trace.trace("#EJ", 1024, 2, null, s1);
        }
        if(getState().equals(JournalConstants.EJ_STATE_INACTIVE))
        {
            setEntity(s);
            open();
        } else
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s2 = "State must be inactive to open Journal service";
                Trace.trace("#EJ", 1024, 8, null, s2);
            }
            throw new DSEInvalidRequestException("DSEException.critical", "", "State must be inactive to open Journal service");
        }
    }

    public void openForEntity(String s, String s1)
        throws DSEInternalErrorException, DSEInvalidRequestException, DSEInvalidArgumentException, DSESQLException
    {
        if(Trace.doTrace("#EJ", 1024, 2))
        {
            String s2 = "openForEntity(" + s + ", " + s1 + ")";
            Trace.trace("#EJ", 1024, 2, null, s2);
        }
        if(getState().equals(JournalConstants.EJ_STATE_INACTIVE))
        {
            schemaName = s1;
            setEntity(s);
            open();
        } else
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s3 = "State must be inactive to open Journal service";
                Trace.trace("#EJ", 1024, 8, null, s3);
            }
            throw new DSEInvalidRequestException("DSEException.critical", "", "State must be inactive to open Journal service");
        }
    }

    public void openForSchema(String s)
        throws DSEInternalErrorException, DSEInvalidRequestException, DSEInvalidArgumentException, DSESQLException
    {
        if(Trace.doTrace("#EJ", 1024, 2))
        {
            String s1 = "openForSchema(" + s + ")";
            Trace.trace("#EJ", 1024, 2, null, s1);
        }
        if(getState().equals(JournalConstants.EJ_STATE_INACTIVE))
        {
            schemaName = s;
            open();
        } else
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s2 = "State must be inactive to open Journal service";
                Trace.trace("#EJ", 1024, 8, null, s2);
            }
            throw new DSEInvalidRequestException("DSEException.critical", "", "State must be inactive to open Journal service");
        }
    }

    public void openOnCurrentTable()
        throws DSEInvalidRequestException, DSEInternalErrorException, DSEInvalidArgumentException, DSESQLException
    {
        openOnCurrentTableForSchema(schemaName);
    }

    public void openOnCurrentTableForSchema(String s)
        throws DSEInvalidRequestException, DSEInternalErrorException, DSEInvalidArgumentException, DSESQLException
    {
        if(Trace.doTrace("#EJ", 1024, 2))
        {
            String s1 = "openOnCurrentTableForSchema(" + s + ")";
            Trace.trace("#EJ", 1024, 2, null, s1);
        }
        if(getState().equals(JournalConstants.EJ_STATE_INACTIVE))
        {
            wrapNumber = currentWrapNumber();
            date = today();
            setTableName(tableName(schemaName, super.entity, wrapNumber));
            try
            {
                buildAddString();
                buildUpdateString();
            }
            catch(Exception exception)
            {
                if(Trace.doTrace("#EJ", 1024, 8))
                {
                    String s4 = "Exception while building the sql sentence: " + exception.getMessage();
                    Trace.trace("#EJ", 1024, 8, null, s4);
                }
                throw new DSEInvalidRequestException("DSEException.critical", "", "Error while building the sql sentence:" + exception.getMessage());
            }
            if(super.databaseConnection != null)
            {
                try
                {
                    setAddPstmt(super.databaseConnection.prepareStatement(getAddString()));
                }
                catch(SQLException sqlexception)
                {
                    if(Trace.doTrace("#EJ", 1024, 8))
                    {
                        String s5 = "Error while saving the statement";
                        Trace.trace("#EJ", 1024, 8, null, s5);
                    }
                    throw new DSEInvalidRequestException("DSEException.critical", "", "Error while saving the statement:" + sqlexception.getMessage());
                }
            } else
            {
                if(Trace.doTrace("#EJ", 1024, 8))
                {
                    String s2 = "There is no connection to the database. The journal instance must be connected before openning it";
                    Trace.trace("#EJ", 1024, 8, null, s2);
                }
                throw new DSEInvalidRequestException("DSEException.critical", "", "The journal instance must be connected before openning it");
            }
            if(!isSingleTable())
                super.lastRecordNumber = queryLastRecordNumber();
        } else
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s3 = "State must be inactive to open Journal service";
                Trace.trace("#EJ", 1024, 8, null, s3);
            }
            throw new DSEInvalidRequestException("DSEException.critical", "", "State must be inactive to open Journal service");
        }
    }

    public void openOnNextTable()
        throws DSEInternalErrorException, DSEInvalidRequestException, DSEInvalidArgumentException, DSESQLException
    {
        openOnNextTableForSchema(schemaName);
    }

    public void openOnNextTableForSchema(String s)
        throws DSEInternalErrorException, DSEInvalidRequestException, DSEInvalidArgumentException, DSESQLException
    {
        if(Trace.doTrace("#EJ", 1024, 2))
        {
            String s1 = "openOnNextTableForSchema(" + s + ")";
            Trace.trace("#EJ", 1024, 2, null, s1);
        }
        if(getState().equals(JournalConstants.EJ_STATE_INACTIVE))
        {
            int i = nextWrapNumberForSchema(schemaName);
            Object obj = null;
            try
            {
                String s3 = "UPDATE " + controlTableName() + " SET " + JournalConstants.CT_WRAPNBR_COLUMN_NAME + " = ?, " + JournalConstants.CT_LASTDATE_COLUMN_NAME + " = ? WHERE " + JournalConstants.CT_ENTITY_COLUMN_NAME + " = '" + super.entity + "'";
                PreparedStatement preparedstatement = super.databaseConnection.prepareStatement(s3);
                if(preparedstatement == null)
                {
                    if(Trace.doTrace("#EJ", 256, 16))
                    {
                        String s4 = "databaseConnection.prepareStatement(preparedString) return null.";
                        Trace.trace("#EJ", 256, 16, null, s4);
                    }
                    throw new DSEInternalErrorException("DSEException.critical", "", "databaseConnection.prepareStatement(preparedString) return null.");
                }
                preparedstatement.setInt(1, i);
                preparedstatement.setString(2, today());
                preparedstatement.executeUpdate();
                preparedstatement.close();
                deleteJournalContent(i);
                if(Trace.doTrace("#EJ", 1024, 2))
                {
                    String s5 = "Committing all Database changes...";
                    Trace.trace("#EJ", 1024, 2, null, s5);
                }
                if(!super.autoCommit)
                    commit();
                wrapNumber = i;
                super.lastRecordNumber = 0;
                date = today();
                setTableName(tableName(schemaName, super.entity, wrapNumber));
                try
                {
                    buildAddString();
                    buildUpdateString();
                }
                catch(Exception exception) { }
                if(super.databaseConnection != null)
                {
                    try
                    {
                        setAddPstmt(super.databaseConnection.prepareStatement(getAddString()));
                    }
                    catch(SQLException sqlexception1)
                    {
                        if(Trace.doTrace("#EJ", 1024, 8))
                        {
                            String s9 = "Exception while building the sql sentence: " + sqlexception1.getMessage();
                            Trace.trace("#EJ", 1024, 8, null, s9);
                        }
                        throw new DSEInvalidRequestException("DSEException.critical", "", "Error while building the sql sentence");
                    }
                } else
                {
                    if(Trace.doTrace("#EJ", 1024, 8))
                    {
                        String s6 = "There is no connection to the database. The journal instance must be connected before openning it";
                        Trace.trace("#EJ", 1024, 8, null, s6);
                    }
                    throw new DSEInvalidRequestException("DSEException.critical", "", "The journal instance must be connected before openning it");
                }
            }
            catch(SQLException sqlexception)
            {
                if(Trace.doTrace("#EJ", 1024, 2))
                {
                    String s7 = "SQLException while openOnNextTableForSchema for Entity " + super.entity + ":" + sqlexception.getMessage() + ". Trying to rollback";
                    Trace.trace("#EJ", 1024, 2, null, s7);
                }
                if(!super.autoCommit)
                    rollback();
                if(Trace.doTrace("#EJ", 1024, 2))
                {
                    String s8 = "Rollback successful";
                    Trace.trace("#EJ", 1024, 2, null, s8);
                }
                throw new DSESQLException("DSEException.critical", "", sqlexception.getErrorCode(), sqlexception.getSQLState(), "SQLException while openOnNextTableForSchema for Entity " + super.entity + ":" + sqlexception.getMessage() + ". Rollback successful");
            }
        } else
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s2 = "State must be inactive to open Journal service";
                Trace.trace("#EJ", 1024, 8, null, s2);
            }
            throw new DSEInvalidRequestException("DSEException.critical", "", "State must be inactive to open Journal service");
        }
    }

    public int queryLastRecordNumber()
        throws DSESQLException, DSEInternalErrorException
    {
        Object obj = null;
        Object obj1 = null;
        boolean flag = false;
        try
        {
            Statement statement = super.databaseConnection.createStatement();
            if(statement == null)
            {
                if(Trace.doTrace("#EJ", 256, 16))
                {
                    String s = "databaseConnection.createStatement() return null";
                    Trace.trace("#EJ", 256, 16, null, s);
                }
                throw new DSEInternalErrorException("DSEException.critical", "", "databaseConnection.createStatement() return null");
            }
            String s1 = "SELECT MAX(" + JournalConstants.JT_RECNBR_COLUMN_NAME + ") FROM " + getTableName();
            ResultSet resultset = statement.executeQuery(s1);
            int i;
            if(resultset.next())
            {
                i = resultset.getInt(1);
            } else
            {
                if(Trace.doTrace("#EJ", 256, 16))
                {
                    String s2 = "Can not make the first row the current row.";
                    Trace.trace("#EJ", 256, 16, null, s2);
                }
                throw new DSEInternalErrorException("DSEException.critical", "", "Can not make the first row the current row.");
            }
            resultset.close();
            statement.close();
            return i;
        }
        catch(SQLException sqlexception)
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s3 = "SQLException while retrieving last Record Number from table " + getTableName() + ": " + sqlexception.getMessage();
                Trace.trace("#EJ", 1024, 8, null, s3);
            }
            throw new DSESQLException("DSEException.critical", "", sqlexception.getErrorCode(), sqlexception.getSQLState(), "SQLException while retrieving last Record Number from table " + getTableName() + ": " + sqlexception.getMessage());
        }
    }

    private Vector recordToRow(Hashtable hashtable)
        throws DSEInvalidRequestException, DSEInternalErrorException, DSEInvalidArgumentException, DSESQLException
    {
        Vector vector = new Vector();
        try
        {
            if(JDBCServicesAdministrator.databaseConnection == null || JDBCServicesAdministrator.databaseConnection.isClosed())
                JDBCServicesAdministrator.databaseConnection = getDatabaseConnection();
            DatabaseResultSetMetaData databaseresultsetmetadata = JDBCJournalSchemaGenerator.getMetaData(schemaName, isCreateSchema());
            if(databaseresultsetmetadata == null)
            {
                if(Trace.doTrace("#EJ", 256, 16))
                {
                    String s = "getMetaData() returned null.";
                    Trace.trace("#EJ", 256, 16, null, s);
                }
                throw new DSEInternalErrorException("DSEException.critical", "", "getMetaData() returned null.");
            }
            int i = databaseresultsetmetadata.getColumnCount();
            String s2 = null;
            Object obj = null;
            Object obj1 = null;
            if(i < 1)
            {
                if(Trace.doTrace("#EJ", 256, 16))
                {
                    String s5 = "Invalid number of columns: " + i;
                    Trace.trace("#EJ", 256, 16, null, s5);
                }
                throw new DSEInternalErrorException("DSEException.critical", "", "Invalid number of columns: " + i);
            }
            s2 = databaseresultsetmetadata.getColumnName(1);
            if(!s2.equals(JournalConstants.JT_RECNBR_COLUMN_NAME))
            {
                if(Trace.doTrace("#EJ", 256, 16))
                {
                    String s6 = "The first column name should be " + JournalConstants.JT_RECNBR_COLUMN_NAME + ". Actual name is " + s2;
                    Trace.trace("#EJ", 256, 16, null, s6);
                }
                throw new DSEInternalErrorException("DSEException.critical", "", "The first column name should be " + JournalConstants.JT_RECNBR_COLUMN_NAME + ". Actual name is " + s2);
            }
            for(int j = 2; j <= i; j++)
            {
                String s3 = databaseresultsetmetadata.getColumnName(j);
                String s4 = (String)columnsTable.get(s3);
                if(s4 == null)
                    s4 = s3;
                Object obj2 = hashtable.get(s4);
                vector.addElement(obj2);
            }

        }
        catch(SQLException sqlexception)
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s1 = "SQLException while converting record to row: " + sqlexception.getMessage();
                Trace.trace("#EJ", 1024, 8, null, s1);
            }
            throw new DSESQLException("DSEException.critical", "", sqlexception.getErrorCode(), sqlexception.getSQLState(), "SQLException while converting record to row: " + sqlexception.getMessage());
        }
        return vector;
    }

    private Vector recordToRow(Hashtable hashtable, Vector vector)
        throws DSEInvalidRequestException, DSEInternalErrorException, DSEInvalidArgumentException, DSESQLException
    {
        Vector vector1 = new Vector();
        try
        {
            if(JDBCServicesAdministrator.databaseConnection == null || JDBCServicesAdministrator.databaseConnection.isClosed())
                JDBCServicesAdministrator.databaseConnection = getDatabaseConnection();
            DatabaseResultSetMetaData databaseresultsetmetadata = JDBCJournalSchemaGenerator.getMetaData(schemaName, isCreateSchema());
            if(databaseresultsetmetadata == null)
            {
                if(Trace.doTrace("#EJ", 256, 16))
                {
                    String s = "getMetaData() returned null.";
                    Trace.trace("#EJ", 256, 16, null, s);
                }
                throw new DSEInternalErrorException("DSEException.critical", "", "getMetaData() returned null.");
            }
            int i = databaseresultsetmetadata.getColumnCount();
            String s2 = null;
            Object obj = null;
            Object obj1 = null;
            if(i < 1)
            {
                if(Trace.doTrace("#EJ", 256, 16))
                {
                    String s5 = "Invalid number of columns: " + i;
                    Trace.trace("#EJ", 256, 16, null, s5);
                }
                throw new DSEInternalErrorException("DSEException.critical", "", "Invalid number of columns: " + i);
            }
            s2 = databaseresultsetmetadata.getColumnName(1);
            if(!s2.equals(JournalConstants.JT_RECNBR_COLUMN_NAME))
            {
                if(Trace.doTrace("#EJ", 256, 16))
                {
                    String s6 = "The first column name should be " + JournalConstants.JT_RECNBR_COLUMN_NAME + ". Actual name is " + s2;
                    Trace.trace("#EJ", 256, 16, null, s6);
                }
                throw new DSEInternalErrorException("DSEException.critical", "", "The first column name should be " + JournalConstants.JT_RECNBR_COLUMN_NAME + ". Actual name is " + s2);
            }
            for(int j = 2; j <= i; j++)
            {
                String s3 = databaseresultsetmetadata.getColumnName(j);
                if(vector.contains(s3))
                {
                    String s4 = (String)columnsTable.get(s3);
                    if(s4 == null)
                        s4 = s3;
                    Object obj2 = hashtable.get(s4);
                    Integer integer = new Integer(databaseresultsetmetadata.getColumnType(j));
                    Vector vector2 = new Vector();
                    vector2.addElement(obj2);
                    vector2.addElement(integer);
                    vector1.addElement(vector2);
                }
            }

        }
        catch(SQLException sqlexception)
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s1 = "SQLException while converting record to row: " + sqlexception.getMessage();
                Trace.trace("#EJ", 1024, 8, null, s1);
            }
            throw new DSESQLException("DSEException.critical", "", sqlexception.getErrorCode(), sqlexception.getSQLState(), "SQLException while converting record to row: " + sqlexception.getMessage());
        }
        return vector1;
    }

    public Hashtable retrieveLastRecord()
        throws DSEInternalErrorException, DSEInvalidArgumentException, DSESQLException
    {
        if(Trace.doTrace("#EJ", 1024, 2))
        {
            String s = "Retrieving last record..... ";
            Trace.trace("#EJ", 1024, 2, null, s);
        }
        return retrieveRecord(super.lastRecordNumber);
    }

    public void retrieveLastRecord(Context context, HashtableFormat hashtableformat)
        throws DSEInvalidRequestException, DSEInternalErrorException, DSEInvalidArgumentException, DSESQLException
    {
        if(Trace.doTrace("#EJ", 1024, 2))
        {
            String s = "retrieveLastRecord(Context, HashtableFormat";
            Trace.trace("#EJ", 1024, 2, null, s);
        }
        retrieveRecord(getLastRecordNumber(), context, hashtableformat);
    }

    public void retrieveLastRecord(Context context, String s)
        throws DSEInvalidRequestException, DSEInternalErrorException, DSEInvalidArgumentException, DSESQLException
    {
        if(Trace.doTrace("#EJ", 1024, 2))
        {
            String s1 = "retrieveLastRecord(Context, " + s + ")";
            Trace.trace("#EJ", 1024, 2, null, s1);
        }
        retrieveRecord(getLastRecordNumber(), context, s);
    }

    public Vector retrieveLastRecords(int i)
        throws DSEInternalErrorException, DSEInvalidArgumentException, DSEInvalidRequestException, DSESQLException
    {
        if(Trace.doTrace("#EJ", 1024, 2))
        {
            String s = "retrieveLastRecords(" + i + ")";
            Trace.trace("#EJ", 1024, 2, null, s);
        }
        if(i == 0)
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s1 = "aNumber argument is 0.";
                Trace.trace("#EJ", 1024, 8, null, s1);
            }
            throw new DSEInvalidRequestException("DSEException.critical", "", "aNumber argument is 0.");
        }
        if(super.lastRecordNumber < 1)
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s2 = "lastRecordNumber value is less than 1: " + super.lastRecordNumber;
                Trace.trace("#EJ", 1024, 8, null, s2);
            }
            throw new DSEInvalidRequestException("DSEException.critical", "", "lastRecordNumber value is less than 1: " + super.lastRecordNumber);
        } else
        {
            String s3 = " (" + JournalConstants.JT_RECNBR_COLUMN_NAME + " <= " + super.lastRecordNumber + ") " + "AND (" + JournalConstants.JT_RECNBR_COLUMN_NAME + " > " + (super.lastRecordNumber - i) + ") ORDER BY " + JournalConstants.JT_RECNBR_COLUMN_NAME + " DESC";
            return retrieveRecords(s3);
        }
    }

    public void retrieveLastRecords(int i, Context context, HashtableIndexedCollectionFormat hashtableindexedcollectionformat)
        throws DSEInternalErrorException, DSEInvalidArgumentException, DSEInvalidRequestException, DSESQLException, DSEObjectNotFoundException
    {
        if(Trace.doTrace("#EJ", 1024, 2))
        {
            String s = "retrieveLastRecords(" + i + ")";
            Trace.trace("#EJ", 1024, 2, null, s);
        }
        if(i == 0)
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s1 = "aNumber argument is 0.";
                Trace.trace("#EJ", 1024, 8, null, s1);
            }
            throw new DSEInvalidRequestException("DSEException.critical", "", "aNumber argument is 0.");
        }
        if(super.lastRecordNumber < 1)
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s2 = "lastRecordNumber value is less than 1: " + super.lastRecordNumber;
                Trace.trace("#EJ", 1024, 8, null, s2);
            }
            throw new DSEInvalidRequestException("DSEException.critical", "", "lastRecordNumber value is less than 1: " + super.lastRecordNumber);
        }
        if(context == null)
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s3 = "aContext reference is null.";
                Trace.trace("#EJ", 1024, 8, null, s3);
            }
            throw new DSEInvalidArgumentException("DSEException.critical", "", "aContext reference is null.");
        }
        if(hashtableindexedcollectionformat == null)
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s4 = "anOutputFormat reference is null.";
                Trace.trace("#EJ", 1024, 8, null, s4);
            }
            throw new DSEInvalidArgumentException("DSEException.critical", "", "anOutputFormat reference is null.");
        } else
        {
            String s5 = " (" + JournalConstants.JT_RECNBR_COLUMN_NAME + " <= " + super.lastRecordNumber + ") " + "AND (" + JournalConstants.JT_RECNBR_COLUMN_NAME + " > " + (super.lastRecordNumber - i) + ") ORDER BY " + JournalConstants.JT_RECNBR_COLUMN_NAME + " DESC";
            retrieveRecords(s5, context, hashtableindexedcollectionformat);
            return;
        }
    }

    public void retrieveLastRecords(int i, Context context, String s)
        throws DSEInternalErrorException, DSEInvalidArgumentException, DSEInvalidRequestException, DSESQLException, DSEObjectNotFoundException
    {
        if(Trace.doTrace("#EJ", 1024, 2))
        {
            String s1 = "retrieveLastRecords(" + i + ")";
            Trace.trace("#EJ", 1024, 2, null, s1);
        }
        if(i == 0)
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s2 = "aNumber argument is 0.";
                Trace.trace("#EJ", 1024, 8, null, s2);
            }
            throw new DSEInvalidRequestException("DSEException.critical", "", "aNumber argument is 0.");
        }
        if(super.lastRecordNumber < 1)
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s3 = "lastRecordNumber value is less than 1: " + super.lastRecordNumber;
                Trace.trace("#EJ", 1024, 8, null, s3);
            }
            throw new DSEInvalidRequestException("DSEException.critical", "", "lastRecordNumber value is less than 1: " + super.lastRecordNumber);
        }
        if(context == null)
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s4 = "aContext reference is null.";
                Trace.trace("#EJ", 1024, 8, null, s4);
            }
            throw new DSEInvalidArgumentException("DSEException.critical", "", "aContext reference is null.");
        }
        if(s == null)
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s5 = "anOutputFormat reference is null.";
                Trace.trace("#EJ", 1024, 8, null, s5);
            }
            throw new DSEInvalidArgumentException("DSEException.critical", "", "anOutputFormat reference is null.");
        }
        if(s == null || s.equals(""))
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s6 = "anOutputFormatName reference is null or the argument size is 0.";
                Trace.trace("#EJ", 1024, 8, null, s6);
            }
            throw new DSEInvalidArgumentException("DSEException.critical", "", "anOutputFormatName reference is null or the argument size is 0");
        }
        try
        {
            HashtableIndexedCollectionFormat hashtableindexedcollectionformat = (HashtableIndexedCollectionFormat)FormatElement.readObject(s);
            if(hashtableindexedcollectionformat == null)
            {
                if(Trace.doTrace("#EJ", 256, 16))
                {
                    String s7 = "The formatter reference is null.";
                    Trace.trace("#EJ", 256, 16, null, s7);
                }
                throw new DSEInternalErrorException("DSEException.critical", "", "The formatter reference is null.");
            }
            retrieveLastRecords(i, context, hashtableindexedcollectionformat);
        }
        catch(IOException ioexception)
        {
            if(Trace.doTrace("#EJ", 256, 16))
            {
                String s8 = "IOException: " + ioexception.getMessage();
                Trace.trace("#EJ", 256, 16, null, s8);
            }
            throw new DSEInternalErrorException("DSEException.critical", "", ioexception.getMessage());
        }
    }

    public Hashtable retrieveRecord(int i)
        throws DSEInternalErrorException, DSEInvalidArgumentException, DSESQLException
    {
        if(Trace.doTrace("#EJ", 1024, 2))
        {
            String s = "retrieveRecord(" + i + ")";
            Trace.trace("#EJ", 1024, 2, null, s);
        }
        if(i < 1)
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s1 = "aRecordNumber is not valid: " + i;
                Trace.trace("#EJ", 1024, 8, null, s1);
            }
            throw new DSEInvalidArgumentException("DSEException.critical", "", "aRecordNumber is not valid: " + i);
        }
        if(i > super.lastRecordNumber)
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s2 = "aRecordNumber is greater than lastRecordNumber: " + super.lastRecordNumber;
                Trace.trace("#EJ", 1024, 8, null, s2);
            }
            throw new DSEInvalidArgumentException("DSEException.critical", "", "aRecordNumber is greater than lastRecordNumber: " + super.lastRecordNumber);
        }
        Object obj = null;
        Object obj1 = null;
        Hashtable hashtable = null;
        try
        {
            Statement statement = getFromStmtPool();
            if(statement == null)
            {
                if(Trace.doTrace("#EJ", 256, 16))
                {
                    String s3 = "databaseConnection.createStatement() return null";
                    Trace.trace("#EJ", 256, 16, null, s3);
                }
                throw new DSEInternalErrorException("DSEException.critical", "", "databaseConnection.createStatement() return null");
            }
            String s4 = "SELECT * FROM " + getTableName() + " WHERE " + JournalConstants.JT_RECNBR_COLUMN_NAME + " = " + i;
            ResultSet resultset = statement.executeQuery(s4);
            if(resultset == null)
            {
                if(Trace.doTrace("#EJ", 256, 16))
                {
                    String s5 = "ResultSet reference is null.";
                    Trace.trace("#EJ", 256, 16, null, s5);
                }
                throw new DSEInternalErrorException("DSEException.critical", "", "ResultSet reference is null.");
            }
            if(resultset.next())
                hashtable = rowToRecord(resultset);
            resultset.close();
            returnToStmtPool(statement);
            return hashtable;
        }
        catch(SQLException sqlexception)
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s6 = "SQLException while retrieving record number " + i + " from Table " + getTableName() + ": " + sqlexception.getMessage();
                Trace.trace("#EJ", 1024, 8, null, s6);
            }
            throw new DSESQLException("DSEException.critical", "", sqlexception.getErrorCode(), sqlexception.getSQLState(), "SQLException while retrieving record number " + i + " from Table " + getTableName() + ": " + sqlexception.getMessage());
        }
    }

    public void retrieveRecord(int i, Context context, HashtableFormat hashtableformat)
        throws DSEInvalidRequestException, DSEInternalErrorException, DSEInvalidArgumentException, DSESQLException
    {
        if(i < 1)
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s = "aRecordNumber is not valid: " + i;
                Trace.trace("#EJ", 1024, 8, null, s);
            }
            throw new DSEInvalidArgumentException("DSEException.critical", "", "aRecordNumber is not valid: " + i);
        }
        if(i > super.lastRecordNumber)
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s1 = "aRecordNumber is greater than lastRecordNumber: " + super.lastRecordNumber;
                Trace.trace("#EJ", 1024, 8, null, s1);
            }
            throw new DSEInvalidArgumentException("DSEException.critical", "", "aRecordNumber is greater than lastRecordNumber: " + super.lastRecordNumber);
        }
        if(context == null)
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s2 = "aContext reference is null.";
                Trace.trace("#EJ", 1024, 8, null, s2);
            }
            throw new DSEInvalidArgumentException("DSEException.critical", "", "aContext reference is null.");
        }
        if(hashtableformat == null)
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s3 = "aHashtableFormat reference is null.";
                Trace.trace("#EJ", 1024, 8, null, s3);
            }
            throw new DSEInvalidArgumentException("DSEException.critical", "", "aHashtableFormat reference is null.");
        }
        if(!isActive())
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s4 = "The Journal is not in ACTIVE state. Can not retrieve record from Journal.";
                Trace.trace("#EJ", 1024, 8, null, s4);
            }
            throw new DSEInvalidRequestException("DSEException.critical", "", "The Journal is not in ACTIVE state. Can not retrieve record from Journal.");
        }
        try
        {
            Hashtable hashtable = retrieveRecord(i);
            if(hashtable == null)
            {
                if(Trace.doTrace("#EJ", 256, 16))
                {
                    String s5 = "The data hashtable reference is null.";
                    Trace.trace("#EJ", 256, 16, null, s5);
                }
                throw new DSEInternalErrorException("DSEException.critical", "", "The data hashtable reference is null.");
            }
            hashtableformat.unformat(hashtable, context);
        }
        catch(Exception exception)
        {
            if(Trace.doTrace("#EJ", 256, 16))
            {
                String s6 = "Exception: " + exception.getMessage();
                Trace.trace("#EJ", 256, 16, null, s6);
            }
            throw new DSEInternalErrorException("DSEException.critical", "", exception.getMessage());
        }
    }

    public void retrieveRecord(int i, Context context, String s)
        throws DSEInvalidRequestException, DSEInternalErrorException, DSEInvalidArgumentException, DSESQLException
    {
        if(i < 1)
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s1 = "aRecordNumber is not valid: " + i;
                Trace.trace("#EJ", 1024, 8, null, s1);
            }
            throw new DSEInvalidArgumentException("DSEException.critical", "", "aRecordNumber is not valid: " + i);
        }
        if(i > super.lastRecordNumber)
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s2 = "aRecordNumber is greater than lastRecordNumber: " + super.lastRecordNumber;
                Trace.trace("#EJ", 1024, 8, null, s2);
            }
            throw new DSEInvalidArgumentException("DSEException.critical", "", "aRecordNumber is greater than lastRecordNumber: " + super.lastRecordNumber);
        }
        if(context == null)
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s3 = "aContext reference is null.";
                Trace.trace("#EJ", 1024, 8, null, s3);
            }
            throw new DSEInvalidArgumentException("DSEException.critical", "", "aContext reference is null.");
        }
        if(s == null || s.equals(""))
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s4 = "aFormatName reference is null or the argument size is 0.";
                Trace.trace("#EJ", 1024, 8, null, s4);
            }
            throw new DSEInvalidArgumentException("DSEException.critical", "", "aFormatName reference is null or the argument size is 0");
        }
        if(!isActive())
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s5 = "The Journal is not in ACTIVE state. Can not retrieve record from Journal.";
                Trace.trace("#EJ", 1024, 8, null, s5);
            }
            throw new DSEInvalidRequestException("DSEException.critical", "", "The Journal is not in ACTIVE state. Can not retrieve record from Journal.");
        }
        try
        {
            HashtableFormat hashtableformat = (HashtableFormat)FormatElement.readObject(s);
            if(hashtableformat == null)
            {
                if(Trace.doTrace("#EJ", 256, 16))
                {
                    String s6 = "The formatter reference is null.";
                    Trace.trace("#EJ", 256, 16, null, s6);
                }
                throw new DSEInternalErrorException("DSEException.critical", "", "The formatter reference is null.");
            }
            Hashtable hashtable = retrieveRecord(i);
            if(hashtable == null)
            {
                if(Trace.doTrace("#EJ", 256, 16))
                {
                    String s8 = "The data hashtable reference is null.";
                    Trace.trace("#EJ", 256, 16, null, s8);
                }
                throw new DSEInternalErrorException("DSEException.critical", "", "The data hashtable reference is null.");
            }
            hashtableformat.unformat(hashtable, context);
        }
        catch(Exception exception)
        {
            if(Trace.doTrace("#EJ", 256, 16))
            {
                String s7 = "Exception: " + exception.getMessage();
                Trace.trace("#EJ", 256, 16, null, s7);
            }
            throw new DSEInternalErrorException("DSEException.critical", "", exception.getMessage());
        }
    }

    public Vector retrieveRecords(String s)
        throws DSEInternalErrorException, DSEInvalidRequestException, DSEInvalidArgumentException, DSESQLException
    {
        if(Trace.doTrace("#EJ", 1024, 2))
        {
            String s1 = "retrieveRecords(" + s + ")";
            Trace.trace("#EJ", 1024, 2, null, s1);
        }
        if(s == null || s.equals(""))
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s2 = "aSearchCondition value is not valid: " + s;
                Trace.trace("#EJ", 1024, 8, null, s2);
            }
            throw new DSEInvalidRequestException("DSEException.critical", "", "aSearchCondition value is not valid: " + s);
        }
        Object obj = null;
        Object obj1 = null;
        Object obj2 = null;
        Object obj3 = null;
        try
        {
            Statement statement = getFromStmtPool();
            if(statement == null)
            {
                if(Trace.doTrace("#EJ", 256, 16))
                {
                    String s3 = "databaseConnection.createStatement() return null";
                    Trace.trace("#EJ", 256, 16, null, s3);
                }
                throw new DSEInternalErrorException("DSEException.critical", "", "databaseConnection.createStatement() return null");
            }
            String s4 = "SELECT * FROM " + getTableName() + " WHERE " + s;
            ResultSet resultset = statement.executeQuery(s4);
            Vector vector;
            if(resultset != null)
            {
                vector = allRowsToRecords(resultset);
                resultset.close();
            } else
            {
                if(Trace.doTrace("#EJ", 256, 16))
                {
                    String s5 = "ResultSet reference is null.";
                    Trace.trace("#EJ", 256, 16, null, s5);
                }
                throw new DSEInternalErrorException("DSEException.critical", "", "ResultSet reference is null.");
            }
            returnToStmtPool(statement);
            return vector;
        }
        catch(SQLException sqlexception)
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s6 = "SQLException while retrieving records from table " + getTableName() + ": " + sqlexception.getMessage();
                Trace.trace("#EJ", 1024, 8, null, s6);
            }
            throw new DSESQLException("DSEException.critical", "", sqlexception.getErrorCode(), sqlexception.getSQLState(), "SQLException while retrieving records from table " + getTableName() + ": " + sqlexception.getMessage());
        }
    }

    public void retrieveRecords(String s, Context context, HashtableIndexedCollectionFormat hashtableindexedcollectionformat)
        throws DSEInternalErrorException, DSEInvalidRequestException, DSEInvalidArgumentException, DSESQLException, DSEObjectNotFoundException
    {
        if(Trace.doTrace("#EJ", 1024, 2))
        {
            String s1 = "retrieveRecords(" + s + ")";
            Trace.trace("#EJ", 1024, 2, null, s1);
        }
        if(s == null || s.equals(""))
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s2 = "aSearchCondition value is not valid: " + s;
                Trace.trace("#EJ", 1024, 8, null, s2);
            }
            throw new DSEInvalidRequestException("DSEException.critical", "", "aSearchCondition value is not valid: " + s);
        }
        if(context == null)
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s3 = "aContext reference is null.";
                Trace.trace("#EJ", 1024, 8, null, s3);
            }
            throw new DSEInvalidArgumentException("DSEException.critical", "", "aContext reference is null.");
        }
        if(hashtableindexedcollectionformat == null)
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s4 = "anOutputFormat reference is null.";
                Trace.trace("#EJ", 1024, 8, null, s4);
            }
            throw new DSEInvalidArgumentException("DSEException.critical", "", "anOutputFormat reference is null.");
        }
        Object obj = null;
        Object obj1 = null;
        Object obj2 = null;
        Object obj3 = null;
        try
        {
            Statement statement = getFromStmtPool();
            if(statement == null)
            {
                if(Trace.doTrace("#EJ", 256, 16))
                {
                    String s5 = "databaseConnection.createStatement() return null";
                    Trace.trace("#EJ", 256, 16, null, s5);
                }
                throw new DSEInternalErrorException("DSEException.critical", "", "databaseConnection.createStatement() return null");
            }
            String s6 = "SELECT * FROM " + getTableName() + " WHERE " + s;
            ResultSet resultset = statement.executeQuery(s6);
            Vector vector;
            if(resultset != null)
            {
                vector = allRowsToRecords(resultset);
                resultset.close();
            } else
            {
                if(Trace.doTrace("#EJ", 256, 16))
                {
                    String s7 = "ResultSet reference is null.";
                    Trace.trace("#EJ", 256, 16, null, s7);
                }
                throw new DSEInternalErrorException("DSEException.critical", "", "ResultSet reference is null.");
            }
            returnToStmtPool(statement);
            if(Trace.doTrace("#EJ", 1024, 2))
            {
                String s8 = "Unformatting " + vector.size() + " records...";
                Trace.trace("#EJ", 1024, 2, null, s8);
            }
            hashtableindexedcollectionformat.unformat(vector, context);
        }
        catch(IOException ioexception)
        {
            if(Trace.doTrace("#EJ", 256, 16))
            {
                String s9 = "IOException: " + ioexception.getMessage();
                Trace.trace("#EJ", 256, 16, null, s9);
            }
            throw new DSEInternalErrorException("DSEException.critical", "", ioexception.getMessage());
        }
        catch(SQLException sqlexception)
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s10 = "SQLException while retrieving records from table " + getTableName() + ": " + sqlexception.getMessage();
                Trace.trace("#EJ", 1024, 8, null, s10);
            }
            throw new DSESQLException("DSEException.critical", "", sqlexception.getErrorCode(), sqlexception.getSQLState(), "SQLException while retrieving records from table " + getTableName() + ": " + sqlexception.getMessage());
        }
    }

    public void retrieveRecords(String s, Context context, String s1)
        throws DSEInternalErrorException, DSEInvalidRequestException, DSEInvalidArgumentException, DSESQLException, DSEObjectNotFoundException
    {
        if(Trace.doTrace("#EJ", 1024, 2))
        {
            String s2 = "retrieveRecords(" + s + ")";
            Trace.trace("#EJ", 1024, 2, null, s2);
        }
        if(s == null || s.equals(""))
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s3 = "aSearchCondition value is not valid: " + s;
                Trace.trace("#EJ", 1024, 8, null, s3);
            }
            throw new DSEInvalidRequestException("DSEException.critical", "", "aSearchCondition value is not valid: " + s);
        }
        if(context == null)
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s4 = "aContext reference is null.";
                Trace.trace("#EJ", 1024, 8, null, s4);
            }
            throw new DSEInvalidArgumentException("DSEException.critical", "", "aContext reference is null.");
        }
        if(s1 == null || s1.equals(""))
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s5 = "anOutputFormatName reference is null or the argument size is 0.";
                Trace.trace("#EJ", 1024, 8, null, s5);
            }
            throw new DSEInvalidArgumentException("DSEException.critical", "", "anOutputFormatName reference is null or the argument size is 0");
        }
        try
        {
            HashtableIndexedCollectionFormat hashtableindexedcollectionformat = (HashtableIndexedCollectionFormat)FormatElement.readObject(s1);
            if(hashtableindexedcollectionformat == null)
            {
                if(Trace.doTrace("#EJ", 256, 16))
                {
                    String s6 = "The formatter reference is null.";
                    Trace.trace("#EJ", 256, 16, null, s6);
                }
                throw new DSEInternalErrorException("DSEException.critical", "", "The formatter reference is null.");
            }
            retrieveRecords(s, context, hashtableindexedcollectionformat);
        }
        catch(IOException ioexception)
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s7 = "IOException: " + ioexception.getMessage();
                Trace.trace("#EJ", 1024, 8, null, s7);
            }
            throw new DSEInternalErrorException("DSEException.critical", "", ioexception.getMessage());
        }
    }

    public Vector retrieveRecords(String s, Vector vector)
        throws DSEInternalErrorException, DSEInvalidRequestException, DSEInvalidArgumentException, DSESQLException
    {
        if(Trace.doTrace("#EJ", 1024, 2))
        {
            String s1 = "retrieveRecords(" + s + ")";
            Trace.trace("#EJ", 1024, 2, null, s1);
        }
        if(s == null || s.equals(""))
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s2 = "aSearchCondition value is not valid: " + s;
                Trace.trace("#EJ", 1024, 8, null, s2);
            }
            throw new DSEInvalidRequestException("DSEException.critical", "", "aSearchCondition value is not valid: " + s);
        }
        if(vector == null || vector.isEmpty())
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s3 = "Invalid columns vector. At least one column should be specified";
                Trace.trace("#EJ", 1024, 8, null, s3);
            }
            throw new DSEInvalidArgumentException("DSEException.critical", "", "Invalid columns vector. At least one column should be specified");
        }
        Object obj = null;
        Object obj1 = null;
        Object obj2 = null;
        Object obj3 = null;
        try
        {
            Statement statement = getFromStmtPool();
            if(statement == null)
            {
                if(Trace.doTrace("#EJ", 256, 16))
                {
                    String s4 = "getDatabaseConnection().createStatement() return null";
                    Trace.trace("#EJ", 256, 16, null, s4);
                }
                throw new DSEInternalErrorException("DSEException.critical", "", "getDatabaseConnection().createStatement() return null");
            }
            String s5 = "";
            for(Enumeration enumeration = vector.elements(); enumeration.hasMoreElements();)
            {
                String s6 = (String)enumeration.nextElement();
                if(enumeration.hasMoreElements())
                    s5 = s5 + s6 + ", ";
                else
                    s5 = s5 + s6;
            }

            String s8 = "SELECT " + s5 + " FROM " + getTableName() + " WHERE " + s;
            ResultSet resultset = statement.executeQuery(s8);
            Vector vector1;
            if(resultset != null)
            {
                vector1 = allRowsToRecordsWithSomeColumns(resultset);
                resultset.close();
            } else
            {
                if(Trace.doTrace("#EJ", 256, 16))
                {
                    String s9 = "ResultSet reference is null.";
                    Trace.trace("#EJ", 256, 16, null, s9);
                }
                throw new DSEInternalErrorException("DSEException.critical", "", "ResultSet reference is null.");
            }
            returnToStmtPool(statement);
            return vector1;
        }
        catch(SQLException sqlexception)
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s7 = "SQLException while retrieving records from table " + getTableName() + ": " + sqlexception.getMessage();
                Trace.trace("#EJ", 1024, 8, null, s7);
            }
            throw new DSESQLException("DSEException.critical", "", sqlexception.getErrorCode(), sqlexception.getSQLState(), "SQLException while retrieving records from table " + getTableName() + ": " + sqlexception.getMessage());
        }
    }

    public void retrieveRecords(String s, Vector vector, Context context, HashtableIndexedCollectionFormat hashtableindexedcollectionformat)
        throws DSEInternalErrorException, DSEInvalidRequestException, DSEInvalidArgumentException, DSESQLException, DSEObjectNotFoundException
    {
        if(Trace.doTrace("#EJ", 1024, 2))
        {
            String s1 = "retrieveRecords(" + s + ")";
            Trace.trace("#EJ", 1024, 2, null, s1);
        }
        if(s == null || s.equals(""))
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s2 = "aSearchCondition value is not valid: " + s;
                Trace.trace("#EJ", 1024, 8, null, s2);
            }
            throw new DSEInvalidRequestException("DSEException.critical", "", "aSearchCondition value is not valid: " + s);
        }
        if(vector == null || vector.isEmpty())
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s3 = "Invalid columns vector. At least one column should be specified";
                Trace.trace("#EJ", 1024, 8, null, s3);
            }
            throw new DSEInvalidArgumentException("DSEException.critical", "", "Invalid columns vector. At least one column should be specified");
        }
        if(context == null)
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s4 = "aContext reference is null.";
                Trace.trace("#EJ", 1024, 8, null, s4);
            }
            throw new DSEInvalidArgumentException("DSEException.critical", "", "aContext reference is null.");
        }
        if(hashtableindexedcollectionformat == null)
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s5 = "anOutputFormat reference is null.";
                Trace.trace("#EJ", 1024, 8, null, s5);
            }
            throw new DSEInvalidArgumentException("DSEException.critical", "", "anOutputFormat reference is null.");
        }
        Object obj = null;
        Object obj1 = null;
        Object obj2 = null;
        Object obj3 = null;
        try
        {
            Statement statement = getFromStmtPool();
            if(statement == null)
            {
                if(Trace.doTrace("#EJ", 256, 16))
                {
                    String s6 = "getDatabaseConnection().createStatement() return null";
                    Trace.trace("#EJ", 256, 16, null, s6);
                }
                throw new DSEInternalErrorException("DSEException.critical", "", "getDatabaseConnection().createStatement() return null");
            }
            String s7 = "";
            for(Enumeration enumeration = vector.elements(); enumeration.hasMoreElements();)
            {
                String s8 = (String)enumeration.nextElement();
                if(enumeration.hasMoreElements())
                    s7 = s7 + s8 + ", ";
                else
                    s7 = s7 + s8;
            }

            String s11 = "SELECT " + s7 + " FROM " + getTableName() + " WHERE " + s;
            ResultSet resultset = statement.executeQuery(s11);
            Vector vector1;
            if(resultset != null)
            {
                vector1 = allRowsToRecordsWithSomeColumns(resultset);
                resultset.close();
            } else
            {
                if(Trace.doTrace("#EJ", 256, 16))
                {
                    String s12 = "ResultSet reference is null.";
                    Trace.trace("#EJ", 256, 16, null, s12);
                }
                throw new DSEInternalErrorException("DSEException.critical", "", "ResultSet reference is null.");
            }
            returnToStmtPool(statement);
            if(Trace.doTrace("#EJ", 1024, 2))
            {
                String s13 = "Unformatting " + vector1.size() + " records...";
                Trace.trace("#EJ", 1024, 2, null, s13);
            }
            hashtableindexedcollectionformat.unformat(vector1, context);
        }
        catch(IOException ioexception)
        {
            if(Trace.doTrace("#EJ", 256, 16))
            {
                String s9 = "IOException: " + ioexception.getMessage();
                Trace.trace("#EJ", 256, 16, null, s9);
            }
            throw new DSEInternalErrorException("DSEException.critical", "", ioexception.getMessage());
        }
        catch(SQLException sqlexception)
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s10 = "SQLException while retrieving records from table " + getTableName() + ": " + sqlexception.getMessage();
                Trace.trace("#EJ", 1024, 8, null, s10);
            }
            throw new DSESQLException("DSEException.critical", "", sqlexception.getErrorCode(), sqlexception.getSQLState(), "SQLException while retrieving records from table " + getTableName() + ": " + sqlexception.getMessage());
        }
    }

    public void retrieveRecords(String s, Vector vector, Context context, String s1)
        throws DSEInternalErrorException, DSEInvalidRequestException, DSEInvalidArgumentException, DSESQLException, DSEObjectNotFoundException
    {
        if(Trace.doTrace("#EJ", 1024, 2))
        {
            String s2 = "retrieveRecords(" + s + ")";
            Trace.trace("#EJ", 1024, 2, null, s2);
        }
        if(s == null || s.equals(""))
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s3 = "aSearchCondition value is not valid: " + s;
                Trace.trace("#EJ", 1024, 8, null, s3);
            }
            throw new DSEInvalidRequestException("DSEException.critical", "", "aSearchCondition value is not valid: " + s);
        }
        if(vector == null || vector.isEmpty())
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s4 = "Invalid columns vector. At least one column should be specified";
                Trace.trace("#EJ", 1024, 8, null, s4);
            }
            throw new DSEInvalidArgumentException("DSEException.critical", "", "Invalid columns vector. At least one column should be specified");
        }
        if(context == null)
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s5 = "aContext reference is null.";
                Trace.trace("#EJ", 1024, 8, null, s5);
            }
            throw new DSEInvalidArgumentException("DSEException.critical", "", "aContext reference is null.");
        }
        if(s1 == null || s1.equals(""))
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s6 = "anOutputFormatName reference is null or the argument size is 0.";
                Trace.trace("#EJ", 1024, 8, null, s6);
            }
            throw new DSEInvalidArgumentException("DSEException.critical", "", "anOutputFormatName reference is null or the argument size is 0");
        }
        try
        {
            HashtableIndexedCollectionFormat hashtableindexedcollectionformat = (HashtableIndexedCollectionFormat)FormatElement.readObject(s1);
            if(hashtableindexedcollectionformat == null)
            {
                if(Trace.doTrace("#EJ", 256, 16))
                {
                    String s7 = "The formatter reference is null.";
                    Trace.trace("#EJ", 256, 16, null, s7);
                }
                throw new DSEInternalErrorException("DSEException.critical", "", "The formatter reference is null.");
            }
            retrieveRecords(s, vector, context, hashtableindexedcollectionformat);
        }
        catch(IOException ioexception)
        {
            if(Trace.doTrace("#EJ", 256, 16))
            {
                String s8 = "IOException: " + ioexception.getMessage();
                Trace.trace("#EJ", 256, 16, null, s8);
            }
            throw new DSEInternalErrorException("DSEException.critical", "", ioexception.getMessage());
        }
    }

    private void returnToStmtPool(Statement statement)
        throws SQLException
    {
        if(statement == null)
            return;
        synchronized(this)
        {
            if(super.stmtPool.size() < super.stmtPoolSize)
            {
                super.stmtPool.add(statement);
                return;
            }
        }
        statement.close();
    }

    public void rollback()
        throws DSESQLException
    {
        if(!super.autoCommit)
            try
            {
                super.databaseConnection.rollback();
            }
            catch(SQLException sqlexception)
            {
                if(Trace.doTrace("#EJ", 1024, 8))
                {
                    String s1 = "SQLException while rolling back Database changes for Entity " + super.entity + ": " + sqlexception.getMessage() + ". Rollback unsuccessful";
                    Trace.trace("#EJ", 1024, 8, null, s1);
                }
                throw new DSESQLException("DSEException.critical", "", sqlexception.getErrorCode(), sqlexception.getSQLState(), "SQLException while rolling back Database changes for Entity " + super.entity + ": " + sqlexception.getMessage() + ". Rollback unsuccessful");
            }
        else
        if(Trace.doTrace("#EJ", 1024, 8))
        {
            String s = "Journal is set with AUTOCOMMIT=TRUE. Rollback not executed.";
            Trace.trace("#EJ", 1024, 8, null, s);
        }
    }

    private Hashtable rowToRecord(ResultSet resultset)
        throws DSEInvalidArgumentException, DSEInternalErrorException, DSESQLException
    {
        Hashtable hashtable = new Hashtable();
        try
        {
            if(JDBCServicesAdministrator.databaseConnection == null || JDBCServicesAdministrator.databaseConnection.isClosed())
                JDBCServicesAdministrator.databaseConnection = getDatabaseConnection();
            ResultSetMetaData resultsetmetadata = resultset.getMetaData();
            if(resultsetmetadata == null)
            {
                if(Trace.doTrace("#EJ", 256, 16))
                {
                    String s = "aResultSet.getMetaData() returned null.";
                    Trace.trace("#EJ", 256, 16, null, s);
                }
                throw new DSEInternalErrorException("DSEException.critical", "", "aResultSet.getMetaData() returned null.");
            }
            int i = resultsetmetadata.getColumnCount();
            String s2 = null;
            Object obj = null;
            Object obj1 = null;
            if(i < 1)
            {
                if(Trace.doTrace("#EJ", 256, 16))
                {
                    String s5 = "Invalid number of columns: " + i;
                    Trace.trace("#EJ", 256, 16, null, s5);
                }
                throw new DSEInternalErrorException("DSEException.critical", "", "Invalid number of columns: " + i);
            }
            s2 = resultsetmetadata.getColumnName(1);
            if(!s2.equals(JournalConstants.JT_RECNBR_COLUMN_NAME))
            {
                if(Trace.doTrace("#EJ", 256, 16))
                {
                    String s6 = "The first column name should be " + JournalConstants.JT_RECNBR_COLUMN_NAME + ". Actual name is " + s2;
                    Trace.trace("#EJ", 256, 16, null, s6);
                }
                throw new DSEInternalErrorException("DSEException.critical", "", "The first column name should be " + JournalConstants.JT_RECNBR_COLUMN_NAME + ". Actual name is " + s2);
            }
            for(int j = 2; j <= i; j++)
            {
                String s3 = resultsetmetadata.getColumnName(j);
                String s4 = (String)columnsTable.get(s3);
                if(s4 == null)
                    s4 = s3;
                Object obj2 = resultset.getObject(s3);
                if(obj2 != null)
                    hashtable.put(s4, obj2);
            }

        }
        catch(SQLException sqlexception)
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s1 = "SQLException while converting row to record: " + sqlexception.getMessage();
                Trace.trace("#EJ", 1024, 8, null, s1);
            }
            throw new DSESQLException("DSEException.critical", "", sqlexception.getErrorCode(), sqlexception.getSQLState(), "SQL Exception while converting row to record: " + sqlexception.getMessage());
        }
        return hashtable;
    }

    protected void setColumnsTable(Hashtable hashtable)
    {
        columnsTable = hashtable;
    }

    public void setCreateSchema(boolean flag)
    {
        createSchema = flag;
    }

    protected void setDatabaseConnection(Connection connection)
    {
        super.databaseConnection = connection;
    }

    public void setEntity(String s)
    {
        super.entity = s;
        setTableName(tableName(schemaName, super.entity, wrapNumber));
    }

    public void setJDBCDriver(String s)
    {
        JDBCDriver = s;
        buildConnectionProperties();
    }

    public void setSchemaName(String s)
    {
        schemaName = s;
        setTableName(tableName(schemaName, super.entity, wrapNumber));
    }

    protected void setTableName(String s)
    {
        tableName = s;
    }

    protected String tableName(String s, String s1, int i)
    {
        return s + "." + s1 + "_" + i;
    }

    public Vector toStrings()
    {
        Vector vector = new Vector();
        String s = "<" + getTagName() + " ";
        s = JavaExtensions.addAttribute(s, "id", getName());
        if(super.autoCommit)
            s = JavaExtensions.addAttribute(s, "autoCommit", "true");
        s = JavaExtensions.addAttribute(s, "schema", getSchemaName());
        if(createSchema)
            s = JavaExtensions.addAttribute(s, "createSchema", "true");
        s = JavaExtensions.addAttribute(s, "poolName", getPoolName());
        s = JavaExtensions.addAttribute(s, "JDBCDriver", getJDBCDriver());
        if(super.waitRetry)
            s = JavaExtensions.addAttribute(s, "waitRetry", "true");
        s = JavaExtensions.addAttribute(s, "dataSourceName", getDataSourceName());
        s = JavaExtensions.addAttribute(s, "sharedConnection", getSharedConnection());
        s = s + ">";
        vector.addElement(s);
        String s2;
        for(Enumeration enumeration = getColumnsTable().keys(); enumeration.hasMoreElements(); vector.addElement(s2))
        {
            String s1 = (String)enumeration.nextElement();
            s2 = "<column ";
            s2 = JavaExtensions.addAttribute(s2, "id", s1);
            s2 = JavaExtensions.addAttribute(s2, "dataName", (String)getColumnsTable().get(s1));
            s2 = s2 + "/>";
        }

        vector.addElement("</" + getTagName() + ">");
        vector.tabulate();
        return vector;
    }

    public int updateLastRecord(Context context, HashtableFormat hashtableformat)
        throws DSEInvalidRequestException, DSEInternalErrorException, DSEInvalidArgumentException, DSESQLException
    {
        int i = queryLastRecordNumber();
        String s = JournalConstants.RECNCR_SEARCH_CONDITION + i;
        return updateRecords(s, context, hashtableformat);
    }

    public int updateLastRecord(Context context, HashtableFormat hashtableformat, Vector vector)
        throws DSEInvalidRequestException, DSEInternalErrorException, DSEInvalidArgumentException, DSESQLException
    {
        int i = queryLastRecordNumber();
        String s = JournalConstants.RECNCR_SEARCH_CONDITION + i;
        return updateRecords(s, context, hashtableformat, vector);
    }

    public int updateLastRecord(Context context, String s)
        throws DSEInvalidRequestException, DSEInternalErrorException, DSEInvalidArgumentException, DSESQLException
    {
        int i = queryLastRecordNumber();
        String s1 = JournalConstants.RECNCR_SEARCH_CONDITION + i;
        return updateRecords(s1, context, s);
    }

    public int updateLastRecord(Context context, String s, Vector vector)
        throws DSEInvalidRequestException, DSEInternalErrorException, DSEInvalidArgumentException, DSESQLException
    {
        int i = queryLastRecordNumber();
        String s1 = JournalConstants.RECNCR_SEARCH_CONDITION + i;
        return updateRecords(s1, context, s, vector);
    }

    public int updateLastRecord(Hashtable hashtable)
        throws DSEInvalidRequestException, DSEInternalErrorException, DSEInvalidArgumentException, DSESQLException
    {
        int i = queryLastRecordNumber();
        String s = JournalConstants.RECNCR_SEARCH_CONDITION + i;
        return updateRecords(s, hashtable);
    }

    public int updateLastRecord(Hashtable hashtable, Vector vector)
        throws DSEInvalidRequestException, DSEInternalErrorException, DSEInvalidArgumentException, DSESQLException
    {
        int i = queryLastRecordNumber();
        String s = JournalConstants.RECNCR_SEARCH_CONDITION + i;
        return updateRecords(s, hashtable, vector);
    }

    public int updateRecord(int i, Context context, HashtableFormat hashtableformat)
        throws DSEInvalidRequestException, DSEInternalErrorException, DSEInvalidArgumentException, DSESQLException
    {
        String s = JournalConstants.RECNCR_SEARCH_CONDITION + i;
        return updateRecords(s, context, hashtableformat);
    }

    public int updateRecord(int i, Context context, HashtableFormat hashtableformat, Vector vector)
        throws DSEInvalidRequestException, DSEInternalErrorException, DSEInvalidArgumentException, DSESQLException
    {
        String s = JournalConstants.RECNCR_SEARCH_CONDITION + i;
        return updateRecords(s, context, hashtableformat, vector);
    }

    public int updateRecord(int i, Context context, String s)
        throws DSEInvalidRequestException, DSEInternalErrorException, DSEInvalidArgumentException, DSESQLException
    {
        String s1 = JournalConstants.RECNCR_SEARCH_CONDITION + i;
        return updateRecords(s1, context, s);
    }

    public int updateRecord(int i, Context context, String s, Vector vector)
        throws DSEInvalidRequestException, DSEInternalErrorException, DSEInvalidArgumentException, DSESQLException
    {
        String s1 = JournalConstants.RECNCR_SEARCH_CONDITION + i;
        return updateRecords(s1, context, s, vector);
    }

    public int updateRecord(int i, Hashtable hashtable)
        throws DSEInvalidRequestException, DSEInternalErrorException, DSEInvalidArgumentException, DSESQLException
    {
        String s = JournalConstants.RECNCR_SEARCH_CONDITION + i;
        return updateRecords(s, hashtable);
    }

    public int updateRecord(int i, Hashtable hashtable, Vector vector)
        throws DSEInvalidArgumentException, DSEInternalErrorException, DSEInvalidRequestException, DSESQLException
    {
        String s = JournalConstants.RECNCR_SEARCH_CONDITION + i;
        return updateRecords(s, hashtable, vector);
    }

    public int updateRecords(String s, Context context, HashtableFormat hashtableformat)
        throws DSEInvalidRequestException, DSEInternalErrorException, DSEInvalidArgumentException, DSESQLException
    {
        if(s == null || s.equals(""))
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s1 = "aSearchCondition value is not valid: " + s;
                Trace.trace("#EJ", 1024, 8, null, s1);
            }
            throw new DSEInvalidArgumentException("DSEException.critical", "", "aSearchCondition is either null or size is 0.");
        }
        if(context == null)
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s2 = "aContext reference is null.";
                Trace.trace("#EJ", 1024, 8, null, s2);
            }
            throw new DSEInvalidArgumentException("DSEException.critical", "", "aContext reference is null.");
        }
        if(hashtableformat == null)
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s3 = "aHashtableFormat reference is null.";
                Trace.trace("#EJ", 1024, 8, null, s3);
            }
            throw new DSEInvalidArgumentException("DSEException.critical", "", "aHashtableFormat reference is null.");
        }
        try
        {
            Hashtable hashtable = hashtableformat.format(context);
            if(hashtable == null)
            {
                if(Trace.doTrace("#EJ", 256, 16))
                {
                    String s4 = "The data hashtable reference is null.";
                    Trace.trace("#EJ", 256, 16, null, s4);
                }
                throw new DSEInternalErrorException("DSEException.critical", "", "The data hashtable reference is null.");
            } else
            {
                return updateRecords(s, hashtable);
            }
        }
        catch(Exception exception)
        {
            if(Trace.doTrace("#EJ", 256, 16))
            {
                String s5 = "Exception: " + exception.getMessage();
                Trace.trace("#EJ", 256, 16, null, s5);
            }
            throw new DSEInternalErrorException("DSEException.critical", "", exception.getMessage());
        }
    }

    public int updateRecords(String s, Context context, HashtableFormat hashtableformat, Vector vector)
        throws DSEInvalidRequestException, DSEInternalErrorException, DSEInvalidArgumentException, DSESQLException
    {
        if(s == null || s.equals(""))
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s1 = "aSearchCondition value is not valid: " + s;
                Trace.trace("#EJ", 1024, 8, null, s1);
            }
            throw new DSEInvalidArgumentException("DSEException.critical", "", "aSearchCondition is either null or size is 0.");
        }
        if(context == null)
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s2 = "aContext reference is null.";
                Trace.trace("#EJ", 1024, 8, null, s2);
            }
            throw new DSEInvalidArgumentException("DSEException.critical", "", "aContext reference is null.");
        }
        if(hashtableformat == null)
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s3 = "aHashtableFormat reference is null.";
                Trace.trace("#EJ", 1024, 8, null, s3);
            }
            throw new DSEInvalidArgumentException("DSEException.critical", "", "aHashtableFormat reference is null.");
        }
        if(vector == null || vector.isEmpty())
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s4 = "Invalid columns vector. At least one column should be specified";
                Trace.trace("#EJ", 1024, 8, null, s4);
            }
            throw new DSEInvalidArgumentException("DSEException.critical", "", "Invalid columns vector. At least one column should be specified");
        }
        try
        {
            Hashtable hashtable = hashtableformat.format(context);
            if(hashtable == null)
            {
                if(Trace.doTrace("#EJ", 256, 16))
                {
                    String s5 = "The data hashtable reference is null.";
                    Trace.trace("#EJ", 256, 16, null, s5);
                }
                throw new DSEInternalErrorException("DSEException.critical", "", "The data hashtable reference is null.");
            } else
            {
                return updateRecords(s, hashtable, vector);
            }
        }
        catch(Exception exception)
        {
            if(Trace.doTrace("#EJ", 256, 16))
            {
                String s6 = "Exception: " + exception.getMessage();
                Trace.trace("#EJ", 256, 16, null, s6);
            }
            throw new DSEInternalErrorException("DSEException.critical", "", exception.getMessage());
        }
    }

    public int updateRecords(String s, Context context, String s1)
        throws DSEInvalidRequestException, DSEInternalErrorException, DSEInvalidArgumentException, DSESQLException
    {
        if(s == null || s.equals(""))
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s2 = "aSearchCondition value is not valid: " + s;
                Trace.trace("#EJ", 1024, 8, null, s2);
            }
            throw new DSEInvalidArgumentException("DSEException.critical", "", "aSearchCondition is either null or size is 0.");
        }
        if(context == null)
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s3 = "aContext reference is null.";
                Trace.trace("#EJ", 1024, 8, null, s3);
            }
            throw new DSEInvalidArgumentException("DSEException.critical", "", "aContext reference is null.");
        }
        if(s1 == null || s1.equals(""))
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s4 = "aFormatName reference is null or the argument size is 0.";
                Trace.trace("#EJ", 1024, 8, null, s4);
            }
            throw new DSEInvalidArgumentException("DSEException.critical", "", "aFormatName reference is null or the argument size is 0");
        }
        try
        {
            HashtableFormat hashtableformat = (HashtableFormat)FormatElement.readObject(s1);
            if(hashtableformat == null)
            {
                if(Trace.doTrace("#EJ", 256, 16))
                {
                    String s5 = "The formatter reference is null.";
                    Trace.trace("#EJ", 256, 16, null, s5);
                }
                throw new DSEInternalErrorException("DSEException.critical", "", "The formatter reference is null.");
            }
            Hashtable hashtable = hashtableformat.format(context);
            if(hashtable == null)
            {
                if(Trace.doTrace("#EJ", 256, 16))
                {
                    String s7 = "The data hashtable reference is null.";
                    Trace.trace("#EJ", 256, 16, null, s7);
                }
                throw new DSEInternalErrorException("DSEException.critical", "", "The data hashtable reference is null.");
            } else
            {
                return updateRecords(s, hashtable);
            }
        }
        catch(Exception exception)
        {
            if(Trace.doTrace("#EJ", 256, 16))
            {
                String s6 = "Exception: " + exception.getMessage();
                Trace.trace("#EJ", 256, 16, null, s6);
            }
            throw new DSEInternalErrorException("DSEException.critical", "", exception.getMessage());
        }
    }

    public int updateRecords(String s, Context context, String s1, Vector vector)
        throws DSEInvalidRequestException, DSEInternalErrorException, DSEInvalidArgumentException, DSESQLException
    {
        if(s == null || s.equals(""))
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s2 = "aSearchCondition value is not valid: " + s;
                Trace.trace("#EJ", 1024, 8, null, s2);
            }
            throw new DSEInvalidArgumentException("DSEException.critical", "", "aSearchCondition is either null or size is 0.");
        }
        if(context == null)
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s3 = "aContext reference is null.";
                Trace.trace("#EJ", 1024, 8, null, s3);
            }
            throw new DSEInvalidArgumentException("DSEException.critical", "", "aContext reference is null.");
        }
        if(s1 == null || s1.equals(""))
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s4 = "aFormatName reference is null or the argument size is 0.";
                Trace.trace("#EJ", 1024, 8, null, s4);
            }
            throw new DSEInvalidArgumentException("DSEException.critical", "", "aFormatName reference is null or the argument size is 0");
        }
        if(vector == null || vector.isEmpty())
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s5 = "Invalid columns vector. At least one column should be specified";
                Trace.trace("#EJ", 1024, 8, null, s5);
            }
            throw new DSEInvalidArgumentException("DSEException.critical", "", "Invalid columns vector. At least one column should be specified");
        }
        try
        {
            HashtableFormat hashtableformat = (HashtableFormat)FormatElement.readObject(s1);
            if(hashtableformat == null)
            {
                if(Trace.doTrace("#EJ", 256, 16))
                {
                    String s6 = "The formatter reference is null.";
                    Trace.trace("#EJ", 256, 16, null, s6);
                }
                throw new DSEInternalErrorException("DSEException.critical", "", "The formatter reference is null.");
            }
            Hashtable hashtable = hashtableformat.format(context);
            if(hashtable == null)
            {
                if(Trace.doTrace("#EJ", 256, 16))
                {
                    String s8 = "The data hashtable reference is null.";
                    Trace.trace("#EJ", 256, 16, null, s8);
                }
                throw new DSEInternalErrorException("DSEException.critical", "", "The data hashtable reference is null.");
            } else
            {
                return updateRecords(s, hashtable, vector);
            }
        }
        catch(Exception exception)
        {
            if(Trace.doTrace("#EJ", 256, 16))
            {
                String s7 = "Exception: " + exception.getMessage();
                Trace.trace("#EJ", 256, 16, null, s7);
            }
            throw new DSEInternalErrorException("DSEException.critical", "", exception.getMessage());
        }
    }

    public int updateRecords(String s, Hashtable hashtable)
        throws DSEInvalidRequestException, DSEInternalErrorException, DSEInvalidArgumentException, DSESQLException
    {
        if(Trace.doTrace("#EJ", 1024, 2))
        {
            String s1 = "updateRecords(" + s + ", Hashtable)";
            Trace.trace("#EJ", 1024, 2, null, s1);
        }
        if(s == null || s.equals(""))
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s2 = "aSearchCondition value is not valid: " + s;
                Trace.trace("#EJ", 1024, 8, null, s2);
            }
            throw new DSEInvalidArgumentException("DSEException.critical", "", "aSearchCondition is either null or its size is 0.");
        }
        if(hashtable == null)
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s3 = "The data hashtable reference is null.";
                Trace.trace("#EJ", 1024, 8, null, s3);
            }
            throw new DSEInvalidArgumentException("DSEException.critical", "", "The data hashtable reference is null.");
        }
        if(columnsTable == null)
        {
            if(Trace.doTrace("#EJ", 256, 16))
            {
                String s4 = "The columns hashtable reference is null.";
                Trace.trace("#EJ", 256, 16, null, s4);
            }
            throw new DSEInternalErrorException("DSEException.critical", "", "The columns hashtable reference is null.");
        }
        if(!isActive())
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s5 = "The Journal is not in ACTIVE state. Can not update the record into the Journal table.";
                Trace.trace("#EJ", 1024, 8, null, s5);
            }
            throw new DSEInvalidRequestException("DSEException.critical", "", "The Journal is not in ACTIVE state. Can not update the record into the Journal table.");
        }
        int j;
        synchronized(poolSem)
        {
            Vector vector = recordToRow(hashtable);
            int i;
            try
            {
                PreparedStatement preparedstatement = super.databaseConnection.prepareStatement(getUpdateString() + s);
                DatabaseResultSetMetaData databaseresultsetmetadata = JDBCJournalSchemaGenerator.getMetaData(schemaName, isCreateSchema());
                if(databaseresultsetmetadata == null)
                {
                    if(Trace.doTrace("#EJ", 256, 16))
                    {
                        String s6 = "getMetaData() returned null.";
                        Trace.trace("#EJ", 256, 16, null, s6);
                    }
                    throw new DSEInternalErrorException("DSEException.critical", "", "getMetaData() returned null.");
                }
                Enumeration enumeration = vector.elements();
                for(int k = 1; enumeration.hasMoreElements(); k++)
                {
                    Object obj1 = enumeration.nextElement();
                    if(obj1 == null)
                        preparedstatement.setNull(k, databaseresultsetmetadata.getColumnType(k + 1));
                    else
                    if(obj1 instanceof BigDecimal)
                        preparedstatement.setBigDecimal(k, (BigDecimal)obj1);
                    else
                        preparedstatement.setObject(k, obj1);
                }

                i = preparedstatement.executeUpdate();
                preparedstatement.close();
                if(super.autoCommit && Trace.doTrace("#EJ", 1024, 2))
                {
                    String s8 = "Commiting all Database changes...";
                    Trace.trace("#EJ", 1024, 2, null, s8);
                }
            }
            catch(SQLException sqlexception)
            {
                if(Trace.doTrace("#EJ", 1024, 8))
                {
                    String s7 = "SQLException while updating records in Journal Table " + getTableName() + ": " + sqlexception.getMessage();
                    Trace.trace("#EJ", 1024, 8, null, s7);
                }
                throw new DSESQLException("DSEException.critical", "", sqlexception.getErrorCode(), sqlexception.getSQLState(), "SQLException while updating records in Journal Table " + getTableName() + ": " + sqlexception.getMessage());
            }
            j = i;
        }
        return j;
    }

    public int updateRecords(String s, Hashtable hashtable, Vector vector)
        throws DSEInvalidArgumentException, DSEInternalErrorException, DSEInvalidRequestException, DSESQLException
    {
        if(Trace.doTrace("#EJ", 1024, 2))
        {
            String s1 = "updateRecord(" + s + ", Hashtable, aColumnVector)";
            Trace.trace("#EJ", 1024, 2, null, s1);
        }
        if(s == null || s.equals(""))
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s2 = "aSearchCondition value is not valid: " + s;
                Trace.trace("#EJ", 1024, 8, null, s2);
            }
            throw new DSEInvalidArgumentException("DSEException.critical", "", "aSearchCondition is either null or its size is 0.");
        }
        if(hashtable == null)
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s3 = "The data hashtable reference is null.";
                Trace.trace("#EJ", 1024, 8, null, s3);
            }
            throw new DSEInvalidArgumentException("DSEException.critical", "", "The data hashtable reference is null.");
        }
        if(vector == null || vector.isEmpty())
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s4 = "Invalid columns vector. At least one column should be specified";
                Trace.trace("#EJ", 1024, 8, null, s4);
            }
            throw new DSEInvalidArgumentException("DSEException.critical", "", "Invalid columns vector. At least one column should be specified");
        }
        if(columnsTable == null)
        {
            if(Trace.doTrace("#EJ", 256, 16))
            {
                String s5 = "The columns hashtable reference is null.";
                Trace.trace("#EJ", 256, 16, null, s5);
            }
            throw new DSEInternalErrorException("DSEException.critical", "", "The columns hashtable reference is null.");
        }
        if(!isActive())
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s6 = "The Journal is not in ACTIVE state. Can not update the record into the Journal table.";
                Trace.trace("#EJ", 1024, 8, null, s6);
            }
            throw new DSEInvalidRequestException("DSEException.critical", "", "The Journal is not in ACTIVE state. Can not update the record into the Journal table.");
        }
        int l;
        synchronized(poolSem)
        {
            try
            {
                if(JDBCServicesAdministrator.databaseConnection == null || JDBCServicesAdministrator.databaseConnection.isClosed())
                    JDBCServicesAdministrator.databaseConnection = getDatabaseConnection();
            }
            catch(SQLException sqlexception)
            {
                if(Trace.doTrace("#EJ", 1024, 8))
                {
                    String s7 = "SQLException while updating records in Journal Table: " + sqlexception.getMessage();
                    Trace.trace("#EJ", 1024, 8, null, s7);
                }
                throw new DSESQLException("DSEException.critical", "", sqlexception.getErrorCode(), sqlexception.getSQLState(), "SQLException while updating records in Journal Table: " + sqlexception.getMessage());
            }
            DatabaseResultSetMetaData databaseresultsetmetadata = JDBCJournalSchemaGenerator.getMetaData(schemaName, isCreateSchema());
            Vector vector1 = new Vector();
            if(databaseresultsetmetadata == null)
            {
                if(Trace.doTrace("#EJ", 256, 16))
                {
                    String s8 = "getMetaData() returned null.";
                    Trace.trace("#EJ", 256, 16, null, s8);
                }
                throw new DSEInternalErrorException("DSEException.critical", "", "getMetaData() returned null.");
            }
            Object obj1 = null;
            int j = databaseresultsetmetadata.getColumnCount();
            Enumeration enumeration = null;
            Object obj2 = null;
            if(j < 1)
            {
                if(Trace.doTrace("#EJ", 256, 16))
                {
                    String s12 = "Invalid number of columns: " + j;
                    Trace.trace("#EJ", 256, 16, null, s12);
                }
                throw new DSEInternalErrorException("DSEException.critical", "", "Invalid number of columns: " + j);
            }
            obj1 = databaseresultsetmetadata.getColumnName(1);
            if(!((String) (obj1)).equals(JournalConstants.JT_RECNBR_COLUMN_NAME))
            {
                if(Trace.doTrace("#EJ", 256, 16))
                {
                    String s13 = "The first column name should be " + JournalConstants.JT_RECNBR_COLUMN_NAME + ". Actual name is " + obj1;
                    Trace.trace("#EJ", 256, 16, null, s13);
                }
                throw new DSEInternalErrorException("DSEException.critical", "", "The first column name should be " + JournalConstants.JT_RECNBR_COLUMN_NAME + ". Actual name is " + obj1);
            }
            for(int k = 2; k <= j; k++)
            {
                enumeration = vector.elements();
                obj1 = databaseresultsetmetadata.getColumnName(k);
                while(enumeration.hasMoreElements()) 
                {
                    String s11 = (String)enumeration.nextElement();
                    if(s11.equals(obj1))
                        vector1.addElement(s11);
                }
            }

            obj1 = recordToRow(hashtable, vector1);
            String s9 = " SET ";
            enumeration = null;
            for(Enumeration enumeration1 = vector1.elements(); enumeration1.hasMoreElements();)
            {
                String s10 = (String)enumeration1.nextElement();
                if(!s10.equals(JournalConstants.JT_RECNBR_COLUMN_NAME));
                if(enumeration1.hasMoreElements())
                    s9 = s9 + s10 + " = ?, ";
                else
                    s9 = s9 + s10 + " = ?";
            }

            Object obj3 = null;
            int i;
            try
            {
                String s14 = "UPDATE " + getTableName() + s9 + " WHERE " + s;
                PreparedStatement preparedstatement = super.databaseConnection.prepareStatement(s14);
                Enumeration enumeration2 = ((java.util.Vector) (obj1)).elements();
                int i1 = 1;
                Object obj4 = null;
                while(enumeration2.hasMoreElements()) 
                {
                    Vector vector2 = (Vector)enumeration2.nextElement();
                    Object obj5 = vector2.elementAt(0);
                    if(obj5 == null)
                        preparedstatement.setNull(i1, ((Integer)vector2.elementAt(1)).intValue());
                    else
                    if(obj5 instanceof BigDecimal)
                        preparedstatement.setBigDecimal(i1, (BigDecimal)obj5);
                    else
                        preparedstatement.setObject(i1, obj5);
                    i1++;
                }
                i = preparedstatement.executeUpdate();
                preparedstatement.close();
                if(super.autoCommit && Trace.doTrace("#EJ", 1024, 2))
                {
                    String s16 = "Commiting all Database changes...";
                    Trace.trace("#EJ", 1024, 2, null, s16);
                }
            }
            catch(SQLException sqlexception1)
            {
                if(Trace.doTrace("#EJ", 1024, 8))
                {
                    String s15 = "SQLException while updating records in Journal Table " + getTableName() + ": " + sqlexception1.getMessage();
                    Trace.trace("#EJ", 1024, 8, null, s15);
                }
                throw new DSESQLException("DSEException.critical", "", sqlexception1.getErrorCode(), sqlexception1.getSQLState(), "SQLException while updating records in Journal Table " + getTableName() + ": " + sqlexception1.getMessage());
            }
            l = i;
        }
        return l;
    }

    public static int getSingleTableLastRecordNumber()
    {
        return singleTableLastRecordNumber;
    }

    public boolean isSingleTable()
    {
        return singleTable;
    }

    public static void setSingleTableLastRecordNumber(int i)
    {
        singleTableLastRecordNumber = i;
    }

    public void setSingleTable(boolean flag)
    {
        singleTable = flag;
    }

}
