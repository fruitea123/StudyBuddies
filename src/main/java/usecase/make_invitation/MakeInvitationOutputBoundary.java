package usecase.make_invitation;

public interface MakeInvitationOutputBoundary {
    void presentSuccess(MakeInvitationOutputData response);
    void presentFailure(String errorMessage);
}