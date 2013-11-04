package fi.helsinki.cs.jakaarel.levyhylly.data;

import fi.helsinki.cs.jakaarel.levyhylly.TestContextTestCase;
import fi.helsinki.cs.jakaarel.levyhylly.TestDatabaseConfiguration;

/**
 * 
 * @author jakaarl
 */
public abstract class DatabaseTestCase extends TestContextTestCase {
	
	@Override
	protected Class<?>[] getConfigurations() {
		return new Class[] { TestDatabaseConfiguration.class };
	}
	
}
