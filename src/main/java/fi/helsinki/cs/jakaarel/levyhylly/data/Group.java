package fi.helsinki.cs.jakaarel.levyhylly.data;

/**
 * 
 * @author Jani Kaarela
 *
 */
public class Group {
    
    private Long id;
    private String name;
    
    public Group(Long id, String name) {
	this.id = id;
	this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    
}
