/**
 * Created on 13/04/2005
 * @author P0006439
 * @version $id$
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package pagecode;

import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlMessages;
import javax.faces.component.html.HtmlCommandButton;
/**
 * @author P0006439
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class MovieDetails extends PageCodeBase {
	protected HtmlInputText text2;
	protected HtmlInputText text3;
	protected HtmlSelectOneMenu menu1;
	protected HtmlOutputText text4;
	protected HtmlMessages msg;
	protected HtmlCommandButton closeButton;
	protected HtmlCommandButton save;
	protected HtmlCommandButton details;
	protected HtmlInputText getText2()
	{
		if (text2 == null)
		{
			text2 = (HtmlInputText) findComponentInRoot("text2");
		}
		return text2;
	}
	protected HtmlInputText getText3()
	{
		if (text3 == null)
		{
			text3 = (HtmlInputText) findComponentInRoot("text3");
		}
		return text3;
	}
	protected HtmlSelectOneMenu getMenu1()
	{
		if (menu1 == null)
		{
			menu1 = (HtmlSelectOneMenu) findComponentInRoot("menu1");
		}
		return menu1;
	}
	protected HtmlOutputText getText4()
	{
		if (text4 == null)
		{
			text4 = (HtmlOutputText) findComponentInRoot("text4");
		}
		return text4;
	}
	protected HtmlMessages getMsg()
	{
		if (msg == null)
		{
			msg = (HtmlMessages) findComponentInRoot("msg");
		}
		return msg;
	}
	protected HtmlCommandButton getCloseButton()
	{
		if (closeButton == null)
		{
			closeButton =
				(HtmlCommandButton) findComponentInRoot("closeButton");
		}
		return closeButton;
	}
	protected HtmlCommandButton getSave()
	{
		if (save == null)
		{
			save = (HtmlCommandButton) findComponentInRoot("save");
		}
		return save;
	}
	protected HtmlCommandButton getDetails()
	{
		if (details == null)
		{
			details = (HtmlCommandButton) findComponentInRoot("details");
		}
		return details;
	}
}