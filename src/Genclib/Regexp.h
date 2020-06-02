
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


#ifndef _REGEXP_H
#define _REGEXP_H
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
	int r_matched_asterisk;
} regexp_info_t;



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
int regexp_match(char *pattern, char *s);

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
int regexp_match_ex(char *pattern, char *s, regexp_info_t *reginfo);

#endif
