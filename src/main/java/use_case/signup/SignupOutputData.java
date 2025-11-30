package use_case.signup;

import java.util.List;

/**
 * Output Data for the Signup Use Case.
 */
public class SignupOutputData {

    private final String email;
    private final String firstName;
    private final String lastName;
    private final List<String> programs;
    private final int pfpIndex;
    private final String description;

    public SignupOutputData(String email,
                            String firstName,
                            String lastName,
                            List<String> programs,
                            int pfpIndex, String description)
    {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.programs = programs;
        this.pfpIndex = pfpIndex;
        this.description = description;
    }

    public String getEmail() { return email; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public List<String> getPrograms() { return programs; }
    public int getPfpIndex() { return pfpIndex; }
    public String getDescription() { return description; }
}
