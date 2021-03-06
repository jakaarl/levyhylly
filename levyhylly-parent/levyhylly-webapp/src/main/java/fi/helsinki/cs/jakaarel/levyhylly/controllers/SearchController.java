package fi.helsinki.cs.jakaarel.levyhylly.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import fi.helsinki.cs.jakaarel.levyhylly.data.Album;
import fi.helsinki.cs.jakaarel.levyhylly.data.AlbumDao;
import fi.helsinki.cs.jakaarel.levyhylly.data.Artist;
import fi.helsinki.cs.jakaarel.levyhylly.data.ArtistDao;
import fi.helsinki.cs.jakaarel.levyhylly.data.Track;
import fi.helsinki.cs.jakaarel.levyhylly.data.TrackDao;

/**
 * Controller for performing artist and album searches.
 * 
 * @author Jani Kaarela
 */
@Controller
public class SearchController {

	public static final String ARTIST_RESULTS_KEY = "resultArtists";
	public static final String ALBUM_RESULTS_KEY = "resultAlbums";
	public static final String TRACK_RESULTS_KEY = "resultTracks";
	private static final String ARTIST_RESULTS_VIEW = "artistResults";
	private static final String ALBUM_RESULTS_VIEW = "albumResults";
	private static final String TRACK_RESULTS_VIEW = "trackResults";
	private static final String BY_ARTIST_BUTTON = "byArtist";
	private static final String BY_ALBUM_BUTTON = "byAlbum";
	private static final String BY_TRACK_BUTTON = "byTrack";
	private static final String SEARCH_TERM_KEY = "searchTerm";
	private static final String ARTIST_ID_KEY = "artistId";
	private static final String ARTIST_NAME_KEY = "artistName";

	private @Autowired
	AlbumDao albumDao;
	private @Autowired
	ArtistDao artistDao;
	private @Autowired
	TrackDao trackDao;

	/**
	 * Handles search requests. Delegates to {@link #handleSearchAlbums(String)} or
	 * {@link #handleSearchArtists(String)}, depending on the search button clicked.
	 * 
	 * @param searchTerm	search term.
	 * @param submitButton	submit button, determines if searching by artist or album.
	 * 
	 * @return	a view displaying search results.
	 */
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ModelAndView handleSearch(@RequestParam String searchTerm, @RequestParam String submitButton) {
		ModelAndView mav = null;
		if (BY_ARTIST_BUTTON.equals(submitButton)) {
			mav = handleSearchArtists(searchTerm);
		} else if (BY_ALBUM_BUTTON.equals(submitButton)) {
			mav = handleSearchAlbums(searchTerm);
		} else if (BY_TRACK_BUTTON.equals(submitButton)) {
			mav = handleSearchTracks(searchTerm);
		}
		return mav;
	}

	/**
	 * Searches artists.
	 *  
	 * @param artist	artist name or part of it.
	 * 
	 * @return	a view displaying results.
	 */
	ModelAndView handleSearchArtists(String artist) {
		ModelAndView mav = new ModelAndView(ARTIST_RESULTS_VIEW);
		mav.addObject(SEARCH_TERM_KEY, artist);
		List<Artist> results = artistDao.findArtistsByNameLike(artist);
		if (!results.isEmpty()) {
			mav.addObject(ARTIST_RESULTS_KEY, results);
		}
		return mav;
	}

	/**
	 * Searches albums.
	 * 
	 * @param album	album name or part of it.
	 * 
	 * @return	a view displaying search results.
	 */
	ModelAndView handleSearchAlbums(String album) {
		ModelAndView mav = new ModelAndView(ALBUM_RESULTS_VIEW);
		mav.addObject(SEARCH_TERM_KEY, album);
		List<Album> results = albumDao.findAlbumByNameLike(album);
		if (!results.isEmpty()) {
			mav.addObject(ALBUM_RESULTS_KEY, results);
		}
		return mav;
	}
	
	/**
	 * Searches tracks.
	 * 
	 * @param track	track name or part of it.
	 * 
	 * @return	a view displaying search results.
	 */
	ModelAndView handleSearchTracks(String track) {
		ModelAndView mav = new ModelAndView(TRACK_RESULTS_VIEW);
		mav.addObject(SEARCH_TERM_KEY, track);
		List<Track> results = trackDao.findTrackByNameLike(track);
		if (!results.isEmpty()) {
			mav.addObject(TRACK_RESULTS_KEY, results);
		}
		return mav;
	}

	/**
	 * Displays artist albums.
	 * 
	 * @param artistId	artist identifier.
	 * 
	 * @return	a view displaying all albums for the artist.
	 */
	@RequestMapping(value = "/artistAlbums", method = RequestMethod.GET)
	public ModelAndView handleArtistAlbums(@RequestParam Long artistId) {
		ModelAndView mav = new ModelAndView(ALBUM_RESULTS_VIEW);
		Artist artist = artistDao.loadArtist(artistId);
		mav.addObject(ARTIST_ID_KEY, artist.getId());
		mav.addObject(ARTIST_NAME_KEY, artist.getName());
		List<Album> results = albumDao.findAlbumByArtistId(artistId);
		if (!results.isEmpty()) {
			mav.addObject(ALBUM_RESULTS_KEY, results);
		}
		return mav;
	}

}
