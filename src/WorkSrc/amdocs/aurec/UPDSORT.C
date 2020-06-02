
#include <stdio.h>


#define NUM_RECS (20)



#define NUM_RECS (20)
#define Bfso_MAX_GROUP 10
#define Bfso_MAX_KEY_LEN 50



typedef struct {
	char acKeyValue[Bfso_MAX_KEY_LEN + 1 + sizeof(short)];
	short shKeyLen;
	short shNumSeq;
	short ashSeq[Bfso_MAX_GROUP];
	short shItemId;
	short shIdx;
	} Bfso_SortItem_t;




void update_rec(FILE *fp , int rec_num);

void read_file(FILE *fp);

int main(int argc , char **argv)
{
FILE *input;

	if(argc < 2) {
		printf("usage %s <file>\n",argv[0]);
		return(2);
        }

	if(NULL == (input = fopen(argv[1],"r+"))) {
		fprintf(stderr,"can't open input file!!\n");
		exit(1);
        }


	read_file(input);
	update_rec(input,3);
	read_file(input);
	fclose(input);

}


void read_file(FILE *fp)
{
Bfso_SortItem_t inrec;
int stat = 0, total = 0;;

	stat = fread(&inrec, sizeof(Bfso_SortItem_t),1,fp);
	while(stat == 1 ) {
	   ++total;
	   printf("rec[%d] = %d\t%s\n",total,inrec.shNumSeq,inrec.acKeyValue);
		stat = fread(&inrec, sizeof(Bfso_SortItem_t),1,fp);
	}
	printf("Rec SIZE = %d\n",sizeof(Bfso_SortItem_t));
}

	
void update_rec(FILE *fp , int rec_num)
{
Bfso_SortItem_t inrec;
long offset = rec_num * sizeof(Bfso_SortItem_t);
int stat = 0;

	fseek(fp , offset, SEEK_SET);
	fread(&inrec, sizeof(Bfso_SortItem_t),1,fp);
	printf("rec[%d] = %d\t%s\n",rec_num,inrec.shNumSeq,inrec.acKeyValue);
        inrec.shNumSeq++;
	fseek(fp , offset, SEEK_SET);
	stat = fwrite(&inrec, sizeof(Bfso_SortItem_t),1,fp);
	printf("write stat = %d\n",stat);
	fseek(fp , 0 , SEEK_SET);

}
