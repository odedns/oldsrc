import java.awt.*;
import corejava.*;


class AboutDialog extends Dialog {
	Button Close_Button;
	Panel txtPanel;

	AboutDialog(Frame f, String args)
	{
		super(f,"About Dialog", true);

		// grid layout manager.
		
		Panel AboutPanel = new Panel();

		AboutPanel.setLayout(new GridLayout(2,1,0,0));

		TextArea t = new TextArea(args,10,40);
		Close_Button = new Button("Close");
		t.setEditable(false);
		Close_Button.resize(5,5);
		//t.preferredSize(10,40);
		
		AboutPanel.add(t);
		AboutPanel.add(Close_Button);
		setLayout(new BorderLayout());
		add("North",AboutPanel);
		
		
		setResizable(false);
		pack();
		resize(220,150);
		show();


	
		//{{REGISTER_LISTENERS
		SymAction lSymAction = new SymAction();
		closeButton.addActionListener(lSymAction);
		//}}
	} // constructor


	public boolean action(Event e, Object arg) 
	{

		if(!(e.target instanceof Button)) {
			return false;
		}

		
		dispose();
		
		return(true);
		
	}



	class SymAction implements java.awt.event.ActionListener
	{
		public void actionPerformed(java.awt.event.ActionEvent event)
		{
			Object object = event.getSource();
			if (object == closeButton)
				closeButton_Action(event);
		}
	}

	void closeButton_Action(java.awt.event.ActionEvent event)
	{
		// to do: code goes here.
		hide();
		dispose();
			 
	}
} // AboutDialog


class LoginDialog extends Dialog {

	String OK_Button_label = "  OK  ";
	String CANCEL_Button_label = "Cancel";
	String m_user;
	String m_passwd;
	Button OK_Button, CANCEL_Button;
	TextField passField,userField;

	public LoginDialog(Frame f)
	{

		super(f,"Login Dialog",true);


		Label Luser = new Label("User    ");
		Label Lpass = new Label("Password");
		OK_Button = new Button(OK_Button_label);
		CANCEL_Button = new Button(CANCEL_Button_label);
		Panel userPanel = new Panel();
		Panel passPanel = new Panel();
		Panel buttonPanel = new Panel();


		//Font fnt = new Font("Sans Serif", Font.BOLD,14);
		//setFont(fnt);
	

		setLayout(new FlowLayout(FlowLayout.CENTER,5,5));


		passField = new TextField(8);
		userField = new TextField(8);
		passField.setEchoCharacter('*');


		userPanel.setLayout(new GridLayout(1,1,20,0));
		passPanel.setLayout(new GridLayout(1,1,20,0));
		buttonPanel.setLayout(new GridLayout(1,1,20,0));


		userPanel.add(Luser);
		userPanel.add(userField);
		passPanel.add(Lpass);
		passPanel.add(passField);
		buttonPanel.add(OK_Button);
		buttonPanel.add(CANCEL_Button);
		add("North",userPanel);
		add("Center", passPanel);
		add("South", buttonPanel);



		resize(350,270);
		show();
	}


	String getUser()
	{
		return(m_user);
	}

	String getPassword()
	{
		return(m_passwd);
	}
	// the action handler
	public boolean action(Event e, Object arg)
	{

		if(!(e.target instanceof Button)) {
			return false;
		}

		String buttonLabel = (String ) arg;

		if(buttonLabel == OK_Button_label ) {
			
			m_user = userField.getText();
			m_passwd = passField.getText();
		} else {
			m_user = null;
			m_passwd = null;
		}
		
		dispose();

		return(true);
	}


} // LoginDialog

 class MessageBox  extends Dialog {
	String m_button;		

	public MessageBox(Frame f, String msg, String button)
	{
		   super(f,"Message Box",true);


		   // create one panel for the message 
		   // and another for the button
		   Panel p1 = new Panel();
		   Panel p2 = new Panel();


		   p1.setLayout(new GridLayout(2,2));
		   p1.add(new Label(msg));
		   add("Center",p1);
		



		   // add the button
		   Button b = new Button(button);
		   m_button = button;
	
		   p2.add(b);
		   add("South",p2);
		
		   // make the dialog fit the text size
		   pack();

		   // show the dialog box
		   show();
	}
	public boolean action(Event e, Object arg) 
	{

		if(!(e.target instanceof Button)) {
			return false;
		}

		String buttonLabel = (String ) arg;

		if(buttonLabel == m_button ) {
			dispose();
		}
		return(true);
		
	}


	
	public static void main(String[] args)
	{

		Frame f = new CloseableFrame();

		

/*
		MessageBox m = new MessageBox(f,"My fucking message Boxx\nThis is it ggggggggggggggg","OK");
		*/
			
		LoginDialog l = new LoginDialog(f);

	//	OKMsgBox m1 = new OKMsgBox(f,l.getUser());
//	OKMsgBox m2 = new OKMsgBox(f,l.getPassword());
	

//		CloseMsgBox m2 = new CloseMsgBox(f,"My CloseMsgBox");

		//AboutDialog a = new AboutDialog(f,"First Line\nSecond Lineddddddddd\nhfhfhfhf");

		
//		MyDialog myd = new  MyDialog(f);
		System.exit(1);

//		this.show();

	}

}


 class OKMsgBox extends MessageBox {

	 OKMsgBox(Frame f, String msg)
	 {

		super(f,msg,"OK");

	 }
	
 }


 class CloseMsgBox extends MessageBox {
	 CloseMsgBox(Frame f, String msg)
	 {

		 super(f,msg,"CLOSE");
	 }
 }




