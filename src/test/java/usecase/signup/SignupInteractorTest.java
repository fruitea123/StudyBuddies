package usecase.signup;

import data_access.InMemoryUserDataAccessObject;
import data_access.PasswordHasher;
import entity.UserFactory;
import entity.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SignupInteractorTest {

    @Test
    void successTest() {
        List<String> programs = new ArrayList<>(); ;
        programs.add("Bussiness");
        SignupInputData inputData = new SignupInputData("paul@gmail.com", "password",
                "wrong", "Paul", "Edwards",
                LocalDate.of(2004,06,12), 1, programs, "hi I'm Paul",
                0);
        SignupUserDataAccessInterface userRepository = new InMemoryUserDataAccessObject();

        // This creates a successPresenter that tests whether the test case is as we expect.
        SignupOutputBoundary successPresenter = new SignupOutputBoundary() {
            @Override
            public void prepareSuccessView(SignupOutputData user) {
                // 2 things to check: the output data is correct, and the user has been created in the DAO.
                assertEquals("paul@gmail.com", user.getEmail());
                assertTrue(userRepository.existsByEmail("paul@gmail.com"));
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case failure is unexpected.");
            }

            @Override
            public void switchToProfileView() {
                // This is expected
            }
        };

        SignupInputBoundary interactor = new SignupInteractor(userRepository, new PasswordHasher(), successPresenter, new UserFactory());
        interactor.execute(inputData);
    }

    @Test
    void failurePasswordMismatchTest() {
        List<String> programs = new ArrayList<>(); ;
        programs.add("Bussiness");
        SignupInputData inputData = new SignupInputData("paul@gmail.com", "password",
                "wrong", "Paul", "Edwards",
                LocalDate.of(2004,06,12), 1, programs, "hi I'm Paul",
                0);

        SignupUserDataAccessInterface userRepository = new InMemoryUserDataAccessObject();

        // This creates a presenter that tests whether the test case is as we expect.
        SignupOutputBoundary failurePresenter = new SignupOutputBoundary() {
            @Override
            public void prepareSuccessView(SignupOutputData user) {
                // this should never be reached since the test case should fail
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Passwords don't match.", error);
            }

            @Override
            public void switchToProfileView() {
                // This is expected
            }
        };

        SignupInputBoundary interactor = new SignupInteractor(userRepository, new PasswordHasher(), failurePresenter, new UserFactory());
        interactor.execute(inputData);
    }

    @Test
    void failureUserExistsTest() {
        List<String> programs = new ArrayList<>(); ;
        programs.add("Bussiness");
        SignupInputData inputData = new SignupInputData("paul@gmail.com", "password",
                "wrong", "Paul", "Edwards",
                LocalDate.of(2004,06,12), 1, programs, "hi I'm Paul",
                0);
        SignupUserDataAccessInterface userRepository = new InMemoryUserDataAccessObject();

        // Add Paul to the repo so that when we check later they already exist
        UserFactory factory = new UserFactory();
        User user = factory.create("paul@gmail.com", "password",
                "Paul", "Edwards",
                programs, 0, "hi I'm Paul");
        userRepository.save(user);

        // This creates a presenter that tests whether the test case is as we expect.
        SignupOutputBoundary failurePresenter = new SignupOutputBoundary() {
            @Override
            public void prepareSuccessView(SignupOutputData user) {
                // this should never be reached since the test case should fail
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("User already exists.", error);
            }

            @Override
            public void switchToProfileView() {
                // This is expected
            }
        };

        SignupInputBoundary interactor = new SignupInteractor(userRepository, new PasswordHasher(), failurePresenter, new UserFactory());
        interactor.execute(inputData);
    }
}