package fi.helsinki.cs.jakaarel.levyhylly.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
 * Data access object for {@link Album}s.
 * 
 * @author Jani Kaarela
 */
@Component
public class AlbumDao extends JdbcDaoSupport {

	private static final String ID_COLUMN = "id";
	private static final String NAME_COLUMN = "name";
	private static final String YEAR_COLUMN = "year";
	private static final String ARTIST_COLUMN = "artist_id";

	private static final String LOAD_QUERY = "SELECT id, name, year, artist_id FROM album WHERE id = ?";
	private static final String CREATE_STATEMENT = "INSERT INTO album (name, year, artist_id) VALUES (?, ?, ?)";
	private static final String FIND_BY_ARTIST_ID_QUERY = "SELECT id, name, year, artist_id FROM album WHERE artist_id = ? ORDER BY year";
	private static final String FIND_BY_ALBUM_NAME_QUERY = "SELECT id, name, year, artist_id FROM album WHERE LOWER(name) LIKE ? ORDER BY name, year";

	@Autowired
	public AlbumDao(DataSource dataSource) {
		super();
		setDataSource(dataSource);
	}

	/**
	 * Loads album by identifier.
	 * 
	 * @param id	album identifier.
	 * 
	 * @return	album object.
	 * 
	 * @throws DataAccessException	if load fails.
	 */
	public Album loadAlbum(Long id) throws DataAccessException {
		return getJdbcTemplate().queryForObject(LOAD_QUERY, AlbumRowMapper.INSTANCE, new Object[] { id });
	}
	
	/**
	 * Creates a new album.
	 * 
	 * @param name		album name.
	 * @param year		album year.
	 * @param artistId	album artist identifier.
	 * 
	 * @return	the newly created album.
	 * 
	 * @throws DataAccessException	if album creation fails.
	 */
	public Album createAlbum(final String name, final Short year, final Long artistId) throws DataAccessException {
		PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement preparedStatement = connection.prepareStatement(
						CREATE_STATEMENT, Statement.RETURN_GENERATED_KEYS);
				preparedStatement.setString(1, name);
				preparedStatement.setShort(2, year);
				preparedStatement.setLong(3, artistId);
				return preparedStatement;
			}
		};
		KeyHolder keyHolder = new GeneratedKeyHolder();
		getJdbcTemplate().update(preparedStatementCreator, keyHolder);
		Long albumId = new Long(keyHolder.getKey().longValue());
		Album album = new Album(albumId, name, year, artistId);
		return album;
	}

	/**
	 * Finds albums by artist identifier.
	 * 
	 * @param artistId	artist identifier.
	 * 
	 * @return	list of found albums.
	 * 	
	 * @throws DataAccessException	if finding albums fails.
	 */
	public List<Album> findAlbumByArtistId(Long artistId) throws DataAccessException {
		return getJdbcTemplate().query(FIND_BY_ARTIST_ID_QUERY, AlbumRowMapper.INSTANCE, new Object[] { artistId });
	}

	/**
	 * Finds albums by name, or part of name.
	 * 
	 * @param name	album name (or part of it).
	 * 
	 * @return	list of found albums.
	 * 
	 * @throws DataAccessException	if finding albums fails.
	 */
	public List<Album> findAlbumByNameLike(String name) throws DataAccessException {
		String likeifiedName = "%" + StringHelper.escapeLikeWildcards(name).toLowerCase() + "%";
		return getJdbcTemplate().query(FIND_BY_ALBUM_NAME_QUERY, AlbumRowMapper.INSTANCE,
				new Object[] { likeifiedName });
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
