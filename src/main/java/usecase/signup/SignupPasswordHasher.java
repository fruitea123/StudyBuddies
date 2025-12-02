package usecase.signup;

public interface SignupPasswordHasher {
    String hashPassword(String password);
}
