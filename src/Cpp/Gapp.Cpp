
#include <iostream.h>

class gApp {
	const char *m_name;
	int  m_argc;
	char **m_argv;

public:
	gApp()  { }
	gApp(const char *app_name)  { m_name = app_name; }
	virtual void run(int argc =0, char **argv = NULL) =0;
};

class myapp : public gApp {

public:
	void run(int argc =0 , char **argv= NULL);
};

 void myapp::run(int argc , char **argv)
{
	cout << "running: " <<  '\n';
	if(argv) {
		cout << "argv[0] = " << argv[0];
	}

}

void main(int argc, char **argv)
{

  myapp a;

	a.run(argc,argv);

}

