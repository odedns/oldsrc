package com.ibm.dse.desktop;

/*_
 * Licensed Materials - Property of IBM
 * Restricted Materials of IBM
 * 5648-D89
 * (C) Copyright IBM Corp. 2000, 2003
 * US Government Users Restricted Rights - Use, duplication or disclosure
 * restricted by GSA ADP Schedule Contract with IBM Corp
 */
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import com.ibm.dse.base.ClientOperation;
import com.ibm.dse.base.Context;
import com.ibm.dse.base.DSEClientOperation;
import com.ibm.dse.base.DSEEventObject;
import com.ibm.dse.base.DSEInvalidArgumentException;
import com.ibm.dse.base.DataMapperFormat;
import com.ibm.dse.base.Trace;
import com.ibm.dse.gui.CoordinatedEventListener;
import com.ibm.dse.gui.CoordinatedEventMulticaster;
import com.ibm.dse.gui.CoordinatedEventSource;
import com.ibm.dse.gui.CoordinatedPanel;
import com.ibm.dse.gui.DSECoordinatedPanel;
import com.ibm.dse.gui.DSECoordinationEvent;

/**
 * This class controls the navigation of a business function. Each Task has a DSENavigationController to 
 * keep information of the views and navigation amongst them based on the received DSECoordinated events.
 * @copyright(c) Copyright IBM Corporation 2000, 2003.
 */
public class DSENavigationController extends com.ibm.dse.base.DSENotifier implements NavigationController,CoordinatedEventSource,CoordinatedEventListener {
	private static final java.lang.String COPYRIGHT =
		"Licensed Materials - Property of IBM "+//$NON-NLS-1$
		"Restricted Materials of IBM "+//$NON-NLS-1$
		"5648-D89 "+//$NON-NLS-1$
		"(C) Copyright IBM Corp. 2000, 2003 All Rights Reserved. "+//$NON-NLS-1$
		"US Government Users Restricted Rights - Use, duplication or disclosure "+//$NON-NLS-1$
		"restricted by GSA ADP Schedule Contract with IBM Corp ";//$NON-NLS-1$
	private String activeView=null;
	private com.ibm.dse.automaton.Processor process=null;
	/** The hashtable with the list of views **/
	protected Hashtable viewRegistryList=new Hashtable();
      /** The hashtable with the list of views ordered **/
	protected Hashtable viewOrderList=new Hashtable();
	private SpInternalFrame navigationArea=null;
	/** The identifier of none navigation **/
	protected int NAV_NONE=0;  
	/** The identifier of the peer navigation **/
	protected int NAV_PEER=1;
	/** The identifier of children navigation **/
	protected int NAV_CHILDREN=2;
	/** The identifier of the context of the process **/
	public static final String CTXUSED_PROCESS="processor";
	/** The identifier of the context of the active view  **/
	public static final String CTXUSED_ACTIVE="activeView";
	/** The identifier of the context of the parent active view **/
	public static final String CTXUSED_PARENT="parentActiveView";
	private transient CoordinatedEventListener aCoordinatedEventListener=null;
	private boolean close=true;
	private JScrollPane scrollpane= new JScrollPane();
	private int level=0;
	private Dimension d=null;

	
/**
 * This constructor creates a DSENavigationController object.
 * <P>This class takes charge of all the navigation of the views.
 */
public DSENavigationController() {
	super();
}
/**
 * This constructor creates a DSENavigationController object.
 * <P>This class takes charge of all the navigation of the views.
 * @param aName java.lang.String
 * @exception java.io.IOException 
 */
public DSENavigationController(String aName) throws java.io.IOException {
	super(aName);
}
/**
 * Activates the view passed as the parameter. This view is the active view of
 * the NavigationArea and has the focus.
 * @param viewId java.lang.String - the identifier of the view
 */
public void activateView(String viewId) {
	try{
			if (((ViewRegistry)(viewRegistryList.get(viewId))).getSubview()) {
				reactivateSubView(viewId);
				return;

			}
			
			CoordinatedPanel view = (CoordinatedPanel) getViewInstance(viewId);
			
			SpInternalFrame f = (SpInternalFrame) getNavigationArea();
			if (getActiveView() != null)
				deActivateView(getActiveView());
			setActiveView(viewId);
			((JPanel)view).setPreferredSize(((JPanel)view).getSize());
			getScrollPane().setViewportView((Component)view);
			f.validate();
			f.setSelected(true);
			try {
				view.postInitialize();
				view.refresh();
				view.becomingActiveActions();
				((JPanel)view).setVisible(true);
			} catch (Exception e) {
				if (Trace.doTrace(Desktop.COMPID, Trace.Medium, Trace.Error))
					Trace.trace(Desktop.COMPID, Trace.Medium, Trace.Error, com.ibm.dse.base.Settings.getTID(), e.toString() + ".Error activating the view");
			}
			f.setTitle(view.getTitle());
			f.setVisible(true);
			f.toFront();
			Component c=(((DSECoordinatedPanel)view).getFocusManager()).getLastFocused();
			if ( c!=null)	c.requestFocus();
		else{
			 if (getFirstFocusableComponent(((JPanel)view))!=null)
		    	getFirstFocusableComponent(((JPanel)view)).requestFocus();
			}
	} catch (java.lang.Throwable e) {
			handleException(new Throwable("Error activating panel - " + e.toString()));
		}
	
}
/**
 * Activates the view passed as a parameter. This view is the active view of
 * the NavigationArea and remains modal through the rest of the navigation flow.
 * This view has a fixed position and size, which have been passed as parameters.
 * The behavior of this view is like a subview.
 * @param viewId java.lang.String - identifier of the view
 * @param position java.util.Hashtable - position and size of the view
 */
public void activeSubView(String viewId, java.util.Hashtable position) {
	
	CoordinatedPanel view = (CoordinatedPanel) getViewInstance(viewId);
	if (view != null) {
		try {
		
			SpInternalFrame f1 = new SpInternalFrame();
			JDesktopPane dp=(JDesktopPane)SwingUtilities.getAncestorOfClass(JDesktopPane.class,getNavigationArea());	
			dp.add(f1);

			//the rest of the Navigation flow is not active.
			((DSECoordinatedPanel)getViewInstance(getActiveView())).setEnabled(false);
			setActiveView(viewId);
			
			int x = 0, y = 0, w, h;			
			if (((Boolean)position.get("usePos")).booleanValue()) {
				x = new Integer((String)position.get("x")).intValue();
				y = new Integer((String)position.get("y")).intValue();
			}

			if (((Boolean)position.get("useSize")).booleanValue()) {
				w = new Integer((String)position.get("w")).intValue();
				h = new Integer((String)position.get("h")).intValue();
			} else {
				w = ((DSECoordinatedPanel)view).getWidth();
				h = ((DSECoordinatedPanel)view).getHeight();
			}
			if(((DSECoordinatedPanel)view).getWidth() > w )
			{
						//if it is necessary a JScrollPane
						JScrollPane sp = new JScrollPane();
						f1.setContentPane(sp);
						sp.setViewportView((Component)view);
			
			} 
			else
			{
				if(((DSECoordinatedPanel)view).getHeight() > h )
				{
						JScrollPane sp = new JScrollPane();
						f1.setContentPane(sp);
						sp.setViewportView((Component)view);
			
				}
				else	f1.setContentPane((Container)view);
			}
			
			Dimension d = new Dimension(w,h);
			((JPanel)view).setPreferredSize(d);
			f1.setOriginalLocation(new java.awt.Point(x,y));
			f1.setLocation(new java.awt.Point(x,y));
			f1.setSize(new Dimension(w,h));
			f1.setRealSize(new Dimension(w,h));

			f1.setHorizontalMovable(true);
			
			f1.validate();
			f1.setSelected(true);
			try {
				view.postInitialize();
				view.refresh();
				view.becomingActiveActions();
			} catch (Exception e) {
				if (Trace.doTrace(Desktop.COMPID, Trace.Medium, Trace.Error))
					Trace.trace(Desktop.COMPID, Trace.Medium, Trace.Error, com.ibm.dse.base.Settings.getTID(), e.toString() + ".Error activating the view");
			}
			f1.setTitle(view.getTitle());
			f1.setVisible(true);
			f1.toFront();
			((ViewRegistry)viewRegistryList.get(viewId)).setOwner(f1);
	
		Component c=(((DSECoordinatedPanel)view).getFocusManager()).getLastFocused();
			if ( c!=null)  c.requestFocus();
			else{
			 if (getFirstFocusableComponent(((JPanel)view))!=null)
		    	getFirstFocusableComponent(((JPanel)view)).requestFocus();
			}
				} catch (java.lang.Throwable e) {
			handleException(new Throwable("Error activating panel - " + e.toString()));
		}
	}
	
}
/**
 * Adds a CoordinatedEventListener. 
 * @param newListener CoordinatedEventListener
 */
public void addCoordinatedEventListener(CoordinatedEventListener newListener){
	aCoordinatedEventListener = CoordinatedEventMulticaster.add(aCoordinatedEventListener, newListener);
}
/**
 * Cancels all the operations of the navigation and closes the navigation's panels until it reaches the parent view,
 * if it exists.  
 * @param viewId String 
 */
public void cancel(String viewId) {
	
	modifyNavigation(viewId,true,true,null);
	
}
/**
 * Closes all the views opened for the task. 
 */
public void close() {
	while (getActiveView()!=null&& close==true)
	closeView(getActiveView());
}
/**
 * Closes all the panels of the navigation and uses the closeMapFormat to
 * map the value to the active view.
 * @param viewId String 
 * @param closeMapFormat String 
 */
public void closeNavigation(String viewId, String closeMapFormat) {
	com.ibm.dse.base.DataMapperFormat mapper=null;
	try{
		if ((closeMapFormat!=null)&&(closeMapFormat.length()>0)) {
			mapper = (com.ibm.dse.base.DataMapperFormat) com.ibm.dse.base.FormatElement.readObject(closeMapFormat);
		}
		closeNavigation(viewId,mapper);
	}catch (java.lang.Throwable t) {
		if (Trace.doTrace(Desktop.COMPID,Trace.Low,Trace.Warning)) {
			Trace.trace(Desktop.COMPID,Trace.High,Trace.Error,com.ibm.dse.base.Settings.getTID(),
			"Warning - Exception in Close Map Format: "+t.toString());
		}
	}
	
}
/**
 * Closes all the panels of the navigation and, if a closeMapFormat is declared,
 * maps the value to the active view.
 * @param viewId String 
 * @param mapper DataMapperFormat 
 */
public void closeNavigation(String viewId, DataMapperFormat mapper) {
	modifyNavigation(viewId,true,false,mapper);
	
}
/**
 * Closes the view passed as the parameter.
 * @param id java.lang.String - identifier of the view
 */
public void closeView(String id) {
	CoordinatedPanel view=(CoordinatedPanel)getViewInstance(id);
	if (view.isClosable()){
		boolean unchain=((ViewRegistry)viewRegistryList.get(id)).getUnchain();
		view.close(unchain);
	}
	else{
		Desktop.getDesktop().showMessage(Desktop.getDesktop().getWorkingArea().getWarningMessageWhenAViewCanNotBeClosed(),javax.swing.JOptionPane.WARNING_MESSAGE);
		close=false;
		((JPanel)view).requestFocus();
		Component c=(((DSECoordinatedPanel)view).getFocusManager()).getLastFocused();
			if ( c!=null)  c.requestFocus();
			else{
			 if (getFirstFocusableComponent(((JPanel)view))!=null)
		    	getFirstFocusableComponent(((JPanel)view)).requestFocus();
			}
		}
}
/**
 * Deactivates the view passed as the parameter.
 * @param viewId java.lang.String - identifier of the view
 */
public void deActivateView(String viewId) {
	try {
	CoordinatedPanel view=(CoordinatedPanel)getViewInstance(viewId);
	if (view==null) return;
	else
	getScrollPane().setViewportView(null);
	}
	catch (Exception e) {} 		
}
/**
 * Removes the entry of this view from the View registry.
 * @param viewId java.lang.String - identifier of the view
 */
public void deRegister(String viewId) {
	int lon=0;
	try{
		lon=(getChilds(viewId).size())-1;
		CoordinatedPanel view=(CoordinatedPanel) getViewInstance(viewId);
		if (getNextView(viewId)!=null) closeView(getNextView(viewId));
		if (lon!=0) {
			while (lon>=0) {
				closeView((String)getChilds(viewId).elementAt(lon));
				lon=lon-1;
			}
		}
		viewRegistryList.remove(viewId);
		viewOrderList.remove(viewId);
		getScrollPane().setViewportView(null);

	}catch(Exception e){
		if(Trace.doTrace(Desktop.COMPID,Trace.Medium,Trace.Error))
			Trace.trace(Desktop.COMPID,Trace.Medium,Trace.Error,com.ibm.dse.base.Settings.getTID(), e.toString()+".Error deregistering the view");
	}
}
/**
 * Removes the entry of this view from the View registry.
 * @param viewId java.lang.String - identifier of the view
 */
public void deRegisterSubView(String viewId) {
	try{
		CoordinatedPanel view=(CoordinatedPanel) getViewInstance(viewId);
		ViewRegistry v=(ViewRegistry)viewRegistryList.get(viewId);
		((DSECoordinatedPanel)getViewInstance(v.getParentView())).setEnabled(true);
		((SpInternalFrame)v.getOwner()).dispose();
		viewRegistryList.remove(viewId);
		viewOrderList.remove(viewId);
	}catch(Exception e){
		if(Trace.doTrace(Desktop.COMPID,Trace.Medium,Trace.Error))
			Trace.trace(Desktop.COMPID,Trace.Medium,Trace.Error,com.ibm.dse.base.Settings.getTID(), e.toString()+".Error deregistering the view");
	}
}
/**
 * Fires a CoordinationEvent. 
 */
public void fireCoordinationEvent(){
DSECoordinationEvent newEvent = new DSECoordinationEvent(this);
	fireCoordinationEvent(newEvent);
}
/**
 * Fires a CoordinationEvent.
 * @param DSECoordinationEvent event
 */
public void fireCoordinationEvent(DSECoordinationEvent event){
if (aCoordinatedEventListener == null) return;
	aCoordinatedEventListener.handleDSECoordinationEvent(event);
	}
/**
 * Returns the viewId associated with the active view.
 * @return String 
 */
public String getActiveView() {
	return activeView;
}
/**
 * Returns a list of the children associated with the viewId.
 * @return Vector
 */
public java.util.Vector getChilds(String viewId) {
	Vector views=new Vector();
	if (viewRegistryList.size()!=0){
		java.util.Enumeration viewsId=viewRegistryList.keys();
		String view= null;
		String parent= null;
		while (viewsId.hasMoreElements()){
			view=(String)viewsId.nextElement();
			parent=getParent(view);
			if (parent!=null)
			if(parent.equals(viewId)) views.addElement(view);
 		 }
		return  reorder(views);
	}
	else return views;
}
/**
 * Gets the first component inside the task that can accept the focus.
 * @return java.awt.Component
 * @param c java.awt.Container
 */
public static java.awt.Component getFirstFocusableComponent(java.awt.Container c) {
	for (int i = 0; i < c.getComponentCount(); i++) {
		if (c.getComponent(i).isFocusTraversable()) {
			return c.getComponent(i);
		}
		else {
			java.awt.Container co = null; 
			try {
				co = ((java.awt.Container)(c.getComponent(i)));
				java.awt.Component p = getFirstFocusableComponent(co);
				if (p != null) return p;
			} catch (ClassCastException cce) {}
		}
	}
	return null;
}
/**
 * Returns the last element of the queue of views.
 * @return String
 */
public String getKey() {
	if( viewOrderList.size()!=0){
		Enumeration keys=viewOrderList.keys();
		Vector v=new Vector();
		int i=0;
		while (keys.hasMoreElements()){
			v.insertElementAt(keys.nextElement(),i);
			i=i+1;
		}
		Vector v1=reorder(v);
		return ((String)v1.lastElement());
	}else return null;
}
/**
 * Returns the Container of the view.
 * @return java.swing JComponent 
 */
public SpInternalFrame getNavigationArea() {
	if (navigationArea.isVisible()== false){
		navigationArea.setVisible(true);
		if (d!=null)navigationArea.setSize(d);
	}
	return navigationArea;
}
/**
 * Returns the viewId of the next view associated with the passed viewId.
 * @param viewId
 * @return String
 */
public String getNextView(String viewId) {
return ((ViewRegistry)viewRegistryList.get(viewId)).getNextView();
}
/**
 * Returns the ID of the parent view associated with the viewId.
 * @param viewId
 * @returns String
 */
public String getParent(String viewId) {
	return ((ViewRegistry)viewRegistryList.get(viewId)).getParentView();
}
/**
 * Returns the peers associated with the viewId.
 * @param viewId
 * @return Vector
 */
public Vector getPeers(String viewId) {
	String parent=getParent(viewId);
	Vector children=getChilds(parent);
	return (children) ;
}
/**
 * Returns the viewId of the previous view associated with the viewId.
 * @param viewId
 * @return String
 */
public String getPreviousView(String viewId) {

return ((ViewRegistry)viewRegistryList.get(viewId)).getPreviousView();
	
}
/**
 * Returns the process associated with this NavigationController.
 * @return process com.ibm.dse.automaton.DSEProcessor
 */
public com.ibm.dse.automaton.Processor getProcess() {
	return process;
}
/**
 * Returns the scrollpane associated with the navigationArea.
 * @returns JScrollpane
 */
public JScrollPane getScrollPane() {	
	return scrollpane;	
	
}
/**
 * Returns the instance of the view passed as a parameter.
 * @return com.ibm.dse.gui.CoordinatedPanel
 * @param viewId java.lang.String - identifier of the view
 */
public CoordinatedPanel getViewInstance(String viewId) {
	return ((ViewRegistry) viewRegistryList.get(viewId)).getView();  
	
}
/**
 * Contains the appropriate actions to be executed when DSECoordinationEvent has been triggered.
 * @param event DSECoordinationEvent
 */
public void handleDSECoordinationEvent(DSECoordinationEvent event){
	
	String viewId=null;
	String viewId2=null;
	String viewParent=null;
	DSEEventObject CBTFevent=null;
	if (event.getEventType()!=null) {
		if (event.getEventType().equals(DSECoordinationEvent.EVENT_EVENTYPE_ACTION)) {
		try {
			CBTFevent=new DSEEventObject(event.getName(),this);
		    signalEvent(CBTFevent);			
		} catch (DSEInvalidArgumentException dse){}
		}
		else if (event.getEventType().equals(DSECoordinationEvent.EVENT_EVENTYPE_NAVIGATION)) {
				if  (event.getEventSourceType().equals(DSECoordinationEvent.EVENT_SOURCETYPE_OPEN_VIEW)){
	                if (viewRegistryList.containsKey(event.getViewName())){  			
						 if(Trace.doTrace(Desktop.COMPID,Trace.Medium,Trace.Error))
							Trace.trace(Desktop.COMPID,Trace.Medium,Trace.Error,com.ibm.dse.base.Settings.getTID(),"the view"+event.getViewName()+"is already opened");
	                }		
					else {
						if ((event.getOperationName()!=null)&&((event.getOperationName().trim()).length()>0)){			
							try{
		 					    event.setOperation((DSEClientOperation)DSEClientOperation.readObject(event.getOperationName())); 
		 					}
							catch (Exception e)	{}
						}
					    openView(event.getViewName(),event.getViewSource(),(ClientOperation)event.getOperation(),event.getNavigation(),event.getLinkContextTo(),event.getOpenMapFormat(), event.getPosition());
		 			} 
				}
				if  (event.getEventSourceType().equals(DSECoordinationEvent.EVENT_SOURCETYPE_PREVIOUS)){
					previousView();
				}
				if  (event.getEventSourceType().equals(DSECoordinationEvent.EVENT_SOURCETYPE_NEXT)){
			 		if ((event.getOperationName()!=null)&&((event.getOperationName().trim()).length()>0)){			
							try{
		 					    event.setOperation((DSEClientOperation)DSEClientOperation.readObject(event.getOperationName())); 
		 					}
							catch (Exception e)	{}
						}
					   	
					nextView(event.getViewName(),event.getViewSource(),(ClientOperation)event.getOperation(),event.getNavigation(),event.getLinkContextTo(),event.getOpenMapFormat(), event.getPosition());
				}
				if  (event.getEventSourceType().equals(DSECoordinationEvent.EVENT_SOURCETYPE_UNDO)){
					undo(getActiveView());
				}
				if  (event.getEventSourceType().equals(DSECoordinationEvent.EVENT_SOURCETYPE_VIEW_VISIBLE)){
					if (((ViewRegistry)viewRegistryList.get(getActiveView())).getSubview()==false)
					fireCoordinationEvent(event);
					else  if (!(getViewInstance(getActiveView())==event.getSource())) reactivateSubView(getActiveView());
				}
				
				if  (event.getEventSourceType().equals(DSECoordinationEvent.EVENT_SOURCETYPE_CANCEL)){
					cancel(getActiveView());
				}
				
				if  (event.getEventSourceType().equals(DSECoordinationEvent.EVENT_SOURCETYPE_CLOSE_NAVIGATION)){
					closeNavigation(getActiveView(),event.getCloseMapFormat());
				}
		
				if  (event.getEventSourceType().equals(DSECoordinationEvent.EVENT_SOURCETYPE_VIEW_OPENED)){
					try {
					CBTFevent=new DSEEventObject(event.getName(),this);
		   			 signalEvent(CBTFevent);			
					} catch (DSEInvalidArgumentException dse){}
				}
				if  (event.getEventSourceType().equals(DSECoordinationEvent.EVENT_SOURCETYPE_VIEW_CLOSED)){
					if (getActiveView()!=null){
						viewId=event.getViewName();
						viewId2=getPreviousView(viewId);
						viewParent=getParent(viewId);
						if (getChilds(viewId).size()!=0)  closeView((String)getChilds(viewId).elementAt(0));

						if (((ViewRegistry)viewRegistryList.get(getActiveView())).getSubview()==true)
							//for subViews
							deRegisterSubView(getActiveView());
						else
						deRegister(viewId);
						if (viewId2!=null) {
						((ViewRegistry)viewRegistryList.get(viewId2)).setNextView(null);	
						activateView(viewId2);
						}
						else {
							if ((viewParent)==null) {
								String view=getKey();
								if (view!=null){
									activateView(view);
									return;
								}
								else  {
									setActiveView(null);
									if (getProcess()==null) fireCoordinationEvent(event);
									else getNavigationArea().setVisible(false);
									return;
								}
							}
							if ((viewParent)!="") activateView(viewParent);
							else  {
								setActiveView(null);
								if (getProcess()==null) fireCoordinationEvent(event);
								else{
									d=getNavigationArea().getSize();
									getNavigationArea().setVisible(false);
								}
							}
						}
					}
					try {
						CBTFevent=new DSEEventObject(event.getName(),this);
		 				signalEvent(CBTFevent);
					}catch (DSEInvalidArgumentException dse){}
					
	
				}
				// for event OpenTask received
				if (event.getEventSourceType().equals(DSECoordinationEvent.EVENT_SOURCETYPE_OPEN_TASK)) {

						int x = 0, y = 0, w =0, h=0;			
						java.util.Hashtable position = event.getPosition();
						TaskInfo ti = Desktop.getDesktop().getTaskInfo(event.getViewName());

						if (((Boolean)position.get("usePos")).booleanValue()) {
							x = new Integer((String)position.get("x")).intValue();
							y = new Integer((String)position.get("y")).intValue();
						}
					
		
						if (((Boolean)position.get("useSize")).booleanValue()) {
							w = new Integer((String)position.get("w")).intValue();
							h = new Integer((String)position.get("h")).intValue();
						}

						Desktop.getDesktop().getTaskArea().openTask(ti.getOperation(),ti.getOperationPanel(),ti.getExecuteOperation(),ti.getType(),ti.getShortDescription(),ti.getLongDescription(),ti.getTaskName(),"","",w,h,x,y);
				}
		}
	}
	
		
}
/**
 * Called whenever the part throws an exception.
 * @param exception java.lang.Throwable
 */
private void handleException(Throwable exception) {

	Desktop.getDesktop().showTraceMessage(Trace.Medium,Trace.Error,"Error in Task instance.",exception);
}
/**
 * Returns whether the view with the passed ID has children. 
 * @param viewId
 * @return boolean - True if the view has children
 */
public boolean isChildren(String viewId) {
	int nav=((ViewRegistry)(viewRegistryList.get(viewId))).getNavigationType();
	if (nav==NAV_CHILDREN) return true;
	else return false;
}
/**
 * Returns whether the view with the passed ID is a peer of another view.
 * @param viewId
 * @return boolean - True if the view has peers
 */
public boolean isPeer(String viewId) {
	int nav=((ViewRegistry)(viewRegistryList.get(viewId))).getNavigationType();
	if (nav==NAV_PEER) return true;
	else return false;
	
}
/**
 * Closes, rolls back, and maps (depending on the parameters) the view.
 * @param viewId String
 * @param close boolean - True to close the view
 * @param rollback boolean - True to roll back the view
 * @param mapper DataMapperFormat
 */
public void modifyNavigation(String viewId, boolean close ,boolean rollback,DataMapperFormat mapper) {
	Vector peers=getPeers(viewId); 
	String parent= getParent(viewId);
	Vector children;
	
	Context c=null;
	if (viewId!=null)  c=getViewInstance(viewId).getContext();
	int lon=peers.size();
	int len=0;
	int i=lon-1,j=0;
	String view=null;
	if (i<0)  {
		if (rollback)((CoordinatedPanel)getViewInstance(activeView)).rollBack();
		 if (close) closeView(viewId);
		}
	while (i>=0){
		view=(String)peers.elementAt(i);
		children=getChilds(view);
		len=children.size();
		j=len-1;
		while (j>=0){
			String id=(String)children.elementAt(j);
			
			if (rollback)((CoordinatedPanel)getViewInstance(id)).rollBack();
			if (close==true) closeView(id);
  		    j=j-1; 
		}
		 if (rollback)((CoordinatedPanel)getViewInstance(view)).rollBack();
		 if (close==true) closeView(view);
	   	 i=i-1;
	}
	try{
		if (mapper!=null){
			if ((activeView!=null)&& (getViewInstance(activeView).getContext())!=null){
				if (c!=null)
				mapper.mapContents(c,getViewInstance(activeView).getContext());
				else {
					if ((getProcess()!=null)&&(getProcess().getContext()!=null))
						mapper.mapContents(getProcess().getContext(),getViewInstance(activeView).getContext());
				}
			}
			else {
				if ((c!=null)&&((getProcess()!=null)&&(getProcess().getContext()!=null)))
						mapper.mapContents(c,getProcess().getContext());
			}
		}
	}catch (java.lang.Throwable t) {
		if (Trace.doTrace(Desktop.COMPID,Trace.Low,Trace.Warning)) {
			Trace.trace(Desktop.COMPID,Trace.High,Trace.Error,com.ibm.dse.base.Settings.getTID(),
			"Warning - Exception mapping contents using closeMapFormat: "+t.toString());
		}
	}

}
/**
 * Shows the next view, activates it, and stores the IDs of the next and previous views.
 * @param id java.lang.String - identifier of the view  
 * @param operation ClientOperation 
 * @param navigation int 
 * @param linkContextTo String 
 */
 
public void nextView(String id,ClientOperation operation,int navigation,String linkContextTo,String inputPathFormat) {	
	String viewId=null;
	viewId=getActiveView();
	if (navigation!=NAV_CHILDREN)((ViewRegistry)viewRegistryList.get(viewId)).setNextView(id);  
	if (viewRegistryList.containsKey(id)) activateView(id);
	else  openView(id,operation,navigation,linkContextTo,inputPathFormat);
	((ViewRegistry)viewRegistryList.get(id)).setPreviousView(viewId);  								
}
/**
 * Shows the next view, activates it, and stores the IDs of the next and previous views.
 * @param id java.lang.String - identifier of the view
 * @param source java.lang.String - full name of the view
 * @param operation ClientOperation 
 * @param navigation int 
 * @param linkContextTo String 
 */
 
public void nextView(String id,String source,ClientOperation operation,int navigation,String linkContextTo,String inputPathFormat) {
	String viewId=null;
	viewId=getActiveView();
	if (navigation!=NAV_CHILDREN)((ViewRegistry)viewRegistryList.get(viewId)).setNextView(id);  
	if (viewRegistryList.containsKey(id)) activateView(id);
	else  openView(id,source,operation,navigation,linkContextTo,inputPathFormat);
	((ViewRegistry)viewRegistryList.get(id)).setPreviousView(viewId);  								
}
/**
 * Shows the next view, activates it, and stores the IDs of the next
 * and previous views.
 * @param id java.lang.String - identifier of the view
 * @param source java.lang.String - full name of the view
 * @param operation ClientOperation 
 * @param navigation int 
 * @param linkContextTo String
 * @param position java.util.Hashtable - position and size of the view
 */
 
public void nextView(String id,String source,ClientOperation operation,int navigation,String linkContextTo,String inputPathFormat, java.util.Hashtable position) {
	String viewId=null;
	viewId=getActiveView();
	System.out.println("nextView id = " + id +"\tviewId = " + viewId);
	if (navigation!=NAV_CHILDREN)((ViewRegistry)viewRegistryList.get(viewId)).setNextView(id);  
	if (viewRegistryList.containsKey(id)) activateView(id);
	else  openView(id,source,operation,navigation,linkContextTo,inputPathFormat,position);
	((ViewRegistry)viewRegistryList.get(id)).setPreviousView(viewId);  								
}
/**
 * Opens a view, activates it, and stores all the information
 * about the view.
 * @param viewId java.lang.String - identifier of the view  
 */

public void openView(String viewId) throws Exception{
	openView(viewId,null,new Boolean(false));	
}
/**
 * Opens a view, activates it, and stores all the information 
 * related to this view.
 * @param viewId  java.lang.String - identifier of the view  
 * @param op ClientOperation   
 * @param navigation int 
 * @param linkContextTo String 
 */
public void openView(String viewId, ClientOperation op,int navigation, String linkContextTo,String inputPathFormat) {
	openView(viewId,viewId,op,navigation,linkContextTo,inputPathFormat);
}
/**
 * Opens a view, activates it, and stores all the information 
 * related to this view.
 * @param viewId  java.lang.String - identifier of the view  
 * @param op ClientOperation 
 * @param execOp boolean 
 */
public void openView(String viewId, ClientOperation op, Boolean execOp)  throws Exception{
	CoordinatedPanel view=null; 
		try{ 
	 	 view=(com.ibm.dse.gui.CoordinatedPanel)com.ibm.dse.gui.DSECoordinatedPanel.readObject(viewId);
		}catch (Exception e){
			view = (CoordinatedPanel) Class.forName(viewId).newInstance();
		}
	((DSECoordinatedPanel)view).getFocusManager();
	if (op!=null) view.setOperation(op);
	ViewRegistry reg= new ViewRegistry();
	reg.setView(view);
	 if (getActiveView()==null)reg.setParentView("");
	reg.setOwner(getNavigationArea());
	viewRegistryList.put(((JPanel)view).getName(),reg);
	viewOrderList.put(((JPanel)view).getName(),(new Integer(viewRegistryList.size())));
	view.addCoordinatedEventListener(this);
	if ((execOp!=null)&&(execOp.booleanValue())) view.tryRunOperation();
	activateView(((JPanel)view).getName());

}
/**
 * Opens a view, activates it, and stores all the information 
 * related to this view.
 * @param viewId java.lang.String - identifier of the view
 * @param viewSource java.lang.String - name of the package of the view
 * @param op ClientOperation   
 * @param navigation int 
 * @param linkContextTo String
 * @param mapper DataMapperFormat
 */
public void openView(String viewId, String viewSource,ClientOperation op,int navigation, String linkContextTo,DataMapperFormat mapper ) {
	CoordinatedPanel view=null;
	boolean unchain=true;
	try{
		try{ 
	 	 view=(com.ibm.dse.gui.CoordinatedPanel) com.ibm.dse.gui.DSECoordinatedPanel.readObject(viewId);
		}catch (Exception e){
			view = (CoordinatedPanel) Class.forName(viewSource).newInstance();
		}
		((DSECoordinatedPanel)view).getFocusManager();
		if (op!=null) view.setOperation(op);
		ViewRegistry reg= new ViewRegistry();
		reg.setView(view);
		reg.setOwner(navigationArea);
		if (getActiveView()==null)reg.setParentView("");
		if ((navigation==NAV_PEER)&& (getActiveView()!=null)){
			((ViewRegistry)viewRegistryList.get(getActiveView())).setNextView(((JPanel)view).getName());
	 		reg.setParentView(getParent(getActiveView()));
	 		reg.setPreviousView(getActiveView());
		}
		if ((navigation==NAV_CHILDREN)&& (getActiveView()!=null)){
			 reg.setParentView(getActiveView());
		}
		if (linkContextTo!=null){
			if (view.getContext()!=null){
	
				if (linkContextTo.equals(CTXUSED_PROCESS)){
					view.getContext().chainTo(getProcess().getContext());
					if (mapper!=null) mapper.mapContents(getProcess().getContext(), view.getContext());
			
				} 
				if (linkContextTo.equals(CTXUSED_ACTIVE)){
					CoordinatedPanel view2=  ((ViewRegistry)viewRegistryList.get(getActiveView())).getView();	
					
					view.getContext().chainTo(view2.getContext());
					if (mapper!=null)	mapper.mapContents(view2.getContext(), view.getContext());
			
					}
				if (linkContextTo.equals(CTXUSED_PARENT)){
					CoordinatedPanel viewParent=  ((ViewRegistry)viewRegistryList.get(getParent(getActiveView()))).getView();		
					view.getContext().chainTo(viewParent.getContext());
					if (mapper!=null)	mapper.mapContents(viewParent.getContext(), view.getContext());
					}
			}	  
			else{
				if (linkContextTo.equals(CTXUSED_PROCESS)){
					if (getProcess()!=null)
					view.setContext(getProcess().getContext());
					else {
						if(Trace.doTrace(Desktop.COMPID,Trace.Medium,Trace.Error))
							Trace.trace(Desktop.COMPID,Trace.Medium,Trace.Error,com.ibm.dse.base.Settings.getTID(),"The context of the view can not be set to the process context.The process doesn't exist");	
					return;
					}
					unchain=false;
				} 
				if (linkContextTo.equals(CTXUSED_ACTIVE)){
					CoordinatedPanel view2=  ((ViewRegistry)viewRegistryList.get(getActiveView())).getView();	
					if (view2!=null)
					view.setContext(view2.getContext());
					else {
						if(Trace.doTrace(Desktop.COMPID,Trace.Medium,Trace.Error))
							Trace.trace(Desktop.COMPID,Trace.Medium,Trace.Error,com.ibm.dse.base.Settings.getTID(),"The context of the view can not be set to the active view context.The active view doesn't exist");	
					return;
					}	
				}
				if (linkContextTo.equals(CTXUSED_PARENT)){
					CoordinatedPanel viewParent=  ((ViewRegistry)viewRegistryList.get(getParent(getActiveView()))).getView();		
					if (viewParent!=null)
						view.setContext(viewParent.getContext());
					else {
						if(Trace.doTrace(Desktop.COMPID,Trace.Medium,Trace.Error))
						Trace.trace(Desktop.COMPID,Trace.Medium,Trace.Error,com.ibm.dse.base.Settings.getTID(),"The context of the view can not be set to the Parent context.The view parent doesn't exist");	
						return;
					}
					unchain=false;
				} 
			}
		}
		
	reg.setNavigationType(navigation);
	viewRegistryList.put(((JPanel)view).getName(),reg);
	viewOrderList.put(((JPanel)view).getName(),new Integer(viewRegistryList.size()));
	((ViewRegistry)viewRegistryList.get(((JPanel)view).getName())).setUnchain(unchain);
	view.addCoordinatedEventListener(this);
	activateView(((JPanel)view).getName());
	}catch(Exception e){
	if(Trace.doTrace(Desktop.COMPID,Trace.Medium,Trace.Error))
		Trace.trace(Desktop.COMPID,Trace.Medium,Trace.Error,com.ibm.dse.base.Settings.getTID(),e.toString()+".error opening the view");	
	};
}
/**
 * Opens a view, activates it, and stores all the information 
 * related to this view.
 * @param viewId java.lang.String - identifier of the view
 * @param viewSource java.lang.String - name of the package of the view
 * @param op ClientOperation   
 * @param navigation int 
 * @param linkContextTo String
 * @param mapper DataMapperFormat
 * @param position java.util.Hashtable - position and size of the view
 */
public void openView(String viewId, String viewSource,ClientOperation op,int navigation, String linkContextTo,DataMapperFormat mapper,java.util.Hashtable position ) {
	CoordinatedPanel view=null;
	boolean unchain=true;
	try{
		try{ 
	 	 view=(com.ibm.dse.gui.CoordinatedPanel) com.ibm.dse.gui.DSECoordinatedPanel.readObject(viewId);
		}catch (Exception e){
			view = (CoordinatedPanel) Class.forName(viewSource).newInstance();
		}
		((DSECoordinatedPanel)view).getFocusManager();
		if (op!=null) view.setOperation(op);
		ViewRegistry reg= new ViewRegistry();
		reg.setView(view);
		reg.setOwner(navigationArea);
		if (getActiveView()==null)reg.setParentView("");
		if ((navigation==NAV_PEER)&& (getActiveView()!=null)){
			((ViewRegistry)viewRegistryList.get(getActiveView())).setNextView(((JPanel)view).getName());
	 		reg.setParentView(getParent(getActiveView()));
	 		reg.setPreviousView(getActiveView());
		}
		if ((navigation==NAV_CHILDREN)&& (getActiveView()!=null)){
			 reg.setParentView(getActiveView());
		}
		if (linkContextTo!=null){
			if (view.getContext()!=null){
	
				if (linkContextTo.equals(CTXUSED_PROCESS)){
					view.getContext().chainTo(getProcess().getContext());
					if (mapper!=null) mapper.mapContents(getProcess().getContext(), view.getContext());
			
				} 
				if (linkContextTo.equals(CTXUSED_ACTIVE)){
					CoordinatedPanel view2=  ((ViewRegistry)viewRegistryList.get(getActiveView())).getView();	
					Context ctx2 = view2.getContext();
					Context ctx = view.getContext();
					view.getContext().chainTo(view2.getContext());
					if (mapper!=null)	mapper.mapContents(view2.getContext(), view.getContext());
			
					}
				if (linkContextTo.equals(CTXUSED_PARENT)){
					CoordinatedPanel viewParent=  ((ViewRegistry)viewRegistryList.get(getParent(getActiveView()))).getView();		
					view.getContext().chainTo(viewParent.getContext());
					if (mapper!=null)	mapper.mapContents(viewParent.getContext(), view.getContext());
					}
			}	  
			else{
				if (linkContextTo.equals(CTXUSED_PROCESS)){
					if (getProcess()!=null)
					view.setContext(getProcess().getContext());
					else {
						if(Trace.doTrace(Desktop.COMPID,Trace.Medium,Trace.Error))
							Trace.trace(Desktop.COMPID,Trace.Medium,Trace.Error,com.ibm.dse.base.Settings.getTID(),"The context of the view can not be set to the process context.The process doesn't exist");	
					return;
					}
					unchain=false;
				} 
				if (linkContextTo.equals(CTXUSED_ACTIVE)){
					CoordinatedPanel view2=  ((ViewRegistry)viewRegistryList.get(getActiveView())).getView();	
					if (view2!=null)
					view.setContext(view2.getContext());
					else {
						if(Trace.doTrace(Desktop.COMPID,Trace.Medium,Trace.Error))
							Trace.trace(Desktop.COMPID,Trace.Medium,Trace.Error,com.ibm.dse.base.Settings.getTID(),"The context of the view can not be set to the active view context.The active view doesn't exist");	
					return;
					}	
				}
				if (linkContextTo.equals(CTXUSED_PARENT)){
					CoordinatedPanel viewParent=  ((ViewRegistry)viewRegistryList.get(getParent(getActiveView()))).getView();		
					if (viewParent!=null)
						view.setContext(viewParent.getContext());
					else {
						if(Trace.doTrace(Desktop.COMPID,Trace.Medium,Trace.Error))
						Trace.trace(Desktop.COMPID,Trace.Medium,Trace.Error,com.ibm.dse.base.Settings.getTID(),"The context of the view can not be set to the Parent context.The view parent doesn't exist");	
						return;
					}
					unchain=false;
				} 
			}
		}
		
	reg.setNavigationType(navigation);
	viewRegistryList.put(((JPanel)view).getName(),reg);
	viewOrderList.put(((JPanel)view).getName(),new Integer(viewRegistryList.size()));
	((ViewRegistry)viewRegistryList.get(((JPanel)view).getName())).setUnchain(unchain);
	view.addCoordinatedEventListener(this);
	if (position != null){
		if( (((Boolean)position.get("usePos")).booleanValue())== false && (((Boolean)position.get("useSize")).booleanValue())== false){
				activateView(((JPanel)view).getName());
		}
		else{
		reg.setSubview(true);
		reg.setParentView(getActiveView());
		activeSubView(((JPanel)view).getName(),position);
		}
	}
	else	
	activateView(((JPanel)view).getName());
	}catch(Exception e){
	if(Trace.doTrace(Desktop.COMPID,Trace.Medium,Trace.Error))
		Trace.trace(Desktop.COMPID,Trace.Medium,Trace.Error,com.ibm.dse.base.Settings.getTID(),e.toString()+".error opening the view");	
	};
}
/**
 * Opens a view, activates it, and stores all the information 
 * related to this view.
 * @param viewId  java.lang.String - identifier of the view
 * @param viewSource java.lang.String - name of the package of the view
 * @param op ClientOperation   
 * @param navigation int 
 * @param linkContextTo String 
 */
public void openView(String viewId, String viewSource,ClientOperation op,int navigation, String linkContextTo,String inputFormat ) {
	com.ibm.dse.base.DataMapperFormat mapper=null;
	try {
		if ((inputFormat!=null)&&(inputFormat.length()>0))
		mapper = (com.ibm.dse.base.DataMapperFormat) com.ibm.dse.base.FormatElement.readObject(inputFormat);
		openView(viewId,viewSource,op,navigation,linkContextTo,mapper);
	}
		catch (java.lang.Throwable t) {
			try {
				if (Trace.doTrace(Desktop.COMPID,Trace.Low,Trace.Warning)) {
					Trace.trace(Desktop.COMPID,Trace.High,Trace.Error,com.ibm.dse.base.Settings.getTID(),
						"Warning - Exception in Input Map Format: "+t.toString());
				}
			} catch (java.lang.Throwable t2) {}	
		
		}
}
/**
 * Opens a view, activates it, and stores all the information 
 * related to this view.
 * @param viewId java.lang.String - identifier of the view
 * @param viewSource java.lang.String - name of the package of the view
 * @param op ClientOperation   
 * @param navigation int 
 * @param linkContextTo String
 * @param position java.util.Hashtable - position and size of the view 
 */
public void openView(String viewId, String viewSource,ClientOperation op,int navigation, String linkContextTo,String inputFormat, java.util.Hashtable position ) {
	com.ibm.dse.base.DataMapperFormat mapper=null;
	try {
		if ((inputFormat!=null)&&(inputFormat.length()>0))
		mapper = (com.ibm.dse.base.DataMapperFormat) com.ibm.dse.base.FormatElement.readObject(inputFormat);
		openView(viewId,viewSource,op,navigation,linkContextTo,mapper,position);
	}
		catch (java.lang.Throwable t) {
			try {
				if (Trace.doTrace(Desktop.COMPID,Trace.Low,Trace.Warning)) {
					Trace.trace(Desktop.COMPID,Trace.High,Trace.Error,com.ibm.dse.base.Settings.getTID(),
						"Warning - Exception in Input Map Format: "+t.toString());
				}
			} catch (java.lang.Throwable t2) {}	
		
		}
}
/**
 * Deactivates the active view and shows the previous view.
 */
public void previousView() {
	String viewId=null;
	String viewId2=null;
	viewId=getActiveView();
	viewId2= getPreviousView(viewId);
	if (viewId2!=null) activateView(viewId2);
	else {
		if(Trace.doTrace(Desktop.COMPID,Trace.Medium,Trace.Error))
			Trace.trace(Desktop.COMPID,Trace.Medium,Trace.Error,com.ibm.dse.base.Settings.getTID(),"the view"+getActiveView()+"doesn't have a previous view assigned");	
		}
}
/**
 * Reactivates the view passed as a parameter. 
 * @param viewId java.lang.String - identifier of the view
 */
public void reactivateSubView(String viewId) {
	CoordinatedPanel view = getViewInstance(viewId);
	if (view != null) {  
		try {
			SpInternalFrame f1 = (SpInternalFrame) ((ViewRegistry)viewRegistryList.get(getActiveView())).getOwner();
			getNavigationArea().setEnabled(false);	
			f1.validate();
			try {
				view.postInitialize();
				view.refresh();
				view.becomingActiveActions();
			} catch (Exception e) {
				if (Trace.doTrace(Desktop.COMPID, Trace.Medium, Trace.Error))
					Trace.trace(Desktop.COMPID, Trace.Medium, Trace.Error, com.ibm.dse.base.Settings.getTID(), e.toString() + ".Error activating the view");
			}
			f1.setTitle(view.getTitle());
			f1.setVisible(true);
			f1.toFront();
				f1.setSelected(true);
			 if (getFirstFocusableComponent(((JPanel)view))!=null)
		  	getFirstFocusableComponent(((JPanel)view)).requestFocus();
	} catch (java.lang.Throwable e) {
			handleException(new Throwable("Error activating panel - " + e.toString()));
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	}
/**
 * Refreshes the active view of the Task.
 */
public void refreshActiveView(){
	CoordinatedPanel view =getViewInstance(getActiveView());
	view.postInitialize();
	view.refresh();
	view.becomingActiveActions();
	 	
}
/**
 * Removes the CoordinatedEventListener. 
 * @param newListener DataChangedListener
 */
public void removeCoordinatedEventListener(CoordinatedEventListener newListener){
	aCoordinatedEventListener = CoordinatedEventMulticaster.remove(aCoordinatedEventListener, newListener);
}
/**
 * Returns the order in which the views were created.
 * @param v java.util.Vector 
 * @return Vector 
 */
public java.util.Vector reorder(Vector v) {
	int lon=v.size();
	int i=0;
	int j=0;
	int k=0;
	Vector results= new Vector();
	String view=null;
	String view1=null;
	while (i<lon){  
		k=i;
		j=i+1;
		while (j<lon){
			if (((Integer)viewOrderList.get(v.elementAt(i))).intValue()>(((Integer)viewOrderList.get(v.elementAt(j))).intValue())){
				if (results.contains(v.elementAt(i))) results.removeElement(v.elementAt(i));
				if (results.contains(v.elementAt(j))) results.removeElement(v.elementAt(j));
				results.insertElementAt(v.elementAt(j),k);
				results.insertElementAt(v.elementAt(i),j);
				k=j;
			}		
		j=j+1;
		}
		 if (!(results.contains(v.elementAt(i)))) results.insertElementAt(v.elementAt(i),i);
		i=i+1;
	}
	return results;
}
/**
 * Resizes the active view.
 * @param height int
 * @param width int
 */
public void resizeActiveView(int height, int width) {
	String viewId=getActiveView();
	ViewRegistry vReg=(ViewRegistry)viewRegistryList.get(viewId);
	((JComponent)vReg.getView()).reshape(vReg.getXpos(),vReg.getYpos(),width,height); 
}
/**
 * Resizes and relocates the active view.
 * @param height int
 * @param width int
 * @param xpos int
 * @param ypos int
 */
public void resizeActiveView(int height, int width, int xpos, int ypos) {
	String viewId=getActiveView();
	ViewRegistry vReg=(ViewRegistry)viewRegistryList.get(viewId);
	((JComponent)vReg.getView()).reshape(xpos,ypos,width,height);
	vReg.setXpos(xpos);
	vReg.setYpos(ypos);
}
/**
 * Sets the value of the <I>activeView</I> property.
 * @param viewId String  - the new value of the property
 */
public void setActiveView(String viewId) {
	activeView=viewId;

}
/**
 * Returns the level of the navigationArea with respect to the WorkingArea.
 */
public void setLevel(int i) {
	level=i;	
}
/**
 * Sets the value of the <I>navigationArea</I> property.
 * @param na Container - the new value of the property
 */
public void setNavigationArea(SpInternalFrame na) {
	if (navigationArea==null){
		navigationArea=na;
		na.setContentPane(scrollpane);
	}
	else{
	Desktop.getDesktop().getWorkingArea().remove(na);
	navigationArea=Desktop.getDesktop().getWorkingArea().getFrameAtLevel(level,na.getWidth(),na.getHeight(),na.getX(),na.getY());
	}
}
/**
 * Sets the value of the <I>process</I> property.
 * @param proc com.ibm.dse.automaton.DSEProcessor - the new value of the property
 */
public void setProcess(com.ibm.dse.automaton.Processor proc) {
	process=proc;
	
}
/**
 * Rolls back all the operations of the navigation.
 * @param viewId String
 */
public void undo(String viewId) {
	
 modifyNavigation(viewId,false,true,null);	
}
}
