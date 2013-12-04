package fi.helsinki.cs.jakaarel.levyhylly.data;

import static fi.helsinki.cs.jakaarel.levyhylly.DatabaseConfiguration.ARTIST_DATA_FILE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;

/**
 * 
 * @author Jani Kaarela
 */
public class ArtistDaoTest extends DatabaseTestCase {

	private static final Long NON_EXISTENT_ARTIST_ID = Long.MAX_VALUE;
	private static final String NON_EXISTENT_ARTIST_NAME = "Janttu ja Himmeet Tähdet";
	private static final Long EXISTENT_ARTIST_ID = Long.valueOf(1);
	private static final String EXISTENT_ARTIST_NAME = "Tauski";
	private static final String NON_EXISTENT_ARTIST_LIKE_QUERY = "Janttu";
	private static final String EXISTENT_ARTIST_LIKE_QUERY = "aus";
	private static final String EXISTENT_ARTIST_LIKE_QUERY_WRONG_CASE = "SKI";

	private ArtistDao artistDao;

	@Before
	public void setUp() {
		super.setUp();
		artistDao = new ArtistDao(dataSource);
	}

	@Test(expected = IncorrectResultSizeDataAccessException.class)
	public void loadArtistShouldFailForNonExistent() {
		artistDao.loadArtist(NON_EXISTENT_ARTIST_ID);
	}

	@Test
	public void shouldLoadArtist() {
		Artist artist = artistDao.loadArtist(EXISTENT_ARTIST_ID);
		assertNotNull(artist);
		assertEquals(EXISTENT_ARTIST_NAME, artist.getName());
	}
	
	@Test
	public void shouldCreateArtist() {
		String artistName = NON_EXISTENT_ARTIST_NAME;
		Artist artist = artistDao.createArtist(artistName);
		assertNotNull(artist);
		assertNotNull(artist.getId());
		assertEquals(NON_EXISTENT_ARTIST_NAME, artist.getName());
	}
	
	@Test(expected = DuplicateKeyException.class)
	public void shouldFailToCreateArtistWithConflictingName() {
		artistDao.createArtist(EXISTENT_ARTIST_NAME);
	}

	@Test
	public void findArtistsByNameShouldReturnEmpty() {
		List<Artist> artists = artistDao.findArtistsByName(NON_EXISTENT_ARTIST_NAME);
		assertNotNull(artists);
		assertTrue(artists.isEmpty());
	}

	@Test
	public void findArtistsByNameShouldReturnResults() {
		List<Artist> artists = artistDao.findArtistsByName(EXISTENT_ARTIST_NAME);
		assertNotNull(artists);
		assertEquals(1, artists.size());
	}

	@Test
	public void findArtistByNameLikeShouldReturnEmpty() {
		List<Artist> artists = artistDao.findArtistsByNameLike(NON_EXISTENT_ARTIST_LIKE_QUERY);
		assertNotNull(artists);
		assertTrue(artists.isEmpty());
	}

	@Test
	public void findArtistByNameLikeShouldReturnResults() {
		List<Artist> artists = artistDao.findArtistsByNameLike(EXISTENT_ARTIST_LIKE_QUERY);
		assertNotNull(artists);
		assertEquals(1, artists.size());
	}

	@Test
	public void findArtistByNameLikeShouldBeCaseInsensitive() {
		List<Artist> artists = artistDao.findArtistsByNameLike(EXISTENT_ARTIST_LIKE_QUERY_WRONG_CASE);
		assertNotNull(artists);
		assertEquals(1, artists.size());
	}

	@Override
	protected String getDataScriptPath() {
		return ARTIST_DATA_FILE;
	}

}
