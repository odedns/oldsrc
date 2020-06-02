#include <iostream.h>
#include <fstream.h>

class concat_file {
	int m_flags;
	public:
		enum { shownumbers };
		concat_file(const char *fname);
		concat_file();
		~concat_file()  {  };

		void set_flags(int flags) { m_flags = flags; };

		int get_flags()  const { return m_flags; };
};

concat_file::concat_file(const char *fname)
{
char c;
int line_num = 0;
ifstream in;

	in.open(fname);
	while(in.good()) {
		in.get(c);
		cout << c;
		if('\n' == c && m_flags) {
			cout << line_num << '\t';
			++line_num;
		}
		
	}
	in.close();

}

concat_file :: concat_file()
{
char c;
int line_num = 0;

	while(cin.get(c)) {
		if(m_flags ) {
			if(!line_num) {
				++line_num;
				cout << line_num << "\t";
			 }
			 if(c == '\n') {
				cout << c;
				++line_num;
				cout << line_num << "\t";
			 }
		} else {
			cout << c;
		}

	}

}

int main(int argc, char **argv)
{
// concat_file f;
int i = 0;

	if(argc < 2) {
		concat_file f;
		return(1);
	}
	while(--argc) {

		concat_file f(argv[++i]);
	}
	return(0);
}

