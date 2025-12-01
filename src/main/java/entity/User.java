package entity;

import java.time.LocalDate;
import java.util.List;

/**
 * An entity representing a user. Users have a username (email),
 * password, first name, last name, the programs they are enrolled in,
 * a profile photo (represented by an index), and a description.
 */
public class User {

    private final String email;
    private final String password;
    private final String firstName;
    private final String lastName;
    private final LocalDate dateOfBirth;
    private final List<String> programs;
    private final String icon;
    private final String description;

    /**
     * Creates a new user with the given non-empty email, password, firstName, lastName, and programs.
     * @param email the username
     * @param password the password
     * @param firstName the first name
     * @param lastName the last name
     * @param dateOfBirth the date of birth
     * @param programs the programs the user is enrolled in
     * @param icon the index that correlates to each profile photo
     * @param description the description of the user
     * @throws IllegalArgumentException if the password or name are empty
     */
    public User(String email,
                String password,
                String firstName,
                String lastName,
                LocalDate dateOfBirth,
                List<String> programs,
                String icon,
                String description) {
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
        if ("".equals(dateOfBirth)) {
            throw new IllegalArgumentException("Date of Birth cannot be empty");
        }
        if ("".equals(programs)) {
            throw new IllegalArgumentException("Program(s) cannot be empty");
        }

        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.programs = programs;
        this.icon = icon;
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() { return firstName; }

    public String getLastName() { return lastName; }

    public LocalDate getDateOfBirth() { return dateOfBirth; }

    public List<String> getPrograms() { return programs; }

    public String getIcon() { return icon; }

    public String getDescription() { return description; }
}
