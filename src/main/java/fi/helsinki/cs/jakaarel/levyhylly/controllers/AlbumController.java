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
 * 
 * @author Jani Kaarela
 *
 */
@Controller
public class AlbumController {
    
    public static final String ALBUM_KEY = "album";
    public static final String TRACKS_KEY = "tracks";
    public static final String ALBUM_ARTIST_ID_KEY = "artistId";
    public static final String ALBUM_ARTIST_NAME_KEY = "artistName";
    public static final String ALBUM_NAME_KEY = "name";
    static final String DETAILS_VIEW_NAME = "albumDetails";
    static final String EDIT_VIEW_NAME = "albumEditor";
    
    private @Autowired AlbumDao albumDao;
    private @Autowired ArtistDao artistDao;
    private @Autowired TrackDao trackDao;

    @RequestMapping(value = "/albumDetails", method = RequestMethod.GET)
    public ModelAndView handleAlbumDetails(@RequestParam Long albumId) {
	ModelAndView mav = new ModelAndView(DETAILS_VIEW_NAME);	
	Album album = albumDao.loadAlbum(albumId);
	Artist artist = artistDao.loadArtist(album.getArtistId());
	List<Track> tracks = trackDao.findTrackByAlbumId(albumId);
	mav.addObject(ALBUM_KEY, album);
	mav.addObject(ALBUM_ARTIST_NAME_KEY, artist.getName());
	mav.addObject(TRACKS_KEY, tracks);
	return mav;
    }
    
    @RequestMapping(value = "/createAlbum", method = RequestMethod.GET)
    public ModelAndView handleCreateAlbum(@RequestParam(required = false) Long artistId) {
	ModelAndView mav = new ModelAndView(EDIT_VIEW_NAME);
	if (artistId != null) {
	    Artist artist = artistDao.loadArtist(artistId);
	    mav.addObject(ALBUM_ARTIST_ID_KEY, artistId);
	    mav.addObject(ALBUM_ARTIST_NAME_KEY, artist.getName());
	}
	return mav;
    }
}