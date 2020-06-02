/* Created on 10/10/2006 */
package beans;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;


/**
 * 
 * @author Odedn
 */
public class SessionListener implements PhaseListener {
	
	private final static Logger logger = Logger.getLogger(SessionListener.class);

	/* (non-Javadoc)
	 * @see javax.faces.event.PhaseListener#afterPhase(javax.faces.event.PhaseEvent)
	 */
	public void afterPhase(PhaseEvent arg0)
	{
		/*
		 * check if the current session is active. 
		 * if not send a logout navigation event.
		 */
		logger.debug("afterPhase : " + arg0.toString());
		HttpSession session = JsfUtil.getSession();
		if(session.isNew()) {
			FacesContext ctx = FacesContext.getCurrentInstance();
			JsfUtil.addMessage("invalidSession");
			NavigationHandler nh = ctx.getApplication().getNavigationHandler();
			nh.handleNavigation(ctx,null,"logout");
			logger.info("logging out - invalid session");			
		} else {
			logger.debug("got session: " + session.getId());
		}

	}

	/* (non-Javadoc)
	 * @see javax.faces.event.PhaseListener#beforePhase(javax.faces.event.PhaseEvent)
	 */
	public void beforePhase(PhaseEvent arg0)
	{
		// TODO Auto-generated method stub
		logger.debug("afterPhase");

	}

	/* (non-Javadoc)
	 * @see javax.faces.event.PhaseListener#getPhaseId()
	 */
	public PhaseId getPhaseId()
	{
		// TODO Auto-generated method stub
		return (PhaseId.RESTORE_VIEW);
	}

}
