#!/bin/sh
JAVA_HOME=/usr/java
JAVA=$JAVA_HOME/bin/java
WL_HOME=/home/ginshoo2/weblogic

$JAVA -cp $WL_HOME/classes:$WL_HOME/lib/weblogicaux.jar weblogic.Admin t3://localhost:8050 SHUTDOWN system adminadmin

