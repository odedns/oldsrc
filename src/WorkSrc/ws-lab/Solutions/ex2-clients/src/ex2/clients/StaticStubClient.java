/*
 * StaticStubClient.java
 * Created on 02/08/2004
 *
 */
package ex2.clients;

import ex2.stub.*;
import ex2.stub.WeatherService;

/**
 * StaticStubClient
 * 
 * @author rank
 */
public class StaticStubClient {

	public static void main(String[] args) {
		try {
			WeatherService service = (new WeatherServiceServiceLocator()).getWeatherService();
			
			System.out.println(service.getForecast(1));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
