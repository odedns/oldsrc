/*
 * Created on 22/04/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package mataf.desktop;

import java.awt.Dimension;

import mataf.desktop.beans.MatafWorkingArea;

import mataf.desktop.views.MatafClientView;
import mataf.desktop.views.MatafTransactionView;


import com.ibm.dse.base.DSEClientOperation;
import com.ibm.dse.base.DSEException;
import com.ibm.dse.base.FormatElement;
import com.ibm.dse.base.KeyedCollection;
import com.ibm.dse.base.Notifier;
import com.ibm.dse.base.Settings;
import com.ibm.dse.desktop.DSETaskButton;
import com.ibm.dse.desktop.Desktop;
import com.ibm.dse.desktop.ExitButton;
import com.ibm.dse.desktop.NavigationController;
import com.ibm.dse.desktop.TaskArea;

import com.ibm.dse.gui.CoordinatedEventSource;



/**
 * @author eyalbz
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class MatafTaskButton extends DSETaskButton {
	
	private MatafClientView matafClientView = null;
	// Formar SpInternalFrame internalFrame
	private MatafTransactionView transactionView = null;
	/** identifier of the aborted status of a process **/
	public final static String ABORTED = "_aborted"; //$NON-NLS-1$
	/** identifier of the suspended status of a process */
	public final static String SUSPENDED = "_suspended"; //$NON-NLS-1$
	/** identifier of the co status of a process */
	public final static String COMPLETED = "_completed"; //$NON-NLS-1$
	private String process = null;
	/** identifier of the fast path format */
	protected static final String FAST_PATH_FORMAT_NAME = "fastPathInputFmt"; //$NON-NLS-1$
	
	private NavigationController nc = null;
	
	private boolean activate = false;
	private boolean close=false;
	
	private DSETaskButton Task = null;
	com.ibm.dse.automaton.Processor p = null;
	/** identifier of the Navigation Controller type**/
	public String TYPE = "NavController";
	class NewThread extends java.lang.Thread {
		public void run() {
			try {
				if (nc instanceof Notifier) {
					p = (com.ibm.dse.automaton.Processor) com.ibm.dse.automaton.DSEProcessor.readObject(process);
					nc.setProcess(p);
					p.getContext().addNotifier((Notifier) nc, ((Notifier) nc).getName(), TYPE);
					p.addProcessorStatusChangedListener(Task);
					p.execute();
					p.close();
				}
			} catch (Exception e) {
				e.printStackTrace();

			}
		}
	};
	/**
	 * 
	 */
	public MatafTaskButton() {
		super();
	}

	public void openTask(String viewId, String launchable, Boolean execOp, String type, int task, String parameters, String error, int width, int height, int x, int y) throws Exception {
		
		String navc = null;
		DSEClientOperation op = null;
		activate = true;
		Task = this;
		try {
			KeyedCollection settingsVTF = Settings.getSettings();
			MatafClientView activeClientView = MatafWorkingArea.getWorkingArea().getActiveClientView();//((MatafWorkingArea)Desktop.getDesktop().getWorkingArea()).getActiveClientView();
			if (Desktop.getDesktop().getComponentByName("ExitButton") != null)
				 ((ExitButton) Desktop.getDesktop().getComponentByName("ExitButton")).setEnabled(false);
			//$NON-NLS-1$
			setLevel(task);
			setClientView(activeClientView);
			try {
				navc = (String) settingsVTF.getValueAt("desktop.NavigationController"); //$NON-NLS-1$
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
			nc = (NavigationController) Class.forName(navc).newInstance();
			setNavigationController(nc);
			if (nc instanceof Notifier)
				 ((Notifier) nc).setName(TYPE);
			if (nc instanceof CoordinatedEventSource)
				 ((CoordinatedEventSource) nc).addCoordinatedEventListener(this);
			((MatafNavigationController) nc).setClientView(activeClientView);
			nc.setLevel(getLevel());
			if (type.equals("OPERATION")) { //$NON-NLS-1$
				op = (DSEClientOperation) getOperationInstance(launchable);
				try {
					FormatElement fe = (FormatElement) op.getFormat(FAST_PATH_FORMAT_NAME);
					if (fe != null)
						fe.unformat(parameters, op);
				} catch (DSEException e) {
					e.printStackTrace();
				}
				try {
					nc.openView(viewId, op, execOp);
					if (nc.getActiveView() != null)
						setTransactionView(((MatafNavigationController)nc).getTransactionView());
				} catch (Exception e) {
					// TODO replace this line
					//	Desktop.getDesktop().getWorkingArea().removeFrame(navigationArea);
					nc = null;
					e.printStackTrace();
				}
			}
			if (type.equals("PROCESS")) { //$NON-NLS-1$
				process = launchable;
				NewThread processorThread = new NewThread();
				getJMenuItem2().setEnabled(false);
				processorThread.start();
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
		activate = false;
	}
	
	/**
	 * Closes the task class and closes the navigation Area and all the views
	 * opened in the Area.
	 */
	public void closeTask() {
		close=true;
		getNavigationController().close();
		TaskArea ta = (TaskArea)getParent();
		if (ta != null){
			ta.removeTask(this);			
		}			
		//TODO close the client view if this is the last task
		
		
		
		//Desktop.getDesktop().getWorkingArea().removeFrame(getInternalFrame());
		}

	/**
	 * Sets the value of the <I>internalFrame</I> property.
	 * @param na SpInternalFrame - the new value of the property
	 */
	public void setClientView(MatafClientView matafClientView) {
		this.matafClientView = matafClientView;
	}

	public MatafClientView getMatafClientView() {
		return matafClientView;
	}
	
	/**
	 * Activates the navigation Area associated with the Task and gets the focus.
	 */
	public void activate() 
	{
		activate=true;
		
		if (nc != null) 
		{
			if ((!getMatafClientView().isVisible()) && p == null )
				getTransactionView().setVisible(true);
			
			String viewId =	getNavigationController().getActiveView();
			
			((TaskArea)getParent()).activate(this);
					
			//nc.activateView(viewId);
			activate = false;		
		}
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
	public void setTransactionView(MatafTransactionView view) {
		transactionView = view;
	}

}
