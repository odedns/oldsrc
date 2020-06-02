/* Created on 14/08/2006 */
package pagecode.wasmon;

import pagecode.PageCodeBase;
import javax.faces.component.html.HtmlForm;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.component.UIColumn;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlSelectBooleanCheckbox;
import javax.faces.component.html.HtmlCommandLink;
import javax.faces.component.html.HtmlCommandButton;
/**
 * 
 * @author Odedn
 */
public class ServerList extends PageCodeBase {

	protected HtmlForm	serverListForm;
	protected HtmlDataTable	serverTable;
	protected UIColumn	select;
	protected HtmlOutputText	voidText;
	protected HtmlOutputText	serverNameTxt;
	protected HtmlOutputText	text1;
	protected HtmlOutputText	text2;
	protected HtmlOutputText	text3;
	protected HtmlOutputText	text4;
	protected HtmlOutputText	actionText;
	protected HtmlSelectBooleanCheckbox	serverSelect;
	protected UIColumn	serverName;
	protected HtmlCommandLink	serverLink;
	protected UIColumn	machine;
	protected UIColumn	prodName;
	protected UIColumn	pid;
	protected UIColumn	status;
	protected UIColumn	action;
	protected HtmlCommandButton	stopServer;
	protected HtmlCommandButton	restartServer;
	protected HtmlCommandButton	newServer;
	protected HtmlCommandButton	deleteServer;
	protected HtmlForm getServerListForm()
	{
		if (serverListForm == null) {
			serverListForm = (HtmlForm)findComponentInRoot("serverListForm");
		}
		return serverListForm;
	}
	protected HtmlDataTable getServerTable()
	{
		if (serverTable == null) {
			serverTable = (HtmlDataTable)findComponentInRoot("serverTable");
		}
		return serverTable;
	}
	protected UIColumn getSelect()
	{
		if (select == null) {
			select = (UIColumn)findComponentInRoot("select");
		}
		return select;
	}
	protected HtmlOutputText getVoidText()
	{
		if (voidText == null) {
			voidText = (HtmlOutputText)findComponentInRoot("voidText");
		}
		return voidText;
	}
	protected HtmlOutputText getServerNameTxt()
	{
		if (serverNameTxt == null) {
			serverNameTxt = (HtmlOutputText)findComponentInRoot("serverNameTxt");
		}
		return serverNameTxt;
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
	protected HtmlOutputText getText4()
	{
		if (text4 == null) {
			text4 = (HtmlOutputText)findComponentInRoot("text4");
		}
		return text4;
	}
	protected HtmlOutputText getActionText()
	{
		if (actionText == null) {
			actionText = (HtmlOutputText)findComponentInRoot("actionText");
		}
		return actionText;
	}
	protected HtmlSelectBooleanCheckbox getServerSelect()
	{
		if (serverSelect == null) {
			serverSelect = (HtmlSelectBooleanCheckbox)findComponentInRoot("serverSelect");
		}
		return serverSelect;
	}
	protected UIColumn getServerName()
	{
		if (serverName == null) {
			serverName = (UIColumn)findComponentInRoot("serverName");
		}
		return serverName;
	}
	protected HtmlCommandLink getServerLink()
	{
		if (serverLink == null) {
			serverLink = (HtmlCommandLink)findComponentInRoot("serverLink");
		}
		return serverLink;
	}
	protected UIColumn getMachine()
	{
		if (machine == null) {
			machine = (UIColumn)findComponentInRoot("machine");
		}
		return machine;
	}
	protected UIColumn getProdName()
	{
		if (prodName == null) {
			prodName = (UIColumn)findComponentInRoot("prodName");
		}
		return prodName;
	}
	protected UIColumn getPid()
	{
		if (pid == null) {
			pid = (UIColumn)findComponentInRoot("pid");
		}
		return pid;
	}
	protected UIColumn getStatus()
	{
		if (status == null) {
			status = (UIColumn)findComponentInRoot("status");
		}
		return status;
	}
	protected UIColumn getAction()
	{
		if (action == null) {
			action = (UIColumn)findComponentInRoot("action");
		}
		return action;
	}
	protected HtmlCommandButton getStopServer()
	{
		if (stopServer == null) {
			stopServer = (HtmlCommandButton)findComponentInRoot("stopServer");
		}
		return stopServer;
	}
	protected HtmlCommandButton getRestartServer()
	{
		if (restartServer == null) {
			restartServer = (HtmlCommandButton)findComponentInRoot("restartServer");
		}
		return restartServer;
	}
	protected HtmlCommandButton getNewServer()
	{
		if (newServer == null) {
			newServer = (HtmlCommandButton)findComponentInRoot("newServer");
		}
		return newServer;
	}
	protected HtmlCommandButton getDeleteServer()
	{
		if (deleteServer == null) {
			deleteServer = (HtmlCommandButton)findComponentInRoot("deleteServer");
		}
		return deleteServer;
	}
}