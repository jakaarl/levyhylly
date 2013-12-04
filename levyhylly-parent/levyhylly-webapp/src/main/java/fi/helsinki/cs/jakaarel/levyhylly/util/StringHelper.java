package fi.helsinki.cs.jakaarel.levyhylly.util;

/**
 * Utility methods for handling <code>String</code>s.
 * 
 * @author jakaarl
 */
public class StringHelper {

	/**
	 * Escapes SQL LIKE wildcards.
	 * 
	 * @param param
	 *            parameter to escape.
	 * 
	 * @return parameter with wildcards escaped.
	 */
	public static String escapeLikeWildcards(String param) {
		return param.replace("%", "\\%").replace("_", "\\_");
	}
	
	/**
	 * Parses a <code>Long</code> from given string, unless it is <code>null</code> or empty.
	 * 
	 * @param string	string to parse, possibly <code>null</code>.
	 * 
	 * @return	<code>null</code> or the parsed <code>Long</code>.
	 */
	public static Long nullSafeParseLong(String string) {
		return ((string == null || string.isEmpty()) ? null : Long.valueOf(string));
	}
	
	/**
	 * Parses a <code>Short</code> from given string, unless it is <code>null</code> or empty.
	 * 
	 * @param string	string to parse, possibly <code>null</code>.
	 * 
	 * @return	<code>null</code> or the parsed <code>Short</code>.
	 */
	public static Short nullSafeParseShort(String string) {
		return ((string == null || string.isEmpty()) ? null : Short.valueOf(string));
	}

}
