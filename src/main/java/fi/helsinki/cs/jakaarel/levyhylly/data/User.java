package fi.helsinki.cs.jakaarel.levyhylly.data;

import java.util.SortedSet;
import java.util.TreeSet;

/**
 * 
 * @author Jani Kaarela
 *
 */
public class User {
    
    private Long id;
    private String login;
    private String name;
    private SortedSet<Long> groupIds;
    
    public User(Long id, String login, String name, SortedSet<Long> groupIds) {
	this.id = id;
	this.login = login;
	this.name = name;
	this.groupIds = groupIds;
    }
    
    User() {
	groupIds = new TreeSet<Long>();
    }

    public Long getId() {
        return id;
    }
    
    void setId(Long id) {
	this.id = id;
    }

    public String getLogin() {
        return login;
    }
    
    void setLogin(String login) {
	this.login = login;
    }

    public String getName() {
        return name;
    }
    
    void setName(String name) {
	this.name = name;
    }
    
    public boolean isMemberOf(Group group) {
	return groupIds.contains(group.getId());
    }
    
    void addGroup(Long groupId) {
	groupIds.add(groupId);
    }
    
}
