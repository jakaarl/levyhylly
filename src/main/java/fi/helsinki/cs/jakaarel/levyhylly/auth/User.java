package fi.helsinki.cs.jakaarel.levyhylly.auth;

/**
 * 
 * @author Jani Kaarela
 *
 */
public class User {
    
    private Long id;
    private String login;
    private String name;
    
    public User(Long id, String login, String name) {
	this.id = id;
	this.login = login;
	this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getName() {
        return name;
    }
    
    void setName(String name) {
	this.name = name;
    }
    
}
