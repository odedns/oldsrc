
import java.io.*;
import java.util.StringTokenizer;
import java.util.Vector;


class DBMapping {
	InvoiceData m_invData;
	InvoicePsotioData m_pos;
	InvoiceCallData m_call;
	
	void map(String segmentName, Hashtable h)
	{
		int state = pushState(segmentName);
		switch(state) {
			case INV_POSITION:
				// store previous invoice position data.
				// map fields into InvoicePostion, simcard etc..
				invpost.amount = h.get("AMT");
				invpos.dienst = h.get("
				break;
			
			case INV_CALL:
			// store previous invoice call data.
				// map fields into InvoiceCall,Simcard etc..
				break;
			
			case INV_SUMMARY:
			// store previous invoice call data.
				// map fields into invoiceDate
				break;
			case HEADER:
				break;
			case TRAILER:
			// store previous invoice call data.
				break;
			default:
				thrown new Exception("..");
		}
		
		
	int pushState(String segName);
	// store data into db.
	void store(int who);
	
}


class EDIFACTSegment {
	static final char SEGMENT_TERMINATOR = ''';
	static final char SEGMENT_SEPARATOR  = '+';
	String m_data;
	String m_name;
	Vector v;
	Hashtable m_hash;

	EDIFACTSegment(StringBuffer sb)
	{
		m_data = sb.toString();
		v = new Vector(10);
		/* parse the SEGMENT */
		parse();
	}
	EDIFACTSegment(String s)
	{
		m_data = s;
		v = new Vector(10);
		/* parse the SEGMENT */
		parse();
	}

	void parse()
	{
		StringTokenizer st = new StringTokenizer(m_data,"+");
		String token = null;
		while(st.hasMoreElements()) {
			token = st.nextToken();
			v.addElement(token);
		}

		EDIFACTDict d = new EDIDict();
		m_hash = d.getFields(v);
	}

	String getName()
	{
		return(v.elementAt(0));
	}
	Hashtable getFields()
	{
		return(m_hash);
		//return(v.toArray());
	}
	public String toString()
	{
		return(v.toString());
	}

}


class EDIFACTInputStream {
	BufferedReader m_in;

	EDIFACTInputStream(String fname)
		throws IOException
	{
		m_in = new BufferedReader(new FileReader(fname));

	}

	EDIFACTSegment read()
		throws IOException
	{
		StringBuffer sb = new StringBuffer(100);
		int c = m_in.read();
		while(c != -1 && EDIFACTSegment.SEGMENT_TERMINATOR != (char)c) {
			sb.append((char)c);
			c= m_in.read();
		}
		return(sb.length() > 0 ? new EDIFACTSegment(sb): null);
	}
	BufferedReader getIOHandle()
	{
		return(m_in);
	}

	void dumpFile()
		throws IOException
	{
		String s = m_in.readLine();
		while(null != s) {
			System.out.println(s);
			s = m_in.readLine();
		}
	}

	void close()
		throws IOException
	{
		m_in.close();
	}
	public static void main(String argv[])
	{
		EDIFACTSegment seg = null;
		try {
			EDIFACTInputStream edi = new EDIFACTInputStream("EDI.dat");
			while(null != (seg = edi.read())) {
				System.out.println(seg.toString());
				Hashtable h = seg.getFields();
				/*
				 * map fields into dl classes and store in DB.
				 */
				dbmapping.map(h);
			
				
			}
			
			edi.close();

//			edi.dumpFile();
		} catch (IOException ie) {
			ie.printStackTrace();
		}


	}


}

