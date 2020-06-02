#!/bin/sh
# $Id$
#
# This script can be used to start WebLogic Server on UNIX systems. 
# It contains following variables: 
# 
# JAVA_HOME      - Determines the version of Java used to start  
#                  WebLogic Server. This variable must point to the 
#                  root directory of a JDK or JRE installation. See 
#                  the WebLogic platform support page 
#                  (http://www.weblogic.com/docs51/platforms/index.html)
#                  for an up-to-date list of supported JVMs your platform. 
# PRE_CLASSPATH  - Use this variable to prepend jar files or directories to 
#                  the WEBLOGIC_CLASSPATH.
# POST_CLASSPATH - Use this variable to append jar files or directories to 
#                  the end of the WEBLOGIC_CLASSPATH.
# JAVA_OPTIONS -   On some platforms, certain java command-line options are 
#                  necessary when running WebLogic Server. For additional 
#                  information, see Setting up and Starting WebLogic Server 
#                  (/install/startserver.html in your local documentation set 
#                  or on the Internet at 
#                  http://www.weblogic.com/docs51/install/startserver.html)
# 
# This script assumes that native libraries (performance packs and jDriver 
# for Oracle) have been installed in the proper location and that your 
# system PATH variable has been set appropriately (jDriver for Oracle only).
# For additional information refer to Installing and Setting up WebLogic 
# Server (/install/index.html in your local documentation 
# set or on the Internet at http://www.weblogic.com/docs51/install/index.html). 

# Set user-defined variables
JAVA_HOME=/usr/java

### added for Ginshoo
GINSHOO_HOME=$HOME/projects/ginshoo/1.1
GINSHOO_PKG=/home/ginshoo2/packages
GINSHOO_CLASSES=/home/ginshoo2/projects/ginshoo/1.1/classes
MY_CLASSES=/home/oded/work/ejb/classes:/home/oded/projects/ginshoo/1.1/classes:$HOME/public_html/servlet
PRE_CLASSPATH=$MY_CLASSES:$GINSHOO_CLASSES:$GINSHOO_PKG:/home/oded/work/ejb/classes:/home/ginshoo2/packages/xsql:/home/ginshoo2/packages/xsql/oraclexmlsql.jar:/home/ginshoo2/packages/xsql/oraclexsql.jar:/home/ginshoo2/packages/xsql/xmlparserv2.jar:/home/ginshoo2/packages/xalan.jar:/home/ginshoo2/packages/jakarta-oro-2.0.jar:/home/ginshoo2/packages/xerces.jar

WL_HOME=/home/ginshoo2/weblogic
###############

POST_CLASSPATH=
JAVA_OPTIONS=-native

JAVA=$JAVA_HOME/bin/java

if [ `uname -s` = "Windows_NT" ]
then
  echo "***Error*** Use startWebLogic.cmd if you are running Windows NT"
  exit
fi

# Make sure we're in the right place
#if [ ! -f license/WebLogicLicense.xml -o ! -f weblogic.policy ]; then
#  echo "startWeblogic.sh: must be run from the WebLogic installation directory." 1>&2
 # exit 1
#else
#  WL_HOME=`pwd`
#fi

# Grab some file descriptors.
maxfiles=`ulimit -H -n`
if [ ! $? -a "$maxfiles" != 1024 ]; then
  if [ "$maxfiles" = "unlimited" ]; then
    maxfiles=1025
  fi
  if [ "$maxfiles" -lt 1024 ]; then
    ulimit -n $maxfiles
  else
    ulimit -n 1024
  fi
fi

# Figure out how to use our shared libraries
case `uname -s` in
AIX)
  if [ -n "$LIBPATH" ]; then
    LIBPATH=$LIBPATH:$WL_HOME/lib/aix
  else
    LIBPATH=$WL_HOME/lib/aix
  fi
  export LIB_PATH
  echo "LIBPATH=$LIBPATH"
;;
HP-UX)
  if expr "`uname -r`" : "..11..." > /dev/null 2>&1; then
    hpsubdir=hpux11
  elif expr "`uname -r`" : "..10..." > /dev/null 2>&1; then 
    hpsubdir=hpux10
  fi

  chmod a+x `find $WL_HOME/lib/$hpsubdir -name "*.sl" -print`
 
  if [ -n "$SHLIB_PATH" ]; then
    SHLIB_PATH=$SHLIB_PATH:$WL_HOME/lib/$hpsubdir
  else
    SHLIB_PATH=$WL_HOME/lib/$hpsubdir
  fi
  export SHLIB_PATH
  echo "SHLIB_PATH=$SHLIB_PATH"
;;
IRIX)
  if [ -n "$LD_LIBRARY_PATH" ]; then
    LD_LIBRARY_PATH=$LD_LIBRARY_PATH:$WL_HOME/lib/irix
  else
    LD_LIBRARY_PATH=$WL_HOME/lib/irix
  fi
  export LD_LIBRARY_PATH
  echo "LD_LIBRARY_PATH=$LD_LIBRARY_PATH"
;;
LINUX)
  if [ -n "$LD_LIBRARY_PATH" ]; then
    LD_LIBRARY_PATH=$LD_LIBRARY_PATH:$WL_HOME/lib/linux
  else
    LD_LIBRARY_PATH=$WL_HOME/lib/linux
  fi
  export LD_LIBRARY_PATH
  echo "LD_LIBRARY_PATH=$LD_LIBRARY_PATH"
;;
OSF1)
  if [ -n "$_RLD_LIST" ]; then
    _RLD_LIST=$_RLD_LIST:$WL_HOME/lib/tru64unix
  else
    _RLD_LIST=$WL_HOME/lib/tru64unix
  fi
  export _RLD_LIST
  echo "_RLD_LIST=$_RLD_LIST"
;;
SunOS)
  if [ -n "$LD_LIBRARY_PATH" ]; then
    LD_LIBRARY_PATH=$LD_LIBRARY_PATH:$WL_HOME/lib/solaris
  else
    LD_LIBRARY_PATH=$WL_HOME/lib/solaris
  fi
  export LD_LIBRARY_PATH
  echo "LD_LIBRARY_PATH=$LD_LIBRARY_PATH"
;;
*)
  echo "$0: Don't know how to set the shared library path for `uname -s`.  "
esac

# Set the server classpath

WEBLOGICCLASSPATH=$WL_HOME/license:$WL_HOME/classes:$WL_HOME/lib/weblogicaux.jar:$WL_HOME/myserver/serverclasses
if [ "$PRE_CLASSPATH" != "" ]; then
  WEBLOGICCLASSPATH=$PRE_CLASSPATH:$WEBLOGICCLASSPATH
fi

if [ "$POST_CLASSPATH" != "" ]; then
  WEBLOGICCLASSPATH=$WEBLOGICCLASSPATH:$POST_CLASSPATH
fi

# Find out which java version we're running

if [ -f $JAVA_HOME/lib/classes.zip ]; then
  # Java 1
  JAVACLASSPATH=$JAVA_HOME/lib/classes.zip:$WL_HOME/classes/boot:$WL_HOME/eval/cloudscape/lib/cloudscape.jar

  $JAVA -ms64m -mx64m -classpath $JAVACLASSPATH -Dweblogic.class.path=$WEBLOGICCLASSPATH -Dweblogic.home=/home/oded/weblogic -Dweblogic.system.name=odev01 weblogic.Server
else
  # Java 2
  WEBLOGICCLASSPATH=$WL_HOME/lib/weblogic510sp8.jar:$WEBLOGICCLASSPATH
  JAVACLASSPATH=$WL_HOME/lib/weblogic510sp8boot.jar:$WL_HOME/classes/boot:$WL_HOME/eval/cloudscape/lib/cloudscape.jar

  $JAVA $JAVA_OPTIONS -ms64m -mx64m -Dweblogic.system.name=oded_v1.1 -classpath $JAVACLASSPATH -Dweblogic.class.path=$WEBLOGICCLASSPATH -Dweblogic.home=$WL_HOME -Djava.security.manager -Djava.security.policy==`pwd`/weblogic.policy -Dweblogic.system.home=$WL_HOME -Dginshoo.home=$GINSHOO_HOME weblogic.Server
fi
