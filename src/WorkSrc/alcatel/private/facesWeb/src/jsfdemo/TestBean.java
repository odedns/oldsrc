/**
 * Date: 20/02/2007
 * File: TestBean.java
 * Package: beans
 */
package jsfdemo;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.UICommand;
import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.component.UIPanel;
import javax.faces.component.UIViewRoot;
import javax.faces.component.html.HtmlCommandButton;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import beans.JsfUtil;

/**
 * @author a73552
 *
 */
public class TestBean {

	private static Logger log = Logger.getLogger(TestBean.class);
	private String longText;
	private Long longVal;
	private Date dateVal;
	private Float sum;
	private boolean checkMe;
	private String selectOne;
	private String selectMany[];
	private String selectMany2[];
	private Integer currSelection;
	private Integer currSelection2;
	private String password;
	private String valueChanged="original";
	private Map <String,Boolean> tabRenderMap;
	private String currTab;
	private String[] osNames1 = {"Linux", "Solaris", "AIX","HP-UX"} ;
	private String[] osNames2 = {"Windows", "MacOS", "Unix"} ;
	public TestBean()
	{
		log.info("TesBean()");
		log.info("curretab = " + currTab);
		tabRenderMap = new HashMap<String,Boolean>();
		currTab = "tab1";
		tabRenderMap.put("tab1", Boolean.FALSE);
		tabRenderMap.put("tab2", Boolean.FALSE);
		tabRenderMap.put("tab3", Boolean.FALSE);
		tabRenderMap.put(currTab, Boolean.TRUE);
		
	}
	
	public Date getDateVal() {
		return dateVal;
	}
	public void setDateVal(Date dateVal) {
		this.dateVal = dateVal;
	}
	public Long getLongVal() {
		return longVal;
	}
	public void setLongVal(Long longVal) {
		this.longVal = longVal;
	}
	public Float getSum() {
		log.info("getSum");
		return sum;
	}
	public void setSum(Float sum) {
		log.info("setSum");
		this.sum = sum;
	}
	
	public String doAction()
	{
		log.info("doAction()");
		return(null);
	}
	
	public List getSelections()
	{
		List<SelectItem> list = new LinkedList <SelectItem>();
		SelectItem item = new SelectItem(new Integer(1),"Choice 1");
		list.add(item);
		item = new SelectItem(new Integer(2),"Choice 2");
		list.add(item);
		item = new SelectItem(new Integer(3),"Choice 3");
		list.add(item);
		return(list);
	}
	
	
	public String[] getSelectMany() {
		return selectMany;
	}
	public void setSelectMany(String selectMany[]) {
		this.selectMany = selectMany;
	}
	public String getSelectOne() {
		return selectOne;
	}
	public void setSelectOne(String selectOne) {
		this.selectOne = selectOne;
	}
	public Integer getCurrSelection() {
		return currSelection;
	}
	public void setCurrSelection(Integer currSelection) {
		this.currSelection = currSelection;
	}
	public Integer getCurrSelection2() {
		return currSelection2;
	}
	public void setCurrSelection2(Integer currSelection2) {
		this.currSelection2 = currSelection2;
	}
	public boolean isCheckMe() {
		return checkMe;
	}
	public void setCheckMe(boolean checkMe) {
		this.checkMe = checkMe;
	}
	public String[] getSelectMany2() {
		return selectMany2;
	}
	public void setSelectMany2(String[] selectMany2) {
		this.selectMany2 = selectMany2;
	}
	public String getLongText() {
		return longText;
	}
	public void setLongText(String longText) {
		this.longText = longText;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getValueChanged() {
		return valueChanged;
	}
	public void setValueChanged(String valueChanged) {
		this.valueChanged = valueChanged;
	}
	
	public void handleValueChange(ValueChangeEvent event)
	{
		log.info("in testBean.valueChange()");
		String val = (String) event.getNewValue();
		UIComponent comp = event.getComponent();
		JsfUtil.addMessage(comp,"New Value: " + val);
	}
	
	public void handleTab(ActionEvent event)
	{
		log.info("TestBean.handleTab()");
		UIComponent comp = event.getComponent();
		String id = comp.getId();
		System.out.println("componenet id = " + id);
		tabRenderMap.put(currTab, Boolean.FALSE);
		tabRenderMap.put(id, Boolean.TRUE);				
		this.currTab = id;
		log.info("map=" +tabRenderMap.toString());
		
	}

	public void addComponent(ActionEvent event)
	{
		log.info("TestBean.addComponent()");
		UIComponent comp = event.getComponent();
		if(comp instanceof UICommand) {
			((UICommand)comp).setValue("pressed");
		}
		UIPanel panel = (UIPanel) JsfUtil.findComponent("dynForm","dynPanel");
		if(panel != null) {
			log.info("found component");
			HtmlCommandButton hc = new HtmlCommandButton();
			// hc.setId("dynButton");
			hc.setValue("dynamic Button");
			panel.getChildren().add(hc);
			
		} else {
			log.error("component not found");
		}
		
		
		
	}
	
	public String getCurrTab() {
		return currTab;
	}

	public void setCurrTab(String currTab) {
		this.currTab = currTab;
	}

	public Map<String, Boolean> getTabRenderMap() {
		return tabRenderMap;
	}

	public void setTabRenderMap(Map<String, Boolean> tabRenderMap) {
		this.tabRenderMap = tabRenderMap;
	}

	/**
	 * here we return the appropriate OS name list
	 * according to the parameter passed in the URL.
	 * @return
	 */
	public String[] getOsNames() {
		String osNames[] = osNames2;
		HttpServletRequest r = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String s = (String) r.getParameter("type");
		if("unix".equals(s)) {
			osNames = osNames1;
		} 
	
		return osNames;
	}

	
}
