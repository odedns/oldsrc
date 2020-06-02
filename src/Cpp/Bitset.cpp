
#include <iostream.h>

#define DEF_SIZE  (4)
#define CALC_BYTES(bits) ( (bits / 8 ) + 1)
#define MIN(a,b) ( a > b ? a : b)
class BitSet {
	enum bool_actions { or, and , xor };
	long *m_vl;
	int m_nbits;

private: 
	void bool_action(int action, BitSet set)
	{
		nbytes = MIN(CALC_BYTES(m_nbits), set.length());
		for(int i = 0; i < nbytes; ++i) {

		}
	}
public:
	int length()
	{
		return(m_nbits);
	}
	BitSet()
	{
		m_vl = new long[4];
		for(int i=0; i < DEF_SIZE; ++i) {
			m_vl[i] = 0;
		}
		m_nbits = DEF_SIZE * 8;

	}
	BitSet(int nbits)
	{
		int nbytes = ( nbits / 8 ) + 1
		m_nbits = nbits;
		for(int i=0; i < nbytes; ++i) {
			m_vl[i] = 0;
		}
	}

	void clear(int bit)
	{
		long mask = 1;
		m_vl[0] &= ~(mask << bit);

	}
	int  get(int bit)
	{
		long mask = 1;
		m_vl[0] & (mask << bit);
	}

	void set(int bit)
	{
		long mask = 1;
		m_vl[0] |= (mask << bit);
	}

	// logical actions
	void and(BitSet set)
	{

	}
	void or(BitSet set);
	void xor(BitSet set);
};
