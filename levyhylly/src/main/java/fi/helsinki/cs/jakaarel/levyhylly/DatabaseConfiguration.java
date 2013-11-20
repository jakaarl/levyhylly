package fi.helsinki.cs.jakaarel.levyhylly;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.jndi.JndiLocatorSupport;
import org.springframework.jndi.JndiTemplate;

import fi.helsinki.cs.jakaarel.levyhylly.util.DefaultInMemoryDatabase;

/**
 * Spring database configuration.
 * 
 * @author Jani Kaarela
 */
@Configuration
public class DatabaseConfiguration {

	public static final String ALBUM_DATA_FILE = "album-data.sql";
	public static final String ARTIST_DATA_FILE = "artist-data.sql";
	public static final String CREATE_SCHEMA_FILE = "create-schema.sql";
	public static final String TRACK_DATA_FILE = "track-data.sql";
	private static final Logger LOGGER = Logger.getLogger(DatabaseConfiguration.class.getName());
	private static final String DATA_SOURCE_JNDI_NAME = JndiLocatorSupport.CONTAINER_PREFIX + "levyhyllyDataSource";
	private static final String POPULATE_TEST_DATA_KEY = "populateTestDataToDb";

	private DataSource dataSource;

	public DatabaseConfiguration() {
		JndiTemplate jndiTemplate = new JndiTemplate();
		try {
			dataSource = jndiTemplate.lookup(DATA_SOURCE_JNDI_NAME, DataSource.class);
			if (LOGGER.isLoggable(Level.INFO)) {
				LOGGER.info("Successfully looked up data source " + DATA_SOURCE_JNDI_NAME);
			}
		} catch (NamingException ne) {
			LOGGER.log(Level.WARNING, "JNDI data source lookup failed, reverting to in-memory database"
					+ "- are you sure you bound a data source to " + DATA_SOURCE_JNDI_NAME + "?", ne);
			DefaultInMemoryDatabase inMemoryDatabase = new DefaultInMemoryDatabase();
			dataSource = inMemoryDatabase.getDataSource();
		}
		if (Boolean.getBoolean(POPULATE_TEST_DATA_KEY)) {
			LOGGER.info("Inserting test data to database.");
			ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
			populator.addScript(new ClassPathResource(TRACK_DATA_FILE));
			DatabasePopulatorUtils.execute(populator, dataSource);
		}
	}

	@Bean
	public DataSource dataSource() {
		return dataSource;
	}

}
