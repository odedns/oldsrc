!include ..\inc\Win32.mak


LIBOBJ1 = $(BINDIR)\\llist.obj 		\
	  $(BINDIR)\\hash.obj 		\
	  $(BINDIR)\\stk.obj 		\
	  $(BINDIR)\\queue.obj 		\
	  $(BINDIR)\\path.obj 		

LIBOBJ2 = $(BINDIR)\\str.obj 		\
          $(BINDIR)\\getopt.obj 	\
	  $(BINDIR)\\inifile.obj	\
	  $(BINDIR)\\vector.obj 	\
	  $(BINDIR)\\sort.obj		



LIB = $(LIB)$(BINDIR)
GENCLIB = gclib32.lib
HASHTST = $(BINDIR)\\hashtst.exe
INITST = $(BINDIR)\\initst.exe
BINDIR = \dev\bin

RM = rm -v
SRCDIR = /dev/src/genclib/

CFLAGS = $(CFLAGS) /Fo$(BINDIR)\\


tests: $(INITST) $(HASHTST)
all: $(GENCLIB) $(INITST) $(HASHTST)


# remove object files and executables
clean:
	@echo cleanning up ....
	@cd $(BINDIR)
	@-$(RM) $(LIBOBJ1) 
	@-$(RM) $(LIBOBJ2) 
	@-$(RM) $(GENCLIB) 
	@-$(RM) $(INITST) $(HASHTST)
	@cd $(SRCDIR)


# create the library.
$(GENCLIB): $(LIBOBJ1) $(LIBOBJ2) 
	@echo creating $@ ....
	cd $(BINDIR)
#	lib/OUT:$@ $?
	lib/OUT:$@ $(LIBOBJ1) $(LIBOBJ2) 
	cd $(SRCDIR)

$(HASHTST): hashtst.c $(GENCLIB)
	@echo creating $@ ....
	@echo cd 
	$(CC) $(CFLAGS) hashtst.c $(BINDIR)\\$(GENCLIB)  -o $@

$(INITST): initst.c $(GENCLIB)
	@echo creating $@ ....
	$(CC) $(CFLAGS) initst.c $(BINDIR)\\$(GENCLIB)  -o $@


show_flags:
	@echo CC 		=  $(CC)
	@echo CFLAGS 		=  $(CFLAGS)
	@echo LIB 		= $(LIB)
	@echo INCLUDE  		= $(INCLUDE)
	@echo OS 		= $(OS)
	@echo MSVCDIR 		= $(MSVCDIR)
	@echo SRCDIR		= $(SRCDIR)

.c{$(BINDIR)}.obj:
	$(CC) -c $(CFLAGS) $<  
