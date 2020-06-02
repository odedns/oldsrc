#-------------------------------------------------------------
# Make file for the mylib library.
#
#-------------------------------------------------------------

#include ..\inc\gmake.inc

MYOBJS = video.obj misc.obj window.obj llist.obj getopt.obj \
	dirlib.obj keyboard.obj menu.obj  
MYOBJS2 = sort.obj mouse.obj stk.obj queue.obj



mylib.lib: $(MYOBJS) $(MYOBJS2)
#	@cd $(BIN_DIR)
	@cd \dev\bin

	$(LIB) $(notdir $@)/C $(foreach f,$(notdir $(MYOBJS)), $(join -+ , $(f)))
	$(LIB) $(notdir $@)/C $(foreach f,$(notdir $(MYOBJS2)), $(join -+ , $(f)))
	$(LIB) mylib.lib/E
	@cd \dev\src

clean:  
	@echo "Cleaning up ...."
	@cd $(BIN_DIR)
	$(RM) $(MYOBJS)
	$(RM) $(MYOBJS2)

video.obj : video.c
misc.obj : misc.c
window.obj : window.c
llist.obj : llist.c
getopt.obj : getopt.c
dirlib.obj : dirlib.c
keyboard.obj : keyboard.c
menu.obj : menu.c
mouse.obj : mouse.c
sort.obj  : sort.c
stk.obj : stk.c
queue.obj : queue.c

