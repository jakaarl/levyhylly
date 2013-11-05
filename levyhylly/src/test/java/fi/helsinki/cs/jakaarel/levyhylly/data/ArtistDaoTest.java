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

import fi.helsinki.cs.jakaarel.levyhylly.data.Artist;
import fi.helsinki.cs.jakaarel.levyhylly.data.ArtistDao;

/**
 * 
 * @author Jani Kaarela
 * 
 */
public class ArtistDaoTest extends DatabaseTestCase {
	
	private static final String SQL_DATA_SCRIPT = "artist-data.sql";
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
		DataSource dataSource = context.getBean(DATASOURCE_BEAN_NAME,
				DataSource.class);
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		populator.addScript(new ClassPathResource(SQL_DATA_SCRIPT));
		DatabasePopulatorUtils.execute(populator, dataSource);
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
	public void findArtistsByNameShouldReturnEmpty() {
		List<Artist> artists = artistDao
				.findArtistsByName(NON_EXISTENT_ARTIST_NAME);
		assertNotNull(artists);
		assertTrue(artists.isEmpty());
	}
	
	@Test
	public void findArtistsByNameShouldReturnResults() {
		List<Artist> artists = artistDao
				.findArtistsByName(EXISTENT_ARTIST_NAME);
		assertNotNull(artists);
		assertEquals(1, artists.size());
	}
	
	@Test
	public void findArtistByNameLikeShouldReturnEmpty() {
		List<Artist> artists = artistDao
				.findArtistsByNameLike(NON_EXISTENT_ARTIST_LIKE_QUERY);
		assertNotNull(artists);
		assertTrue(artists.isEmpty());
	}
	
	@Test
	public void findArtistByNameLikeShouldReturnResults() {
		List<Artist> artists = artistDao
				.findArtistsByNameLike(EXISTENT_ARTIST_LIKE_QUERY);
		assertNotNull(artists);
		assertEquals(1, artists.size());
	}
	
	@Test
	public void findArtistByNameLikeShouldBeCaseInsensitive() {
		List<Artist> artists = artistDao
				.findArtistsByNameLike(EXISTENT_ARTIST_LIKE_QUERY_WRONG_CASE);
		assertNotNull(artists);
		assertEquals(1, artists.size());
	}
	
}
