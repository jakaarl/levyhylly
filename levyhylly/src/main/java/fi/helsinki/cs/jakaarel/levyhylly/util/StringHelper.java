package fi.helsinki.cs.jakaarel.levyhylly.util;

/**
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
