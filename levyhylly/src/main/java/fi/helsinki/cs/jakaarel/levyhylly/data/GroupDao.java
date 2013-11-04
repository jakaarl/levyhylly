package fi.helsinki.cs.jakaarel.levyhylly.data;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 * 
 * @author Jani Kaarela
 * 
 */
public class GroupDao extends JdbcDaoSupport {
	
	private static final String ID_COLUMN = "id";
	private static final String NAME_COLUMN = "name";
	private static final String LOAD_QUERY = "SELECT id, name FROM user_group WHERE id = ?";
	
	public Group loadGroup(Long id) throws DataAccessException {
		return getJdbcTemplate().queryForObject(LOAD_QUERY,
				GroupRowMapper.INSTANCE, new Object[] { id });
	}
	
	private static class GroupRowMapper implements RowMapper<Group> {
		
		public static final GroupRowMapper INSTANCE = new GroupRowMapper();
		
		private GroupRowMapper() {
			// suppress public constructor
		}
		
		@Override
		public Group mapRow(ResultSet rs, int row) throws SQLException {
			Long id = Long.valueOf(rs.getLong(ID_COLUMN));
			String name = rs.getString(NAME_COLUMN);
			return new Group(id, name);
		}
		
	}
}
