#include <iostream.h>
#include <time.h>

#include "gtime.h"


GTime::GTime(unsigned char hour, unsigned char min,
	     unsigned char sec, unsigned char hund)
{
struct tm *t;
time_t curr_time;

	curr_time = time(NULL);
	t = localtime(&curr_time);

	m_hour = hour ? hour : t->tm_hour;
	m_min = min ? min : t->tm_min;
	m_sec = sec ? sec : t->tm_sec;
	m_hund = hund ? hund : 0;


}


GTime::GTime(long time)
{
struct tm *t;

	t = localtime(&time);
	m_hour = t->tm_hour;
	m_min =  t->tm_min;
	m_sec =  t->tm_sec;
	m_hund = 0;
}

// check date validity
int GTime::valid() const
{
int rv;
	rv = !(m_hour > 24 || m_min > 60 || m_sec > 59);
	return(rv);
}

void GTime::print()  const
{
	cout << (int)m_hour << ':' << (int)m_min << ':' << m_sec << endl;
}


int GTime::compare(const GTime& t1, const GTime& t2)
{
	if(t1.m_hour != t2.m_hour) {
		return(t1.m_hour - t2.m_hour);
	}
	if(t1.m_min != t2.m_min) {
		return(t1.m_min - t2.m_min);
	}
	if(t1.m_sec != t2.m_sec) {
		return(t1.m_sec - t2.m_sec);
	}

	return(t1.m_hund - t2.m_hund);
}

ostream& operator<<(ostream& s, const GTime& t)
{
	return(s << (int)t.m_hour << ':' << (int)t.m_min << ':' << (int) t.m_sec);
}


GTime& GTime::operator=(const GTime& t)
{
	m_hour = t.m_hour;
	m_min = t.m_min;
	m_sec = t.m_sec;
	m_hund = t.m_hund;

	return(*this);
}

int GTime::operator==(const GTime& t) const
{
	return(m_hour == t.m_hour &&
	       m_min == t.m_min &&
	       m_sec == t.m_sec &&
	       m_hund == t.m_hund);
}
int GTime::operator!=(const GTime& t) const
{
	return(m_hour != t.m_hour ||
	       m_min != t.m_min ||
	       m_sec != t.m_sec ||
	       m_hund != t.m_hund);
}

// gdate implementation
GDate::GDate(unsigned char day, unsigned char month, unsigned year)
{
struct tm *t;
time_t curr_time;

	curr_time = time(NULL);

	t = localtime(&curr_time);


	m_day = day ? day : t->tm_mday;
	m_month = month ? month : t->tm_mon;
	m_year = year ? year : t->tm_year;
	if(!(year % 4) ) {
	// update february days
		GDate_vec[2].days++;
	}
}

// check date validity
int GDate::valid() const
{
int rv;
	if(m_month > 12 || GDate_vec[m_month].days < m_day) {
		rv = 0;
	} else {
		rv = 1;
	}
	return(rv);
}

const char *GDate::get_month_name() const
{
	return(GDate_vec[m_month].name);
}

void GDate::print()  const
{
	cout << (int)m_day << '-' << (int)m_month << '-' << m_year << endl;
}

int GDate::compare(const GDate& d1, const GDate& d2)
{
	if(d1.m_year != d2.m_year) {
		return(d1.m_year - d2.m_year);
	}
	if(d1.m_month != d2.m_month) {
		return(d1.m_month - d2.m_month);
	}
	return(d1.m_day - d2.m_day);
}

ostream& operator<<(ostream& s, const GDate& d)
{
	return(s << (int)d.m_day << '-' << (int)d.m_month << '-' << d.m_year);
}


GDate& GDate::operator=(const GDate& d)
{
	m_day = d.m_day;
	m_month = d.m_month;
	m_year = d.m_year;
	return(*this);
}

int GDate::operator==(const GDate& d) const
{
	return(m_day == d.m_day &&
	       m_month == d.m_month &&
	       m_year == d.m_year);
}
int GDate::operator!=(const GDate& d) const
{
	return(m_day != d.m_day ||
	       m_month != d.m_month ||
	       m_year != d.m_year);
}

#ifdef _TEST
void main()
{
GDate d, d2(1);
GDate d1(11,2,1994);


	if(d.valid()) {
		d.print();
	} else {
		cout << "invalid date" << endl;
	}
	 cout << d  << endl;
}
#endif
