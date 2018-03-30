import java.util.ArrayList;
import java.util.Date;

/**
 * The Movie class implements a movie with its name, director, cast, type, date
 * and metascore
 * 
 * @author Volkan Ozer
 * @version 1.0
 * @since 2017-11-26
 *
 */
public class Movie {

	public String name; // name of the movie
	public String director; // director of the movie
	public ArrayList<String> cast = new ArrayList<>(); // cast of the movie
	public ArrayList<String> type = new ArrayList<>(); // type of the movie
	public ArrayList<Date> dates = new ArrayList<>(); // date of the movie
	public int metascore; // metascore of the movie

	/**
	 * Initializes an empty movie.
	 */
	Movie() {

	}

	/**
	 * Initializes a movie with its name and director
	 * 
	 * @param name
	 *            name of the movie
	 * @param director
	 *            director of the movie
	 */
	Movie(String name, String director) {
		this.name = name;
		this.director = director;
	}

	/**
	 * Returns a string representation of movie information
	 * 
	 * @return a movie's name, director, cast, type and metascore
	 */
	public String toString() {
		return String.format("Name     : %s\nDirector : %s\nCast     : %s\nType     : %s\nMetascore: %d", this.name,
				this.director, this.cast, this.type, this.metascore);
	}
}
