package fi.helsinki.cs.jakaarel.levyhylly.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * @author Jani Kaarela
 *
 */
@Controller
@RequestMapping("/search")
public class SearchController {
    
    static final String ARTIST_RESULTS_VIEW = "artistResults";
    static final String ALBUM_RESULTS_VIEW = "albumResults";
    private static final String BY_ARTIST_BUTTON = "byArtist";
    private static final String BY_ALBUM_BUTTON = "byAlbum";
    
    @RequestMapping(method = RequestMethod.POST)
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
	return mav;
    }
    
    ModelAndView handleSearchAlbums(String album) {
	ModelAndView mav = new ModelAndView(ALBUM_RESULTS_VIEW);
	return mav;
    }

}
