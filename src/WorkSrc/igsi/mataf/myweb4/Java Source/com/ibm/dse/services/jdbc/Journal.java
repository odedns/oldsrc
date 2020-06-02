// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 

package com.ibm.dse.services.jdbc;

import com.ibm.dse.base.*;
import com.ibm.dse.services.Poolable;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.GregorianCalendar;

// Referenced classes of package com.ibm.dse.services.jdbc:
//            JDBCService, DSESQLException, JournalService, JournalConstants

public abstract class Journal extends JDBCService
    implements JournalService, Poolable
{

    public Journal()
    {
        date = null;
        state = "Inactive";
    }

    public abstract int addRecord(Context context, HashtableFormat hashtableformat)
        throws DSEInvalidArgumentException, DSEInvalidRequestException, DSEInternalErrorException, DSESQLException;

    public abstract int addRecord(Context context, String s)
        throws DSEInvalidArgumentException, DSEInvalidRequestException, DSEInternalErrorException, DSESQLException;

    public abstract int addRecord(Hashtable hashtable)
        throws DSEInvalidRequestException, DSEInvalidArgumentException, DSEInternalErrorException, DSESQLException;

    public void close()
        throws DSESQLException
    {
        if(Trace.doTrace("#EJ", 1024, 2))
        {
            String s = "Closing the Journal service.....";
            Trace.trace("#EJ", 1024, 2, null, s);
        }
        if(getState().equals(JournalConstants.EJ_STATE_INACTIVE))
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s1 = "Journal service is already inactive";
                Trace.trace("#EJ", 1024, 4, null, s1);
            }
        } else
        {
            state = JournalConstants.EJ_STATE_INACTIVE;
            try
            {
                if(getAddPstmt() != null)
                    getAddPstmt().close();
            }
            catch(SQLException sqlexception)
            {
                if(Trace.doTrace("#EJ", 1024, 8))
                {
                    String s2 = "SQLException while closing statement: " + sqlexception.getMessage();
                    Trace.trace("#EJ", 1024, 8, null, s2);
                }
                throw new DSESQLException("DSEException.critical", "", sqlexception.getErrorCode(), sqlexception.getSQLState(), "SQLException while closing statement:" + sqlexception.getMessage());
            }
        }
    }

    public abstract void commit()
        throws DSESQLException;

    protected abstract String controlTableName();

    public abstract String currentGenerationDate()
        throws DSEInternalErrorException, DSESQLException;

    public String getDate()
    {
        return date;
    }

    public String getEntity()
    {
        return entity;
    }

    public boolean getInUse()
    {
        return inUse;
    }

    public abstract String getJDBCDriver();

    protected int getLastRecordNumber()
    {
        return lastRecordNumber;
    }

    public abstract String getSchemaName();

    protected String getState()
    {
        return state;
    }

    public abstract String getTableName();

    public void initialize()
        throws DSEException
    {
    }

    public boolean isActive()
    {
        return state.equals(JournalConstants.EJ_STATE_ACTIVE);
    }

    public abstract boolean isCreateSchema();

    protected abstract boolean isValidEntity()
        throws DSEInternalErrorException, DSESQLException;

    public void open()
        throws DSEInternalErrorException, DSEInvalidRequestException, DSEInvalidArgumentException, DSESQLException
    {
        if(Trace.doTrace("#EJ", 1024, 2))
        {
            String s = "Opening the Journal service .....";
            Trace.trace("#EJ", 1024, 2, null, s);
        }
        if(isValidEntity())
        {
            String s1 = currentGenerationDate();
            System.out.println("currentGenDatew = " + s1);
            if(s1 == null)
            {
                openOnNextTable();
                if(Trace.doTrace("#EJ", 1024, 2))
                {
                    String s3 = "Setting Journal to ACTIVE state...";
                    Trace.trace("#EJ", 1024, 2, null, s3);
                }
                state = JournalConstants.EJ_STATE_ACTIVE;
            } else
            {
                String s4 = today();
                System.out.println("today = " + s4);
                if(s4 == null)
                {
                    if(Trace.doTrace("#EJ", 256, 16))
                    {
                        String s5 = "todayDate reference is null.";
                        Trace.trace("#EJ", 256, 16, null, s5);
                    }
                    throw new DSEInternalErrorException("DSEException.critical", "", "todayDate reference is null.");
                }
                if(s4.compareTo(s1) == 0)
                {
                	System.out.println("opening currnet table ");
                    openOnCurrentTable();
                    if(Trace.doTrace("#EJ", 1024, 2))
                    {
                        String s6 = "Setting Journal to ACTIVE state...";
                        Trace.trace("#EJ", 1024, 2, null, s6);
                    }
                    state = JournalConstants.EJ_STATE_ACTIVE;
                } else
                if(s4.compareTo(s1) > 0)
                {
                	System.out.println("opening next table ");
                    openOnNextTable();
                    if(Trace.doTrace("#EJ", 1024, 2))
                    {
                        String s7 = "Setting Journal to ACTIVE state...";
                        Trace.trace("#EJ", 1024, 2, null, s7);
                    }
                    state = JournalConstants.EJ_STATE_ACTIVE;
                } else
                {
                    if(Trace.doTrace("#EJ", 256, 16))
                    {
                        String s8 = "todayDate is previous to currentGenerationDate:today (yyyymmdd): " + s4 + "currentGeneration(yyyymmdd): " + s1;
                        Trace.trace("#EJ", 256, 16, null, s8);
                    }
                    throw new DSEInternalErrorException("DSEInternalErrorException.critical", "", "todayDate is before currentGenerationDate:today (yyyymmdd): " + s4 + "  currentGeneration (yyyymmdd): " + s1);
                }
            }
        } else
        {
            if(Trace.doTrace("#EJ", 1024, 8))
            {
                String s2 = "Entity name " + entity + " was not found in Control Table " + controlTableName() + ".";
                Trace.trace("#EJ", 1024, 8, null, s2);
            }
            throw new DSEInvalidRequestException("DSEException.critical", "", "Entity name " + entity + " was not found in Control Table " + controlTableName() + ".");
        }
    }

    public abstract void openOnCurrentTable()
        throws DSEInvalidRequestException, DSEInternalErrorException, DSEInvalidArgumentException, DSESQLException;

    public abstract void openOnNextTable()
        throws DSEInternalErrorException, DSEInvalidRequestException, DSEInvalidArgumentException, DSESQLException;

    public abstract int queryLastRecordNumber()
        throws DSESQLException, DSEInternalErrorException;

    public void reset()
        throws DSEException
    {
        close();
    }

    public abstract Hashtable retrieveLastRecord()
        throws DSEInternalErrorException, DSEInvalidArgumentException, DSESQLException;

    public abstract void retrieveLastRecord(Context context, HashtableFormat hashtableformat)
        throws DSEInvalidRequestException, DSEInternalErrorException, DSEInvalidArgumentException, DSESQLException;

    public abstract void retrieveLastRecord(Context context, String s)
        throws DSEInvalidRequestException, DSEInternalErrorException, DSEInvalidArgumentException, DSESQLException;

    public abstract Vector retrieveLastRecords(int i)
        throws DSEInternalErrorException, DSEInvalidRequestException, DSEInvalidArgumentException, DSESQLException;

    public abstract Hashtable retrieveRecord(int i)
        throws DSEInternalErrorException, DSEInvalidArgumentException, DSESQLException;

    public abstract void retrieveRecord(int i, Context context, HashtableFormat hashtableformat)
        throws DSEInvalidRequestException, DSEInternalErrorException, DSEInvalidArgumentException, DSESQLException;

    public abstract void retrieveRecord(int i, Context context, String s)
        throws DSEInvalidRequestException, DSEInternalErrorException, DSEInvalidArgumentException, DSESQLException;

    public abstract Vector retrieveRecords(String s)
        throws DSEInternalErrorException, DSEInvalidRequestException, DSEInvalidArgumentException, DSESQLException;

    public abstract void rollback()
        throws DSESQLException;

    public abstract void setCreateSchema(boolean flag);

    private void setDate(String s)
    {
        date = s;
    }

    public abstract void setEntity(String s);

    public void setInUse(boolean flag)
    {
        inUse = flag;
    }

    public abstract void setJDBCDriver(String s);

    protected void setLastRecordNumber(int i)
    {
        lastRecordNumber = i;
    }

    public abstract void setSchemaName(String s);

    protected void setState(String s)
    {
        state = s;
    }

    protected abstract void setTableName(String s);

    public void terminate()
        throws DSEException
    {
        super.terminate();
        if(databaseConnection != null)
        {
            commit();
            disconnect();
        }
        close();
    }

    protected String today()
    {
        StringBuffer stringbuffer = new StringBuffer();
        GregorianCalendar gregoriancalendar = new GregorianCalendar();
        String s = String.valueOf(gregoriancalendar.get(2) + 1);
        if(s.length() < 2)
            s = "0" + s;
        String s1 = String.valueOf(gregoriancalendar.get(5));
        if(s1.length() < 2)
            s1 = "0" + s1;
        String s2 = String.valueOf(gregoriancalendar.get(1));
        stringbuffer.append(s2);
        stringbuffer.append(s);
        stringbuffer.append(s1);
        String s3 = stringbuffer.toString();
        return s3;
    }

    public abstract int updateLastRecord(Context context, HashtableFormat hashtableformat)
        throws DSEInvalidRequestException, DSEInternalErrorException, DSEInvalidArgumentException, DSESQLException;

    public abstract int updateLastRecord(Context context, String s)
        throws DSEInvalidRequestException, DSEInvalidArgumentException, DSEInternalErrorException, DSESQLException;

    public abstract int updateLastRecord(Hashtable hashtable)
        throws DSEInvalidRequestException, DSEInvalidArgumentException, DSEInternalErrorException, DSESQLException;

    public abstract int updateRecord(int i, Context context, HashtableFormat hashtableformat)
        throws DSEInvalidRequestException, DSEInternalErrorException, DSEInvalidArgumentException, DSESQLException;

    public abstract int updateRecord(int i, Context context, String s)
        throws DSEInvalidRequestException, DSEInvalidArgumentException, DSEInternalErrorException, DSESQLException;

    public abstract int updateRecord(int i, Hashtable hashtable)
        throws DSEInvalidRequestException, DSEInvalidArgumentException, DSEInternalErrorException, DSESQLException;

    public abstract int updateRecords(String s, Context context, HashtableFormat hashtableformat)
        throws DSEInvalidRequestException, DSEInternalErrorException, DSEInvalidArgumentException, DSESQLException;

    public abstract int updateRecords(String s, Context context, String s1)
        throws DSEInvalidRequestException, DSEInvalidArgumentException, DSEInternalErrorException, DSESQLException;

    public abstract int updateRecords(String s, Hashtable hashtable)
        throws DSEInvalidRequestException, DSEInternalErrorException, DSEInvalidArgumentException, DSESQLException;

    public abstract int updateRecords(String s, Hashtable hashtable, Vector vector)
        throws DSEInvalidArgumentException, DSEInternalErrorException, DSEInvalidRequestException, DSESQLException;

    public abstract int updateRecords(String s, Context context, String s1, Vector vector)
        throws DSEInvalidRequestException, DSEInternalErrorException, DSEInvalidArgumentException, DSESQLException;

    public abstract int updateRecords(String s, Context context, HashtableFormat hashtableformat, Vector vector)
        throws DSEInvalidRequestException, DSEInternalErrorException, DSEInvalidArgumentException, DSESQLException;

    public abstract int updateRecord(int i, Hashtable hashtable, Vector vector)
        throws DSEInvalidArgumentException, DSEInternalErrorException, DSEInvalidRequestException, DSESQLException;

    public abstract int updateRecord(int i, Context context, String s, Vector vector)
        throws DSEInvalidRequestException, DSEInternalErrorException, DSEInvalidArgumentException, DSESQLException;

    public abstract int updateRecord(int i, Context context, HashtableFormat hashtableformat, Vector vector)
        throws DSEInvalidRequestException, DSEInternalErrorException, DSEInvalidArgumentException, DSESQLException;

    public abstract int updateLastRecord(Hashtable hashtable, Vector vector)
        throws DSEInvalidRequestException, DSEInternalErrorException, DSEInvalidArgumentException, DSESQLException;

    public abstract int updateLastRecord(Context context, String s, Vector vector)
        throws DSEInvalidRequestException, DSEInternalErrorException, DSEInvalidArgumentException, DSESQLException;

    public abstract int updateLastRecord(Context context, HashtableFormat hashtableformat, Vector vector)
        throws DSEInvalidRequestException, DSEInternalErrorException, DSEInvalidArgumentException, DSESQLException;

    public abstract void retrieveRecords(String s, Vector vector, Context context, String s1)
        throws DSEInternalErrorException, DSEInvalidRequestException, DSEInvalidArgumentException, DSESQLException, DSEObjectNotFoundException;

    public abstract void retrieveRecords(String s, Vector vector, Context context, HashtableIndexedCollectionFormat hashtableindexedcollectionformat)
        throws DSEInternalErrorException, DSEInvalidRequestException, DSEInvalidArgumentException, DSESQLException, DSEObjectNotFoundException;

    public abstract Vector retrieveRecords(String s, Vector vector)
        throws DSEInternalErrorException, DSEInvalidRequestException, DSEInvalidArgumentException, DSESQLException;

    public abstract void retrieveRecords(String s, Context context, String s1)
        throws DSEInternalErrorException, DSEInvalidRequestException, DSEInvalidArgumentException, DSESQLException, DSEObjectNotFoundException;

    public abstract void retrieveRecords(String s, Context context, HashtableIndexedCollectionFormat hashtableindexedcollectionformat)
        throws DSEInternalErrorException, DSEInvalidRequestException, DSEInvalidArgumentException, DSESQLException, DSEObjectNotFoundException;

    public abstract void retrieveLastRecords(int i, Context context, String s)
        throws DSEInternalErrorException, DSEInvalidArgumentException, DSEInvalidRequestException, DSESQLException, DSEObjectNotFoundException;

    public abstract void retrieveLastRecords(int i, Context context, HashtableIndexedCollectionFormat hashtableindexedcollectionformat)
        throws DSEInternalErrorException, DSEInvalidArgumentException, DSEInvalidRequestException, DSESQLException, DSEObjectNotFoundException;

    public abstract void openForSchema(String s)
        throws DSEInternalErrorException, DSEInvalidRequestException, DSEInvalidArgumentException, DSESQLException;

    public abstract void openForEntity(String s, String s1)
        throws DSEInternalErrorException, DSEInvalidRequestException, DSEInvalidArgumentException, DSESQLException;

    public abstract void openForEntity(String s)
        throws DSEInternalErrorException, DSEInvalidRequestException, DSEInvalidArgumentException, DSESQLException;

    public abstract void open(String s, String s1, int i)
        throws DSEInvalidRequestException, DSEInternalErrorException, DSESQLException;

    public abstract void open(String s, int i)
        throws DSEInvalidRequestException, DSEInternalErrorException, DSESQLException;

    public abstract void loadDriver(String s)
        throws DSEInternalErrorException;

    public abstract void loadDriver()
        throws DSEInternalErrorException;

    public abstract int getNumberOfGenerations()
        throws DSESQLException, DSEInternalErrorException;

    public abstract Vector getEntities()
        throws DSESQLException, DSEInternalErrorException;

    public abstract Hashtable getColumnsTable();

    private static final String COPYRIGHT = "Licensed Materials - Property of IBM Restricted Materials of IBM 5648-D89 (C) Copyright IBM Corp. 1998, 2002 All Rights Reserved. US Government Users Restricted Rights - Use, duplication or disclosure restricted by GSA ADP Schedule Contract with IBM Corp ";
    public static final String COMPID = "#EJ";
    public String date;
    public String entity;
    private String state;
    protected int lastRecordNumber;
    private boolean inUse;
}
