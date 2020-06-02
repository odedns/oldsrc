package work;
import java.util.*;


public class T1 {

		public static void main(String args[])
		{
				Calendar c = Calendar.getInstance();
				System.out.println("c = " + c.toString());


				int day = c.get(Calendar.DAY_OF_MONTH);
				int month = c.get(Calendar.MONTH);
				int year = c.get(Calendar.YEAR);
				System.out.println("day=" + day + "\tmonth=" + month +
								"\tyear =" + year);

				Date d = new Date();
				day = d.getDate();
				month = d.getMonth();
				year = d.getYear();
				System.out.println("day=" + day + "\tmonth=" + month +
								"\tyear =" + year);


		}

}
