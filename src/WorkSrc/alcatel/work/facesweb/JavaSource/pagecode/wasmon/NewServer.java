/* Created on 31/07/2006 */
package pagecode.wasmon;

import pagecode.PageCodeBase;
import com.ibm.faces.component.html.HtmlScriptCollector;
import javax.faces.component.html.HtmlForm;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlCommandButton;
/**
 * 
 * @author odedn
 */
public class NewServer extends PageCodeBase {

	protected HtmlScriptCollector	scriptCollector1;
	protected HtmlForm	newServerForm;
	protected HtmlPanelGrid	grid1;
	protected HtmlCommandButton	save;
	protected HtmlCommandButton	close;
	protected HtmlInputText	machineName;
	protected HtmlInputText	port;
	protected HtmlInputText	name;
	protected HtmlScriptCollector getScriptCollector1()
	{
		if (scriptCollector1 == null) {
			scriptCollector1 = (HtmlScriptCollector)findComponentInRoot("scriptCollector1");
		}
		return scriptCollector1;
	}
	protected HtmlForm getNewServerForm()
	{
		if (newServerForm == null) {
			newServerForm = (HtmlForm)findComponentInRoot("newServerForm");
		}
		return newServerForm;
	}
	protected HtmlPanelGrid getGrid1()
	{
		if (grid1 == null) {
			grid1 = (HtmlPanelGrid)findComponentInRoot("grid1");
		}
		return grid1;
	}
	protected HtmlCommandButton getSave()
	{
		if (save == null) {
			save = (HtmlCommandButton)findComponentInRoot("save");
		}
		return save;
	}
	protected HtmlCommandButton getClose()
	{
		if (close == null) {
			close = (HtmlCommandButton)findComponentInRoot("close");
		}
		return close;
	}
	protected HtmlInputText getMachineName()
	{
		if (machineName == null) {
			machineName = (HtmlInputText)findComponentInRoot("machineName");
		}
		return machineName;
	}
	protected HtmlInputText getPort()
	{
		if (port == null) {
			port = (HtmlInputText)findComponentInRoot("port");
		}
		return port;
	}
	protected HtmlInputText getName()
	{
		if (name == null) {
			name = (HtmlInputText)findComponentInRoot("name");
		}
		return name;
	}
}