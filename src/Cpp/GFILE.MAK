.AUTODEPEND

.PATH.obj = E:\DEV\BIN

#		*Translator Definitions*
CC = tcc +GFILE.CFG
TASM = C:\DOSAPPS\TASM\TASM
TLIB = tlib
TLINK = tlink
LIBPATH = E:\TC\LIB;E:\TC\CLASSLIB\LIB;E:\TC\TVISION\LIB;E:\DEV\BIN
INCLUDEPATH = E:\TC\INCLUDE;E:\TC\TVISION\INCLUDE;E:\DEV\SRC\C;E:\DEV\SRC\CPP;E:\TC\CLASSLIB\INCLUDE;


#		*Implicit Rules*
.c.obj:
  $(CC) -c {$< }

.cpp.obj:
  $(CC) -c {$< }

#		*List Macros*


EXE_dependencies =  \
 gtime.obj \
 gfile.obj

#		*Explicit Rules*
e:\dev\bin\gfile.exe: gfile.cfg $(EXE_dependencies)
  $(TLINK) /v/x/i/c/d/L$(LIBPATH) @&&|
c0s.obj+
e:\dev\bin\gtime.obj+
e:\dev\bin\gfile.obj
e:\dev\bin\gfile
		# no map file
graphics.lib+
tclasss.lib+
emu.lib+
maths.lib+
cs.lib
|


#		*Individual File Dependencies*
gtime.obj: gfile.cfg gtime.cpp 

gfile.obj: gfile.cfg gfile.cpp 

#		*Compiler Configuration File*
gfile.cfg: gfile.mak
  copy &&|
-a
-v
-vi-
-w-ret
-w-nci
-w-inl
-wpin
-wamb
-wamp
-w-par
-wasm
-wcln
-w-cpt
-wdef
-w-dup
-w-pia
-wsig
-wnod
-w-ill
-w-sus
-wstv
-wucp
-wuse
-w-ext
-w-ias
-w-ibc
-w-pre
-w-nst
-nE:\DEV\BIN
-I$(INCLUDEPATH)
-L$(LIBPATH)
| gfile.cfg


