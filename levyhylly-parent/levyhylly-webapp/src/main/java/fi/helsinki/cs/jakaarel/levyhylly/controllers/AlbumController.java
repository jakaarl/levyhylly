package fi.helsinki.cs.jakaarel.levyhylly.controllers;

import static fi.helsinki.cs.jakaarel.levyhylly.util.StringHelper.nullSafeParseLong;
import static fi.helsinki.cs.jakaarel.levyhylly.util.StringHelper.nullSafeParseShort;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import fi.helsinki.cs.jakaarel.levyhylly.data.Album;
import fi.helsinki.cs.jakaarel.levyhylly.data.AlbumDao;
import fi.helsinki.cs.jakaarel.levyhylly.data.Artist;
import fi.helsinki.cs.jakaarel.levyhylly.data.ArtistDao;
import fi.helsinki.cs.jakaarel.levyhylly.data.Track;
import fi.helsinki.cs.jakaarel.levyhylly.data.TrackDao;

/**
 * Controller for all things album-related.
 * 
 * @author Jani Kaarela
 */
@Controller
public class AlbumController {

	/*
	 * TODO: move all the REST methods to a separate controller for clarity? Or RESTify completely?
	 */
	/** Model key for album. */
	public static final String ALBUM_KEY = "album";
	/** Model key for tracks. */
	public static final String TRACKS_KEY = "tracks";
	/** Model key for album identifier. */
	public static final String ALBUM_ID_KEY = "albumId";
	/** Model key for artist identifier. */
	public static final String ALBUM_ARTIST_ID_KEY = "artistId";
	/** Model key for artist name. */
	public static final String ALBUM_ARTIST_NAME_KEY = "artistName";
	/** Model key for album name. */
	public static final String ALBUM_NAME_KEY = "name";
	/** Model key for album year. */
	public static final String ALBUM_YEAR_KEY = "year";
	/** View name for album details. */
	public static final String DETAILS_VIEW_NAME = "albumDetails";
	/** View name for album editor. */
	public static final String EDIT_VIEW_NAME = "albumEditor";
	/** Model key for error messages. */
	public static final String FORM_ERRORS_KEY = "formErrors";

	private @Autowired
	AlbumDao albumDao;
	private @Autowired
	ArtistDao artistDao;
	private @Autowired
	TrackDao trackDao;

	/**
	 * Loads album details and displays them.
	 * 
	 * @param albumId	album identifier.
	 * 
	 * @return	a <code>ModelAndView</code> of album details.
	 */
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

	/**
	 * Displays album editor for a newly created album.
	 * 
	 * @param artistId	artist identifier if existing artist, <code>null</code> if not.
	 * 
	 * @return	a <code>ModelAndView</code> for album creation.
	 */
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

	/**
	 * Displays album editor for an existing album.
	 * 
	 * @param albumId	album identifier of existing album.
	 * 
	 * @return	a <code>ModelAndView</code> for album editing.
	 */
	@RequestMapping(value = "/editAlbum", method = RequestMethod.GET)
	public ModelAndView handleEditAlbum(@RequestParam(required = true) Long albumId) {
		Album album = albumDao.loadAlbum(albumId);
		Long artistId = album.getArtistId();
		ModelAndView mav = handleCreateAlbum(artistId);
		mav.addObject(ALBUM_KEY, album);
		return mav;
	}

	/**
	 * Saves an edited or newly created album.
	 * 
	 * @param formParams	form containing the album details, including tracks.
	 * 
	 * @return	a <code>ModelAndView</code> displaying the edited/created album.
	 */
	@RequestMapping(value = "/saveAlbum", method = RequestMethod.POST)
	public ModelAndView handleSaveAlbum(@ModelAttribute @Valid AlbumDetails details) {
		Long albumId = details.albumId;
		Long artistId = details.artistId;
		String albumName = details.name;
		if (albumName == null || albumName.isEmpty()) {
			ModelAndView mav = handleCreateAlbum(artistId);
			mav.addObject("", "");
		}
		Short albumYear = details.year;
		if (albumId == null) { // new album
			if (artistId == null) {
				String albumArtist = details.artistName;
				Artist artist = artistDao.createArtist(albumArtist);
				artistId = artist.getId();
			}
			Album album = albumDao.createAlbum(albumName, albumYear, artistId);
			albumId = album.getId();
		} else {
			// TODO: save name and year
		}
		return handleEditAlbum(albumId);
	}
	
	/**
	 * Fetches track listing for given album.
	 * 
	 * @param albumId	album identifier.
	 * 
	 * @return	album tracks, rendered as JSON.
	 */
	@RequestMapping(value="/albums/{albumId}/tracks", method = RequestMethod.GET)
	public @ResponseBody List<Track> loadTracks(@PathVariable Long albumId) {
		List<Track> tracks = trackDao.findTrackByAlbumId(albumId);
		return tracks;
	}
	
	/**
	 * Adds a new track to an album. If the added track has a track number, it will replace
	 * that track, otherwise it is added after the existing tracks.
	 * 
	 * @param albumId	album identifier.
	 * @param track		track to add.
	 * 
	 * @return	newly created track, rendered as JSON.
	 */
	@RequestMapping(value="/albums/{albumId}/tracks", method = RequestMethod.POST)
	public @ResponseBody Track addTrack(@PathVariable Long albumId, @RequestBody @Valid AddedTrack track) {
		List<Track> albumTracks = trackDao.findTrackByAlbumId(albumId);
		if (track.number != null) {
			if (albumTracks.size() >= track.number) {
				Track trackToReplace = albumTracks.get(track.number - 1);
				trackDao.deleteTrack(trackToReplace);
			} else {
				throw new InvalidIdentifierException("No track number " + track.number + " on album: " + albumId);
			}
		} else {
			track.number = new Short((short) (albumTracks.size() + 1));
		}
		return trackDao.createTrack(albumId, track.number, track.name, track.length);
	}
	
	/**
	 * Removes track.
	 * 
	 * @param albumId	album identifier.
	 * @param number	track number.
	 * 
	 * @return	the removed track.
	 */
	@RequestMapping(value = "/albums/{albumId}/tracks/{number}", method = RequestMethod.DELETE)
	public @ResponseBody Track removeTrack(@PathVariable Long albumId, @PathVariable Short number) {
		List<Track> albumTracks = trackDao.findTrackByAlbumId(albumId);
		Track trackToRemove = null;
		if (albumTracks.size() >= number) {
			trackToRemove = albumTracks.get(number - 1);
			trackDao.deleteTrack(trackToRemove);
		} else {
			throw new InvalidIdentifierException("No track number " + number + " on album: " + albumId);
		}
		return trackToRemove;
	}
	
	/**
	 * Data transfer object for creating and editing albums.
	 * 
	 * @author Jani Kaarela (@gmail.com)
	 */
	public static class AlbumDetails {
		public Long albumId;
		@NotNull @Size(min=1, max=128)
		public String name;
		@Min(1877) @Max(2020)
		public Short year;
		public Long artistId;
		@Size(max=128)
		public String artistName;
	}
	
	/**
	 * Simple data transfer object for adding tracks.
	 * 
	 * @author Jani Kaarela (@gmail.com)
	 */
	public static class AddedTrack {
		public Long albumId;
		@NotNull @Min(1) @Max(99)
		public Short number;
		@NotNull @Size(min=1, max=128)
		public String name;
		@Max(4800)
		public Short length;
	}
	
	/**
	 * An exception indicating an invalid object identifier. Maps to HTTP status 404.
	 * 
	 * @author Jani Kaarela (@gmail.com)
	 */
	@ResponseStatus(HttpStatus.NOT_FOUND)
	private static class InvalidIdentifierException extends IllegalArgumentException {
		private static final long serialVersionUID = 1L;
		private InvalidIdentifierException(Class<?> objectClass, Object identifier) {
			this("No " + objectClass.getName() + " found by identifier: " + String.valueOf(identifier));
		}
		private InvalidIdentifierException(String message) {
			super(message);
		}
	}
}
