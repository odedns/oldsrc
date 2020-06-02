#!/bin/sh
# $Id: //depot/dev/src45/bin/startWebLogic.sh#11 $
#
# Shell script to manually start WebLogic Server on UNIX systems
# Assumes that the native libraries are installed where they'll
# be found (that LD_LIBRARY_PATH or SHLIB_PATH is set properly)

# Some Java utilities don't know where their classes are
# Add more settings as needed
sys=`uname -s`
if [ "$sys" = AIX ]; then
  # Change JDK_HOME to the actual location of your JDK, if necessary
  JDK_HOME=/usr/jdk_base
  JAVA=$JDK_HOME/bin/java
else
  JAVA=java
fi


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

#################################
# classpath for Inline
#################################

#Oracle env
NLS_LANG=AMERICAN_AMERICA.WE8ISO8859P1
NLS_DATE_FORMAT=DD-MON-RR
export NLS_LANG
export NLS_DATE_FORMAT

WEBLOGICHOME=/tmbchome/ccwww/cctm/weblogic
INLINEHOME=/tmbchome/ccwww/cctm/inline/v01

UAMS_CLASSES=$INLINEHOME/jars/entrust.jar
JNDI_CLASSES=$INLINEHOME/jars/jndi.jar:$INLINEHOME/jars/ldap.jar:$INLINEHOME/jars/providerutil.jar

USERHOME=$HOME/ejb/classes/:$INLINEHOME/classes


# Check whether we're running Java 2
$JAVA -X > /dev/null 2>&1
if [ $? -a -n "$JDK_HOME" ]; then
  # Java 1
  JAVACLASSPATH=$JDK_HOME/lib/classes.zip:./classes/boot:./eval/cloudscape/lib/cloudscape.jar
else
  # Java 2, or Java 1 and java knows where classes.zip is
  JAVACLASSPATH=$WEBLOGICHOME/classes/boot:$WEBLOGICHOME/eval/cloudscape/lib/cloudscape.jar:$WEBLOGICHOME/lib/poolorb.jar
fi

JAVACLASSPATH=$UAMS_CLASSES/:$JAVACLASSPATH/
WEBLOGICCLASSPATH=$WEBLOGICHOME/license:$WEBLOGICHOME/classes:$WEBLOGICHOME/lib/weblogicaux.jar:$WEBLOGICHOME/myserver/serverclasses:$USERHOME:$JNDI_CLASSES

$JAVA -ms64m -mx64m -classpath $JAVACLASSPATH -Dweblogic.system.home=$WEBLOGICHOME -Dweblogic.system.name=odednv01 -Dweblogic.class.path=$WEBLOGICCLASSPATH -Djava.security.manager -Djava.security.policy==weblogic.policy -Damdocs.system.home=$HOME/inline/v01/properties/server weblogic.Server
