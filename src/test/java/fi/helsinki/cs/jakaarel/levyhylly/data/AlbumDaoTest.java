package fi.helsinki.cs.jakaarel.levyhylly.data;

import static fi.helsinki.cs.jakaarel.levyhylly.TestDatabaseConfiguration.DATASOURCE_BEAN_NAME;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import fi.helsinki.cs.jakaarel.levyhylly.TestContextTestCase;
import fi.helsinki.cs.jakaarel.levyhylly.TestDatabaseConfiguration;

/**
 * 
 * @author Jani Kaarela
 *
 */
public class AlbumDaoTest extends TestContextTestCase {
    
    private static final String ALBUM_DATA_SCRIPT = "album-data.sql";
    private static final Long NON_EXISTENT_ALBUM_ID = Long.MAX_VALUE;
    private static final Long EXISTENT_ALBUM_ID = Long.valueOf(1);
    private static final String EXISTENT_ALBUM_NAME = "Charlotta";
    private static final Long NON_EXISTENT_ARTIST_ID = Long.MAX_VALUE;
    private static final Long EXISTENT_ARTIST_ID = Long.valueOf(1);
    private static final int EXISTENT_ARTIST_ALBUM_COUNT = 10;
    
    private AlbumDao albumDao;

    @Before
    public void setUp() {
	DataSource dataSource = context.getBean(DATASOURCE_BEAN_NAME, DataSource.class);
	ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
	populator.addScript(new ClassPathResource(ALBUM_DATA_SCRIPT));
	DatabasePopulatorUtils.execute(populator, dataSource);
	albumDao = new AlbumDao();
	albumDao.setDataSource(dataSource);
    }
    
    @Test(expected = IncorrectResultSizeDataAccessException.class)
    public void testLoadAlbumNonExistent() {
	albumDao.loadAlbum(NON_EXISTENT_ALBUM_ID);
    }
    
    @Test
    public void testLoadAlbumExistent() {
	Album album = albumDao.loadAlbum(EXISTENT_ALBUM_ID);
	assertNotNull(album);
	assertEquals(EXISTENT_ALBUM_NAME, album.getName());
    }
    
    @Test
    public void testFindAlbumByArtistIdNonExistent() {
    	List<Album> albums = albumDao.findAlbumByArtistId(NON_EXISTENT_ARTIST_ID);
    	assertNotNull(albums);
    	assertTrue(albums.isEmpty());
    }
    
    @Test
    public void testFindAlbumByArtistIdExistent() {
    	List<Album> albums = albumDao.findAlbumByArtistId(EXISTENT_ARTIST_ID);
    	assertNotNull(albums);
    	assertEquals(EXISTENT_ARTIST_ALBUM_COUNT, albums.size());
    }
    
    @Override
    protected Class<?>[] getConfigurations() {
	return new Class[] { TestDatabaseConfiguration.class };
    }

}
