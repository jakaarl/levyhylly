package fi.helsinki.cs.jakaarel.levyhylly.data;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * @author Jani Kaarela (@gmail.com)
 */
public class TrackTest {
	
	private static final Long TRACK_ID = Long.valueOf(1l);
	private static final Short TRACK_NUMBER = Short.valueOf((short) 1);
	private static final String TRACK_NAME = "Vain mäkimies voi tietää sen";
	private static final Long ALBUM_ID = Long.valueOf(2l);

	@Test
	public void shouldCountHoursCorrectly() {
		int twoHoursInSeconds = 2 * 60 * 60;
		Track track = createTrackWithLength(twoHoursInSeconds);
		String expected = "02:00:00";
		assertEquals(expected, track.getFormattedLength());
	}
	
	@Test
	public void shouldCountMinutesCorrectly() {
		int elevenMinutesInSeconds = 11 * 60;
		Track track = createTrackWithLength(elevenMinutesInSeconds);
		String expected = "11:00";
		assertEquals(expected, track.getFormattedLength());
	}
	
	@Test
	public void shouldFormatHoursMinutesSeconds() {
		int oneHourOneMinuteOneSecond = 61 * 60 + 1;
		Track track = createTrackWithLength(oneHourOneMinuteOneSecond);
		String expected = "01:01:01";
		assertEquals(expected, track.getFormattedLength());
	}
	
	private Track createTrackWithLength(int lengthInSeconds) {
		return new Track(TRACK_ID, TRACK_NUMBER, TRACK_NAME, new Short((short) lengthInSeconds), ALBUM_ID);
	}
}
