/* Created on 25/01/2007 */
package pagecode.custom;

import pagecode.PageCodeBase;
import com.ibm.faces.component.html.HtmlScriptCollector;
import javax.faces.component.html.HtmlForm;
import com.ibm.faces.bf.component.html.HtmlTree;
import com.ibm.faces.bf.component.html.HtmlTreeNodeAttr;
import com.ibm.faces.component.html.HtmlCommandExButton;
import com.ibm.faces.component.html.HtmlInputMiniCalendar;
/**
 * 
 * @author Odedn
 */
public class TestTree extends PageCodeBase {

	protected HtmlScriptCollector	scriptCollector1;
	protected HtmlForm	form1;
	protected HtmlTree tree1;
	protected HtmlTreeNodeAttr treenodeattr1;
	protected HtmlTreeNodeAttr treenodeattr2;
	protected HtmlTreeNodeAttr treenodeattr3;
	protected HtmlTreeNodeAttr treenodeattr4;
	protected HtmlTreeNodeAttr treenodeattr5;
	protected HtmlTreeNodeAttr treenodeattr6;
	protected HtmlCommandExButton open;
	protected HtmlInputMiniCalendar miniCalendar1;
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
	protected HtmlTree getTree1() {
		if (tree1 == null) {
			tree1 = (HtmlTree) findComponentInRoot("tree1");
		}
		return tree1;
	}
	protected HtmlTreeNodeAttr getTreenodeattr1() {
		if (treenodeattr1 == null) {
			treenodeattr1 = (HtmlTreeNodeAttr) findComponentInRoot("treenodeattr1");
		}
		return treenodeattr1;
	}
	protected HtmlTreeNodeAttr getTreenodeattr2() {
		if (treenodeattr2 == null) {
			treenodeattr2 = (HtmlTreeNodeAttr) findComponentInRoot("treenodeattr2");
		}
		return treenodeattr2;
	}
	protected HtmlTreeNodeAttr getTreenodeattr3() {
		if (treenodeattr3 == null) {
			treenodeattr3 = (HtmlTreeNodeAttr) findComponentInRoot("treenodeattr3");
		}
		return treenodeattr3;
	}
	protected HtmlTreeNodeAttr getTreenodeattr4() {
		if (treenodeattr4 == null) {
			treenodeattr4 = (HtmlTreeNodeAttr) findComponentInRoot("treenodeattr4");
		}
		return treenodeattr4;
	}
	protected HtmlTreeNodeAttr getTreenodeattr5() {
		if (treenodeattr5 == null) {
			treenodeattr5 = (HtmlTreeNodeAttr) findComponentInRoot("treenodeattr5");
		}
		return treenodeattr5;
	}
	protected HtmlTreeNodeAttr getTreenodeattr6() {
		if (treenodeattr6 == null) {
			treenodeattr6 = (HtmlTreeNodeAttr) findComponentInRoot("treenodeattr6");
		}
		return treenodeattr6;
	}
	protected HtmlCommandExButton getOpen() {
		if (open == null) {
			open = (HtmlCommandExButton) findComponentInRoot("open");
		}
		return open;
	}
	protected HtmlInputMiniCalendar getMiniCalendar1() {
		if (miniCalendar1 == null) {
			miniCalendar1 = (HtmlInputMiniCalendar) findComponentInRoot("miniCalendar1");
		}
		return miniCalendar1;
	}
}