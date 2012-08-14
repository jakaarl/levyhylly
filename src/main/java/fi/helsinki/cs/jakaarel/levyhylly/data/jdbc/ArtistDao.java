package fi.helsinki.cs.jakaarel.levyhylly.data.jdbc;

import java.util.List;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import fi.helsinki.cs.jakaarel.levyhylly.data.Artist;

/**
 * @author Jani Kaarela
 *
 */
public class ArtistDao extends JdbcDaoSupport {
    
    public Artist loadArtist(Integer id) {
	Artist artist = null;
	return artist;
    }
    
    public List<Artist> findArtistByName(String name) {
	List<Artist> results = null;
	return results;
    }

}
