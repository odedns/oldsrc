/*****************************************************************************
IDENTIFICATION                                     DIVISION.
*****************************************************************************
PROGRAM-ID.             TAR19.
AUTHOR.                 AMIR&ODED.
DATE-WRITTEN.           25/03/93.
DATE-COMPILED.          09/03/93.
****************************************************************************
*  TAR334AR                         -     EXERCISE No. TAR334AR
****************************************************************************
* Purpose:  Update an index file.                                          *
*           Use decision tables in oreder to produce the right reports.    *
*                                                                          *
*--------------------------------------------------------------------------*
* Program history:                                                         *
*                                                                          *
* 1) Date:           09-MAR-93                                             *
*    Programer:      AMIR & ODED                                           *
*    Supervisor:                                                           *
*                                                                          *
*    Version   :      1.0                                                  *
****************************************************************************

/***************************************************************************
ENVIRONMENT                                        DIVISION.
****************************************************************************

*------------------------------------------------------------------------------*
 INPUT-OUTPUT                                    SECTION.
*------------------------------------------------------------------------------*
* Purpose    :                                                                 *
* Description:                                                                 *
*------------------------------------------------------------------------------*
FILE-CONTROL.
*=============*

            SELECT IO-OWNERS         
            ASSIGN TO "OWNERS.DAT"
            ORGANIZATION IS SEQUENTIAL
            FILE STATUS IS WS-IO-FILE-STATUS.

            SELECT IM-MESSAGE
            ASSIGN TO "MESSAGES.DAT"
            ORGANIZATION IS SEQUENTIAL
            FILE STATUS IS WS-IM-FILE-STATUS.

            SELECT IP-PAYMENTS         
            ASSIGN TO "PAYMENTS.DAT"
            ORGANIZATION IS SEQUENTIAL
            FILE STATUS IS WS-IP-FILE-STATUS.

            SELECT R1-NO-MSG
            ASSIGN TO "NO-MSG.REP"
            ORGANIZATION IS SEQUENTIAL
            FILE STATUS IS WS-R1-FILE-STATUS.

            SELECT R2-NO-PAY         
            ASSIGN TO "R2-NO-PAY.REP"
            ORGANIZATION IS SEQUENTIAL
            FILE STATUS IS WS-R2-FILE-STATUS.

            SELECT R3-PART-PAY
            ASSIGN TO "R3-PART-PAY.REP"
            ORGANIZATION IS SEQUENTIAL
            FILE STATUS IS WS-R3-FILE-STATUS.

            SELECT R4-ERRORS
            ASSIGN TO "R4-ERRORS.REP"
            ORGANIZATION IS SEQUENTIAL
            FILE STATUS IS WS-R4-FILE-STATUS.
  


/******************************************************************************
 DATA                                                             DIVISION.
******************************************************************************
*
*------------------------------------------------------------------------------*
 FILE                                            SECTION.
*------------------------------------------------------------------------------*
* Purpose    :                                                                 *
* Description:                                                                 *
*------------------------------------------------------------------------------*
FD IO-OWNERS.
01 IO-R-FD.
   03 OWNER-ID               PIC 9(6).
   03 OWNER-NAME             PIC X(20).
   03 OWNER-ADDRESS          PIC X(30).

FD IM-MESSAGE.
01 IM-R-FD. 
   03 MESSAGE-ID             PIC 9(6).
   03 MESSAGE-NAME           PIC X(20).
   03 MESSAGE-ADDRESS        PIC X(30).

FD IP-PAYMENTS.
01 IP-R-FD.
   03 PAYMENTS-ID             PIC 9(6).
   03 PAYMENTS-NO             PIC 9.

FD R1-NO-MSG.
01 R1-R-FD.
   03  R1-ID                  PIC 9(6).
   03  R1-NAME                PIC X(20).
   03  R1-ADDRESS             PIC X(30).

FD R2-NO-PAY.
01 R2-R-FD.
   03  R2-ID                  PIC 9(6).
   03  R2-NAME                PIC X(20).
   03  R2-ADDRESS             PIC X(30).

FD R3-PART-PAY.
01 R3-R-FD.
   03  R3-ID                  PIC 9(6).
   03  R3-NAME                PIC X(20).
   03  R3-ADDRESS             PIC X(30).
   03  R3-NO-PAYS             PIC 9.

FD R4-ERRORS.
01 R4-R-FD.
   03  R4-ID                  PIC 9(6).
   03  R4-NAME                PIC X(20).
   03  R4-ADDRESS             PIC X(30).


/------------------------------------------------------------------------------*
  WORKING-STORAGE                                SECTION.
*------------------------------------------------------------------------------*
* Purpose    :                                                                 *
* Description:                                                                 *
*------------------------------------------------------------------------------
01 TB-TABLES.
   03 TB-T   OCCURS 8 INDEXED BY TB-T-I.
      05 TB-KEY              PIC 9(3).
      05 TB-ACTION           PIC 9.

   03 TB-T-X      REDEFINES  TB-T  OCCURS 8.
      05 FILLER    VALUE   "0004".
      05 FILLER    VALUE   "1001".
      05 FILLER    VALUE   "0104".
      05 FILLER    VALUE   "1102".
      05 FILLER    VALUE   "0014".
      05 FILLER    VALUE   "1014".
      05 FILLER    VALUE   "0114".
      05 FILLER    VALUE   "1113".

   03 TB-FILES.
      05 TB-FILE       OCCURS 3   INDEXED BY TB-F-I.
         07 TB-NO                PIC 9.
    
   03 TB-PAYMENTS.
      05 TB-PAYMENT   OCCURS 3 INDEXED BY TB-P-I.
         07 TB-PAY-NO            PIC 9.

   03 TB-CURR-KEY       OCCURS 3 INDEXED BY TB-C-I.
      05 TB-KEY-NUM           PIC 9.

   03 TB-IDS
      05 TB-ID               OCCURS 3 INDEXED BY TB-ID-I.
          07 TB-ID-NUM            PIC 9(6).

   03 I-MIN             PIC 9.
   03 ID-MIN            PIC 9(6).
   03 PREV-ID           PIC 9(6).

/************************************************************************************
PROCEDURE                                        DIVISION.
*------------------------------------------------------------------------------*
  A-MAIN                                         SECTION.
*------------------------------------------------------------------------------*
* Purpose    :                                                                 *
* Description:                                                                 *
*------------------------------------------------------------------------------*
A-00.
                   PERFORM B-PROG-INIT

                   PERFORM  UNTIL   TB-IDS = HIGH-VALUE
                         PERFORM C-REC-INIT

                            PERFORM UNTIL  PREV-ID NOT = 
                                        TB-ID(TB-ID-I)

                               PERFORM  D-TREAT
                               PERFORM  E-READ-ON-DUTY
                               PERFORM   F-FIND-ON-DUTY

                             END-PERFORM

                           PERFORM  G-END-REC

                    END-PERFORM

                    PERFORM Z-FINISH
                          STOP RUN.

A-EXIT.

     EXIT.
/------------------------------------------------------------------------------*
B-PROG-INIT                                           SECTION.
*------------------------------------------------------------------------------*
* Purpose    :                                                                 *
* Description:                                                                 *
*------------------------------------------------------------------------------*
B-00.

                       OPEN INPUT IO-OWNERS
                                  IM-MESSAGE
                                  IP-PAYMENTS

                       OPEN OUTPUT    R1-NO-MSG
                                      R2-NO-PAY
                                      R3-PART-PAY
                                      R4-ERRORS


                       PERFORM EA-READ-OWNERS

                       PERFORM EB-READ-MESSAGES
                       
                       PERFORM EC-READ-PAYMENTS
                       
                       PERFORM F-FIND-ON-DUTY

                                 .
                        
B-EXIT.

     EXIT.

/------------------------------------------------------------------------------*
EA-READ-OWNERS                                   SECTION.
*------------------------------------------------------------------------------*
* Purpose    :                                                                 *
* Description:                                                                 *
*------------------------------------------------------------------------------*
EA-00.

                     READ IO-OWNERS
                       AT END
                          MOVE HIGH-VALUE TO TB-ID(1)
                       NOT AT END
                          MOVE  OWNER-ID TO TB-ID(1)
                     END-READ
                                  .
EA-EXIT.
     EXIT.

/------------------------------------------------------------------------------*
EB-READ-MESSAGE                                  SECTION.
*------------------------------------------------------------------------------*
* Purpose    :                                                                 *
* Description:                                                                 *
*------------------------------------------------------------------------------*
EB-00.
                     READ IM-MESSAGE
                       AT END
                          MOVE HIGH-VALUE TO TB-ID(2)
                       NOT AT END
                          MOVE  MESSAGE-ID TO TB-ID(2)
                     END-READ
                                  .


EB-EXIT.
     EXIT.
/------------------------------------------------------------------------------*
EC-READ-PAYMENTS                                 SECTION.
*------------------------------------------------------------------------------*
* Purpose    :                                                                 *
* Description:                                                                 *
*------------------------------------------------------------------------------*
EC-00.

                     READ IP-PAYMENTS
                       AT END
                          MOVE HIGH-VALUE TO TB-ID(3)
                       NOT AT END
                          MOVE  PAYMENTS-ID TO TB-ID(3)
                     END-READ
                                  .
               
EC-EXIT.
     EXIT.
/------------------------------------------------------------------------------*
F-FIND-ON-DUTY                                   SECTION.
*------------------------------------------------------------------------------*
* Purpose    :                                                                 *
* Description:                                                                 *
*------------------------------------------------------------------------------*
F-00.

                      MOVE 1 TO I-MIN
                      MOVE TB-ID(1) TO ID-MIN

                      IF ID-MIN > TB-ID(2)
                         MOVE 2 TO I-MIN
                         MOVE TB-ID(2) TO ID-MIN
                      END-IF

                      IF ID-MIN > TB-ID(3)
                         MOVE 3 TO I-MIN
                         MOVE TB-ID(3) TO ID-MIN
                      END-IF
                               .
F-EXIT.
     EXIT.
/------------------------------------------------------------------------------*
C-REC-INIT                                       SECTION.
*------------------------------------------------------------------------------*
* Purpose    :                                                                 *
* Description:                                                                 *
*------------------------------------------------------------------------------*
C-00.


                   MOVE ALL ZEROS TO  TB-FILES
                   MOVE ALL ZEROS TO TB-PAYMENTS
                   MOVE ID-MIN TO PREV-ID
                               .
C-EXIT.
     EXIT.
