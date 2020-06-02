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
/**
 * @author p0006439
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Login extends PageCodeBase {

	protected HtmlScriptCollector scriptCollector1;
	protected HtmlOutputText text1;
	protected HtmlForm form1;
	protected HtmlInputText userId;
	protected HtmlCommandExButton submit;
	protected HtmlMessage msg1;
	protected HtmlScriptCollector getScriptCollector1()
	{
		if (scriptCollector1 == null)
		{
			scriptCollector1 =
				(HtmlScriptCollector) findComponentInRoot("scriptCollector1");
		}
		return scriptCollector1;
	}
	protected HtmlOutputText getText1()
	{
		if (text1 == null)
		{
			text1 = (HtmlOutputText) findComponentInRoot("text1");
		}
		return text1;
	}
	protected HtmlForm getForm1()
	{
		if (form1 == null)
		{
			form1 = (HtmlForm) findComponentInRoot("form1");
		}
		return form1;
	}
	protected HtmlInputText getUserId()
	{
		if (userId == null)
		{
			userId = (HtmlInputText) findComponentInRoot("userId");
		}
		return userId;
	}
	protected HtmlCommandExButton getSubmit()
	{
		if (submit == null)
		{
			submit = (HtmlCommandExButton) findComponentInRoot("submit");
		}
		return submit;
	}
	protected HtmlMessage getMsg1()
	{
		if (msg1 == null)
		{
			msg1 = (HtmlMessage) findComponentInRoot("msg1");
		}
		return msg1;
	}
}