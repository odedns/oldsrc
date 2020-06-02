package mataf.services.printer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.geom.AffineTransform;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.ibm.dse.base.Context;
import com.ibm.dse.base.DSEClientOperation;
import com.ibm.dse.base.DataField;
import com.ibm.dse.base.DateFormat;
import com.ibm.dse.base.KeyedCollection;
import com.ibm.dse.base.NumericStringFormat;
import com.ibm.dse.base.RecordFormat;
import com.ibm.dse.base.StringFormat;
import com.ibm.dse.base.TimeFormat;

/**
 * @author tibig
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class PrinterClientOp extends DSEClientOperation {

	PrinterService printer;
	JButton printFormButton;
	JButton printFrameButton;
	JButton printScreenButton;

	public class DrawingPanel extends JPanel {
		private int fontSize = 90;
		private String message = "Java 2D";
		private int messageWidth;

		public DrawingPanel() {
			setBackground(Color.white);
			Font font = new Font("Serif", Font.PLAIN, fontSize);
			setFont(font);
			FontMetrics metrics = getFontMetrics(font);
			messageWidth = metrics.stringWidth(message);
			int width = messageWidth * 5 / 3;
			int height = fontSize * 3;
			setPreferredSize(new Dimension(width, height));
		}

		/** Draws a black string with a tall angled "shadow"
		 *  of the string behind it.
		 */
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;
			int x = messageWidth / 10;
			int y = fontSize * 5 / 2;
			g2d.translate(x, y);
			g2d.setPaint(Color.lightGray);
			AffineTransform origTransform = g2d.getTransform();
			g2d.shear(-0.95, 0);
			g2d.scale(1, 3);
			g2d.drawString(message, 0, 0);
			g2d.setTransform(origTransform);
			g2d.setPaint(Color.black);
			g2d.drawString(message, 0, 0);
		}
	}

	public class PrintTest extends JFrame implements WindowListener, ActionListener {

		public PrintTest() {
			super("Print Service Test");
			addWindowListener(this);
			Container content = getContentPane();
			printFormButton = new JButton("Print Form");
			printFormButton.addActionListener(this);
			printFrameButton = new JButton("Print Frame");
			printFrameButton.addActionListener(this);
			printScreenButton = new JButton("Print Screen");
			printScreenButton.addActionListener(this);
			JPanel buttonPanel = new JPanel();
			buttonPanel.setBackground(Color.white);
			buttonPanel.add(printFormButton);
			buttonPanel.add(printFrameButton);
			buttonPanel.add(printScreenButton);
			content.add(buttonPanel, BorderLayout.SOUTH);
			DrawingPanel drawingPanel = new DrawingPanel();
			content.add(drawingPanel, BorderLayout.CENTER);
			pack();
			setVisible(true);
		}

		public void actionPerformed(ActionEvent event) {
			try {
				if (event.getSource() == printFormButton) {
					Context ctx = new Context();
					ctx.clearKeyedCollection();
					KeyedCollection kcoll = new KeyedCollection();
					kcoll.setName("prtControl");
					DataField df = new DataField();
					df.setName("formName");
					kcoll.addElement(df);
					df = new DataField();
					df.setName("replControlTags");
					kcoll.addElement(df);
					ctx.addElement(kcoll);
					df = new DataField();
					df.setName("PK_ZIHUI_PAKID");
					ctx.addElement(df);
					df = new DataField();
					df.setName("PK_SHEM_KATZAR");
					ctx.addElement(df);
					df = new DataField();
					df.setName("GK_SHAA");
					ctx.addElement(df);
					df = new DataField();
					df.setName("GK_DD");
					ctx.addElement(df);
					df = new DataField();
					df.setName("PK_SNIF");
					ctx.addElement(df);
					df = new DataField();
					df.setName("PK_DAF");
					ctx.addElement(df);
					df = new DataField();
					df.setName("PK_MISHMERET");
					ctx.addElement(df);
					df = new DataField();
					df.setName("PK_TR_BITZUA");
					ctx.addElement(df);
					df = new DataField();
					df.setName("PK_SIDURI_TAH");
					ctx.addElement(df);
					df = new DataField();
					df.setName("PK_SHAA_BITZUA");
					ctx.addElement(df);
					df = new DataField();
					df.setName("PK_SCH_TMORA");
					ctx.addElement(df);
					df = new DataField();
					df.setName("PK_SNIF_TMORA");
					ctx.addElement(df);
					df = new DataField();
					df.setName("PK_SCH");
					ctx.addElement(df);
					df = new DataField();
					df.setName("PK_CH");
					ctx.addElement(df);
					df = new DataField();
					df.setName("PK_TAR_PERAON");
					ctx.addElement(df);
					df = new DataField();
					df.setName("PK_TAR_HAFK");
					ctx.addElement(df);
					df = new DataField();
					df.setName("PK_SCHUM_HAFKADA");
					ctx.addElement(df);
					df = new DataField();
					df.setName("PK_AHUZ_RIBIT");
					ctx.addElement(df);
					df = new DataField();
					df.setName("PK_RIBIT_MTU");
					ctx.addElement(df);
					df = new DataField();
					df.setName("PK_SHEM_LKH");
					ctx.addElement(df);
					Calendar clnd = Calendar.getInstance();
					ctx.setValueAt("prtControl.formName", "PK_RAM11");
					ctx.setValueAt("prtControl.replControlTags", Boolean.TRUE);
					ctx.setValueAt("PK_ZIHUI_PAKID", new Integer(123456));
					ctx.setValueAt("PK_SHEM_KATZAR", "א.כהן");
					clnd.set(1, 1, 1, 9, 50);
					ctx.setValueAt("GK_SHAA", clnd.getTime());
					clnd.set(2003, 10, 9);
					ctx.setValueAt("GK_DD", clnd.getTime());
					ctx.setValueAt("PK_SNIF", new Integer(283));
					ctx.setValueAt("PK_DAF", new Integer(1));
					ctx.setValueAt("PK_MISHMERET", new Integer(1));
					clnd.set(2003, 10, 10);
					ctx.setValueAt("PK_TR_BITZUA", clnd.getTime());
					ctx.setValueAt("PK_SIDURI_TAH", new Integer(1234));
					clnd.set(1, 1, 1, 9, 55);
					ctx.setValueAt("PK_SHAA_BITZUA", clnd.getTime());
					ctx.setValueAt("PK_SCH_TMORA", new Integer(105));
					ctx.setValueAt("PK_SNIF_TMORA", new Integer(284));
					ctx.setValueAt("PK_SCH", new Integer(105));
					ctx.setValueAt("PK_CH", new Integer(100366));
					clnd.set(2004, 11, 31);
					ctx.setValueAt("PK_TAR_PERAON", clnd.getTime());
					clnd.set(2004, 10, 8);
					ctx.setValueAt("PK_TAR_HAFK", clnd.getTime());
					ctx.setValueAt("PK_SCHUM_HAFKADA", new Float(20000.00));
					ctx.setValueAt("PK_AHUZ_RIBIT", new Float(5.455));
					ctx.setValueAt("PK_RIBIT_MTU", new Float(4.525));
					ctx.setValueAt("PK_SHEM_LKH", "א.ישראלי");
					RecordFormat frm = new RecordFormat();
					NumericStringFormat nsf = new NumericStringFormat();
					nsf.setDataElementName("PK_ZIHUI_PAKID");
					frm.add(nsf);
					StringFormat sf = new StringFormat();
					sf.setDataElementName("PK_SHEM_KATZAR");
					frm.add(sf);
					TimeFormat tf = new TimeFormat();
					tf.setHours24(true);
					tf.setShowSeconds(false);
					tf.setUseSeparator(true);
					tf.setDataElementName("GK_SHAA");
					frm.add(tf);
					DateFormat df1 = new DateFormat();
					df1.setFourDigitYear(true);
					df1.setDataElementName("GK_DD");
					frm.add(df1);
					nsf = new NumericStringFormat();
					nsf.setDataElementName("PK_SNIF");
					frm.add(nsf);
					nsf = new NumericStringFormat();
					nsf.setDataElementName("PK_DAF");
					frm.add(nsf);
					nsf = new NumericStringFormat();
					nsf.setDataElementName("PK_MISHMERET");
					frm.add(nsf);
					df1 = new DateFormat();
					df1.setFourDigitYear(true);
					df1.setDataElementName("PK_TR_BITZUA");
					frm.add(df1);
					nsf = new NumericStringFormat();
					nsf.setDataElementName("PK_SIDURI_TAH");
					frm.add(nsf);
					tf = new TimeFormat();
					tf.setHours24(true);
					tf.setShowSeconds(false);
					tf.setUseSeparator(true);
					tf.setDataElementName("PK_SHAA_BITZUA");
					frm.add(tf);
					nsf = new NumericStringFormat();
					nsf.setDataElementName("PK_SCH_TMORA");
					frm.add(nsf);
					nsf = new NumericStringFormat();
					nsf.setDataElementName("PK_SNIF_TMORA");
					frm.add(nsf);
					nsf = new NumericStringFormat();
					nsf.setDataElementName("PK_SCH");
					frm.add(nsf);
					nsf = new NumericStringFormat();
					nsf.setDataElementName("PK_ZIHUI_PAKID");
					frm.add(nsf);
					df1 = new DateFormat();
					df1.setDataElementName("PK_TAR_PERAON");
					frm.add(df1);
					df1 = new DateFormat();
					df1.setFourDigitYear(true);
					df1.setDataElementName("PK_TAR_HAFK");
					frm.add(df1);
					nsf = new NumericStringFormat();
					nsf.setDecimalPlaces(2);
					nsf.setUseThousandsSeparator(true);
					nsf.setDataElementName("PK_SCHUM_HAFKADA");
					frm.add(nsf);
					nsf = new NumericStringFormat();
					nsf.setDecimalPlaces(4);
					nsf.setUseThousandsSeparator(false);
					nsf.setDataElementName("PK_AHUZ_RIBIT");
					frm.add(nsf);
					nsf = new NumericStringFormat();
					nsf.setDecimalPlaces(4);
					nsf.setUseThousandsSeparator(false);
					nsf.setDataElementName("PK_RIBIT_MTU");
					frm.add(nsf);
					sf = new StringFormat();
					sf.setDataElementName("PK_SHEM_LKH");
					frm.add(sf);
					String s = printer.buildForm(null, frm, ctx);
					printer.printFormAndWait(s);
				}
				else if (event.getSource() == printFrameButton) {
					printer.printFrame(this);
				}	
				else if (event.getSource() == printScreenButton) {
					printer.printScreen();
				}
			}
			catch (Exception e) {
				System.out.println(e.getMessage());
			}

		}
		public void windowActivated(WindowEvent e) {
		}
		public void windowDeactivated(WindowEvent e) {
		}
		public void windowDeiconified(WindowEvent e) {
		}
		public void windowIconified(WindowEvent e) {
		}
		public void windowOpened(WindowEvent e) {
		}
		public void windowClosed(WindowEvent e) {
		}
		public void windowClosing(WindowEvent e) {
			synchronized (this) {
				notify();
			}	
		}
	}

	/**
	 * @see com.ibm.dse.base.DSEClientOperation#execute()
	 */
	public void execute() throws Exception {
		printer = (PrinterService) getContext().getService("testPrinterService");
		printer.initialize();
		PrintTest p = new PrintTest();
		synchronized(p) {
			p.wait();
		}	
	}
}
