#include <iostream.h>
#include <iomanip.h>


void func1()
{
	throw "bullshit";
}


/*
 *
 *
 *
 */
main()
{
int n = 10;
float f = 4.5;
int old_options;

	cout << "Test\n";
	cout << "n = " << n << "\tf = " << f << '\n';

	cout.setf(ios::oct,ios::basefield);
	cout << 1234 << ' ';

	// print hex with 0x and leading zeros.
//	cout.setf(ios::hex,ios::basefield);
  //	cout.setf(ios::showbase);
    //	cout.width(6);
      //	cout.fill('0');
//	cout.setf(ios::internal,ios::adjustfield);

	cout << setiosflags(ios::hex | ios::basefield |
			    ios::showbase | ios::internal) << 1234 << ' ';
	cout << 1234 << ' ';

	try {
		func1();
	} catch (char *err) {
		cerr << endl << err << endl;
	}


}
