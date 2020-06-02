/*
 * Calculator
 */
package beans;

import java.math.BigDecimal;

public class CalculatorBean {
	Long number1;
	Long number2;
	BigDecimal result = null;
	String operation = null;
	String errorMessage = null;

	public Long getNumber1()        { return number1; }
	public Long getNumber2()        { return number2; }
	public BigDecimal getResult()   { return result; }
	public String getOperation()    { return operation; }
	public String getErrorMessage() { return errorMessage; }

	public void setNumber1(Long i) {
		System.out.println("CalcBean setNumber1="+i);
		number1 = i;
		errorMessage = null;
	}
	public void setNumber2(Long i) {
		System.out.println("CalcBean setNumber2="+i);
		number2 = i;
		errorMessage = null;
	}
	public void setResult(BigDecimal decimal) {
		System.out.println("CalcBean setResult="+decimal);
		result = decimal;
		errorMessage = null;
	}
	public void setOperation(String string) {
		System.out.println("CalcBean setOperation="+string);
		operation = string;
		errorMessage = null;
	}
	public void setErrorMessage(String string) {
		System.out.println("CalcBean setErrorMessage="+string);
		errorMessage = string;
	}

	public void calculate() throws Exception {
		System.out.println("CalcBean calculate");
		errorMessage = null;
		setResult( new BigDecimal(0) );
		BigDecimal bd1 = new BigDecimal(number1.longValue());
		BigDecimal bd2 = new BigDecimal(number2.longValue());
		char op = operation.charAt(0);
		switch (op) {
			case 'A':
				setResult( bd1.add(bd2) );
				break;
			case 'S':
				setResult( bd1.subtract(bd2) );
				break;
			case 'M':
				setResult( bd1.multiply(bd2) );
				break;
			case 'D':
				setResult( bd1.divide(bd2,BigDecimal.ROUND_DOWN) );
				if ( !result.multiply(bd2).equals(bd1) ) {
					setErrorMessage("Not an integer division");
					throw new Exception("Not an integer division");
				}
				break;
		}
		setNumber1( new Long(getResult().longValue()) );
	}
	
	public String toString() {
		return "Calculator: "+number1+" "+operation+" "+number2;
	}

}
