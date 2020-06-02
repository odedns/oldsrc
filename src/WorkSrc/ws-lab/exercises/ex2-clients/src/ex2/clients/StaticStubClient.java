/*
 * StaticStubClient.java
 * Created on 02/08/2004
 *
 */
package ex2.clients;
import ex2.stub.*;

/**
 * StaticStubClient
 * 
 * @author rank
 */
public class StaticStubClient {

	public static void main(String[] args) {
		// Invoke the weather service using the static stub created by the wsdl2java
		try {
			WeatherService srv = new WeatherServiceServiceLocator().getWeatherService();
			String s = srv.getForecast(1);
			System.out.println("got forcats = " + s);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
}
