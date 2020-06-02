package composer;

import com.ibm.dse.base.*;

/**
 * Insert the type's description here.
 * Creation date: (02/01/03 16:38:11)
 * @author: Doron
 */
public abstract class BopRunner {

	Context m_ctx;
	DSEOperation m_bop;
/**
 * BopRunner constructor comment.
 */
public BopRunner() {
	super();
}

public BopRunner(String bop, String ctxName) throws Exception
{

	// we need this context of the state transition vars.
	Context ctxBase = (Context) Context.readObject("htmlSessionCtx");
	// the context that the BL uses
	m_ctx = (Context) Context.readObject(ctxName);
	// chain the, together.
	m_ctx.chainTo(ctxBase);
	m_bop = (DSEOperation) DSEOperation.readObject(bop);
}

public void setBop(String bop) throws Exception
{
	m_bop = (DSEOperation) DSEOperation.readObject(bop);
}

public void setCtx(String ctxName) throws Exception
{
	// we need this context of the state transition vars.
	Context ctxBase = (Context) Context.readObject("htmlSessionCtx");
	// the context that the BL uses
	m_ctx = (Context) Context.readObject(ctxName);
	// chain the, together.
	m_ctx.chainTo(ctxBase);
}

public abstract void initCtx() throws Exception;

public void execute() throws Exception
{
	System.out.println("in execute");
	// pass the created context to the BL.

	initCtx();	
	m_bop.setContext(m_ctx);
	// fill the context with some test values.

	// execute the BL
	m_bop.execute();
	m_bop.close();
	
}
}
