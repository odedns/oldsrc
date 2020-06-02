/**
 * Date: 26/03/2007
 * File: Team.java
 * Package: jpatest
 */
package jpatest;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.CascadeType;

/**
 * @author a73552
 *
 */
@Entity
public class Team {
	@Id
	int id;
	String name;
	String city;
	@OneToMany(fetch=FetchType.EAGER, mappedBy = "team" )
	List <Player> players;
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Player> getPlayers() {
		return players;
	}
	public void setPlayers(List<Player> players) {
		this.players = players;
	}
}
