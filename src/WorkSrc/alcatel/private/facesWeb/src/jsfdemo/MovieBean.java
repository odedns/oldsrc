/**
 * Created on 15/03/2005
 * @author P0006439
 * @version $id$
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package jsfdemo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.UIData;
import javax.faces.component.UIInput;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import beans.JsfUtil;


/**
 * @author P0006439
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class MovieBean
{
	String store;
	List<Movie> movies;
	Movie currentMovie = null;
	String context;
	Integer radioId;
	/**
	 * 
	 */
	public MovieBean()
	{
		super();
		movies = new LinkedList<Movie>();
		Movie m = new Movie("Raging Bull", 1980, "Scorcese", Movie.DRAMA);
		movies.add(m);
		m = new Movie("Heat", 1995, "Mann", Movie.ACTION);
		movies.add(m);
		m = new Movie("Lost Highway", 1997, "Lynch", Movie.DRAMA);
		movies.add(m);
		m = new Movie("Singles", 1992, "Crowe", Movie.COMEDY);
		movies.add(m);
		currentMovie = m;
	
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
		UIData table = (UIData) JsfUtil.findComponent("movieForm","movieTbl");
		Movie m = (Movie) table.getRowData();
		Movie details = (Movie) JsfUtil.getManagedBean("#{movieDetails}");
		details.setMovie(m);
		System.out.println("got movie =" + m.getName() + "gender=" + m.getGender());
		return("movieDetails");	
	}

	
	public String showMovieDetails()
	{
		System.out.println("showMovieDetails()");
		UIData table = (UIData) JsfUtil.findComponent("movieForm","movieTbl");
		Movie m = (Movie) table.getRowData();
		currentMovie = m;
		System.out.println("currentMovie =" + currentMovie.getName());
		return(null);
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
	
	
	/**
	 * get the list of movies from the data table UIComponent.
	 * Now loop over the table and find the movies to be deleted.
	 * Store them in a temp array. 
	 * Loop over the deleted array and remove all deleted movies
	 * from the original movie list.
	 * We can't simultaneously loop over a list and delete entries
	 * so we have to do this using a temp array.
	 * @return String the navigation outcome.
	 */
	public void deleteMovie(ActionEvent event)
	{
		System.out.println("MovieBean.deleteMovie()");
		UIData table = (UIData) JsfUtil.findComponent("movieForm", "movieTbl");
		ArrayList <Movie> ar = new ArrayList<Movie>();
		List list = (List) table.getValue();
		Iterator iter = list.iterator();
		while(iter.hasNext()) {
			Movie m = (Movie) iter.next();
			if(m.isSelected()) {
				ar.add(m);
			}		
		}
		/*
		 * now loop over delete list and delete
		 * the movies from the original list.
		 */
		iter = ar.iterator();
		while(iter.hasNext()) {
			 Movie m = (Movie) iter.next();
			 movies.remove(m);
			 System.out.println("deleted: " + m.getName());
			 
		}
	}
	
	public void handleValueChange(ValueChangeEvent event)
	{
		System.out.println("MovieBean.valueChange()");
		String val = (String) event.getNewValue();
		UIComponent comp = event.getComponent();
		JsfUtil.addMessage(comp,"New Value: " + val);
	}

	public Integer getRadioId() {
		return radioId;
	}

	public void setRadioId(Integer radioId) {
		this.radioId = radioId;
	}

	public void setCurrentMovie(Movie currentMovie) {
		this.currentMovie = currentMovie;
	}
	
	public String sortMovies()
	{
		Collections.sort(movies);
		return(null);
	}
}
