package interface_adapter.make_invitation;

import interface_adapter.ViewModel;

public class MakeInvitationViewModel extends ViewModel<MakeInvitationState> {

    // for ViewManager/view
    public static final String VIEW_NAME = "make invitation";

    // title for view
    public static final String TITLE = "Make Invitation";
    public MakeInvitationViewModel() {
        super(VIEW_NAME);
        this.setState(new MakeInvitationState());
    }

}