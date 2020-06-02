

class IntArray {
	int m_buf[];
	int m_cnt;
	int m_capacity;

	IntArray(int capacity)
	{
		m_buf = new int[capacity];
		m_capacity = capacity;
		m_cnt = 0;
	}

	IntArray()
	{
		m_buf = new int[10];
		m_capacity = capacity;
		m_cnt = 0;

	}

	int get(int index)
	{
		return(m_buf[index]);
	}

	void put(int n)
	{
		m_buf[m_cnt++] = n;
	}

	int remove(int index)
	{
		int n = m_buff[index];
		return(n);
	}

}
