package entity;

import java.util.List;

/**
 * Factory for creating CommonUser objects.
 */
public class UserFactory {

    public User create(String email,
                       String password,
                       String firstName,
                       String lastName,
                       List<String> programs,
                       String icon,
                       String descripion) {
        return new User(
                email,
                password,
                firstName,
                lastName,
                programs,
                icon,
                descripion);
    }
}
