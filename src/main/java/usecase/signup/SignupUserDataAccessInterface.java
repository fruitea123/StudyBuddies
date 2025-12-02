package usecase.signup;

import entity.User;

/**
 * DAO interface for the Signup Use Case.
 */
public interface SignupUserDataAccessInterface {

    /**
     * Checks if the given user exists.
     * @param email the email to look for
     * @return true if a user with the given user exists; false otherwise
     */
    boolean existsByEmail(String email);

    /**
     * Saves the user.
     * @param user the user to save
     */
    void save(User user);
}
