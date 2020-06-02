/* Created on 31/07/2006 */
package jmonitor;

import java.util.*;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIData;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import beans.JsfUtil;
import beans.Movie;

import com.ibm.websphere.management.AdminClient;




/**
 * 
 * @author odedn
 */
public class WasadminBean {

	private final static Logger logger = Logger.getLogger(WasadminBean.class);
	private Server newServer;
	private Server selectedServer;
	
	
	/**
	 * constructor for the managed bean.
	 *
	 */
	public WasadminBean()
	{
		newServer =  new Server();
		FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"example message","example message"));
		
	}
	/**
	 * login the user.
	 * @return String the outcome.
	 */
	public String login()
	{
		logger.debug("wasAdminBean.login()");
		HttpSession session =(HttpSession ) FacesContext.getCurrentInstance().getExternalContext().getSession(true);		
		return("login");
	}
	
	public String logout()
	{
		logger.debug("wasadminBean.logout()");
		JsfUtil.invalidateSession();
		return("logout");
	}
	/**
	 * get the list of servers from the XML config file.
	 * 
	 * @return List of Server objects.
	 */
	public List getServers()
	{
		Config cfg = Config.getInstance();
		List list = cfg.getServerList();
		Iterator iter = list.iterator();
		while(iter.hasNext()) {
			Server srv = (Server) iter.next();
			srv.setProductName("IBM WebSphere");
			try {
				AdminClient client = WasJmxUtils.createAdminClient(srv.getHost(),srv.getPort());
				srv.setAdminClient(client);
				WasJmxUtils.retrieveServerData(srv);
			} catch(Exception e) {				
				logger.error("Error retrieving server info: " + e);
				srv.setState("STOPPED");
				// e.printStackTrace();
			}
		}
		return(list);
		
	}
	
	/**
	 * called when adding a new server.
	 * @return String the outcome.
	 */
	public String addServer()
	{
		logger.debug("in addServer()");
		Config cfg = Config.getInstance();
		try {
			cfg.addServer(newServer);
		}catch(JadminException je) {
			logger.error("Error in addServer");
			JsfUtil.addMessage("Error in addServer");
		}
		return("close");
	}
	
	/**
	 * Getter for newServer. <br>
	 * 
	 * @return newServer
	 */
	public Server getNewServer()
	{
		return newServer;
	}
	/**
	 * Setter for newServer. <br>
	 * 
	 * @param newServer the newServer to set
	 */
	public void setNewServer(Server newServer)
	{		
		this.newServer = newServer;
	}
	
	public String deleteServer()
	{
		logger.debug("in deleteServer");
		UIData table = (UIData) JsfUtil.findComponent("serverListForm","serverTable");
		logger.debug("rowIndex= " + table.getRowIndex());
		List list  = (List) table.getValue();
		List srvList = Config.getInstance().getServerList();
		Iterator iter = list.iterator();
		while(iter.hasNext()) {		
			Server srv = (Server)iter.next();
			if(srv.isSelected()) {
				srvList.remove(srv);				
			}
		}
		
		return(null);
		
	}
	
	/**
	 * show server details if server is running.
	 * @return String result.
	 */
	public String serverDetails()
	{
		logger.debug("in serverDetails");
		UIData table = (UIData) JsfUtil.findComponent("serverListForm","serverTable");
		Server srv = (Server) table.getRowData();
		logger.debug("selecte server: " + srv.getName());
		if(srv.getState().equals("STOPPED")) {
			String msg = JsfUtil.getMessageFromBundle("messages","noDetails", null,Locale.ENGLISH);
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,msg,msg));
			logger.debug("added message: " + msg);
			return(null);
		}
		try {
			JVMInfo jvm = WasJmxUtils.getJVMInfo(srv.getAdminClient());
			srv.setJvmInfo(jvm);
			logger.debug("got jvminfo");
		} catch(Exception e) {
			String msg = JsfUtil.getMessageFromBundle("messages","jvmInfoError", null,Locale.ENGLISH);
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,msg,msg));
		}
		setSelectedServer(srv);
		return("serverDetails");
	}
	
	/**
	 * Getter for selectedServer. <br>
	 * 
	 * @return selectedServer
	 */
	public Server getSelectedServer()
	{
		return selectedServer;
	}
	/**
	 * Setter for selectedServer. <br>
	 * 
	 * @param selectedServer the selectedServer to set
	 */
	public void setSelectedServer(Server selectedServer)
	{
		this.selectedServer = selectedServer;
		Map m = JsfUtil.getSessionBean();
		m.put("selectedServer", selectedServer);
	}
	
	/**
	 * get the current server.
	 */
	public Server currentServer()
	{
		return(selectedServer);
	}
	
	/**
	 * stop the server
	 * @return null to stay in same page.
	 */
	public String stopServer()
	{
		UIData table = (UIData) JsfUtil.findComponent("serverListForm","serverTable");
		Server srv = (Server) table.getRowData();
		logger.info("stopping server: " + srv.getName());
		try {
			WasJmxUtils.serverControll(srv, WasJmxUtils.OP_STOP);
		} catch(Exception e){
			e.printStackTrace();
			JsfUtil.addMessage("Error stopping server");
		}
		return(null);
		
	}
	
	/**
	 * restart the server.
	 * @return null to stay in same page.
	 */
	public String restartServer()
	{
		UIData table = (UIData) JsfUtil.findComponent("serverListForm","serverTable");
		Server srv = (Server) table.getRowData();
		logger.info("restarting server: " + srv.getName());
		try {
			WasJmxUtils.serverControll(srv, WasJmxUtils.OP_RESTART);
		} catch(Exception e){
			e.printStackTrace();
			JsfUtil.addMessage("Error restarting server");
		}
		return(null);
		
	}
	
	/**
	 * get the current server from the session bean
	 * @return Server object for the current server.
	 */
	public Server getCurrentServer()
	{
		Map m = JsfUtil.getSessionBean();
		Server srv = (Server) m.get("selectedServer");
		return(srv);
	}
	
	/**
	 * get all applications for the current server.
	 * @return List containing AppInfo objects.
	 */
	public List getApplications()
	{
		List apps=null;
		Server srv = getCurrentServer();
		try {
			apps = WasJmxUtils.getApplications(srv);
		} catch(Exception e) {
			e.printStackTrace();
			JsfUtil.addMessage("Error getting applications for server:" + srv.getName());
		}
		return(apps);
	}
	
	
}
