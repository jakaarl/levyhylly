package fi.helsinki.cs.jakaarel.levyhylly.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static fi.helsinki.cs.jakaarel.levyhylly.TestDatabaseConfiguration.DATASOURCE_BEAN_NAME;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

/**
 * 
 * @author jakaarl
 */
public class TrackDaoTest extends DatabaseTestCase {
	
	private static final String TRACK_DATA_SCRIPT = "track-data.sql";
	private static final Long NON_EXISTENT_TRACK_ID = Long.MAX_VALUE;
	private static final Long EXISTENT_TRACK_ID = Long.valueOf(1);
	private static final Short EXISTENT_TRACK_NUMBER = Short.valueOf((short) 1); 
	private static final String EXISTENT_TRACK_NAME = "Haavemaa (Liberta)";
	private static final Long NON_EXISTENT_ALBUM_ID = Long.MAX_VALUE;
    private static final Long EXISTENT_ALBUM_ID = Long.valueOf(1);
    private static final int EXISTENT_ALBUM_TRACK_COUNT = 13;

    private TrackDao trackDao;
    
    @Before
    public void setUp() {
    	DataSource dataSource = context.getBean(DATASOURCE_BEAN_NAME, DataSource.class);
    	ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
    	populator.addScript(new ClassPathResource(TRACK_DATA_SCRIPT));
    	DatabasePopulatorUtils.execute(populator, dataSource);
    	trackDao = new TrackDao(dataSource);
    }
    
    @Test(expected = IncorrectResultSizeDataAccessException.class)
    public void testLoadTrackNonExistent() {
    	trackDao.loadTrack(NON_EXISTENT_TRACK_ID);
    }
    
    @Test
    public void testLoadTrackExistent() {
    	Track track = trackDao.loadTrack(EXISTENT_TRACK_ID);
    	assertNotNull(track);
    	assertEquals(EXISTENT_TRACK_NUMBER, track.getNumber());
    	assertEquals(EXISTENT_TRACK_NAME, track.getName());
    }
	

}
