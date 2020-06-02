#------------------------------------------------------------------
# General Gnu Make include file.
# Date :	09/07/1994.
#
#------------------------------------------------------------------


#------------------------------------------------------------------
# Macro definitions.
#------------------------------------------------------------------
CC = cl
INCDIRS = -I. 
CFLAGS = -c -O1 $(INCDIRS)
CPP = /usr/bin/cpp
BISON = bison
LEX = flex
LIB = lib
LINK = link
LD = link
RM = rm -v

MAKE = gmake
MAKFLAGS = -f

.SUFFIXES: .c .o .h .inc .y .l .asm .a .obj .exe
#VPATH = /apps/tc/out;.;/apps/tc/c;/apps/tc/lib

#----------------------------------------------------------------
# implicit rules.
#----------------------------------------------------------------

.c.obj:
	echo Compiling C file :        $<
	$(CC) -c $(CFLAGS) $<

.l.c:
	@$(LEX) $<
	@$(CC) -c $(CFLAGS) lexyy.c

.y.c:
	@$(YACC) -d $<
	@$(CC) -c $(CFLAGS) y_tab.c

