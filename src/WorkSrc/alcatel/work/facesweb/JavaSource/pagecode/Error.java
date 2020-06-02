/**
 * Created on 09/03/2005
 * @author P0006439
 * @version $id$
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package pagecode;

import beans.CalculatorBean;
/**
 * @author P0006439
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Error extends PageCodeBase {

	protected CalculatorBean calculator;
	/** 
	* @author WebSphere Studio
	* @beanName calculator
	* @managed-bean true
	* @beanClass beans.CalculatorBean
	*/
	public CalculatorBean getCalculator()
	{
		if (calculator == null)
		{
			calculator = new CalculatorBean();
			calculator =
				(CalculatorBean) getFacesContext()
					.getApplication()
					.createValueBinding("#{calculator}")
					.getValue(getFacesContext());
		}
		return calculator;
	}
	public void setCalculator(CalculatorBean calculator)
	{
		this.calculator = calculator;
	}
	/** 
	* @author WebSphere Studio
	* @beanName calculator
	* @managed-bean true
	* @beanClass beans.CalculatorBean
	*/
	public CalculatorBean getCalculator1()
	{
		if (calculator == null)
		{
			calculator = new CalculatorBean();
			calculator =
				(CalculatorBean) getFacesContext()
					.getApplication()
					.createValueBinding("#{calculator}")
					.getValue(getFacesContext());
		}
		return calculator;
	}
	public void setCalculator1(CalculatorBean calculator)
	{
		this.calculator = calculator;
	}
}