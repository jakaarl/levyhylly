package fi.helsinki.cs.jakaarel.levyhylly;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * 
 * @author Jani Kaarela
 * 
 */
@Configuration
public class BeanConfiguration {

	private static final String[] BUNDLE_BASENAMES = new String[] { "WEB-INF/uitexts" };

	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasenames(BUNDLE_BASENAMES);
		return messageSource;
	}

}
