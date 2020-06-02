package ex3.services.rpc;

public class AddressBean {
	String city;
	String street;
	int num;

	/**
	 * @return Returns the city.
	 */
	public String getCity() {
		return city;
	}
	/**
	 * @param city The city to set.
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * @return Returns the num.
	 */
	public int getNum() {
		return num;
	}
	/**
	 * @param num The num to set.
	 */
	public void setNum(int num) {
		this.num = num;
	}
	/**
	 * @return Returns the street.
	 */
	public String getStreet() {
		return street;
	}
	/**
	 * @param street The street to set.
	 */
	public void setStreet(String street) {
		this.street = street;
	}
}