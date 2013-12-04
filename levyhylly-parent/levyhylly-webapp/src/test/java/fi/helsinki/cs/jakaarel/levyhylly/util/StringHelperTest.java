package fi.helsinki.cs.jakaarel.levyhylly.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

/**
 * Tests for {@link StringHelper}.
 * 
 * @author jakaarl
 */
public class StringHelperTest {

	@Test
	public void escapeLikeWildcardsShouldReturnAsIs() {
		String original = "this-does*notIncludeWild/cards";
		String escaped = StringHelper.escapeLikeWildcards(original);
		assertEquals(original, escaped);
	}

	@Test
	public void escapeLikeWildcardsShouldEscapePercentageSign() {
		String original = "the%shouldBeEscaped";
		String expected = "the\\%shouldBeEscaped";
		String escaped = StringHelper.escapeLikeWildcards(original);
		assertEquals(expected, escaped);
	}

	@Test
	public void escapeLikeWildcardsShouldEscapeUnderscore() {
		String original = "under_score_ShouldBeEscaped";
		String expected = "under\\_score\\_ShouldBeEscaped";
		String escaped = StringHelper.escapeLikeWildcards(original);
		assertEquals(expected, escaped);
	}

	@Test
	public void escapeLikeWildcardsShouldEscapeBoth() {
		String original = "both%and_ShouldBeEscaped";
		String expected = "both\\%and\\_ShouldBeEscaped";
		String escaped = StringHelper.escapeLikeWildcards(original);
		assertEquals(expected, escaped);
	}
	
	@Test
	public void nullSafeParseLongShouldReturnNull() {
		String original = null;
		assertNull(StringHelper.nullSafeParseLong(original));
	}
	
	@Test
	public void nullSafeParseLongShouldParseOk() {
		Long expected = Long.valueOf(1L);
		assertEquals(expected, StringHelper.nullSafeParseLong(String.valueOf(expected)));
	}
	
	@Test(expected = NumberFormatException.class)
	public void nullSafeParseLongShouldThrowNFE() {
		String invalid = "definitelyNotALong";
		StringHelper.nullSafeParseLong(invalid);
	}
	
	@Test
	public void nullSafeParseShortShouldReturnNull() {
		String original = null;
		assertNull(StringHelper.nullSafeParseShort(original));
	}
	
	@Test
	public void nullSafeParseShortShouldParseOk() {
		Short expected = Short.valueOf((short) 1);
		assertEquals(expected, StringHelper.nullSafeParseShort(String.valueOf(expected)));
	}
	
	@Test(expected = NumberFormatException.class)
	public void nullSafeParseShortShouldThrowNFE() {
		String invalid = "definitelyNotAShort";
		StringHelper.nullSafeParseShort(invalid);
	}

}
