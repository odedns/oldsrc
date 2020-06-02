package com.ibm.dse.monitor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

import com.ibm.dse.base.Context;
import com.ibm.dse.base.DSEException;
import com.ibm.dse.base.SessionEntry;
import com.ibm.dse.base.SessionTable;


class ContextTreeCellRenderer extends DefaultTreeCellRenderer {

	protected static Icon ctxIcon=null;
	protected static Icon sessIcon=null;
	protected static Icon procIcon=null;

	public ContextTreeCellRenderer() {
		super();
		if (ctxIcon==null) {
			ctxIcon=BallIcon.getBallIcon(new Color(255,0,0),"C");
			sessIcon=BallIcon.getBallIcon(new Color(240,128,0),"S");
			procIcon=BallIcon.getBallIcon(new Color(200,128,128),"P",16,16,1,0,12);
		}
	}

	public Component getTreeCellRendererComponent(JTree tree, Object value,
		boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
		JLabel jl;
		ContextHandle ch;

		jl=(JLabel)super.getTreeCellRendererComponent(
			tree,value,sel,expanded,leaf,row,hasFocus);
		ch=(ContextHandle)((DefaultMutableTreeNode)value).getUserObject();
		if (ch==null) return jl;
		switch (ch.kind) {
			case ContextHandle.NONE:
				jl.setIcon(ctxIcon);
				break;
			case ContextHandle.SESSION:
				jl.setIcon(sessIcon);
				break;
			case ContextHandle.PROC:
				jl.setIcon(procIcon);
				break;
			default:
				throw new IllegalStateException("ContextHandle.kind="+ch.kind);
		}
		return jl;
	}

}


class ContextHandle {

	Context ctx;
	int kind;
	String id;
	static final int NONE=0;
	static final int SESSION=1;
	static final int PROC=2;
	
	ContextHandle(Context ctx) {
		this.ctx=ctx;
		kind=NONE;
		id=null;
	}
	
	boolean checkSession() {
		SessionEntry se;
		SessionTable st;
		Context sc;
		int i;

		try {
			st=Context.getSessionTable();
			for (i=0; i<st.size(); i++) {
				se=(SessionEntry)st.getElementAt(i);
				sc=se.getCurrentContext();
				if (ctx==sc) {
					kind=SESSION;
					id=se.getSessionId();
					return true;
				}
			}
		} catch (DSEException e) {}
		return false;
	}
	
}


public class ContextTreePanel extends JPanel {
	
	protected JScrollPane sPane;
	public JTree tree;
	public Container titleBar;
	
	public ContextTreePanel() {
		super(new BorderLayout());
		// Place tree
		tree=getTree();
		refreshTree();
		tree.setSelectionRow(0);
		sPane=new JScrollPane(tree);
		add(sPane,BorderLayout.CENTER);
		// Place title & refresh button
		JButton refresh=new JButton("Refresh");
		titleBar=new JPanel(new BorderLayout());
		titleBar.add(new JLabel(" Context Tree"),BorderLayout.WEST);
		titleBar.add(refresh,BorderLayout.EAST);
		add(titleBar,BorderLayout.NORTH);
		refresh.addActionListener(getRefreshListener());
	}
	
	public void addTreeSelectionListener(TreeSelectionListener tsl) {
		tree.addTreeSelectionListener(tsl);
	}

	public ActionListener getRefreshListener() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				refreshTree();
			}
		};
	}
	
	public JTree getTree() {
		JTree theTree=new JTree(new DefaultMutableTreeNode()) {
			public String convertValueToText(Object o, boolean selected,
				boolean expanded, boolean leaf, int row, boolean hasFocus) {
					DefaultMutableTreeNode dmtn=(DefaultMutableTreeNode)o;
					ContextHandle ch=(ContextHandle)dmtn.getUserObject();
					if (ch==null) return "";
					String s = ch.ctx.getName()+" ["+ch.ctx.getType()+"]";
					if (ch.id!=null) s=s+" - "+ch.id;
					return s;
			}
		};
		theTree.setCellRenderer(new ContextTreeCellRenderer());
		theTree.setRowHeight(20);
		return theTree;
	}
	
	public void addProcChildren(ContextHandle ch, DefaultMutableTreeNode parentNode) {
		Hashtable ht=null;
		Enumeration keys;
		ContextHandle chProc;
		DefaultMutableTreeNode childNode;

		try {
			ht=(Hashtable)ch.ctx.getValueAt("contextTable");
			if (ht==null) return;
			keys=ht.keys();
			while (keys.hasMoreElements()) {
				chProc=new ContextHandle(null);
				chProc.id=(String)keys.nextElement();
				chProc.ctx=(Context)ht.get(chProc.id);
				chProc.kind=ContextHandle.PROC;
				childNode=new DefaultMutableTreeNode(chProc);
				parentNode.add(childNode);
				addChildren(childNode);
			}
		} catch (Exception e) {}
	}
	
	public void addChildren(DefaultMutableTreeNode parentNode) {
		int i;
		DefaultMutableTreeNode childNode;
		Context parentCtx,childCtx;
		ContextHandle ch;

		parentCtx=((ContextHandle)parentNode.getUserObject()).ctx;
		for (i=0; i<parentCtx.getChildren().size(); i++) {
			childCtx=(Context)parentCtx.getChildren().get(i);
			ch=new ContextHandle(childCtx);
			childNode=new DefaultMutableTreeNode(ch);
			parentNode.add(childNode);
			// add regular chained child contexts
			addChildren(childNode);
			// if session context, mark and add dynamic processor children
			if (ch.checkSession()) addProcChildren(ch,childNode);
		}
	}
	
	public TreeNode buildTree() {
		DefaultMutableTreeNode theRoot;
		
		theRoot=new DefaultMutableTreeNode(new ContextHandle(Context.getRoot()));
		addChildren(theRoot);
		return theRoot;
	}
	
	public void refreshTree() {
		int i;
		DefaultTreeModel treeModel;
		int [] selRows;

		selRows=tree.getSelectionRows();
		treeModel=(DefaultTreeModel)tree.getModel();
		treeModel.setRoot(buildTree());
		for (i=0; i<tree.getRowCount();i++) tree.expandRow(i);
		if (selRows!=null && selRows.length>0) tree.setSelectionRows(selRows);
	}

}

