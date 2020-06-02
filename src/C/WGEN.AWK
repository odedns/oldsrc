
{

#	print $0

	a = $0;
#	printf("case %s:\n perr = \"%s\";\nbreak;\n", $1, $a);
	printf("{%s, \"%s\"},\n", $1, $a);

}
