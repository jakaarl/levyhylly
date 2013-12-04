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
 * Data access object for {@link Artist}s.
 * 
 * @author Jani Kaarela
 */
@Component
public class ArtistDao extends JdbcDaoSupport {

	private static final String ID_COLUMN = "id";
	private static final String NAME_COLUMN = "name";
	private static final String LOAD_QUERY = "SELECT id, name FROM artist WHERE id = ?";
	private static final String CREATE_STATEMENT = "INSERT INTO artist (name) VALUES (?)";
	private static final String FIND_BY_NAME_QUERY = "SELECT id, name FROM artist WHERE name " + "= ? ORDER BY name"; // TODO:
																														// limit,
																														// like
	private static final String FIND_BY_NAME_LIKE_QUERY = "SELECT id, name FROM artist WHERE LOWER(name) LIKE ? ORDER BY name";

	@Autowired
	public ArtistDao(DataSource dataSource) {
		super();
		setDataSource(dataSource);
	}

	/**
	 * Loads artist by identifier.
	 * 
	 * @param id	artist identifier.
	 * 
	 * @return	artist object.
	 * 
	 * @throws DataAccessException	if load fails.
	 */
	public Artist loadArtist(Long id) throws DataAccessException {
		return getJdbcTemplate().queryForObject(LOAD_QUERY, ArtistRowMapper.INSTANCE, new Object[] { id });
	}
	
	/**
	 * Creates a new artist.
	 * 
	 * @param name	artist name.
	 * 
	 * @return	the newly created artist.
	 * 
	 * @throws DataAccessException	if inserting the artist fails.
	 */
	public Artist createArtist(final String name) throws DataAccessException {
		PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement preparedStatement = connection.prepareStatement(
						CREATE_STATEMENT, Statement.RETURN_GENERATED_KEYS);
				preparedStatement.setString(1, name);
				return preparedStatement;
			}
		};
		KeyHolder keyHolder = new GeneratedKeyHolder();
		getJdbcTemplate().update(preparedStatementCreator, keyHolder);
		Long artistId = new Long(keyHolder.getKey().longValue());
		Artist artist = new Artist(artistId, name);
		return artist;
	}

	/**
	 * Finds artist by name.
	 * 
	 * @param name	artist name.
	 * 
	 * @return	result artists.
	 * 
	 * @throws DataAccessException	if query fails.
	 */
	public List<Artist> findArtistsByName(String name) throws DataAccessException {
		return getJdbcTemplate().query(FIND_BY_NAME_QUERY, ArtistRowMapper.INSTANCE, new Object[] { name });
	}

	/**
	 * Finds artist by name, or part of name.
	 * 
	 * @param name	artist name (or part of it).
	 * 
	 * @return	result artists.
	 * 
	 * @throws DataAccessException	if query fails.
	 */
	public List<Artist> findArtistsByNameLike(String name) throws DataAccessException {
		String likeifiedName = "%" + StringHelper.escapeLikeWildcards(name).toLowerCase() + "%";
		return getJdbcTemplate().query(FIND_BY_NAME_LIKE_QUERY, ArtistRowMapper.INSTANCE,
				new Object[] { likeifiedName });
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
