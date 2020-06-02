*************************************************************************
IDENTIFICATION             DIVISION.
*************************************************************************
PROGRAM-ID.     TARGIL-SCREEN.
AUTHOR.         ARIEL&ODED.
DATE-WRITTEN.   21/3/93.
DATE-COMPILED.  21/3/93.
*************************************************************************
 DATA                       DIVISION.
*************************************************************************
*------------------------------------------------------------------------------*
  WORKING-STORAGE                                      SECTION.
*------------------------------------------------------------------------------*
* Purpose    :                                                                 *
* Description:                                                                 *
*------------------------------------------------------------------------------*
01 AAA.
  03  WS-KEY          PIC X(4).
  03  WS-KEY-VAL       REDEFINES WS-KEY PIC 9(8) COMP.
  03  VAL             PIC 9(4)  VALUE 0000.
      88 ENTER-KEY     VALUE  6269.
      88 PF1-KEY       VALUE  8687.
      88 PF2-KEY       VALUE  8943.
      88 PF3-KEY       VALUE  9199.
      88 PF4-KEY       VALUE  9455.
      88 TAB-KEY       VALUE  6265.
      88 UP-KEY        VALUE  4859.
      88 DOWN-KEY      VALUE  5115.
      88 RIGHT-KEY     VALUE  5371.
      88 LEFT-KEY      VALUE  5627.
  03  ERR-MSG         PIC X(35)  VALUE ALL SPACES.
      88 KEY-ERR      VALUE " INVALID KEY PRESSED ! ".

  03  I               PIC 9. 

  03  USER-HELP       PIC X(75) VALUE ALL SPACES.
      88 ID-HELP      VALUE  " ENTER  WORKERS ID NUMBER ! ".
      88 FNAME-HELP   VALUE  " ENTER WORKER'S FIRST NAME !".
      88 LNAME-HELP   VALUE  " ENTER WORKER'S LAST NAME  !".
      88 RANK-HELP    VALUE  " ENTER THE WORKER'S JOB RANK ! ".
      88 DATE-HELP    VALUE  " ENTER THE WORKER'S FIRST WORK DATE ! DD/MM/YY ".
      88 CONFIRM-HELP VALUE  " PRESS Y TO UPDATE AND EXIT         ! ".
 
01 WORKER-REC.
   03 WORKER-ID            PIC X(9).
   03 WORKER-FNAME         PIC X(10).
   03 WORKER-LNAME         PIC X(10).
   03 WORKER-RANK          PIC 9.
   03 WORKER-START-DATE    PIC X(6).
   03 WORKER-CONFIRM       PIC X.
      88 SW-END-SCREEN        VALUE "Y".
01 SW-PROG_STAT            PIC S9(6) COMP                   VALUE 1.
********************************************************************************
  PROCEDURE                                               DIVISION.
********************************************************************************
*------------------------------------------------------------------------------*
  A-MAIN-PROGRAM                                 SECTION.
*------------------------------------------------------------------------------*
* Purpose    :                                                                 *
* Description:                                                                 *
*------------------------------------------------------------------------------*
  A00.

                PERFORM B-INIT

            PERFORM UNTIL SW-END-SCREEN 
              PERFORM C-GET-INPUT
              PERFORM D-GET-ACTION
            END-PERFORM

                PERFORM Z-FINNISH.  


A-EXIT.
        EXIT.
*------------------------------------------------------------------------------*
 B-INIT                                          SECTION.
*------------------------------------------------------------------------------*
* Purpose    :                                                                 *
* Description:                                                                 *
*------------------------------------------------------------------------------*
 B-00.

         MOVE ALL SPACES TO WORKER-REC
         MOVE 1 TO I
         DISPLAY " "
                 AT LINE NUMBER 1 
                 AT COLUMN NUMBER 1
                ERASE TO END OF SCREEN

              DISPLAY   "  W O R K E R   E N T R Y  S C R E E N  !  "
                 AT LINE 2 
                 AT COLUMN 10

       DISPLAY   "1. ID NUMBER    :"
              AT LINE 5
               AT COLUMN NUMBER 10

       DISPLAY  "2. FIRST NAME   :"
                AT LINE 7
                AT COLUMN 10

       DISPLAY  "3. LAST NAME    :"
               AT LINE 9 
               AT COLUMN 10
       DISPLAY  "4. RANK         :"
               AT LINE 11
               AT COLUMN 10
       DISPLAY  "5. START DATE   :"
               AT LINE 13
               AT COLUMN 10
       DISPLAY " CONFIRM Y/N     :"
               AT LINE 18 
               AT COLUMN 50
                DISPLAY
     "ENTER - NEXT FIELD  PF1 - PREVIOUS FIELD  PF2 - HOME  PF3 - END  PF4 - NEXT"
             AT LINE 23
             AT COLUMN 5
                  .

B-EXIT.
      EXIT.

*------------------------------------------------------------------------------*
C-GET-INPUT                                      SECTION.
*------------------------------------------------------------------------------*
* Purpose    :                                                                 *
* Description:                                                                 *
*------------------------------------------------------------------------------*
C-00.

              EVALUATE I 

                WHEN 1 
                 SET  ID-HELP TO TRUE
                 DISPLAY USER-HELP 
                         AT LINE 22
                         AT COLUMN 5
                  ACCEPT WORKER-ID    WITH CONVERSION
                     CONTROL KEY IN WS-KEY
                     FROM LINE  5 
                     FROM COLUMN 28
                  END-ACCEPT

                WHEN 2
                 SET  FNAME-HELP TO TRUE
                 DISPLAY USER-HELP 
                         AT LINE 22
                         AT COLUMN 5

                  ACCEPT WORKER-FNAME   WITH CONVERSION
                  CONTROL KEY IN WS-KEY
                         FROM LINE 7
                         FROM COLUMN 28
                  END-ACCEPT

                WHEN 3
                 SET  LNAME-HELP  TO TRUE
                 DISPLAY USER-HELP 
                         AT LINE 22
                         AT COLUMN 5

                 ACCEPT WORKER-LNAME  WITH CONVERSION
                  CONTROL KEY IN WS-KEY
                         FROM LINE 9
                         FROM COLUMN 28
                 END-ACCEPT

                WHEN 4
                 SET RANK-HELP  TO TRUE
                 DISPLAY USER-HELP 
                         AT LINE 22
                         AT COLUMN 5

                 ACCEPT  WORKER-RANK  WITH CONVERSION
                  CONTROL KEY IN WS-KEY
                         FROM LINE 11
                         FROM COLUMN 28
                 END-ACCEPT

                WHEN 5 
                 SET DATE-HELP TO TRUE
                 DISPLAY USER-HELP 
                         AT LINE 22
                         AT COLUMN 5

                   ACCEPT WORKER-START-DATE WITH CONVERSION
                    CONTROL KEY IN WS-KEY
                          FROM LINE 13
                          FROM COLUMN 28
                   END-ACCEPT
                

                WHEN 6
                 SET CONFIRM-HELP TO TRUE
                 DISPLAY USER-HELP 
                         AT LINE 22
                         AT COLUMN 5

                  ACCEPT WORKER-CONFIRM  WITH CONVERSION
                  CONTROL KEY IN WS-KEY
                          FROM LINE 18
                          FROM COLUMN 68
                  END-ACCEPT

                END-EVALUATE
                       .
C-EXIT.
     EXIT.
*------------------------------------------------------------------------------*
D-GET-ACTION                                     SECTION.
*------------------------------------------------------------------------------*
* Purpose    :                                                                 *
* Description:                                                                 *
*------------------------------------------------------------------------------*
D-00.
             MOVE WS-KEY-VAL TO VAL
              MOVE ALL SPACES TO ERR-MSG
              DISPLAY ERR-MSG
                      AT LINE 24 
                      AT COLUMN 10


                EVALUATE TRUE

                WHEN ENTER-KEY

                 IF I < 6 
                  ADD 1 TO I
                 END-IF

                 IF I = 6  AND WORKER-CONFIRM = "Y" OR WORKER-CONFIRM = "y"
                  SET SW-END-SCREEN TO TRUE
                 END-IF

                WHEN PF1-KEY

                 IF I > 1  
                  SUBTRACT 1 FROM I
                   ELSE
                     MOVE 6 TO I
                 END-IF


                WHEN PF2-KEY
                  MOVE 1 TO I

                WHEN PF3-KEY
                  MOVE 6 TO I

                WHEN PF4-KEY

                  IF I < 6 
                         ADD 1 TO I
                  ELSE 
                    MOVE 1 TO I
                  END-IF

                WHEN OTHER
                     SET KEY-ERR TO TRUE 
                    DISPLAY ERR-MSG 
                            AT LINE 24
                            AT COLUMN 10
              END-EVALUATE
                      .

D-EXIT.
     EXIT.
*------------------------------------------------------------------------------*
Z-FINNISH                                        SECTION.
*------------------------------------------------------------------------------*
* Purpose    :                                                                 *
* Description:                                                                 *
*------------------------------------------------------------------------------*
Z-00.

            DISPLAY "" AT LINE 1 AT COLUMN 1
                     ERASE TO END OF SCREEN

             CALL 'SYS$EXIT'  USING BY VALUE SW-PROG-STAT
                            .
Z-EXIT.
     EXIT.
