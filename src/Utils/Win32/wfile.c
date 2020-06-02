/*------------------------------------------------------------------------*/
/*   Module       :  Wfile.exe                                            */
/*   File         :  Wfile.c                                              */
/*   Date         :  14/06/1998                                           */
/*   Description  :  Display  win32 file versions info.                   */
/*   Author       :  Oded Nissan                                          */
/*                   Copyright (c) 1994-1998 Oded Nissan.                 */
/*   History      :                                                       */
/*------------------------------------------------------------------------*/
/*   Date         |   Description                                         */
/*------------------------------------------------------------------------*/
/*   13/06/1998   |   Initial Release.                                    */
/*                |                                                       */
/*                |                                                       */
/*------------------------------------------------------------------------*/
#include <stdio.h>
#include <windows.h>

#include "werror.h"

void help();
void file_version(char *fname);

int main(int argc, char **argv)
{

	int i;

	if(argc < 2 || !strcmp("-h",argv[1])) {
		help();
		return(1);
	}
	
	for(i=1; argv[i] != NULL; ++i) {

		file_version(argv[i]);
	}

	return(0);

}


void file_version(char *fname)
{
	DWORD vsize, dummy,langLen;
	LPVOID pver;
	LPWORD langInfo;
	LPVOID verInfo;
	char version_str[MAX_PATH];

	vsize = GetFileVersionInfoSize(fname,&dummy);
	if(vsize < 0) {
		win_perror("GetFileVersionInfoSize");
		exit(1);
	}


	pver = malloc(vsize);

	if(!GetFileVersionInfo(fname,0,vsize,pver)) {
		win_perror("GetFileVersionInfo");
		exit(1);
	}

	if(!VerQueryValue(pver,
	"\\VarFileInfo\\Translation",&langInfo,(unsigned int *)&langLen)) {
		win_perror("VerQueryValue");
		exit(1);
	}


	printf("FileInfo for %s:\n",fname);

	sprintf(version_str,"\\StringFileInfo\\%4.4X%4.4X\\FileVersion",
		    langInfo[0],langInfo[1]);
	if(!VerQueryValue(pver,version_str,&verInfo,&vsize)) {
		win_perror("VerQueryValue");
		exit(1);
	}
	printf("%-20s:\t%s\n","FileVersion",verInfo);

	sprintf(version_str,"\\StringFileInfo\\%4.4X%4.4X\\FileDescription",
		    langInfo[0],langInfo[1]);
	if(!VerQueryValue(pver,version_str,&verInfo,&vsize)) {
		win_perror("VerQueryValue");
		exit(1);
	}
	printf("%-20s:\t%s\n","FileDescription",verInfo);

	sprintf(version_str,"\\StringFileInfo\\%4.4X%4.4X\\LegalCopyright",
		    langInfo[0],langInfo[1]);
	if(!VerQueryValue(pver,version_str,&verInfo,&vsize)) {
		win_perror("VerQueryValue");
		exit(1);
	}
	printf("%-20s:\t%s\n","Legalcopyright",verInfo);
	
	sprintf(version_str,"\\StringFileInfo\\%4.4X%4.4X\\CompanyName",
		    langInfo[0],langInfo[1]);
	if(!VerQueryValue(pver,version_str,&verInfo,&vsize)) {
		win_perror("VerQueryValue");
		exit(1);
	}
	printf("%-20s:\t%s\n","CompanyName", verInfo);

	sprintf(version_str,"\\StringFileInfo\\%4.4X%4.4X\\InternalName",
		    langInfo[0],langInfo[1]);
	if(!VerQueryValue(pver,version_str,&verInfo,&vsize)) {
		win_perror("VerQueryValue");
		exit(1);
	}
	printf("%-20s:\t%s\n","InternalName", verInfo);

	sprintf(version_str,"\\StringFileInfo\\%4.4X%4.4X\\OriginalFilename",
		    langInfo[0],langInfo[1]);
	if(!VerQueryValue(pver,version_str,&verInfo,&vsize)) {
		win_perror("VerQueryValue");
		exit(1);
	}
	printf("%-20s:\t%s\n","OriginalFilename",verInfo);

	sprintf(version_str,"\\StringFileInfo\\%4.4X%4.4X\\ProductName",
		    langInfo[0],langInfo[1]);
	if(!VerQueryValue(pver,version_str,&verInfo,&vsize)) {
		win_perror("VerQueryValue");
		exit(1);
	}
	printf("%-20s:\t%s\n","ProductName",verInfo);

	sprintf(version_str,"\\StringFileInfo\\%4.4X%4.4X\\ProductVersion",
		    langInfo[0],langInfo[1]);
	if(!VerQueryValue(pver,version_str,&verInfo,&vsize)) {
		win_perror("VerQueryValue");
		exit(1);
	}
	printf("%-20s:\t%s\n","ProductVersion", verInfo);

}

void help()
{
char Msg[] = "Wfile version 1.0\n"
	     "Display file information for win32 executables and dlls\n"
	     "Copyright (c) 1998 Oded Nissan\n" 
	     "Usage: wfile [-h] [files...]\n";

	fprintf(stderr, Msg);
}

