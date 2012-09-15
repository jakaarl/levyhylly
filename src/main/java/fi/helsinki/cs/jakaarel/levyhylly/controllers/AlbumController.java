package fi.helsinki.cs.jakaarel.levyhylly.controllers;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import fi.helsinki.cs.jakaarel.levyhylly.data.Album;
import fi.helsinki.cs.jakaarel.levyhylly.data.Track;

/**
 * 
 * @author Jani Kaarela
 *
 */
@Controller
public class AlbumController {
    
    public static final String ALBUM_KEY = "album";
    public static final String TRACKS_KEY = "tracks";
    static final String DETAILS_VIEW_NAME = "albumDetails";

    @RequestMapping(value = "/albumDetails", method = RequestMethod.GET)
    public ModelAndView handleAlbumDetails(@RequestParam Long albumId) {
	ModelAndView mav = new ModelAndView(DETAILS_VIEW_NAME);
	Album album = new Album(Long.valueOf(1), "Example Album", Integer.valueOf(2012).shortValue(), Long.valueOf(1));
	Track track = new Track(Long.valueOf(1), Integer.valueOf(1).shortValue(), "Example Track", null, album.getId());
	List<Track> tracks = Collections.singletonList(track);
	mav.addObject(ALBUM_KEY, album);
	mav.addObject(TRACKS_KEY, tracks);
	return mav;
    }
}
