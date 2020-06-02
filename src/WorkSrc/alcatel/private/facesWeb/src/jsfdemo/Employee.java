/**
 * Date: 21/02/2007
 * File: Employee.java
 * Package: jsfdemo
 */
package jsfdemo;

/**
 * @author Oded Nissan
 *
 */
public class Employee {

	private int id;
	private String firstName;
	private String lastName;
	private int status;
	private float salary;
	
	
	
	public Employee()
	{
		
	}



	public String getFirstName() {
		return firstName;
	}



	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getLastName() {
		return lastName;
	}



	public void setLastName(String lastName) {
		this.lastName = lastName;
	}



	public float getSalary() {
		return salary;
	}



	public void setSalary(float salary) {
		this.salary = salary;
	}



	public int getStatus() {
		return status;
	}



	public void setStatus(int status) {
		this.status = status;
	}
	
	
}
