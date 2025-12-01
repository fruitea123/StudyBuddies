package data_access;

import org.mindrot.jbcrypt.BCrypt;
import use_case.signup.SignupPasswordHasher;

/**
 * Uses BCrypt to generate hash codes
 */
public class PasswordHasher implements SignupPasswordHasher {
    @Override
    public String hashPassword(String password) {
        return  BCrypt.hashpw(password, BCrypt.gensalt());
    }
}
