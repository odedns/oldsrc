#-------------------------------------------------------------
# Make file for the mylib library.
#
#-------------------------------------------------------------

include gmake.inc

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
