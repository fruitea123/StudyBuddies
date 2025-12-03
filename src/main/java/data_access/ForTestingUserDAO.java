package data_access;

import entity.User;
import use_case.signup.SignupUserDataAccessInterface;

import java.util.HashMap;
import java.util.Map;

public class ForTestingUserDAO implements SignupUserDataAccessInterface {

    private final Map<String, User> users = new HashMap<>();

    @Override
    public boolean existsByEmail(String email) {
        return users.containsKey(email);
    }

    @Override
    public void save(User user) {
        users.put(user.getEmail(), user);
    }

    @Override
    public User get(String email) {
        return users.get(email);
    }

    @Override
    public void setCurrentUsername(String name) {}

    @Override
    public String getCurrentUsername() { return null; }
}
