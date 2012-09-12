package fi.helsinki.cs.jakaarel.levyhylly.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import fi.helsinki.cs.jakaarel.levyhylly.util.StringHelper;

/**
 * 
 * @author Jani Kaarela
 *
 */
public class AlbumDao extends JdbcDaoSupport {
    
    private static final String ID_COLUMN = "id";
    private static final String NAME_COLUMN = "name";
    private static final String YEAR_COLUMN = "year";
    private static final String ARTIST_COLUMN = "artist_id";
    
    private static final String LOAD_QUERY =
	    "SELECT id, name, year, artist_id FROM album WHERE id = ?";
    private static final String FIND_BY_ARTIST_ID_QUERY =
	    "SELECT id, name, year, artist_id FROM album WHERE artist_id = ? ORDER BY year";
    private static final String FIND_BY_ALBUM_NAME_QUERY =
    		"SELECT id, name, year, artist_id FROM album WHERE LOWER(name) LIKE ? ORDER BY name, year";
    
    public Album loadAlbum(Long id) throws DataAccessException {
	return getJdbcTemplate().queryForObject(
		LOAD_QUERY, AlbumRowMapper.INSTANCE, new Object[] { id });
    }
    
    public List<Album> findAlbumByArtistId(Long artistId) throws DataAccessException {
	return getJdbcTemplate().query(
		FIND_BY_ARTIST_ID_QUERY, AlbumRowMapper.INSTANCE, new Object[] { artistId });
    }
    
    public List<Album> findAlbumByNameLike(String name) throws DataAccessException {
    	String likeifiedName = "%" + StringHelper.escapeLikeWildcards(name).toLowerCase() + "%";
    	return getJdbcTemplate().query(
    		FIND_BY_ALBUM_NAME_QUERY, AlbumRowMapper.INSTANCE, new Object[] { likeifiedName });
    }
    
    
    private static class AlbumRowMapper implements RowMapper<Album> {
	
	private static final AlbumRowMapper INSTANCE = new AlbumRowMapper();
	
	private AlbumRowMapper() {
	    // suppress default constructor
	}

	@Override
	public Album mapRow(ResultSet rs, int rowNum) throws SQLException {
	    Long id = rs.getLong(ID_COLUMN);
	    String name = rs.getString(NAME_COLUMN);
	    Short year = rs.getShort(YEAR_COLUMN);
	    Long artistId = rs.getLong(ARTIST_COLUMN);
	    return new Album(id, name, year, artistId);
	}
	
    }

}