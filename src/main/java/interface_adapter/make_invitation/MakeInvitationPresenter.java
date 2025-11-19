package interface_adapter.make_invitation;

import use_case.make_invitation.MakeInvitationOutputBoundary;
import use_case.make_invitation.MakeInvitationOutputData;

public class MakeInvitationPresenter implements MakeInvitationOutputBoundary {
    private final MakeInvitationViewModel vm;

    public MakeInvitationPresenter(MakeInvitationViewModel vm) {
        this.vm = vm;
    }

    @Override
    public void presentSuccess(MakeInvitationOutputData response) {
        vm.setFailMessage("");
        vm.setSuccessMessage(response.getMessage());

    }

    @Override
    public void presentFailure(String errorMessage) {
        vm.setSuccessMessage("");
        vm.setFailMessage(errorMessage);
    }
}
