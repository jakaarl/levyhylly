package fi.helsinki.cs.jakaarel.levyhylly.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import fi.helsinki.cs.jakaarel.levyhylly.util.StringHelper;

/**
 * Data access object for {@link Track}s.
 * 
 * @author jakaarl
 */
@Component
public class TrackDao extends JdbcDaoSupport {

	private static final String ID_COLUMN = "id";
	private static final String NUMBER_COLUMN = "track_number";
	private static final String NAME_COLUMN = "name";
	private static final String LENGTH_COLUMN = "track_length";
	private static final String ALBUM_ID_COLUMN = "album_id";

	private static final String LOAD_QUERY = "SELECT id, track_number, name, track_length, album_id FROM track WHERE id = ?";
	private static final String FIND_BY_NAME_LIKE_QUERY = "SELECT id, track_number, name, track_length, album_id FROM track WHERE LOWER(name) LIKE ? ORDER BY album_id, track_number";
	private static final String FIND_BY_ALBUM_ID_QUERY = "SELECT id, track_number, name, track_length, album_id FROM track WHERE album_id = ? ORDER BY track_number";
	private static final String INSERT_TRACK_STATEMENT = "INSERT INTO track (track_number, name, track_length, album_id) values (?, ?, ?, ?)";
	private static final String DELETE_TRACK_STATEMENT = "DELETE FROM track WHERE id = ?";

	@Autowired
	public TrackDao(DataSource dataSource) {
		super();
		setDataSource(dataSource);
	}

	/**
	 * Loads track by identifier.
	 * 
	 * @param id	track identifier.
	 * 
	 * @return	track object.
	 * 
	 * @throws DataAccessException	if load fails.
	 */
	public Track loadTrack(Long id) throws DataAccessException {
		return getJdbcTemplate().queryForObject(LOAD_QUERY, TrackRowMapper.INSTANCE, new Object[] { id });
	}

	/**
	 * Finds track by name, or part of name.
	 * 
	 * @param name	name, or part of it.
	 * 
	 * @return	matching tracks.
	 * 
	 * @throws DataAccessException	if query fails.
	 */
	public List<Track> findTrackByNameLike(String name) throws DataAccessException {
		String likeifiedName = "%" + StringHelper.escapeLikeWildcards(name).toLowerCase() + "%";
		return getJdbcTemplate()
				.query(FIND_BY_NAME_LIKE_QUERY, TrackRowMapper.INSTANCE, new Object[] { likeifiedName });
	}

	/**
	 * Finds tracks by album id.
	 * 
	 * @param albumId	album identifier.
	 * 
	 * @return	album tracks.
	 * 
	 * @throws DataAccessException	if 	query fails.
	 */
	public List<Track> findTrackByAlbumId(Long albumId) throws DataAccessException {
		return getJdbcTemplate().query(FIND_BY_ALBUM_ID_QUERY, TrackRowMapper.INSTANCE, new Object[] { albumId });
	}

	/**
	 * Creates a new track.
	 * 
	 * @param albumId	album identifier.
	 * @param number	track number.
	 * @param name		track name.
	 * @param length	track length.
	 * 
	 * @return	created track object.
	 * 
	 * @throws DataAccessException	if creation fails.
	 */
	public Track createTrack(final Long albumId, final Short number, final String name, final Short length)
			throws DataAccessException {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		getJdbcTemplate().update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(INSERT_TRACK_STATEMENT,
						Statement.RETURN_GENERATED_KEYS);
				ps.setShort(1, number.shortValue());
				ps.setString(2, name);
				if (length != null) {
					ps.setShort(3, length.shortValue());
				} else {
					ps.setNull(3, Types.SMALLINT);
				}
				ps.setLong(4, albumId);
				return ps;
			}
		}, keyHolder);
		Long id = keyHolder.getKey().longValue();
		return new Track(id, number, name, length, albumId);
	}
	
	/**
	 * Deletes the given track (if found).
	 * 
	 * @param track	track to delete.
	 * 
	 * @return	the deleted track, or <code>null</code> if no such track exists.
	 * 
	 * @throws DataAccessException	if deletion fails.
	 */
	public Track deleteTrack(Track track) throws DataAccessException {
		int affectedRows = getJdbcTemplate().update(DELETE_TRACK_STATEMENT, new Object[] { track.getId() });
		return (affectedRows == 0 ? null : track);
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
