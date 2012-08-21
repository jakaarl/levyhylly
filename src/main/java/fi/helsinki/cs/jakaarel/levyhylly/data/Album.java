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
	
	public Long getId() {
	    return id;
	}
	
	public String getName() {
	    return name;
	}
	
	public Short getYear() {
	    return year;
	}
	
	public Artist getArtist() {
	    return artist;
	}
	
	void setName(String name) {
	    this.name = name;
	}
	
	void setYear(Short year) {
	    this.year = year;
	}
	
	void setArtist(Artist artist) {
	    this.artist = artist;
	}

}
