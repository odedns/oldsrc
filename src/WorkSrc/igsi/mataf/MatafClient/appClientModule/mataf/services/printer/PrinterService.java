package mataf.services.printer;

import java.awt.AWTException;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintJobAttributeSet;
import javax.print.attribute.PrintServiceAttributeSet;
import javax.print.attribute.standard.DateTimeAtCompleted;
import javax.print.attribute.standard.DateTimeAtCreation;
import javax.print.attribute.standard.OrientationRequested;
import javax.print.attribute.standard.PrinterState;
import javax.print.event.PrintJobEvent;
import javax.print.event.PrintJobListener;
import javax.print.event.PrintServiceAttributeEvent;
import javax.print.event.PrintServiceAttributeListener;
import javax.swing.RepaintManager;

import mataf.logger.GLogger;

import com.ibm.dse.base.Context;
import com.ibm.dse.base.DSEException;
import com.ibm.dse.base.DSEInvalidArgumentException;
import com.ibm.dse.base.DSEInvalidClassException;
import com.ibm.dse.base.DSEInvalidRequestException;
import com.ibm.dse.base.DSEObjectNotFoundException;
import com.ibm.dse.base.DataElement;
import com.ibm.dse.base.FormatElement;
import com.ibm.dse.base.JavaExtensions;
import com.ibm.dse.base.KeyedCollection;
import com.ibm.dse.base.KeyedCollectionFormat;
import com.ibm.dse.base.Service;
import com.ibm.dse.base.Tag;
import com.ibm.dse.base.TagAttribute;
import com.ibm.dse.services.formsprint.DSEJobNotFoundException;
import com.ibm.dse.services.formsprint.DSEPrinterException;

/**
 * PrinterService class
 *  	
 * @author 	Tibi Glazer
 * @version 	1.0
 * @since		9-10-2003
 * 
 **/
public class PrinterService extends Service implements com.ibm.dse.services.formsprint.PrintService, PrintJobListener {

	/**
	 *  Internal common interface for PrintFormJobInfo & PrintFrameJobInfo
	 */
	interface PrintJobInfo {
		/**
		 * Returns the completedAt.
		 * @return Date
		 */
		public Date getCompletedAt();

		/**
		 * Sets the completedAt.
		 * @param completedAt The completedAt to set
		 */
		public void setCompletedAt(Date completedAt);

		/**
		 * Returns the createdAt.
		 * @return Date
		 */
		public Date getCreatedAt();

		/**
		 * Sets the createdAt.
		 * @param createdAt The createdAt to set
		 */
		public void setCreatedAt(Date createdAt);

		/**
		 * Returns the submittedOn.
		 * @return int
		 */
		public int getSubmittedOn();

		/**
		 * Sets the submittedOn.
		 * @param submittedOn The submittedOn to set
		 */
		public void setSubmittedOn(int submittedOn);
	}

	/**
	 *  Internal PrintFormJobInfo class
	 */
	class PrintFormJobInfo implements PrintJobInfo {

		private PrintJobEvent jobInfo;
		private int submittedOn;
		private long jobId;
		private boolean isAsynch;
		private DateTimeAtCreation createdAt;
		private DateTimeAtCompleted completedAt;

		/**
		 *  Constructor
		 */
		PrintFormJobInfo() {
			jobInfo = null;
			jobId = 0;
			submittedOn = current.getValue();
			isAsynch = false;
			createdAt = new DateTimeAtCreation(new Date());
			completedAt = null;
		}

		/**
		 *  Constructor
		 */
		PrintFormJobInfo(boolean asynch) {
			this();
			isAsynch = asynch;
		}

		/**
		 *  Constructor
		 */
		PrintFormJobInfo(long jobId, boolean asynch) {
			this();
			isAsynch = asynch;
			this.jobId = jobId;
		}

		/**
		 * Returns the isAsynch.
		 * @return boolean
		 */
		public boolean isAsynch() {
			return isAsynch;
		}

		/**
		 * Returns the jobInfo.
		 * @return PrintJobEvent
		 */
		public PrintJobEvent getJobInfo() {
			return jobInfo;
		}

		/**
		 * Sets the isAsynch.
		 * @param isAsynch The isAsynch to set
		 */
		public void setIsAsynch(boolean isAsynch) {
			this.isAsynch = isAsynch;
		}

		/**
		 * Sets the jobInfo.
		 * @param jobInfo The jobInfo to set
		 */
		public void setJobInfo(PrintJobEvent jobInfo) {
			this.jobInfo = jobInfo;
		}

		/**
		 * Returns the completedAt.
		 * @return Date
		 */
		public Date getCompletedAt() {
			return (completedAt != null) ? completedAt.getValue() : null;
		}

		/**
		 * Sets the completedAt.
		 * @param completedAt The completedAt to set
		 */
		public void setCompletedAt(Date completedAt) {
			this.completedAt = new DateTimeAtCompleted(completedAt);
		}

		/**
		 * Returns the createdAt.
		 * @return Date
		 */
		public Date getCreatedAt() {
			return (createdAt != null) ? createdAt.getValue() : null;
		}

		/**
		 * Sets the createdAt.
		 * @param createdAt The createdAt to set
		 */
		public void setCreatedAt(Date createdAt) {
			this.createdAt = new DateTimeAtCreation(createdAt);
		}

		/**
		 * Returns the submittedOn.
		 * @return int
		 */
		public int getSubmittedOn() {
			return submittedOn;
		}

		/**
		 * Sets the submittedOn.
		 * @param submittedOn The submittedOn to set
		 */
		public void setSubmittedOn(int submittedOn) {
			this.submittedOn = submittedOn;
		}

		/**
		 * Returns the jobId.
		 * @return long
		 */
		public long getJobId() {
			return jobId;
		}

		/**
		 * Sets the jobId.
		 * @param jobId The jobId to set
		 */
		public void setJobId(long jobId) {
			this.jobId = jobId;
		}

		/**
		 * @see java.lang.Object#toString()
		 */
		public String toString() {
			String s = "<PrintFormJobInfo " + " ";
			s = s + "printer=\"" + printers[submittedOn].getPrintService().getName() + "\" ";
			s = s + "id=\"" + Long.toString(jobId) + "\" ";
			s = s + "async=\"" + Boolean.toString(isAsynch) + "\" ";
			s = s + "created=\"" + createdAt.toString() + "\" ";
			if (completedAt != null) {
				s = s + "completed=\"" + completedAt.toString() + "\" ";
			}
			s = s + ">";
			return s;
		}
	}

	/**
	 *  Internal PrintFrameJobInfo class
	 */
	class PrintFrameJobInfo implements PrintJobInfo, Printable {

		private Component component;
		private int submittedOn;
		private DateTimeAtCreation createdAt;
		private DateTimeAtCompleted completedAt;

		/**
		 *  Constructor
		 */
		PrintFrameJobInfo(Component c) {
			component = c;
			submittedOn = current.getValue();
			createdAt = new DateTimeAtCreation(new Date());
			completedAt = null;
		}

		/**
		 * Returns the completedAt.
		 * @return Date
		 */
		public Date getCompletedAt() {
			return (completedAt != null) ? completedAt.getValue() : null;
		}

		/**
		 * Sets the completedAt.
		 * @param completedAt The completedAt to set
		 */
		public void setCompletedAt(Date completedAt) {
			this.completedAt = new DateTimeAtCompleted(completedAt);
		}

		/**
		 * Returns the createdAt.
		 * @return Date
		 */
		public Date getCreatedAt() {
			return (createdAt != null) ? createdAt.getValue() : null;
		}

		/**
		 * Sets the createdAt.
		 * @param createdAt The createdAt to set
		 */
		public void setCreatedAt(Date createdAt) {
			this.createdAt = new DateTimeAtCreation(createdAt);
		}

		/**
		 * Returns the submittedOn.
		 * @return int
		 */
		public int getSubmittedOn() {
			return submittedOn;
		}

		/**
		 * Sets the submittedOn.
		 * @param submittedOn The submittedOn to set
		 */
		public void setSubmittedOn(int submittedOn) {
			this.submittedOn = submittedOn;
		}

		/**
		 * @see java.awt.print.Printable#print(Graphics, PageFormat, int)
		 */
		public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
			if (pageIndex > 0) {
				return (NO_SUCH_PAGE);
			}
			else {
				Graphics2D g2d = (Graphics2D) graphics;
				g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
				disableDoubleBuffering(component);
				component.paint(g2d);
				enableDoubleBuffering(component);
				completedAt = new DateTimeAtCompleted(new Date());
				return (PAGE_EXISTS);
			}
		}

		/**
		 *  Disable double buffering 
		 */
		public void disableDoubleBuffering(Component c) {
			RepaintManager mngr = RepaintManager.currentManager(c);
			mngr.setDoubleBufferingEnabled(false);
		}

		/**
		 *  Enable double buffering 
		 */
		public void enableDoubleBuffering(Component c) {
			RepaintManager mngr = RepaintManager.currentManager(c);
			mngr.setDoubleBufferingEnabled(true);
		}

		/**
		 * @see java.lang.Object#toString()
		 */
		public String toString() {
			String s = "<PrintFrameJobInfo " + " ";
			s = s + "printer=\"" + printers[submittedOn].getPrintService().getName() + "\" ";
			s = s + "created=\"" + createdAt.toString() + "\" ";
			if (completedAt != null) {
				s = s + "completed=\"" + completedAt.toString() + "\" ";
			}
			s = s + ">";
			return s;
		}
	}

	/**
	 *  Internal PrintScreenJobInfo class
	 */
	class PrintScreenJobInfo implements PrintJobInfo, Printable {

		private int submittedOn;
		private DateTimeAtCreation createdAt;
		private DateTimeAtCompleted completedAt;

		/**
		 *  Constructor
		 */
		PrintScreenJobInfo() {
			submittedOn = current.getValue();
			createdAt = new DateTimeAtCreation(new Date());
			completedAt = null;
		}

		/**
		 * Returns the completedAt.
		 * @return Date
		 */
		public Date getCompletedAt() {
			return (completedAt != null) ? completedAt.getValue() : null;
		}

		/**
		 * Sets the completedAt.
		 * @param completedAt The completedAt to set
		 */
		public void setCompletedAt(Date completedAt) {
			this.completedAt = new DateTimeAtCompleted(completedAt);
		}

		/**
		 * Returns the createdAt.
		 * @return Date
		 */
		public Date getCreatedAt() {
			return (createdAt != null) ? createdAt.getValue() : null;
		}

		/**
		 * Sets the createdAt.
		 * @param createdAt The createdAt to set
		 */
		public void setCreatedAt(Date createdAt) {
			this.createdAt = new DateTimeAtCreation(createdAt);
		}

		/**
		 * Returns the submittedOn.
		 * @return int
		 */
		public int getSubmittedOn() {
			return submittedOn;
		}

		/**
		 * Sets the submittedOn.
		 * @param submittedOn The submittedOn to set
		 */
		public void setSubmittedOn(int submittedOn) {
			this.submittedOn = submittedOn;
		}

		/**
		 * @see java.awt.print.Printable#print(Graphics, PageFormat, int)
		 */
		public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
			if (pageIndex > 0) {
				return (NO_SUCH_PAGE);
			}
			else {

				try {
					Robot r = new Robot();
					int w = Toolkit.getDefaultToolkit().getScreenSize().width;
					int h = Toolkit.getDefaultToolkit().getScreenSize().height;
					double px = pageFormat.getImageableX();
					double py = pageFormat.getImageableY();
					double pw = pageFormat.getImageableWidth();
					double ph = pageFormat.getImageableHeight();
					double wscale = pw / w;
					double hscale = ph / h;
					double factor = Math.min(wscale, hscale);
					int ipw = (int) (pw * (factor / wscale));
					int iph = (int) (ph * (factor / hscale));
					int ipx = (int) (px + (pw - ipw) / 2);
					int ipy = (int) (py + (ph - iph) / 2);
					BufferedImage bim = r.createScreenCapture(new Rectangle(0, 0, w, h));
					Graphics2D g2d = (Graphics2D) graphics;
					g2d.drawImage((Image) bim, ipx, ipy, ipw, iph, null);
					completedAt = new DateTimeAtCompleted(new Date());
					return (PAGE_EXISTS);
				}
				catch (AWTException e) {
					return (NO_SUCH_PAGE);

				}
			}
		}

		/**
		 * @see java.lang.Object#toString()
		 */
		public String toString() {
			String s = "<PrintFrameJobInfo " + " ";
			s = s + "printer=\"" + printers[submittedOn].getPrintService().getName() + "\" ";
			s = s + "created=\"" + createdAt.toString() + "\" ";
			if (completedAt != null) {
				s = s + "completed=\"" + completedAt.toString() + "\" ";
			}
			s = s + ">";
			return s;
		}
	}

	/**
	 *  Internal PrintCleanTask class
	 */
	class PrintCleanTask extends TimerTask {

		/**
		 * Constructor for PrintCleanTask.
		 */
		public PrintCleanTask() {
			super();
		}

		/**
		 * @see java.lang.Runnable#run() : remove all PrintFormJobInfo that completed more than one hour ago
		 */
		public void run() {
			long now = System.currentTimeMillis();
			Iterator iter = jobsInfo.values().iterator();
			while (iter.hasNext()) {
				PrintJobInfo pji = (PrintJobInfo) iter.next();
				Date dt = pji.getCompletedAt();
				if ((dt != null) && (((now - dt.getTime()) / 1000) > 3600)) {
					iter.remove();
				}
			}
		}
	}

	/**
	 *  Internal Printer class
	 */
	class Printer implements PrintServiceAttributeListener {

		private javax.print.PrintService printService = null;
		private PrinterState state = PrinterState.UNKNOWN;

		/**
		 *  Default constructor
		 */
		Printer() {
		}

		/**
		 *  Default constructor
		 */
		Printer(javax.print.PrintService ps) {
			printService = ps;
			printService.addPrintServiceAttributeListener(this);
		}

		/**
		 * @see javax.print.event.PrintServiceAttributeListener#attributeUpdate(PrintServiceAttributeEvent)
		 */
		public void attributeUpdate(PrintServiceAttributeEvent psae) {
			PrintServiceAttributeSet psas = psae.getAttributes();
			if (psas.containsKey(PrinterState.class)) {
				state = (PrinterState) psas.get(PrinterState.class);
			}
		}

		/**
		 * Returns the printService.
		 * @return javax.print.PrintService
		 */
		public javax.print.PrintService getPrintService() {
			return printService;
		}

		/**
		 * Sets the printService.
		 * @param printService The printService to set
		 */
		public void setPrintService(javax.print.PrintService printService) {
			this.printService = printService;
		}

		/**
		 * Returns the state.
		 * @return int
		 */
		public PrinterState getState() {
			return state;
		}

		/**
		 * Sets the status.
		 * @param status The status to set
		 */
		public void setState(PrinterState state) {
			this.state = state;
		}
	}

	/**
	 * Detected printers 
	 */
	private Printer[] printers;

	/**
	 * Hashtable containing job information: the key is the printjob's hashcode
	 */
	private Hashtable jobsInfo = new Hashtable();

	/**
	 * Current printer
	 */
	private PrinterName current = PrinterName.NONE;

	/**
	 * Form files directory path
	 */
	private String formsPath = null;

	/**
	 * Print completed flag
	 */
	private boolean done;

	/**
	 * Cleanup timer
	 */
	private Timer timer;

	/**
	 * Constructor for PrinterService.
	 */
	public PrinterService() {
		super();
	}

	/**
	 * Constructor for PrinterService.
	 * @param arg0
	 * @throws IOException
	 */
	public PrinterService(String arg0) throws IOException {
		super(arg0);
	}

	/**
	 * Returns the formsPath.
	 * @return String
	 */
	public String getFormsPath() {
		return formsPath;
	}

	/**
	 * Sets the formsPath.
	 * @param formsPath The formsPath to set
	 */
	public void setFormsPath(String formsPath) {
		this.formsPath = formsPath;
	}

	/**
	 * Returns the done.
	 * @return boolean
	 */
	public boolean isDone() {
		return done;
	}

	/**
	 * Sets the done.
	 * @param done The done to set
	 */
	public void setDone(boolean done) {
		this.done = done;
	}

	/**
	 * @see com.ibm.dse.base.Externalizable#initializeFrom(Tag)
	 */
	public Object initializeFrom(Tag arg0) throws IOException, DSEException {
		for (int i = 0; i < arg0.getAttrList().size(); i++) {
			TagAttribute attribute = (TagAttribute) arg0.getAttrList().elementAt(i);
			if (attribute.getName().equals("formsPath")) {
				formsPath = (String) (attribute.getValue());
			}
		}
		return this;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {

		String s = "<" + getTagName() + " ";
		s = JavaExtensions.addAttribute(s, "formsPath", getFormsPath());
		s = s + ">";
		return s;
	}

	/**
	 * @see com.ibm.dse.services.formsprint.PrintService#getJobListPrintingResults()
	 */
	public Vector getJobListPrintingResults() {
		Vector results = new Vector();
		Iterator iter = jobsInfo.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			Long jobId = (Long) entry.getKey();
			PrintFormJobInfo pji = (PrintFormJobInfo) entry.getValue();
			results.add(pji.toString());
		}
		return results;
	}

	/**
	 * @see com.ibm.dse.services.formsprint.PrintService#getJobResult(long)
	 */
	public KeyedCollection getJobResult(long arg0) throws DSEJobNotFoundException {
		KeyedCollection result = new KeyedCollection();
		if (jobsInfo.containsKey(new Long(arg0))) {
			PrintFormJobInfo pji = (PrintFormJobInfo) jobsInfo.get(new Long(arg0));
		}
		else {
			throw new DSEJobNotFoundException(String.valueOf(arg0));
		}
		return result;
	}

	/**
	 * @see com.ibm.dse.services.formsprint.PrintService#getJobsCurrentlyProcessing()
	 */
	public int getJobsCurrentlyProcessing() {
		int num = 0;
		Iterator iter = jobsInfo.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			Long jobId = (Long) entry.getKey();
			PrintFormJobInfo pji = (PrintFormJobInfo) entry.getValue();
			if (pji.completedAt == null)
				num++;
		}
		return num;
	}

	/**
	 * @see com.ibm.dse.services.formsprint.PrintService#getStatus()
	 */
	public int getStatus() {
		return printers[current.getValue()].getState().getValue();
	}

	/**
	 * @see com.ibm.dse.services.formsprint.PrintService#initialize()
	 */
	public void initialize() throws DSEPrinterException {
		GLogger.debug("initialize started.");

		/*
		 * Build an array of available printers 
		 */
		javax.print.PrintService[] p = PrintServiceLookup.lookupPrintServices(null, null);
		if (p.length == 0) {
			DSEPrinterException pe = new DSEPrinterException(PrinterConstants.NO_PRINTERS_AVAILABLE);
			GLogger.error(PrinterService.class, null, PrinterConstants.NO_PRINTERS_AVAILABLE, pe, true);
			throw pe;
		}

		/*
		 * Default print service will be always on first entry
		 */
		javax.print.PrintService dps = PrintServiceLookup.lookupDefaultPrintService();

		int num = p.length;
		if (num > PrinterName.length) {
			num = PrinterName.length;
		}
		printers = new Printer[num];
		for (int i = 0, j = 0; i < num; i++) {
			if (j == 0) {
				printers[j++] = new Printer(dps);
			}
			else if (!p[i].getName().equals(dps.getName())) {
				printers[j++] = new Printer(p[i]);
			}
		}

		/*
		 * Schedule a print job cleanup task that will run on midnight
		 */
		Calendar now = Calendar.getInstance();
		now.setLenient(true);
		now.set(Calendar.HOUR_OF_DAY, 0);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);
		now.set(Calendar.MILLISECOND, 0);
		now.add(Calendar.DAY_OF_MONTH, 1);
		timer = new Timer();
		timer.schedule(new PrintCleanTask(), now.getTime());
		current = PrinterName.DEFAULT;
		GLogger.debug("initialize ended.");
	}

	/**
	 * @see com.ibm.dse.services.formsprint.PrintService#isJobCompleted(long)
	 */
	public boolean isJobCompleted(long arg0) throws DSEJobNotFoundException {

		try {
			Object jobInfo = jobsInfo.get(new Long(arg0));
			if (jobInfo == null) {
				return false;
			}
			else if (jobInfo instanceof PrintJobEvent) {
				PrintJobEvent pje = (PrintJobEvent) jobInfo;
				int event = pje.getPrintEventType();
				return (event != PrintJobEvent.REQUIRES_ATTENTION);
			}
		}
		catch (ArrayIndexOutOfBoundsException e) {
			DSEJobNotFoundException je = new DSEJobNotFoundException(Integer.toString((int) arg0));
			GLogger.error(PrinterService.class, null, Integer.toString((int) arg0), je, false);
			throw je;
		}
		return true;
	}

	/**
	 * @see com.ibm.dse.services.formsprint.PrintService#printForm(FormatElement, Context)
	 */
	public long printForm(FormatElement arg0, Context arg1) throws DSEPrinterException, DSEException {
		return printForm(buildForm(null, arg0, arg1));
	}

	/**
	 * @see com.ibm.dse.services.formsprint.PrintService#printForm(String, Context)
	 */
	public long printForm(String arg0, Context arg1) throws DSEPrinterException, DSEException, IOException {
		return printForm(buildForm(arg0, arg1));
	}

	/**
	 * @see com.ibm.dse.services.formsprint.PrintService#printForm(String)
	 */
	public long printForm(String arg0) throws DSEPrinterException {
		GLogger.debug("printForm started.");
		DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
		Doc doc = new SimpleDoc(arg0.getBytes(), DocFlavor.BYTE_ARRAY.AUTOSENSE, null);
		DocPrintJob job = printers[current.getValue()].getPrintService().createPrintJob();
		job.addPrintJobListener(this);
		int jobId = job.hashCode();
		jobsInfo.put(new Long(jobId), new PrintFormJobInfo(true));
		try {
			job.print(doc, null);
		}
		catch (PrintException e) {
			DSEPrinterException pe = new DSEPrinterException(e.getMessage());
			GLogger.error(PrinterService.class, null, e.getMessage(), pe, false);
			throw pe;
		}			
		GLogger.debug("printForm ended.");
		return jobId;
	}

	/**
	 * @see com.ibm.dse.services.formsprint.PrintService#printFormAndWait(FormatElement, Context)
	 */
	public void printFormAndWait(FormatElement arg0, Context arg1) throws DSEPrinterException, DSEException {
		printFormAndWait(buildForm(null, arg0, arg1));
	}

	/**
	 * @see com.ibm.dse.services.formsprint.PrintService#printFormAndWait(String, Context)
	 */
	public void printFormAndWait(String arg0, Context arg1) throws DSEPrinterException, DSEException, IOException {
		printFormAndWait(buildForm(arg0, arg1));
	}

	/**
	 * @see com.ibm.dse.services.formsprint.PrintService#printFormAndWait(String)
	 */
	public void printFormAndWait(String arg0) throws DSEPrinterException {
		GLogger.debug("printFormAndWait started.");
		DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
		Doc doc = new SimpleDoc(arg0.getBytes(), DocFlavor.BYTE_ARRAY.AUTOSENSE, null);
		DocPrintJob job = printers[current.getValue()].getPrintService().createPrintJob();
		job.addPrintJobListener(this);
		int jobId = job.hashCode();
		jobsInfo.put(new Long(jobId), new PrintFormJobInfo(false));
		setDone(false);
		try {
			job.print(doc, null);
		}
		catch (PrintException e) {
			DSEPrinterException pe = new DSEPrinterException(e.getMessage());
			GLogger.error(PrinterService.class, null, e.getMessage(), pe, false);
			throw pe;
		}
		waitForDone();
		job.removePrintJobListener(this);
		GLogger.debug("printFormAndWait ended.");
		return;
	}

	/**
	 * Print frame 
	 */
	public void printFrame(Component comp) throws DSEPrinterException {
		GLogger.debug("PrintFrame started.");
		try {
			PrinterJob job = PrinterJob.getPrinterJob();
			job.setPrintService(printers[current.getValue()].getPrintService());
			PrintFrameJobInfo pji = new PrintFrameJobInfo(comp);
			int jobId = job.hashCode();
			jobsInfo.put(new Long(jobId), pji);
			job.setPrintable(pji);
			job.print();
		}
		catch (PrinterException e) {
			DSEPrinterException pe = new DSEPrinterException(e.getMessage());
			GLogger.error(PrinterService.class, null, e.getMessage(), pe, false);
			throw pe;
		}
		GLogger.debug("PrintFrame ended.");
	}

	/**
	 * Print screen 
	 */
	public void printScreen() throws DSEPrinterException {
		GLogger.debug("PrintScreen started.");
		try {
			PrinterJob job = PrinterJob.getPrinterJob();
			job.setPrintService(printers[current.getValue()].getPrintService());
			PrintScreenJobInfo pji = new PrintScreenJobInfo();
			int jobId = job.hashCode();
			jobsInfo.put(new Long(jobId), pji);
			job.setPrintable(pji);
			HashPrintRequestAttributeSet as = new HashPrintRequestAttributeSet();
			as.add(OrientationRequested.LANDSCAPE);
			job.print(as);
		}
		catch (PrinterException e) {
			DSEPrinterException pe = new DSEPrinterException(e.getMessage());
			GLogger.error(PrinterService.class, null, e.getMessage(), pe, false);
			throw pe;
		}
		GLogger.debug("PrintScreen ended.");
	}

	/**
	 * @see com.ibm.dse.services.formsprint.PrintService#setFormName(String)
	 */
	public void setFormName(String arg0) {
		setFormsPath(arg0);
	}

	/**
	 * @see com.ibm.dse.services.formsprint.PrintService#setPrinterName(String)
	 */
	public void setPrinterName(String arg0) {
		String[] s = current.getStringTable();
		PrinterName[] v = (PrinterName[]) current.getEnumValueTable();
		for (int i = 0; i < s.length; i++) {
			if (arg0.equalsIgnoreCase(s[i])) {
				current = v[i];
				break;
			}
		}
	}

	/**
	 * @see com.ibm.dse.services.formsprint.PrintService#setServerName(String)
	 */
	public void setServerName(String arg0) {
		setPrinterName(arg0);
	}

	/**
	 * getPrinterName
	 */
	public String getPrinterName() {
		String[] s = current.getStringTable();
		PrinterName[] v = (PrinterName[]) current.getEnumValueTable();
		for (int i = 0; i < v.length; i++) {
			if (current == v[i])
				return s[i];
		}
		return null;
	}

	/**
	 * getServerName
	 */
	public String getServerName() {
		return getPrinterName();
	}


	/**
	 * @see com.ibm.dse.services.formsprint.PrintService#shutdown()
	 */
	public void shutdown() {
		GLogger.debug("shutdown started.");
		for (int i = 0; i < printers.length; i++) {
			printers[i].getPrintService().removePrintServiceAttributeListener(printers[i]);
			printers[i] = null;
		}
		timer.cancel();

		GLogger.debug("shutdown ended.");
	}

	/**
	 * @see javax.print.event.PrintJobListener#printDataTransferCompleted(PrintJobEvent)
	 */
	public void printDataTransferCompleted(PrintJobEvent pje) {
		GLogger.debug("printDataTransferCompleted started.");
		DocPrintJob job = pje.getPrintJob();
		if (jobsInfo.containsKey(new Long(job.hashCode()))) {
			PrintFormJobInfo jobInfo = (PrintFormJobInfo) jobsInfo.get(new Long(job.hashCode()));
			jobInfo.setJobInfo(pje);
		}
		GLogger.debug("printDataTransferCompleted end.");
	}

	/**
	 * @see javax.print.event.PrintJobListener#printJobCanceled(PrintJobEvent)
	 */
	public void printJobCanceled(PrintJobEvent pje) {
		GLogger.debug("printJobCanceled started.");
		DocPrintJob job = pje.getPrintJob();
		Date dt = null;
		PrintJobAttributeSet attset = job.getAttributes();
		if (attset.containsKey(DateTimeAtCompleted.class)) {
			dt = ((DateTimeAtCompleted) attset.get(DateTimeAtCompleted.class)).getValue();
		}
		else {
			dt = new Date();
		}
		if (jobsInfo.containsKey(new Long(job.hashCode()))) {
			PrintFormJobInfo jobInfo = (PrintFormJobInfo) jobsInfo.get(new Long(job.hashCode()));
			jobInfo.setJobInfo(pje);
			jobInfo.setCompletedAt(dt);
			if (jobInfo.isAsynch) {
				try {
					signalEvent(PrinterConstants.CANCELED);
				}
				catch (DSEInvalidArgumentException e) {
					GLogger.debug("printJobCanceled DSEInvalidArgumentException: " + e.getMessage());
				}
			}
			else {
				setDone(true);
			}
		}
		GLogger.debug("printJobCanceled ended.");
	}

	/**
	 * @see javax.print.event.PrintJobListener#printJobCompleted(PrintJobEvent)
	 */
	public void printJobCompleted(PrintJobEvent pje) {
		GLogger.debug("printJobCompleted started.");
		DocPrintJob job = pje.getPrintJob();
		Date dt = null;
		PrintJobAttributeSet attset = job.getAttributes();
		if (attset.containsKey(DateTimeAtCompleted.class)) {
			dt = ((DateTimeAtCompleted) attset.get(DateTimeAtCompleted.class)).getValue();
		}
		else {
			dt = new Date();
		}
		if (jobsInfo.containsKey(new Long(job.hashCode()))) {
			PrintFormJobInfo jobInfo = (PrintFormJobInfo) jobsInfo.get(new Long(job.hashCode()));
			jobInfo.setJobInfo(pje);
			jobInfo.setCompletedAt(dt);
			if (jobInfo.isAsynch) {
				try {
					signalEvent(PrinterConstants.COMPLETED);
					job.removePrintJobListener(this);
				}
				catch (DSEInvalidArgumentException e) {
					GLogger.debug("printJobCompleted DSEInvalidArgumentException: " + e.getMessage());
				}
			}
			else {
				setDone(true);
			}
		}
		GLogger.debug("printJobCompleted ended.");
	}

	/**
	 * @see javax.print.event.PrintJobListener#printJobFailed(PrintJobEvent)
	 */
	public void printJobFailed(PrintJobEvent pje) {
		GLogger.debug("printJobFailed started.");
		DocPrintJob job = pje.getPrintJob();
		Date dt = null;
		PrintJobAttributeSet attset = job.getAttributes();
		if (attset.containsKey(DateTimeAtCompleted.class)) {
			dt = ((DateTimeAtCompleted) attset.get(DateTimeAtCompleted.class)).getValue();
		}
		else {
			dt = new Date();
		}
		if (jobsInfo.containsKey(new Long(job.hashCode()))) {
			PrintFormJobInfo jobInfo = (PrintFormJobInfo) jobsInfo.get(new Long(job.hashCode()));
			jobInfo.setJobInfo(pje);
			jobInfo.setCompletedAt(dt);
			if (jobInfo.isAsynch) {
				try {
					signalEvent(PrinterConstants.FAILED);
					job.removePrintJobListener(this);
				}
				catch (DSEInvalidArgumentException e) {
					GLogger.debug("printJobFailed DSEInvalidArgumentException: " + e.getMessage());
				}
			}
			else {
				setDone(true);
			}
		}
		GLogger.debug("printJobFailed ended.");
	}

	/**
	 * @see javax.print.event.PrintJobListener#printJobNoMoreEvents(PrintJobEvent)
	 */
	public void printJobNoMoreEvents(PrintJobEvent pje) {
		GLogger.debug("printJobNoMoreEvents started.");
		DocPrintJob job = pje.getPrintJob();
		Date dt = null;
		PrintJobAttributeSet attset = job.getAttributes();
		if (attset.containsKey(DateTimeAtCompleted.class)) {
			dt = ((DateTimeAtCompleted) attset.get(DateTimeAtCompleted.class)).getValue();
		}
		else {
			dt = new Date();
		}
		if (jobsInfo.containsKey(new Long(job.hashCode()))) {
			PrintFormJobInfo jobInfo = (PrintFormJobInfo) jobsInfo.get(new Long(job.hashCode()));
			jobInfo.setJobInfo(pje);
			jobInfo.setCompletedAt(dt);
			if (jobInfo.isAsynch) {
				try {
					signalEvent(PrinterConstants.NOEVENTS);
					job.removePrintJobListener(this);
				}
				catch (DSEInvalidArgumentException e) {
					GLogger.debug("printJobNoMoreEvents DSEInvalidArgumentException: " + e.getMessage());
				}
			}
			else {
				setDone(true);
			}
		}
		GLogger.debug("printJobNoMoreEvents ended.");
	}

	/**
	 * @see javax.print.event.PrintJobListener#printJobRequiresAttention(PrintJobEvent)
	 */
	public void printJobRequiresAttention(PrintJobEvent pje) {
		GLogger.debug("printJobRequiresAttention started.");
		DocPrintJob job = pje.getPrintJob();
		if (jobsInfo.containsKey(new Long(job.hashCode()))) {
			PrintFormJobInfo jobInfo = (PrintFormJobInfo) jobsInfo.get(new Long(job.hashCode()));
			jobInfo.setJobInfo(pje);
			if (jobInfo.isAsynch) {
				try {
					signalEvent(PrinterConstants.ATTENTION);
				}
				catch (DSEInvalidArgumentException e) {
					GLogger.debug("printJobRequiresAttention DSEInvalidArgumentException: " + e.getMessage());
				}
			}
			else {
				setDone(true);
			}
		}
		GLogger.debug("printJobRequiresAttention ended.");
	}

	/**
	 * Builds data to be printed: Context must contain "formName", "formatId" and "replaceControlTags" fields.
	 * @param arg0	The input string to be used instead of the file
	 * @param arg1	The context containing the field values 
	 * @return String The formatted text ready for printing
	 * @throws DSEPrinterException
	 **/
	public String buildForm(String arg0, Context arg1) throws DSEPrinterException {
		GLogger.debug("buildForm started.");
        boolean isDebug = ((String)com.ibm.dse.gui.Settings.getValueAt("debugMode")).equals("true"); 
		String result = null;
		try {
			String s = null;
			KeyedCollectionFormat kcf = (KeyedCollectionFormat) KeyedCollectionFormat.readObject((String) arg1.getValueAt(PrinterConstants.FORMAT_ID));
			String formFile = getFormsPath() + (getFormsPath().endsWith("/") ? "" : "/") + arg1.getValueAt(PrinterConstants.FORM_NAME) + "." + PrinterConstants.FORM_FILE_EXT;
			Boolean replaceControlTags = (Boolean) arg1.getValueAt(PrinterConstants.REPLACE_CONTROL_TAGS);
			if (arg0 == null) {

				FileInputStream fs = new FileInputStream(formFile);
				byte[] bs = new byte[fs.available()];
				fs.read(bs);
				s = new String(bs);
			}
			else {
				s = arg0;
			}
			Pattern p = Pattern.compile(PrinterConstants.TAG_PATTERN);
			Matcher m = p.matcher(new String(s));
			StringBuffer sb = new StringBuffer();
			while (m.find()) {
				String tagName = s.substring(m.start() + PrinterConstants.TAG_PREFIX.length(), m.end() - PrinterConstants.TAG_SUFFIX.length());
				Pattern rp = Pattern.compile(PrinterConstants.RESP_PATTERN);
				Matcher rm = rp.matcher(new String(tagName));
				if (rm.find()) {
					StringBuffer rb = new StringBuffer();
					rm.appendReplacement(rb, tagName.substring(rm.start(PrinterConstants.RESP_GROUP), rm.end(PrinterConstants.RESP_GROUP)) + PrinterConstants.RESP_SUFFIX);
					rm.appendTail(rb);
					tagName = new String(rb);
				}
				Pattern vp = Pattern.compile(PrinterConstants.VECTOR_PATTERN);
				Matcher vm = vp.matcher(new String(tagName));
				if (vm.find()) {
					tagName =
						tagName.substring(vm.start(PrinterConstants.VECTOR_GROUP_PART1), vm.end(PrinterConstants.VECTOR_GROUP_PART1))
							+ "."
							+ Integer.toString(Integer.parseInt(tagName.substring(vm.start(PrinterConstants.VECTOR_GROUP_PART2), vm.end(PrinterConstants.VECTOR_GROUP_PART2))) - 1)
							+ "."
							+ tagName.substring(vm.start(PrinterConstants.VECTOR_GROUP_PART3), vm.end(PrinterConstants.VECTOR_GROUP_PART3));
				}
				String tagValue = PrinterConstants.TAG_PREFIX + tagName + PrinterConstants.TAG_SUFFIX;
				if ((PrinterConstants.CONTROL_TAGS.indexOf(tagName) < 0) || replaceControlTags.booleanValue()) {
					try {
						boolean found = false;
						Enumeration e = kcf.getEnumeration();
						while (e.hasMoreElements()) {
							FormatElement fe = (FormatElement) e.nextElement();
							if (fe.getDataElementName().equals(tagName)) {
								DataElement elem = arg1.getElementAt(tagName);
								if (elem.getValue() != null) {
									tagValue = (String) (fe.format(elem));
									found = true;
								}
							}
						}
						if (!found && !isDebug)
						{
							tagValue = "";
						}
					}
					catch (DSEObjectNotFoundException e) {
						if (PrinterConstants.CONTROL_TAGS.indexOf(tagName) < 0) {
							DSEPrinterException pe = new DSEPrinterException("Field " + tagName + " not found in context.");
							GLogger.error(PrinterService.class, null, "Field " + tagName + " not found in context.", pe, false);
							throw pe;
						}
					}
					finally {
						if (PrinterConstants.CONTROL_TAGS.indexOf(tagName) >= 0) {
							int iColon = tagName.indexOf(':');
							if (iColon >= 0) {
								tagName = tagName.substring(iColon + 1);
							}
							tagValue = (String) arg1.getValueAt(PrinterConstants.PRINT_CONTROL + tagName);
							if (tagValue == null) tagValue = "";
						}
					}
				}
				m.appendReplacement(sb, tagValue);
			}
			m.appendTail(sb);
			result = new String(sb);
		}
		catch (FileNotFoundException e) {
			DSEPrinterException pe = new DSEPrinterException(e.getMessage());
			GLogger.error(PrinterService.class, "buildForm exception FileNotFoundException", e.getMessage(), pe, false);
			throw pe;
		}
		catch (IOException e) {
			DSEPrinterException pe = new DSEPrinterException(e.getMessage());
			GLogger.error(PrinterService.class, "buildForm exception IOException", e.getMessage(), pe, false);
			throw pe;
		}
		catch (DSEObjectNotFoundException e) {
			DSEPrinterException pe = new DSEPrinterException(e.getMessage());
			GLogger.error(PrinterService.class, "buildForm exception DSEObjectNotFoundException", e.getMessage(), pe, false);
			throw pe;
		}
		catch (DSEInvalidArgumentException e) {
			DSEPrinterException pe = new DSEPrinterException(e.getMessage());
			GLogger.error(PrinterService.class, "buildForm exception DSEInvalidArgumentException", e.getMessage(), pe, false);
			throw pe;
		}
		catch (DSEInvalidClassException e) {
			DSEPrinterException pe = new DSEPrinterException(e.getMessage());
			GLogger.error(PrinterService.class, "buildForm exception DSEInvalidClassException", e.getMessage(), pe, false);
			throw pe;
		}
		catch (DSEInvalidRequestException e) {
			DSEPrinterException pe = new DSEPrinterException(e.getMessage());
			GLogger.error(PrinterService.class, "buildForm exception DSEInvalidRequestException", e.getMessage(), pe, false);
			throw pe;
		}
		catch (Exception e) {
			DSEPrinterException pe = new DSEPrinterException(e.getMessage());
			GLogger.error(PrinterService.class, "buildForm exception Exception", e.getMessage(), pe, false);
			throw pe;
		}
		GLogger.debug("buildForm ended.");
		return result;
	}

	/**
	 * Builds data to be printed: Context must contain "formName" and "replaceControlTags" fields.
	 * @param arg0	The input string to be used instead of the file
	 * @param arg1	The format name to be used for formating the form fields
	 * @param arg2	The context containing the formatted field values 
	 * @return String The formatted text ready for printing
	 * @throws DSEPrinterException
	 **/
	public String buildForm(String arg0, String arg1, Context arg2) throws DSEPrinterException {
		GLogger.debug("buildForm started.");
        boolean isDebug = ((String)com.ibm.dse.gui.Settings.getValueAt("debugMode")).equals("true"); 
		String result = null;
		try {
			String s = null;
			KeyedCollectionFormat kcf = (KeyedCollectionFormat) KeyedCollectionFormat.readObject(arg1);
			Boolean replaceControlTags = (Boolean) arg2.getValueAt(PrinterConstants.REPLACE_CONTROL_TAGS);
			if (arg0 == null) {
				String formFile = getFormsPath() + (getFormsPath().endsWith("/") ? "" : "/") + arg2.getValueAt(PrinterConstants.FORM_NAME) + "." + PrinterConstants.FORM_FILE_EXT;
				FileInputStream fs = new FileInputStream(formFile);
				byte[] bs = new byte[fs.available()];
				fs.read(bs);
				s = new String(bs);
			}
			else {
				s = arg0;
			}
			Pattern p = Pattern.compile(PrinterConstants.TAG_PATTERN);
			Matcher m = p.matcher(new String(s));
			StringBuffer sb = new StringBuffer();
			while (m.find()) {
				String tagName = s.substring(m.start() + PrinterConstants.TAG_PREFIX.length(), m.end() - PrinterConstants.TAG_SUFFIX.length());
				Pattern rp = Pattern.compile(PrinterConstants.RESP_PATTERN);
				Matcher rm = rp.matcher(new String(tagName));
				if (rm.find()) {
					StringBuffer rb = new StringBuffer();
					rm.appendReplacement(rb, tagName.substring(rm.start(PrinterConstants.RESP_GROUP), rm.end(PrinterConstants.RESP_GROUP)) + PrinterConstants.RESP_SUFFIX);
					rm.appendTail(rb);
					tagName = new String(rb);
				}
				Pattern vp = Pattern.compile(PrinterConstants.VECTOR_PATTERN);
				Matcher vm = vp.matcher(new String(tagName));
				if (vm.find()) {
					tagName =
						tagName.substring(vm.start(PrinterConstants.VECTOR_GROUP_PART1), vm.end(PrinterConstants.VECTOR_GROUP_PART1))
							+ "."
							+ Integer.toString(Integer.parseInt(tagName.substring(vm.start(PrinterConstants.VECTOR_GROUP_PART2), vm.end(PrinterConstants.VECTOR_GROUP_PART2))) - 1)
							+ "."
							+ tagName.substring(vm.start(PrinterConstants.VECTOR_GROUP_PART3), vm.end(PrinterConstants.VECTOR_GROUP_PART3));
				}
				String tagValue = PrinterConstants.TAG_PREFIX + tagName + PrinterConstants.TAG_SUFFIX;
				if ((PrinterConstants.CONTROL_TAGS.indexOf(tagName) < 0) || replaceControlTags.booleanValue()) {
					try {
						boolean found = false;
						Enumeration e = kcf.getEnumeration();
						while (e.hasMoreElements()) {
							FormatElement fe = (FormatElement) e.nextElement();
							if (fe.getDataElementName().equals(tagName)) {
								DataElement elem = arg2.getElementAt(tagName);
								if (elem.getValue() != null) {
									tagValue = (String) (fe.format(elem));
									found = true;
								}
								break;
							}
						}
						if (!found && !isDebug)
						{
							tagValue = "";
						}
					}
					catch (DSEObjectNotFoundException e) {
						if (PrinterConstants.CONTROL_TAGS.indexOf(tagName) < 0) {
							DSEPrinterException pe = new DSEPrinterException("Field " + tagName + " not found in context.");
							GLogger.error(PrinterService.class, null, "Field " + tagName + " not found in context.", pe, false);
							throw pe;
						}
					}
					finally {
						if (PrinterConstants.CONTROL_TAGS.indexOf(tagName) >= 0) {
							int iColon = tagName.indexOf(':');
							if (iColon >= 0) {
								tagName = tagName.substring(iColon + 1);
							}
							tagValue = (String) arg2.getValueAt(PrinterConstants.PRINT_CONTROL + tagName);
							if (tagValue == null) tagValue = "";
						}
					}
				}
				m.appendReplacement(sb, tagValue);
			}
			m.appendTail(sb);
			result = new String(sb);
		}
		catch (FileNotFoundException e) {
			DSEPrinterException pe = new DSEPrinterException(e.getMessage());
			GLogger.error(PrinterService.class, "buildForm exception FileNotFoundException", e.getMessage(), pe, false);
			throw pe;
		}
		catch (IOException e) {
			DSEPrinterException pe = new DSEPrinterException(e.getMessage());
			GLogger.error(PrinterService.class, "buildForm exception IOException", e.getMessage(), pe, false);
			throw pe;
		}
		catch (DSEObjectNotFoundException e) {
			DSEPrinterException pe = new DSEPrinterException(e.getMessage());
			GLogger.error(PrinterService.class, "buildForm exception DSEObjectNotFoundException", e.getMessage(), pe, false);
			throw pe;
		}
		catch (DSEInvalidArgumentException e) {
			DSEPrinterException pe = new DSEPrinterException(e.getMessage());
			GLogger.error(PrinterService.class, "buildForm exception DSEInvalidArgumentException", e.getMessage(), pe, false);
			throw pe;
		}
		catch (DSEInvalidClassException e) {
			DSEPrinterException pe = new DSEPrinterException(e.getMessage());
			GLogger.error(PrinterService.class, "buildForm exception DSEInvalidClassException", e.getMessage(), pe, false);
			throw pe;
		}
		catch (DSEInvalidRequestException e) {
			DSEPrinterException pe = new DSEPrinterException(e.getMessage());
			GLogger.error(PrinterService.class, "buildForm exception DSEInvalidRequestException", e.getMessage(), pe, false);
			throw pe;
		}
		catch (Exception e) {
			DSEPrinterException pe = new DSEPrinterException(e.getMessage());
			GLogger.error(PrinterService.class, "buildForm exception Exception", e.getMessage(), pe, false);
			throw pe;
		}
		GLogger.debug("buildForm ended.");
		return result;
	}

	/**
	 * Builds data to be printed: Context must contain "formName" and "replaceControlTags" fields.
	 * @param arg0	The input string to be used instead of the file
	 * @param arg1	The format to be used for formating the form fields
	 * @param arg2	The context containing the field values + the map file name
	 * @return String The formatted text ready for printing
	 * @throws DSEPrinterException
	 **/
	public String buildForm(String arg0, FormatElement arg1, Context arg2) throws DSEPrinterException {
		GLogger.debug("buildForm started.");
        boolean isDebug = ((String)com.ibm.dse.gui.Settings.getValueAt("debugMode")).equals("true"); 
		String result = null;
		try {
			String s = null;
			Boolean replaceControlTags = (Boolean) arg2.getValueAt(PrinterConstants.REPLACE_CONTROL_TAGS);
			if (arg0 == null) {
				String formFile = getFormsPath() + (getFormsPath().endsWith("/") ? "" : "/") + arg2.getValueAt(PrinterConstants.FORM_NAME) + "." + PrinterConstants.FORM_FILE_EXT;
				FileInputStream fs = new FileInputStream(formFile);
				byte[] bs = new byte[fs.available()];
				fs.read(bs);
				s = bs.toString();
			}
			else {
				s = arg0;
			}
			Pattern p = Pattern.compile(PrinterConstants.TAG_PATTERN);
			Matcher m = p.matcher(s);
			StringBuffer sb = new StringBuffer();
			while (m.find()) {
				String tagName = s.substring(m.start() + PrinterConstants.TAG_PREFIX.length(), m.end() - PrinterConstants.TAG_SUFFIX.length());
				Pattern rp = Pattern.compile(PrinterConstants.RESP_PATTERN);
				Matcher rm = rp.matcher(new String(tagName));
				if (rm.find()) {
					StringBuffer rb = new StringBuffer();
					rm.appendReplacement(rb, tagName.substring(rm.start(PrinterConstants.RESP_GROUP), rm.end(PrinterConstants.RESP_GROUP)) + PrinterConstants.RESP_SUFFIX);
					rm.appendTail(rb);
					tagName = new String(rb);
				}
				Pattern vp = Pattern.compile(PrinterConstants.VECTOR_PATTERN);
				Matcher vm = vp.matcher(new String(tagName));
				if (vm.find()) {
					tagName =
						tagName.substring(vm.start(PrinterConstants.VECTOR_GROUP_PART1), vm.end(PrinterConstants.VECTOR_GROUP_PART1))
							+ "."
							+ Integer.toString(Integer.parseInt(tagName.substring(vm.start(PrinterConstants.VECTOR_GROUP_PART2), vm.end(PrinterConstants.VECTOR_GROUP_PART2))) - 1)
							+ "."
							+ tagName.substring(vm.start(PrinterConstants.VECTOR_GROUP_PART3), vm.end(PrinterConstants.VECTOR_GROUP_PART3));
				}
				String tagValue = PrinterConstants.TAG_PREFIX + tagName + PrinterConstants.TAG_SUFFIX;
				if ((PrinterConstants.CONTROL_TAGS.indexOf(tagName) < 0) || replaceControlTags.booleanValue()) {
					try {
						boolean found = false;
						Enumeration e = ((KeyedCollectionFormat) arg1).getEnumeration();
						while (e.hasMoreElements()) {
							FormatElement fe = (FormatElement) e.nextElement();
							if (fe.getDataElementName().equals(tagName)) {
								DataElement elem = arg2.getElementAt(tagName);
								if (elem.getValue() != null) {
									tagValue = (String) (fe.format(elem));
									found = true;
								}
								break;
							}
						}
						if (!found && !isDebug)
						{
							tagValue = "";
						}

					}
					catch (DSEObjectNotFoundException e) {
						if (PrinterConstants.CONTROL_TAGS.indexOf(tagName) < 0) {
							DSEPrinterException pe = new DSEPrinterException("Field " + tagName + " not found in context.");
							GLogger.error(PrinterService.class, null, "Field " + tagName + " not found in context.", pe, false);
							throw pe;
						}
					}
					finally {
						if (PrinterConstants.CONTROL_TAGS.indexOf(tagName) >= 0) {
							int iColon = tagName.indexOf(':');
							if (iColon >= 0) {
								tagName = tagName.substring(iColon + 1);
							}
							tagValue = (String) arg2.getValueAt(PrinterConstants.PRINT_CONTROL + tagName);
							if (tagValue == null) tagValue = "";
						}
					}
				}
				m.appendReplacement(sb, tagValue);
			}
			m.appendTail(sb);
			result = new String(sb);
		}
		catch (FileNotFoundException e) {
			DSEPrinterException pe = new DSEPrinterException(e.getMessage());
			GLogger.error(PrinterService.class, "buildForm exception FileNotFoundException", e.getMessage(), pe, false);
			throw pe;
		}
		catch (IOException e) {
			DSEPrinterException pe = new DSEPrinterException(e.getMessage());
			GLogger.error(PrinterService.class, "buildForm exception IOException", e.getMessage(), pe, false);
			throw pe;
		}
		catch (DSEObjectNotFoundException e) {
			DSEPrinterException pe = new DSEPrinterException(e.getMessage());
			GLogger.error(PrinterService.class, "buildForm exception DSEObjectNotFoundException", e.getMessage(), pe, false);
			throw pe;
		}
		catch (DSEInvalidArgumentException e) {
			DSEPrinterException pe = new DSEPrinterException(e.getMessage());
			GLogger.error(PrinterService.class, "buildForm exception DSEInvalidArgumentException", e.getMessage(), pe, false);
			throw pe;
		}
		catch (DSEInvalidClassException e) {
			DSEPrinterException pe = new DSEPrinterException(e.getMessage());
			GLogger.error(PrinterService.class, "buildForm exception DSEInvalidClassException", e.getMessage(), pe, false);
			throw pe;
		}
		catch (DSEInvalidRequestException e) {
			DSEPrinterException pe = new DSEPrinterException(e.getMessage());
			GLogger.error(PrinterService.class, "buildForm exception DSEInvalidRequestException", e.getMessage(), pe, false);
			throw pe;
		}
		catch (Exception e) {
			DSEPrinterException pe = new DSEPrinterException(e.getMessage());
			GLogger.error(PrinterService.class, "buildForm exception Exception", e.getMessage(), pe, false);
			throw pe;
		}
		GLogger.debug("buildForm ended.");
		return result;
	}

	/**
	 * Wait routine for implementing synchronous print.
	 */
	private synchronized void waitForDone() {
		try {
			while (!isDone()) {
				wait();
			}
		}
		catch (InterruptedException e) {
		}
	}
}
