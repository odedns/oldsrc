/**
 * This is a regular expression library.
 * The following regular expression symbols are supported:
 * the asterisk '*' - matches any one or more characters.
 * the wildcard '?' - matches any single character.
 * the set  -
 * sets are grouped within brackets ([]).
 * a set can be any combination of the following:
 * A group of characters :  	[abcdrttt]
 * A range of characters: 	[a-z] [a-z0-9]
 * Any combination of both: 	[a-z#$%&0-9]
 * Sets can be negated using the '^' or '!' chars :   [!abc] or [!a-z]
 *
 * In order to use the '[' ']' '^' and '!' chars in the set as regular
 * characters (ignore their special meaning) use the escape char '\'
 * for example :  [abc\^\!\]] 
 * uses special characters as regular characters in the set.
 * The asterisk and wildcard can also be escaped using the '\' character.
 *
 */
     
#include <stdio.h>
#include <string.h>

#define ASTERISK '*'
#define WILDCARD '?'
#define ESCAPE   '\\'
#define SET_START '['
#define SET_END   ']'
#define SET_RANGE '-'


#define REGEXP_MATCH            (0)
#define REGEXP_FAILED_CHAR      (-1)
#define REGEXP_FAILED_SET       (-2)
#define REGEXP_INVALID_PATTERN  (-3)
#define REGEXP_SHORT_PATTERN    (-4)


/**
 * regular expression info structure.
 * contains :
 * return code of regular expression matching function,
 * counters for:  
 * number of characters matched by a regular character
 * number of characters matched by an asterisk
 * number of characters matched by a wildcard.
 */
typedef struct{
	int r_ret_code;
	int r_matched_char;
	int r_matched_wildcard;
	int r_matched_asterisk
} regexp_info_t;


/**
 * match the character c to the set contained between 
 * set_start and set_end.
 * return true on match, false otherwise.
 */
static int regexp_match_set(char *set_start, char *set_end,char c);



static void print_regexp_info(regexp_info_t *reginfo)
{
	printf("r_ret_code = %d\n",reginfo->r_ret_code);
	printf("r_matched_char = %d\n",reginfo->r_matched_char);
	printf("r_matched_wildcard = %d\n",reginfo->r_matched_wildcard);
	printf("r_matched_asterisk = %d\n",reginfo->r_matched_asterisk);
}

/**
 * perform regular expression matching for the input
 * string s, by the regular expression pattern pattern.
 * returns 0 on success,
 * anyone of the following on failure:
 * REGEXP_FAILED_CHAR  	- match failed on regular character.     
 * REGEXP_FAILED_SET    - match failed on a set.
 * REGEXP_INVALID_PATTERN  - regular expression pattern is invalid.  
 * REGEXP_SHORT_PATTERN   - regular expression pattern too short.  
 */
int regexp_match(char *pattern, char *s)
{
	regexp_info_t reginfo;
	int rv;
	
	rv = regexp_match_ex(pattern,s,&reginfo);
	print_regexp_info(&reginfo);
	return(rv);
}

/**
 * perform regular expression matching for the input
 * string s, by the regular expression pattern pattern.
 * return regular expression info structure in reginfo.
 * returns 0 on success,
 * anyone of the following on failure:
 * REGEXP_FAILED_CHAR  	- match failed on regular character.     
 * REGEXP_FAILED_SET    - match failed on a set.
 * REGEXP_INVALID_PATTERN  - regular expression pattern is invalid.  
 * REGEXP_SHORT_PATTERN   - regular expression pattern too short.  
 */
int regexp_match_ex(char *pattern, char *s, regexp_info_t *reginfo)
{
	int matched_chars = 0;
	int cont = 1;
	int slen = strlen(s);
	char c;
	char *set_end;


	reginfo->r_ret_code     = 0;
	reginfo->r_matched_char = 0;
	reginfo->r_matched_wildcard = 0;
	reginfo->r_matched_asterisk = 0;

	printf("pattern = %s\nstring = %s\n",pattern,s);
	while(*pattern && *s && cont ) {
		if(matched_chars >= slen) {
			break;
		}
		c = *pattern;

		switch(c) {
			case WILDCARD:
				++matched_chars;
				++s;
				++pattern;
				reginfo->r_matched_wildcard++;
				break;
			case ASTERISK:
				c = *(++pattern);
				printf("in asterisk comparing c=%c\t*s=%c\n",
						c,*s);
				if( c != *s)  {
					--pattern;
				} else {
					++pattern;
				}
				++matched_chars;
				++s;
				reginfo->r_matched_asterisk++;
				break;

			case ESCAPE:
				c = *(++pattern);
				if(c == *s) {
					++matched_chars;
					++s;
					++pattern;
					reginfo->r_matched_char++;
				} else {
					cont = 0;
					reginfo->r_ret_code = REGEXP_FAILED_CHAR;
				}
				break;

			case SET_START:
				set_end = strchr(pattern,SET_END);
				if(NULL == set_end) {
					cont = 0;
					reginfo->r_ret_code =
						REGEXP_INVALID_PATTERN;
				} else {
					if(regexp_match_set(++pattern,
							set_end,*s)) {
						++matched_chars;
						++s;
						pattern = set_end +1;
					} else {
						cont = 0;
						reginfo->r_ret_code =
							REGEXP_FAILED_SET;
					}

				}
				break;
			default:
				if(c == *s) {
					++matched_chars;
					++s;
					++pattern;
					reginfo->r_matched_char++;
				} else {
					cont = 0;
					reginfo->r_ret_code = REGEXP_FAILED_CHAR;
				}
				break;

		}
	}
					
	if(matched_chars < slen) {
		if(!reginfo->r_ret_code) {
			reginfo->r_ret_code = REGEXP_SHORT_PATTERN;
		}
	} 


	return(reginfo->r_ret_code);
}


/**
 * match the character c to the set contained between 
 * set_start and set_end.
 * return true on match, false otherwise.
 */
static int regexp_match_set(char *set_start, char *set_end,char c)
{
	int match = 0;
	int done = 0;
	char *p = set_start;

	printf("in regexp_match_set = %s\t c = %c\n",set_start,c);
	while(p < set_end && !done) {

		switch(*p) {

			case ESCAPE:
				++p;
				if(*p == c) {
					match = !match;
					done = 1;
				}
				++p;
				break;
			case SET_RANGE: 
				if(*(p-1) <= c && *(++p) >= c) {
					match = !match;
					done = 1;
				}
				if(*p) {
					++p;
				}
				break;
			case '!':
			case '^':
				/* negate the set */
				match = !match;
				++p;
				break;
			default:
				if(*p == c) {
					match = !match;
					done = 1;
				}
				++p;
				break;
		}

	}
	printf("regexpt_match_set = %d\n", match);
	return(match);
			
}


int main(int argc, char **argv)
{
	int rv = 0;

	if(argc < 3) {
		printf("usage %s <pattern> <string>\n", argv[0]);
		return(1);
	}
	rv = regexp_match(argv[1],argv[2]);
	printf("pattern = %s\nstring = %s\nrv = %d\n",
			argv[1],argv[2],rv);
	return(0);
}
