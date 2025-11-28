package interface_adapter.make_invitation;

import use_case.make_invitation.MakeInvitationOutputBoundary;
import use_case.make_invitation.MakeInvitationOutputData;

public class MakeInvitationPresenter implements MakeInvitationOutputBoundary {

    private final MakeInvitationViewModel vm;

    public MakeInvitationPresenter(MakeInvitationViewModel vm) {
        this.vm = vm;
    }

    @Override
    public void presentSuccess(MakeInvitationOutputData out) {
        MakeInvitationState s = vm.getState();
        s.setSuccessMessage(out.getMessage());
        s.setErrorMessage("");
        // notify view to update
//        vm.firePropertyChanged();
        vm.setState(s);
        vm.firePropertyChange();
    }

    @Override
    public void presentFailure(String errorMessage) {
        MakeInvitationState s = vm.getState();
        s.setSuccessMessage("");
        s.setErrorMessage(errorMessage);
//
        vm.setState(s);
        vm.firePropertyChange();
    }
}