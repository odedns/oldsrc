
#------------------------------------------------------------------
# General Gnu Make include file.
# Date :	09/07/1994.
#
#------------------------------------------------------------------


#------------------------------------------------------------------
# Macro definitions.
#------------------------------------------------------------------


CC_DIR = /tc
COMPILER = tcc
#WILDARGS = \\tc\\lib\\wildargs.obj
WILDARGS = $(BIN_DIR)/wildargs.obj

CC = $(CC_DIR)/bin/$(COMPILER)
CPP = $(CC_DIR)/bin/cpp
LDFLAGS = $(CC_DIR)\\lib\\c0s.obj
LDLIBS =  $(CC_DIR)\lib\\cs.lib
ASM = /tasm/tasm
LINK = $(CC_DIR)/tlink
LIB = $(CC_DIR)/bin/tlib
INCDIR = -I. -I$(CC_DIR)/include 
CFLAGS = -O -a  $(INCDIR)
