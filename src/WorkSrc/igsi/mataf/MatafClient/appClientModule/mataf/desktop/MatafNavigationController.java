package mataf.desktop;
import java.awt.Component;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.JPanel;

import mataf.desktop.beans.MatafWorkingArea;
import mataf.desktop.views.MatafBusinessPane;
import mataf.desktop.views.MatafClientView;
import mataf.desktop.views.MatafTransactionView;
import mataf.logger.GLogger;

import com.ibm.dse.base.ClientOperation;
import com.ibm.dse.base.Context;
import com.ibm.dse.base.DSEClientOperation;
import com.ibm.dse.base.DSEEventObject;
import com.ibm.dse.base.DSEInvalidArgumentException;
import com.ibm.dse.base.DSEInvalidClassException;
import com.ibm.dse.base.DSEInvalidRequestException;
import com.ibm.dse.base.DataMapperFormat;
import com.ibm.dse.base.FormatElement;
import com.ibm.dse.desktop.DSENavigationController;
import com.ibm.dse.desktop.Desktop;
import com.ibm.dse.desktop.TaskInfo;
import com.ibm.dse.gui.CoordinatedPanel;
import com.ibm.dse.gui.DSECoordinatedPanel;
import com.ibm.dse.gui.DSECoordinationEvent;

/*
 * Created on 20/04/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

/**
 * @author eyalbz
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class MatafNavigationController extends DSENavigationController {
	private MatafClientView clientView = null; //Former navigationArea
	private MatafTransactionView transactionView = null;
	private boolean taskAccessible = true;

	//protected Hashtable viewRegistryList = new Hashtable();
	/**
	 * 
	 */
	public MatafNavigationController() {
		super();

	}

	public MatafNavigationController(String aName) throws IOException {
		super(aName);

	}

	public void openView(String viewId) throws Exception {
		openView(viewId, null, new Boolean(false));
	}
	/**
	 * Opens a view, activates it, and stores all the information 
	 * related to this view.
	 * @param viewId  java.lang.String - identifier of the view  
	 * @param op ClientOperation   
	 * @param navigation int 
	 * @param linkContextTo String 
	 */
	public void openView(String viewId, ClientOperation op, int navigation, String linkContextTo, String inputPathFormat) {
		openView(viewId, viewId, op, navigation, linkContextTo, inputPathFormat);
	}
	/**
	 * Opens a view, activates it, and stores all the information 
	 * related to this view.
	 * @param viewId  java.lang.String - identifier of the view  
	 * @param op ClientOperation 
	 * @param execOp boolean 
	 */
	public void openView(String viewId, ClientOperation op, Boolean execOp) throws Exception {
		CoordinatedPanel view = null;
		try {
			view = (CoordinatedPanel) DSECoordinatedPanel.readObject(viewId);
		} catch (Exception e) {
			view = (CoordinatedPanel) Class.forName(viewId).newInstance();
		}
		//((DSECoordinatedPanel) view).getFocusManager();
		if (op != null) {
			view.setOperation(op);
		}
		if ((execOp != null) && (execOp.booleanValue()))
			view.tryRunOperation();

		if (((DSEClientOperation) op).getOperationStepRC() == 1)
			return;

		MatafViewRegistry reg = new MatafViewRegistry();
		reg.setView(view);
		if (getActiveView() == null) {
			reg.setParentView("");
		}
		reg.setOwner(getClientView());
		viewRegistryList.put(((JPanel) view).getName(), reg);
		viewOrderList.put(((JPanel) view).getName(), (new Integer(viewRegistryList.size())));
		view.addCoordinatedEventListener(this);

		activateView(((JPanel) view).getName());
		view.refresh();

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
	public void openView(String viewId, String viewSource, ClientOperation op, int navigation, String linkContextTo, String inputFormat, java.util.Hashtable position) {
		DataMapperFormat mapper = null;
		try {
			if ((inputFormat != null) && (inputFormat.length() > 0))
				mapper = (DataMapperFormat) FormatElement.readObject(inputFormat);
			openView(viewId, viewSource, op, navigation, linkContextTo, mapper, position);
		} catch (Exception e) {
			e.printStackTrace();
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
	 * @param mapper DataMapperFormat
	 */
	public void openView(String viewId, String viewSource, ClientOperation op, int navigation, String linkContextTo, DataMapperFormat mapper) {
		CoordinatedPanel view = null;
		boolean unchain = true;

		try {
			view = (CoordinatedPanel) DSECoordinatedPanel.readObject(viewId);
		} catch (Exception e) {
			try {
				view = (CoordinatedPanel) Class.forName(viewSource).newInstance();
			} catch (InstantiationException e1) {
				GLogger.error(this.getClass(), null, "Error while instaciating the view", e1, false);
			} catch (IllegalAccessException e1) {
				GLogger.error(this.getClass(), null, "Error while instaciating the view", e1, false);
			} catch (ClassNotFoundException e1) {
				GLogger.error(this.getClass(), null, "Error while instaciating the view", e1, false);
			}
		}
		//((DSECoordinatedPanel) view).getFocusManager();
		if (op != null)
			view.setOperation(op);
		MatafViewRegistry reg = new MatafViewRegistry();
		reg.setView(view);
		reg.setOwner(clientView);
		if (getActiveView() == null)
			reg.setParentView("");
		if ((navigation == NAV_PEER) && (getActiveView() != null)) {
			((MatafViewRegistry) viewRegistryList.get(getActiveView())).setNextView(((JPanel) view).getName());
			reg.setParentView(getParent(getActiveView()));
			reg.setPreviousView(getActiveView());
		}
		if ((navigation == NAV_CHILDREN) && (getActiveView() != null)) {
			reg.setParentView(getActiveView());
		}
		if (linkContextTo != null) {
			if (view.getContext() != null) {

				if (linkContextTo.equals(CTXUSED_PROCESS)) {
					try {
						view.getContext().chainTo(getProcess().getContext());
					} catch (DSEInvalidRequestException e1) {
						GLogger.error(this.getClass(), null, "Error while trying to chain the context of the new view to the processor context", e1, false);
					}
					if (mapper != null)
						try {
							mapper.mapContents(getProcess().getContext(), view.getContext());
						} catch (DSEInvalidArgumentException e2) {
							GLogger.error(this.getClass(), null, "Error while mapping view data to processor data", e2, false);
						} catch (DSEInvalidClassException e2) {
							GLogger.error(this.getClass(), null, "Error while mapping view data to processor data", e2, false);
						} catch (DSEInvalidRequestException e2) {
							GLogger.error(this.getClass(), null, "Error while mapping view data to processor data", e2, false);
						}

				}
				if (linkContextTo.equals(CTXUSED_ACTIVE)) {
					CoordinatedPanel view2 = ((MatafViewRegistry) viewRegistryList.get(getActiveView())).getView();

					try {
						view.getContext().chainTo(view2.getContext());
					} catch (DSEInvalidRequestException e2) {
						GLogger.error(this.getClass(), null, "Error chainning view context to active view context", e2, false);
					}
					if (mapper != null)
						try {
							mapper.mapContents(view2.getContext(), view.getContext());
						} catch (DSEInvalidArgumentException e1) {
							GLogger.error(this.getClass(), null, "Error while mapping new view data to active view data", e1, false);
						} catch (DSEInvalidClassException e1) {
							GLogger.error(this.getClass(), null, "Error while mapping new view data to active view data", e1, false);
						} catch (DSEInvalidRequestException e1) {
							GLogger.error(this.getClass(), null, "Error while mapping new view data to active view data", e1, false);
						}

				}
				if (linkContextTo.equals(CTXUSED_PARENT)) {
					CoordinatedPanel viewParent = ((MatafViewRegistry) viewRegistryList.get(getParent(getActiveView()))).getView();
					try {
						view.getContext().chainTo(viewParent.getContext());
					} catch (DSEInvalidRequestException e1) {
						GLogger.error(this.getClass(), null, "Error chainning view context to parent view context", e1, false);
					}
					if (mapper != null)
						try {
							mapper.mapContents(viewParent.getContext(), view.getContext());
						} catch (DSEInvalidArgumentException e2) {
							GLogger.error(this.getClass(), null, "Error while mapping new view data to parent view data", e2, false);
						} catch (DSEInvalidClassException e2) {
							GLogger.error(this.getClass(), null, "Error while mapping new view data to parent view data", e2, false);
						} catch (DSEInvalidRequestException e2) {
							GLogger.error(this.getClass(), null, "Error while mapping new view data to parent view data", e2, false);
						}
				}
			} else {
				if (linkContextTo.equals(CTXUSED_PROCESS)) {
					if (getProcess() != null)
						view.setContext(getProcess().getContext());
					else {
						GLogger.error(this.getClass(), null, "The context of the view can not be set to the process context.The process doesn't exist", new Exception(), false);
						return;
					}
					unchain = false;
				}
				if (linkContextTo.equals(CTXUSED_ACTIVE)) {
					CoordinatedPanel view2 = ((MatafViewRegistry) viewRegistryList.get(getActiveView())).getView();
					if (view2 != null)
						view.setContext(view2.getContext());
					else {
						GLogger.error(this.getClass(), null, "The context of the view can not be set to the view context.The active view doesn't exist", new Exception(), false);
						return;
					}
				}
				if (linkContextTo.equals(CTXUSED_PARENT)) {
					CoordinatedPanel viewParent = ((MatafViewRegistry) viewRegistryList.get(getParent(getActiveView()))).getView();
					if (viewParent != null)
						view.setContext(viewParent.getContext());
					else {
						GLogger.error(this.getClass(), null, "The context of the view can not be set to the Parent context.The view parent doesn't exist", new Exception(), false);
						return;
					}
					unchain = false;
				}
			}
		}

		reg.setNavigationType(navigation);
		viewRegistryList.put(((JPanel) view).getName(), reg);
		viewOrderList.put(((JPanel) view).getName(), new Integer(viewRegistryList.size()));
		((MatafViewRegistry) viewRegistryList.get(((JPanel) view).getName())).setUnchain(unchain);
		view.addCoordinatedEventListener(this);
		activateView(((JPanel) view).getName());
		view.refresh();

	}

	public void openView(String viewId, String viewSource, ClientOperation op, int navigation, String linkContextTo, DataMapperFormat mapper, java.util.Hashtable position) {
		CoordinatedPanel view = null;
		boolean unchain = true;
		try {
			try {
				view = (CoordinatedPanel) DSECoordinatedPanel.readObject(viewId);
			} catch (Exception e) {
				view = (CoordinatedPanel) Class.forName(viewSource).newInstance();
			}
			//((DSECoordinatedPanel) view).getFocusManager();
			if (op != null)
				view.setOperation(op);
			MatafViewRegistry reg = new MatafViewRegistry();
			reg.setView(view);
			reg.setOwner(clientView);
			if (getActiveView() == null)
				reg.setParentView("");
			if ((navigation == NAV_PEER) && (getActiveView() != null)) {
				((MatafViewRegistry) viewRegistryList.get(getActiveView())).setNextView(((JPanel) view).getName());
				reg.setParentView(getParent(getActiveView()));
				reg.setPreviousView(getActiveView());
			}
			if ((navigation == NAV_CHILDREN) && (getActiveView() != null)) {
				reg.setParentView(getActiveView());
			}
			if (linkContextTo != null) {
				if (view.getContext() != null) {

					if (linkContextTo.equals(CTXUSED_PROCESS)) {
						view.getContext().chainTo(getProcess().getContext());
						if (mapper != null)
							mapper.mapContents(getProcess().getContext(), view.getContext());

					}
					if (linkContextTo.equals(CTXUSED_ACTIVE)) {
						CoordinatedPanel view2 = ((MatafViewRegistry) viewRegistryList.get(getActiveView())).getView();
						view.getContext().chainTo(view2.getContext());
						if (mapper != null)
							mapper.mapContents(view2.getContext(), view.getContext());

					}
					if (linkContextTo.equals(CTXUSED_PARENT)) {
						CoordinatedPanel viewParent = ((MatafViewRegistry) viewRegistryList.get(getParent(getActiveView()))).getView();
						view.getContext().chainTo(viewParent.getContext());
						if (mapper != null)
							mapper.mapContents(viewParent.getContext(), view.getContext());
					}
				} else {
					if (linkContextTo.equals(CTXUSED_PROCESS)) {
						if (getProcess() != null)
							view.setContext(getProcess().getContext());
						else {
							GLogger.error(this.getClass(), null, "The context of the view can not be set to the process context.The view process doesn't exist", new Exception(), false);
							return;
						}
						unchain = false;
					}
					if (linkContextTo.equals(CTXUSED_ACTIVE)) {
						CoordinatedPanel view2 = ((MatafViewRegistry) viewRegistryList.get(getActiveView())).getView();
						if (view2 != null)
							view.setContext(view2.getContext());
						else {
							GLogger.error(this.getClass(), null, "The context of the view can not be set to the active view context.The active view doesn't exist", new Exception(), false);
							return;
						}
					}
					if (linkContextTo.equals(CTXUSED_PARENT)) {
						CoordinatedPanel viewParent = ((MatafViewRegistry) viewRegistryList.get(getParent(getActiveView()))).getView();
						if (viewParent != null)
							view.setContext(viewParent.getContext());
						else {
							GLogger.error(this.getClass(), null, "The context of the view can not be set to the parent context.The view parent doesn't exist", new Exception(), false);
							return;
						}
						unchain = false;
					}
				}
			}

			reg.setNavigationType(navigation);
			viewRegistryList.put(((JPanel) view).getName(), reg);
			viewOrderList.put(((JPanel) view).getName(), new Integer(viewRegistryList.size()));
			((MatafViewRegistry) viewRegistryList.get(((JPanel) view).getName())).setUnchain(unchain);
			view.addCoordinatedEventListener(this);
			if (position != null) {
				if ((((Boolean) position.get("usePos")).booleanValue()) == false && (((Boolean) position.get("useSize")).booleanValue()) == false) {
					activateView(((JPanel) view).getName());
				} else {
					reg.setSubview(true);
					reg.setParentView(getActiveView());
					activeSubView(((JPanel) view).getName(), position);
				}
			} else
				activateView(((JPanel) view).getName());

			if (((DSECoordinatedPanel) view).getExecuteWhenOpen() & ((DSECoordinatedPanel) view).getOperation() != null)
				 ((DSECoordinatedPanel) view).getOperation().execute();
			view.refresh();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * Handle close view action from the GUI point of view (handle tabs, operations, context)
	 * @param id java.lang.String - identifier of the view
	 */
	public void closeView(String id) {

		CoordinatedPanel view = (CoordinatedPanel) getViewInstance(id);
		if (view.isClosable()) {
			boolean unchain = ((MatafViewRegistry) viewRegistryList.get(id)).getUnchain();
			try {
				getClientView().removeTransactionView(getViewInstance(id));

			} catch (Exception e) {
				// it is possible that we are trying to remove a view that exists in the registry but not on the tab
				// in this situation we get exception and do nothing
			}
			view.close(unchain);
			//			if (previousView != null) {
			//				setActiveView(previousView);
			//				activateView(previousView);
			//			}
		} else {
			//TODO handle the situation when the view can not be close 
			//			Desktop.getDesktop().showMessage(Desktop.getDesktop().getWorkingArea().getWarningMessageWhenAViewCanNotBeClosed(),javax.swing.JOptionPane.WARNING_MESSAGE);
			//			close=false;
			//			((JPanel)view).requestFocus();
			//			Component c=(((DSECoordinatedPanel)view).getFocusManager()).getLastFocused();
			//				if ( c!=null)  c.requestFocus();
			//				else{
			//				 if (getFirstFocusableComponent(((JPanel)view))!=null)
			//					getFirstFocusableComponent(((JPanel)view)).requestFocus();
			//				}
		}

	}

	/**
	 * Returns the Container of the view.
	 * @return java.swing JComponent 
	 */
	public MatafClientView getClientView() {
		return clientView;
	}

	/**
	 * Sets the value of the <I>navigationArea</I> property.
	 * @param na Container - the new value of the property
	 */
	public void setClientView(MatafClientView clientView) {
		if (this.clientView == null) {
			this.clientView = clientView;
		}
	}

	public void activateView(String viewId) {
		// 
		//		if (((MatafViewRegistry) (viewRegistryList.get(viewId))).getSubview()) {
		//			reactivateSubView(viewId);
		//			return;
		//		}

		CoordinatedPanel view = (CoordinatedPanel) getViewInstance(viewId);

		if (getActiveView() != null)
			deActivateView(getActiveView());
		setActiveView(viewId);

		getClientView().addTransactionView((MatafTransactionView) view);

		setTransactionView(view);

		view.postInitialize();
		//		view.refresh();
		view.becomingActiveActions();

		// Get the first component into focus.
		/*Component c = (((DSECoordinatedPanel) view).getFocusManager()).getLastFocused();
		if (c != null)
			c.requestFocus();
		else {
			if (getFirstFocusableComponent(((JPanel) view)) != null)
				getFirstFocusableComponent(((JPanel) view)).requestFocus();
		}*/
	}

	public void reactivateSubView(String viewId) {
		CoordinatedPanel view = getViewInstance(viewId);
		if (view != null) {
			MatafClientView matafClientView = (MatafClientView) ((MatafViewRegistry) viewRegistryList.get(getActiveView())).getOwner();
			getClientView().setEnabled(false);
			matafClientView.validate();
			try {
				view.postInitialize();
				view.refresh();
				view.becomingActiveActions();
			} catch (Exception e) {
				e.printStackTrace();
			}

			matafClientView.setVisible(true);

		/*	if (getFirstFocusableComponent(((JPanel) view)) != null)
				getFirstFocusableComponent(((JPanel) view)).requestFocus();*/
		}
	}

	/**
	 * Returns the instance of the view passed as a parameter.
	 * @return com.ibm.dse.gui.CoordinatedPanel
	 * @param viewId java.lang.String - identifier of the view
	 */
	public CoordinatedPanel getViewInstance(String viewId) {
		return ((MatafViewRegistry) viewRegistryList.get(viewId)).getView();

	}

	/**
	 * Deactivates the view passed as the parameter.
	 * @param viewId java.lang.String - identifier of the view
	 */
	public void deActivateView(String viewId) {
		CoordinatedPanel view = null;
		try {
			view = (CoordinatedPanel) getViewInstance(viewId);
		} catch (Exception e) {
			// we dont have a view in the registry so we move on
		}
		if (view == null)
			return;
		else {
			try {

				getClientView().removeTransactionView(view);

			} catch (Exception e) {
				// we have a view in the registry but not on the tab so we move on
			}

		}

	}

	/**
	 * Contains the appropriate actions to be executed when DSECoordinationEvent has been triggered.
	 * @param event DSECoordinationEvent
	 */
	public void handleDSECoordinationEvent(DSECoordinationEvent event) {

		String viewId = null;
		String viewId2 = null;
		String viewParent = null;
		DSEEventObject CBTFevent = null;
		if (event.getEventType() != null) {
			if (event.getEventType().equals(DSECoordinationEvent.EVENT_EVENTYPE_ACTION)) {
				try {
					CBTFevent = new DSEEventObject(event.getName(), this);
					signalEvent(CBTFevent);
				} catch (DSEInvalidArgumentException dse) {
				}
			} else if (event.getEventType().equals(DSECoordinationEvent.EVENT_EVENTYPE_NAVIGATION)) {
				if (event.getEventSourceType().equals(DSECoordinationEvent.EVENT_SOURCETYPE_OPEN_VIEW)) {
					if (viewRegistryList.containsKey(event.getViewName())) {
						GLogger.error(this.getClass(), null, "the view" + event.getViewName() + "is already opened", new Exception(), false);
					} else {
						if ((event.getOperationName() != null) && ((event.getOperationName().trim()).length() > 0)) {
							try {
								event.setOperation((DSEClientOperation) DSEClientOperation.readObject(event.getOperationName()));
							} catch (Exception e) {
							}
						}
						openView(event.getViewName(), event.getViewSource(), (ClientOperation) event.getOperation(), event.getNavigation(), event.getLinkContextTo(), event.getOpenMapFormat(), event.getPosition());
					}
				}
				if (event.getEventSourceType().equals(DSECoordinationEvent.EVENT_SOURCETYPE_PREVIOUS)) {
					taskAccessible = false;
					previousView();
					taskAccessible = true;
				}
				if (event.getEventSourceType().equals(DSECoordinationEvent.EVENT_SOURCETYPE_NEXT)) {
					if ((event.getOperationName() != null) && ((event.getOperationName().trim()).length() > 0)) {
						try {
							event.setOperation((DSEClientOperation) DSEClientOperation.readObject(event.getOperationName()));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					taskAccessible = false;
					String viewName = event.getViewName();
					String viewSource = event.getViewSource();
					ClientOperation clientOp = (ClientOperation) event.getOperation();
					int navigation = event.getNavigation();
					String linkContextTo = event.getLinkContextTo();
					String openMapFormat = event.getOpenMapFormat();
					Hashtable position = event.getPosition();
					nextView(viewName, viewSource, clientOp, navigation, linkContextTo, openMapFormat, position);
					taskAccessible = true;
				}
				if (event.getEventSourceType().equals(DSECoordinationEvent.EVENT_SOURCETYPE_UNDO)) {
					//					TODO handle undo event
					//undo(getActiveView());
				}
				if (event.getEventSourceType().equals(DSECoordinationEvent.EVENT_SOURCETYPE_VIEW_VISIBLE)) {
					if (((MatafViewRegistry) viewRegistryList.get(getActiveView())).getSubview() == false)
						fireCoordinationEvent(event);
					else if (!(getViewInstance(getActiveView()) == event.getSource()))
						reactivateSubView(getActiveView());
				}

				if (event.getEventSourceType().equals(DSECoordinationEvent.EVENT_SOURCETYPE_CANCEL)) {
					//					TODO handle cancel event
					//cancel(getActiveView());
				}

				if (event.getEventSourceType().equals(DSECoordinationEvent.EVENT_SOURCETYPE_CLOSE_NAVIGATION)) {
					taskAccessible = false;
					closeNavigation(getActiveView(), event.getCloseMapFormat());
					setActiveView(null);
					taskAccessible = true;
					if (getProcess() == null) {
						event.setEventSourceType(DSECoordinationEvent.EVENT_SOURCETYPE_VIEW_CLOSED);
						fireCoordinationEvent(event);
					}
				}

				if (event.getEventSourceType().equals(DSECoordinationEvent.EVENT_SOURCETYPE_VIEW_OPENED)) {
					try {
						//						MatafTransactionView aView = (MatafTransactionView) event.getSource();
						//						MatafBusinessPane pane = MatafWorkingArea.getActiveClientView().getBusinessPanel().getBusinessPaneOfTransactionView(aView);
						//						pane.getTaskButton().activate();
						CBTFevent = new DSEEventObject(event.getName(), this);
						signalEvent(CBTFevent);
					} catch (DSEInvalidArgumentException dse) {
						dse.printStackTrace();
					}
				}
				if (event.getEventSourceType().equals(DSECoordinationEvent.EVENT_SOURCETYPE_VIEW_CLOSED)) {
					if (getActiveView() != null) {
						taskAccessible = false;
						viewId = event.getViewName();
						viewId2 = getPreviousView(viewId);
						viewParent = getParent(viewId);
						if (getChilds(viewId).size() != 0)
							closeView((String) getChilds(viewId).elementAt(0));

						if (((MatafViewRegistry) viewRegistryList.get(getActiveView())).getSubview() == true)
							//for subViews
							deRegisterSubView(getActiveView());
						else
							deRegister(viewId);

						if (viewId2 != null) {
							((MatafViewRegistry) viewRegistryList.get(viewId2)).setNextView(null);
							activateView(viewId2);
							getViewInstance(viewId2).refresh();
						} else {
							if ((viewParent) == null) {
								String view = getKey();
								if (view != null) {
									activateView(view);
									getViewInstance(viewId2).refresh();
									return;
								} else {
									setActiveView(null);
									if (getProcess() == null)
										fireCoordinationEvent(event);
									else
										getClientView().setVisible(false);
									return;
								}
							}
							if ((viewParent) != "") {
								activateView(viewParent);
								getViewInstance(viewId2).refresh();
							} else {
								setActiveView(null);
								if (getProcess() == null)
									fireCoordinationEvent(event);
								else {
									getClientView().setVisible(false);
								}
							}
						}
					}
					try {
						CBTFevent = new DSEEventObject(event.getName(), this);
						signalEvent(CBTFevent);
					} catch (DSEInvalidArgumentException dse) {
						dse.printStackTrace();
					}
					taskAccessible = true;
				}
				// for event OpenTask received
				if (event.getEventSourceType().equals(DSECoordinationEvent.EVENT_SOURCETYPE_OPEN_TASK)) {

					int x = 0, y = 0, w = 0, h = 0;
					java.util.Hashtable position = event.getPosition();
					TaskInfo ti = Desktop.getDesktop().getTaskInfo(event.getViewName());

					if (((Boolean) position.get("usePos")).booleanValue()) {
						x = new Integer((String) position.get("x")).intValue();
						y = new Integer((String) position.get("y")).intValue();
					}

					if (((Boolean) position.get("useSize")).booleanValue()) {
						w = new Integer((String) position.get("w")).intValue();
						h = new Integer((String) position.get("h")).intValue();
					}

					Desktop.getDesktop().getTaskArea().openTask(ti.getOperation(), ti.getOperationPanel(), ti.getExecuteOperation(), ti.getType(), ti.getShortDescription(), ti.getLongDescription(), ti.getTaskName(), "", "", w, h, x, y);
				}
			}
		}

	}

	/**
	 * Removes the entry of this view from the View registry.
	 * @param viewId java.lang.String - identifier of the view
	 */
	public void deRegisterSubView(String viewId) {
		try {
			CoordinatedPanel view = (CoordinatedPanel) getViewInstance(viewId);
			MatafViewRegistry v = (MatafViewRegistry) viewRegistryList.get(viewId);
			((DSECoordinatedPanel) getViewInstance(v.getParentView())).setEnabled(true);

			((MatafClientView) v.getOwner()).removeTransactionView(view);

			viewRegistryList.remove(viewId);
			viewOrderList.remove(viewId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Removes the entry of this view from the View registry.
	 * @param viewId java.lang.String - identifier of the view
	 */
	public void deRegister(String viewId) {
		int lon = 0;
		try {
			lon = (getChilds(viewId).size()) - 1;
			if (getNextView(viewId) != null) {
				closeView(getNextView(viewId));
			}
			if (lon != 0) {
				while (lon >= 0) {
					closeView((String) getChilds(viewId).elementAt(lon));
					lon = lon - 1;
				}
			}
			//			setActiveView(getPreviousView(viewId));
			closeView(viewId);
			viewRegistryList.remove(viewId);
			viewOrderList.remove(viewId);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns the viewId of the next view associated with the passed viewId.
	 * @param viewId
	 * @return String
	 */
	public String getNextView(String viewId) {
		return ((MatafViewRegistry) viewRegistryList.get(viewId)).getNextView();
	}

	/**
	 * Returns the viewId of the previous view associated with the viewId.
	 * @param viewId
	 * @return String
	 */
	public String getPreviousView(String viewId) {

		return ((MatafViewRegistry) viewRegistryList.get(viewId)).getPreviousView();

	}

	/**
	 * Returns the ID of the parent view associated with the viewId.
	 * @param viewId
	 * @returns String
	 */
	public String getParent(String viewId) {
		return ((MatafViewRegistry) viewRegistryList.get(viewId)).getParentView();
	}

	/**
	 * @return
	 */
	public MatafTransactionView getTransactionView() {
		return transactionView;
	}

	/**
	 * @param view
	 */
	public void setTransactionView(CoordinatedPanel view) {
		transactionView = (MatafTransactionView) view;
	}

	/**
	 * Deactivates the active view and shows the previous view.
	 */
	public void previousView() {
		String viewId = null;
		String viewId2 = null;
		viewId = getActiveView();
		viewId2 = getPreviousView(viewId);
		if (viewId2 != null) {
			activateView(viewId2);
			getViewInstance(viewId2).refresh();
		} else {
			GLogger.error(this.getClass(), null, "the view" + getActiveView() + "doesn't have a previous view assigned", new Exception(), false);
		}
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

	public void nextView(String id, String source, ClientOperation operation, int navigation, String linkContextTo, String inputPathFormat, java.util.Hashtable position) {
		String viewId = null;
		viewId = getActiveView();
		System.out.println("nextView id = " + id + "\tviewId = " + viewId);
		if (navigation != NAV_CHILDREN)
			 ((MatafViewRegistry) viewRegistryList.get(viewId)).setNextView(id);
		if (viewRegistryList.containsKey(id)) {

			activateView(id);
			getViewInstance(id).refresh();
		} else
			openView(id, source, operation, navigation, linkContextTo, inputPathFormat, position);
		((MatafViewRegistry) viewRegistryList.get(id)).setPreviousView(viewId);
	}

	/* Closes all the panels of the navigation and uses the closeMapFormat to
	* map the value to the active view.
	* @param viewId String 
	* @param closeMapFormat String 
	*/
	public void closeNavigation(String viewId, String closeMapFormat) {
		DataMapperFormat mapper = null;
		try {
			if ((closeMapFormat != null) && (closeMapFormat.length() > 0)) {
				mapper = (com.ibm.dse.base.DataMapperFormat) com.ibm.dse.base.FormatElement.readObject(closeMapFormat);
			}
			closeNavigation(viewId, mapper);
		} catch (Exception e) {
			GLogger.error(this.getClass(), null, "Exception when preforming close_navigation action", e, false);
		}
	}

	/**
	 * Closes all the panels of the navigation and, if a closeMapFormat is declared,
	 * maps the value to the active view.
	 * @param viewId String 
	 * @param mapper DataMapperFormat 
	 */
	public void closeNavigation(String viewId, DataMapperFormat mapper) {
		modifyNavigation(getRoot(viewId), true, false, mapper);
	}

	/**
	 * Closes, rolls back, and maps (depending on the parameters) the view and its peers ending with parent view.
	 * @param viewId String
	 * @param close boolean - True to close the view
	 * @param rollback boolean - True to roll back the view
	 * @param mapper DataMapperFormat
	 */
	public void modifyNavigation(String viewId, boolean close, boolean rollback, DataMapperFormat mapper) {
		Vector children = null;
		//		Context ctx = null;
		//		if (viewId != null) {
		//			ctx = getViewInstance(viewId).getContext();
		//		}

		children = getChilds(viewId);

		for (int i = 0; i < children.size(); i++) {
			modifyNavigation((String) children.elementAt(i), close, rollback, mapper);
		}

		if (rollback) {
			((CoordinatedPanel) getViewInstance(viewId)).rollBack();
		}
		if (close) {
			deRegister(viewId);
		}
		try {
			if (mapper != null) {
				System.out.println("map ctx here");
				//TODO Map context from closing view to target ctx
			}
		} catch (Exception e) {
			GLogger.error(this.getClass(), null, "Exception when preforming close_navigation action", e, false);
		}

	}

	/**
	 * Returns the peers associated with the viewId.
	 * @param viewId
	 * @return Vector
	 */
	public Vector getPeers(String viewId) {
		String parent = getParent(viewId);
		Vector children = getChilds(parent);
		return (children);
	}

	/**
	 * Returns a list of the children associated with the viewId.
	 * @return Vector
	 */
	public Vector getChilds(String viewId) {
		Vector views = new Vector();
		if (viewRegistryList.size() != 0) {
			Enumeration viewsId = viewRegistryList.keys();
			String view = null;
			String parent = null;
			while (viewsId.hasMoreElements()) {
				view = (String) viewsId.nextElement();
				parent = getParent(view);
				if (parent != null)
					if (parent.equals(viewId))
						views.addElement(view);
			}
			return reorder(views);
		} else
			return views;
	}

	/**
	 * Returns the root view 
	 * @param viewId
	 * @return String
	 */
	public String getRoot(String viewId) {
		String parent = viewId;
		String root = null;
		do {
			root = parent;
			parent = getParent(root);
		} while (parent != "");
		return root;
		// I will write a recursive method later on... 		
		//		String parent = getParent(viewId);
		//		if (parent != null) {
		//			getRoot(parent);
		//			
		//		}
		//		return viewId
	}

	/**
	 * Closes all the views opened for the task. 
	 */
	public void close() {
		//		while (getActiveView() != null && close == true)
		//		closeView(getActiveView());
	}

	/**
	 * @return
	 */
	public boolean isTaskAccessible() {
		return taskAccessible;
	}

}
