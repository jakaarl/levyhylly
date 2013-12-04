package fi.helsinki.cs.jakaarel.levyhylly.data;

/**
 * @author Jani Kaarela
 * 
 */
public class Album {

	private Long id;
	private String name;
	private Short year;
	private Long artistId;

	public Album(Long id, String name, Short year, Long artistId) {
		this.id = id;
		this.name = name;
		this.year = year;
		this.artistId = artistId;
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

	public Long getArtistId() {
		return artistId;
	}

	void setName(String name) {
		this.name = name;
	}

	void setYear(Short year) {
		this.year = year;
	}

	void setArtistId(Long artistId) {
		this.artistId = artistId;
	}

}
