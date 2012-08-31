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
 * @author jakaarl
 */
public class TrackDao extends JdbcDaoSupport {

	private static final String ID_COLUMN = "id";
	private static final String NUMBER_COLUMN = "track_number";
    private static final String NAME_COLUMN = "name";
    private static final String LENGTH_COLUMN = "track_length";
    private static final String ALBUM_ID_COLUMN = "album_id";
    
    private static final String LOAD_QUERY =
	    "SELECT id, track_number, name, track_length, album_id FROM track WHERE id = ?";
    private static final String FIND_BY_NAME_LIKE_QUERY =
    		"SELECT id, track_number, name, track_length, album_id FROM track WHERE LOWER(name) LIKE ? ORDER BY album_id, track_number";
    private static final String FIND_BY_ALBUM_ID_QUERY =
    		"SELECT id, track_number, name, track_length, album_id FROM track WHERE album_id = ? ORDER BY track_number";
    
    public Track loadTrack(Long id) throws DataAccessException {
    	return getJdbcTemplate().queryForObject(LOAD_QUERY, TrackRowMapper.INSTANCE, new Object[] { id });
    }
    
    public List<Track> findTrackByNameLike(String name) throws DataAccessException {
    	String likeifiedName = "%" + StringHelper.escapeLikeWildcards(name).toLowerCase() + "%";
    	return getJdbcTemplate().query(
    		FIND_BY_NAME_LIKE_QUERY, TrackRowMapper.INSTANCE, new Object[] { likeifiedName });
    }
    
    public List<Track> findTrackByAlbumId(Long albumId) throws DataAccessException {
    	return getJdbcTemplate().query(
    		FIND_BY_ALBUM_ID_QUERY, TrackRowMapper.INSTANCE, new Object[] { albumId });
    }
    
    
    private static class TrackRowMapper implements RowMapper<Track> {
    	
    	private static final TrackRowMapper INSTANCE = new TrackRowMapper();
    	
    	private TrackRowMapper() {
    		// default public constructor? no thanks!
    	}

		@Override
		public Track mapRow(ResultSet rs, int rowNum) throws SQLException {
			Long id = rs.getLong(ID_COLUMN);
			Short number = rs.getShort(NUMBER_COLUMN);
			String name = rs.getString(NAME_COLUMN);
			Short length = rs.getShort(LENGTH_COLUMN);
			if (rs.wasNull()) {
				length = null;
			}
			Long albumId = rs.getLong(ALBUM_ID_COLUMN);
			return new Track(id, number, name, length, albumId);
		}
    	
    	
    }
}
