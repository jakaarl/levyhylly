package fi.helsinki.cs.jakaarel.levyhylly.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import fi.helsinki.cs.jakaarel.levyhylly.data.Track;
import fi.helsinki.cs.jakaarel.levyhylly.data.TrackDao;

/**
 * A REST controller for manipulating {@link Track}s.
 * 
 * @author jakaarl
 */
@Controller
public class TrackController {
	
	private @Autowired
	TrackDao trackDao;

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
	 * Simple data transfer object for adding tracks.
	 * 
	 * @author Jani Kaarela (@gmail.com)
	 */
	public static class AddedTrack {
		public Long albumId;
		@Min(1) @Max(99)
		public Short number;
		@NotNull @Size(min=1, max=128)
		public String name;
		@Max(4800)
		public Short length;
	}
	
	/**
	 * Exception handler for validation errors.
	 * 
	 * @param validationException	validation exception to be handled.
	 * 
	 * @return	list of validation error messages.
	 */
	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	private @ResponseBody List<String> validationError(ConstraintViolationException validationException) {
		List<String> errors = new ArrayList<String>();
		for (ConstraintViolation<?> violation : validationException.getConstraintViolations()) {
			errors.add(violation.getMessage());
		}
		return errors;
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
