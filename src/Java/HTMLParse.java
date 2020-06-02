import javax.swing.text.html.parser;
class HTMLParse {
	
	
	public static void main(String argv[]) 
	{

                              
		URL webPage = new URL("http://java.sun.com"); 
		HTMLEditorKit editorKit = new HTMLEditorKit(); 
		HTMLDocument HTMLDoc = (HTMLDocument)editorKit.createDefaultDocument(); 
		Reader HTMLReader = new InputStreamReader(webPage.openConnection().getInputStream()); 
		editorKit.read(HTMLReader, HTMLDoc, 0); 
	
		//Set up an element iterator to test the HTML 
		//elements for image types 
		ElementIterator it = new ElementIterator(HTMLDoc); 
		javax.swing.text.Element elem; 
		while ((elem = it.next()) != null) 
		{ 
			if (elem.getName().equals("img")) 
			{ 
				String imageSource; 
				if ((imageSource = 
					(String)elem.getAttributes().getAttribute (HTML.Attribute.SRC))
				 	!= null) 
				{ 
					//imageSource now contains the src="blah" string 
					//Insert any additional code 
				} 
			} 
		} // while
	} // main
}