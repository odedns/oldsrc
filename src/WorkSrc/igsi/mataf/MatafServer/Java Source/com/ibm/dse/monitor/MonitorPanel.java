package com.ibm.dse.monitor;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import com.ibm.dse.base.*;
import java.beans.*;

public class MonitorPanel extends JPanel {
	
	ContextTreePanel ctp;	// panel showing the context tree
	ContextPanel cp;		// panel showing the selected context data
	FieldPanel fp;			// panel showing the selected field
	XmlPanel tap;			// panel showing the xml text area
	JSplitPane innerSp,outerSp;
	int layout;
	double innerSpPos,outerSpPos;
	boolean isMaximized=false;
	JFrame ownerFrame;

	class MegaListener extends MouseAdapter
		implements ActionListener, TreeSelectionListener {

		public void valueChanged(TreeSelectionEvent ev) {
			DefaultMutableTreeNode dmtn;
			dmtn=(DefaultMutableTreeNode)ev.getPath().getLastPathComponent();
			if (ev.getSource()==ctp.tree) {
				ContextHandle ch=(ContextHandle)dmtn.getUserObject();
				cp.setContext(ch.ctx);
				showXml(ch.ctx.toString());
			}
			else if (ev.getSource()==cp.tree) {
				DataElement de=(DataElement)dmtn.getUserObject();
				if (de instanceof DataField) fp.setField((DataField)de);
				showXml(de.toString());
			}
		}

		public void actionPerformed(ActionEvent ev) {
			if (ev.getSource()==fp.updateButton) cp.refresh();
		}

		public void mouseClicked(MouseEvent ev) {
			JPanel p;

			if (ev.getClickCount()!=2) return;
			p=(JPanel)((Container)ev.getSource()).getParent();
			if (isMaximized) {
				// if maximized, set back to normal
				removeAll();
				layoutPanes();
				ownerFrame.setContentPane(MonitorPanel.this);
			}
			else {
				// if not maximized, do maximize
				Dimension frameSize=ownerFrame.getSize();
				double size;
				if (layout==JSplitPane.HORIZONTAL_SPLIT) size=frameSize.getWidth();
				else size=frameSize.getHeight();
				innerSpPos=innerSp.getDividerLocation()/size;
				outerSpPos=outerSp.getDividerLocation()/frameSize.getWidth();
				ownerFrame.setContentPane(p);
			}
			isMaximized=!isMaximized;
			ownerFrame.validate();
		}

	}
	
	
	class XmlPanel extends JPanel {

		JTextArea xmlTa;
		Container titleBar;

		XmlPanel() {
			setLayout(new BorderLayout());
			// Set title box
			titleBar=Box.createHorizontalBox();
			titleBar.add(new JLabel(" XML Representation"));
			int butH=new JButton("Lj").getPreferredSize().height;
			titleBar.add(Box.createVerticalStrut(butH));
			add(titleBar,BorderLayout.NORTH);
			xmlTa=new JTextArea();
			xmlTa.setEditable(false);
			xmlTa.setFont(new Font("monospaced",Font.PLAIN,xmlTa.getFont().getSize()));
			JScrollPane xmlSp=new JScrollPane(xmlTa);
			add(xmlSp,BorderLayout.CENTER);
			setBorder(new EmptyBorder(0,5,5,5));
		}

	}


	//--------------------------------------------------------------
	//----------------------- Monitor Panel ------------------------
	//--------------------------------------------------------------
	public MonitorPanel() {
		super();
		setLayout(new BorderLayout());
		ctp=new ContextTreePanel();
		cp=new ContextPanel();
		fp=new FieldPanel();
		tap=new XmlPanel();
		MegaListener ml=new MegaListener();
		ctp.addTreeSelectionListener(ml);
		cp.addTreeSelectionListener(ml);
		fp.addUpdateListener(ml);
		ctp.titleBar.addMouseListener(ml);
		cp.titleBar.addMouseListener(ml);
		fp.titleBar.addMouseListener(ml);
		tap.titleBar.addMouseListener(ml);
	}
	
	void layoutPanes() {
		Dimension frameSize;
		JPanel multiPanel;

		multiPanel=new JPanel(new BorderLayout());
		multiPanel.add(fp,BorderLayout.NORTH);
		// add text area panel to multipanel
		multiPanel.add(tap,BorderLayout.CENTER);
		// add split panes		
		innerSp=new JSplitPane(layout,true,ctp,cp);
		outerSp=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,true,innerSp,multiPanel);
		add(outerSp,BorderLayout.CENTER);
		innerSp.setResizeWeight(0.5);
		outerSp.setResizeWeight(0.5);
		// set inner pane divider location
		frameSize=ownerFrame.getSize();
		if (layout==JSplitPane.HORIZONTAL_SPLIT)
			innerSp.setDividerLocation((int)(innerSpPos*frameSize.getWidth()));
		else innerSp.setDividerLocation((int)(innerSpPos*frameSize.getHeight()));
		outerSp.setDividerLocation((int)(outerSpPos*frameSize.getWidth()));
	}

	public void showXml(String s) {
		tap.xmlTa.setText(s);
		tap.xmlTa.setCaretPosition(0);
	}

	public static void initComposer() throws Exception {
		Settings.reset();
		Settings.initializeExternalizers();
		Context.readObject("customerSessionCtx");
	}

	public static JFrame showMonitorFrame(int layout) {
		final int WW=750, HH=500;
		MonitorPanel mp;
		JFrame jf;

		mp=new MonitorPanel();
		mp.layout=layout;
		jf=new JFrame("WSBCC Monitor");
		jf.setContentPane(mp);
		mp.ownerFrame=jf;
		jf.setSize(WW,HH);
		if (layout==JSplitPane.HORIZONTAL_SPLIT) mp.innerSpPos=1.0/3.0;
		else mp.innerSpPos=1.0/4.0;
		mp.outerSpPos=2.0/3.0;
		mp.layoutPanes();
		jf.show();
		return jf;
	}

	public static void main(String[] args) throws Exception {
		initComposer();
		UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		JFrame jf=showMonitorFrame(JSplitPane.VERTICAL_SPLIT);
		jf.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
					System.exit(0);
			}
		});
	}
}

