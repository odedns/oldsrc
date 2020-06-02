#include <stdio.h>
#include <stdlib.h>
#include <rpcsvc/nis.h>
#include <rpcsvc/nislib.h>
#include <string.h>
#include <pwd.h>

static char *string_it(long l)
{
    static char buff[10] ;
    
    sprintf(buff,"%li",l) ;
    
    return buff ; 
    
}

int put_in_entry(*entry_col ec, char *data) 
{
    if ((ec->ec_value.ec_value_val = strdup(data)) == NULL) 
      {
          fprintf(stderr,"out of memory\n") ;
          return 1 ;
          
      }
    
    ec->ec_value.ec_value_len = strlen(data) + 1;
    return 0 ;    
}


void delete_entry(entry_col ec) 
{
    if (ec.ec_value.ec_value_val) 
      free(ec.ec_value.ec_value_val) ;
}

/* add a user to nis+ passwd table in the current nis+ domain.
   returns 1 on normal failure, 
           2 on strdup failure,
           0 on success.
*/
int nisplus_add_user (struct passwd *p) 
{
  nis_result *cres ;
  nis_object *entdata ;
  int         i;

  entry_col ent_col_data[8] ;
  char table_name[100] = {"passwd.org_dir."};
  char owner_name[100] = {""};
  entry_col *old_col ;
  zotypes   old_type;
  nis_name   old_owner;

  strcat(table_name,(char *) nis_local_directory()) ;
  strcpy(owner_name,p->pw_name) ;
  strcat(owner_name,".") ;
  strcat(owner_name,(char *) nis_local_directory()) ;
  
  cres = nis_lookup (table_name,0) ;
  if (cres->status != NIS_SUCCESS)
    {
      nis_perror(cres->status, "unable to read nis+ password table") ;
      (void) nis_freeresult(cres) ;
      return 1 ;
    }

  entdata = nis_clone_object(NIS_RES_OBJECT(cres),0);
/*
  entdata.zo_name = NIS_RES_OBJECT (cres)->zo_name ;
  entdata.zo_owner = owner_name;
  entdata.zo_group = NIS_RES_OBJECT (cres)->zo_group ;
  entdata.zo_domain = NIS_RES_OBJECT (cres)->zo_domain ;
  entdata.zo_access = 65536U ;  
  entdata.zo_ttl = NIS_RES_OBJECT (cres)->zo_ttl ; 
  
  entdata.zo_data.zo_type = ENTRY_OBJ ;
  entdata.EN_data.en_type = NIS_RES_OBJECT (cres)->EN_data.en_type ;
  entdata.EN_data.en_cols.en_cols_len = 8 ;
*/

  old_type = entdata->zo_data.zo_type ;
  entdata->zo_data.zo_type = ENTRY_OBJ ;
  old_owner = entdata->zo_owner ;
  entdata->zo_owner=strdup(owner_name);
/*  entdata->zo_access = 65536U ;  /* ??? */

  (void) nis_freeresult(cres) ;

  if (put_in_entry(&ent_col_data[0],p->pw_name)) 
    return 2 ;
  if (put_in_entry(&ent_col_data[1],p->pw_passwd))
    return 2 ;
  if (put_in_entry(&ent_col_data[2],string_it(p->pw_uid)))
    return 2 ;
  if (put_in_entry(&ent_col_data[3],string_it(p->pw_gid)))
    return 2 ;
  if (put_in_entry(&ent_col_data[4],p->pw_gecos))
    return 2 ;
  if (put_in_entry(&ent_col_data[5],p->pw_dir))
    return 2 ;
  if (put_in_entry(&ent_col_data[6],p->pw_shell))
    return 2 ;
  if (put_in_entry(&ent_col_data[7],""))
    return 2 ;
  
  old_col = entdata->EN_data.en_cols.en_cols_val ;
  entdata->EN_data.en_cols.en_cols_val = &ent_col_data[0] ;  

  cres = nis_add_entry(table_name,entdata,0);

  for(i=0;i<8;i++) 
    delete_entry(ent_col_data[i]);

  entdata->zo_data.zo_type = old_type ;
  entdata->EN_data.en_cols.en_cols_val = old_col ;
  entdata->zo_owner = old_owner ;  

  nis_destroy_object(entdata);

  if (cres->status != NIS_SUCCESS)
    {
      nis_perror(cres->status, "unable to add nis+ user") ;
      (void) nis_freeresult(cres) ;
      return 1 ;
    }
  
  (void) nis_freeresult(cres) ;
    
  return 0 ;
  
}

/* remove a user with the given uid from the passwd nis+ table, in
   the current nis+ domain 
*/
int nisplus_remove_user(char *name) 
{
  nis_result *cres ;
  nis_object *entdata ;
  int         i;

  entry_col ent_col_data[8] = { 0, 0, 0, 0, 0, 0, 0, 0 };
  char table_name[100] = {"passwd.org_dir."};
  char search_name[100]  ;
  char owner_name[100] = {""};

  strcat(table_name,(char *) nis_local_directory()) ;
    
  sprintf(search_name,"[name=%s],%s",name,table_name) ;
  
  cres = nis_list(search_name,0,0,0) ;

  if (cres->status != NIS_SUCCESS)
    {
      nis_perror(cres->status, "uid not found in nis+ table") ;
      (void) nis_freeresult(cres) ;
      return 1 ;
    }  
 
  entdata = nis_clone_object(NIS_RES_OBJECT(cres),0);
  (void) nis_freeresult(cres) ;

  cres = nis_remove_entry(table_name,entdata,0) ;
  nis_destroy_object(entdata);

  if (cres->status != NIS_SUCCESS)
    {
      nis_perror(cres->status, "unable remove nis+ user") ;
      (void) nis_freeresult(cres) ;
      return 1 ;
    }
  
  return 0 ;
  
}

int main(int argc, char **argv) 
{
    struct passwd pwd ;
    
    if (argc == 2) 
      return nisplus_remove_user(argv[1]) ;
    
    pwd.pw_name = argv[1] ;
    pwd.pw_passwd = strdup("km.GaCWB3e7YI") ;
    pwd.pw_uid = atol(argv[2]);
    pwd.pw_gid = 201 ;
    pwd.pw_gecos = strdup("no name") ;
    pwd.pw_dir = strdup("/home") ;
    pwd.pw_shell = strdup("/usr/local/bin/tcsh") ;
    
    return nisplus_add_user(&pwd) ;
}






