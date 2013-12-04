package fi.helsinki.cs.jakaarel.levyhylly.data;

import static fi.helsinki.cs.jakaarel.levyhylly.TestDatabaseConfiguration.DATASOURCE_BEAN_NAME;

import javax.sql.DataSource;

import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import fi.helsinki.cs.jakaarel.levyhylly.TestContextTestCase;
import fi.helsinki.cs.jakaarel.levyhylly.TestDatabaseConfiguration;

/**
 * 
 * @author jakaarl
 */
public abstract class DatabaseTestCase extends TestContextTestCase {

	protected DataSource dataSource;

	public void setUp() {
		dataSource = context.getBean(DATASOURCE_BEAN_NAME, DataSource.class);
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		populator.addScript(new ClassPathResource(getDataScriptPath()));
		DatabasePopulatorUtils.execute(populator, dataSource);
	}

	abstract protected String getDataScriptPath();

	@Override
	protected Class<?>[] getConfigurations() {
		return new Class[] { TestDatabaseConfiguration.class };
	}

}
