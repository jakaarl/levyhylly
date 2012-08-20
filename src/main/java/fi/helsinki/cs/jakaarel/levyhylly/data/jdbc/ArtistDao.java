package fi.helsinki.cs.jakaarel.levyhylly.data.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import fi.helsinki.cs.jakaarel.levyhylly.data.Artist;

/**
 * @author Jani Kaarela
 *
 */
public class ArtistDao extends JdbcDaoSupport {
    
    private static final String ID_COLUMN = "id";
    private static final String NAME_COLUMN = "name";
    private static final String LOAD_QUERY =
	    "SELECT id, name FROM artist WHERE id = ?";
    private static final String FIND_BY_NAME_QUERY =
	    "SELECT id, name FROM artist WHERE name "+
	    "= ? ORDER BY name"; // TODO: limit, like
    private static final String FIND_BY_NAME_LIKE_QUERY =
	    "SELECT id, name FROM artist WHERE name LIKE ? ORDER BY name";
    
    public Artist loadArtist(Long id) throws DataAccessException {
	return getJdbcTemplate().queryForObject(
		LOAD_QUERY, ArtistRowMapper.INSTANCE, new Object[] { id });
    }
    
    public List<Artist> findArtistsByName(String name) throws DataAccessException {
	return getJdbcTemplate().query(
		FIND_BY_NAME_QUERY, ArtistRowMapper.INSTANCE, new Object[] { name });
    }
    
    public List<Artist> findArtistsByNameLike(String name) throws DataAccessException {
	String escapedName = name.replaceAll("(%|_)", "\\\\\\1");
	return getJdbcTemplate().query(
		FIND_BY_NAME_LIKE_QUERY, ArtistRowMapper.INSTANCE, new Object[] { escapedName });
    }
    
    
    private static class ArtistRowMapper implements RowMapper<Artist> {

	private static final ArtistRowMapper INSTANCE = new ArtistRowMapper();
	
	private ArtistRowMapper() {
	    // suppress public constructor
	}
	
	@Override
	public Artist mapRow(ResultSet rs, int rowNum) throws SQLException {
	    Long id = rs.getLong(ID_COLUMN);
	    String name = rs.getString(NAME_COLUMN);
	    return new Artist(id, name);
	}
	
    }

}
