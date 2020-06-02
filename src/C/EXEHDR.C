#include <stdio.h>

typdef struct {
	char signature[2];
	unsigned num_bytes;
	unsigned num_pages;
	unsigned num_reloc;
	unsigned header_size;
	unsigned min_paragraphs;
	unsigned max_paragraphs;
	unsigned initial_ss;
	unsigned initial_sp;
	unsigned checksum;
	unsigned long ip;
	unsigned offset;
	unsigned overlay_num;
} exe_header;


main()
{
	printf("enter file : ");
	gets(file_name);
	read_exehdr(file_name);
}

