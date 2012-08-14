package fi.helsinki.cs.jakaarel.levyhylly.data.jdbc;

import static fi.helsinki.cs.jakaarel.levyhylly.TestDatabaseConfiguration.DATASOURCE_BEAN_NAME;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.springframework.dao.IncorrectResultSizeDataAccessException;

import fi.helsinki.cs.jakaarel.levyhylly.TestContextTestCase;
import fi.helsinki.cs.jakaarel.levyhylly.TestDatabaseConfiguration;
import fi.helsinki.cs.jakaarel.levyhylly.data.Artist;

/**
 * 
 * @author Jani Kaarela
 *
 */
public class ArtistDaoTest extends TestContextTestCase {
    
    private static final Integer NON_EXISTENT_ARTIST_ID = Integer.MAX_VALUE;
    private static final Integer EXISTENT_ARTIST_ID = Integer.valueOf(1);
    private static final String EXISTENT_ARTIST_NAME = "Tauski";
    
    private ArtistDao artistDao;
    
    @Before
    public void setUpDao() {
	artistDao = new ArtistDao();
	artistDao.setDataSource(context.getBean(DATASOURCE_BEAN_NAME, DataSource.class));
    }
    
    @Test(expected = IncorrectResultSizeDataAccessException.class)
    public void testLoadArtistNonExistent() {
	artistDao.loadArtist(NON_EXISTENT_ARTIST_ID);
    }
    
    @Test
    public void testLoadArtistExistent() {
	Artist artist = artistDao.loadArtist(EXISTENT_ARTIST_ID);
	assertNotNull(artist);
	assertEquals(EXISTENT_ARTIST_NAME, artist.getName());
    }

    @Override
    protected Class<?>[] getConfigurations() {
	return new Class[] { TestDatabaseConfiguration.class };
    }

}
