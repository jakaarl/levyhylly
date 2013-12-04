package fi.helsinki.cs.jakaarel.levyhylly.data;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 * 
 * @author Jani Kaarela
 * 
 */
public class UserDao extends JdbcDaoSupport {

	private static final String LOAD_USER_QUERY = "SELECT u.id, u.login, u.name, g.id FROM "
			+ "user_account AS u, user_group AS g WHERE u.login = ?";
	private static final String USER_ID_COLUMN = "u.id";
	private static final String LOGIN_COLUMN = "u.login";
	private static final String NAME_COLUMN = "u.name";
	private static final String GROUP_COLUMN = "g.id";

	public User loadUser(String login) throws DataAccessException {
		return getJdbcTemplate().query(LOAD_USER_QUERY, new Object[] { login }, UserResultSetExtractor.INSTANCE);
	}

	private static class UserResultSetExtractor implements ResultSetExtractor<User> {

		private static UserResultSetExtractor INSTANCE = new UserResultSetExtractor();

		private UserResultSetExtractor() {
			// suppress public constructor
		}

		@Override
		public User extractData(ResultSet rs) throws SQLException, DataAccessException {
			User user = new User();
			long groupId = 0;
			if (rs.next()) {
				user.setId(Long.valueOf(rs.getLong(USER_ID_COLUMN)));
				user.setLogin(rs.getString(LOGIN_COLUMN));
				user.setName(rs.getString(NAME_COLUMN));
				groupId = rs.getLong(GROUP_COLUMN);
				if (!rs.wasNull()) {
					user.addGroup(Long.valueOf(groupId));
				}
			}
			while (rs.next()) {
				groupId = rs.getLong(GROUP_COLUMN);
				if (!rs.wasNull()) {
					user.addGroup(Long.valueOf(groupId));
				}
			}
			return user;
		}

	}

}
