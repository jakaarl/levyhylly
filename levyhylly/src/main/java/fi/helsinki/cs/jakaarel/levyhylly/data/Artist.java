package fi.helsinki.cs.jakaarel.levyhylly.data;

/**
 * @author Jani Kaarela
 * 
 */
public class Artist {

	private Long id;
	private String name;

	public Artist(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	void setName(String name) {
		this.name = name;
	}

}
