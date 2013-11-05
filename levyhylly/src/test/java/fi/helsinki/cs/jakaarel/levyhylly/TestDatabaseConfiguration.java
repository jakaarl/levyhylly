package fi.helsinki.cs.jakaarel.levyhylly;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import fi.helsinki.cs.jakaarel.levyhylly.util.DefaultInMemoryDatabase;

/**
 * A configuration class for test database.
 * 
 * @author Jani Kaarela
 */
@Configuration
@Profile("test")
public class TestDatabaseConfiguration {

	public static final String DATASOURCE_BEAN_NAME = "dataSource";
	private DefaultInMemoryDatabase db = new DefaultInMemoryDatabase();

	@Bean
	public DataSource dataSource() {
		return db.getDataSource();
	}

}
