package fi.helsinki.cs.jakaarel.levyhylly.controllers;

import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import fi.helsinki.cs.jakaarel.levyhylly.data.Album;
import fi.helsinki.cs.jakaarel.levyhylly.data.AlbumDao;
import fi.helsinki.cs.jakaarel.levyhylly.data.Artist;
import fi.helsinki.cs.jakaarel.levyhylly.data.ArtistDao;

/**
 * Controller for all things album-related.
 * 
 * @author Jani Kaarela
 */
@Controller
public class AlbumController {

	/** Model key for album. */
	public static final String ALBUM_DETAILS_KEY = "albumDetails";
	
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
		mav.addObject(ALBUM_DETAILS_KEY, new AlbumDetails(album, artist));
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
		AlbumDetails albumDetails = new AlbumDetails();
		mav.addObject(ALBUM_DETAILS_KEY, albumDetails);
		if (artistId != null) {
			Artist artist = artistDao.loadArtist(artistId);
			albumDetails.setArtistId(artist.getId());
			albumDetails.setArtistName(artist.getName());
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
		Artist artist = artistDao.loadArtist(album.getArtistId());
		ModelAndView mav = new ModelAndView(EDIT_VIEW_NAME);
		AlbumDetails albumDetails = new AlbumDetails(album, artist);
		mav.addObject(ALBUM_DETAILS_KEY, albumDetails);
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
	public ModelAndView handleSaveAlbum(@ModelAttribute @Valid AlbumDetails details, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			ModelAndView mav = new ModelAndView(EDIT_VIEW_NAME);
			Map<String,Object> model = bindingResult.getModel();
			mav.addAllObjects(model);
			mav.addObject(ALBUM_DETAILS_KEY, details);
			return mav;
		}
		Long albumId = details.albumId;
		Long artistId = details.artistId;
		String albumName = details.name;
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
			Album albumToUpdate = new Album(albumId, albumName, albumYear, artistId);
			albumDao.updateAlbum(albumToUpdate);
		}
		return handleEditAlbum(albumId);
	}
	
	/**
	 * Data transfer object for creating and editing albums.
	 * 
	 * @author Jani Kaarela (@gmail.com)
	 */
	public static class AlbumDetails {
		private Long albumId;
		@NotNull @Size(min=1, max=128)
		private String name;
		@Min(1877) @Max(2020)
		private Short year;
		private Long artistId;
		@NotNull @Size(min=1, max=128)
		private String artistName;
		
		public AlbumDetails() {
			// create blank instance
		}
		
		private AlbumDetails(Album album, Artist artist) {
			this.albumId = album.getId();
			this.name = album.getName();
			this.year = album.getYear();
			this.artistId = artist.getId();
			this.artistName = artist.getName();
		}
		
		public Long getAlbumId() {
			return albumId;
		}
		
		public void setAlbumId(Long albumId) {
			this.albumId = albumId;
		}
		
		public String getName() {
			return name;
		}
		
		public void setName(String name) {
			this.name = name;
		}
		
		public Short getYear() {
			return year;
		}
		
		public void setYear(Short year) {
			this.year = year;
		}
		
		public Long getArtistId() {
			return artistId;
		}
		
		public void setArtistId(Long artistId) {
			this.artistId = artistId;
		}
		
		public String getArtistName() {
			return artistName;
		}
		
		public void setArtistName(String artistName) {
			this.artistName = artistName;
		}
	}
}
