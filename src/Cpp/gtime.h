
////////////////////////////////////////////////////////////////////////////
//   Module       :                                                       //
//   File         :  Gtime.h                                              //
//   Date         :                                                       //
//   Description  :                                                       //
//   Author       :  Oded Nissan                                          //
//                   Copyright (c) 1994-1998 Oded Nissan.                 //
//   History      :                                                       //
//------------------------------------------------------------------------//
//   Date         |   Description                                         //
//------------------------------------------------------------------------//
//   01/01/1998   |   Initial Release.                                    //
//                |                                                       //
//                |                                                       //
////////////////////////////////////////////////////////////////////////////
#ifndef _GTIME_H_
#define _GTIME_H_

// the GTime class
class GTime {
	unsigned char m_hour; 			// hour
	unsigned char m_min;			// minute
	unsigned char m_sec;			// second
	unsigned char m_hund;			// hundredth second
public:
//	GTime()  { }
	GTime(unsigned char hour=0,unsigned char min=0,
	      unsigned char sec=0,
	      unsigned char hund=0);		// constructor
	GTime(long time);			// conversion constructor
	~GTime()  {   };			// destructor
		// set day month and year
	inline void set_hour(unsigned char hour) { m_hour = hour; }
	inline void set_min(unsigned char min) { m_min = min; }
	inline void set_sec(unsigned char sec) { m_sec = sec; }
	inline void set_hund(unsigned char hund) { m_hund = hund; }

		// get day mointh and year
	inline unsigned char get_hour() const { return (m_hour); }
	inline unsigned char get_minute()   const { return (m_min); }
	inline unsigned char get_sec()  const { return (m_sec); }
	// check date validity
	int valid()  const;
	// print the date
	void print() const;
	// compare two GDate objects
	int compare(const GTime& t1, const GTime& t2);

	// operators
	friend ostream& operator<<(ostream& s, const GTime & t);
	GTime& operator=(const GTime& t);
	int operator==(const GTime& t) const;
	int operator!=(const GTime& t) const;
	int operator>(const GTime& t) { return(0 < compare(*this, t)); }
	int operator<(const GTime & t) { return(0 > compare(*this, t)); }

};

// the gdate class
struct gdate_tag {
	unsigned char days;
	char *name;
	};

	// vector holds month names and number of
	// days in each month.
static struct gdate_tag GDate_vec[] = {
	{0,"0"},
	{31,"January"},
	{28,"Fewbruary"},
	{31,"March"},
	{30,"April"},
	{31,"May"},
	{30,"June"},
	{31,"July"},
	{31,"August"},
	{30,"September"},
	{31,"October"},
	{30,"November"},
	{31,"December"}
	};



// the GDate class
class GDate {
	unsigned char m_day;			// day
	unsigned char m_month;			// month 
	unsigned m_year;			// year
public:
	// contructor
	GDate(unsigned char day=0, unsigned char month=0, unsigned year=0);
	// destructor
	~GDate() {  }
	// set day month and year
	inline void set_day(unsigned char day) { m_day = day; }
	inline void set_month(unsigned char month) { m_month = month; }
	inline void set_year(unsigned year) { m_year = year; }

	// get day mointh and year
	inline unsigned char get_month() const { return (m_month); }
	inline unsigned char get_day()   const { return (m_day); }
	inline unsigned char get_year()  const { return (m_year); }
	const char *get_month_name() const;
	// check date validity
	int valid()  const;
	// print the date
	void print() const;
	// compare two GDate objects
	int compare(const GDate& d1, const GDate& d2);

	// operators
	friend ostream& operator<<(ostream& s, const GDate& d);
	GDate& operator=(const GDate& d);
	int operator==(const GDate& d) const;
	int operator!=(const GDate& d) const;
	int operator>(const GDate& d) { return(0 < compare(*this, d)); }
	int operator<(const GDate& d) { return(0 > compare(*this, d)); }



};

#endif
