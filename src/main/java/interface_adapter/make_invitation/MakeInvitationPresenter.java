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
        // 取状态对象，设置成功消息，清空错误消息
        MakeInvitationState s = vm.getState();
        s.setSuccessMessage(out.getMessage());
        s.setErrorMessage("");
        // 通知 View：state 更新了
        vm.firePropertyChanged();
    }

    @Override
    public void presentFailure(String errorMessage) {
        // 设置错误消息，清空成功消息
        MakeInvitationState s = vm.getState();
        s.setSuccessMessage("");
        s.setErrorMessage(errorMessage);
        vm.firePropertyChanged();
    }
}