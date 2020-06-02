
#-------------------------------------------------------------
# Make file for the Othello Game Program 
#
#-------------------------------------------------------------
include ..\inc\gmake.inc


gothello.exe: gothello.obj game.obj egavga.obj trip.obj graphics.lib
	@echo Linking : $@
	$(CC) $(CFLAGS) -e$(@F) $^


game.obj: game.c
	$(CC) -c $(CFLAGS) game.c

gothello.obj: gothello.c
	$(CC) -c $(CFLAGS) gothello.c

