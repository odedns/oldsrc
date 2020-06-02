/------------------------------------------------------------------------------*
* PROJECT                                                                      *
*------------------------------------------------------------------------------*
*                                                                              *
* File : [akurs7.proj]proj1.cob                                                *
*                                                                              *
*------------------------------------------------------------------------------*
* Purpose: This program reads the whole data base and produces a report        *
*          which prints the number of customers per each product in the        *
*          data base   and sums up the number of customer records which        *
*          have segment records(products).                                     *
*                                                                              *
* Description:                                                                 *
*          The program uses Cobol's Sort routine with Input and Output         *
*          procedures.                                                         *
*          The input procedure reads the data base and releases record to      *
*          the sort file , while the output procedure creates the report by    *
*          using the sorted records from the sort file.                        *
*          The sort key is the product key.                                    *
* -----------------------------------------------------------------------------*
* Files :                                                                      *
*                                                                              *
*          Output file:                                                        *
*          OR-REPORT-FILE               : the produced  report.                *
*------------------------------------------------------------------------------*
* Called procedures:                                                           *
*            OP-LOG-MSG                    : write error messages to log file  *
*            DBFIL_OPEN                    : open the data base                *
*            GN-INIDEF-C                   : initialize DB customer record     *
*            GN-INIDEF-P                   : initialize DB product record      *
*            DBKER_NEXT                    : get the next kernel               *
*            DBSEG_FIND                    : locate a segment                  *
*            DBSEG_NEXT                    : get the next segment              *
*            DBFIL_CLOSE                   : close the data base               *
*            SYS$EXIT                      : stop program by a VMS system call *
*------------------------------------------------------------------------------*
* Program history: None                                                        *
*                                                                              *
*    Date:              22-APRIL-1993                                          *
*    Programmers:       Nissan Oded                                            *
*                       Mandl Amir                                             *
*    Supervisors:       Itzik Alkalay                                          *
*                       Liat  Porat                                            *
*------------------------------------------------------------------------------*
 
/******************************************************************************
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
                ASSIGN         TO     "PR-REPORT-FILE"
                RESERVE        2      AREAS
                ORGANIZATION   IS     SEQUENTIAL
                FILE STATUS    IS     WS01-OR-FILE-STATUS.

      SELECT    SO-SORT-FILE 
                ASSIGN         TO     "SORT-FILE".

/******************************************************************************
 DATA                                           DIVISION.
*******************************************************************************
*------------------------------------------------------------------------------
 FILE                                           SECTION.
*------------------------------------------------------------------------------

FD  OR-REPORT-FILE
    REPORT IS OR-REPORT-REC.

SD  SO-SORT-FILE.
01  SO-REC.
    03   SO-PRODUCT-CODE                    PIC X(4).
    03   SO-CUSTOMER-NUMBER                 PIC S9(9) COMP-3.

/-------------------------------------------------------------------------------
 WORKING-STORAGE                                 SECTION.
*-------------------------------------------------------------------------------

*-------------------------------------------------------------------------------
*    CNXX.   COUNTERS
*-------------------------------------------------------------------------------
 
01    CNXX.
      03   CN01-PRODUCTS-READ               PIC 9(8) COMP       VALUE 0.
      03   CN02-CUSTOMERS-READ              PIC 9(8) COMP       VALUE 0.
      03   CN03-CUSTOMERS-PER-PRD           PIC 9(8) COMP       VALUE 0.
      03   CN04-CUSTS-WITH-PRDS             PIC 9(8) COMP       VALUE 0.       
      03   CN05-QUOTIENT                    PIC 9(8) COMP       VALUE 0.   
      03   CN06-REMAINDER                   PIC 9(8) COMP       VALUE 0.   

*------------------------------------------------------------------------------
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
*    MSGXX.   MESSAGES PARAMETERS.
*-------------------------------------------------------------------------------

01   MSG01-MSG-PRM.
     COPY "GOP$COMSRCOP:OPMSGPRM.LIB".

/-------------------------------------------------------------------------------
*   SWXX.   SWITCHES.
*-------------------------------------------------------------------------------

01  SW01-OUTPUT-BREAK                       PIC S9(5) COMP      VALUE 0.
    88    SW01-NOBREAK                                          VALUE 0.
    88    SW01-END-OF-SORT-FILE                                 VALUE 1.
    88    SW01-END-OF-PRODUCT                                   VALUE 2  1.

01  SW02-INPUT-BREAK                        PIC S9(4) COMP      VALUE 0.
    88   SW02-NOBREAK                                           VALUE 0.
    88   SW02-END-DB                                            VALUE 1.
    88   SW02-END-CUSTOMER                                      VALUE 2  1.

01  SW03-OR-REPORT-STATUS                   PIC S9(4) COMP      VALUE 0.
    88    SW03-OR-REPORT-OPEN                                   VALUE 1.
    88    SW03-OR-REPORT-CLOSED                                 VALUE 0.

01  SW04-DB-STATUS                          PIC 9               VALUE 0.
    88    SW04-DB-CLOSE                                         VALUE 0.
    88    SW04-DB-OPEN                                          VALUE 1.

01  SW05-PROGRAM-STATUS                     PIC S9(6) COMP      VALUE 1.
    88    SW05-PROGRAM-END-SUCCESSFULY                          VALUE 1.
    88    SW05-PROGRAM-END-ERROR                                VALUE 2.

01   SW06-ERROR-STATUS                      PIC 99              VALUE 0.
     88   SW06-NO-ERROR                                         VALUE 0.
     88   SW06-OPEN-REPORT-ERROR                                VALUE 1.
     88   SW06-CLOSE-REPORT-ERROR                               VALUE 2.
     88   SW06-WRITING-REPORT-ERROR                             VALUE 3.
     88   SW06-GN-NAME-ERROR                                    VALUE 4.
     88   SW06-OPEN-DB-ERROR                                    VALUE 5.
     88   SW06-DBKER-NEXT-ERROR                                 VALUE 6.
     88   SW06-DBSEG-FIND-ERROR                                 VALUE 7.
     88   SW06-DBSEG-NEXT-ERROR                                 VALUE 8.

01   SW07-DB-EMPTYNESS                      PIC 9               VALUE 0.
     88   SW07-DB-NOT-EMPTY                                     VALUE 0.
     88   SW07-DB-EMPTY                                         VALUE 1.
      
01   SW08-OR-IO-STATUS                      PIC 9               VALUE 0.
     88   SW08-OR-IO-NO-ERROR                                   VALUE 0.
     88   SW08-OR-IO-ERROR                                      VALUE 1.

*-------------------------------------------------------------------------------
*  WSXX.   WORKING STORAGE VARIABLES.
*-------------------------------------------------------------------------------

01   WS01-OR-FILE-STATUS                    PIC XX.          
     88   WS01-OR-FS-NORMAL                                     VALUE "00".

01   WS02-PREV-PROD-CODE                    PIC X(4).

01   WS03-CURRENT-DATE.
     03   WS03-YY                           PIC 99.
     03   WS03-MM                           PIC 99.
     03   WS03-DD                           PIC 99.

/-----------------------------------------------------------------------------*
REPORT                                    SECTION.
*-----------------------------------------------------------------------------*
RD  OR-REPORT-REC
    PAGE LIMIT IS   COPY 'GOP$FLDEF:EF0195.VAR'.
    HEADING         1
    FIRST DETAIL    09
    LAST  DETAIL    COPY 'GOP$FLDEF:EF0210.VAR'.
    FOOTING         61
    CONTROLS ARE FINAL.

*-----------------------------------------------------------------------------*
01 L10-PAGE-HEADING                       TYPE IS PAGE HEADING.
*-----------------------------------------------------------------------------*

    03  LINE 1.
        05  COLUMN   1                      PIC  X(16) VALUE 'SBP ADS/SALES-II'.
        05  COLUMN   63                     PIC  X(10) VALUE 'DATA BASE:'.   
        05  COLUMN   77                     PIC  X(3)  SOURCE DB-PREFIX-NAME.
   
    03  LINE IS PLUS 2.
        05  COLUMN  20                      PIC  X(35) VALUE "C U S T O M E R S    R E P O R T   ".

    03  LINE IS PLUS 2.
        05  COLUMN  1                       PIC  X(9)  VALUE 'RUN DATE:'. 
        05  COLUMN  11                      PIC  99    SOURCE WS03-DD.
        05  COLUMN  13                      PIC  X     VALUE '/'.
        05  COLUMN  14                      PIC  99    SOURCE WS03-MM.
        05  COLUMN  16                      PIC  X     VALUE '/'.
        05  COLUMN  17                      PIC  99    SOURCE WS03-YY.
        05  COLUMN  68                      PIC  X(6)  VALUE "PAGE:".
        05  COLUMN  76                      PIC  ZZZ9  SOURCE PAGE-COUNTER.

    03  LINE IS PLUS 2.
        05  COLUMN  5                       PIC  X(20) VALUE 'PRODUCT CODE       '.
        05  COLUMN  30                      PIC  X(20) VALUE 'NUMBER OF CUSTOMERS '.

*-----------------------------------------------------------------------------*
01 L20-CST                                TYPE IS DETAIL.
*-----------------------------------------------------------------------------*

    03  LINE IS PLUS 1.
        05  COLUMN  8                       PIC  X(4)  SOURCE WS02-PREV-PROD-CODE.
        05  COLUMN  35                      PIC  ZZZ9  SOURCE CN03-CUSTOMERS-PER-PRD.

*-----------------------------------------------------------------------------*
01 L30-EMPTY-DB                           TYPE IS DETAIL.
*-----------------------------------------------------------------------------*

    03  LINE IS PLUS 1.
        05  COLUMN  20                      PIC  X(20)  VALUE "DATABASE IS EMPTY !!".

*-----------------------------------------------------------------------------*
01 L31-DUMMY                              TYPE IS DETAIL.
*-----------------------------------------------------------------------------*

    03  LINE IS PLUS 1.
        05  COLUMN 1                        PIC X(80)   VALUE ALL SPACES.

*-----------------------------------------------------------------------------*
01 L40-REPORT-FOOTING                     TYPE IS REPORT FOOTING.
*-----------------------------------------------------------------------------*

    03  LINE IS PLUS 5.
        05  COLUMN  10                      PIC  X(30) VALUE 'TOTAL CUSTOMERS WITH PRDS  = '.
        05  COLUMN  45                      PIC  ZZZZ9 SOURCE CN04-CUSTS-WITH-PRDS.

    03  LINE IS PLUS 5.
        05  COLUMN  28                      PIC  X(34) VALUE "*** E N D   O F   R E P O R T ***".

/*******************************************************************************        
 PROCEDURE                                       DIVISION.
********************************************************************************
DECLARATIVES.
*------------------------------------------------------------------------------*
OR-IO-ERRORS                                     SECTION.
*------------------------------------------------------------------------------*
* Purpose    : handling report's i-o errors                                    *
* Description:                                                                 *
*------------------------------------------------------------------------------*
    USE AFTER  STANDARD ERROR PROCEDURE ON OR-REPORT-FILE. 
OR-00. 
       SET SW08-OR-IO-ERROR IN SW08-OR-IO-STATUS  TO TRUE
       CALL "DISPLAY_ERR_MSG" USING BY REFERENCE  RMS-FILENAME OF OR-REPORT-FILE
                                                  RMS-STS      OF OR-REPORT-FILE
                                                  RMS-STV      OF OR-REPORT-FILE.

OR-EXIT.
      EXIT.
*------------------------------------------------------------------------------*
END DECLARATIVES.
*------------------------------------------------------------------------------*
A-MAIN                                           SECTION.
*------------------------------------------------------------------------------*
* Purpose    :  controling the program's flow                                  *
* Description:                                                                 *
*           - begining program                                                 *
*           - sort                                                             *
*                  -input procedure : db reading                               *
*                  -output procedure : report creation                         *
*           - terminating program                                              *
*                                                                              *
*------------------------------------------------------------------------------*
A-00.
     PERFORM W-INIT-PROGRAM

         SORT SO-SORT-FILE 
              ASCENDING KEY SO-PRODUCT-CODE
              INPUT PROCEDURE  B-INPUT-PROCESS
              OUTPUT PROCEDURE C-OUTPUT-PROCESS  

     PERFORM Z-FINISH-PROGRAM.

A-EXIT.
     EXIT.

/------------------------------------------------------------------------------*
B-INPUT-PROCESS                                  SECTION.
*------------------------------------------------------------------------------*
* Purpose    : controling the database reading                                 *
* Description:                                                                 *
*            - begining of the proceduere                                      *
*            - loop1                                                           *
*                 - beging of customer's handling                              *
*                 - loop2                                                      *
*                      - handling record : releasing to sort file              *
*                      - bringing next product                                 *
*                 - bringing next customer                                     *
*                                                                              *
*------------------------------------------------------------------------------*
B-00.
     PERFORM BA-INIT-INPUT-PROCESS  
     PERFORM UNTIL SW02-END-DB    
            PERFORM BB-BEGIN-CUST  
            PERFORM UNTIL SW02-END-CUSTOMER
                  PERFORM BC-INPUT-REC-HANDLING  
                  PERFORM BD-INPUT-READ-DB-SEG
            END-PERFORM
            PERFORM BE-GET-NEXT-CUST 
     END-PERFORM.

B-EXIT.
     EXIT.

/------------------------------------------------------------------------------*
BA-INIT-INPUT-PROCESS                            SECTION.
*------------------------------------------------------------------------------*
* Purpose    :  beginning  the input procedure                                 *
* Description:                                                                 *
*           - bringing the first cusatomer                                     *
*           - checking if data base is empty                                   *
*------------------------------------------------------------------------------*
BA-00.

     PERFORM BE-GET-NEXT-CUST
     IF SW02-END-DB    
        SET SW07-DB-EMPTY IN SW07-DB-EMPTYNESS TO TRUE
     END-IF.

BA-EXIT.
     EXIT.
/------------------------------------------------------------------------------*
BB-BEGIN-CUST                                    SECTION.
*------------------------------------------------------------------------------*
* Purpose    :  beginning customer's handling                                  *
* Description:                                                                 *
*            - location of the customer's first product                        *
*------------------------------------------------------------------------------*
BB-00.
      SET SW02-NOBREAK  IN  SW02-INPUT-BREAK                          TO  TRUE
      MOVE ALL SPACES   TO  PRODUCT-CODE      IN  DB03-PRODUCT

      CALL 'DBSEG_FIND' USING  DB-NO
                               DB-RCB
                               PRODUCT-KEY    IN DB03-PRODUCT
                               DB-STAT.                        

      IF        NOT DB__NORMAL IN DB-STAT
            AND NOT DB__NOREC  IN DB-STAT

           SET SW06-DBSEG-FIND-ERROR          IN   SW06-ERROR-STATUS  TO  TRUE
           PERFORM X-HANDLE-ERRORS

      END-IF

      PERFORM BD-INPUT-READ-DB-SEG.      

BB-EXIT.
     EXIT.
/------------------------------------------------------------------------------*
BC-INPUT-REC-HANDLING                            SECTION.
*------------------------------------------------------------------------------*
* Purpose    : releasing records to the sort file                              *
* Description:                                                                 *
*------------------------------------------------------------------------------*
BC-00.
      MOVE CUSTOMER-ID  IN DB02-CUSTOMER TO SO-CUSTOMER-NUMBER
      MOVE PRODUCT-CODE IN DB03-PRODUCT  TO SO-PRODUCT-CODE 
      RELEASE  SO-REC.

BC-EXIT.
     EXIT.
/------------------------------------------------------------------------------*
BD-INPUT-READ-DB-SEG                             SECTION.
*------------------------------------------------------------------------------*
* Purpose    :  getting the next customer's product                            *
* Description:                                                                 *
*------------------------------------------------------------------------------*
BD-00.
      CALL 'DBSEG_NEXT'   USING    DB-NO
                                   DB-RCB
                                   DB-OPERATION-TYPE
                                   DB03-PRODUCT
                                   DB-STAT.


      IF NOT DB__NORMAL IN DB-STAT
           IF NOT DB__ENDTYPE_SEG
               SET  SW06-DBSEG-NEXT-ERROR  IN  SW06-ERROR-STATUS  TO  TRUE
               PERFORM X-HANDLE-ERRORS
           ELSE
               SET SW02-END-CUSTOMER       IN  SW02-INPUT-BREAK   TO  TRUE
           END-IF
      ELSE
           ADD 1 TO CN01-PRODUCTS-READ
      END-IF.

BD-EXIT.
     EXIT.
/------------------------------------------------------------------------------*
BE-GET-NEXT-CUST                                 SECTION.
*------------------------------------------------------------------------------*
* Purpose    :  bringing the next customer's record                            *
* Description:                                                                 *
*            - counting the number of customer read from data base             *
*------------------------------------------------------------------------------*
BE-00.
    CALL 'DBKER_NEXT'  USING DB-NO
                             DB-RCB
                             DB-UPDATE-IND
                             DB02-CUSTOMER
                             DB-STAT.

    IF DB__NORMAL IN DB-STAT

       ADD 1 TO   CN02-CUSTOMERS-READ      

    ELSE

       IF DB__ENDFILE IN DB-STAT

            SET SW02-END-DB           IN  SW02-INPUT-BREAK    TO TRUE

       ELSE

          SET  SW06-DBKER-NEXT-ERROR  IN   SW06-ERROR-STATUS  TO TRUE
          PERFORM X-HANDLE-ERRORS

       END-IF

    END-IF

    PERFORM BF-COUNT-MSG.  

BE-EXIT.
     EXIT.

/------------------------------------------------------------------------------*
BF-COUNT-MSG                                     SECTION.
*------------------------------------------------------------------------------*
* Purpose    : displaying the number of customers records read during run time *
* Description:                                                                 *
*           - displaying the message every five customers read                 *
*------------------------------------------------------------------------------*
BF-00.

    DIVIDE   CN02-CUSTOMERS-READ  BY 5  GIVING    CN05-QUOTIENT 
                                        REMAINDER CN06-REMAINDER
    
    IF CN06-REMAINDER = 0 

         MOVE 000000030                        TO OPMSG-MSG-NO
         MOVE CN02-CUSTOMERS-READ              TO MSG-PARAM-TXT(1)
         MOVE 10                               TO MSG-PARAM-LEN(1)
         PERFORM Y-DISPLAY-MSG

    END-IF.
     
BF-EXIT.
     EXIT.
/------------------------------------------------------------------------------*
C-OUTPUT-PROCESS                                 SECTION.
*------------------------------------------------------------------------------*
* Purpose    : controling the output procedure flow                            *
* Description:                                                                 *
*           - begining  of the procedure                                       *
*           - loop1                                                            *
*                - begining of product's handling                              *
*                - loop2                                                       *
*                     - record's handling                                      *
*                     - reading sort file                                      *
*                     - checking break                                         *
*                - ending product                                              *
*           - ending the output  procedure                                     *
*------------------------------------------------------------------------------*
C-00.
     PERFORM CA-BEGIN-OUTPUT-PROCESS 
     PERFORM UNTIL SW01-END-OF-SORT-FILE 
          PERFORM CB-BEGIN-PRODUCT    
          PERFORM UNTIL SW01-END-OF-PRODUCT
               PERFORM CC-REC-HANDLE  
               PERFORM CD-REC-READING  
               PERFORM CE-CHECK-BREAK
          END-PERFORM
          PERFORM CF-END-PRD  
     END-PERFORM
     PERFORM CG-END-OUTPUT-PROCESS.

C-EXIT.
     EXIT.
/------------------------------------------------------------------------------*
CA-BEGIN-OUTPUT-PROCESS                          SECTION.
*------------------------------------------------------------------------------*
* Purpose    : begining of the output process                                  *
* Description:                                                                 *
*           - writing report's headers                                         *
*           - writing a message to the report if db is empty                   *
*------------------------------------------------------------------------------*
CA-00.
      INITIATE   OR-REPORT-REC

      IF  SW08-OR-IO-ERROR                    IN  SW08-OR-IO-STATUS   
           SET  SW06-WRITING-REPORT-ERROR     IN  SW06-ERROR-STATUS  TO  TRUE
           PERFORM X-HANDLE-ERRORS
      END-IF

*--displaying message if db is empty else a blanck line---

      IF  SW07-DB-EMPTY
          GENERATE L30-EMPTY-DB
          IF  SW08-OR-IO-ERROR                IN  SW08-OR-IO-STATUS 
              SET  SW06-WRITING-REPORT-ERROR  IN  SW06-ERROR-STATUS  TO  TRUE
              PERFORM X-HANDLE-ERRORS
          END-IF     
      ELSE 
          GENERATE L31-DUMMY
          IF  SW08-OR-IO-ERROR                IN  SW08-OR-IO-STATUS 
             SET  SW06-WRITING-REPORT-ERROR   IN  SW06-ERROR-STATUS  TO  TRUE
             PERFORM X-HANDLE-ERRORS
          END-IF     
      END-IF         

      PERFORM    CD-REC-READING. 

CA-EXIT.
     EXIT.
/------------------------------------------------------------------------------*
CB-BEGIN-PRODUCT                                 SECTION.
*------------------------------------------------------------------------------*
* Purpose    : begining of product's handling                                  *
* Description:                                                                 *
*           - initializing the number of customers for each product            *
*           - keeping in working storage the product's code                    *
*------------------------------------------------------------------------------*
CB-00.
      MOVE 0 TO CN03-CUSTOMERS-PER-PRD  
      MOVE SO-PRODUCT-CODE IN SO-REC TO WS02-PREV-PROD-CODE
      SET  SW01-NOBREAK    IN SW01-OUTPUT-BREAK  TO  TRUE.

CB-EXIT.
     EXIT.
/------------------------------------------------------------------------------*
CC-REC-HANDLE                                    SECTION.
*------------------------------------------------------------------------------*
* Purpose    : counting number of customers  for each product                  *
* Description:                                                                 *
*------------------------------------------------------------------------------*
CC-00.
      ADD 1 TO   CN03-CUSTOMERS-PER-PRD .
CC-EXIT.
     EXIT.
/------------------------------------------------------------------------------*
CD-REC-READING                                   SECTION.
*------------------------------------------------------------------------------*
* Purpose    :  reading next record from the sort file                         *
* Description:                                                                 *
*------------------------------------------------------------------------------*
CD-00.
       RETURN SO-SORT-FILE
            AT END 
                SET SW01-END-OF-SORT-FILE  IN  SW01-OUTPUT-BREAK   TO  TRUE
       END-RETURN.

CD-EXIT.
     EXIT.
/------------------------------------------------------------------------------*
CE-CHECK-BREAK                                   SECTION.
*------------------------------------------------------------------------------*
* Purpose    :  checking if a new product record has been read                 *
* Description:                                                                 *
*------------------------------------------------------------------------------*
CE-00.
      IF              WS02-PREV-PROD-CODE  NOT=   SO-PRODUCT-CODE
            AND NOT   SW01-END-OF-SORT-FILE 

            SET SW01-END-OF-PRODUCT   IN  SW01-OUTPUT-BREAK  TO TRUE 

      END-IF.

CE-EXIT.
     EXIT.
/------------------------------------------------------------------------------*
CF-END-PRD                                       SECTION.
*------------------------------------------------------------------------------*
* Purpose    : ending product's handling                                       *
* Description:                                                                 *
*           - writing to the report file                                       *
*           - summing the total number of customers with products              *
*------------------------------------------------------------------------------*
CF-00.
      GENERATE L20-CST

      IF  SW08-OR-IO-ERROR                IN  SW08-OR-IO-STATUS                                                              
          SET  SW06-WRITING-REPORT-ERROR  IN  SW06-ERROR-STATUS  TO  TRUE
          PERFORM X-HANDLE-ERRORS
      END-IF

      ADD CN03-CUSTOMERS-PER-PRD TO CN04-CUSTS-WITH-PRDS.     

CF-EXIT.
     EXIT.
/------------------------------------------------------------------------------*
CG-END-OUTPUT-PROCESS                            SECTION.
*------------------------------------------------------------------------------*
* Purpose    :  terminating the output procedure                               *
* Description:                                                                 *
*           - writing the report's footer                                      *
*           - closing report                                                   *
*------------------------------------------------------------------------------*
CG-00.
      TERMINATE   OR-REPORT-REC

      IF  SW08-OR-IO-ERROR                IN  SW08-OR-IO-STATUS 
          SET  SW06-WRITING-REPORT-ERROR  IN  SW06-ERROR-STATUS  TO  TRUE
          PERFORM X-HANDLE-ERRORS
      END-IF
 
      CLOSE OR-REPORT-FILE
      IF  SW08-OR-IO-ERROR                IN  SW08-OR-IO-STATUS                                                            
          SET  SW06-CLOSE-REPORT-ERROR    IN  SW06-ERROR-STATUS  TO  TRUE
          PERFORM X-HANDLE-ERRORS
      END-IF.
      
CG-EXIT.
     EXIT.
/------------------------------------------------------------------------------*
W-INIT-PROGRAM                                   SECTION.
*------------------------------------------------------------------------------*
* Purpose    : beging of the program                                           *
* Description:                                                                 *
*           - getting the current date from the system                         *
*           - displaying opening message                                       *
*           - opening report file                                              *
*           - opening data base                                                *
*           - initializing customer's and product's records                    *
*------------------------------------------------------------------------------*
W-00.
      ACCEPT WS03-CURRENT-DATE FROM DATE 
      PERFORM WA-BEGINNING-MSG
      PERFORM WB-OPEN-REPORT-FILE 
      PERFORM WC-OPEN-DB
      CALL 'GN-INIDEF-C'   USING DB02-CUSTOMER
      CALL 'GN-INIDEF-P'   USING  DB03-PRODUCT.

W-EXIT.
     EXIT.
/------------------------------------------------------------------------------*
WA-BEGINNING-MSG                                 SECTION.
*------------------------------------------------------------------------------*
* Purpose    : opening log file and displaying opening message                 *
* Description:                                                                 *
*------------------------------------------------------------------------------*
WA-00.

*--opening log file---

    SET OP-OPEN IN OPMSG-VERB        TO TRUE
    PERFORM Y-DISPLAY-MSG

    SET OP-REGULAR IN OPMSG-MSG-TYPE TO TRUE
    SET OP-UPDATE  IN OPMSG-VERB     TO TRUE

    MOVE 000000000                   TO OPMSG-MSG-NO.
    MOVE ""                          TO OPMSG-TEXT.
    PERFORM Y-DISPLAY-MSG.

*--running message---

    MOVE 000000001                   TO OPMSG-MSG-NO
    MOVE "PROJECT 2"                 TO MSG-PARAM-TXT(1).
    MOVE 10                          TO MSG-PARAM-LEN(1).
    PERFORM Y-DISPLAY-MSG.
      
WA-EXIT.
     EXIT.
/------------------------------------------------------------------------------*
WB-OPEN-REPORT-FILE                              SECTION.
*------------------------------------------------------------------------------*
* Purpose    :  opening report file and setting opening switch                 *
* Description:                                                                 *
*------------------------------------------------------------------------------*
WB-00.
       OPEN  OUTPUT OR-REPORT-FILE 
       IF  SW08-OR-IO-ERROR                IN  SW08-OR-IO-STATUS 
              SET  SW06-OPEN-REPORT-ERROR  IN  SW06-ERROR-STATUS       TO TRUE
              PERFORM X-HANDLE-ERRORS
       END-IF
       SET   SW03-OR-REPORT-OPEN           IN  SW03-OR-REPORT-STATUS   TO  TRUE.

WB-EXIT.
     EXIT.
/------------------------------------------------------------------------------*
WC-OPEN-DB                                       SECTION.
*------------------------------------------------------------------------------*
* Purpose    :   opening data base                                             *
* Description:                                                                 *
*           - setting DB arguments                                             *
*           - finding data base default name                                   *
*           - opening DB                                                       *
*           - setting open switch                                              *
*------------------------------------------------------------------------------*
WC-00.
    SET OPEN-BATCH-READ-ONLY           IN DB-OPEN-TYPE        TO  TRUE
    SET DB-EXPAND                      IN DB-EC-IND           TO  TRUE
    SET DB-ANNEX-NOT-REQUIRED          IN DB-ANNEX-IND        TO  TRUE
    SET DB-RETRIEVE                    IN DB-UPDATE-IND       TO  TRUE
    SET DB-SUMMARY                     IN DB-OPERATION-TYPE   TO  TRUE
    SET PRODUCT-SEG-TYPE               IN PRODUCT-KEY         TO  TRUE

*  ----  find default_db name  ----

    CALL 'GN_NAME'  USING  DB-NAME
                           DB-PREFIX-NAME
                           DB-NAME-STATUS

    IF NOT OP__NORMAL IN DB-NAME-STATUS

       SET  SW06-GN-NAME-ERROR         IN  SW06-ERROR-STATUS  TO  TRUE
       PERFORM X-HANDLE-ERRORS

    END-IF

*  ----  open data base  ----

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

       SET SW04-DB-OPEN                IN  SW04-DB-STATUS     TO TRUE    

    ELSE

       SET SW06-OPEN-DB-ERROR          IN  SW06-ERROR-STATUS  TO  TRUE
       PERFORM X-HANDLE-ERRORS

    END-IF. 

WC-EXIT.
     EXIT.
/------------------------------------------------------------------------------*
X-HANDLE-ERRORS                                  SECTION.
*------------------------------------------------------------------------------*
* Purpose    :  dispalying proper error message and terminating program        *
* Description:                                                                 *
*           - displaying an error message by using Aurec's standard            *
*             message file                                                     *
*                                                                              *
*------------------------------------------------------------------------------*
X-00.
     GO TO 
             X-01-OPEN-REPORT-ERROR                                
             X-02-CLOSE-REPORT-ERROR                               
             X-03-WRITING-REPORT-ERROR                             
             X-04-GN-NAME-ERROR                                    
             X-05-OPEN-DB-ERROR                                    
             X-06-DBKER-NEXT-ERROR                                 
             X-07-DBSEG-FIND-ERROR                                 
             X-08-DBSEG-NEXT-ERROR                                 

                               DEPENDING ON SW06-ERROR-STATUS.

X-00-NO-MESSAGE.

    GO TO X-EXIT.

X-01-OPEN-REPORT-ERROR.                                

    MOVE 000000024                        TO OPMSG-MSG-NO
    MOVE 'REPORT FIL'                     TO MSG-PARAM-TXT(1)
    MOVE 'E'                              TO MSG-PARAM-TXT(2)
    MOVE WS01-OR-FILE-STATUS              TO MSG-PARAM-TXT(3)
    MOVE 10                               TO MSG-PARAM-LEN(1)
    MOVE 1                                TO MSG-PARAM-LEN(2)
    MOVE 10                               TO MSG-PARAM-LEN(3)
    PERFORM Y-DISPLAY-MSG
    GO TO X-STOP.

X-02-CLOSE-REPORT-ERROR.

    MOVE 000000026                        TO OPMSG-MSG-NO
    MOVE 'REPORT FIL'                     TO MSG-PARAM-TXT(1)
    MOVE 'E'                              TO MSG-PARAM-TXT(2)
    MOVE WS01-OR-FILE-STATUS              TO MSG-PARAM-TXT(3)
    MOVE 10                               TO MSG-PARAM-LEN(1)
    MOVE 1                                TO MSG-PARAM-LEN(2)
    MOVE 10                               TO MSG-PARAM-LEN(3)
    PERFORM Y-DISPLAY-MSG
    GO TO X-STOP.
 
X-03-WRITING-REPORT-ERROR .

    MOVE 000000006                        TO OPMSG-MSG-NO
    MOVE 'REPORT FIL'                     TO MSG-PARAM-TXT(1)
    MOVE 'WRITING'                        TO MSG-PARAM-TXT(2)
    MOVE WS01-OR-FILE-STATUS              TO MSG-PARAM-TXT(3)
    MOVE 10                               TO MSG-PARAM-LEN(1)
    MOVE 10                               TO MSG-PARAM-LEN(2)
    MOVE 10                               TO MSG-PARAM-LEN(3)
    PERFORM Y-DISPLAY-MSG
    GO TO X-STOP.

X-04-GN-NAME-ERROR.

    MOVE 000000004                        TO OPMSG-MSG-NO
    MOVE 'GN-NAME   '                     TO MSG-PARAM-TXT(1)
    MOVE ' '                              TO MSG-PARAM-TXT(2)
    MOVE DB-NAME-STATUS                   TO MSG-PARAM-TXT(3)
    MOVE 10                               TO MSG-PARAM-LEN(1)
    MOVE 0                                TO MSG-PARAM-LEN(2)
    MOVE 10                               TO MSG-PARAM-LEN(3)
    PERFORM Y-DISPLAY-MSG
    GO TO X-STOP.

X-05-OPEN-DB-ERROR.

    MOVE 000000004                        TO OPMSG-MSG-NO
    MOVE "DBFIL_OPEN"                     TO MSG-PARAM-TXT(1)
    MOVE DB-STAT                          TO MSG-PARAM-TXT(3)
    MOVE 10                               TO MSG-PARAM-LEN(1)
    MOVE 0                                TO MSG-PARAM-LEN(2)
    MOVE 10                               TO MSG-PARAM-LEN(3)
    PERFORM Y-DISPLAY-MSG
    GO TO X-STOP.

X-06-DBKER-NEXT-ERROR.

    MOVE 000000004                        TO OPMSG-MSG-NO
    MOVE 'DBKER-NEXT'                     TO MSG-PARAM-TXT(1)
    MOVE DB-STAT                          TO MSG-PARAM-TXT(3)
    MOVE 10                               TO MSG-PARAM-LEN(1)
    MOVE 0                                TO MSG-PARAM-LEN(2)
    MOVE 10                               TO MSG-PARAM-LEN(3)
    PERFORM Y-DISPLAY-MSG

    MOVE 000000010                        TO OPMSG-MSG-NO
    MOVE CUSTOMER-ID IN DB02-CUSTOMER     TO MSG-PARAM-TXT(1)
    MOVE 10                               TO MSG-PARAM-LEN(1)
    PERFORM Y-DISPLAY-MSG
    GO TO X-STOP.
    
X-07-DBSEG-FIND-ERROR.

    MOVE 000000004                        TO OPMSG-MSG-NO
    MOVE 'DBSEG-FIND'                     TO MSG-PARAM-TXT(1)
    MOVE DB-STAT                          TO MSG-PARAM-TXT(3)
    MOVE 10                               TO MSG-PARAM-LEN(1)
    MOVE 0                                TO MSG-PARAM-LEN(2)
    MOVE 10                               TO MSG-PARAM-LEN(3)
    PERFORM Y-DISPLAY-MSG

    MOVE 000000010                        TO OPMSG-MSG-NO
    MOVE CUSTOMER-ID IN DB02-CUSTOMER     TO MSG-PARAM-TXT(1)
    MOVE 10                               TO MSG-PARAM-LEN(1)
    PERFORM Y-DISPLAY-MSG
    GO TO X-STOP.
    
X-08-DBSEG-NEXT-ERROR.
   
    MOVE 000000004                        TO OPMSG-MSG-NO
    MOVE 'DBSEG-NEXT'                     TO MSG-PARAM-TXT(1)
    MOVE DB-STAT                          TO MSG-PARAM-TXT(3)
    MOVE 10                               TO MSG-PARAM-LEN(1)
    MOVE 0                                TO MSG-PARAM-LEN(2)
    MOVE 10                               TO MSG-PARAM-LEN(3)
    PERFORM Y-DISPLAY-MSG

    MOVE 000000010                        TO OPMSG-MSG-NO
    MOVE CUSTOMER-ID IN DB02-CUSTOMER     TO MSG-PARAM-TXT(1)
    MOVE 10                               TO MSG-PARAM-LEN(1)
    PERFORM Y-DISPLAY-MSG
    GO TO X-STOP.

X-STOP.
    
    SET SW05-PROGRAM-END-ERROR  IN SW05-PROGRAM-STATUS    TO TRUE
    PERFORM Z-FINISH-PROGRAM.
 
X-EXIT.
     EXIT.
/------------------------------------------------------------------------------*
Y-DISPLAY-MSG                                    SECTION.
*------------------------------------------------------------------------------*
* Purpose    :  displaying  messages                                           *
* Description:                                                                 *
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

/------------------------------------------------------------------------------*
Z-FINISH-PROGRAM                                 SECTION.
*------------------------------------------------------------------------------*
* Purpose    : terminating the program                                         *
* Description:                                                                 *
*------------------------------------------------------------------------------*
Z-00.
     PERFORM ZA-CLOSE-DB 
     PERFORM ZB-FINAL-MSG
     CALL 'SYS$EXIT'  USING BY VALUE SW05-PROGRAM-STATUS.

Z-EXIT.
     EXIT.

*------------------------------------------------------------------------------*
ZA-CLOSE-DB                                      SECTION.
*------------------------------------------------------------------------------*
* Purpose    : closing database                                                *
* Description:                                                                 *
*           - closing the database if it is open                               *
*------------------------------------------------------------------------------*
ZA-00.
     IF  SW04-DB-OPEN IN SW04-DB-STATUS

         CALL 'DBFIL-CLOSE' USING  DB-NO
                                   DB-STAT

         IF NOT DB__NORMAL IN DB-STAT
             MOVE 000000004                        TO OPMSG-MSG-NO
             MOVE "DBFIL_CLOS"                     TO MSG-PARAM-TXT(1)
             MOVE "E"                              TO MSG-PARAM-TXT(2) 
             MOVE DB-STAT                          TO MSG-PARAM-TXT(3)
             MOVE 10                               TO MSG-PARAM-LEN(1)
             MOVE 1                                TO MSG-PARAM-LEN(2)
             MOVE 10                               TO MSG-PARAM-LEN(3)
             PERFORM Y-DISPLAY-MSG
             SET SW05-PROGRAM-END-ERROR IN  SW05-PROGRAM-STATUS  TO TRUE
         END-IF
     END-IF.
  
ZA-EXIT.
     EXIT.

*------------------------------------------------------------------------------*
ZB-FINAL-MSG                                     SECTION.
*------------------------------------------------------------------------------*
* Purpose    : displaying final message and closing log file                   *
* Description:                                                                 *
*           - displaying a message according to the program's status           *
*------------------------------------------------------------------------------*
ZB-00.

*--displaying the number of customers read from db---

     MOVE 000000030                        TO OPMSG-MSG-NO
     MOVE CN02-CUSTOMERS-READ              TO MSG-PARAM-TXT(1)
     MOVE 10                               TO MSG-PARAM-LEN(1)
     PERFORM Y-DISPLAY-MSG

*--displaying termination message---

     IF SW05-PROGRAM-END-SUCCESSFULY  IN   SW05-PROGRAM-STATUS
          MOVE 000000002                   TO OPMSG-MSG-NO
          MOVE "PROJECT 2"                 TO MSG-PARAM-TXT(1)
          MOVE 10                          TO MSG-PARAM-LEN(1)
          PERFORM Y-DISPLAY-MSG
     ELSE
          MOVE 000000003                   TO OPMSG-MSG-NO
          MOVE "PROJECT 2"                 TO MSG-PARAM-TXT(1)
          MOVE 10                          TO MSG-PARAM-LEN(1)
          PERFORM Y-DISPLAY-MSG
     END-IF

*--closing log file---

     SET OP-CLOSE                     IN   OPMSG-VERB  TO TRUE
     PERFORM Y-DISPLAY-MSG.

ZB-EXIT.
     EXIT.
