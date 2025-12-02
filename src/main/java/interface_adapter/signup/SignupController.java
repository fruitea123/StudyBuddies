package interface_adapter.signup;

import use_case.signup.SignupInputBoundary;
import use_case.signup.SignupInputData;

import java.time.LocalDate;
import java.util.List;

/**
 * Controller for the Signup Use Case.
 */
public class SignupController {

    private final SignupInputBoundary userSignupUseCaseInteractor;

    public SignupController(SignupInputBoundary userSignupUseCaseInteractor) {
        this.userSignupUseCaseInteractor = userSignupUseCaseInteractor;
    }

//    /**
//     * Executes the Signup Use Case.
//     * @param username the username to sign up
//     * @param password1 the password
//     * @param password2 the password repeated
//     * @param firstName the username to sign up
//     * @param lastName the password
//     * @param dob the username to sign up
//     * @param numPrograms the password
//     * @param p1 the username to sign up
//     * @param p2 the password
//     * @param p3 the username to sign up
//     * @param icon the password
//     * @param description the password
//     */
    public void execute(SignupState signupState) {
            SignupInputData input = new SignupInputData(
                    signupState.getEmail(),
                    signupState.getPassword(),
                    signupState.getRepeatPassword(),
                    signupState.getFirstName(),
                    signupState.getLastName(),
                    signupState.getDateOfBirth(),
                    signupState.getNumPrograms(),
                    signupState.getProgram1(),
                    signupState.getProgram2(),
                    signupState.getProgram3(),
                    signupState.getIcon(),
                    signupState.getDescription()
            );

        userSignupUseCaseInteractor.execute(input);

    }

    /**
     * Executes the "switch to LoginView" Use Case.
     */
    public void switchToProfileView() {
        userSignupUseCaseInteractor.switchToProfileView();
    }
}
