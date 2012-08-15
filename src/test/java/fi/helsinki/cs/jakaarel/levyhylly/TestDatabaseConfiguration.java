package fi.helsinki.cs.jakaarel.levyhylly;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

/**
 * A configuration class for test database.
 * 
 * @author Jani Kaarela
 */
@Configuration
@Profile("test")
public class TestDatabaseConfiguration {
    
    public static final String DATASOURCE_BEAN_NAME = "dataSource";
    private static final String CREATE_SCHEMA_SCRIPT = "create-schema.sql";
    
    @Bean
    public DataSource dataSource() {
	EmbeddedDatabaseBuilder dbBuilder = new EmbeddedDatabaseBuilder();
	dbBuilder.setType(EmbeddedDatabaseType.HSQL);
	dbBuilder.addScript(CREATE_SCHEMA_SCRIPT);
	return dbBuilder.build();
    }

}
