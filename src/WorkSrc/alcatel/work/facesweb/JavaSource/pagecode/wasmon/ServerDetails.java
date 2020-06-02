/* Created on 31/07/2006 */
package pagecode.wasmon;

import pagecode.PageCodeBase;
import javax.faces.component.html.HtmlForm;
/**
 * 
 * @author odedn
 */
public class ServerDetails extends PageCodeBase {

	protected HtmlForm	newServerForm;
	protected HtmlForm getNewServerForm()
	{
		if (newServerForm == null) {
			newServerForm = (HtmlForm)findComponentInRoot("newServerForm");
		}
		return newServerForm;
	}
}