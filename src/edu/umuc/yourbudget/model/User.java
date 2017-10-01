package edu.umuc.yourbudget.model;

/**
 * Created by Christian on 9/30/17.
 */
public class User {

    private String firstName;
    private String username;
    private String id;

    public User(String firstName, String username, String id) {
        this.firstName = firstName;
        this.username = username;
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
