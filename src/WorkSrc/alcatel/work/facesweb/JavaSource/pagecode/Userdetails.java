/**
 * Created on 15/03/2005
 * @author P0006439
 * @version $id$
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package pagecode;

import com.ibm.faces.component.html.HtmlScriptCollector;
import javax.faces.component.html.HtmlOutputLabel;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlForm;
import javax.faces.component.html.HtmlCommandLink;
/**
 * @author P0006439
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Userdetails extends PageCodeBase {

	protected HtmlScriptCollector scriptCollector1;
	protected HtmlOutputLabel label1;
	protected HtmlOutputLabel label2;
	protected HtmlOutputText text1;
	protected HtmlOutputText text2;
	protected HtmlForm form1;
	protected HtmlCommandLink catalog;
	protected HtmlScriptCollector getScriptCollector1()
	{
		if (scriptCollector1 == null)
		{
			scriptCollector1 =
				(HtmlScriptCollector) findComponentInRoot("scriptCollector1");
		}
		return scriptCollector1;
	}
	protected HtmlOutputLabel getLabel1()
	{
		if (label1 == null)
		{
			label1 = (HtmlOutputLabel) findComponentInRoot("label1");
		}
		return label1;
	}
	protected HtmlOutputLabel getLabel2()
	{
		if (label2 == null)
		{
			label2 = (HtmlOutputLabel) findComponentInRoot("label2");
		}
		return label2;
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
	protected HtmlForm getForm1()
	{
		if (form1 == null)
		{
			form1 = (HtmlForm) findComponentInRoot("form1");
		}
		return form1;
	}
	protected HtmlCommandLink getCatalog()
	{
		if (catalog == null)
		{
			catalog = (HtmlCommandLink) findComponentInRoot("catalog");
		}
		return catalog;
	}
}