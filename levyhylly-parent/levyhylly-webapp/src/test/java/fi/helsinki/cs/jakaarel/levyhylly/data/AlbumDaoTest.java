package fi.helsinki.cs.jakaarel.levyhylly.data;

import static fi.helsinki.cs.jakaarel.levyhylly.DatabaseConfiguration.ALBUM_DATA_FILE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;

/**
 * Tests for {@link AlbumDao}.
 * 
 * @author Jani Kaarela
 */
public class AlbumDaoTest extends DatabaseTestCase {

	private static final Long NON_EXISTENT_ALBUM_ID = Long.MAX_VALUE;
	private static final Long EXISTENT_ALBUM_ID = Long.valueOf(1);
	private static final String EXISTENT_ALBUM_NAME = "Charlotta";
	private static final String NON_EXISTENT_ALBUM_NAME = "Tauskin paluu";
	private static final Short NON_EXISTENT_ALBUM_YEAR = new Short((short)2014);
	private static final Long NON_EXISTENT_ARTIST_ID = Long.MAX_VALUE;
	private static final Long EXISTENT_ARTIST_ID = Long.valueOf(1);
	private static final int EXISTENT_ARTIST_ALBUM_COUNT = 10;

	private AlbumDao albumDao;

	@Before
	public void setUp() {
		super.setUp();
		albumDao = new AlbumDao(dataSource);
	}

	@Test(expected = IncorrectResultSizeDataAccessException.class)
	public void loadAlbumShouldFailForNonExistent() {
		albumDao.loadAlbum(NON_EXISTENT_ALBUM_ID);
	}

	@Test
	public void shouldLoadAlbum() {
		Album album = albumDao.loadAlbum(EXISTENT_ALBUM_ID);
		assertNotNull(album);
		assertEquals(EXISTENT_ALBUM_NAME, album.getName());
	}
	
	@Test
	public void shouldCreateAlbum() {
		String albumName = NON_EXISTENT_ALBUM_NAME;
		Short albumYear = NON_EXISTENT_ALBUM_YEAR;
		Album album = albumDao.createAlbum(albumName, albumYear, EXISTENT_ARTIST_ID); // moar Tauski! <3
		assertNotNull(album);
		assertNotNull(album.getId());
		assertEquals(NON_EXISTENT_ALBUM_NAME, album.getName());
		assertEquals(NON_EXISTENT_ALBUM_YEAR, album.getYear());
		assertEquals(EXISTENT_ARTIST_ID, album.getArtistId());
	}
	
	@Test(expected = DuplicateKeyException.class)
	public void shouldFailToCreateAlbumWithConflictingNameAndArtist() {
		albumDao.createAlbum(EXISTENT_ALBUM_NAME, NON_EXISTENT_ALBUM_YEAR, EXISTENT_ARTIST_ID);
	}
	
	@Test(expected = DataIntegrityViolationException.class)
	public void shouldFailToCreateAlbumWithNonExistentArtist() {
		albumDao.createAlbum(NON_EXISTENT_ALBUM_NAME, NON_EXISTENT_ALBUM_YEAR, NON_EXISTENT_ARTIST_ID);
	}

	@Test
	public void shouldUpdateAlbum() {
		String newName = NON_EXISTENT_ALBUM_NAME;
		Short newYear = NON_EXISTENT_ALBUM_YEAR;
		Album albumWithUpdates = new Album(EXISTENT_ALBUM_ID, newName, newYear, EXISTENT_ARTIST_ID);
		albumDao.updateAlbum(albumWithUpdates);
		Album loadedAlbum = albumDao.loadAlbum(EXISTENT_ALBUM_ID);
		assertEquals(newName, loadedAlbum.getName());
		assertEquals(newYear, loadedAlbum.getYear());
	}
	
	@Test
	public void findAlbumByArtistIdShouldReturnEmpty() {
		List<Album> albums = albumDao.findAlbumByArtistId(NON_EXISTENT_ARTIST_ID);
		assertNotNull(albums);
		assertTrue(albums.isEmpty());
	}

	@Test
	public void findAlbumByArtistIdShouldReturnResults() {
		List<Album> albums = albumDao.findAlbumByArtistId(EXISTENT_ARTIST_ID);
		assertNotNull(albums);
		assertEquals(EXISTENT_ARTIST_ALBUM_COUNT, albums.size());
	}

	@Override
	protected String getDataScriptPath() {
		return ALBUM_DATA_FILE;
	}

}
