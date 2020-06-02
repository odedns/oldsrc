import javax.swing.text.html.parser.*;
import javax.swing.text.html.HTML.*;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTMLEditorKit;

import java.io.*;


/**
 * <p>Title: HtmlCallbacks</p>
 * <p>Description: This class implements call back functions used
 * by the swing HTML parser.</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Open University</p>
 * @author Oded Nissan
 * @version 1.0 28/8/2002
 */

public class HtmlCallbacks extends HTMLEditorKit.ParserCallback {
	StringBuffer m_sb = null;
	boolean m_collect;
	String m_currTag;
	String m_title;

    public HtmlCallbacks()
	{
		m_sb = new StringBuffer();
		m_collect = false;
    }

	public void handleStartTag(Tag t, MutableAttributeSet a, int pos)
	{
		//System.out.println("handleStartTag tag =" + t);
		m_currTag = t.toString();

	}


	public void handleText(char[] data, int pos)
	{
	//	System.out.println("handleText data= " + new String(data));

		if(m_currTag.equalsIgnoreCase("title")) {
			m_title =  new String(data);
		} else {
			m_sb.append(data);
		}

	}

	/**
	 * get the text collected  by the parser.
	 * @return String the text in the HTML document
	 * without the HTML tags.
	 */
	public String getText()
	{
		return(m_sb.toString());
	}

	/**
	 * get the document's title text.
	 * @return String the document title.
	 */
	public String getTitle()
	{
		return(m_title);
	}

	public static void main(String argv[])
	{
		String fname = "e:/dev/docs/shelltips.html";
		try {
			ParserDelegator parser = new ParserDelegator();
			HtmlCallbacks callBack = new HtmlCallbacks();
			parser.parse(new FileReader(fname), callBack,true);
			System.out.println("text =\n" + callBack.getText());

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}


}