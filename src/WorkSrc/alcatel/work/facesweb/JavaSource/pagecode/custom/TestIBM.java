/* Created on 24/01/2007 */
package pagecode.custom;

import pagecode.PageCodeBase;
import com.ibm.faces.component.html.HtmlScriptCollector;
import javax.faces.component.html.HtmlForm;
import com.ibm.faces.bf.component.html.HtmlTabbedPanel;
import com.ibm.faces.bf.component.html.HtmlBfPanel;
import com.ibm.faces.component.html.HtmlCommandExButton;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.UIColumn;
import com.ibm.faces.component.html.HtmlPanelBox;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.component.html.HtmlCommandLink;
import com.ibm.faces.component.html.HtmlInputRowSelect;
/**
 * 
 * @author Odedn
 */
public class TestIBM extends PageCodeBase {

	protected HtmlScriptCollector	scriptCollector1;
	protected HtmlForm	form1;
	protected HtmlTabbedPanel	tabbedPanel1;
	protected HtmlBfPanel	bfpanel1;
	protected HtmlCommandExButton	tabbedPanel1_back;
	protected HtmlCommandExButton	tabbedPanel1_next;
	protected HtmlCommandExButton	tabbedPanel1_finish;
	protected HtmlCommandExButton	tabbedPanel1_cancel;
	protected HtmlBfPanel	bfpanel2;
	protected HtmlOutputText	text1;
	protected HtmlInputText	text22;
	protected HtmlPanelBox	box1;
	protected HtmlPanelBox	box2;
	protected HtmlDataTable	table1;
	protected UIColumn	name;
	protected HtmlCommandLink	link2;
	protected UIColumn	year1;
	protected UIColumn	director1;
	protected UIColumn	gender;
	protected HtmlInputRowSelect	rowSelect1;
	protected UIColumn	column1;
	protected HtmlScriptCollector getScriptCollector1()
	{
		if (scriptCollector1 == null) {
			scriptCollector1 = (HtmlScriptCollector)findComponentInRoot("scriptCollector1");
		}
		return scriptCollector1;
	}
	protected HtmlForm getForm1()
	{
		if (form1 == null) {
			form1 = (HtmlForm)findComponentInRoot("form1");
		}
		return form1;
	}
	protected HtmlTabbedPanel getTabbedPanel1()
	{
		if (tabbedPanel1 == null) {
			tabbedPanel1 = (HtmlTabbedPanel)findComponentInRoot("tabbedPanel1");
		}
		return tabbedPanel1;
	}
	protected HtmlBfPanel getBfpanel1()
	{
		if (bfpanel1 == null) {
			bfpanel1 = (HtmlBfPanel)findComponentInRoot("bfpanel1");
		}
		return bfpanel1;
	}
	protected HtmlCommandExButton getTabbedPanel1_back()
	{
		if (tabbedPanel1_back == null) {
			tabbedPanel1_back = (HtmlCommandExButton)findComponentInRoot("tabbedPanel1_back");
		}
		return tabbedPanel1_back;
	}
	protected HtmlCommandExButton getTabbedPanel1_next()
	{
		if (tabbedPanel1_next == null) {
			tabbedPanel1_next = (HtmlCommandExButton)findComponentInRoot("tabbedPanel1_next");
		}
		return tabbedPanel1_next;
	}
	protected HtmlCommandExButton getTabbedPanel1_finish()
	{
		if (tabbedPanel1_finish == null) {
			tabbedPanel1_finish = (HtmlCommandExButton)findComponentInRoot("tabbedPanel1_finish");
		}
		return tabbedPanel1_finish;
	}
	protected HtmlCommandExButton getTabbedPanel1_cancel()
	{
		if (tabbedPanel1_cancel == null) {
			tabbedPanel1_cancel = (HtmlCommandExButton)findComponentInRoot("tabbedPanel1_cancel");
		}
		return tabbedPanel1_cancel;
	}
	protected HtmlBfPanel getBfpanel2()
	{
		if (bfpanel2 == null) {
			bfpanel2 = (HtmlBfPanel)findComponentInRoot("bfpanel2");
		}
		return bfpanel2;
	}
	protected HtmlOutputText getText1()
	{
		if (text1 == null) {
			text1 = (HtmlOutputText)findComponentInRoot("text1");
		}
		return text1;
	}
	protected HtmlInputText getText22()
	{
		if (text22 == null) {
			text22 = (HtmlInputText)findComponentInRoot("text22");
		}
		return text22;
	}
	protected HtmlPanelBox getBox1()
	{
		if (box1 == null) {
			box1 = (HtmlPanelBox)findComponentInRoot("box1");
		}
		return box1;
	}
	protected HtmlPanelBox getBox2()
	{
		if (box2 == null) {
			box2 = (HtmlPanelBox)findComponentInRoot("box2");
		}
		return box2;
	}
	protected HtmlDataTable getTable1()
	{
		if (table1 == null) {
			table1 = (HtmlDataTable)findComponentInRoot("table1");
		}
		return table1;
	}
	protected UIColumn getName()
	{
		if (name == null) {
			name = (UIColumn)findComponentInRoot("name");
		}
		return name;
	}
	protected HtmlCommandLink getLink2()
	{
		if (link2 == null) {
			link2 = (HtmlCommandLink)findComponentInRoot("link2");
		}
		return link2;
	}
	protected UIColumn getYear1()
	{
		if (year1 == null) {
			year1 = (UIColumn)findComponentInRoot("year1");
		}
		return year1;
	}
	protected UIColumn getDirector1()
	{
		if (director1 == null) {
			director1 = (UIColumn)findComponentInRoot("director1");
		}
		return director1;
	}
	protected UIColumn getGender()
	{
		if (gender == null) {
			gender = (UIColumn)findComponentInRoot("gender");
		}
		return gender;
	}
	public String doRowAction1Action()
	{
		// TODO Auto-generated method
		//     Get the index of the selected row
		//     int row = getRowAction1().getRowIndex();
		//
		//     Copy key values from the selection to the request so they can be used in a database filter
		//     For example, if the table has a column "keyvalue" and there is an SDO object that uses the
		//     filter "request.keyvalue", then this code sets up the request so the filter will work
		//     correctly
		//     For V5.1 server use:
		//     Object keyvalue = getIe().getDataObjectAccessBean(row).get("keyvalue");
		//     For V6 server use:
		//     Object keyvalue = ((DataObject)getIe().get(row)).get("keyvalue");
		//
		//     getRequestScope().put("keyvalue", keyvalue);
		//
		//     Specify the return value (a string) which is used by the navigation map to determine
		//     the next page to display

		return "";

	}
	protected HtmlInputRowSelect getRowSelect1()
	{
		if (rowSelect1 == null) {
			rowSelect1 = (HtmlInputRowSelect)findComponentInRoot("rowSelect1");
		}
		return rowSelect1;
	}
	protected UIColumn getColumn1()
	{
		if (column1 == null) {
			column1 = (UIColumn)findComponentInRoot("column1");
		}
		return column1;
	}
}