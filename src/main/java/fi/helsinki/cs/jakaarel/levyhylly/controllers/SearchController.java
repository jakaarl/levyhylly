package fi.helsinki.cs.jakaarel.levyhylly.controllers;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import fi.helsinki.cs.jakaarel.levyhylly.data.Album;
import fi.helsinki.cs.jakaarel.levyhylly.data.Artist;

/**
 * 
 * @author Jani Kaarela
 *
 */
@Controller
public class SearchController {
    
    public static final String ARTIST_RESULTS_KEY = "resultArtists";
    public static final String ALBUM_RESULTS_KEY = "resultAlbums";
    static final String ARTIST_RESULTS_VIEW = "artistResults";
    static final String ALBUM_RESULTS_VIEW = "albumResults";
    private static final String BY_ARTIST_BUTTON = "byArtist";
    private static final String BY_ALBUM_BUTTON = "byAlbum";
    private static final Artist SAMPLE_ARTIST = new Artist(Long.valueOf(1L), "Example Artist");
    private static final Album SAMPLE_ALBUM =
	    new Album(Long.valueOf(1), "Example Album", Integer.valueOf(2012).shortValue(), Long.valueOf(1L));
    
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ModelAndView handleSearch(@RequestParam String searchTerm, @RequestParam String submitButton) {
	ModelAndView mav = null;
	if (BY_ARTIST_BUTTON.equals(submitButton)) {
	    mav = handleSearchArtists(searchTerm);
	} else if (BY_ALBUM_BUTTON.equals(submitButton)) {
	    mav = handleSearchAlbums(searchTerm);
	}
	return mav;
    }
    
    ModelAndView handleSearchArtists(String artist) {
	ModelAndView mav = new ModelAndView(ARTIST_RESULTS_VIEW);
	List<Artist> results = Collections.singletonList(SAMPLE_ARTIST);
	mav.addObject(ARTIST_RESULTS_KEY, results);
	return mav;
    }
    
    ModelAndView handleSearchAlbums(String album) {
	ModelAndView mav = new ModelAndView(ALBUM_RESULTS_VIEW);
	List<Album> results = Collections.singletonList(SAMPLE_ALBUM);
	mav.addObject(ALBUM_RESULTS_KEY, results);
	return mav;
    }
    
    @RequestMapping(value = "/artistAlbums", method = RequestMethod.GET)
    public ModelAndView handleArtistAlbums(@RequestParam Long artistId) {
	ModelAndView mav = new ModelAndView(ALBUM_RESULTS_VIEW);
	List<Album> results = Collections.singletonList(SAMPLE_ALBUM);
	mav.addObject(ALBUM_RESULTS_KEY, results);
	return mav;
    }

}
