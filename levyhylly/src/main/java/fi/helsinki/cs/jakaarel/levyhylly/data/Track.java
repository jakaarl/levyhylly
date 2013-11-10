package fi.helsinki.cs.jakaarel.levyhylly.data;

import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.NumberFormat;

/**
 * 
 * @author jakaarl
 */
public class Track {

	private static final String LENGTH_FORMAT = "00";
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
	
	public String getFormattedLength() {
		NumberFormat formatter = new DecimalFormat(LENGTH_FORMAT);
		int hours = length / (60 * 60);
		int minutes = length % (60 * 60) / 60;
		int seconds = length % 60;
		StringBuilder sb = new StringBuilder();
		if (hours > 0) {
			sb.append(formatter.format(hours) + ':');
		}
		sb.append(formatter.format(minutes) + ':');
		sb.append(formatter.format(seconds));
		return sb.toString();
		
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
