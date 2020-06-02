package problems;

public class PermTest {

	/**
	 * print all combinations of the input String.
	 * @param prefix the prefix to which we append the remainder of the string.
	 * @param s the string.
	 */
	private static void printCombinations(String prefix, String s)
	{
		System.out.println(prefix);
		for(int i=0; i < s.length(); ++i) {
			printCombinations(prefix + s.charAt(i), s.substring(i+1));
		}
		
	}
	
	
	private static void combine(String s)
	{
		String prefix = "";	
		for(int i=0; i < s.length(); ++i) {
			prefix = "" + s.charAt(i);
			System.out.println(prefix);
			for(int j=i+1; j < s.length(); ++j){
				String tmp = prefix + s.substring(j);
				System.out.println(tmp);
			}
		}
	}
	
	
	private static void combine2(String s)
	{
		String prefix = "";	
		for(int i=0; i < s.length(); ++i) {
			prefix = "" + s.charAt(i);
			System.out.println(prefix);
			for(int j=i+1; j < s.length(); ++j){
				prefix = prefix + s.charAt(j);
				System.out.println(prefix);
			}
		}
			
	}
	 public static void comb2(String s) { comb2("", s); }
	    private static void comb2(String prefix, String s) {
	        System.out.println(prefix);
	        for (int i = 0; i < s.length(); i++)
	            comb2(prefix + s.charAt(i), s.substring(i + 1));
	    }  

	/**
	 * print all permutations of the string in c[].
	 * @param c the input char array.
	 */
	public static void permute(char c[])
	{
		int len = c.length;
		int first, skip;
		for(int i=0; i < len;++ i) {
			first = i;
			for(int j=0; j < len;++j) {
				skip = j;
				if(skip == first){
					continue;
				}
				System.out.print(c[first]);
				for(int k=0; k < len; ++k ) {
					if(k != first && k != skip) {
						System.out.print(c[k]);
					}
				}
				System.out.println(c[skip]);				
				
			}
			
		}
		
		
		
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		char c[] = {'a','b','c','d','e'};
		//permute(c);
		
		
		String s = "xyz";
		//printCombinations("",s);
		comb2(s);

	}

}
