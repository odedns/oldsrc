!include ..\..\inc\Win32.mak

GENCLIB = \dev\bin\gclib32.lib

LIB = $(LIB);..\..\genclib
CFLAGS = $(CFLAGS) -I ..\..\genclib
RM = rm -v
BINDIR = .


all:  wls.exe ntdf.exe wfile.exe finger.exe getch.exe env.exe


clean:
	@echo Cleanning up ...!
	@$(RM) *.exe *.obj

$(GENCLIB):
	@echo making $@
	@cd ..\..\genclib
	$(MAKE)
	@cd ..\utils\win32

wls.exe: wls.c $(GENCLIB)
	@echo creating .... $@
	$(CC) $(CFLAGS) $?  -o $@

ntdf.exe: ntdf.c 
	@echo creating .... $@
	$(CC) $(CFLAGS) $?  -o $@

wfile.exe: wfile.c werror.obj 
	@echo creating .... $@
	$(CC) $(CFLAGS) wfile.c werror.obj wsock32.lib version.lib -o $@

finger.exe: finger.c werror.obj
	@echo creating .... $@
	$(CC) $(CFLAGS) finger.c werror.obj wsock32.lib -o $@

getch.exe: getch.c
	@echo creating .... $@
	$(CC) $(CFLAGS) getch.c  -o $@

env.exe: env.c
	@echo creating .... $@
	$(CC) $(CFLAGS) env.c  -o $@

show_flags:
	@echo CC 		=  $(CC)
	@echo CFLAGS 		=  $(CFLAGS)
	@echo LIB 		= $(LIB)
	@echo INCLUDE  		= $(INCLUDE)
	@echo OS 		= $(OS)
	@echo MSVCDIR 		= $(MSVCDIR)
