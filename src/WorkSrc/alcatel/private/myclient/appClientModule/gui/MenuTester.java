package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class MenuTester extends JFrame 
    implements ActionListener {

  public void actionPerformed (ActionEvent e) {
    System.out.println (e.getActionCommand());
  }

  public MenuTester() {
    super ("Menu Example");

    JMenuBar jmb = new JMenuBar();
    JMenu file = new JMenu ("File");
    JMenuItem item;
    file.add (item = new JMenuItem ("New"));
    item.addActionListener (this);
    file.add (item = new JMenuItem ("Open"));
    item.addActionListener (this);
    file.addSeparator();
    file.add (item = new JMenuItem ("Close"));
    item.addActionListener (this);
    jmb.add (file);

    JMenu edit = new JMenu ("Edit");
    edit.add (item = new JMenuItem ("Copy"));
    item.addActionListener (this);
    Icon tigerIcon = new ImageIcon("SmallTiger.gif"); 
    edit.add (item = 
      new JMenuItem ("Woods", tigerIcon));
    item.setHorizontalTextPosition (JMenuItem.LEFT);
    item.addActionListener (this);
    edit.add (item = 
      new JMenuItem ("Woods", tigerIcon));
    item.addActionListener (this);
    jmb.add (edit);

    JMenu choice = new JMenu ("Choices");
    JCheckBoxMenuItem check = 
      new JCheckBoxMenuItem ("Toggle");
    check.addActionListener (this);
    choice.add (check);
    ButtonGroup rbg = new ButtonGroup();
    JRadioButtonMenuItem rad = 
      new JRadioButtonMenuItem ("Choice 1");
    choice.add (rad);
    rbg.add (rad);
    rad.addActionListener (this);
    rad = new JRadioButtonMenuItem ("Choice 2");
    choice.add (rad);
    rbg.add (rad);
    rad.addActionListener (this);
    rad = new JRadioButtonMenuItem ("Choice 3");
    choice.add (rad);
    rbg.add (rad);
    rad.addActionListener (this);

    jmb.add (choice);

    setJMenuBar (jmb);
  }

	/** 
	 * main test.
	 */  
   public static void main(String s[]) {
    MenuTester frame = new MenuTester();
    frame.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {System.exit(0);}
    });

    frame.setSize(400, 400);
    frame.show();
  }
}