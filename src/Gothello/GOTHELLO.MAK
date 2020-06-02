#
# Borland C++ IDE generated makefile
#
.AUTODEPEND


#
# Borland C++ tools
#
IMPLIB  = Implib
BCCDOS  = Bcc +BccDos.cfg 
TLINK   = TLink
TLIB    = TLib
TASM    = Tasm
#
# IDE macros
#


#
# Options
#
IDE_LFLAGSDOS =  -LC:\apps\BC45\LIB
IDE_BFLAGS =  /E
LLATDOS_bDEVbBINbgothellodexe =  -c -Tde
RLATDOS_bDEVbBINbgothellodexe = 
BLATDOS_bDEVbBINbgothellodexe = 
CNIEAT_bDEVbBINbgothellodexe = -IC:\apps\BC45\INCLUDE -D
LNIEAT_bDEVbBINbgothellodexe = -x
LEAT_bDEVbBINbgothellodexe = $(LLATDOS_bDEVbBINbgothellodexe)
REAT_bDEVbBINbgothellodexe = $(RLATDOS_bDEVbBINbgothellodexe)
BEAT_bDEVbBINbgothellodexe = $(BLATDOS_bDEVbBINbgothellodexe)
CLATW16_ddbddbappsbbc45blibbgraphicsdlib = 
LLATW16_ddbddbappsbbc45blibbgraphicsdlib = 
RLATW16_ddbddbappsbbc45blibbgraphicsdlib = 
BLATW16_ddbddbappsbbc45blibbgraphicsdlib = 
CEAT_ddbddbappsbbc45blibbgraphicsdlib = $(CEAT_bDEVbBINbgothellodexe) $(CLATW16_ddbddbappsbbc45blibbgraphicsdlib)
CNIEAT_ddbddbappsbbc45blibbgraphicsdlib = -IC:\apps\BC45\INCLUDE -D
LNIEAT_ddbddbappsbbc45blibbgraphicsdlib = -x
LEAT_ddbddbappsbbc45blibbgraphicsdlib = $(LEAT_bDEVbBINbgothellodexe) $(LLATW16_ddbddbappsbbc45blibbgraphicsdlib)
REAT_ddbddbappsbbc45blibbgraphicsdlib = $(REAT_bDEVbBINbgothellodexe) $(RLATW16_ddbddbappsbbc45blibbgraphicsdlib)
BEAT_ddbddbappsbbc45blibbgraphicsdlib = $(BEAT_bDEVbBINbgothellodexe) $(BLATW16_ddbddbappsbbc45blibbgraphicsdlib)

#
# Dependency List
#
Dep_gothello = \
   \DEV\BIN\gothello.exe

gothello : BccDos.cfg $(Dep_gothello)
  echo MakeNode 

Dep_bDEVbBINbgothellodexe = \
   ..\..\apps\bc45\lib\graphics.lib\
   ..\bin\trip.obj\
   ..\bin\egavga.obj\
   \DEV\BIN\gothello.obj\
   \DEV\BIN\game.obj

\DEV\BIN\gothello.exe : $(Dep_bDEVbBINbgothellodexe)
  $(TLINK)   @&&|
 /v $(IDE_LFLAGSDOS) $(LEAT_bDEVbBINbgothellodexe) $(LNIEAT_bDEVbBINbgothellodexe) +
C:\apps\BC45\LIB\c0m.obj+
..\bin\trip.obj+
..\bin\egavga.obj+
\DEV\BIN\gothello.obj+
\DEV\BIN\game.obj
$<,$*
..\..\apps\bc45\lib\graphics.lib+
C:\apps\BC45\LIB\bidsm.lib+
C:\apps\BC45\LIB\emu.lib+
C:\apps\BC45\LIB\mathm.lib+
C:\apps\BC45\LIB\cm.lib

|

\DEV\BIN\gothello.obj :  gothello.c
  $(BCCDOS) -P- -c @&&|
 $(CEAT_bDEVbBINbgothellodexe) $(CNIEAT_bDEVbBINbgothellodexe) -o$@ gothello.c
|

\DEV\BIN\game.obj :  game.c
  $(BCCDOS) -P- -c @&&|
 $(CEAT_bDEVbBINbgothellodexe) $(CNIEAT_bDEVbBINbgothellodexe) -o$@ game.c
|

# Compiler configuration file
BccDos.cfg : 
   Cp &&|
-W-
-R
-v
-vi
-H
-H=gothello.csm
-w-voi
-wret
-w-pro
-w-rvl
-w-sig
-a
-w-ret
-w-ias
-w-msg
-mm
-f
| $@


