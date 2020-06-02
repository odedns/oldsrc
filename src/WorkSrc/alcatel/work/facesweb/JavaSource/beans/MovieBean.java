/**
 * Created on 15/03/2005
 * @author P0006439
 * @version $id$
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package beans;

import java.util.LinkedList;
import java.util.List;

import javax.faces.component.UIData;

/**
 * @author P0006439
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class MovieBean
{
	String store;
	List movies;
	Movie currentMovie = null;
	String context;
	/**
	 * 
	 */
	public MovieBean()
	{
		super();
		movies = new LinkedList();
		Movie m = new Movie("Raging Bull", 1980, "Scorcese", Movie.DRAMA);
		movies.add(m);
		m = new Movie("Heat", 1995, "Mann", Movie.ACTION);
		movies.add(m);
		m = new Movie("Lost Highway", 1997, "Lynch", Movie.DRAMA);
		movies.add(m);
		m = new Movie("Singles", 1992, "Crowe", Movie.COMEDY);
		movies.add(m);
	}

	/**
	 * @return
	 */
	public List getMovies()
	{
		return movies;
	}

	/**
	 * @param list
	 */
	public void setMovies(List list)
	{
		movies = list;
	}
	

	/**
	 * @return
	 */
	public String getStore()
	{
		return store;
	}


	public Movie getCurrentMovie()
	{
		return(currentMovie);
	}
	/**
	 * @param string
	 */
	public void setStore(String string)
	{
		store = string;
	}
	
	public String movieDetails()
	{
		System.out.println("movie details context=" + context);
		UIData table = (UIData) JsfUtil.findComponent("form1","movieTbl");
		Movie m = (Movie) table.getRowData();
		Movie details = (Movie) JsfUtil.getManagedBean("#{movieDetails}");
		details.setMovie(m);
		System.out.println("got movie =" + m.getName() + "gender=" + m.getGender());
		return("movieDetails");	
	}

	
	/**
	 * @return
	 */
	public String getContext()
	{
		return context;
	}

	/**
	 * @param string
	 */
	public void setContext(String string)
	{
		context = string;
	}

}
