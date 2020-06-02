# ==========================================================================
#                       auxilliary daemon   Make File
# ==========================================================================
#  Copyright MEMCO Software Ltd.
#  By : Oded Nissan   
# ==========================================================================
#  Last Update    : 25/02/97
#  Last Update By : 
# ==========================================================================

# Include standard make file of memco  !!!
include ../../../../make/STDMakefile

minver=2.10

INCDIR  = $(SINC)
LIBDIR  = $(SLIB)
BINDIR  = $(SBIN)
OBJDIR  = $(SOBJ)/utils/daemons/seauxd/


#----------------------------------------------
# flags               
#----------------------------------------------

DEFINES        = -D__MINVER__=\"$(minver)\"
DEBUG_CFLAG    = $(STD_DB_CFLAGS)
OPTIMIZE_CFLAG = $(STD_OPT_CFLAGS)
MAP_CFLGS      = $(STD_MAP_CFLAGS)
PROF_CFLAGS    = $(STD_OPT_CFLAGS)
CFLAGS         = -D_DEBUG -I../general $(STD_CFLAGS) $(DEBUG_CFLAG) $(STD_WCFLAGS) $(DEFINES)
LDFLAGS        = $(STD_LDFLAGS) ${STD_DB_LDFLAGS} ${SEOS_SYSCALL}
ARFLAGS	       = $(STD_ARFLAGS)

ifeq ($(OS),_AIX)
LDFLAGS += -lbsd -ls
endif


ifeq ($(OS),_HPUX)
LDFLAGS += -Wl,-E
endif

ifeq ($(OS),_ATT)
LDFLAGS += -Wl,-Bexport
endif

LDFLAGS += $(LD_DLL_FLAGS) $(LD_NET_FLAGS)



#----------------------------------------------
# target variables
#----------------------------------------------

LIBSRCS := aux_dns_res.c aux_dns_tbl.c 
ALLSRCS := $(LIBSRC)



LIBOBJS := ${LIBSRCS:%.c=$(OBJDIR)/%.o}
ALLOBJS := $(LIBOBJS)
ALLDEPS := ${ALLSRCS:%.c=$(OBJDIR)/%.dep}

AUXD_LIB := $(OBJDIR)/libauxdns.a
TARGETS :=  $(AUXD_LIB)  


EXELIBS =  $(AUXD_LIB) 	\
	$(LIBDIR)/UnixDb.a \
	$(LIBDIR)/misclib.a
	

#----------------------------------------------
# make rules
#----------------------------------------------


all: $(TARGETS)

install: all install_files

clean :
	@echo "	Clean Up"
	@/bin/rm -f $(OBJDIR)/*.o $(OBJDIR)/*.dep $(TARGETS)
	@echo "	Done"


install_files : 
	@echo "installing $(AUXD_LIB) in $(SLIB)"
	@/bin/cp  $(AUXD_LIB)  $(SLIB)



$(AUXD_LIB) : $(LIBOBJS)
	@/bin/rm -f $@
	@ar $(ARFLAGS) $@ $(LIBOBJS)
	@ranlib $@
	@echo " done "  $@



buildall : clean all

include ../../../../make/genrules.mk

#-include ${ALLDEPS}

