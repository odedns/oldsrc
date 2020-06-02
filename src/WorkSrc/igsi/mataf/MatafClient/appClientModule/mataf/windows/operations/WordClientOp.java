package mataf.windows.operations;

import Word.Application;
import Word.Document;
import Word.Documents;
import Word.Range;
import Word.Words;

import com.ibm.dse.base.DSEClientOperation;

/**
 * @author Eyal Ben Ze'ev
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class WordClientOp extends DSEClientOperation {
	/**
	 * @see com.ibm.dse.base.DSEClientOperation#execute()
	 */
	public void execute() throws Exception {
		Application app;
        Documents docs;
        Document doc;
        Range range;
        Words words;
        try
        {
            // Initialize the Java2Com Environment
            com.ibm.bridge2java.OleEnvironment.Initialize();

            // Create a new application
            app = new Application();

            // Make the application visible
            app.set_Visible(true);

            // Get all documents
            docs = app.get_Documents();

            // Add a blank document
            doc = docs.Add();

            // Add some text
            doc.get_Content().InsertAfter(getKeyedCollection().toString());

            // Get a range consisting of the second word
            range = doc.get_Words().Item(2);

            // Select the second word
            range.Select();

            // Bold the second word.  In this case, the bold property took an integer for the true/false 
            // value.
            range.set_Bold(1);            

            // Wait one second
            Thread.sleep(1000);

            // Close the workbook without saving
//            doc.Close(new Boolean("false"));
  
            // Quit the application
//            app.Quit();

        } catch (com.ibm.bridge2java.ComException e)
        {
            java.lang.System.out.println( "COM Exception:" );
            java.lang.System.out.println( Long.toHexString((e.getHResult())) );
            java.lang.System.out.println( e.getMessage() );
        } catch (Exception e)
        {
            java.lang.System.out.println("message: " + e.getMessage());
        } finally
        {
            app = null;
            com.ibm.bridge2java.OleEnvironment.UnInitialize();
        }

	}


}
