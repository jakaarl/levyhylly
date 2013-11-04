package fi.helsinki.cs.jakaarel.levyhylly;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit tests for {@link TestContextTestCase}. YEs, the class name has a certain
 * comical aspect, but at least it's consistent!
 * 
 * @author Jani Kaarela
 */
public class TestContextTestCaseTest extends TestContextTestCase {
	
	/**
	 * Test setting up application context and data source.
	 */
	@Test
	public void testDataSource() {
		assertTrue(context
				.containsBeanDefinition(TestDatabaseConfiguration.DATASOURCE_BEAN_NAME));
	}
	
	@Override
	protected Class<?>[] getConfigurations() {
		return new Class[] { TestDatabaseConfiguration.class };
	}
	
}
