package fi.helsinki.cs.jakaarel.levyhylly.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
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

}
