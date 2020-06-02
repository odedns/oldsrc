#------------------------------------------------------------------
# General Gnu Make include file.
# Date :	09/07/1994.
#
#------------------------------------------------------------------


#------------------------------------------------------------------
# Macro definitions.
#------------------------------------------------------------------

SRC_DIR = /dev/src/c
BIN_DIR = /dev/bin

CC_DIR = /tc
COMPILER = tcc
#WILDARGS = \\tc\\lib\\wildargs.obj
WILDARGS = $(BIN_DIR)/wildargs.obj

CC = $(CC_DIR)/bin/$(COMPILER)
CPP = $(CC_DIR)/bin/cpp
LDFLAGS = $(CC_DIR)\\lib\\c0s.obj
LDLIBS =  $(CC_DIR)\lib\\cs.lib

YACC = /bin/byacc -d
LEX = /bin/flex -S\\doc\\flex.skl
ASM = /tasm/tasm
LINK = $(CC_DIR)/tlink
LIB = $(CC_DIR)/bin/tlib
INCDIR = -I. -I$(CC_DIR)/include -I$(SRC_DIR)
CFLAGS = -O -a  $(INCDIR)
CXXFLAGS = $(CFLAGS)
MAKE = gmake
MAKFLAGS = -f

.SUFFIXES: .c .hpp .cpp .exe .obj .h .inc .y .l .asm .lib
#VPATH = \dev\bin;.;\dev\src
VPATH = /dev/bin;.;/dev/src/c;/dev/src/cpp


#----------------------------------------------------------------
# implicit rules.
#----------------------------------------------------------------

.cpp.obj:
	@$(CC) -c $(CFLAGS) $<

.c.obj:
	@$(CC) -c $(CFLAGS) $<

.obj.exe:
	@$(CC) $(CFLAGS) -e$(@F) $^
.l.c:
	@$(LEX) $<
	@$(CC) -c $(CFLAGS) lexyy.c

.y.c:
	@$(YACC) -d $<
	@$(CC) -c $(CFLAGS) y_tab.c

.asm.obj:
	@$(ASM) -c $<
