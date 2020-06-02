
#include <stdio.h>
#include <rpcsvc/nis.h>

#define NUM_COLS(x) \
	(NIS_RES_OBJECT(x)->zo_data.objdata_u.en_data.en_cols.en_cols_len)

#define COL_VAL(x,col) \
	(ENTRY_VAL(NIS_RES_OBJECT(x),col))




main(int argc, char **argv)
{
	char *ypdomain;
	char search_name[256];
	char table_name[256];
	char query[256];
	nis_result *pres;
	entry_obj *pobj;
	int i;

	strcpy(table_name,"passwd.org_dir.");

	if(argc <2 ) {
		fprintf(stderr,"usage %s <user>\n",argv[0]);
		return(1);
	}

	printf("looking up %s\n",argv[1]);
	if(NULL == (ypdomain = nis_local_directory())) {
		printf("error getting nis_local_directory\n");
		exit(1);
	}

	printf("ypdomain = %s\n",ypdomain);
	strcat(table_name,ypdomain);
	pres = nis_lookup(table_name,0);
	if(pres && pres->status != NIS_SUCCESS) {
		nis_perror(pres->status,"error on nis_lookup\n");
		nis_freeresult(pres);
		exit(1);
	}
	printf("zo_name = %s\n",NIS_RES_OBJECT(pres)->zo_name);
	printf("zo_group= %s\n",NIS_RES_OBJECT(pres)->zo_group);
		
	pres = nis_first_entry(table_name);
	if(pres && pres->status != NIS_SUCCESS) {
		nis_perror(pres->status,"error on nis_lookup\n");
		nis_freeresult(pres);
		exit(1);
	}
	printf("zo_name = %s\n",NIS_RES_OBJECT(pres)->zo_name);
	printf("zo_group= %s\n",NIS_RES_OBJECT(pres)->zo_group);
		
  	sprintf(search_name,"[uid=%d],%s",252,table_name) ;
	/*
  	sprintf(search_name,"[name=%s],%s",argv[1],table_name) ;
	*/
  
  	pres = nis_list(search_name,0,0,0) ;

  	if (pres->status != NIS_SUCCESS)
    	{
	      nis_perror(pres->status, "uid not found in nis+ table") ;
	      (void) nis_freeresult(pres) ;
      		return 1 ;
    	}  
	printf("zo_name = %s\n",NIS_RES_OBJECT(pres)->zo_name);
	printf("zo_group= %s\n",NIS_RES_OBJECT(pres)->zo_group);
	printf("zo_owner= %s\n",NIS_RES_OBJECT(pres)->zo_owner);

	printf("cols = %d\n", NUM_COLS(pres));
	for(i=0; i < NUM_COLS(pres) -1 ; ++i) {
		/*
	printf("data = %s\n",pres->objects.objects_val->zo_data.objdata_u.en_data.en_cols.en_cols_val[i].ec_value.ec_value_val);

	*/
	printf("data[%d] = %s\n",i,ENTRY_VAL(NIS_RES_OBJECT(pres),i));
	}


}
