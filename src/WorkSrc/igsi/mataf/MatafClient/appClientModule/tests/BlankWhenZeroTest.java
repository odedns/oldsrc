package tests;

import mataf.decorator.BlankWhenZeroDecorator;
/**
 * @author O702998
 *
 * Class: BlankWhenZeroTest.java
 * Description:
 * Created at: Dec 16, 2003
 * 
 */
public class BlankWhenZeroTest {

	public static void main(String[] args) {
		BlankWhenZeroDecorator bzd = new BlankWhenZeroDecorator();
		String input;
		input = new String("00000");
		System.out.println("*"+input+"*"+bzd.addDecoration(input)+"*");
		input = new String("0.00");
		System.out.println("*"+input+"*"+bzd.addDecoration(input)+"*");
		input = new String("  00");
		System.out.println("*"+input+"*"+bzd.addDecoration(input)+"*");
		input = new String("00  ");
		System.out.println("*"+input+"*"+bzd.addDecoration(input)+"*");
		input = new String("    0.00");
		System.out.println("*"+input+"*"+bzd.addDecoration(input)+"*");
		input = new String("fsfdsf");
		System.out.println("*"+input+"*"+bzd.addDecoration(input)+"*");
		input = new String("0,000,000.00");
		System.out.println("*"+input+"*"+bzd.addDecoration(input)+"*");
		input = new String("    -0.00");
		System.out.println("*"+input+"*"+bzd.addDecoration(input)+"*");
		input = new String("    +0.00");
		System.out.println("*"+input+"*"+bzd.addDecoration(input)+"*");
		input = new String("-000");
		System.out.println("*"+input+"*"+bzd.addDecoration(input)+"*");
		input = new String("+000");
		System.out.println("*"+input+"*"+bzd.addDecoration(input)+"*");
	}
}
