package use_case.signup;

import java.util.List;

/**
 * Output Data for the Signup Use Case.
 */
public class SignupOutputData {

    private final String email;
    private final String password;
//    private final String repeatPassword;
    private final String firstName;
    private final String lastName;
//    private final LocalDate dateOfBirth;
//    private final int numPrograms;
    private final List<String> programs;
//    private final String program2;
//    private final String program3;
    private final String icon;
    private final String description;

    public SignupOutputData(String email,
                            String password,
//                            String repeatPassword,
                            String firstName,
                            String lastName,
//                            LocalDate dateOfBirth,
//                            int numPrograms,
                            List<String> programs,
                            String icon,
                            String description)
    {
        this.email = email;
        this.password = password;
//        this.repeatPassword = repeatPassword;
        this.firstName = firstName;
        this.lastName = lastName;
//        this.dateOfBirth = dateOfBirth;
//        this.numPrograms = numPrograms;
        this.programs = programs;
//        this.program2 = program2;
//        this.program3 = program3;
        this.icon = icon;
        this.description = description;

    }

    public String getEmail() { return email; }
    public String getPassword() { return password; }
//    public String getRepeatPassword() { return repeatPassword; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
//    public LocalDate getDateOfBirth() { return dateOfBirth; }
//    public int getNumPrograms() { return numPrograms; }
    public List<String> getPrograms() { return programs; }
//    public String getProgram2() { return program2; }
//    public String getProgram3() { return program3; }
    public String getIcon() { return icon; }
    public String getDescription() { return description; }
}
