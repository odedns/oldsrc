/* Created on 14/08/2006 */
package pagecode.custom;

import pagecode.PageCodeBase;
import com.ibm.faces.component.html.HtmlScriptCollector;
import com.ibm.faces.component.html.HtmlPanelActionbar;
import javax.faces.component.UINamingContainer;
import javax.faces.component.html.HtmlCommandLink;
import javax.faces.component.html.HtmlOutputText;
/**
 * 
 * @author Odedn
 */
public class Tab_panel extends PageCodeBase {

	protected HtmlScriptCollector	scriptCollector1;
	protected HtmlPanelActionbar	actionbar1;
	protected UINamingContainer	footerView;
	protected HtmlCommandLink	link1;
	protected HtmlOutputText	text1;
	protected HtmlOutputText	text2;
	protected HtmlOutputText	text3;
	protected HtmlCommandLink	link2;
	protected HtmlCommandLink	link3;
	protected UINamingContainer tab;
	protected HtmlScriptCollector getScriptCollector1()
	{
		if (scriptCollector1 == null) {
			scriptCollector1 = (HtmlScriptCollector)findComponentInRoot("scriptCollector1");
		}
		return scriptCollector1;
	}
	protected HtmlPanelActionbar getActionbar1()
	{
		if (actionbar1 == null) {
			actionbar1 = (HtmlPanelActionbar)findComponentInRoot("actionbar1");
		}
		return actionbar1;
	}
	protected UINamingContainer getFooterView()
	{
		if (footerView == null) {
			footerView = (UINamingContainer)findComponentInRoot("footerView");
		}
		return footerView;
	}
	protected HtmlCommandLink getLink1()
	{
		if (link1 == null) {
			link1 = (HtmlCommandLink)findComponentInRoot("link1");
		}
		return link1;
	}
	protected HtmlOutputText getText1()
	{
		if (text1 == null) {
			text1 = (HtmlOutputText)findComponentInRoot("text1");
		}
		return text1;
	}
	protected HtmlOutputText getText2()
	{
		if (text2 == null) {
			text2 = (HtmlOutputText)findComponentInRoot("text2");
		}
		return text2;
	}
	protected HtmlOutputText getText3()
	{
		if (text3 == null) {
			text3 = (HtmlOutputText)findComponentInRoot("text3");
		}
		return text3;
	}
	protected HtmlCommandLink getLink2()
	{
		if (link2 == null) {
			link2 = (HtmlCommandLink)findComponentInRoot("link2");
		}
		return link2;
	}
	protected HtmlCommandLink getLink3()
	{
		if (link3 == null) {
			link3 = (HtmlCommandLink)findComponentInRoot("link3");
		}
		return link3;
	}
	protected UINamingContainer getTab() {
		if (tab == null) {
			tab = (UINamingContainer) findComponentInRoot("tab");
		}
		return tab;
	}
}