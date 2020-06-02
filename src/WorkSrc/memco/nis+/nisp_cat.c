

#include <stdio.h>
#include <rpcsvc/nis.h>

#define NUM_COLS(x) \
	(NIS_RES_OBJECT(x)->zo_data.objdata_u.en_data.en_cols.en_cols_len)

#define COL_VAL(x,col) \
	(ENTRY_VAL(NIS_RES_OBJECT(x),col))

int nisp_lookup(char *key);


int main(int argc, char **argv)
{
	char *ypdomain;
	char table_name[256];
	char search_name[256];
	nis_result *pres;


	if(argc <2 ) {
		fprintf(stderr,"usage %s <table> <key> \n",argv[0]);
		return(1);
	}

	printf("looking up %s\n",argv[1]);
	if(NULL == (ypdomain = nis_local_directory())) {
		printf("error getting nis_local_directory\n");
		exit(1);
	}


	sprintf(table_name,"%s.%s.%s",argv[1],"org_dir",ypdomain);
	printf("table_name = %s\n", table_name);


	/*
	pres = nis_lookup(table_name,0);
	if(pres && pres->status != NIS_SUCCESS) {
		nis_perror(pres->status,"error on nis_lookup\n");
		nis_freeresult(pres);
		exit(1);
	}
	printf("zo_name = %s\n",NIS_RES_OBJECT(pres)->zo_name);
	printf("zo_group= %s\n",NIS_RES_OBJECT(pres)->zo_group);
	*/
		

	if(!strcmp(argv[1],"passwd")) {
  		sprintf(search_name,"[uid=%d],%s",atoi(argv[2]),table_name) ;
	}
	if(!strcmp(argv[1],"hosts")) {
  		sprintf(search_name,"[addr=%s],%s",argv[2],table_name) ;
	}
	if(!strcmp(argv[1],"group")) {
  		sprintf(search_name,"[gid=%d],%s",atoi(argv[2]),table_name) ;
	}
	if(!strcmp(argv[1],"services")) {
  		sprintf(search_name,"[port=%d],%s",atoi(argv[2]),table_name);
	}

	nisp_lookup(search_name);
	return(0);
}


int nisp_lookup(char *key)
{
	nis_result *pres;
	entry_obj *pobj;
	int i;
		
  
  	pres = nis_list(key,0,0,0) ;

  	if (pres->status != NIS_SUCCESS)
    	{
	      nis_perror(pres->status, "uid not found in nis+ table") ;
	      (void) nis_freeresult(pres) ;
      		return 1 ;
    	}  

	printf("zo_name = %s\n",NIS_RES_OBJECT(pres)->zo_name);
	printf("zo_group= %s\n",NIS_RES_OBJECT(pres)->zo_group);
	printf("zo_owner = %s\n",NIS_RES_OBJECT(pres)->zo_owner);
	printf("cols = %d\n", NUM_COLS(pres));
	for(i=0; i < NUM_COLS(pres) -1 ; ++i) {
		printf("data[%d] = %s\n",i,ENTRY_VAL(NIS_RES_OBJECT(pres),i));
	}

	return(0);

}
