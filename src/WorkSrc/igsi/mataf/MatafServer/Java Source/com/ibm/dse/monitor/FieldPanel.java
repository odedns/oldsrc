package com.ibm.dse.monitor;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import com.ibm.dse.base.*;
import com.ibm.dse.base.types.*;

public class FieldPanel extends JPanel {
	
	private JLabel idLabel,typeLabel;
	private JTextField tfValue;
	private int LMARG=10,VMARG=30;
	private DataField field=null;
	private Conversion conv;
	private ActionListener updateListener=null;
	public JButton updateButton;
	public Container titleBar;
	
	private class SizeLayoutManager implements LayoutManager {
		int vgap=10,hgap=10;
		public void addLayoutComponent(String name, Component comp) {}
		public Dimension minimumLayoutSize(Container parent) {
			return preferredLayoutSize(parent);
		}
		public Dimension preferredLayoutSize(Container parent) {
			Dimension dim = new Dimension(0,0);
			int nmembers = parent.getComponentCount();
			for (int i = 0 ; i < nmembers ; i++) {
			    Component m = parent.getComponent(i);
				Rectangle r = m.getBounds();
				dim.width = Math.max(dim.width, r.x+r.width);
				dim.height = Math.max(dim.height, r.y+r.height);
			}
			dim.width+=hgap*2;
			dim.height+=vgap*2;
			return dim;
		}
		public void layoutContainer(Container parent) {}
		public void removeLayoutComponent(Component comp) {}
	}

	public FieldPanel() {
		super();
		setLayout(new BorderLayout());
		// Set title box
		titleBar=Box.createHorizontalBox();
		titleBar.add(new JLabel(" Field Inspector"));
		int butH=new JButton("Lj").getPreferredSize().height;
		titleBar.add(Box.createVerticalStrut(butH));
		add(titleBar,BorderLayout.NORTH);
		// Set inner panel
		int txtH=new JLabel("Lj").getPreferredSize().height;
		JPanel p=new JPanel();
		p.setBorder(new EtchedBorder());
		p.setLayout(new SizeLayoutManager());
		// Field name
		put(p,new JLabel("Id"),LMARG,10);
		idLabel=new JLabel("");
		idLabel.setForeground(Color.black);
		put(p,idLabel,VMARG,10+txtH);
		// Field type
		put(p,new JLabel("Type"),LMARG,10+txtH*3);
		typeLabel=new JLabel("");
		typeLabel.setForeground(Color.black);
		put(p,typeLabel,VMARG,10+txtH*4);
		// Field value
		put(p,new JLabel("Value"),LMARG,10+txtH*6);
		tfValue=new JTextField(15);
		put(p,tfValue,VMARG,10+txtH*7);
		updateButton=new JButton("Update");
		put(p,updateButton,VMARG,10+txtH*7+tfValue.getHeight()+5);
		add(p,BorderLayout.CENTER);
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) { updateField(ev); }
		});
		// Stretch label sizes
		idLabel.setSize(tfValue.getWidth(),txtH);
		typeLabel.setSize(tfValue.getWidth(),txtH);
	}
	
	protected void updateField(ActionEvent ev) {
		if (field!=null) field.setValue(tfValue.getText());
		updateListener.actionPerformed(ev);
	}
	
	public void addUpdateListener(ActionListener updateListener) {
		this.updateListener=updateListener;
	}
	
	public void setField(DataField field) {
		PropertyDescription desc;
		String type;
		Object value;

		this.field=field;
		idLabel.setText(field.getName());
		desc=field.getDescriptor();
		if (desc==null) {
			type="- untyped -";
			conv=null;
		}
		else {
			type=desc.getType().getName();
			conv=desc.getConversion("default");
		}
		typeLabel.setText(type);
		value=field.getValue();
		// # Later, use converter
		if (value==null || !(value instanceof String)) tfValue.setText("");
		else tfValue.setText((String)value);
	}
	
	public DataField getField() {
		return field;
	}
	
	private void put(JComponent parent, JComponent child, int x, int y) {
		Dimension ps=child.getPreferredSize();
		child.setBounds(x,y,ps.width,ps.height);
		parent.add(child);
	}

}

