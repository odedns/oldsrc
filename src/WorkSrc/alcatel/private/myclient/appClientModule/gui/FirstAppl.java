package gui;

// Copyright MageLang Institute; Version $Id: //depot/main/src/edu/modules/Swing/magercises/FirstAppl/FirstAppl.java#2 $
/*
 * Demonstrates that basic use of Swing components are just like
 * old AWT versions.  Illustrates components, frames, layouts, and
 * 1.1 style events.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
// import the swing package

public class FirstAppl extends Frame {
  // The initial width and height of the frame
  public static int WIDTH = 250;
  public static int HEIGHT = 130;
  
  public FirstAppl(String lab) {
    super(lab);
    setLayout(new GridLayout(3,1));
    JButton top = new JButton("Top");
    top.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
	    setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
        System.out.println("top");
      }
    });
    JButton bottom = new JButton("Bottom");
    bottom.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {	
//	    setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
//	    setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	try {
			Image curImage = Toolkit.getDefaultToolkit().createImage("c:/temp/icon_stamp.gif");			
			if(curImage ==  null) {				
					System.out.println("curImage is null");
			}
			Dimension dim = Toolkit.getDefaultToolkit().getBestCursorSize(0,0);
			System.out.println("got dimension = " + dim.toString());
			Point pt = new Point((int) dim.getHeight()-1, (int)dim.getWidth()-1);
			Cursor curs = Toolkit.getDefaultToolkit().createCustomCursor(curImage,pt,"icon_stamp");
			setCursor(Cursor.getSystemCustomCursor("IconStamp"));	
			setCursor(curs);
	} catch(AWTException ae) {
		ae.printStackTrace();	
	}
        System.out.println("bottom");
      }
    });
    add(new JLabel("Swing Components are like AWT 1.1"));
    add(top);
    add(bottom);
  }



	public void paint(Graphics g)
	{
		super.paint(g);
		Image backgroundImg =
			new ImageIcon("images/BGRFIBI2.gif").getImage();
		g.drawImage(backgroundImg, 4, 4, getWidth() - 10, getHeight() - 10,	this);
	}
			
			
			
  public static void main(String s[]) {
    FirstAppl frame = new FirstAppl("First Swing Application");
    frame.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {System.exit(0);}
    });

    frame.setSize(WIDTH, HEIGHT);
    
	Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	frame.setLocation( ( screen.width -  frame.getSize().width ) / 2,
				( screen.height - frame.getSize().height ) / 2 );

    frame.show();
    System.out.println("after frame");
  }
}