package fi.helsinki.cs.jakaarel.levyhylly;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Programmatic Spring Framework context configuration.
 * 
 * @author Jani Kaarela
 */
@EnableWebMvc
@Configuration
public class ApplicationConfiguration extends WebMvcConfigurerAdapter {

}
