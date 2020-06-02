/*****************************************************************************
IDENTIFICATION                                     DIVISION.
***********************************************************************************
PROGRAM-ID.             TARGIL-15.
AUTHOR.                 ODED.
DATE-WRITTEN.           06/03/93.
DATE-COMPILED.          07/03/93.
*********************************************************************************8
        FLOW CHART FOR THE MAIN PROGRAM !
********************************************************************************888
*
*       ------------------------------------------------------------------
*      |                             |                  |                |
*      |                             |                  |                |
*      |                             |                  |                |
* -----------                     --------      --------------  -------------
*| B-INIT    |                   | SW-EOF |    | RC-END-BANK  | |Z-FINNISH   |
* -----------                     --------      --------------  -------------
*                                    |
*                                    |
*                                    |
*      ----------------------------------------------------------
*      |                             |                          |
*      |                             |                          |
*      |                             |                          |
*  ----------------        ----------------              ---------------
* |BB-START-BRANCH |       | SW-END-BRANCH |            | RB-END-BRANCH |
*  ----------------        | SW-EOF        |             ---------------
*                           ---------------
*                                     |
*                                     |
*                                     |
*     ----------------------------------------------------------
*    |                                |                         |
*    |                                |                         |
*  ------------------     -----------------             ----------------
* | BB-START-ACCOUNT |   | SW-END-ACCOUNT  |           | RA-END-ACCOUNT |
*  ----------------- -   | SW-END-BRANCH   |            ---------------
*                        | SW-EOF          |
*                         -----------------
*                                     |
*                                     |
*                                     |
*                   ------------------------------------
*                   |                                  |
*                   |                                  |
*            -----------                        ------------ 
*           | C-PROCESS |                      | D-READ     |
*            -----------                        ------------
*
/**********************************************************************************

*********************************************************************************
ENVIRONMENT                                        DIVISION.
*******************************************************************************
*------------------------------------------------------------------------------*
 INPUT-OUTPUT                                    SECTION.
*------------------------------------------------------------------------------*
* Purpose    :                                                                 *
* Description:                                                                 *
*------------------------------------------------------------------------------*
FILE-CONTROL.

             SELECT KM-DEPOSITES
             ASSIGN TO "KURS$USER:[AKURS7]DEPOSITES.DAT"
             ORGANIZATION IS SEQUENTIAL 
             FILE STATUS IS D-FILE-STATUS.

             SELECT RP-DEPOSITES
             ASSIGN TO "DEPOSITES.REP"
             FILE STATUS IS R-FILE-STATUS.
             
/*******************************************************************************8888888
DATA                                               DIVISION.
******************************************************************************
*
*------------------------------------------------------------------------------*
 FILE                                            SECTION.
*------------------------------------------------------------------------------*
* Purpose    :                                                                 *
* Description:                                                                 *
*------------------------------------------------------------------------------*
* INPUT FILE 
FD KM-DEPOSITES.
01 K-DEPOSIT.
   03 K-BRANCH    PIC 9(2).
   03 K-NUMBER    PIC 9(6).
   03 K-DAY      PIC 99.
   03 K-SUM       PIC 9(7).

*  REPORT FILE

FD RP-DEPOSITES
   LINAGE IS 62 LINES
   WITH FOOTING AT 59
   LINES AT TOP    2
   LINES AT BOTTOM  2.
01 RP-LINE PIC X(80).
/------------------------------------------------------------------------------*
 WORKING-STORAGE                                 SECTION.
*------------------------------------------------------------------------------*
* Purpose    :                                                                 *
* Description:                                                                 *
*------------------------------------------------------------------------------*
01   DEPOSITES.

*    TEMPORARY VARS FOR COMPUTAION 

     03 PREV-NUM         PIC 9(6).
     03 PREV-BRANCH      PIC 99.
     03 ACCOUNT-TOT            PIC 9(4) COMP.
     03 BRANCH-TOT             PIC 9(4) COMP.
     03 BANK-TOT               PIC 9(4) COMP.  
*    FILE STATUS VARIABLES
 
     03 D-FILE-STATUS    PIC XX.
        88 D-GOOD-STATUS    VALUE "00".
     03 R-FILE-STATUS    PIC XX.
        88 R-GOOD-STATUS   VALUE "00".

*      BREAKPOINT SWITCHES
  
     03 SW           PIC 9.   
        88 SW-EOF      VALUE 1.
        88 SW-END-BRANCH   VALUE 3.
        88 SW-END-ACCOUNT  VALUE 2.

*    TABLE INDEXES !
     03 TB-I             PIC 99  COMP.
     03 TB-J             PIC 99  COMP.    

*    DISTRIBUTION  TABLE 

     03 TB-DAYS.
        05 TB-LEVELS        OCCURS 3.
           07 TB-DAY       OCCURS 31.
            09 TB-SUM        PIC 9(8) COMP.
COPY  "REPORT.LIB".
/************************************************************************************
PROCEDURE                                       DIVISION.
***********************************************************************************
*------------------------------------------------------------------------------*
 A-MAIN-PROG                                     SECTION.
*------------------------------------------------------------------------------*
* Purpose    :                                                                 *
* Description:                                                                 *
*------------------------------------------------------------------------------*
A00.
         PERFORM B-INIT
*         EOF LOOP
                PERFORM UNTIL SW-EOF 

                   PERFORM BB-START-BRANCH
*        BRANCH LOOP
                  PERFORM UNTIL SW-END-BRANCH   OR SW-EOF 
                     PERFORM BA-START-ACCOUNT
*      ACCOUNT LOOP
                PERFORM UNTIL SW-END-ACCOUNT  OR SW-END-BRANCH OR  SW-EOF 
                   PERFORM C-PROCESS
                   PERFORM D-READ
                END-PERFORM
*  END ACCOUNT LOOP
                PERFORM RA-END-ACCOUNT
               END-PERFORM
*  END BRANCH      LOOP
                PERFORM RB-END-BRANCH
                 END-PERFORM
*   END  FILE LOOP
                PERFORM RC-END-BANK
                PERFORM Z-FINNISH

                 STOP RUN.
A-EXIT.
     EXIT.
/------------------------------------------------------------------------------*
 B-INIT                                              SECTION.
*------------------------------------------------------------------------------*
* Purpose    :                                                                 *
* Description:                                                                 *
*------------------------------------------------------------------------------*
B00.
               MOVE 0 TO SW
               OPEN INPUT KM-DEPOSITES

                  IF NOT D-GOOD-STATUS
                     DISPLAY " DATA FILE MISSING !"
                     STOP RUN
                  END-IF
               OPEN OUTPUT RP-DEPOSITES

                  IF NOT R-GOOD-STATUS
                     DISPLAY " ERROR OPENING REPORT FILE ! "
                     STOP RUN
                  END-IF

               READ KM-DEPOSITES
               AT END
                  MOVE 1 TO SW
               END-READ
               INITIALIZE TB-DAYS
                      .

B-EXIT.
     EXIT.
/------------------------------------------------------------------------------*
 BB-START-BRANCH                                 SECTION.
*------------------------------------------------------------------------------*
* Purpose    :                                                                 *
* Description:                                                                 *
*------------------------------------------------------------------------------*
BB00.

            MOVE 0 TO SW
            MOVE 0 TO BRANCH-TOT
            PERFORM VARYING TB-J FROM 1 BY 1 UNTIL TB-J > 31
               MOVE 0 TO TB-SUM(2 , TB-J)
            END-PERFORM

            MOVE K-BRANCH TO PREV-BRANCH

                  MOVE PREV-BRANCH TO RP-BRANCH-NO
                  WRITE RP-LINE FROM RP-BRANCH-HEADER
                  AFTER ADVANCING 2
                  MOVE ALL "*" TO RP-LINE 
                  WRITE RP-LINE
            
                    .
BB-EXIT.
     EXIT.
/------------------------------------------------------------------------------*
 BA-START-ACCOUNT                                SECTION.
*------------------------------------------------------------------------------*
* Purpose    :                                                                 *
* Description:                                                                 *
*------------------------------------------------------------------------------*
BA00.
                  MOVE 0 TO ACCOUNT-TOT
                  MOVE 0 TO SW
                    MOVE  1 TO TB-I
                 PERFORM VARYING TB-J FROM 1 BY 1 UNTIL TB-J > 31
                     MOVE 0 TO TB-SUM(TB-I , TB-J)
                 END-PERFORM

                    MOVE K-NUMBER TO PREV-NUM  
                     MOVE PREV-NUM TO RP-ACCOUNT-NO
                     WRITE RP-LINE FROM RP-ACCOUNT-HEADER  
                     AFTER ADVANCING 2
                     WRITE RP-LINE FROM RP-REC-HEADER
                     MOVE ALL "*"    TO RP-LINE
                     MOVE PREV-NUM TO RP-ACCOUNT-NO
                     WRITE RP-LINE    
                         
                         .

BA-EXIT.
     EXIT.
/------------------------------------------------------------------------------*
 C-PROCESS                                       SECTION.
*------------------------------------------------------------------------------*
* Purpose    :                                                                 *
* Description:                                                                 *
*------------------------------------------------------------------------------*
C00.
                  MOVE K-DAY TO TB-J
                  ADD K-SUM TO TB-SUM(TB-I , TB-J)
                  ADD K-SUM TO ACCOUNT-TOT
                        .

C-EXIT.
     EXIT.
/------------------------------------------------------------------------------*
 D-READ                                          SECTION.
*------------------------------------------------------------------------------*
* Purpose    :                                                                 *
* Description:                                                                 *
*------------------------------------------------------------------------------*
D00.

              READ KM-DEPOSITES
              AT END
                     MOVE 1 TO SW
              NOT AT END
                   IF K-NUMBER NOT = PREV-NUM
                        MOVE 2 TO SW
                   END-IF
                    
                   IF K-BRANCH NOT = PREV-BRANCH 
                       MOVE 3 TO SW
                   END-IF               
                  MOVE K-NUMBER TO PREV-NUM
                  MOVE K-BRANCH TO PREV-BRANCH

              END-READ
                          .

D-EXIT.
     EXIT.

/------------------------------------------------------------------------------*
 E-SUMATION                                      SECTION.
*------------------------------------------------------------------------------*
* Purpose    :                                                                 *
* Description:                                                                 *
*------------------------------------------------------------------------------*
E00.
            PERFORM VARYING TB-J FROM 1 BY 1 UNTIL TB-J > 31
            IF TB-SUM(TB-I , TB-J) > 0
            ADD TB-SUM(TB-I , TB-J) TO TB-SUM(TB-I + 1 , TB-J)
            END-IF
            END-PERFORM     .
E-EXIT.
     EXIT.
/------------------------------------------------------------------------------*
 Z-FINNISH                                      SECTION.
*------------------------------------------------------------------------------*
* Purpose    :                                                                 *
* Description:                                                                 *
*------------------------------------------------------------------------------*
Z00.
              CLOSE KM-DEPOSITES
              CLOSE RP-DEPOSITES
                      .
Z-EXIT.
     EXIT.
/------------------------------------------------------------------------------*
 RA-END-ACCOUNT                                  SECTION.
*------------------------------------------------------------------------------*
* Purpose    :                                                                 *
* Description:                                                                 *
*------------------------------------------------------------------------------*
RA00.
                    MOVE 1 TO TB-I
                    PERFORM E-SUMATION
                    ADD ACCOUNT-TOT TO BRANCH-TOT
                    PERFORM P-PRINT-RECS
                    MOVE ACCOUNT-TOT TO RP-TOTAL
                    MOVE ALL "*" TO RP-LINE
                    WRITE RP-LINE AFTER ADVANCING 2
                    MOVE ACCOUNT-TOT TO RP-TOTAL
                    WRITE RP-LINE FROM RP-FOOTER 
                        .
RA-EXIT.
     EXIT.
/------------------------------------------------------------------------------*
 RB-END-BRANCH                                   SECTION.
*------------------------------------------------------------------------------*
* Purpose    :                                                                 *
* Description:                                                                 *
*------------------------------------------------------------------------------*
RB00.
                    MOVE 2 TO TB-I
                    PERFORM E-SUMATION
                    ADD BRANCH-TOT TO BANK-TOT
                    WRITE RP-LINE FROM RP-BRANCH-HEADER AFTER ADVANCING 2
                    MOVE ALL "*" TO RP-LINE 
                    WRITE RP-LINE  
                    WRITE RP-LINE FROM RP-REC-HEADER
                    MOVE ALL "*" TO RP-LINE 
                    WRITE RP-LINE  
                    
                    PERFORM P-PRINT-RECS
                    MOVE ALL "*" TO RP-LINE
                    WRITE RP-LINE AFTER ADVANCING 2
                    MOVE BRANCH-TOT TO RP-TOTAL
                    WRITE RP-LINE FROM RP-FOOTER 
                             .
RB-EXIT.
     EXIT.
/------------------------------------------------------------------------------*
 RC-END-BANK                                     SECTION.
*------------------------------------------------------------------------------*
* Purpose    :                                                                 *
* Description:                                                                 *
*------------------------------------------------------------------------------*
RC00.
                     MOVE 3 TO TB-I
                     WRITE RP-LINE FROM RP-BANK-HEADER AFTER ADVANCING 2
                     MOVE ALL "*" TO RP-LINE
                     WRITE RP-LINE AFTER ADVANCING 2 LINES
                     WRITE RP-LINE FROM RP-REC-HEADER
                     MOVE ALL "*" TO RP-LINE
                     WRITE RP-LINE 

                     PERFORM P-PRINT-RECS
                     MOVE ALL "*" TO RP-LINE
                     WRITE RP-LINE AFTER ADVANCING 2
                     MOVE BANK-TOT TO RP-TOTAL
                     WRITE RP-LINE FROM RP-FOOTER 
                     AFTER ADVANCING 2
                         .

RC-EXIT.
     EXIT.
/------------------------------------------------------------------------------*
 P-PRINT-RECS                                    SECTION.
*------------------------------------------------------------------------------*
* Purpose    :                                                                 *
* Description:                                                                 *
*------------------------------------------------------------------------------*
P00.
              PERFORM VARYING TB-J FROM 1 BY 1 UNTIL TB-J > 31
               IF TB-SUM(TB-I , TB-J) > 0
                           MOVE TB-J TO RP-DAY
                           MOVE TB-SUM(TB-I , TB-J) TO RP-SUM
                           WRITE RP-LINE FROM RP-REC-LINE
                           AT EOP
                           WRITE RP-LINE FROM RP-REC-HEADER 
                           AFTER ADVANCING PAGE
                           MOVE ALL "*" TO RP-LINE
                           WRITE RP-LINE
                           END-WRITE
               END-IF       
               END-PERFORM
                             .
P-EXIT.
     EXIT.
 
