/* Created on 01/08/2006 */
package pagecode.wasmon;

import pagecode.PageCodeBase;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlInputSecret;
import javax.faces.component.html.HtmlCommandButton;
/**
 * 
 * @author odedn
 */
public class Login extends PageCodeBase {

	protected HtmlPanelGrid	grid1;
	protected HtmlInputText	login;
	protected HtmlInputSecret	password;
	protected HtmlCommandButton	loginButton;
	protected HtmlCommandButton	cancelButton;
	protected HtmlPanelGrid getGrid1()
	{
		if (grid1 == null) {
			grid1 = (HtmlPanelGrid)findComponentInRoot("grid1");
		}
		return grid1;
	}
	protected HtmlInputText getLogin()
	{
		if (login == null) {
			login = (HtmlInputText)findComponentInRoot("login");
		}
		return login;
	}
	protected HtmlInputSecret getPassword()
	{
		if (password == null) {
			password = (HtmlInputSecret)findComponentInRoot("password");
		}
		return password;
	}
	protected HtmlCommandButton getLoginButton()
	{
		if (loginButton == null) {
			loginButton = (HtmlCommandButton)findComponentInRoot("loginButton");
		}
		return loginButton;
	}
	protected HtmlCommandButton getCancelButton()
	{
		if (cancelButton == null) {
			cancelButton = (HtmlCommandButton)findComponentInRoot("cancelButton");
		}
		return cancelButton;
	}
}