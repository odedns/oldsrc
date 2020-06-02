/**
 * Created on 15/03/2005
 * @author P0006439
 * @version $id$
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package pagecode;

import com.ibm.faces.component.html.HtmlScriptCollector;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.component.UIColumn;
import javax.faces.component.html.HtmlCommandLink;
import javax.faces.component.html.HtmlForm;
/**
 * @author P0006439
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Catalog extends PageCodeBase {

	protected HtmlScriptCollector scriptCollector1;
	protected HtmlDataTable movieTbl;
	protected UIColumn name;
	protected HtmlOutputText text2;
	protected HtmlOutputText text6;
	protected HtmlOutputText text3;
	protected UIColumn year1;
	protected HtmlOutputText text4;
	protected UIColumn director1;
	protected HtmlOutputText text5;
	protected UIColumn gender;
	protected HtmlCommandLink link1;
	protected HtmlOutputText text1;
	protected HtmlForm form1;
	protected HtmlCommandLink movieDetails;
	protected HtmlCommandLink link2;
	protected HtmlScriptCollector getScriptCollector1()
	{
		if (scriptCollector1 == null)
		{
			scriptCollector1 =
				(HtmlScriptCollector) findComponentInRoot("scriptCollector1");
		}
		return scriptCollector1;
	}
	protected HtmlDataTable getMovieTbl()
	{
		if (movieTbl == null)
		{
			movieTbl = (HtmlDataTable) findComponentInRoot("movieTbl");
		}
		return movieTbl;
	}
	protected UIColumn getName()
	{
		if (name == null)
		{
			name = (UIColumn) findComponentInRoot("name");
		}
		return name;
	}
	protected HtmlOutputText getText2()
	{
		if (text2 == null)
		{
			text2 = (HtmlOutputText) findComponentInRoot("text2");
		}
		return text2;
	}
	protected HtmlOutputText getText6()
	{
		if (text6 == null)
		{
			text6 = (HtmlOutputText) findComponentInRoot("text6");
		}
		return text6;
	}
	protected HtmlOutputText getText3()
	{
		if (text3 == null)
		{
			text3 = (HtmlOutputText) findComponentInRoot("text3");
		}
		return text3;
	}
	protected UIColumn getYear1()
	{
		if (year1 == null)
		{
			year1 = (UIColumn) findComponentInRoot("year1");
		}
		return year1;
	}
	protected HtmlOutputText getText4()
	{
		if (text4 == null)
		{
			text4 = (HtmlOutputText) findComponentInRoot("text4");
		}
		return text4;
	}
	protected UIColumn getDirector1()
	{
		if (director1 == null)
		{
			director1 = (UIColumn) findComponentInRoot("director1");
		}
		return director1;
	}
	protected HtmlOutputText getText5()
	{
		if (text5 == null)
		{
			text5 = (HtmlOutputText) findComponentInRoot("text5");
		}
		return text5;
	}
	protected UIColumn getGender()
	{
		if (gender == null)
		{
			gender = (UIColumn) findComponentInRoot("gender");
		}
		return gender;
	}
	protected HtmlCommandLink getLink1()
	{
		if (link1 == null)
		{
			link1 = (HtmlCommandLink) findComponentInRoot("link1");
		}
		return link1;
	}
	protected HtmlOutputText getText1()
	{
		if (text1 == null)
		{
			text1 = (HtmlOutputText) findComponentInRoot("text1");
		}
		return text1;
	}
	protected HtmlForm getForm1()
	{
		if (form1 == null)
		{
			form1 = (HtmlForm) findComponentInRoot("form1");
		}
		return form1;
	}
	protected HtmlCommandLink getMovieDetails()
	{
		if (movieDetails == null)
		{
			movieDetails =
				(HtmlCommandLink) findComponentInRoot("movieDetails");
		}
		return movieDetails;
	}
	protected HtmlCommandLink getLink2()
	{
		if (link2 == null)
		{
			link2 = (HtmlCommandLink) findComponentInRoot("link2");
		}
		return link2;
	}
}