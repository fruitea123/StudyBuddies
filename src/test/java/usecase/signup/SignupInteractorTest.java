package usecase.signup;

import data_access.ForTestingUserDAO;
import entity.User;
import entity.UserFactory;
import org.junit.jupiter.api.Test;
import use_case.signup.*;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SignupInteractorTest {

    // Helper: valid base input
    private SignupInputData baseValidInput() {
        LocalDate dob = LocalDate.now().minusYears(20); // >= 16

        return new SignupInputData(
                "test@utoronto.ca",
                "Password1",
                "Password1",
                "First",
                "Last",
                dob,
                1,
                "Computer Science",
                "",     // program2
                "",     // program3
                "A cool person", // description
                "cat"   // icon/pfp
        );
    }

    // Helper: make interactor with custom presenter
    private SignupInteractor makeInteractor(SignupUserDataAccessInterface repo,
                                            SignupOutputBoundary presenter) {
        return new SignupInteractor(repo, presenter, new UserFactory());
    }

    @Test
    void successTest() {
        SignupUserDataAccessInterface repo = new ForTestingUserDAO();
        SignupInputData input = baseValidInput();

        SignupOutputBoundary presenter = new SignupOutputBoundary() {
            @Override
            public void prepareSuccessView(SignupOutputData response) {
                assertEquals("test@utoronto.ca", response.getEmail());
                assertTrue(repo.existsByEmail("test@utoronto.ca"));
            }

            @Override
            public void prepareFailView(String error) {
                fail("Failure not expected in successTest, got: " + error);
            }

            @Override
            public void switchToProfileView() {
                // not used here
            }
        };

        SignupInteractor interactor = makeInteractor(repo, presenter);
        interactor.execute(input);
    }

    @Test
    void emptyEmailTest() {
        SignupUserDataAccessInterface repo = new ForTestingUserDAO();
        SignupInputData input = new SignupInputData(
                "",                    // email
                "Password1",
                "Password1",
                "First",
                "Last",
                LocalDate.now().minusYears(20),
                1,
                "CS",
                "",
                "",
                "desc",
                "cat"
        );

        SignupOutputBoundary presenter = new SignupOutputBoundary() {
            @Override
            public void prepareSuccessView(SignupOutputData response) {
                fail("Success not expected when email is empty");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("This field cannot be empty", error);
            }

            @Override
            public void switchToProfileView() {}
        };

        SignupInteractor interactor = makeInteractor(repo, presenter);
        interactor.execute(input);
    }

    @Test
    void emptyPasswordTest() {
        SignupUserDataAccessInterface repo = new ForTestingUserDAO();
        SignupInputData input = new SignupInputData(
                "test@utoronto.ca",
                "",               // password
                "whatever",
                "First",
                "Last",
                LocalDate.now().minusYears(20),
                1,
                "CS",
                "",
                "",
                "desc",
                "cat"
        );

        SignupOutputBoundary presenter = new SignupOutputBoundary() {
            @Override
            public void prepareSuccessView(SignupOutputData response) {
                fail("Success not expected when password is empty");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("This field cannot be empty", error);
            }

            @Override
            public void switchToProfileView() {}
        };

        SignupInteractor interactor = makeInteractor(repo, presenter);
        interactor.execute(input);
    }

    @Test
    void emptyRepeatPasswordTest() {
        SignupUserDataAccessInterface repo = new ForTestingUserDAO();
        SignupInputData input = new SignupInputData(
                "test@utoronto.ca",
                "Password1",
                "",               // repeat password
                "First",
                "Last",
                LocalDate.now().minusYears(20),
                1,
                "CS",
                "",
                "",
                "desc",
                "cat"
        );

        SignupOutputBoundary presenter = new SignupOutputBoundary() {
            @Override
            public void prepareSuccessView(SignupOutputData response) {
                fail("Success not expected when repeat password is empty");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("This field cannot be empty", error);
            }

            @Override
            public void switchToProfileView() {}
        };

        SignupInteractor interactor = makeInteractor(repo, presenter);
        interactor.execute(input);
    }

    @Test
    void emptyFirstNameTest() {
        SignupUserDataAccessInterface repo = new ForTestingUserDAO();
        SignupInputData input = new SignupInputData(
                "test@utoronto.ca",
                "Password1",
                "Password1",
                "",               // first name
                "Last",
                LocalDate.now().minusYears(20),
                1,
                "CS",
                "",
                "",
                "desc",
                "cat"
        );

        SignupOutputBoundary presenter = new SignupOutputBoundary() {
            @Override
            public void prepareSuccessView(SignupOutputData response) {
                fail("Success not expected when first name is empty");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("This field cannot be empty", error);
            }

            @Override
            public void switchToProfileView() {}
        };

        SignupInteractor interactor = makeInteractor(repo, presenter);
        interactor.execute(input);
    }

    @Test
    void emptyLastNameTest() {
        SignupUserDataAccessInterface repo = new ForTestingUserDAO();
        SignupInputData input = new SignupInputData(
                "test@utoronto.ca",
                "Password1",
                "Password1",
                "First",
                "",               // last name
                LocalDate.now().minusYears(20),
                1,
                "CS",
                "",
                "",
                "desc",
                "cat"
        );

        SignupOutputBoundary presenter = new SignupOutputBoundary() {
            @Override
            public void prepareSuccessView(SignupOutputData response) {
                fail("Success not expected when last name is empty");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("This field cannot be empty", error);
            }

            @Override
            public void switchToProfileView() {}
        };

        SignupInteractor interactor = makeInteractor(repo, presenter);
        interactor.execute(input);
    }

    @Test
    void emptyDobTest() {
        SignupUserDataAccessInterface repo = new ForTestingUserDAO();
        SignupInputData input = new SignupInputData(
                "test@utoronto.ca",
                "Password1",
                "Password1",
                "First",
                "Last",
                null,             // DOB is null
                1,
                "CS",
                "",
                "",
                "desc",
                "cat"
        );

        SignupOutputBoundary presenter = new SignupOutputBoundary() {
            @Override
            public void prepareSuccessView(SignupOutputData response) {
                fail("Success not expected when DOB is null");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("This field cannot be empty", error);
            }

            @Override
            public void switchToProfileView() {}
        };

        SignupInteractor interactor = makeInteractor(repo, presenter);
        interactor.execute(input);
    }

    @Test
    void numProgramsLessThanOneTest() {
        SignupUserDataAccessInterface repo = new ForTestingUserDAO();
        SignupInputData input = new SignupInputData(
                "test@utoronto.ca",
                "Password1",
                "Password1",
                "First",
                "Last",
                LocalDate.now().minusYears(20),
                0,                // numPrograms < 1
                "",               // program1 (won't matter)
                "",
                "",
                "desc",
                "cat"
        );

        SignupOutputBoundary presenter = new SignupOutputBoundary() {
            @Override
            public void prepareSuccessView(SignupOutputData response) {
                fail("Success not expected when numPrograms < 1");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Number of programs must be at least 1", error);
            }

            @Override
            public void switchToProfileView() {}
        };

        SignupInteractor interactor = makeInteractor(repo, presenter);
        interactor.execute(input);
    }

    @Test
    void program1EmptyWhenRequiredTest() {
        SignupUserDataAccessInterface repo = new ForTestingUserDAO();
        SignupInputData input = new SignupInputData(
                "test@utoronto.ca",
                "Password1",
                "Password1",
                "First",
                "Last",
                LocalDate.now().minusYears(20),
                1,                // needs program1
                "",               // program1 empty
                "",
                "",
                "desc",
                "cat"
        );

        SignupOutputBoundary presenter = new SignupOutputBoundary() {
            @Override
            public void prepareSuccessView(SignupOutputData response) {
                fail("Success not expected when program1 is empty");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("This field cannot be empty", error);
            }

            @Override
            public void switchToProfileView() {}
        };

        SignupInteractor interactor = makeInteractor(repo, presenter);
        interactor.execute(input);
    }

    @Test
    void program2EmptyWhenNumPrograms2Test() {
        SignupUserDataAccessInterface repo = new ForTestingUserDAO();
        SignupInputData input = new SignupInputData(
                "test@utoronto.ca",
                "Password1",
                "Password1",
                "First",
                "Last",
                LocalDate.now().minusYears(20),
                2,
                "CS",
                "",               // program2 empty, required
                "",
                "desc",
                "cat"
        );

        SignupOutputBoundary presenter = new SignupOutputBoundary() {
            @Override
            public void prepareSuccessView(SignupOutputData response) {
                fail("Success not expected when program2 is empty and numPrograms=2");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("This field cannot be empty", error);
            }

            @Override
            public void switchToProfileView() {}
        };

        SignupInteractor interactor = makeInteractor(repo, presenter);
        interactor.execute(input);
    }

    @Test
    void program3EmptyWhenNumPrograms3Test() {
        SignupUserDataAccessInterface repo = new ForTestingUserDAO();
        SignupInputData input = new SignupInputData(
                "test@utoronto.ca",
                "Password1",
                "Password1",
                "First",
                "Last",
                LocalDate.now().minusYears(20),
                3,
                "CS",
                "Math",
                "",               // program3 empty, required
                "desc",
                "cat"
        );

        SignupOutputBoundary presenter = new SignupOutputBoundary() {
            @Override
            public void prepareSuccessView(SignupOutputData response) {
                fail("Success not expected when program3 is empty and numPrograms=3");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("This field cannot be empty", error);
            }

            @Override
            public void switchToProfileView() {}
        };

        SignupInteractor interactor = makeInteractor(repo, presenter);
        interactor.execute(input);
    }

    @Test
    void userAlreadyExistsTest() {
        SignupUserDataAccessInterface repo = new ForTestingUserDAO();
        UserFactory factory = new UserFactory();

        // Pre-save a user
        User existing = factory.create(
                "test@utoronto.ca",
                "Password1",
                "First",
                "Last",
                List.of("CS"),
                "cat",
                "desc"
        );
        repo.save(existing);

        SignupInputData input = baseValidInput();

        SignupOutputBoundary presenter = new SignupOutputBoundary() {
            @Override
            public void prepareSuccessView(SignupOutputData response) {
                fail("Success not expected when user already exists");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("User already exists", error);
            }

            @Override
            public void switchToProfileView() {}
        };

        SignupInteractor interactor = makeInteractor(repo, presenter);
        interactor.execute(input);
    }

    @Test
    void invalidEmailDomainTest() {
        SignupUserDataAccessInterface repo = new ForTestingUserDAO();
        SignupInputData input = new SignupInputData(
                "test@gmail.com",      // not @utoronto.ca
                "Password1",
                "Password1",
                "First",
                "Last",
                LocalDate.now().minusYears(20),
                1,
                "CS",
                "",
                "",
                "desc",
                "cat"
        );

        SignupOutputBoundary presenter = new SignupOutputBoundary() {
            @Override
            public void prepareSuccessView(SignupOutputData response) {
                fail("Success not expected when email domain is wrong");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Email address must be a UofT email address", error);
            }

            @Override
            public void switchToProfileView() {}
        };

        SignupInteractor interactor = makeInteractor(repo, presenter);
        interactor.execute(input);
    }

    @Test
    void shortPasswordTest() {
        SignupUserDataAccessInterface repo = new ForTestingUserDAO();
        SignupInputData input = new SignupInputData(
                "test@utoronto.ca",
                "Pw1",             // too short
                "Pw1",
                "First",
                "Last",
                LocalDate.now().minusYears(20),
                1,
                "CS",
                "",
                "",
                "desc",
                "cat"
        );

        SignupOutputBoundary presenter = new SignupOutputBoundary() {
            @Override
            public void prepareSuccessView(SignupOutputData response) {
                fail("Success not expected when password too short");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Password must be at least 8 characters", error);
            }

            @Override
            public void switchToProfileView() {}
        };

        SignupInteractor interactor = makeInteractor(repo, presenter);
        interactor.execute(input);
    }

    @Test
    void missingUppercaseInPasswordTest() {
        SignupUserDataAccessInterface repo = new ForTestingUserDAO();
        SignupInputData input = new SignupInputData(
                "test@utoronto.ca",
                "password1",       // no uppercase
                "password1",
                "First",
                "Last",
                LocalDate.now().minusYears(20),
                1,
                "CS",
                "",
                "",
                "desc",
                "cat"
        );

        SignupOutputBoundary presenter = new SignupOutputBoundary() {
            @Override
            public void prepareSuccessView(SignupOutputData response) {
                fail("Success not expected when no uppercase");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Password must contain at least 1 uppercase letter", error);
            }

            @Override
            public void switchToProfileView() {}
        };

        SignupInteractor interactor = makeInteractor(repo, presenter);
        interactor.execute(input);
    }

    @Test
    void missingLowercaseInPasswordTest() {
        SignupUserDataAccessInterface repo = new ForTestingUserDAO();
        SignupInputData input = new SignupInputData(
                "test@utoronto.ca",
                "PASSWORD1",       // no lowercase
                "PASSWORD1",
                "First",
                "Last",
                LocalDate.now().minusYears(20),
                1,
                "CS",
                "",
                "",
                "desc",
                "cat"
        );

        SignupOutputBoundary presenter = new SignupOutputBoundary() {
            @Override
            public void prepareSuccessView(SignupOutputData response) {
                fail("Success not expected when no lowercase");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Password must contain at least 1 lowercase letter", error);
            }

            @Override
            public void switchToProfileView() {}
        };

        SignupInteractor interactor = makeInteractor(repo, presenter);
        interactor.execute(input);
    }

    @Test
    void missingNumberInPasswordTest() {
        SignupUserDataAccessInterface repo = new ForTestingUserDAO();
        SignupInputData input = new SignupInputData(
                "test@utoronto.ca",
                "Password",        // no number
                "Password",
                "First",
                "Last",
                LocalDate.now().minusYears(20),
                1,
                "CS",
                "",
                "",
                "desc",
                "cat"
        );

        SignupOutputBoundary presenter = new SignupOutputBoundary() {
            @Override
            public void prepareSuccessView(SignupOutputData response) {
                fail("Success not expected when no number");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Password must contain at least 1 number", error);
            }

            @Override
            public void switchToProfileView() {}
        };

        SignupInteractor interactor = makeInteractor(repo, presenter);
        interactor.execute(input);
    }

    @Test
    void passwordMismatchTest() {
        SignupUserDataAccessInterface repo = new ForTestingUserDAO();
        SignupInputData input = new SignupInputData(
                "test@utoronto.ca",
                "Password1",
                "Password2",       // mismatch
                "First",
                "Last",
                LocalDate.now().minusYears(20),
                1,
                "CS",
                "",
                "",
                "desc",
                "cat"
        );

        SignupOutputBoundary presenter = new SignupOutputBoundary() {
            @Override
            public void prepareSuccessView(SignupOutputData response) {
                fail("Success not expected when passwords mismatch");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Passwords don't match", error);
            }

            @Override
            public void switchToProfileView() {}
        };

        SignupInteractor interactor = makeInteractor(repo, presenter);
        interactor.execute(input);
    }

    @Test
    void underageUserTest() {
        SignupUserDataAccessInterface repo = new ForTestingUserDAO();
        LocalDate dob = LocalDate.now().minusYears(10); // < 16

        SignupInputData input = new SignupInputData(
                "test@utoronto.ca",
                "Password1",
                "Password1",
                "First",
                "Last",
                dob,
                1,
                "CS",
                "",
                "",
                "desc",
                "cat"
        );

        SignupOutputBoundary presenter = new SignupOutputBoundary() {
            @Override
            public void prepareSuccessView(SignupOutputData response) {
                fail("Success not expected when user is under 16");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("You must be at least 16 years old", error);
            }

            @Override
            public void switchToProfileView() {}
        };

        SignupInteractor interactor = makeInteractor(repo, presenter);
        interactor.execute(input);
    }

    @Test
    void switchToProfileViewTest() {
        SignupUserDataAccessInterface repo = new ForTestingUserDAO();

        final boolean[] called = {false};

        SignupOutputBoundary presenter = new SignupOutputBoundary() {
            @Override
            public void prepareSuccessView(SignupOutputData response) {}

            @Override
            public void prepareFailView(String error) {}

            @Override
            public void switchToProfileView() {
                called[0] = true;
            }
        };

        SignupInteractor interactor = makeInteractor(repo, presenter);
        interactor.switchToProfileView();

        assertTrue(called[0], "switchToProfileView() should delegate to presenter");
    }
}
