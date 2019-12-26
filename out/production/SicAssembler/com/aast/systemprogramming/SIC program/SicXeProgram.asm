LOADER     START     0
           CLEAR     X
           JSUB      GETPAIR
           STCH      ADDR1
           JSUB      GETPAIR
           STCH      ADDR2
           JSUB      GETPAIR
           STCH      ADDR3
           LDB       ADDR1
           JSUB      GETPAIR
           STCH      ADDR1
           JSUB      GETPAIR
           STCH      ADDR2
           JSUB      GETPAIR
           STCH      ADDR3
LOOP       JSUB      GETPAIR
           BASE      ADDR1
           STCH      ADDR1,X
           NOBASE
           TIXR      X
           J         LOOP
GETPAIR    STL       RTADDR1
           JSUB      READ
           SHIFTL    A,4
           STCH      HEX
           JSUB      READ
           OR        ORADDR1
           J        @RTADDR1
READ       TD       #X'F1'
           JEQ       READ
           CLEAR     A
           RD       #X'F1'
           COMP     #48
           JLT       EOFCK
           SUB      #48
           COMP     #10
           JLT       GOBACK
           SUB      #7
GOBACK     RSUB
EOFCK      COMP     #33
           JEQ       EXIT
           COMP     #4
           JGT       READ
EXIT       CLEAR     L
           J        @ADDR1
ADDR1      RESB      1
ADDR2      RESB      1
ADDR3      RESB      3
RTADDR1    RESB      1
ORADDR1    RESB      1
           RESB      1
HEX        RESB      1
           END       0