#!/bin/sh 
#############################################################################
# File  :   	ejbdeploy
# Date  :	11-28-1999
#               05/01/2001
# Version:	v01
# Written By:	Oded Nissan
# Purpose:	Deploy an ejb bean using weblogic ejb server utilities.
############################################################################# 



# print usage help.
help() {

	echo "Usage ejbdeploy [options]"
	echo " "
	echo "	-help | -h  	print this help text."
	echo "	-bean <bean name>	bean name."
	echo "	-outdir <dir>		output directory."
	echo "	-sources <directory>	directory to take sources from.  (fullpath)"
	echo "	-package <name>	package name. "
}

# parse command line args.
parse_args() {

OUTDIR="/."
BEAN_NAME=""
PACKAGE=$BEAN_NAME
SOURCES=""

	if [ $# -lt 2 ] ;  then 
		help
		exit 1
	fi
	while [ $# != 0 ] 
	do
		case "$1" in 
			-help) 
				help;
				exit;;
			-h)
				help;
				exit;;
			-bean)
				shift
				BEAN_NAME=$1
				;;
			-package)
				shift
				PACKAGE=$1
				;;
			-outdir)
				shift
				OUTDIR=$1
				;;
			-sources)
				shift
				SOURCES=$1
				;;
			*) 	echo "invalid arg: $1"
				;;
		esac
		shift
	done
	check_vars
}


# debug print variables.
print_vars() {
	echo "OUTDIR 	= $OUTDIR"
	echo "BEAN 	= $BEAN_NAME"
	echo "PACKAGE   = $PACKAGE"
	echo "SOURCES   = $SOURCES"
}


# check_vars
# check if all required input arguments have been
# given on the command line.
check_vars() {


	if [ -z "$OUTDIR" -o  -z "$BEAN_NAME" ]; then 
		help
		exit 1
	fi
	if [ -z "$PACKAGE" ]; then 
		PACKAGE=$BEAN_NAME
	fi
}


deploy() {

	CLASS_HOME=.
	WEBLOGICHOME=/home/ginshoo/weblogic


	CLASSPATH="$WEBLOGICHOME/lib/weblogicaux.jar:$WEBLOGICHOME/lib:$WEBLOGICHOME/classes/:$WEBLOGICHOME/:$CLASS_HOME/:$CLASSPATH"
	export CLASSPATH

	if [ -z "$SOURCES" ]; then 
		SOURCES=.
	fi
	echo "===================================="
	echo " building bean: $BEAN_NAME "
	echo "===================================="


	# removing older classes.
	echo "================================="
	echo "removing old jar file ..."
	echo "================================="
	echo cd $OUTDIR
	echo rm $BEAN_NAME.jar
	cd $OUTDIR
	rm -rf $BEAN_NAME.jar $BEAN_NAME

	# compile classes
	echo "================================="
	echo "compiling classes"
	echo "================================="
	echo javac -d . $SOURCES/*.java
	javac -d . $SOURCES/*.java

	# copy XML files
	echo "================================="
	echo "creating deployment descriptor"
	echo "================================="
	echo mkdir META-INF
	echo cp $SOURCES/*.xml META-INF
	rm -rf META-INF
	mkdir META-INF
	cp $SOURCES/*.xml META-INF

	echo "================================="
	echo "creating jar file $BEAN_NAME.jar"
	echo "================================="
	echo jar cv0f $BEAN_NAME.jar META-INF $PACKAGE
	jar cv0f $BEAN_NAME.jar META-INF $PACKAGE

	# generate wrapper classes.
	echo "================================="
	echo "generating wrapper classes"
	echo "================================="
	echo java weblogic.ejbc -d . $BEAN_NAME.jar
	java weblogic.ejbc -d . $BEAN_NAME.jar

	echo ""
	echo "===================================="
	echo " Finished building bean: $BEAN_NAME "
	echo "===================================="


}



#-------------------------
# main 
#-------------------------

	parse_args $@
	print_vars
	deploy

