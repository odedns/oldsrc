/**
 * Date: 08/05/2007
 * File: MyVariableResolver.java
 * Package: jsfdemo
 */
package jsfdemo;

import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.VariableResolver;

import org.apache.log4j.Logger;

/**
 * @author a73552
 *
 */
public class MyVariableResolver extends VariableResolver {

	private VariableResolver originalResolver;
	private static final Logger log = Logger.getLogger(MyVariableResolver.class);
	/**
	 * The original resolver is automatically passed to the 
	 * custom VariableResolver by JSF
	 */
	public MyVariableResolver(VariableResolver origResolver) {

		this.originalResolver = origResolver;
	}

	
	/* (non-Javadoc)
	 * @see javax.faces.el.VariableResolver#resolveVariable(javax.faces.context.FacesContext, java.lang.String)
	 */
	@Override
	public Object resolveVariable(FacesContext ctx, String name)
			throws EvaluationException {
		// TODO Auto-generated method stub
		log.info("trying to resolve : " + name);
		Object o = this.originalResolver.resolveVariable(ctx, name);
		return(o);
	}


	/** 
	 * get the original VariableResolver used by 
	 * the JSF framwork.
	 * @return VariableResolver the orignal VariableResolver.
	 */
	public VariableResolver getOriginalResolver() {
		return originalResolver;
	}

}
