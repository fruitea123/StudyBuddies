package entity;

import java.util.List;

/**
 * Factory for creating CommonUser objects.
 */
public class UserFactory {

    public User create(String email,
                       String hashedPassword,
                       String firstName,
                       String lastName,
                       List<String> programs,
                       int pfpindex,
                       String descripion) {
        return new User(email, hashedPassword,
                        firstName, lastName,
                        programs, pfpindex, descripion);
    }
}
