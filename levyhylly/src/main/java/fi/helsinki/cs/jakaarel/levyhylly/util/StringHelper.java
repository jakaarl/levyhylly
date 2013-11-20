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

}
