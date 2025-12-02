package interface_adapter.signup;

import java.time.LocalDate;

/**
 * The state for the Signup View Model.
 */
public class SignupState {
    private String email = "";
    private String emailError;
    private String password = "";
    private String passwordError;
    private String repeatPassword = "";
    private String repeatPasswordError;
    private String firstName = "";
    private String firstNameError;
    private String lastName = "";
    private String lastNameError;
    private LocalDate dateOfBirth;
    private String dateOfBirthError;
    private int numPrograms = 0;
    private String numProgramsError;
    private String program1 = "";
    private String program1Error;
    private String program2 = "";
    private String program2Error;
    private String program3 = "";
    private String program3Error;
    private String icon = "";
    private String iconError;
    private String description = "";
    private String descriptionError;

    public String getEmail() {
        return email;
    }
    public String getEmailError() {
        return emailError;
    }

    public String getPassword() {
        return password;
    }
    public String getPasswordError() {
        return passwordError;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }
    public String getRepeatPasswordError() {
        return repeatPasswordError;
    }

    public String getFirstName() { return firstName; }
    public String getFirstNameError() { return firstNameError; }

    public String getLastName() { return lastName; }
    public String getLastNameError() { return lastNameError; }

    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public String getDateOfBirthError() { return dateOfBirthError; }

    public int getNumPrograms() { return numPrograms; }
    public String getNumProgramsError() { return numProgramsError; }

    public String getProgram1() { return program1; }
    public String getProgram1Error() { return program1Error; }

    public String getProgram2() { return program2; }
    public String getProgram2Error() { return program2Error; }

    public String getProgram3() { return program3; }
    public String getProgram3Error() { return program3Error; }

    public String getIcon() { return icon; }
    public String getIconError() { return iconError; }

    public String getDescription() { return description; }
    public String getDescriptionError() { return descriptionError; }

    public void setEmail(String email) { this.email = email; }

    public void setEmailError(String emailError) {
        this.emailError = emailError;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPasswordError(String passwordError) {
        this.passwordError = passwordError;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }

    public void setRepeatPasswordError(String repeatPasswordError) {
        this.repeatPasswordError = repeatPasswordError;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setFirstNameError(String firstNameError) {
        this.firstNameError = firstNameError;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setLastNameError(String lastNameError) {
        this.lastNameError = lastNameError;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setDateOfBirthError(String dateOfBirthError) {
        this.dateOfBirthError = dateOfBirthError;
    }

    public void setNumPrograms(int numPrograms) {
        this.numPrograms = numPrograms;
    }

    public void setNumProgramsError(String numProgramsError) {
        this.numProgramsError = numProgramsError;
    }

    public void setProgram1(String program1) {
        this.program1 = program1;
    }

    public void setProgram1Error(String program1Error) {
        this.program1Error = program1Error;
    }

    public void setProgram2(String program2) { this.program2 = program2; }

    public void setProgram2Error(String program2Error) {
        this.program2Error = program2Error;
    }

    public void setProgram3(String program3) { this.program3 = program3; }

    public void setProgram3Error(String program3Error) {
        this.program3Error = program3Error;
    }

    public void setIcon(String icon) { this.icon = icon; }
    public void setIconError(String iconError) { this.iconError = iconError; }

    public void setDescription(String description) { this.description = description; }
    public void setDescriptionError(String descriptionError) { this.descriptionError = descriptionError; }



    @Override
    public String toString() {
        return "SignupState{"
                + "email='" + email + '\''
                + ", password='" + password + '\''
                + ", repeatPassword='" + repeatPassword + '\''
                + ", firstName='" + firstName + '\''
                + ", lastName='" + lastName + '\''
                + ", dateOfBirth='" + dateOfBirth + '\''
                + ", numPrograms='" + numPrograms + '\''
                + ", program1='" + program1 + '\''
                + ", program2='" + program2 + '\''
                + ", program3='" + program3 + '\''
                + '}';
    }
}
