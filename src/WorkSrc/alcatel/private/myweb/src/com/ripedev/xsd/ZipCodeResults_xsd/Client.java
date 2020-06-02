package com.ripedev.xsd.ZipCodeResults_xsd;

public class Client {
	
	
	public static void main(String args[]) {
		
		
		System.out.println("in Client.main()");
		try {
			LocalTimeLocator locator = new LocalTimeLocator();
			LocalTimeSoap soap = locator.getLocalTimeSoap();
			String localTime =soap.localTimeByZipCode("20910");
			System.out.println("localtime= " + localTime);
		} catch(Exception e){
			e.printStackTrace();
		}
		
	}

}
