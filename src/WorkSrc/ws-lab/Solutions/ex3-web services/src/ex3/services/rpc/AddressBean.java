package ex3.services.rpc;

public class AddressBean implements java.io.Serializable {
	
	private String street;
	private int postcode;

	public AddressBean() {
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public int getPostcode() {
		return postcode;
	}

	public void setPostcode(int postcode) {
		this.postcode = postcode;
	}
}