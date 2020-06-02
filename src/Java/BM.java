
class BM
{
	// Copyright by Michael Lecuyer 1998.
	// This program may be used in any way you choose.
	// Please acknowledge my copyright where you use this in your program.
	// No need for a public copyright notice.

	private static final int MAXCHAR = 256;	// Maximum chars in character set.

	private byte pat[];	// Byte representation of pattern
	private int patLen;
	private int partial;	// Bytes of a partial match found at the end of a text buffer

	private int skip[];	// Internal BM table
	private int d[];		// Internal BM table

	/**
	* Boyer-Moore text search
	* <P> Scans text left to right using what it knows of the pattern
	* quickly determine if a match has been made in the text. In addition
	* it knows how much of the text to skip if a match fails.
	* This cuts down considerably on the number of comparisons between
	* the pattern and text found in pure brute-force compares
	* This has some advantages over the Knuth-Morris-Pratt text search.
	* <P>The particular version used here is
	* from "Handbook of Algorithms and Data
	* Structures", G.H. Gonnet & R. Baeza-Yates.
	*
	* Example of use:
	* <PRE>
	* String pattern = "and ";
	* <BR>
	* BM bm = new BM();
	* bm.compile(pattern);
	* 
	* int bcount;
	* int search;
	* while ((bcount = f.read(b)) >= 0)
	* {
	*    System.out.println("New Block:");
	*    search = 0;
	*    while ((search = bm.search(b, search, bcount-search)) >= 0)
	*    {
	*       if (search >= 0)
	*       {
	*          System.out.println("full pattern found at " + search);
	* <BR>
	*          search += pattern.length();
	*          continue;
	*       }
	*    }
	*    if ((search = bm.partialMatch()) >= 0)
	*    {
	*       System.out.println("Partial pattern found at " + search);
	*    }
	* }
	* </PRE>
	*/
	BM()
	{
		skip = new int[MAXCHAR];
		d = null;
	}

	/**
	* Compiles the text pattern for searching.
	*
	* @param pattern What we're looking for.
	*/
	public void compile(String pattern)
	{
		pat = pattern.getBytes();
		patLen = pat.length;

		int j, k, m, t, t1, q, q1;
		int f[] = new int[patLen];
		d = new int[patLen];

		m = patLen;
		for (k = 0; k < MAXCHAR; k++)
			skip[k] = m;

		for (k = 1; k <= m; k++)
		{
			d[k-1] = (m << 1) - k;
			skip[pat[k-1]] = m - k;
		}

		t = m + 1;
		for (j = m; j > 0; j--)
		{
			f[j-1] = t;
			while (t <= m && pat[j-1] != pat[t-1])
			{
				d[t-1] = (d[t-1] < m - j) ? d[t-1] : m - j;
				t = f[t-1];
			}
			t--;
		}
		q = t;
		t = m + 1 - q;
		q1 = 1;
		t1 = 0;

		for (j = 1; j <= t; j++)
		{
			f[j-1] = t1;
			while (t1 >= 1 && pat[j-1] != pat[t1-1])
				t1 = f[t1-1];
			t1++;
		}

		while (q < m)
		{
			for (k = q1; k <= q; k++)
				d[k-1] = (d[k-1] < m + q - k) ? d[k-1] : m + q - k;
			q1 = q + 1;
			q = q + t - f[t-1];
			t = f[t-1];
		}
	}


	/**
	* Search for the compiled pattern in the given text.
	* A side effect of the search is the notion of a partial
	* match at the end of the searched buffer.
	* This partial match is helpful in searching text files when
	* the entire file doesn't fit into memory.
	*
	* @param text Buffer containing the text
	* @param start Start position for search
	* @param length Length of text in the buffer to be searched.
	*
	* @return position in buffer where the pattern was found.
	* @see patialMatch
	*/
	public int search(byte text[], int start, int length)
	{
		int textLen = length + start;
		partial = -1;	// assume no partial match

		if (d == null)
			return -1;	// no pattern compiled, nothing matches.

		int m = patLen;
		if (m == 0)
			return 0;

		int k, j = 0;
		int max = 0;	// used in calculation of partial match. Max distand we jumped.

		for (k = start + m - 1; k < textLen;)
		{
			for (j = m - 1; j >= 0 && text[k] == pat[j]; j--)
				k--;

			if (j == -1)
				return k + 1;

			int z = skip[text[k]];
			max = (z > d[j]) ? z : d[j];
			k += max;
		}

		if (k >= textLen && j > 0)	// if we're near end of buffer --
		{
			partial = k - max - 1;

			return -1;	// not a real match
		}

		return -1;	// No match
	}


	/**
	* Returns the position at the end of the text buffer where a partial match was found.
	* <P>
	* In many case where a full text search of a large amount of data
	* precludes access to the entire file or stream the search algorithm
	* will note where the final partial match occurs.
	* After an entire buffer has been searched for full matches calling
	* this method will reveal if a potential match appeared at the end.
	* This information can be used to patch together the partial match
	* with the next buffer of data to determine if a real match occurred.
	*
	* @return -1 the number of bytes that formed a partial match, -1 if no
	* partial match
	*/
	public int partialMatch()
	{
		return partial;
	}

}
