package fi.helsinki.cs.jakaarel.levyhylly;

import org.junit.After;
import org.junit.Before;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Base class for tests requiring an application context. Sets up the context
 * with {@link TestDatabaseConfiguration}.
 * 
 * @author Jani Kaarela
 */
public abstract class TestContextTestCase {
    
    private static final String TEST_PROFILE = "test";
    protected AnnotationConfigApplicationContext context;
    
    @Before
    public void setUpContext() {
	context = new AnnotationConfigApplicationContext();
	context.getEnvironment().setActiveProfiles(TEST_PROFILE);
	context.register(TestDatabaseConfiguration.class);
	context.refresh();
    }
    
    @After
    public void tearDownContext() {
	context.close();
    }

}
