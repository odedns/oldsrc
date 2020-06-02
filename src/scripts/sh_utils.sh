#!/bin/sh
# -------------------------------------------------------------------------
# Parameter $1 should be the directory where seos was installed
# Parameter $2 should be "yes" if user selected nis or no nis server
# If parameters are not specified then script will try to use __SEOS__ env
# or SEOSDIR env variable, and NIS will be assumed as if not asked.

# Simply echo in one line, like echo -n on some systems
echo_n()
{
if [ -z "`echo -n`" ]; then
    echo -n $ECHO "$1" "$2"
else
    echo $ECHO "$1 $2\c"
fi
}


# Reads user KB. Echos $1 as a prefix message
# Echo $2 as the prompt for the message
# if there is $3 then, empty response is considered as if $3 was specified
# else, a repeated request to specify an answer is prompted to the user.
PromptUser()
{
  if [ $# -eq 0 -o $# -eq 1 ]; then
    echo "ERROR: Script internal error."
    echo "       Wrong # of arguments specified to PrompUser"
    exit 1
  fi

  echo $1
  echo
  echo_n "$2" ": "
  stay=1
  while [ $stay = 1 ]
  do
     read PromptUser_Input
     if [ "A$PromptUser_Input" = "A" ]; then
      if [ $# = 3 ]; then
        PromptUser_Input="$3"
        stay=0
      else
        echo "Error: Must enter value."
      fi
    else
      stay=0
    fi
  done
  export PromptUser_Input
}

# 'Which' function
#
Which_Prog()
{
   program=$1
   for bindir in /bin /usr/bin /usr/ucb /usr/bsd ; do
       if [ -x $bindir/$program ] ; then
	  echo $bindir/$program
          break
       fi
   done
}

# Function expects a single variable. This should be Yes or No or any
# flavor of these values (Y/YES/Yes/yes N/NO/No/no)
# Function return 0 status code if the argument was a valid value, or
#                 1 if the argument was not part of the Y/N flavors.
#                 In the 2nd case the exported variable from the function
#                 contains the original value specified.
# The result itself is returned in an exported variable YesNoFlavours_result
# where the values are set to "yes" or to "no" in lower case.
Check_YesNoFlavours()
{
YES="yes"
NO="no"

  if [ $# != 1 ]; then
     echo "ERROR: Script internal error."
     echo "       An expected argument to Check_YesNoFlavours is missing."
     exit 1
  fi

  case $1 in
	Yes)
	      YesNoFlavours_result=$YES
	      status=0
	;;
	YES)
	      YesNoFlavours_result=$YES
	      status=0
	;;
	yes)
	      YesNoFlavours_result=$YES
	      status=0
	;;
	y)
	      YesNoFlavours_result=$YES
	      status=0
	;;
	Y)
	      YesNoFlavours_result=$YES
	      status=0
	      ;;
	No)
	      YesNoFlavours_result=$NO
	      status=0
	      ;;
	NO)
	      YesNoFlavours_result=$NO
	      status=0
	      ;;
	no)
	      YesNoFlavours_result=$NO
	      status=0
	      ;;
	n)
	      YesNoFlavours_result=$NO
	      status=0
	      ;;
	N)
	      YesNoFlavours_result=$NO
	      status=0
	      ;;
	*)
		echo "Error: You must enter Yes/No."
		YesNoFlavours_result=$1
		status=1
esac
  export YesNoFlavours_result
  return $status
}


# Prompts the user with a message, then present a question. The user
# is prompted on the same line to provide a response.
# Argument #1 is the echod message.
# Argument #2 is the prompted message.
# Argument #3 is an optional default that applies for an empty response from
#             the user.
# The response from the user is expected to be yes or no (all flavors).
# Function will continuously ask for a response from the user if the answer
# was not valid.
# Function will return status code of 0 on success or 1 if too few parameters.
# The value itself is returned in an exported variable PromptUserYN_Input.
# Valid returned values in this variable is according to Check_YesNoFlavours
# function.
PromptUserYN()
{
  if [ $# = 2 -o $# = 3 ]; then
    stay=1
    while [ $stay = 1 ]
    do
       PromptUser "$1" "$2" "$3"
       Check_YesNoFlavours $PromptUser_Input
       if [ $? != 0 ]; then
         stay=1
       else
         stay=0
       fi
    done
    PromptUserYN_Input=$YesNoFlavours_result
    export PromptUserYN_Input
    return 0
  fi
  return 1
}


# Simply clears screen
ClearScreen()
{
  clear
  if [ $# = 0 ]; then
    echo "              ------------------------------------"
    echo "                    $PRODNAME interactive setup"
    echo "              ------------------------------------"
  else
    echo "  -------------------[ $1 ]-------------------"
  fi
}


# The normal abort message and exit
AbortScript()
{
  echo "Aborting interactive setup"
  exit 1
}

WaitEnter()
{
  echo "Press ENTER to continue"
  read dummy
}

# Function tests if user is root or not
Check_ifroot()
{
ID=`id`

    uid=`expr "$ID" : "uid=0.*"`
    uidlen=`expr "$uid" : '.*'`
    if [ $uidlen -eq 1 ]
    then
        echo "ERROR: You are not root, You must be root to use this script."
        exit 1
    fi
}



# Function test if a daemon, supplied as parameter is running. If so the
# result is returned in a the status variable. 1 means running, 0 means not
Check_ifrunning()
{

  DAEMON=$1
  PSOPTIONS=-ea   # default
  if [ ! -x $SEOSDIR/lib/getvar.sh ]; then
    echo "ERROR: $PRODNAME could not find $SEOSDIR/lib/getvar.sh"
    AbortScript
  fi
  OS=`$SEOSDIR/lib/getvar.sh OS`
  status=1

  case ${OS} in
    _HPUX)
      PSOPTIONS=-ea
      ;;
    _AIX)
      PSOPTIONS=-ea
      ;;
    _SOLARIS)
      PSOPTIONS=-ea
      ;;
    _ATT)
      PSOPTIONS=-ea
      ;;
    _SUNOS)
      PSOPTIONS=-ax
      ;;
    *)
      echo $ECHO Unknown Operating system - $OS
      exit 1
  esac

  InputLines=`ps ${PSOPTIONS} | grep ${DAEMON} | grep -v grep`
  if [ "A${InputLines}" = "A" ]   # empty
  then
    status=0
  else
    status=1
  fi
  export status
  return $status
}


Check_IfNeedunderNIS()
{
OS=`$SEOSDIR/lib/getvar.sh OSVER`

        case $OS in
                _SOLARIS20)
                        status=0
                        ;;
                _SOLARIS21)
                        status=0
                        ;;
                _SOLARIS22)
                        status=0
                        ;;
                _SOLARIS23)
                        status=0
                        ;;
                _SOLARIS24)
                        status=0
                        ;;
                # Solaris 2.5 and above
                _SOLARIS2*)
                        status=1
                        ;;
        esac
        if [ $status = 1 ]; then
                export status
                return $status
        fi
        # Now check if one of the NIS/DNS is running in the system
        for daemon in "ypserv named rpc.nisd"
        do
                Check_ifrunning $daemon
                if [ $? = 1 ]; then
                        status=1
                        export status
                        return $status
                fi
        done
        status=0
        export status
        return $status
}
