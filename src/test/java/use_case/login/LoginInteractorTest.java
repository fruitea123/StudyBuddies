package use_case.login;

import entity.User;
import interface_adapter.makeinvitation.SessionCurrentUserGateway;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginInteractorTest {

    // ====== Stub: 内存版 DAO，专门用于测试 ======
    private static class InMemoryLoginUserDAO implements LoginUserDataAccessInterface {

        private User storedUser;
        private String currentUsername;

        void setStoredUser(User user) {
            this.storedUser = user;
        }

        @Override
        public boolean existsByEmail(String email) {
            return storedUser != null && storedUser.getEmail().equals(email);
        }

        @Override
        public User get(String email) {
            if (existsByEmail(email)) {
                return storedUser;
            }
            return null;
        }

        @Override
        public void save(User user) {
            this.storedUser = user;
        }

        @Override
        public void setCurrentUsername(String name) {
            this.currentUsername = name;
        }

        @Override
        public String getCurrentUsername() {
            return currentUsername;
        }
    }

    // ====== Stub: 记录结果用的 presenter ======
    private static class TestLoginPresenter implements LoginOutputBoundary {

        boolean failed = false;
        String failMessage;
        LoginOutputData successData;

        @Override
        public void prepareSuccessView(LoginOutputData response) {
            failed = false;
            successData = response;
        }

        @Override
        public void prepareFailView(String error) {
            failed = true;
            failMessage = error;
        }
    }

    // ====== Stub: 记录当前用户的 session gateway ======
    private static class TestSessionCurrentUserGateway extends SessionCurrentUserGateway {

        private User currentUser;

        @Override
        public void setCurrentUser(User user) {
            this.currentUser = user;
        }

        @Override
        public User getCurrentUser() {
            return currentUser;
        }
    }

    // 帮你构造一个 User（按你自己的构造函数改）
    private User makeUser(String email, String password) {
        return new User(
                email,
                password,
                "First",
                "Last",
                null,
                "",
                ""
        );
    }

    @Test
    void failsWhenUserDoesNotExist() {
        InMemoryLoginUserDAO userDAO = new InMemoryLoginUserDAO();
        TestLoginPresenter presenter = new TestLoginPresenter();
        TestSessionCurrentUserGateway sessionGateway = new TestSessionCurrentUserGateway();

        LoginInteractor interactor = new LoginInteractor(userDAO, presenter, sessionGateway);

        LoginInputData input = new LoginInputData("missing@example.com", "pwd");

        interactor.execute(input);

        assertTrue(presenter.failed);
        assertEquals("missing@example.com: Account does not exist.", presenter.failMessage);
        assertNull(presenter.successData);
        assertNull(sessionGateway.getCurrentUser());
    }

    @Test
    void failsWhenPasswordIncorrect() {
        InMemoryLoginUserDAO userDAO = new InMemoryLoginUserDAO();
        User user = makeUser("test@example.com", "correct");
        userDAO.setStoredUser(user);

        TestLoginPresenter presenter = new TestLoginPresenter();
        TestSessionCurrentUserGateway sessionGateway = new TestSessionCurrentUserGateway();

        LoginInteractor interactor = new LoginInteractor(userDAO, presenter, sessionGateway);

        LoginInputData input = new LoginInputData("test@example.com", "wrong");

        interactor.execute(input);

        assertTrue(presenter.failed);
        assertEquals("Incorrect password for \"test@example.com\".", presenter.failMessage);
        assertNull(presenter.successData);
        assertNull(sessionGateway.getCurrentUser());
    }

    @Test
    void succeedsWhenCredentialsCorrect() {
        InMemoryLoginUserDAO userDAO = new InMemoryLoginUserDAO();
        User user = makeUser("test@example.com", "correct");
        userDAO.setStoredUser(user);

        TestLoginPresenter presenter = new TestLoginPresenter();
        TestSessionCurrentUserGateway sessionGateway = new TestSessionCurrentUserGateway();

        LoginInteractor interactor = new LoginInteractor(userDAO, presenter, sessionGateway);

        LoginInputData input = new LoginInputData("test@example.com", "correct");

        interactor.execute(input);

        // 成功路径
        assertFalse(presenter.failed);
        assertNotNull(presenter.successData);
        assertEquals("test@example.com", presenter.successData.getUsername());

        // Session 设置了当前用户
        assertNotNull(sessionGateway.getCurrentUser());
        assertEquals("test@example.com", sessionGateway.getCurrentUser().getEmail());

        // DAO 里 currentUsername 也被设成 email
        assertEquals("test@example.com", userDAO.getCurrentUsername());
    }
}
