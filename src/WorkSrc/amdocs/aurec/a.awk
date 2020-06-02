#-------------------------------------------------------------
# File: .awk
# purpose : replace EXEC SQL INCLUDE statements with regular
# 	    C #include statements
# 
# Supervisor : Dmitry Perl.
# written by : Oded Nissan.
# Date Written: 20/06/1994.
#
#--------------------------------------------------------------

{
	old = "Oded";
	sub(/$old/,"ODED" );
        print $0
 }
