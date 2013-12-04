package fi.helsinki.cs.jakaarel.levyhylly;

import java.lang.management.ManagementFactory;

import org.eclipse.jetty.jmx.MBeanContainer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 * Launcher for starting the web application with an embedded Jetty server.
 * Configured with the following system properties:
 * <ul>
 *   <li>{@value #PORT_PROPERTY} (default {@value #DEFAULT_PORT})</li>
 *   <li>{@value #DATABASE_URL_PROPERTY} (default {@value #DEFAULT_DATABASE_URL})</li>
 *   <li>{@value #WAR_FILE_PROPERTY} (default {@value #DEFAULT_WAR_FILE}</li>
 * </ul>
 * 
 * @author Jani Kaarela (@gmail.com)
 */
public class EmbeddedJettyLauncher {
    
    private static final String PORT_PROPERTY = "jettyPort";
    private static final String DATABASE_URL_PROPERTY = "databaseUrl";
    private static final String WAR_FILE_PROPERTY = "warFile";
    private static final String DEFAULT_PORT = String.valueOf(8080);
    private static final String DEFAULT_DATABASE_URL = "";
    private static final String DEFAULT_WAR_FILE = "levyhylly-webapp/target/levyhylly-webapp-1.0-SNAPSHOT.war";
    private static final String HEROKU_DB_URL_PREFIX = "postgres://";
    private static final String CORRECT_PG_DB_URL_PREFIX = "jdbc:postgresql://";
	    
    public static void main(String[] args) throws Exception {
	int port = Integer.parseInt(System.getProperty(PORT_PROPERTY, DEFAULT_PORT));
	String dbUrl = System.getProperty(DATABASE_URL_PROPERTY, DEFAULT_DATABASE_URL);
	if (isHerokuPostgresDbUrl(dbUrl)) {
	    dbUrl = fixHerokuPostgresDbUrl(dbUrl);
	}
	String warFile = System.getProperty(WAR_FILE_PROPERTY, DEFAULT_WAR_FILE);
	
	MBeanContainer mbeanContainer = new MBeanContainer(ManagementFactory.getPlatformMBeanServer());
	WebAppContext webAppContext = new WebAppContext();
	webAppContext.setContextPath("/");
	webAppContext.setWar(warFile);
	//TODO: create and register data source
	//Resource resource = new Resource("java:comp/env/levyhyllyDataSource", null);
	Server server = new Server(port);
	server.addBean(mbeanContainer);
	server.setHandler(webAppContext);
	server.start();
	server.join();
    }
    
    private static boolean isHerokuPostgresDbUrl(String url) {
	return url.startsWith(HEROKU_DB_URL_PREFIX);
    }
    
    private static String fixHerokuPostgresDbUrl(String url) {
	return url.replace(HEROKU_DB_URL_PREFIX, CORRECT_PG_DB_URL_PREFIX);
    }

}
