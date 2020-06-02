#include <stdio.h>
#include <dos.h>

typedef struct {
	unsigned char b;
	unsigned char oem_code;
	unsigned int  id;
	char bios_date[8];
	unsigned int dummy;
	char bios_ver[8];
	} tbios_info_t;

#define TBUFSIZ (sizeof(tbios_info_t) -2)

void print_info(char *info)
{
int i;
tbios_info_t t;


	t.b = *info++;
	t.oem_code = *info++;
	memcpy(&(t.id), info,sizeof(int));
	info+=sizeof(int);
	for(i=0; i < 8; ++i) {
		t.bios_date[i] = *info++;
	}
	t.bios_date[i] = '\0';
	memcpy(&(t.dummy),info,sizeof(int));
	info+=sizeof(int);
	for(i=0; i < 8; ++i) {
		t.bios_ver[i] = *info++;
	}
	t.bios_ver[i] = '\0';
	printf("oem =%02X\n",t.oem_code);
	printf("id = %04X\n",t.id);
	printf("date = %s\n",t.bios_date);
	printf("version = %s\n",t.bios_ver);
}

int trident_info()
{
int rv = 0;
tbios_info_t info;

	_AH = 0x12;
	_BL = 0x11;
	/*
	_ES = FP_SEG(&info);
	_BP = FP_OFF(&info);
	*/
	geninterrupt(0x10);
	printf("ES = %04X\nBP = %04X\n",_ES,_BP);
	rv = (_AL == 0x12 ? 0 : -1);
	return(rv);
	
}

main()
{
	if(!trident_info())  
		puts("success");

}

