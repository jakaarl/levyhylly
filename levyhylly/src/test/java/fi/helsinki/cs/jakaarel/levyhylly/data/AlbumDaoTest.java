package fi.helsinki.cs.jakaarel.levyhylly.data;

import static fi.helsinki.cs.jakaarel.levyhylly.DatabaseConfiguration.ALBUM_DATA_FILE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.dao.IncorrectResultSizeDataAccessException;

/**
 * 
 * @author Jani Kaarela
 * 
 */
public class AlbumDaoTest extends DatabaseTestCase {

	private static final Long NON_EXISTENT_ALBUM_ID = Long.MAX_VALUE;
	private static final Long EXISTENT_ALBUM_ID = Long.valueOf(1);
	private static final String EXISTENT_ALBUM_NAME = "Charlotta";
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
