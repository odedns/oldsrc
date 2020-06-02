#-------------------------------------------------------------
# Make file for the cp program for MS-DOS.
#
#-------------------------------------------------------------


#_BCC = 1
include ..\..\inc\gmake.inc
MYLIB := $(BIN_DIR)/mylib.lib

OBJS := fcopy.obj mv.obj rm.obj rm.exe cp.exe mv.exe \
        ls.obj ls.exe df.exe df.obj \
        fdump.exe fdump.obj wc.exe wc.obj 
TARGETS := cp.exe df.exe ls.exe rm.exe mv.exe \
     fdump.exe wc.exe ecd.exe getch.exe which.exe clock.exe

all: $(TARGETS)

cp.exe: fcopy.c $(WILDARGS) mylib.lib
	$(MAKE) $(MAKFLAGS) mylib.mak
	$(CC) $(CFLAGS) -e$(@F) $^

mv.exe: mv.c $(WILDARGS) mylib.lib
	$(MAKE) $(MAKFLAGS) mylib.mak
	$(CC) $(CFLAGS) -e$(@F) $^

rm.exe: rm.c $(WILDARGS) mylib.lib
	$(MAKE) $(MAKFLAGS) mylib.mak
	$(CC) $(CFLAGS) -e$(@F) $^

df.exe: df.c mylib.lib
	$(MAKE) $(MAKFLAGS) mylib.mak
	$(CC) $(CFLAGS) -e$(@F) $^

ls.exe: ls.c $(WILDARGS) mylib.lib
	$(MAKE) $(MAKFLAGS) mylib.mak
	$(CC) $(CFLAGS) -e$(@F) $^

wc.exe: wc.c $(WILDARGS) getopt.c
	$(CC)  $(CFLAGS) -e$(@F) $^
	
fdump.exe: fdump.c $(WILDARGS) getopt.c
	$(CC)  $(CFLAGS) -e$(@F) $^

getch.exe: getch.c
	$(CC) -mt $(CFLAGS) -e$(@F) $^

ecd.exe: ecd.c
	$(CC) -mt $(CFLAGS) -e$(@F) $^

which.exe: which.c $(WILDARGS) 
	$(CC) $(CFLAGS) -e$(@F) $^

clock.exe: clock.c 
	$(CC) -mt $(CFLAGS) -e$(@F) $^
clean :
	@cd $(BIN_DIR)
	$(RM)  $(OBJS)
