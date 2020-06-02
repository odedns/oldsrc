
import java.awt.*;
import java.net.InetAddress;
import java.net.UnknownHostException;



class HLookup extends Dialog {
		HLookup(Frame f)
		{
			super(f,"Host Lookup",true);

			Button lookupButton = new Button("Lookup");
			Button cancelButton = new Button("Cancel");
			TextField hname = new TextField(25);
			TextArea res = new TextArea("",5,25);

			Label Prompt = new Label("Enter Host name of address to lookup");
			;
			Panel subPanel = new Panel();
			Panel buttonPanel = new Panel();


			setLayout(new GridLayout(5,1));

			
			buttonPanel.add(lookupButton);
			buttonPanel.add(cancelButton);
			add(Prompt);
			add(hname);
			add(res);
			
			add(buttonPanel);
			//resize(320,250);
			pack();
			show();

		}

		public boolean action(Event e, Object arg)
		{


			if(!(e.target instanceof Button)) {
				return false;
			}

			String buttonLabel = (String ) arg;

			if(buttonLabel == "Lookup" ) {
				dispose();
			}
			return(true);
		
		}

// handle the close event 
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


		public static void main(String[] args)
		{
			Frame f = new Frame();


			HLookup h = new HLookup(f);

			System.exit(0);
		}


}
		






/*
 class HLookup {

	static final String usage = "usage: Hlookup <hostname | hostaddress>";

	public static void main(String[] args) 
	{

		System.out.println("in main");
		
		if(args.length < 1) {
			System.out.println(usage);
		 	System.exit(1);
		}


	
		try {
			InetAddress n  = InetAddress.getByName(args[0]);

			System.out.println("Hostname: " + n.getHostName());
			System.out.println("Address: " + n.getHostAddress());
			
		} catch(UnknownHostException e)  {
			System.out.println("Error: Unknown Host");
		}
		
	}
}

  */

