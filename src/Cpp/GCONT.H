
#ifndef _GCONTAINER_H_
#define _GCONTAINER_H_
class GContainer {

public:
	virtual int type() = 0;
	virtual const char *name() = 0;
	virtual int is_empty() = 0;
	virtual int empty() = 0;
};
#endif
