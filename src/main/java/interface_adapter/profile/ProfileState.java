package interface_adapter.profile;

import java.time.LocalDate;

public class ProfileState {

    private String username;
    private String name;
    private java.util.List<String> programs;
    private String description;
    private String profileImagePath;

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public java.util.List<String> getPrograms() { return programs; }
    public void setPrograms(java.util.List<String> programs) { this.programs = programs; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getIcon() { return profileImagePath; }
    public void setIcon(String profileImagePath) { this.profileImagePath = profileImagePath; }
}