/* Created on 14/08/2006 */
package pagecode.custom;

import pagecode.PageCodeBase;
import com.ibm.faces.component.html.HtmlScriptCollector;
import com.ibm.faces.component.html.HtmlPanelActionbar;
/**
 * 
 * @author Odedn
 */
public class Inc_test1 extends PageCodeBase {

	protected HtmlScriptCollector	scriptCollector1;
	protected HtmlPanelActionbar	actionbar1;
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
}