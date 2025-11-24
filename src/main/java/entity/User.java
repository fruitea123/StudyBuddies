package entity;

import java.util.List;

/**
 * A simple entity representing a user. Users have a username (email),
 * password, first name, last name, the programs they are enrolled in,
 * a profile photo, and a description.
 */
public class User {

    private final String email;
    private final String password;
    private final String firstName;
    private final String lastName;
    private final List<String> programs;
    private final int pfpIndex;
    private final String description;

    /**
     * Creates a new user with the given non-empty email, password, firstName, lastName, and programs.
     * @param email the username
     * @param password the password
     * @param firstName the first name
     * @param lastName the last name
     * @param programs the programs the user is enrolled in
     * @param pfpIndex the index that correlates to each profile photo
     * @param description the description of the user
     * @throws IllegalArgumentException if the password or name are empty
     */
    public User(String email, String password, String firstName,
                String lastName, List<String> programs, int pfpIndex, String description) {
        if ("".equals(email)) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        if ("".equals(password)) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
        if ("".equals(firstName)) {
            throw new IllegalArgumentException("First Name cannot be empty");
        }
        if ("".equals(lastName)) {
            throw new IllegalArgumentException("Last Name cannot be empty");
        }
        if ("".equals(programs)) {
            throw new IllegalArgumentException("Program(s) cannot be empty");
        }

        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.programs = programs;
        this.pfpIndex = pfpIndex;
        this.description = description;
    }

    public String getName() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() { return firstName; }

    public String getLastName() { return lastName; }

    public List<String> getPrograms() { return programs; }

    public int getPfpIndex() { return pfpIndex; }

    public String getDescription() { return description; }
}
