#
# Borland C++ IDE generated makefile
#
.AUTODEPEND


#
# Borland C++ tools
#
IMPLIB  = Implib
BCC     = Bcc +BccW16.cfg 
TLINK   = TLink
TLIB    = TLib
BRC     = Brc
TASM    = Tasm
#
# IDE macros
#


#
# Options
#
IDE_LFLAGS =  -LC:\apps\BC45\LIB
IDE_RFLAGS = 
LLATW16_CcbDEVbBINbdfdexe =  -i -Twe -c -C
RLATW16_CcbDEVbBINbdfdexe =  -31
BLATW16_CcbDEVbBINbdfdexe = 
CNIEAT_CcbDEVbBINbdfdexe = -IC:\APPS\BC45\INCLUDE;C:\DEV\SRC -D
LNIEAT_CcbDEVbBINbdfdexe = -x
LEAT_CcbDEVbBINbdfdexe = $(LLATW16_CcbDEVbBINbdfdexe)
REAT_CcbDEVbBINbdfdexe = $(RLATW16_CcbDEVbBINbdfdexe)
BEAT_CcbDEVbBINbdfdexe = $(BLATW16_CcbDEVbBINbdfdexe)
CLATW16_ddbbinbmylibdlib = 
LLATW16_ddbbinbmylibdlib = 
RLATW16_ddbbinbmylibdlib = 
BLATW16_ddbbinbmylibdlib = 
CEAT_ddbbinbmylibdlib = $(CEAT_CcbDEVbBINbdfdexe) $(CLATW16_ddbbinbmylibdlib)
CNIEAT_ddbbinbmylibdlib = -IC:\APPS\BC45\INCLUDE;C:\DEV\SRC -D
LNIEAT_ddbbinbmylibdlib = -x
LEAT_ddbbinbmylibdlib = $(LEAT_CcbDEVbBINbdfdexe) $(LLATW16_ddbbinbmylibdlib)
REAT_ddbbinbmylibdlib = $(REAT_CcbDEVbBINbdfdexe) $(RLATW16_ddbbinbmylibdlib)
BEAT_ddbbinbmylibdlib = $(BEAT_CcbDEVbBINbdfdexe) $(BLATW16_ddbbinbmylibdlib)

#
# Dependency List
#
Dep_df = \
   C:\DEV\BIN\df.exe

df : BccW16.cfg $(Dep_df)
  echo MakeNode 

Dep_CcbDEVbBINbdfdexe = \
   ..\bin\mylib.lib\
   C:\DEV\BIN\df.obj

C:\DEV\BIN\df.exe : $(Dep_CcbDEVbBINbdfdexe)
  $(TLINK)   @&&|
 /v $(IDE_LFLAGS) $(LEAT_CcbDEVbBINbdfdexe) $(LNIEAT_CcbDEVbBINbdfdexe) +
C:\apps\BC45\LIB\c0ws.obj+
C:\DEV\BIN\df.obj
$<,$*
..\bin\mylib.lib+
C:\apps\BC45\LIB\import.lib+
C:\apps\BC45\LIB\mathws.lib+
C:\apps\BC45\LIB\cws.lib

|

C:\DEV\BIN\df.obj :  df.c
  $(BCC)   -P- -c @&&|
 $(CEAT_CcbDEVbBINbdfdexe) $(CNIEAT_CcbDEVbBINbdfdexe) -o$@ df.c
|

# Compiler configuration file
BccW16.cfg : 
#   Copy &&|
	Cp &&|
-R
-v
-vi
-H
-H=df.csm
-w-voi
-wret
-w-pro
-w-rvl
-w-sig
-a
-w-ret
-w-ias
-w-msg
-v
-R-
-O
-ms
-WS
| $@


