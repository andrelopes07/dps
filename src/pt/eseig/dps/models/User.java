package pt.eseig.dps.models;

public class User {

	private String name;
	private String password;
	private String email;
    private String accessLevel;

    public User(String name, String password, String email, String accessLevel) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.accessLevel = accessLevel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(String accessLevel) {
        this.accessLevel = accessLevel;
    }

}
