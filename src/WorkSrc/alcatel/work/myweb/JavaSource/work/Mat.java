package work;

public class Mat {

	public static void main(String args[])
	{
		Integer mat[][] = new Integer[10][10];

		for(int i=0; i < 10; ++i) {
			mat[i] = getIntArray();
		}


		for(int i=0; i < 10; ++i) {
			for(int j=0; j < 10; ++j) {
				System.out.println("mat[" + i + "][" + j + "]=" + mat[i][j]);
			}
		}
				
		
	}



	static Integer[] getIntArray()
	{
		Integer arr[] = new Integer[10];

		for(int i=0; i < 10; ++i) {
			arr[i] =  new Integer(i);
		}
		return(arr);
	}

}
