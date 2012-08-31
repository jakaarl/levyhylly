package fi.helsinki.cs.jakaarel.levyhylly.data;

/**
 * 
 * @author jakaarl
 */
public class Track {
	
	private Long id;
	private Short number;
	private String name;
	private Short length;
	private Long albumId;
	
	public Track(Long id, Short number, String name, Short length, Long albumId) {
		this.id = id;
		this.number = number;
		this.name = name;
		this.length = length;
		this.albumId = albumId;
	}

	public Long getId() {
		return id;
	}
	
	public Short getNumber() {
		return number;
	}

	public String getName() {
		return name;
	}

	public Short getLength() {
		return length;
	}

	public Long getAlbumId() {
		return albumId;
	}
	
	void setNumber(Short number) {
		this.number = number;
	}
	
	void setName(String name) {
		this.name = name;
	}
	
	void setLength(Short length) {
		this.length = length;
	}
	
	void setAlbumId(Long albumId) {
		this.albumId = albumId;
	}
}
