package entity;

import java.time.LocalDate;
import java.util.List;

/**
 * An entity representing a user. Users have a username (email),
 * password, repeated password, first name, last name,
 * number of programs they are enrolled in,
 * the programs they are enrolled in,
 * a profile photo (represented by an index), and a description.
 */
public class User {

    // I didn't know whether or not user should hold all the info that
    // is collected at sign up, but I put it in just in case
    private final String email;
    private final String password;
    private final String repeatPassword;
    private final String firstName;
    private final String lastName;
    private final LocalDate dateOfBirth;
    private final int numPrograms;
    private final List<String> programs;
    private final String icon;
    private final String description;

    /**
     * Creates a new user with the given non-empty email, password, repeatPassword, firstName, lastName,
     * dateOfBirth, numPrograms, programs, icon, and description.
     * @param email the username
     * @param password the password
     * @param repeatPassword the repeated password
     * @param firstName the first name
     * @param lastName the last name
     * @param dateOfBirth the date of birth
     * @param numPrograms the number of programs the user is enrolled in
     * @param programs the programs the user is enrolled in
     * @param icon the profile photo
     * @param description the description of the user
     * @throws IllegalArgumentException if the password or name are empty
     */
    public User(String email,
                String password,
                String repeatPassword,
                String firstName,
                String lastName,
                LocalDate dateOfBirth,
                int numPrograms,
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
        if ("".equals(programs)) {
            throw new IllegalArgumentException("Program(s) cannot be empty");
        }

        this.email = email;
        this.password = password;
        this.repeatPassword = repeatPassword;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.numPrograms = numPrograms;
        this.programs = programs;
        this.icon = icon;
        this.description = description;
    }

    public String getEmail() { return email; }

    public String getPassword() { return password; }

    public String getRepeatPassword() { return repeatPassword; }

    public String getFirstName() { return firstName; }

    public String getLastName() { return lastName; }

    public LocalDate getDateOfBirth() { return dateOfBirth; }

    public int getNumPrograms() { return numPrograms; }

    public List<String> getPrograms() { return programs; }

    public String getIcon() { return icon; }

    public String getDescription() { return description; }
}
