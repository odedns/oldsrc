
#-------------------------------------------------------------
# File: cobcopy.awk
# purpose : scan COBOL source files for COPY statements and     
# 	    print the COPY files associated with the source.
# 
# Supervisor : Dmitry Perl.
# written by : Oded Nissan.
# Date Written: 20/06/1994.
#
#--------------------------------------------------------------


function get_copy()
{
	for(i = 1; i < NF; ++i) {
		if ($i ~ /COPY/) {
			f =  $(++i);
			return(f);
			}
        }
	return("");
}



#--------------------------------------------
# get copy files 
#--------------------------------------------

function scan_file(file_name) {
	a = getline < file_name;
	while ( a > 0) {

                f1 = get_copy();
		if (f1 != "") {
               		gsub(/"|.$/,"",f1);
			f = f1; 
			stat = file_exist(f);
			if (stat == 1) {
	   			printf("%s \ \n", myfile);
	    			scan_file(myfile);
               		} 
		}
	        a = getline < file_name;
        }
	close(file_name);
}

# end function 


#--------------------------------------------
# check command line args for search path
#--------------------------------------------
function parse_cmdline() {
 j = 0;
 for (i = 1; i < ARGC - 1; ++i) {
	path[j] = ARGV[i];
	++j;
 }
 psize = j;
 myfile = ARGV[i];
}



#--------------------------------------------
# check file existance in all path
#--------------------------------------------

function file_exist(file_name) {
 
# parse environment variable 
	f = interpet_var(file_name);
	stat = getline < f;
	if ( stat > 0) {
	 	myfile = f;
	   	return(1);
        }

# if copy statement contains a relative path

	pos = index(file_name,"/");
        if (pos > 1) {
		file_name = sprintf("%s/%s",curr_dir,file_name);
        }
	stat = getline < file_name;
	if ( stat > 0) {
	    myfile = file_name;
	    return(1);
        }


# search copy path for copy files

	for (i = 0; i <= psize; ++i) {
		p = sprintf("%s/%s",path[i] , file_name);
		stat = getline < p;
		if ( stat > 0) {
		    myfile = p;
		    return(1);
                }
        }
	return(0);
}
	

# end function 

#--------------------------------------------
# interpet_var 
#--------------------------------------------

function interpet_var(string) {
 	pos = index(string,"$");
	if (pos == 1) {
		pos = index(string,"/");
		if (pos >0 ) {
			s1 = substr(string,0,pos-1);
			s2 = substr(string,pos+1);
			gsub(/\$/,"",s1);
			var = ENVIRON[s1];
			string = sprintf("%s/%s",var, s2);
                } else {
			s1 = string;
			gsub(/\$/,"",s1);
			var = ENVIRON[s1];
			string = var;
		}

        }
	return(string);
}




#--------------------------------------------
# main program
#--------------------------------------------

BEGIN {
	parse_cmdline();
	curr_dir = ENVIRON["PWD"];
	printf("%s:\n",myfile);
        scan_file(myfile);
}
