

########################################################
# set the location of the Inline account and
# Weblogic installation directory.
########################################################

INLINEHOME=/tmbchome/ccwww/cctm
WEBLOGICHOME=$INLINEHOME/weblogic

########################################################
# set the LD_LIBRARY_PATH in order to use Weblogic's
# Oracle JDBC server.
########################################################
LD_LIBRARY_PATH=$WEBLOGICHOME/lib/solaris8:$LD_LIBRARY_PATH
export LD_LIBRARY_PATH

########################################################
# Weblogic classes used by the application scripts.
########################################################
WEBLOGIC_CLASSES=$WEBLOGICHOME/lib/weblogicaux.jar:$WEBLOGICHOME/classes:$WEBLOGICHOME/license:$WEBLOGICHOME/lib

########################################################
# Application jar files.
########################################################
JARS=$INLINEHOME/inline/v01/jars/jspInfraV31.jar:$INLINEHOME/inline/v01/jars/jutilV02.jar:$INLINEHOME/inline/v01/jars/amdocsEjbInfraV70.jar:$INLINEHOME/inline/v01/jars/entrust.jar

########################################################
# Application Classes.
########################################################
APP_CLASSES=/tmbchome/ccwww/cctm/proj/inlinev01/classes


########################################################
# Set the Java CLASSPATH
########################################################
CLASSPATH=./:$JARS/:$APP_CLASSES/:$WEBLOGIC_CLASSES/:$CLASSPATH
export CLASSPATH
