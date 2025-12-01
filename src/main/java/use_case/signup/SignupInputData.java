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
    private final String program1;
    private final String program2;
    private final String program3;
    private final String pfp;
    private final String description;

    public SignupInputData(
            String email,
            String password,
            String repeatPassword,
            String firstName,
            String lastName,
            LocalDate DOB,
            int programNumber,
            String program1,
            String program2,
            String program3,
            String description,
            String pfp) {
        this.email = email;
        this.password = password;
        this.repeatPassword = repeatPassword;
        this.firstName = firstName;
        this.lastName = lastName;
        this.DOB = DOB;
        this.programNumber = programNumber;
        this.program1 = program1;
        this.program2 = program2;
        this.program3 = program3;
        this.pfp = pfp;
        this.description = description;
    }

    String getEmail() {
        return email;
    }
    String getPassword() { return password;}
    String getRepeatPassword() { return repeatPassword;}
    String getFirstName() {
        return firstName;
    }
    String getLastName() { return lastName; }
    LocalDate getDateOfBirth() { return DOB; }
    int getNumPrograms() { return programNumber; }
    String getProgram1() { return program1; }
    String getProgram2() { return program2; }
    String getProgram3() { return program3; }
    String getIcon() { return pfp; }
    String getDescription() { return description; }
}
