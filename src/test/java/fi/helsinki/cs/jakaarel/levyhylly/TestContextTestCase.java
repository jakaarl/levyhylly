package fi.helsinki.cs.jakaarel.levyhylly;

import org.junit.After;
import org.junit.Before;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Base class for tests requiring an application context. Sets up a context and
 * registers the configurations returned by {@link #getConfigurations()}.
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
	context.register(getConfigurations());
	context.refresh();
    }
    
    @After
    public void tearDownContext() {
	context.close();
    }
    
    /**
     * Gets the configuration classes (annotated with @Configuration) to register.
     * 
     * @return	configuration classes to register.
     */
    protected abstract Class<?>[] getConfigurations();

}
