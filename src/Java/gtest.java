import corejava.*;
import java.awt.*;


// window test

public class gtest extends CloseableFrame {

		Menu componentMenu;

//	constructor
	public gtest() 
	{



		MenuBar mb = new MenuBar();
		componentMenu = new Menu("Component");
		componentMenu.add(new MenuItem("Button"));
		componentMenu.add(new MenuItem("Checkbox"));
		componentMenu.add(new MenuItem("Choice"));
		componentMenu.add(new MenuItem("Label"));
		componentMenu.add(new MenuItem("List"));
		componentMenu.add(new MenuItem("Panel"));
	
		mb.add(componentMenu);

		setMenuBar(mb);
		setLayout(null);
		 setTitle("Mitzik !!");
		resize(400,400);

	
		
		show();


	
	}


	// moved this to Closeable frame
		/*
	public boolean handleEvent(Event e) 
	{
		switch(e.id) {
		case Event.WINDOW_DESTROY:
				System.exit(0);
				return (true);
		default :
				return super.handleEvent(e);
		}
	}
		  */

	// the paint function
	public void paint(Graphics g)
	{
		Font f = new Font("Sans Serif", Font.BOLD,14);
		FontMetrics fm = g.getFontMetrics(f);


		String s1 = "My Fucking Sample String !!!";
		String s2 = "My fucking second string !!!!";
		int cx = 75; 
		int cy = 100;
		

		g.setFont(f);
		g.drawString(s1,cx,cy );
		g.drawLine(100,100,500,100);
		// cx+= fm.stringWidth(s1);
		cy+= fm.getHeight();
		g.drawString(s2,cx,cy);

	}


	// main function
	public static void main(String[] args) {

		gtest f = new gtest();
		f.show();

	}
}


			