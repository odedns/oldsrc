/**
 * Created on 07/03/2005
 * @author p0006439
 * @version $id$
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package pagecode;

import com.ibm.faces.component.html.HtmlScriptCollector;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlForm;
import javax.faces.component.html.HtmlInputText;
import com.ibm.faces.component.html.HtmlCommandExButton;
import javax.faces.component.html.HtmlMessage;
import beans.CalculatorBean;
import javax.faces.component.html.HtmlMessages;
import javax.faces.event.ValueChangeEvent;
import javax.faces.component.html.HtmlSelectOneMenu;
/**
 * @author p0006439
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Calculate extends PageCodeBase {

	protected HtmlScriptCollector scriptCollector1;
	protected HtmlOutputText comment;
	protected HtmlOutputText label1;
	protected HtmlOutputText operationLabel;
	protected HtmlOutputText label2;
	protected HtmlOutputText resultLabel;
	protected HtmlForm form1;
	protected HtmlInputText number2;
	protected HtmlCommandExButton calc;
	protected HtmlOutputText result;
	protected HtmlInputText number1;
	protected HtmlMessage message1;
	protected CalculatorBean calculator;
	protected HtmlOutputText text1;
	protected HtmlOutputText text2;
	protected HtmlOutputText text3;
	protected HtmlOutputText text4;
	protected HtmlOutputText text5;
	protected HtmlMessages messages1;
	protected HtmlCommandExButton button1;
	protected HtmlSelectOneMenu operation;
	protected HtmlScriptCollector getScriptCollector1()
	{
		if (scriptCollector1 == null)
		{
			scriptCollector1 =
				(HtmlScriptCollector) findComponentInRoot("scriptCollector1");
		}
		return scriptCollector1;
	}
	protected HtmlOutputText getComment()
	{
		if (comment == null)
		{
			comment = (HtmlOutputText) findComponentInRoot("comment");
		}
		return comment;
	}
	protected HtmlOutputText getLabel1()
	{
		if (label1 == null)
		{
			label1 = (HtmlOutputText) findComponentInRoot("label1");
		}
		return label1;
	}
	protected HtmlOutputText getOperationLabel()
	{
		if (operationLabel == null)
		{
			operationLabel = (HtmlOutputText) findComponentInRoot("operationLabel");
		}
		return operationLabel;
	}
	protected HtmlOutputText getLabel2()
	{
		if (label2 == null)
		{
			label2 = (HtmlOutputText) findComponentInRoot("label2");
		}
		return label2;
	}
	protected HtmlOutputText getResultLabel()
	{
		if (resultLabel == null)
		{
			resultLabel = (HtmlOutputText) findComponentInRoot("resultLabel");
		}
		return resultLabel;
	}
	protected HtmlForm getForm1()
	{
		if (form1 == null)
		{
			form1 = (HtmlForm) findComponentInRoot("form1");
		}
		return form1;
	}
	protected HtmlInputText getNumber2()
	{
		if (number2 == null)
		{
			number2 = (HtmlInputText) findComponentInRoot("number2");
		}
		return number2;
	}
	protected HtmlCommandExButton getCalc()
	{
		if (calc == null)
		{
			calc = (HtmlCommandExButton) findComponentInRoot("calc");
		}
		return calc;
	}
	protected HtmlOutputText getResult()
	{
		if (result == null)
		{
			result = (HtmlOutputText) findComponentInRoot("result");
		}
		return result;
	}
	protected HtmlInputText getNumber1()
	{
		if (number1 == null)
		{
			number1 = (HtmlInputText) findComponentInRoot("number1");
		}
		return number1;
	}
	protected HtmlMessage getMessage1()
	{
		if (message1 == null)
		{
			message1 = (HtmlMessage) findComponentInRoot("message1");
		}
		return message1;
	}
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
	protected HtmlOutputText getText1()
	{
		if (text1 == null)
		{
			text1 = (HtmlOutputText) findComponentInRoot("text1");
		}
		return text1;
	}
	protected HtmlOutputText getText2()
	{
		if (text2 == null)
		{
			text2 = (HtmlOutputText) findComponentInRoot("text2");
		}
		return text2;
	}
	protected HtmlOutputText getText3()
	{
		if (text3 == null)
		{
			text3 = (HtmlOutputText) findComponentInRoot("text3");
		}
		return text3;
	}
	protected HtmlOutputText getText4()
	{
		if (text4 == null)
		{
			text4 = (HtmlOutputText) findComponentInRoot("text4");
		}
		return text4;
	}
	protected HtmlOutputText getText5()
	{
		if (text5 == null)
		{
			text5 = (HtmlOutputText) findComponentInRoot("text5");
		}
		return text5;
	}
	protected HtmlMessages getMessages1()
	{
		if (messages1 == null)
		{
			messages1 = (HtmlMessages) findComponentInRoot("messages1");
		}
		return messages1;
	}
	protected HtmlCommandExButton getButton1()
	{
		if (button1 == null)
		{
			button1 = (HtmlCommandExButton) findComponentInRoot("button1");
		}
		return button1;
	}
	public void handleOperationValueChange(ValueChangeEvent valueChangedEvent)
	{
		
	}
	public void handleOperationValueChange1(ValueChangeEvent valueChangedEvent)
	{
		// Type Java code to handle value changed event here
		// Note, valueChangeEvent contains new and old values
	}
	protected HtmlSelectOneMenu getOperation()
	{
		if (operation == null)
		{
			operation = (HtmlSelectOneMenu) findComponentInRoot("operation");
		}
		return operation;
	}
	public String doCalcAction()
	{
		// Type Java code to handle command event here
		// Note, this code must return an object of type String (or null)
		log("CalculateAction start");
				try {
					getCalculator().calculate();
				} catch (Exception e) {
					log("Calculator-Exception: "+e.getMessage());
					if (getCalculator().getErrorMessage() == null)
						getCalculator().setErrorMessage("Exception: "+e.getMessage());
				} finally {
					log("CalculateAction end");	
				}
				return null;
		
	}
}