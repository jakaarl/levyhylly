package fi.helsinki.cs.jakaarel.levyhylly.util;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

/**
 * If no database is configured, this class sets up an in-memory database.
 * 
 * @author Jani Kaarela
 */
public class DefaultInMemoryDatabase {

	private static final String CREATE_SCHEMA_SCRIPT = "create-schema.sql";
	private DataSource dataSource;

	/**
	 * Constructs the in-memory HSQL database and schema.
	 */
	public DefaultInMemoryDatabase() {
		EmbeddedDatabaseBuilder dbBuilder = new EmbeddedDatabaseBuilder();
		dbBuilder.setType(EmbeddedDatabaseType.HSQL);
		dbBuilder.addScript(CREATE_SCHEMA_SCRIPT);
		dataSource = dbBuilder.build();
	}

	public DataSource getDataSource() {
		return dataSource;
	}

}
