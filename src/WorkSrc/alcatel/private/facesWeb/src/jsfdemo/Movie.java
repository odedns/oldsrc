/**
 * Created on 15/03/2005
 * @author P0006439
 * @version $id$
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package jsfdemo;

import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import beans.JsfUtil;

/**
 * @author P0006439
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Movie implements Comparable<Movie>
{
	private boolean selected;
	String name;
	int year;
	String director;
	int gender;
	
	static final int ACTION = 1;
	static final int COMEDY = 2;
	static final int DRAMA = 3;

	private SelectItem[] genders = {

		new SelectItem(new Integer(ACTION), "Action"),

		new SelectItem(new Integer(COMEDY), "Comedy"),

		new SelectItem(new Integer(DRAMA), "Drama")
		
	};



	/**
	 * 
	 */
	public Movie()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public Movie(String name, int year, String director, int gender)
	{
		this.name = name;
		this.year = year;
		this.director = director;
		this.gender = gender;
			
	}
	
	
	public void setMovie(Movie m)
	{
		this.name = m.name;
		this.year = m.year;
		this.director = m.director;
		this.gender = m.gender;
		
	}
	/**
	 * @return
	 */
	public String getDirector()
	{
		return director;
	}

	/**
	 * @return
	 */
	public int getGender()
	{
		return gender;
	}


	public String getGenderString()
	{
		String s = null;
		switch(gender) {
			case COMEDY:
				s = "Comedy";
				break;
			case ACTION:
				s = "Action";
				break;
			case DRAMA:
				s = "Drama";
				break;
			default:
				s = "None";
				break;
				
		}
		return(s);
	}
	public String getGenderStr()
	{
			return new Integer(gender).toString();
	}

	public SelectItem[] getGenders(){
		return(genders);
	}


	public Integer getCurrentGender()
	{
		int currInx = 0;
		for(int i=0; i < genders.length; ++i) {
			Integer n = (Integer)genders[i].getValue();
			if(n.intValue() == gender) {
				currInx = i;
				break;
			}
		} // for
		System.out.println("currInx =" + currInx);
		return((Integer) genders[currInx].getValue());
		
	}
	/**
	 * @return
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @return
	 */
	public int getYear()
	{
		return year;
	}

	/**
	 * @param string
	 */
	public void setDirector(String string)
	{
		director = string;
	}

	/**
	 * @param string
	 */
	public void setGender(int gender)
	{
		this.gender = gender;
	}

	/**
	 * @param string
	 */
	public void setName(String string)
	{
		name = string;
	}

	/**
	 * @param i
	 */
	public void setYear(int i)
	{
		year = i;
	}
	
	public String showDetails()
	{
		UIComponent c = JsfUtil.findComponent("movieDetailsForm","text4");
		boolean b = c.isRendered();
		c.setRendered(!b);
		System.out.println("showDetails");
		return(null);
	}
	
	public void movieActionListener(ActionEvent event)
	{
		System.out.println("movieActionEvent= " + event.toString());
	}

	public String saveMovieDetails()
	{
		System.out.println("saving movie");
		return(null);	
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public void setGenders(SelectItem[] genders) {
		this.genders = genders;
	}

	public int compareTo(Movie o) {
		// TODO Auto-generated method stub
		return(this.name.compareTo(o.name));
	}

}
