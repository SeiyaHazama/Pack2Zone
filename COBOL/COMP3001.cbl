      ******************************************************************
      *  OVERVIEW: THIS PGM IS WRITE PACK VAR ON OUT TAPE.             *
      ******************************************************************
      *--1----+----2----+----3----+----4----+----5----+----6----+----7--
        IDENTIFICATION  DIVISION. 
           PROGRAM-ID. COMP3001.
           AUTHOR.     ZAMA_8722.
        ENVIRONMENT     DIVISION.
        INPUT-OUTPUT    SECTION.
        FILE-CONTROL.
           SELECT  OUT-TP  ASSIGN  TO  'OUT-TP.dat'.
        DATA            DIVISION.
        FILE            SECTION.
      *--  OUTPUT TAPE.
           FD      OUT-TP.
           01      TP-REC.
             02    ZONEAREA  PICTURE 9(10).
             02    PACKAREA  PICTURE 9(10) COMP-3.
             02    KANAAREA  PICTURE X(9).
        WORKING-STORAGE SECTION.
      *--  COMP-3 CONST VARIABLE.
           01      VARIABLE.
             02    FILLER  PICTURE 9(10)
                           VALUE 1234567890. 
             02    FILLER  PICTURE 9(10) COMP-3
                           VALUE 1234567890.
             02    FILLER  PICTURE X(9)
                           VALUE 'あいう'.
        PROCEDURE       DIVISION.
        MAIN            SECTION.
        MAIN-S.
      *--  TAPE OPEN.
           OPEN  OUTPUT  OUT-TP.
      *--  WRITE PACK VARIABLE.
           MOVE  VARIABLE  TO  TP-REC.
           WRITE TP-REC.
      *--  TAPE CLOSE.
           CLOSE OUT-TP. 
        MAIN-E.
           STOP RUN.
