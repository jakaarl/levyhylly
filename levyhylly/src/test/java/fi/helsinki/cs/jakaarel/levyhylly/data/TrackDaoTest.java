package fi.helsinki.cs.jakaarel.levyhylly.data;

import static fi.helsinki.cs.jakaarel.levyhylly.DatabaseConfiguration.TRACK_DATA_FILE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import org.junit.Before;
import org.junit.Test;
import org.springframework.dao.IncorrectResultSizeDataAccessException;

/**
 * 
 * @author jakaarl
 */
public class TrackDaoTest extends DatabaseTestCase {

	private static final Long NON_EXISTENT_TRACK_ID = Long.MAX_VALUE;
	private static final Long EXISTENT_TRACK_ID = Long.valueOf(1);
	private static final Short EXISTENT_TRACK_NUMBER = Short.valueOf((short) 1);
	private static final String EXISTENT_TRACK_NAME = "Haavemaa (Liberta)";
	private static final Long EXISTENT_ALBUM_ID = Long.valueOf(1);
	private static final int EXISTENT_ALBUM_TRACK_COUNT = 13;

	private TrackDao trackDao;

	@Before
	public void setUp() {
		super.setUp();
		trackDao = new TrackDao(dataSource);
	}

	@Test(expected = IncorrectResultSizeDataAccessException.class)
	public void loadTrackShouldFailForNonExistent() {
		trackDao.loadTrack(NON_EXISTENT_TRACK_ID);
	}

	@Test
	public void shouldLoadTrack() {
		Track track = trackDao.loadTrack(EXISTENT_TRACK_ID);
		assertNotNull(track);
		assertEquals(EXISTENT_TRACK_NUMBER, track.getNumber());
		assertEquals(EXISTENT_TRACK_NAME, track.getName());
	}

	@Test
	public void shouldCreateTrack() {
		Short trackNumber = Short.valueOf((short) (EXISTENT_ALBUM_TRACK_COUNT + 1));
		String trackName = "Lallallaa foo bar";
		Track track = trackDao.createTrack(EXISTENT_ALBUM_ID, trackNumber, trackName, null);
		assertNotNull(track);
		assertEquals(trackNumber, track.getNumber());
		assertEquals(trackName, track.getName());
		assertNull(track.getLength());
	}
	
	@Test
	public void shouldDeleteTrack() {
		Track track = new Track(EXISTENT_TRACK_ID, EXISTENT_TRACK_NUMBER, EXISTENT_TRACK_NAME, null, null);
		Track deletedTrack = trackDao.deleteTrack(track);
		assertSame(deletedTrack, track);
	}
	
	@Test
	public void shouldNotDeleteTrack() {
		Track track = new Track(NON_EXISTENT_TRACK_ID, null, "Totally bogus", null, null);
		Track deletedTrack = trackDao.deleteTrack(track);
		assertNull(deletedTrack);
	}

	@Override
	protected String getDataScriptPath() {
		return TRACK_DATA_FILE;
	}

}
