
*------------------------------------------------------------------------------*
* PROJECT                                                                      *
*------------------------------------------------------------------------------*
*                                                                              *
* File : [akurs7.proj]prj                                                      *
*                                                                              *
* Purpose: This program updates a data base according to received              *
*          transactions from a transaction file, and creates a report file     *
*          which includes each transaction and the activity which it created   *
*          (including errors). In addition a log file is created (for DB fatal *
*          errors).                                                            *
*                                                                              *
* Files :                                                                      *
*                                                                              *
*          input file:                                                         *
*          TR-TRANSACTION-FILE          : customer's transactions.             *
*                                                                              *
*          Output file:                                                        *
*          OR-REPORT-FILE               : transactions' report.                *
*------------------------------------------------------------------------------*

*------------------------------------------------------------------------------*
* Called procedures:                                                           *
*            OP-LOG-MSG                    : write error messages to log file  *
*            DBFIL_OPEN                    : open the data base                *
*            GN-INIDEF-C                   : initialize DB customer record     *
*            GN-INIDEF-P                   : initialize DB product record      *
*            DBKER_BRING                   : bring a kernel                    *
*            DBKER_INIT                    : initialize a kernel               *
*            DBSEG_BRING                   : bring the segment                 *
*            DBKER_INSER                   : insert a new kernel               *
*            DBKER_MODIFY                  : modify the kernel                 *
*            DBKER_DELETE                  : delete the kernel                 *
*            DBFIL_CLOSE                   : close the data base               *
*            SYS$EXIT                      : stop program by a VMS system call *
*------------------------------------------------------------------------------*
*                                                                              *
*------------------------------------------------------------------------------*
* Program history: None                                                        *
*                                                                              *
*    Date:              18-APRIL-1993                                          *
*    Programmers:       Nissan Oded                                            *
*                       Mandl Amir                                             *
*    Supervisors:       Itzik Alkalay                                          *
*                       Udi   Grossman                                         *
*------------------------------------------------------------------------------*

*******************************************************************************
 IDENTIFICATION                                DIVISION.
*******************************************************************************
 PROGRAM-ID.                                   PROJECT.
*******************************************************************************
 ENVIRONMENT                                   DIVISION.
*******************************************************************************
*------------------------------------------------------------------------------
 INPUT-OUTPUT                                  SECTION.
*------------------------------------------------------------------------------

 FILE-CONTROL.
*-------------

      SELECT    OR-REPORT-FILE
                ASSIGN         TO     "REPORT-FILE.REP"
                RESERVE        2      AREAS
                ORGANIZATION   IS     SEQUENTIAL
                FILE STATUS    IS     WS01-OR-FILE-STATUS.

      SELECT   IT-TRANSACTION-FILE
                ASSIGN         TO     "TR-TRANSACTION-FILE"
                RESERVE        2      AREAS
                ORGANIZATION   IS     SEQUENTIAL    
                FILE STATUS    IS     WS02-IT-FILE-STATUS.

*******************************************************************************
 DATA                                           DIVISION.
*******************************************************************************
*------------------------------------------------------------------------------
 FILE                                           SECTION.
*------------------------------------------------------------------------------

FD  OR-REPORT-FILE
    LABEL RECORDS ARE STANDARD
    LINAGE  IS 63
    FOOTING AT 58
    TOP        1
    BOTTOM     2.         

01  OR-REC.  
    COPY  'KURS$USER:[KURS0.COMSRC]REPORT_REC.LIB'.

01  OR-NON-DETAIL-REC.
    03   OR-HL-REC                          PIC X(105).
                                   
FD  IT-TRANSACTION-FILE
    LABEL RECORDS ARE STANDARD.

01  TR-REC.
    COPY  'KURS$USER:[KURS0.SCREENS]TR_REC.LIB'.

*-------------------------------------------------------------------------------
 WORKING-STORAGE                                 SECTION.
*-------------------------------------------------------------------------------

*-------------------------------------------------------------------------------
*    CNXX.   COUNTERS
*-------------------------------------------------------------------------------
 
01    CNXX.
      03   CN01-PROCESSED-REC               PIC 9(8) COMP       VALUE 0.
      03   CN02-REJECTED-REC                PIC 9(8) COMP       VALUE 0.
      03   CN03-TOTAL-RECORDS-READ          PIC 9(8) COMP       VALUE 0.
      03   CN04-PAGE-NUM                    PIC 9(8) COMP       VALUE 0.

*-------------------------------------------------------------------------------
*    DBXX.   DATA-BASE-RECORDS.
*-------------------------------------------------------------------------------

01  DB01-DB-ARGUMENTS.
    COPY 'GOP$COMSRCGN:DBARG.LIB'.

01  DB02-CUSTOMER.
    COPY 'GOP$COMSRCGN:DBCST.LIB'.

01  DB03-PRODUCT.
    COPY 'GOP$COMSRCGN:DBPRD.LIB'.

01  DB04-PRODUCT-KEY.
    COPY 'GOP$COMSRCGN:DBPRDKEY.LIB'.

*-------------------------------------------------------------------------------
*   HXX.     REPORT'S NON-DETAILS LINES.
*-------------------------------------------------------------------------------

01  L10-HEADLINE-1.
    03                                      PIC X(5)            VALUE ALL " ".
    03                                      PIC X(19)           VALUE
         "TRANSACTIONS-REPORT".
    03                                      PIC X(10)           VALUE ALL " ".
    03                                      PIC X(6)            VALUE "DATE: ".
    03   L10-DD                             PIC Z9.
    03                                      PIC X               VALUE "/".
    03   L10-MM                             PIC 99.
    03                                      PIC X               VALUE "/".
    03   L10-YY                             PIC 99.
    03                                      PIC X(40)           VALUE ALL " ".
    03                                      PIC X(6)            VALUE "PAGE: ".
    03   L10-PAGE-NUM                       PIC ZZ9.
    03                                      PIC X(8)            VALUE ALL " ".

01  L20-HEADLINE-2.
    03                                      PIC X(3)            VALUE "TR#".
    03                                      PIC X               VALUE " ".
    03                                      PIC X               VALUE "T".
    03                                      PIC X               VALUE " ".
    03                                      PIC X(9)            VALUE "CUST-ID  ".
    03                                      PIC X               VALUE " ".
    03                                      PIC X(9)            VALUE "CUST-NAME".  
    03                                      PIC X(22)           VALUE ALL " ".
    03                                      PIC X(2)            VALUE "C ".
    03                                      PIC X(2)            VALUE "L ".
    03                                      PIC X(5)            VALUE "COD  ".
    03                                      PIC X(2)            VALUE "S ".
    03                                      PIC X(2)            VALUE "R ".
    03                                      PIC X(8)            VALUE "TRN STAT".
    03                                      PIC X(37)           VALUE ALL " ".

01  L30-LINE.
    03                                      PIC X(105)          VALUE ALL "=".

01  L40-END-LINE.
    03                                      PIC X(25)           VALUE ALL " ".
    03                                      PIC X(35)           VALUE
         "********** END OF REPORT **********".
    03                                      PIC X(45)           VALUE ALL " ".

01  L50-DUMMY.
    03                                      PIC X(105)          VALUE ALL " ".  
 

*-------------------------------------------------------------------------------
*    MSGXX.   MESSAGES.
*-------------------------------------------------------------------------------

01   MSG01-MSG-PRM.
     COPY "GOP$COMSRCOP:OPMSGPRM.LIB".

01   MSG02-ERROR-MESSAGE. 

     03   MSG02-MESSAGE-TEXT                PIC X(40).

          88    MSG02-RUNNING-PROGRAM                           VALUE
                "RUNNING PROGRAM !!".
          88    MSG02-REPORT-OPENING-ERROR                      VALUE
                "ERROR OPENING REPORT FILE !!".
          88    MSG02-REPORT-CLOSING-ERROR                      VALUE
                "ERROR CLOSING REPORT FILE ! ".
          88    MSG02-TRN-FILE-OPEN-ERROR                       VALUE
                "ERROR OPENING TRANSACTION FILE !!".
          88    MSG02-EMPTY-TRN-FILE                            VALUE
                "EMPTY TRANSACTION FILE !".
          88    MSG02-TRN-FILE-CLOSE-ERROR                      VALUE
                "ERROR CLOSING TRANSACTION FILE ! ".
          88    MSG02-DEFAULT-DB-NAME-ERROR                     VALUE
                "ERROR FINDING DB DEFAULT NAME !!".
          88    MSG02-OPEN-DB-ERROR                             VALUE
                "ERROR OPENING DATA-BASE !!".
          88    MSG02-CLOSE-DB-ERROR                            VALUE
                "ERROR CLOSING DATA BASE !".
          88    MSG02-WRITING-REPORT-ERROR                      VALUE
                "ERROR WRITING INTO REPORT FILE".
          88    MSG02-READING-TRANSACTION-ERROR                 VALUE
                "ERROR READING TRANSACTION FILE !! ".
          88    MSG02-DBKER-INIT-ERROR                          VALUE
                "ERROR IN DBKER_INIT !".
          88    MSG02-DBKER-DELETE-ERROR                        VALUE
                "ERROR CALLING DBKER_DELETE ! ".
          88    MSG02-DBKER-MODIFY-ERROR                        VALUE
                "ERROR CALLING DBKER_MODIFY ! ".
          88    MSG02-DBKER-INSERT-ERROR                        VALUE
                "ERROR CALLING DBKER_INSERT ! ".
          88    MSG02-DBKER-BRING-ERROR                         VALUE
                "ERROR  CALLING DBKER_BRING ! ".
          88    MSG02-DBSEG-DELETE-ERROR                        VALUE
                "ERROR CALLING DBSEG_DELETE ! ".
          88    MSG02-DBSEG-MODIFY-ERROR                        VALUE
                "ERROR CALLING DBSEG_MODIFY ! ".
          88    MSG02-DBSEG-INSERT-ERROR                        VALUE
                "ERROR CALLING DBSEG_INSERT ! ".
          88    MSG02-DBSEG-BRING-ERROR                         VALUE
                "ERROR  CALLING DBSEG_BRING ! ".
          88    MSG02-SUCCESSFUL-TERMINATION                    VALUE
                "PROGRAM TERMINATED SUCCESSFULY !!! ".       

     03   MSG02-FILLER                      PIC XX              VALUE ALL SPACE.
     03   MSG02-FILLER                      PIC X(8)            VALUE "TRN # : ".
     03   MSG02-TRN-NUM                     PIC 9(3).
     03   MSG02-FILLER                      PIC XX              VALUE ALL SPACE.
     03   MSG02-FILLER                      PIC X(6)            VALUE "STAT: ".
     03   MSG02-DB-STAT                     PIC -ZZZ9.         

*-------------------------------------------------------------------------------
*   SWXX.   SWITCHES.
*-------------------------------------------------------------------------------

01  SW01-BREAK                              PIC S9(5) COMP      VALUE 0.
    88    SW01-NOBREAK                                          VALUE 0.
    88    SW01-END-OF-TRANSACTION-FILE                          VALUE 1.
    88    SW01-END-OF-CUSTOMER                                  VALUE 2.

01  SW02-IT-TRANSACTION-STATUS              PIC S9(4) COMP      VALUE 0.
    88    SW02-IT-TRANSACTION-OPEN                              VALUE 1.
    88    SW02-IT-TRANSACTION-CLOSED                            VALUE 0.

01  SW03-OR-REPORT-STATUS                   PIC S9(4) COMP      VALUE 0.
    88    SW03-OR-REPORT-OPEN                                   VALUE 1.
    88    SW03-OR-REPORT-CLOSED                                 VALUE 0.

01  SW04-DB-STATUS                          PIC 9               VALUE 0.
    88    SW04-DB-CLOSE                                         VALUE 0.
    88    SW04-DB-OPEN                                          VALUE 1.

01  SW05-PROGRAM-STATUS                     PIC S9(6) COMP      VALUE 1.
    88    SW05-PROGRAM-END-SUCCESSFULY                          VALUE 1.
    88    SW05-PROGRAM-END-ERROR                                VALUE 2.

01   SW06-CUSTOMER-EXISTANCE                PIC 9               VALUE 0.
     88   SW06-CUSTOMER-EXIST                                   VALUE 1.
     88   SW06-CUSTOMER-NOT-EXIST                               VALUE 0.

01   SW07-CUSTOMER-ACTION                   PIC X. 
     88   SW07-CUSTOMER-NO-ACTION                               VALUE "N".    
     88   SW07-CUSTOMER-INSERT                                  VALUE "I".
     88   SW07-CUSTOMER-MODIFY                                  VALUE "M".   

01   SW08-PRODUCT-EXISTANCE                 PIC 9               VALUE 0.
     88   SW08-PRODUCT-EXIST                                    VALUE 1.
     88   SW08-PRODUCT-NOT-EXIST                                VALUE 0.

01   SW09-ERROR-STATUS                      PIC 99              VALUE 0.
     88   SW09-NO-ERROR                                         VALUE 0.
     88   SW09-OPEN-REPORT-ERROR                                VALUE 1.
     88   SW09-OPEN-TRN-FILE-ERROR                              VALUE 2.
     88   SW09-GN-NAME-ERROR                                    VALUE 3.
     88   SW09-OPEN-DB-ERROR                                    VALUE 4.
     88   SW09-WRITING-REPORT-ERROR                             VALUE 5.
     88   SW09-READING-TRANSACTION-ERROR                        VALUE 6.  
     88   SW09-DBKER-DELETE-ERROR                               VALUE 7.
     88   SW09-DBKER-MODIFY-ERROR                               VALUE 8.
     88   SW09-DBKER-INSERT-ERROR                               VALUE 9.
     88   SW09-DBKER-BRING-ERROR                                VALUE 10.  
     88   SW09-DBSEG-DELETE-ERROR                               VALUE 11.
     88   SW09-DBSEG-MODIFY-ERROR                               VALUE 12.
     88   SW09-DBSEG-INSERT-ERROR                               VALUE 13.
     88   SW09-DBSEG-BRING-ERROR                                VALUE 14.
     88   SW09-DBKER-INIT-ERROR                                 VALUE 15.

*-------------------------------------------------------------------------------
*  WSXX.   WORKING STORAGE VARIABLES
*-------------------------------------------------------------------------------

01   WS01-OR-FILE-STATUS                    PIC XX.          
     88   WS01-OR-FS-NORMAL                                     VALUE "00".

01   WS02-IT-FILE-STATUS                    PIC XX.
     88   WS02-IT-FS-NORMAL                                     VALUE "00".
     88   WS02-IT-FS-EOF                                        VALUE "10".

01   WS03-PREV-CUST-NUM                     PIC S9(9) COMP-3.

01   WS04-TRANSACTION-STATUS                PIC X(15).
     88  WS04-STATUS-NORMAL                                    VALUE "NORMAL".
     88  WS04-STATUS-NO-CUST                                   VALUE "CUST NOT FOUND".
     88  WS04-STATUS-NO-PRD                                    VALUE "PRD NOT FOUND".
     88  WS04-STATUS-CUST-EXIST                                VALUE "CUST ALREADY EX".
     88  WS04-STATUS-PRD-EXIST                                 VALUE "PRD ALREADY EX".
     88  WS04-STATUS-DB-ERROR                                  VALUE "DB ERROR".
     88  WS04-STATUS-GENERAL-ERR                               VALUE "OTHER ERROR".
     88  WS04-STATUS-EMPTY-FILE                                VALUE "EMPTY TRN FILE".

01   WS05-CURRENT-DATE.
     03   WS05-YY                           PIC 99.
     03   WS05-MM                           PIC 99.
     03   WS05-DD                           PIC 99.

********************************************************************************
 PROCEDURE                                       DIVISION.
********************************************************************************

*-------------------------------------------------------------------------------
 A-MAIN                                          SECTION.
*-------------------------------------------------------------------------------
A-00.
     PERFORM W-INIT-PROGRAM

     PERFORM UNTIL    SW01-END-OF-TRANSACTION-FILE

          PERFORM C-CUST-BEGIN

          PERFORM UNTIL     SW01-END-OF-CUSTOMER
                        OR  SW01-END-OF-TRANSACTION-FILE

               PERFORM D-HANDLE-RECORD   
               PERFORM E-READ-RECORD
               PERFORM F-BREAK

          END-PERFORM
          PERFORM G-CUSTOMER-END

     END-PERFORM
     PERFORM Z-FINISH-PROGRAM.

 A-EXIT.
    EXIT.


*------------------------------------------------------------------------------*
 C-CUST-BEGIN                                     SECTION.
*------------------------------------------------------------------------------*
* Purpose    : initialize customer's variables                                 *
* Description:                                                                 *
*          - initializing switches                                             *
*          - keeping in memory the cust-id                                     *
*          - bring a customer record from the DB 
*------------------------------------------------------------------------------*
C-00.
      SET   SW07-CUSTOMER-NO-ACTION         IN  SW07-CUSTOMER-ACTION     TO TRUE
      SET   SW08-PRODUCT-NOT-EXIST          IN  SW08-PRODUCT-EXISTANCE   TO TRUE
      SET   SW01-NOBREAK                    IN  SW01-BREAK               TO TRUE
      SET   DB-UPDATE                       IN DB-UPDATE-IND             TO TRUE
      MOVE  TR-CUSTOMER-NUMBER IN TR-REC    TO  WS03-PREV-CUST-NUM
      MOVE  TR-CUSTOMER-NUMBER IN TR-REC    TO  CUSTOMER-ID IN DB02-CUSTOMER

      CALL 'DBKER_BRING' USING DB-NO
                               DB-RCB
                               DB-UPDATE-IND
                               DB02-CUSTOMER
                               DB-STAT.

      IF NOT DB__NORMAL
          IF DB__NOREC
             SET  SW06-CUSTOMER-NOT-EXIST   IN SW06-CUSTOMER-EXISTANCE   TO TRUE
         ELSE
             SET SW09-DBKER-BRING-ERROR     IN SW09-ERROR-STATUS         TO  TRUE
             PERFORM X-HANDLE-ERROR
         END-IF
      ELSE
         SET SW06-CUSTOMER-EXIST            IN  SW06-CUSTOMER-EXISTANCE  TO TRUE
     END-IF.

 C-EXIT. 
    EXIT.

*------------------------------------------------------------------------------*
D-HANDLE-RECORD                                  SECTION.
*------------------------------------------------------------------------------*
* Purpose    : handling the differnt record types  (customer or product)       *
* Description:                                                                 *
*          - sending to relevant section according to record type              *
*------------------------------------------------------------------------------*
D-00.
     EVALUATE  TRUE

          WHEN TR-CUSTOMER-RECORD
               PERFORM DA-CUSTOMER-RECORD-HANDLING 

          WHEN TR-PRODUCT-RECORD
               PERFORM DB-PRODUCT-RECORD-HANDLING  

          WHEN OTHER
               SET WS04-STATUS-GENERAL-ERR IN WS04-TRANSACTION-STATUS TO TRUE 
               PERFORM  H-PREPARE-REPORT

     END-EVALUATE.

D-EXIT.
     EXIT.


*------------------------------------------------------------------------------*
*                 CUSTOMER   ACTION  DECISION  TABLE  !                        *
*  +-----------------+-----------------+--------------------------+-------+    *
*  |transaction-code | customer-status | customer-action-switch   | error |    *
*  |-----------------+-----------------+--------------------------+-------+    *
*  |    Insert       |   exist         | no-action                |   Y   |    *
*  +-----------------+-----------------+--------------------------+-------+    *
*  |    Modify       |   exist         | modify                   |   N   |    *
*  +-----------------+-----------------+--------------------------+-------+    *
*  |    Delete       |   exist         | delete                   |   N   |    *
*  +-----------------+-----------------+--------------------------+-------+    *
*  |    Insert       |   not-exist     | insert                   |   N   |    *
*  +-----------------+-----------------+--------------------------+-------+    *
*  |    Modify       |   not-exist     | no-action                |   Y   |    *
*  +-----------------+-----------------+--------------------------+-------+    *
*  |    Delete       |   not-exist     | no-action                |   Y   |    *
*  +-----------------+-----------------+--------------------------+-------+    *
*  |    Other        |   exist         | no-action                |   N   |    *
*  +-----------------+-----------------+--------------------------+-------+    *
*  |    Other        |   not-exist     | no-action                |   N   |    *
*  +-----------------+-----------------+--------------------------+-------+    *

*------------------------------------------------------------------------------*
DA-CUSTOMER-RECORD-HANDLING                      SECTION.
*------------------------------------------------------------------------------*
* Purpose    : dealing with a customer's record                                *
* Description:                                                                 *
*          - sending to a relevant section according to transaction's code     *
*------------------------------------------------------------------------------*
DA-00.
      EVALUATE TRUE

          WHEN TR-INSERT-RECORD
               PERFORM DAA-CUSTOMER-INSERT    

          WHEN TR-MODIFY-RECORD
               PERFORM DAB-CUSTOMER-MODIFY  

          WHEN TR-DELETE-RECORD
               PERFORM DAC-CUSTOMER-DELETE  

          WHEN OTHER
               SET WS04-STATUS-GENERAL-ERR IN WS04-TRANSACTION-STATUS TO TRUE                                                   
               PERFORM H-PREPARE-REPORT 

      END-EVALUATE.

DA-EXIT.
     EXIT.

*------------------------------------------------------------------------------*
DAA-CUSTOMER-INSERT                              SECTION.
*------------------------------------------------------------------------------*
* Purpose    : handling a customer's insertion record                          *
* Description:                                                                 *
*          - preparing switches and working-storage fields for inserting a     *
*            a new customer after breaking point.                              *
*          - if customer doesn't exist: initialization of area in DB.          *
*------------------------------------------------------------------------------*
DAA-00.
       EVALUATE  TRUE

           WHEN SW06-CUSTOMER-EXIST
                SET WS04-STATUS-CUST-EXIST  IN WS04-TRANSACTION-STATUS  TO TRUE
                PERFORM H-PREPARE-REPORT

           WHEN SW06-CUSTOMER-NOT-EXIST
                SET SW06-CUSTOMER-EXIST     IN SW06-CUSTOMER-EXISTANCE  TO TRUE
                SET WS04-STATUS-NORMAL      IN WS04-TRANSACTION-STATUS  TO TRUE
                SET SW07-CUSTOMER-INSERT    IN SW07-CUSTOMER-ACTION     TO TRUE 
                CALL 'GN-INIDEF-C'   USING DB02-CUSTOMER
                PERFORM H-PREPARE-REPORT
                PERFORM UC-UPDATE-CUST-FIELDS
                PERFORM I-CUSTOMER-INIT

       END-EVALUATE.

DAA-EXIT.
     EXIT.

*------------------------------------------------------------------------------*
DAB-CUSTOMER-MODIFY                              SECTION.
*------------------------------------------------------------------------------*
* Purpose    : handling a customer's modification record.                      *
* Description:                                                                 *
*          - preparing switches and updating customer's fields for modification*
*            after breaking point.                                             *
*------------------------------------------------------------------------------*
DAB-00.
       EVALUATE  TRUE

           WHEN SW06-CUSTOMER-EXIST      AND     NOT SW07-CUSTOMER-INSERT
                SET WS04-STATUS-NORMAL       IN  WS04-TRANSACTION-STATUS  TO TRUE
                SET SW07-CUSTOMER-MODIFY     IN  SW07-CUSTOMER-ACTION     TO TRUE
                PERFORM H-PREPARE-REPORT
                PERFORM UC-UPDATE-CUST-FIELDS

           WHEN SW06-CUSTOMER-NOT-EXIST
                SET SW07-CUSTOMER-NO-ACTION  IN  SW07-CUSTOMER-ACTION     TO TRUE
                SET WS04-STATUS-NO-CUST      IN  WS04-TRANSACTION-STATUS  TO TRUE 
                PERFORM H-PREPARE-REPORT

           WHEN SW06-CUSTOMER-EXIST AND SW07-CUSTOMER-INSERT
                SET WS04-STATUS-NORMAL  TO TRUE
                PERFORM H-PREPARE-REPORT
                PERFORM UC-UPDATE-CUST-FIELDS
                
       END-EVALUATE.

DAB-EXIT.
     EXIT.

*------------------------------------------------------------------------------*
DAC-CUSTOMER-DELETE                              SECTION.
*------------------------------------------------------------------------------*
* Purpose    : handling a customer's deleting record                           *
* Description:  deleting the record if customer exists in the DB               *
*               ( if the previous customer transaction was not Insert)         *
*------------------------------------------------------------------------------*
DAC-00.
       EVALUATE TRUE

           WHEN SW06-CUSTOMER-EXIST  AND NOT SW07-CUSTOMER-INSERT        
                SET SW06-CUSTOMER-NOT-EXIST    IN  SW06-CUSTOMER-EXISTANCE  TO TRUE
                SET WS04-STATUS-NORMAL         IN  WS04-TRANSACTION-STATUS  TO TRUE
                SET SW07-CUSTOMER-NO-ACTION    IN  SW07-CUSTOMER-ACTION     TO TRUE
                CALL 'DBKER_DELETE'         USING
                                            DB-NO
                                            DB-RCB
                                            TR-CUSTOMER-NUMBER
                                            DB-ANNEX-IND
                                            DB-STAT

                IF NOT DB__NORMAL IN DB-STAT
                   SET SW09-DBKER-DELETE-ERROR IN  SW09-ERROR-STATUS        TO TRUE
                   PERFORM  X-HANDLE-ERROR
                END-IF

                PERFORM H-PREPARE-REPORT
                
           WHEN SW06-CUSTOMER-NOT-EXIST
                SET WS04-STATUS-NO-CUST        IN  WS04-TRANSACTION-STATUS  TO TRUE
                PERFORM H-PREPARE-REPORT

           WHEN SW06-CUSTOMER-EXIST      AND SW07-CUSTOMER-INSERT
                SET SW06-CUSTOMER-NOT-EXIST    IN  SW06-CUSTOMER-EXISTANCE  TO TRUE  
                SET WS04-STATUS-NORMAL         IN  WS04-TRANSACTION-STATUS  TO TRUE
                SET SW07-CUSTOMER-NO-ACTION    IN  SW07-CUSTOMER-ACTION     TO TRUE
                PERFORM H-PREPARE-REPORT
                 
       END-EVALUATE.
                                  
DAC-EXIT.
     EXIT.

*------------------------------------------------------------------------------*
*               PRODUCT      DECISION        TABLE                             *
*+------------+-----------+-------------+------------+--------------+-------+  *
*|  customer  |  product  | transaction | product    | customer     |       |  *
*|   -status  |  - status |   -code     | action     | action switch| error |  *
*+------------+-----------+-------------+------------+--------------+-------+  *
*|  not-exist | not-exist |   Insert    |   Insert   |   Insert     |   N   |  *
*+------------+-----------+-------------+------------+--------------+-------+  *
*|  not-exist | not-exist |   Modify    |   ------   |   ------     |   Y   |  *
*+------------+-----------+-------------+------------+--------------+-------+  *
*|  not-exist | not-exist |   Delete    |   ------   |   ------     |   Y   |  *
*+------------+-----------+-------------+------------+--------------+-------+  *
*|  not-exist | not-exist |   Other     |   ------   |   ------     |   Y   |  *
*+------------+-----------+-------------+------------+--------------+-------+  *
*|    exist   |   exist   |   Insert    |   Insert   |   Modify     |   N   |  *
*+------------+-----------+-------------+------------+--------------+-------+  *
*|    exist   |   exist   |   Modify    |   Modify   |   Modify     |   N   |  *
*+------------+-----------+-------------+------------+--------------+-------+  *
*|    exist   |   exist   |   Delete    |   Delete   |   Modify     |   N   |  *
*+------------+-----------+-------------+------------+--------------+-------+  *
*|    exist   |   exist   |   Other     |   ------   |   ------     |   Y   |  *
*+--------------------------------------------------------------------------+  *
*|    exist   | not-exist |   Insert    |   Insert   |   Modify     |   N   |  *
*+------------+-----------+-------------+------------+--------------+-------+  *
*|    exist   | not-exist |   Modify    |   ------   |   ------     |   Y   |  *
*+------------+-----------+-------------+------------+--------------+-------+  *
*|    exist   | not-exist |   Delete    |   ------   |   ------     |   Y   |  *
*+------------+-----------+-------------+------------+--------------+-------+  *
*|    exist   | not-exist |   Other     |   ------   |   ------     |   Y   |  *
*+------------+-----------+-------------+------------+--------------+-------+  *


*------------------------------------------------------------------------------*
DB-PRODUCT-RECORD-HANDLING                       SECTION.
*------------------------------------------------------------------------------*
* Purpose    :  handling a product's  record                                   *
* Description:                                                                 *
*          -  bringing product's record (if customer exists)                   *
*          -  setting switches                                                 *
*          - performing the relevant section according to transaction type     *
*------------------------------------------------------------------------------*
DB-00.
      MOVE TR-PRODUCT-CODE IN TR-REC TO PRODUCT-CODE IN DB03-PRODUCT 

      IF SW06-CUSTOMER-EXIST

          CALL 'DBSEG_BRING'    USING    DB-NO
                                         DB-RCB
                                         DB-OPERATION-TYPE
                                         DB03-PRODUCT
                                         DB-STAT

          IF NOT DB__NORMAL
              IF DB__NOREC 
                    SET SW08-PRODUCT-NOT-EXIST  IN  SW08-PRODUCT-EXISTANCE  TO TRUE
              ELSE
                    SET SW09-DBSEG-BRING-ERROR  IN  SW09-ERROR-STATUS       TO TRUE
                    PERFORM X-HANDLE-ERROR
              END-IF
          ELSE
                    SET SW08-PRODUCT-EXIST      IN  SW08-PRODUCT-EXISTANCE  TO TRUE
          END-IF
      END-IF

*----------------------------------------*                                          

      EVALUATE  TRUE

          WHEN  TR-INSERT-RECORD IN TR-REC 
                PERFORM DBA-PRODUCT-INSERT
          WHEN  TR-MODIFY-RECORD IN TR-REC 
                PERFORM DBB-PRODUCT-MODIFY
          WHEN  TR-DELETE-RECORD IN TR-REC 
                PERFORM DBC-PRODUCT-DELETE
          WHEN  OTHER 
                SET WS04-STATUS-GENERAL-ERR IN WS04-TRANSACTION-STATUS TO TRUE                                                   
                PERFORM H-PREPARE-REPORT 

      END-EVALUATE.

DB-EXIT.
     EXIT.

*------------------------------------------------------------------------------*
DBA-PRODUCT-INSERT                               SECTION.
*------------------------------------------------------------------------------*
* Purpose    : insertion of a product's record                                 *
* Description:                                                                 *
*          - decision of insertion according to product's and customer's       *
*            existance/not existance.                                          *
*          - updating costomer's & product's field                             *
*          - initializing an area in DB for a customer when one doesn't exist  *
*          - setting switches                                                  *
*          - insertion of product's record                                     *
*------------------------------------------------------------------------------*
DBA-00.
       EVALUATE TRUE
 
            WHEN SW08-PRODUCT-NOT-EXIST AND SW06-CUSTOMER-NOT-EXIST

                   SET SW08-PRODUCT-EXIST   IN   SW08-PRODUCT-EXISTANCE   TO TRUE
                   SET SW06-CUSTOMER-EXIST  IN   SW06-CUSTOMER-EXISTANCE  TO TRUE
                   SET SW07-CUSTOMER-INSERT IN   SW07-CUSTOMER-ACTION     TO TRUE
                   SET WS04-STATUS-NORMAL   IN   WS04-TRANSACTION-STATUS  TO TRUE
                   CALL 'GN-INIDEF-C' USING  DB02-CUSTOMER
                   CALL 'GN-INIDEF-P' USING  DB03-PRODUCT
                   PERFORM UC-UPDATE-CUST-FIELDS
                   PERFORM UP-UPDATE-PROD-FIELDS
                   PERFORM I-CUSTOMER-INIT
                   CALL 'DBSEG_INSERT' USING DB-NO
                                             DB-RCB
                                             DB03-PRODUCT
                                             DB-STAT
                   IF NOT DB__NORMAL
                      SET SW09-DBSEG-INSERT-ERROR IN  SW09-ERROR-STATUS   TO TRUE
                      PERFORM X-HANDLE-ERROR
                   END-IF   
                   PERFORM H-PREPARE-REPORT
                   

             WHEN SW08-PRODUCT-NOT-EXIST AND SW06-CUSTOMER-EXIST
                   IF SW07-CUSTOMER-NO-ACTION
                         PERFORM UC-UPDATE-CUST-FIELDS
                         SET  SW07-CUSTOMER-MODIFY IN  SW07-CUSTOMER-ACTION TO TRUE
                   END-IF
                   SET SW08-PRODUCT-EXIST     IN   SW08-PRODUCT-EXISTANCE   TO TRUE
                   CALL 'GN-INIDEF-P' USING  DB03-PRODUCT
                   PERFORM UP-UPDATE-PROD-FIELDS

                   CALL 'DBSEG_INSERT' USING DB-NO
                                             DB-RCB
                                             DB03-PRODUCT
                                             DB-STAT
                   IF NOT DB__NORMAL
                      SET SW09-DBSEG-INSERT-ERROR IN  SW09-ERROR-STATUS   TO TRUE
                      PERFORM X-HANDLE-ERROR
                   END-IF   

                   SET WS04-STATUS-NORMAL TO TRUE
                   PERFORM H-PREPARE-REPORT
                   
             WHEN SW08-PRODUCT-EXIST AND SW06-CUSTOMER-EXIST
                   SET WS04-STATUS-PRD-EXIST IN  WS04-TRANSACTION-STATUS  TO TRUE
                   PERFORM H-PREPARE-REPORT
                   
       END-EVALUATE.

DBA-EXIT.
      EXIT. 

/------------------------------------------------------------------------------*
DBB-PRODUCT-MODIFY                               SECTION.
*------------------------------------------------------------------------------*
* Purpose    : handling a product's modification record.                       *
* Description:                                                                 *
*          - decision of modification according to product's and customer's    *
*            existance/not existance.                                          *
*          - setting switches                                                  *
*          - updating product's & customer's fields                            *
*          - modifiying of product's segment                                   *
*------------------------------------------------------------------------------*
DBB-00.
       EVALUATE TRUE

              WHEN SW08-PRODUCT-NOT-EXIST AND SW06-CUSTOMER-NOT-EXIST
                   SET WS04-STATUS-NO-CUST         IN  WS04-TRANSACTION-STATUS  TO TRUE
                   PERFORM H-PREPARE-REPORT

              WHEN SW08-PRODUCT-NOT-EXIST AND SW06-CUSTOMER-EXIST
                   SET WS04-STATUS-NO-PRD          IN  WS04-TRANSACTION-STATUS  TO TRUE
                   PERFORM H-PREPARE-REPORT

              WHEN SW08-PRODUCT-EXIST AND SW06-CUSTOMER-EXIST
                   IF SW07-CUSTOMER-NO-ACTION
                         PERFORM UC-UPDATE-CUST-FIELDS
                         SET  SW07-CUSTOMER-MODIFY IN  SW07-CUSTOMER-ACTION     TO TRUE
                   END-IF
                   PERFORM UP-UPDATE-PROD-FIELDS
                   CALL 'DBSEG_MODIFY'      USING  DB-NO
                                                   DB-RCB
                                                   DB03-PRODUCT
                                                   DB-STAT

                   IF NOT DB__NORMAL                                            
                      SET SW09-DBSEG-MODIFY-ERROR  IN  SW09-ERROR-STATUS          TO TRUE
                      PERFORM X-HANDLE-ERROR
                   END-IF   

                   SET WS04-STATUS-NORMAL          IN  WS04-TRANSACTION-STATUS    TO TRUE
                   PERFORM H-PREPARE-REPORT
                   
       END-EVALUATE.
                            
DBB-EXIT.
      EXIT.

/------------------------------------------------------------------------------*
DBC-PRODUCT-DELETE                               SECTION.
*------------------------------------------------------------------------------*
* Purpose    : handling a product's deleting record                            *
* Description:                                                                 *
*          - decision of deleting  according to product's and customer's       *
*            existance/not existance.                                          *
*          - setting switches                                                   *
*          - deleting of product's segment                                     *
*------------------------------------------------------------------------------*
DBC-00.
       EVALUATE TRUE

              WHEN SW08-PRODUCT-NOT-EXIST AND SW06-CUSTOMER-NOT-EXIST
                   SET WS04-STATUS-NO-CUST IN  WS04-TRANSACTION-STATUS   TO TRUE
                   PERFORM H-PREPARE-REPORT

              WHEN SW08-PRODUCT-NOT-EXIST AND SW06-CUSTOMER-EXIST
                   SET WS04-STATUS-NO-PRD IN   WS04-TRANSACTION-STATUS   TO TRUE
                   PERFORM H-PREPARE-REPORT


              WHEN SW08-PRODUCT-EXIST AND SW06-CUSTOMER-EXIST  
                   MOVE TR-PRODUCT-CODE IN TR-REC TO PRODUCT-CODE IN DB04-PRODUCT-KEY 
                   CALL 'DBSEG_DELETE'       USING   DB-NO
                                                     DB-RCB
                                                     DB04-PRODUCT-KEY
                                                     DB-STAT

                   IF NOT DB__NORMAL 
                      SET SW09-DBSEG-DELETE-ERROR IN  SW09-ERROR-STATUS  TO TRUE
                      PERFORM X-HANDLE-ERROR
                   END-IF   
 
                   IF SW07-CUSTOMER-NO-ACTION
                         PERFORM UC-UPDATE-CUST-FIELDS
                         SET  SW07-CUSTOMER-MODIFY IN  SW07-CUSTOMER-ACTION  TO TRUE
                   END-IF
                   
                   SET WS04-STATUS-NORMAL TO TRUE
                   PERFORM H-PREPARE-REPORT

       END-EVALUATE.
                                      
DBC-EXIT.
      EXIT.

*------------------------------------------------------------------------------*
E-READ-RECORD                                    SECTION.
*------------------------------------------------------------------------------*
* Purpose    : reading transaction file                                        *
* Description:                                                                 *
*          - setting switches                                                  *
*          - counting read records                                             *
*------------------------------------------------------------------------------*
E-00.
     READ IT-TRANSACTION-FILE
        AT END
            SET SW01-END-OF-TRANSACTION-FILE       IN  SW01-BREAK        TO TRUE
        NOT AT END
            IF NOT WS02-IT-FS-NORMAL
                SET SW09-READING-TRANSACTION-ERROR IN  SW09-ERROR-STATUS TO TRUE
                PERFORM X-HANDLE-ERROR
            ELSE
                ADD 1 TO CN03-TOTAL-RECORDS-READ
            END-IF
     END-READ.
                                   
E-EXIT.
     EXIT.

*------------------------------------------------------------------------------*
F-BREAK                                          SECTION.
*------------------------------------------------------------------------------*
* Purpose    :  checking wheather a new customer is read                       *
* Description:                                                                 *
*          - setting a switch if breaking point is detected                    *
*------------------------------------------------------------------------------*
F-00.
     IF TR-CUSTOMER-NUMBER IN TR-REC  NOT=  WS03-PREV-CUST-NUM  
          SET SW01-END-OF-CUSTOMER IN SW01-BREAK TO TRUE
     END-IF.

F-EXIT.
     EXIT.

*------------------------------------------------------------------------------*
 H-PREPARE-REPORT                                SECTION.
*------------------------------------------------------------------------------*
* Purpose    : preparing report's fields                                       *
* Description:                                                                 *
*         - moving transaction record's fields to report's detail-line         *
*------------------------------------------------------------------------------*
 H-00.
       MOVE ALL SPACES                       TO PR-PRINT-RECORD        IN OR-REC
       MOVE TR-TRANSACTION-NUMBER IN TR-REC  TO PR-TRANSACTION-NUMBER  IN OR-REC
       MOVE TR-TRANSACTION-CODE   IN TR-REC  TO PR-TRANSACTION-CODE    IN OR-REC
       MOVE TR-CUSTOMER-NUMBER    IN TR-REC  TO PR-CUSTOMER-ID         IN OR-REC
       MOVE TR-OWNER-NAME         IN TR-REC  TO PR-OWNER-NAME          IN OR-REC
       MOVE TR-CANCEL-CODE        IN TR-REC  TO PR-CANCEL-CODE         IN OR-REC
       MOVE TR-LEGAL-STATUS       IN TR-REC  TO PR-LEGAL-STATUS        IN OR-REC
       MOVE TR-PRODUCT-CODE       IN TR-REC  TO PR-PRODUCT-CODE        IN OR-REC
       MOVE TR-PRODUCT-STATUS     IN TR-REC  TO PR-PRODUCT-STATUS      IN OR-REC
       MOVE TR-RETIREMENT-STATUS  IN TR-REC  TO PR-RETIREMENT-STATUS   IN OR-REC
       MOVE WS04-TRANSACTION-STATUS          TO PR-STATUS              IN OR-REC

       PERFORM HA-WRITE-REPORT. 

 H-EXIT.     
    EXIT.

*------------------------------------------------------------------------------*
HA-WRITE-REPORT                                  SECTION.
*------------------------------------------------------------------------------*
* Purpose    : writing to report a details record                              *
* Description:                                                                 *
*          - writing                                                           *
*          - counting rejected/worked records                                  *
*------------------------------------------------------------------------------*
HA-00.
       WRITE OR-REC
             AT EOP
                 WRITE  OR-NON-DETAIL-REC  FROM L50-DUMMY  AFTER ADVANCING PAGE
                 PERFORM HB-REPORT-HEADLINES
       END-WRITE

       IF NOT WS01-OR-FS-NORMAL  
            SET SW09-WRITING-REPORT-ERROR IN SW09-ERROR-STATUS  TO TRUE
            PERFORM X-HANDLE-ERROR                             
       END-IF

       IF WS04-STATUS-NORMAL
            ADD 1 TO CN01-PROCESSED-REC
       ELSE        
            IF CN03-TOTAL-RECORDS-READ > 0
               ADD 1 TO CN02-REJECTED-REC
            END-IF
       END-IF.

HA-EXIT.
     EXIT.

*------------------------------------------------------------------------------*
HB-REPORT-HEADLINES                              SECTION.
*------------------------------------------------------------------------------*
* Purpose    : preparing report's non details lines (headlines)                *
* Description:                                                                 *
*          - counting report's page number                                     *
*          - preparation of line for writing                                   *
*------------------------------------------------------------------------------*
HA-00.
      ADD 1 TO CN04-PAGE-NUM
      MOVE CN04-PAGE-NUM TO  L10-PAGE-NUM 

      MOVE L10-HEADLINE-1 TO OR-NON-DETAIL-REC  
      PERFORM HBA-WR-REP-HEADLINE

      MOVE L50-DUMMY TO OR-NON-DETAIL-REC  
      PERFORM HBA-WR-REP-HEADLINE

      MOVE L20-HEADLINE-2  TO OR-NON-DETAIL-REC
      PERFORM HBA-WR-REP-HEADLINE
    
      MOVE L30-LINE TO OR-NON-DETAIL-REC
      PERFORM HBA-WR-REP-HEADLINE.

HA-EXIT.
     EXIT.

*------------------------------------------------------------------------------*
HBA-WR-REP-HEADLINE                              SECTION.
*------------------------------------------------------------------------------*
* Purpose    : writing report's non details lines                              *
* Description:                                                                 *
*------------------------------------------------------------------------------*
HAA-00.
       WRITE  OR-NON-DETAIL-REC

       IF NOT  WS01-OR-FS-NORMAL 
             SET  SW09-WRITING-REPORT-ERROR IN SW09-ERROR-STATUS TO TRUE
             PERFORM  X-HANDLE-ERROR 
       END-IF.

HAA-EXIT.
     EXIT.

*------------------------------------------------------------------------------*
I-CUSTOMER-INIT                                  SECTION.
*------------------------------------------------------------------------------*
* Purpose    :  initializing area for a new customer in DB                     *
* Description:                                                                 *
*------------------------------------------------------------------------------*
I-00.
     CALL 'DBKER_INIT'   USING DB-NO
                               DB-RCB
                               DB-STAT.

     IF NOT DB__NORMAL IN DB-STAT
           SET SW09-DBKER-INIT-ERROR  IN  SW09-ERROR-STATUS  TO TRUE
           PERFORM X-HANDLE-ERROR
     END-IF.

I-EXIT.
     EXIT.
 

*------------------------------------------------------------------------------*
 G-CUSTOMER-END                               SECTION.
*------------------------------------------------------------------------------*
* Purpose    : updating DB with customer's details                             *
* Description:                                                                 *
*          - performing a relevant section according to sw07: insertion,       *
*            modification, no action                                          *
*------------------------------------------------------------------------------*
 G-00.
      EVALUATE TRUE

          WHEN SW07-CUSTOMER-INSERT
               PERFORM GA-INSERT-CUST

          WHEN SW07-CUSTOMER-MODIFY
               PERFORM GB-MODIFY-CUST

          WHEN OTHER
               GO TO G-EXIT

      END-EVALUATE.

 G-EXIT.           
     EXIT.

*------------------------------------------------------------------------------*
GA-INSERT-CUST                                   SECTION.
*------------------------------------------------------------------------------*
* Purpose    : inserting a customer                                            *
* Description:                                                                 *
*------------------------------------------------------------------------------*
GA-00.
      CALL 'DBKER_INSERT' USING DB-NO
                                DB-RCB
                                DB02-CUSTOMER
                                DB-ANNEX-IND
                                DB-STAT.

      IF  NOT DB__NORMAL
         SET SW09-DBKER-INSERT-ERROR  IN  SW09-ERROR-STATUS  TO TRUE
         PERFORM X-HANDLE-ERROR
      END-IF.

GA-EXIT.
     EXIT.

*------------------------------------------------------------------------------*
GB-MODIFY-CUST                                   SECTION.
*------------------------------------------------------------------------------*
* Purpose    : modifying a customer                                            *
* Description:                                                                 *
*------------------------------------------------------------------------------*
GB-00.
      CALL 'DBKER_MODIFY' USING DB-NO
                                DB-RCB
                                DB02-CUSTOMER
                                DB-ANNEX-IND
                                DB-STAT.

       IF  NOT DB__NORMAL
           SET SW09-DBKER-MODIFY-ERROR   IN  SW09-ERROR-STATUS  TO TRUE
           PERFORM X-HANDLE-ERROR
       END-IF.

GB-EXIT.
     EXIT.

*------------------------------------------------------------------------------*
UC-UPDATE-CUST-FIELDS                            SECTION.
*------------------------------------------------------------------------------*
* Purpose    : updating customer's  fields                                     *
* Description:                                                                 *
*          - moving information from transaction record to customer's one      *
*------------------------------------------------------------------------------*
UC-00.
      MOVE TR-CUSTOMER-NUMBER IN TR-REC    TO CUSTOMER-ID  IN DB02-CUSTOMER   
      MOVE TR-PRODUCT-CODE    IN TR-REC    TO HBD          IN DB02-CUSTOMER                 
      MOVE TR-CANCEL-CODE     IN TR-REC    TO CANCEL-CODE  IN DB02-CUSTOMER               
      MOVE TR-OWNER-NAME      IN TR-REC    TO OWNER-NAME   IN DB02-CUSTOMER              
      MOVE TR-LEGAL-STATUS    IN TR-REC    TO LEGAL-STATUS IN DB02-CUSTOMER.                   
                            
UC-EXIT.
     EXIT.

*------------------------------------------------------------------------------*
UP-UPDATE-PROD-FIELDS                            SECTION.
*------------------------------------------------------------------------------*
* Purpose    : updating product's   fields                                     *
* Description:                                                                 *
*          - moving information from transaction record to product's one       *
*------------------------------------------------------------------------------*
UP-00.
      MOVE TR-PRODUCT-CODE      IN TR-REC  TO PRODUCT-CODE         IN DB03-PRODUCT    
      MOVE TR-PRODUCT-STATUS    IN TR-REC  TO PRODUCT-STATUS       IN DB03-PRODUCT(1)                   
      MOVE TR-RETIREMENT-STATUS IN TR-REC  TO RETIREMENT-STATUS    IN DB03-PRODUCT(1).                   
                           
UP-EXIT.
    EXIT.

*------------------------------------------------------------------------------*
 W-INIT-PROGRAM                                  SECTION.
*------------------------------------------------------------------------------*
* Purpose    :  to initialize the program                                      *
* Description:                                                                 *
*             - accept date from the system                                    *
*             - open files (transactin & report)                               *
*             - open database                                                  *
*             - read first record from transaction file                        *
*------------------------------------------------------------------------------*
W-00.
     ACCEPT  WS05-CURRENT-DATE FROM DATE
     MOVE WS05-YY TO L10-YY
     MOVE WS05-MM TO L10-MM
     MOVE WS05-DD TO L10-DD

     PERFORM WA-OPENING-MSG
     PERFORM WB-OPEN-REPORT-FILE
     PERFORM HB-REPORT-HEADLINES 
     PERFORM WC-OPEN-TRANSACTION-FILE    
     PERFORM WD-DB-OPEN
     PERFORM E-READ-RECORD

     IF  SW01-END-OF-TRANSACTION-FILE
         MOVE 000000000   TO OPMSG-MSG-NO
         SET  MSG02-EMPTY-TRN-FILE     IN MSG02-MESSAGE-TEXT      TO TRUE
         MOVE MSG02-MESSAGE-TEXT       TO OPMSG-TEXT
         PERFORM Y-DISPLAY-MESSAGE

         MOVE ALL SPACES               TO PR-PRINT-RECORD         IN OR-REC
         SET  WS04-STATUS-EMPTY-FILE   IN WS04-TRANSACTION-STATUS TO TRUE
         MOVE WS04-TRANSACTION-STATUS  TO PR-STATUS               IN OR-REC
         PERFORM  HA-WRITE-REPORT
     END-IF
 
     SET PRODUCT-SEG-TYPE IN SEG-TYPE  IN DB03-PRODUCT            TO TRUE
     SET PRODUCT-SEG-TYPE IN SEG-TYPE  IN DB04-PRODUCT-KEY        TO TRUE.

 W-EXIT.   
    EXIT.

*------------------------------------------------------------------------------*
WA-OPENING-MSG                                   SECTION.
*------------------------------------------------------------------------------*
* Purpose    : sending a message to the log-file                               *
* Description:                                                                 *
*------------------------------------------------------------------------------*
WA-00.
      SET OP-OPEN IN OPMSG-VERB                           TO TRUE
      PERFORM Y-DISPLAY-MESSAGE
                        
      SET OP-REGULAR IN OPMSG-MSG-TYPE                    TO TRUE
      SET OP-UPDATE IN OPMSG-VERB                         TO TRUE

      MOVE 000000000 TO OPMSG-MSG-NO 
      SET MSG02-RUNNING-PROGRAM  IN  MSG02-MESSAGE-TEXT   TO TRUE
      MOVE MSG02-MESSAGE-TEXT TO OPMSG-TEXT
      PERFORM Y-DISPLAY-MESSAGE.

WA-EXIT.
     EXIT.

*------------------------------------------------------------------------------*
WB-OPEN-REPORT-FILE                              SECTION.
*------------------------------------------------------------------------------*
* Purpose    : opening the report file                                         *
* Description:                                                                 *
*------------------------------------------------------------------------------*
WB-00.
      OPEN OUTPUT OR-REPORT-FILE
      IF NOT WS01-OR-FS-NORMAL  IN  WS01-OR-FILE-STATUS 
           SET SW09-OPEN-REPORT-ERROR  IN  SW09-ERROR-STATUS         TO TRUE
           PERFORM X-HANDLE-ERROR 
      ELSE
           SET SW03-OR-REPORT-OPEN  IN   SW03-OR-REPORT-STATUS       TO TRUE
      END-IF.

WB-EXIT.
     EXIT.

*------------------------------------------------------------------------------*
WC-OPEN-TRANSACTION-FILE                         SECTION.
*------------------------------------------------------------------------------*
* Purpose    :  opening the transaction file                                   *
* Description:                                                                 *
*------------------------------------------------------------------------------*
WC-00.
      OPEN INPUT IT-TRANSACTION-FILE
      IF NOT  WS02-IT-FS-NORMAL  IN  WS02-IT-FILE-STATUS   
           SET  SW09-OPEN-TRN-FILE-ERROR  IN  SW09-ERROR-STATUS           TO TRUE
           PERFORM X-HANDLE-ERROR    
      ELSE
           SET SW02-IT-TRANSACTION-OPEN   IN  SW02-IT-TRANSACTION-STATUS  TO TRUE
      END-IF.


WC-EXIT.
     EXIT.

*------------------------------------------------------------------------------*
WD-DB-OPEN                                       SECTION.
*------------------------------------------------------------------------------*
* Purpose    :  opening the database                                           *
* Description:                                                                 *
*------------------------------------------------------------------------------*
WD-00.
      SET OPEN-UPDATING-BATCH-RUN-ALONE IN DB-OPEN-TYPE        TO TRUE
      SET DB-EXPAND                     IN DB-EC-IND           TO TRUE
      SET DB-ANNEX-NOT-REQUIRED         IN DB-ANNEX-IND        TO TRUE
      SET DB-UPDATE                     IN DB-UPDATE-IND       TO TRUE
      
      CALL 'GN_NAME'  USING  DB-NAME
                             DB-PREFIX-NAME
                             DB-NAME-STATUS.
                                   
      IF NOT OP__NORMAL IN DB-NAME-STATUS
           SET  SW09-GN-NAME-ERROR      IN  SW09-ERROR-STATUS  TO TRUE
           PERFORM X-HANDLE-ERROR
      END-IF

      CALL "DBFIL-OPEN" USING DB-PREFIX-NAME
                              DB-OPEN-TYPE
                              DB-BUF-LENGTH
                              DB-SEG-BUF-LENGTH 
                              DB-EC-IND 
                              DB-PGM-NAME
                              DB-ANNEX-IND 
                              DB-NO 
                              DB-STAT. 

      IF DB__NORMAL IN  DB-STAT
           SET SW04-DB-OPEN             IN  SW04-DB-STATUS     TO TRUE
      ELSE
           SET  SW09-OPEN-DB-ERROR      IN  SW09-ERROR-STATUS  TO TRUE
           PERFORM X-HANDLE-ERROR    
      END-IF. 
    
WD-EXIT.
    EXIT.

*------------------------------------------------------------------------------*
X-HANDLE-ERROR                                   SECTION.
*------------------------------------------------------------------------------*
* Purpose    : handling fatal errors                                           *
* Description:                                                                 *
*          - moving all zero to msg-no (for free text)                         *
*          - moving trn #  and db-status to free text                          *
*          - activating the relevant paragraph according to error's type       *
*          - terminating the program via z-finnish                             *
*------------------------------------------------------------------------------*
X-00.
     MOVE 000000000                            TO OPMSG-MSG-NO
     MOVE TR-TRANSACTION-NUMBER IN TR-REC      TO MSG02-TRN-NUM IN MSG02-ERROR-MESSAGE 
     MOVE DB-STAT                              TO MSG02-DB-STAT IN MSG02-ERROR-MESSAGE 

     GO TO   X-01-OPEN-REPORT-ERROR
             X-02-OPEN-TRN-FILE-ERROR
             X-03-GN-NAME-ERROR
             X-04-OPEN-DB-ERROR
             X-05-WRITING-REPORT-ERROR    
             X-06-READING-TRANSACTION-ERROR
             X-07-DBKER-DELETE-ERROR
             X-08-DBKER-MODIFY-ERROR             
             X-09-DBKER-INSERT-ERROR 
             X-10-DBKER-BRING-ERROR
             X-11-DBSEG-DELETE-ERROR 
             X-12-DBSEG-MODIFY-ERROR
             X-13-DBSEG-INSERT-ERROR 
             X-14-DBSEG-BRING-ERROR
             X-15-DBKER-INIT-ERROR 

                                            DEPENDING ON SW09-ERROR-STATUS.

X-01-OPEN-REPORT-ERROR.

         SET MSG02-REPORT-OPENING-ERROR            IN MSG02-MESSAGE-TEXT    TO TRUE
         MOVE MSG02-ERROR-MESSAGE                                           TO OPMSG-TEXT   
         PERFORM Y-DISPLAY-MESSAGE
         GO TO X-STOP.

X-02-OPEN-TRN-FILE-ERROR.

         SET  MSG02-TRN-FILE-OPEN-ERROR             IN MSG02-MESSAGE-TEXT    TO TRUE  
         MOVE MSG02-ERROR-MESSAGE                                            TO OPMSG-TEXT   
         PERFORM Y-DISPLAY-MESSAGE
         GO TO X-STOP.

X-03-GN-NAME-ERROR.

         SET  MSG02-DEFAULT-DB-NAME-ERROR           IN MSG02-MESSAGE-TEXT    TO TRUE     
         MOVE MSG02-ERROR-MESSAGE                                            TO OPMSG-TEXT   
         PERFORM Y-DISPLAY-MESSAGE
         GO TO X-STOP.

X-04-OPEN-DB-ERROR.   

         SET  MSG02-OPEN-DB-ERROR                   IN MSG02-MESSAGE-TEXT    TO TRUE  
         MOVE MSG02-ERROR-MESSAGE                                            TO OPMSG-TEXT   
         PERFORM Y-DISPLAY-MESSAGE
         GO TO X-STOP.
  

X-05-WRITING-REPORT-ERROR.    

         SET  MSG02-WRITING-REPORT-ERROR            IN MSG02-MESSAGE-TEXT    TO TRUE          
         MOVE MSG02-ERROR-MESSAGE                                            TO OPMSG-TEXT     
         PERFORM Y-DISPLAY-MESSAGE
         GO TO X-STOP.

X-06-READING-TRANSACTION-ERROR.

         SET MSG02-READING-TRANSACTION-ERROR        IN MSG02-MESSAGE-TEXT    TO TRUE     
         MOVE MSG02-ERROR-MESSAGE                                            TO OPMSG-TEXT   
         PERFORM Y-DISPLAY-MESSAGE
         GO TO X-STOP.

X-07-DBKER-DELETE-ERROR.

         SET  MSG02-DBKER-DELETE-ERROR              IN MSG02-MESSAGE-TEXT    TO TRUE     
         MOVE MSG02-ERROR-MESSAGE                                            TO OPMSG-TEXT   
         PERFORM Y-DISPLAY-MESSAGE
         GO TO X-STOP.

X-08-DBKER-MODIFY-ERROR.

         SET  MSG02-DBKER-MODIFY-ERROR              IN MSG02-MESSAGE-TEXT    TO TRUE     
         MOVE MSG02-ERROR-MESSAGE                                            TO OPMSG-TEXT   
         PERFORM Y-DISPLAY-MESSAGE
         GO TO X-STOP.

            
X-09-DBKER-INSERT-ERROR.

         SET  MSG02-DBKER-INSERT-ERROR              IN MSG02-MESSAGE-TEXT    TO TRUE     
         MOVE MSG02-ERROR-MESSAGE                                            TO OPMSG-TEXT   
         PERFORM Y-DISPLAY-MESSAGE
         GO TO X-STOP.

X-10-DBKER-BRING-ERROR.

         SET  MSG02-DBKER-BRING-ERROR               IN MSG02-MESSAGE-TEXT    TO TRUE     
         MOVE MSG02-ERROR-MESSAGE                                            TO OPMSG-TEXT   
         PERFORM Y-DISPLAY-MESSAGE
         GO TO X-STOP.

X-11-DBSEG-DELETE-ERROR.

         SET  MSG02-DBSEG-DELETE-ERROR              IN MSG02-MESSAGE-TEXT    TO TRUE     
         MOVE MSG02-ERROR-MESSAGE                                            TO OPMSG-TEXT   
         PERFORM Y-DISPLAY-MESSAGE
         GO TO X-STOP.

X-12-DBSEG-MODIFY-ERROR.

         SET  MSG02-DBSEG-MODIFY-ERROR              IN MSG02-MESSAGE-TEXT    TO TRUE     
         MOVE MSG02-ERROR-MESSAGE                                            TO OPMSG-TEXT   
         PERFORM Y-DISPLAY-MESSAGE
         GO TO X-STOP.

X-13-DBSEG-INSERT-ERROR.

         SET  MSG02-DBSEG-INSERT-ERROR              IN MSG02-MESSAGE-TEXT    TO TRUE     
         MOVE MSG02-ERROR-MESSAGE                                            TO OPMSG-TEXT   
         PERFORM Y-DISPLAY-MESSAGE
         GO TO X-STOP.

X-14-DBSEG-BRING-ERROR.

         SET  MSG02-DBSEG-BRING-ERROR               IN MSG02-MESSAGE-TEXT    TO TRUE     
         MOVE MSG02-ERROR-MESSAGE                                            TO OPMSG-TEXT   
         PERFORM Y-DISPLAY-MESSAGE
         GO TO X-STOP.
         
X-15-DBKER-INIT-ERROR.

         SET  MSG02-DBKER-INIT-ERROR                IN MSG02-MESSAGE-TEXT    TO TRUE     
         MOVE MSG02-ERROR-MESSAGE                                            TO OPMSG-TEXT   
         PERFORM Y-DISPLAY-MESSAGE
         GO TO X-STOP.                                    

X-STOP.

         SET  SW05-PROGRAM-END-ERROR                IN SW05-PROGRAM-STATUS   TO TRUE
         PERFORM Z-FINISH-PROGRAM.
                                  
X-EXIT.
     EXIT.

*------------------------------------------------------------------------------*
Y-DISPLAY-MESSAGE                                SECTION.
*------------------------------------------------------------------------------*
* Purpose    : printing messages into log file                                 *
* Description: using the OP-LOG-MSG routine                                    *
*------------------------------------------------------------------------------*
Y-00.
     CALL "OP-LOG-MSG" USING   OPMSG-VERB
                               OPMSG-MSG-TYPE
                               OPMSG-MSG-NO
                               MSG-PARAM-LIST
                               OPMSG-TEXT
                               OPMSG-STATUSES.

Y-EXIT.
     EXIT.

*------------------------------------------------------------------------------*
Z-FINISH-PROGRAM                                SECTION.
*------------------------------------------------------------------------------*
* Purpose    :  termination of program                                         *
* Description:                                                                 *
*           -  activating relevant sections for closing report file and        *
*              transaction file.                                               *
*           -  calling a section for closing DB only if DB is open and program *
*              is not in fatal error.                                          *
*           -  calling a section for writing last statistics to report         *
*           -  terminating the program by the 'sys$exit' system call           *
*------------------------------------------------------------------------------*
Z-00.
     PERFORM ZA-CLOSE-TRAN-FILE 
     PERFORM ZB-STATISTICS
     PERFORM ZC-CLOSE-REPORT 
     IF      SW05-PROGRAM-END-SUCCESSFULY
         AND SW04-DB-OPEN       

             PERFORM ZD-CLOSE-DB

     END-IF

     CALL 'SYS$EXIT'  USING BY VALUE SW05-PROGRAM-STATUS. 
     
Z-EXIT.
     EXIT.

*------------------------------------------------------------------------------*
ZA-CLOSE-TRAN-FILE                               SECTION.
*------------------------------------------------------------------------------*
* Purpose    : closing the transactions file                                   *
* Description:                                                                 *
*------------------------------------------------------------------------------*
ZA-00.
     IF SW02-IT-TRANSACTION-OPEN 
        CLOSE IT-TRANSACTION-FILE
        IF NOT WS02-IT-FS-NORMAL
            MOVE 000000000                           TO OPMSG-MSG-NO
            SET MSG02-TRN-FILE-CLOSE-ERROR IN MSG02-MESSAGE-TEXT TO TRUE 
            MOVE MSG02-MESSAGE-TEXT                  TO OPMSG-TEXT   
            PERFORM Y-DISPLAY-MESSAGE
         END-IF
      END-IF.
    
ZA-EXIT.
     EXIT.

*------------------------------------------------------------------------------*
ZB-STATISTICS                                    SECTION.
*------------------------------------------------------------------------------*
* Purpose    : preparing information for statistics writing                    *
* Description:                                                                 *
*          - moving the following numbers (records worked, records rejected    *
*            records read) to report details.                                  *
*            After each move a write section is performed                      *
*------------------------------------------------------------------------------*
ZB-00.
      MOVE L30-LINE  TO OR-NON-DETAIL-REC  
      PERFORM HBA-WR-REP-HEADLINE

      MOVE ALL SPACES TO PR-PRINT-RECORD IN OR-REC
      SET PR-RECORD-WORKED IN PR-STATISTICS TO TRUE
      MOVE CN01-PROCESSED-REC TO PR-DISPLAY-COUNTER IN PR-STATISTICS
      PERFORM  ZBA-WRITE-STATISTICS 

      MOVE ALL SPACES TO PR-PRINT-RECORD IN OR-REC
      SET PR-RECORD-REJECTED IN PR-STATISTICS TO TRUE
      MOVE CN02-REJECTED-REC TO PR-DISPLAY-COUNTER IN PR-STATISTICS
      PERFORM  ZBA-WRITE-STATISTICS 

      MOVE ALL SPACES TO PR-PRINT-RECORD IN OR-REC
      SET PR-RECORD-READ   IN PR-STATISTICS TO TRUE
      MOVE CN03-TOTAL-RECORDS-READ TO PR-DISPLAY-COUNTER IN PR-STATISTICS
      PERFORM  ZBA-WRITE-STATISTICS.

ZB-EXIT.
     EXIT.

*------------------------------------------------------------------------------*
ZBA-WRITE-STATISTICS                             SECTION.
*------------------------------------------------------------------------------*
* Purpose    : writing statistics into report                                  *
* Description:                                                                 *
*------------------------------------------------------------------------------*
ZBA-00.
       WRITE OR-REC
           IF NOT WS01-OR-FS-NORMAL 
               MOVE 000000000                        TO OPMSG-MSG-NO
               SET MSG02-WRITING-REPORT-ERROR    IN MSG02-MESSAGE-TEXT     TO TRUE
               MOVE MSG02-MESSAGE-TEXT               TO OPMSG-TEXT   
               PERFORM Y-DISPLAY-MESSAGE
           END-IF.
 
ZBA-EXIT.
     EXIT.

*------------------------------------------------------------------------------*
ZC-CLOSE-REPORT                                  SECTION.
*------------------------------------------------------------------------------*
* Purpose    : closing report file                                             *
* Description:                                                                 *
*          - writing closing lines                                             *
*          - closing the file
*------------------------------------------------------------------------------*
ZC-00.
      MOVE L50-DUMMY TO OR-NON-DETAIL-REC
      WRITE  OR-NON-DETAIL-REC
          IF NOT  WS01-OR-FS-NORMAL 
              MOVE 000000000                        TO OPMSG-MSG-NO
              SET MSG02-WRITING-REPORT-ERROR        IN MSG02-MESSAGE-TEXT TO TRUE 
              MOVE MSG02-MESSAGE-TEXT               TO OPMSG-TEXT   
              PERFORM Y-DISPLAY-MESSAGE
          END-IF

      MOVE L40-END-LINE TO OR-NON-DETAIL-REC
      WRITE  OR-NON-DETAIL-REC
          IF NOT  WS01-OR-FS-NORMAL 
              MOVE 000000000                        TO OPMSG-MSG-NO
              SET MSG02-WRITING-REPORT-ERROR        IN MSG02-MESSAGE-TEXT TO TRUE 
              MOVE MSG02-MESSAGE-TEXT               TO OPMSG-TEXT   
              PERFORM Y-DISPLAY-MESSAGE
          END-IF

      IF SW03-OR-REPORT-OPEN
         CLOSE OR-REPORT-FILE
         IF NOT WS01-OR-FS-NORMAL
              MOVE 000000000                        TO OPMSG-MSG-NO
              SET MSG02-REPORT-CLOSING-ERROR        IN MSG02-MESSAGE-TEXT TO TRUE 
              MOVE MSG02-MESSAGE-TEXT               TO OPMSG-TEXT   
              PERFORM Y-DISPLAY-MESSAGE
         END-IF
      END-IF.

ZC-EXIT.
     EXIT.

*------------------------------------------------------------------------------*
ZD-CLOSE-DB                                      SECTION.
*------------------------------------------------------------------------------*
* Purpose    : closing DB                                                      *
* Description:                                                                 *
*------------------------------------------------------------------------------*
ZD-00.
      CALL 'DBFIL_CLOSE'                 USING   DB-NO
                                                 DB-STAT.

      IF NOT DB__NORMAL IN DB-STAT
            MOVE 000000000                        TO OPMSG-MSG-NO
            SET MSG02-CLOSE-DB-ERROR              IN MSG02-MESSAGE-TEXT TO TRUE
            MOVE MSG02-MESSAGE-TEXT               TO OPMSG-TEXT   
            PERFORM Y-DISPLAY-MESSAGE
      ELSE
            MOVE 000000000                        TO OPMSG-MSG-NO
            SET MSG02-SUCCESSFUL-TERMINATION      IN MSG02-MESSAGE-TEXT TO TRUE
            MOVE MSG02-MESSAGE-TEXT               TO OPMSG-TEXT   
            PERFORM Y-DISPLAY-MESSAGE
      END-IF.

ZD-EXIT.
     EXIT.
