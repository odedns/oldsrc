
/*
 * Ternaray Search Trees
 * from Dr. Dobbs 4/1998
 *
 */

typedef struct tnode *Tptr;
typedef struct tnode {
	char splitchar;
	Tptr lokid, eqkid, hikid;
} Tnode;

/*
 * search for the string s in the
 * ternary tree starting at node p.
 * recursive version.
 */
int rsearch(Tptr p, char *s)
{
	if(!p) return 0;
	if(*s < p->splitchar) {
		return(rsearch(p->lokid,s));
	} else {
		if(*s > p->splitchar) {
			return(rsearch(p->hikid,s));
		} else {
			if(*s == '\0') {
				return(1);
			}
			return(rsearch(p->eqkid,++s));
		}
	}
}
	
	

/*
 * search for the string s in the
 * ternary tree starting at root node.
 * non recursive version of search
 */
int search(char *s)
{
	Tptr p;
	p = root;

	while(p) {
		if(*s < p->splitchar) {
			p = p->lokid;
		} else {
			if(*s == p->splitchar) {
				if(*s++ == '\0') 
					return(1);
				p = p->eqkid;
			} else {
				p = p->hikid;
			}
		}
	}
	return(0);
}
		
/*
 * insert a string into the ternary search tree.
 * recursive version.
 */
Tptr insert(Tptr p, char *s)
{
	if(p == 0) {
		p = (Tptr) malloc(sizeof(Tnode));
		p->splitchar = *s;
		p->lokid = p->eqkid = p->hikid = 0;
	}
	if(*s < p->splitchar) {
		p->lokid = insert(p->lokid,s);
	} else {
		if(*s == p->splitchar) {
			if(*s != '\0') 
				p->eqkid = insert(p->eqkid,++s);
		} else {
			p->hikid = insert(p->hikid,s);
		}
	}
	return(p);
}


/*
 * K&R hash function
 */

int hashfunc(int tabsize, char *s)
{
	unsigned n = 0;
	for(; *s; ++s) {
		n = 31 * n + *s;
	}
	return(n % tabsize);
}
