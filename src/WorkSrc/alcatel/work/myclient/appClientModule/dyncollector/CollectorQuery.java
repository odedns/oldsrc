/**
 * Created on 09/01/2005
 * @author P0006439
 * @version $id$
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package dyncollector;

import java.util.*;

import onjlib.utils.ToStringBuilder;


public class CollectorQuery
{
	private static final String SEPARATOR = ".";
	private static final String PARAM_SEPARATOR = ",";
	private static final int  INDEX_OPEN = '(';
	private static final int  INDEX_END = ')';
	String query;
	Map paramMap;

	/**
	 * constructor
	 * @param s the query string.
	 */
	public CollectorQuery(String s)
	{
		query = s;
		paramMap = new HashMap();
	}

	/**
	 * add a parameter to  
	 * @param num the param number
	 * @param param the param value.
	 */
	public void addParam(int num, Object param)
	{
		String paramKey = "$" + num;
		paramMap.put(paramKey,param);	
	}
	
	/**
	 * parse the query and create a list of
	 * methoInfo objects containing methodnames 
	 * and their respective parameters.
	 * @return MethodInfo list.
	 */
	public List parseQuery()
	{
		StringTokenizer st = new StringTokenizer(query,SEPARATOR);
		ArrayList list = new ArrayList();		
		while(st.hasMoreTokens()) {
			String methodName = null;
			ArrayList paramList = null;
			String s = st.nextToken();
			int currInx = s.indexOf(INDEX_OPEN);
			if(currInx > -1) {				
				int lastInx = s.indexOf(INDEX_END);
				String s2 = s.substring(currInx, lastInx);
				paramList = parseParams(s2);
				methodName = s.substring(0,currInx);			
													
			} else {
				methodName = s;			
			}
			MethodInfo m = new MethodInfo(methodName);
			/*
			 * add the parameters for the method.
			 */
			if(paramList != null) {
				/*
				 * loop over params and retrieve
				 * params from hasmap.
				 */
				Iterator iter = paramList.iterator();
				while(iter.hasNext()) {
					String key = (String) iter.next();
					Object o = paramMap.get(key);
					m.addArgument(o.getClass());
					m.addParam(o);					
				}					
			}
			list.add(m);
			
		} // while
		return(list);
	}
	
	/**
	 * Parse arguments.
	 * @param s
	 * @return
	 */
	private ArrayList parseParams(String s)
	{
		
		String t = s.substring(1, s.length());
		ArrayList list = new ArrayList();
		StringTokenizer st = new StringTokenizer(t,PARAM_SEPARATOR);
		while(st.hasMoreTokens()) {
			String token = st.nextToken();
			list.add(token);
					
		}
		
		return(list);
	}
	
	
	/*
	 * main test driver
	 */
	 public static void main(String argv[])
	 {
	 	System.out.println("CollectorQuery..");
	 	CollectorQuery query = new CollectorQuery("getCovers.getInsuranceSum($1,$2,$3).getRate");
	 	query.addParam(1, new Integer(100));
	 	query.addParam(2,new String("foo"));
	 	query.addParam(3,new Double(1.333));
	 	List l = query.parseQuery();
	 	Iterator iter = l.iterator();
	 	while(iter.hasNext()) {
	 		MethodInfo m = (MethodInfo)iter.next();
	 		System.out.println("m=" + ToStringBuilder.toString(m));
	 	}
	 }
}
