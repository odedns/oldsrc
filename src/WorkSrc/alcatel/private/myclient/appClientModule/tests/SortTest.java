/**
 * Date: 13/03/2007
 * File: SortTest.java
 * Package: tests
 */
package tests;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ComparatorUtils;

/**
 * @author a73552
 *
 */
class Person  implements Comparable<Person>{ 
	private String firstName;
	private String lastName;
	final int BEFORE = -1;
    final int EQUAL = 0;
    final int AFTER = 1;
    
	Person(String firstName, String lastName)
	{
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public String toString()
	{
		return(lastName + '-' + firstName);
	}

	public int compareTo(Person o) {
		// TODO Auto-generated method stub
		return(this.lastName.compareTo(o.lastName));
	}

}


public class SortTest {
	


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<Person> list = new ArrayList<Person>();
		list.add(new Person("Joe", "Cool"));
		list.add(new Person("Bill", "Buffalo"));
		list.add(new Person("John", "Correy"));
		list.add(new Person("Harry", "Bosch"));
		Comparator comp = ComparatorUtils.naturalComparator();
		Collections.sort(list);
		printList(list);
		
		
	}

	private static void printList(List l)
	{
		Iterator iter = l.iterator();
		while(iter.hasNext()) {
			Person p = (Person) iter.next();
			System.out.println(p.toString());
		}
	}
}
