package com.ibm.dse.monitor;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.*;
import com.ibm.dse.base.*;


class ContextCellRenderer extends DefaultTreeCellRenderer {

	protected static Icon fieldIcon=null;
	protected static Icon kCollIcon=null;
	protected static Icon iCollIcon=null;

	public ContextCellRenderer() {
		super();
		if (fieldIcon==null) {
			fieldIcon=BallIcon.getBallIcon(new Color(0,96,255),"F",14,14,1,0,11);
			kCollIcon=BallIcon.getBallIcon(new Color(0,208,96),"K");
			iCollIcon=BallIcon.getBallIcon(new Color(0,160,255),"I",16,16,1,0,12);
		}
	}
	
	public Component getTreeCellRendererComponent(JTree tree, Object value,
		boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
		JLabel jl;
		Object userObj;

		jl=(JLabel)super.getTreeCellRendererComponent(
			tree,value,sel,expanded,leaf,row,hasFocus);
		userObj=((DefaultMutableTreeNode)value).getUserObject();
		if (userObj instanceof DataField) jl.setIcon(fieldIcon);
		else if (userObj instanceof KeyedCollection) jl.setIcon(kCollIcon);
		else if (userObj instanceof IndexedCollection) jl.setIcon(iCollIcon);
		return jl;
	}

}



public class ContextPanel extends JPanel {
	
	protected JScrollPane sPane;
	public JTree tree;
	public Container titleBar;
	protected Context ctx;

	public ContextPanel() {
		super(new BorderLayout());
		// Place tree
		tree=getTree();
		setContext(Context.getRoot());
		sPane=new JScrollPane(tree);
		add(sPane,BorderLayout.CENTER);
		// Place title
		titleBar=Box.createHorizontalBox();
		titleBar.add(new JLabel(" Context Data"));
		int h=new JButton("Lj").getPreferredSize().height;
		titleBar.add(Box.createVerticalStrut(h));
		add(titleBar,BorderLayout.NORTH);
	}
	
	public void addTreeSelectionListener(TreeSelectionListener tsl) {
		tree.addTreeSelectionListener(tsl);
	}

	protected void addChildren(DefaultMutableTreeNode parent)
		throws DSEObjectNotFoundException {
		int i;
		Object userObj;
		KeyedCollection kc;
		IndexedCollection ic;
		DataElement de;
		DefaultMutableTreeNode child;

		userObj=parent.getUserObject();
		if (userObj instanceof KeyedCollection) {
			kc=(KeyedCollection)userObj;
			for (i=0; i<kc.size(); i++) {
				de=kc.getElementAt(i);
				child=new DefaultMutableTreeNode(de);
				parent.add(child);
				addChildren(child);
			}
		}
		else if (userObj instanceof IndexedCollection) {
			ic=(IndexedCollection)userObj;
			if (ic.size()<=0) addChildren(
				new DefaultMutableTreeNode(ic.getDataElement()));
			else for (i=0; i<ic.size(); i++) {
				de=ic.getElementAt(i);
				child=new DefaultMutableTreeNode(de);
				parent.add(child);
				addChildren(child);
			}
		}
	}

	public void setContext(Context ctx) {
		int i;
		DefaultMutableTreeNode theRoot;
		DefaultTreeModel treeModel;

		this.ctx=ctx;
		theRoot=new DefaultMutableTreeNode(ctx.getKeyedCollection());
		try {
			addChildren(theRoot);
		} catch (DSEObjectNotFoundException e) {}
		treeModel=(DefaultTreeModel)tree.getModel();
		treeModel.setRoot(theRoot);
		for (i=0; i<tree.getRowCount();i++) {
			/*Object item;
			TreePath tp;
			tp=tree.getPathForRow(i);
			item=((DefaultMutableTreeNode)tp.getLastPathComponent()).getUserObject();
			if (!(item instanceof IndexedCollection)) tree.expandRow(i);*/
			tree.expandRow(i);
		}
	}
	
	public void refresh() {
		setContext(ctx);
	}

	public JTree getTree() {
		JTree theTree=new JTree(new DefaultMutableTreeNode()) {
			public String convertValueToText(Object o, boolean selected,
				boolean expanded, boolean leaf, int row, boolean hasFocus) {
					DefaultMutableTreeNode dmtn=(DefaultMutableTreeNode)o;
					Object value=dmtn.getUserObject();
					if (value==null) return "";
					if (!(value instanceof DataElement)) return value.toString();
					StringBuffer buf=new StringBuffer();
					DataElement de=(DataElement)value;
					buf.append("<"+de.getTagName()+" id=\""+de.getName()+'\"');
					if (de instanceof IndexedCollection)
						buf.append(" size=\""+((IndexedCollection)de).size()+"\">");
					else if (de instanceof DataCollection)
						buf.append('>');
					else {
						Object v=de.getValue();
						if (v!=null) buf.append(" value=\""+v+"\"");
						buf.append(" />");
					}
					return buf.toString();
			}
		};
		theTree.setCellRenderer(new ContextCellRenderer());
		theTree.setRowHeight(20);
		return theTree;
	}
	
}

