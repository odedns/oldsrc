# ==========================================================================
#                       auxilliary daemon   Make File
# ==========================================================================
#  Copyright MEMCO Software Ltd.
#  By : Oded Nissan   
# ==========================================================================
#  Last Update    : 23/07/97
#  Last Update By : 
# ==========================================================================

# Include standard make file of memco  !!!

INCDIR  = $(SINC)
LIBDIR  = $(SLIB)
BINDIR  = $(SBIN)
OBJDIR  = .


#----------------------------------------------
# flags               
#----------------------------------------------
CFLAGS := $(CFLAGS) -g 


OBJS:= nis_clnt.o nisrpc.o nis_xdr.o
NIS_LIBS := -lnsl 

#----------------------------------------------
# make rules
#----------------------------------------------


TARGETS := nisrpc
all: $(TARGETS)

install: all install_files

clean :
	@echo "	Clean Up"
	@/bin/rm -f $(OBJDIR)/*.o $(OBJDIR)/*.dep $(TARGETS)
	@echo "	Done"


nisrpc: $(OBJS)
	$(CC) $(CFLAGS) $(OBJS) $(NIS_LIBS) -o $@
	@echo " done "  $@



