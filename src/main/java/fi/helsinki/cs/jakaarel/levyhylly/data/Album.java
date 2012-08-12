package fi.helsinki.cs.jakaarel.levyhylly.data;

/**
 * @author Jani Kaarela
 *
 */
public class Album {
	
	private Long id;
	private String name;
	private Short year;
	private Artist artist;
	
	public Album(Long id, String name, Short year, Artist artist) {
		this.id = id;
		this.name = name;
		this.year = year;
		this.artist = artist;
	}

}
