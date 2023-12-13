package fsoft.ads.process;

public class DatabaseAccessInfo {
	private String username;
    private String password;
    // Add other necessary fields such as database URL, driver, etc.

    // Constructors, getters, and setters

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
