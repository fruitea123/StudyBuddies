package use_case.signup;

public interface SignupPasswordHasher {
    String hashPassword(String password);
}
