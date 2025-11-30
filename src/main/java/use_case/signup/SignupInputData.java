package use_case.signup;

import java.time.LocalDate;
import java.util.List;

/**
 * The Input Data for the Signup Use Case.
 */
public class SignupInputData {

    private final String email;
    private final String password;
    private final String repeatPassword;
    private final String firstName;
    private final String lastName;
    private final LocalDate DOB;
    private final int programNumber;
    private final List<String> programs;
    private final int pfp;
    private final String description;

    public SignupInputData(
            String email, String password, String repeatPassword,
            String firstName, String lastName, LocalDate DOB,
            int programNumber, List<String> programs, String description, int pfp) {
        this.email = email;
        this.password = password;
        this.repeatPassword = repeatPassword;
        this.firstName = firstName;
        this.lastName = lastName;
        this.DOB = DOB;
        this.programNumber = programNumber;
        this.programs = programs;
        this.pfp = pfp;
        this.description = description;
    }

    String getEmail() {
        return email;
    }
    String getPassword1() { return password;}
    String getPassword2() { return repeatPassword;}
    String getFirstName() {
        return firstName;
    }
    String getLastName() { return lastName; }
    LocalDate getDOB() { return DOB; }
    int getProgramNumber() { return programNumber; }
    List<String> getPrograms() { return programs; }
    int getPfp() { return pfp; }
    String getDescription() { return description; }
}
